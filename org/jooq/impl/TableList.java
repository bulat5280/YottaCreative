package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Table;

final class TableList extends QueryPartList<Table<?>> {
   private static final long serialVersionUID = -8545559185481762229L;

   TableList() {
   }

   TableList(List<? extends Table<?>> wrappedList) {
      super((Collection)wrappedList);
   }

   protected void toSQLEmptyList(Context<?> ctx) {
      ctx.visit(new Dual());
   }

   public final boolean declaresTables() {
      return true;
   }

   final void toSQLFields(Context<?> ctx) {
      String separator = "";
      boolean unqualified = Arrays.asList(SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB).contains(ctx.family());
      boolean qualify = ctx.qualify();
      if (unqualified) {
         ctx.qualify(false);
      }

      Iterator var5 = this.iterator();

      while(var5.hasNext()) {
         Table<?> table = (Table)var5.next();
         Field[] var7 = table.fieldsRow().fields();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Field<?> field = var7[var9];
            ctx.sql(separator);
            ctx.visit(field);
            separator = ", ";
         }
      }

      if (unqualified) {
         ctx.qualify(qualify);
      }

   }
}
