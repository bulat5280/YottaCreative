package org.flywaydb.core.internal.resolver;

import java.util.Comparator;
import org.flywaydb.core.api.resolver.ResolvedMigration;

public class ResolvedMigrationComparator implements Comparator<ResolvedMigration> {
   public int compare(ResolvedMigration o1, ResolvedMigration o2) {
      if (o1.getVersion() != null && o2.getVersion() != null) {
         int v = o1.getVersion().compareTo(o2.getVersion());
         return v;
      } else if (o1.getVersion() != null) {
         return Integer.MIN_VALUE;
      } else {
         return o2.getVersion() != null ? Integer.MAX_VALUE : o1.getDescription().compareTo(o2.getDescription());
      }
   }
}
