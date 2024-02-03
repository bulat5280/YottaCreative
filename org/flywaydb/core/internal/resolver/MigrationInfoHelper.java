package org.flywaydb.core.internal.resolver;

import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.internal.util.Pair;
import org.flywaydb.core.internal.util.StringUtils;

public class MigrationInfoHelper {
   private MigrationInfoHelper() {
   }

   public static Pair<MigrationVersion, String> extractVersionAndDescription(String migrationName, String prefix, String separator, String[] suffixes, boolean repeatable) {
      String cleanMigrationName = cleanMigrationName(migrationName, prefix, suffixes);
      int descriptionPos = cleanMigrationName.indexOf(separator);
      if (descriptionPos < 0) {
         throw new FlywayException("Wrong migration name format: " + migrationName + "(It should look like this: " + prefix + (repeatable ? "" : "1.2") + separator + "Description" + suffixes[0] + ")");
      } else {
         String version = cleanMigrationName.substring(0, descriptionPos);
         String description = cleanMigrationName.substring(descriptionPos + separator.length()).replaceAll("_", " ");
         if (StringUtils.hasText(version)) {
            if (repeatable) {
               throw new FlywayException("Wrong repeatable migration name format: " + migrationName + "(It cannot contain a version and should look like this: " + prefix + separator + description + suffixes[0] + ")");
            } else {
               return Pair.of(MigrationVersion.fromVersion(version), description);
            }
         } else if (!repeatable) {
            throw new FlywayException("Wrong versioned migration name format: " + migrationName + "(It must contain a version and should look like this: " + prefix + "1.2" + separator + description + suffixes[0] + ")");
         } else {
            return Pair.of((Object)null, description);
         }
      }
   }

   private static String cleanMigrationName(String migrationName, String prefix, String[] suffixes) {
      String[] var3 = suffixes;
      int var4 = suffixes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String suffix = var3[var5];
         if (migrationName.endsWith(suffix)) {
            return migrationName.substring(StringUtils.hasLength(prefix) ? prefix.length() : 0, migrationName.length() - suffix.length());
         }
      }

      return migrationName;
   }
}
