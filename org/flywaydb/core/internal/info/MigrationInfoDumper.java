package org.flywaydb.core.internal.info;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.internal.util.AsciiTable;
import org.flywaydb.core.internal.util.DateUtils;

public class MigrationInfoDumper {
   private MigrationInfoDumper() {
   }

   public static String dumpToAsciiTable(MigrationInfo[] migrationInfos) {
      List<String> columns = Arrays.asList("Category", "Version", "Description", "Type", "Installed On", "State");
      List<List<String>> rows = new ArrayList();
      MigrationInfo[] var3 = migrationInfos;
      int var4 = migrationInfos.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         MigrationInfo migrationInfo = var3[var5];
         List<String> row = Arrays.asList(getCategory(migrationInfo), getVersionStr(migrationInfo), migrationInfo.getDescription(), migrationInfo.getType().name(), DateUtils.formatDateAsIsoString(migrationInfo.getInstalledOn()), migrationInfo.getState().getDisplayName());
         rows.add(row);
      }

      return (new AsciiTable(columns, rows, "", "No migrations found")).render();
   }

   private static String getCategory(MigrationInfo migrationInfo) {
      return migrationInfo.getVersion() == null ? "Repeatable" : "Versioned";
   }

   private static String getVersionStr(MigrationInfo migrationInfo) {
      return migrationInfo.getVersion() == null ? "" : migrationInfo.getVersion().toString();
   }
}
