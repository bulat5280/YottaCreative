package org.jooq;

import java.util.Collection;
import org.jooq.exception.DataAccessException;

public interface SelectOnStep<R extends Record> {
   @Support
   SelectOnConditionStep<R> on(Condition... var1);

   @Support
   SelectOnConditionStep<R> on(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   SelectOnConditionStep<R> on(Boolean var1);

   @Support
   @PlainSQL
   SelectOnConditionStep<R> on(SQL var1);

   @Support
   @PlainSQL
   SelectOnConditionStep<R> on(String var1);

   @Support
   @PlainSQL
   SelectOnConditionStep<R> on(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectOnConditionStep<R> on(String var1, QueryPart... var2);

   @Support
   SelectJoinStep<R> onKey() throws DataAccessException;

   @Support
   SelectJoinStep<R> onKey(TableField<?, ?>... var1) throws DataAccessException;

   @Support
   SelectJoinStep<R> onKey(ForeignKey<?, ?> var1);

   @Support
   SelectJoinStep<R> using(Field<?>... var1);

   @Support
   SelectJoinStep<R> using(Collection<? extends Field<?>> var1);
}
