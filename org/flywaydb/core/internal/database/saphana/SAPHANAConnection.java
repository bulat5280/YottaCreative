package org.flywaydb.core.internal.database.saphana;

import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Schema;

public class SAPHANAConnection extends Connection<SAPHANADatabase> {
   SAPHANAConnection(FlywayConfiguration configuration, SAPHANADatabase database, java.sql.Connection connection, int nullType) {
      super(configuration, database, connection, nullType);
   }

   protected String doGetCurrentSchemaName() throws SQLException {
      return this.jdbcTemplate.queryForString("SELECT CURRENT_SCHEMA FROM DUMMY");
   }

   public void doChangeCurrentSchemaTo(String schema) throws SQLException {
      this.jdbcTemplate.execute("SET SCHEMA " + ((SAPHANADatabase)this.database).doQuote(schema));
   }

   public Schema getSchema(String name) {
      return new SAPHANASchema(this.jdbcTemplate, (SAPHANADatabase)this.database, name);
   }
}
