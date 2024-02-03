package org.jooq.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jooq.BetweenAndStep;
import org.jooq.Binding;
import org.jooq.CaseValueStep;
import org.jooq.CaseWhenStep;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.LikeEscapeStep;
import org.jooq.QuantifiedSelect;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.WindowIgnoreNullsStep;
import org.jooq.WindowPartitionByStep;
import org.jooq.tools.Convert;
import org.jooq.tools.StringUtils;

abstract class AbstractField<T> extends AbstractQueryPart implements Field<T> {
   private static final long serialVersionUID = 2884811923648354905L;
   private static final Clause[] CLAUSES;
   private final String name;
   private final String comment;
   private final DataType<T> dataType;

   AbstractField(String name, DataType<T> type) {
      this(name, type, (String)null, type.getBinding());
   }

   AbstractField(String name, DataType<T> type, String comment, Binding<?, T> binding) {
      this.name = name;
      this.comment = StringUtils.defaultString(comment);
      this.dataType = type.asConvertedDataType(binding);
   }

   public abstract void accept(Context<?> var1);

   public Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   public final Field<T> field(Record record) {
      return record.field((Field)this);
   }

   public final T get(Record record) {
      return record.get((Field)this);
   }

   public final T getValue(Record record) {
      return record.getValue((Field)this);
   }

   public final T original(Record record) {
      return record.original((Field)this);
   }

   public final boolean changed(Record record) {
      return record.changed((Field)this);
   }

   public final void reset(Record record) {
      record.reset((Field)this);
   }

   public final Record1<T> from(Record record) {
      return record.into((Field)this);
   }

   public Field<T> as(String alias) {
      return new FieldAlias(this, alias);
   }

   public final Field<T> as(Field<?> otherField) {
      return this.as(otherField.getName());
   }

   public final Field<T> as(java.util.function.Function<? super Field<T>, ? extends String> aliasFunction) {
      return this.as((String)aliasFunction.apply(this));
   }

   public final String getName() {
      return this.name;
   }

   public final String getComment() {
      return this.comment;
   }

   public final Converter<?, T> getConverter() {
      return this.getBinding().converter();
   }

   public final Binding<?, T> getBinding() {
      return this.dataType.getBinding();
   }

   public final DataType<T> getDataType() {
      return this.dataType;
   }

   public final DataType<T> getDataType(Configuration configuration) {
      return this.dataType.getDataType(configuration);
   }

   public final Class<T> getType() {
      return this.dataType.getType();
   }

   public final <Z> Field<Z> cast(Field<Z> field) {
      return this.cast(field.getDataType());
   }

   public final <Z> Field<Z> cast(DataType<Z> type) {
      return (Field)(this.getDataType().equals(type) ? this : new Cast(this, type));
   }

   public final <Z> Field<Z> cast(Class<Z> type) {
      return (Field)(this.getType() == type ? this : this.cast(DefaultDataType.getDataType((SQLDialect)null, (Class)type)));
   }

   public final <Z> Field<Z> coerce(Field<Z> field) {
      return this.coerce(field.getDataType());
   }

   public final <Z> Field<Z> coerce(DataType<Z> type) {
      return (Field)(this.getDataType().equals(type) ? this : new Coerce(this, type));
   }

   public final <Z> Field<Z> coerce(Class<Z> type) {
      return this.coerce(DefaultDataType.getDataType((SQLDialect)null, (Class)type));
   }

   public final SortField<T> asc() {
      return this.sort(SortOrder.ASC);
   }

   public final SortField<T> desc() {
      return this.sort(SortOrder.DESC);
   }

   public final SortField<T> sort(SortOrder order) {
      return new SortFieldImpl(this, order);
   }

   public final SortField<Integer> sortAsc(Collection<T> sortList) {
      Map<T, Integer> map = new LinkedHashMap();
      int i = 0;
      Iterator var4 = sortList.iterator();

      while(var4.hasNext()) {
         T value = var4.next();
         map.put(value, i++);
      }

      return this.sort((Map)map);
   }

   @SafeVarargs
   public final SortField<Integer> sortAsc(T... sortList) {
      return this.sortAsc((Collection)Arrays.asList(sortList));
   }

   public final SortField<Integer> sortDesc(Collection<T> sortList) {
      Map<T, Integer> map = new LinkedHashMap();
      int i = 0;
      Iterator var4 = sortList.iterator();

      while(var4.hasNext()) {
         T value = var4.next();
         map.put(value, i--);
      }

      return this.sort((Map)map);
   }

   @SafeVarargs
   public final SortField<Integer> sortDesc(T... sortList) {
      return this.sortDesc((Collection)Arrays.asList(sortList));
   }

   public final <Z> SortField<Z> sort(Map<T, Z> sortMap) {
      CaseValueStep<T> decode = DSL.choose((Field)this);
      CaseWhenStep<T, Z> result = null;
      Iterator var4 = sortMap.entrySet().iterator();

      while(var4.hasNext()) {
         Entry<T, Z> entry = (Entry)var4.next();
         if (result == null) {
            result = decode.when((Object)entry.getKey(), (Field)DSL.inline(entry.getValue()));
         } else {
            result.when((Object)entry.getKey(), (Field)DSL.inline(entry.getValue()));
         }
      }

      if (result == null) {
         return null;
      } else {
         return result.asc();
      }
   }

