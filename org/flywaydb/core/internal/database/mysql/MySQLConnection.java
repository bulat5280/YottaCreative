package org.flywaydb.core.internal.database.mysql;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.Callable;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.StringUtils;

public class MySQLConnection extends Connection<MySQLDatabase> {
   private static final Log LOG = LogFactory.getLog(MySQLConnection.class);

   MySQLConnection(FlywayConfiguration configuration, MySQLDatabase database, java.sql.Connection connection, int nullType) {
      super(configuration, database, connection, nullType);
   }

   protected String doGetCurrentSchemaName() throws SQLException {
      return this.jdbcTemplate.getConnection().getCatalog();
   }

   public void doChangeCurrentSchemaTo(String schema) throws SQLException {
      if (!StringUtils.hasLength(schema)) {
         try {
            String newDb = ((MySQLDatabase)this.database).quote(new String[]{UUID.randomUUID().toString()});
            this.jdbcTemplate.execute("CREATE SCHEMA " + newDb);
            this.jdbcTemplate.execute("USE " + newDb);
            this.jdbcTemplate.execute("DROP SCHEMA " + newDb);
         } catch (Exception var3) {
            LOG.warn("Unable to restore connection to having no default schema: " + var3.getMessage());
         }
      } else {
         this.jdbcTemplate.getConnection().setCatalog(schema);
      }

   }

   public Schema getSchema(String name) {
      return new MySQLSchema(this.jdbcTemplate, (MySQLDatabase)this.database, name);
   }

   public <T> T lock(Table table, Callable<T> callable) {
      return (new MySQLNamedLockTemplate(this.jdbcTemplate, table.toString().hashCode())).execute(callable);
   }
}
