package org.jooq.impl;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.GroupField;
import org.jooq.JoinType;
import org.jooq.Name;
import org.jooq.Operator;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.SelectQuery;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TablePartitionByStep;
import org.jooq.WindowDefinition;
import org.jooq.exception.DataAccessException;
import org.jooq.tools.StringUtils;

final class SelectQueryImpl<R extends Record> extends AbstractResultQuery<R> implements SelectQuery<R> {
   private static final long serialVersionUID = 1646393178384872967L;
   private static final Clause[] CLAUSES;
   private final WithImpl with;
   private final SelectFieldList select;
   private Table<?> into;
   private String hint;
   private String option;
   private boolean distinct;
   private final QueryPartList<SelectField<?>> distinctOn;
   private boolean forUpdate;
   private final QueryPartList<Field<?>> forUpdateOf;
   private final TableList forUpdateOfTables;
   private SelectQueryImpl.ForUpdateMode forUpdateMode;
   private int forUpdateWait;
   private boolean forShare;
   private final TableList from;
   private final ConditionProviderImpl condition;
   private final ConditionProviderImpl connectBy;
   private boolean connectByNoCycle;
   private final ConditionProviderImpl connectByStartWith;
   private boolean grouping;
   private final QueryPartList<GroupField> groupBy;
   private final ConditionProviderImpl having;
   private final WindowList window;
   private final SortFieldList orderBy;
   private boolean orderBySiblings;
   private final QueryPartList<Field<?>> seek;
   private boolean seekBefore;
   private final Limit limit;
   private final List<CombineOperator> unionOp;
   private final List<QueryPartList<Select<?>>> union;
   private final SortFieldList unionOrderBy;
   private boolean unionOrderBySiblings;
   private final QueryPartList<Field<?>> unionSeek;
   private boolean unionSeekBefore;
   private final Limit unionLimit;

   SelectQueryImpl(Configuration configuration, WithImpl with) {
      this(configuration, with, (TableLike)null);
   }

   SelectQueryImpl(Configuration configuration, WithImpl with, boolean distinct) {
      this(configuration, with, (TableLike)null, distinct);
   }

   SelectQueryImpl(Configuration configuration, WithImpl with, TableLike<? extends R> from) {
      this(configuration, with, from, false);
   }

   SelectQueryImpl(Configuration configuration, WithImpl with, TableLike<? extends R> from, boolean distinct) {
      super(configuration);
      this.with = with;
      this.distinct = distinct;
      this.distinctOn = new QueryPartList();
      this.select = new SelectFieldList();
      this.from = new TableList();
      this.condition = new ConditionProviderImpl();
      this.connectBy = new ConditionProviderImpl();
      this.connectByStartWith = new ConditionProviderImpl();
      this.groupBy = new QueryPartList();
      this.having = new ConditionProviderImpl();
      this.window = new WindowList();
      this.orderBy = new SortFieldList();
      this.seek = new QueryPartList();
      this.limit = new Limit();
      this.unionOp = new ArrayList();
      this.union = new ArrayList();
      this.unionOrderBy = new SortFieldList();
      this.unionSeek = new QueryPartList();
      this.unionLimit = new Limit();
      if (from != null) {
         this.from.add(from.asTable());
      }

      this.forUpdateOf = new QueryPartList();
      this.forUpdateOfTables = new TableList();
   }

   public final int fetchCount() throws DataAccessException {
      return DSL.using(this.configuration()).fetchCount((Select)this);
   }

   public final <T> Field<T> asField() {
      List<Field<?>> s = this.getSelect();
      if (s.size() != 1) {
         throw new IllegalStateException("Can only use single-column ResultProviderQuery as a field");
      } else {
         return new ScalarSubquery(this, ((Field)s.get(0)).getDataType());
      }
   }

   public final <T> Field<T> asField(String alias) {
      return this.asField().as(alias);
   }

   public <T> Field<T> asField(java.util.function.Function<? super Field<T>, ? extends String> aliasFunction) {
      return this.asField().as(aliasFunction);
   }

   public final Row fieldsRow() {
      return this.asTable().fieldsRow();
   }

   public final Stream<Field<?>> fieldStream() {
      return Stream.of(this.fields());
   }

   public final <T> Field<T> field(Field<T> field) {
      return this.asTable().field(field);
   }

   public final Field<?> field(String string) {
      return this.asTable().field(string);
   }

   public final <T> Field<T> field(String name, Class<T> type) {
      return this.asTable().field(name, type);
   }

   public final <T> Field<T> field(String name, DataType<T> dataType) {
      return this.asTable().field(name, dataType);
   }

   public final Field<?> field(Name string) {
      return this.asTable().field(string);
   }

   public final <T> Field<T> field(Name name, Class<T> type) {
      return this.asTable().field(name, type);
   }

   public final <T> Field<T> field(Name name, DataType<T> dataType) {
      return this.asTable().field(name, dataType);
   }

   public final Field<?> field(int index) {
      return this.asTable().field(index);
   }

   public final <T> Field<T> field(int index, Class<T> type) {
      return this.asTable().field(index, type);
   }

