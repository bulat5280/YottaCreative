package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.XSession;

public class VirtualNodeSession extends NodeSessionImpl {
   protected VirtualNodeSession(XSession xsession) {
      this.session = xsession.getMysqlxSession();
      this.defaultSchemaName = xsession.getDefaultSchemaName();
   }

   public void close() {
      this.session = null;
   }

   public boolean isOpen() {
      return this.session != null && this.session.isOpen();
   }
}
