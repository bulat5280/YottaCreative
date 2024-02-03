package org.jooq;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Table<R extends Record> extends TableLike<R> {
   Catalog getCatalog();

   Schema getSchema();

   String getName();

   String getComment();

   RecordType<R> recordType();

   Class<? extends R> getRecordType();

   DataType<R> getDataType();

   R newRecord();

   Identity<R, ?> getIdentity();

   UniqueKey<R> getPrimaryKey();

   TableField<R, ?> getRecordVersion();

   TableField<R, ?> getRecordTimestamp();

   List<UniqueKey<R>> getKeys();

   <O extends Record> List<ForeignKey<O, R>> getReferencesFrom(Table<O> var1);

   List<ForeignKey<R, ?>> getReferences();

   <O extends Record> List<ForeignKey<R, O>> getReferencesTo(Table<O> var1);

   @Support
   Table<R> as(String var1);

   @Support
   Table<R> as(String var1, String... var2);

   @Support
   Table<R> as(String var1, Function<? super Field<?>, ? extends String> var2);

   @Support
   Table<R> as(String var1, BiFunction<? super Field<?>, ? super Integer, ? extends String> var2);

   @Support
   Table<R> as(Table<?> var1);

   @Support
   Table<R> as(Table<?> var1, Field<?>... var2);

   @Support
   Table<R> as(Table<?> var1, Function<? super Field<?>, ? extends Field<?>> var2);

   @Support
   Table<R> as(Table<?> var1, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> var2);

   @Support
   TableOptionalOnStep<Record> join(TableLike<?> var1, JoinType var2);

   @Support
   TableOnStep<Record> join(TableLike<?> var1);

   @Support
   @PlainSQL
   TableOnStep<Record> join(SQL var1);

   @Support
   @PlainSQL
   TableOnStep<Record> join(String var1);

   @Support
   @PlainSQL
   TableOnStep<Record> join(String var1, Object... var2);

   @Support
   @PlainSQL
   TableOnStep<Record> join(String var1, QueryPart... var2);

   @Support
   @PlainSQL
   TableOnStep<Record> join(Name var1);

   @Support
   TableOnStep<Record> innerJoin(TableLike<?> var1);

   @Support
   @PlainSQL
   TableOnStep<Record> innerJoin(SQL var1);

   @Support
   @PlainSQL
   TableOnStep<Record> innerJoin(String var1);

   @Support
   @PlainSQL
   TableOnStep<Record> innerJoin(String var1, Object... var2);

   @Support
   @PlainSQL
   TableOnStep<Record> innerJoin(String var1, QueryPart... var2);

   @Support
   TableOnStep<Record> innerJoin(Name var1);

   @Support
   TablePartitionByStep<Record> leftJoin(TableLike<?> var1);

   @Support
   @PlainSQL
   TablePartitionByStep<Record> leftJoin(SQL var1);

   @Support
   @PlainSQL
   TablePartitionByStep<Record> leftJoin(String var1);

   @Support
   @PlainSQL
   TablePartitionByStep<Record> leftJoin(String var1, Object... var2);

   @Support
   @PlainSQL
   TablePartitionByStep<Record> leftJoin(String var1, QueryPart... var2);

   @Support
   TablePartitionByStep<Record> leftJoin(Name var1);

   @Support
   TablePartitionByStep<Record> leftOuterJoin(TableLike<?> var1);

   @Support
   @PlainSQL
   TablePartitionByStep<Record> leftOuterJoin(SQL var1);

   @Support
   @PlainSQL
   TablePartitionByStep<Record> leftOuterJoin(String var1);

   @Support
   @PlainSQL
   TablePartitionByStep<Record> leftOuterJoin(String var1, Object... var2);

   @Support
   @PlainSQL
   TablePartitionByStep<Record> leftOuterJoin(String var1, QueryPart... var2);

   @Support
   TablePartitionByStep<Record> leftOuterJoin(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   TablePartitionByStep<Record> rightJoin(TableLike<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   TablePartitionByStep<Record> rightJoin(SQL var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   TablePartitionByStep<Record> rightJoin(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   TablePartitionByStep<Record> rightJoin(String var1, Object... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   TablePartitionByStep<Record> rightJoin(String var1, QueryPart... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   TablePartitionByStep<Record> rightJoin(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   TablePartitionByStep<Record> rightOuterJoin(TableLike<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   TablePartitionByStep<Record> rightOuterJoin(SQL var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   TablePartitionByStep<Record> rightOuterJoin(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   TablePartitionByStep<Record> rightOuterJoin(String var1, Object... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   TablePartitionByStep<Record> rightOuterJoin(String var1, QueryPart... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   TablePartitionByStep<Record> rightOuterJoin(Name var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   TableOnStep<Record> fullOuterJoin(TableLike<?> var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   @PlainSQL
   TableOnStep<Record> fullOuterJoin(SQL var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   @PlainSQL
   TableOnStep<Record> fullOuterJoin(String var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   @PlainSQL
   TableOnStep<Record> fullOuterJoin(String var1, Object... var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   @PlainSQL
   TableOnStep<Record> fullOuterJoin(String var1, QueryPart... var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   TableOnStep<Record> fullOuterJoin(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Table<Record> crossJoin(TableLike<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   @PlainSQL
   Table<Record> crossJoin(SQL var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   @PlainSQL
   Table<Record> crossJoin(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   @PlainSQL
   Table<Record> crossJoin(String var1, Object... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   @PlainSQL
   Table<Record> crossJoin(String var1, QueryPart... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Table<Record> crossJoin(Name var1);

   @Support
   Table<Record> naturalJoin(TableLike<?> var1);

   @Support
   @PlainSQL
   Table<Record> naturalJoin(SQL var1);

   @Support
   @PlainSQL
   Table<Record> naturalJoin(String var1);

   @Support
   @PlainSQL
   Table<Record> naturalJoin(String var1, Object... var2);

   @Support
   Table<Record> naturalJoin(Name var1);

   @Support
   @PlainSQL
   Table<Record> naturalJoin(String var1, QueryPart... var2);

   @Support
   Table<Record> naturalLeftOuterJoin(TableLike<?> var1);

   @Support
   @PlainSQL
   Table<Record> naturalLeftOuterJoin(SQL var1);

   @Support
   @PlainSQL
   Table<Record> naturalLeftOuterJoin(String var1);

   @Support
   @PlainSQL
   Table<Record> naturalLeftOuterJoin(String var1, Object... var2);

   @Support
   @PlainSQL
   Table<Record> naturalLeftOuterJoin(String var1, QueryPart... var2);

   @Support
   @PlainSQL
   Table<Record> naturalLeftOuterJoin(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Table<Record> naturalRightOuterJoin(TableLike<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   Table<Record> naturalRightOuterJoin(SQL var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   Table<Record> naturalRightOuterJoin(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   Table<Record> naturalRightOuterJoin(String var1, Object... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   @PlainSQL
   Table<Record> naturalRightOuterJoin(String var1, QueryPart... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Table<Record> naturalRightOuterJoin(Name var1);

   @Support({SQLDialect.POSTGRES_9_3})
   Table<Record> crossApply(TableLike<?> var1);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   Table<Record> crossApply(SQL var1);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   Table<Record> crossApply(String var1);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   Table<Record> crossApply(String var1, Object... var2);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   Table<Record> crossApply(String var1, QueryPart... var2);

   @Support({SQLDialect.POSTGRES_9_3})
   Table<Record> crossApply(Name var1);

   @Support({SQLDialect.POSTGRES_9_3})
   Table<Record> outerApply(TableLike<?> var1);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   Table<Record> outerApply(SQL var1);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   Table<Record> outerApply(String var1);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   Table<Record> outerApply(String var1, Object... var2);

   @Support({SQLDialect.POSTGRES_9_3})
   @PlainSQL
   Table<Record> outerApply(String var1, QueryPart... var2);

   @Support({SQLDialect.POSTGRES_9_3})
   Table<Record> outerApply(Name var1);

   @Support({SQLDialect.MYSQL})
   TableOnStep<Record> straightJoin(TableLike<?> var1);

   @Support({SQLDialect.MYSQL})
   @PlainSQL
   TableOnStep<Record> straightJoin(SQL var1);

   @Support({SQLDialect.MYSQL})
   @PlainSQL
   TableOnStep<Record> straightJoin(String var1);

   @Support({SQLDialect.MYSQL})
   @PlainSQL
   TableOnStep<Record> straightJoin(String var1, Object... var2);

   @Support({SQLDialect.MYSQL})
   @PlainSQL
   TableOnStep<Record> straightJoin(String var1, QueryPart... var2);

   @Support({SQLDialect.MYSQL})
   @PlainSQL
   TableOnStep<Record> straightJoin(Name var1);

   @Support
   Condition eq(Table<R> var1);

   @Support
   Condition equal(Table<R> var1);

   boolean equals(Object var1);

   @Support
   Condition ne(Table<R> var1);

   @Support
   Condition notEqual(Table<R> var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   Table<R> useIndex(String... var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   Table<R> useIndexForJoin(String... var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   Table<R> useIndexForOrderBy(String... var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   Table<R> useIndexForGroupBy(String... var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   Table<R> ignoreIndex(String... var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   Table<R> ignoreIndexForJoin(String... var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   Table<R> ignoreIndexForOrderBy(String... var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   Table<R> ignoreIndexForGroupBy(String... var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   Table<R> forceIndex(String... var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   Table<R> forceIndexForJoin(String... var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   Table<R> forceIndexForOrderBy(String... var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   Table<R> forceIndexForGroupBy(String... var1);

   @Support
   DivideByOnStep divideBy(Table<?> var1);

   @Support
   TableOnStep<R> leftSemiJoin(TableLike<?> var1);

   @Support
   TableOnStep<R> leftAntiJoin(TableLike<?> var1);

   R from(Record var1);
}
