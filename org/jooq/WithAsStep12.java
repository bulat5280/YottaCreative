package org.jooq;

public interface WithAsStep12 {
   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep as(Select<? extends Record12<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>> var1);
}
