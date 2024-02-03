package org.jooq.impl;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import org.jooq.Binding;
import org.jooq.BindingGetResultSetContext;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.Converters;
import org.jooq.tools.jdbc.JDBCUtils;

public class ClobBinding implements Binding<String, String> {
   private static final long serialVersionUID = 358789452467943117L;

   public final Converter<String, String> converter() {
      return Converters.identity(String.class);
   }

   public final void sql(BindingSQLContext<String> ctx) throws SQLException {
      ctx.render().sql("?");
   }

   public final void register(BindingRegisterContext<String> ctx) throws SQLException {
      ctx.statement().registerOutParameter(ctx.index(), 2005);
   }

   public final void set(BindingSetStatementContext<String> ctx) throws SQLException {
      Clob clob = this.newClob(ctx.configuration(), (String)ctx.value());
      DefaultExecuteContext.register(clob);
      ctx.statement().setClob(ctx.index(), clob);
   }

   public final void set(BindingSetSQLOutputContext<String> ctx) throws SQLException {
      Clob clob = this.newClob(ctx.configuration(), (String)ctx.value());
      DefaultExecuteContext.register(clob);
      ctx.output().writeClob(clob);
   }

   public final void get(BindingGetResultSetContext<String> ctx) throws SQLException {
      Clob clob = ctx.resultSet().getClob(ctx.index());

      try {
         ctx.value(clob == null ? null : clob.getSubString(1L, (int)clob.length()));
      } finally {
         JDBCUtils.safeFree(clob);
      }

   }

   public final void get(BindingGetStatementContext<String> ctx) throws SQLException {
      Clob clob = ctx.statement().getClob(ctx.index());

      try {
         ctx.value(clob == null ? null : clob.getSubString(1L, (int)clob.length()));
      } finally {
         JDBCUtils.safeFree(clob);
      }

   }

   public final void get(BindingGetSQLInputContext<String> ctx) throws SQLException {
      Clob clob = ctx.input().readClob();

      try {
         ctx.value(clob == null ? null : clob.getSubString(1L, (int)clob.length()));
      } finally {
         JDBCUtils.safeFree(clob);
      }

   }

   private final Clob newClob(Configuration configuration, String string) throws SQLException {
      Connection c = configuration.connectionProvider().acquire();

      Clob var5;
      try {
         Clob clob = null;
         switch(configuration.family()) {
         default:
            clob = c.createClob();
            clob.setString(1L, string);
            var5 = clob;
         }
      } finally {
         configuration.connectionProvider().release(c);
      }

      return var5;
   }
}
