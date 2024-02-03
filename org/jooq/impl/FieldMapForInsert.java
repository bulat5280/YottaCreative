package org.jooq.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;

final class FieldMapForInsert extends AbstractQueryPartMap<Field<?>, Field<?>> {
   private static final long serialVersionUID = -2192833491610583485L;
   private static final Clause[] CLAUSES;

   public final void accept(Context<?> ctx) {
      boolean indent = this.size() > 1;
      ctx.sql('(');
      if (indent) {
         ctx.formatIndentStart();
      }

      String separator = "";

      for(Iterator var4 = this.values().iterator(); var4.hasNext(); separator = ", ") {
         Field<?> field = (Field)var4.next();
         ctx.sql(separator);
         if (indent) {
            ctx.formatNewLine();
         }

         ctx.visit(field);
      }

      if (indent) {
         ctx.formatIndentEnd().formatNewLine();
      }

      ctx.sql(')');
   }

   final void toSQLReferenceKeys(Context<?> ctx) {
      if (this.size() != 0) {
         boolean indent = this.size() > 1;
         ctx.sql(" (");
         if (indent) {
            ctx.formatIndentStart();
         }

         boolean qualify = ctx.qualify();
         ctx.qualify(false);
         String separator = "";

         for(Iterator var5 = this.keySet().iterator(); var5.hasNext(); separator = ", ") {
            Field<?> field = (Field)var5.next();
            ctx.sql(separator);
            if (indent) {
               ctx.formatNewLine();
            }

            ctx.visit(field);
         }

         ctx.qualify(qualify);
         if (indent) {
            ctx.formatIndentEnd().formatNewLine();
         }

         ctx.sql(')');
      }
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   final void putFields(Collection<? extends Field<?>> fields) {
      Iterator var2 = fields.iterator();

      while(var2.hasNext()) {
         Field<?> field = (Field)var2.next();
         this.put(field, (QueryPart)null);
      }

   }

   final void putValues(Collection<? extends Field<?>> values) {
      if (values.size() != this.size()) {
         throw new IllegalArgumentException("The number of values must match the number of fields: " + this);
      } else {
         Iterator<? extends Field<?>> it = values.iterator();
         Iterator var3 = this.entrySet().iterator();

         while(var3.hasNext()) {
            Entry<Field<?>, Field<?>> entry = (Entry)var3.next();
            entry.setValue(it.next());
         }

      }
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

   static {
      CLAUSES = new Clause[]{Clause.FIELD_ROW};
   }
}
