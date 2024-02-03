package com.p6spy.engine.common;

import java.util.HashMap;
import java.util.Map;

public class PreparedStatementInformation extends StatementInformation implements Loggable {
   private final Map<Integer, Value> parameterValues = new HashMap();

   public PreparedStatementInformation(ConnectionInformation connectionInformation, String query) {
      super(connectionInformation);
      this.setStatementQuery(query);
   }

   public String getSqlWithValues() {
      StringBuilder sb = new StringBuilder();
      String statementQuery = this.getStatementQuery();
      int currentParameter = 0;

      for(int pos = 0; pos < statementQuery.length(); ++pos) {
         char character = statementQuery.charAt(pos);
         if (statementQuery.charAt(pos) == '?' && currentParameter <= this.parameterValues.size()) {
            Value value = (Value)this.parameterValues.get(currentParameter);
            sb.append(value != null ? value.toString() : (new Value()).toString());
            ++currentParameter;
         } else {
            sb.append(character);
         }
      }

      return sb.toString();
   }

   public void setParameterValue(int position, Object value) {
      this.parameterValues.put(position - 1, new Value(value));
   }

   protected Map<Integer, Value> getParameterValues() {
      return this.parameterValues;
   }
}
