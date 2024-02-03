package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPartInternal;

final class FieldCondition extends AbstractCondition {
   private static final long serialVersionUID = -9170915951443879057L;
   private final Field<Boolean> field;

   FieldCondition(Field<Boolean> field) {
      this.field = field;
   }

   public void accept(Context<?> ctx) {
      ctx.visit(this.delegate(ctx.configuration()));
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   private final QueryPartInternal delegate(Configuration configuration) {
      switch(configuration.family()) {
      case CUBRID:
      case FIREBIRD:
         return (QueryPartInternal)DSL.condition("{0} = {1}", this.field, DSL.inline(true));
      case DERBY:
      case H2:
      case HSQLDB:
      case MARIADB:
      case MYSQL:
      case POSTGRES:
      case SQLITE:
      default:
         return (QueryPartInternal)this.field;
      }
   }
}
