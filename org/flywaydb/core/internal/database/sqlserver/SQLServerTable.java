package org.flywaydb.core.internal.database.sqlserver;

import java.sql.SQLException;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class SQLServerTable extends Table {
   private final String databaseName;

   SQLServerTable(JdbcTemplate jdbcTemplate, Database database, String databaseName, Schema schema, String name) {
      super(jdbcTemplate, database, schema, name);
      this.databaseName = databaseName;
   }

   protected void doDrop() throws SQLException {
      this.jdbcTemplate.execute("DROP TABLE " + this);
   }

   protected boolean doExists() throws SQLException {
      return this.jdbcTemplate.queryForBoolean("SELECT CAST(CASE WHEN EXISTS(  SELECT 1 FROM [" + this.databaseName + "].INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA=? AND TABLE_NAME=?) THEN 1 ELSE 0 END AS BIT)", this.schema.getName(), this.name);
   }

   protected void doLock() throws SQLException {
      this.jdbcTemplate.execute("select * from " + this + " WITH (TABLOCKX)");
   }

   public String toString() {
      return this.database.quote(this.databaseName, this.schema.getName(), this.name);
   }
}
