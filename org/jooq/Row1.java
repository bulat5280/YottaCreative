package org.jooq;

import java.util.Collection;

public interface Row1<T1> extends Row {
   Field<T1> field1();

   @Support
   Condition compare(Comparator var1, Row1<T1> var2);

   @Support
   Condition compare(Comparator var1, Record1<T1> var2);

   @Support
   Condition compare(Comparator var1, T1 var2);

   @Support
   Condition compare(Comparator var1, Field<T1> var2);

   @Support
   Condition compare(Comparator var1, Select<? extends Record1<T1>> var2);

   @Support
   Condition compare(Comparator var1, QuantifiedSelect<? extends Record1<T1>> var2);

   @Support
   Condition equal(Row1<T1> var1);

   @Support
   Condition equal(Record1<T1> var1);

   @Support
   Condition equal(T1 var1);

   @Support
   Condition equal(Field<T1> var1);

   @Support
   Condition equal(Select<? extends Record1<T1>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition equal(QuantifiedSelect<? extends Record1<T1>> var1);

   @Support
   Condition eq(Row1<T1> var1);

   @Support
   Condition eq(Record1<T1> var1);

   @Support
   Condition eq(T1 var1);

   @Support
   Condition eq(Field<T1> var1);

   @Support
   Condition eq(Select<? extends Record1<T1>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition eq(QuantifiedSelect<? extends Record1<T1>> var1);

   @Support
   Condition notEqual(Row1<T1> var1);

   @Support
   Condition notEqual(Record1<T1> var1);

   @Support
   Condition notEqual(T1 var1);

   @Support
   Condition notEqual(Field<T1> var1);

   @Support
   Condition notEqual(Select<? extends Record1<T1>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition notEqual(QuantifiedSelect<? extends Record1<T1>> var1);

   @Support
   Condition ne(Row1<T1> var1);

   @Support
   Condition ne(Record1<T1> var1);

   @Support
   Condition ne(T1 var1);

   @Support
   Condition ne(Field<T1> var1);

   @Support
   Condition ne(Select<? extends Record1<T1>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ne(QuantifiedSelect<? extends Record1<T1>> var1);

   @Support
   Condition lessThan(Row1<T1> var1);

   @Support
   Condition lessThan(Record1<T1> var1);

   @Support
   Condition lessThan(T1 var1);

   @Support
   Condition lessThan(Field<T1> var1);

   @Support
   Condition lessThan(Select<? extends Record1<T1>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessThan(QuantifiedSelect<? extends Record1<T1>> var1);

   @Support
   Condition lt(Row1<T1> var1);

   @Support
   Condition lt(Record1<T1> var1);

   @Support
   Condition lt(T1 var1);

   @Support
   Condition lt(Field<T1> var1);

   @Support
   Condition lt(Select<? extends Record1<T1>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lt(QuantifiedSelect<? extends Record1<T1>> var1);

   @Support
   Condition lessOrEqual(Row1<T1> var1);

   @Support
   Condition lessOrEqual(Record1<T1> var1);

   @Support
   Condition lessOrEqual(T1 var1);

   @Support
   Condition lessOrEqual(Field<T1> var1);

   @Support
   Condition lessOrEqual(Select<? extends Record1<T1>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessOrEqual(QuantifiedSelect<? extends Record1<T1>> var1);

   @Support
   Condition le(Row1<T1> var1);

   @Support
   Condition le(Record1<T1> var1);

   @Support
   Condition le(T1 var1);

   @Support
   Condition le(Field<T1> var1);

   @Support
   Condition le(Select<? extends Record1<T1>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition le(QuantifiedSelect<? extends Record1<T1>> var1);

   @Support
   Condition greaterThan(Row1<T1> var1);

   @Support
   Condition greaterThan(Record1<T1> var1);

   @Support
   Condition greaterThan(T1 var1);

   @Support
   Condition greaterThan(Field<T1> var1);

   @Support
   Condition greaterThan(Select<? extends Record1<T1>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterThan(QuantifiedSelect<? extends Record1<T1>> var1);

   @Support
   Condition gt(Row1<T1> var1);

   @Support
   Condition gt(Record1<T1> var1);

   @Support
   Condition gt(T1 var1);

   @Support
   Condition gt(Field<T1> var1);

   @Support
   Condition gt(Select<? extends Record1<T1>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition gt(QuantifiedSelect<? extends Record1<T1>> var1);

   @Support
   Condition greaterOrEqual(Row1<T1> var1);

   @Support
   Condition greaterOrEqual(Record1<T1> var1);

   @Support
   Condition greaterOrEqual(T1 var1);

   @Support
   Condition greaterOrEqual(Field<T1> var1);

   @Support
   Condition greaterOrEqual(Select<? extends Record1<T1>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterOrEqual(QuantifiedSelect<? extends Record1<T1>> var1);

   @Support
   Condition ge(Row1<T1> var1);

   @Support
   Condition ge(Record1<T1> var1);

   @Support
   Condition ge(T1 var1);

   @Support
   Condition ge(Field<T1> var1);

   @Support
   Condition ge(Select<? extends Record1<T1>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ge(QuantifiedSelect<? extends Record1<T1>> var1);

   @Support
   BetweenAndStep1<T1> between(T1 var1);

   @Support
   BetweenAndStep1<T1> between(Field<T1> var1);

   @Support
   BetweenAndStep1<T1> between(Row1<T1> var1);

   @Support
   BetweenAndStep1<T1> between(Record1<T1> var1);

   @Support
   Condition between(Row1<T1> var1, Row1<T1> var2);

   @Support
   Condition between(Record1<T1> var1, Record1<T1> var2);

   @Support
   BetweenAndStep1<T1> betweenSymmetric(T1 var1);

   @Support
   BetweenAndStep1<T1> betweenSymmetric(Field<T1> var1);

   @Support
   BetweenAndStep1<T1> betweenSymmetric(Row1<T1> var1);

   @Support
   BetweenAndStep1<T1> betweenSymmetric(Record1<T1> var1);

   @Support
   Condition betweenSymmetric(Row1<T1> var1, Row1<T1> var2);

   @Support
   Condition betweenSymmetric(Record1<T1> var1, Record1<T1> var2);

   @Support
   BetweenAndStep1<T1> notBetween(T1 var1);

   @Support
   BetweenAndStep1<T1> notBetween(Field<T1> var1);

   @Support
   BetweenAndStep1<T1> notBetween(Row1<T1> var1);

   @Support
   BetweenAndStep1<T1> notBetween(Record1<T1> var1);

   @Support
   Condition notBetween(Row1<T1> var1, Row1<T1> var2);

   @Support
   Condition notBetween(Record1<T1> var1, Record1<T1> var2);

   @Support
   BetweenAndStep1<T1> notBetweenSymmetric(T1 var1);

   @Support
   BetweenAndStep1<T1> notBetweenSymmetric(Field<T1> var1);

   @Support
   BetweenAndStep1<T1> notBetweenSymmetric(Row1<T1> var1);

   @Support
   BetweenAndStep1<T1> notBetweenSymmetric(Record1<T1> var1);

   @Support
   Condition notBetweenSymmetric(Row1<T1> var1, Row1<T1> var2);

   @Support
   Condition notBetweenSymmetric(Record1<T1> var1, Record1<T1> var2);

   @Support
   Condition in(Collection<? extends Row1<T1>> var1);

   @Support
   Condition in(Result<? extends Record1<T1>> var1);

   @Support
   Condition in(Row1<T1>... var1);

   @Support
   Condition in(Record1<T1>... var1);

   @Support
   Condition in(Select<? extends Record1<T1>> var1);

   @Support
   Condition notIn(Collection<? extends Row1<T1>> var1);

   @Support
   Condition notIn(Result<? extends Record1<T1>> var1);

   @Support
   Condition notIn(Row1<T1>... var1);

   @Support
   Condition notIn(Record1<T1>... var1);

   @Support
   Condition notIn(Select<? extends Record1<T1>> var1);
}
