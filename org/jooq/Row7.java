package org.jooq;

import java.util.Collection;

public interface Row7<T1, T2, T3, T4, T5, T6, T7> extends Row {
   Field<T1> field1();

   Field<T2> field2();

   Field<T3> field3();

   Field<T4> field4();

   Field<T5> field5();

   Field<T6> field6();

   Field<T7> field7();

   @Support
   Condition compare(Comparator var1, Row7<T1, T2, T3, T4, T5, T6, T7> var2);

   @Support
   Condition compare(Comparator var1, Record7<T1, T2, T3, T4, T5, T6, T7> var2);

   @Support
   Condition compare(Comparator var1, T1 var2, T2 var3, T3 var4, T4 var5, T5 var6, T6 var7, T7 var8);

   @Support
   Condition compare(Comparator var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8);

   @Support
   Condition compare(Comparator var1, Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var2);

   @Support
   Condition compare(Comparator var1, QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var2);

   @Support
   Condition equal(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition equal(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition equal(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   Condition equal(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   Condition equal(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition equal(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition eq(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition eq(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition eq(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   Condition eq(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   Condition eq(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition eq(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition notEqual(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition notEqual(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition notEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   Condition notEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   Condition notEqual(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition notEqual(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition ne(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition ne(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition ne(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   Condition ne(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   Condition ne(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ne(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition lessThan(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition lessThan(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition lessThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   Condition lessThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   Condition lessThan(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessThan(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition lt(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition lt(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition lt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   Condition lt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   Condition lt(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lt(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition lessOrEqual(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition lessOrEqual(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition lessOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   Condition lessOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   Condition lessOrEqual(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessOrEqual(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition le(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition le(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition le(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   Condition le(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   Condition le(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition le(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition greaterThan(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition greaterThan(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition greaterThan(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   Condition greaterThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   Condition greaterThan(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterThan(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition gt(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition gt(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition gt(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   Condition gt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   Condition gt(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition gt(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition greaterOrEqual(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition greaterOrEqual(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition greaterOrEqual(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   Condition greaterOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   Condition greaterOrEqual(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterOrEqual(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition ge(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition ge(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition ge(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   Condition ge(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   Condition ge(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ge(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> between(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> between(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> between(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> between(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition between(Row7<T1, T2, T3, T4, T5, T6, T7> var1, Row7<T1, T2, T3, T4, T5, T6, T7> var2);

   @Support
   Condition between(Record7<T1, T2, T3, T4, T5, T6, T7> var1, Record7<T1, T2, T3, T4, T5, T6, T7> var2);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> betweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> betweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> betweenSymmetric(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> betweenSymmetric(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition betweenSymmetric(Row7<T1, T2, T3, T4, T5, T6, T7> var1, Row7<T1, T2, T3, T4, T5, T6, T7> var2);

   @Support
   Condition betweenSymmetric(Record7<T1, T2, T3, T4, T5, T6, T7> var1, Record7<T1, T2, T3, T4, T5, T6, T7> var2);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetween(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetween(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetween(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetween(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition notBetween(Row7<T1, T2, T3, T4, T5, T6, T7> var1, Row7<T1, T2, T3, T4, T5, T6, T7> var2);

   @Support
   Condition notBetween(Record7<T1, T2, T3, T4, T5, T6, T7> var1, Record7<T1, T2, T3, T4, T5, T6, T7> var2);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetweenSymmetric(Row7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetweenSymmetric(Record7<T1, T2, T3, T4, T5, T6, T7> var1);

   @Support
   Condition notBetweenSymmetric(Row7<T1, T2, T3, T4, T5, T6, T7> var1, Row7<T1, T2, T3, T4, T5, T6, T7> var2);

   @Support
   Condition notBetweenSymmetric(Record7<T1, T2, T3, T4, T5, T6, T7> var1, Record7<T1, T2, T3, T4, T5, T6, T7> var2);

   @Support
   Condition in(Collection<? extends Row7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition in(Result<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition in(Row7<T1, T2, T3, T4, T5, T6, T7>... var1);

   @Support
   Condition in(Record7<T1, T2, T3, T4, T5, T6, T7>... var1);

   @Support
   Condition in(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition notIn(Collection<? extends Row7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition notIn(Result<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);

   @Support
   Condition notIn(Row7<T1, T2, T3, T4, T5, T6, T7>... var1);

   @Support
   Condition notIn(Record7<T1, T2, T3, T4, T5, T6, T7>... var1);

   @Support
   Condition notIn(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);
}
