package org.jooq.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import org.jooq.Binding;
import org.jooq.BindingGetResultSetContext;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.Converter;
import org.jooq.Converters;

public class DateAsTimestampBinding implements Binding<Timestamp, Timestamp> {
   private static final long serialVersionUID = -7730882831126647188L;
   private final Converter<Timestamp, Timestamp> converter = Converters.identity(Timestamp.class);
   private final DefaultBinding<Timestamp, Timestamp> delegate;

   public DateAsTimestampBinding() {
      this.delegate = new DefaultBinding(this.converter);
   }

   public final Converter<Timestamp, Timestamp> converter() {
      return this.converter;
   }

   public final void sql(BindingSQLContext<Timestamp> ctx) throws SQLException {
      this.delegate.sql(ctx);
   }

   public final void register(BindingRegisterContext<Timestamp> ctx) throws SQLException {
      this.delegate.register(ctx);
   }

   public final void set(BindingSetStatementContext<Timestamp> ctx) throws SQLException {
      this.delegate.set(ctx);
   }

   public final void set(BindingSetSQLOutputContext<Timestamp> ctx) throws SQLException {
      this.delegate.set(ctx);
   }

   public final void get(BindingGetResultSetContext<Timestamp> ctx) throws SQLException {
      this.delegate.get(ctx);
   }

   public final void get(BindingGetStatementContext<Timestamp> ctx) throws SQLException {
      this.delegate.get(ctx);
   }

   public final void get(BindingGetSQLInputContext<Timestamp> ctx) throws SQLException {
      this.delegate.get(ctx);
   }
}
