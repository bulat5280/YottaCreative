package org.jooq;

public interface WithAsStep8 {
   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep as(Select<? extends Record8<?, ?, ?, ?, ?, ?, ?, ?>> var1);
}
