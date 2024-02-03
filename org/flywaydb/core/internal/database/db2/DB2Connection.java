package org.flywaydb.core.internal.database.db2;

import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Schema;

public class DB2Connection extends Connection<DB2Database> {
   DB2Connection(FlywayConfiguration configuration, DB2Database database, java.sql.Connection connection, int nullType) {
      super(configuration, database, connection, nullType);
   }

   protected String doGetCurrentSchemaName() throws SQLException {
      return this.jdbcTemplate.queryForString("select current_schema from sysibm.sysdummy1");
   }

   public void doChangeCurrentSchemaTo(String schema) throws SQLException {
      this.jdbcTemplate.execute("SET SCHEMA " + ((DB2Database)this.database).quote(new String[]{schema}));
   }

   public Schema getSchema(String name) {
      return new DB2Schema(this.jdbcTemplate, (DB2Database)this.database, name);
   }
}