   public final Field<T> neg() {
      return new Neg(this, ExpressionOperator.SUBTRACT);
   }

   public final Field<T> add(Number value) {
      return this.add(Tools.field((Object)value));
   }

   public Field<T> add(Field<?> value) {
      return new Expression(ExpressionOperator.ADD, this, new Field[]{DSL.nullSafe(value, this.getDataType())});
   }

   public final Field<T> sub(Number value) {
      return this.sub(Tools.field((Object)value));
   }

   public final Field<T> sub(Field<?> value) {
      return new Expression(ExpressionOperator.SUBTRACT, this, new Field[]{DSL.nullSafe(value, this.getDataType())});
   }

   public final Field<T> mul(Number value) {
      return this.mul(Tools.field((Object)value));
   }

   public Field<T> mul(Field<? extends Number> value) {
      return new Expression(ExpressionOperator.MULTIPLY, this, new Field[]{DSL.nullSafe(value, this.getDataType())});
   }

   public final Field<T> div(Number value) {
      return this.div(Tools.field((Object)value));
   }

   public final Field<T> div(Field<? extends Number> value) {
      return new Expression(ExpressionOperator.DIVIDE, this, new Field[]{DSL.nullSafe(value, this.getDataType())});
   }

   public final Field<T> mod(Number value) {
      return this.mod(Tools.field((Object)value));
   }

   public final Field<T> mod(Field<? extends Number> value) {
      return new Mod(this, DSL.nullSafe(value, this.getDataType()));
   }

   public final Field<T> plus(Number value) {
      return this.add(value);
   }

   public final Field<T> plus(Field<?> value) {
      return this.add(value);
   }

   public final Field<T> subtract(Number value) {
      return this.sub(value);
   }

   public final Field<T> subtract(Field<?> value) {
      return this.sub(value);
   }

   public final Field<T> minus(Number value) {
      return this.sub(value);
   }

   public final Field<T> minus(Field<?> value) {
      return this.sub(value);
   }

   public final Field<T> multiply(Number value) {
      return this.mul(value);
   }

   public final Field<T> multiply(Field<? extends Number> value) {
      return this.mul(value);
   }

   public final Field<T> divide(Number value) {
      return this.div(value);
   }

   public final Field<T> divide(Field<? extends Number> value) {
      return this.div(value);
   }

   public final Field<T> modulo(Number value) {
      return this.mod(value);
   }

   public final Field<T> modulo(Field<? extends Number> value) {
      return this.mod(value);
   }

   public final Field<T> bitNot() {
      Field result = DSL.bitNot((Field)this);
      return result;
   }

   public final Field<T> bitAnd(T value) {
      Field result = DSL.bitAnd((Field)this, (Field)DSL.val(value, (Field)this));
      return result;
   }

   public final Field<T> bitAnd(Field<T> value) {
      Field result = DSL.bitAnd((Field)this, (Field)value);
      return result;
   }

   public final Field<T> bitNand(T value) {
      Field result = DSL.bitNand((Field)this, (Field)DSL.val(value, (Field)this));
      return result;
   }

   public final Field<T> bitNand(Field<T> value) {
      Field result = DSL.bitNand((Field)this, (Field)value);
      return result;
   }

   public final Field<T> bitOr(T value) {
      Field result = DSL.bitOr((Field)this, (Field)DSL.val(value, (Field)this));
      return result;
   }

   public final Field<T> bitOr(Field<T> value) {
      Field result = DSL.bitOr((Field)this, (Field)value);
      return result;
   }

   public final Field<T> bitNor(T value) {
      Field result = DSL.bitNor((Field)this, (Field)DSL.val(value, (Field)this));
      return result;
   }

   public final Field<T> bitNor(Field<T> value) {
      Field result = DSL.bitNor((Field)this, (Field)value);
      return result;
   }

   public final Field<T> bitXor(T value) {
      Field result = DSL.bitXor((Field)this, (Field)DSL.val(value, (Field)this));
      return result;
   }

   public final Field<T> bitXor(Field<T> value) {
      Field result = DSL.bitXor((Field)this, (Field)value);
      return result;
   }

   public final Field<T> bitXNor(T value) {
      Field result = DSL.bitXNor((Field)this, (Field)DSL.val(value, (Field)this));
      return result;
   }

   public final Field<T> bitXNor(Field<T> value) {
      Field result = DSL.bitXNor((Field)this, (Field)value);
      return result;
   }

   public final Field<T> shl(Number value) {
      Field result = DSL.shl((Field)this, (Field)Tools.field((Object)value));
      return result;
   }

   public final Field<T> shl(Field<? extends Number> value) {
      Field result = DSL.shl((Field)this, (Field)value);
      return result;
   }

   public final Field<T> shr(Number value) {
      Field result = DSL.shr((Field)this, (Field)Tools.field((Object)value));
      return result;
   }

   public final Field<T> shr(Field<? extends Number> value) {
      Field result = DSL.shr((Field)this, (Field)value);
      return result;
   }

   public final Condition isNull() {
      return new IsNull(this, true);
   }

   public final Condition isNotNull() {
      return new IsNull(this, false);
   }

   public final Condition isDistinctFrom(T value) {
      return this.isDistinctFrom(Tools.field(value, (Field)this));
   }

