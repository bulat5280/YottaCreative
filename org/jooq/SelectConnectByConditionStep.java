package org.jooq;

public interface SelectConnectByConditionStep<R extends Record> extends SelectStartWithStep<R> {
   @Support({SQLDialect.CUBRID})
   SelectConnectByConditionStep<R> and(Condition var1);

   @Support({SQLDialect.CUBRID})
   SelectConnectByConditionStep<R> and(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support({SQLDialect.CUBRID})
   SelectConnectByConditionStep<R> and(Boolean var1);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectConnectByConditionStep<R> and(SQL var1);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectConnectByConditionStep<R> and(String var1);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectConnectByConditionStep<R> and(String var1, Object... var2);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectConnectByConditionStep<R> and(String var1, QueryPart... var2);
}
