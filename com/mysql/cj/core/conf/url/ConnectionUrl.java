package com.mysql.cj.core.conf.url;

import com.mysql.cj.api.conf.ConnectionPropertiesTransform;
import com.mysql.cj.api.conf.DatabaseUrlContainer;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.InvalidConnectionAttributeException;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.io.NamedPipeSocketFactory;
import com.mysql.cj.core.util.LRUCache;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.core.util.Util;
import com.mysql.cj.jdbc.Driver;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ConnectionUrl implements DatabaseUrlContainer {
   private static final String DEFAULT_HOST = "localhost";
   private static final int DEFAULT_PORT = 3306;
   protected ConnectionUrl.Type type;
   protected String originalConnStr;
   protected String originalDatabase;
   protected List<HostInfo> hosts = new ArrayList();
   protected Map<String, String> properties = new HashMap();
   ConnectionPropertiesTransform propertiesTransformer;
   private static final LRUCache connectionUrlCache = new LRUCache(100);
   private static final ReadWriteLock rwLock = new ReentrantReadWriteLock();

   public static ConnectionUrl getConnectionUrlInstance(String connString, Properties info) {
      if (connString == null) {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.0"));
      } else {
         String connStringCacheKey = buildConnectionStringCacheKey(connString, info);
         rwLock.readLock().lock();
         ConnectionUrl connectionString = (ConnectionUrl)connectionUrlCache.get(connStringCacheKey);
         if (connectionString == null) {
            rwLock.readLock().unlock();
            rwLock.writeLock().lock();

            try {
               connectionString = (ConnectionUrl)connectionUrlCache.get(connStringCacheKey);
               if (connectionString == null) {
                  ConnectionUrlParser connStrParser = ConnectionUrlParser.parseConnectionString(connString);

                  try {
                     ConnectionUrl.Type.fromValue(connStrParser.getScheme(), -1);
                  } catch (WrongArgumentException var10) {
                     ConnectionUrl var6 = new ConnectionUrl(connString) {
                     };
                     return var6;
                  }

                  switch(ConnectionUrl.Type.fromValue(connStrParser.getScheme(), connStrParser.getHosts().size())) {
                  case SINGLE_CONNECTION:
                     connectionString = new SingleConnectionUrl(connStrParser, info);
                     break;
                  case FAILOVER_CONNECTION:
                     connectionString = new FailoverConnectionUrl(connStrParser, info);
                     break;
                  case LOADBALANCE_CONNECTION:
                     connectionString = new LoadbalanceConnectionUrl(connStrParser, info);
                     break;
                  case REPLICATION_CONNECTION:
                     connectionString = new ReplicationConnectionUrl(connStrParser, info);
                     break;
                  case MYSQLX_SESSION:
                     connectionString = new MysqlxConnectionUrl(connStrParser, info);
                     break;
                  default:
                     ConnectionUrl var5 = new ConnectionUrl(connString) {
                     };
                     return var5;
                  }

                  connectionUrlCache.put(connStringCacheKey, connectionString);
               }

               rwLock.readLock().lock();
            } finally {
               rwLock.writeLock().unlock();
            }
         }

         rwLock.readLock().unlock();
         return (ConnectionUrl)connectionString;
      }
   }

   private static String buildConnectionStringCacheKey(String connString, Properties info) {
      StringBuilder sbKey = new StringBuilder(connString);
      sbKey.append("??");
      sbKey.append(info == null ? null : (String)info.stringPropertyNames().stream().map((k) -> {
         return k + "=" + info.getProperty(k);
      }).collect(Collectors.joining(", ", "{", "}")));
      return sbKey.toString();
   }

   public static boolean acceptsUrl(String connString) {
      if (connString == null) {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.0"));
      } else {
         try {
            ConnectionUrlParser connStringParser = ConnectionUrlParser.parseConnectionString(connString);
            ConnectionUrl.Type.fromValue(connStringParser.getScheme(), -1);
            return true;
         } catch (Throwable var2) {
            return false;
         }
      }
   }

   protected ConnectionUrl() {
   }

   public ConnectionUrl(String origUrl) {
      this.originalConnStr = origUrl;
   }

   protected ConnectionUrl(ConnectionUrlParser connStrParser, Properties info) {
      this.originalConnStr = connStrParser.getDatabaseUrl();
      this.originalDatabase = connStrParser.getPath() == null ? "" : connStrParser.getPath();
      this.collectProperties(connStrParser, info);
      this.collectHostsInfo(connStrParser);
   }

   protected void collectProperties(ConnectionUrlParser connStrParser, Properties info) {
      this.properties.putAll(connStrParser.getProperties());
      if (info != null) {
         info.stringPropertyNames().stream().forEach((k) -> {
            String var10000 = (String)this.properties.put(k, info.getProperty(k));
         });
      }

      this.processColdFusionAutoConfiguration();
      this.setupPropertiesTransformer();
      this.expandPropertiesFromConfigFiles(this.properties);
      this.injectPerTypeProperties(this.properties);
   }

   protected void processColdFusionAutoConfiguration() {
      if (Util.isColdFusion()) {
         String autoConfigCf = (String)this.properties.get("autoConfigureForColdFusion");
         if (autoConfigCf == null || autoConfigCf.equalsIgnoreCase("TRUE") || autoConfigCf.equalsIgnoreCase("YES")) {
            String currentConfigFiles = (String)this.properties.get("useConfigs");
            StringBuilder newConfigFiles = new StringBuilder();
            if (currentConfigFiles != null) {
               newConfigFiles.append(currentConfigFiles).append(",");
            }

            newConfigFiles.append("coldFusion");
            this.properties.put("useConfigs", newConfigFiles.toString());
         }
      }

   }

   protected void setupPropertiesTransformer() {
      String propertiesTransformClassName = (String)this.properties.get("propertiesTransform");
      if (!StringUtils.isNullOrEmpty(propertiesTransformClassName)) {
         try {
            this.propertiesTransformer = (ConnectionPropertiesTransform)Class.forName(propertiesTransformClassName).newInstance();
         } catch (IllegalAccessException | ClassNotFoundException | CJException | InstantiationException var3) {
            throw (InvalidConnectionAttributeException)ExceptionFactory.createException((Class)InvalidConnectionAttributeException.class, (String)Messages.getString("ConnectionString.9", new Object[]{propertiesTransformClassName, var3.toString()}), (Throwable)var3);
         }
      }

   }

   protected void expandPropertiesFromConfigFiles(Map<String, String> props) {
      String configFiles = (String)props.get("useConfigs");
      if (!StringUtils.isNullOrEmpty(configFiles)) {
         Properties configProps = getPropertiesFromConfigFiles(configFiles);
         configProps.stringPropertyNames().stream().filter((k) -> {
            return !props.containsKey(k);
         }).forEach((k) -> {
            String var10000 = (String)props.put(k, configProps.getProperty(k));
         });
      }

   }

   public static Properties getPropertiesFromConfigFiles(String configFiles) {
      Properties configProps = new Properties();
      String[] var2 = configFiles.split(",");
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String configFile = var2[var4];

         try {
            InputStream configAsStream = Driver.class.getResourceAsStream("../configurations/" + configFile + ".properties");
            Throwable var7 = null;

            try {
               if (configAsStream == null) {
                  throw (InvalidConnectionAttributeException)ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.10", new Object[]{configFile}));
               }

               configProps.load(configAsStream);
            } catch (Throwable var17) {
               var7 = var17;
               throw var17;
            } finally {
               if (configAsStream != null) {
                  if (var7 != null) {
                     try {
                        configAsStream.close();
                     } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                     }
                  } else {
                     configAsStream.close();
                  }
               }

            }
         } catch (IOException var19) {
            throw (InvalidConnectionAttributeException)ExceptionFactory.createException((Class)InvalidConnectionAttributeException.class, (String)Messages.getString("ConnectionString.11", new Object[]{configFile}), (Throwable)var19);
         }
      }

      return configProps;
   }

   protected void injectPerTypeProperties(Map<String, String> props) {
   }

   protected void collectHostsInfo(ConnectionUrlParser connStrParser) {
      Stream var10000 = connStrParser.getHosts().stream().map(this::fixHostInfo);
      List var10001 = this.hosts;
      var10000.forEach(var10001::add);
   }

   protected HostInfo fixHostInfo(HostInfo hi) {
      Map<String, String> hostProps = new TreeMap(String.CASE_INSENSITIVE_ORDER);
      hostProps.putAll(this.properties);
      hostProps.putAll(hi.getHostProperties());
      hostProps.put("DBNAME", this.getDatabase());
      Map<String, String> hostProps = this.preprocessPerTypeHostProperties(hostProps);
      String host = (String)hostProps.remove("HOST");
      if (!StringUtils.isNullOrEmpty(hi.getHost())) {
         host = hi.getHost();
      } else if (StringUtils.isNullOrEmpty(host)) {
         host = this.getDefaultHost();
      }

      String portAsString = (String)hostProps.remove("PORT");
      int port = hi.getPort();
      if (port == -1 && !StringUtils.isNullOrEmpty(portAsString)) {
         try {
            port = Integer.valueOf(portAsString);
         } catch (NumberFormatException var8) {
            throw (WrongArgumentException)ExceptionFactory.createException((Class)WrongArgumentException.class, (String)Messages.getString("ConnectionString.7", new Object[]{hostProps.get("PORT")}), (Throwable)var8);
         }
      }

      if (port == -1) {
         port = this.getDefaultPort();
      }

      String user = (String)hostProps.remove("user");
      if (!StringUtils.isNullOrEmpty(hi.getUser())) {
         user = hi.getUser();
      } else if (StringUtils.isNullOrEmpty(user)) {
         user = this.getDefaultUser();
      }

      String password = (String)hostProps.remove("password");
      if (!StringUtils.isNullOrEmpty(hi.getPassword())) {
         password = hi.getPassword();
      } else if (StringUtils.isNullOrEmpty(password)) {
         password = this.getDefaultPassword();
      }

      this.expandPropertiesFromConfigFiles(hostProps);
      this.fixKeysCase(hostProps);
      this.fixProtocolDependencies(hostProps);
      return this.buildHostInfo(host, port, user, password, hostProps);
   }

   protected Map<String, String> preprocessPerTypeHostProperties(Map<String, String> hostProps) {
      return hostProps;
   }

   public String getDefaultHost() {
      return "localhost";
   }

   public int getDefaultPort() {
      return 3306;
   }

   public String getDefaultUser() {
      String user = (String)this.properties.get("user");
      return StringUtils.isNullOrEmpty(user) ? "" : user;
   }

   public String getDefaultPassword() {
      String password = (String)this.properties.get("password");
      return StringUtils.isNullOrEmpty(password) ? "" : password;
   }

   protected void fixKeysCase(Map<String, String> hostProps) {
      Iterator var2 = Arrays.asList("PROTOCOL", "PATH", "TYPE", "ADDRESS", "PRIORITY").iterator();

      while(var2.hasNext()) {
         String key = (String)var2.next();
         if (hostProps.containsKey(key)) {
            hostProps.put(key, hostProps.remove(key));
         }
      }

   }

   protected void fixProtocolDependencies(Map<String, String> hostProps) {
      String protocol = (String)hostProps.get("PROTOCOL");
      if (!StringUtils.isNullOrEmpty(protocol) && protocol.equalsIgnoreCase("PIPE")) {
         if (!hostProps.containsKey("socketFactory")) {
            hostProps.put("socketFactory", NamedPipeSocketFactory.class.getName());
         }

         if (hostProps.containsKey("PATH") && !hostProps.containsKey("namedPipePath")) {
            hostProps.put("namedPipePath", hostProps.get("PATH"));
         }
      }

   }

   public ConnectionUrl.Type getType() {
      return this.type;
   }

   public String getDatabaseUrl() {
      return this.originalConnStr;
   }

   public String getDatabase() {
      return this.properties.containsKey("DBNAME") ? (String)this.properties.get("DBNAME") : this.originalDatabase;
   }

   public int hostsCount() {
      return this.hosts.size();
   }

   public HostInfo getMainHost() {
      return this.hosts.isEmpty() ? null : (HostInfo)this.hosts.get(0);
   }

   public List<HostInfo> getHostsList() {
      return Collections.unmodifiableList(this.hosts);
   }

   public HostInfo getHostOrSpawnIsolated(String hostPortPair) {
      return this.getHostOrSpawnIsolated(hostPortPair, this.hosts);
   }

   public HostInfo getHostOrSpawnIsolated(String hostPortPair, List<HostInfo> hostsList) {
      Iterator var3 = hostsList.iterator();

      HostInfo hi;
      do {
         if (!var3.hasNext()) {
            ConnectionUrlParser.Pair<String, Integer> hostAndPort = ConnectionUrlParser.parseHostPortPair(hostPortPair);
            String host = (String)hostAndPort.left;
            Integer port = (Integer)hostAndPort.right;
            String user = this.getDefaultUser();
            String password = this.getDefaultPassword();
            return this.buildHostInfo(host, port, user, password, this.properties);
         }

         hi = (HostInfo)var3.next();
      } while(!hostPortPair.equals(hi.getHostPortPair()));

      return hi;
   }

   private HostInfo buildHostInfo(String host, int port, String user, String password, Map<String, String> hostProps) {
      if (this.propertiesTransformer != null) {
         Properties props = new Properties();
         props.putAll((Map)hostProps);
         props.setProperty("HOST", host);
         props.setProperty("PORT", String.valueOf(port));
         props.setProperty("user", user);
         props.setProperty("password", password);
         Properties transformedProps = this.propertiesTransformer.transformProperties(props);
         host = transformedProps.getProperty("HOST");

         try {
            port = Integer.parseInt(transformedProps.getProperty("PORT"));
         } catch (NumberFormatException var10) {
            throw (WrongArgumentException)ExceptionFactory.createException((Class)WrongArgumentException.class, (String)Messages.getString("ConnectionString.8", new Object[]{"PORT", transformedProps.getProperty("PORT")}), (Throwable)var10);
         }

         user = transformedProps.getProperty("user");
         password = transformedProps.getProperty("password");
         List<String> surplusKeys = Arrays.asList("HOST", "PORT", "user", "password");
         Map<String, String> transformedHostProps = new TreeMap(String.CASE_INSENSITIVE_ORDER);
         transformedProps.stringPropertyNames().stream().filter((k) -> {
            return !surplusKeys.contains(k);
         }).forEach((k) -> {
            String var10000 = (String)transformedHostProps.put(k, transformedProps.getProperty(k));
         });
         hostProps = transformedHostProps;
      }

      return new HostInfo(this, host, port, user, password, (Map)hostProps);
   }

   public Map<String, String> getOriginalProperties() {
      return Collections.unmodifiableMap(this.properties);
   }

   public Properties getConnectionArgumentsAsProperties() {
      Properties props = new Properties();
      if (this.properties != null) {
         props.putAll(this.properties);
      }

      return this.propertiesTransformer != null ? this.propertiesTransformer.transformProperties(props) : props;
   }

   public String toString() {
      StringBuilder asStr = new StringBuilder(super.toString());
      asStr.append(String.format(" :: {type: \"%s\", hosts: %s, database: \"%s\", properties: %s, propertiesTransformer: %s}", this.type, this.hosts, this.originalDatabase, this.properties, this.propertiesTransformer));
      return asStr.toString();
   }

   public static enum Type {
      SINGLE_CONNECTION("jdbc:mysql:", ConnectionUrl.HostsCardinality.SINGLE),
      FAILOVER_CONNECTION("jdbc:mysql:", ConnectionUrl.HostsCardinality.MULTIPLE),
      LOADBALANCE_CONNECTION("jdbc:mysql:loadbalance:", ConnectionUrl.HostsCardinality.ONE_OR_MORE),
      REPLICATION_CONNECTION("jdbc:mysql:replication:", ConnectionUrl.HostsCardinality.ONE_OR_MORE),
      MYSQLX_SESSION("mysqlx:", ConnectionUrl.HostsCardinality.ONE_OR_MORE);

      private String protocol;
      private ConnectionUrl.HostsCardinality cardinality;

      private Type(String protocol, ConnectionUrl.HostsCardinality cardinality) {
         this.protocol = protocol;
         this.cardinality = cardinality;
      }

      public String getProtocol() {
         return this.protocol;
      }

      public ConnectionUrl.HostsCardinality getCardinality() {
         return this.cardinality;
      }

      public static ConnectionUrl.Type fromValue(String protocol, int n) {
         ConnectionUrl.Type[] var2 = values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ConnectionUrl.Type t = var2[var4];
            if (t.getProtocol().equalsIgnoreCase(protocol) && (n < 0 || t.getCardinality().assertSize(n))) {
               return t;
            }
         }

         if (n < 0) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.5", new Object[]{protocol}));
         } else {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.6", new Object[]{protocol, n}));
         }
      }
   }

   public static enum HostsCardinality {
      SINGLE {
         public boolean assertSize(int n) {
            return n == 1;
         }
      },
      MULTIPLE {
         public boolean assertSize(int n) {
            return n > 1;
         }
      },
      ONE_OR_MORE {
         public boolean assertSize(int n) {
            return n >= 1;
         }
      };

      private HostsCardinality() {
      }

      public abstract boolean assertSize(int var1);

      // $FF: synthetic method
      HostsCardinality(Object x2) {
         this();
      }
   }
}