   public final <T> Field<T> field(int index, DataType<T> dataType) {
      return this.asTable().field(index, dataType);
   }

   public final Field<?>[] fields() {
      return this.asTable().fields();
   }

   public final Field<?>[] fields(Field<?>... fields) {
      return this.asTable().fields(fields);
   }

   public final Field<?>[] fields(String... fieldNames) {
      return this.asTable().fields(fieldNames);
   }

   public final Field<?>[] fields(Name... fieldNames) {
      return this.asTable().fields(fieldNames);
   }

   public final Field<?>[] fields(int... fieldIndexes) {
      return this.asTable().fields(fieldIndexes);
   }

   public final Table<R> asTable() {
      return (new DerivedTable(this)).as("alias_" + Tools.hash(this));
   }

   public final Table<R> asTable(String alias) {
      return (new DerivedTable(this)).as(alias);
   }

   public final Table<R> asTable(String alias, String... fieldAliases) {
      return (new DerivedTable(this)).as(alias, fieldAliases);
   }

   public final Table<R> asTable(String alias, java.util.function.Function<? super Field<?>, ? extends String> aliasFunction) {
      return (new DerivedTable(this)).as(alias, aliasFunction);
   }

   public final Table<R> asTable(String alias, BiFunction<? super Field<?>, ? super Integer, ? extends String> aliasFunction) {
      return (new DerivedTable(this)).as(alias, aliasFunction);
   }

