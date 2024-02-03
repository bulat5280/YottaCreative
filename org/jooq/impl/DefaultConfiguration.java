package org.jooq.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import javax.sql.DataSource;
import javax.xml.bind.JAXB;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.ConverterProvider;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteListenerProvider;
import org.jooq.ExecutorProvider;
import org.jooq.Record;
import org.jooq.RecordListener;
import org.jooq.RecordListenerProvider;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.RecordType;
import org.jooq.SQLDialect;
import org.jooq.SchemaMapping;
import org.jooq.TransactionListener;
import org.jooq.TransactionListenerProvider;
import org.jooq.TransactionProvider;
import org.jooq.VisitListener;
import org.jooq.VisitListenerProvider;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.ConfigurationException;

public class DefaultConfiguration implements Configuration {
   private static final long serialVersionUID = 8193158984283234708L;
   private SQLDialect dialect;
   private Settings settings;
   private ConcurrentHashMap<Object, Object> data;
   private transient ConnectionProvider connectionProvider;
   private transient ExecutorProvider executorProvider;
   private transient TransactionProvider transactionProvider;
   private transient RecordMapperProvider recordMapperProvider;
   private transient RecordListenerProvider[] recordListenerProviders;
   private transient ExecuteListenerProvider[] executeListenerProviders;
   private transient VisitListenerProvider[] visitListenerProviders;
   private transient TransactionListenerProvider[] transactionListenerProviders;
   private transient ConverterProvider converterProvider;
   private SchemaMapping mapping;

   public DefaultConfiguration() {
      this(SQLDialect.DEFAULT);
   }

   DefaultConfiguration(SQLDialect dialect) {
      this((ConnectionProvider)null, (ExecutorProvider)null, (TransactionProvider)null, (RecordMapperProvider)null, (RecordListenerProvider[])null, (ExecuteListenerProvider[])null, (VisitListenerProvider[])null, (TransactionListenerProvider[])null, (ConverterProvider)null, dialect, SettingsTools.defaultSettings(), (Map)null);
   }

   DefaultConfiguration(Configuration configuration) {
      this(configuration.connectionProvider(), configuration.executorProvider(), configuration.transactionProvider(), configuration.recordMapperProvider(), configuration.recordListenerProviders(), configuration.executeListenerProviders(), configuration.visitListenerProviders(), configuration.transactionListenerProviders(), configuration.converterProvider(), configuration.dialect(), configuration.settings(), configuration.data());
   }

   /** @deprecated */
   @Deprecated
   DefaultConfiguration(ConnectionProvider connectionProvider, ExecuteListenerProvider[] executeListenerProviders, SQLDialect dialect, Settings settings, Map<Object, Object> data) {
      this(connectionProvider, (ExecutorProvider)null, (TransactionProvider)null, (RecordMapperProvider)null, (RecordListenerProvider[])null, executeListenerProviders, (VisitListenerProvider[])null, (TransactionListenerProvider[])null, (ConverterProvider)null, dialect, settings, data);
   }

   /** @deprecated */
   @Deprecated
   DefaultConfiguration(ConnectionProvider connectionProvider, RecordMapperProvider recordMapperProvider, ExecuteListenerProvider[] executeListenerProviders, SQLDialect dialect, Settings settings, Map<Object, Object> data) {
      this(connectionProvider, (ExecutorProvider)null, (TransactionProvider)null, recordMapperProvider, (RecordListenerProvider[])null, executeListenerProviders, (VisitListenerProvider[])null, (TransactionListenerProvider[])null, (ConverterProvider)null, dialect, settings, data);
   }

   /** @deprecated */
   @Deprecated
   DefaultConfiguration(ConnectionProvider connectionProvider, RecordMapperProvider recordMapperProvider, RecordListenerProvider[] recordListenerProviders, ExecuteListenerProvider[] executeListenerProviders, VisitListenerProvider[] visitListenerProviders, SQLDialect dialect, Settings settings, Map<Object, Object> data) {
      this(connectionProvider, (ExecutorProvider)null, (TransactionProvider)null, recordMapperProvider, recordListenerProviders, executeListenerProviders, visitListenerProviders, (TransactionListenerProvider[])null, (ConverterProvider)null, dialect, settings, data);
   }

   /** @deprecated */
   @Deprecated
   DefaultConfiguration(ConnectionProvider connectionProvider, TransactionProvider transactionProvider, RecordMapperProvider recordMapperProvider, RecordListenerProvider[] recordListenerProviders, ExecuteListenerProvider[] executeListenerProviders, VisitListenerProvider[] visitListenerProviders, SQLDialect dialect, Settings settings, Map<Object, Object> data) {
      this(connectionProvider, (ExecutorProvider)null, transactionProvider, recordMapperProvider, recordListenerProviders, executeListenerProviders, visitListenerProviders, (TransactionListenerProvider[])null, (ConverterProvider)null, dialect, settings, data);
   }

