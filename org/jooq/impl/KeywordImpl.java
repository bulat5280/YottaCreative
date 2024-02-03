package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Keyword;

public class KeywordImpl extends AbstractQueryPart implements Keyword {
   private static final long serialVersionUID = 9137269798087732005L;
   private final String keyword;

   KeywordImpl(String keyword) {
      this.keyword = keyword;
   }

   public final void accept(Context<?> ctx) {
      ctx.keyword(this.keyword);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }
}
