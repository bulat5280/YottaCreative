package org.jooq.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.jooq.AggregateFunction;
import org.jooq.ArrayAggOrderByStep;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.OrderedAggregateFunction;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.SortField;
import org.jooq.WindowBeforeOverStep;
import org.jooq.WindowDefinition;
import org.jooq.WindowFinalStep;
import org.jooq.WindowIgnoreNullsStep;
import org.jooq.WindowOrderByStep;
import org.jooq.WindowPartitionByStep;
import org.jooq.WindowRowsAndStep;
import org.jooq.WindowRowsStep;
import org.jooq.WindowSpecification;

class Function<T> extends AbstractField<T> implements OrderedAggregateFunction<T>, ArrayAggOrderByStep<T>, AggregateFunction<T>, WindowIgnoreNullsStep<T>, WindowPartitionByStep<T>, WindowRowsStep<T>, WindowRowsAndStep<T> {
   private static final long serialVersionUID = 347252741712134044L;
   static final Field<Integer> ASTERISK = DSL.field("*", Integer.class);
   private final Name name;
   private final Term term;
   private final QueryPartList<QueryPart> arguments;
   private final boolean distinct;
   private final SortFieldList withinGroupOrderBy;
   private final SortFieldList keepDenseRankOrderBy;
   private Condition filter;
   private WindowSpecificationImpl windowSpecification;
   private WindowDefinitionImpl windowDefinition;
   private Name windowName;
   private boolean first;
   private boolean ignoreNulls;
   private boolean respectNulls;

   Function(String name, DataType<T> type, QueryPart... arguments) {
      this(name, false, type, arguments);
   }

   Function(Term term, DataType<T> type, QueryPart... arguments) {
      this(term, false, type, arguments);
   }

   Function(Name name, DataType<T> type, QueryPart... arguments) {
      this(name, false, type, arguments);
   }

   Function(String name, boolean distinct, DataType<T> type, QueryPart... arguments) {
      super(name, type);
      this.term = null;
      this.name = null;
      this.distinct = distinct;
      this.arguments = new QueryPartList(arguments);
      this.keepDenseRankOrderBy = new SortFieldList();
      this.withinGroupOrderBy = new SortFieldList();
   }

   Function(Term term, boolean distinct, DataType<T> type, QueryPart... arguments) {
      super(term.name().toLowerCase(), type);
      this.term = term;
      this.name = null;
      this.distinct = distinct;
      this.arguments = new QueryPartList(arguments);
      this.keepDenseRankOrderBy = new SortFieldList();
      this.withinGroupOrderBy = new SortFieldList();
   }

   Function(Name name, boolean distinct, DataType<T> type, QueryPart... arguments) {
      super(last(name.getName()), type);
      this.term = null;
      this.name = name;
      this.distinct = distinct;
      this.arguments = new QueryPartList(arguments);
      this.keepDenseRankOrderBy = new SortFieldList();
      this.withinGroupOrderBy = new SortFieldList();
   }

   static final String last(String... strings) {
      return strings != null && strings.length > 0 ? strings[strings.length - 1] : null;
   }

   public void accept(Context<?> ctx) {
      if (this.term == Term.ARRAY_AGG && Arrays.asList(SQLDialect.HSQLDB, SQLDialect.POSTGRES).contains(ctx.family())) {
         this.toSQLGroupConcat(ctx);
         this.toSQLFilterClause(ctx);
         this.toSQLOverClause(ctx);
      } else if (this.term == Term.LIST_AGG && Arrays.asList(SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL).contains(ctx.family())) {
         this.toSQLGroupConcat(ctx);
      } else if (this.term == Term.LIST_AGG && Arrays.asList(SQLDialect.POSTGRES).contains(ctx.family())) {
         this.toSQLStringAgg(ctx);
         this.toSQLFilterClause(ctx);
         this.toSQLOverClause(ctx);
      } else if (this.term == Term.MEDIAN && Arrays.asList(SQLDialect.POSTGRES).contains(ctx.family())) {
         Field<?>[] fields = new Field[this.arguments.size()];

         for(int i = 0; i < fields.length; ++i) {
            fields[i] = DSL.field("{0}", this.arguments.get(i));
         }

         ctx.visit(DSL.percentileCont((Number)(new BigDecimal("0.5"))).withinGroupOrderBy(fields));
      } else {
         this.toSQLArguments(ctx);
         this.toSQLKeepDenseRankOrderByClause(ctx);
         this.toSQLWithinGroupClause(ctx);
         this.toSQLFilterClause(ctx);
         this.toSQLOverClause(ctx);
      }

   }

