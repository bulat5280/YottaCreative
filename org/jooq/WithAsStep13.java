package org.jooq;

public interface WithAsStep13 {
   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep as(Select<? extends Record13<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>> var1);
}
