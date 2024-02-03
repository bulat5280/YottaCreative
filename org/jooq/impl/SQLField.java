package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.QueryPart;

final class SQLField<T> extends AbstractField<T> {
   private static final long serialVersionUID = 6937002867156868761L;
   private final QueryPart delegate;

   SQLField(DataType<T> type, QueryPart delegate) {
      super(delegate.toString(), type);
      this.delegate = delegate;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.delegate);
   }
}
