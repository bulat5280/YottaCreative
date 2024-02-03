package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.QuantifiedSelect;
import org.jooq.QueryPartInternal;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.SelectField;

final class QuantifiedSelectImpl<R extends Record> extends AbstractQueryPart implements QuantifiedSelect<R> {
   private static final long serialVersionUID = -1224570388944748450L;
   private final Quantifier quantifier;
   private final Select<R> query;
   private final Field<? extends Object[]> array;

   QuantifiedSelectImpl(Quantifier quantifier, Select<R> query) {
      this.quantifier = quantifier;
      this.query = query;
      this.array = null;
   }

   QuantifiedSelectImpl(Quantifier quantifier, Field<? extends Object[]> array) {
      this.quantifier = quantifier;
      this.query = null;
      this.array = array;
   }

   public final void accept(Context<?> ctx) {
      Object data = ctx.data(Tools.DataKey.DATA_ROW_VALUE_EXPRESSION_PREDICATE_SUBQUERY);
      boolean extraParentheses = data != null && Arrays.asList().contains(ctx.family());
      if (ctx.subquery()) {
         ctx.keyword(this.quantifier.toSQL()).sql(extraParentheses ? " ((" : " (").formatIndentStart().formatNewLine().visit(this.delegate(ctx.configuration())).formatIndentEnd().formatNewLine().sql(extraParentheses ? "))" : ")");
      } else {
         ctx.keyword(this.quantifier.toSQL()).sql(extraParentheses ? " ((" : " (").subquery(true).formatIndentStart().formatNewLine().visit(this.delegate(ctx.configuration())).formatIndentEnd().formatNewLine().subquery(false).sql(extraParentheses ? "))" : ")");
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   private final QueryPartInternal delegate(Configuration ctx) {
      if (this.query != null) {
         return (QueryPartInternal)this.query;
      } else {
         switch(ctx.family()) {
         case POSTGRES:
            return (QueryPartInternal)this.array;
         case H2:
         case HSQLDB:
            return (QueryPartInternal)this.create(ctx).select().from(DSL.table(this.array));
         default:
            if (this.array instanceof Param) {
               Object[] values = (Object[])((Param)this.array).getValue();
               Select<Record1<Object>> select = null;
               Object[] var4 = values;
               int var5 = values.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  Object value = var4[var6];
                  if (select == null) {
                     select = DSL.select((SelectField)DSL.val(value));
                  } else {
                     select = ((Select)select).unionAll(DSL.select((SelectField)DSL.val(value)));
                  }
               }

               return (QueryPartInternal)select;
            } else {
               return (QueryPartInternal)DSL.select().from(DSL.table(this.array));
            }
         }
      }
   }
}
