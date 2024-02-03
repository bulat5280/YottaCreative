package com.mysql.cj.core.conf.url;

import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.util.StringUtils;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class MysqlxConnectionUrl extends ConnectionUrl {
   private static final int DEFAULT_PORT = 33060;

   protected MysqlxConnectionUrl(ConnectionUrlParser connStrParser, Properties info) {
      super(connStrParser, info);
      this.type = ConnectionUrl.Type.MYSQLX_SESSION;
      boolean first = true;
      String user = null;
      String password = null;
      boolean hasPriority = false;
      Iterator var7 = this.hosts.iterator();

      while(var7.hasNext()) {
         HostInfo hi = (HostInfo)var7.next();
         if (first) {
            first = false;
            user = hi.getUser();
            password = hi.getPassword();
            hasPriority = hi.getHostProperties().containsKey("PRIORITY");
         } else {
            if (!user.equals(hi.getUser()) || !password.equals(hi.getPassword())) {
               throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.14", new Object[]{ConnectionUrl.Type.MYSQLX_SESSION.getProtocol()}));
            }

            if (hasPriority ^ hi.getHostProperties().containsKey("PRIORITY")) {
               throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.15", new Object[]{ConnectionUrl.Type.MYSQLX_SESSION.getProtocol()}));
            }
         }

         if (hasPriority) {
            try {
               int priority = Integer.parseInt(hi.getProperty("PRIORITY"));
               if (priority < 0 || priority > 100) {
                  throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.16", new Object[]{ConnectionUrl.Type.MYSQLX_SESSION.getProtocol()}));
               }
            } catch (NumberFormatException var10) {
               throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.16", new Object[]{ConnectionUrl.Type.MYSQLX_SESSION.getProtocol()}));
            }
         }
      }

      if (hasPriority) {
         this.hosts.sort(Comparator.comparing((hix) -> {
            return Integer.parseInt((String)hix.getHostProperties().get("PRIORITY"));
         }).reversed());
      }

   }

   protected void processColdFusionAutoConfiguration() {
   }

   protected Map<String, String> preprocessPerTypeHostProperties(Map<String, String> hostProps) {
      if (hostProps.containsKey("ADDRESS")) {
         String address = (String)hostProps.get("ADDRESS");
         ConnectionUrlParser.Pair<String, Integer> hostPortPair = ConnectionUrlParser.parseHostPortPair(address);
         String host = StringUtils.safeTrim((String)hostPortPair.left);
         Integer port = (Integer)hostPortPair.right;
         if (!StringUtils.isNullOrEmpty(host) && !hostProps.containsKey("HOST")) {
            hostProps.put("HOST", host);
         }

         if (port != -1 && !hostProps.containsKey("PORT")) {
            hostProps.put("PORT", port.toString());
         }
      }

      return hostProps;
   }

   public int getDefaultPort() {
      return 33060;
   }

   protected void fixProtocolDependencies(Map<String, String> hostProps) {
   }
}
