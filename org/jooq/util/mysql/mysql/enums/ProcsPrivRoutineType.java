package org.jooq.util.mysql.mysql.enums;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;

public enum ProcsPrivRoutineType implements EnumType {
   FUNCTION("FUNCTION"),
   PROCEDURE("PROCEDURE");

   private final String literal;

   private ProcsPrivRoutineType(String literal) {
      this.literal = literal;
   }

   public Catalog getCatalog() {
      return null;
   }

   public Schema getSchema() {
      return null;
   }

   public String getName() {
      return "procs_priv_Routine_type";
   }

   public String getLiteral() {
      return this.literal;
   }
}
