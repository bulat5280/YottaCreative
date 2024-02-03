package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.SortField;
import org.jooq.WindowSpecificationFinalStep;
import org.jooq.WindowSpecificationOrderByStep;
import org.jooq.WindowSpecificationPartitionByStep;
import org.jooq.WindowSpecificationRowsAndStep;

final class WindowSpecificationImpl extends AbstractQueryPart implements WindowSpecificationPartitionByStep, WindowSpecificationRowsAndStep {
   private static final long serialVersionUID = 2996016924769376361L;
   private final QueryPartList<Field<?>> partitionBy = new QueryPartList();
   private final SortFieldList orderBy = new SortFieldList();
   private Integer frameStart;
   private Integer frameEnd;
   private WindowSpecificationImpl.FrameUnits frameUnits;
   private boolean partitionByOne;

   public final void accept(Context<?> ctx) {
      String glue = "";
      if (!this.partitionBy.isEmpty() && (!this.partitionByOne || !Arrays.asList(SQLDialect.CUBRID).contains(ctx.configuration().dialect()))) {
         ctx.sql(glue).keyword("partition by").sql(' ').visit(this.partitionBy);
         glue = " ";
      }

      if (!this.orderBy.isEmpty()) {
         ctx.sql(glue).keyword("order by").sql(' ').visit(this.orderBy);
         glue = " ";
      }

      if (this.frameStart != null) {
         ctx.sql(glue);
         ctx.keyword(this.frameUnits.keyword).sql(' ');
         if (this.frameEnd != null) {
            ctx.keyword("between").sql(' ');
            this.toSQLRows(ctx, this.frameStart);
            ctx.sql(' ').keyword("and").sql(' ');
            this.toSQLRows(ctx, this.frameEnd);
         } else {
            this.toSQLRows(ctx, this.frameStart);
         }

         glue = " ";
      }

   }

