package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;

final class WrappedList extends AbstractQueryPart {
   private static final long serialVersionUID = -6722573826107165561L;
   private final QueryPartList<?> wrapped;

   WrappedList(QueryPartList<?> wrapped) {
      this.wrapped = wrapped;
   }

   public final void accept(Context<?> ctx) {
      ctx.sql('(').visit(this.wrapped).sql(')');
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }
}
