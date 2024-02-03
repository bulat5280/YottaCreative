package org.jooq.impl;

import java.sql.Date;
import java.sql.Time;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.QueryPart;

final class DateOrTime<T> extends AbstractFunction<T> {
   private static final long serialVersionUID = -6729613078727690134L;
   private final Field<?> field;

   DateOrTime(Field<?> field, DataType<T> dataType) {
      super(name(dataType), dataType, field);
      this.field = field;
   }

   private static String name(DataType<?> dataType) {
      return dataType.getType() == Date.class ? "date" : (dataType.getType() == Time.class ? "time" : "timestamp");
   }

   final QueryPart getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case MYSQL:
      case MARIADB:
         return DSL.field("{" + name(this.getDataType()) + "}({0})", this.getDataType(), this.field);
      case SQLITE:
         String name = this.getDataType().getType() == Date.class ? "date" : (this.getDataType().getType() == Time.class ? "time" : "datetime");
         return DSL.field("{0}({1})", this.getDataType(), DSL.keyword(name), this.field);
      default:
         return this.field.cast(this.getDataType());
      }
   }
}
