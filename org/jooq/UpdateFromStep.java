package org.jooq;

import java.util.Collection;

public interface UpdateFromStep<R extends Record> extends UpdateWhereStep<R> {
   @Support({SQLDialect.POSTGRES})
   UpdateWhereStep<R> from(TableLike<?> var1);

   @Support({SQLDialect.POSTGRES})
   UpdateWhereStep<R> from(TableLike<?>... var1);

   @Support({SQLDialect.POSTGRES})
   UpdateWhereStep<R> from(Collection<? extends TableLike<?>> var1);

   @Support({SQLDialect.POSTGRES})
   @PlainSQL
   UpdateWhereStep<R> from(SQL var1);

   @Support({SQLDialect.POSTGRES})
   @PlainSQL
   UpdateWhereStep<R> from(String var1);

   @Support({SQLDialect.POSTGRES})
   @PlainSQL
   UpdateWhereStep<R> from(String var1, Object... var2);

   @Support({SQLDialect.POSTGRES})
   @PlainSQL
   UpdateWhereStep<R> from(String var1, QueryPart... var2);

   @Support({SQLDialect.POSTGRES})
   UpdateWhereStep<R> from(Name var1);
}
