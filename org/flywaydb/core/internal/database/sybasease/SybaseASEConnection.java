package org.flywaydb.core.internal.database.sybasease;

import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Schema;

public class SybaseASEConnection extends Connection<SybaseASEDatabase> {
   private static final Log LOG = LogFactory.getLog(SybaseASEConnection.class);
   private static boolean schemaMessagePrinted;

   SybaseASEConnection(FlywayConfiguration configuration, SybaseASEDatabase database, java.sql.Connection connection, int nullType) {
      super(configuration, database, connection, nullType);
   }

   public Schema getSchema(String name) {
      return new SybaseASESchema(this.jdbcTemplate, (SybaseASEDatabase)this.database, "dbo");
   }

   protected String doGetCurrentSchemaName() throws SQLException {
      return "dbo";
   }

   public void doChangeCurrentSchemaTo(String schema) throws SQLException {
      if (!schemaMessagePrinted) {
         LOG.info("Sybase ASE does not support setting the schema for the current session. Default schema NOT changed to " + schema);
         schemaMessagePrinted = true;
      }

   }
}
