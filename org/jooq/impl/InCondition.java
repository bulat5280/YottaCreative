package org.jooq.impl;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.tools.JooqLogger;

final class InCondition<T> extends AbstractCondition {
   private static final JooqLogger log = JooqLogger.getLogger(InCondition.class);
   private static final long serialVersionUID = -1653924248576930761L;
   private static final int IN_LIMIT = 1000;
   private static final Clause[] CLAUSES_IN;
   private static final Clause[] CLAUSES_IN_NOT;
   private final Field<T> field;
   private final Field<?>[] values;
   private final Comparator comparator;

   InCondition(Field<T> field, Field<?>[] values, Comparator comparator) {
      this.field = field;
      this.values = values;
      this.comparator = comparator;
   }

   public final Clause[] clauses(Context<?> ctx) {
      return this.comparator == Comparator.IN ? CLAUSES_IN : CLAUSES_IN_NOT;
   }

   public final void accept(Context<?> ctx) {
      List<Field<?>> list = Arrays.asList(this.values);
      if (list.size() == 0) {
         if (this.comparator == Comparator.IN) {
            ctx.visit(DSL.falseCondition());
         } else {
            ctx.visit(DSL.trueCondition());
         }
      } else if (list.size() > 1000) {
         switch(ctx.family()) {
         case FIREBIRD:
            ctx.sql('(').formatIndentStart().formatNewLine();

            for(int i = 0; i < list.size(); i += 1000) {
               if (i > 0) {
                  if (this.comparator == Comparator.IN) {
                     ctx.formatSeparator().keyword("or").sql(' ');
                  } else {
                     ctx.formatSeparator().keyword("and").sql(' ');
                  }
               }

               this.toSQLSubValues(ctx, padded(ctx, list.subList(i, Math.min(i + 1000, list.size()))));
            }

            ctx.formatIndentEnd().formatNewLine().sql(')');
            break;
         default:
            this.toSQLSubValues(ctx, list);
         }
      } else {
         this.toSQLSubValues(ctx, padded(ctx, list));
      }

   }

   private static List<Field<?>> padded(Context<?> ctx, List<Field<?>> list) {
      return (List)(ctx.paramType() == ParamType.INDEXED && Boolean.TRUE.equals(ctx.settings().isInListPadding()) ? new InCondition.PaddedList(list, Arrays.asList(SQLDialect.FIREBIRD).contains(ctx.family()) ? 1000 : Integer.MAX_VALUE) : list);
   }

   private void toSQLSubValues(Context<?> ctx, List<Field<?>> subValues) {
      ctx.visit(this.field).sql(' ').keyword(this.comparator.toSQL()).sql(" (");
      if (subValues.size() > 1) {
         ctx.formatIndentStart().formatNewLine();
      }

      String separator = "";

      for(Iterator var4 = subValues.iterator(); var4.hasNext(); separator = ", ") {
         Field<?> value = (Field)var4.next();
         ctx.sql(separator).formatNewLineAfterPrintMargin().visit(value);
      }

      if (subValues.size() > 1) {
         ctx.formatIndentEnd().formatNewLine();
      }

      ctx.sql(')');
   }

   static {
      CLAUSES_IN = new Clause[]{Clause.CONDITION, Clause.CONDITION_IN};
      CLAUSES_IN_NOT = new Clause[]{Clause.CONDITION, Clause.CONDITION_NOT_IN};
   }

   private static class PaddedList<T> extends AbstractList<T> {
      private final List<T> delegate;
      private final int realSize;
      private final int padSize;

      PaddedList(List<T> delegate, int maxPadding) {
         this.delegate = delegate;
         this.realSize = delegate.size();
         this.padSize = Math.min(maxPadding, this.realSize <= 0 ? 0 : (this.realSize <= 1 ? 1 : (this.realSize <= 2 ? 2 : (this.realSize <= 4 ? 4 : (this.realSize <= 8 ? 8 : (this.realSize <= 16 ? 16 : (this.realSize <= 32 ? 32 : (this.realSize <= 64 ? 64 : (this.realSize <= 128 ? 128 : (this.realSize <= 256 ? 256 : (this.realSize <= 512 ? 512 : (this.realSize <= 1024 ? 1024 : (this.realSize <= 2048 ? 2048 : (this.realSize <= 4096 ? 4096 : (this.realSize <= 8192 ? 8192 : (this.realSize <= 16384 ? 16384 : (this.realSize <= 32768 ? '耀' : (this.realSize <= 65536 ? 65536 : (this.realSize <= 131072 ? 131072 : (this.realSize <= 262144 ? 262144 : (this.realSize <= 524288 ? 524288 : (this.realSize <= 1048576 ? 1048576 : (this.realSize <= 2097152 ? 2097152 : (this.realSize <= 4194304 ? 4194304 : (this.realSize <= 8388608 ? 8388608 : (this.realSize <= 16777216 ? 16777216 : (this.realSize <= 33554432 ? 33554432 : (this.realSize <= 67108864 ? 67108864 : (this.realSize <= 134217728 ? 134217728 : (this.realSize <= 268435456 ? 268435456 : (this.realSize <= 536870912 ? 536870912 : (this.realSize <= 1073741824 ? 1073741824 : (this.realSize <= Integer.MIN_VALUE ? Integer.MIN_VALUE : this.realSize)))))))))))))))))))))))))))))))));
      }

      public T get(int index) {
         return index < this.realSize ? this.delegate.get(index) : this.delegate.get(this.realSize - 1);
      }

      public int size() {
         return this.padSize;
      }
   }
}
