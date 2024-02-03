package org.jooq.impl;

import java.util.EnumMap;
import org.jooq.SQLDialect;

final class Identifiers {
   static final EnumMap<SQLDialect, String[][]> QUOTES = new EnumMap(SQLDialect.class);
   static final int QUOTE_START_DELIMITER = 0;
   static final int QUOTE_END_DELIMITER = 1;
   static final int QUOTE_END_DELIMITER_ESCAPED = 2;

   static {
      SQLDialect[] var0 = SQLDialect.families();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         SQLDialect family = var0[var2];
         switch(family) {
         case MARIADB:
         case MYSQL:
            QUOTES.put(family, new String[][]{{"`", "\""}, {"`", "\""}, {"``", "\"\""}});
            break;
         case CUBRID:
         case DERBY:
         case FIREBIRD:
         case H2:
         case HSQLDB:
         case POSTGRES:
         case SQLITE:
         default:
            QUOTES.put(family, new String[][]{{"\""}, {"\""}, {"\"\""}});
         }
      }

   }
}
