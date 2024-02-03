package org.jooq.util;

public class KeepNamesGeneratorStrategy extends DefaultGeneratorStrategy {
   public String getJavaIdentifier(Definition definition) {
      return definition.getOutputName();
   }

   public String getJavaSetterName(Definition definition, GeneratorStrategy.Mode mode) {
      return definition.getOutputName();
   }

   public String getJavaGetterName(Definition definition, GeneratorStrategy.Mode mode) {
      return definition.getOutputName();
   }

   public String getJavaMethodName(Definition definition, GeneratorStrategy.Mode mode) {
      return definition.getOutputName();
   }

   public String getJavaClassName(Definition definition, GeneratorStrategy.Mode mode) {
      return definition.getOutputName();
   }

   public String getJavaMemberName(Definition definition, GeneratorStrategy.Mode mode) {
      return definition.getOutputName();
   }
}
