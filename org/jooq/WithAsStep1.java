package org.jooq;

public interface WithAsStep1 {
   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep as(Select<? extends Record1<?>> var1);
}
