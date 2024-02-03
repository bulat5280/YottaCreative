package org.jooq;

import java.util.Collection;

public interface Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends Row {
   Field<T1> field1();

   Field<T2> field2();

   Field<T3> field3();

   Field<T4> field4();

   Field<T5> field5();

   Field<T6> field6();

   Field<T7> field7();

   Field<T8> field8();

   Field<T9> field9();

   Field<T10> field10();

   @Support
   Condition compare(Comparator var1, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var2);

   @Support
   Condition compare(Comparator var1, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var2);

   @Support
   Condition compare(Comparator var1, T1 var2, T2 var3, T3 var4, T4 var5, T5 var6, T6 var7, T7 var8, T8 var9, T9 var10, T10 var11);

   @Support
   Condition compare(Comparator var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11);

   @Support
   Condition compare(Comparator var1, Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var2);

   @Support
   Condition compare(Comparator var1, QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var2);

   @Support
   Condition equal(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition equal(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition equal(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   Condition equal(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   Condition equal(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition equal(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition eq(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition eq(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition eq(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   Condition eq(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   Condition eq(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition eq(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition notEqual(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition notEqual(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition notEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   Condition notEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   Condition notEqual(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition notEqual(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition ne(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition ne(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition ne(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   Condition ne(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   Condition ne(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ne(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition lessThan(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition lessThan(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition lessThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   Condition lessThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   Condition lessThan(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessThan(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition lt(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition lt(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition lt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   Condition lt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   Condition lt(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lt(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition lessOrEqual(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition lessOrEqual(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition lessOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   Condition lessOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   Condition lessOrEqual(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessOrEqual(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition le(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition le(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition le(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   Condition le(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   Condition le(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition le(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition greaterThan(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition greaterThan(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition greaterThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   Condition greaterThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   Condition greaterThan(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterThan(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition gt(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition gt(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition gt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   Condition gt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   Condition gt(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition gt(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition greaterOrEqual(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition greaterOrEqual(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition greaterOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   Condition greaterOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   Condition greaterOrEqual(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterOrEqual(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition ge(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition ge(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition ge(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   Condition ge(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   Condition ge(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ge(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition between(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var2);

   @Support
   Condition between(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var2);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition betweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var2);

   @Support
   Condition betweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var2);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition notBetween(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var2);

   @Support
   Condition notBetween(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var2);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1);

   @Support
   Condition notBetweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var2);

   @Support
   Condition notBetweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var2);

   @Support
   Condition in(Collection<? extends Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition in(Result<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition in(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>... var1);

   @Support
   Condition in(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>... var1);

   @Support
   Condition in(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition notIn(Collection<? extends Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition notIn(Result<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);

   @Support
   Condition notIn(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>... var1);

   @Support
   Condition notIn(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>... var1);

   @Support
   Condition notIn(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var1);
}
