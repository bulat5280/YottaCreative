package org.jooq;

public interface DerivedColumnList3 extends QueryPart {
   <R extends Record3<?, ?, ?>> CommonTableExpression<R> as(Select<R> var1);
}
