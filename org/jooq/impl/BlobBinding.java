package org.jooq.impl;

import java.sql.Blob;
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

public class BlobBinding implements Binding<byte[], byte[]> {
   private static final long serialVersionUID = 358789452467943117L;

   public final Converter<byte[], byte[]> converter() {
      return Converters.identity(byte[].class);
   }

   public final void sql(BindingSQLContext<byte[]> ctx) throws SQLException {
      ctx.render().sql("?");
   }

   public final void register(BindingRegisterContext<byte[]> ctx) throws SQLException {
      ctx.statement().registerOutParameter(ctx.index(), 2004);
   }

   public final void set(BindingSetStatementContext<byte[]> ctx) throws SQLException {
      Blob blob = this.newBlob(ctx.configuration(), (byte[])ctx.value());
      DefaultExecuteContext.register(blob);
      ctx.statement().setBlob(ctx.index(), blob);
   }

   public final void set(BindingSetSQLOutputContext<byte[]> ctx) throws SQLException {
      Blob blob = this.newBlob(ctx.configuration(), (byte[])ctx.value());
      DefaultExecuteContext.register(blob);
      ctx.output().writeBlob(blob);
   }

   public final void get(BindingGetResultSetContext<byte[]> ctx) throws SQLException {
      Blob blob = ctx.resultSet().getBlob(ctx.index());

      try {
         ctx.value(blob == null ? null : blob.getBytes(1L, (int)blob.length()));
      } finally {
         JDBCUtils.safeFree(blob);
      }

   }

   public final void get(BindingGetStatementContext<byte[]> ctx) throws SQLException {
      Blob blob = ctx.statement().getBlob(ctx.index());

      try {
         ctx.value(blob == null ? null : blob.getBytes(1L, (int)blob.length()));
      } finally {
         JDBCUtils.safeFree(blob);
      }

   }

   public final void get(BindingGetSQLInputContext<byte[]> ctx) throws SQLException {
      Blob blob = ctx.input().readBlob();

      try {
         ctx.value(blob == null ? null : blob.getBytes(1L, (int)blob.length()));
      } finally {
         JDBCUtils.safeFree(blob);
      }

   }

   private final Blob newBlob(Configuration configuration, byte[] bytes) throws SQLException {
      Connection c = configuration.connectionProvider().acquire();

      Blob var5;
      try {
         Blob blob = null;
         switch(configuration.family()) {
         default:
            blob = c.createBlob();
            blob.setBytes(1L, bytes);
            var5 = blob;
         }
      } finally {
         configuration.connectionProvider().release(c);
      }

      return var5;
   }
}