   protected final Field<?>[] getFields(ResultSetMetaData meta) {
      List<Field<?>> fields = this.getSelect();
      if (fields.isEmpty()) {
         Configuration configuration = this.configuration();
         return (new MetaDataFieldProvider(configuration, meta)).getFields();
      } else {
         return Tools.fieldArray(fields);
      }
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   public final void accept(Context<?> context) {
      SQLDialect dialect = context.dialect();
      SQLDialect family = context.family();
      Object renderTrailingLimit = context.data(Tools.DataKey.DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE);
      Object localDataMap = context.data(Tools.DataKey.DATA_LOCALLY_SCOPED_DATA_MAP);

      try {
         if (renderTrailingLimit != null) {
            context.data().remove(Tools.DataKey.DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE);
         }

         context.data(Tools.DataKey.DATA_LOCALLY_SCOPED_DATA_MAP, new HashMap());
         if (this.into != null && context.data(Tools.DataKey.DATA_OMIT_INTO_CLAUSE) == null && Arrays.asList(SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE).contains(family)) {
            context.data(Tools.DataKey.DATA_OMIT_INTO_CLAUSE, true);
            context.visit(DSL.createTable(this.into).as(this));
            context.data().remove(Tools.DataKey.DATA_OMIT_INTO_CLAUSE);
            return;
         }

         if (this.with != null) {
            context.visit(this.with).formatSeparator();
         }

         this.pushWindow(context);
         Boolean wrapDerivedTables = (Boolean)context.data(Tools.DataKey.DATA_WRAP_DERIVED_TABLES_IN_PARENTHESES);
         if (Boolean.TRUE.equals(wrapDerivedTables)) {
            context.sql('(').formatIndentStart().formatNewLine().data(Tools.DataKey.DATA_WRAP_DERIVED_TABLES_IN_PARENTHESES, (Object)null);
         }

         switch(dialect) {
         default:
            this.toSQLReferenceLimitDefault(context);
            if (this.forUpdate && !Arrays.asList(SQLDialect.CUBRID).contains(family)) {
               context.formatSeparator().keyword("for update");
               if (!this.forUpdateOf.isEmpty()) {
                  boolean unqualified = Arrays.asList(SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB).contains(context.family());
                  boolean qualify = context.qualify();
                  if (unqualified) {
                     context.qualify(false);
                  }

                  context.sql(' ').keyword("of").sql(' ').visit(this.forUpdateOf);
                  if (unqualified) {
                     context.qualify(qualify);
                  }
               } else if (!this.forUpdateOfTables.isEmpty()) {
                  context.sql(' ').keyword("of").sql(' ');
                  switch(family) {
                  case DERBY:
                     this.forUpdateOfTables.toSQLFields(context);
                     break;
                  default:
                     Tools.tableNames(context, (Collection)this.forUpdateOfTables);
                  }
               }

               if (family == SQLDialect.FIREBIRD) {
                  context.sql(' ').keyword("with lock");
               }

               if (this.forUpdateMode != null) {
                  context.sql(' ');
                  context.keyword(this.forUpdateMode.toSQL());
                  if (this.forUpdateMode == SelectQueryImpl.ForUpdateMode.WAIT) {
                     context.sql(' ');
                     context.sql(this.forUpdateWait);
                  }
               }
            } else if (this.forShare) {
               switch(dialect) {
               case MARIADB:
               case MYSQL:
                  context.formatSeparator().keyword("lock in share mode");
                  break;
               default:
                  context.formatSeparator().keyword("for share");
               }
            }

            if (!StringUtils.isBlank(this.option)) {
               context.formatSeparator().sql(this.option);
            }

            if (Boolean.TRUE.equals(wrapDerivedTables)) {
               context.formatIndentEnd().formatNewLine().sql(')').data(Tools.DataKey.DATA_WRAP_DERIVED_TABLES_IN_PARENTHESES, true);
            }
         }
      } finally {
         context.data(Tools.DataKey.DATA_LOCALLY_SCOPED_DATA_MAP, localDataMap);
         if (renderTrailingLimit != null) {
            context.data(Tools.DataKey.DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE, renderTrailingLimit);
         }

      }

   }

   private final void pushWindow(Context<?> context) {
      if (!this.getWindow().isEmpty()) {
         ((Map)context.data(Tools.DataKey.DATA_LOCALLY_SCOPED_DATA_MAP)).put(Tools.DataKey.DATA_WINDOW_DEFINITIONS, this.getWindow());
      }

   }

   private void toSQLReferenceLimitDefault(Context<?> context) {
      Object data = context.data(Tools.DataKey.DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE);
      context.data(Tools.DataKey.DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE, true);
      this.toSQLReference0(context);
      if (data == null) {
         context.data().remove(Tools.DataKey.DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE);
      } else {
         context.data(Tools.DataKey.DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE, data);
      }

   }

   private final void toSQLReference0(Context<?> context) {
      this.toSQLReference0(context, (Field[])null, (Field[])null);
   }

   private final void toSQLReference0(Context<?> context, Field<?>[] originalFields, Field<?>[] alternativeFields) {
      SQLDialect dialect = context.dialect();
      SQLDialect family = dialect.family();
      int unionOpSize = this.unionOp.size();
      boolean wrapQueryExpressionBodyInDerivedTable = false;
      boolean wrapQueryExpressionInDerivedTable = context.data(Tools.DataKey.DATA_INSERT_SELECT_WITHOUT_INSERT_COLUMN_LIST) != null && unionOpSize > 0;
      if (wrapQueryExpressionInDerivedTable) {
         context.keyword("select").sql(" *").formatSeparator().keyword("from").sql(" (").formatIndentStart().formatNewLine();
      }

      if (unionOpSize > 0) {
         for(int i = unionOpSize - 1; i >= 0; --i) {
            switch((CombineOperator)this.unionOp.get(i)) {
            case EXCEPT:
               context.start(Clause.SELECT_EXCEPT);
               break;
            case EXCEPT_ALL:
               context.start(Clause.SELECT_EXCEPT_ALL);
               break;
            case INTERSECT:
               context.start(Clause.SELECT_INTERSECT);
               break;
            case INTERSECT_ALL:
               context.start(Clause.SELECT_INTERSECT_ALL);
               break;
            case UNION:
               context.start(Clause.SELECT_UNION);
               break;
            case UNION_ALL:
               context.start(Clause.SELECT_UNION_ALL);
            }

            this.unionParenthesis(context, "(");
         }
      }

      context.start(Clause.SELECT_SELECT).keyword("select").sql(' ');
      if (!StringUtils.isBlank(this.hint)) {
         context.sql(this.hint).sql(' ');
      }

      if (!this.distinctOn.isEmpty()) {
         context.keyword("distinct on").sql(" (").visit(this.distinctOn).sql(") ");
      } else if (this.distinct) {
         context.keyword("distinct").sql(' ');
      }

      context.declareFields(true);
      if (alternativeFields != null) {
         if (wrapQueryExpressionBodyInDerivedTable && originalFields.length < alternativeFields.length) {
            context.visit(new SelectFieldList((SelectField[])Arrays.copyOf(alternativeFields, alternativeFields.length - 1)));
         } else {
            context.visit(new SelectFieldList(alternativeFields));
         }
      } else if (context.subquery() && dialect == SQLDialect.H2 && context.data(Tools.DataKey.DATA_ROW_VALUE_EXPRESSION_PREDICATE_SUBQUERY) != null) {
         Object data = context.data(Tools.DataKey.DATA_ROW_VALUE_EXPRESSION_PREDICATE_SUBQUERY);

         try {
            context.data(Tools.DataKey.DATA_ROW_VALUE_EXPRESSION_PREDICATE_SUBQUERY, (Object)null);
            context.sql('(').visit(this.getSelect1()).sql(')');
         } finally {
            context.data(Tools.DataKey.DATA_ROW_VALUE_EXPRESSION_PREDICATE_SUBQUERY, data);
         }
      } else {
         context.visit(this.getSelect1());
      }

      context.declareFields(false).end(Clause.SELECT_SELECT);
      if (!context.subquery() && !Arrays.asList().contains(family)) {
         context.start(Clause.SELECT_INTO);
         Table<?> actualInto = (Table)context.data(Tools.DataKey.DATA_SELECT_INTO_TABLE);
         if (actualInto == null) {
            actualInto = this.into;
         }

         if (actualInto != null && context.data(Tools.DataKey.DATA_OMIT_INTO_CLAUSE) == null && Arrays.asList(SQLDialect.HSQLDB, SQLDialect.POSTGRES).contains(family)) {
            context.formatSeparator().keyword("into").sql(' ').visit(actualInto);
         }

         context.end(Clause.SELECT_INTO);
      }

      context.start(Clause.SELECT_FROM).declareTables(true);
      boolean hasFrom = !this.getFrom().isEmpty() || Arrays.asList(SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL).contains(family);
      List<Condition> semiAntiJoinPredicates = null;
      if (hasFrom) {
         Object previousCollect = context.data(Tools.DataKey.DATA_COLLECT_SEMI_ANTI_JOIN, true);
         Object previousCollected = context.data(Tools.DataKey.DATA_COLLECTED_SEMI_ANTI_JOIN, (Object)null);
         context.formatSeparator().keyword("from").sql(' ').visit(this.getFrom());
         semiAntiJoinPredicates = (List)context.data(Tools.DataKey.DATA_COLLECTED_SEMI_ANTI_JOIN, previousCollected);
         context.data(Tools.DataKey.DATA_COLLECT_SEMI_ANTI_JOIN, previousCollect);
      }

      context.declareTables(false).end(Clause.SELECT_FROM);
      context.start(Clause.SELECT_WHERE);
      if (!(this.getWhere().getWhere() instanceof TrueCondition) || semiAntiJoinPredicates != null) {
         ConditionProviderImpl where = new ConditionProviderImpl();
         if (semiAntiJoinPredicates != null) {
            where.addConditions((Collection)semiAntiJoinPredicates);
         }

         if (!(this.getWhere().getWhere() instanceof TrueCondition)) {
            where.addConditions(this.getWhere());
         }

         context.formatSeparator().keyword("where").sql(' ').visit(where);
      }

      context.end(Clause.SELECT_WHERE);
      context.start(Clause.SELECT_START_WITH);
      if (!(this.getConnectByStartWith().getWhere() instanceof TrueCondition)) {
         context.formatSeparator().keyword("start with").sql(' ').visit(this.getConnectByStartWith());
      }

      context.end(Clause.SELECT_START_WITH);
      context.start(Clause.SELECT_CONNECT_BY);
      if (!(this.getConnectBy().getWhere() instanceof TrueCondition)) {
         context.formatSeparator().keyword("connect by");
         if (this.connectByNoCycle) {
            context.sql(' ').keyword("nocycle");
         }

         context.sql(' ').visit(this.getConnectBy());
      }

      context.end(Clause.SELECT_CONNECT_BY);
      context.start(Clause.SELECT_GROUP_BY);
      if (this.grouping) {
         context.formatSeparator().keyword("group by").sql(' ');
         if (this.getGroupBy().isEmpty()) {
            if (Arrays.asList().contains(family)) {
               context.sql("empty_grouping_dummy_table.dual");
            } else if (Arrays.asList(SQLDialect.DERBY).contains(family)) {
               context.sql('0');
            } else if (family == SQLDialect.CUBRID) {
               context.sql("1 + 0");
            } else if (Arrays.asList(SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE).contains(family)) {
               context.sql('(').visit(DSL.select((SelectField)DSL.one())).sql(')');
            } else {
               context.sql("()");
            }
         } else {
            context.visit(this.getGroupBy());
         }
      }

      context.end(Clause.SELECT_GROUP_BY);
      context.start(Clause.SELECT_HAVING);
      if (!(this.getHaving().getWhere() instanceof TrueCondition)) {
         context.formatSeparator().keyword("having").sql(' ').visit(this.getHaving());
      }

      context.end(Clause.SELECT_HAVING);
      context.start(Clause.SELECT_WINDOW);
      if (!this.getWindow().isEmpty() && Arrays.asList(SQLDialect.POSTGRES).contains(family)) {
         context.formatSeparator().keyword("window").sql(' ').declareWindows(true).visit(this.getWindow()).declareWindows(false);
      }

      context.end(Clause.SELECT_WINDOW);
      this.toSQLOrderBy(context, originalFields, alternativeFields, false, wrapQueryExpressionBodyInDerivedTable, this.orderBy, this.limit);
      if (unionOpSize > 0) {
         this.unionParenthesis(context, ")");

         for(int i = 0; i < unionOpSize; ++i) {
            CombineOperator op = (CombineOperator)this.unionOp.get(i);
            Iterator var13 = ((QueryPartList)this.union.get(i)).iterator();

            while(var13.hasNext()) {
               Select<?> other = (Select)var13.next();
               context.formatSeparator().keyword(op.toSQL(dialect)).sql(' ');
               this.unionParenthesis(context, "(");
               context.visit(other);
               this.unionParenthesis(context, ")");
            }

            if (i < unionOpSize - 1) {
               this.unionParenthesis(context, ")");
            }

            switch((CombineOperator)this.unionOp.get(i)) {
            case EXCEPT:
               context.end(Clause.SELECT_EXCEPT);
               break;
            case EXCEPT_ALL:
               context.end(Clause.SELECT_EXCEPT_ALL);
               break;
            case INTERSECT:
               context.end(Clause.SELECT_INTERSECT);
               break;
            case INTERSECT_ALL:
               context.end(Clause.SELECT_INTERSECT_ALL);
               break;
            case UNION:
               context.end(Clause.SELECT_UNION);
               break;
            case UNION_ALL:
               context.end(Clause.SELECT_UNION_ALL);
            }
         }
      }

      boolean qualify = context.qualify();

      try {
         context.qualify(false);
         this.toSQLOrderBy(context, originalFields, alternativeFields, wrapQueryExpressionInDerivedTable, wrapQueryExpressionBodyInDerivedTable, this.unionOrderBy, this.unionLimit);
      } finally {
         context.qualify(qualify);
      }

   }

   private final void toSQLOrderBy(Context<?> context, Field<?>[] originalFields, Field<?>[] alternativeFields, boolean wrapQueryExpressionInDerivedTable, boolean wrapQueryExpressionBodyInDerivedTable, SortFieldList actualOrderBy, Limit actualLimit) {
      context.start(Clause.SELECT_ORDER_BY);
      if (!actualOrderBy.isEmpty()) {
         context.formatSeparator().keyword("order").sql(this.orderBySiblings ? " " : "").keyword(this.orderBySiblings ? "siblings" : "").sql(' ').keyword("by").sql(' ');
         context.visit(actualOrderBy);
      }

      context.end(Clause.SELECT_ORDER_BY);
      if (wrapQueryExpressionInDerivedTable) {
         context.formatIndentEnd().formatNewLine().sql(") x");
      }

      if (context.data().containsKey(Tools.DataKey.DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE) && actualLimit.isApplicable()) {
         context.visit(actualLimit);
      }

   }

   private final void unionParenthesis(Context<?> ctx, String parenthesis) {
      if (")".equals(parenthesis)) {
         ctx.formatIndentEnd().formatNewLine();
      } else if ("(".equals(parenthesis)) {
         switch(ctx.family()) {
         case DERBY:
         case MARIADB:
         case MYSQL:
         case SQLITE:
            ctx.formatNewLine().keyword("select").sql(" *").formatSeparator().keyword("from").sql(' ');
         }
      }

      switch(ctx.family()) {
      default:
         ctx.sql(parenthesis);
      case FIREBIRD:
         if ("(".equals(parenthesis)) {
            ctx.formatIndentStart().formatNewLine();
         } else if (")".equals(parenthesis)) {
            switch(ctx.family()) {
            case DERBY:
            case MARIADB:
            case MYSQL:
            case SQLITE:
               ctx.sql(" x");
            }
         }

      }
   }

   public final void addSelect(Collection<? extends SelectField<?>> fields) {
      this.getSelect0().addAll(Tools.fields(fields));
   }

   public final void addSelect(SelectField<?>... fields) {
      this.addSelect((Collection)Arrays.asList(fields));
   }

   public final void setDistinct(boolean distinct) {
      this.distinct = distinct;
   }

   public final void addDistinctOn(SelectField<?>... fields) {
      this.addDistinctOn((Collection)Arrays.asList(fields));
   }

   public final void addDistinctOn(Collection<? extends SelectField<?>> fields) {
      this.distinctOn.addAll(fields);
   }

   public final void setInto(Table<?> into) {
      this.into = into;
   }

   public final void addOffset(int offset) {
      this.getLimit().setOffset(offset);
   }

   public final void addOffset(Param<Integer> offset) {
      this.getLimit().setOffset(offset);
   }

   public final void addLimit(int numberOfRows) {
      this.getLimit().setNumberOfRows(numberOfRows);
   }

   public final void addLimit(Param<Integer> numberOfRows) {
      this.getLimit().setNumberOfRows(numberOfRows);
   }

   public final void addLimit(int offset, int numberOfRows) {
      this.getLimit().setOffset(offset);
      this.getLimit().setNumberOfRows(numberOfRows);
   }

   public final void addLimit(int offset, Param<Integer> numberOfRows) {
      this.getLimit().setOffset(offset);
      this.getLimit().setNumberOfRows(numberOfRows);
   }

   public final void addLimit(Param<Integer> offset, int numberOfRows) {
      this.getLimit().setOffset(offset);
      this.getLimit().setNumberOfRows(numberOfRows);
   }

   public final void addLimit(Param<Integer> offset, Param<Integer> numberOfRows) {
      this.getLimit().setOffset(offset);
      this.getLimit().setNumberOfRows(numberOfRows);
   }

   public final void setForUpdate(boolean forUpdate) {
      this.forUpdate = forUpdate;
      this.forShare = false;
   }

   public final void setForUpdateOf(Field<?>... fields) {
      this.setForUpdateOf((Collection)Arrays.asList(fields));
   }

   public final void setForUpdateOf(Collection<? extends Field<?>> fields) {
      this.setForUpdate(true);
      this.forUpdateOf.clear();
      this.forUpdateOfTables.clear();
      this.forUpdateOf.addAll(fields);
   }

   public final void setForUpdateOf(Table<?>... tables) {
      this.setForUpdate(true);
      this.forUpdateOf.clear();
      this.forUpdateOfTables.clear();
      this.forUpdateOfTables.addAll(Arrays.asList(tables));
   }

   public final void setForUpdateNoWait() {
      this.setForUpdate(true);
      this.forUpdateMode = SelectQueryImpl.ForUpdateMode.NOWAIT;
      this.forUpdateWait = 0;
   }

   public final void setForUpdateSkipLocked() {
      this.setForUpdate(true);
      this.forUpdateMode = SelectQueryImpl.ForUpdateMode.SKIP_LOCKED;
      this.forUpdateWait = 0;
   }

   public final void setForShare(boolean forShare) {
      this.forUpdate = false;
      this.forShare = forShare;
      this.forUpdateOf.clear();
      this.forUpdateOfTables.clear();
      this.forUpdateMode = null;
      this.forUpdateWait = 0;
   }

   public final List<Field<?>> getSelect() {
      return this.getSelect1();
   }

   final SelectFieldList getSelect0() {
      return this.select;
   }

   final SelectFieldList getSelect1() {
      if (!this.getSelect0().isEmpty()) {
         return this.getSelect0();
      } else {
         SelectFieldList result = new SelectFieldList();
         if (this.knownTableSource()) {
            Iterator var2 = this.getFrom().iterator();

            while(var2.hasNext()) {
               TableLike<?> table = (TableLike)var2.next();
               Field[] var4 = table.asTable().fields();
               int var5 = var4.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  Field<?> field = var4[var6];
                  result.add(field);
               }
            }
         }

         if (this.getFrom().isEmpty()) {
            result.add(DSL.one());
         }

         return result;
      }
   }

