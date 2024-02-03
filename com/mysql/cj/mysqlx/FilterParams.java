package com.mysql.cj.mysqlx;

import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.mysqlx.protobuf.MysqlxCrud;
import com.mysql.cj.mysqlx.protobuf.MysqlxDatatypes;
import com.mysql.cj.mysqlx.protobuf.MysqlxExpr;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FilterParams {
   private MysqlxCrud.Collection collection;
   private Long limit;
   private Long offset;
   private List<MysqlxCrud.Order> order;
   private MysqlxExpr.Expr criteria;
   private MysqlxDatatypes.Scalar[] args;
   private Map<String, Integer> placeholderNameToPosition;
   protected boolean isRelational;

   public FilterParams(String schemaName, String collectionName, boolean isRelational) {
      this.collection = ExprUtil.buildCollection(schemaName, collectionName);
      this.isRelational = isRelational;
   }

   public FilterParams(String schemaName, String collectionName, String criteriaString, boolean isRelational) {
      this.collection = ExprUtil.buildCollection(schemaName, collectionName);
      this.isRelational = isRelational;
      this.setCriteria(criteriaString);
   }

   public Object getCollection() {
      return this.collection;
   }

   public Object getOrder() {
      return this.order;
   }

   public void setOrder(String... orderExpression) {
      this.order = (new ExprParser((String)Arrays.stream(orderExpression).collect(Collectors.joining(", ")), this.isRelational)).parseOrderSpec();
   }

   public Long getLimit() {
      return this.limit;
   }

   public void setLimit(Long limit) {
      this.limit = limit;
   }

   public Long getOffset() {
      return this.offset;
   }

   public void setOffset(Long offset) {
      this.offset = offset;
   }

   public Object getCriteria() {
      return this.criteria;
   }

   public void setCriteria(String criteriaString) {
      ExprParser parser = new ExprParser(criteriaString, this.isRelational);
      this.criteria = parser.parse();
      if (parser.getPositionalPlaceholderCount() > 0) {
         this.placeholderNameToPosition = parser.getPlaceholderNameToPositionMap();
         this.args = new MysqlxDatatypes.Scalar[parser.getPositionalPlaceholderCount()];
      }

   }

   public Object getArgs() {
      return this.args == null ? null : Arrays.asList(this.args);
   }

   public void addArg(String name, Object value) {
      if (this.args == null) {
         throw new WrongArgumentException("No placeholders");
      } else if (this.placeholderNameToPosition.get(name) == null) {
         throw new WrongArgumentException("Unknown placeholder: " + name);
      } else {
         this.args[(Integer)this.placeholderNameToPosition.get(name)] = ExprUtil.argObjectToScalar(value);
      }
   }

   public void verifyAllArgsBound() {
      if (this.args != null) {
         IntStream.range(0, this.args.length).filter((i) -> {
            return this.args[i] == null;
         }).mapToObj((i) -> {
            return (String)this.placeholderNameToPosition.entrySet().stream().filter((e) -> {
               return (Integer)e.getValue() == i;
            }).map(Entry::getKey).findFirst().get();
         }).forEach((name) -> {
            throw new WrongArgumentException("Placeholder '" + name + "' is not bound");
         });
      }

   }

   public void clearArgs() {
      if (this.args != null) {
         IntStream.range(0, this.args.length).forEach((i) -> {
            this.args[i] = null;
         });
      }

   }

   public boolean isRelational() {
      return this.isRelational;
   }
}
