package org.jooq;

public interface DerivedColumnList2 extends QueryPart {
   <R extends Record2<?, ?>> CommonTableExpression<R> as(Select<R> var1);
}
