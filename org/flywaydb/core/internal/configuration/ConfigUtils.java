package org.flywaydb.core.internal.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.configuration.ConfigurationAware;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.util.FileCopyUtils;
import org.flywaydb.core.internal.util.StringUtils;

public class ConfigUtils {
   private static Log LOG = LogFactory.getLog(ConfigUtils.class);
   public static final String CONFIG_FILE_NAME = "flyway.conf";
   /** @deprecated */
   @Deprecated
   public static final String CONFIG_FILE = "flyway.configFile";
   public static final String CONFIG_FILES = "flyway.configFiles";
   public static final String CONFIG_FILE_ENCODING = "flyway.configFileEncoding";
   public static final String BASELINE_DESCRIPTION = "flyway.baselineDescription";
   public static final String BASELINE_ON_MIGRATE = "flyway.baselineOnMigrate";
   public static final String BASELINE_VERSION = "flyway.baselineVersion";
   public static final String CALLBACKS = "flyway.callbacks";
   public static final String CLEAN_DISABLED = "flyway.cleanDisabled";
   public static final String CLEAN_ON_VALIDATION_ERROR = "flyway.cleanOnValidationError";
   public static final String DRIVER = "flyway.driver";
   public static final String DRYRUN_OUTPUT = "flyway.dryRunOutput";
   public static final String ENCODING = "flyway.encoding";
   public static final String ERROR_HANDLERS = "flyway.errorHandlers";
   public static final String GROUP = "flyway.group";
   public static final String IGNORE_FUTURE_MIGRATIONS = "flyway.ignoreFutureMigrations";
   public static final String IGNORE_MISSING_MIGRATIONS = "flyway.ignoreMissingMigrations";
   public static final String INSTALLED_BY = "flyway.installedBy";
   public static final String LOCATIONS = "flyway.locations";
   public static final String MIXED = "flyway.mixed";
   public static final String OUT_OF_ORDER = "flyway.outOfOrder";
   public static final String PASSWORD = "flyway.password";
   public static final String PLACEHOLDER_PREFIX = "flyway.placeholderPrefix";
   public static final String PLACEHOLDER_REPLACEMENT = "flyway.placeholderReplacement";
   public static final String PLACEHOLDER_SUFFIX = "flyway.placeholderSuffix";
   public static final String PLACEHOLDERS_PROPERTY_PREFIX = "flyway.placeholders.";
   public static final String REPEATABLE_SQL_MIGRATION_PREFIX = "flyway.repeatableSqlMigrationPrefix";
   public static final String RESOLVERS = "flyway.resolvers";
   public static final String SCHEMAS = "flyway.schemas";
   public static final String SKIP_DEFAULT_CALLBACKS = "flyway.skipDefaultCallbacks";
   public static final String SKIP_DEFAULT_RESOLVERS = "flyway.skipDefaultResolvers";
   public static final String SQL_MIGRATION_PREFIX = "flyway.sqlMigrationPrefix";
   public static final String SQL_MIGRATION_SEPARATOR = "flyway.sqlMigrationSeparator";
   /** @deprecated */
   @Deprecated
   public static final String SQL_MIGRATION_SUFFIX = "flyway.sqlMigrationSuffix";
   public static final String SQL_MIGRATION_SUFFIXES = "flyway.sqlMigrationSuffixes";
   public static final String TABLE = "flyway.table";
   public static final String TARGET = "flyway.target";
   public static final String UNDO_SQL_MIGRATION_PREFIX = "flyway.undoSqlMigrationPrefix";
   public static final String URL = "flyway.url";
   public static final String USER = "flyway.user";
   public static final String VALIDATE_ON_MIGRATE = "flyway.validateOnMigrate";
   public static final String JAR_DIRS = "flyway.jarDirs";

   private ConfigUtils() {
   }

   public static void injectFlywayConfiguration(Object target, FlywayConfiguration configuration) {
      if (target instanceof ConfigurationAware) {
         ((ConfigurationAware)target).setFlywayConfiguration(configuration);
      }

   }

   public static Map<String, String> environmentVariablesToPropertyMap() {
      Map<String, String> result = new HashMap();
      Iterator var1 = System.getenv().entrySet().iterator();

      while(var1.hasNext()) {
         Entry<String, String> entry = (Entry)var1.next();
         String convertedKey = convertKey((String)entry.getKey());
         if (convertedKey != null) {
            result.put(convertKey((String)entry.getKey()), (String)entry.getValue());
         }
      }

      return result;
   }

