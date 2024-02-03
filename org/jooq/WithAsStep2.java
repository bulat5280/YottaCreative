package org.jooq;

public interface WithAsStep2 {
   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep as(Select<? extends Record2<?, ?>> var1);
}
