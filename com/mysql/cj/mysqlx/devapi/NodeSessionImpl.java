package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.NodeSession;
import java.util.Properties;

public class NodeSessionImpl extends AbstractSession implements NodeSession {
   public NodeSessionImpl(Properties properties) {
      super(properties);
   }

   protected NodeSessionImpl() {
   }

   public SqlStatementImpl sql(String sql) {
      return new SqlStatementImpl(this, sql);
   }
}
