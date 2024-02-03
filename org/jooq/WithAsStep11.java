package org.jooq;

public interface WithAsStep11 {
   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep as(Select<? extends Record11<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>> var1);
}
