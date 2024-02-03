package org.flywaydb.core.internal.database.derby;

import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Schema;

public class DerbyConnection extends Connection<DerbyDatabase> {
   DerbyConnection(FlywayConfiguration configuration, DerbyDatabase database, java.sql.Connection connection, int nullType) {
      super(configuration, database, connection, nullType);
   }

   protected String doGetCurrentSchemaName() throws SQLException {
      return this.jdbcTemplate.queryForString("SELECT CURRENT SCHEMA FROM SYSIBM.SYSDUMMY1");
   }

   public void doChangeCurrentSchemaTo(String schema) throws SQLException {
      this.jdbcTemplate.execute("SET SCHEMA " + ((DerbyDatabase)this.database).quote(new String[]{schema}));
   }

   public Schema getSchema(String name) {
      return new DerbySchema(this.jdbcTemplate, (DerbyDatabase)this.database, name);
   }
}