   public final Condition isDistinctFrom(Field<T> field) {
      return this.compare(Comparator.IS_DISTINCT_FROM, field);
   }

   public final Condition isNotDistinctFrom(T value) {
      return this.isNotDistinctFrom(Tools.field(value, (Field)this));
   }

   public final Condition isNotDistinctFrom(Field<T> field) {
      return this.compare(Comparator.IS_NOT_DISTINCT_FROM, field);
   }

   public final Condition isTrue() {
      Class<?> type = this.getType();
      if (type == String.class) {
         return this.in((Collection)Tools.inline(Convert.TRUE_VALUES.toArray(Tools.EMPTY_STRING)));
      } else if (Number.class.isAssignableFrom(type)) {
         return this.equal((Field)DSL.inline((Object)((Number)this.getDataType().convert((int)1))));
      } else {
         return Boolean.class.isAssignableFrom(type) ? this.equal((Field)DSL.inline(true)) : this.cast(String.class).in((Collection)Convert.TRUE_VALUES);
      }
   }

   public final Condition isFalse() {
      Class<?> type = this.getType();
      if (type == String.class) {
         return this.in((Collection)Tools.inline(Convert.FALSE_VALUES.toArray(Tools.EMPTY_STRING)));
      } else if (Number.class.isAssignableFrom(type)) {
         return this.equal((Field)DSL.inline((Object)((Number)this.getDataType().convert((int)0))));
      } else {
         return Boolean.class.isAssignableFrom(type) ? this.equal((Field)DSL.inline(false)) : this.cast(String.class).in((Collection)Tools.inline(Convert.FALSE_VALUES.toArray(Tools.EMPTY_STRING)));
      }
   }

   public final LikeEscapeStep like(String value) {
      return this.like(Tools.field(value, (Class)String.class));
   }

   public final Condition like(String value, char escape) {
      return this.like(Tools.field(value, (Class)String.class), escape);
   }

   public final LikeEscapeStep like(Field<String> field) {
      return new CompareCondition(this, DSL.nullSafe(field, this.getDataType()), Comparator.LIKE);
   }

   public final Condition like(Field<String> field, char escape) {
      return this.like(field).escape(escape);
   }

   public final LikeEscapeStep likeIgnoreCase(String value) {
      return this.likeIgnoreCase(Tools.field(value, (Class)String.class));
   }

   public final Condition likeIgnoreCase(String value, char escape) {
      return this.likeIgnoreCase(Tools.field(value, (Class)String.class), escape);
   }

   public final LikeEscapeStep likeIgnoreCase(Field<String> field) {
      return new CompareCondition(this, DSL.nullSafe(field, this.getDataType()), Comparator.LIKE_IGNORE_CASE);
   }

   public final Condition likeIgnoreCase(Field<String> field, char escape) {
      return this.likeIgnoreCase(field).escape(escape);
   }

   public final Condition likeRegex(String pattern) {
      return this.likeRegex(Tools.field(pattern, (Class)String.class));
   }

   public final Condition likeRegex(Field<String> pattern) {
      return new RegexpLike(this, DSL.nullSafe(pattern, this.getDataType()));
   }

   public final LikeEscapeStep notLike(String value) {
      return this.notLike(Tools.field(value, (Class)String.class));
   }

   public final Condition notLike(String value, char escape) {
      return this.notLike(Tools.field(value, (Class)String.class), escape);
   }

   public final LikeEscapeStep notLike(Field<String> field) {
      return new CompareCondition(this, DSL.nullSafe(field, this.getDataType()), Comparator.NOT_LIKE);
   }

   public final Condition notLike(Field<String> field, char escape) {
      return this.notLike(field).escape(escape);
   }

   public final LikeEscapeStep notLikeIgnoreCase(String value) {
      return this.notLikeIgnoreCase(Tools.field(value, (Class)String.class));
   }

   public final Condition notLikeIgnoreCase(String value, char escape) {
      return this.notLikeIgnoreCase(Tools.field(value, (Class)String.class), escape);
   }

   public final LikeEscapeStep notLikeIgnoreCase(Field<String> field) {
      return new CompareCondition(this, DSL.nullSafe(field, this.getDataType()), Comparator.NOT_LIKE_IGNORE_CASE);
   }

   public final Condition notLikeIgnoreCase(Field<String> field, char escape) {
      return this.notLikeIgnoreCase(field).escape(escape);
   }

   public final Condition notLikeRegex(String pattern) {
      return this.likeRegex(pattern).not();
   }

   public final Condition notLikeRegex(Field<String> pattern) {
      return this.likeRegex(pattern).not();
   }

   public final Condition contains(T value) {
      return new Contains(this, value);
   }

   public final Condition contains(Field<T> value) {
      return new Contains(this, value);
   }

   public final Condition startsWith(T value) {
      Field<String> concat = DSL.concat(Tools.escapeForLike(value), DSL.inline("%"));
      return this.like(concat, '!');
   }

   public final Condition startsWith(Field<T> value) {
      Field<String> concat = DSL.concat(Tools.escapeForLike(value), DSL.inline("%"));
      return this.like(concat, '!');
   }

   public final Condition endsWith(T value) {
      Field<String> concat = DSL.concat(DSL.inline("%"), Tools.escapeForLike(value));
      return this.like(concat, '!');
   }

