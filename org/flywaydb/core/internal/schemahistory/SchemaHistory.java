package org.flywaydb.core.internal.schemahistory;

import java.util.List;
import java.util.concurrent.Callable;
import org.flywaydb.core.api.MigrationType;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.resolver.ResolvedMigration;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.util.AbbreviationUtils;
import org.flywaydb.core.internal.util.StringUtils;

public abstract class SchemaHistory {
   public abstract <T> T lock(Callable<T> var1);

   public abstract boolean exists();

   public abstract void create();

   public abstract boolean hasAppliedMigrations();

   public abstract List<AppliedMigration> allAppliedMigrations();

   public final void addBaselineMarker(MigrationVersion baselineVersion, String baselineDescription) {
      this.addAppliedMigration(baselineVersion, baselineDescription, MigrationType.BASELINE, baselineDescription, (Integer)null, 0, true);
   }

   public abstract boolean hasBaselineMarker();

   public abstract AppliedMigration getBaselineMarker();

   public abstract void removeFailedMigrations();

   public abstract void addSchemasMarker(Schema[] var1);

   protected final void doAddSchemasMarker(Schema[] schemas) {
      this.addAppliedMigration((MigrationVersion)null, "<< Flyway Schema Creation >>", MigrationType.SCHEMA, StringUtils.arrayToCommaDelimitedString(schemas), (Integer)null, 0, true);
   }

   public abstract boolean hasSchemasMarker();

   public abstract void update(AppliedMigration var1, ResolvedMigration var2);

   public void clearCache() {
   }

   public final void addAppliedMigration(MigrationVersion version, String description, MigrationType type, String script, Integer checksum, int executionTime, boolean success) {
      this.doAddAppliedMigration(version, AbbreviationUtils.abbreviateDescription(description), type, AbbreviationUtils.abbreviateScript(script), checksum, executionTime, success);
   }

   protected abstract void doAddAppliedMigration(MigrationVersion var1, String var2, MigrationType var3, String var4, Integer var5, int var6, boolean var7);
}
