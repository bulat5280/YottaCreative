package org.flywaydb.core.internal.callback;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.callback.FlywayCallback;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.SqlScript;
import org.flywaydb.core.internal.util.Location;
import org.flywaydb.core.internal.util.Locations;
import org.flywaydb.core.internal.util.PlaceholderReplacer;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.util.scanner.LoadableResource;
import org.flywaydb.core.internal.util.scanner.Scanner;

public class SqlScriptFlywayCallback implements FlywayCallback {
   private static final Log LOG = LogFactory.getLog(SqlScriptFlywayCallback.class);
   private static final String BEFORE_CLEAN = "beforeClean";
   private static final String AFTER_CLEAN = "afterClean";
   private static final String BEFORE_MIGRATE = "beforeMigrate";
   private static final String AFTER_MIGRATE = "afterMigrate";
   private static final String BEFORE_EACH_MIGRATE = "beforeEachMigrate";
   private static final String AFTER_EACH_MIGRATE = "afterEachMigrate";
   private static final String BEFORE_VALIDATE = "beforeValidate";
   private static final String AFTER_VALIDATE = "afterValidate";
   private static final String BEFORE_BASELINE = "beforeBaseline";
   private static final String AFTER_BASELINE = "afterBaseline";
   private static final String BEFORE_REPAIR = "beforeRepair";
   private static final String AFTER_REPAIR = "afterRepair";
   private static final String BEFORE_INFO = "beforeInfo";
   private static final String AFTER_INFO = "afterInfo";
   public static final List<String> ALL_CALLBACKS = Arrays.asList("beforeClean", "afterClean", "beforeMigrate", "beforeEachMigrate", "afterEachMigrate", "afterMigrate", "beforeValidate", "afterValidate", "beforeBaseline", "afterBaseline", "beforeRepair", "afterRepair", "beforeInfo", "afterInfo");
   private final Map<String, SqlScript> scripts = new HashMap();

   public SqlScriptFlywayCallback(Database database, Scanner scanner, Locations locations, PlaceholderReplacer placeholderReplacer, FlywayConfiguration configuration) {
      Iterator var6 = ALL_CALLBACKS.iterator();

      while(var6.hasNext()) {
         String callback = (String)var6.next();
         this.scripts.put(callback, (Object)null);
      }

      LOG.debug("Scanning for SQL callbacks ...");
      var6 = locations.getLocations().iterator();

      while(var6.hasNext()) {
         Location location = (Location)var6.next();

         LoadableResource[] resources;
         try {
            resources = scanner.scanForResources(location, "", configuration.getSqlMigrationSuffixes());
         } catch (FlywayException var15) {
            continue;
         }

         LoadableResource[] var9 = resources;
         int var10 = resources.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            LoadableResource resource = var9[var11];
            String key = this.stripSuffix(resource.getFilename(), configuration.getSqlMigrationSuffixes());
            if (this.scripts.keySet().contains(key)) {
               SqlScript existing = (SqlScript)this.scripts.get(key);
               if (existing != null) {
                  throw new FlywayException("Found more than 1 SQL callback script for " + key + "!\nOffenders:\n-> " + existing.getResource().getLocationOnDisk() + "\n-> " + resource.getLocationOnDisk());
               }

               this.scripts.put(key, database.createSqlScript(resource, placeholderReplacer.replacePlaceholders(resource.loadAsString(configuration.getEncoding())), configuration.isMixed()));
            }
         }
      }

   }

   private String stripSuffix(String fileName, String[] suffixes) {
      String[] var3 = suffixes;
      int var4 = suffixes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String suffix = var3[var5];
         if (fileName.endsWith(suffix)) {
            return fileName.substring(0, fileName.length() - suffix.length());
         }
      }

      return fileName;
   }

   public void beforeClean(Connection connection) {
      this.execute("beforeClean", connection);
   }

   public void afterClean(Connection connection) {
      this.execute("afterClean", connection);
   }

   public void beforeMigrate(Connection connection) {
      this.execute("beforeMigrate", connection);
   }

   public void afterMigrate(Connection connection) {
      this.execute("afterMigrate", connection);
   }

   public void beforeEachMigrate(Connection connection, MigrationInfo info) {
      this.execute("beforeEachMigrate", connection);
   }

   public void afterEachMigrate(Connection connection, MigrationInfo info) {
      this.execute("afterEachMigrate", connection);
   }

   public void beforeUndo(Connection connection) {
   }

   public void afterUndo(Connection connection) {
   }

   public void beforeEachUndo(Connection connection, MigrationInfo info) {
   }

   public void afterEachUndo(Connection connection, MigrationInfo info) {
   }

   public void beforeValidate(Connection connection) {
      this.execute("beforeValidate", connection);
   }

   public void afterValidate(Connection connection) {
      this.execute("afterValidate", connection);
   }

   public void beforeBaseline(Connection connection) {
      this.execute("beforeBaseline", connection);
   }

   public void afterBaseline(Connection connection) {
      this.execute("afterBaseline", connection);
   }

   public void beforeRepair(Connection connection) {
      this.execute("beforeRepair", connection);
   }

   public void afterRepair(Connection connection) {
      this.execute("afterRepair", connection);
   }

   public void beforeInfo(Connection connection) {
      this.execute("beforeInfo", connection);
   }

   public void afterInfo(Connection connection) {
      this.execute("afterInfo", connection);
   }

   private void execute(String key, Connection connection) {
      SqlScript sqlScript = (SqlScript)this.scripts.get(key);
      if (sqlScript != null) {
         LOG.info("Executing SQL callback: " + key);
         sqlScript.execute(new JdbcTemplate(connection, 0));
      }

   }
}
