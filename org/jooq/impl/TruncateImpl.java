package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TruncateCascadeStep;
import org.jooq.TruncateFinalStep;
import org.jooq.TruncateIdentityStep;

final class TruncateImpl<R extends Record> extends AbstractQuery implements TruncateIdentityStep<R> {
   private static final long serialVersionUID = 8904572826501186329L;
   private static final Clause[] CLAUSES;
   private final Table<R> table;
   private Boolean cascade;
   private Boolean restartIdentity;

   public TruncateImpl(Configuration configuration, Table<R> table) {
      super(configuration);
      this.table = table;
   }

   public final TruncateFinalStep<R> cascade() {
      this.cascade = true;
      return this;
   }

   public final TruncateFinalStep<R> restrict() {
      this.cascade = false;
      return this;
   }

   public final TruncateCascadeStep<R> restartIdentity() {
      this.restartIdentity = true;
      return this;
   }

   public final TruncateCascadeStep<R> continueIdentity() {
      this.restartIdentity = false;
      return this;
   }

   public final void accept(Context<?> ctx) {
      switch(ctx.family()) {
      case FIREBIRD:
      case SQLITE:
         ctx.visit(this.create(ctx).delete(this.table));
         break;
      default:
         ctx.start(Clause.TRUNCATE_TRUNCATE).keyword("truncate table").sql(' ').visit(this.table);
         if (this.restartIdentity != null) {
            ctx.formatSeparator().keyword(this.restartIdentity ? "restart identity" : "continue identity");
         }

         if (this.cascade != null) {
            ctx.formatSeparator().keyword(this.cascade ? "cascade" : "restrict");
         }

         ctx.end(Clause.TRUNCATE_TRUNCATE);
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.TRUNCATE};
   }
}
