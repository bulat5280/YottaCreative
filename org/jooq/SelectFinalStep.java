package org.jooq;

public interface SelectFinalStep<R extends Record> extends Select<R> {
   SelectQuery<R> getQuery();
}
