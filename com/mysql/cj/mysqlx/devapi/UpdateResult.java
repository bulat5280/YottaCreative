package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.Result;
import com.mysql.cj.api.x.Warning;
import com.mysql.cj.core.io.StatementExecuteOk;
import java.util.Iterator;
import java.util.List;

public class UpdateResult implements Result {
   private StatementExecuteOk ok;
   private List<String> lastDocIds;

   public UpdateResult(StatementExecuteOk ok, List<String> lastDocIds) {
      this.ok = ok;
      this.lastDocIds = lastDocIds;
   }

   public long getAffectedItemsCount() {
      return this.ok.getRowsAffected();
   }

   public Long getAutoIncrementValue() {
      return this.ok.getLastInsertId();
   }

   public List<String> getLastDocumentIds() {
      return this.lastDocIds;
   }

   public int getWarningsCount() {
      return this.ok.getWarnings().size();
   }

   public Iterator<Warning> getWarnings() {
      return this.ok.getWarnings().iterator();
   }
}
