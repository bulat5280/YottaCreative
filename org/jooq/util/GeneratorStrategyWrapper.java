package org.jooq.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jooq.Record;
import org.jooq.impl.AbstractRoutine;
import org.jooq.impl.TableRecordImpl;
import org.jooq.impl.UDTRecordImpl;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.tools.StringUtils;

class GeneratorStrategyWrapper extends AbstractGeneratorStrategy {
   private final Map<Class<?>, Set<String>> reservedColumns = new HashMap();
   final Generator generator;
   final GeneratorStrategy delegate;
   final AbstractGenerator.Language language;

   GeneratorStrategyWrapper(Generator generator, GeneratorStrategy delegate, AbstractGenerator.Language language) {
      this.generator = generator;
      this.delegate = delegate;
      this.language = language;
   }

   public String getTargetDirectory() {
      return this.delegate.getTargetDirectory();
   }

   public void setTargetDirectory(String directory) {
      this.delegate.setTargetDirectory(directory);
   }

   public String getTargetPackage() {
      return this.delegate.getTargetPackage();
   }

   public void setTargetPackage(String packageName) {
      this.delegate.setTargetPackage(packageName);
   }

   public void setInstanceFields(boolean instanceFields) {
      this.delegate.setInstanceFields(instanceFields);
   }

   public boolean getInstanceFields() {
      return this.delegate.getInstanceFields();
   }

   public String getFileHeader(Definition definition, GeneratorStrategy.Mode mode) {
      return this.delegate.getFileHeader(definition, mode);
   }

   public String getJavaIdentifier(Definition definition) {
      String identifier = this.getFixedJavaIdentifier(definition);
      if (identifier != null) {
         return identifier;
      } else {
         identifier = GenerationUtil.convertToIdentifier(this.delegate.getJavaIdentifier(definition), this.language);
         if (!(definition instanceof ColumnDefinition) && !(definition instanceof AttributeDefinition)) {
            if (definition instanceof TableDefinition) {
               SchemaDefinition schema = definition.getSchema();
               if (identifier.equals(this.getJavaIdentifier(schema))) {
                  return identifier + "_";
               }
            } else if (definition instanceof SchemaDefinition) {
               CatalogDefinition catalog = definition.getCatalog();
               if (identifier.equals(this.getJavaIdentifier(catalog))) {
                  return identifier + "_";
               }
            }
         } else {
            TypedElementDefinition<?> e = (TypedElementDefinition)definition;
            if (identifier.equals(this.getJavaIdentifier(e.getContainer()))) {
               return identifier + "_";
            }

            if (identifier.equals(this.getJavaPackageName(e.getContainer()).replaceAll("\\..*", ""))) {
               return identifier + "_";
            }
         }

         return identifier;
      }
   }

   public String getJavaSetterName(Definition definition, GeneratorStrategy.Mode mode) {
      return this.disambiguateMethod(definition, GenerationUtil.convertToIdentifier(this.delegate.getJavaSetterName(definition, mode), this.language));
   }

   public String getJavaGetterName(Definition definition, GeneratorStrategy.Mode mode) {
      return this.disambiguateMethod(definition, GenerationUtil.convertToIdentifier(this.delegate.getJavaGetterName(definition, mode), this.language));
   }

   public String getJavaMethodName(Definition definition, GeneratorStrategy.Mode mode) {
      String methodName = this.delegate.getJavaMethodName(definition, mode);
      methodName = this.overload(definition, mode, methodName);
      methodName = GenerationUtil.convertToIdentifier(methodName, this.language);
      return this.disambiguateMethod(definition, methodName);
   }

   private String overload(Definition definition, GeneratorStrategy.Mode mode, String identifier) {
      if (!StringUtils.isBlank(definition.getOverload())) {
         identifier = identifier + this.getOverloadSuffix(definition, mode, definition.getOverload());
      }

      return identifier;
   }

