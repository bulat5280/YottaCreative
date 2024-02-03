package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.conf.ParamType;

final class SQLInline extends AbstractQueryPart implements SQL {
   private static final long serialVersionUID = 5352233054249655126L;
   private SQL sql;

   SQLInline(QueryPart part) {
      this(DSL.sql("{0}", part));
   }

   SQLInline(SQL sql) {
      this.sql = sql;
   }

   public final void accept(Context<?> ctx) {
      ParamType paramType = ctx.paramType();
      ctx.paramType(ParamType.INLINED).visit(this.sql).paramType(paramType);
   }

   public Clause[] clauses(Context<?> ctx) {
      return null;
   }

   public String toString() {
      return this.sql.toString();
   }
}