   private final boolean knownTableSource() {
      Iterator var1 = this.getFrom().iterator();

      Table table;
      do {
         if (!var1.hasNext()) {
            return true;
         }

         table = (Table)var1.next();
      } while(this.knownTable(table));

      return false;
   }

   private final boolean knownTable(Table<?> table) {
      return table.fieldsRow().size() > 0;
   }

   public final Class<? extends R> getRecordType() {
      return this.getFrom().size() == 1 && this.getSelect0().isEmpty() ? ((Table)this.getFrom().get(0)).asTable().getRecordType() : RecordImpl.class;
   }

   final TableList getFrom() {
      return this.from;
   }

   final void setGrouping() {
      this.grouping = true;
   }

   final QueryPartList<GroupField> getGroupBy() {
      return this.groupBy;
   }

   final ConditionProviderImpl getWhere() {
      if (!this.getOrderBy().isEmpty() && !this.getSeek().isEmpty()) {
         SortFieldList o = this.getOrderBy();
         Condition c = null;
         if (o.nulls()) {
         }

         ConditionProviderImpl or;
         if (o.size() > 1 && o.uniform()) {
            if (((SortField)o.get(0)).getOrder() == SortOrder.ASC ^ this.seekBefore) {
               c = DSL.row((Collection)o.fields()).gt(DSL.row((Collection)this.getSeek()));
            } else {
               c = DSL.row((Collection)o.fields()).lt(DSL.row((Collection)this.getSeek()));
            }
         } else {
            or = new ConditionProviderImpl();

            for(int i = 0; i < o.size(); ++i) {
               ConditionProviderImpl and = new ConditionProviderImpl();

               for(int j = 0; j < i; ++j) {
                  SortFieldImpl<?> s = (SortFieldImpl)o.get(j);
                  and.addConditions(s.getField().eq((Field)this.getSeek().get(j)));
               }

               SortFieldImpl<?> s = (SortFieldImpl)o.get(i);
               if (s.getOrder() == SortOrder.ASC ^ this.seekBefore) {
                  and.addConditions(s.getField().gt((Field)this.getSeek().get(i)));
               } else {
                  and.addConditions(s.getField().lt((Field)this.getSeek().get(i)));
               }

               or.addConditions(Operator.OR, and);
            }

            c = or;
         }

         or = new ConditionProviderImpl();
         or.addConditions(this.condition, (Condition)c);
         return or;
      } else {
         return this.condition;
      }
   }

