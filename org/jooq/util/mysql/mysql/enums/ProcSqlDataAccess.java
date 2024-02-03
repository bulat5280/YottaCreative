package org.jooq.util.mysql.mysql.enums;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;

public enum ProcSqlDataAccess implements EnumType {
   CONTAINS_SQL("CONTAINS_SQL"),
   NO_SQL("NO_SQL"),
   READS_SQL_DATA("READS_SQL_DATA"),
   MODIFIES_SQL_DATA("MODIFIES_SQL_DATA");

   private final String literal;

   private ProcSqlDataAccess(String literal) {
      this.literal = literal;
   }

   public Catalog getCatalog() {
      return null;
   }

   public Schema getSchema() {
      return null;
   }

   public String getName() {
      return "proc_sql_data_access";
   }

   public String getLiteral() {
      return this.literal;
   }
}
