package org.jooq.tools.jdbc;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.sql.DataSource;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.ConverterProvider;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteListenerProvider;
import org.jooq.ExecutorProvider;
import org.jooq.RecordListener;
import org.jooq.RecordListenerProvider;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.SQLDialect;
import org.jooq.SchemaMapping;
import org.jooq.TransactionListener;
import org.jooq.TransactionListenerProvider;
import org.jooq.TransactionProvider;
import org.jooq.VisitListener;
import org.jooq.VisitListenerProvider;
import org.jooq.conf.Settings;

public class MockConfiguration implements Configuration {
   private static final long serialVersionUID = 2600901130544049995L;
   private final Configuration delegate;
   private final MockDataProvider provider;

   public MockConfiguration(Configuration delegate, MockDataProvider provider) {
      this.delegate = delegate;
      this.provider = provider;
   }

   public Map<Object, Object> data() {
      return this.delegate.data();
   }

   public Object data(Object key) {
      return this.delegate.data(key);
   }

   public Object data(Object key, Object value) {
      return this.delegate.data(key, value);
   }

   public ConnectionProvider connectionProvider() {
      return new MockConnectionProvider(this.delegate.connectionProvider(), this.provider);
   }

   public ExecutorProvider executorProvider() {
      return this.delegate.executorProvider();
   }

   public TransactionProvider transactionProvider() {
      return this.delegate.transactionProvider();
   }

   public RecordMapperProvider recordMapperProvider() {
      return this.delegate.recordMapperProvider();
   }

   public RecordListenerProvider[] recordListenerProviders() {
      return this.delegate.recordListenerProviders();
   }

   public ExecuteListenerProvider[] executeListenerProviders() {
      return this.delegate.executeListenerProviders();
   }

   public VisitListenerProvider[] visitListenerProviders() {
      return this.delegate.visitListenerProviders();
   }

   public TransactionListenerProvider[] transactionListenerProviders() {
      return this.delegate.transactionListenerProviders();
   }

   public ConverterProvider converterProvider() {
      return this.delegate.converterProvider();
   }

   public SchemaMapping schemaMapping() {
      return this.delegate.schemaMapping();
   }

   public SQLDialect dialect() {
      return this.delegate.dialect();
   }

   public SQLDialect family() {
      return this.delegate.family();
   }

   public Settings settings() {
      return this.delegate.settings();
   }

   public Configuration set(ConnectionProvider newConnectionProvider) {
      return this.delegate.set(newConnectionProvider);
   }

   public Configuration set(Connection newConnection) {
      return this.delegate.set(newConnection);
   }

   public Configuration set(DataSource newDataSource) {
      return this.delegate.set(newDataSource);
   }

   public Configuration set(Executor newExecutor) {
      return this.delegate.set(newExecutor);
   }

   public Configuration set(ExecutorProvider newExecutorProvider) {
      return this.delegate.set(newExecutorProvider);
   }

   public Configuration set(TransactionProvider newTransactionProvider) {
      return this.delegate.set(newTransactionProvider);
   }

   public Configuration set(RecordMapper<?, ?> newRecordMapper) {
      return this.delegate.set(newRecordMapper);
   }

   public Configuration set(RecordMapperProvider newRecordMapperProvider) {
      return this.delegate.set(newRecordMapperProvider);
   }

   public Configuration set(RecordListener... newRecordListeners) {
      return this.delegate.set(newRecordListeners);
   }

   public Configuration set(RecordListenerProvider... newRecordListenerProviders) {
      return this.delegate.set(newRecordListenerProviders);
   }

   public Configuration set(ExecuteListener... newExecuteListeners) {
      return this.delegate.set(newExecuteListeners);
   }

   public Configuration set(ExecuteListenerProvider... newExecuteListenerProviders) {
      return this.delegate.set(newExecuteListenerProviders);
   }

   public Configuration set(VisitListener... newVisitListeners) {
      return this.delegate.set(newVisitListeners);
   }

   public Configuration set(VisitListenerProvider... newVisitListenerProviders) {
      return this.delegate.set(newVisitListenerProviders);
   }

   public Configuration set(TransactionListener... newTransactionListeners) {
      return this.delegate.set(newTransactionListeners);
   }

   public Configuration set(TransactionListenerProvider... newTransactionListenerProviders) {
      return this.delegate.set(newTransactionListenerProviders);
   }

   public Configuration set(ConverterProvider newConverterProvider) {
      return this.delegate.set(newConverterProvider);
   }

   public Configuration set(SQLDialect newDialect) {
      return this.delegate.set(newDialect);
   }

   public Configuration set(Settings newSettings) {
      return this.delegate.set(newSettings);
   }

   public Configuration derive() {
      return this.delegate.derive();
   }

   public Configuration derive(Connection newConnection) {
      return this.delegate.derive(newConnection);
   }

   public Configuration derive(DataSource newDataSource) {
      return this.delegate.derive(newDataSource);
   }

   public Configuration derive(ConnectionProvider newConnectionProvider) {
      return this.delegate.derive(newConnectionProvider);
   }

   public Configuration derive(Executor newExecutor) {
      return this.delegate.derive(newExecutor);
   }

   public Configuration derive(ExecutorProvider newExecutorProvider) {
      return this.delegate.derive(newExecutorProvider);
   }

   public Configuration derive(TransactionProvider newTransactionProvider) {
      return this.delegate.derive(newTransactionProvider);
   }

   public Configuration derive(RecordMapper<?, ?> newRecordMapper) {
      return this.delegate.derive(newRecordMapper);
   }

   public Configuration derive(RecordMapperProvider newRecordMapperProvider) {
      return this.delegate.derive(newRecordMapperProvider);
   }

   public Configuration derive(RecordListener... newRecordListeners) {
      return this.delegate.derive(newRecordListeners);
   }

   public Configuration derive(RecordListenerProvider... newRecordListenerProviders) {
      return this.delegate.derive(newRecordListenerProviders);
   }

   public Configuration derive(ExecuteListener... newExecuteListeners) {
      return this.delegate.derive(newExecuteListeners);
   }

   public Configuration derive(ExecuteListenerProvider... newExecuteListenerProviders) {
      return this.delegate.derive(newExecuteListenerProviders);
   }

   public Configuration derive(VisitListener... newVisitListeners) {
      return this.delegate.derive(newVisitListeners);
   }

   public Configuration derive(VisitListenerProvider... newVisitListenerProviders) {
      return this.delegate.derive(newVisitListenerProviders);
   }

   public Configuration derive(TransactionListener... newTransactionListeners) {
      return this.delegate.derive(newTransactionListeners);
   }

   public Configuration derive(TransactionListenerProvider... newTransactionListenerProviders) {
      return this.delegate.derive(newTransactionListenerProviders);
   }

   public Configuration derive(ConverterProvider newConverterProvider) {
      return this.delegate.derive(newConverterProvider);
   }

   public Configuration derive(SQLDialect newDialect) {
      return this.delegate.derive(newDialect);
   }

   public Configuration derive(Settings newSettings) {
      return this.delegate.derive(newSettings);
   }
}
