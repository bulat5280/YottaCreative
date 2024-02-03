package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.QueryPart;

final class TruncDate<T> extends AbstractFunction<T> {
   private static final long serialVersionUID = -4617792768119885313L;
   private final Field<T> date;
   private final DatePart part;

   TruncDate(Field<T> date, DatePart part) {
      super("trunc", date.getDataType());
      this.date = date;
      this.part = part;
   }

   final QueryPart getFunction0(Configuration configuration) {
      String keyword = null;
      String format = null;
      switch(configuration.family()) {
      case CUBRID:
      case HSQLDB:
         switch(this.part) {
         case YEAR:
            keyword = "YY";
            break;
         case MONTH:
            keyword = "MM";
            break;
         case DAY:
            keyword = "DD";
            break;
         case HOUR:
            keyword = "HH";
            break;
         case MINUTE:
            keyword = "MI";
            break;
         case SECOND:
            keyword = "SS";
            break;
         default:
            this.throwUnsupported();
         }

         return DSL.field("{trunc}({0}, {1})", this.getDataType(), this.date, DSL.inline(keyword));
      case H2:
         switch(this.part) {
         case YEAR:
            format = "yyyy";
            break;
         case MONTH:
            format = "yyyy-MM";
            break;
         case DAY:
            format = "yyyy-MM-dd";
            break;
         case HOUR:
            format = "yyyy-MM-dd HH";
            break;
         case MINUTE:
            format = "yyyy-MM-dd HH:mm";
            break;
         case SECOND:
            format = "yyyy-MM-dd HH:mm:ss";
            break;
         default:
            this.throwUnsupported();
         }

         return DSL.field("{parsedatetime}({formatdatetime}({0}, {1}), {1})", this.getDataType(), this.date, DSL.inline(format));
      case POSTGRES:
         switch(this.part) {
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

         return DSL.field("{date_trunc}({0}, {1})", this.getDataType(), DSL.inline(keyword), this.date);
      default:
         return DSL.field("{trunc}({0}, {1})", this.getDataType(), this.date, DSL.inline(this.part.name()));
      }
   }

   private final void throwUnsupported() {
      throw new UnsupportedOperationException("Unknown date part : " + this.part);
   }
}
