package org.jooq.impl;

import org.jooq.AlterViewFinalStep;
import org.jooq.AlterViewStep;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Table;

final class AlterViewImpl extends AbstractQuery implements AlterViewStep, AlterViewFinalStep {
   private static final long serialVersionUID = 8904572826501186329L;
   private static final Clause[] CLAUSES;
   private final Table<?> view;
   private final boolean ifExists;
   private Table<?> renameTo;

   AlterViewImpl(Configuration configuration, Table<?> view) {
      this(configuration, view, false);
   }

   AlterViewImpl(Configuration configuration, Table<?> view, boolean ifExists) {
      super(configuration);
      this.view = view;
      this.ifExists = ifExists;
   }

   public final AlterViewImpl renameTo(Table<?> newName) {
      this.renameTo = newName;
      return this;
   }

   public final AlterViewImpl renameTo(Name newName) {
      return this.renameTo(DSL.table(newName));
   }

   public final AlterViewImpl renameTo(String newName) {
      return this.renameTo(DSL.name(newName));
   }

   public final void accept(Context<?> ctx) {
      switch(ctx.family()) {
      default:
         this.accept0(ctx);
      }
   }

   private final void accept0(Context<?> ctx) {
      ctx.start(Clause.ALTER_VIEW_VIEW).keyword("alter").sql(' ').keyword(ctx.family() == SQLDialect.HSQLDB ? "table" : "view");
      if (this.ifExists) {
         ctx.sql(' ').keyword("if exists");
      }

      ctx.sql(' ').visit(this.view).end(Clause.ALTER_VIEW_VIEW).formatIndentStart().formatSeparator();
      if (this.renameTo != null) {
         boolean qualify = ctx.qualify();
         ctx.start(Clause.ALTER_VIEW_RENAME).qualify(false).keyword("rename to").sql(' ').visit(this.renameTo).qualify(qualify).end(Clause.ALTER_VIEW_RENAME);
      }

      ctx.formatIndentEnd();
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.ALTER_VIEW};
   }
}
