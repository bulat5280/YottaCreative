package org.jooq;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public interface Field<T> extends SelectField<T>, GroupField, FieldOrRow {
   String getName();

   String getComment();

   Converter<?, T> getConverter();

   Binding<?, T> getBinding();

   Class<T> getType();

   DataType<T> getDataType();

   DataType<T> getDataType(Configuration var1);

   @Support
   Field<T> as(String var1);

   @Support
   Field<T> as(Field<?> var1);

   @Support
   Field<T> as(Function<? super Field<T>, ? extends String> var1);

   boolean equals(Object var1);

   @Support
   <Z> Field<Z> cast(Field<Z> var1);

   @Support
   <Z> Field<Z> cast(DataType<Z> var1);

   @Support
   <Z> Field<Z> cast(Class<Z> var1);

   @Support
   <Z> Field<Z> coerce(Field<Z> var1);

   @Support
   <Z> Field<Z> coerce(DataType<Z> var1);

   @Support
   <Z> Field<Z> coerce(Class<Z> var1);

   @Support
   SortField<T> asc();

   @Support
   SortField<T> desc();

   @Support
   SortField<T> sort(SortOrder var1);

   @Support
   SortField<Integer> sortAsc(Collection<T> var1);

   @Support
   SortField<Integer> sortAsc(T... var1);

   @Support
   SortField<Integer> sortDesc(Collection<T> var1);

   @Support
   SortField<Integer> sortDesc(T... var1);

   @Support
   <Z> SortField<Z> sort(Map<T, Z> var1);

   @Support
   Field<T> neg();

   @Support
   Field<T> add(Number var1);

   @Support
   Field<T> add(Field<?> var1);

   @Support
   Field<T> plus(Number var1);

   @Support
   Field<T> plus(Field<?> var1);

   @Support
   Field<T> sub(Number var1);

   @Support
   Field<T> sub(Field<?> var1);

   @Support
   Field<T> subtract(Number var1);

   @Support
   Field<T> subtract(Field<?> var1);

   @Support
   Field<T> minus(Number var1);

   @Support
   Field<T> minus(Field<?> var1);

   @Support
   Field<T> mul(Number var1);

   @Support
   Field<T> mul(Field<? extends Number> var1);

   @Support
   Field<T> multiply(Number var1);

   @Support
   Field<T> multiply(Field<? extends Number> var1);

   @Support
   Field<T> div(Number var1);

   @Support
   Field<T> div(Field<? extends Number> var1);

   @Support
   Field<T> divide(Number var1);

   @Support
   Field<T> divide(Field<? extends Number> var1);

   @Support
   Field<T> mod(Number var1);

   @Support
   Field<T> mod(Field<? extends Number> var1);

   @Support
   Field<T> modulo(Number var1);

   @Support
   Field<T> modulo(Field<? extends Number> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> bitNot();

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> bitAnd(T var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> bitAnd(Field<T> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> bitNand(T var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> bitNand(Field<T> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> bitOr(T var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> bitOr(Field<T> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> bitNor(T var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> bitNor(Field<T> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> bitXor(T var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> bitXor(Field<T> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> bitXNor(T var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> bitXNor(Field<T> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> shl(Number var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> shl(Field<? extends Number> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> shr(Number var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<T> shr(Field<? extends Number> var1);

   @Support
   Condition isNull();

   @Support
   Condition isNotNull();

   @Support
   Condition isDistinctFrom(T var1);

   @Support
   Condition isDistinctFrom(Field<T> var1);

   @Support
   Condition isNotDistinctFrom(T var1);

   @Support
   Condition isNotDistinctFrom(Field<T> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Condition likeRegex(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Condition likeRegex(Field<String> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Condition notLikeRegex(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Condition notLikeRegex(Field<String> var1);

   @Support
   LikeEscapeStep like(Field<String> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Condition like(Field<String> var1, char var2);

   @Support
   LikeEscapeStep like(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Condition like(String var1, char var2);

   @Support
   LikeEscapeStep likeIgnoreCase(Field<String> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Condition likeIgnoreCase(Field<String> var1, char var2);

   @Support
   LikeEscapeStep likeIgnoreCase(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Condition likeIgnoreCase(String var1, char var2);

   @Support
   LikeEscapeStep notLike(Field<String> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Condition notLike(Field<String> var1, char var2);

   @Support
   LikeEscapeStep notLike(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Condition notLike(String var1, char var2);

   @Support
   LikeEscapeStep notLikeIgnoreCase(Field<String> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Condition notLikeIgnoreCase(Field<String> var1, char var2);

   @Support
   LikeEscapeStep notLikeIgnoreCase(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Condition notLikeIgnoreCase(String var1, char var2);

   @Support
   Condition contains(T var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Condition contains(Field<T> var1);

   @Support
   Condition startsWith(T var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Condition startsWith(Field<T> var1);

   @Support
   Condition endsWith(T var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Condition endsWith(Field<T> var1);

   @Support
   Condition in(Collection<?> var1);

   Condition in(Result<? extends Record1<T>> var1);

   @Support
   Condition in(T... var1);

   @Support
   Condition in(Field<?>... var1);

   @Support
   Condition in(Select<? extends Record1<T>> var1);

   @Support
   Condition notIn(Collection<?> var1);

   Condition notIn(Result<? extends Record1<T>> var1);

   @Support
   Condition notIn(T... var1);

   @Support
   Condition notIn(Field<?>... var1);

   @Support
   Condition notIn(Select<? extends Record1<T>> var1);

   @Support
   Condition between(T var1, T var2);

   @Support
   Condition between(Field<T> var1, Field<T> var2);

   @Support
   Condition betweenSymmetric(T var1, T var2);

   @Support
   Condition betweenSymmetric(Field<T> var1, Field<T> var2);

   @Support
   Condition notBetween(T var1, T var2);

   @Support
   Condition notBetween(Field<T> var1, Field<T> var2);

   @Support
   Condition notBetweenSymmetric(T var1, T var2);

   @Support
   Condition notBetweenSymmetric(Field<T> var1, Field<T> var2);

   @Support
   BetweenAndStep<T> between(T var1);

   @Support
   BetweenAndStep<T> between(Field<T> var1);

   @Support
   BetweenAndStep<T> betweenSymmetric(T var1);

   @Support
   BetweenAndStep<T> betweenSymmetric(Field<T> var1);

   @Support
   BetweenAndStep<T> notBetween(T var1);

   @Support
   BetweenAndStep<T> notBetween(Field<T> var1);

   @Support
   BetweenAndStep<T> notBetweenSymmetric(T var1);

   @Support
   BetweenAndStep<T> notBetweenSymmetric(Field<T> var1);

   @Support
   Condition compare(Comparator var1, T var2);

   @Support
   Condition compare(Comparator var1, Field<T> var2);

   @Support
   Condition compare(Comparator var1, Select<? extends Record1<T>> var2);

   @Support
   Condition compare(Comparator var1, QuantifiedSelect<? extends Record1<T>> var2);

   @Support
   Condition equal(T var1);

   @Support
   Condition equal(Field<T> var1);

   @Support
   Condition equal(Select<? extends Record1<T>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition equal(QuantifiedSelect<? extends Record1<T>> var1);

   @Support
   Condition eq(T var1);

   @Support
   Condition eq(Field<T> var1);

   @Support
   Condition eq(Select<? extends Record1<T>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition eq(QuantifiedSelect<? extends Record1<T>> var1);

   @Support
   Condition notEqual(T var1);

   @Support
   Condition notEqual(Field<T> var1);

   @Support
   Condition notEqual(Select<? extends Record1<T>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition notEqual(QuantifiedSelect<? extends Record1<T>> var1);

   @Support
   Condition ne(T var1);

   @Support
   Condition ne(Field<T> var1);

   @Support
   Condition ne(Select<? extends Record1<T>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ne(QuantifiedSelect<? extends Record1<T>> var1);

   @Support
   Condition lessThan(T var1);

   @Support
   Condition lessThan(Field<T> var1);

   @Support
   Condition lessThan(Select<? extends Record1<T>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessThan(QuantifiedSelect<? extends Record1<T>> var1);

   @Support
   Condition lt(T var1);

   @Support
   Condition lt(Field<T> var1);

   @Support
   Condition lt(Select<? extends Record1<T>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lt(QuantifiedSelect<? extends Record1<T>> var1);

   @Support
   Condition lessOrEqual(T var1);

   @Support
   Condition lessOrEqual(Field<T> var1);

   @Support
   Condition lessOrEqual(Select<? extends Record1<T>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition lessOrEqual(QuantifiedSelect<? extends Record1<T>> var1);

   @Support
   Condition le(T var1);

   @Support
   Condition le(Field<T> var1);

   @Support
   Condition le(Select<? extends Record1<T>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition le(QuantifiedSelect<? extends Record1<T>> var1);

   @Support
   Condition greaterThan(T var1);

   @Support
   Condition greaterThan(Field<T> var1);

   @Support
   Condition greaterThan(Select<? extends Record1<T>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterThan(QuantifiedSelect<? extends Record1<T>> var1);

   @Support
   Condition gt(T var1);

   @Support
   Condition gt(Field<T> var1);

   @Support
   Condition gt(Select<? extends Record1<T>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition gt(QuantifiedSelect<? extends Record1<T>> var1);

   @Support
   Condition greaterOrEqual(T var1);

   @Support
   Condition greaterOrEqual(Field<T> var1);

   @Support
   Condition greaterOrEqual(Select<? extends Record1<T>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition greaterOrEqual(QuantifiedSelect<? extends Record1<T>> var1);

   @Support
   Condition ge(T var1);

   @Support
   Condition ge(Field<T> var1);

   @Support
   Condition ge(Select<? extends Record1<T>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Condition ge(QuantifiedSelect<? extends Record1<T>> var1);

   @Support
   Condition isTrue();

   @Support
   Condition isFalse();

   @Support
   Condition equalIgnoreCase(String var1);

   @Support
   Condition equalIgnoreCase(Field<String> var1);

   @Support
   Condition notEqualIgnoreCase(String var1);

   @Support
   Condition notEqualIgnoreCase(Field<String> var1);

   @Support
   Field<Integer> sign();

   @Support
   Field<T> abs();

   @Support
   Field<T> round();

   @Support
   Field<T> round(int var1);

   @Support
   Field<T> floor();

   @Support
   Field<T> ceil();

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> sqrt();

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> exp();

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> ln();

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> log(int var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> pow(Number var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> power(Number var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> acos();

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> asin();

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> atan();

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> atan2(Number var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> atan2(Field<? extends Number> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> cos();

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> sin();

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> tan();

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> cot();

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> sinh();

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> cosh();

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> tanh();

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> coth();

   @Support
   Field<BigDecimal> deg();

   @Support
   Field<BigDecimal> rad();

   @Support
   Field<Integer> count();

   @Support
   Field<Integer> countDistinct();

   @Support
   Field<T> max();

   @Support
   Field<T> min();

   @Support
   Field<BigDecimal> sum();

   @Support
   Field<BigDecimal> avg();

   @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
   Field<BigDecimal> median();

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> stddevPop();

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> stddevSamp();

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> varPop();

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<BigDecimal> varSamp();

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowPartitionByStep<Integer> countOver();

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowPartitionByStep<T> maxOver();

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowPartitionByStep<T> minOver();

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowPartitionByStep<BigDecimal> sumOver();

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowPartitionByStep<BigDecimal> avgOver();

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowIgnoreNullsStep<T> firstValue();

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowIgnoreNullsStep<T> lastValue();

   @Support({SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowIgnoreNullsStep<T> lead();

   @Support({SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowIgnoreNullsStep<T> lead(int var1);

   @Support({SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowIgnoreNullsStep<T> lead(int var1, T var2);

   @Support({SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowIgnoreNullsStep<T> lead(int var1, Field<T> var2);

   @Support({SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowIgnoreNullsStep<T> lag();

   @Support({SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowIgnoreNullsStep<T> lag(int var1);

   @Support({SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowIgnoreNullsStep<T> lag(int var1, T var2);

   @Support({SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowIgnoreNullsStep<T> lag(int var1, Field<T> var2);

   @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
   WindowPartitionByStep<BigDecimal> stddevPopOver();

   @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
   WindowPartitionByStep<BigDecimal> stddevSampOver();

   @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
   WindowPartitionByStep<BigDecimal> varPopOver();

   @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
   WindowPartitionByStep<BigDecimal> varSampOver();

   @Support
   Field<String> upper();

   @Support
   Field<String> lower();

   @Support
   Field<String> trim();

   @Support
   Field<String> rtrim();

   @Support
   Field<String> ltrim();

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<String> rpad(Field<? extends Number> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<String> rpad(int var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<String> rpad(Field<? extends Number> var1, Field<String> var2);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<String> rpad(int var1, char var2);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<String> lpad(Field<? extends Number> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<String> lpad(int var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<String> lpad(Field<? extends Number> var1, Field<String> var2);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<String> lpad(int var1, char var2);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<String> repeat(Number var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<String> repeat(Field<? extends Number> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<String> replace(Field<String> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<String> replace(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<String> replace(Field<String> var1, Field<String> var2);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Field<String> replace(String var1, String var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<Integer> position(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<Integer> position(Field<String> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   Field<Integer> ascii();

   @Support
   Field<String> concat(Field<?>... var1);

   @Support
   Field<String> concat(String... var1);

   @Support
   Field<String> substring(int var1);

   @Support
   Field<String> substring(Field<? extends Number> var1);

   @Support
   Field<String> substring(int var1, int var2);

   @Support
   Field<String> substring(Field<? extends Number> var1, Field<? extends Number> var2);

   @Support
   Field<Integer> length();

   @Support
   Field<Integer> charLength();

   @Support
   Field<Integer> bitLength();

   @Support
   Field<Integer> octetLength();

   @Support
   Field<Integer> extract(DatePart var1);

   @Support
   Field<T> greatest(T... var1);

   @Support
   Field<T> greatest(Field<?>... var1);

   @Support
   Field<T> least(T... var1);

   @Support
   Field<T> least(Field<?>... var1);

   @Support
   Field<T> nvl(T var1);

   @Support
   Field<T> nvl(Field<T> var1);

   @Support
   <Z> Field<Z> nvl2(Z var1, Z var2);

   @Support
   <Z> Field<Z> nvl2(Field<Z> var1, Field<Z> var2);

   @Support
   Field<T> nullif(T var1);

   @Support
   Field<T> nullif(Field<T> var1);

   @Support
   <Z> Field<Z> decode(T var1, Z var2);

   @Support
   <Z> Field<Z> decode(T var1, Z var2, Object... var3);

   @Support
   <Z> Field<Z> decode(Field<T> var1, Field<Z> var2);

   @Support
   <Z> Field<Z> decode(Field<T> var1, Field<Z> var2, Field<?>... var3);

   @Support
   Field<T> coalesce(T var1, T... var2);

   @Support
   Field<T> coalesce(Field<T> var1, Field<?>... var2);

   Field<T> field(Record var1);

   T get(Record var1);

   T getValue(Record var1);

   T original(Record var1);

   boolean changed(Record var1);

   void reset(Record var1);

   Record1<T> from(Record var1);
}