   /** @deprecated */
   @Deprecated
   DefaultConfiguration(ConnectionProvider connectionProvider, TransactionProvider transactionProvider, RecordMapperProvider recordMapperProvider, RecordListenerProvider[] recordListenerProviders, ExecuteListenerProvider[] executeListenerProviders, VisitListenerProvider[] visitListenerProviders, ConverterProvider converterProvider, SQLDialect dialect, Settings settings, Map<Object, Object> data) {
      this(connectionProvider, (ExecutorProvider)null, transactionProvider, recordMapperProvider, recordListenerProviders, executeListenerProviders, visitListenerProviders, (TransactionListenerProvider[])null, converterProvider, dialect, settings, data);
   }

   /** @deprecated */
   @Deprecated
   DefaultConfiguration(ConnectionProvider connectionProvider, ExecutorProvider executorProvider, TransactionProvider transactionProvider, RecordMapperProvider recordMapperProvider, RecordListenerProvider[] recordListenerProviders, ExecuteListenerProvider[] executeListenerProviders, VisitListenerProvider[] visitListenerProviders, ConverterProvider converterProvider, SQLDialect dialect, Settings settings, Map<Object, Object> data) {
      this(connectionProvider, executorProvider, transactionProvider, recordMapperProvider, recordListenerProviders, executeListenerProviders, visitListenerProviders, (TransactionListenerProvider[])null, converterProvider, dialect, settings, data);
   }

   DefaultConfiguration(ConnectionProvider connectionProvider, ExecutorProvider executorProvider, TransactionProvider transactionProvider, RecordMapperProvider recordMapperProvider, RecordListenerProvider[] recordListenerProviders, ExecuteListenerProvider[] executeListenerProviders, VisitListenerProvider[] visitListenerProviders, TransactionListenerProvider[] transactionListenerProviders, ConverterProvider converterProvider, SQLDialect dialect, Settings settings, Map<Object, Object> data) {
      this.set(connectionProvider);
      this.set(executorProvider);
      this.set(transactionProvider);
      this.set(recordMapperProvider);
      this.set(recordListenerProviders);
      this.set(executeListenerProviders);
      this.set(visitListenerProviders);
      this.set(transactionListenerProviders);
      this.set(converterProvider);
      this.set(dialect);
      this.set(settings);
      this.data = data != null ? new ConcurrentHashMap(data) : new ConcurrentHashMap();
   }

   public final Configuration derive() {
      return new DefaultConfiguration(this);
   }

   public final Configuration derive(Connection newConnection) {
      return this.derive((ConnectionProvider)(new DefaultConnectionProvider(newConnection)));
   }

   public final Configuration derive(DataSource newDataSource) {
      return this.derive((ConnectionProvider)(new DataSourceConnectionProvider(newDataSource)));
   }

   public final Configuration derive(ConnectionProvider newConnectionProvider) {
      return new DefaultConfiguration(newConnectionProvider, this.executorProvider, this.transactionProvider, this.recordMapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.converterProvider, this.dialect, this.settings, this.data);
   }

   public final Configuration derive(Executor newExecutor) {
      return this.derive((ExecutorProvider)(new DefaultConfiguration.ExecutorWrapper(newExecutor)));
   }

   public final Configuration derive(ExecutorProvider newExecutorProvider) {
      return new DefaultConfiguration(this.connectionProvider, newExecutorProvider, this.transactionProvider, this.recordMapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.converterProvider, this.dialect, this.settings, this.data);
   }

   public final Configuration derive(TransactionProvider newTransactionProvider) {
      return new DefaultConfiguration(this.connectionProvider, this.executorProvider, newTransactionProvider, this.recordMapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.converterProvider, this.dialect, this.settings, this.data);
   }

   public final Configuration derive(RecordMapper<?, ?> newRecordMapper) {
      return this.derive((RecordMapperProvider)(new DefaultConfiguration.RecordMapperWrapper(newRecordMapper)));
   }

   public final Configuration derive(RecordMapperProvider newRecordMapperProvider) {
      return new DefaultConfiguration(this.connectionProvider, this.executorProvider, this.transactionProvider, newRecordMapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.converterProvider, this.dialect, this.settings, this.data);
   }

   public final Configuration derive(RecordListener... newRecordListeners) {
      return this.derive(DefaultRecordListenerProvider.providers(newRecordListeners));
   }

