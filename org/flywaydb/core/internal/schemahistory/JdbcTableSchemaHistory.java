package org.flywaydb.core.internal.schemahistory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationType;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.api.resolver.ResolvedMigration;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.util.jdbc.RowMapper;
import org.flywaydb.core.internal.util.scanner.Resource;

public class JdbcTableSchemaHistory extends SchemaHistory {
   private static final Log LOG = LogFactory.getLog(JdbcTableSchemaHistory.class);
   private final Database database;
   private Table table;
   private boolean tableFallback;
   private final Connection<?> connection;
   private final JdbcTemplate jdbcTemplate;
   private final LinkedList<AppliedMigration> cache = new LinkedList();
   private String installedBy;

   JdbcTableSchemaHistory(Database database, Table table, String installedBy) {
      this.connection = database.getMainConnection();
      this.database = database;
      this.table = table;
      this.installedBy = installedBy;
      this.jdbcTemplate = this.connection.getJdbcTemplate();
   }

   public void clearCache() {
      this.cache.clear();
   }

   public boolean exists() {
      if (!this.tableFallback && this.table.getName().equals("flyway_schema_history")) {
         Table fallbackTable = this.table.getSchema().getTable("schema_version");
         if (fallbackTable.exists()) {
            LOG.warn("Could not find schema history table " + this.table + ", but found " + fallbackTable + " instead. You are seeing this message because Flyway changed its default for flyway.table in version 5.0.0 to flyway_schema_history and you are still relying on the old default (schema_version). Set flyway.table=schema_version in your configuration to fix this. This fallback mechanism will be removed in Flyway 6.0.0.");
            this.tableFallback = true;
            this.table = fallbackTable;
         }
      }

      return this.table.exists();
   }

   public void create() {
      int retries = 0;

      while(!this.exists()) {
         if (retries == 0) {
            LOG.info("Creating Schema History table: " + this.table);
         }

         try {
            this.database.createSqlScript((Resource)null, this.database.getCreateScript(this.table), false).execute(this.connection.getJdbcTemplate());
            LOG.debug("Schema History table " + this.table + " created.");
         } catch (FlywayException var5) {
            ++retries;
            if (retries >= 10) {
               throw var5;
            }

            try {
               LOG.debug("Schema History table creation failed. Retrying in 1 sec ...");
               Thread.sleep(1000L);
            } catch (InterruptedException var4) {
            }
         }
      }

   }

   public <T> T lock(Callable<T> callable) {
      return this.connection.lock(this.table, callable);
   }

   protected void doAddAppliedMigration(MigrationVersion version, String description, MigrationType type, String script, Integer checksum, int executionTime, boolean success) {
      this.connection.changeCurrentSchemaTo(this.table.getSchema());
      if (!this.database.supportsDdlTransactions()) {
         this.table.lock();
      }

      try {
         String versionStr = version == null ? null : version.toString();
         int installedRank = type == MigrationType.SCHEMA ? 0 : this.calculateInstalledRank();
         this.jdbcTemplate.update(this.database.getInsertStatement(this.table), installedRank, versionStr, description, type.name(), script, checksum, this.installedBy, executionTime, success);
         LOG.debug("Schema history table " + this.table + " successfully updated to reflect changes");
      } catch (SQLException var10) {
         throw new FlywaySqlException("Unable to insert row for version '" + version + "' in Schema History table " + this.table, var10);
      }
   }

   private int calculateInstalledRank() throws SQLException {
      int currentMax = this.jdbcTemplate.queryForInt("SELECT MAX(" + this.database.quote("installed_rank") + ") FROM " + this.table);
      return currentMax + 1;
   }

   public List<AppliedMigration> allAppliedMigrations() {
      return this.findAppliedMigrations();
   }