   private String disambiguateMethod(Definition definition, String method) {
      Set<String> reserved = null;
      if (definition instanceof AttributeDefinition) {
         reserved = this.reservedColumns(UDTRecordImpl.class);
      } else if (definition instanceof ColumnDefinition) {
         if (((TableDefinition)((ColumnDefinition)definition).getContainer()).getPrimaryKey() != null) {
            reserved = this.reservedColumns(UpdatableRecordImpl.class);
         } else {
            reserved = this.reservedColumns(TableRecordImpl.class);
         }
      } else if (definition instanceof ParameterDefinition) {
         reserved = this.reservedColumns(AbstractRoutine.class);
      }

      if (reserved != null) {
         if (reserved.contains(method)) {
            return method + "_";
         }

         if (method.startsWith("set")) {
            String base = method.substring(3);
            if (reserved.contains("get" + base) || reserved.contains("is" + base)) {
               return method + "_";
            }
         }
      }

      return method;
   }

   private Set<String> reservedColumns(Class<?> clazz) {
      if (clazz == null) {
         return Collections.emptySet();
      } else {
         Set<String> result = (Set)this.reservedColumns.get(clazz);
         if (result == null) {
            result = new HashSet();
            this.reservedColumns.put(clazz, result);
            ((Set)result).addAll(this.reservedColumns(clazz.getSuperclass()));
            Class[] var3 = clazz.getInterfaces();
            int var4 = var3.length;

            int var5;
            for(var5 = 0; var5 < var4; ++var5) {
               Class<?> c = var3[var5];
               ((Set)result).addAll(this.reservedColumns(c));
            }

            Method[] var7 = clazz.getDeclaredMethods();
            var4 = var7.length;

            for(var5 = 0; var5 < var4; ++var5) {
               Method m = var7[var5];
               if (m.getParameterTypes().length == 0) {
                  ((Set)result).add(m.getName());
               }
            }

            if (this.language == AbstractGenerator.Language.SCALA) {
               Field[] var8 = clazz.getDeclaredFields();
               var4 = var8.length;

               for(var5 = 0; var5 < var4; ++var5) {
                  Field f = var8[var5];
                  ((Set)result).add(f.getName());
               }
            }
         }

         return (Set)result;
      }
   }

   public String getJavaClassExtends(Definition definition, GeneratorStrategy.Mode mode) {
      return this.delegate.getJavaClassExtends(definition, mode);
   }

   public List<String> getJavaClassImplements(Definition definition, GeneratorStrategy.Mode mode) {
      Set<String> result = new LinkedHashSet(this.delegate.getJavaClassImplements(definition, mode));
      if (mode == GeneratorStrategy.Mode.INTERFACE) {
         result.add(Serializable.class.getName());
      } else if (mode == GeneratorStrategy.Mode.POJO && !this.generator.generateInterfaces()) {
         result.add(Serializable.class.getName());
      }

      return new ArrayList(result);
   }

   public String getJavaClassName(Definition definition, GeneratorStrategy.Mode mode) {
      String name = this.getFixedJavaClassName(definition);
      if (name != null) {
         return name;
      } else if (definition instanceof TableDefinition && !this.generator.generateRecords() && mode == GeneratorStrategy.Mode.RECORD) {
         return Record.class.getSimpleName();
      } else {
         String className = this.delegate.getJavaClassName(definition, mode);
         className = this.overload(definition, mode, className);
         className = GenerationUtil.convertToIdentifier(className, this.language);
         className = GenerationUtil.escapeWindowsForbiddenNames(className);
         return className;
      }
   }

   public String getJavaPackageName(Definition definition, GeneratorStrategy.Mode mode) {
      if (!this.generator.generateRecords() && mode == GeneratorStrategy.Mode.RECORD && definition instanceof TableDefinition) {
         return Record.class.getPackage().getName();
      } else {
         String[] split = this.delegate.getJavaPackageName(definition, mode).split("\\.");

         for(int i = 0; i < split.length; ++i) {
            split[i] = GenerationUtil.convertToIdentifier(split[i], this.language);
            split[i] = GenerationUtil.escapeWindowsForbiddenNames(split[i]);
         }

         return StringUtils.join(split, ".").replaceAll("\\._?\\.", ".");
      }
   }

   public String getJavaMemberName(Definition definition, GeneratorStrategy.Mode mode) {
      String identifier = GenerationUtil.convertToIdentifier(this.delegate.getJavaMemberName(definition, mode), this.language);
      return identifier.equals(this.getJavaPackageName(definition, mode).replaceAll("\\..*", "")) ? identifier + "_" : identifier;
   }

   public String getOverloadSuffix(Definition definition, GeneratorStrategy.Mode mode, String overloadIndex) {
      return this.delegate.getOverloadSuffix(definition, mode, overloadIndex);
   }
}
