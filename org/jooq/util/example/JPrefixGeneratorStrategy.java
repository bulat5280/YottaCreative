package org.jooq.util.example;

import org.jooq.util.DefaultGeneratorStrategy;
import org.jooq.util.Definition;
import org.jooq.util.GeneratorStrategy;

public class JPrefixGeneratorStrategy extends DefaultGeneratorStrategy {
   public String getJavaClassName(Definition definition, GeneratorStrategy.Mode mode) {
      return 'J' + super.getJavaClassName(definition, mode);
   }
}