   public final Configuration derive(RecordListenerProvider... newRecordListenerProviders) {
      return new DefaultConfiguration(this.connectionProvider, this.executorProvider, this.transactionProvider, this.recordMapperProvider, newRecordListenerProviders, this.executeListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.converterProvider, this.dialect, this.settings, this.data);
   }

   public final Configuration derive(ExecuteListener... newExecuteListeners) {
      return this.derive(DefaultExecuteListenerProvider.providers(newExecuteListeners));
   }

   public final Configuration derive(ExecuteListenerProvider... newExecuteListenerProviders) {
      return new DefaultConfiguration(this.connectionProvider, this.executorProvider, this.transactionProvider, this.recordMapperProvider, this.recordListenerProviders, newExecuteListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.converterProvider, this.dialect, this.settings, this.data);
   }

   public final Configuration derive(VisitListener... newVisitListeners) {
      return this.derive(DefaultVisitListenerProvider.providers(newVisitListeners));
   }

   public final Configuration derive(VisitListenerProvider... newVisitListenerProviders) {
      return new DefaultConfiguration(this.connectionProvider, this.executorProvider, this.transactionProvider, this.recordMapperProvider, this.recordListenerProviders, this.executeListenerProviders, newVisitListenerProviders, this.transactionListenerProviders, this.converterProvider, this.dialect, this.settings, this.data);
   }

   public final Configuration derive(TransactionListener... newTransactionListeners) {
      return this.derive(DefaultTransactionListenerProvider.providers(newTransactionListeners));
   }

   public final Configuration derive(TransactionListenerProvider... newTransactionListenerProviders) {
      return new DefaultConfiguration(this.connectionProvider, this.executorProvider, this.transactionProvider, this.recordMapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.visitListenerProviders, newTransactionListenerProviders, this.converterProvider, this.dialect, this.settings, this.data);
   }

   public final Configuration derive(ConverterProvider newConverterProvider) {
      return new DefaultConfiguration(this.connectionProvider, this.executorProvider, this.transactionProvider, this.recordMapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, newConverterProvider, this.dialect, this.settings, this.data);
   }

   public final Configuration derive(SQLDialect newDialect) {
      return new DefaultConfiguration(this.connectionProvider, this.executorProvider, this.transactionProvider, this.recordMapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.converterProvider, newDialect, this.settings, this.data);
   }

   public final Configuration derive(Settings newSettings) {
      return new DefaultConfiguration(this.connectionProvider, this.executorProvider, this.transactionProvider, this.recordMapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.converterProvider, this.dialect, newSettings, this.data);
   }

   public final Configuration set(Connection newConnection) {
      return this.set((ConnectionProvider)(new DefaultConnectionProvider(newConnection)));
   }

   public final Configuration set(DataSource newDataSource) {
      return this.set((ConnectionProvider)(new DataSourceConnectionProvider(newDataSource)));
   }

   public final Configuration set(ConnectionProvider newConnectionProvider) {
      if (newConnectionProvider != null) {
         if (this.transactionProvider instanceof ThreadLocalTransactionProvider && !(newConnectionProvider instanceof ThreadLocalTransactionProvider.ThreadLocalConnectionProvider)) {
            throw new ConfigurationException("Cannot specify custom ConnectionProvider when Configuration contains a ThreadLocalTransactionProvider");
         }

         this.connectionProvider = newConnectionProvider;
      } else {
         this.connectionProvider = new NoConnectionProvider();
      }

      return this;
   }

   public final Configuration set(Executor newExecutor) {
      return this.set((ExecutorProvider)(new DefaultConfiguration.ExecutorWrapper(newExecutor)));
   }

   public final Configuration set(ExecutorProvider newExecutorProvider) {
      this.executorProvider = newExecutorProvider;
      return this;
   }

   public final Configuration set(TransactionProvider newTransactionProvider) {
      if (newTransactionProvider != null) {
         this.transactionProvider = newTransactionProvider;
         if (newTransactionProvider instanceof ThreadLocalTransactionProvider) {
            this.connectionProvider = ((ThreadLocalTransactionProvider)newTransactionProvider).localConnectionProvider;
         }
      } else {
         this.transactionProvider = new NoTransactionProvider();
      }

      return this;
   }

   public Configuration set(RecordMapper<?, ?> newRecordMapper) {
      return this.set((RecordMapperProvider)(new DefaultConfiguration.RecordMapperWrapper(newRecordMapper)));
   }

   public final Configuration set(RecordMapperProvider newRecordMapperProvider) {
      this.recordMapperProvider = newRecordMapperProvider;
      return this;
   }

