package org.jooq;

import java.util.Collection;

public interface Row2<T1, T2> extends Row {
   Field<T1> field1();

   Field<T2> field2();

   @Support
   Condition compare(Comparator var1, Row2<T1, T2> var2);

   @Support
   Condition compare(Comparator var1, Record2<T1, T2> var2);

   @Support
   Condition compare(Comparator var1, T1 var2, T2 var3);

   @Support
   Condition compare(Comparator var1, Field<T1> var2, Field<T2> var3);

   @Support
   Condition compare(Comparator var1, Select<? extends Record2<T1, T2>> var2);

   @Support
   Condition compare(Comparator var1, QuantifiedSelect<? extends Record2<T1, T2>> var2);

   @Support
   Condition equal(Row2<T1, T2> var1);

   @Support
   Condition equal(Record2<T1, T2> var1);

   @Support
   Condition equal(T1 var1, T2 var2);

   @Support
   Condition equal(Field<T1> var1, Field<T2> var2);

   @Support
   Condition equal(Select<? extends Record2<T1, T2>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition equal(QuantifiedSelect<? extends Record2<T1, T2>> var1);

   @Support
   Condition eq(Row2<T1, T2> var1);

   @Support
   Condition eq(Record2<T1, T2> var1);

   @Support
   Condition eq(T1 var1, T2 var2);

   @Support
   Condition eq(Field<T1> var1, Field<T2> var2);

   @Support
   Condition eq(Select<? extends Record2<T1, T2>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition eq(QuantifiedSelect<? extends Record2<T1, T2>> var1);

   @Support
   Condition notEqual(Row2<T1, T2> var1);

   @Support
   Condition notEqual(Record2<T1, T2> var1);

   @Support
   Condition notEqual(T1 var1, T2 var2);

   @Support
   Condition notEqual(Field<T1> var1, Field<T2> var2);

   @Support
   Condition notEqual(Select<? extends Record2<T1, T2>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition notEqual(QuantifiedSelect<? extends Record2<T1, T2>> var1);

   @Support
   Condition ne(Row2<T1, T2> var1);

   @Support
   Condition ne(Record2<T1, T2> var1);

   @Support
   Condition ne(T1 var1, T2 var2);

   @Support
   Condition ne(Field<T1> var1, Field<T2> var2);

   @Support
   Condition ne(Select<? extends Record2<T1, T2>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ne(QuantifiedSelect<? extends Record2<T1, T2>> var1);

   @Support
   Condition lessThan(Row2<T1, T2> var1);

   @Support
   Condition lessThan(Record2<T1, T2> var1);

   @Support
   Condition lessThan(T1 var1, T2 var2);

   @Support
   Condition lessThan(Field<T1> var1, Field<T2> var2);

   @Support
   Condition lessThan(Select<? extends Record2<T1, T2>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessThan(QuantifiedSelect<? extends Record2<T1, T2>> var1);

   @Support
   Condition lt(Row2<T1, T2> var1);

   @Support
   Condition lt(Record2<T1, T2> var1);

   @Support
   Condition lt(T1 var1, T2 var2);

   @Support
   Condition lt(Field<T1> var1, Field<T2> var2);

   @Support
   Condition lt(Select<? extends Record2<T1, T2>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lt(QuantifiedSelect<? extends Record2<T1, T2>> var1);

   @Support
   Condition lessOrEqual(Row2<T1, T2> var1);

   @Support
   Condition lessOrEqual(Record2<T1, T2> var1);

   @Support
   Condition lessOrEqual(T1 var1, T2 var2);

   @Support
   Condition lessOrEqual(Field<T1> var1, Field<T2> var2);

   @Support
   Condition lessOrEqual(Select<? extends Record2<T1, T2>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessOrEqual(QuantifiedSelect<? extends Record2<T1, T2>> var1);

   @Support
   Condition le(Row2<T1, T2> var1);

   @Support
   Condition le(Record2<T1, T2> var1);

   @Support
   Condition le(T1 var1, T2 var2);

   @Support
   Condition le(Field<T1> var1, Field<T2> var2);

   @Support
   Condition le(Select<? extends Record2<T1, T2>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition le(QuantifiedSelect<? extends Record2<T1, T2>> var1);

   @Support
   Condition greaterThan(Row2<T1, T2> var1);

   @Support
   Condition greaterThan(Record2<T1, T2> var1);

   @Support
   Condition greaterThan(T1 var1, T2 var2);

   @Support
   Condition greaterThan(Field<T1> var1, Field<T2> var2);

   @Support
   Condition greaterThan(Select<? extends Record2<T1, T2>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterThan(QuantifiedSelect<? extends Record2<T1, T2>> var1);

   @Support
   Condition gt(Row2<T1, T2> var1);

   @Support
   Condition gt(Record2<T1, T2> var1);

   @Support
   Condition gt(T1 var1, T2 var2);

   @Support
   Condition gt(Field<T1> var1, Field<T2> var2);

   @Support
   Condition gt(Select<? extends Record2<T1, T2>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition gt(QuantifiedSelect<? extends Record2<T1, T2>> var1);

   @Support
   Condition greaterOrEqual(Row2<T1, T2> var1);

   @Support
   Condition greaterOrEqual(Record2<T1, T2> var1);

   @Support
   Condition greaterOrEqual(T1 var1, T2 var2);

   @Support
   Condition greaterOrEqual(Field<T1> var1, Field<T2> var2);

   @Support
   Condition greaterOrEqual(Select<? extends Record2<T1, T2>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterOrEqual(QuantifiedSelect<? extends Record2<T1, T2>> var1);

   @Support
   Condition ge(Row2<T1, T2> var1);

   @Support
   Condition ge(Record2<T1, T2> var1);

   @Support
   Condition ge(T1 var1, T2 var2);

   @Support
   Condition ge(Field<T1> var1, Field<T2> var2);

   @Support
   Condition ge(Select<? extends Record2<T1, T2>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ge(QuantifiedSelect<? extends Record2<T1, T2>> var1);

   @Support
   BetweenAndStep2<T1, T2> between(T1 var1, T2 var2);

   @Support
   BetweenAndStep2<T1, T2> between(Field<T1> var1, Field<T2> var2);

   @Support
   BetweenAndStep2<T1, T2> between(Row2<T1, T2> var1);

   @Support
   BetweenAndStep2<T1, T2> between(Record2<T1, T2> var1);

   @Support
   Condition between(Row2<T1, T2> var1, Row2<T1, T2> var2);

   @Support
   Condition between(Record2<T1, T2> var1, Record2<T1, T2> var2);

   @Support
   BetweenAndStep2<T1, T2> betweenSymmetric(T1 var1, T2 var2);

   @Support
   BetweenAndStep2<T1, T2> betweenSymmetric(Field<T1> var1, Field<T2> var2);

   @Support
   BetweenAndStep2<T1, T2> betweenSymmetric(Row2<T1, T2> var1);

   @Support
   BetweenAndStep2<T1, T2> betweenSymmetric(Record2<T1, T2> var1);

   @Support
   Condition betweenSymmetric(Row2<T1, T2> var1, Row2<T1, T2> var2);

   @Support
   Condition betweenSymmetric(Record2<T1, T2> var1, Record2<T1, T2> var2);

   @Support
   BetweenAndStep2<T1, T2> notBetween(T1 var1, T2 var2);

   @Support
   BetweenAndStep2<T1, T2> notBetween(Field<T1> var1, Field<T2> var2);

   @Support
   BetweenAndStep2<T1, T2> notBetween(Row2<T1, T2> var1);

   @Support
   BetweenAndStep2<T1, T2> notBetween(Record2<T1, T2> var1);

   @Support
   Condition notBetween(Row2<T1, T2> var1, Row2<T1, T2> var2);

   @Support
   Condition notBetween(Record2<T1, T2> var1, Record2<T1, T2> var2);

   @Support
   BetweenAndStep2<T1, T2> notBetweenSymmetric(T1 var1, T2 var2);

   @Support
   BetweenAndStep2<T1, T2> notBetweenSymmetric(Field<T1> var1, Field<T2> var2);

   @Support
   BetweenAndStep2<T1, T2> notBetweenSymmetric(Row2<T1, T2> var1);

   @Support
   BetweenAndStep2<T1, T2> notBetweenSymmetric(Record2<T1, T2> var1);

   @Support
   Condition notBetweenSymmetric(Row2<T1, T2> var1, Row2<T1, T2> var2);

   @Support
   Condition notBetweenSymmetric(Record2<T1, T2> var1, Record2<T1, T2> var2);

   @Support
   Condition in(Collection<? extends Row2<T1, T2>> var1);

   @Support
   Condition in(Result<? extends Record2<T1, T2>> var1);

   @Support
   Condition in(Row2<T1, T2>... var1);

   @Support
   Condition in(Record2<T1, T2>... var1);

   @Support
   Condition in(Select<? extends Record2<T1, T2>> var1);

   @Support
   Condition notIn(Collection<? extends Row2<T1, T2>> var1);

   @Support
   Condition notIn(Result<? extends Record2<T1, T2>> var1);

   @Support
   Condition notIn(Row2<T1, T2>... var1);

   @Support
   Condition notIn(Record2<T1, T2>... var1);

   @Support
   Condition notIn(Select<? extends Record2<T1, T2>> var1);

   @Support
   Condition overlaps(T1 var1, T2 var2);

   @Support
   Condition overlaps(Field<T1> var1, Field<T2> var2);

   @Support
   Condition overlaps(Row2<T1, T2> var1);
}
