package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import org.jooq.Binding;
import org.jooq.Catalog;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.DivideByOnStep;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.JoinType;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.RecordType;
import org.jooq.Row;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableOnStep;
import org.jooq.TableOptionalOnStep;
import org.jooq.TablePartitionByStep;
import org.jooq.UniqueKey;
import org.jooq.tools.StringUtils;

abstract class AbstractTable<R extends Record> extends AbstractQueryPart implements Table<R> {
   private static final long serialVersionUID = 3155496238969274871L;
   private static final Clause[] CLAUSES;
   private final Schema tableschema;
   private final String tablename;
   private final String tablecomment;
   private transient DataType<R> type;

   AbstractTable(String name) {
      this(name, (Schema)null, (String)null);
   }

   AbstractTable(String name, Schema schema) {
      this(name, schema, (String)null);
   }

   AbstractTable(String name, Schema schema, String comment) {
      this.tableschema = schema;
      this.tablename = name;
      this.tablecomment = comment;
   }

   public Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   public final R from(Record record) {
      return record.into((Table)this);
   }

   abstract Fields<R> fields0();

   public final DataType<R> getDataType() {
      if (this.type == null) {
         this.type = new TableDataType(this);
      }

      return this.type;
   }

   public final RecordType<R> recordType() {
      return this.fields0();
   }

   public final R newRecord() {
      return DSL.using((Configuration)(new DefaultConfiguration())).newRecord((Table)this);
   }

   public final Row fieldsRow() {
      return new RowImpl(this.fields0());
   }

   public final Stream<Field<?>> fieldStream() {
      return Stream.of(this.fields());
   }

   public final <T> Field<T> field(Field<T> field) {
      return this.fieldsRow().field(field);
   }

   public final Field<?> field(String string) {
      return this.fieldsRow().field(string);
   }

   public final <T> Field<T> field(String name, Class<T> type) {
      return this.fieldsRow().field(name, type);
   }

   public final <T> Field<T> field(String name, DataType<T> dataType) {
      return this.fieldsRow().field(name, dataType);
   }

   public final Field<?> field(Name name) {
      return this.fieldsRow().field(name);
   }

   public final <T> Field<T> field(Name name, Class<T> type) {
      return this.fieldsRow().field(name, type);
   }

   public final <T> Field<T> field(Name name, DataType<T> dataType) {
      return this.fieldsRow().field(name, dataType);
   }

   public final Field<?> field(int index) {
      return this.fieldsRow().field(index);
   }

   public final <T> Field<T> field(int index, Class<T> type) {
      return this.fieldsRow().field(index, type);
   }

   public final <T> Field<T> field(int index, DataType<T> dataType) {
      return this.fieldsRow().field(index, dataType);
   }

   public final Field<?>[] fields() {
      return this.fieldsRow().fields();
   }

   public final Field<?>[] fields(Field<?>... fields) {
      return this.fieldsRow().fields(fields);
   }

   public final Field<?>[] fields(String... fieldNames) {
      return this.fieldsRow().fields(fieldNames);
   }

   public final Field<?>[] fields(Name... fieldNames) {
      return this.fieldsRow().fields(fieldNames);
   }

   public final Field<?>[] fields(int... fieldIndexes) {
      return this.fieldsRow().fields(fieldIndexes);
   }

   public final Table<R> asTable() {
      return this;
   }

   public final Table<R> asTable(String alias) {
      return this.as(alias);
   }

   public final Table<R> asTable(String alias, String... fieldAliases) {
      return this.as((String)alias, (String[])fieldAliases);
   }

   public final Table<R> asTable(String alias, java.util.function.Function<? super Field<?>, ? extends String> aliasFunction) {
      return this.as(alias, aliasFunction);
   }

   public final Table<R> asTable(String alias, BiFunction<? super Field<?>, ? super Integer, ? extends String> aliasFunction) {
      return this.as(alias, aliasFunction);
   }

   public final Table<R> as(String alias, java.util.function.Function<? super Field<?>, ? extends String> aliasFunction) {
      return this.as((String)alias, (String[])((String[])Stream.of(this.fields()).map(aliasFunction).toArray((x$0) -> {
         return new String[x$0];
      })));
   }

