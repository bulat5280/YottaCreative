package org.jooq.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class YearToMonth extends Number implements Interval, Comparable<YearToMonth> {
   private static final long serialVersionUID = 1308553645456594273L;
   private static final Pattern PATTERN = Pattern.compile("(\\+|-)?(\\d+)-(\\d+)");
   private final boolean negative;
   private final int years;
   private final int months;

   public YearToMonth(int years) {
      this(years, 0, false);
   }

   public YearToMonth(int years, int months) {
      this(years, months, false);
   }

   private YearToMonth(int years, int months, boolean negative) {
      if (months >= 12) {
         years += months / 12;
         months %= 12;
      }

      this.negative = negative;
      this.years = years;
      this.months = months;
   }

   public static YearToMonth valueOf(String string) {
      if (string != null) {
         Matcher matcher = PATTERN.matcher(string);
         if (matcher.find()) {
            boolean negative = "-".equals(matcher.group(1));
            int years = Integer.parseInt(matcher.group(2));
            int months = Integer.parseInt(matcher.group(3));
            return new YearToMonth(years, months, negative);
         }
      }

      return null;
   }

   public final YearToMonth neg() {
      return new YearToMonth(this.years, this.months, !this.negative);
   }

   public final YearToMonth abs() {
      return new YearToMonth(this.years, this.months, false);
   }

   public final int getYears() {
      return this.years;
   }

   public final int getMonths() {
      return this.months;
   }

   public final int getSign() {
      return this.negative ? -1 : 1;
   }

   public final int intValue() {
      return (this.negative ? -1 : 1) * (12 * this.years + this.months);
   }

   public final long longValue() {
      return (long)this.intValue();
   }

   public final float floatValue() {
      return (float)this.intValue();
   }

   public final double doubleValue() {
      return (double)this.intValue();
   }

   public final int compareTo(YearToMonth that) {
      if (this.years < that.years) {
         return -1;
      } else if (this.years > that.years) {
         return 1;
      } else if (this.months < that.months) {
         return -1;
      } else {
         return this.months > that.months ? 1 : 0;
      }
   }

   public final int hashCode() {
      int prime = true;
      int result = 1;
      int result = 31 * result + this.months;
      result = 31 * result + this.years;
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
         YearToMonth other = (YearToMonth)obj;
         if (this.months != other.months) {
            return false;
         } else {
            return this.years == other.years;
         }
      }
   }

   public final String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.negative ? "-" : "+");
      sb.append(this.years);
      sb.append("-");
      sb.append(this.months);
      return sb.toString();
   }
}
