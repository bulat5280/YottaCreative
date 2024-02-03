package org.jooq.impl;

import java.sql.SQLException;
import org.jooq.Binding;
import org.jooq.BindingGetResultSetContext;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.tools.Convert;

abstract class AbstractVarcharBinding<T> implements Binding<Object, T> {
   private static final long serialVersionUID = -2153155338260706262L;

   public final void sql(BindingSQLContext<T> ctx) throws SQLException {
      ctx.render().visit(DSL.val(ctx.convert(this.converter()).value()));
   }

   public final void register(BindingRegisterContext<T> ctx) throws SQLException {
      ctx.statement().registerOutParameter(ctx.index(), 12);
   }

   public final void set(BindingSetStatementContext<T> ctx) throws SQLException {
      ctx.statement().setString(ctx.index(), (String)Convert.convert(ctx.convert(this.converter()).value(), String.class));
   }

   public final void get(BindingGetResultSetContext<T> ctx) throws SQLException {
      ctx.convert(this.converter()).value(ctx.resultSet().getString(ctx.index()));
   }

   public final void get(BindingGetStatementContext<T> ctx) throws SQLException {
      ctx.convert(this.converter()).value(ctx.statement().getString(ctx.index()));
   }

   public final void set(BindingSetSQLOutputContext<T> ctx) throws SQLException {
      ctx.output().writeString((String)Convert.convert(ctx.convert(this.converter()).value(), String.class));
   }

   public final void get(BindingGetSQLInputContext<T> ctx) throws SQLException {
      ctx.convert(this.converter()).value(ctx.input().readString());
   }
}
