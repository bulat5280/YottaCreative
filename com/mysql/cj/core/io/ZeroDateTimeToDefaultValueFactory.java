package com.mysql.cj.core.io;

import com.mysql.cj.api.io.ValueFactory;

public class ZeroDateTimeToDefaultValueFactory<T> extends BaseDecoratingValueFactory<T> {
   public ZeroDateTimeToDefaultValueFactory(ValueFactory<T> targetVf) {
      super(targetVf);
   }

   public T createFromDate(int year, int month, int day) {
      return year + month + day == 0 ? this.targetVf.createFromDate(1, 1, 1) : this.targetVf.createFromDate(year, month, day);
   }

   public T createFromTime(int hours, int minutes, int seconds, int nanos) {
      return hours + minutes + seconds + nanos == 0 ? this.targetVf.createFromTime(0, 0, 0, 0) : this.targetVf.createFromTime(hours, minutes, seconds, nanos);
   }

   public T createFromTimestamp(int year, int month, int day, int hours, int minutes, int seconds, int nanos) {
      return year + month + day + hours + minutes + seconds + nanos == 0 ? this.targetVf.createFromTimestamp(1, 1, 1, 0, 0, 0, 0) : this.targetVf.createFromTimestamp(year, month, day, hours, minutes, seconds, nanos);
   }
}
