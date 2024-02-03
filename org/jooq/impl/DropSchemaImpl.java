package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DropSchemaFinalStep;
import org.jooq.DropSchemaStep;
import org.jooq.SQLDialect;
import org.jooq.Schema;

final class DropSchemaImpl extends AbstractQuery implements DropSchemaStep {
   private static final long serialVersionUID = 8904572826501186329L;
   private static final Clause[] CLAUSES;
   private final Schema schema;
   private final boolean ifExists;
   private boolean cascade;

   DropSchemaImpl(Configuration configuration, Schema schema) {
      this(configuration, schema, false);
   }

   DropSchemaImpl(Configuration configuration, Schema schema, boolean ifExists) {
      super(configuration);
      this.schema = schema;
      this.ifExists = ifExists;
   }

   public final DropSchemaFinalStep cascade() {
      this.cascade = true;
      return this;
   }

   public final DropSchemaFinalStep restrict() {
      this.cascade = false;
      return this;
   }

   private final boolean supportsIfExists(Context<?> ctx) {
      return !Arrays.asList(SQLDialect.DERBY, SQLDialect.FIREBIRD).contains(ctx.family());
   }

   public final void accept(Context<?> ctx) {
      if (this.ifExists && !this.supportsIfExists(ctx)) {
         Tools.executeImmediateBegin(ctx, DDLStatementType.DROP_SCHEMA);
         this.accept0(ctx);
         Tools.executeImmediateEnd(ctx, DDLStatementType.DROP_SCHEMA);
      } else {
         this.accept0(ctx);
      }

   }

   private void accept0(Context<?> ctx) {
      ctx.start(Clause.DROP_SCHEMA_SCHEMA).keyword("drop schema");
      if (this.ifExists && this.supportsIfExists(ctx)) {
         ctx.sql(' ').keyword("if exists");
      }

      ctx.sql(' ').visit(this.schema);
      if (this.cascade) {
         ctx.sql(' ').keyword("cascade");
      }

      ctx.end(Clause.DROP_SCHEMA_SCHEMA);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.DROP_SCHEMA};
   }
}
