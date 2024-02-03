package org.jooq;

public interface WithAsStep9 {
   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep as(Select<? extends Record9<?, ?, ?, ?, ?, ?, ?, ?, ?>> var1);
}
