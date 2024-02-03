package org.jooq;

import java.util.Collection;
import org.jooq.exception.DataAccessException;

public interface TableOnStep<R extends Record> {
   @Support
   TableOnConditionStep<R> on(Condition... var1);

   @Support
   TableOnConditionStep<R> on(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   TableOnConditionStep<R> on(Boolean var1);

   @Support
   @PlainSQL
   TableOnConditionStep<R> on(SQL var1);

   @Support
   @PlainSQL
   TableOnConditionStep<R> on(String var1);

   @Support
   @PlainSQL
   TableOnConditionStep<R> on(String var1, Object... var2);

   @Support
   @PlainSQL
   TableOnConditionStep<R> on(String var1, QueryPart... var2);

   @Support
   Table<Record> using(Field<?>... var1);

   @Support
   Table<Record> using(Collection<? extends Field<?>> var1);

   @Support
   TableOnConditionStep<R> onKey() throws DataAccessException;

   @Support
   TableOnConditionStep<R> onKey(TableField<?, ?>... var1) throws DataAccessException;

   @Support
   TableOnConditionStep<R> onKey(ForeignKey<?, ?> var1);
}
