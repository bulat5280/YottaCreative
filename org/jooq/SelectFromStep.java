package org.jooq;

import java.util.Collection;

public interface SelectFromStep<R extends Record> extends SelectWhereStep<R> {
   @Support
   SelectJoinStep<R> from(TableLike<?> var1);

   @Support
   SelectJoinStep<R> from(TableLike<?>... var1);

   @Support
   SelectJoinStep<R> from(Collection<? extends TableLike<?>> var1);

   @Support
   @PlainSQL
   SelectJoinStep<R> from(SQL var1);

   @Support
   @PlainSQL
   SelectJoinStep<R> from(String var1);

   @Support
   @PlainSQL
   SelectJoinStep<R> from(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectJoinStep<R> from(String var1, QueryPart... var2);

   @Support
   SelectJoinStep<R> from(Name var1);

   @Support
   SelectFromStep<R> hint(String var1);
}
