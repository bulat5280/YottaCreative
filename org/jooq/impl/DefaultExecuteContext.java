package org.jooq.impl;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.DDLQuery;
import org.jooq.Delete;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteType;
import org.jooq.Insert;
import org.jooq.Merge;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.Routine;
import org.jooq.SQLDialect;
import org.jooq.Update;
import org.jooq.conf.Settings;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.tools.reflect.Reflect;
import org.jooq.tools.reflect.ReflectException;

class DefaultExecuteContext implements ExecuteContext {
   private static final JooqLogger log = JooqLogger.getLogger(DefaultExecuteContext.class);
   private final Configuration configuration;
   private final Map<Object, Object> data;
   private final Query query;
   private final Routine<?> routine;
   private String sql;
   private final Query[] batchQueries;
   private final String[] batchSQL;
   private final int[] batchRows;
   private transient ConnectionProvider connectionProvider;
   private transient Connection connection;
   private transient PreparedStatement statement;
   private transient ResultSet resultSet;
   private transient Record record;
   private transient Result<?> result;
   private transient int rows;
   private transient RuntimeException exception;
   private transient SQLException sqlException;
   private transient SQLWarning sqlWarning;
   private static final ThreadLocal<List<Blob>> BLOBS = new ThreadLocal();
   private static final ThreadLocal<List<Clob>> CLOBS = new ThreadLocal();
   private static final ThreadLocal<List<SQLXML>> SQLXMLS = new ThreadLocal();
   private static final ThreadLocal<List<java.sql.Array>> ARRAYS = new ThreadLocal();
   private static final ThreadLocal<Configuration> LOCAL_CONFIGURATION = new ThreadLocal();
   private static final ThreadLocal<Map<Object, Object>> LOCAL_DATA = new ThreadLocal();
   private static final ThreadLocal<Connection> LOCAL_CONNECTION = new ThreadLocal();
   private static int maxUnwrappedConnections = 256;

   static final void clean() {
      List<Blob> blobs = (List)BLOBS.get();
      List<Clob> clobs = (List)CLOBS.get();
      List<SQLXML> xmls = (List)SQLXMLS.get();
      List<java.sql.Array> arrays = (List)ARRAYS.get();
      Iterator var4;
      if (blobs != null) {
         var4 = blobs.iterator();

         while(var4.hasNext()) {
            Blob blob = (Blob)var4.next();
            JDBCUtils.safeFree(blob);
         }

         BLOBS.remove();
      }

      if (clobs != null) {
         var4 = clobs.iterator();

         while(var4.hasNext()) {
            Clob clob = (Clob)var4.next();
            JDBCUtils.safeFree(clob);
         }

         CLOBS.remove();
      }

      if (xmls != null) {
         var4 = xmls.iterator();

         while(var4.hasNext()) {
            SQLXML xml = (SQLXML)var4.next();
            JDBCUtils.safeFree(xml);
         }

         SQLXMLS.remove();
      }

      if (arrays != null) {
         var4 = arrays.iterator();

         while(var4.hasNext()) {
            java.sql.Array array = (java.sql.Array)var4.next();
            JDBCUtils.safeFree(array);
         }

         SQLXMLS.remove();
      }

      LOCAL_CONFIGURATION.remove();
      LOCAL_DATA.remove();
      LOCAL_CONNECTION.remove();
   }

   static final void register(Blob blob) {
      ((List)BLOBS.get()).add(blob);
   }

   static final void register(Clob clob) {
      ((List)CLOBS.get()).add(clob);
   }

   static final void register(SQLXML xml) {
      ((List)SQLXMLS.get()).add(xml);
   }

   static final void register(java.sql.Array array) {
      ((List)ARRAYS.get()).add(array);
   }

   static final Configuration localConfiguration() {
      return (Configuration)LOCAL_CONFIGURATION.get();
   }

   static final Map<Object, Object> localData() {
      return (Map)LOCAL_DATA.get();
   }

   static final Connection localConnection() {
      return (Connection)LOCAL_CONNECTION.get();
   }

   static final Connection localTargetConnection() {
      Connection result = localConnection();

      for(int i = 0; i < maxUnwrappedConnections; ++i) {
         Connection r;
         try {
            r = (Connection)Reflect.on((Object)result).call("getTargetConnection").get();
            if (result != r && r != null) {
               result = r;
               continue;
            }
         } catch (ReflectException var4) {
         }

         try {
            r = (Connection)Reflect.on((Object)result).call("getDelegate").get();
            if (result == r || r == null) {
               break;
            }

            result = r;
         } catch (ReflectException var3) {
            break;
         }
      }

      return result;
   }

   DefaultExecuteContext(Configuration configuration) {
      this(configuration, (Query)null, (Query[])null, (Routine)null);
   }

   DefaultExecuteContext(Configuration configuration, Query[] batchQueries) {
      this(configuration, (Query)null, batchQueries, (Routine)null);
   }

   DefaultExecuteContext(Configuration configuration, Query query) {
      this(configuration, query, new Query[]{query}, (Routine)null);
   }

   DefaultExecuteContext(Configuration configuration, Routine<?> routine) {
      this(configuration, (Query)null, (Query[])null, routine);
   }

