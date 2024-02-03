package org.flywaydb.core;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import javax.sql.DataSource;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationInfoService;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.callback.FlywayCallback;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.errorhandler.ErrorHandler;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.api.resolver.MigrationResolver;
import org.flywaydb.core.internal.callback.SqlScriptFlywayCallback;
import org.flywaydb.core.internal.command.DbBaseline;
import org.flywaydb.core.internal.command.DbClean;
import org.flywaydb.core.internal.command.DbInfo;
import org.flywaydb.core.internal.command.DbMigrate;
import org.flywaydb.core.internal.command.DbRepair;
import org.flywaydb.core.internal.command.DbSchemas;
import org.flywaydb.core.internal.command.DbValidate;
import org.flywaydb.core.internal.configuration.ConfigUtils;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.DatabaseFactory;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.exception.FlywayProUpgradeRequiredException;
import org.flywaydb.core.internal.resolver.CompositeMigrationResolver;
import org.flywaydb.core.internal.schemahistory.SchemaHistory;
import org.flywaydb.core.internal.schemahistory.SchemaHistoryFactory;
import org.flywaydb.core.internal.util.ClassUtils;
import org.flywaydb.core.internal.util.Location;
import org.flywaydb.core.internal.util.Locations;
import org.flywaydb.core.internal.util.PlaceholderReplacer;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.VersionPrinter;
import org.flywaydb.core.internal.util.jdbc.DriverDataSource;
import org.flywaydb.core.internal.util.scanner.Scanner;

public class Flyway implements FlywayConfiguration {
   private static final Log LOG = LogFactory.getLog(Flyway.class);
   private Locations locations;
   private String encoding;
   private String[] schemaNames;
   private String table;
   private MigrationVersion target;
   private boolean placeholderReplacement;
   private Map<String, String> placeholders;
   private String placeholderPrefix;
   private String placeholderSuffix;
   private String sqlMigrationPrefix;
   private String repeatableSqlMigrationPrefix;
   private String sqlMigrationSeparator;
   private String[] sqlMigrationSuffixes;
   private boolean ignoreMissingMigrations;
   private boolean ignoreFutureMigrations;
   private boolean validateOnMigrate;
   private boolean cleanOnValidationError;
   private boolean cleanDisabled;
   private MigrationVersion baselineVersion;
   private String baselineDescription;
   private boolean baselineOnMigrate;
   private boolean outOfOrder;
   private final List<FlywayCallback> callbacks;
   private boolean skipDefaultCallbacks;
   private MigrationResolver[] resolvers;
   private boolean skipDefaultResolvers;
   private DataSource dataSource;
   private ClassLoader classLoader;
   private boolean dbConnectionInfoPrinted;
   private boolean mixed;
   private boolean group;
   private String installedBy;

   public Flyway() {
      this.locations = new Locations(new String[]{"db/migration"});
      this.encoding = "UTF-8";
      this.schemaNames = new String[0];
      this.table = "flyway_schema_history";
      this.placeholderReplacement = true;
      this.placeholders = new HashMap();
      this.placeholderPrefix = "${";
      this.placeholderSuffix = "}";
      this.sqlMigrationPrefix = "V";
      this.repeatableSqlMigrationPrefix = "R";
      this.sqlMigrationSeparator = "__";
      this.sqlMigrationSuffixes = new String[]{".sql"};
      this.ignoreFutureMigrations = true;
      this.validateOnMigrate = true;
      this.baselineVersion = MigrationVersion.fromVersion("1");
      this.baselineDescription = "<< Flyway Baseline >>";
      this.callbacks = new ArrayList();
      this.resolvers = new MigrationResolver[0];
      this.classLoader = Thread.currentThread().getContextClassLoader();
   }

   public Flyway(ClassLoader classLoader) {
      this.locations = new Locations(new String[]{"db/migration"});
      this.encoding = "UTF-8";
      this.schemaNames = new String[0];
      this.table = "flyway_schema_history";
      this.placeholderReplacement = true;
      this.placeholders = new HashMap();
      this.placeholderPrefix = "${";
      this.placeholderSuffix = "}";
      this.sqlMigrationPrefix = "V";
      this.repeatableSqlMigrationPrefix = "R";
      this.sqlMigrationSeparator = "__";
      this.sqlMigrationSuffixes = new String[]{".sql"};
      this.ignoreFutureMigrations = true;
      this.validateOnMigrate = true;
      this.baselineVersion = MigrationVersion.fromVersion("1");
      this.baselineDescription = "<< Flyway Baseline >>";
      this.callbacks = new ArrayList();
      this.resolvers = new MigrationResolver[0];
      this.classLoader = Thread.currentThread().getContextClassLoader();
      if (classLoader != null) {
         this.classLoader = classLoader;
      }

   }

