package com.mysql.cj.core.conf.url;

import java.util.Properties;

public class SingleConnectionUrl extends ConnectionUrl {
   protected SingleConnectionUrl(ConnectionUrlParser connStrParser, Properties info) {
      super(connStrParser, info);
      this.type = ConnectionUrl.Type.SINGLE_CONNECTION;
   }
}
