package org.flywaydb.core.internal.database.cockroachdb;

import java.sql.SQLException;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class CockroachDBTable extends Table {
   private static final Log LOG = LogFactory.getLog(CockroachDBTable.class);

   CockroachDBTable(JdbcTemplate jdbcTemplate, Database database, Schema schema, String name) {
      super(jdbcTemplate, database, schema, name);
   }

   protected void doDrop() throws SQLException {
      this.jdbcTemplate.execute("DROP TABLE " + this.database.quote(this.schema.getName(), this.name) + " CASCADE");
   }

   protected boolean doExists() throws SQLException {
      return this.jdbcTemplate.queryForBoolean("SELECT EXISTS (\n   SELECT 1\n   FROM   information_schema.tables \n   WHERE  table_schema = ?\n   AND    table_name = ?\n)", this.schema.getName(), this.name);
   }

   protected void doLock() {
      LOG.debug("Unable to lock " + this + " as CockroachDB does not support locking. No concurrent migration supported.");
   }
}
