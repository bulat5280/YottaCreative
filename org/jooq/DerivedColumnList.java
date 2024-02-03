package org.jooq;

public interface DerivedColumnList extends QueryPart {
   <R extends Record> CommonTableExpression<R> as(Select<R> var1);
}
