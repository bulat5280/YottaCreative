package org.flywaydb.core.internal.schemahistory;

import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;

public class SchemaHistoryFactory {
   private SchemaHistoryFactory() {
   }

   public static SchemaHistory getSchemaHistory(FlywayConfiguration configuration, Database database, Schema schema) {
      String installedBy = configuration.getInstalledBy() == null ? database.getCurrentUser() : configuration.getInstalledBy();
      Table table = schema.getTable(configuration.getTable());
      JdbcTableSchemaHistory jdbcTableSchemaHistory = new JdbcTableSchemaHistory(database, table, installedBy);
      return jdbcTableSchemaHistory;
   }
}
