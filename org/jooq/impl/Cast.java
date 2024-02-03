package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.RenderContext;

final class Cast<T> extends AbstractFunction<T> {
   private static final long serialVersionUID = -6776617606751542856L;
   private final Field<?> field;

   public Cast(Field<?> field, DataType<T> type) {
      super("cast", type);
      this.field = field;
   }

   private final DataType<T> getSQLDataType() {
      return this.getDataType().getSQLDataType();
   }

   final QueryPart getFunction0(Configuration configuration) {
      switch(configuration.dialect().family()) {
      case DERBY:
         return new Cast.CastDerby();
      default:
         return new Cast.Native();
      }
   }

   private class Native extends AbstractQueryPart {
      private static final long serialVersionUID = -8497561014419483312L;

      private Native() {
      }

      public void accept(Context<?> ctx) {
         RenderContext.CastMode castMode = ctx.castMode();
         ctx.keyword("cast").sql('(').castMode(RenderContext.CastMode.NEVER).visit(Cast.this.field).castMode(castMode).sql(' ').keyword("as").sql(' ').keyword(Cast.this.getDataType(ctx.configuration()).getCastTypeName(ctx.configuration())).sql(')');
      }

      public final Clause[] clauses(Context<?> ctx) {
         return null;
      }

      // $FF: synthetic method
      Native(Object x1) {
         this();
      }
   }

   private class CastDerby extends Cast<T>.Native {
      private static final long serialVersionUID = -8737153188122391258L;

      private CastDerby() {
         super(null);
      }

      private final Field<Boolean> asDecodeNumberToBoolean() {
         return DSL.choose(Cast.this.field).when((Field)DSL.inline((int)0), (Field)DSL.inline(false)).when((Field)DSL.inline((Integer)null), (Field)DSL.inline((Boolean)null)).otherwise((Field)DSL.inline(true));
      }

      private final Field<Boolean> asDecodeVarcharToBoolean() {
         Field<String> s = Cast.this.field;
         return DSL.when(s.equal((Field)DSL.inline("0")), (Field)DSL.inline(false)).when(DSL.lower(s).equal((Field)DSL.inline("false")), (Field)DSL.inline(false)).when(DSL.lower(s).equal((Field)DSL.inline("f")), (Field)DSL.inline(false)).when(s.isNull(), (Field)DSL.inline((Boolean)null)).otherwise((Field)DSL.inline(true));
      }

      public final void accept(Context<?> ctx) {
         RenderContext.CastMode castMode = ctx.castMode();
         if (Cast.this.field.getDataType().isNumeric() && SQLDataType.VARCHAR.equals(Cast.this.getSQLDataType())) {
            ctx.keyword("trim").sql('(').keyword("cast").sql('(').keyword("cast").sql('(').castMode(RenderContext.CastMode.NEVER).visit(Cast.this.field).castMode(castMode).sql(' ').keyword("as").sql(" char(38))").sql(' ').keyword("as").sql(' ').keyword(Cast.this.getDataType(ctx.configuration()).getCastTypeName(ctx.configuration())).sql("))");
         } else if (Cast.this.field.getDataType().isString() && Arrays.asList(SQLDataType.FLOAT, SQLDataType.DOUBLE, SQLDataType.REAL).contains(Cast.this.getSQLDataType())) {
            ctx.keyword("cast").sql('(').keyword("cast").sql('(').castMode(RenderContext.CastMode.NEVER).visit(Cast.this.field).castMode(castMode).sql(' ').keyword("as").sql(' ').keyword("decimal").sql(") ").keyword("as").sql(' ').keyword(Cast.this.getDataType(ctx.configuration()).getCastTypeName(ctx.configuration())).sql(')');
         } else if (Cast.this.field.getDataType().isNumeric() && SQLDataType.BOOLEAN.equals(Cast.this.getSQLDataType())) {
            ctx.visit(this.asDecodeNumberToBoolean());
         } else if (Cast.this.field.getDataType().isString() && SQLDataType.BOOLEAN.equals(Cast.this.getSQLDataType())) {
            ctx.visit(this.asDecodeVarcharToBoolean());
         } else {
            super.accept(ctx);
         }
      }

      // $FF: synthetic method
      CastDerby(Object x1) {
         this();
      }
   }
}
