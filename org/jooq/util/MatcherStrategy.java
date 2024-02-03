package org.jooq.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.tools.StringUtils;
import org.jooq.util.jaxb.MatcherRule;
import org.jooq.util.jaxb.MatcherTransformType;
import org.jooq.util.jaxb.Matchers;
import org.jooq.util.jaxb.MatchersFieldType;
import org.jooq.util.jaxb.MatchersRoutineType;
import org.jooq.util.jaxb.MatchersSchemaType;
import org.jooq.util.jaxb.MatchersSequenceType;
import org.jooq.util.jaxb.MatchersTableType;

public class MatcherStrategy extends DefaultGeneratorStrategy {
   private final Matchers matchers;

   public MatcherStrategy(Matchers matchers) {
      this.matchers = matchers;
   }

   private final String match(Definition definition, String expression, MatcherRule rule) {
      return rule != null ? this.match(definition, expression, rule.getExpression(), rule.getTransform()) : null;
   }

   private final String match(Definition definition, String expression, String ruleExpression) {
      return this.match(definition, expression, ruleExpression, (MatcherTransformType)null);
   }

   private final String match(Definition definition, String expression, String ruleExpression, MatcherTransformType ruleTransformType) {
      if (ruleTransformType != null && ruleExpression == null) {
         ruleExpression = "$0";
      }

      if (ruleExpression != null) {
         Pattern p = Pattern.compile(StringUtils.defaultIfEmpty(expression, "^.*$").trim());
         Matcher m = p.matcher(definition.getName());
         if (m.matches()) {
            return this.transform(m.replaceAll(ruleExpression), ruleTransformType);
         }

         m = p.matcher(definition.getQualifiedName());
         if (m.matches()) {
            return this.transform(m.replaceAll(ruleExpression), ruleTransformType);
         }
      }

      return null;
   }

   private final String transform(String string, MatcherTransformType transform) {
      if (transform == null) {
         return string;
      } else {
         switch(transform) {
         case AS_IS:
            return string;
         case LOWER:
            return string.toLowerCase();
         case UPPER:
            return string.toUpperCase();
         case CAMEL:
            return StringUtils.toCamelCaseLC(string);
         case PASCAL:
            return StringUtils.toCamelCase(string);
         default:
            throw new UnsupportedOperationException("Transform Type not supported : " + transform);
         }
      }
   }

   private final List<MatchersSchemaType> schemas(Definition definition) {
      return definition instanceof SchemaDefinition ? this.matchers.getSchemas() : Collections.emptyList();
   }

   private final List<MatchersTableType> tables(Definition definition) {
      return definition instanceof TableDefinition ? this.matchers.getTables() : Collections.emptyList();
   }

   private final List<MatchersFieldType> fields(Definition definition) {
      return definition instanceof ColumnDefinition ? this.matchers.getFields() : Collections.emptyList();
   }

   private final List<MatchersRoutineType> routines(Definition definition) {
      return definition instanceof RoutineDefinition ? this.matchers.getRoutines() : Collections.emptyList();
   }

   private final List<MatchersSequenceType> sequences(Definition definition) {
      return definition instanceof SequenceDefinition ? this.matchers.getSequences() : Collections.emptyList();
   }

   private final List<String> split(String result) {
      List<String> list = new ArrayList();
      String[] var3 = result.split(",");
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String string = var3[var5];
         list.add(string.trim());
      }

