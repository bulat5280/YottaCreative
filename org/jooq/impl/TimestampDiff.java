package org.jooq.impl;

import java.sql.Timestamp;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.types.DayToSecond;

final class TimestampDiff extends AbstractFunction<DayToSecond> {
   private static final long serialVersionUID = -4813228000332771961L;
   private final Field<Timestamp> timestamp1;
   private final Field<Timestamp> timestamp2;

   TimestampDiff(Field<Timestamp> timestamp1, Field<Timestamp> timestamp2) {
      super("timestampdiff", SQLDataType.INTERVALDAYTOSECOND, timestamp1, timestamp2);
      this.timestamp1 = timestamp1;
      this.timestamp2 = timestamp2;
   }

   final Field<DayToSecond> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case POSTGRES:
         return DSL.field("({0} - {1})", this.getDataType(), this.timestamp1, this.timestamp2);
      case CUBRID:
         return this.timestamp1.sub(this.timestamp2);
      case DERBY:
         return DSL.field("1000 * {fn {timestampdiff}({sql_tsi_second}, {0}, {1}) }", SQLDataType.INTEGER, this.timestamp2, this.timestamp1);
      case FIREBIRD:
         return DSL.field("{datediff}(millisecond, {0}, {1})", this.getDataType(), this.timestamp2, this.timestamp1);
      case H2:
      case HSQLDB:
         return DSL.field("{datediff}('ms', {0}, {1})", this.getDataType(), this.timestamp2, this.timestamp1);
      case MARIADB:
      case MYSQL:
         return DSL.field("{timestampdiff}(microsecond, {0}, {1}) / 1000", this.getDataType(), this.timestamp2, this.timestamp1);
      case SQLITE:
         return DSL.field("({strftime}('%s', {0}) - {strftime}('%s', {1})) * 1000", this.getDataType(), this.timestamp1, this.timestamp2);
      default:
         return this.timestamp1.sub(this.timestamp2).cast(DayToSecond.class);
      }
   }
}
