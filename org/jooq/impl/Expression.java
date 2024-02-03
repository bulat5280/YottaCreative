package org.jooq.impl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.exception.DataTypeException;
import org.jooq.types.DayToSecond;
import org.jooq.types.Interval;
import org.jooq.types.YearToMonth;

final class Expression<T> extends AbstractFunction<T> {
   private static final long serialVersionUID = -5522799070693019771L;
   private final Field<T> lhs;
   private final QueryPartList<Field<?>> rhs;
   private final ExpressionOperator operator;

   Expression(ExpressionOperator operator, Field<T> lhs, Field<?>... rhs) {
      super(operator.toSQL(), lhs.getDataType(), Tools.combine(lhs, rhs));
      this.operator = operator;
      this.lhs = lhs;
      this.rhs = new QueryPartList(rhs);
   }

   public final Field<T> add(Field<?> value) {
      if (this.operator == ExpressionOperator.ADD) {
         this.rhs.add((QueryPart)value);
         return this;
      } else {
         return super.add(value);
      }
   }

   public final Field<T> mul(Field<? extends Number> value) {
      if (this.operator == ExpressionOperator.MULTIPLY) {
         this.rhs.add((QueryPart)value);
         return this;
      } else {
         return super.mul(value);
      }
   }

   final Field<T> getFunction0(Configuration configuration) {
      SQLDialect family = configuration.dialect().family();
      if (ExpressionOperator.BIT_AND == this.operator && Arrays.asList(SQLDialect.H2, SQLDialect.HSQLDB).contains(family)) {
         return DSL.function("bitand", this.getDataType(), this.getArguments());
      } else if (ExpressionOperator.BIT_AND == this.operator && SQLDialect.FIREBIRD == family) {
         return DSL.function("bin_and", this.getDataType(), this.getArguments());
      } else if (ExpressionOperator.BIT_XOR == this.operator && Arrays.asList(SQLDialect.H2, SQLDialect.HSQLDB).contains(family)) {
         return DSL.function("bitxor", this.getDataType(), this.getArguments());
      } else if (ExpressionOperator.BIT_XOR == this.operator && SQLDialect.FIREBIRD == family) {
         return DSL.function("bin_xor", this.getDataType(), this.getArguments());
      } else if (ExpressionOperator.BIT_OR == this.operator && Arrays.asList(SQLDialect.H2, SQLDialect.HSQLDB).contains(family)) {
         return DSL.function("bitor", this.getDataType(), this.getArguments());
      } else if (ExpressionOperator.BIT_OR == this.operator && SQLDialect.FIREBIRD == family) {
         return DSL.function("bin_or", this.getDataType(), this.getArguments());
      } else if (ExpressionOperator.BIT_XOR == this.operator && Arrays.asList(SQLDialect.SQLITE).contains(family)) {
         return DSL.bitAnd(DSL.bitNot(DSL.bitAnd(this.lhsAsNumber(), this.rhsAsNumber())), DSL.bitOr(this.lhsAsNumber(), this.rhsAsNumber()));
      } else if (ExpressionOperator.SHL == this.operator && Arrays.asList(SQLDialect.H2, SQLDialect.HSQLDB).contains(family)) {
         return this.lhs.mul(DSL.power((Field)DSL.two(), (Field)this.rhsAsNumber()).cast(this.lhs));
      } else if (ExpressionOperator.SHR == this.operator && Arrays.asList(SQLDialect.H2, SQLDialect.HSQLDB).contains(family)) {
         return this.lhs.div(DSL.power((Field)DSL.two(), (Field)this.rhsAsNumber()).cast(this.lhs));
      } else if (ExpressionOperator.SHL == this.operator && SQLDialect.FIREBIRD == family) {
         return DSL.function("bin_shl", this.getDataType(), this.getArguments());
      } else if (ExpressionOperator.SHR == this.operator && SQLDialect.FIREBIRD == family) {
         return DSL.function("bin_shr", this.getDataType(), this.getArguments());
      } else if (ExpressionOperator.BIT_NAND == this.operator) {
         return DSL.bitNot(DSL.bitAnd(this.lhsAsNumber(), this.rhsAsNumber()));
      } else if (ExpressionOperator.BIT_NOR == this.operator) {
         return DSL.bitNot(DSL.bitOr(this.lhsAsNumber(), this.rhsAsNumber()));
      } else if (ExpressionOperator.BIT_XNOR == this.operator) {
         return DSL.bitNot(DSL.bitXor(this.lhsAsNumber(), this.rhsAsNumber()));
      } else {
         return (Field)(!Arrays.asList(ExpressionOperator.ADD, ExpressionOperator.SUBTRACT).contains(this.operator) || !this.lhs.getDataType().isDateTime() || !((Field)this.rhs.get(0)).getDataType().isNumeric() && !((Field)this.rhs.get(0)).getDataType().isInterval() ? new Expression.DefaultExpression() : new Expression.DateExpression());
      }
   }

