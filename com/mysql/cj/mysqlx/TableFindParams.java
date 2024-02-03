package com.mysql.cj.mysqlx;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TableFindParams extends FindParams {
   public TableFindParams(String schemaName, String collectionName) {
      super(schemaName, collectionName, true);
   }

   public TableFindParams(String schemaName, String collectionName, String criteriaString) {
      super(schemaName, collectionName, criteriaString, true);
   }

   public void setFields(String... projection) {
      this.fields = (new ExprParser((String)Arrays.stream(projection).collect(Collectors.joining(", ")), true)).parseTableSelectProjection();
   }
}
