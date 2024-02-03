package org.jooq.impl;

import java.beans.ConstructorProperties;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import org.jooq.Attachable;
import org.jooq.AttachableInternal;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordType;
import org.jooq.exception.MappingException;
import org.jooq.tools.Convert;
import org.jooq.tools.StringUtils;
import org.jooq.tools.reflect.Reflect;

public class DefaultRecordMapper<R extends Record, E> implements RecordMapper<R, E> {
   private final Field<?>[] fields;
   private final RecordType<R> rowType;
   private final Class<? extends E> type;
   private final Configuration configuration;
   private RecordMapper<R, E> delegate;

   public DefaultRecordMapper(RecordType<R> rowType, Class<? extends E> type) {
      this(rowType, type, (Object)null, (Configuration)null);
   }

   DefaultRecordMapper(RecordType<R> rowType, Class<? extends E> type, Configuration configuration) {
      this(rowType, type, (Object)null, configuration);
   }

   DefaultRecordMapper(RecordType<R> rowType, Class<? extends E> type, E instance) {
      this(rowType, type, instance, (Configuration)null);
   }

   DefaultRecordMapper(RecordType<R> rowType, Class<? extends E> type, E instance, Configuration configuration) {
      this.rowType = rowType;
      this.fields = rowType.fields();
      this.type = type;
      this.configuration = configuration;
      this.init(instance);
   }

   private final void init(E instance) {
      if (this.type.isArray()) {
         this.delegate = new DefaultRecordMapper.ArrayMapper(instance);
      } else if (Stream.class.isAssignableFrom(this.type)) {
         DefaultRecordMapper<R, Object[]> local = new DefaultRecordMapper(this.rowType, Object[].class, this.configuration);
         this.delegate = (r) -> {
            return Stream.of((Object[])local.map(r));
         };
      } else if (!this.type.isPrimitive() && !DefaultDataType.types().contains(this.type) && !Enum.class.isAssignableFrom(this.type)) {
         if (Modifier.isAbstract(this.type.getModifiers())) {
            this.delegate = new DefaultRecordMapper.ProxyMapper();
         } else if (AbstractRecord.class.isAssignableFrom(this.type)) {
            this.delegate = new DefaultRecordMapper.RecordToRecordMapper();
         } else {
            try {
               this.delegate = new DefaultRecordMapper.MutablePOJOMapper(this.type.getDeclaredConstructor(), instance);
            } catch (NoSuchMethodException var8) {
               Constructor<E>[] constructors = (Constructor[])this.type.getDeclaredConstructors();
               Constructor[] var3 = constructors;
               int var4 = constructors.length;

               int var5;
               Constructor constructor;
               for(var5 = 0; var5 < var4; ++var5) {
                  constructor = var3[var5];
                  ConstructorProperties properties = (ConstructorProperties)constructor.getAnnotation(ConstructorProperties.class);
                  if (properties != null) {
                     this.delegate = new DefaultRecordMapper.ImmutablePOJOMapperWithConstructorProperties(constructor, properties);
                     return;
                  }
               }

               var3 = constructors;
               var4 = constructors.length;

               for(var5 = 0; var5 < var4; ++var5) {
                  constructor = var3[var5];
                  Class<?>[] parameterTypes = constructor.getParameterTypes();
                  if (parameterTypes.length == this.fields.length) {
                     this.delegate = new DefaultRecordMapper.ImmutablePOJOMapper(constructor, parameterTypes);
                     return;
                  }
               }

               throw new MappingException("No matching constructor found on type " + this.type + " for record " + this);
            }
         }
      } else {
         this.delegate = new DefaultRecordMapper.ValueTypeMapper();
      }
   }

   public final E map(R record) {
      if (record == null) {
         return null;
      } else {
         try {
            return attach(this.delegate.map(record), record);
         } catch (MappingException var3) {
            throw var3;
         } catch (Exception var4) {
            throw new MappingException("An error ocurred when mapping record to " + this.type, var4);
         }
      }
   }

   private static <E> E attach(E attachable, Record record) {
      if (attachable instanceof Attachable && record instanceof AttachableInternal) {
         Attachable a = (Attachable)attachable;
         AttachableInternal r = (AttachableInternal)record;
         if (Tools.attachRecords(r.configuration())) {
            a.attach(r.configuration());
         }
      }

      return attachable;
   }

