package org.flywaydb.core.internal.database.sqlite;

import java.sql.SQLException;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class SQLiteTable extends Table {
   private static final Log LOG = LogFactory.getLog(SQLiteTable.class);
   static final String SQLITE_SEQUENCE = "sqlite_sequence";
   private final boolean undroppable;

   public SQLiteTable(JdbcTemplate jdbcTemplate, Database database, Schema schema, String name) {
      super(jdbcTemplate, database, schema, name);
      this.undroppable = "sqlite_sequence".equals(name);
   }

   protected void doDrop() throws SQLException {
      if (this.undroppable) {
         LOG.debug("SQLite system table " + this + " cannot be dropped. Ignoring.");
      } else {
         this.jdbcTemplate.execute("DROP TABLE " + this.database.quote(this.schema.getName(), this.name));
      }

   }

   protected boolean doExists() throws SQLException {
      return this.jdbcTemplate.queryForInt("SELECT count(tbl_name) FROM " + this.database.quote(this.schema.getName()) + ".sqlite_master WHERE type='table' AND tbl_name='" + this.name + "'") > 0;
   }

   protected void doLock() throws SQLException {
      LOG.debug("Unable to lock " + this + " as SQLite does not support locking. No concurrent migration supported.");
   }
}
