package org.jooq;

public interface WithAsStep10 {
   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep as(Select<? extends Record10<?, ?, ?, ?, ?, ?, ?, ?, ?, ?>> var1);
}
