package com.mysql.cj.core.io;

import com.mysql.cj.api.io.ValueFactory;

public class ZeroDateTimeToNullValueFactory<T> extends BaseDecoratingValueFactory<T> {
   public ZeroDateTimeToNullValueFactory(ValueFactory<T> targetVf) {
      super(targetVf);
   }

   public T createFromDate(int year, int month, int day) {
      return year + month + day == 0 ? null : this.targetVf.createFromDate(year, month, day);
   }

   public T createFromTime(int hours, int minutes, int seconds, int nanos) {
      return hours + minutes + seconds + nanos == 0 ? null : this.targetVf.createFromTime(hours, minutes, seconds, nanos);
   }

   public T createFromTimestamp(int year, int month, int day, int hours, int minutes, int seconds, int nanos) {
      return year + month + day + hours + minutes + seconds + nanos == 0 ? null : this.targetVf.createFromTimestamp(year, month, day, hours, minutes, seconds, nanos);
   }
}