   private final Field<Number> lhsAsNumber() {
      return this.lhs;
   }

   private final Field<Number> rhsAsNumber() {
      return (Field)this.rhs.get(0);
   }

   private final YearToMonth rhsAsYTM() {
      try {
         return (YearToMonth)((Param)this.rhs.get(0)).getValue();
      } catch (ClassCastException var2) {
         throw new DataTypeException("Cannot perform datetime arithmetic with a non-numeric, non-interval data type on the right hand side of the expression: " + this.rhs.get(0));
      }
   }

   private final DayToSecond rhsAsDTS() {
      try {
         return (DayToSecond)((Param)this.rhs.get(0)).getValue();
      } catch (ClassCastException var2) {
         throw new DataTypeException("Cannot perform datetime arithmetic with a non-numeric, non-interval data type on the right hand side of the expression: " + this.rhs.get(0));
      }
   }

   private final Interval rhsAsInterval() {
      try {
         return (Interval)((Param)this.rhs.get(0)).getValue();
      } catch (ClassCastException var2) {
         throw new DataTypeException("Cannot perform datetime arithmetic with a non-numeric, non-interval data type on the right hand side of the expression: " + this.rhs.get(0));
      }
   }

   private class DefaultExpression extends AbstractField<T> {
      private static final long serialVersionUID = -5105004317793995419L;

      private DefaultExpression() {
         super(Expression.this.operator.toSQL(), Expression.this.lhs.getDataType());
      }

      public final void accept(Context<?> ctx) {
         String op = Expression.this.operator.toSQL();
         if (Expression.this.operator == ExpressionOperator.BIT_XOR && Arrays.asList(SQLDialect.POSTGRES).contains(ctx.family())) {
            op = "#";
         }

         ctx.sql('(');
         ctx.visit(Expression.this.lhs);
         Iterator var3 = Expression.this.rhs.iterator();

         while(var3.hasNext()) {
            Field<?> field = (Field)var3.next();
            ctx.sql(' ').sql(op).sql(' ').visit(field);
         }

         ctx.sql(')');
      }

      // $FF: synthetic method
      DefaultExpression(Object x1) {
         this();
      }
   }

   private class DateExpression extends AbstractFunction<T> {
      private static final long serialVersionUID = 3160679741902222262L;

      DateExpression() {
         super(Expression.this.operator.toSQL(), Expression.this.lhs.getDataType());
      }

      final Field<T> getFunction0(Configuration configuration) {
         return ((Field)Expression.this.rhs.get(0)).getDataType().isInterval() ? this.getIntervalExpression(configuration) : this.getNumberExpression(configuration);
      }

      private final Field<T> getIntervalExpression(Configuration configuration) {
         SQLDialect dialect = configuration.dialect();
         int sign = Expression.this.operator == ExpressionOperator.ADD ? 1 : -1;
         switch(dialect.family()) {
         case CUBRID:
         case MARIADB:
         case MYSQL:
            Interval interval = Expression.this.rhsAsInterval();
            if (Expression.this.operator == ExpressionOperator.SUBTRACT) {
               interval = interval.neg();
            }

            if (((Field)Expression.this.rhs.get(0)).getType() == YearToMonth.class) {
               return DSL.field("{date_add}({0}, {interval} {1} {year_month})", this.getDataType(), Expression.this.lhs, Tools.field(interval, (Class)String.class));
            } else {
               if (dialect == SQLDialect.CUBRID) {
                  return DSL.field("{date_add}({0}, {interval} {1} {day_millisecond})", this.getDataType(), Expression.this.lhs, Tools.field(interval, (Class)String.class));
               }

               return DSL.field("{date_add}({0}, {interval} {1} {day_microsecond})", this.getDataType(), Expression.this.lhs, Tools.field(interval, (Class)String.class));
            }
         case DERBY:
         case HSQLDB:
            Field result;
            if (((Field)Expression.this.rhs.get(0)).getType() == YearToMonth.class) {
               result = DSL.field("{fn {timestampadd}({sql_tsi_month}, {0}, {1}) }", this.getDataType(), DSL.val(sign * Expression.this.rhsAsYTM().intValue()), Expression.this.lhs);
            } else {
               result = DSL.field("{fn {timestampadd}({sql_tsi_second}, {0}, {1}) }", this.getDataType(), DSL.val((long)sign * (long)Expression.this.rhsAsDTS().getTotalSeconds()), Expression.this.lhs);
            }

            return this.castNonTimestamps(configuration, result);
         case FIREBIRD:
            if (((Field)Expression.this.rhs.get(0)).getType() == YearToMonth.class) {
               return DSL.field("{dateadd}({month}, {0}, {1})", this.getDataType(), DSL.val(sign * Expression.this.rhsAsYTM().intValue()), Expression.this.lhs);
            }

            return DSL.field("{dateadd}({millisecond}, {0}, {1})", this.getDataType(), DSL.val((long)sign * (long)Expression.this.rhsAsDTS().getTotalMilli()), Expression.this.lhs);
         case H2:
            if (((Field)Expression.this.rhs.get(0)).getType() == YearToMonth.class) {
               return DSL.field("{dateadd}('month', {0}, {1})", this.getDataType(), DSL.val(sign * Expression.this.rhsAsYTM().intValue()), Expression.this.lhs);
            }

            return DSL.field("{dateadd}('ms', {0}, {1})", this.getDataType(), DSL.val((long)sign * (long)Expression.this.rhsAsDTS().getTotalMilli()), Expression.this.lhs);
         case SQLITE:
            boolean ytm = ((Field)Expression.this.rhs.get(0)).getType() == YearToMonth.class;
            Field<?> intervalxx = DSL.val(ytm ? (double)Expression.this.rhsAsYTM().intValue() : Expression.this.rhsAsDTS().getTotalSeconds());
            if (sign < 0) {
               intervalxx = ((Field)intervalxx).neg();
            }

            Field<?> intervalx = ((Field)intervalxx).concat(DSL.inline(ytm ? " months" : " seconds"));
            return DSL.field("{datetime}({0}, {1})", this.getDataType(), Expression.this.lhs, intervalx);
         case POSTGRES:
         default:
            return Expression.this.new DefaultExpression();
         }
      }

