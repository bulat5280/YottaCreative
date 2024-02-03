package org.flywaydb.core.internal.resolver.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationType;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.resolver.MigrationResolver;
import org.flywaydb.core.api.resolver.ResolvedMigration;
import org.flywaydb.core.internal.callback.SqlScriptFlywayCallback;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.resolver.MigrationInfoHelper;
import org.flywaydb.core.internal.resolver.ResolvedMigrationComparator;
import org.flywaydb.core.internal.resolver.ResolvedMigrationImpl;
import org.flywaydb.core.internal.util.Location;
import org.flywaydb.core.internal.util.Locations;
import org.flywaydb.core.internal.util.Pair;
import org.flywaydb.core.internal.util.PlaceholderReplacer;
import org.flywaydb.core.internal.util.scanner.LoadableResource;
import org.flywaydb.core.internal.util.scanner.Resource;
import org.flywaydb.core.internal.util.scanner.Scanner;

public class SqlMigrationResolver implements MigrationResolver {
   private final Database database;
   private final Scanner scanner;
   private final Locations locations;
   private final PlaceholderReplacer placeholderReplacer;
   private final FlywayConfiguration configuration;

   public SqlMigrationResolver(Database database, Scanner scanner, Locations locations, PlaceholderReplacer placeholderReplacer, FlywayConfiguration configuration) {
      this.database = database;
      this.scanner = scanner;
      this.locations = locations;
      this.placeholderReplacer = placeholderReplacer;
      this.configuration = configuration;
   }

   public List<ResolvedMigration> resolveMigrations() {
      List<ResolvedMigration> migrations = new ArrayList();
      String separator = this.configuration.getSqlMigrationSeparator();
      String[] suffixes = this.configuration.getSqlMigrationSuffixes();
      Iterator var4 = this.locations.getLocations().iterator();

      while(var4.hasNext()) {
         Location location = (Location)var4.next();
         this.scanForMigrations(location, migrations, this.configuration.getSqlMigrationPrefix(), separator, suffixes, false);
         this.scanForMigrations(location, migrations, this.configuration.getRepeatableSqlMigrationPrefix(), separator, suffixes, true);
      }

      Collections.sort(migrations, new ResolvedMigrationComparator());
      return migrations;
   }

   private void scanForMigrations(Location location, List<ResolvedMigration> migrations, String prefix, String separator, String[] suffixes, boolean repeatable) {
      LoadableResource[] var7 = this.scanner.scanForResources(location, prefix, suffixes);
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         LoadableResource resource = var7[var9];
         String filename = resource.getFilename();
         if (!isSqlCallback(filename, suffixes)) {
            Pair<MigrationVersion, String> info = MigrationInfoHelper.extractVersionAndDescription(filename, prefix, separator, suffixes, repeatable);
            ResolvedMigrationImpl migration = new ResolvedMigrationImpl();
            migration.setVersion((MigrationVersion)info.getLeft());
            migration.setDescription((String)info.getRight());
            migration.setScript(this.extractScriptName(resource, location));
            migration.setChecksum(calculateChecksum(resource, resource.loadAsString(this.configuration.getEncoding())));
            migration.setType(MigrationType.SQL);
            migration.setPhysicalLocation(resource.getLocationOnDisk());
            migration.setExecutor(new SqlMigrationExecutor(this.database, resource, this.placeholderReplacer, this.configuration));
            migrations.add(migration);
         }
      }

   }

   static boolean isSqlCallback(String filename, String... suffixes) {
      String[] var2 = suffixes;
      int var3 = suffixes.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String suffix = var2[var4];
         String baseName = filename.substring(0, filename.length() - suffix.length());
         if (SqlScriptFlywayCallback.ALL_CALLBACKS.contains(baseName)) {
            return true;
         }
      }

      return false;
   }

   String extractScriptName(Resource resource, Location location) {
      return location.getPath().isEmpty() ? resource.getLocation() : resource.getLocation().substring(location.getPath().length() + 1);
   }

   static int calculateChecksum(Resource resource, String str) {
      CRC32 crc32 = new CRC32();
      BufferedReader bufferedReader = new BufferedReader(new StringReader(str));

      String line;
      try {
         while((line = bufferedReader.readLine()) != null) {
            crc32.update(line.getBytes("UTF-8"));
         }
      } catch (IOException var6) {
         String message = "Unable to calculate checksum";
         if (resource != null) {
            message = message + " for " + resource.getLocation() + " (" + resource.getLocationOnDisk() + ")";
         }

         throw new FlywayException(message, var6);
      }

      return (int)crc32.getValue();
   }
}
