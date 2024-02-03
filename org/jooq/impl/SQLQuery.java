package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;

final class SQLQuery extends AbstractQuery {
   private static final long serialVersionUID = 1740879770879469220L;
   private final QueryPart delegate;

   SQLQuery(Configuration configuration, QueryPart delegate) {
      super(configuration);
      this.delegate = delegate;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.delegate);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return this.delegate instanceof QueryPartInternal ? ((QueryPartInternal)this.delegate).clauses(ctx) : null;
   }
}