   public final Table<R> as(String alias, BiFunction<? super Field<?>, ? super Integer, ? extends String> aliasFunction) {
      Field<?>[] fields = this.fields();
      String[] names = new String[fields.length];

      for(int i = 0; i < fields.length; ++i) {
         names[i] = (String)aliasFunction.apply(fields[i], i);
      }

      return this.as((String)alias, (String[])names);
   }

   public final Catalog getCatalog() {
      return this.getSchema() == null ? null : this.getSchema().getCatalog();
   }

   public Schema getSchema() {
      return this.tableschema;
   }

   public final String getName() {
      return this.tablename;
   }

   public final String getComment() {
      return this.tablecomment;
   }

   public Identity<R, ?> getIdentity() {
      return null;
   }

   public UniqueKey<R> getPrimaryKey() {
      return null;
   }

   public TableField<R, ?> getRecordVersion() {
      return null;
   }

   public TableField<R, ?> getRecordTimestamp() {
      return null;
   }

   public List<UniqueKey<R>> getKeys() {
      return Collections.emptyList();
   }

   public final <O extends Record> List<ForeignKey<O, R>> getReferencesFrom(Table<O> other) {
      return other.getReferencesTo(this);
   }

   public List<ForeignKey<R, ?>> getReferences() {
      return Collections.emptyList();
   }

   public final <O extends Record> List<ForeignKey<R, O>> getReferencesTo(Table<O> other) {
      List<ForeignKey<R, O>> result = new ArrayList();
      Iterator var3 = this.getReferences().iterator();

      while(var3.hasNext()) {
         ForeignKey<R, ?> reference = (ForeignKey)var3.next();
         if (other.equals(reference.getKey().getTable())) {
            result.add(reference);
         } else {
            Table aliased;
            if (other instanceof TableImpl) {
               aliased = ((TableImpl)other).getAliasedTable();
               if (aliased != null && aliased.equals(reference.getKey().getTable())) {
                  result.add(reference);
               }
            } else if (other instanceof TableAlias) {
               aliased = ((TableAlias)other).getAliasedTable();
               if (aliased != null && aliased.equals(reference.getKey().getTable())) {
                  result.add(reference);
               }
            }
         }
      }

      return Collections.unmodifiableList(result);
   }

   protected static final <R extends Record, T> TableField<R, T> createField(String name, DataType<T> type, Table<R> table) {
      return createField(name, type, table, (String)null, (Converter)null, (Binding)null);
   }

   protected static final <R extends Record, T> TableField<R, T> createField(String name, DataType<T> type, Table<R> table, String comment) {
      return createField(name, type, table, comment, (Converter)null, (Binding)null);
   }

   protected static final <R extends Record, T, U> TableField<R, U> createField(String name, DataType<T> type, Table<R> table, String comment, Converter<T, U> converter) {
      return createField(name, type, table, comment, converter, (Binding)null);
   }

   protected static final <R extends Record, T, U> TableField<R, U> createField(String name, DataType<T> type, Table<R> table, String comment, Binding<T, U> binding) {
      return createField(name, type, table, comment, (Converter)null, binding);
   }

   protected static final <R extends Record, T, X, U> TableField<R, U> createField(String name, DataType<T> type, Table<R> table, String comment, Converter<X, U> converter, Binding<T, X> binding) {
      Binding<T, U> actualBinding = DefaultBinding.newBinding(converter, type, binding);
      DataType<U> actualType = converter == null && binding == null ? type : type.asConvertedDataType(actualBinding);
      TableFieldImpl<R, U> tableField = new TableFieldImpl(name, actualType, table, comment, actualBinding);
      if (table instanceof TableImpl) {
         ((TableImpl)table).fields0().add(tableField);
      }

      return tableField;
   }

   protected final <T> TableField<R, T> createField(String name, DataType<T> type) {
      return createField(name, type, this, (String)null, (Converter)null, (Binding)null);
   }

   protected final <T> TableField<R, T> createField(String name, DataType<T> type, String comment) {
      return createField(name, type, this, comment, (Converter)null, (Binding)null);
   }

