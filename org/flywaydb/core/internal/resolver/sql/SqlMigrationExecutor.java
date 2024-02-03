package org.flywaydb.core.internal.resolver.sql;

import java.sql.Connection;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.resolver.MigrationExecutor;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.SqlScript;
import org.flywaydb.core.internal.util.PlaceholderReplacer;
import org.flywaydb.core.internal.util.scanner.LoadableResource;

public class SqlMigrationExecutor implements MigrationExecutor {
   private final Database database;
   private final PlaceholderReplacer placeholderReplacer;
   private final LoadableResource resource;
   private final FlywayConfiguration configuration;
   private SqlScript sqlScript;

   SqlMigrationExecutor(Database database, LoadableResource resource, PlaceholderReplacer placeholderReplacer, FlywayConfiguration configuration) {
      this.database = database;
      this.resource = resource;
      this.placeholderReplacer = placeholderReplacer;
      this.configuration = configuration;
   }

   public void execute(Connection connection) {
      this.getSqlScript().execute(this.database.getMigrationConnection().getJdbcTemplate());
   }

   private synchronized SqlScript getSqlScript() {
      if (this.sqlScript == null) {
         this.sqlScript = this.database.createSqlScript(this.resource, this.placeholderReplacer.replacePlaceholders(this.resource.loadAsString(this.configuration.getEncoding())), this.configuration.isMixed());
      }

      return this.sqlScript;
   }

   public boolean executeInTransaction() {
      return this.getSqlScript().executeInTransaction();
   }
}
