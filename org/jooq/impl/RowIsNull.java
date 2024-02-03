package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPartInternal;
import org.jooq.Row;
import org.jooq.SQLDialect;

final class RowIsNull extends AbstractCondition {
   private static final long serialVersionUID = -1806139685201770706L;
   private static final Clause[] CLAUSES_NULL;
   private static final Clause[] CLAUSES_NOT_NULL;
   private final Row row;
   private final boolean isNull;

   RowIsNull(Row row, boolean isNull) {
      this.row = row;
      this.isNull = isNull;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.delegate(ctx.configuration()));
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   private final QueryPartInternal delegate(Configuration configuration) {
      if (Arrays.asList(SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLITE).contains(configuration.family())) {
         List<Condition> conditions = new ArrayList();
         Field[] var3 = this.row.fields();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Field<?> field = var3[var5];
            conditions.add(this.isNull ? field.isNull() : field.isNotNull());
         }

         Condition result = DSL.and((Collection)conditions);
         return (QueryPartInternal)result;
      } else {
         return new RowIsNull.Native();
      }
   }

   static {
      CLAUSES_NULL = new Clause[]{Clause.CONDITION, Clause.CONDITION_IS_NULL};
      CLAUSES_NOT_NULL = new Clause[]{Clause.CONDITION, Clause.CONDITION_IS_NOT_NULL};
   }

   private class Native extends AbstractCondition {
      private static final long serialVersionUID = -2977241780111574353L;

      private Native() {
      }

      public final void accept(Context<?> ctx) {
         ctx.visit(RowIsNull.this.row).sql(' ').keyword(RowIsNull.this.isNull ? "is null" : "is not null");
      }

      public final Clause[] clauses(Context<?> ctx) {
         return RowIsNull.this.isNull ? RowIsNull.CLAUSES_NULL : RowIsNull.CLAUSES_NOT_NULL;
      }

      // $FF: synthetic method
      Native(Object x1) {
         this();
      }
   }
}
