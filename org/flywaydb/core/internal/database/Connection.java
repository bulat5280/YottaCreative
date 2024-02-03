package org.flywaydb.core.internal.database;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.util.jdbc.JdbcUtils;
import org.flywaydb.core.internal.util.jdbc.TransactionTemplate;

public abstract class Connection<D extends Database> implements Closeable {
   protected final D database;
   protected final JdbcTemplate jdbcTemplate;
   private final java.sql.Connection jdbcConnection;
   protected final String originalSchema;

   protected Connection(FlywayConfiguration configuration, D database, java.sql.Connection connection, int nullType) {
      this.database = database;
      this.jdbcConnection = connection;
      this.jdbcTemplate = new JdbcTemplate(this.jdbcConnection, nullType);
      this.originalSchema = this.jdbcTemplate.getConnection() == null ? null : this.getCurrentSchemaName();
   }

   public String getCurrentSchemaName() {
      try {
         return this.doGetCurrentSchemaName();
      } catch (SQLException var2) {
         throw new FlywaySqlException("Unable to retrieve the current schema for the connection", var2);
      }
   }

   protected abstract String doGetCurrentSchemaName() throws SQLException;

   public Schema getOriginalSchema() {
      return this.originalSchema == null ? null : this.getSchema(this.originalSchema);
   }

   public abstract Schema getSchema(String var1);

   public void changeCurrentSchemaTo(Schema schema) {
      try {
         if (schema.exists()) {
            this.doChangeCurrentSchemaTo(schema.getName());
         }
      } catch (SQLException var3) {
         throw new FlywaySqlException("Error setting current schema to " + schema, var3);
      }
   }

   public void restoreCurrentSchema() {
      try {
         this.doChangeCurrentSchemaTo(this.originalSchema);
      } catch (SQLException var2) {
         throw new FlywaySqlException("Error restoring current schema to its original setting", var2);
      }
   }

   public abstract void doChangeCurrentSchemaTo(String var1) throws SQLException;

   public <T> T lock(final Table table, final Callable<T> callable) {
      return (new TransactionTemplate(this.jdbcTemplate.getConnection(), this.database.supportsDdlTransactions())).execute(new Callable<T>() {
         public T call() throws Exception {
            table.lock();
            return callable.call();
         }
      });
   }

   public JdbcTemplate getJdbcTemplate() {
      return this.jdbcTemplate;
   }

   public void close() {
      JdbcUtils.closeConnection(this.jdbcConnection);
   }

   public java.sql.Connection getJdbcConnection() {
      return this.jdbcConnection;
   }
}
