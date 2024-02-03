package org.flywaydb.core.internal.database.h2;

import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Schema;

public class H2Connection extends Connection<H2Database> {
   H2Connection(FlywayConfiguration configuration, H2Database database, java.sql.Connection connection, int nullType) {
      super(configuration, database, connection, nullType);
   }

   public void doChangeCurrentSchemaTo(String schema) throws SQLException {
      this.jdbcTemplate.execute("SET SCHEMA " + ((H2Database)this.database).quote(new String[]{schema}));
   }

   public Schema getSchema(String name) {
      return new H2Schema(this.jdbcTemplate, (H2Database)this.database, name);
   }

   protected String doGetCurrentSchemaName() throws SQLException {
      return this.jdbcTemplate.queryForString("CALL SCHEMA()");
   }
}
