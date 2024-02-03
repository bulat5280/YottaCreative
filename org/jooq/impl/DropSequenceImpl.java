package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DropSequenceFinalStep;
import org.jooq.SQLDialect;
import org.jooq.Sequence;

final class DropSequenceImpl extends AbstractQuery implements DropSequenceFinalStep {
   private static final long serialVersionUID = 8904572826501186329L;
   private static final Clause[] CLAUSES;
   private final Sequence<?> sequence;
   private final boolean ifExists;

   DropSequenceImpl(Configuration configuration, Sequence<?> sequence) {
      this(configuration, sequence, false);
   }

   DropSequenceImpl(Configuration configuration, Sequence<?> sequence, boolean ifExists) {
      super(configuration);
      this.sequence = sequence;
      this.ifExists = ifExists;
   }

   private final boolean supportsIfExists(Context<?> ctx) {
      return !Arrays.asList(SQLDialect.DERBY, SQLDialect.FIREBIRD).contains(ctx.family());
   }

   public final void accept(Context<?> ctx) {
      if (this.ifExists && !this.supportsIfExists(ctx)) {
         Tools.executeImmediateBegin(ctx, DDLStatementType.DROP_SEQUENCE);
         this.accept0(ctx);
         Tools.executeImmediateEnd(ctx, DDLStatementType.DROP_SEQUENCE);
      } else {
         this.accept0(ctx);
      }

   }

   private void accept0(Context<?> ctx) {
      ctx.start(Clause.DROP_SEQUENCE_SEQUENCE).keyword("drop").sql(' ').keyword(ctx.family() == SQLDialect.CUBRID ? "serial" : "sequence").sql(' ');
      if (this.ifExists && this.supportsIfExists(ctx)) {
         ctx.keyword("if exists").sql(' ');
      }

      switch(ctx.family()) {
      default:
         ctx.visit(this.sequence);
         if (ctx.family() == SQLDialect.DERBY) {
            ctx.sql(' ').keyword("restrict");
         }

         ctx.end(Clause.DROP_SEQUENCE_SEQUENCE);
      }
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.DROP_SEQUENCE};
   }
}
