package org.jooq.impl;

import org.jooq.AlterSchemaFinalStep;
import org.jooq.AlterSchemaStep;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Name;
import org.jooq.Schema;

final class AlterSchemaImpl extends AbstractQuery implements AlterSchemaStep, AlterSchemaFinalStep {
   private static final long serialVersionUID = 8904572826501186329L;
   private static final Clause[] CLAUSES;
   private final Schema schema;
   private final boolean ifExists;
   private Schema renameTo;

   AlterSchemaImpl(Configuration configuration, Schema schema) {
      this(configuration, schema, false);
   }

   AlterSchemaImpl(Configuration configuration, Schema schema, boolean ifExists) {
      super(configuration);
      this.schema = schema;
      this.ifExists = ifExists;
   }

   public final AlterSchemaImpl renameTo(Schema newName) {
      this.renameTo = newName;
      return this;
   }

   public final AlterSchemaImpl renameTo(Name newName) {
      return this.renameTo(DSL.schema(newName));
   }

   public final AlterSchemaImpl renameTo(String newName) {
      return this.renameTo(DSL.name(newName));
   }

   public final void accept(Context<?> ctx) {
      ctx.start(Clause.ALTER_SCHEMA_SCHEMA).keyword("alter schema");
      if (this.ifExists) {
         ctx.sql(' ').keyword("if exists");
      }

      ctx.sql(' ').visit(this.schema).end(Clause.ALTER_SCHEMA_SCHEMA).formatIndentStart().formatSeparator();
      if (this.renameTo != null) {
         boolean qualify = ctx.qualify();
         ctx.start(Clause.ALTER_SCHEMA_RENAME).qualify(false).keyword("rename to").sql(' ').visit(this.renameTo).qualify(qualify).end(Clause.ALTER_SCHEMA_RENAME);
      }

      ctx.formatIndentEnd();
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.ALTER_SCHEMA};
   }
}
