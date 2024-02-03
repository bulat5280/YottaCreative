package org.jooq.impl;

import org.jooq.Context;
import org.jooq.QueryPart;

final class SQLCondition extends AbstractCondition {
   private static final long serialVersionUID = -7661748411414898501L;
   private final QueryPart delegate;

   SQLCondition(QueryPart delegate) {
      this.delegate = delegate;
   }

   public final void accept(Context<?> ctx) {
      ctx.sql('(');
      ctx.visit(this.delegate);
      ctx.sql(')');
   }
}
