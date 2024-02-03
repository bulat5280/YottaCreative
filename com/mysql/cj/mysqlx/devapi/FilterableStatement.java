package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.Statement;
import com.mysql.cj.mysqlx.FilterParams;

public abstract class FilterableStatement<STMT_T, RES_T> implements Statement<STMT_T, RES_T> {
   protected FilterParams filterParams;

   public FilterableStatement(FilterParams filterParams) {
      this.filterParams = filterParams;
   }

   public FilterableStatement(String schemaName, String collectionName, boolean isRelational) {
      this.filterParams = new FilterParams(schemaName, collectionName, isRelational);
   }

   public STMT_T where(String searchCondition) {
      this.filterParams.setCriteria(searchCondition);
      return this;
   }

   public STMT_T sort(String... sortFields) {
      return this.orderBy(sortFields);
   }

   public STMT_T orderBy(String... sortFields) {
      this.filterParams.setOrder(sortFields);
      return this;
   }

   public STMT_T limit(long numberOfRows) {
      this.filterParams.setLimit(numberOfRows);
      return this;
   }

   public STMT_T skip(long limitOffset) {
      return this.offset(limitOffset);
   }

   public STMT_T offset(long limitOffset) {
      this.filterParams.setOffset(limitOffset);
      return this;
   }

   public boolean isRelational() {
      return this.filterParams.isRelational();
   }

   public STMT_T clearBindings() {
      this.filterParams.clearArgs();
      return this;
   }

   public STMT_T bind(String argName, Object value) {
      this.filterParams.addArg(argName, value);
      return this;
   }
}
