package com.p6spy.engine.common;

import com.p6spy.engine.logging.Category;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ResultSetInformation implements Loggable {
   private final StatementInformation statementInformation;
   private String query;
   private final Map<String, Value> resultMap = new LinkedHashMap();
   private int currRow = -1;
   private int lastRowLogged = -1;

   public ResultSetInformation(StatementInformation statementInformation) {
      this.statementInformation = statementInformation;
      this.query = statementInformation.getStatementQuery();
   }

   public void generateLogMessage() {
      if (this.lastRowLogged != this.currRow) {
         P6LogQuery.log(Category.RESULTSET, this);
         this.resultMap.clear();
         this.lastRowLogged = this.currRow;
      }

   }

   public int getCurrRow() {
      return this.currRow;
   }

   public void incrementCurrRow() {
      ++this.currRow;
   }

   public void setColumnValue(String columnName, Object value) {
      this.resultMap.put(columnName, new Value(value));
   }

   public String getSql() {
      return this.query;
   }

   public String getSqlWithValues() {
      StringBuilder sb = new StringBuilder();
      Iterator var2 = this.resultMap.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<String, Value> entry = (Entry)var2.next();
         if (sb.length() > 0) {
            sb.append(", ");
         }

         sb.append((String)entry.getKey());
         sb.append(" = ");
         sb.append(entry.getValue() != null ? ((Value)entry.getValue()).toString() : (new Value()).toString());
      }

      return sb.toString();
   }

   public StatementInformation getStatementInformation() {
      return this.statementInformation;
   }

   public ConnectionInformation getConnectionInformation() {
      return this.statementInformation.getConnectionInformation();
   }
}