   public final Configuration set(RecordListener... newRecordListeners) {
      return this.set(DefaultRecordListenerProvider.providers(newRecordListeners));
   }

   public final Configuration set(RecordListenerProvider... newRecordListenerProviders) {
      this.recordListenerProviders = newRecordListenerProviders != null ? newRecordListenerProviders : new RecordListenerProvider[0];
      return this;
   }

   public final Configuration set(ExecuteListener... newExecuteListeners) {
      return this.set(DefaultExecuteListenerProvider.providers(newExecuteListeners));
   }

   public final Configuration set(ExecuteListenerProvider... newExecuteListenerProviders) {
      this.executeListenerProviders = newExecuteListenerProviders != null ? newExecuteListenerProviders : new ExecuteListenerProvider[0];
      return this;
   }

   public final Configuration set(VisitListener... newVisitListeners) {
      return this.set(DefaultVisitListenerProvider.providers(newVisitListeners));
   }

   public final Configuration set(VisitListenerProvider... newVisitListenerProviders) {
      this.visitListenerProviders = newVisitListenerProviders != null ? newVisitListenerProviders : new VisitListenerProvider[0];
      return this;
   }

   public final Configuration set(TransactionListener... newTransactionListeners) {
      return this.set(DefaultTransactionListenerProvider.providers(newTransactionListeners));
   }

   public final Configuration set(TransactionListenerProvider... newTransactionListenerProviders) {
      this.transactionListenerProviders = newTransactionListenerProviders != null ? newTransactionListenerProviders : new TransactionListenerProvider[0];
      return this;
   }

   public final Configuration set(ConverterProvider newConverterProvider) {
      this.converterProvider = (ConverterProvider)(newConverterProvider != null ? newConverterProvider : new DefaultConverterProvider());
      return this;
   }

   public final Configuration set(SQLDialect newDialect) {
      this.dialect = newDialect;
      return this;
   }

   public final Configuration set(Settings newSettings) {
      this.settings = newSettings != null ? SettingsTools.clone(newSettings) : SettingsTools.defaultSettings();
      this.mapping = new SchemaMapping(this);
      return this;
   }

   public final void setConnection(Connection newConnection) {
      this.set(newConnection);
   }

   public final void setDataSource(DataSource newDataSource) {
      this.set(newDataSource);
   }

   public final void setConnectionProvider(ConnectionProvider newConnectionProvider) {
      this.set(newConnectionProvider);
   }

   public final void setExecutorProvider(ExecutorProvider newExecutorProvider) {
      this.set(newExecutorProvider);
   }

   public final void setTransactionProvider(TransactionProvider newTransactionProvider) {
      this.set(newTransactionProvider);
   }

   public final void setRecordMapperProvider(RecordMapperProvider newRecordMapperProvider) {
      this.set(newRecordMapperProvider);
   }

   public final void setRecordListenerProvider(RecordListenerProvider... newRecordListenerProviders) {
      this.set(newRecordListenerProviders);
   }

   public final void setExecuteListenerProvider(ExecuteListenerProvider... newExecuteListenerProviders) {
      this.set(newExecuteListenerProviders);
   }

   public final void setVisitListenerProvider(VisitListenerProvider... newVisitListenerProviders) {
      this.set(newVisitListenerProviders);
   }

   public final void setTransactionListenerProvider(TransactionListenerProvider... newTransactionListenerProviders) {
      this.set(newTransactionListenerProviders);
   }

   public final void setSQLDialect(SQLDialect newDialect) {
      this.set(newDialect);
   }

   public final void setSettings(Settings newSettings) {
      this.set(newSettings);
   }

   public final ConnectionProvider connectionProvider() {
      TransactionProvider tp = this.transactionProvider();
      ConnectionProvider transactional = tp instanceof ThreadLocalTransactionProvider ? ((ThreadLocalTransactionProvider)tp).localConnectionProvider : (ConnectionProvider)this.data(Tools.DataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_CONNECTION);
      return (ConnectionProvider)(transactional == null ? this.connectionProvider : transactional);
   }

   public final ExecutorProvider executorProvider() {
      return (ExecutorProvider)(this.executorProvider != null ? this.executorProvider : new DefaultExecutorProvider());
   }

   public final TransactionProvider transactionProvider() {
      return (TransactionProvider)(this.transactionProvider instanceof NoTransactionProvider ? new DefaultTransactionProvider(this.connectionProvider) : this.transactionProvider);
   }

   public final RecordMapperProvider recordMapperProvider() {
      return (RecordMapperProvider)(this.recordMapperProvider != null ? this.recordMapperProvider : new DefaultRecordMapperProvider(this));
   }

