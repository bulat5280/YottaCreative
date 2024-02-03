package org.jooq;

import java.io.Serializable;
import java.sql.SQLException;

public interface Binding<T, U> extends Serializable {
   Converter<T, U> converter();

   void sql(BindingSQLContext<U> var1) throws SQLException;

   void register(BindingRegisterContext<U> var1) throws SQLException;

   void set(BindingSetStatementContext<U> var1) throws SQLException;

   void set(BindingSetSQLOutputContext<U> var1) throws SQLException;

   void get(BindingGetResultSetContext<U> var1) throws SQLException;

   void get(BindingGetStatementContext<U> var1) throws SQLException;

   void get(BindingGetSQLInputContext<U> var1) throws SQLException;
}