   public final Condition endsWith(Field<T> value) {
      Field<String> concat = DSL.concat(DSL.inline("%"), Tools.escapeForLike(value));
      return this.like(concat, '!');
   }

   private final boolean isAccidentalSelect(T[] values) {
      return values != null && values.length == 1 && values[0] instanceof Select;
   }

   private final boolean isAccidentalCollection(T[] values) {
      return values != null && values.length == 1 && values[0] instanceof Collection;
   }

   public final Condition in(T... values) {
      if (this.isAccidentalSelect(values)) {
         return this.in((Select)values[0]);
      } else {
         return this.isAccidentalCollection(values) ? this.in((Collection)values[0]) : this.in((Field[])Tools.fields(values, (Field)this).toArray(Tools.EMPTY_FIELD));
      }
   }

   public final Condition in(Field<?>... values) {
      return new InCondition(this, DSL.nullSafe(values), Comparator.IN);
   }

   public final Condition in(Collection<?> values) {
      List<Field<?>> fields = new ArrayList();
      Iterator var3 = values.iterator();

      while(var3.hasNext()) {
         Object value = var3.next();
         fields.add(Tools.field(value, (Field)this));
      }

      return this.in((Field[])fields.toArray(Tools.EMPTY_FIELD));
   }

   public final Condition in(Result<? extends Record1<T>> result) {
      return this.in((Collection)result.getValues(0, (Class)this.getType()));
   }

   public final Condition in(Select<? extends Record1<T>> query) {
      return this.compare(Comparator.IN, query);
   }

   public final Condition notIn(T... values) {
      if (this.isAccidentalSelect(values)) {
         return this.notIn((Select)values[0]);
      } else {
         return this.isAccidentalCollection(values) ? this.notIn((Collection)values[0]) : this.notIn((Field[])Tools.fields(values, (Field)this).toArray(Tools.EMPTY_FIELD));
      }
   }

   public final Condition notIn(Field<?>... values) {
      return new InCondition(this, DSL.nullSafe(values), Comparator.NOT_IN);
   }

   public final Condition notIn(Collection<?> values) {
      List<Field<?>> fields = new ArrayList();
      Iterator var3 = values.iterator();

      while(var3.hasNext()) {
         Object value = var3.next();
         fields.add(Tools.field(value, (Field)this));
      }

      return this.notIn((Field[])fields.toArray(Tools.EMPTY_FIELD));
   }

   public final Condition notIn(Result<? extends Record1<T>> result) {
      return this.notIn((Collection)result.getValues(0, (Class)this.getType()));
   }

   public final Condition notIn(Select<? extends Record1<T>> query) {
      return this.compare(Comparator.NOT_IN, query);
   }

   public final Condition between(T minValue, T maxValue) {
      return this.between(Tools.field(minValue, (Field)this), Tools.field(maxValue, (Field)this));
   }

   public final Condition between(Field<T> minValue, Field<T> maxValue) {
      return this.between(DSL.nullSafe(minValue, this.getDataType())).and(DSL.nullSafe(maxValue, this.getDataType()));
   }

   public final Condition betweenSymmetric(T minValue, T maxValue) {
      return this.betweenSymmetric(Tools.field(minValue, (Field)this), Tools.field(maxValue, (Field)this));
   }

   public final Condition betweenSymmetric(Field<T> minValue, Field<T> maxValue) {
      return this.betweenSymmetric(DSL.nullSafe(minValue, this.getDataType())).and(DSL.nullSafe(maxValue, this.getDataType()));
   }

   public final Condition notBetween(T minValue, T maxValue) {
      return this.notBetween(Tools.field(minValue, (Field)this), Tools.field(maxValue, (Field)this));
   }

   public final Condition notBetween(Field<T> minValue, Field<T> maxValue) {
      return this.notBetween(DSL.nullSafe(minValue, this.getDataType())).and(DSL.nullSafe(maxValue, this.getDataType()));
   }

   public final Condition notBetweenSymmetric(T minValue, T maxValue) {
      return this.notBetweenSymmetric(Tools.field(minValue, (Field)this), Tools.field(maxValue, (Field)this));
   }

   public final Condition notBetweenSymmetric(Field<T> minValue, Field<T> maxValue) {
      return this.notBetweenSymmetric(DSL.nullSafe(minValue, this.getDataType())).and(DSL.nullSafe(maxValue, this.getDataType()));
   }

   public final BetweenAndStep<T> between(T minValue) {
      return this.between(Tools.field(minValue, (Field)this));
   }

   public final BetweenAndStep<T> between(Field<T> minValue) {
      return new BetweenCondition(this, DSL.nullSafe(minValue, this.getDataType()), false, false);
   }

   public final BetweenAndStep<T> betweenSymmetric(T minValue) {
      return this.betweenSymmetric(Tools.field(minValue, (Field)this));
   }

   public final BetweenAndStep<T> betweenSymmetric(Field<T> minValue) {
      return new BetweenCondition(this, DSL.nullSafe(minValue, this.getDataType()), false, true);
   }

   public final BetweenAndStep<T> notBetween(T minValue) {
      return this.notBetween(Tools.field(minValue, (Field)this));
   }

