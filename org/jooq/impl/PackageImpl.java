package org.jooq.impl;

import org.jooq.Catalog;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Package;
import org.jooq.Schema;
import org.jooq.tools.StringUtils;

public class PackageImpl extends AbstractQueryPart implements Package {
   private static final long serialVersionUID = 7466890004995197675L;
   private final Schema schema;
   private final String name;

   public PackageImpl(String name, Schema schema) {
      this.schema = schema;
      this.name = name;
   }

   public final Catalog getCatalog() {
      return this.getSchema() == null ? null : this.getSchema().getCatalog();
   }

   public final Schema getSchema() {
      return this.schema;
   }

   public final String getName() {
      return this.name;
   }

   public final void accept(Context<?> ctx) {
      ctx.literal(this.getName());
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   public int hashCode() {
      return this.name != null ? this.name.hashCode() : 0;
   }

   public boolean equals(Object that) {
      if (this == that) {
         return true;
      } else if (!(that instanceof PackageImpl)) {
         return super.equals(that);
      } else {
         return StringUtils.equals(this.schema, ((PackageImpl)that).getSchema()) && StringUtils.equals(this.name, ((PackageImpl)that).name);
      }
   }
}
