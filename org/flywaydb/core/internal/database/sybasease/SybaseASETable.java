package org.flywaydb.core.internal.database.sybasease;

import java.sql.SQLException;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class SybaseASETable extends Table {
   SybaseASETable(JdbcTemplate jdbcTemplate, Database database, Schema schema, String name) {
      super(jdbcTemplate, database, schema, name);
   }

   protected boolean doExists() throws SQLException {
      return this.jdbcTemplate.queryForString("SELECT object_id('" + this.name + "')") != null;
   }

   protected void doLock() throws SQLException {
      this.jdbcTemplate.execute("LOCK TABLE " + this + " IN EXCLUSIVE MODE");
   }

   protected void doDrop() throws SQLException {
      this.jdbcTemplate.execute("DROP TABLE " + this.getName());
   }

   public String toString() {
      return this.name;
   }
}
