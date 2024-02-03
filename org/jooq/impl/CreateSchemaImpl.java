package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateSchemaFinalStep;
import org.jooq.Record;
import org.jooq.Schema;

final class CreateSchemaImpl<R extends Record> extends AbstractQuery implements CreateSchemaFinalStep {
   private static final long serialVersionUID = 8904572826501186329L;
   private static final Clause[] CLAUSES;
   private final Schema schema;
   private final boolean ifNotExists;

   CreateSchemaImpl(Configuration configuration, Schema schema, boolean ifNotExists) {
      super(configuration);
      this.schema = schema;
      this.ifNotExists = ifNotExists;
   }

   public final void accept(Context<?> ctx) {
      ctx.start(Clause.CREATE_SCHEMA_NAME).keyword("create schema");
      if (this.ifNotExists) {
         ctx.sql(' ').keyword("if not exists");
      }

      ctx.sql(' ').visit(this.schema).end(Clause.CREATE_SCHEMA_NAME);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.CREATE_SCHEMA};
   }
}
