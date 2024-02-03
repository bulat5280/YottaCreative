package org.flywaydb.core.api;

public interface MigrationInfoService {
   MigrationInfo[] all();

   MigrationInfo current();

   MigrationInfo[] pending();

   MigrationInfo[] applied();
}
