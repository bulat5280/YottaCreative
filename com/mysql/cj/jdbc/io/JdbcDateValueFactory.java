package com.mysql.cj.jdbc.io;

import com.mysql.cj.api.WarningListener;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.DataReadException;
import com.mysql.cj.core.io.DefaultValueFactory;
import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class JdbcDateValueFactory extends DefaultValueFactory<Date> {
   private TimeZone tz;
   private WarningListener warningListener;
   private Calendar cal;

   public JdbcDateValueFactory(TimeZone tz) {
      this.tz = tz;
      this.cal = Calendar.getInstance(this.tz, Locale.US);
      this.cal.set(14, 0);
      this.cal.setLenient(false);
   }

   public JdbcDateValueFactory(TimeZone tz, WarningListener warningListener) {
      this(tz);
      this.warningListener = warningListener;
   }

   public Date createFromDate(int year, int month, int day) {
      synchronized(this.cal) {
         if (year == 0 && month == 0 && day == 0) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
         } else {
            this.cal.set(year, month - 1, day, 0, 0, 0);
            long ms = this.cal.getTimeInMillis();
            return new Date(ms);
         }
      }
   }

   public Date createFromTimestamp(int year, int month, int day, int hours, int minutes, int seconds, int nanos) {
      if (this.warningListener != null) {
         this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[]{"java.sql.Date"}));
      }

      return this.createFromDate(year, month, day);
   }

   public String getTargetTypeName() {
      return Date.class.getName();
   }
}
