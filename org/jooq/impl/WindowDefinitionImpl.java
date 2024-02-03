package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.WindowDefinition;
import org.jooq.WindowSpecification;

final class WindowDefinitionImpl extends AbstractQueryPart implements WindowDefinition {
   private static final long serialVersionUID = -7779419148766154430L;
   private final Name name;
   private final WindowSpecification window;

   WindowDefinitionImpl(Name name, WindowSpecification window) {
      this.name = name;
      this.window = window;
   }

   final Name getName() {
      return this.name;
   }

   public final void accept(Context<?> ctx) {
      if (ctx.declareWindows()) {
         ctx.visit(this.name).sql(' ').keyword("as").sql(" (").visit(this.window).sql(')');
      } else if (Arrays.asList(ctx.family()).contains(SQLDialect.POSTGRES)) {
         ctx.visit(this.name);
      } else {
         ctx.visit(this.window);
      }

   }

   public final boolean declaresWindows() {
      return true;
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }
}