   final void toSQLStringAgg(Context<?> ctx) {
      this.toSQLFunctionName(ctx);
      ctx.sql('(');
      if (this.distinct) {
         ctx.keyword("distinct").sql(' ');
      }

      ctx.visit(((Field)this.arguments.get(0)).cast(String.class));
      if (this.arguments.size() > 1) {
         ctx.sql(", ").visit(this.arguments.get(1));
      } else {
         ctx.sql(", ''");
      }

      if (!this.withinGroupOrderBy.isEmpty()) {
         ctx.sql(' ').keyword("order by").sql(' ').visit(this.withinGroupOrderBy);
      }

      ctx.sql(')');
   }

   final void toSQLGroupConcat(Context<?> ctx) {
      this.toSQLFunctionName(ctx);
      ctx.sql('(');
      this.toSQLArguments1(ctx, new QueryPartList(new QueryPart[]{this.arguments.get(0)}));
      if (!this.withinGroupOrderBy.isEmpty()) {
         ctx.sql(' ').keyword("order by").sql(' ').visit(this.withinGroupOrderBy);
      }

      if (this.arguments.size() > 1) {
         ctx.sql(' ').keyword("separator").sql(' ').visit(this.arguments.get(1));
      }

      ctx.sql(')');
   }

   final void toSQLFilterClause(Context<?> ctx) {
      if (this.filter != null && (SQLDialect.HSQLDB == ctx.family() || SQLDialect.POSTGRES_9_4.precedes(ctx.dialect()))) {
         ctx.sql(' ').keyword("filter").sql(" (").keyword("where").sql(' ').visit(this.filter).sql(')');
      }

   }

   final void toSQLOverClause(Context<?> ctx) {
      QueryPart window = this.window(ctx);
      if (window != null) {
         if (this.term != Term.ROW_NUMBER || ctx.configuration().dialect() != SQLDialect.HSQLDB) {
            ctx.sql(' ').keyword("over").sql(' ').visit(window);
         }
      }
   }

   final QueryPart window(Context<?> ctx) {
      if (this.windowSpecification != null) {
         return DSL.sql("({0})", this.windowSpecification);
      } else if (this.windowDefinition != null) {
         return (QueryPart)(SQLDialect.POSTGRES == ctx.family() ? this.windowDefinition : DSL.sql("({0})", this.windowDefinition));
      } else {
         if (this.windowName != null) {
            if (Arrays.asList(SQLDialect.POSTGRES).contains(ctx.family())) {
               return this.windowName;
            }

            Map<Object, Object> map = (Map)ctx.data(Tools.DataKey.DATA_LOCALLY_SCOPED_DATA_MAP);
            QueryPartList<WindowDefinition> windows = (QueryPartList)map.get(Tools.DataKey.DATA_WINDOW_DEFINITIONS);
            if (windows == null) {
               return this.windowName;
            }

            Iterator var4 = windows.iterator();

            while(var4.hasNext()) {
               WindowDefinition window = (WindowDefinition)var4.next();
               if (((WindowDefinitionImpl)window).getName().equals(this.windowName)) {
                  return DSL.sql("({0})", window);
               }
            }
         }

         return null;
      }
   }

   final void toSQLKeepDenseRankOrderByClause(Context<?> ctx) {
      if (!this.keepDenseRankOrderBy.isEmpty()) {
         ctx.sql(' ').keyword("keep").sql(" (").keyword("dense_rank").sql(' ').keyword(this.first ? "first" : "last").sql(' ').keyword("order by").sql(' ').visit(this.keepDenseRankOrderBy).sql(')');
      }

   }

   final void toSQLWithinGroupClause(Context<?> ctx) {
      if (!this.withinGroupOrderBy.isEmpty()) {
         ctx.sql(' ').keyword("within group").sql(" (").keyword("order by").sql(' ').visit(this.withinGroupOrderBy).sql(')');
      }

   }

   final void toSQLArguments(Context<?> ctx) {
      this.toSQLFunctionName(ctx);
      ctx.sql('(');
      this.toSQLArguments0(ctx);
      ctx.sql(')');
   }

   final void toSQLArguments0(Context<?> ctx) {
      this.toSQLArguments1(ctx, this.arguments);
   }

