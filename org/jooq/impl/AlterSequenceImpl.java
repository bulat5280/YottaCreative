package org.jooq.impl;

import org.jooq.AlterSequenceFinalStep;
import org.jooq.AlterSequenceStep;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Sequence;

final class AlterSequenceImpl<T extends Number> extends AbstractQuery implements AlterSequenceStep<T>, AlterSequenceFinalStep {
   private static final long serialVersionUID = 8904572826501186329L;
   private static final Clause[] CLAUSES;
   private final Sequence<T> sequence;
   private final boolean ifExists;
   private T restartWith;
   private Sequence<?> renameTo;

   AlterSequenceImpl(Configuration configuration, Sequence<T> sequence) {
      this(configuration, sequence, false);
   }

   AlterSequenceImpl(Configuration configuration, Sequence<T> sequence, boolean ifExists) {
      super(configuration);
      this.sequence = sequence;
      this.ifExists = ifExists;
   }

   public final AlterSequenceFinalStep restart() {
      return this;
   }

   public final AlterSequenceFinalStep restartWith(T value) {
      this.restartWith = value;
      return this;
   }

   public final AlterSequenceFinalStep renameTo(Sequence<?> newName) {
      this.renameTo = newName;
      return this;
   }

   public final AlterSequenceFinalStep renameTo(Name newName) {
      return this.renameTo(DSL.sequence(newName));
   }

   public final AlterSequenceFinalStep renameTo(String newName) {
      return this.renameTo(DSL.name(newName));
   }

   public final void accept(Context<?> ctx) {
      switch(ctx.family()) {
      default:
         this.accept0(ctx);
      }
   }

   private final void accept0(Context<?> ctx) {
      ctx.start(Clause.ALTER_SEQUENCE_SEQUENCE).keyword("alter").sql(' ').keyword(ctx.family() == SQLDialect.CUBRID ? "serial" : "sequence");
      if (this.ifExists) {
         ctx.sql(' ').keyword("if exists");
      }

      switch(ctx.family()) {
      default:
         ctx.sql(' ').visit(this.sequence);
         ctx.end(Clause.ALTER_SEQUENCE_SEQUENCE);
         if (this.renameTo != null) {
            boolean qualify = ctx.qualify();
            ctx.start(Clause.ALTER_SEQUENCE_RENAME).sql(' ').keyword("rename to").sql(' ').qualify(false).visit(this.renameTo).qualify(qualify).end(Clause.ALTER_SEQUENCE_RENAME);
         } else {
            ctx.start(Clause.ALTER_SEQUENCE_RESTART);
            T with = this.restartWith;
            if (with == null) {
               ctx.sql(' ').keyword("restart");
            } else if (ctx.family() == SQLDialect.CUBRID) {
               ctx.sql(' ').keyword("start with").sql(' ').sql(with.toString());
            } else {
               ctx.sql(' ').keyword("restart with").sql(' ').sql(with.toString());
            }

            ctx.end(Clause.ALTER_SEQUENCE_RESTART);
         }

      }
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.ALTER_SEQUENCE};
   }
}
