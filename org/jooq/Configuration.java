package org.jooq;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.sql.DataSource;
import org.jooq.conf.Settings;

public interface Configuration extends Serializable {
   Map<Object, Object> data();

   Object data(Object var1);

   Object data(Object var1, Object var2);

   ConnectionProvider connectionProvider();

   ExecutorProvider executorProvider();

   TransactionProvider transactionProvider();

   TransactionListenerProvider[] transactionListenerProviders();

   RecordMapperProvider recordMapperProvider();

   RecordListenerProvider[] recordListenerProviders();

   ExecuteListenerProvider[] executeListenerProviders();

   VisitListenerProvider[] visitListenerProviders();

   /** @deprecated */
   @Deprecated
   ConverterProvider converterProvider();

   /** @deprecated */
   @Deprecated
   SchemaMapping schemaMapping();

   SQLDialect dialect();

   SQLDialect family();

   Settings settings();

   Configuration set(ConnectionProvider var1);

   Configuration set(ExecutorProvider var1);

   Configuration set(Executor var1);

   Configuration set(Connection var1);

   Configuration set(DataSource var1);

   Configuration set(TransactionProvider var1);

   Configuration set(RecordMapper<?, ?> var1);

   Configuration set(RecordMapperProvider var1);

   Configuration set(RecordListener... var1);

   Configuration set(RecordListenerProvider... var1);

   Configuration set(ExecuteListener... var1);

   Configuration set(ExecuteListenerProvider... var1);

   Configuration set(VisitListener... var1);

   Configuration set(VisitListenerProvider... var1);

   Configuration set(TransactionListener... var1);

   Configuration set(TransactionListenerProvider... var1);

   /** @deprecated */
   @Deprecated
   Configuration set(ConverterProvider var1);

   Configuration set(SQLDialect var1);

   Configuration set(Settings var1);

   Configuration derive();

   Configuration derive(Connection var1);

   Configuration derive(DataSource var1);

   Configuration derive(ConnectionProvider var1);

   Configuration derive(Executor var1);

   Configuration derive(ExecutorProvider var1);

   Configuration derive(TransactionProvider var1);

   Configuration derive(RecordMapper<?, ?> var1);

   Configuration derive(RecordMapperProvider var1);

   Configuration derive(RecordListener... var1);

   Configuration derive(RecordListenerProvider... var1);

   Configuration derive(ExecuteListener... var1);

   Configuration derive(ExecuteListenerProvider... var1);

   Configuration derive(VisitListener... var1);

   Configuration derive(VisitListenerProvider... var1);

   Configuration derive(TransactionListener... var1);

   Configuration derive(TransactionListenerProvider... var1);

   /** @deprecated */
   @Deprecated
   Configuration derive(ConverterProvider var1);

   Configuration derive(SQLDialect var1);

   Configuration derive(Settings var1);
}