   final ConditionProviderImpl getConnectBy() {
      return this.connectBy;
   }

   final ConditionProviderImpl getConnectByStartWith() {
      return this.connectByStartWith;
   }

   final ConditionProviderImpl getHaving() {
      return this.having;
   }

   final QueryPartList<WindowDefinition> getWindow() {
      return this.window;
   }

   final SortFieldList getOrderBy() {
      return this.unionOp.size() == 0 ? this.orderBy : this.unionOrderBy;
   }

   final QueryPartList<Field<?>> getSeek() {
      return this.unionOp.size() == 0 ? this.seek : this.unionSeek;
   }

   final Limit getLimit() {
      return this.unionOp.size() == 0 ? this.limit : this.unionLimit;
   }

   public final void addOrderBy(Collection<? extends SortField<?>> fields) {
      this.getOrderBy().addAll(fields);
   }

   public final void addOrderBy(Field<?>... fields) {
      this.getOrderBy().addAll(fields);
   }

   public final void addOrderBy(SortField<?>... fields) {
      this.addOrderBy((Collection)Arrays.asList(fields));
   }

   public final void addOrderBy(int... fieldIndexes) {
      Field<?>[] fields = new Field[fieldIndexes.length];

      for(int i = 0; i < fieldIndexes.length; ++i) {
         fields[i] = DSL.inline(fieldIndexes[i]);
      }

      this.addOrderBy(fields);
   }

