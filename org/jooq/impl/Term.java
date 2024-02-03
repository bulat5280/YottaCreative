package org.jooq.impl;

import org.jooq.SQLDialect;

enum Term {
   ARRAY_AGG {
      public String translate(SQLDialect dialect) {
         return "array_agg";
      }
   },
   ATAN2 {
      public String translate(SQLDialect dialect) {
         return "atan2";
      }
   },
   BIT_LENGTH {
      public String translate(SQLDialect dialect) {
         switch(dialect.family()) {
         case DERBY:
         case SQLITE:
            return "8 * length";
         default:
            return "bit_length";
         }
      }
   },
   CHAR_LENGTH {
      public String translate(SQLDialect dialect) {
         switch(dialect.family()) {
         case DERBY:
         case SQLITE:
            return "length";
         default:
            return "char_length";
         }
      }
   },
   LIST_AGG {
      public String translate(SQLDialect dialect) {
         switch(dialect.family()) {
         case SQLITE:
         case CUBRID:
         case H2:
         case HSQLDB:
         case MARIADB:
         case MYSQL:
            return "group_concat";
         case POSTGRES:
            return "string_agg";
         default:
            return "listagg";
         }
      }
   },
   MEDIAN {
      public String translate(SQLDialect dialect) {
         return "median";
      }
   },
   OCTET_LENGTH {
      public String translate(SQLDialect dialect) {
         switch(dialect.family()) {
         case DERBY:
         case SQLITE:
            return "length";
         default:
            return "octet_length";
         }
      }
   },
   ROW_NUMBER {
      public String translate(SQLDialect dialect) {
         switch(dialect.family()) {
         case HSQLDB:
            return "rownum";
         default:
            return "row_number";
         }
      }
   },
   STDDEV_POP {
      public String translate(SQLDialect dialect) {
         return "stddev_pop";
      }
   },
   STDDEV_SAMP {
      public String translate(SQLDialect dialect) {
         return "stddev_samp";
      }
   },
   VAR_POP {
      public String translate(SQLDialect dialect) {
         return "var_pop";
      }
   },
   VAR_SAMP {
      public String translate(SQLDialect dialect) {
         return "var_samp";
      }
   };

   private Term() {
   }

   public String toString() {
      return super.toString();
   }

   abstract String translate(SQLDialect var1);

   // $FF: synthetic method
   Term(Object x2) {
      this();
   }
}