   public final BetweenAndStep<T> notBetween(Field<T> minValue) {
      return new BetweenCondition(this, DSL.nullSafe(minValue, this.getDataType()), true, false);
   }

   public final BetweenAndStep<T> notBetweenSymmetric(T minValue) {
      return this.notBetweenSymmetric(Tools.field(minValue, (Field)this));
   }

   public final BetweenAndStep<T> notBetweenSymmetric(Field<T> minValue) {
      return new BetweenCondition(this, DSL.nullSafe(minValue, this.getDataType()), true, true);
   }

   public final Condition eq(T value) {
      return this.equal(value);
   }

   public final Condition eq(Field<T> field) {
      return this.equal(field);
   }

   public final Condition eq(Select<? extends Record1<T>> query) {
      return this.equal(query);
   }

   public final Condition eq(QuantifiedSelect<? extends Record1<T>> query) {
      return this.equal(query);
   }

   public final Condition ne(T value) {
      return this.notEqual(value);
   }

   public final Condition ne(Field<T> field) {
      return this.notEqual(field);
   }

   public final Condition ne(Select<? extends Record1<T>> query) {
      return this.notEqual(query);
   }

   public final Condition ne(QuantifiedSelect<? extends Record1<T>> query) {
      return this.notEqual(query);
   }

   public final Condition lt(T value) {
      return this.lessThan(value);
   }

   public final Condition lt(Field<T> field) {
      return this.lessThan(field);
   }

   public final Condition lt(Select<? extends Record1<T>> query) {
      return this.lessThan(query);
   }

   public final Condition lt(QuantifiedSelect<? extends Record1<T>> query) {
      return this.lessThan(query);
   }

   public final Condition le(T value) {
      return this.lessOrEqual(value);
   }

   public final Condition le(Field<T> field) {
      return this.lessOrEqual(field);
   }

   public final Condition le(Select<? extends Record1<T>> query) {
      return this.lessOrEqual(query);
   }

   public final Condition le(QuantifiedSelect<? extends Record1<T>> query) {
      return this.lessOrEqual(query);
   }

   public final Condition gt(T value) {
      return this.greaterThan(value);
   }

   public final Condition gt(Field<T> field) {
      return this.greaterThan(field);
   }

   public final Condition gt(Select<? extends Record1<T>> query) {
      return this.greaterThan(query);
   }

   public final Condition gt(QuantifiedSelect<? extends Record1<T>> query) {
      return this.greaterThan(query);
   }

   public final Condition ge(T value) {
      return this.greaterOrEqual(value);
   }

   public final Condition ge(Field<T> field) {
      return this.greaterOrEqual(field);
   }

   public final Condition ge(Select<? extends Record1<T>> query) {
      return this.greaterOrEqual(query);
   }

   public final Condition ge(QuantifiedSelect<? extends Record1<T>> query) {
      return this.greaterOrEqual(query);
   }

   public final Condition equal(T value) {
      return this.equal(Tools.field(value, (Field)this));
   }

   public final Condition equal(Field<T> field) {
      return this.compare(Comparator.EQUALS, DSL.nullSafe(field, this.getDataType()));
   }

   public final Condition equalIgnoreCase(String value) {
      return this.equalIgnoreCase(Tools.field(value, (Class)String.class));
   }

   public final Condition equalIgnoreCase(Field<String> value) {
      return DSL.lower(this.cast(String.class)).equal(DSL.lower(value));
   }

   public final Condition equal(Select<? extends Record1<T>> query) {
      return this.compare(Comparator.EQUALS, query);
   }

   public final Condition equal(QuantifiedSelect<? extends Record1<T>> query) {
      return this.compare(Comparator.EQUALS, query);
   }

   public final Condition notEqual(T value) {
      return this.notEqual(Tools.field(value, (Field)this));
   }

   public final Condition notEqual(Field<T> field) {
      return this.compare(Comparator.NOT_EQUALS, DSL.nullSafe(field, this.getDataType()));
   }

   public final Condition notEqualIgnoreCase(String value) {
      return this.notEqualIgnoreCase(Tools.field(value, (Class)String.class));
   }

   public final Condition notEqualIgnoreCase(Field<String> value) {
      return DSL.lower(this.cast(String.class)).notEqual(DSL.lower(value));
   }

   public final Condition notEqual(Select<? extends Record1<T>> query) {
      return this.compare(Comparator.NOT_EQUALS, query);
   }

   public final Condition notEqual(QuantifiedSelect<? extends Record1<T>> query) {
      return this.compare(Comparator.NOT_EQUALS, query);
   }

   public final Condition lessThan(T value) {
      return this.lessThan(Tools.field(value, (Field)this));
   }

   public final Condition lessThan(Field<T> field) {
      return this.compare(Comparator.LESS, DSL.nullSafe(field, this.getDataType()));
   }

   public final Condition lessThan(Select<? extends Record1<T>> query) {
      return this.compare(Comparator.LESS, query);
   }

   public final Condition lessThan(QuantifiedSelect<? extends Record1<T>> query) {
      return this.compare(Comparator.LESS, query);
   }

   public final Condition lessOrEqual(T value) {
      return this.lessOrEqual(Tools.field(value, (Field)this));
   }

