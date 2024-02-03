package org.jooq.util;

import org.jooq.SQLDialect;
import org.jooq.util.cubrid.CUBRIDDatabase;
import org.jooq.util.derby.DerbyDatabase;
import org.jooq.util.firebird.FirebirdDatabase;
import org.jooq.util.h2.H2Database;
import org.jooq.util.hsqldb.HSQLDBDatabase;
import org.jooq.util.jdbc.JDBCDatabase;
import org.jooq.util.mariadb.MariaDBDatabase;
import org.jooq.util.mysql.MySQLDatabase;
import org.jooq.util.postgres.PostgresDatabase;
import org.jooq.util.sqlite.SQLiteDatabase;

public class Databases {
   public static final Class<? extends Database> databaseClass(SQLDialect dialect) {
      Class<? extends Database> result = JDBCDatabase.class;
      switch(dialect) {
      case CUBRID:
         result = CUBRIDDatabase.class;
         break;
      case DERBY:
         result = DerbyDatabase.class;
         break;
      case FIREBIRD_2_5:
      case FIREBIRD_3_0:
      case FIREBIRD:
         result = FirebirdDatabase.class;
         break;
      case H2:
         result = H2Database.class;
         break;
      case HSQLDB:
         result = HSQLDBDatabase.class;
         break;
      case MARIADB:
         result = MariaDBDatabase.class;
         break;
      case MYSQL:
         result = MySQLDatabase.class;
         break;
      case POSTGRES_9_3:
      case POSTGRES_9_4:
      case POSTGRES_9_5:
      case POSTGRES:
         result = PostgresDatabase.class;
         break;
      case SQLITE:
         result = SQLiteDatabase.class;
         break;
      case DEFAULT:
      case SQL99:
         result = JDBCDatabase.class;
      }

      return result;
   }

   public static final Database database(SQLDialect dialect) {
      try {
         return (Database)databaseClass(dialect).newInstance();
      } catch (Exception var2) {
         throw new IllegalArgumentException("Cannot create an Database instance for " + dialect, var2);
      }
   }
}
