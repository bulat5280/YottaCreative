package org.jooq;

public interface WithAsStep {
   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep as(Select<?> var1);
}
