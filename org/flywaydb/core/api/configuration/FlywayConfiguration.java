package org.flywaydb.core.api.configuration;

import java.io.OutputStream;
import java.util.Map;
import javax.sql.DataSource;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.callback.FlywayCallback;
import org.flywaydb.core.api.errorhandler.ErrorHandler;
import org.flywaydb.core.api.resolver.MigrationResolver;

public interface FlywayConfiguration {
   ClassLoader getClassLoader();

   DataSource getDataSource();

   MigrationVersion getBaselineVersion();

   String getBaselineDescription();

   MigrationResolver[] getResolvers();

   boolean isSkipDefaultResolvers();

   FlywayCallback[] getCallbacks();

   boolean isSkipDefaultCallbacks();

   String getSqlMigrationPrefix();

   String getUndoSqlMigrationPrefix();

   String getRepeatableSqlMigrationPrefix();

   String getSqlMigrationSeparator();

   /** @deprecated */
   @Deprecated
   String getSqlMigrationSuffix();

   String[] getSqlMigrationSuffixes();

   boolean isPlaceholderReplacement();

   String getPlaceholderSuffix();

   String getPlaceholderPrefix();

   Map<String, String> getPlaceholders();

   MigrationVersion getTarget();

   String getTable();

   String[] getSchemas();

   String getEncoding();

   String[] getLocations();

   boolean isBaselineOnMigrate();

   boolean isOutOfOrder();

   boolean isIgnoreMissingMigrations();

   boolean isIgnoreFutureMigrations();

   boolean isValidateOnMigrate();

   boolean isCleanOnValidationError();

   boolean isCleanDisabled();

   boolean isMixed();

   boolean isGroup();

   String getInstalledBy();

   ErrorHandler[] getErrorHandlers();

   OutputStream getDryRunOutput();
}
