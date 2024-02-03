package com.p6spy.engine.spy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.P6LogQuery;
import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.wrapper.ConnectionWrapper;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Wrapper;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import javax.sql.CommonDataSource;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

public class P6DataSource implements DataSource, ConnectionPoolDataSource, XADataSource, Referenceable, Serializable {
   protected CommonDataSource realDataSource;
   protected String rdsName;
   protected JdbcEventListenerFactory jdbcEventListenerFactory;

   public P6DataSource() {
   }

   public P6DataSource(DataSource delegate) {
      this.realDataSource = delegate;
   }

   public String getRealDataSource() {
      return this.rdsName;
   }

   public void setRealDataSource(String jndiName) {
      this.rdsName = jndiName;
   }

   protected synchronized void bindDataSource() throws SQLException {
      if (null == this.realDataSource) {
         P6SpyLoadableOptions options = P6SpyOptions.getActiveInstance();
         if (this.rdsName == null) {
            this.rdsName = options.getRealDataSource();
         }

         if (this.rdsName == null) {
            throw new SQLException("P6DataSource: no value for Real Data Source Name, cannot perform jndi lookup");
         } else {
            Hashtable<String, String> env = null;
            String factory;
            if ((factory = options.getJNDIContextFactory()) != null) {
               env = new Hashtable();
               env.put("java.naming.factory.initial", factory);
               String url = options.getJNDIContextProviderURL();
               if (url != null) {
                  env.put("java.naming.provider.url", url);
               }

               String custom = options.getJNDIContextCustom();
               if (custom != null) {
                  env.putAll(this.parseDelimitedString(custom));
               }
            }

            try {
               InitialContext ctx;
               if (env != null) {
                  ctx = new InitialContext(env);
               } else {
                  ctx = new InitialContext();
               }

               this.realDataSource = (CommonDataSource)ctx.lookup(this.rdsName);
            } catch (NamingException var6) {
               throw new SQLException("P6DataSource: naming exception during jndi lookup of Real Data Source Name of '" + this.rdsName + "'. " + var6.getMessage(), var6);
            }

            HashMap<String, String> props = this.parseDelimitedString(options.getRealDataSourceProperties());
            if (props != null) {
               this.setDataSourceProperties(props);
            }

            if (this.realDataSource == null) {
               throw new SQLException("P6DataSource: jndi lookup for Real Data Source Name of '" + this.rdsName + "' failed, cannot bind named data source.");
            }
         }
      }
   }

   private void setDataSourceProperties(HashMap<String, String> props) throws SQLException {
      HashMap<String, String> matchedProps = new HashMap();
      Class<?> klass = this.realDataSource.getClass();
      Method[] var4 = klass.getMethods();
      int var5 = var4.length;

      label56:
      for(int var6 = 0; var6 < var5; ++var6) {
         Method method = var4[var6];
         String methodName = method.getName();
         if (methodName.startsWith("set")) {
            String propertyName = methodName.substring(3).toLowerCase();
            Iterator var10 = props.keySet().iterator();

            while(true) {
               String key;
               do {
                  if (!var10.hasNext()) {
                     continue label56;
                  }

                  key = (String)var10.next();
               } while(!key.toLowerCase().equals(propertyName));

               try {
                  String value = (String)props.get(key);
                  Class<?>[] types = method.getParameterTypes();
                  if (types[0].getName().equals(value.getClass().getName())) {
                     String[] args = new String[]{value};
                     P6LogQuery.debug("calling " + methodName + " on DataSource " + this.rdsName + " with " + value);
                     method.invoke(this.realDataSource, args);
                     matchedProps.put(key, value);
                  } else if (types[0].isPrimitive() && "int".equals(types[0].getName())) {
                     Integer[] args = new Integer[]{Integer.valueOf(value)};
                     P6LogQuery.debug("calling " + methodName + " on DataSource " + this.rdsName + " with " + value);
                     method.invoke(this.realDataSource, args);
                     matchedProps.put(key, value);
                  } else {
                     P6LogQuery.debug("method " + methodName + " on DataSource " + this.rdsName + " matches property " + propertyName + " but expects unsupported type " + types[0].getName());
                     matchedProps.put(key, value);
                  }
               } catch (IllegalAccessException var15) {
                  throw new SQLException("spy.properties file includes datasource property " + key + " for datasource " + this.rdsName + " but access is denied to method " + methodName, var15);
               } catch (InvocationTargetException var16) {
                  throw new SQLException("spy.properties file includes datasource property " + key + " for datasource " + this.rdsName + " but call method " + methodName + " fails", var16);
               }
            }
         }
      }

      Iterator var17 = props.keySet().iterator();

      while(var17.hasNext()) {
         String key = (String)var17.next();
         if (!matchedProps.containsKey(key)) {
            P6LogQuery.debug("spy.properties file includes datasource property " + key + " for datasource " + this.rdsName + " but class " + klass.getName() + " has no method by that name");
         }
      }

   }

   private HashMap<String, String> parseDelimitedString(String delimitedString) {
      if (delimitedString == null) {
         return null;
      } else {
         HashMap<String, String> result = new HashMap();
         StringTokenizer st = new StringTokenizer(delimitedString, ",", false);

         while(st.hasMoreElements()) {
            String pair = st.nextToken();
            StringTokenizer pst = new StringTokenizer(pair, ";", false);
            if (pst.hasMoreElements()) {
               String name = pst.nextToken();
               if (pst.hasMoreElements()) {
                  String value = pst.nextToken();
                  result.put(name, value);
               }
            }
         }

         return result;
      }
   }

   public Reference getReference() throws NamingException {
      Reference reference = new Reference(this.getClass().getName(), P6DataSourceFactory.class.getName(), (String)null);
      reference.add(new StringRefAddr("dataSourceName", this.getRealDataSource()));
      return reference;
   }

