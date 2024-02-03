package org.jooq.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.tools.Convert;
import org.jooq.tools.StringUtils;

public final class DayToSecond extends Number implements Interval, Comparable<DayToSecond> {
   private static final long serialVersionUID = -3853596481984643811L;
   private static final Pattern PATTERN = Pattern.compile("(\\+|-)?(?:(\\d+) )?(\\d+):(\\d+):(\\d+)(?:\\.(\\d+))?");
   private final boolean negative;
   private final int days;
   private final int hours;
   private final int minutes;
   private final int seconds;
   private final int nano;

   public DayToSecond(int days) {
      this(days, 0, 0, 0, 0, false);
   }

   public DayToSecond(int days, int hours) {
      this(days, hours, 0, 0, 0, false);
   }

   public DayToSecond(int days, int hours, int minutes) {
      this(days, hours, minutes, 0, 0, false);
   }

   public DayToSecond(int days, int hours, int minutes, int seconds) {
      this(days, hours, minutes, seconds, 0, false);
   }

   public DayToSecond(int days, int hours, int minutes, int seconds, int nano) {
      this(days, hours, minutes, seconds, nano, false);
   }

   private DayToSecond(int days, int hours, int minutes, int seconds, int nano, boolean negative) {
      if (nano >= 1000000000) {
         seconds += nano / 1000000000;
         nano %= 1000000000;
      }

      if (seconds >= 60) {
         minutes += seconds / 60;
         seconds %= 60;
      }

      if (minutes >= 60) {
         hours += minutes / 60;
         minutes %= 60;
      }

      if (hours >= 24) {
         days += hours / 24;
         hours %= 24;
      }

      this.negative = negative;
      this.days = days;
      this.hours = hours;
      this.minutes = minutes;
      this.seconds = seconds;
      this.nano = nano;
   }

   public static DayToSecond valueOf(String string) {
      if (string != null) {
         try {
            return valueOf(Double.valueOf(string));
         } catch (NumberFormatException var9) {
            Matcher matcher = PATTERN.matcher(string);
            if (matcher.find()) {
               boolean negative = "-".equals(matcher.group(1));
               int days = (Integer)Convert.convert((Object)matcher.group(2), (Class)Integer.TYPE);
               int hours = (Integer)Convert.convert((Object)matcher.group(3), (Class)Integer.TYPE);
               int minutes = (Integer)Convert.convert((Object)matcher.group(4), (Class)Integer.TYPE);
               int seconds = (Integer)Convert.convert((Object)matcher.group(5), (Class)Integer.TYPE);
               int nano = (Integer)Convert.convert((Object)StringUtils.rightPad(matcher.group(6), 9, "0"), (Class)Integer.TYPE);
               return new DayToSecond(days, hours, minutes, seconds, nano, negative);
            }
         }
      }

      return null;
   }

   public static DayToSecond valueOf(double milli) {
      double abs = Math.abs(milli);
      int n = (int)(abs % 1000.0D * 1000000.0D);
      abs = Math.floor(abs / 1000.0D);
      int s = (int)(abs % 60.0D);
      abs = Math.floor(abs / 60.0D);
      int m = (int)(abs % 60.0D);
      abs = Math.floor(abs / 60.0D);
      int h = (int)(abs % 24.0D);
      abs = Math.floor(abs / 24.0D);
      int d = (int)abs;
      DayToSecond result = new DayToSecond(d, h, m, s, n);
      if (milli < 0.0D) {
         result = result.neg();
      }

      return result;
   }

   public final int intValue() {
      return (int)this.doubleValue();
   }

   public final long longValue() {
      return (long)this.doubleValue();
   }

   public final float floatValue() {
      return (float)this.doubleValue();
   }

   public final double doubleValue() {
      return this.getTotalMilli();
   }

   public final DayToSecond neg() {
      return new DayToSecond(this.days, this.hours, this.minutes, this.seconds, this.nano, !this.negative);
   }

   public final DayToSecond abs() {
      return new DayToSecond(this.days, this.hours, this.minutes, this.seconds, this.nano, false);
   }

   public final int getDays() {
      return this.days;
   }

