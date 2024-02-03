package org.flywaydb.core.internal.database.postgresql;

import java.sql.SQLException;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class PostgreSQLTable extends Table {
   PostgreSQLTable(JdbcTemplate jdbcTemplate, Database database, Schema schema, String name) {
      super(jdbcTemplate, database, schema, name);
   }

   protected void doDrop() throws SQLException {
      this.jdbcTemplate.execute("DROP TABLE " + this.database.quote(this.schema.getName(), this.name) + " CASCADE");
   }

   protected boolean doExists() throws SQLException {
      return this.jdbcTemplate.queryForBoolean("SELECT EXISTS (\n   SELECT 1\n   FROM   pg_catalog.pg_class c\n   JOIN   pg_catalog.pg_namespace n ON n.oid = c.relnamespace\n   WHERE  n.nspname = ?\n   AND    c.relname = ?\n   AND    c.relkind = 'r'    -- only tables\n   );", this.schema.getName(), this.name);
   }

   protected void doLock() throws SQLException {
      this.jdbcTemplate.execute("SELECT * FROM " + this + " FOR UPDATE");
   }
}