   public int getLoginTimeout() throws SQLException {
      if (this.realDataSource == null) {
         this.bindDataSource();
      }

      return this.realDataSource.getLoginTimeout();
   }

   public void setLoginTimeout(int inVar) throws SQLException {
      if (this.realDataSource == null) {
         this.bindDataSource();
      }

      this.realDataSource.setLoginTimeout(inVar);
   }

   public PrintWriter getLogWriter() throws SQLException {
      if (this.realDataSource == null) {
         this.bindDataSource();
      }

      return this.realDataSource.getLogWriter();
   }

   public void setLogWriter(PrintWriter inVar) throws SQLException {
      this.realDataSource.setLogWriter(inVar);
   }

   public Connection getConnection() throws SQLException {
      if (this.realDataSource == null) {
         this.bindDataSource();
      }

      long start = System.nanoTime();
      if (this.jdbcEventListenerFactory == null) {
         this.jdbcEventListenerFactory = JdbcEventListenerFactoryLoader.load();
      }

      JdbcEventListener jdbcEventListener = this.jdbcEventListenerFactory.createJdbcEventListener();
      ConnectionInformation connectionInformation = ConnectionInformation.fromDataSource(this.realDataSource);
      jdbcEventListener.onBeforeGetConnection(connectionInformation);

      Connection conn;
      try {
         conn = ((DataSource)this.realDataSource).getConnection();
         connectionInformation.setConnection(conn);
         connectionInformation.setTimeToGetConnectionNs(System.nanoTime() - start);
         jdbcEventListener.onAfterGetConnection(connectionInformation, (SQLException)null);
      } catch (SQLException var7) {
         connectionInformation.setTimeToGetConnectionNs(System.nanoTime() - start);
         jdbcEventListener.onAfterGetConnection(connectionInformation, var7);
         throw var7;
      }

      return ConnectionWrapper.wrap(conn, jdbcEventListener, connectionInformation);
   }

   public Connection getConnection(String username, String password) throws SQLException {
      if (this.realDataSource == null) {
         this.bindDataSource();
      }

      long start = System.nanoTime();
      if (this.jdbcEventListenerFactory == null) {
         this.jdbcEventListenerFactory = JdbcEventListenerFactoryLoader.load();
      }

      JdbcEventListener jdbcEventListener = this.jdbcEventListenerFactory.createJdbcEventListener();
      ConnectionInformation connectionInformation = ConnectionInformation.fromDataSource(this.realDataSource);
      jdbcEventListener.onBeforeGetConnection(connectionInformation);

      Connection conn;
      try {
         conn = ((DataSource)this.realDataSource).getConnection(username, password);
         connectionInformation.setConnection(conn);
         connectionInformation.setTimeToGetConnectionNs(System.nanoTime() - start);
         jdbcEventListener.onAfterGetConnection(connectionInformation, (SQLException)null);
      } catch (SQLException var9) {
         connectionInformation.setTimeToGetConnectionNs(System.nanoTime() - start);
         jdbcEventListener.onAfterGetConnection(connectionInformation, var9);
         throw var9;
      }

      return ConnectionWrapper.wrap(conn, jdbcEventListener, connectionInformation);
   }

   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      return ((Wrapper)this.realDataSource).isWrapperFor(iface);
   }

   public <T> T unwrap(Class<T> iface) throws SQLException {
      return ((DataSource)this.realDataSource).unwrap(iface);
   }

   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      return this.realDataSource.getParentLogger();
   }

   public PooledConnection getPooledConnection() throws SQLException {
      if (this.jdbcEventListenerFactory == null) {
         this.jdbcEventListenerFactory = JdbcEventListenerFactoryLoader.load();
      }

      return new P6XAConnection(((ConnectionPoolDataSource)this.castRealDS(ConnectionPoolDataSource.class)).getPooledConnection(), this.jdbcEventListenerFactory);
   }

   public PooledConnection getPooledConnection(String user, String password) throws SQLException {
      if (this.jdbcEventListenerFactory == null) {
         this.jdbcEventListenerFactory = JdbcEventListenerFactoryLoader.load();
      }

      return new P6XAConnection(((ConnectionPoolDataSource)this.castRealDS(ConnectionPoolDataSource.class)).getPooledConnection(user, password), this.jdbcEventListenerFactory);
   }

   public XAConnection getXAConnection() throws SQLException {
      if (this.jdbcEventListenerFactory == null) {
         this.jdbcEventListenerFactory = JdbcEventListenerFactoryLoader.load();
      }

      return new P6XAConnection(((XADataSource)this.castRealDS(XADataSource.class)).getXAConnection(), this.jdbcEventListenerFactory);
   }

   public XAConnection getXAConnection(String user, String password) throws SQLException {
      if (this.jdbcEventListenerFactory == null) {
         this.jdbcEventListenerFactory = JdbcEventListenerFactoryLoader.load();
      }

      return new P6XAConnection(((XADataSource)this.castRealDS(XADataSource.class)).getXAConnection(user, password), this.jdbcEventListenerFactory);
   }

   <T> T castRealDS(Class<T> iface) throws SQLException {
      if (this.realDataSource == null) {
         this.bindDataSource();
      }

      if (iface.isInstance(this.realDataSource)) {
         return this.realDataSource;
      } else if (this.isWrapperFor(iface)) {
         return this.unwrap(iface);
      } else {
         throw new IllegalStateException("realdatasource type not supported: " + this.realDataSource);
      }
   }

   public void setJdbcEventListenerFactory(JdbcEventListenerFactory jdbcEventListenerFactory) {
      this.jdbcEventListenerFactory = jdbcEventListenerFactory;
   }
}
