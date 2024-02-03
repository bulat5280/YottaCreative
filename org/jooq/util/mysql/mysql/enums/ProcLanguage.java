package org.jooq.util.mysql.mysql.enums;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;

public enum ProcLanguage implements EnumType {
   SQL("SQL");

   private final String literal;

   private ProcLanguage(String literal) {
      this.literal = literal;
   }

   public Catalog getCatalog() {
      return null;
   }

   public Schema getSchema() {
      return null;
   }

   public String getName() {
      return "proc_language";
   }

   public String getLiteral() {
      return this.literal;
   }
}
