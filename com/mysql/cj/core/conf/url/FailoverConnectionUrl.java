package com.mysql.cj.core.conf.url;

import java.util.Map;
import java.util.Properties;

public class FailoverConnectionUrl extends ConnectionUrl {
   protected FailoverConnectionUrl(ConnectionUrlParser connStrParser, Properties info) {
      super(connStrParser, info);
      this.type = ConnectionUrl.Type.FAILOVER_CONNECTION;
   }

   protected void injectPerTypeProperties(Map<String, String> props) {
      props.put("useLocalSessionState", "true");
   }
}
