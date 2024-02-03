package org.jooq.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import org.jooq.Configuration;
import org.jooq.Field;

final class Trunc<T> extends AbstractFunction<T> {
   private static final long serialVersionUID = 4291348230758816484L;
   private final Field<T> field;
   private final Field<Integer> decimals;

   Trunc(Field<T> field, Field<Integer> decimals) {
      super("trunc", field.getDataType());
      this.field = field;
      this.decimals = decimals;
   }

   final Field<T> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case DERBY:
         Integer decimalsVal = (Integer)Tools.extractVal(this.decimals);
         Object power;
         if (decimalsVal != null) {
            power = DSL.inline(BigDecimal.TEN.pow(decimalsVal, MathContext.DECIMAL128));
         } else {
            power = DSL.power((Field)DSL.inline(BigDecimal.TEN), (Field)this.decimals);
         }

         return DSL.decode().when(this.field.sign().greaterOrEqual((Field)DSL.zero()), this.field.mul((Field)power).floor().div((Field)power)).otherwise(this.field.mul((Field)power).ceil().div((Field)power));
      case H2:
      case MARIADB:
      case MYSQL:
         return DSL.field("{truncate}({0}, {1})", this.field.getDataType(), this.field, this.decimals);
      case POSTGRES:
         return DSL.field("{trunc}({0}, {1})", SQLDataType.NUMERIC, this.field.cast(BigDecimal.class), this.decimals).cast(this.field.getDataType());
      case CUBRID:
      case HSQLDB:
      default:
         return DSL.field("{trunc}({0}, {1})", this.field.getDataType(), this.field, this.decimals);
      }
   }
}
