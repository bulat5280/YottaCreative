package org.flywaydb.core.internal.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.util.jdbc.JdbcUtils;

public abstract class Table extends SchemaObject {
   private static final Log LOG = LogFactory.getLog(Table.class);

   public Table(JdbcTemplate jdbcTemplate, Database database, Schema schema, String name) {
      super(jdbcTemplate, database, schema, name);
   }

   public boolean exists() {
      try {
         return this.doExists();
      } catch (SQLException var2) {
         throw new FlywaySqlException("Unable to check whether table " + this + " exists", var2);
      }
   }

   protected abstract boolean doExists() throws SQLException;

   protected boolean exists(Schema catalog, Schema schema, String table, String... tableTypes) throws SQLException {
      String[] types = tableTypes;
      if (tableTypes.length == 0) {
         types = null;
      }

      ResultSet resultSet = null;

      boolean found;
      try {
         resultSet = this.database.jdbcMetaData.getTables(catalog == null ? null : catalog.getName(), schema == null ? null : schema.getName(), table, types);
         found = resultSet.next();
      } finally {
         JdbcUtils.closeResultSet(resultSet);
      }

      return found;
   }

   public void lock() {
      try {
         LOG.debug("Locking table " + this + "...");
         this.doLock();
         LOG.debug("Lock acquired for table " + this);
      } catch (SQLException var2) {
         throw new FlywaySqlException("Unable to lock table " + this, var2);
      }
   }

   protected abstract void doLock() throws SQLException;
}
