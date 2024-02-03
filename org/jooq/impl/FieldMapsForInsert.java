package org.jooq.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Select;

final class FieldMapsForInsert extends AbstractQueryPart {
   private static final long serialVersionUID = -6227074228534414225L;
   final List<FieldMapForInsert> insertMaps = new ArrayList();

   FieldMapsForInsert() {
      this.insertMaps.add((Object)null);
   }

   public final void accept(Context<?> ctx) {
      if (!this.isExecutable()) {
         ctx.formatSeparator().start(Clause.INSERT_VALUES).keyword("default values").end(Clause.INSERT_VALUES);
      } else if (this.insertMaps.size() != 1 && this.insertMaps.get(1) != null) {
         switch(ctx.family()) {
         case FIREBIRD:
         case SQLITE:
            ctx.formatSeparator().start(Clause.INSERT_SELECT);
            ctx.visit(this.insertSelect(ctx));
            ctx.end(Clause.INSERT_SELECT);
            break;
         default:
            ctx.formatSeparator().start(Clause.INSERT_VALUES).keyword("values").sql(' ');
            this.toSQL92Values(ctx);
            ctx.end(Clause.INSERT_VALUES);
         }
      } else {
         ctx.formatSeparator().start(Clause.INSERT_VALUES).keyword("values").sql(' ').visit((QueryPart)this.insertMaps.get(0)).end(Clause.INSERT_VALUES);
      }

   }

   private final Select<Record> insertSelect(Context<?> context) {
      Select<Record> select = null;
      Iterator var3 = this.insertMaps.iterator();

      while(var3.hasNext()) {
         FieldMapForInsert map = (FieldMapForInsert)var3.next();
         if (map != null) {
            Select<Record> iteration = DSL.using(context.configuration()).select(map.values());
            if (select == null) {
               select = iteration;
            } else {
               select = ((Select)select).unionAll(iteration);
            }
         }
      }

      return (Select)select;
   }

   private final void toSQL92Values(Context<?> context) {
      context.visit((QueryPart)this.insertMaps.get(0));
      int i = 0;

      for(Iterator var3 = this.insertMaps.iterator(); var3.hasNext(); ++i) {
         FieldMapForInsert map = (FieldMapForInsert)var3.next();
         if (map != null && i > 0) {
            context.sql(", ");
            context.visit(map);
         }
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   final boolean isExecutable() {
      return !this.insertMaps.isEmpty() && this.insertMaps.get(0) != null;
   }

   public final FieldMapForInsert getMap() {
      if (this.insertMaps.get(this.index()) == null) {
         this.insertMaps.set(this.index(), new FieldMapForInsert());
      }

      return (FieldMapForInsert)this.insertMaps.get(this.index());
   }

   public final void newRecord() {
      if (this.insertMaps.get(this.index()) != null) {
         this.insertMaps.add((Object)null);
      }

   }

   private final int index() {
      return this.insertMaps.size() - 1;
   }
}
