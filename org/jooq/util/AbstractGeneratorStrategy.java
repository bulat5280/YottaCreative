package org.jooq.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractGeneratorStrategy implements GeneratorStrategy {
   public final String getFileName(Definition definition) {
      return this.getFileName(definition, GeneratorStrategy.Mode.DEFAULT);
   }

   public final String getFileName(Definition definition, GeneratorStrategy.Mode mode) {
      return this.getJavaClassName(definition, mode) + ".java";
   }

   public final File getFileRoot() {
      String dir = this.getTargetDirectory();
      String pkg = this.getTargetPackage().replaceAll("\\.", "/");
      return new File(dir + "/" + pkg);
   }

   public final File getFile(Definition definition) {
      return this.getFile(definition, GeneratorStrategy.Mode.DEFAULT);
   }

   public final File getFile(Definition definition, GeneratorStrategy.Mode mode) {
      String dir = this.getTargetDirectory();
      String pkg = this.getJavaPackageName(definition, mode).replaceAll("\\.", "/");
      return new File(dir + "/" + pkg, this.getFileName(definition, mode));
   }

   public final File getFile(String fileName) {
      String dir = this.getTargetDirectory();
      String pkg = this.getTargetPackage().replaceAll("\\.", "/");
      return new File(dir + "/" + pkg, fileName);
   }

   public final String getFileHeader(Definition definition) {
      return this.getFileHeader(definition, GeneratorStrategy.Mode.DEFAULT);
   }

   public final String getFullJavaIdentifier(Definition definition) {
      StringBuilder sb = new StringBuilder();
      TypedElementDefinition e;
      if (definition instanceof ColumnDefinition) {
         e = (TypedElementDefinition)definition;
         if (this.getInstanceFields()) {
            sb.append(this.getFullJavaIdentifier(e.getContainer()));
         } else {
            sb.append(this.getFullJavaClassName(e.getContainer()));
         }
      } else if (definition instanceof SequenceDefinition) {
         sb.append(this.getJavaPackageName(definition.getSchema()));
         sb.append(".Sequences");
      } else if (definition instanceof TypedElementDefinition) {
         e = (TypedElementDefinition)definition;
         sb.append(this.getFullJavaClassName(e.getContainer()));
      } else if (definition instanceof IdentityDefinition) {
         sb.append(this.getJavaPackageName(definition.getSchema()));
         sb.append(".Keys");
      } else if (definition instanceof UniqueKeyDefinition) {
         sb.append(this.getJavaPackageName(definition.getSchema()));
         sb.append(".Keys");
      } else if (definition instanceof ForeignKeyDefinition) {
         sb.append(this.getJavaPackageName(definition.getSchema()));
         sb.append(".Keys");
      } else {
         sb.append(this.getFullJavaClassName(definition));
      }

      sb.append(".");
      sb.append(this.getJavaIdentifier(definition));
      return sb.toString();
   }

   public final String getJavaSetterName(Definition definition) {
      return this.getJavaSetterName(definition, GeneratorStrategy.Mode.DEFAULT);
   }

   public final String getJavaGetterName(Definition definition) {
      return this.getJavaGetterName(definition, GeneratorStrategy.Mode.DEFAULT);
   }

   public final String getJavaMethodName(Definition definition) {
      return this.getJavaMethodName(definition, GeneratorStrategy.Mode.DEFAULT);
   }

   public final String getJavaClassExtends(Definition definition) {
      return this.getJavaClassExtends(definition, GeneratorStrategy.Mode.DEFAULT);
   }

   public final List<String> getJavaClassImplements(Definition definition) {
      return this.getJavaClassImplements(definition, GeneratorStrategy.Mode.DEFAULT);
   }

   public final String getJavaClassName(Definition definition) {
      return this.getJavaClassName(definition, GeneratorStrategy.Mode.DEFAULT);
   }

   public final String getJavaPackageName(Definition definition) {
      return this.getJavaPackageName(definition, GeneratorStrategy.Mode.DEFAULT);
   }

   public final String getJavaMemberName(Definition definition) {
      return this.getJavaMemberName(definition, GeneratorStrategy.Mode.DEFAULT);
   }

   public final String getFullJavaClassName(Definition definition) {
      return this.getFullJavaClassName(definition, GeneratorStrategy.Mode.DEFAULT);
   }

   public final String getFullJavaClassName(Definition definition, GeneratorStrategy.Mode mode) {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getJavaPackageName(definition, mode));
      sb.append(".");
      sb.append(this.getJavaClassName(definition, mode));
      return sb.toString();
   }

   public final List<String> getJavaIdentifiers(Collection<? extends Definition> definitions) {
      List<String> result = new ArrayList();
      Iterator var3 = nonNull(definitions).iterator();

      while(var3.hasNext()) {
         Definition definition = (Definition)var3.next();
         result.add(this.getJavaIdentifier(definition));
      }

      return result;
   }

   public final List<String> getJavaIdentifiers(Definition... definitions) {
      return this.getJavaIdentifiers((Collection)Arrays.asList(definitions));
   }

   public final List<String> getFullJavaIdentifiers(Collection<? extends Definition> definitions) {
      List<String> result = new ArrayList();
      Iterator var3 = nonNull(definitions).iterator();

      while(var3.hasNext()) {
         Definition definition = (Definition)var3.next();
         result.add(this.getFullJavaIdentifier(definition));
      }

      return result;
   }

   public final List<String> getFullJavaIdentifiers(Definition... definitions) {
      return this.getFullJavaIdentifiers((Collection)Arrays.asList(definitions));
   }

   private static final <T> List<T> nonNull(Collection<? extends T> collection) {
      List<T> result = new ArrayList();
      Iterator var2 = collection.iterator();

      while(var2.hasNext()) {
         T t = var2.next();
         if (t != null) {
            result.add(t);
         }
      }

      return result;
   }

   final String getFixedJavaIdentifier(Definition definition) {
      if (definition instanceof IdentityDefinition) {
         return "IDENTITY_" + this.getJavaIdentifier(((IdentityDefinition)definition).getColumn().getContainer());
      } else if (definition instanceof CatalogDefinition && ((CatalogDefinition)definition).isDefaultCatalog()) {
         return "DEFAULT_CATALOG";
      } else {
         return definition instanceof SchemaDefinition && ((SchemaDefinition)definition).isDefaultSchema() ? "DEFAULT_SCHEMA" : null;
      }
   }

   final String getFixedJavaClassName(Definition definition) {
      if (definition instanceof CatalogDefinition && ((CatalogDefinition)definition).isDefaultCatalog()) {
         return "DefaultCatalog";
      } else {
         return definition instanceof SchemaDefinition && ((SchemaDefinition)definition).isDefaultSchema() ? "DefaultSchema" : null;
      }
   }
}
