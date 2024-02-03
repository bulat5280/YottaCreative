package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Row;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Table;

final class Values<R extends Record> extends AbstractTable<R> {
   private static final long serialVersionUID = -637982217747670311L;
   private final Row[] rows;

   Values(Row[] rows) {
      super("values");
      this.rows = assertNotEmpty(rows);
   }

   static Row[] assertNotEmpty(Row[] rows) {
      if (rows != null && rows.length != 0) {
         return rows;
      } else {
         throw new IllegalArgumentException("Cannot create a VALUES() constructor with an empty set of rows");
      }
   }

   public final Class<? extends R> getRecordType() {
      return RecordImpl.class;
   }

   public final Table<R> as(String alias) {
      return new TableAlias(this, alias, true);
   }

   public final Table<R> as(String alias, String... fieldAliases) {
      return new TableAlias(this, alias, fieldAliases, true);
   }

   public final void accept(Context<?> ctx) {
      int var5;
      switch(ctx.family()) {
      case FIREBIRD:
      case MARIADB:
      case MYSQL:
      case SQLITE:
         Select<Record> selects = null;
         boolean subquery = ctx.subquery();
         Row[] var4 = this.rows;
         var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Row row = var4[var6];
            Select<Record> select = this.create().select((SelectField[])row.fields());
            if (selects == null) {
               selects = select;
            } else {
               selects = ((Select)selects).unionAll(select);
            }
         }

         ctx.formatIndentStart().formatNewLine().subquery(true).visit((QueryPart)selects).subquery(subquery).formatIndentEnd().formatNewLine();
         break;
      case CUBRID:
      case DERBY:
      case H2:
      case HSQLDB:
      case POSTGRES:
      default:
         ctx.start(Clause.TABLE_VALUES).keyword("values").formatIndentLockStart();
         boolean firstRow = true;
         Row[] var10 = this.rows;
         int var11 = var10.length;

         for(var5 = 0; var5 < var11; ++var5) {
            Row row = var10[var5];
            if (!firstRow) {
               ctx.sql(',').formatSeparator();
            }

            ctx.visit(row);
            firstRow = false;
         }

         ctx.formatIndentLockEnd().end(Clause.TABLE_VALUES);
      }

   }

   final Fields<R> fields0() {
      return new Fields(this.rows[0].fields());
   }
}
