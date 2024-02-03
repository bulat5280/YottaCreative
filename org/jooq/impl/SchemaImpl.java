package org.jooq.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import org.jooq.Catalog;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.UDT;
import org.jooq.tools.StringUtils;

public class SchemaImpl extends AbstractQueryPart implements Schema {
   private static final long serialVersionUID = -8101463810207566546L;
   private static final Clause[] CLAUSES;
   private final Catalog catalog;
   private final String schemaName;

   SchemaImpl(Name name) {
      this(name.getName()[name.getName().length - 1], name.getName().length > 1 ? DSL.catalog(DSL.name(name.getName()[0])) : null);
   }

   public SchemaImpl(String name) {
      this(name, (Catalog)null);
   }

   public SchemaImpl(String name, Catalog catalog) {
      this.schemaName = name;
      this.catalog = catalog;
   }

   public Catalog getCatalog() {
      return this.catalog;
   }

   public final String getName() {
      return this.schemaName;
   }

   public final void accept(Context<?> ctx) {
      Catalog mappedCatalog = Tools.getMappedCatalog(ctx.configuration(), this.getCatalog());
      if (ctx.qualifyCatalog() && mappedCatalog != null) {
         ctx.visit(mappedCatalog);
         ctx.sql('.');
      }

      ctx.literal(this.getName());
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   public final Table<?> getTable(String name) {
      Iterator var2 = this.getTables().iterator();

      Table table;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         table = (Table)var2.next();
      } while(!table.getName().equals(name));

      return table;
   }

   public final UDT<?> getUDT(String name) {
      Iterator var2 = this.getUDTs().iterator();

      UDT udt;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         udt = (UDT)var2.next();
      } while(!udt.getName().equals(name));

      return udt;
   }

   public final Sequence<?> getSequence(String name) {
      Iterator var2 = this.getSequences().iterator();

      Sequence sequence;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         sequence = (Sequence)var2.next();
      } while(!sequence.getName().equals(name));

      return sequence;
   }

   public List<Table<?>> getTables() {
      return Collections.emptyList();
   }

   public List<UDT<?>> getUDTs() {
      return Collections.emptyList();
   }

   public List<Sequence<?>> getSequences() {
      return Collections.emptyList();
   }

   public final Stream<Table<?>> tableStream() {
      return this.getTables().stream();
   }

   public final Stream<UDT<?>> udtStream() {
      return this.getUDTs().stream();
   }

   public final Stream<Sequence<?>> sequenceStream() {
      return this.getSequences().stream();
   }

   public int hashCode() {
      return this.getName() != null ? this.getName().hashCode() : 0;
   }

   public boolean equals(Object that) {
      if (this == that) {
         return true;
      } else if (!(that instanceof SchemaImpl)) {
         return super.equals(that);
      } else {
         SchemaImpl other = (SchemaImpl)that;
         return StringUtils.equals(this.getCatalog(), other.getCatalog()) && StringUtils.equals(this.getName(), other.getName());
      }
   }

   static {
      CLAUSES = new Clause[]{Clause.SCHEMA, Clause.SCHEMA_REFERENCE};
   }
}
