package org.jooq.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.conf.RenderNameStyle;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.util.sqlite.SQLiteDSL;

abstract class AbstractDMLQuery<R extends Record> extends AbstractQuery {
   private static final long serialVersionUID = -7438014075226919192L;
   final WithImpl with;
   final Table<R> table;
   final QueryPartList<Field<?>> returning;
   Result<R> returned;

   AbstractDMLQuery(Configuration configuration, WithImpl with, Table<R> table) {
      super(configuration);
      this.with = with;
      this.table = table;
      this.returning = new QueryPartList();
   }

   public final void setReturning() {
      this.setReturning(this.table.fields());
   }

   public final void setReturning(Identity<R, ?> identity) {
      if (identity != null) {
         this.setReturning(identity.getField());
      }

   }

   public final void setReturning(Field<?>... fields) {
      this.setReturning((Collection)Arrays.asList(fields));
   }

   public final void setReturning(Collection<? extends Field<?>> fields) {
      this.returning.clear();
      this.returning.addAll(fields);
   }

   public final R getReturnedRecord() {
      return this.getReturnedRecords().size() == 0 ? null : (Record)this.getReturnedRecords().get(0);
   }

   public final Result<R> getReturnedRecords() {
      if (this.returned == null) {
         this.returned = new ResultImpl(this.configuration(), this.returning);
      }

      return this.returned;
   }

   public final void accept(Context<?> ctx) {
      if (this.with != null) {
         ctx.visit(this.with).formatSeparator();
      }

      this.accept0(ctx);
   }

   abstract void accept0(Context<?> var1);

   final void toSQLReturning(Context<?> ctx) {
      if (!this.returning.isEmpty()) {
         switch(ctx.family()) {
         case FIREBIRD:
         case POSTGRES:
            ctx.formatSeparator().keyword("returning").sql(' ').visit(this.returning);
         }
      }

   }

   protected final void prepare(ExecuteContext ctx) throws SQLException {
      Connection connection = ctx.connection();
      if (this.returning.isEmpty()) {
         super.prepare(ctx);
      } else {
         switch(ctx.family()) {
         case FIREBIRD:
         case POSTGRES:
         case SQLITE:
         case CUBRID:
            super.prepare(ctx);
            return;
         case DERBY:
         case H2:
         case MARIADB:
         case MYSQL:
            ctx.statement(connection.prepareStatement(ctx.sql(), 1));
            return;
         case HSQLDB:
         default:
            List<String> names = new ArrayList();
            RenderNameStyle style = this.configuration().settings().getRenderNameStyle();
            Iterator var5 = this.returning.iterator();

            while(var5.hasNext()) {
               Field<?> field = (Field)var5.next();
               if (style == RenderNameStyle.UPPER) {
                  names.add(field.getName().toUpperCase());
               } else if (style == RenderNameStyle.LOWER) {
                  names.add(field.getName().toLowerCase());
               } else {
                  names.add(field.getName());
               }
            }

            ctx.statement(connection.prepareStatement(ctx.sql(), (String[])names.toArray(Tools.EMPTY_STRING)));
         }
      }
   }

   protected final int execute(ExecuteContext ctx, ExecuteListener listener) throws SQLException {
      if (this.returning.isEmpty()) {
         return super.execute(ctx, listener);
      } else {
         int result = 1;
         ResultSet rs;
         switch(ctx.family()) {
         case FIREBIRD:
         case POSTGRES:
            listener.executeStart(ctx);
            rs = ctx.statement().executeQuery();
            listener.executeEnd(ctx);
            break;
         case SQLITE:
            listener.executeStart(ctx);
            result = ctx.statement().executeUpdate();
            ctx.rows(result);
            listener.executeEnd(ctx);
            DSLContext create = DSL.using(ctx.configuration());
            this.returned = create.select((Collection)this.returning).from(this.table).where(new Condition[]{SQLiteDSL.rowid().equal(SQLiteDSL.rowid().getDataType().convert((Object)create.lastID()))}).fetchInto(this.table);
            return result;
         case CUBRID:
            listener.executeStart(ctx);
            result = ctx.statement().executeUpdate();
            ctx.rows(result);
            listener.executeEnd(ctx);
            this.selectReturning(ctx.configuration(), this.create(ctx.configuration()).lastID());
            return result;
         case DERBY:
         case H2:
         case MARIADB:
         case MYSQL:
            listener.executeStart(ctx);
            result = ctx.statement().executeUpdate();
            ctx.rows(result);
            listener.executeEnd(ctx);
            rs = ctx.statement().getGeneratedKeys();

            int var6;
            try {
               List<Object> list = new ArrayList();
               if (rs != null) {
                  while(rs.next()) {
                     list.add(rs.getObject(1));
                  }
               }

               this.selectReturning(ctx.configuration(), list.toArray());
               var6 = result;
            } finally {
               JDBCUtils.safeClose(rs);
            }

            return var6;
         case HSQLDB:
         default:
            listener.executeStart(ctx);
            result = ctx.statement().executeUpdate();
            ctx.rows(result);
            listener.executeEnd(ctx);
            rs = ctx.statement().getGeneratedKeys();
         }

         ExecuteContext ctx2 = new DefaultExecuteContext(ctx.configuration());
         ExecuteListener listener2 = new ExecuteListeners(ctx2);
         ctx2.resultSet(rs);
         this.returned = (new CursorImpl(ctx2, listener2, Tools.fieldArray(this.returning), (int[])null, false, true)).fetch();
         if (this.table.fields().length > 0) {
            this.returned = this.returned.into(this.table);
         }

         if (this.returned.size() > 0 || ctx.family() != SQLDialect.HSQLDB) {
            result = this.returned.size();
            ctx.rows(result);
         }

         return result;
      }
   }

   private final void selectReturning(Configuration configuration, Object... values) {
      if (values != null && values.length > 0 && this.table.getIdentity() != null) {
         final Field<Object> field = this.table.getIdentity().getField();
         Object[] ids = new Object[values.length];

         for(int i = 0; i < values.length; ++i) {
            ids[i] = field.getDataType().convert(values[i]);
         }

         if (this.returning.size() == 1 && (new Fields(this.returning)).field((Field)field) != null) {
            Object[] var9 = ids;
            int var6 = ids.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               final Object id = var9[var7];
               this.getReturnedRecords().add(Tools.newRecord(true, this.table, configuration).operate(new RecordOperation<R, RuntimeException>() {
                  public R operate(R record) throws RuntimeException {
                     int index = record.fieldsRow().indexOf((Field)field);
                     ((AbstractRecord)record).values[index] = id;
                     ((AbstractRecord)record).originals[index] = id;
                     return record;
                  }
               }));
            }
         } else {
            this.returned = this.create(configuration).select((Collection)this.returning).from(this.table).where(new Condition[]{field.in(ids)}).fetchInto(this.table);
         }
      }

   }
}
