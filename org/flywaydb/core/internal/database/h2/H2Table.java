package org.flywaydb.core.internal.database.h2;

import java.sql.SQLException;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class H2Table extends Table {
   public H2Table(JdbcTemplate jdbcTemplate, Database database, Schema schema, String name) {
      super(jdbcTemplate, database, schema, name);
   }

   protected void doDrop() throws SQLException {
      this.jdbcTemplate.execute("DROP TABLE " + this.database.quote(this.schema.getName(), this.name) + " CASCADE");
   }

   protected boolean doExists() throws SQLException {
      return this.exists((Schema)null, this.schema, this.name, new String[0]);
   }

   protected void doLock() throws SQLException {
      this.jdbcTemplate.execute("select * from " + this + " for update");
   }
}