   private class ImmutablePOJOMapperWithConstructorProperties implements RecordMapper<R, E> {
      private final Constructor<E> constructor;
      private final Class<?>[] parameterTypes;
      private final Object[] parameterValues;
      private final List<String> propertyNames;
      private final boolean useAnnotations;
      private final List<java.lang.reflect.Field>[] members;
      private final Method[] methods;
      private final Integer[] propertyIndexes;

      ImmutablePOJOMapperWithConstructorProperties(Constructor<E> constructor, ConstructorProperties properties) {
         this.constructor = constructor;
         this.propertyNames = Arrays.asList(properties.value());
         this.useAnnotations = Tools.hasColumnAnnotations(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type);
         this.parameterTypes = constructor.getParameterTypes();
         this.parameterValues = new Object[this.parameterTypes.length];
         this.members = new List[DefaultRecordMapper.this.fields.length];
         this.methods = new Method[DefaultRecordMapper.this.fields.length];
         this.propertyIndexes = new Integer[DefaultRecordMapper.this.fields.length];

         for(int i = 0; i < DefaultRecordMapper.this.fields.length; ++i) {
            Field<?> field = DefaultRecordMapper.this.fields[i];
            String name = field.getName();
            String nameLC = StringUtils.toCamelCaseLC(name);
            if (this.useAnnotations) {
               this.members[i] = Tools.getAnnotatedMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, name);
               this.methods[i] = Tools.getAnnotatedGetter(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, name);
            } else {
               this.members[i] = Tools.getMatchingMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, name);
               this.methods[i] = Tools.getMatchingGetter(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, name);
            }

