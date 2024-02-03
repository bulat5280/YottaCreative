package org.flywaydb.core.api.callback;

import java.sql.Connection;
import org.flywaydb.core.api.MigrationInfo;

public interface FlywayCallback {
   void beforeClean(Connection var1);

   void afterClean(Connection var1);

   void beforeMigrate(Connection var1);

   void afterMigrate(Connection var1);

   void beforeUndo(Connection var1);

   void beforeEachUndo(Connection var1, MigrationInfo var2);

   void afterEachUndo(Connection var1, MigrationInfo var2);

   void afterUndo(Connection var1);

   void beforeEachMigrate(Connection var1, MigrationInfo var2);

   void afterEachMigrate(Connection var1, MigrationInfo var2);

   void beforeValidate(Connection var1);

   void afterValidate(Connection var1);

   void beforeBaseline(Connection var1);

   void afterBaseline(Connection var1);

   void beforeRepair(Connection var1);

   void afterRepair(Connection var1);

   void beforeInfo(Connection var1);

   void afterInfo(Connection var1);
}
