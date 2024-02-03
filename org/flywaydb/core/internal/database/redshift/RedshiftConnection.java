package org.flywaydb.core.internal.database.redshift;

import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.StringUtils;

public class RedshiftConnection extends Connection<RedshiftDatabase> {
   RedshiftConnection(FlywayConfiguration configuration, RedshiftDatabase database, java.sql.Connection connection, int nullType) {
      super(configuration, database, connection, nullType);
   }

   protected String doGetCurrentSchemaName() throws SQLException {
      return this.jdbcTemplate.queryForString("SHOW search_path");
   }

   public void changeCurrentSchemaTo(Schema schema) {
      try {
         if (!schema.getName().equals(this.originalSchema) && !this.originalSchema.startsWith(schema.getName() + ",") && schema.exists()) {
            if (StringUtils.hasText(this.originalSchema) && !"unset".equals(this.originalSchema)) {
               this.doChangeCurrentSchemaTo(schema.toString() + "," + this.originalSchema);
            } else {
               this.doChangeCurrentSchemaTo(schema.toString());
            }

         }
      } catch (SQLException var3) {
         throw new FlywaySqlException("Error setting current schema to " + schema, var3);
      }
   }

   public void doChangeCurrentSchemaTo(String schema) throws SQLException {
      if ("unset".equals(schema)) {
         schema = "";
      }

      this.jdbcTemplate.execute("SELECT set_config('search_path', ?, false)", schema);
   }

   public Schema getOriginalSchema() {
      return this.originalSchema == null ? null : this.getSchema(getFirstSchemaFromSearchPath(this.originalSchema));
   }

   static String getFirstSchemaFromSearchPath(String searchPath) {
      String result = searchPath.replace("\"$user\"", "").replace("$user", "").trim();
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
      return new RedshiftSchema(this.jdbcTemplate, (RedshiftDatabase)this.database, name);
   }
}
