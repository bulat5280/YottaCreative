package org.jooq;

public interface SelectConnectByStep<R extends Record> extends SelectGroupByStep<R> {
   @Support({SQLDialect.CUBRID})
   SelectConnectByConditionStep<R> connectBy(Condition var1);

   @Support({SQLDialect.CUBRID})
   SelectConnectByConditionStep<R> connectBy(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support({SQLDialect.CUBRID})
   SelectConnectByConditionStep<R> connectBy(Boolean var1);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectConnectByConditionStep<R> connectBy(SQL var1);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectConnectByConditionStep<R> connectBy(String var1);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectConnectByConditionStep<R> connectBy(String var1, Object... var2);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectConnectByConditionStep<R> connectBy(String var1, QueryPart... var2);

   @Support({SQLDialect.CUBRID})
   SelectConnectByConditionStep<R> connectByNoCycle(Condition var1);

   @Support({SQLDialect.CUBRID})
   SelectConnectByConditionStep<R> connectByNoCycle(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support({SQLDialect.CUBRID})
   SelectConnectByConditionStep<R> connectByNoCycle(Boolean var1);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectConnectByConditionStep<R> connectByNoCycle(SQL var1);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectConnectByConditionStep<R> connectByNoCycle(String var1);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectConnectByConditionStep<R> connectByNoCycle(String var1, Object... var2);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectConnectByConditionStep<R> connectByNoCycle(String var1, QueryPart... var2);
}