      private final Field<T> castNonTimestamps(Configuration configuration, Field<T> result) {
         return this.getDataType().getType() != Timestamp.class ? DSL.field("{cast}({0} {as} " + this.getDataType().getCastTypeName(configuration) + ")", this.getDataType(), result) : result;
      }

      private final Field<T> getNumberExpression(Configuration configuration) {
         switch(configuration.family()) {
         case CUBRID:
         case MARIADB:
         case MYSQL:
            if (Expression.this.operator == ExpressionOperator.ADD) {
               return DSL.field("{date_add}({0}, {interval} {1} {day})", this.getDataType(), Expression.this.lhs, Expression.this.rhsAsNumber());
            }

            return DSL.field("{date_add}({0}, {interval} {1} {day})", this.getDataType(), Expression.this.lhs, Expression.this.rhsAsNumber().neg());
         case DERBY:
            Field result;
            if (Expression.this.operator == ExpressionOperator.ADD) {
               result = DSL.field("{fn {timestampadd}({sql_tsi_day}, {0}, {1}) }", this.getDataType(), Expression.this.rhsAsNumber(), Expression.this.lhs);
            } else {
               result = DSL.field("{fn {timestampadd}({sql_tsi_day}, {0}, {1}) }", this.getDataType(), Expression.this.rhsAsNumber().neg(), Expression.this.lhs);
            }

            return this.castNonTimestamps(configuration, result);
         case HSQLDB:
            if (Expression.this.operator == ExpressionOperator.ADD) {
               return Expression.this.lhs.add(DSL.field("{0} day", Expression.this.rhsAsNumber()));
            }

            return Expression.this.lhs.sub(DSL.field("{0} day", Expression.this.rhsAsNumber()));
         case FIREBIRD:
            if (Expression.this.operator == ExpressionOperator.ADD) {
               return DSL.field("{dateadd}(day, {0}, {1})", this.getDataType(), Expression.this.rhsAsNumber(), Expression.this.lhs);
            }

            return DSL.field("{dateadd}(day, {0}, {1})", this.getDataType(), Expression.this.rhsAsNumber().neg(), Expression.this.lhs);
         case H2:
         default:
            return Expression.this.new DefaultExpression();
         case SQLITE:
            if (Expression.this.operator == ExpressionOperator.ADD) {
               return DSL.field("{datetime}({0}, {1})", this.getDataType(), Expression.this.lhs, Expression.this.rhsAsNumber().concat(DSL.inline(" day")));
            }

            return DSL.field("{datetime}({0}, {1})", this.getDataType(), Expression.this.lhs, Expression.this.rhsAsNumber().neg().concat(DSL.inline(" day")));
         case POSTGRES:
            return Expression.this.operator == ExpressionOperator.ADD ? new DateAdd(Expression.this.lhs, Expression.this.rhsAsNumber(), DatePart.DAY) : new DateAdd(Expression.this.lhs, Expression.this.rhsAsNumber().neg(), DatePart.DAY);
         }
      }
   }
}
