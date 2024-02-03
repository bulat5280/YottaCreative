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
import org.jooq.tools.StringUtils;

public class CatalogImpl extends AbstractQueryPart implements Catalog {
   private static final long serialVersionUID = -3650318934053960244L;
   private static final Clause[] CLAUSES;
   private final String catalogName;

   CatalogImpl(Name name) {
      this(name.getName()[0]);
   }

   public CatalogImpl(String name) {
      this.catalogName = name;
   }

   public final String getName() {
      return this.catalogName;
   }

   public final void accept(Context<?> ctx) {
      ctx.literal(this.getName());
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   public final Schema getSchema(String name) {
      Iterator var2 = this.getSchemas().iterator();

      Schema schema;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         schema = (Schema)var2.next();
      } while(!schema.getName().equals(name));

      return schema;
   }

   public List<Schema> getSchemas() {
      return Collections.emptyList();
   }

   public final Stream<Schema> schemaStream() {
      return this.getSchemas().stream();
   }

   public int hashCode() {
      return this.getName() != null ? this.getName().hashCode() : 0;
   }

   public boolean equals(Object that) {
      if (this == that) {
         return true;
      } else {
         return that instanceof CatalogImpl ? StringUtils.equals(this.getName(), ((CatalogImpl)that).getName()) : super.equals(that);
      }
   }

   static {
      CLAUSES = new Clause[]{Clause.CATALOG, Clause.CATALOG_REFERENCE};
   }
}