   public final int getHours() {
      return this.hours;
   }

   public final int getMinutes() {
      return this.minutes;
   }

   public final int getSeconds() {
      return this.seconds;
   }

   public final int getMilli() {
      return this.nano / 1000000;
   }

   public final int getMicro() {
      return this.nano / 1000;
   }

   public final int getNano() {
      return this.nano;
   }

   public final double getTotalDays() {
      return (double)this.getSign() * ((double)this.nano / 8.64E13D + (double)this.seconds / 86400.0D + (double)this.minutes / 1440.0D + (double)this.hours / 24.0D + (double)this.days);
   }

   public final double getTotalHours() {
      return (double)this.getSign() * ((double)this.nano / 3.6E12D + (double)this.seconds / 3600.0D + (double)this.minutes / 60.0D + (double)this.hours + 24.0D * (double)this.days);
   }

   public final double getTotalMinutes() {
      return (double)this.getSign() * ((double)this.nano / 6.0E10D + (double)this.seconds / 60.0D + (double)this.minutes + 60.0D * (double)this.hours + 1440.0D * (double)this.days);
   }

   public final double getTotalSeconds() {
      return (double)this.getSign() * ((double)this.nano / 1.0E9D + (double)this.seconds + 60.0D * (double)this.minutes + 3600.0D * (double)this.hours + 86400.0D * (double)this.days);
   }

   public final double getTotalMilli() {
      return (double)this.getSign() * ((double)this.nano / 1000000.0D + 1000.0D * (double)this.seconds + 60000.0D * (double)this.minutes + 3600000.0D * (double)this.hours + 8.64E7D * (double)this.days);
   }

   public final double getTotalMicro() {
      return (double)this.getSign() * ((double)this.nano / 1000.0D + 1000000.0D * (double)this.seconds + 6.0E7D * (double)this.minutes + 3.6E9D * (double)this.hours + 8.64E10D * (double)this.days);
   }

   public final double getTotalNano() {
      return (double)this.getSign() * ((double)this.nano + 1.0E9D * (double)this.seconds + 6.0E10D * (double)this.minutes + 3.6E12D * (double)this.hours + 8.64E13D * (double)this.days);
   }

   public final int getSign() {
      return this.negative ? -1 : 1;
   }

   public final int compareTo(DayToSecond that) {
      if (this.days < that.days) {
         return -1;
      } else if (this.days > that.days) {
         return 1;
      } else if (this.hours < that.hours) {
         return -1;
      } else if (this.hours > that.hours) {
         return 1;
      } else if (this.minutes < that.minutes) {
         return -1;
      } else if (this.minutes > that.minutes) {
         return 1;
      } else if (this.seconds < that.seconds) {
         return -1;
      } else if (this.seconds > that.seconds) {
         return 1;
      } else if (this.nano < that.nano) {
         return -1;
      } else {
         return this.nano > that.nano ? 1 : 0;
      }
   }

   public final int hashCode() {
      int prime = true;
      int result = 1;
      int result = 31 * result + this.days;
      result = 31 * result + this.hours;
      result = 31 * result + this.minutes;
      result = 31 * result + this.nano;
      result = 31 * result + this.seconds;
      return result;
   }

   public final boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         DayToSecond other = (DayToSecond)obj;
         if (this.days != other.days) {
            return false;
         } else if (this.hours != other.hours) {
            return false;
         } else if (this.minutes != other.minutes) {
            return false;
         } else if (this.nano != other.nano) {
            return false;
         } else {
            return this.seconds == other.seconds;
         }
      }
   }

   public final String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.negative ? "-" : "+");
      sb.append(this.days);
      sb.append(" ");
      if (this.hours < 10) {
         sb.append("0");
      }

      sb.append(this.hours);
      sb.append(":");
      if (this.minutes < 10) {
         sb.append("0");
      }

      sb.append(this.minutes);
      sb.append(":");
      if (this.seconds < 10) {
         sb.append("0");
      }

      sb.append(this.seconds);
      sb.append(".");
      sb.append(StringUtils.leftPad("" + this.nano, 9, "0"));
      return sb.toString();
   }
}
