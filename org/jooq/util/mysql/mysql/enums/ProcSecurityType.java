package org.jooq.util.mysql.mysql.enums;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;

public enum ProcSecurityType implements EnumType {
   INVOKER("INVOKER"),
   DEFINER("DEFINER");

   private final String literal;

   private ProcSecurityType(String literal) {
      this.literal = literal;
   }

   public Catalog getCatalog() {
      return null;
   }

   public Schema getSchema() {
      return null;
   }

   public String getName() {
      return "proc_security_type";
   }

   public String getLiteral() {
      return this.literal;
   }
}
