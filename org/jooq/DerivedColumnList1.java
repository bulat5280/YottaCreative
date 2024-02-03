package org.jooq;

public interface DerivedColumnList1 extends QueryPart {
   <R extends Record1<?>> CommonTableExpression<R> as(Select<R> var1);
}
