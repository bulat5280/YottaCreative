package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.SqlResult;
import com.mysql.cj.api.x.SqlStatement;
import com.mysql.cj.core.exceptions.FeatureNotAvailableException;
import com.mysql.cj.mysqlx.ExprUtil;
import com.mysql.cj.mysqlx.protobuf.MysqlxDatatypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SqlStatementImpl implements SqlStatement {
   private NodeSessionImpl session;
   private String sql;
   private List<MysqlxDatatypes.Any> args = new ArrayList();

   public SqlStatementImpl(NodeSessionImpl session, String sql) {
      this.session = session;
      this.sql = sql;
   }

   public SqlResult execute() {
      return this.session.getMysqlxSession().executeSql(this.sql, this.args);
   }

   public CompletableFuture<SqlResult> executeAsync() {
      return this.session.getMysqlxSession().asyncExecuteSql(this.sql, this.args);
   }

   public SqlStatement clearBindings() {
      this.args.clear();
      return this;
   }

   public SqlStatement bind(List<Object> values) {
      values.stream().map(ExprUtil::argObjectToScalarAny).forEach((a) -> {
         this.args.add(a);
      });
      return this;
   }

   public SqlStatement bind(Map<String, Object> values) {
      throw new FeatureNotAvailableException("Cannot bind named parameters for SQL statements");
   }
}
