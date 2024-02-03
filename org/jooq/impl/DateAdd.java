package org.jooq.impl;

import java.util.Date;
import org.jooq.Configuration;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.QueryPart;

final class DateAdd<T extends Date> extends AbstractFunction<T> {
   private static final long serialVersionUID = -19593015886723235L;
   private final Field<T> date;
   private final Field<? extends Number> interval;
   private final DatePart datePart;

   DateAdd(Field<T> date, Field<? extends Number> interval, DatePart datePart) {
      super("dateadd", date.getDataType());
      this.date = date;
      this.interval = interval;
      this.datePart = datePart;
   }

   final QueryPart getFunction0(Configuration configuration) {
      String keyword = null;
      switch(configuration.family()) {
      case CUBRID:
      case MARIADB:
      case MYSQL:
         switch(this.datePart) {
         case YEAR:
            keyword = "year";
            break;
         case MONTH:
            keyword = "month";
            break;
         case DAY:
            keyword = "day";
            break;
         case HOUR:
            keyword = "hour";
            break;
         case MINUTE:
            keyword = "minute";
            break;
         case SECOND:
            keyword = "second";
            break;
         default:
            this.throwUnsupported();
         }

         return DSL.field("{date_add}({0}, {interval} {1} {2})", this.getDataType(), this.date, this.interval, DSL.keyword(keyword));
      case DERBY:
      case HSQLDB:
         switch(this.datePart) {
         case YEAR:
            keyword = "sql_tsi_year";
            break;
         case MONTH:
            keyword = "sql_tsi_month";
            break;
         case DAY:
            keyword = "sql_tsi_day";
            break;
         case HOUR:
            keyword = "sql_tsi_hour";
            break;
         case MINUTE:
            keyword = "sql_tsi_minute";
            break;
         case SECOND:
            keyword = "sql_tsi_second";
            break;
         default:
            this.throwUnsupported();
         }

         return DSL.field("{fn {timestampadd}({0}, {1}, {2}) }", this.getDataType(), DSL.keyword(keyword), this.interval, this.date);
      case FIREBIRD:
         switch(this.datePart) {
         case YEAR:
            keyword = "year";
            break;
         case MONTH:
            keyword = "month";
            break;
         case DAY:
            keyword = "day";
            break;
         case HOUR:
            keyword = "hour";
            break;
         case MINUTE:
            keyword = "minute";
            break;
         case SECOND:
            keyword = "second";
            break;
         default:
            this.throwUnsupported();
         }

         return DSL.field("{dateadd}({0}, {1}, {2})", this.getDataType(), DSL.keyword(keyword), this.interval, this.date);
      case H2:
         switch(this.datePart) {
         case YEAR:
            keyword = "year";
            break;
         case MONTH:
            keyword = "month";
            break;
         case DAY:
            keyword = "day";
            break;
         case HOUR:
            keyword = "hour";
            break;
         case MINUTE:
            keyword = "minute";
            break;
         case SECOND:
            keyword = "second";
            break;
         default:
            this.throwUnsupported();
         }

         return DSL.field("{dateadd}({0}, {1}, {2})", this.getDataType(), DSL.inline(keyword), this.interval, this.date);
      case POSTGRES:
         switch(this.datePart) {
         case YEAR:
            keyword = " year";
            break;
         case MONTH:
            keyword = " month";
            break;
         case DAY:
            keyword = " day";
            break;
         case HOUR:
            keyword = " hour";
            break;
         case MINUTE:
            keyword = " minute";
            break;
         case SECOND:
            keyword = " second";
            break;
         default:
            this.throwUnsupported();
         }

         if (this.getDataType().getType() == java.sql.Date.class) {
            return DSL.field("({0} + ({1} || {2})::interval)::date", this.getDataType(), this.date, this.interval, DSL.inline(keyword));
         }

         return DSL.field("({0} + ({1} || {2})::interval)", this.getDataType(), this.date, this.interval, DSL.inline(keyword));
      case SQLITE:
         switch(this.datePart) {
         case YEAR:
            keyword = " year";
            break;
         case MONTH:
            keyword = " month";
            break;
         case DAY:
            keyword = " day";
            break;
         case HOUR:
            keyword = " hour";
            break;
         case MINUTE:
            keyword = " minute";
            break;
         case SECOND:
            keyword = " second";
            break;
         default:
            this.throwUnsupported();
         }

         return DSL.field("{datetime}({0}, '+' || {1} || {2})", this.getDataType(), this.date, this.interval, DSL.inline(keyword));
      default:
         return null;
      }
   }

   private final void throwUnsupported() {
      throw new UnsupportedOperationException("Unknown date part : " + this.datePart);
   }
}
