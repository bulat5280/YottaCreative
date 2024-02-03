package org.jooq.impl;

import java.sql.Connection;
import org.jooq.ConnectionProvider;

public class NoConnectionProvider implements ConnectionProvider {
   public final Connection acquire() {
      return null;
   }

   public final void release(Connection connection) {
   }
}