   final void toSQLArguments1(Context<?> ctx, QueryPartList<QueryPart> args) {
      if (this.distinct) {
         ctx.keyword("distinct");
         if (ctx.family() == SQLDialect.POSTGRES && args.size() > 1) {
            ctx.sql('(');
         } else {
            ctx.sql(' ');
         }
      }

      if (!args.isEmpty()) {
         if (this.filter != null && SQLDialect.HSQLDB != ctx.family() && !SQLDialect.POSTGRES_9_4.precedes(ctx.dialect())) {
            QueryPartList<Field<?>> expressions = new QueryPartList();
            Iterator var4 = args.iterator();

            while(var4.hasNext()) {
               QueryPart argument = (QueryPart)var4.next();
               expressions.add((QueryPart)DSL.when(this.filter, argument == ASTERISK ? DSL.one() : argument));
            }

            ctx.visit(expressions);
         } else {
            ctx.visit(args);
         }
      }

      if (this.distinct && ctx.family() == SQLDialect.POSTGRES && args.size() > 1) {
         ctx.sql(')');
      }

      if (this.ignoreNulls) {
         ctx.sql(' ').keyword("ignore nulls");
      } else if (this.respectNulls) {
         ctx.sql(' ').keyword("respect nulls");
      }

   }

   final void toSQLFunctionName(Context<?> ctx) {
      if (this.name != null) {
         ctx.visit(this.name);
      } else if (this.term != null) {
         ctx.sql(this.term.translate(ctx.configuration().dialect()));
      } else {
         ctx.sql(this.getName());
      }

   }

   final QueryPartList<QueryPart> getArguments() {
      return this.arguments;
   }

   public final AggregateFunction<T> withinGroupOrderBy(Field<?>... fields) {
      this.withinGroupOrderBy.addAll(fields);
      return this;
   }

   public final AggregateFunction<T> withinGroupOrderBy(SortField<?>... fields) {
      this.withinGroupOrderBy.addAll(Arrays.asList(fields));
      return this;
   }

   public final AggregateFunction<T> withinGroupOrderBy(Collection<? extends SortField<?>> fields) {
      this.withinGroupOrderBy.addAll(fields);
      return this;
   }

   public final WindowBeforeOverStep<T> filterWhere(Condition... conditions) {
      return this.filterWhere((Collection)Arrays.asList(conditions));
   }

   public final WindowBeforeOverStep<T> filterWhere(Collection<? extends Condition> conditions) {
      ConditionProviderImpl c = new ConditionProviderImpl();
      c.addConditions(conditions);
      this.filter = c;
      return this;
   }

   public final WindowBeforeOverStep<T> filterWhere(Field<Boolean> field) {
      return this.filterWhere(DSL.condition(field));
   }

   public final WindowBeforeOverStep<T> filterWhere(Boolean field) {
      return this.filterWhere(DSL.condition(field));
   }

   public final WindowBeforeOverStep<T> filterWhere(SQL sql) {
      return this.filterWhere(DSL.condition(sql));
   }

   public final WindowBeforeOverStep<T> filterWhere(String sql) {
      return this.filterWhere(DSL.condition(sql));
   }

   public final WindowBeforeOverStep<T> filterWhere(String sql, Object... bindings) {
      return this.filterWhere(DSL.condition(sql, bindings));
   }

   public final WindowBeforeOverStep<T> filterWhere(String sql, QueryPart... parts) {
      return this.filterWhere(DSL.condition(sql, parts));
   }

   public final WindowPartitionByStep<T> over() {
      this.windowSpecification = new WindowSpecificationImpl();
      return this;
   }

   public final WindowFinalStep<T> over(WindowSpecification specification) {
      this.windowSpecification = (WindowSpecificationImpl)specification;
      return this;
   }

   public final WindowFinalStep<T> over(WindowDefinition definition) {
      this.windowDefinition = (WindowDefinitionImpl)definition;
      return this;
   }

   public final WindowFinalStep<T> over(String n) {
      return this.over(DSL.name(n));
   }

   public final WindowFinalStep<T> over(Name n) {
      this.windowName = n;
      return this;
   }

   public final WindowOrderByStep<T> partitionBy(Field<?>... fields) {
      this.windowSpecification.partitionBy(fields);
      return this;
   }

   public final WindowOrderByStep<T> partitionByOne() {
      this.windowSpecification.partitionByOne();
      return this;
   }

   public final Function<T> orderBy(Field<?>... fields) {
      if (this.windowSpecification != null) {
         this.windowSpecification.orderBy(fields);
      } else {
         this.withinGroupOrderBy(fields);
      }

      return this;
   }

