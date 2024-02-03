package org.jooq.util.mysql.mysql.enums;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;

public enum ProcType implements EnumType {
   FUNCTION("FUNCTION"),
   PROCEDURE("PROCEDURE");

   private final String literal;

   private ProcType(String literal) {
      this.literal = literal;
   }

   public Catalog getCatalog() {
      return null;
   }

   public Schema getSchema() {
      return null;
   }

   public String getName() {
      return "proc_type";
   }

   public String getLiteral() {
      return this.literal;
   }
}