   private final void toSQLRows(Context<?> ctx, Integer rows) {
      if (rows == Integer.MIN_VALUE) {
         ctx.keyword("unbounded preceding");
      } else if (rows == Integer.MAX_VALUE) {
         ctx.keyword("unbounded following");
      } else if (rows < 0) {
         ctx.sql(-rows);
         ctx.sql(' ').keyword("preceding");
      } else if (rows > 0) {
         ctx.sql(rows);
         ctx.sql(' ').keyword("following");
      } else {
         ctx.keyword("current row");
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   public final WindowSpecificationPartitionByStep partitionBy(Field<?>... fields) {
      return this.partitionBy((Collection)Arrays.asList(fields));
   }

   public final WindowSpecificationPartitionByStep partitionBy(Collection<? extends Field<?>> fields) {
      this.partitionBy.addAll(fields);
      return this;
   }

   public final WindowSpecificationOrderByStep partitionByOne() {
      this.partitionByOne = true;
      this.partitionBy.add((QueryPart)DSL.one());
      return null;
   }

   public final WindowSpecificationOrderByStep orderBy(Field<?>... fields) {
      this.orderBy.addAll(fields);
      return this;
   }

   public final WindowSpecificationOrderByStep orderBy(SortField<?>... fields) {
      return this.orderBy((Collection)Arrays.asList(fields));
   }

   public final WindowSpecificationOrderByStep orderBy(Collection<? extends SortField<?>> fields) {
      this.orderBy.addAll(fields);
      return this;
   }

   public final WindowSpecificationFinalStep rowsUnboundedPreceding() {
      this.frameUnits = WindowSpecificationImpl.FrameUnits.ROWS;
      this.frameStart = Integer.MIN_VALUE;
      return this;
   }

   public final WindowSpecificationFinalStep rowsPreceding(int number) {
      this.frameUnits = WindowSpecificationImpl.FrameUnits.ROWS;
      this.frameStart = -number;
      return this;
   }

   public final WindowSpecificationFinalStep rowsCurrentRow() {
      this.frameUnits = WindowSpecificationImpl.FrameUnits.ROWS;
      this.frameStart = 0;
      return this;
   }

   public final WindowSpecificationFinalStep rowsUnboundedFollowing() {
      this.frameUnits = WindowSpecificationImpl.FrameUnits.ROWS;
      this.frameStart = Integer.MAX_VALUE;
      return this;
   }

   public final WindowSpecificationFinalStep rowsFollowing(int number) {
      this.frameUnits = WindowSpecificationImpl.FrameUnits.ROWS;
      this.frameStart = number;
      return this;
   }

   public final WindowSpecificationRowsAndStep rowsBetweenUnboundedPreceding() {
      this.rowsUnboundedPreceding();
      return this;
   }

   public final WindowSpecificationRowsAndStep rowsBetweenPreceding(int number) {
      this.rowsPreceding(number);
      return this;
   }

   public final WindowSpecificationRowsAndStep rowsBetweenCurrentRow() {
      this.rowsCurrentRow();
      return this;
   }

   public final WindowSpecificationRowsAndStep rowsBetweenUnboundedFollowing() {
      this.rowsUnboundedFollowing();
      return this;
   }

   public final WindowSpecificationRowsAndStep rowsBetweenFollowing(int number) {
      this.rowsFollowing(number);
      return this;
   }

   public final WindowSpecificationFinalStep rangeUnboundedPreceding() {
      this.frameUnits = WindowSpecificationImpl.FrameUnits.RANGE;
      this.frameStart = Integer.MIN_VALUE;
      return this;
   }

   public final WindowSpecificationFinalStep rangePreceding(int number) {
      this.frameUnits = WindowSpecificationImpl.FrameUnits.RANGE;
      this.frameStart = -number;
      return this;
   }

   public final WindowSpecificationFinalStep rangeCurrentRow() {
      this.frameUnits = WindowSpecificationImpl.FrameUnits.RANGE;
      this.frameStart = 0;
      return this;
   }

   public final WindowSpecificationFinalStep rangeUnboundedFollowing() {
      this.frameUnits = WindowSpecificationImpl.FrameUnits.RANGE;
      this.frameStart = Integer.MAX_VALUE;
      return this;
   }

   public final WindowSpecificationFinalStep rangeFollowing(int number) {
      this.frameUnits = WindowSpecificationImpl.FrameUnits.RANGE;
      this.frameStart = number;
      return this;
   }

   public final WindowSpecificationRowsAndStep rangeBetweenUnboundedPreceding() {
      this.rangeUnboundedPreceding();
      return this;
   }

   public final WindowSpecificationRowsAndStep rangeBetweenPreceding(int number) {
      this.rangePreceding(number);
      return this;
   }

   public final WindowSpecificationRowsAndStep rangeBetweenCurrentRow() {
      this.rangeCurrentRow();
      return this;
   }

   public final WindowSpecificationRowsAndStep rangeBetweenUnboundedFollowing() {
      this.rangeUnboundedFollowing();
      return this;
   }

   public final WindowSpecificationRowsAndStep rangeBetweenFollowing(int number) {
      this.rangeFollowing(number);
      return this;
   }

   public final WindowSpecificationFinalStep andUnboundedPreceding() {
      this.frameEnd = Integer.MIN_VALUE;
      return this;
   }

   public final WindowSpecificationFinalStep andPreceding(int number) {
      this.frameEnd = -number;
      return this;
   }

   public final WindowSpecificationFinalStep andCurrentRow() {
      this.frameEnd = 0;
      return this;
   }

   public final WindowSpecificationFinalStep andUnboundedFollowing() {
      this.frameEnd = Integer.MAX_VALUE;
      return this;
   }

   public final WindowSpecificationFinalStep andFollowing(int number) {
      this.frameEnd = number;
      return this;
   }

   static enum FrameUnits {
      ROWS("rows"),
      RANGE("range");

      private final String keyword;

      private FrameUnits(String keyword) {
         this.keyword = keyword;
      }
   }
}
