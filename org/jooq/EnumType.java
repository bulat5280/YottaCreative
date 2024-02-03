package org.jooq;

public interface EnumType {
   String getLiteral();

   Catalog getCatalog();

   Schema getSchema();

   String getName();
}
