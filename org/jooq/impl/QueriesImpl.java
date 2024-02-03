package org.jooq.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;
import org.jooq.Queries;
import org.jooq.Query;

final class QueriesImpl implements Queries {
   private final Collection<? extends Query> queries;

   QueriesImpl(Collection<? extends Query> queries) {
      this.queries = queries;
   }

   public final Query[] queries() {
      return (Query[])this.queries.toArray(Tools.EMPTY_QUERY);
   }

   public final Iterator<Query> iterator() {
      return this.queries.iterator();
   }

   public final Stream<Query> stream() {
      return this.queries.stream();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      Iterator var2 = this.queries.iterator();

      while(var2.hasNext()) {
         Query query = (Query)var2.next();
         sb.append(query).append(";\n");
      }

      return sb.toString();
   }
}
