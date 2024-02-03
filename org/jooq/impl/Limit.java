package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.RenderContext;
import org.jooq.conf.ParamType;

final class Limit extends AbstractQueryPart {
   private static final long serialVersionUID = 2053741242981425602L;
   private static final Field<Integer> ZERO = DSL.zero();
   private static final Field<Integer> ONE = DSL.one();
   private Field<Integer> numberOfRows;
   private Field<Integer> numberOfRowsOrMax = DSL.inline(Integer.MAX_VALUE);
   private Field<Integer> offset;
   private Field<Integer> offsetOrZero;
   private Field<Integer> offsetPlusOne;
   private boolean rendersParams;

   Limit() {
      this.offsetOrZero = ZERO;
      this.offsetPlusOne = ONE;
   }

   public final void accept(Context<?> context) {
      ParamType paramType = context.paramType();
      RenderContext.CastMode castMode = context.castMode();
      switch(context.dialect()) {
      case H2:
      case MARIADB:
      case MYSQL:
      case SQLITE:
         context.castMode(RenderContext.CastMode.NEVER).formatSeparator().keyword("limit").sql(' ').visit(this.numberOfRowsOrMax);
         if (!this.offsetZero()) {
            context.formatSeparator().keyword("offset").sql(' ').visit(this.offsetOrZero);
         }

         context.castMode(castMode);
         break;
      case HSQLDB:
      case POSTGRES:
      case POSTGRES_9_3:
      case POSTGRES_9_4:
      case POSTGRES_9_5:
         context.castMode(RenderContext.CastMode.NEVER);
         if (!this.limitZero()) {
            context.formatSeparator().keyword("limit").sql(' ').visit(this.numberOfRows);
         }

         if (!this.offsetZero()) {
            context.formatSeparator().keyword("offset").sql(' ').visit(this.offsetOrZero);
         }

         context.castMode(castMode);
         break;
      case CUBRID:
         context.castMode(RenderContext.CastMode.NEVER).formatSeparator().keyword("limit").sql(' ').visit(this.offsetOrZero).sql(", ").visit(this.numberOfRowsOrMax).castMode(castMode);
         break;
      case FIREBIRD:
      case FIREBIRD_2_5:
      case FIREBIRD_3_0:
         context.castMode(RenderContext.CastMode.NEVER).formatSeparator().keyword("rows").sql(' ').visit(this.getLowerRownum().add((Field)DSL.inline(1, (DataType)SQLDataType.INTEGER))).sql(' ').keyword("to").sql(' ').visit(this.getUpperRownum()).castMode(castMode);
         break;
      case DERBY:
         context.castMode(RenderContext.CastMode.NEVER).formatSeparator().keyword("offset").sql(' ').visit(this.offsetOrZero).sql(' ').keyword("rows");
         if (!this.limitZero()) {
            context.sql(' ').keyword("fetch next").sql(' ').visit(this.numberOfRows).sql(' ').keyword("rows only");
         }

         context.castMode(castMode);
         break;
      default:
         context.castMode(RenderContext.CastMode.NEVER).formatSeparator().keyword("limit").sql(' ').visit(this.numberOfRows);
         if (!this.offsetZero()) {
            context.sql(' ').keyword("offset").sql(' ').visit(this.offsetOrZero);
         }

         context.castMode(castMode);
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   final boolean limitZero() {
      return this.numberOfRows == null;
   }

   final boolean offsetZero() {
      return this.offset == null;
   }

   final Field<Integer> getLowerRownum() {
      return this.offsetOrZero;
   }

   final Field<Integer> getUpperRownum() {
      return this.offsetOrZero.add(this.numberOfRowsOrMax);
   }

   final boolean isApplicable() {
      return this.offset != null || this.numberOfRows != null;
   }

   final boolean rendersParams() {
      return this.rendersParams;
   }

   final void setOffset(int offset) {
      if (offset != 0) {
         this.offset = DSL.val(offset, (DataType)SQLDataType.INTEGER);
         this.offsetOrZero = this.offset;
         this.offsetPlusOne = DSL.val(offset + 1, (DataType)SQLDataType.INTEGER);
      }

   }

   final void setOffset(Param<Integer> offset) {
      this.offset = offset;
      this.offsetOrZero = offset;
      this.rendersParams = true;
   }

   final void setNumberOfRows(int numberOfRows) {
      this.numberOfRows = DSL.val(numberOfRows, (DataType)SQLDataType.INTEGER);
      this.numberOfRowsOrMax = this.numberOfRows;
   }

   final void setNumberOfRows(Param<Integer> numberOfRows) {
      this.numberOfRows = numberOfRows;
      this.numberOfRowsOrMax = numberOfRows;
      this.rendersParams = true;
   }
}
