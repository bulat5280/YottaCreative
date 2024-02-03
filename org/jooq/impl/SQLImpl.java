package org.jooq.impl;

import java.util.List;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.SQL;

final class SQLImpl extends AbstractQueryPart implements SQL {
   private static final long serialVersionUID = -7514156096865122018L;
   private static final Clause[] CLAUSES;
   private final String sql;
   private final List<QueryPart> substitutes;

   SQLImpl(String sql, Object... input) {
      this.sql = sql;
      this.substitutes = Tools.queryParts(input);
   }

   public final void accept(Context<?> ctx) {
      Tools.renderAndBind(ctx, this.sql, this.substitutes);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   public String toString() {
      return this.sql;
   }

   static {
      CLAUSES = new Clause[]{Clause.TEMPLATE};
   }
}
