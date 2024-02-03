package org.flywaydb.core.internal.resolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationType;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.api.migration.MigrationChecksumProvider;
import org.flywaydb.core.api.migration.MigrationInfoProvider;
import org.flywaydb.core.api.resolver.MigrationExecutor;
import org.flywaydb.core.api.resolver.MigrationResolver;
import org.flywaydb.core.api.resolver.ResolvedMigration;
import org.flywaydb.core.internal.configuration.ConfigUtils;
import org.flywaydb.core.internal.util.ClassUtils;
import org.flywaydb.core.internal.util.Location;
import org.flywaydb.core.internal.util.Locations;
import org.flywaydb.core.internal.util.Pair;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.scanner.Scanner;

public abstract class JavaMigrationResolver<M, E extends MigrationExecutor> implements MigrationResolver {
   private static final Log LOG = LogFactory.getLog(JavaMigrationResolver.class);
   private final Locations locations;
   private Scanner scanner;
   private FlywayConfiguration configuration;

   public JavaMigrationResolver(Scanner scanner, Locations locations, FlywayConfiguration configuration) {
      this.locations = locations;
      this.scanner = scanner;
      this.configuration = configuration;
   }

   public List<ResolvedMigration> resolveMigrations() {
      List<ResolvedMigration> migrations = new ArrayList();
      Iterator var2 = this.locations.getLocations().iterator();

      while(var2.hasNext()) {
         Location location = (Location)var2.next();
         if (location.isClassPath()) {
            this.resolveMigrationsForSingleLocation(location, migrations);
         }
      }

      Collections.sort(migrations, new ResolvedMigrationComparator());
      return migrations;
   }

   private void resolveMigrationsForSingleLocation(Location location, List<ResolvedMigration> migrations) {
      try {
         Class<?>[] classes = this.scanner.scanForClasses(location, this.getImplementedInterface());
         Class[] var4 = classes;
         int var5 = classes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class<?> clazz = var4[var6];
            M migration = ClassUtils.instantiate(clazz.getName(), this.scanner.getClassLoader());
            ConfigUtils.injectFlywayConfiguration(migration, this.configuration);
            ResolvedMigrationImpl migrationInfo = this.extractMigrationInfo(migration);
            migrationInfo.setPhysicalLocation(ClassUtils.getLocationOnDisk(clazz));
            migrationInfo.setExecutor(this.createExecutor(migration));
            migrations.add(migrationInfo);
         }

      } catch (Exception var10) {
         throw new FlywayException("Unable to resolve " + this.getMigrationTypeStr() + " Java migrations in location " + location + " : " + var10.getMessage(), var10);
      }
   }

   protected abstract String getMigrationTypeStr();

   protected abstract Class<M> getImplementedInterface();

   protected abstract E createExecutor(M var1);

   public ResolvedMigrationImpl extractMigrationInfo(M migration) {
      Integer checksum = null;
      if (migration instanceof MigrationChecksumProvider) {
         MigrationChecksumProvider checksumProvider = (MigrationChecksumProvider)migration;
         checksum = checksumProvider.getChecksum();
      }

      String description;
      MigrationVersion version;
      if (migration instanceof MigrationInfoProvider) {
         MigrationInfoProvider infoProvider = (MigrationInfoProvider)migration;
         version = infoProvider.getVersion();
         description = infoProvider.getDescription();
         if (!StringUtils.hasText(description)) {
            throw new FlywayException("Missing description for migration " + version);
         }
      } else {
         String shortName = ClassUtils.getShortName(migration.getClass());
         boolean repeatable = shortName.startsWith("R");
         if (!shortName.startsWith("V") && !repeatable) {
            throw new FlywayException("Invalid " + this.getMigrationTypeStr() + " migration class name: " + migration.getClass().getName() + " => ensure it starts with V or R, or implement org.flywaydb.core.api.migration.MigrationInfoProvider for non-default naming");
         }

         String prefix = shortName.substring(0, 1);
         Pair info = MigrationInfoHelper.extractVersionAndDescription(shortName, prefix, "__", new String[]{""}, repeatable);
         version = (MigrationVersion)info.getLeft();
         description = (String)info.getRight();
      }

      ResolvedMigrationImpl resolvedMigration = new ResolvedMigrationImpl();
      resolvedMigration.setVersion(version);
      resolvedMigration.setDescription(description);
      resolvedMigration.setScript(migration.getClass().getName());
      resolvedMigration.setChecksum(checksum);
      resolvedMigration.setType(this.getMigrationType());
      return resolvedMigration;
   }

   protected abstract MigrationType getMigrationType();
}