   public Flyway(FlywayConfiguration configuration) {
      this(configuration.getClassLoader());
      this.setBaselineDescription(configuration.getBaselineDescription());
      this.setBaselineOnMigrate(configuration.isBaselineOnMigrate());
      this.setBaselineVersion(configuration.getBaselineVersion());
      this.setCallbacks(configuration.getCallbacks());
      this.setCleanDisabled(configuration.isCleanDisabled());
      this.setCleanOnValidationError(configuration.isCleanOnValidationError());
      this.setDataSource(configuration.getDataSource());
      this.setEncoding(configuration.getEncoding());
      this.setGroup(configuration.isGroup());
      this.setIgnoreFutureMigrations(configuration.isIgnoreFutureMigrations());
      this.setIgnoreMissingMigrations(configuration.isIgnoreMissingMigrations());
      this.setInstalledBy(configuration.getInstalledBy());
      this.setLocations(configuration.getLocations());
      this.setMixed(configuration.isMixed());
      this.setOutOfOrder(configuration.isOutOfOrder());
      this.setPlaceholderPrefix(configuration.getPlaceholderPrefix());
      this.setPlaceholderReplacement(configuration.isPlaceholderReplacement());
      this.setPlaceholders(configuration.getPlaceholders());
      this.setPlaceholderSuffix(configuration.getPlaceholderSuffix());
      this.setRepeatableSqlMigrationPrefix(configuration.getRepeatableSqlMigrationPrefix());
      this.setResolvers(configuration.getResolvers());
      this.setSchemas(configuration.getSchemas());
      this.setSkipDefaultCallbacks(configuration.isSkipDefaultCallbacks());
      this.setSkipDefaultResolvers(configuration.isSkipDefaultResolvers());
      this.setSqlMigrationPrefix(configuration.getSqlMigrationPrefix());
      this.setSqlMigrationSeparator(configuration.getSqlMigrationSeparator());
      this.setSqlMigrationSuffixes(configuration.getSqlMigrationSuffixes());
      this.setTable(configuration.getTable());
      this.setTarget(configuration.getTarget());
      this.setValidateOnMigrate(configuration.isValidateOnMigrate());
   }

   public String[] getLocations() {
      String[] result = new String[this.locations.getLocations().size()];

      for(int i = 0; i < this.locations.getLocations().size(); ++i) {
         result[i] = ((Location)this.locations.getLocations().get(i)).toString();
      }

      return result;
   }

   public String getEncoding() {
      return this.encoding;
   }

   public String[] getSchemas() {
      return this.schemaNames;
   }

   public String getTable() {
      return this.table;
   }

   public MigrationVersion getTarget() {
      return this.target;
   }

   public boolean isPlaceholderReplacement() {
      return this.placeholderReplacement;
   }

   public Map<String, String> getPlaceholders() {
      return this.placeholders;
   }

   public String getPlaceholderPrefix() {
      return this.placeholderPrefix;
   }

   public String getPlaceholderSuffix() {
      return this.placeholderSuffix;
   }

   public String getSqlMigrationPrefix() {
      return this.sqlMigrationPrefix;
   }

   public String getRepeatableSqlMigrationPrefix() {
      return this.repeatableSqlMigrationPrefix;
   }

   public String getSqlMigrationSeparator() {
      return this.sqlMigrationSeparator;
   }

   public String getSqlMigrationSuffix() {
      LOG.warn("sqlMigrationSuffix has been deprecated and will be removed in Flyway 6.0.0. Use sqlMigrationSuffixes instead.");
      return this.sqlMigrationSuffixes[0];
   }

   public String[] getSqlMigrationSuffixes() {
      return this.sqlMigrationSuffixes;
   }

   public boolean isIgnoreMissingMigrations() {
      return this.ignoreMissingMigrations;
   }

   public boolean isIgnoreFutureMigrations() {
      return this.ignoreFutureMigrations;
   }

