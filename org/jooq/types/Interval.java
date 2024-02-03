package org.jooq.types;

import java.io.Serializable;

public interface Interval extends Serializable {
   Interval neg();

   Interval abs();

   int getSign();

   double doubleValue();

   float floatValue();

   long longValue();

   int intValue();

   byte byteValue();

   short shortValue();
}
