package org.jooq;

import java.util.Collection;

public interface Row8<T1, T2, T3, T4, T5, T6, T7, T8> extends Row {
   Field<T1> field1();

   Field<T2> field2();

   Field<T3> field3();

   Field<T4> field4();

   Field<T5> field5();

   Field<T6> field6();

   Field<T7> field7();

   Field<T8> field8();

   @Support
   Condition compare(Comparator var1, Row8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

   @Support
   Condition compare(Comparator var1, Record8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

   @Support
   Condition compare(Comparator var1, T1 var2, T2 var3, T3 var4, T4 var5, T5 var6, T6 var7, T7 var8, T8 var9);

   @Support
   Condition compare(Comparator var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9);

   @Support
   Condition compare(Comparator var1, Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var2);

   @Support
   Condition compare(Comparator var1, QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var2);

   @Support
   Condition equal(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition equal(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition equal(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   Condition equal(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   Condition equal(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition equal(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition eq(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition eq(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition eq(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   Condition eq(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   Condition eq(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition eq(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition notEqual(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition notEqual(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition notEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   Condition notEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   Condition notEqual(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition notEqual(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition ne(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition ne(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition ne(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   Condition ne(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   Condition ne(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ne(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition lessThan(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition lessThan(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition lessThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   Condition lessThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   Condition lessThan(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessThan(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition lt(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition lt(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition lt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   Condition lt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   Condition lt(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lt(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition lessOrEqual(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition lessOrEqual(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition lessOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   Condition lessOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   Condition lessOrEqual(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessOrEqual(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition le(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition le(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition le(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   Condition le(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   Condition le(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition le(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition greaterThan(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition greaterThan(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition greaterThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   Condition greaterThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   Condition greaterThan(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterThan(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition gt(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition gt(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition gt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   Condition gt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   Condition gt(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition gt(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition greaterOrEqual(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition greaterOrEqual(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition greaterOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   Condition greaterOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   Condition greaterOrEqual(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterOrEqual(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition ge(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition ge(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition ge(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   Condition ge(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   Condition ge(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ge(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition between(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Row8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

   @Support
   Condition between(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Record8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition betweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Row8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

   @Support
   Condition betweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Record8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition notBetween(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Row8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

   @Support
   Condition notBetween(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Record8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1);

   @Support
   Condition notBetweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Row8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

   @Support
   Condition notBetweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Record8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

   @Support
   Condition in(Collection<? extends Row8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition in(Result<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition in(Row8<T1, T2, T3, T4, T5, T6, T7, T8>... var1);

   @Support
   Condition in(Record8<T1, T2, T3, T4, T5, T6, T7, T8>... var1);

   @Support
   Condition in(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition notIn(Collection<? extends Row8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition notIn(Result<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);

   @Support
   Condition notIn(Row8<T1, T2, T3, T4, T5, T6, T7, T8>... var1);

   @Support
   Condition notIn(Record8<T1, T2, T3, T4, T5, T6, T7, T8>... var1);

   @Support
   Condition notIn(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);
}
