package org.flywaydb.core.api.migration;

public interface MigrationChecksumProvider {
   Integer getChecksum();
}
