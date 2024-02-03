package org.jooq;

public interface Package extends QueryPart {
   Catalog getCatalog();

   Schema getSchema();

   String getName();
}