   public final Function<T> orderBy(SortField<?>... fields) {
      if (this.windowSpecification != null) {
         this.windowSpecification.orderBy(fields);
      } else {
         this.withinGroupOrderBy(fields);
      }

      return this;
   }

   public final Function<T> orderBy(Collection<? extends SortField<?>> fields) {
      if (this.windowSpecification != null) {
         this.windowSpecification.orderBy(fields);
      } else {
         this.withinGroupOrderBy(fields);
      }

      return this;
   }

   public final WindowFinalStep<T> rowsUnboundedPreceding() {
      this.windowSpecification.rowsUnboundedPreceding();
      return this;
   }

   public final WindowFinalStep<T> rowsPreceding(int number) {
      this.windowSpecification.rowsPreceding(number);
      return this;
   }

   public final WindowFinalStep<T> rowsCurrentRow() {
      this.windowSpecification.rowsCurrentRow();
      return this;
   }

   public final WindowFinalStep<T> rowsUnboundedFollowing() {
      this.windowSpecification.rowsUnboundedFollowing();
      return this;
   }

   public final WindowFinalStep<T> rowsFollowing(int number) {
      this.windowSpecification.rowsFollowing(number);
      return this;
   }

   public final WindowRowsAndStep<T> rowsBetweenUnboundedPreceding() {
      this.windowSpecification.rowsBetweenUnboundedPreceding();
      return this;
   }

   public final WindowRowsAndStep<T> rowsBetweenPreceding(int number) {
      this.windowSpecification.rowsBetweenPreceding(number);
      return this;
   }

   public final WindowRowsAndStep<T> rowsBetweenCurrentRow() {
      this.windowSpecification.rowsBetweenCurrentRow();
      return this;
   }

   public final WindowRowsAndStep<T> rowsBetweenUnboundedFollowing() {
      this.windowSpecification.rowsBetweenUnboundedFollowing();
      return this;
   }

   public final WindowRowsAndStep<T> rowsBetweenFollowing(int number) {
      this.windowSpecification.rowsBetweenFollowing(number);
      return this;
   }

   public final WindowFinalStep<T> rangeUnboundedPreceding() {
      this.windowSpecification.rangeUnboundedPreceding();
      return this;
   }

   public final WindowFinalStep<T> rangePreceding(int number) {
      this.windowSpecification.rangePreceding(number);
      return this;
   }

   public final WindowFinalStep<T> rangeCurrentRow() {
      this.windowSpecification.rangeCurrentRow();
      return this;
   }

   public final WindowFinalStep<T> rangeUnboundedFollowing() {
      this.windowSpecification.rangeUnboundedFollowing();
      return this;
   }

   public final WindowFinalStep<T> rangeFollowing(int number) {
      this.windowSpecification.rangeFollowing(number);
      return this;
   }

   public final WindowRowsAndStep<T> rangeBetweenUnboundedPreceding() {
      this.windowSpecification.rangeBetweenUnboundedPreceding();
      return this;
   }

   public final WindowRowsAndStep<T> rangeBetweenPreceding(int number) {
      this.windowSpecification.rangeBetweenPreceding(number);
      return this;
   }

   public final WindowRowsAndStep<T> rangeBetweenCurrentRow() {
      this.windowSpecification.rangeBetweenCurrentRow();
      return this;
   }

   public final WindowRowsAndStep<T> rangeBetweenUnboundedFollowing() {
      this.windowSpecification.rangeBetweenUnboundedFollowing();
      return this;
   }

   public final WindowRowsAndStep<T> rangeBetweenFollowing(int number) {
      this.windowSpecification.rangeBetweenFollowing(number);
      return this;
   }

   public final WindowFinalStep<T> andUnboundedPreceding() {
      this.windowSpecification.andUnboundedPreceding();
      return this;
   }

   public final WindowFinalStep<T> andPreceding(int number) {
      this.windowSpecification.andPreceding(number);
      return this;
   }

   public final WindowFinalStep<T> andCurrentRow() {
      this.windowSpecification.andCurrentRow();
      return this;
   }

   public final WindowFinalStep<T> andUnboundedFollowing() {
      this.windowSpecification.andUnboundedFollowing();
      return this;
   }

   public final WindowFinalStep<T> andFollowing(int number) {
      this.windowSpecification.andFollowing(number);
      return this;
   }
}