   public final RecordListenerProvider[] recordListenerProviders() {
      return this.recordListenerProviders;
   }

   public final ExecuteListenerProvider[] executeListenerProviders() {
      return this.executeListenerProviders;
   }

   public final VisitListenerProvider[] visitListenerProviders() {
      return this.visitListenerProviders;
   }

   public final TransactionListenerProvider[] transactionListenerProviders() {
      return this.transactionListenerProviders;
   }

   public final ConverterProvider converterProvider() {
      return this.converterProvider;
   }

   public final SQLDialect dialect() {
      return this.dialect;
   }

   public final SQLDialect family() {
      return this.dialect.family();
   }

   public final Settings settings() {
      return this.settings;
   }

   public final ConcurrentHashMap<Object, Object> data() {
      return this.data;
   }

   public final Object data(Object key) {
      return this.data.get(key);
   }

   public final Object data(Object key, Object value) {
      return this.data.put(key, value);
   }

   /** @deprecated */
   @Deprecated
   public final SchemaMapping schemaMapping() {
      return this.mapping;
   }

   public String toString() {
      StringWriter writer = new StringWriter();
      JAXB.marshal(this.settings, writer);
      return "DefaultConfiguration [\n\tconnected=" + (this.connectionProvider != null && !(this.connectionProvider instanceof NoConnectionProvider)) + ",\n\ttransactional=" + (this.transactionProvider != null && !(this.transactionProvider instanceof NoTransactionProvider)) + ",\n\tdialect=" + this.dialect + ",\n\tdata=" + this.data + ",\n\tsettings=\n\t\t" + writer.toString().trim().replace("\n", "\n\t\t") + "\n]";
   }

   private void writeObject(ObjectOutputStream oos) throws IOException {
      oos.defaultWriteObject();
      oos.writeObject(this.connectionProvider instanceof Serializable ? this.connectionProvider : null);
      oos.writeObject(this.transactionProvider instanceof Serializable ? this.transactionProvider : null);
      oos.writeObject(this.recordMapperProvider instanceof Serializable ? this.recordMapperProvider : null);
      oos.writeObject(this.cloneSerializables(this.executeListenerProviders));
      oos.writeObject(this.cloneSerializables(this.recordListenerProviders));
      oos.writeObject(this.cloneSerializables(this.visitListenerProviders));
      oos.writeObject(this.cloneSerializables(this.transactionListenerProviders));
      oos.writeObject(this.converterProvider instanceof Serializable ? this.converterProvider : null);
   }

   private <E> E[] cloneSerializables(E[] array) {
      E[] clone = (Object[])array.clone();

      for(int i = 0; i < clone.length; ++i) {
         if (!(clone[i] instanceof Serializable)) {
            clone[i] = null;
         }
      }

      return clone;
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      ois.defaultReadObject();
      this.connectionProvider = (ConnectionProvider)ois.readObject();
      this.transactionProvider = (TransactionProvider)ois.readObject();
      this.recordMapperProvider = (RecordMapperProvider)ois.readObject();
      this.executeListenerProviders = (ExecuteListenerProvider[])((ExecuteListenerProvider[])ois.readObject());
      this.recordListenerProviders = (RecordListenerProvider[])((RecordListenerProvider[])ois.readObject());
      this.visitListenerProviders = (VisitListenerProvider[])((VisitListenerProvider[])ois.readObject());
      this.transactionListenerProviders = (TransactionListenerProvider[])((TransactionListenerProvider[])ois.readObject());
      this.converterProvider = (ConverterProvider)ois.readObject();
   }

   private final class RecordMapperWrapper implements RecordMapperProvider {
      private final RecordMapper<?, ?> newRecordMapper;

      private RecordMapperWrapper(RecordMapper<?, ?> newRecordMapper) {
         this.newRecordMapper = newRecordMapper;
      }

      public <R extends Record, E> RecordMapper<R, E> provide(RecordType<R> recordType, Class<? extends E> type) {
         return this.newRecordMapper;
      }

      // $FF: synthetic method
      RecordMapperWrapper(RecordMapper x1, Object x2) {
         this(x1);
      }
   }

   private final class ExecutorWrapper implements ExecutorProvider {
      private final Executor newExecutor;

      private ExecutorWrapper(Executor newExecutor) {
         this.newExecutor = newExecutor;
      }

      public Executor provide() {
         return this.newExecutor;
      }

      // $FF: synthetic method
      ExecutorWrapper(Executor x1, Object x2) {
         this(x1);
      }
   }
}