      return list;
   }

   public String getJavaIdentifier(Definition definition) {
      Iterator var2 = this.schemas(definition).iterator();

      String result;
      do {
         if (!var2.hasNext()) {
            var2 = this.tables(definition).iterator();

            do {
               if (!var2.hasNext()) {
                  var2 = this.fields(definition).iterator();

                  do {
                     if (!var2.hasNext()) {
                        var2 = this.sequences(definition).iterator();

                        do {
                           if (!var2.hasNext()) {
                              return super.getJavaIdentifier(definition);
                           }

                           MatchersSequenceType sequences = (MatchersSequenceType)var2.next();
                           result = this.match(definition, sequences.getExpression(), sequences.getSequenceIdentifier());
                        } while(result == null);

                        return result;
                     }

                     MatchersFieldType fields = (MatchersFieldType)var2.next();
                     result = this.match(definition, fields.getExpression(), fields.getFieldIdentifier());
                  } while(result == null);

                  return result;
               }

               MatchersTableType tables = (MatchersTableType)var2.next();
               result = this.match(definition, tables.getExpression(), tables.getTableIdentifier());
            } while(result == null);

            return result;
         }

         MatchersSchemaType schemas = (MatchersSchemaType)var2.next();
         result = this.match(definition, schemas.getExpression(), schemas.getSchemaIdentifier());
      } while(result == null);

      return result;
   }

   public String getJavaSetterName(Definition definition, GeneratorStrategy.Mode mode) {
      Iterator var3 = this.fields(definition).iterator();

      String result;
      do {
         if (!var3.hasNext()) {
            return super.getJavaSetterName(definition, mode);
         }

         MatchersFieldType fields = (MatchersFieldType)var3.next();
         result = this.match(definition, fields.getExpression(), fields.getFieldSetter());
      } while(result == null);

      return result;
   }

   public String getJavaGetterName(Definition definition, GeneratorStrategy.Mode mode) {
      Iterator var3 = this.fields(definition).iterator();

      String result;
      do {
         if (!var3.hasNext()) {
            return super.getJavaGetterName(definition, mode);
         }

         MatchersFieldType fields = (MatchersFieldType)var3.next();
         result = this.match(definition, fields.getExpression(), fields.getFieldGetter());
      } while(result == null);

      return result;
   }

   public String getJavaMethodName(Definition definition, GeneratorStrategy.Mode mode) {
      Iterator var3 = this.routines(definition).iterator();

      String result;
      do {
         if (!var3.hasNext()) {
            return super.getJavaMethodName(definition, mode);
         }

         MatchersRoutineType routines = (MatchersRoutineType)var3.next();
         result = this.match(definition, routines.getExpression(), routines.getRoutineMethod());
      } while(result == null);

      return result;
   }

   public String getJavaClassExtends(Definition definition, GeneratorStrategy.Mode mode) {
      Iterator var3 = this.tables(definition).iterator();

      while(var3.hasNext()) {
         MatchersTableType tables = (MatchersTableType)var3.next();
         String result = null;
         switch(mode) {
         case POJO:
            result = this.match(definition, tables.getExpression(), tables.getPojoExtends());
         default:
            if (result != null) {
               return result;
            }
         }
      }

      return super.getJavaClassExtends(definition, mode);
   }

   public List<String> getJavaClassImplements(Definition definition, GeneratorStrategy.Mode mode) {
      Iterator var3 = this.schemas(definition).iterator();

      String result;
      do {
         if (!var3.hasNext()) {
            var3 = this.tables(definition).iterator();

            do {
               if (!var3.hasNext()) {
                  var3 = this.routines(definition).iterator();

                  do {
                     if (!var3.hasNext()) {
                        return super.getJavaClassImplements(definition, mode);
                     }

                     MatchersRoutineType routines = (MatchersRoutineType)var3.next();
                     result = this.match(definition, routines.getExpression(), routines.getRoutineImplements());
                  } while(result == null);

                  return this.split(result);
               }

               MatchersTableType tables = (MatchersTableType)var3.next();
               result = null;
               switch(mode) {
               case POJO:
                  result = this.match(definition, tables.getExpression(), tables.getPojoImplements());
                  break;
               case DEFAULT:
                  result = this.match(definition, tables.getExpression(), tables.getTableImplements());
                  break;
               case DAO:
                  result = this.match(definition, tables.getExpression(), tables.getDaoImplements());
                  break;
               case INTERFACE:
                  result = this.match(definition, tables.getExpression(), tables.getInterfaceImplements());
                  break;
               case RECORD:
                  result = this.match(definition, tables.getExpression(), tables.getRecordImplements());
               }
            } while(result == null);

            return this.split(result);
         }

         MatchersSchemaType schemas = (MatchersSchemaType)var3.next();
         result = this.match(definition, schemas.getExpression(), schemas.getSchemaImplements());
      } while(result == null);

      return this.split(result);
   }

   public String getJavaClassName(Definition definition, GeneratorStrategy.Mode mode) {
      Iterator var3 = this.schemas(definition).iterator();

      String result;
      do {
         if (!var3.hasNext()) {
            var3 = this.tables(definition).iterator();

            do {
               if (!var3.hasNext()) {
                  var3 = this.routines(definition).iterator();

                  do {
                     if (!var3.hasNext()) {
                        return super.getJavaClassName(definition, mode);
                     }

                     MatchersRoutineType routines = (MatchersRoutineType)var3.next();
                     result = this.match(definition, routines.getExpression(), routines.getRoutineClass());
                  } while(result == null);

                  return result;
               }

               MatchersTableType tables = (MatchersTableType)var3.next();
               result = null;
               switch(mode) {
               case POJO:
                  result = this.match(definition, tables.getExpression(), tables.getPojoClass());
                  break;
               case DEFAULT:
                  result = this.match(definition, tables.getExpression(), tables.getTableClass());
                  break;
               case DAO:
                  result = this.match(definition, tables.getExpression(), tables.getDaoClass());
                  break;
               case INTERFACE:
                  result = this.match(definition, tables.getExpression(), tables.getInterfaceClass());
                  break;
               case RECORD:
                  result = this.match(definition, tables.getExpression(), tables.getRecordClass());
               }
            } while(result == null);

            return result;
         }

         MatchersSchemaType schemas = (MatchersSchemaType)var3.next();
         result = this.match(definition, schemas.getExpression(), schemas.getSchemaClass());
      } while(result == null);

      return result;
   }

   public String getJavaPackageName(Definition definition, GeneratorStrategy.Mode mode) {
      return super.getJavaPackageName(definition, mode);
   }

   public String getJavaMemberName(Definition definition, GeneratorStrategy.Mode mode) {
      Iterator var3 = this.fields(definition).iterator();

      String result;
      do {
         if (!var3.hasNext()) {
            return super.getJavaMemberName(definition, mode);
         }

         MatchersFieldType fields = (MatchersFieldType)var3.next();
         result = this.match(definition, fields.getExpression(), fields.getFieldMember());
      } while(result == null);

      return result;
   }

   public String getOverloadSuffix(Definition definition, GeneratorStrategy.Mode mode, String overloadIndex) {
      return super.getOverloadSuffix(definition, mode, overloadIndex);
   }
}
