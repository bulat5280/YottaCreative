package org.flywaydb.core.internal.database.cockroachdb;

import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.StringUtils;

public class CockroachDBConnection extends Connection<CockroachDBDatabase> {
   CockroachDBConnection(FlywayConfiguration configuration, CockroachDBDatabase database, java.sql.Connection connection, int nullType) {
      super(configuration, database, connection, nullType);
   }

   public Schema getOriginalSchema() {
      return this.originalSchema == null ? null : this.getSchema(this.getFirstSchemaFromSearchPath(this.originalSchema));
   }

   private String getFirstSchemaFromSearchPath(String searchPath) {
      String result = searchPath.replace(((CockroachDBDatabase)this.database).doQuote("$user"), "").trim();
      if (result.startsWith(",")) {
         result = result.substring(1);
      }

      if (result.contains(",")) {
         result = result.substring(0, result.indexOf(","));
      }

      result = result.trim();
      if (result.startsWith("\"") && result.endsWith("\"") && !result.endsWith("\\\"") && result.length() > 1) {
         result = result.substring(1, result.length() - 1);
      }

      return result;
   }

   public Schema getSchema(String name) {
      return new CockroachDBSchema(this.jdbcTemplate, (CockroachDBDatabase)this.database, name);
   }

   protected String doGetCurrentSchemaName() throws SQLException {
      return this.jdbcTemplate.queryForString("SHOW database");
   }

   public void changeCurrentSchemaTo(Schema schema) {
      try {
         if (!schema.getName().equals(this.originalSchema) && schema.exists()) {
            this.doChangeCurrentSchemaTo(schema.getName());
         }
      } catch (SQLException var3) {
         throw new FlywaySqlException("Error setting current schema to " + schema, var3);
      }
   }

   public void doChangeCurrentSchemaTo(String schema) throws SQLException {
      if (!StringUtils.hasLength(schema)) {
         schema = "DEFAULT";
      }

      this.jdbcTemplate.execute("SET database = " + schema);
   }
}
