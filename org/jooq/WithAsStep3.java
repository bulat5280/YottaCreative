package org.jooq;

public interface WithAsStep3 {
   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep as(Select<? extends Record3<?, ?, ?>> var1);
}
