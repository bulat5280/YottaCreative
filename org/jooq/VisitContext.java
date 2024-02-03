package org.jooq;

public interface VisitContext extends Scope {
   Clause clause();

   Clause[] clauses();

   int clausesLength();

   QueryPart queryPart();

   void queryPart(QueryPart var1);

   QueryPart[] queryParts();

   int queryPartsLength();

   Context<?> context();

   RenderContext renderContext();

   BindContext bindContext() throws UnsupportedOperationException;
}
