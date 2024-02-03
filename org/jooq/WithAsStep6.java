package org.jooq;

public interface WithAsStep6 {
   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep as(Select<? extends Record6<?, ?, ?, ?, ?, ?>> var1);
}
