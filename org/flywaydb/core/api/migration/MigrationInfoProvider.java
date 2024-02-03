package org.flywaydb.core.api.migration;

import org.flywaydb.core.api.MigrationVersion;

public interface MigrationInfoProvider {
   MigrationVersion getVersion();

   String getDescription();

   boolean isUndo();
}
