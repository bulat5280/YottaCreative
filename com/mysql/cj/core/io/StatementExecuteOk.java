package com.mysql.cj.core.io;

import com.mysql.cj.api.x.Warning;
import java.util.List;

public class StatementExecuteOk {
   private long rowsAffected;
   private Long lastInsertId;
   private List<Warning> warnings;

   public StatementExecuteOk(long rowsAffected, Long lastInsertId, List<Warning> warnings) {
      this.rowsAffected = rowsAffected;
      this.lastInsertId = lastInsertId;
      this.warnings = warnings;
   }

   public long getRowsAffected() {
      return this.rowsAffected;
   }

   public Long getLastInsertId() {
      return this.lastInsertId;
   }

   public List<Warning> getWarnings() {
      return this.warnings;
   }
}
