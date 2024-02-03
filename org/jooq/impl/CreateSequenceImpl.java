package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateSequenceFinalStep;
import org.jooq.SQLDialect;
import org.jooq.Sequence;

final class CreateSequenceImpl extends AbstractQuery implements CreateSequenceFinalStep {
   private static final long serialVersionUID = 8904572826501186329L;
   private static final Clause[] CLAUSES;
   private final Sequence<?> sequence;
   private final boolean ifNotExists;

   CreateSequenceImpl(Configuration configuration, Sequence<?> sequence, boolean ifNotExists) {
      super(configuration);
      this.sequence = sequence;
      this.ifNotExists = ifNotExists;
   }

   private final boolean supportsIfNotExists(Context<?> ctx) {
      return !Arrays.asList(SQLDialect.DERBY, SQLDialect.FIREBIRD).contains(ctx.family());
   }

   public final void accept(Context<?> ctx) {
      if (this.ifNotExists && !this.supportsIfNotExists(ctx)) {
         Tools.executeImmediateBegin(ctx, DDLStatementType.CREATE_SEQUENCE);
         this.accept0(ctx);
         Tools.executeImmediateEnd(ctx, DDLStatementType.CREATE_SEQUENCE);
      } else {
         this.accept0(ctx);
      }

   }

   private final void accept0(Context<?> ctx) {
      ctx.start(Clause.CREATE_SEQUENCE_SEQUENCE).keyword("create").sql(' ').keyword(ctx.family() == SQLDialect.CUBRID ? "serial" : "sequence").sql(' ');
      if (this.ifNotExists && this.supportsIfNotExists(ctx)) {
         ctx.keyword("if not exists").sql(' ');
      }

      ctx.visit(this.sequence);
      if (Arrays.asList(SQLDialect.DERBY).contains(ctx.family())) {
         ctx.sql(' ').keyword("start with").sql(" 1");
      }

      ctx.end(Clause.CREATE_SEQUENCE_SEQUENCE);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.CREATE_SEQUENCE};
   }
}
