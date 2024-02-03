package com.mysql.cj.mysqlx;

import com.mysql.cj.mysqlx.protobuf.MysqlxCrud;
import com.mysql.cj.mysqlx.protobuf.MysqlxExpr;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class FindParams extends FilterParams {
   private List<MysqlxExpr.Expr> grouping;
   private MysqlxExpr.Expr groupingCriteria;
   protected List<MysqlxCrud.Projection> fields;

   public FindParams(String schemaName, String collectionName, boolean isRelational) {
      super(schemaName, collectionName, isRelational);
   }

   public FindParams(String schemaName, String collectionName, String criteriaString, boolean isRelational) {
      super(schemaName, collectionName, criteriaString, isRelational);
   }

   public abstract void setFields(String... var1);

   public Object getFields() {
      return this.fields;
   }

   public void setGrouping(String... groupBy) {
      this.grouping = (new ExprParser((String)Arrays.stream(groupBy).collect(Collectors.joining(", ")), this.isRelational())).parseExprList();
   }

   public Object getGrouping() {
      return this.grouping;
   }

   public void setGroupingCriteria(String having) {
      this.groupingCriteria = (new ExprParser(having, this.isRelational())).parse();
   }

   public Object getGroupingCriteria() {
      return this.groupingCriteria;
   }
}
