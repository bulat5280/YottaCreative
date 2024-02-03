package org.jooq.util.mariadb;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.util.mysql.MySQLDatabase;

public class MariaDBDatabase extends MySQLDatabase {
   protected DSLContext create0() {
      return DSL.using(this.getConnection(), SQLDialect.MARIADB);
   }
}
