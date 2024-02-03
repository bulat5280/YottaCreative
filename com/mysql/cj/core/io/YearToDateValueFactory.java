package com.mysql.cj.core.io;

import com.mysql.cj.api.io.ValueFactory;

public class YearToDateValueFactory<T> extends BaseDecoratingValueFactory<T> {
   public YearToDateValueFactory(ValueFactory<T> targetVf) {
      super(targetVf);
   }

   public T createFromLong(long year) {
      if (year < 100L) {
         if (year <= 69L) {
            year += 100L;
         }

         year += 1900L;
      }

      return this.targetVf.createFromDate((int)year, 1, 1);
   }
}