   public final Condition lessOrEqual(Field<T> field) {
      return this.compare(Comparator.LESS_OR_EQUAL, DSL.nullSafe(field, this.getDataType()));
   }

   public final Condition lessOrEqual(Select<? extends Record1<T>> query) {
      return this.compare(Comparator.LESS_OR_EQUAL, query);
   }

   public final Condition lessOrEqual(QuantifiedSelect<? extends Record1<T>> query) {
      return this.compare(Comparator.LESS_OR_EQUAL, query);
   }

   public final Condition greaterThan(T value) {
      return this.greaterThan(Tools.field(value, (Field)this));
   }

   public final Condition greaterThan(Field<T> field) {
      return this.compare(Comparator.GREATER, DSL.nullSafe(field, this.getDataType()));
   }

   public final Condition greaterThan(Select<? extends Record1<T>> query) {
      return this.compare(Comparator.GREATER, query);
   }

   public final Condition greaterThan(QuantifiedSelect<? extends Record1<T>> query) {
      return this.compare(Comparator.GREATER, query);
   }

   public final Condition greaterOrEqual(T value) {
      return this.greaterOrEqual(Tools.field(value, (Field)this));
   }

   public final Condition greaterOrEqual(Field<T> field) {
      return this.compare(Comparator.GREATER_OR_EQUAL, DSL.nullSafe(field, this.getDataType()));
   }

   public final Condition greaterOrEqual(Select<? extends Record1<T>> query) {
      return this.compare(Comparator.GREATER_OR_EQUAL, query);
   }

   public final Condition greaterOrEqual(QuantifiedSelect<? extends Record1<T>> query) {
      return this.compare(Comparator.GREATER_OR_EQUAL, query);
   }

   public final Condition compare(Comparator comparator, T value) {
      return this.compare(comparator, Tools.field(value, (Field)this));
   }

   public final Condition compare(Comparator comparator, Field<T> field) {
      switch(comparator) {
      case IS_DISTINCT_FROM:
      case IS_NOT_DISTINCT_FROM:
         return new IsDistinctFrom(this, DSL.nullSafe(field, this.getDataType()), comparator);
      default:
         return new CompareCondition(this, DSL.nullSafe(field, this.getDataType()), comparator);
      }
   }

   public final Condition compare(Comparator comparator, Select<? extends Record1<T>> query) {
      return this.compare(comparator, (Field)(new ScalarSubquery(query, this.getDataType())));
   }

   public final Condition compare(Comparator comparator, QuantifiedSelect<? extends Record1<T>> query) {
      return new QuantifiedComparisonCondition(query, this, comparator);
   }

   private final <Z extends Number> Field<Z> numeric() {
      return (Field)(this.getDataType().isNumeric() ? this : this.cast(BigDecimal.class));
   }

   private final Field<String> varchar() {
      return (Field)(this.getDataType().isString() ? this : this.cast(String.class));
   }

   private final <Z extends Date> Field<Z> date() {
      return (Field)(this.getDataType().isTemporal() ? this : this.cast(Timestamp.class));
   }

