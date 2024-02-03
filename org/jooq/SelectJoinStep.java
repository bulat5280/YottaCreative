package org.jooq;

public interface SelectJoinStep<R extends Record> extends SelectWhereStep<R> {
   @Support
   SelectOptionalOnStep<R> join(TableLike<?> var1, JoinType var2);

   @Support
   SelectOnStep<R> join(TableLike<?> var1);

   @Support
   @PlainSQL
   SelectOnStep<R> join(SQL var1);

   @Support
   @PlainSQL
   SelectOnStep<R> join(String var1);

   @Support
   @PlainSQL
   SelectOnStep<R> join(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectOnStep<R> join(String var1, QueryPart... var2);

   @Support
   @PlainSQL
   SelectOnStep<R> join(Name var1);

   @Support
   SelectOnStep<R> innerJoin(TableLike<?> var1);

   @Support
   @PlainSQL
   SelectOnStep<R> innerJoin(SQL var1);

   @Support
   @PlainSQL
   SelectOnStep<R> innerJoin(String var1);

   @Support
   @PlainSQL
   SelectOnStep<R> innerJoin(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectOnStep<R> innerJoin(String var1, QueryPart... var2);

   @Support
   SelectOnStep<R> innerJoin(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   SelectJoinStep<R> crossJoin(TableLike<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   @PlainSQL
   SelectJoinStep<R> crossJoin(SQL var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   @PlainSQL
   SelectJoinStep<R> crossJoin(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   @PlainSQL
   SelectJoinStep<R> crossJoin(String var1, Object... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   @PlainSQL
   SelectJoinStep<R> crossJoin(String var1, QueryPart... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   SelectJoinStep<R> crossJoin(Name var1);

   @Support
   SelectJoinPartitionByStep<R> leftJoin(TableLike<?> var1);

   @Support
   @PlainSQL
   SelectJoinPartitionByStep<R> leftJoin(SQL var1);

   @Support
   @PlainSQL
   SelectJoinPartitionByStep<R> leftJoin(String var1);

   @Support
   @PlainSQL
   SelectJoinPartitionByStep<R> leftJoin(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectJoinPartitionByStep<R> leftJoin(String var1, QueryPart... var2);

   @Support
   SelectJoinPartitionByStep<R> leftJoin(Name var1);

   @Support
   SelectJoinPartitionByStep<R> leftOuterJoin(TableLike<?> var1);

   @Support
   @PlainSQL
   SelectJoinPartitionByStep<R> leftOuterJoin(SQL var1);

   @Support
   @PlainSQL
   SelectJoinPartitionByStep<R> leftOuterJoin(String var1);

   @Support
   @PlainSQL
   SelectJoinPartitionByStep<R> leftOuterJoin(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectJoinPartitionByStep<R> leftOuterJoin(String var1, QueryPart... var2);

   @Support
   SelectJoinPartitionByStep<R> leftOuterJoin(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   SelectJoinPartitionByStep<R> rightJoin(TableLike<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   SelectJoinPartitionByStep<R> rightJoin(SQL var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   SelectJoinPartitionByStep<R> rightJoin(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   SelectJoinPartitionByStep<R> rightJoin(String var1, Object... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   SelectJoinPartitionByStep<R> rightJoin(String var1, QueryPart... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   SelectJoinPartitionByStep<R> rightJoin(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   SelectJoinPartitionByStep<R> rightOuterJoin(TableLike<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   SelectJoinPartitionByStep<R> rightOuterJoin(SQL var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   SelectJoinPartitionByStep<R> rightOuterJoin(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   SelectJoinPartitionByStep<R> rightOuterJoin(String var1, Object... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   SelectJoinPartitionByStep<R> rightOuterJoin(String var1, QueryPart... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   SelectJoinPartitionByStep<R> rightOuterJoin(Name var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   SelectOnStep<R> fullOuterJoin(TableLike<?> var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   @PlainSQL
   SelectOnStep<R> fullOuterJoin(SQL var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   @PlainSQL
   SelectOnStep<R> fullOuterJoin(String var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   @PlainSQL
   SelectOnStep<R> fullOuterJoin(String var1, Object... var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   @PlainSQL
   SelectOnStep<R> fullOuterJoin(String var1, QueryPart... var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   SelectOnStep<R> fullOuterJoin(Name var1);

   @Support
   SelectJoinStep<R> naturalJoin(TableLike<?> var1);

   @Support
   @PlainSQL
   SelectJoinStep<R> naturalJoin(SQL var1);

   @Support
   @PlainSQL
   SelectJoinStep<R> naturalJoin(String var1);

   @Support
   @PlainSQL
   SelectJoinStep<R> naturalJoin(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectJoinStep<R> naturalJoin(String var1, QueryPart... var2);

   @Support
   SelectJoinStep<R> naturalJoin(Name var1);

   @Support
   SelectJoinStep<R> naturalLeftOuterJoin(TableLike<?> var1);

   @Support
   @PlainSQL
   SelectJoinStep<R> naturalLeftOuterJoin(SQL var1);

   @Support
   @PlainSQL
   SelectJoinStep<R> naturalLeftOuterJoin(String var1);

   @Support
   @PlainSQL
   SelectJoinStep<R> naturalLeftOuterJoin(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectJoinStep<R> naturalLeftOuterJoin(String var1, QueryPart... var2);

   @Support
   SelectJoinStep<R> naturalLeftOuterJoin(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   SelectJoinStep<R> naturalRightOuterJoin(TableLike<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   SelectJoinStep<R> naturalRightOuterJoin(SQL var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   SelectJoinStep<R> naturalRightOuterJoin(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   SelectJoinStep<R> naturalRightOuterJoin(String var1, Object... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   SelectJoinStep<R> naturalRightOuterJoin(String var1, QueryPart... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   SelectJoinStep<R> naturalRightOuterJoin(Name var1);

   @Support
   SelectOnStep<R> leftSemiJoin(TableLike<?> var1);

   @Support
   SelectOnStep<R> leftAntiJoin(TableLike<?> var1);

   @Support({SQLDialect.POSTGRES_9_3})
   SelectJoinStep<R> crossApply(TableLike<?> var1);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   SelectJoinStep<R> crossApply(SQL var1);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   SelectJoinStep<R> crossApply(String var1);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   SelectJoinStep<R> crossApply(String var1, Object... var2);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   SelectJoinStep<R> crossApply(String var1, QueryPart... var2);

   @Support({SQLDialect.POSTGRES_9_3})
   SelectJoinStep<R> crossApply(Name var1);

   @Support({SQLDialect.POSTGRES_9_3})
   SelectJoinStep<R> outerApply(TableLike<?> var1);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   SelectJoinStep<R> outerApply(SQL var1);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   SelectJoinStep<R> outerApply(String var1);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   SelectJoinStep<R> outerApply(String var1, Object... var2);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   SelectJoinStep<R> outerApply(String var1, QueryPart... var2);

   @Support({SQLDialect.POSTGRES_9_3})
   SelectJoinStep<R> outerApply(Name var1);

   @Support({SQLDialect.MYSQL})
   SelectOnStep<R> straightJoin(TableLike<?> var1);

   @Support({SQLDialect.MYSQL})
   @PlainSQL
   SelectOnStep<R> straightJoin(SQL var1);

   @Support({SQLDialect.MYSQL})
   @PlainSQL
   SelectOnStep<R> straightJoin(String var1);

   @Support({SQLDialect.MYSQL})
   @PlainSQL
   SelectOnStep<R> straightJoin(String var1, Object... var2);

   @Support({SQLDialect.MYSQL})
   @PlainSQL
   SelectOnStep<R> straightJoin(String var1, QueryPart... var2);

   @Support({SQLDialect.MYSQL})
   SelectOnStep<R> straightJoin(Name var1);
}