   private List<AppliedMigration> findAppliedMigrations(MigrationType... migrationTypes) {
      if (!this.exists()) {
         return new ArrayList();
      } else {
         int minInstalledRank = this.cache.isEmpty() ? -1 : ((AppliedMigration)this.cache.getLast()).getInstalledRank();
         String query = "SELECT " + this.database.quote("installed_rank") + "," + this.database.quote("version") + "," + this.database.quote("description") + "," + this.database.quote("type") + "," + this.database.quote("script") + "," + this.database.quote("checksum") + "," + this.database.quote("installed_on") + "," + this.database.quote("installed_by") + "," + this.database.quote("execution_time") + "," + this.database.quote("success") + " FROM " + this.table + " WHERE " + this.database.quote("installed_rank") + " > " + minInstalledRank;
         if (migrationTypes.length > 0) {
            query = query + " AND " + this.database.quote("type") + " IN (";
            StringBuilder queryBuilder = new StringBuilder(query);

            for(int i = 0; i < migrationTypes.length; ++i) {
               if (i > 0) {
                  queryBuilder.append(",");
               }

               queryBuilder.append("'").append(migrationTypes[i]).append("'");
            }

            query = queryBuilder.toString();
            query = query + ")";
         }

         query = query + " ORDER BY " + this.database.quote("installed_rank");

         try {
            this.cache.addAll(this.jdbcTemplate.query(query, new RowMapper<AppliedMigration>() {
               public AppliedMigration mapRow(ResultSet rs) throws SQLException {
                  Integer checksum = rs.getInt("checksum");
                  if (rs.wasNull()) {
                     checksum = null;
                  }

                  return new AppliedMigration(rs.getInt("installed_rank"), rs.getString("version") != null ? MigrationVersion.fromVersion(rs.getString("version")) : null, rs.getString("description"), MigrationType.valueOf(rs.getString("type")), rs.getString("script"), checksum, rs.getTimestamp("installed_on"), rs.getString("installed_by"), rs.getInt("execution_time"), rs.getBoolean("success"));
               }
            }));
            return this.cache;
         } catch (SQLException var6) {
            throw new FlywaySqlException("Error while retrieving the list of applied migrations from Schema History table " + this.table, var6);
         }
      }
   }

   public void removeFailedMigrations() {
      if (!this.exists()) {
         LOG.info("Repair of failed migration in Schema History table " + this.table + " not necessary. No failed migration detected.");
      } else {
         try {
            int failedCount = this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM " + this.table + " WHERE " + this.database.quote("success") + "=" + this.database.getBooleanFalse());
            if (failedCount == 0) {
               LOG.info("Repair of failed migration in Schema History table " + this.table + " not necessary. No failed migration detected.");
               return;
            }
         } catch (SQLException var3) {
            throw new FlywaySqlException("Unable to check the Schema History table " + this.table + " for failed migrations", var3);
         }

         try {
            this.jdbcTemplate.execute("DELETE FROM " + this.table + " WHERE " + this.database.quote("success") + " = " + this.database.getBooleanFalse());
         } catch (SQLException var2) {
            throw new FlywaySqlException("Unable to repair Schema History table " + this.table, var2);
         }
      }
   }

   public void addSchemasMarker(Schema[] schemas) {
      this.table.lock();
      this.doAddSchemasMarker(schemas);
   }

   public boolean hasSchemasMarker() {
      if (!this.table.exists()) {
         return false;
      } else {
         try {
            int count = this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM " + this.table + " WHERE " + this.database.quote("type") + "='SCHEMA'");
            return count > 0;
         } catch (SQLException var2) {
            throw new FlywaySqlException("Unable to check whether the Schema History table " + this.table + " has a schema marker migration", var2);
         }
      }
   }

   public boolean hasBaselineMarker() {
      if (!this.table.exists()) {
         return false;
      } else {
         try {
            int count = this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM " + this.table + " WHERE " + this.database.quote("type") + "='INIT' OR " + this.database.quote("type") + "='BASELINE'");
            return count > 0;
         } catch (SQLException var2) {
            throw new FlywaySqlException("Unable to check whether the Schema History table " + this.table + " has an baseline marker migration", var2);
         }
      }
   }

   public AppliedMigration getBaselineMarker() {
      List<AppliedMigration> appliedMigrations = this.findAppliedMigrations(MigrationType.BASELINE);
      return appliedMigrations.isEmpty() ? null : (AppliedMigration)appliedMigrations.get(0);
   }

   public boolean hasAppliedMigrations() {
      if (!this.table.exists()) {
         return false;
      } else {
         try {
            int count = this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM " + this.table + " WHERE " + this.database.quote("type") + " NOT IN ('SCHEMA', 'INIT', 'BASELINE')");
            return count > 0;
         } catch (SQLException var2) {
            throw new FlywaySqlException("Unable to check whether the Schema History table " + this.table + " has applied migrations", var2);
         }
      }
   }

   public void update(AppliedMigration appliedMigration, ResolvedMigration resolvedMigration) {
      this.clearCache();
      MigrationVersion version = appliedMigration.getVersion();
      String description = resolvedMigration.getDescription();
      Integer checksum = resolvedMigration.getChecksum();
      MigrationType type = appliedMigration.getType().isSynthetic() ? appliedMigration.getType() : resolvedMigration.getType();
      LOG.info("Repairing Schema History table for version " + version + " (Description: " + description + ", Type: " + type + ", Checksum: " + checksum + ")  ...");

      try {
         this.jdbcTemplate.update("UPDATE " + this.table + " SET " + this.database.quote("description") + "=? , " + this.database.quote("type") + "=? , " + this.database.quote("checksum") + "=? WHERE " + this.database.quote("version") + "=?", description, type, checksum, version);
      } catch (SQLException var8) {
         throw new FlywaySqlException("Unable to repair Schema History table " + this.table + " for version " + version, var8);
      }
   }

   public String toString() {
      return this.table.toString();
   }
}
