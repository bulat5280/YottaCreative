package org.jooq;

public interface WithAsStep7 {
   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep as(Select<? extends Record7<?, ?, ?, ?, ?, ?, ?>> var1);
}
