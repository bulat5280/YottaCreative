package com.mysql.cj.x;

import com.mysql.cj.api.x.NodeSession;
import com.mysql.cj.api.x.XSession;
import com.mysql.cj.api.x.XSessionFactory;
import com.mysql.cj.core.conf.url.ConnectionUrl;
import com.mysql.cj.core.conf.url.HostInfo;
import com.mysql.cj.core.exceptions.CJCommunicationsException;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.InvalidConnectionAttributeException;
import com.mysql.cj.mysqlx.devapi.NodeSessionImpl;
import com.mysql.cj.mysqlx.devapi.SessionImpl;
import java.util.Iterator;
import java.util.Properties;

public class MysqlxSessionFactory implements XSessionFactory {
   private ConnectionUrl parseUrl(String url) {
      ConnectionUrl connUrl = ConnectionUrl.getConnectionUrlInstance(url, (Properties)null);
      if (connUrl.getType() != ConnectionUrl.Type.MYSQLX_SESSION) {
         throw (InvalidConnectionAttributeException)ExceptionFactory.createException(InvalidConnectionAttributeException.class, "Initialization via URL failed for \"" + url + "\"");
      } else {
         return connUrl;
      }
   }

   public XSession getSession(String url) {
      CJCommunicationsException latestException = null;
      ConnectionUrl connUrl = this.parseUrl(url);
      Iterator var4 = connUrl.getHostsList().iterator();

      while(var4.hasNext()) {
         HostInfo hi = (HostInfo)var4.next();

         try {
            return new SessionImpl(hi.exposeAsProperties());
         } catch (CJCommunicationsException var7) {
            latestException = var7;
         }
      }

      if (latestException != null) {
         throw latestException;
      } else {
         return null;
      }
   }

   public XSession getSession(Properties properties) {
      return new SessionImpl(properties);
   }

   public NodeSession getNodeSession(String url) {
      ConnectionUrl connUrl = this.parseUrl(url);
      if (connUrl.getHostsList().size() > 1) {
         throw (InvalidConnectionAttributeException)ExceptionFactory.createException(InvalidConnectionAttributeException.class, "A NodeSession cannot be initialized with a multi-host URL.");
      } else {
         return new NodeSessionImpl(connUrl.getMainHost().exposeAsProperties());
      }
   }

   public NodeSession getNodeSession(Properties properties) {
      return new NodeSessionImpl(properties);
   }
}
