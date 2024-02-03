package org.jooq;

import java.util.EnumSet;
import java.util.Set;

public enum SQLDialect {
   /** @deprecated */
   @Deprecated
   SQL99("", false),
   DEFAULT("", false),
   CUBRID("CUBRID", false),
   DERBY("Derby", false),
   FIREBIRD("Firebird", false),
   FIREBIRD_2_5("Firebird", false, FIREBIRD, (SQLDialect)null),
   FIREBIRD_3_0("Firebird", false, FIREBIRD, FIREBIRD_2_5),
   H2("H2", false),
   HSQLDB("HSQLDB", false),
   MARIADB("MariaDB", false),
   MYSQL("MySQL", false),
   POSTGRES("Postgres", false),
   POSTGRES_9_3("Postgres", false, POSTGRES, (SQLDialect)null),
   POSTGRES_9_4("Postgres", false, POSTGRES, POSTGRES_9_3),
   POSTGRES_9_5("Postgres", false, POSTGRES, POSTGRES_9_4),
   SQLITE("SQLite", false);

   private static final SQLDialect[] FAMILIES;
   private final String name;
   private final boolean commercial;
   private final SQLDialect family;
   private SQLDialect predecessor;
   private final SQLDialect.ThirdParty thirdParty;

   private SQLDialect(String name, boolean commercial) {
      this(name, commercial, (SQLDialect)null, (SQLDialect)null);
   }

   private SQLDialect(String name, boolean commercial, SQLDialect family) {
      this(name, commercial, family, (SQLDialect)null);
   }

   private SQLDialect(String name, boolean commercial, SQLDialect family, SQLDialect predecessor) {
      this.name = name;
      this.commercial = commercial;
      this.family = family == null ? this : family;
      this.predecessor = predecessor == null ? this : predecessor;
      if (family != null) {
         family.predecessor = this;
      }

      this.thirdParty = new SQLDialect.ThirdParty();
   }

   public final boolean commercial() {
      return this.commercial;
   }

   public final SQLDialect family() {
      return this.family;
   }

   public final boolean isFamily() {
      return this == this.family;
   }

   public final SQLDialect predecessor() {
      return this.predecessor;
   }

   public final boolean precedes(SQLDialect other) {
      if (this.family != other.family) {
         return false;
      } else {
         for(SQLDialect candidate = other; candidate != null; candidate = candidate.predecessor()) {
            if (this == candidate) {
               return true;
            }

            if (candidate == candidate.predecessor()) {
               return false;
            }
         }

         return false;
      }
   }

   public final boolean supports(SQLDialect other) {
      if (this.family != other.family) {
         return false;
      } else {
         return !this.isFamily() && !other.isFamily() ? other.precedes(this) : true;
      }
   }

   public final String getName() {
      return this.name;
   }

   public final String getNameLC() {
      return this.name == null ? null : this.name.toLowerCase();
   }

   public final String getNameUC() {
      return this.name == null ? null : this.name.toUpperCase();
   }

   public static final SQLDialect[] families() {
      return (SQLDialect[])FAMILIES.clone();
   }

   public final SQLDialect.ThirdParty thirdParty() {
      return this.thirdParty;
   }

   static {
      Set<SQLDialect> set = EnumSet.noneOf(SQLDialect.class);
      SQLDialect[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         SQLDialect dialect = var1[var3];
         set.add(dialect.family());
      }

      FAMILIES = (SQLDialect[])set.toArray(new SQLDialect[0]);
   }

   public final class ThirdParty {
      public final String springDbName() {
         switch(SQLDialect.this.family) {
         case DERBY:
            return "Derby";
         case H2:
            return "H2";
         case HSQLDB:
            return "HSQL";
         case MARIADB:
         case MYSQL:
            return "MySQL";
         case POSTGRES:
            return "PostgreSQL";
         default:
            return null;
         }
      }

      public final String hibernateDialect() {
         switch(SQLDialect.this) {
         case DERBY:
            return "org.hibernate.dialect.DerbyTenSevenDialect";
         case H2:
            return "org.hibernate.dialect.H2Dialect";
         case HSQLDB:
            return "org.hibernate.dialect.HSQLDialect";
         case MARIADB:
         case MYSQL:
            return "org.hibernate.dialect.MySQL5Dialect";
         case POSTGRES:
         case POSTGRES_9_4:
         case POSTGRES_9_5:
            return "org.hibernate.dialect.PostgreSQL94Dialect";
         case CUBRID:
            return "org.hibernate.dialect.CUBRIDDialect";
         case FIREBIRD:
            return "org.hibernate.dialect.FirebirdDialect";
         case POSTGRES_9_3:
            return "org.hibernate.dialect.PostgreSQL92Dialect";
         case SQLITE:
            return null;
         default:
            return null;
         }
      }
   }
}