   /** @deprecated */
   @Deprecated
   public final Field<Integer> sign() {
      return DSL.sign(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<T> abs() {
      return DSL.abs(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<T> round() {
      return DSL.round(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<T> round(int decimals) {
      return DSL.round(this.numeric(), decimals);
   }

   /** @deprecated */
   @Deprecated
   public final Field<T> floor() {
      return DSL.floor(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<T> ceil() {
      return DSL.ceil(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> sqrt() {
      return DSL.sqrt(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> exp() {
      return DSL.exp(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> ln() {
      return DSL.ln(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> log(int base) {
      return DSL.log(this.numeric(), base);
   }

   public final Field<BigDecimal> pow(Number exponent) {
      return DSL.power(this.numeric(), exponent);
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> power(Number exponent) {
      return this.pow(exponent);
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> acos() {
      return DSL.acos(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> asin() {
      return DSL.asin(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> atan() {
      return DSL.atan(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> atan2(Number y) {
      return DSL.atan2(this.numeric(), y);
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> atan2(Field<? extends Number> y) {
      return DSL.atan2(this.numeric(), y);
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> cos() {
      return DSL.cos(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> sin() {
      return DSL.sin(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> tan() {
      return DSL.tan(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> cot() {
      return DSL.cot(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> sinh() {
      return DSL.sinh(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> cosh() {
      return DSL.cosh(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> tanh() {
      return DSL.tanh(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> coth() {
      return DSL.coth(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> deg() {
      return DSL.deg(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> rad() {
      return DSL.rad(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<Integer> count() {
      return DSL.count((Field)this);
   }

   /** @deprecated */
   @Deprecated
   public final Field<Integer> countDistinct() {
      return DSL.countDistinct((Field)this);
   }

   /** @deprecated */
   @Deprecated
   public final Field<T> max() {
      return DSL.max(this);
   }

   /** @deprecated */
   @Deprecated
   public final Field<T> min() {
      return DSL.min(this);
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> sum() {
      return DSL.sum(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> avg() {
      return DSL.avg(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> median() {
      return DSL.median(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> stddevPop() {
      return DSL.stddevPop(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> stddevSamp() {
      return DSL.stddevSamp(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> varPop() {
      return DSL.varPop(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final Field<BigDecimal> varSamp() {
      return DSL.varSamp(this.numeric());
   }

   /** @deprecated */
   @Deprecated
   public final WindowPartitionByStep<Integer> countOver() {
      return DSL.count((Field)this).over();
   }

   /** @deprecated */
   @Deprecated
   public final WindowPartitionByStep<T> maxOver() {
      return DSL.max(this).over();
   }

   /** @deprecated */
   @Deprecated
   public final WindowPartitionByStep<T> minOver() {
      return DSL.min(this).over();
   }

   /** @deprecated */
   @Deprecated
   public final WindowPartitionByStep<BigDecimal> sumOver() {
      return DSL.sum(this.numeric()).over();
   }

   /** @deprecated */
   @Deprecated
   public final WindowPartitionByStep<BigDecimal> avgOver() {
      return DSL.avg(this.numeric()).over();
   }

   /** @deprecated */
   @Deprecated
   public final WindowIgnoreNullsStep<T> firstValue() {
      return DSL.firstValue(this);
   }

   /** @deprecated */
   @Deprecated
   public final WindowIgnoreNullsStep<T> lastValue() {
      return DSL.lastValue(this);
   }

   /** @deprecated */
   @Deprecated
   public final WindowIgnoreNullsStep<T> lead() {
      return DSL.lead(this);
   }

   /** @deprecated */
   @Deprecated
   public final WindowIgnoreNullsStep<T> lead(int offset) {
      return DSL.lead(this, offset);
   }

   /** @deprecated */
   @Deprecated
   public final WindowIgnoreNullsStep<T> lead(int offset, T defaultValue) {
      return DSL.lead(this, offset, (Object)defaultValue);
   }

   /** @deprecated */
   @Deprecated
   public final WindowIgnoreNullsStep<T> lead(int offset, Field<T> defaultValue) {
      return DSL.lead(this, offset, (Field)defaultValue);
   }

   /** @deprecated */
   @Deprecated
   public final WindowIgnoreNullsStep<T> lag() {
      return DSL.lag(this);
   }

   /** @deprecated */
   @Deprecated
   public final WindowIgnoreNullsStep<T> lag(int offset) {
      return DSL.lag(this, offset);
   }

   /** @deprecated */
   @Deprecated
   public final WindowIgnoreNullsStep<T> lag(int offset, T defaultValue) {
      return DSL.lag(this, offset, (Object)defaultValue);
   }

   /** @deprecated */
   @Deprecated
   public final WindowIgnoreNullsStep<T> lag(int offset, Field<T> defaultValue) {
      return DSL.lag(this, offset, (Field)defaultValue);
   }

   /** @deprecated */
   @Deprecated
   public final WindowPartitionByStep<BigDecimal> stddevPopOver() {
      return DSL.stddevPop(this.numeric()).over();
   }

   /** @deprecated */
   @Deprecated
   public final WindowPartitionByStep<BigDecimal> stddevSampOver() {
      return DSL.stddevSamp(this.numeric()).over();
   }

   /** @deprecated */
   @Deprecated
   public final WindowPartitionByStep<BigDecimal> varPopOver() {
      return DSL.varPop(this.numeric()).over();
   }

   /** @deprecated */
   @Deprecated
   public final WindowPartitionByStep<BigDecimal> varSampOver() {
      return DSL.varSamp(this.numeric()).over();
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> upper() {
      return DSL.upper(this.varchar());
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> lower() {
      return DSL.lower(this.varchar());
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> trim() {
      return DSL.trim(this.varchar());
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> rtrim() {
      return DSL.rtrim(this.varchar());
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> ltrim() {
      return DSL.ltrim(this.varchar());
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> rpad(Field<? extends Number> length) {
      return DSL.rpad(this.varchar(), length);
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> rpad(int length) {
      return DSL.rpad(this.varchar(), length);
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> rpad(Field<? extends Number> length, Field<String> character) {
      return DSL.rpad(this.varchar(), length, character);
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> rpad(int length, char character) {
      return DSL.rpad(this.varchar(), length, character);
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> lpad(Field<? extends Number> length) {
      return DSL.lpad(this.varchar(), length);
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> lpad(int length) {
      return DSL.lpad(this.varchar(), length);
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> lpad(Field<? extends Number> length, Field<String> character) {
      return DSL.lpad(this.varchar(), length, character);
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> lpad(int length, char character) {
      return DSL.lpad(this.varchar(), length, character);
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> repeat(Number count) {
      return DSL.repeat(this.varchar(), count == null ? 0 : count.intValue());
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> repeat(Field<? extends Number> count) {
      return DSL.repeat(this.varchar(), count);
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> replace(Field<String> search) {
      return DSL.replace(this.varchar(), search);
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> replace(String search) {
      return DSL.replace(this.varchar(), search);
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> replace(Field<String> search, Field<String> replace) {
      return DSL.replace(this.varchar(), search, replace);
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> replace(String search, String replace) {
      return DSL.replace(this.varchar(), search, replace);
   }

   /** @deprecated */
   @Deprecated
   public final Field<Integer> position(String search) {
      return DSL.position(this.varchar(), search);
   }

   /** @deprecated */
   @Deprecated
   public final Field<Integer> position(Field<String> search) {
      return DSL.position(this.varchar(), search);
   }

   /** @deprecated */
   @Deprecated
   public final Field<Integer> ascii() {
      return DSL.ascii(this.varchar());
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> concat(Field<?>... fields) {
      return DSL.concat(Tools.combine((Field)this, (Field[])fields));
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> concat(String... values) {
      return DSL.concat(Tools.combine((Field)this, (Field[])((Field[])Tools.fields((Object[])values).toArray(Tools.EMPTY_FIELD))));
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> substring(int startingPosition) {
      return DSL.substring(this.varchar(), startingPosition);
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> substring(Field<? extends Number> startingPosition) {
      return DSL.substring(this.varchar(), startingPosition);
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> substring(int startingPosition, int length) {
      return DSL.substring(this.varchar(), startingPosition, length);
   }

   /** @deprecated */
   @Deprecated
   public final Field<String> substring(Field<? extends Number> startingPosition, Field<? extends Number> length) {
      return DSL.substring(this.varchar(), startingPosition, length);
   }

   /** @deprecated */
   @Deprecated
   public final Field<Integer> length() {
      return DSL.length(this.varchar());
   }

   /** @deprecated */
   @Deprecated
   public final Field<Integer> charLength() {
      return DSL.charLength(this.varchar());
   }

   /** @deprecated */
   @Deprecated
   public final Field<Integer> bitLength() {
      return DSL.bitLength(this.varchar());
   }

   /** @deprecated */
   @Deprecated
   public final Field<Integer> octetLength() {
      return DSL.octetLength(this.varchar());
   }

   /** @deprecated */
   @Deprecated
   public final Field<Integer> extract(DatePart datePart) {
      return DSL.extract(this.date(), datePart);
   }

   /** @deprecated */
   @Deprecated
   @SafeVarargs
   public final Field<T> greatest(T... others) {
      return DSL.greatest((Field)this, (Field[])((Field[])Tools.fields(others).toArray(Tools.EMPTY_FIELD)));
   }

   /** @deprecated */
   @Deprecated
   public final Field<T> greatest(Field<?>... others) {
      return DSL.greatest((Field)this, (Field[])others);
   }

   /** @deprecated */
   @Deprecated
   @SafeVarargs
   public final Field<T> least(T... others) {
      return DSL.least((Field)this, (Field[])((Field[])Tools.fields(others).toArray(Tools.EMPTY_FIELD)));
   }

   /** @deprecated */
   @Deprecated
   public final Field<T> least(Field<?>... others) {
      return DSL.least((Field)this, (Field[])others);
   }

   /** @deprecated */
   @Deprecated
   public final Field<T> nvl(T defaultValue) {
      return DSL.nvl((Field)this, (Object)defaultValue);
   }

   /** @deprecated */
   @Deprecated
   public final Field<T> nvl(Field<T> defaultValue) {
      return DSL.nvl((Field)this, (Field)defaultValue);
   }

   /** @deprecated */
   @Deprecated
   public final <Z> Field<Z> nvl2(Z valueIfNotNull, Z valueIfNull) {
      return DSL.nvl2(this, (Object)valueIfNotNull, (Object)valueIfNull);
   }

   /** @deprecated */
   @Deprecated
   public final <Z> Field<Z> nvl2(Field<Z> valueIfNotNull, Field<Z> valueIfNull) {
      return DSL.nvl2(this, (Field)valueIfNotNull, (Field)valueIfNull);
   }

   /** @deprecated */
   @Deprecated
   public final Field<T> nullif(T other) {
      return DSL.nullif((Field)this, (Object)other);
   }

   /** @deprecated */
   @Deprecated
   public final Field<T> nullif(Field<T> other) {
      return DSL.nullif((Field)this, (Field)other);
   }

   /** @deprecated */
   @Deprecated
   public final <Z> Field<Z> decode(T search, Z result) {
      return DSL.decode((Object)this, (Object)search, (Object)result);
   }

   /** @deprecated */
   @Deprecated
   public final <Z> Field<Z> decode(T search, Z result, Object... more) {
      return DSL.decode((Object)this, (Object)search, (Object)result, (Object[])more);
   }

   /** @deprecated */
   @Deprecated
   public final <Z> Field<Z> decode(Field<T> search, Field<Z> result) {
      return DSL.decode((Field)this, (Field)search, (Field)result);
   }

   /** @deprecated */
   @Deprecated
   public final <Z> Field<Z> decode(Field<T> search, Field<Z> result, Field<?>... more) {
      return DSL.decode((Field)this, (Field)search, (Field)result, (Field[])more);
   }

   /** @deprecated */
   @Deprecated
   @SafeVarargs
   public final Field<T> coalesce(T option, T... options) {
      return DSL.coalesce((Field)this, (Field[])Tools.combine(Tools.field(option), (Field[])Tools.fields(options).toArray(Tools.EMPTY_FIELD)));
   }

   /** @deprecated */
   @Deprecated
   public final Field<T> coalesce(Field<T> option, Field<?>... options) {
      return DSL.coalesce((Field)this, (Field[])Tools.combine(option, options));
   }

   public boolean equals(Object that) {
      if (this == that) {
         return true;
      } else if (that instanceof AbstractField) {
         return StringUtils.equals(this.name, ((AbstractField)that).name) ? super.equals(that) : false;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   static {
      CLAUSES = new Clause[]{Clause.FIELD};
   }
}
