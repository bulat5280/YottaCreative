package org.jooq;

public interface DerivedColumnList4 extends QueryPart {
   <R extends Record4<?, ?, ?, ?>> CommonTableExpression<R> as(Select<R> var1);
}
