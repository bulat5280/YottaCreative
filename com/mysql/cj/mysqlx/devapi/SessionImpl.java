package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.NodeSession;
import com.mysql.cj.api.x.XSession;
import com.mysql.cj.core.exceptions.ConnectionIsClosedException;
import java.util.Properties;

public class SessionImpl extends AbstractSession implements XSession {
   public SessionImpl(Properties properties) {
      super(properties);
   }

   public NodeSession bindToDefaultShard() {
      if (!this.isOpen()) {
         throw new ConnectionIsClosedException("Can't bind NodeSession to closed XSession.");
      } else {
         return new VirtualNodeSession(this);
      }
   }
}
