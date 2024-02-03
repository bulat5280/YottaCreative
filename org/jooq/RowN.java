package org.jooq;

import java.util.Collection;

public interface RowN extends Row {
   @Support
   Condition compare(Comparator var1, RowN var2);

   @Support
   Condition compare(Comparator var1, Record var2);

   @Support
   Condition compare(Comparator var1, Object... var2);

   @Support
   Condition compare(Comparator var1, Field<?>... var2);

   @Support
   Condition compare(Comparator var1, Select<? extends Record> var2);

   @Support
   Condition compare(Comparator var1, QuantifiedSelect<? extends Record> var2);

   @Support
   Condition equal(RowN var1);

   @Support
   Condition equal(Record var1);

   @Support
   Condition equal(Object... var1);

   @Support
   Condition equal(Field<?>... var1);

   @Support
   Condition equal(Select<? extends Record> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition equal(QuantifiedSelect<? extends Record> var1);

   @Support
   Condition eq(RowN var1);

   @Support
   Condition eq(Record var1);

   @Support
   Condition eq(Object... var1);

   @Support
   Condition eq(Field<?>... var1);

   @Support
   Condition eq(Select<? extends Record> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition eq(QuantifiedSelect<? extends Record> var1);

   @Support
   Condition notEqual(RowN var1);

   @Support
   Condition notEqual(Record var1);

   @Support
   Condition notEqual(Object... var1);

   @Support
   Condition notEqual(Field<?>... var1);

   @Support
   Condition notEqual(Select<? extends Record> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition notEqual(QuantifiedSelect<? extends Record> var1);

   @Support
   Condition ne(RowN var1);

   @Support
   Condition ne(Record var1);

   @Support
   Condition ne(Object... var1);

   @Support
   Condition ne(Field<?>... var1);

   @Support
   Condition ne(Select<? extends Record> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ne(QuantifiedSelect<? extends Record> var1);

   @Support
   Condition lessThan(RowN var1);

   @Support
   Condition lessThan(Record var1);

   @Support
   Condition lessThan(Object... var1);

   @Support
   Condition lessThan(Field<?>... var1);

   @Support
   Condition lessThan(Select<? extends Record> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessThan(QuantifiedSelect<? extends Record> var1);

   @Support
   Condition lt(RowN var1);

   @Support
   Condition lt(Record var1);

   @Support
   Condition lt(Object... var1);

   @Support
   Condition lt(Field<?>... var1);

   @Support
   Condition lt(Select<? extends Record> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lt(QuantifiedSelect<? extends Record> var1);

   @Support
   Condition lessOrEqual(RowN var1);

   @Support
   Condition lessOrEqual(Record var1);

   @Support
   Condition lessOrEqual(Object... var1);

   @Support
   Condition lessOrEqual(Field<?>... var1);

   @Support
   Condition lessOrEqual(Select<? extends Record> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessOrEqual(QuantifiedSelect<? extends Record> var1);

   @Support
   Condition le(RowN var1);

   @Support
   Condition le(Record var1);

   @Support
   Condition le(Object... var1);

   @Support
   Condition le(Field<?>... var1);

   @Support
   Condition le(Select<? extends Record> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition le(QuantifiedSelect<? extends Record> var1);

   @Support
   Condition greaterThan(RowN var1);

   @Support
   Condition greaterThan(Record var1);

   @Support
   Condition greaterThan(Object... var1);

   @Support
   Condition greaterThan(Field<?>... var1);

   @Support
   Condition greaterThan(Select<? extends Record> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterThan(QuantifiedSelect<? extends Record> var1);

   @Support
   Condition gt(RowN var1);

   @Support
   Condition gt(Record var1);

   @Support
   Condition gt(Object... var1);

   @Support
   Condition gt(Field<?>... var1);

   @Support
   Condition gt(Select<? extends Record> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition gt(QuantifiedSelect<? extends Record> var1);

   @Support
   Condition greaterOrEqual(RowN var1);

   @Support
   Condition greaterOrEqual(Record var1);

   @Support
   Condition greaterOrEqual(Object... var1);

   @Support
   Condition greaterOrEqual(Field<?>... var1);

   @Support
   Condition greaterOrEqual(Select<? extends Record> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterOrEqual(QuantifiedSelect<? extends Record> var1);

   @Support
   Condition ge(RowN var1);

   @Support
   Condition ge(Record var1);

   @Support
   Condition ge(Object... var1);

   @Support
   Condition ge(Field<?>... var1);

   @Support
   Condition ge(Select<? extends Record> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ge(QuantifiedSelect<? extends Record> var1);

   @Support
   BetweenAndStepN between(Object... var1);

   @Support
   BetweenAndStepN between(Field<?>... var1);

   @Support
   BetweenAndStepN between(RowN var1);

   @Support
   BetweenAndStepN between(Record var1);

   @Support
   Condition between(RowN var1, RowN var2);

   @Support
   Condition between(Record var1, Record var2);

   @Support
   BetweenAndStepN betweenSymmetric(Object... var1);

   @Support
   BetweenAndStepN betweenSymmetric(Field<?>... var1);

   @Support
   BetweenAndStepN betweenSymmetric(RowN var1);

   @Support
   BetweenAndStepN betweenSymmetric(Record var1);

   @Support
   Condition betweenSymmetric(RowN var1, RowN var2);

   @Support
   Condition betweenSymmetric(Record var1, Record var2);

   @Support
   BetweenAndStepN notBetween(Object... var1);

   @Support
   BetweenAndStepN notBetween(Field<?>... var1);

   @Support
   BetweenAndStepN notBetween(RowN var1);

   @Support
   BetweenAndStepN notBetween(Record var1);

   @Support
   Condition notBetween(RowN var1, RowN var2);

   @Support
   Condition notBetween(Record var1, Record var2);

   @Support
   BetweenAndStepN notBetweenSymmetric(Object... var1);

   @Support
   BetweenAndStepN notBetweenSymmetric(Field<?>... var1);

   @Support
   BetweenAndStepN notBetweenSymmetric(RowN var1);

   @Support
   BetweenAndStepN notBetweenSymmetric(Record var1);

   @Support
   Condition notBetweenSymmetric(RowN var1, RowN var2);

   @Support
   Condition notBetweenSymmetric(Record var1, Record var2);

   @Support
   Condition in(Collection<? extends RowN> var1);

   @Support
   Condition in(Result<? extends Record> var1);

   @Support
   Condition in(RowN... var1);

   @Support
   Condition in(Record... var1);

   @Support
   Condition in(Select<? extends Record> var1);

   @Support
   Condition notIn(Collection<? extends RowN> var1);

   @Support
   Condition notIn(Result<? extends Record> var1);

   @Support
   Condition notIn(RowN... var1);

   @Support
   Condition notIn(Record... var1);

   @Support
   Condition notIn(Select<? extends Record> var1);
}
