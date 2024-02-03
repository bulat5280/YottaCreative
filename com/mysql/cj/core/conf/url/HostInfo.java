package com.mysql.cj.core.conf.url;

import com.mysql.cj.api.conf.DatabaseUrlContainer;
import com.mysql.cj.core.util.StringUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class HostInfo implements DatabaseUrlContainer {
   private static final String HOST_PORT_SEPARATOR = ":";
   private final DatabaseUrlContainer originalUrl;
   private final String host;
   private final int port;
   private final String user;
   private final String password;
   private final Map<String, String> hostProperties;

   public HostInfo() {
      this((DatabaseUrlContainer)null, (String)null, -1, (String)null, (String)null);
   }

   public HostInfo(DatabaseUrlContainer url, String host, int port, String user, String password) {
      this(url, host, port, user, password, (Map)null);
   }

   public HostInfo(DatabaseUrlContainer url, String host, int port, String user, String password, Map<String, String> properties) {
      this.hostProperties = new HashMap();
      this.originalUrl = url;
      this.host = host;
      this.port = port;
      this.user = user;
      this.password = password;
      if (properties != null) {
         this.hostProperties.putAll(properties);
      }

   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public String getHostPortPair() {
      return this.host + ":" + this.port;
   }

   public String getUser() {
      return this.user;
   }

   public String getPassword() {
      return this.password;
   }

   public Map<String, String> getHostProperties() {
      return Collections.unmodifiableMap(this.hostProperties);
   }

   public String getProperty(String key) {
      return (String)this.hostProperties.get(key);
   }

   public String getDatabase() {
      String database = (String)this.hostProperties.get("DBNAME");
      return StringUtils.isNullOrEmpty(database) ? "" : database;
   }

   public Properties exposeAsProperties() {
      Properties props = new Properties();
      this.hostProperties.entrySet().stream().forEach((e) -> {
         props.setProperty((String)e.getKey(), e.getValue() == null ? "" : (String)e.getValue());
      });
      props.setProperty("HOST", this.getHost());
      props.setProperty("PORT", String.valueOf(this.getPort()));
      props.setProperty("user", this.getUser());
      props.setProperty("password", this.getPassword());
      return props;
   }

   public String getDatabaseUrl() {
      return this.originalUrl != null ? this.originalUrl.getDatabaseUrl() : "";
   }

   public String toString() {
      StringBuilder asStr = new StringBuilder(super.toString());
      asStr.append(String.format(" :: {host: \"%s\", port: %d, user: %s, password: %s, hostProperties: %s}", this.host, this.port, this.user, this.password, this.hostProperties));
      return asStr.toString();
   }
}