   public final void setOrderBySiblings(boolean orderBySiblings) {
      if (this.unionOp.size() == 0) {
         this.orderBySiblings = orderBySiblings;
      } else {
         this.unionOrderBySiblings = orderBySiblings;
      }

   }

   public final void addSeekAfter(Field<?>... fields) {
      this.addSeekAfter((Collection)Arrays.asList(fields));
   }

   public final void addSeekAfter(Collection<? extends Field<?>> fields) {
      if (this.unionOp.size() == 0) {
         this.seekBefore = false;
      } else {
         this.unionSeekBefore = false;
      }

      this.getSeek().addAll(fields);
   }

   public final void addSeekBefore(Field<?>... fields) {
      this.addSeekBefore((Collection)Arrays.asList(fields));
   }

   public final void addSeekBefore(Collection<? extends Field<?>> fields) {
      if (this.unionOp.size() == 0) {
         this.seekBefore = true;
      } else {
         this.unionSeekBefore = true;
      }

      this.getSeek().addAll(fields);
   }

   public final void addConditions(Condition... conditions) {
      this.condition.addConditions(conditions);
   }

   public final void addConditions(Collection<? extends Condition> conditions) {
      this.condition.addConditions(conditions);
   }

   public final void addConditions(Operator operator, Condition... conditions) {
      this.condition.addConditions(operator, conditions);
   }

