package org.flywaydb.core.internal.database.hsqldb;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.util.jdbc.JdbcUtils;

public class HSQLDBConnection extends Connection<HSQLDBDatabase> {
   HSQLDBConnection(FlywayConfiguration configuration, HSQLDBDatabase database, java.sql.Connection connection, int nullType) {
      super(configuration, database, connection, nullType);
   }

   protected String doGetCurrentSchemaName() throws SQLException {
      ResultSet resultSet = null;
      String schema = null;

      try {
         resultSet = ((HSQLDBDatabase)this.database).getJdbcMetaData().getSchemas();

         while(resultSet.next()) {
            if (resultSet.getBoolean("IS_DEFAULT")) {
               schema = resultSet.getString("TABLE_SCHEM");
               break;
            }
         }
      } finally {
         JdbcUtils.closeResultSet(resultSet);
      }

      return schema;
   }

   public void doChangeCurrentSchemaTo(String schema) throws SQLException {
      this.jdbcTemplate.execute("SET SCHEMA " + ((HSQLDBDatabase)this.database).quote(new String[]{schema}));
   }

   public Schema getSchema(String name) {
      return new HSQLDBSchema(this.jdbcTemplate, (HSQLDBDatabase)this.database, name);
   }
}
