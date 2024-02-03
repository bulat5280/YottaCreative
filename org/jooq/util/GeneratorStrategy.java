package org.jooq.util;

import java.io.File;
import java.util.Collection;
import java.util.List;

public interface GeneratorStrategy {
   String getTargetDirectory();

   void setTargetDirectory(String var1);

   String getTargetPackage();

   void setTargetPackage(String var1);

   void setInstanceFields(boolean var1);

   boolean getInstanceFields();

   String getJavaIdentifier(Definition var1);

   List<String> getJavaIdentifiers(Collection<? extends Definition> var1);

   List<String> getJavaIdentifiers(Definition... var1);

   String getFullJavaIdentifier(Definition var1);

   List<String> getFullJavaIdentifiers(Collection<? extends Definition> var1);

   List<String> getFullJavaIdentifiers(Definition... var1);

   String getJavaSetterName(Definition var1);

   String getJavaSetterName(Definition var1, GeneratorStrategy.Mode var2);

   String getJavaGetterName(Definition var1);

   String getJavaGetterName(Definition var1, GeneratorStrategy.Mode var2);

   String getJavaMethodName(Definition var1);

   String getJavaMethodName(Definition var1, GeneratorStrategy.Mode var2);

   String getJavaClassExtends(Definition var1);

   String getJavaClassExtends(Definition var1, GeneratorStrategy.Mode var2);

   List<String> getJavaClassImplements(Definition var1);

   List<String> getJavaClassImplements(Definition var1, GeneratorStrategy.Mode var2);

   String getJavaClassName(Definition var1);

   String getJavaClassName(Definition var1, GeneratorStrategy.Mode var2);

   String getJavaPackageName(Definition var1);

   String getJavaPackageName(Definition var1, GeneratorStrategy.Mode var2);

   String getJavaMemberName(Definition var1);

   String getJavaMemberName(Definition var1, GeneratorStrategy.Mode var2);

   String getFullJavaClassName(Definition var1);

   String getFullJavaClassName(Definition var1, GeneratorStrategy.Mode var2);

   String getFileName(Definition var1);

   String getFileName(Definition var1, GeneratorStrategy.Mode var2);

   File getFileRoot();

   File getFile(Definition var1);

   File getFile(Definition var1, GeneratorStrategy.Mode var2);

   File getFile(String var1);

   String getFileHeader(Definition var1);

   String getFileHeader(Definition var1, GeneratorStrategy.Mode var2);

   String getOverloadSuffix(Definition var1, GeneratorStrategy.Mode var2, String var3);

   public static enum Mode {
      DEFAULT,
      RECORD,
      POJO,
      INTERFACE,
      DAO,
      ENUM,
      DOMAIN;
   }
}
