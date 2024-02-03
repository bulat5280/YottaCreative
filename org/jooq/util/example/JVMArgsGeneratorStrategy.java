package org.jooq.util.example;

import org.jooq.util.DefaultGeneratorStrategy;
import org.jooq.util.Definition;
import org.jooq.util.GeneratorStrategy;

public class JVMArgsGeneratorStrategy extends DefaultGeneratorStrategy {
   public String getJavaIdentifier(Definition definition) {
      return System.getProperty("org.jooq.util.example.java-identifier-prefix", "") + super.getJavaIdentifier(definition) + System.getProperty("org.jooq.util.example.java-identifier-suffix", "");
   }

   public String getJavaSetterName(Definition definition, GeneratorStrategy.Mode mode) {
      return System.getProperty("org.jooq.util.example.java-setter-name-prefix", "") + super.getJavaSetterName(definition, mode) + System.getProperty("org.jooq.util.example.java-setter-name-suffix", "");
   }

   public String getJavaGetterName(Definition definition, GeneratorStrategy.Mode mode) {
      return System.getProperty("org.jooq.util.example.java-getter-name-prefix", "") + super.getJavaGetterName(definition, mode) + System.getProperty("org.jooq.util.example.java-getter-name-suffix", "");
   }

   public String getJavaMethodName(Definition definition, GeneratorStrategy.Mode mode) {
      return System.getProperty("org.jooq.util.example.java-method-name-prefix", "") + super.getJavaMethodName(definition, mode) + System.getProperty("org.jooq.util.example.java-method-name-suffix", "");
   }

   public String getJavaClassName(Definition definition, GeneratorStrategy.Mode mode) {
      return System.getProperty("org.jooq.util.example.java-class-name-prefix", "") + super.getJavaClassName(definition, mode) + System.getProperty("org.jooq.util.example.java-class-name-suffix", "");
   }

   public String getJavaPackageName(Definition definition, GeneratorStrategy.Mode mode) {
      return System.getProperty("org.jooq.util.example.java-package-name-prefix", "") + super.getJavaPackageName(definition, mode) + System.getProperty("org.jooq.util.example.java-package-name-suffix", "");
   }

   public String getJavaMemberName(Definition definition, GeneratorStrategy.Mode mode) {
      return System.getProperty("org.jooq.util.example.java-member-name-prefix", "") + super.getJavaMemberName(definition, mode) + System.getProperty("org.jooq.util.example.java-member-name-suffix", "");
   }
}
