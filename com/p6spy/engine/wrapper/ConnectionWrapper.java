package com.p6spy.engine.wrapper;

import com.p6spy.engine.common.CallableStatementInformation;
import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.StatementInformation;
import com.p6spy.engine.event.JdbcEventListener;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class ConnectionWrapper extends AbstractWrapper implements Connection {
   private final Connection delegate;
   private final JdbcEventListener jdbcEventListener;
   private final ConnectionInformation connectionInformation;

   public static ConnectionWrapper wrap(Connection delegate, JdbcEventListener eventListener, ConnectionInformation connectionInformation) {
      if (delegate == null) {
         return null;
      } else {
         ConnectionWrapper connectionWrapper = new ConnectionWrapper(delegate, eventListener, connectionInformation);
         eventListener.onConnectionWrapped(connectionInformation);
         return connectionWrapper;
      }
   }

   protected ConnectionWrapper(Connection delegate, JdbcEventListener jdbcEventListener, ConnectionInformation connectionInformation) {
      super(delegate);
      if (delegate == null) {
         throw new NullPointerException("Delegate must not be null");
      } else {
         this.delegate = delegate;
         this.connectionInformation = connectionInformation;
         this.jdbcEventListener = jdbcEventListener;
      }
   }

   public JdbcEventListener getJdbcEventListener() {
      return this.jdbcEventListener;
   }

   public JdbcEventListener getEventListener() {
      return this.jdbcEventListener;
   }

   public Connection getDelegate() {
      return this.delegate;
   }

   public ConnectionInformation getConnectionInformation() {
      return this.connectionInformation;
   }

   public Statement createStatement() throws SQLException {
      return StatementWrapper.wrap(this.delegate.createStatement(), new StatementInformation(this.connectionInformation), this.jdbcEventListener);
   }

   public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
      return StatementWrapper.wrap(this.delegate.createStatement(resultSetType, resultSetConcurrency), new StatementInformation(this.connectionInformation), this.jdbcEventListener);
   }

   public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      return StatementWrapper.wrap(this.delegate.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability), new StatementInformation(this.connectionInformation), this.jdbcEventListener);
   }

   public PreparedStatement prepareStatement(String sql) throws SQLException {
      return PreparedStatementWrapper.wrap(this.delegate.prepareStatement(sql), new PreparedStatementInformation(this.connectionInformation, sql), this.jdbcEventListener);
   }

   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      return PreparedStatementWrapper.wrap(this.delegate.prepareStatement(sql, resultSetType, resultSetConcurrency), new PreparedStatementInformation(this.connectionInformation, sql), this.jdbcEventListener);
   }

   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      return PreparedStatementWrapper.wrap(this.delegate.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability), new PreparedStatementInformation(this.connectionInformation, sql), this.jdbcEventListener);
   }

   public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
      return PreparedStatementWrapper.wrap(this.delegate.prepareStatement(sql, autoGeneratedKeys), new PreparedStatementInformation(this.connectionInformation, sql), this.jdbcEventListener);
   }

   public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
      return PreparedStatementWrapper.wrap(this.delegate.prepareStatement(sql, columnIndexes), new PreparedStatementInformation(this.connectionInformation, sql), this.jdbcEventListener);
   }

   public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
      return PreparedStatementWrapper.wrap(this.delegate.prepareStatement(sql, columnNames), new PreparedStatementInformation(this.connectionInformation, sql), this.jdbcEventListener);
   }

   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      return CallableStatementWrapper.wrap(this.delegate.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability), new CallableStatementInformation(this.connectionInformation, sql), this.jdbcEventListener);
   }

   public CallableStatement prepareCall(String sql) throws SQLException {
      return CallableStatementWrapper.wrap(this.delegate.prepareCall(sql), new CallableStatementInformation(this.connectionInformation, sql), this.jdbcEventListener);
   }

   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      return CallableStatementWrapper.wrap(this.delegate.prepareCall(sql, resultSetType, resultSetConcurrency), new CallableStatementInformation(this.connectionInformation, sql), this.jdbcEventListener);
   }

   public void commit() throws SQLException {
      SQLException e = null;
      long start = System.nanoTime();

      try {
         this.jdbcEventListener.onBeforeCommit(this.connectionInformation);
         this.delegate.commit();
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.jdbcEventListener.onAfterCommit(this.connectionInformation, System.nanoTime() - start, e);
      }

   }

   public void rollback() throws SQLException {
      SQLException e = null;
      long start = System.nanoTime();

      try {
         this.jdbcEventListener.onBeforeRollback(this.connectionInformation);
         this.delegate.rollback();
      } catch (SQLException var8) {
         e = var8;
         throw var8;
      } finally {
         this.jdbcEventListener.onAfterRollback(this.connectionInformation, System.nanoTime() - start, e);
      }

   }

   public void rollback(Savepoint savepoint) throws SQLException {
      SQLException e = null;
      long start = System.nanoTime();

      try {
         this.jdbcEventListener.onBeforeRollback(this.connectionInformation);
         this.delegate.rollback(savepoint);
      } catch (SQLException var9) {
         e = var9;
         throw var9;
      } finally {
         this.jdbcEventListener.onAfterRollback(this.connectionInformation, System.nanoTime() - start, e);
      }

   }

   public String nativeSQL(String sql) throws SQLException {
      return this.delegate.nativeSQL(sql);
   }

   public void setAutoCommit(boolean autoCommit) throws SQLException {
      this.delegate.setAutoCommit(autoCommit);
   }

   public boolean getAutoCommit() throws SQLException {
      return this.delegate.getAutoCommit();
   }

   public void close() throws SQLException {
      SQLException e = null;

      try {
         this.delegate.close();
      } catch (SQLException var6) {
         e = var6;
         throw var6;
      } finally {
         this.jdbcEventListener.onAfterConnectionClose(this.connectionInformation, e);
      }

   }

   public boolean isClosed() throws SQLException {
      return this.delegate.isClosed();
   }

   public DatabaseMetaData getMetaData() throws SQLException {
      return this.delegate.getMetaData();
   }

   public void setReadOnly(boolean readOnly) throws SQLException {
      this.delegate.setReadOnly(readOnly);
   }

   public boolean isReadOnly() throws SQLException {
      return this.delegate.isReadOnly();
   }

   public void setCatalog(String catalog) throws SQLException {
      this.delegate.setCatalog(catalog);
   }

   public String getCatalog() throws SQLException {
      return this.delegate.getCatalog();
   }

   public void setTransactionIsolation(int level) throws SQLException {
      this.delegate.setTransactionIsolation(level);
   }

   public int getTransactionIsolation() throws SQLException {
      return this.delegate.getTransactionIsolation();
   }

   public SQLWarning getWarnings() throws SQLException {
      return this.delegate.getWarnings();
   }

   public void clearWarnings() throws SQLException {
      this.delegate.clearWarnings();
   }

   public Map<String, Class<?>> getTypeMap() throws SQLException {
      return this.delegate.getTypeMap();
   }

   public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
      this.delegate.setTypeMap(map);
   }

   public void setHoldability(int holdability) throws SQLException {
      this.delegate.setHoldability(holdability);
   }

   public int getHoldability() throws SQLException {
      return this.delegate.getHoldability();
   }

   public Savepoint setSavepoint() throws SQLException {
      return this.delegate.setSavepoint();
   }

   public Savepoint setSavepoint(String name) throws SQLException {
      return this.delegate.setSavepoint(name);
   }

   public void releaseSavepoint(Savepoint savepoint) throws SQLException {
      this.delegate.releaseSavepoint(savepoint);
   }

   public Clob createClob() throws SQLException {
      return this.delegate.createClob();
   }

   public Blob createBlob() throws SQLException {
      return this.delegate.createBlob();
   }

   public NClob createNClob() throws SQLException {
      return this.delegate.createNClob();
   }

   public SQLXML createSQLXML() throws SQLException {
      return this.delegate.createSQLXML();
   }

   public boolean isValid(int timeout) throws SQLException {
      return this.delegate.isValid(timeout);
   }

   public void setClientInfo(String name, String value) throws SQLClientInfoException {
      this.delegate.setClientInfo(name, value);
   }

   public void setClientInfo(Properties properties) throws SQLClientInfoException {
      this.delegate.setClientInfo(properties);
   }

   public String getClientInfo(String name) throws SQLException {
      return this.delegate.getClientInfo(name);
   }

   public Properties getClientInfo() throws SQLException {
      return this.delegate.getClientInfo();
   }

   public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
      return this.delegate.createArrayOf(typeName, elements);
   }

   public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
      return this.delegate.createStruct(typeName, attributes);
   }

   public void setSchema(String schema) throws SQLException {
      this.delegate.setSchema(schema);
   }

   public String getSchema() throws SQLException {
      return this.delegate.getSchema();
   }

   public void abort(Executor executor) throws SQLException {
      this.delegate.abort(executor);
   }

   public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
      this.delegate.setNetworkTimeout(executor, milliseconds);
   }

   public int getNetworkTimeout() throws SQLException {
      return this.delegate.getNetworkTimeout();
   }
}