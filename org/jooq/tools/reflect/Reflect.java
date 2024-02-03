package org.jooq.tools.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Reflect {
   private final Object object;
   private final boolean isClass;

   public static Reflect on(String name) throws ReflectException {
      return on(forName(name));
   }

   public static Reflect on(Class<?> clazz) {
      return new Reflect(clazz);
   }

   public static Reflect on(Object object) {
      return new Reflect(object);
   }

   public static <T extends AccessibleObject> T accessible(T accessible) {
      if (accessible == null) {
         return null;
      } else {
         if (accessible instanceof Member) {
            Member member = (Member)accessible;
            if (Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
               return accessible;
            }
         }

         if (!accessible.isAccessible()) {
            accessible.setAccessible(true);
         }

         return accessible;
      }
   }

   private Reflect(Class<?> type) {
      this.object = type;
      this.isClass = true;
   }

   private Reflect(Object object) {
      this.object = object;
      this.isClass = false;
   }

   public <T> T get() {
      return this.object;
   }

   public Reflect set(String name, Object value) throws ReflectException {
      try {
         Field field = this.field0(name);
         field.set(this.object, unwrap(value));
         return this;
      } catch (Exception var4) {
         throw new ReflectException(var4);
      }
   }

   public <T> T get(String name) throws ReflectException {
      return this.field(name).get();
   }

   public Reflect field(String name) throws ReflectException {
      try {
         Field field = this.field0(name);
         return on(field.get(this.object));
      } catch (Exception var3) {
         throw new ReflectException(var3);
      }
   }

   private Field field0(String name) throws ReflectException {
      Class type = this.type();

      try {
         return type.getField(name);
      } catch (NoSuchFieldException var6) {
         while(true) {
            try {
               return (Field)accessible(type.getDeclaredField(name));
            } catch (NoSuchFieldException var5) {
               type = type.getSuperclass();
               if (type == null) {
                  throw new ReflectException(var6);
               }
            }
         }
      }
   }

   public Map<String, Reflect> fields() {
      Map<String, Reflect> result = new LinkedHashMap();
      Class type = this.type();

      do {
         Field[] var3 = type.getDeclaredFields();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            if (!this.isClass ^ Modifier.isStatic(field.getModifiers())) {
               String name = field.getName();
               if (!result.containsKey(name)) {
                  result.put(name, this.field(name));
               }
            }
         }

         type = type.getSuperclass();
      } while(type != null);

      return result;
   }

   public Reflect call(String name) throws ReflectException {
      return this.call(name);
   }

   public Reflect call(String name, Object... args) throws ReflectException {
      Class[] types = types(args);

      try {
         Method method = this.exactMethod(name, types);
         return on(method, this.object, args);
      } catch (NoSuchMethodException var7) {
         try {
            Method method = this.similarMethod(name, types);
            return on(method, this.object, args);
         } catch (NoSuchMethodException var6) {
            throw new ReflectException(var6);
         }
      }
   }

   private Method exactMethod(String name, Class<?>[] types) throws NoSuchMethodException {
      Class type = this.type();

      try {
         return type.getMethod(name, types);
      } catch (NoSuchMethodException var7) {
         while(true) {
            try {
               return type.getDeclaredMethod(name, types);
            } catch (NoSuchMethodException var6) {
               type = type.getSuperclass();
               if (type == null) {
                  throw new NoSuchMethodException();
               }
            }
         }
      }
   }

   private Method similarMethod(String name, Class<?>[] types) throws NoSuchMethodException {
      Class<?> type = this.type();
      Method[] var4 = type.getMethods();
      int var5 = var4.length;

      int var6;
      Method method;
      for(var6 = 0; var6 < var5; ++var6) {
         method = var4[var6];
         if (this.isSimilarSignature(method, name, types)) {
            return method;
         }
      }

      do {
         var4 = type.getDeclaredMethods();
         var5 = var4.length;

         for(var6 = 0; var6 < var5; ++var6) {
            method = var4[var6];
            if (this.isSimilarSignature(method, name, types)) {
               return method;
            }
         }

         type = type.getSuperclass();
      } while(type != null);

      throw new NoSuchMethodException("No similar method " + name + " with params " + Arrays.toString(types) + " could be found on type " + this.type() + ".");
   }

   private boolean isSimilarSignature(Method possiblyMatchingMethod, String desiredMethodName, Class<?>[] desiredParamTypes) {
      return possiblyMatchingMethod.getName().equals(desiredMethodName) && this.match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
   }

   public Reflect create() throws ReflectException {
      return this.create();
   }

   public Reflect create(Object... args) throws ReflectException {
      Class[] types = types(args);

      try {
         Constructor<?> constructor = this.type().getDeclaredConstructor(types);
         return on(constructor, args);
      } catch (NoSuchMethodException var8) {
         Constructor[] var4 = this.type().getDeclaredConstructors();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Constructor<?> constructor = var4[var6];
            if (this.match(constructor.getParameterTypes(), types)) {
               return on(constructor, args);
            }
         }

         throw new ReflectException(var8);
      }
   }

   public <P> P as(Class<P> proxyType) {
      final boolean isMap = this.object instanceof Map;
      InvocationHandler handler = new InvocationHandler() {
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String name = method.getName();

            try {
               return Reflect.on(Reflect.this.object).call(name, args).get();
            } catch (ReflectException var8) {
               if (isMap) {
                  Map<String, Object> map = (Map)Reflect.this.object;
                  int length = args == null ? 0 : args.length;
                  if (length == 0 && name.startsWith("get")) {
                     return map.get(Reflect.property(name.substring(3)));
                  }

                  if (length == 0 && name.startsWith("is")) {
                     return map.get(Reflect.property(name.substring(2)));
                  }

                  if (length == 1 && name.startsWith("set")) {
                     map.put(Reflect.property(name.substring(3)), args[0]);
                     return null;
                  }
               }

               throw var8;
            }
         }
      };
      return Proxy.newProxyInstance(proxyType.getClassLoader(), new Class[]{proxyType}, handler);
   }

   private static String property(String string) {
      int length = string.length();
      if (length == 0) {
         return "";
      } else {
         return length == 1 ? string.toLowerCase() : string.substring(0, 1).toLowerCase() + string.substring(1);
      }
   }

   private boolean match(Class<?>[] declaredTypes, Class<?>[] actualTypes) {
      if (declaredTypes.length == actualTypes.length) {
         for(int i = 0; i < actualTypes.length; ++i) {
            if (actualTypes[i] != Reflect.NULL.class && !wrapper(declaredTypes[i]).isAssignableFrom(wrapper(actualTypes[i]))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.object.hashCode();
   }

   public boolean equals(Object obj) {
      return obj instanceof Reflect ? this.object.equals(((Reflect)obj).get()) : false;
   }

   public String toString() {
      return this.object.toString();
   }

   private static Reflect on(Constructor<?> constructor, Object... args) throws ReflectException {
      try {
         return on(((Constructor)accessible(constructor)).newInstance(args));
      } catch (Exception var3) {
         throw new ReflectException(var3);
      }
   }

   private static Reflect on(Method method, Object object, Object... args) throws ReflectException {
      try {
         accessible(method);
         if (method.getReturnType() == Void.TYPE) {
            method.invoke(object, args);
            return on(object);
         } else {
            return on(method.invoke(object, args));
         }
      } catch (Exception var4) {
         throw new ReflectException(var4);
      }
   }

   private static Object unwrap(Object object) {
      return object instanceof Reflect ? ((Reflect)object).get() : object;
   }

   private static Class<?>[] types(Object... values) {
      if (values == null) {
         return new Class[0];
      } else {
         Class<?>[] result = new Class[values.length];

         for(int i = 0; i < values.length; ++i) {
            Object value = values[i];
            result[i] = value == null ? Reflect.NULL.class : value.getClass();
         }

         return result;
      }
   }

   private static Class<?> forName(String name) throws ReflectException {
      try {
         return Class.forName(name);
      } catch (Exception var2) {
         throw new ReflectException(var2);
      }
   }

   public Class<?> type() {
      return this.isClass ? (Class)this.object : this.object.getClass();
   }

   public static Class<?> wrapper(Class<?> type) {
      if (type == null) {
         return null;
      } else {
         if (type.isPrimitive()) {
            if (Boolean.TYPE == type) {
               return Boolean.class;
            }

            if (Integer.TYPE == type) {
               return Integer.class;
            }

            if (Long.TYPE == type) {
               return Long.class;
            }

            if (Short.TYPE == type) {
               return Short.class;
            }

            if (Byte.TYPE == type) {
               return Byte.class;
            }

            if (Double.TYPE == type) {
               return Double.class;
            }

            if (Float.TYPE == type) {
               return Float.class;
            }

            if (Character.TYPE == type) {
               return Character.class;
            }

            if (Void.TYPE == type) {
               return Void.class;
            }
         }

         return type;
      }
   }

   private static class NULL {
   }
}