   public boolean isValidateOnMigrate() {
      return this.validateOnMigrate;
   }

   public boolean isCleanOnValidationError() {
      return this.cleanOnValidationError;
   }

   public boolean isCleanDisabled() {
      return this.cleanDisabled;
   }

   public MigrationVersion getBaselineVersion() {
      return this.baselineVersion;
   }

   public String getBaselineDescription() {
      return this.baselineDescription;
   }

   public boolean isBaselineOnMigrate() {
      return this.baselineOnMigrate;
   }

   public boolean isOutOfOrder() {
      return this.outOfOrder;
   }

   public MigrationResolver[] getResolvers() {
      return this.resolvers;
   }

   public boolean isSkipDefaultResolvers() {
      return this.skipDefaultResolvers;
   }

   public DataSource getDataSource() {
      return this.dataSource;
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }

   public boolean isMixed() {
      return this.mixed;
   }

   public String getInstalledBy() {
      return this.installedBy;
   }

   public boolean isGroup() {
      return this.group;
   }

   public ErrorHandler[] getErrorHandlers() {
      throw new FlywayProUpgradeRequiredException("errorHandlers");
   }

   public OutputStream getDryRunOutput() {
      throw new FlywayProUpgradeRequiredException("dryRunOutput");
   }

   public void setDryRunOutput(OutputStream dryRunOutput) {
      throw new FlywayProUpgradeRequiredException("dryRunOutput");
   }

   public void setDryRunOutputAsFile(File dryRunOutput) {
      throw new FlywayProUpgradeRequiredException("dryRunOutput");
   }

   public void setDryRunOutputAsFileName(String dryRunOutputFileName) {
      throw new FlywayProUpgradeRequiredException("dryRunOutput");
   }

   public void setErrorHandlers(ErrorHandler... errorHandlers) {
      throw new FlywayProUpgradeRequiredException("errorHandlers");
   }

   public void setErrorHandlersAsClassNames(String... errorHandlerClassNames) {
      throw new FlywayProUpgradeRequiredException("errorHandlers");
   }

   public void setGroup(boolean group) {
      this.group = group;
   }

   public void setInstalledBy(String installedBy) {
      if ("".equals(installedBy)) {
         installedBy = null;
      }

      this.installedBy = installedBy;
   }

   public void setMixed(boolean mixed) {
      this.mixed = mixed;
   }

   public void setIgnoreMissingMigrations(boolean ignoreMissingMigrations) {
      this.ignoreMissingMigrations = ignoreMissingMigrations;
   }

   public void setIgnoreFutureMigrations(boolean ignoreFutureMigrations) {
      this.ignoreFutureMigrations = ignoreFutureMigrations;
   }

   public void setValidateOnMigrate(boolean validateOnMigrate) {
      this.validateOnMigrate = validateOnMigrate;
   }

   public void setCleanOnValidationError(boolean cleanOnValidationError) {
      this.cleanOnValidationError = cleanOnValidationError;
   }

   public void setCleanDisabled(boolean cleanDisabled) {
      this.cleanDisabled = cleanDisabled;
   }

   public void setLocations(String... locations) {
      this.locations = new Locations(locations);
   }

   public void setEncoding(String encoding) {
      this.encoding = encoding;
   }

   public void setSchemas(String... schemas) {
      this.schemaNames = schemas;
   }

   public void setTable(String table) {
      this.table = table;
   }

   public void setTarget(MigrationVersion target) {
      this.target = target;
   }

   public void setTargetAsString(String target) {
      this.target = MigrationVersion.fromVersion(target);
   }

   public void setPlaceholderReplacement(boolean placeholderReplacement) {
      this.placeholderReplacement = placeholderReplacement;
   }

   public void setPlaceholders(Map<String, String> placeholders) {
      this.placeholders = placeholders;
   }

   public void setPlaceholderPrefix(String placeholderPrefix) {
      if (!StringUtils.hasLength(placeholderPrefix)) {
         throw new FlywayException("placeholderPrefix cannot be empty!");
      } else {
         this.placeholderPrefix = placeholderPrefix;
      }
   }

   public void setPlaceholderSuffix(String placeholderSuffix) {
      if (!StringUtils.hasLength(placeholderSuffix)) {
         throw new FlywayException("placeholderSuffix cannot be empty!");
      } else {
         this.placeholderSuffix = placeholderSuffix;
      }
   }