   public final void addConditions(Operator operator, Collection<? extends Condition> conditions) {
      this.condition.addConditions(operator, conditions);
   }

   final void setConnectByNoCycle(boolean connectByNoCycle) {
      this.connectByNoCycle = connectByNoCycle;
   }

   final void setStartWith(Condition condition) {
      this.connectByStartWith.addConditions(condition);
   }

   final void setHint(String hint) {
      this.hint = hint;
   }

   final void setOption(String option) {
      this.option = option;
   }

   final boolean isForUpdate() {
      return this.forUpdate;
   }

   public final void addFrom(Collection<? extends TableLike<?>> f) {
      Iterator var2 = f.iterator();

      while(var2.hasNext()) {
         TableLike<?> provider = (TableLike)var2.next();
         this.getFrom().add(provider.asTable());
      }

   }

   public final void addFrom(TableLike<?> f) {
      this.addFrom((Collection)Arrays.asList(f));
   }

   public final void addFrom(TableLike<?>... f) {
      this.addFrom((Collection)Arrays.asList(f));
   }

   public final void addConnectBy(Condition c) {
      this.getConnectBy().addConditions(c);
   }

   public final void addConnectByNoCycle(Condition c) {
      this.getConnectBy().addConditions(c);
      this.setConnectByNoCycle(true);
   }

   public final void setConnectByStartWith(Condition c) {
      this.setStartWith(c);
   }

   public final void addGroupBy(Collection<? extends GroupField> fields) {
      this.setGrouping();
      this.getGroupBy().addAll(fields);
   }

   public final void addGroupBy(GroupField... fields) {
      this.addGroupBy((Collection)Arrays.asList(fields));
   }

   public final void addHaving(Condition... conditions) {
      this.addHaving((Collection)Arrays.asList(conditions));
   }

   public final void addHaving(Collection<? extends Condition> conditions) {
      this.getHaving().addConditions(conditions);
   }

   public final void addHaving(Operator operator, Condition... conditions) {
      this.getHaving().addConditions(operator, conditions);
   }

   public final void addHaving(Operator operator, Collection<? extends Condition> conditions) {
      this.getHaving().addConditions(operator, conditions);
   }

   public final void addWindow(WindowDefinition... definitions) {
      this.addWindow((Collection)Arrays.asList(definitions));
   }

   public final void addWindow(Collection<? extends WindowDefinition> definitions) {
      this.getWindow().addAll(definitions);
   }

   private final Select<R> combine(CombineOperator op, Select<? extends R> other) {
      int index = this.unionOp.size() - 1;
      if (index == -1 || this.unionOp.get(index) != op || op == CombineOperator.EXCEPT || op == CombineOperator.EXCEPT_ALL) {
         this.unionOp.add(op);
         this.union.add(new QueryPartList());
         ++index;
      }

      ((QueryPartList)this.union.get(index)).add((QueryPart)other);
      return this;
   }

   public final Select<R> union(Select<? extends R> other) {
      return this.combine(CombineOperator.UNION, other);
   }

   public final Select<R> unionAll(Select<? extends R> other) {
      return this.combine(CombineOperator.UNION_ALL, other);
   }

   public final Select<R> except(Select<? extends R> other) {
      return this.combine(CombineOperator.EXCEPT, other);
   }

   public final Select<R> exceptAll(Select<? extends R> other) {
      return this.combine(CombineOperator.EXCEPT_ALL, other);
   }

   public final Select<R> intersect(Select<? extends R> other) {
      return this.combine(CombineOperator.INTERSECT, other);
   }

   public final Select<R> intersectAll(Select<? extends R> other) {
      return this.combine(CombineOperator.INTERSECT_ALL, other);
   }

   public final void addJoin(TableLike<?> table, Condition... conditions) {
      this.addJoin(table, JoinType.JOIN, conditions);
   }

