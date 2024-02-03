package org.jooq.util.mysql.mysql.enums;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;

public enum ProcIsDeterministic implements EnumType {
   YES("YES"),
   NO("NO");

   private final String literal;

   private ProcIsDeterministic(String literal) {
      this.literal = literal;
   }

   public Catalog getCatalog() {
      return null;
   }

   public Schema getSchema() {
      return null;
   }

   public String getName() {
      return "proc_is_deterministic";
   }

   public String getLiteral() {
      return this.literal;
   }
}
