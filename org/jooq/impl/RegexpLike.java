package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;

final class RegexpLike extends AbstractCondition {
   private static final long serialVersionUID = 3162855665213654276L;
   private static final Clause[] CLAUSES;
   private final Field<?> search;
   private final Field<String> pattern;

   RegexpLike(Field<?> search, Field<String> pattern) {
      this.search = search;
      this.pattern = pattern;
   }

   public final void accept(Context<?> ctx) {
      switch(ctx.family()) {
      case CUBRID:
      case H2:
      case MARIADB:
      case MYSQL:
      case SQLITE:
         ctx.visit(this.search).sql(' ').keyword("regexp").sql(' ').visit(this.pattern);
         break;
      case HSQLDB:
         ctx.visit(DSL.condition("{regexp_matches}({0}, {1})", this.search, this.pattern));
         break;
      case POSTGRES:
         ctx.visit(DSL.condition("{0} ~ {1}", this.search, this.pattern));
         break;
      case DERBY:
      case FIREBIRD:
      default:
         ctx.visit(this.search).sql(' ').keyword("like_regex").sql(' ').visit(this.pattern);
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_COMPARISON};
   }
}