   private DefaultExecuteContext(Configuration configuration, Query query, Query[] batchQueries, Routine<?> routine) {
      this.rows = -1;
      this.configuration = configuration;
      this.data = new DataMap();
      this.query = query;
      this.batchQueries = batchQueries == null ? new Query[0] : batchQueries;
      this.routine = routine;
      if (this.batchQueries.length > 0) {
         this.batchSQL = new String[this.batchQueries.length];
         this.batchRows = new int[this.batchQueries.length];

         for(int i = 0; i < this.batchQueries.length; ++i) {
            this.batchRows[i] = -1;
         }
      } else if (routine != null) {
         this.batchSQL = new String[1];
         this.batchRows = new int[]{-1};
      } else {
         this.batchSQL = new String[0];
         this.batchRows = new int[0];
      }

      clean();
      BLOBS.set(new ArrayList());
      CLOBS.set(new ArrayList());
      SQLXMLS.set(new ArrayList());
      ARRAYS.set(new ArrayList());
      LOCAL_CONFIGURATION.set(configuration);
      LOCAL_DATA.set(this.data);
   }

   public final Map<Object, Object> data() {
      return this.data;
   }

   public final Object data(Object key) {
      return this.data.get(key);
   }

   public final Object data(Object key, Object value) {
      return this.data.put(key, value);
   }

   public final ExecuteType type() {
      if (this.routine != null) {
         return ExecuteType.ROUTINE;
      } else if (this.batchQueries.length == 1 && this.query == null) {
         return ExecuteType.BATCH;
      } else if (this.batchQueries.length > 1) {
         return ExecuteType.BATCH;
      } else {
         if (this.query != null) {
            if (this.query instanceof ResultQuery) {
               return ExecuteType.READ;
            }

            if (this.query instanceof Insert || this.query instanceof Update || this.query instanceof Delete || this.query instanceof Merge) {
               return ExecuteType.WRITE;
            }

            if (this.query instanceof DDLQuery) {
               return ExecuteType.DDL;
            }

            String s = this.query.getSQL().toLowerCase();
            if (s.matches("^(with\\b.*?\\bselect|select|explain)\\b.*?")) {
               return ExecuteType.READ;
            }

            if (s.matches("^(insert|update|delete|merge|replace|upsert|lock)\\b.*?")) {
               return ExecuteType.WRITE;
            }

            if (s.matches("^(create|alter|drop|truncate|grant|revoke|analyze|comment|flashback|enable|disable)\\b.*?")) {
               return ExecuteType.DDL;
            }

            if (s.matches("^\\s*\\{\\s*(\\?\\s*=\\s*)call.*?")) {
               return ExecuteType.ROUTINE;
            }

            if (s.matches("^(call|begin|declare)\\b.*?")) {
               return ExecuteType.ROUTINE;
            }
         } else if (this.resultSet != null) {
            return ExecuteType.READ;
         }

         return ExecuteType.OTHER;
      }
   }

   public final Query query() {
      return this.query;
   }

   public final Query[] batchQueries() {
      return this.batchQueries;
   }

   public final Routine<?> routine() {
      return this.routine;
   }

   public final void sql(String s) {
      this.sql = s;
      if (this.batchSQL.length == 1) {
         this.batchSQL[0] = s;
      }

   }

   public final String sql() {
      return this.sql;
   }

   public final String[] batchSQL() {
      return this.batchSQL;
   }

   public final void statement(PreparedStatement s) {
      this.statement = s;
   }

   public final PreparedStatement statement() {
      return this.statement;
   }

   public final void resultSet(ResultSet rs) {
      this.resultSet = rs;
   }

   public final ResultSet resultSet() {
      return this.resultSet;
   }

   public final Configuration configuration() {
      return this.configuration;
   }

   public final Settings settings() {
      return Tools.settings(this.configuration());
   }

   public final SQLDialect dialect() {
      return Tools.configuration(this.configuration()).dialect();
   }

   public final SQLDialect family() {
      return this.dialect().family();
   }

   public final void connectionProvider(ConnectionProvider provider) {
      this.connectionProvider = provider;
   }

   public final Connection connection() {
      ConnectionProvider provider = this.connectionProvider != null ? this.connectionProvider : this.configuration.connectionProvider();
      if (this.connection == null && provider != null) {
         this.connection(provider, provider.acquire());
      }

      return this.connection;
   }

   final void connection(ConnectionProvider provider, Connection c) {
      if (c != null) {
         LOCAL_CONNECTION.set(c);
         this.connection = new SettingsEnabledConnection(new ProviderEnabledConnection(provider, c), this.configuration.settings());
      }

   }

   public final void record(Record r) {
      this.record = r;
   }

   public final Record record() {
      return this.record;
   }

   public final int rows() {
      return this.rows;
   }

   public final void rows(int r) {
      this.rows = r;
      if (this.batchRows.length == 1) {
         this.batchRows[0] = r;
      }

   }

   public final int[] batchRows() {
      return this.batchRows;
   }

   public final void result(Result<?> r) {
      this.result = r;
   }

   public final Result<?> result() {
      return this.result;
   }

   public final RuntimeException exception() {
      return this.exception;
   }

   public final void exception(RuntimeException e) {
      this.exception = e;
      if (Boolean.TRUE.equals(this.settings().isDebugInfoOnStackTrace())) {
         StackTraceElement[] oldStack = e.getStackTrace();
         if (oldStack != null) {
            StackTraceElement[] newStack = new StackTraceElement[oldStack.length + 1];
            System.arraycopy(oldStack, 0, newStack, 1, oldStack.length);
            newStack[0] = new StackTraceElement("org.jooq_3.9.1." + this.dialect(), "debug", (String)null, -1);
            e.setStackTrace(newStack);
         }
      }

   }

   public final SQLException sqlException() {
      return this.sqlException;
   }

   public final void sqlException(SQLException e) {
      this.sqlException = e;
      this.exception(Tools.translate(this.sql(), e));
   }

   public final SQLWarning sqlWarning() {
      return this.sqlWarning;
   }

   public final void sqlWarning(SQLWarning e) {
      this.sqlWarning = e;
   }
}
