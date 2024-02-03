package org.apache.commons.math3.fraction;

import java.io.Serializable;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;

public abstract class AbstractFormat extends NumberFormat implements Serializable {
   private static final long serialVersionUID = -6981118387974191891L;
   private NumberFormat denominatorFormat;
   private NumberFormat numeratorFormat;

   protected AbstractFormat() {
      this(getDefaultNumberFormat());
   }

   protected AbstractFormat(NumberFormat format) {
      this(format, (NumberFormat)format.clone());
   }

   protected AbstractFormat(NumberFormat numeratorFormat, NumberFormat denominatorFormat) {
      this.numeratorFormat = numeratorFormat;
      this.denominatorFormat = denominatorFormat;
   }

   protected static NumberFormat getDefaultNumberFormat() {
      return getDefaultNumberFormat(Locale.getDefault());
   }

   protected static NumberFormat getDefaultNumberFormat(Locale locale) {
      NumberFormat nf = NumberFormat.getNumberInstance(locale);
      nf.setMaximumFractionDigits(0);
      nf.setParseIntegerOnly(true);
      return nf;
   }

   public NumberFormat getDenominatorFormat() {
      return this.denominatorFormat;
   }

   public NumberFormat getNumeratorFormat() {
      return this.numeratorFormat;
   }

   public void setDenominatorFormat(NumberFormat format) {
      if (format == null) {
         throw new NullArgumentException(LocalizedFormats.DENOMINATOR_FORMAT, new Object[0]);
      } else {
         this.denominatorFormat = format;
      }
   }

   public void setNumeratorFormat(NumberFormat format) {
      if (format == null) {
         throw new NullArgumentException(LocalizedFormats.NUMERATOR_FORMAT, new Object[0]);
      } else {
         this.numeratorFormat = format;
      }
   }

   protected static void parseAndIgnoreWhitespace(String source, ParsePosition pos) {
      parseNextCharacter(source, pos);
      pos.setIndex(pos.getIndex() - 1);
   }

   protected static char parseNextCharacter(String source, ParsePosition pos) {
      int index = pos.getIndex();
      int n = source.length();
      char ret = 0;
      if (index < n) {
         while(true) {
            char c = source.charAt(index++);
            if (!Character.isWhitespace(c) || index >= n) {
               pos.setIndex(index);
               if (index < n) {
                  ret = c;
               }
               break;
            }
         }
      }

      return ret;
   }

   public StringBuffer format(double value, StringBuffer buffer, FieldPosition position) {
      return this.format(value, buffer, position);
   }

   public StringBuffer format(long value, StringBuffer buffer, FieldPosition position) {
      return this.format(value, buffer, position);
   }
}