   protected final <T, U> TableField<R, U> createField(String name, DataType<T> type, String comment, Converter<T, U> converter) {
      return createField(name, type, this, comment, converter, (Binding)null);
   }

   protected final <T, U> TableField<R, U> createField(String name, DataType<T> type, String comment, Binding<T, U> binding) {
      return createField(name, type, this, comment, (Converter)null, binding);
   }

   protected final <T, X, U> TableField<R, U> createField(String name, DataType<T> type, String comment, Converter<X, U> converter, Binding<T, X> binding) {
      return createField(name, type, this, comment, converter, binding);
   }

   public final Condition eq(Table<R> that) {
      return this.equal(that);
   }

   public final Condition equal(Table<R> that) {
      return new TableComparison(this, that, Comparator.EQUALS);
   }

   public final Condition ne(Table<R> that) {
      return this.notEqual(that);
   }

   public final Condition notEqual(Table<R> that) {
      return new TableComparison(this, that, Comparator.NOT_EQUALS);
   }

   public final Table<R> useIndex(String... indexes) {
      return new HintedTable(this, "use index", indexes);
   }

   public final Table<R> useIndexForJoin(String... indexes) {
      return new HintedTable(this, "use index for join", indexes);
   }

   public final Table<R> useIndexForOrderBy(String... indexes) {
      return new HintedTable(this, "use index for order by", indexes);
   }

   public final Table<R> useIndexForGroupBy(String... indexes) {
      return new HintedTable(this, "use index for group by", indexes);
   }

   public final Table<R> ignoreIndex(String... indexes) {
      return new HintedTable(this, "ignore index", indexes);
   }

   public final Table<R> ignoreIndexForJoin(String... indexes) {
      return new HintedTable(this, "ignore index for join", indexes);
   }

   public final Table<R> ignoreIndexForOrderBy(String... indexes) {
      return new HintedTable(this, "ignore index for order by", indexes);
   }

   public final Table<R> ignoreIndexForGroupBy(String... indexes) {
      return new HintedTable(this, "ignore index for group by", indexes);
   }

   public final Table<R> forceIndex(String... indexes) {
      return new HintedTable(this, "force index", indexes);
   }

   public final Table<R> forceIndexForJoin(String... indexes) {
      return new HintedTable(this, "force index for join", indexes);
   }

   public final Table<R> forceIndexForOrderBy(String... indexes) {
      return new HintedTable(this, "force index for order by", indexes);
   }

   public final Table<R> forceIndexForGroupBy(String... indexes) {
      return new HintedTable(this, "force index for group by", indexes);
   }

   public final Table<R> as(Table<?> otherTable) {
      return this.as(otherTable.getName());
   }

   public final Table<R> as(Table<?> otherTable, Field<?>... otherFields) {
      return this.as((String)otherTable.getName(), (String[])Tools.fieldNames(otherFields));
   }

   public final Table<R> as(Table<?> otherTable, java.util.function.Function<? super Field<?>, ? extends Field<?>> aliasFunction) {
      return this.as(otherTable.getName(), (f) -> {
         return ((Field)aliasFunction.apply(f)).getName();
      });
   }

   public final Table<R> as(Table<?> otherTable, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> aliasFunction) {
      return this.as(otherTable.getName(), (f, i) -> {
         return ((Field)aliasFunction.apply(f, i)).getName();
      });
   }

   public final DivideByOnStep divideBy(Table<?> divisor) {
      return new DivideBy(this, divisor);
   }

   public final TableOnStep<R> leftSemiJoin(TableLike<?> table) {
      return this.join(table, JoinType.LEFT_SEMI_JOIN);
   }

   public final TableOnStep<R> leftAntiJoin(TableLike<?> table) {
      return this.join(table, JoinType.LEFT_ANTI_JOIN);
   }

   public final TableOptionalOnStep<Record> join(TableLike<?> table, JoinType type) {
      return new JoinTable(this, table, type);
   }

   public final TableOnStep<Record> join(TableLike<?> table) {
      return this.innerJoin(table);
   }

   public final TableOnStep<Record> join(SQL sql) {
      return this.innerJoin(sql);
   }

   public final TableOnStep<Record> join(String sql) {
      return this.innerJoin(sql);
   }

