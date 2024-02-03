package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.FieldOrRow;
import org.jooq.QueryPart;

final class Rollup extends AbstractField<Object> {
   private static final long serialVersionUID = -5820608758939548704L;
   private QueryPartList<FieldOrRow> arguments;

   Rollup(FieldOrRow... arguments) {
      super("rollup", SQLDataType.OTHER);
      this.arguments = new QueryPartList(arguments);
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.delegate(ctx.configuration()));
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   private final QueryPart delegate(Configuration configuration) {
      switch(configuration.family()) {
      case CUBRID:
      case MARIADB:
      case MYSQL:
         return DSL.field("{0} {with rollup}", this.arguments);
      default:
         return DSL.field("{rollup}({0})", Object.class, this.arguments);
      }
   }
}
