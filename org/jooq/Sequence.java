package org.jooq;

public interface Sequence<T extends Number> extends QueryPart {
   String getName();

   Catalog getCatalog();

   Schema getSchema();

   DataType<T> getDataType();

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.POSTGRES})
   Field<T> currval();

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   Field<T> nextval();
}