   public final TableOnStep<Record> join(String sql, Object... bindings) {
      return this.innerJoin(sql, bindings);
   }

   public final TableOnStep<Record> join(String sql, QueryPart... parts) {
      return this.innerJoin(sql, parts);
   }

   public final TableOnStep<Record> join(Name name) {
      return this.innerJoin((TableLike)DSL.table(name));
   }

   public final TableOnStep<Record> innerJoin(TableLike<?> table) {
      return this.join(table, JoinType.JOIN);
   }

   public final TableOnStep<Record> innerJoin(SQL sql) {
      return this.innerJoin((TableLike)DSL.table(sql));
   }

   public final TableOnStep<Record> innerJoin(String sql) {
      return this.innerJoin((TableLike)DSL.table(sql));
   }

   public final TableOnStep<Record> innerJoin(String sql, Object... bindings) {
      return this.innerJoin((TableLike)DSL.table(sql, bindings));
   }

   public final TableOnStep<Record> innerJoin(String sql, QueryPart... parts) {
      return this.innerJoin((TableLike)DSL.table(sql, parts));
   }

   public final TableOnStep<Record> innerJoin(Name name) {
      return this.innerJoin((TableLike)DSL.table(name));
   }

   public final TablePartitionByStep<Record> leftJoin(TableLike<?> table) {
      return this.leftOuterJoin(table);
   }

   public final TablePartitionByStep<Record> leftJoin(SQL sql) {
      return this.leftOuterJoin(sql);
   }

   public final TablePartitionByStep<Record> leftJoin(String sql) {
      return this.leftOuterJoin(sql);
   }

   public final TablePartitionByStep<Record> leftJoin(String sql, Object... bindings) {
      return this.leftOuterJoin(sql, bindings);
   }

   public final TablePartitionByStep<Record> leftJoin(String sql, QueryPart... parts) {
      return this.leftOuterJoin(sql, parts);
   }

   public final TablePartitionByStep<Record> leftJoin(Name name) {
      return this.leftOuterJoin((TableLike)DSL.table(name));
   }

   public final TablePartitionByStep<Record> leftOuterJoin(TableLike<?> table) {
      return this.join(table, JoinType.LEFT_OUTER_JOIN);
   }

   public final TablePartitionByStep<Record> leftOuterJoin(SQL sql) {
      return this.leftOuterJoin((TableLike)DSL.table(sql));
   }

   public final TablePartitionByStep<Record> leftOuterJoin(String sql) {
      return this.leftOuterJoin((TableLike)DSL.table(sql));
   }

   public final TablePartitionByStep<Record> leftOuterJoin(String sql, Object... bindings) {
      return this.leftOuterJoin((TableLike)DSL.table(sql, bindings));
   }

   public final TablePartitionByStep<Record> leftOuterJoin(String sql, QueryPart... parts) {
      return this.leftOuterJoin((TableLike)DSL.table(sql, parts));
   }

   public final TablePartitionByStep<Record> leftOuterJoin(Name name) {
      return this.leftOuterJoin((TableLike)DSL.table(name));
   }

   public final TablePartitionByStep<Record> rightJoin(TableLike<?> table) {
      return this.rightOuterJoin(table);
   }

   public final TablePartitionByStep<Record> rightJoin(SQL sql) {
      return this.rightOuterJoin(sql);
   }

   public final TablePartitionByStep<Record> rightJoin(String sql) {
      return this.rightOuterJoin(sql);
   }

   public final TablePartitionByStep<Record> rightJoin(String sql, Object... bindings) {
      return this.rightOuterJoin(sql, bindings);
   }

   public final TablePartitionByStep<Record> rightJoin(String sql, QueryPart... parts) {
      return this.rightOuterJoin(sql, parts);
   }

   public final TablePartitionByStep<Record> rightJoin(Name name) {
      return this.rightOuterJoin((TableLike)DSL.table(name));
   }

   public final TablePartitionByStep<Record> rightOuterJoin(TableLike<?> table) {
      return this.join(table, JoinType.RIGHT_OUTER_JOIN);
   }

