package org.jooq.util;

class ConstantSchemaVersionProvider implements SchemaVersionProvider {
   private String constant;

   ConstantSchemaVersionProvider(String constant) {
      this.constant = constant;
   }

   public String version(SchemaDefinition schema) {
      return this.constant;
   }
}