   public void setSqlMigrationPrefix(String sqlMigrationPrefix) {
      this.sqlMigrationPrefix = sqlMigrationPrefix;
   }

   public String getUndoSqlMigrationPrefix() {
      throw new FlywayProUpgradeRequiredException("undoSqlMigrationPrefix");
   }

   public void setUndoSqlMigrationPrefix(String undoSqlMigrationPrefix) {
      throw new FlywayProUpgradeRequiredException("undoSqlMigrationPrefix");
   }

   public void setRepeatableSqlMigrationPrefix(String repeatableSqlMigrationPrefix) {
      this.repeatableSqlMigrationPrefix = repeatableSqlMigrationPrefix;
   }

   public void setSqlMigrationSeparator(String sqlMigrationSeparator) {
      if (!StringUtils.hasLength(sqlMigrationSeparator)) {
         throw new FlywayException("sqlMigrationSeparator cannot be empty!");
      } else {
         this.sqlMigrationSeparator = sqlMigrationSeparator;
      }
   }

   /** @deprecated */
   @Deprecated
   public void setSqlMigrationSuffix(String sqlMigrationSuffix) {
      LOG.warn("sqlMigrationSuffix has been deprecated and will be removed in Flyway 6.0.0. Use sqlMigrationSuffixes instead.");
      this.sqlMigrationSuffixes = new String[]{sqlMigrationSuffix};
   }

   public void setSqlMigrationSuffixes(String... sqlMigrationSuffixes) {
      this.sqlMigrationSuffixes = sqlMigrationSuffixes;
   }

   public void setDataSource(DataSource dataSource) {
      this.dataSource = dataSource;
   }

   public void setDataSource(String url, String user, String password, String... initSqls) {
      this.dataSource = new DriverDataSource(this.classLoader, (String)null, url, user, password, (Properties)null, initSqls);
   }

   /** @deprecated */
   @Deprecated
   public void setClassLoader(ClassLoader classLoader) {
      LOG.warn("Flyway.setClassLoader() is deprecated and will be removed in Flyway 6.0. Use new Flyway(ClassLoader) instead.");
      this.classLoader = classLoader;
   }

   public void setBaselineVersion(MigrationVersion baselineVersion) {
      this.baselineVersion = baselineVersion;
   }

   public void setBaselineVersionAsString(String baselineVersion) {
      this.baselineVersion = MigrationVersion.fromVersion(baselineVersion);
   }

   public void setBaselineDescription(String baselineDescription) {
      this.baselineDescription = baselineDescription;
   }

   public void setBaselineOnMigrate(boolean baselineOnMigrate) {
      this.baselineOnMigrate = baselineOnMigrate;
   }

   public void setOutOfOrder(boolean outOfOrder) {
      this.outOfOrder = outOfOrder;
   }

   public FlywayCallback[] getCallbacks() {
      return (FlywayCallback[])this.callbacks.toArray(new FlywayCallback[this.callbacks.size()]);
   }

   public boolean isSkipDefaultCallbacks() {
      return this.skipDefaultCallbacks;
   }

   public void setCallbacks(FlywayCallback... callbacks) {
      this.callbacks.clear();
      this.callbacks.addAll(Arrays.asList(callbacks));
   }

   public void setCallbacksAsClassNames(String... callbacks) {
      this.callbacks.clear();
      this.callbacks.addAll(ClassUtils.instantiateAll(callbacks, this.classLoader));
   }

   public void setSkipDefaultCallbacks(boolean skipDefaultCallbacks) {
      this.skipDefaultCallbacks = skipDefaultCallbacks;
   }

   public void setResolvers(MigrationResolver... resolvers) {
      this.resolvers = resolvers;
   }

   public void setResolversAsClassNames(String... resolvers) {
      List<MigrationResolver> resolverList = ClassUtils.instantiateAll(resolvers, this.classLoader);
      this.setResolvers((MigrationResolver[])resolverList.toArray(new MigrationResolver[resolvers.length]));
   }

   public void setSkipDefaultResolvers(boolean skipDefaultResolvers) {
      this.skipDefaultResolvers = skipDefaultResolvers;
   }