   public final TablePartitionByStep<Record> rightOuterJoin(SQL sql) {
      return this.rightOuterJoin((TableLike)DSL.table(sql));
   }

   public final TablePartitionByStep<Record> rightOuterJoin(String sql) {
      return this.rightOuterJoin((TableLike)DSL.table(sql));
   }

   public final TablePartitionByStep<Record> rightOuterJoin(String sql, Object... bindings) {
      return this.rightOuterJoin((TableLike)DSL.table(sql, bindings));
   }

   public final TablePartitionByStep<Record> rightOuterJoin(String sql, QueryPart... parts) {
      return this.rightOuterJoin((TableLike)DSL.table(sql, parts));
   }

   public final TablePartitionByStep<Record> rightOuterJoin(Name name) {
      return this.rightOuterJoin((TableLike)DSL.table(name));
   }

   public final TableOnStep<Record> fullOuterJoin(TableLike<?> table) {
      return this.join(table, JoinType.FULL_OUTER_JOIN);
   }

   public final TableOnStep<Record> fullOuterJoin(SQL sql) {
      return this.fullOuterJoin((TableLike)DSL.table(sql));
   }

   public final TableOnStep<Record> fullOuterJoin(String sql) {
      return this.fullOuterJoin((TableLike)DSL.table(sql));
   }

   public final TableOnStep<Record> fullOuterJoin(String sql, Object... bindings) {
      return this.fullOuterJoin((TableLike)DSL.table(sql, bindings));
   }

   public final TableOnStep<Record> fullOuterJoin(String sql, QueryPart... parts) {
      return this.fullOuterJoin((TableLike)DSL.table(sql, parts));
   }

   public final TableOnStep<Record> fullOuterJoin(Name name) {
      return this.fullOuterJoin((TableLike)DSL.table(name));
   }

   public final Table<Record> crossJoin(TableLike<?> table) {
      return this.join(table, JoinType.CROSS_JOIN);
   }

   public final Table<Record> crossJoin(SQL sql) {
      return this.crossJoin((TableLike)DSL.table(sql));
   }

   public final Table<Record> crossJoin(String sql) {
      return this.crossJoin((TableLike)DSL.table(sql));
   }

   public final Table<Record> crossJoin(String sql, Object... bindings) {
      return this.crossJoin((TableLike)DSL.table(sql, bindings));
   }

   public final Table<Record> crossJoin(String sql, QueryPart... parts) {
      return this.crossJoin((TableLike)DSL.table(sql, parts));
   }

   public final Table<Record> crossJoin(Name name) {
      return this.crossJoin((TableLike)DSL.table(name));
   }

   public final Table<Record> naturalJoin(TableLike<?> table) {
      return this.join(table, JoinType.NATURAL_JOIN);
   }

   public final Table<Record> naturalJoin(SQL sql) {
      return this.naturalJoin((TableLike)DSL.table(sql));
   }

   public final Table<Record> naturalJoin(String sql) {
      return this.naturalJoin((TableLike)DSL.table(sql));
   }

   public final Table<Record> naturalJoin(String sql, Object... bindings) {
      return this.naturalJoin((TableLike)DSL.table(sql, bindings));
   }

   public final Table<Record> naturalJoin(String sql, QueryPart... parts) {
      return this.naturalJoin((TableLike)DSL.table(sql, parts));
   }

   public final Table<Record> naturalJoin(Name name) {
      return this.naturalJoin((TableLike)DSL.table(name));
   }

   public final Table<Record> naturalLeftOuterJoin(TableLike<?> table) {
      return this.join(table, JoinType.NATURAL_LEFT_OUTER_JOIN);
   }

   public final Table<Record> naturalLeftOuterJoin(SQL sql) {
      return this.naturalLeftOuterJoin((TableLike)DSL.table(sql));
   }

   public final Table<Record> naturalLeftOuterJoin(String sql) {
      return this.naturalLeftOuterJoin((TableLike)DSL.table(sql));
   }

   public final Table<Record> naturalLeftOuterJoin(String sql, Object... bindings) {
      return this.naturalLeftOuterJoin((TableLike)DSL.table(sql, bindings));
   }

