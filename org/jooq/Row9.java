package org.jooq;

import java.util.Collection;

public interface Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends Row {
   Field<T1> field1();

   Field<T2> field2();

   Field<T3> field3();

   Field<T4> field4();

   Field<T5> field5();

   Field<T6> field6();

   Field<T7> field7();

   Field<T8> field8();

   Field<T9> field9();

   @Support
   Condition compare(Comparator var1, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var2);

   @Support
   Condition compare(Comparator var1, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var2);

   @Support
   Condition compare(Comparator var1, T1 var2, T2 var3, T3 var4, T4 var5, T5 var6, T6 var7, T7 var8, T8 var9, T9 var10);

   @Support
   Condition compare(Comparator var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10);

   @Support
   Condition compare(Comparator var1, Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var2);

   @Support
   Condition compare(Comparator var1, QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var2);

   @Support
   Condition equal(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition equal(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition equal(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   Condition equal(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   Condition equal(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition equal(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition eq(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition eq(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition eq(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   Condition eq(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   Condition eq(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition eq(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition notEqual(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition notEqual(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition notEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   Condition notEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   Condition notEqual(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition notEqual(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition ne(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition ne(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition ne(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   Condition ne(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   Condition ne(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ne(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition lessThan(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition lessThan(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition lessThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   Condition lessThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   Condition lessThan(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessThan(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition lt(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition lt(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition lt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   Condition lt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   Condition lt(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lt(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition lessOrEqual(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition lessOrEqual(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition lessOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   Condition lessOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   Condition lessOrEqual(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessOrEqual(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition le(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition le(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition le(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   Condition le(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   Condition le(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition le(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition greaterThan(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition greaterThan(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition greaterThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   Condition greaterThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   Condition greaterThan(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterThan(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition gt(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition gt(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition gt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   Condition gt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   Condition gt(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition gt(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition greaterOrEqual(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition greaterOrEqual(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition greaterOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   Condition greaterOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   Condition greaterOrEqual(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterOrEqual(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition ge(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition ge(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition ge(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   Condition ge(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   Condition ge(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ge(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> between(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> between(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> between(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> between(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition between(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var2);

   @Support
   Condition between(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var2);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> betweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> betweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> betweenSymmetric(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> betweenSymmetric(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition betweenSymmetric(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var2);

   @Support
   Condition betweenSymmetric(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var2);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetween(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetween(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetween(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetween(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition notBetween(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var2);

   @Support
   Condition notBetween(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var2);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetweenSymmetric(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetweenSymmetric(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition notBetweenSymmetric(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var2);

   @Support
   Condition notBetweenSymmetric(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var2);

   @Support
   Condition in(Collection<? extends Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition in(Result<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition in(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>... var1);

   @Support
   Condition in(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>... var1);

   @Support
   Condition in(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition notIn(Collection<? extends Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition notIn(Result<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);

   @Support
   Condition notIn(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>... var1);

   @Support
   Condition notIn(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>... var1);

   @Support
   Condition notIn(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var1);
}
