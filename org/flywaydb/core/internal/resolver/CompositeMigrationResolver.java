package org.flywaydb.core.internal.resolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.resolver.MigrationResolver;
import org.flywaydb.core.api.resolver.ResolvedMigration;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.resolver.jdbc.JdbcMigrationResolver;
import org.flywaydb.core.internal.resolver.spring.SpringJdbcMigrationResolver;
import org.flywaydb.core.internal.resolver.sql.SqlMigrationResolver;
import org.flywaydb.core.internal.util.FeatureDetector;
import org.flywaydb.core.internal.util.Locations;
import org.flywaydb.core.internal.util.PlaceholderReplacer;
import org.flywaydb.core.internal.util.scanner.Scanner;

public class CompositeMigrationResolver implements MigrationResolver {
   private Collection<MigrationResolver> migrationResolvers = new ArrayList();
   private List<ResolvedMigration> availableMigrations;

   public CompositeMigrationResolver(Database database, Scanner scanner, FlywayConfiguration configuration, Locations locations, PlaceholderReplacer placeholderReplacer, MigrationResolver... customMigrationResolvers) {
      if (!configuration.isSkipDefaultResolvers()) {
         this.migrationResolvers.add(new SqlMigrationResolver(database, scanner, locations, placeholderReplacer, configuration));
         this.migrationResolvers.add(new JdbcMigrationResolver(scanner, locations, configuration));
         if ((new FeatureDetector(scanner.getClassLoader())).isSpringJdbcAvailable()) {
            this.migrationResolvers.add(new SpringJdbcMigrationResolver(scanner, locations, configuration));
         }
      }

      this.migrationResolvers.addAll(Arrays.asList(customMigrationResolvers));
   }

   public List<ResolvedMigration> resolveMigrations() {
      if (this.availableMigrations == null) {
         this.availableMigrations = this.doFindAvailableMigrations();
      }

      return this.availableMigrations;
   }

   private List<ResolvedMigration> doFindAvailableMigrations() throws FlywayException {
      List<ResolvedMigration> migrations = new ArrayList(collectMigrations(this.migrationResolvers));
      Collections.sort(migrations, new ResolvedMigrationComparator());
      checkForIncompatibilities(migrations);
      return migrations;
   }

   static Collection<ResolvedMigration> collectMigrations(Collection<MigrationResolver> migrationResolvers) {
      Set<ResolvedMigration> migrations = new HashSet();
      Iterator var2 = migrationResolvers.iterator();

      while(var2.hasNext()) {
         MigrationResolver migrationResolver = (MigrationResolver)var2.next();
         migrations.addAll(migrationResolver.resolveMigrations());
      }

      return migrations;
   }

   static void checkForIncompatibilities(List<ResolvedMigration> migrations) {
      for(int i = 0; i < migrations.size() - 1; ++i) {
         ResolvedMigration current = (ResolvedMigration)migrations.get(i);
         ResolvedMigration next = (ResolvedMigration)migrations.get(i + 1);
         if ((new ResolvedMigrationComparator()).compare(current, next) == 0) {
            if (current.getVersion() != null) {
               throw new FlywayException(String.format("Found more than one migration with version %s\nOffenders:\n-> %s (%s)\n-> %s (%s)", current.getVersion(), current.getPhysicalLocation(), current.getType(), next.getPhysicalLocation(), next.getType()));
            }

            throw new FlywayException(String.format("Found more than one repeatable migration with description %s\nOffenders:\n-> %s (%s)\n-> %s (%s)", current.getDescription(), current.getPhysicalLocation(), current.getType(), next.getPhysicalLocation(), next.getType()));
         }
      }

   }
}
