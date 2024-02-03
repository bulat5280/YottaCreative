package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;

public abstract class CustomQueryPart extends AbstractQueryPart {
   private static final long serialVersionUID = -3439681086987884991L;
   private static final Clause[] CLAUSES;

   protected CustomQueryPart() {
   }

   public abstract void accept(Context<?> var1);

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   public final boolean declaresFields() {
      return super.declaresFields();
   }

   public final boolean declaresTables() {
      return super.declaresTables();
   }

   static {
      CLAUSES = new Clause[]{Clause.CUSTOM};
   }
}
