package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import org.jooq.AggregateFilterStep;
import org.jooq.AggregateFunction;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.GroupConcatOrderByStep;
import org.jooq.GroupConcatSeparatorStep;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.SortField;
import org.jooq.WindowDefinition;
import org.jooq.WindowFinalStep;
import org.jooq.WindowPartitionByStep;
import org.jooq.WindowSpecification;

final class GroupConcat extends AbstractFunction<String> implements GroupConcatOrderByStep {
   private static final long serialVersionUID = -6884415527559632960L;
   private final Field<?> field;
   private final boolean distinct;
   private final SortFieldList orderBy;
   private String separator;

   GroupConcat(Field<?> field) {
      this(field, false);
   }

   GroupConcat(Field<?> field, boolean distinct) {
      super("group_concat", SQLDataType.VARCHAR);
      this.field = field;
      this.distinct = distinct;
      this.orderBy = new SortFieldList();
   }

   final Field<String> getFunction0(Configuration configuration) {
      Function result;
      if (this.separator == null) {
         result = new Function(Term.LIST_AGG, this.distinct, SQLDataType.VARCHAR, new QueryPart[]{this.field});
      } else {
         Field<String> literal = DSL.inline(this.separator);
         result = new Function(Term.LIST_AGG, this.distinct, SQLDataType.VARCHAR, new QueryPart[]{this.field, literal});
      }

      return result.withinGroupOrderBy((Collection)this.orderBy);
   }

   public final AggregateFilterStep<String> filterWhere(Condition... conditions) {
      throw new UnsupportedOperationException("FILTER() not supported on GROUP_CONCAT aggregate function");
   }

   public final AggregateFilterStep<String> filterWhere(Collection<? extends Condition> conditions) {
      throw new UnsupportedOperationException("FILTER() not supported on GROUP_CONCAT aggregate function");
   }

   public final AggregateFilterStep<String> filterWhere(Field<Boolean> c) {
      throw new UnsupportedOperationException("FILTER() not supported on GROUP_CONCAT aggregate function");
   }

   public final AggregateFilterStep<String> filterWhere(Boolean c) {
      throw new UnsupportedOperationException("FILTER() not supported on GROUP_CONCAT aggregate function");
   }

   public final AggregateFilterStep<String> filterWhere(SQL sql) {
      throw new UnsupportedOperationException("FILTER() not supported on GROUP_CONCAT aggregate function");
   }

   public final AggregateFilterStep<String> filterWhere(String sql) {
      throw new UnsupportedOperationException("FILTER() not supported on GROUP_CONCAT aggregate function");
   }

   public final AggregateFilterStep<String> filterWhere(String sql, Object... bindings) {
      throw new UnsupportedOperationException("FILTER() not supported on GROUP_CONCAT aggregate function");
   }

   public final AggregateFilterStep<String> filterWhere(String sql, QueryPart... parts) {
      throw new UnsupportedOperationException("FILTER() not supported on GROUP_CONCAT aggregate function");
   }

   public final WindowPartitionByStep<String> over() {
      throw new UnsupportedOperationException("OVER() not supported on GROUP_CONCAT aggregate function");
   }

   public final WindowFinalStep<String> over(WindowSpecification specification) {
      throw new UnsupportedOperationException("OVER() not supported on GROUP_CONCAT aggregate function");
   }

   public final WindowFinalStep<String> over(WindowDefinition definition) {
      throw new UnsupportedOperationException("OVER() not supported on GROUP_CONCAT aggregate function");
   }

   public final WindowFinalStep<String> over(Name name) {
      throw new UnsupportedOperationException("OVER() not supported on GROUP_CONCAT aggregate function");
   }

   public final WindowFinalStep<String> over(String name) {
      throw new UnsupportedOperationException("OVER() not supported on GROUP_CONCAT aggregate function");
   }

   public final AggregateFunction<String> separator(String s) {
      this.separator = s;
      return this;
   }

   public final GroupConcatSeparatorStep orderBy(Field<?>... fields) {
      this.orderBy.addAll(fields);
      return this;
   }

   public final GroupConcatSeparatorStep orderBy(SortField<?>... fields) {
      this.orderBy.addAll(Arrays.asList(fields));
      return this;
   }

   public final GroupConcatSeparatorStep orderBy(Collection<? extends SortField<?>> fields) {
      this.orderBy.addAll(fields);
      return this;
   }
}
