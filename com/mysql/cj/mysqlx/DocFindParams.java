package com.mysql.cj.mysqlx;

import com.mysql.cj.api.x.Expression;
import com.mysql.cj.mysqlx.protobuf.MysqlxCrud;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class DocFindParams extends FindParams {
   public DocFindParams(String schemaName, String collectionName) {
      super(schemaName, collectionName, false);
   }

   public DocFindParams(String schemaName, String collectionName, String criteriaString) {
      super(schemaName, collectionName, criteriaString, false);
   }

   public void setFields(Expression docProjection) {
      this.fields = Collections.singletonList(MysqlxCrud.Projection.newBuilder().setSource((new ExprParser(docProjection.getExpressionString(), false)).parse()).build());
   }

   public void setFields(String... projection) {
      this.fields = (new ExprParser((String)Arrays.stream(projection).collect(Collectors.joining(", ")), false)).parseDocumentProjection();
   }
}
