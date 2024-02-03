package org.jooq.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;

final class FieldMapForUpdate extends AbstractQueryPartMap<Field<?>, Field<?>> {
   private static final long serialVersionUID = -6139709404698673799L;
   private final Clause assignmentClause;

   FieldMapForUpdate(Clause assignmentClause) {
      this.assignmentClause = assignmentClause;
   }

   public final void accept(Context<?> ctx) {
      if (this.size() > 0) {
         String separator = "";
         boolean restoreQualify = ctx.qualify();
         boolean supportsQualify = Arrays.asList(SQLDialect.POSTGRES, SQLDialect.SQLITE).contains(ctx.family()) ? false : restoreQualify;

         for(Iterator var5 = this.entrySet().iterator(); var5.hasNext(); separator = ", ") {
            Entry<Field<?>, Field<?>> entry = (Entry)var5.next();
            ctx.sql(separator);
            if (!"".equals(separator)) {
               ctx.formatNewLine();
            }

            ctx.start(this.assignmentClause).qualify(supportsQualify).visit((QueryPart)entry.getKey()).qualify(restoreQualify).sql(" = ").visit((QueryPart)entry.getValue()).end(this.assignmentClause);
         }
      } else {
         ctx.sql("[ no fields are updated ]");
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   final void set(Map<? extends Field<?>, ?> map) {
      Iterator var2 = map.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<? extends Field<?>, ?> entry = (Entry)var2.next();
         Field<?> field = (Field)entry.getKey();
         Object value = entry.getValue();
         this.put((QueryPart)entry.getKey(), Tools.field(value, field));
      }

   }
}