   public final Table<Record> naturalLeftOuterJoin(String sql, QueryPart... parts) {
      return this.naturalLeftOuterJoin((TableLike)DSL.table(sql, parts));
   }

   public final Table<Record> naturalLeftOuterJoin(Name name) {
      return this.naturalLeftOuterJoin((TableLike)DSL.table(name));
   }

   public final Table<Record> naturalRightOuterJoin(TableLike<?> table) {
      return this.join(table, JoinType.NATURAL_RIGHT_OUTER_JOIN);
   }

   public final Table<Record> naturalRightOuterJoin(SQL sql) {
      return this.naturalRightOuterJoin((TableLike)DSL.table(sql));
   }

   public final Table<Record> naturalRightOuterJoin(String sql) {
      return this.naturalRightOuterJoin((TableLike)DSL.table(sql));
   }

   public final Table<Record> naturalRightOuterJoin(String sql, Object... bindings) {
      return this.naturalRightOuterJoin((TableLike)DSL.table(sql, bindings));
   }

   public final Table<Record> naturalRightOuterJoin(String sql, QueryPart... parts) {
      return this.naturalRightOuterJoin((TableLike)DSL.table(sql, parts));
   }

   public final Table<Record> naturalRightOuterJoin(Name name) {
      return this.naturalRightOuterJoin((TableLike)DSL.table(name));
   }

   public final Table<Record> crossApply(TableLike<?> table) {
      return this.join(table, JoinType.CROSS_APPLY);
   }

   public final Table<Record> crossApply(SQL sql) {
      return this.crossApply((TableLike)DSL.table(sql));
   }

   public final Table<Record> crossApply(String sql) {
      return this.crossApply((TableLike)DSL.table(sql));
   }

   public final Table<Record> crossApply(String sql, Object... bindings) {
      return this.crossApply((TableLike)DSL.table(sql, bindings));
   }

   public final Table<Record> crossApply(String sql, QueryPart... parts) {
      return this.crossApply((TableLike)DSL.table(sql, parts));
   }

   public final Table<Record> crossApply(Name name) {
      return this.crossApply((TableLike)DSL.table(name));
   }

   public final Table<Record> outerApply(TableLike<?> table) {
      return this.join(table, JoinType.OUTER_APPLY);
   }

   public final Table<Record> outerApply(SQL sql) {
      return this.outerApply((TableLike)DSL.table(sql));
   }

   public final Table<Record> outerApply(String sql) {
      return this.outerApply((TableLike)DSL.table(sql));
   }

   public final Table<Record> outerApply(String sql, Object... bindings) {
      return this.outerApply((TableLike)DSL.table(sql, bindings));
   }

   public final Table<Record> outerApply(String sql, QueryPart... parts) {
      return this.outerApply((TableLike)DSL.table(sql, parts));
   }

   public final Table<Record> outerApply(Name name) {
      return this.outerApply((TableLike)DSL.table(name));
   }

   public final TableOptionalOnStep<Record> straightJoin(TableLike<?> table) {
      return this.join(table, JoinType.STRAIGHT_JOIN);
   }

   public final TableOptionalOnStep<Record> straightJoin(SQL sql) {
      return this.straightJoin((TableLike)DSL.table(sql));
   }

   public final TableOptionalOnStep<Record> straightJoin(String sql) {
      return this.straightJoin((TableLike)DSL.table(sql));
   }

   public final TableOptionalOnStep<Record> straightJoin(String sql, Object... bindings) {
      return this.straightJoin((TableLike)DSL.table(sql, bindings));
   }

   public final TableOptionalOnStep<Record> straightJoin(String sql, QueryPart... parts) {
      return this.straightJoin((TableLike)DSL.table(sql, parts));
   }

   public final TableOptionalOnStep<Record> straightJoin(Name name) {
      return this.straightJoin((TableLike)DSL.table(name));
   }

   public boolean equals(Object that) {
      if (this == that) {
         return true;
      } else if (that instanceof AbstractTable) {
         return StringUtils.equals(this.tablename, ((AbstractTable)that).tablename) ? super.equals(that) : false;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.tablename.hashCode();
   }

   static {
      CLAUSES = new Clause[]{Clause.TABLE};
   }
}
