package org.jooq;

import java.util.Collection;

public interface Row5<T1, T2, T3, T4, T5> extends Row {
   Field<T1> field1();

   Field<T2> field2();

   Field<T3> field3();

   Field<T4> field4();

   Field<T5> field5();

   @Support
   Condition compare(Comparator var1, Row5<T1, T2, T3, T4, T5> var2);

   @Support
   Condition compare(Comparator var1, Record5<T1, T2, T3, T4, T5> var2);

   @Support
   Condition compare(Comparator var1, T1 var2, T2 var3, T3 var4, T4 var5, T5 var6);

   @Support
   Condition compare(Comparator var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6);

   @Support
   Condition compare(Comparator var1, Select<? extends Record5<T1, T2, T3, T4, T5>> var2);

   @Support
   Condition compare(Comparator var1, QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> var2);

   @Support
   Condition equal(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition equal(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition equal(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   Condition equal(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   Condition equal(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition equal(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition eq(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition eq(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition eq(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   Condition eq(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   Condition eq(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition eq(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition notEqual(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition notEqual(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition notEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   Condition notEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   Condition notEqual(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition notEqual(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition ne(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition ne(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition ne(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   Condition ne(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   Condition ne(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ne(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition lessThan(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition lessThan(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition lessThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   Condition lessThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   Condition lessThan(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessThan(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition lt(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition lt(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition lt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   Condition lt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   Condition lt(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lt(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition lessOrEqual(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition lessOrEqual(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition lessOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   Condition lessOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   Condition lessOrEqual(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessOrEqual(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition le(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition le(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition le(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   Condition le(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   Condition le(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition le(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition greaterThan(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition greaterThan(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition greaterThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   Condition greaterThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   Condition greaterThan(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterThan(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition gt(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition gt(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition gt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   Condition gt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   Condition gt(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition gt(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition greaterOrEqual(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition greaterOrEqual(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition greaterOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   Condition greaterOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   Condition greaterOrEqual(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterOrEqual(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition ge(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition ge(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition ge(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   Condition ge(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   Condition ge(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ge(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> between(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> between(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> between(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> between(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition between(Row5<T1, T2, T3, T4, T5> var1, Row5<T1, T2, T3, T4, T5> var2);

   @Support
   Condition between(Record5<T1, T2, T3, T4, T5> var1, Record5<T1, T2, T3, T4, T5> var2);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> betweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> betweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> betweenSymmetric(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> betweenSymmetric(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition betweenSymmetric(Row5<T1, T2, T3, T4, T5> var1, Row5<T1, T2, T3, T4, T5> var2);

   @Support
   Condition betweenSymmetric(Record5<T1, T2, T3, T4, T5> var1, Record5<T1, T2, T3, T4, T5> var2);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> notBetween(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> notBetween(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> notBetween(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> notBetween(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition notBetween(Row5<T1, T2, T3, T4, T5> var1, Row5<T1, T2, T3, T4, T5> var2);

   @Support
   Condition notBetween(Record5<T1, T2, T3, T4, T5> var1, Record5<T1, T2, T3, T4, T5> var2);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> notBetweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> notBetweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> notBetweenSymmetric(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   BetweenAndStep5<T1, T2, T3, T4, T5> notBetweenSymmetric(Record5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition notBetweenSymmetric(Row5<T1, T2, T3, T4, T5> var1, Row5<T1, T2, T3, T4, T5> var2);

   @Support
   Condition notBetweenSymmetric(Record5<T1, T2, T3, T4, T5> var1, Record5<T1, T2, T3, T4, T5> var2);

   @Support
   Condition in(Collection<? extends Row5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition in(Result<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition in(Row5<T1, T2, T3, T4, T5>... var1);

   @Support
   Condition in(Record5<T1, T2, T3, T4, T5>... var1);

   @Support
   Condition in(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition notIn(Collection<? extends Row5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition notIn(Result<? extends Record5<T1, T2, T3, T4, T5>> var1);

   @Support
   Condition notIn(Row5<T1, T2, T3, T4, T5>... var1);

   @Support
   Condition notIn(Record5<T1, T2, T3, T4, T5>... var1);

   @Support
   Condition notIn(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);
}