   public final void addJoin(TableLike<?> table, JoinType type, Condition... conditions) {
      this.addJoin0(table, type, conditions, (Field[])null);
   }

   private final void addJoin0(TableLike<?> table, JoinType type, Condition[] conditions, Field<?>[] partitionBy) {
      int index = this.getFrom().size() - 1;
      Table<?> joined = null;
      switch(type) {
      case JOIN:
      case STRAIGHT_JOIN:
      case LEFT_SEMI_JOIN:
      case LEFT_ANTI_JOIN:
      case FULL_OUTER_JOIN:
         joined = ((Table)this.getFrom().get(index)).join(table, type).on(conditions);
         break;
      case LEFT_OUTER_JOIN:
      case RIGHT_OUTER_JOIN:
         TablePartitionByStep<?> p = ((Table)this.getFrom().get(index)).join(table, type);
         joined = p.on(conditions);
         break;
      case CROSS_JOIN:
      case NATURAL_JOIN:
      case NATURAL_LEFT_OUTER_JOIN:
      case NATURAL_RIGHT_OUTER_JOIN:
         joined = ((Table)this.getFrom().get(index)).join(table, type);
         break;
      default:
         throw new IllegalArgumentException("Bad join type: " + type);
      }

      this.getFrom().set(index, (QueryPart)joined);
   }

   public final void addJoinOnKey(TableLike<?> table, JoinType type) throws DataAccessException {
      int index = this.getFrom().size() - 1;
      Table<?> joined = null;
      switch(type) {
      case JOIN:
      case LEFT_SEMI_JOIN:
      case LEFT_ANTI_JOIN:
      case FULL_OUTER_JOIN:
      case LEFT_OUTER_JOIN:
      case RIGHT_OUTER_JOIN:
         joined = ((Table)this.getFrom().get(index)).join(table, type).onKey();
         this.getFrom().set(index, joined);
         return;
      case STRAIGHT_JOIN:
      default:
         throw new IllegalArgumentException("JoinType " + type + " is not supported with the addJoinOnKey() method. Use INNER or OUTER JOINs only");
      }
   }

   public final void addJoinOnKey(TableLike<?> table, JoinType type, TableField<?, ?>... keyFields) throws DataAccessException {
      int index = this.getFrom().size() - 1;
      Table<?> joined = null;
      switch(type) {
      case JOIN:
      case LEFT_SEMI_JOIN:
      case LEFT_ANTI_JOIN:
      case FULL_OUTER_JOIN:
      case LEFT_OUTER_JOIN:
      case RIGHT_OUTER_JOIN:
         joined = ((Table)this.getFrom().get(index)).join(table, type).onKey(keyFields);
         this.getFrom().set(index, joined);
         return;
      case STRAIGHT_JOIN:
      default:
         throw new IllegalArgumentException("JoinType " + type + " is not supported with the addJoinOnKey() method. Use INNER or OUTER JOINs only");
      }
   }

   public final void addJoinOnKey(TableLike<?> table, JoinType type, ForeignKey<?, ?> key) {
      int index = this.getFrom().size() - 1;
      Table<?> joined = null;
      switch(type) {
      case JOIN:
      case LEFT_SEMI_JOIN:
      case LEFT_ANTI_JOIN:
      case FULL_OUTER_JOIN:
      case LEFT_OUTER_JOIN:
      case RIGHT_OUTER_JOIN:
         joined = ((Table)this.getFrom().get(index)).join(table, type).onKey(key);
         this.getFrom().set(index, joined);
         return;
      case STRAIGHT_JOIN:
      default:
         throw new IllegalArgumentException("JoinType " + type + " is not supported with the addJoinOnKey() method. Use INNER or OUTER JOINs only");
      }
   }

   public final void addJoinUsing(TableLike<?> table, Collection<? extends Field<?>> fields) {
      this.addJoinUsing(table, JoinType.JOIN, fields);
   }

   public final void addJoinUsing(TableLike<?> table, JoinType type, Collection<? extends Field<?>> fields) {
      int index = this.getFrom().size() - 1;
      Table<?> joined = null;
      switch(type) {
      case JOIN:
      case LEFT_SEMI_JOIN:
      case LEFT_ANTI_JOIN:
      case FULL_OUTER_JOIN:
      case LEFT_OUTER_JOIN:
      case RIGHT_OUTER_JOIN:
         joined = ((Table)this.getFrom().get(index)).join(table, type).using(fields);
         this.getFrom().set(index, joined);
         return;
      case STRAIGHT_JOIN:
      default:
         throw new IllegalArgumentException("JoinType " + type + " is not supported with the addJoinUsing() method. Use INNER or OUTER JOINs only");
      }
   }

   public final void addHint(String h) {
      this.setHint(h);
   }

   public final void addOption(String o) {
      this.setOption(o);
   }

   static {
      CLAUSES = new Clause[]{Clause.SELECT};
   }

   private static enum ForUpdateMode {
      WAIT("wait"),
      NOWAIT("nowait"),
      SKIP_LOCKED("skip locked");

      private final String sql;

      private ForUpdateMode(String sql) {
         this.sql = sql;
      }

      public final String toSQL() {
         return this.sql;
      }
   }
}