            for(int j = 0; j < this.propertyNames.size(); ++j) {
               if (name.equals(this.propertyNames.get(j)) || nameLC.equals(this.propertyNames.get(j))) {
                  this.propertyIndexes[i] = j;
                  break;
               }
            }
         }

      }

      public final E map(R record) {
         try {
            for(int i = 0; i < DefaultRecordMapper.this.fields.length; ++i) {
               if (this.propertyIndexes[i] != null) {
                  this.parameterValues[this.propertyIndexes[i]] = record.get(i);
               } else {
                  Iterator var3 = this.members[i].iterator();

                  while(var3.hasNext()) {
                     java.lang.reflect.Field member = (java.lang.reflect.Field)var3.next();
                     int indexx = this.propertyNames.indexOf(member.getName());
                     if (indexx >= 0) {
                        this.parameterValues[indexx] = record.get(i);
                     }
                  }

                  if (this.methods[i] != null) {
                     String name = Tools.getPropertyName(this.methods[i].getName());
                     int index = this.propertyNames.indexOf(name);
                     if (index >= 0) {
                        this.parameterValues[index] = record.get(i);
                     }
                  }
               }
            }

            Object[] converted = Convert.convert(this.parameterValues, this.parameterTypes);
            return ((Constructor)Reflect.accessible(this.constructor)).newInstance(converted);
         } catch (Exception var6) {
            throw new MappingException("An error ocurred when mapping record to " + DefaultRecordMapper.this.type, var6);
         }
      }
   }

   private class ImmutablePOJOMapper implements RecordMapper<R, E> {
      private final Constructor<E> constructor;
      private final Class<?>[] parameterTypes;

      public ImmutablePOJOMapper(Constructor<E> constructor, Class<?>[] parameterTypes) {
         this.constructor = (Constructor)Reflect.accessible(constructor);
         this.parameterTypes = parameterTypes;
      }

      public final E map(R record) {
         try {
            Object[] converted = Convert.convert(record.intoArray(), this.parameterTypes);
            return this.constructor.newInstance(converted);
         } catch (Exception var3) {
            throw new MappingException("An error ocurred when mapping record to " + DefaultRecordMapper.this.type, var3);
         }
      }
   }

   private class MutablePOJOMapper implements RecordMapper<R, E> {
      private final Constructor<? extends E> constructor;
      private final boolean useAnnotations;
      private final List<java.lang.reflect.Field>[] members;
      private final List<Method>[] methods;
      private final Map<String, List<RecordMapper<R, Object>>> nested;
      private final E instance;

      MutablePOJOMapper(Constructor<? extends E> constructor, E instance) {
         this.constructor = (Constructor)Reflect.accessible(constructor);
         this.useAnnotations = Tools.hasColumnAnnotations(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type);
         this.members = new List[DefaultRecordMapper.this.fields.length];
         this.methods = new List[DefaultRecordMapper.this.fields.length];
         this.nested = new HashMap();
         this.instance = instance;
         Map<String, Field<?>[]> nestedFields = new HashMap();

         String prefix;
         for(int i = 0; i < DefaultRecordMapper.this.fields.length; ++i) {
            Field<?> field = DefaultRecordMapper.this.fields[i];
            prefix = field.getName();
            if (this.useAnnotations) {
               this.members[i] = Tools.getAnnotatedMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, prefix);
               this.methods[i] = Tools.getAnnotatedSetters(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, prefix);
            } else {
               int dot = prefix.indexOf(46);
               if (dot > -1) {
                  String prefixx = prefix.substring(0, dot);
                  Field<?>[] f = (Field[])nestedFields.get(prefixx);
                  if (f == null) {
                     f = (Field[])Collections.nCopies(DefaultRecordMapper.this.fields.length, DSL.field("")).toArray(Tools.EMPTY_FIELD);
                     nestedFields.put(prefixx, f);
                  }

                  f[i] = DSL.field(DSL.name(prefix.substring(prefixx.length() + 1)), field.getDataType());
                  this.members[i] = Collections.emptyList();
                  this.methods[i] = Collections.emptyList();
               } else {
                  this.members[i] = Tools.getMatchingMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, prefix);
                  this.methods[i] = Tools.getMatchingSetters(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, prefix);
               }
            }
         }

         Iterator var11 = nestedFields.entrySet().iterator();

         while(var11.hasNext()) {
            Entry<String, Field<?>[]> entry = (Entry)var11.next();
            prefix = (String)entry.getKey();
            List<RecordMapper<R, Object>> list = new ArrayList();
            Iterator var14 = Tools.getMatchingMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, prefix).iterator();

            while(var14.hasNext()) {
               java.lang.reflect.Field member = (java.lang.reflect.Field)var14.next();
               list.add(DefaultRecordMapper.this.new RemovingPrefixRecordMapper(new DefaultRecordMapper(new Fields((Field[])entry.getValue()), member.getType(), (Object)null, DefaultRecordMapper.this.configuration), DefaultRecordMapper.this.fields, prefix));
            }

            var14 = Tools.getMatchingSetters(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, prefix).iterator();

            while(var14.hasNext()) {
               Method method = (Method)var14.next();
               list.add(DefaultRecordMapper.this.new RemovingPrefixRecordMapper(new DefaultRecordMapper(new Fields((Field[])entry.getValue()), method.getParameterTypes()[0], (Object)null, DefaultRecordMapper.this.configuration), DefaultRecordMapper.this.fields, prefix));
            }

            this.nested.put(prefix, list);
         }

      }

      public final E map(R record) {
         try {
            E result = this.instance != null ? this.instance : this.constructor.newInstance();

            label85:
            for(int i = 0; i < DefaultRecordMapper.this.fields.length; ++i) {
               Iterator var4 = this.members[i].iterator();

               while(var4.hasNext()) {
                  java.lang.reflect.Field member = (java.lang.reflect.Field)var4.next();
                  if ((member.getModifiers() & 16) == 0) {
                     this.map(record, result, member, i);
                  }
               }

               var4 = this.methods[i].iterator();

               while(true) {
                  while(true) {
                     if (!var4.hasNext()) {
                        continue label85;
                     }

                     Method method = (Method)var4.next();
                     Class<?> mType = method.getParameterTypes()[0];
                     Object value = record.get(i, mType);
                     if (value instanceof Collection && List.class.isAssignableFrom(mType)) {
                        Class componentType = (Class)((ParameterizedType)method.getGenericParameterTypes()[0]).getActualTypeArguments()[0];
                        method.invoke(result, Convert.convert((Collection)value, componentType));
                     } else {
                        method.invoke(result, record.get(i, mType));
                     }
                  }
               }
            }

            Iterator var12 = this.nested.entrySet().iterator();

            while(var12.hasNext()) {
               Entry<String, List<RecordMapper<R, Object>>> entry = (Entry)var12.next();
               String prefix = (String)entry.getKey();
               Iterator var16 = ((List)entry.getValue()).iterator();

               while(var16.hasNext()) {
                  RecordMapper<R, Object> mapper = (RecordMapper)var16.next();
                  Object valuex = mapper.map(record);
                  Iterator var9 = Tools.getMatchingMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, prefix).iterator();

                  while(var9.hasNext()) {
                     java.lang.reflect.Field memberx = (java.lang.reflect.Field)var9.next();
                     if ((memberx.getModifiers() & 16) == 0) {
                        this.map(valuex, result, memberx);
                     }
                  }

                  var9 = Tools.getMatchingSetters(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, prefix).iterator();

                  while(var9.hasNext()) {
                     Method methodx = (Method)var9.next();
                     methodx.invoke(result, valuex);
                  }
               }
            }

            return result;
         } catch (Exception var11) {
            throw new MappingException("An error ocurred when mapping record to " + DefaultRecordMapper.this.type, var11);
         }
      }

      private final void map(Record record, Object result, java.lang.reflect.Field member, int index) throws IllegalAccessException {
         Class<?> mType = member.getType();
         if (mType.isPrimitive()) {
            if (mType == Byte.TYPE) {
               this.map(record.get(index, Byte.TYPE), result, member);
            } else if (mType == Short.TYPE) {
               this.map(record.get(index, Short.TYPE), result, member);
            } else if (mType == Integer.TYPE) {
               this.map(record.get(index, Integer.TYPE), result, member);
            } else if (mType == Long.TYPE) {
               this.map(record.get(index, Long.TYPE), result, member);
            } else if (mType == Float.TYPE) {
               this.map(record.get(index, Float.TYPE), result, member);
            } else if (mType == Double.TYPE) {
               this.map(record.get(index, Double.TYPE), result, member);
            } else if (mType == Boolean.TYPE) {
               this.map(record.get(index, Boolean.TYPE), result, member);
            } else if (mType == Character.TYPE) {
               this.map(record.get(index, Character.TYPE), result, member);
            }
         } else {
            Object value = record.get(index, mType);
            if (value instanceof Collection && List.class.isAssignableFrom(mType)) {
               Class componentType = (Class)((ParameterizedType)member.getGenericType()).getActualTypeArguments()[0];
               member.set(result, Convert.convert((Collection)value, componentType));
            } else {
               this.map(value, result, member);
            }
         }

      }

      private final void map(Object value, Object result, java.lang.reflect.Field member) throws IllegalAccessException {
         Class<?> mType = member.getType();
         if (mType.isPrimitive()) {
            if (mType == Byte.TYPE) {
               member.setByte(result, (Byte)value);
            } else if (mType == Short.TYPE) {
               member.setShort(result, (Short)value);
            } else if (mType == Integer.TYPE) {
               member.setInt(result, (Integer)value);
            } else if (mType == Long.TYPE) {
               member.setLong(result, (Long)value);
            } else if (mType == Float.TYPE) {
               member.setFloat(result, (Float)value);
            } else if (mType == Double.TYPE) {
               member.setDouble(result, (Double)value);
            } else if (mType == Boolean.TYPE) {
               member.setBoolean(result, (Boolean)value);
            } else if (mType == Character.TYPE) {
               member.setChar(result, (Character)value);
            }
         } else {
            member.set(result, value);
         }

      }
   }

   private class RemovingPrefixRecordMapper implements RecordMapper<R, Object> {
      private final RecordMapper<R, Object> d;
      private final Field<?>[] f;

      RemovingPrefixRecordMapper(RecordMapper<R, Object> d, Field<?>[] fields, String prefix) {
         this.d = d;
         this.f = new Field[fields.length];
         String dotted = prefix + ".";

         for(int i = 0; i < fields.length; ++i) {
            if (fields[i].getName().startsWith(dotted)) {
               this.f[i] = DSL.field(DSL.name(fields[i].getName().substring(dotted.length() + 1)), fields[i].getDataType());
            }
         }

      }

      public Object map(R record) {
         AbstractRecord copy = (AbstractRecord)DSL.using(DefaultRecordMapper.this.configuration).newRecord(this.f);

         for(int i = 0; i < this.f.length; ++i) {
            if (this.f[i] != null) {
               copy.set(i, record.get(i));
            }
         }

         return this.d.map(record);
      }
   }

   private class RecordToRecordMapper implements RecordMapper<R, AbstractRecord> {
      private RecordToRecordMapper() {
      }

      public final AbstractRecord map(R record) {
         try {
            if (record instanceof AbstractRecord) {
               return (AbstractRecord)((AbstractRecord)record).intoRecord(DefaultRecordMapper.this.type);
            } else {
               throw new MappingException("Cannot map record " + record + " to type " + DefaultRecordMapper.this.type);
            }
         } catch (Exception var3) {
            throw new MappingException("An error ocurred when mapping record to " + DefaultRecordMapper.this.type, var3);
         }
      }

      // $FF: synthetic method
      RecordToRecordMapper(Object x1) {
         this();
      }
   }

   private class ProxyMapper implements RecordMapper<R, E> {
      Constructor<Lookup> constructor;

      private ProxyMapper() {
      }

      public final E map(R record) {
         return (DefaultRecordMapper.this.new MutablePOJOMapper((Constructor)null, this.proxy())).map(record);
      }

      private E proxy() {
         final Object[] result = new Object[1];
         final Map<String, Object> map = new HashMap();
         InvocationHandler handler = new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) {
               String name = method.getName();
               int length = args == null ? 0 : args.length;
               if (length == 0 && name.startsWith("get")) {
                  return map.get(name.substring(3));
               } else if (length == 0 && name.startsWith("is")) {
                  return map.get(name.substring(2));
               } else {
                  if (length == 1 && name.startsWith("set")) {
                     map.put(name.substring(3), args[0]);
                  } else if (method.isDefault()) {
                     try {
                        if (ProxyMapper.this.constructor == null) {
                           ProxyMapper.this.constructor = (Constructor)Reflect.accessible(Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE));
                        }

                        Class<?> declaringClass = method.getDeclaringClass();
                        return ((Lookup)ProxyMapper.this.constructor.newInstance(declaringClass, 2)).unreflectSpecial(method, declaringClass).bindTo(result[0]).invokeWithArguments(args);
                     } catch (Throwable var7) {
                        throw new MappingException("Cannot invoke default method", var7);
                     }
                  }

                  return null;
               }
            }
         };
         result[0] = Proxy.newProxyInstance(DefaultRecordMapper.this.type.getClassLoader(), new Class[]{DefaultRecordMapper.this.type}, handler);
         return result[0];
      }

      // $FF: synthetic method
      ProxyMapper(Object x1) {
         this();
      }
   }

   private class ValueTypeMapper implements RecordMapper<R, E> {
      private ValueTypeMapper() {
      }

      public final E map(R record) {
         int size = record.size();
         if (size != 1) {
            throw new MappingException("Cannot map multi-column record of degree " + size + " to value type " + DefaultRecordMapper.this.type);
         } else {
            return record.get(0, (Class)DefaultRecordMapper.this.type);
         }
      }

      // $FF: synthetic method
      ValueTypeMapper(Object x1) {
         this();
      }
   }

   private class ArrayMapper implements RecordMapper<R, E> {
      private final E instance;

      ArrayMapper(E instance) {
         this.instance = instance;
      }

      public final E map(R record) {
         int size = record.size();
         Class<?> componentType = DefaultRecordMapper.this.type.getComponentType();
         Object[] result = (Object[])((Object[])(this.instance != null ? this.instance : java.lang.reflect.Array.newInstance(componentType, size)));
         if (size > result.length) {
            result = (Object[])((Object[])java.lang.reflect.Array.newInstance(componentType, size));
         }

         for(int i = 0; i < size; ++i) {
            result[i] = Convert.convert(record.get(i), componentType);
         }

         return result;
      }
   }
}
