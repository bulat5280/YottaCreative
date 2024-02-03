package org.jooq;

public interface WithAsStep4 {
   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep as(Select<? extends Record4<?, ?, ?, ?>> var1);
}