   private static String convertKey(String key) {
      if ("FLYWAY_BASELINE_DESCRIPTION".equals(key)) {
         return "flyway.baselineDescription";
      } else if ("FLYWAY_BASELINE_ON_MIGRATE".equals(key)) {
         return "flyway.baselineOnMigrate";
      } else if ("FLYWAY_BASELINE_VERSION".equals(key)) {
         return "flyway.baselineVersion";
      } else if ("FLYWAY_CALLBACKS".equals(key)) {
         return "flyway.callbacks";
      } else if ("FLYWAY_CLEAN_DISABLED".equals(key)) {
         return "flyway.cleanDisabled";
      } else if ("FLYWAY_CLEAN_ON_VALIDATION_ERROR".equals(key)) {
         return "flyway.cleanOnValidationError";
      } else if ("FLYWAY_CONFIG_FILE_ENCODING".equals(key)) {
         return "flyway.configFileEncoding";
      } else if ("FLYWAY_CONFIG_FILES".equals(key)) {
         return "flyway.configFiles";
      } else if ("FLYWAY_DRIVER".equals(key)) {
         return "flyway.driver";
      } else if ("FLYWAY_ENCODING".equals(key)) {
         return "flyway.encoding";
      } else if ("FLYWAY_ERROR_HANDLERS".equals(key)) {
         return "flyway.errorHandlers";
      } else if ("FLYWAY_GROUP".equals(key)) {
         return "flyway.group";
      } else if ("FLYWAY_IGNORE_FUTURE_MIGRATIONS".equals(key)) {
         return "flyway.ignoreFutureMigrations";
      } else if ("FLYWAY_IGNORE_MISSING_MIGRATIONS".equals(key)) {
         return "flyway.ignoreMissingMigrations";
      } else if ("FLYWAY_INSTALLED_BY".equals(key)) {
         return "flyway.installedBy";
      } else if ("FLYWAY_LOCATIONS".equals(key)) {
         return "flyway.locations";
      } else if ("FLYWAY_MIXED".equals(key)) {
         return "flyway.mixed";
      } else if ("FLYWAY_OUT_OF_ORDER".equals(key)) {
         return "flyway.outOfOrder";
      } else if ("FLYWAY_PASSWORD".equals(key)) {
         return "flyway.password";
      } else if ("FLYWAY_PLACEHOLDER_PREFIX".equals(key)) {
         return "flyway.placeholderPrefix";
      } else if ("FLYWAY_PLACEHOLDER_REPLACEMENT".equals(key)) {
         return "flyway.placeholderReplacement";
      } else if ("FLYWAY_PLACEHOLDER_SUFFIX".equals(key)) {
         return "flyway.placeholderSuffix";
      } else if (key.matches("FLYWAY_PLACEHOLDERS_.+")) {
         return "flyway.placeholders." + key.substring("FLYWAY_PLACEHOLDERS_".length()).toLowerCase(Locale.ENGLISH);
      } else if ("FLYWAY_REPEATABLE_SQL_MIGRATION_PREFIX".equals(key)) {
         return "flyway.repeatableSqlMigrationPrefix";
      } else if ("FLYWAY_RESOLVERS".equals(key)) {
         return "flyway.resolvers";
      } else if ("FLYWAY_SCHEMAS".equals(key)) {
         return "flyway.schemas";
      } else if ("FLYWAY_SKIP_DEFAULT_CALLBACKS".equals(key)) {
         return "flyway.skipDefaultCallbacks";
      } else if ("FLYWAY_SKIP_DEFAULT_RESOLVERS".equals(key)) {
         return "flyway.skipDefaultResolvers";
      } else if ("FLYWAY_SQL_MIGRATION_PREFIX".equals(key)) {
         return "flyway.sqlMigrationPrefix";
      } else if ("FLYWAY_SQL_MIGRATION_SEPARATOR".equals(key)) {
         return "flyway.sqlMigrationSeparator";
      } else if ("FLYWAY_SQL_MIGRATION_SUFFIXES".equals(key)) {
         return "flyway.sqlMigrationSuffixes";
      } else if ("FLYWAY_TABLE".equals(key)) {
         return "flyway.table";
      } else if ("FLYWAY_TARGET".equals(key)) {
         return "flyway.target";
      } else if ("FLYWAY_UNDO_SQL_MIGRATION_PREFIX".equals(key)) {
         return "flyway.undoSqlMigrationPrefix";
      } else if ("FLYWAY_URL".equals(key)) {
         return "flyway.url";
      } else if ("FLYWAY_USER".equals(key)) {
         return "flyway.user";
      } else if ("FLYWAY_VALIDATE_ON_MIGRATE".equals(key)) {
         return "flyway.validateOnMigrate";
      } else {
         return "FLYWAY_JAR_DIRS".equals(key) ? "flyway.jarDirs" : null;
      }
   }

   public static Map<String, String> loadConfigurationFile(File configFile, String encoding, boolean failIfMissing) throws FlywayException {
      String errorMessage = "Unable to load config file: " + configFile.getAbsolutePath();
      if (configFile.isFile() && configFile.canRead()) {
         LOG.debug("Loading config file: " + configFile.getAbsolutePath());

         try {
            String contents = FileCopyUtils.copyToString(new InputStreamReader(new FileInputStream(configFile), encoding));
            Properties properties = new Properties();
            properties.load(new StringReader(contents.replace("\\", "\\\\")));
            return propertiesToMap(properties);
         } catch (IOException var6) {
            throw new FlywayException(errorMessage, var6);
         }
      } else if (!failIfMissing) {
         LOG.debug(errorMessage);
         return new HashMap();
      } else {
         throw new FlywayException(errorMessage);
      }
   }

   public static Map<String, String> propertiesToMap(Properties properties) {
      Map<String, String> props = new HashMap();
      Iterator var2 = properties.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<Object, Object> entry = (Entry)var2.next();
         props.put(entry.getKey().toString(), entry.getValue().toString());
      }

      return props;
   }

   public static void putIfSet(Map<String, String> config, String key, Object... values) {
      Object[] var3 = values;
      int var4 = values.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object value = var3[var5];
         if (value != null) {
            config.put(key, value.toString());
            return;
         }
      }

   }

   public static void putArrayIfSet(Map<String, String> config, String key, String[]... values) {
      String[][] var3 = values;
      int var4 = values.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String[] value = var3[var5];
         if (value != null) {
            config.put(key, StringUtils.arrayToCommaDelimitedString(value));
            return;
         }
      }

   }
}
