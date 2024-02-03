package org.jooq;

import java.util.Collection;

public interface Row6<T1, T2, T3, T4, T5, T6> extends Row {
   Field<T1> field1();

   Field<T2> field2();

   Field<T3> field3();

   Field<T4> field4();

   Field<T5> field5();

   Field<T6> field6();

   @Support
   Condition compare(Comparator var1, Row6<T1, T2, T3, T4, T5, T6> var2);

   @Support
   Condition compare(Comparator var1, Record6<T1, T2, T3, T4, T5, T6> var2);

   @Support
   Condition compare(Comparator var1, T1 var2, T2 var3, T3 var4, T4 var5, T5 var6, T6 var7);

   @Support
   Condition compare(Comparator var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7);

   @Support
   Condition compare(Comparator var1, Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var2);

   @Support
   Condition compare(Comparator var1, QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> var2);

   @Support
   Condition equal(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition equal(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition equal(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   Condition equal(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   Condition equal(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition equal(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition eq(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition eq(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition eq(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   Condition eq(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   Condition eq(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition eq(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition notEqual(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition notEqual(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition notEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   Condition notEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   Condition notEqual(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition notEqual(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition ne(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition ne(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition ne(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   Condition ne(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   Condition ne(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ne(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition lessThan(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition lessThan(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition lessThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   Condition lessThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   Condition lessThan(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessThan(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition lt(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition lt(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition lt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   Condition lt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   Condition lt(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lt(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition lessOrEqual(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition lessOrEqual(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition lessOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   Condition lessOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   Condition lessOrEqual(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessOrEqual(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition le(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition le(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition le(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   Condition le(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   Condition le(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition le(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition greaterThan(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition greaterThan(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition greaterThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   Condition greaterThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   Condition greaterThan(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterThan(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition gt(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition gt(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition gt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   Condition gt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   Condition gt(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition gt(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition greaterOrEqual(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition greaterOrEqual(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition greaterOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   Condition greaterOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   Condition greaterOrEqual(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterOrEqual(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition ge(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition ge(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition ge(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   Condition ge(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   Condition ge(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ge(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> between(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> between(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> between(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> between(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition between(Row6<T1, T2, T3, T4, T5, T6> var1, Row6<T1, T2, T3, T4, T5, T6> var2);

   @Support
   Condition between(Record6<T1, T2, T3, T4, T5, T6> var1, Record6<T1, T2, T3, T4, T5, T6> var2);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> betweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> betweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> betweenSymmetric(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> betweenSymmetric(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition betweenSymmetric(Row6<T1, T2, T3, T4, T5, T6> var1, Row6<T1, T2, T3, T4, T5, T6> var2);

   @Support
   Condition betweenSymmetric(Record6<T1, T2, T3, T4, T5, T6> var1, Record6<T1, T2, T3, T4, T5, T6> var2);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetween(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetween(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetween(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetween(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition notBetween(Row6<T1, T2, T3, T4, T5, T6> var1, Row6<T1, T2, T3, T4, T5, T6> var2);

   @Support
   Condition notBetween(Record6<T1, T2, T3, T4, T5, T6> var1, Record6<T1, T2, T3, T4, T5, T6> var2);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetweenSymmetric(Row6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetweenSymmetric(Record6<T1, T2, T3, T4, T5, T6> var1);

   @Support
   Condition notBetweenSymmetric(Row6<T1, T2, T3, T4, T5, T6> var1, Row6<T1, T2, T3, T4, T5, T6> var2);

   @Support
   Condition notBetweenSymmetric(Record6<T1, T2, T3, T4, T5, T6> var1, Record6<T1, T2, T3, T4, T5, T6> var2);

   @Support
   Condition in(Collection<? extends Row6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition in(Result<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition in(Row6<T1, T2, T3, T4, T5, T6>... var1);

   @Support
   Condition in(Record6<T1, T2, T3, T4, T5, T6>... var1);

   @Support
   Condition in(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition notIn(Collection<? extends Row6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition notIn(Result<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);

   @Support
   Condition notIn(Row6<T1, T2, T3, T4, T5, T6>... var1);

   @Support
   Condition notIn(Record6<T1, T2, T3, T4, T5, T6>... var1);

   @Support
   Condition notIn(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var1);
}
