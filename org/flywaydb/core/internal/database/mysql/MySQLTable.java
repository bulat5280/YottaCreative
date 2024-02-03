package org.flywaydb.core.internal.database.mysql;

import java.sql.SQLException;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class MySQLTable extends Table {
   MySQLTable(JdbcTemplate jdbcTemplate, Database database, Schema schema, String name) {
      super(jdbcTemplate, database, schema, name);
   }

   protected void doDrop() throws SQLException {
      this.jdbcTemplate.execute("DROP TABLE " + this.database.quote(this.schema.getName(), this.name));
   }

   protected boolean doExists() throws SQLException {
      return this.exists(this.schema, (Schema)null, this.name, new String[0]);
   }

   protected void doLock() throws SQLException {
      this.jdbcTemplate.execute("SELECT * FROM " + this + " FOR UPDATE");
   }
}
