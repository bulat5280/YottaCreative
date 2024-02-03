package org.jooq;

import java.util.Collection;

public interface Row3<T1, T2, T3> extends Row {
   Field<T1> field1();

   Field<T2> field2();

   Field<T3> field3();

   @Support
   Condition compare(Comparator var1, Row3<T1, T2, T3> var2);

   @Support
   Condition compare(Comparator var1, Record3<T1, T2, T3> var2);

   @Support
   Condition compare(Comparator var1, T1 var2, T2 var3, T3 var4);

   @Support
   Condition compare(Comparator var1, Field<T1> var2, Field<T2> var3, Field<T3> var4);

   @Support
   Condition compare(Comparator var1, Select<? extends Record3<T1, T2, T3>> var2);

   @Support
   Condition compare(Comparator var1, QuantifiedSelect<? extends Record3<T1, T2, T3>> var2);

   @Support
   Condition equal(Row3<T1, T2, T3> var1);

   @Support
   Condition equal(Record3<T1, T2, T3> var1);

   @Support
   Condition equal(T1 var1, T2 var2, T3 var3);

   @Support
   Condition equal(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   Condition equal(Select<? extends Record3<T1, T2, T3>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition equal(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

   @Support
   Condition eq(Row3<T1, T2, T3> var1);

   @Support
   Condition eq(Record3<T1, T2, T3> var1);

   @Support
   Condition eq(T1 var1, T2 var2, T3 var3);

   @Support
   Condition eq(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   Condition eq(Select<? extends Record3<T1, T2, T3>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition eq(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

   @Support
   Condition notEqual(Row3<T1, T2, T3> var1);

   @Support
   Condition notEqual(Record3<T1, T2, T3> var1);

   @Support
   Condition notEqual(T1 var1, T2 var2, T3 var3);

   @Support
   Condition notEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   Condition notEqual(Select<? extends Record3<T1, T2, T3>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition notEqual(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

   @Support
   Condition ne(Row3<T1, T2, T3> var1);

   @Support
   Condition ne(Record3<T1, T2, T3> var1);

   @Support
   Condition ne(T1 var1, T2 var2, T3 var3);

   @Support
   Condition ne(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   Condition ne(Select<? extends Record3<T1, T2, T3>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ne(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

   @Support
   Condition lessThan(Row3<T1, T2, T3> var1);

   @Support
   Condition lessThan(Record3<T1, T2, T3> var1);

   @Support
   Condition lessThan(T1 var1, T2 var2, T3 var3);

   @Support
   Condition lessThan(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   Condition lessThan(Select<? extends Record3<T1, T2, T3>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessThan(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

   @Support
   Condition lt(Row3<T1, T2, T3> var1);

   @Support
   Condition lt(Record3<T1, T2, T3> var1);

   @Support
   Condition lt(T1 var1, T2 var2, T3 var3);

   @Support
   Condition lt(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   Condition lt(Select<? extends Record3<T1, T2, T3>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lt(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

   @Support
   Condition lessOrEqual(Row3<T1, T2, T3> var1);

   @Support
   Condition lessOrEqual(Record3<T1, T2, T3> var1);

   @Support
   Condition lessOrEqual(T1 var1, T2 var2, T3 var3);

   @Support
   Condition lessOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   Condition lessOrEqual(Select<? extends Record3<T1, T2, T3>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessOrEqual(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

   @Support
   Condition le(Row3<T1, T2, T3> var1);

   @Support
   Condition le(Record3<T1, T2, T3> var1);

   @Support
   Condition le(T1 var1, T2 var2, T3 var3);

   @Support
   Condition le(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   Condition le(Select<? extends Record3<T1, T2, T3>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition le(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

   @Support
   Condition greaterThan(Row3<T1, T2, T3> var1);

   @Support
   Condition greaterThan(Record3<T1, T2, T3> var1);

   @Support
   Condition greaterThan(T1 var1, T2 var2, T3 var3);

   @Support
   Condition greaterThan(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   Condition greaterThan(Select<? extends Record3<T1, T2, T3>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterThan(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

   @Support
   Condition gt(Row3<T1, T2, T3> var1);

   @Support
   Condition gt(Record3<T1, T2, T3> var1);

   @Support
   Condition gt(T1 var1, T2 var2, T3 var3);

   @Support
   Condition gt(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   Condition gt(Select<? extends Record3<T1, T2, T3>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition gt(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

   @Support
   Condition greaterOrEqual(Row3<T1, T2, T3> var1);

   @Support
   Condition greaterOrEqual(Record3<T1, T2, T3> var1);

   @Support
   Condition greaterOrEqual(T1 var1, T2 var2, T3 var3);

   @Support
   Condition greaterOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   Condition greaterOrEqual(Select<? extends Record3<T1, T2, T3>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterOrEqual(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

   @Support
   Condition ge(Row3<T1, T2, T3> var1);

   @Support
   Condition ge(Record3<T1, T2, T3> var1);

   @Support
   Condition ge(T1 var1, T2 var2, T3 var3);

   @Support
   Condition ge(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   Condition ge(Select<? extends Record3<T1, T2, T3>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ge(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

   @Support
   BetweenAndStep3<T1, T2, T3> between(T1 var1, T2 var2, T3 var3);

   @Support
   BetweenAndStep3<T1, T2, T3> between(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   BetweenAndStep3<T1, T2, T3> between(Row3<T1, T2, T3> var1);

   @Support
   BetweenAndStep3<T1, T2, T3> between(Record3<T1, T2, T3> var1);

   @Support
   Condition between(Row3<T1, T2, T3> var1, Row3<T1, T2, T3> var2);

   @Support
   Condition between(Record3<T1, T2, T3> var1, Record3<T1, T2, T3> var2);

   @Support
   BetweenAndStep3<T1, T2, T3> betweenSymmetric(T1 var1, T2 var2, T3 var3);

   @Support
   BetweenAndStep3<T1, T2, T3> betweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   BetweenAndStep3<T1, T2, T3> betweenSymmetric(Row3<T1, T2, T3> var1);

   @Support
   BetweenAndStep3<T1, T2, T3> betweenSymmetric(Record3<T1, T2, T3> var1);

   @Support
   Condition betweenSymmetric(Row3<T1, T2, T3> var1, Row3<T1, T2, T3> var2);

   @Support
   Condition betweenSymmetric(Record3<T1, T2, T3> var1, Record3<T1, T2, T3> var2);

   @Support
   BetweenAndStep3<T1, T2, T3> notBetween(T1 var1, T2 var2, T3 var3);

   @Support
   BetweenAndStep3<T1, T2, T3> notBetween(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   BetweenAndStep3<T1, T2, T3> notBetween(Row3<T1, T2, T3> var1);

   @Support
   BetweenAndStep3<T1, T2, T3> notBetween(Record3<T1, T2, T3> var1);

   @Support
   Condition notBetween(Row3<T1, T2, T3> var1, Row3<T1, T2, T3> var2);

   @Support
   Condition notBetween(Record3<T1, T2, T3> var1, Record3<T1, T2, T3> var2);

   @Support
   BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(T1 var1, T2 var2, T3 var3);

   @Support
   BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(Row3<T1, T2, T3> var1);

   @Support
   BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(Record3<T1, T2, T3> var1);

   @Support
   Condition notBetweenSymmetric(Row3<T1, T2, T3> var1, Row3<T1, T2, T3> var2);

   @Support
   Condition notBetweenSymmetric(Record3<T1, T2, T3> var1, Record3<T1, T2, T3> var2);

   @Support
   Condition in(Collection<? extends Row3<T1, T2, T3>> var1);

   @Support
   Condition in(Result<? extends Record3<T1, T2, T3>> var1);

   @Support
   Condition in(Row3<T1, T2, T3>... var1);

   @Support
   Condition in(Record3<T1, T2, T3>... var1);

   @Support
   Condition in(Select<? extends Record3<T1, T2, T3>> var1);

   @Support
   Condition notIn(Collection<? extends Row3<T1, T2, T3>> var1);

   @Support
   Condition notIn(Result<? extends Record3<T1, T2, T3>> var1);

   @Support
   Condition notIn(Row3<T1, T2, T3>... var1);

   @Support
   Condition notIn(Record3<T1, T2, T3>... var1);

   @Support
   Condition notIn(Select<? extends Record3<T1, T2, T3>> var1);
}
