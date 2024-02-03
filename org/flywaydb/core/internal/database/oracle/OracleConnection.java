package org.flywaydb.core.internal.database.oracle;

import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Schema;

public class OracleConnection extends Connection<OracleDatabase> {
   OracleConnection(FlywayConfiguration configuration, OracleDatabase database, java.sql.Connection connection, int nullType) {
      super(configuration, database, connection, nullType);
   }

   protected String doGetCurrentSchemaName() throws SQLException {
      return this.jdbcTemplate.queryForString("SELECT SYS_CONTEXT('USERENV', 'CURRENT_SCHEMA') FROM DUAL");
   }

   public void doChangeCurrentSchemaTo(String schema) throws SQLException {
      this.jdbcTemplate.execute("ALTER SESSION SET CURRENT_SCHEMA=" + ((OracleDatabase)this.database).quote(new String[]{schema}));
   }

   public Schema getSchema(String name) {
      return new OracleSchema(this.jdbcTemplate, (OracleDatabase)this.database, name);
   }
}
