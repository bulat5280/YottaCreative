package org.jooq;

public interface WithAsStep5 {
   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep as(Select<? extends Record5<?, ?, ?, ?, ?>> var1);
}
