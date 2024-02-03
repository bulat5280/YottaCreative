package org.jooq.impl;

import java.sql.Date;
import org.jooq.Configuration;
import org.jooq.Field;

final class DateDiff extends AbstractFunction<Integer> {
   private static final long serialVersionUID = -4813228000332771961L;
   private final Field<Date> date1;
   private final Field<Date> date2;

   DateDiff(Field<Date> date1, Field<Date> date2) {
      super("datediff", SQLDataType.INTEGER, date1, date2);
      this.date1 = date1;
      this.date2 = date2;
   }

   final Field<Integer> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case MARIADB:
      case MYSQL:
         return DSL.function("datediff", this.getDataType(), this.date1, this.date2);
      case DERBY:
         return DSL.field("{fn {timestampdiff}({sql_tsi_day}, {0}, {1}) }", this.getDataType(), this.date2, this.date1);
      case FIREBIRD:
         return DSL.field("{datediff}(day, {0}, {1})", this.getDataType(), this.date2, this.date1);
      case H2:
      case HSQLDB:
         return DSL.field("{datediff}('day', {0}, {1})", this.getDataType(), this.date2, this.date1);
      case SQLITE:
         return DSL.field("({strftime}('%s', {0}) - {strftime}('%s', {1})) / 86400", this.getDataType(), this.date1, this.date2);
      case CUBRID:
      case POSTGRES:
         return DSL.field("({0} - {1})", this.getDataType(), this.date1, this.date2);
      default:
         return this.date1.sub(this.date2).cast(Integer.class);
      }
   }
}
