package org.jooq.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultEnumDefinition extends AbstractDefinition implements EnumDefinition {
   private final List<String> literals;
   private final boolean isSynthetic;

   public DefaultEnumDefinition(SchemaDefinition schema, String name, String comment) {
      this(schema, name, comment, false);
   }

   public DefaultEnumDefinition(SchemaDefinition schema, String name, String comment, boolean isSynthetic) {
      super(schema.getDatabase(), schema, name, comment);
      this.literals = new ArrayList();
      this.isSynthetic = isSynthetic;
   }

   public List<Definition> getDefinitionPath() {
      List<Definition> result = new ArrayList();
      result.addAll(this.getSchema().getDefinitionPath());
      result.add(this);
      return result;
   }

   public void addLiteral(String literal) {
      this.literals.add(literal);
   }

   public void addLiterals(String... literal) {
      this.literals.addAll(Arrays.asList(literal));
   }

   public List<String> getLiterals() {
      return this.literals;
   }

   public boolean isSynthetic() {
      return this.isSynthetic;
   }
}
