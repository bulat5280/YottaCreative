package org.jooq;

import java.util.Collection;

public interface Row4<T1, T2, T3, T4> extends Row {
   Field<T1> field1();

   Field<T2> field2();

   Field<T3> field3();

   Field<T4> field4();

   @Support
   Condition compare(Comparator var1, Row4<T1, T2, T3, T4> var2);

   @Support
   Condition compare(Comparator var1, Record4<T1, T2, T3, T4> var2);

   @Support
   Condition compare(Comparator var1, T1 var2, T2 var3, T3 var4, T4 var5);

   @Support
   Condition compare(Comparator var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5);

   @Support
   Condition compare(Comparator var1, Select<? extends Record4<T1, T2, T3, T4>> var2);

   @Support
   Condition compare(Comparator var1, QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> var2);

   @Support
   Condition equal(Row4<T1, T2, T3, T4> var1);

   @Support
   Condition equal(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition equal(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   Condition equal(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   Condition equal(Select<? extends Record4<T1, T2, T3, T4>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition equal(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> var1);

   @Support
   Condition eq(Row4<T1, T2, T3, T4> var1);

   @Support
   Condition eq(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition eq(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   Condition eq(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   Condition eq(Select<? extends Record4<T1, T2, T3, T4>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition eq(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> var1);

   @Support
   Condition notEqual(Row4<T1, T2, T3, T4> var1);

   @Support
   Condition notEqual(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition notEqual(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   Condition notEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   Condition notEqual(Select<? extends Record4<T1, T2, T3, T4>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition notEqual(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> var1);

   @Support
   Condition ne(Row4<T1, T2, T3, T4> var1);

   @Support
   Condition ne(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition ne(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   Condition ne(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   Condition ne(Select<? extends Record4<T1, T2, T3, T4>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ne(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> var1);

   @Support
   Condition lessThan(Row4<T1, T2, T3, T4> var1);

   @Support
   Condition lessThan(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition lessThan(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   Condition lessThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   Condition lessThan(Select<? extends Record4<T1, T2, T3, T4>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessThan(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> var1);

   @Support
   Condition lt(Row4<T1, T2, T3, T4> var1);

   @Support
   Condition lt(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition lt(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   Condition lt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   Condition lt(Select<? extends Record4<T1, T2, T3, T4>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lt(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> var1);

   @Support
   Condition lessOrEqual(Row4<T1, T2, T3, T4> var1);

   @Support
   Condition lessOrEqual(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition lessOrEqual(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   Condition lessOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   Condition lessOrEqual(Select<? extends Record4<T1, T2, T3, T4>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessOrEqual(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> var1);

   @Support
   Condition le(Row4<T1, T2, T3, T4> var1);

   @Support
   Condition le(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition le(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   Condition le(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   Condition le(Select<? extends Record4<T1, T2, T3, T4>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition le(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> var1);

   @Support
   Condition greaterThan(Row4<T1, T2, T3, T4> var1);

   @Support
   Condition greaterThan(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition greaterThan(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   Condition greaterThan(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   Condition greaterThan(Select<? extends Record4<T1, T2, T3, T4>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterThan(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> var1);

   @Support
   Condition gt(Row4<T1, T2, T3, T4> var1);

   @Support
   Condition gt(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition gt(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   Condition gt(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   Condition gt(Select<? extends Record4<T1, T2, T3, T4>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition gt(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> var1);

   @Support
   Condition greaterOrEqual(Row4<T1, T2, T3, T4> var1);

   @Support
   Condition greaterOrEqual(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition greaterOrEqual(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   Condition greaterOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   Condition greaterOrEqual(Select<? extends Record4<T1, T2, T3, T4>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterOrEqual(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> var1);

   @Support
   Condition ge(Row4<T1, T2, T3, T4> var1);

   @Support
   Condition ge(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition ge(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   Condition ge(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   Condition ge(Select<? extends Record4<T1, T2, T3, T4>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ge(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> var1);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> between(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> between(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> between(Row4<T1, T2, T3, T4> var1);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> between(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition between(Row4<T1, T2, T3, T4> var1, Row4<T1, T2, T3, T4> var2);

   @Support
   Condition between(Record4<T1, T2, T3, T4> var1, Record4<T1, T2, T3, T4> var2);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> betweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> betweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> betweenSymmetric(Row4<T1, T2, T3, T4> var1);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> betweenSymmetric(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition betweenSymmetric(Row4<T1, T2, T3, T4> var1, Row4<T1, T2, T3, T4> var2);

   @Support
   Condition betweenSymmetric(Record4<T1, T2, T3, T4> var1, Record4<T1, T2, T3, T4> var2);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> notBetween(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> notBetween(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> notBetween(Row4<T1, T2, T3, T4> var1);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> notBetween(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition notBetween(Row4<T1, T2, T3, T4> var1, Row4<T1, T2, T3, T4> var2);

   @Support
   Condition notBetween(Record4<T1, T2, T3, T4> var1, Record4<T1, T2, T3, T4> var2);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> notBetweenSymmetric(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> notBetweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> notBetweenSymmetric(Row4<T1, T2, T3, T4> var1);

   @Support
   BetweenAndStep4<T1, T2, T3, T4> notBetweenSymmetric(Record4<T1, T2, T3, T4> var1);

   @Support
   Condition notBetweenSymmetric(Row4<T1, T2, T3, T4> var1, Row4<T1, T2, T3, T4> var2);

   @Support
   Condition notBetweenSymmetric(Record4<T1, T2, T3, T4> var1, Record4<T1, T2, T3, T4> var2);

   @Support
   Condition in(Collection<? extends Row4<T1, T2, T3, T4>> var1);

   @Support
   Condition in(Result<? extends Record4<T1, T2, T3, T4>> var1);

   @Support
   Condition in(Row4<T1, T2, T3, T4>... var1);

   @Support
   Condition in(Record4<T1, T2, T3, T4>... var1);

   @Support
   Condition in(Select<? extends Record4<T1, T2, T3, T4>> var1);

   @Support
   Condition notIn(Collection<? extends Row4<T1, T2, T3, T4>> var1);

   @Support
   Condition notIn(Result<? extends Record4<T1, T2, T3, T4>> var1);

   @Support
   Condition notIn(Row4<T1, T2, T3, T4>... var1);

   @Support
   Condition notIn(Record4<T1, T2, T3, T4>... var1);

   @Support
   Condition notIn(Select<? extends Record4<T1, T2, T3, T4>> var1);
}
