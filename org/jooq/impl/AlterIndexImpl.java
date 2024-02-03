package org.jooq.impl;

import org.jooq.AlterIndexFinalStep;
import org.jooq.AlterIndexStep;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Name;

final class AlterIndexImpl extends AbstractQuery implements AlterIndexStep, AlterIndexFinalStep {
   private static final long serialVersionUID = 8904572826501186329L;
   private static final Clause[] CLAUSES;
   private final Name index;
   private final boolean ifExists;
   private Name renameTo;

   AlterIndexImpl(Configuration configuration, Name index) {
      this(configuration, index, false);
   }

   AlterIndexImpl(Configuration configuration, Name index, boolean ifExists) {
      super(configuration);
      this.index = index;
      this.ifExists = ifExists;
   }

   public final AlterIndexImpl renameTo(Name newName) {
      this.renameTo = newName;
      return this;
   }

   public final AlterIndexImpl renameTo(String newName) {
      return this.renameTo(DSL.name(newName));
   }

   public final void accept(Context<?> ctx) {
      ctx.start(Clause.ALTER_INDEX_INDEX).keyword("alter index");
      if (this.ifExists) {
         ctx.sql(' ').keyword("if exists");
      }

      ctx.sql(' ').visit(this.index).end(Clause.ALTER_INDEX_INDEX).formatIndentStart().formatSeparator();
      if (this.renameTo != null) {
         boolean qualify = ctx.qualify();
         ctx.start(Clause.ALTER_INDEX_RENAME).qualify(false).keyword("rename to").sql(' ').visit(this.renameTo).qualify(qualify).end(Clause.ALTER_INDEX_RENAME);
      }

      ctx.formatIndentEnd();
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.ALTER_INDEX};
   }
}