   public int migrate() throws FlywayException {
      return (Integer)this.execute(new Flyway.Command<Integer>() {
         public Integer execute(MigrationResolver migrationResolver, SchemaHistory schemaHistory, Database database, Schema[] schemas, List<FlywayCallback> effectiveCallbacks) {
            if (Flyway.this.validateOnMigrate) {
               Flyway.this.doValidate(database, migrationResolver, schemaHistory, schemas, effectiveCallbacks, true);
            }

            (new DbSchemas(database, schemas, schemaHistory)).create();
            if (!schemaHistory.exists()) {
               List<Schema> nonEmptySchemas = new ArrayList();
               Schema[] var7 = schemas;
               int var8 = schemas.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  Schema schema = var7[var9];
                  if (!schema.empty()) {
                     nonEmptySchemas.add(schema);
                  }
               }

               if (!nonEmptySchemas.isEmpty()) {
                  if (Flyway.this.baselineOnMigrate) {
                     (new DbBaseline(database, schemaHistory, schemas[0], Flyway.this.baselineVersion, Flyway.this.baselineDescription, effectiveCallbacks)).baseline();
                  } else if (!schemaHistory.exists()) {
                     throw new FlywayException("Found non-empty schema(s) " + StringUtils.collectionToCommaDelimitedString(nonEmptySchemas) + " without schema history table! Use baseline() or set baselineOnMigrate to true to initialize the schema history table.");
                  }
               }
            }

            return (new DbMigrate(database, schemaHistory, schemas[0], migrationResolver, Flyway.this, effectiveCallbacks)).migrate();
         }
      });
   }

   public int undo() throws FlywayException {
      throw new FlywayProUpgradeRequiredException("undo");
   }

   public void validate() throws FlywayException {
      this.execute(new Flyway.Command<Void>() {
         public Void execute(MigrationResolver migrationResolver, SchemaHistory schemaHistory, Database database, Schema[] schemas, List<FlywayCallback> effectiveCallbacks) {
            Flyway.this.doValidate(database, migrationResolver, schemaHistory, schemas, effectiveCallbacks, false);
            return null;
         }
      });
   }

   private void doValidate(Database database, MigrationResolver migrationResolver, SchemaHistory schemaHistory, Schema[] schemas, List<FlywayCallback> effectiveCallbacks, boolean pending) {
      String validationError = (new DbValidate(database, schemaHistory, schemas[0], migrationResolver, this.target, this.outOfOrder, pending, this.ignoreMissingMigrations, this.ignoreFutureMigrations, effectiveCallbacks)).validate();
      if (validationError != null) {
         if (!this.cleanOnValidationError) {
            throw new FlywayException("Validate failed: " + validationError);
         }

         (new DbClean(database, schemaHistory, schemas, effectiveCallbacks, this.cleanDisabled)).clean();
      }

   }

   public void clean() {
      this.execute(new Flyway.Command<Void>() {
         public Void execute(MigrationResolver migrationResolver, SchemaHistory schemaHistory, Database database, Schema[] schemas, List<FlywayCallback> effectiveCallbacks) {
            (new DbClean(database, schemaHistory, schemas, effectiveCallbacks, Flyway.this.cleanDisabled)).clean();
            return null;
         }
      });
   }

   public MigrationInfoService info() {
      return (MigrationInfoService)this.execute(new Flyway.Command<MigrationInfoService>() {
         public MigrationInfoService execute(MigrationResolver migrationResolver, SchemaHistory schemaHistory, Database database, Schema[] schemas, List<FlywayCallback> effectiveCallbacks) {
            return (new DbInfo(migrationResolver, schemaHistory, database, Flyway.this, schemas, effectiveCallbacks)).info();
         }
      });
   }

   public void baseline() throws FlywayException {
      this.execute(new Flyway.Command<Void>() {
         public Void execute(MigrationResolver migrationResolver, SchemaHistory schemaHistory, Database database, Schema[] schemas, List<FlywayCallback> effectiveCallbacks) {
            (new DbSchemas(database, schemas, schemaHistory)).create();
            (new DbBaseline(database, schemaHistory, schemas[0], Flyway.this.baselineVersion, Flyway.this.baselineDescription, effectiveCallbacks)).baseline();
            return null;
         }
      });
   }

   public void repair() throws FlywayException {
      this.execute(new Flyway.Command<Void>() {
         public Void execute(MigrationResolver migrationResolver, SchemaHistory schemaHistory, Database database, Schema[] schemas, List<FlywayCallback> effectiveCallbacks) {
            (new DbRepair(database, schemas[0], migrationResolver, schemaHistory, effectiveCallbacks)).repair();
            return null;
         }
      });
   }

   private MigrationResolver createMigrationResolver(Database database, Scanner scanner) {
      MigrationResolver[] var3 = this.resolvers;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         MigrationResolver resolver = var3[var5];
         ConfigUtils.injectFlywayConfiguration(resolver, this);
      }

      return new CompositeMigrationResolver(database, scanner, this, this.locations, this.createPlaceholderReplacer(), this.resolvers);
   }

   private PlaceholderReplacer createPlaceholderReplacer() {
      return this.placeholderReplacement ? new PlaceholderReplacer(this.placeholders, this.placeholderPrefix, this.placeholderSuffix) : PlaceholderReplacer.NO_PLACEHOLDERS;
   }

   public void configure(Properties properties) {
      this.configure(ConfigUtils.propertiesToMap(properties));
   }

   public void configure(Map<String, String> props) {
      Map<String, String> props = new HashMap(props);
      String driverProp = (String)props.remove("flyway.driver");
      String urlProp = (String)props.remove("flyway.url");
      String userProp = (String)props.remove("flyway.user");
      String passwordProp = (String)props.remove("flyway.password");
      if (StringUtils.hasText(urlProp)) {
         this.setDataSource(new DriverDataSource(this.classLoader, driverProp, urlProp, userProp, passwordProp, (Properties)null, new String[0]));
      } else if (!StringUtils.hasText(urlProp) && (StringUtils.hasText(driverProp) || StringUtils.hasText(userProp) || StringUtils.hasText(passwordProp))) {
         LOG.warn("Discarding INCOMPLETE dataSource configuration! flyway.url must be set.");
      }

      String locationsProp = (String)props.remove("flyway.locations");
      if (locationsProp != null) {
         this.setLocations(StringUtils.tokenizeToStringArray(locationsProp, ","));
      }

      Boolean placeholderReplacementProp = this.getBooleanProp(props, "flyway.placeholderReplacement");
      if (placeholderReplacementProp != null) {
         this.setPlaceholderReplacement(placeholderReplacementProp);
      }

      String placeholderPrefixProp = (String)props.remove("flyway.placeholderPrefix");
      if (placeholderPrefixProp != null) {
         this.setPlaceholderPrefix(placeholderPrefixProp);
      }

      String placeholderSuffixProp = (String)props.remove("flyway.placeholderSuffix");
      if (placeholderSuffixProp != null) {
         this.setPlaceholderSuffix(placeholderSuffixProp);
      }

      String sqlMigrationPrefixProp = (String)props.remove("flyway.sqlMigrationPrefix");
      if (sqlMigrationPrefixProp != null) {
         this.setSqlMigrationPrefix(sqlMigrationPrefixProp);
      }

      String undoSqlMigrationPrefixProp = (String)props.remove("flyway.undoSqlMigrationPrefix");
      if (undoSqlMigrationPrefixProp != null) {
         this.setUndoSqlMigrationPrefix(undoSqlMigrationPrefixProp);
      }

      String repeatableSqlMigrationPrefixProp = (String)props.remove("flyway.repeatableSqlMigrationPrefix");
      if (repeatableSqlMigrationPrefixProp != null) {
         this.setRepeatableSqlMigrationPrefix(repeatableSqlMigrationPrefixProp);
      }

      String sqlMigrationSeparatorProp = (String)props.remove("flyway.sqlMigrationSeparator");
      if (sqlMigrationSeparatorProp != null) {
         this.setSqlMigrationSeparator(sqlMigrationSeparatorProp);
      }

      String sqlMigrationSuffixProp = (String)props.remove("flyway.sqlMigrationSuffix");
      if (sqlMigrationSuffixProp != null) {
         this.setSqlMigrationSuffix(sqlMigrationSuffixProp);
      }

      String sqlMigrationSuffixesProp = (String)props.remove("flyway.sqlMigrationSuffixes");
      if (sqlMigrationSuffixesProp != null) {
         this.setSqlMigrationSuffixes(StringUtils.tokenizeToStringArray(sqlMigrationSuffixesProp, ","));
      }

      String encodingProp = (String)props.remove("flyway.encoding");
      if (encodingProp != null) {
         this.setEncoding(encodingProp);
      }

      String schemasProp = (String)props.remove("flyway.schemas");
      if (schemasProp != null) {
         this.setSchemas(StringUtils.tokenizeToStringArray(schemasProp, ","));
      }

      String tableProp = (String)props.remove("flyway.table");
      if (tableProp != null) {
         this.setTable(tableProp);
      }

      Boolean cleanOnValidationErrorProp = this.getBooleanProp(props, "flyway.cleanOnValidationError");
      if (cleanOnValidationErrorProp != null) {
         this.setCleanOnValidationError(cleanOnValidationErrorProp);
      }

      Boolean cleanDisabledProp = this.getBooleanProp(props, "flyway.cleanDisabled");
      if (cleanDisabledProp != null) {
         this.setCleanDisabled(cleanDisabledProp);
      }

      Boolean validateOnMigrateProp = this.getBooleanProp(props, "flyway.validateOnMigrate");
      if (validateOnMigrateProp != null) {
         this.setValidateOnMigrate(validateOnMigrateProp);
      }

      String baselineVersionProp = (String)props.remove("flyway.baselineVersion");
      if (baselineVersionProp != null) {
         this.setBaselineVersion(MigrationVersion.fromVersion(baselineVersionProp));
      }

      String baselineDescriptionProp = (String)props.remove("flyway.baselineDescription");
      if (baselineDescriptionProp != null) {
         this.setBaselineDescription(baselineDescriptionProp);
      }

      Boolean baselineOnMigrateProp = this.getBooleanProp(props, "flyway.baselineOnMigrate");
      if (baselineOnMigrateProp != null) {
         this.setBaselineOnMigrate(baselineOnMigrateProp);
      }

      Boolean ignoreMissingMigrationsProp = this.getBooleanProp(props, "flyway.ignoreMissingMigrations");
      if (ignoreMissingMigrationsProp != null) {
         this.setIgnoreMissingMigrations(ignoreMissingMigrationsProp);
      }

      Boolean ignoreFutureMigrationsProp = this.getBooleanProp(props, "flyway.ignoreFutureMigrations");
      if (ignoreFutureMigrationsProp != null) {
         this.setIgnoreFutureMigrations(ignoreFutureMigrationsProp);
      }

      String targetProp = (String)props.remove("flyway.target");
      if (targetProp != null) {
         this.setTarget(MigrationVersion.fromVersion(targetProp));
      }

      Boolean outOfOrderProp = this.getBooleanProp(props, "flyway.outOfOrder");
      if (outOfOrderProp != null) {
         this.setOutOfOrder(outOfOrderProp);
      }

      String resolversProp = (String)props.remove("flyway.resolvers");
      if (StringUtils.hasLength(resolversProp)) {
         this.setResolversAsClassNames(StringUtils.tokenizeToStringArray(resolversProp, ","));
      }

      Boolean skipDefaultResolversProp = this.getBooleanProp(props, "flyway.skipDefaultResolvers");
      if (skipDefaultResolversProp != null) {
         this.setSkipDefaultResolvers(skipDefaultResolversProp);
      }

      String callbacksProp = (String)props.remove("flyway.callbacks");
      if (StringUtils.hasLength(callbacksProp)) {
         this.setCallbacksAsClassNames(StringUtils.tokenizeToStringArray(callbacksProp, ","));
      }

      Boolean skipDefaultCallbacksProp = this.getBooleanProp(props, "flyway.skipDefaultCallbacks");
      if (skipDefaultCallbacksProp != null) {
         this.setSkipDefaultCallbacks(skipDefaultCallbacksProp);
      }

      Map<String, String> placeholdersFromProps = new HashMap(this.placeholders);
      Iterator iterator = props.entrySet().iterator();

      String installedByProp;
      String dryRunOutputProp;
      while(iterator.hasNext()) {
         Entry<String, String> entry = (Entry)iterator.next();
         String propertyName = (String)entry.getKey();
         if (propertyName.startsWith("flyway.placeholders.") && propertyName.length() > "flyway.placeholders.".length()) {
            installedByProp = propertyName.substring("flyway.placeholders.".length());
            dryRunOutputProp = (String)entry.getValue();
            placeholdersFromProps.put(installedByProp, dryRunOutputProp);
            iterator.remove();
         }
      }

      this.setPlaceholders(placeholdersFromProps);
      Boolean mixedProp = this.getBooleanProp(props, "flyway.mixed");
      if (mixedProp != null) {
         this.setMixed(mixedProp);
      }

      Boolean groupProp = this.getBooleanProp(props, "flyway.group");
      if (groupProp != null) {
         this.setGroup(groupProp);
      }

      installedByProp = (String)props.remove("flyway.installedBy");
      if (installedByProp != null) {
         this.setInstalledBy(installedByProp);
      }

      dryRunOutputProp = (String)props.remove("flyway.dryRunOutput");
      if (dryRunOutputProp != null) {
         this.setDryRunOutputAsFileName(dryRunOutputProp);
      }

      String errorHandlersProp = (String)props.remove("flyway.errorHandlers");
      if (errorHandlersProp != null) {
         this.setErrorHandlersAsClassNames(StringUtils.tokenizeToStringArray(errorHandlersProp, ","));
      }

      Iterator var40 = props.keySet().iterator();

      String key;
      do {
         if (!var40.hasNext()) {
            return;
         }

         key = (String)var40.next();
      } while(!key.startsWith("flyway."));

      throw new FlywayException("Unknown configuration property: " + key);
   }

   private Boolean getBooleanProp(Map<String, String> props, String key) {
      String value = (String)props.remove(key);
      if (value != null && !"true".equals(value) && !"false".equals(value)) {
         throw new FlywayException("Invalid value for " + key + " (should be either true or false): " + value);
      } else {
         return value == null ? null : Boolean.valueOf(value);
      }
   }

   <T> T execute(Flyway.Command<T> command) {
      VersionPrinter.printVersion();
      if (this.dataSource == null) {
         throw new FlywayException("Unable to connect to the database. Configure the url, user and password!");
      } else {
         Database database = null;

         Object result;
         try {
            database = DatabaseFactory.createDatabase(this, !this.dbConnectionInfoPrinted);
            this.dbConnectionInfoPrinted = true;
            LOG.debug("DDL Transactions Supported: " + database.supportsDdlTransactions());
            Schema[] schemas = this.prepareSchemas(database);
            Scanner scanner = new Scanner(this.classLoader);
            MigrationResolver migrationResolver = this.createMigrationResolver(database, scanner);
            List<FlywayCallback> effectiveCallbacks = this.prepareCallbacks(scanner, database);
            SchemaHistory schemaHistory = SchemaHistoryFactory.getSchemaHistory(this, database, schemas[0]);
            result = command.execute(migrationResolver, schemaHistory, database, schemas, effectiveCallbacks);
         } finally {
            if (database != null) {
               database.close();
            }

         }

         return result;
      }
   }

   private Schema[] prepareSchemas(Database database) {
      if (this.schemaNames.length == 0) {
         Schema currentSchema = database.getMainConnection().getOriginalSchema();
         if (currentSchema == null) {
            throw new FlywayException("Unable to determine schema for the schema history table. Set a default schema for the connection or specify one using the schemas property!");
         }

         this.setSchemas(currentSchema.getName());
      }

      if (this.schemaNames.length == 1) {
         LOG.debug("Schema: " + this.schemaNames[0]);
      } else {
         LOG.debug("Schemas: " + StringUtils.arrayToCommaDelimitedString(this.schemaNames));
      }

      Schema[] schemas = new Schema[this.schemaNames.length];

      for(int i = 0; i < this.schemaNames.length; ++i) {
         schemas[i] = database.getMainConnection().getSchema(this.schemaNames[i]);
      }

      return schemas;
   }

   private List<FlywayCallback> prepareCallbacks(Scanner scanner, Database database) {
      List<FlywayCallback> effectiveCallbacks = new ArrayList();
      effectiveCallbacks.addAll(this.callbacks);
      if (!this.skipDefaultCallbacks) {
         effectiveCallbacks.add(new SqlScriptFlywayCallback(database, scanner, this.locations, this.createPlaceholderReplacer(), this));
      }

      Iterator var4 = effectiveCallbacks.iterator();

      while(var4.hasNext()) {
         FlywayCallback callback = (FlywayCallback)var4.next();
         ConfigUtils.injectFlywayConfiguration(callback, this);
      }

      return effectiveCallbacks;
   }

   interface Command<T> {
      T execute(MigrationResolver var1, SchemaHistory var2, Database var3, Schema[] var4, List<FlywayCallback> var5);
   }
}
