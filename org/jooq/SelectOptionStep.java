package org.jooq;

public interface SelectOptionStep<R extends Record> extends SelectUnionStep<R> {
   @Support
   SelectUnionStep<R> option(String var1);
}
