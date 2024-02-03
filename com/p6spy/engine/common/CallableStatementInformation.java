package com.p6spy.engine.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CallableStatementInformation extends PreparedStatementInformation {
   private final Map<String, Value> namedParameterValues = new HashMap();

   public CallableStatementInformation(ConnectionInformation connectionInformation, String query) {
      super(connectionInformation, query);
   }

   public String getSqlWithValues() {
      if (this.namedParameterValues.size() == 0) {
         return super.getSqlWithValues();
      } else {
         StringBuilder result = new StringBuilder();
         String statementQuery = this.getStatementQuery();
         result.append(statementQuery);
         result.append(" ");
         StringBuilder parameters = new StringBuilder();
         Iterator var4 = this.getParameterValues().keySet().iterator();

         while(var4.hasNext()) {
            Integer position = (Integer)var4.next();
            this.appendParameter(parameters, position.toString(), (Value)this.getParameterValues().get(position));
         }

         var4 = this.namedParameterValues.keySet().iterator();

         while(var4.hasNext()) {
            String name = (String)var4.next();
            this.appendParameter(parameters, name, (Value)this.namedParameterValues.get(name));
         }

         result.append(parameters);
         return result.toString();
      }
   }

   private void appendParameter(StringBuilder parameters, String name, Value value) {
      if (parameters.length() > 0) {
         parameters.append(", ");
      }

      parameters.append(name);
      parameters.append(":");
      parameters.append(value != null ? value.toString() : (new Value()).toString());
   }

   public void setParameterValue(String name, Object value) {
      this.namedParameterValues.put(name, new Value(value));
   }
}
