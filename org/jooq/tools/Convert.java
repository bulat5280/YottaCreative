package org.jooq.tools;

import java.io.File;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import org.jooq.Converter;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.exception.DataTypeException;
import org.jooq.tools.jdbc.MockArray;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;
import org.jooq.types.Unsigned;

public final class Convert {
   public static final Set<String> TRUE_VALUES;
   public static final Set<String> FALSE_VALUES;
   private static final Pattern UUID_PATTERN = Pattern.compile("(\\p{XDigit}{8})-?(\\p{XDigit}{4})-?(\\p{XDigit}{4})-?(\\p{XDigit}{4})-?(\\p{XDigit}{12})");

   public static final Object[] convert(Object[] values, Field<?>[] fields) {
      if (values != null) {
         Object[] result = new Object[values.length];

         for(int i = 0; i < values.length; ++i) {
            if (values[i] instanceof Field) {
               result[i] = values[i];
            } else {
               result[i] = convert(values[i], fields[i].getType());
            }
         }

         return result;
      } else {
         return null;
      }
   }

   public static final Object[] convert(Object[] values, Class<?>[] types) {
      if (values != null) {
         Object[] result = new Object[values.length];

         for(int i = 0; i < values.length; ++i) {
            if (values[i] instanceof Field) {
               result[i] = values[i];
            } else {
               result[i] = convert(values[i], types[i]);
            }
         }

         return result;
      } else {
         return null;
      }
   }

   public static final <U> U[] convertArray(Object[] from, Converter<?, ? extends U> converter) throws DataTypeException {
      if (from == null) {
         return null;
      } else {
         Object[] arrayOfT = convertArray(from, converter.fromType());
         Object[] arrayOfU = (Object[])((Object[])Array.newInstance(converter.toType(), from.length));

         for(int i = 0; i < arrayOfT.length; ++i) {
            arrayOfU[i] = convert(arrayOfT[i], converter);
         }

         return (Object[])arrayOfU;
      }
   }

   public static final Object[] convertArray(Object[] from, Class<?> toClass) throws DataTypeException {
      if (from == null) {
         return null;
      } else if (!toClass.isArray()) {
         return convertArray(from, Array.newInstance(toClass, 0).getClass());
      } else if (toClass == from.getClass()) {
         return from;
      } else {
         Class<?> toComponentType = toClass.getComponentType();
         if (from.length == 0) {
            return Arrays.copyOf(from, from.length, toClass);
         } else if (from[0] != null && from[0].getClass() == toComponentType) {
            return Arrays.copyOf(from, from.length, toClass);
         } else {
            Object[] result = (Object[])((Object[])Array.newInstance(toComponentType, from.length));

            for(int i = 0; i < from.length; ++i) {
               result[i] = convert(from[i], toComponentType);
            }

            return result;
         }
      }
   }

   public static final <U> U convert(Object from, Converter<?, ? extends U> converter) throws DataTypeException {
      return convert0(from, converter);
   }

   private static final <T, U> U convert0(Object from, Converter<T, ? extends U> converter) throws DataTypeException {
      Convert.ConvertAll<T> all = new Convert.ConvertAll(converter.fromType());
      return converter.from(all.from(from));
   }

   public static final <T> T convert(Object from, Class<? extends T> toClass) throws DataTypeException {
      return convert((Object)from, (Converter)(new Convert.ConvertAll(toClass)));
   }

   public static final <T> List<T> convert(Collection<?> collection, Class<? extends T> type) throws DataTypeException {
      return convert((Collection)collection, (Converter)(new Convert.ConvertAll(type)));
   }

   public static final <U> List<U> convert(Collection<?> collection, Converter<?, ? extends U> converter) throws DataTypeException {
      return convert0(collection, converter);
   }

   private static final <T, U> List<U> convert0(Collection<?> collection, Converter<T, ? extends U> converter) throws DataTypeException {
      Convert.ConvertAll<T> all = new Convert.ConvertAll(converter.fromType());
      List<U> result = new ArrayList(collection.size());
      Iterator var4 = collection.iterator();

      while(var4.hasNext()) {
         Object o = var4.next();
         result.add(convert(all.from(o), converter));
      }

      return result;
   }

   private Convert() {
   }

   static {
      Set<String> trueValues = new HashSet();
      Set<String> falseValues = new HashSet();
      trueValues.add("1");
      trueValues.add("1.0");
      trueValues.add("y");
      trueValues.add("Y");
      trueValues.add("yes");
      trueValues.add("YES");
      trueValues.add("true");
      trueValues.add("TRUE");
      trueValues.add("t");
      trueValues.add("T");
      trueValues.add("on");
      trueValues.add("ON");
      trueValues.add("enabled");
      trueValues.add("ENABLED");
      falseValues.add("0");
      falseValues.add("0.0");
      falseValues.add("n");
      falseValues.add("N");
      falseValues.add("no");
      falseValues.add("NO");
      falseValues.add("false");
      falseValues.add("FALSE");
      falseValues.add("f");
      falseValues.add("F");
      falseValues.add("off");
      falseValues.add("OFF");
      falseValues.add("disabled");
      falseValues.add("DISABLED");
      TRUE_VALUES = Collections.unmodifiableSet(trueValues);
      FALSE_VALUES = Collections.unmodifiableSet(falseValues);
   }

   private static class ConvertAll<U> implements Converter<Object, U> {
      private static final long serialVersionUID = 2508560107067092501L;
      private final Class<? extends U> toClass;

      ConvertAll(Class<? extends U> toClass) {
         this.toClass = toClass;
      }

      public U from(Object from) {
         if (from == null) {
            if (this.toClass.isPrimitive()) {
               return this.toClass == Character.TYPE ? '\u0000' : Convert.convert((int)0, (Class)this.toClass);
            } else {
               return this.toClass == Optional.class ? Optional.empty() : null;
            }
         } else {
            Class<?> fromClass = from.getClass();
            if (this.toClass == fromClass) {
               return from;
            } else if (this.toClass.isAssignableFrom(fromClass)) {
               return from;
            } else if (fromClass == byte[].class) {
               return Convert.convert((Object)(new String((byte[])((byte[])from))), (Class)this.toClass);
            } else if (fromClass.isArray()) {
               return this.toClass == java.sql.Array.class ? new MockArray((SQLDialect)null, (Object[])((Object[])from), fromClass) : Convert.convertArray((Object[])((Object[])from), this.toClass);
            } else if (this.toClass == Optional.class) {
               return Optional.of(from);
            } else if (this.toClass == String.class) {
               return from instanceof EnumType ? ((EnumType)from).getLiteral() : from.toString();
            } else if (this.toClass == byte[].class) {
               return from.toString().getBytes();
            } else if (this.toClass != Byte.class && this.toClass != Byte.TYPE) {
               if (this.toClass != Short.class && this.toClass != Short.TYPE) {
                  if (this.toClass != Integer.class && this.toClass != Integer.TYPE) {
                     if (this.toClass != Long.class && this.toClass != Long.TYPE) {
                        if (this.toClass == UByte.class) {
                           try {
                              if (Number.class.isAssignableFrom(fromClass)) {
                                 return Unsigned.ubyte(((Number)from).shortValue());
                              } else if (fromClass != Boolean.class && fromClass != Boolean.TYPE) {
                                 return Unsigned.ubyte((new BigDecimal(from.toString().trim())).shortValue());
                              } else {
                                 return (Boolean)from ? Unsigned.ubyte((int)1) : Unsigned.ubyte((int)0);
                              }
                           } catch (NumberFormatException var11) {
                              return null;
                           }
                        } else if (this.toClass == UShort.class) {
                           try {
                              if (Number.class.isAssignableFrom(fromClass)) {
                                 return Unsigned.ushort(((Number)from).intValue());
                              } else if (fromClass != Boolean.class && fromClass != Boolean.TYPE) {
                                 return Unsigned.ushort((new BigDecimal(from.toString().trim())).intValue());
                              } else {
                                 return (Boolean)from ? Unsigned.ushort((int)1) : Unsigned.ushort((int)0);
                              }
                           } catch (NumberFormatException var12) {
                              return null;
                           }
                        } else if (this.toClass == UInteger.class) {
                           try {
                              if (Number.class.isAssignableFrom(fromClass)) {
                                 return Unsigned.uint(((Number)from).longValue());
                              } else if (fromClass != Boolean.class && fromClass != Boolean.TYPE) {
                                 return Unsigned.uint((new BigDecimal(from.toString().trim())).longValue());
                              } else {
                                 return (Boolean)from ? Unsigned.uint(1) : Unsigned.uint(0);
                              }
                           } catch (NumberFormatException var13) {
                              return null;
                           }
                        } else if (this.toClass == ULong.class) {
                           if (fromClass != Boolean.class && fromClass != Boolean.TYPE) {
                              if (Date.class.isAssignableFrom(fromClass)) {
                                 return Unsigned.ulong(((Date)from).getTime());
                              } else if (Temporal.class.isAssignableFrom(fromClass)) {
                                 return Unsigned.ulong(millis((Temporal)from));
                              } else {
                                 try {
                                    return Unsigned.ulong((new BigDecimal(from.toString().trim())).toBigInteger().toString());
                                 } catch (NumberFormatException var14) {
                                    return null;
                                 }
                              }
                           } else {
                              return (Boolean)from ? Unsigned.ulong(1L) : Unsigned.ulong(0L);
                           }
                        } else if (this.toClass != Float.class && this.toClass != Float.TYPE) {
                           if (this.toClass != Double.class && this.toClass != Double.TYPE) {
                              if (this.toClass == BigDecimal.class) {
                                 if (fromClass != Boolean.class && fromClass != Boolean.TYPE) {
                                    try {
                                       return new BigDecimal(from.toString().trim());
                                    } catch (NumberFormatException var15) {
                                       return null;
                                    }
                                 } else {
                                    return (Boolean)from ? BigDecimal.ONE : BigDecimal.ZERO;
                                 }
                              } else if (this.toClass == BigInteger.class) {
                                 if (fromClass != Boolean.class && fromClass != Boolean.TYPE) {
                                    try {
                                       return (new BigDecimal(from.toString().trim())).toBigInteger();
                                    } catch (NumberFormatException var16) {
                                       return null;
                                    }
                                 } else {
                                    return (Boolean)from ? BigInteger.ONE : BigInteger.ZERO;
                                 }
                              } else if (this.toClass != Boolean.class && this.toClass != Boolean.TYPE) {
                                 if (this.toClass != Character.class && this.toClass != Character.TYPE) {
                                    if (fromClass == String.class && this.toClass == URI.class) {
                                       try {
                                          return new URI(from.toString());
                                       } catch (URISyntaxException var17) {
                                          return null;
                                       }
                                    } else if (fromClass == String.class && this.toClass == URL.class) {
                                       try {
                                          return (new URI(from.toString())).toURL();
                                       } catch (Exception var18) {
                                          return null;
                                       }
                                    } else if (fromClass == String.class && this.toClass == File.class) {
                                       try {
                                          return new File(from.toString());
                                       } catch (Exception var19) {
                                          return null;
                                       }
                                    } else if (Date.class.isAssignableFrom(fromClass)) {
                                       return toDate(((Date)from).getTime(), this.toClass);
                                    } else if (Temporal.class.isAssignableFrom(fromClass)) {
                                       return toDate((Long)Convert.convert(from, Long.class), this.toClass);
                                    } else if ((fromClass == Long.class || fromClass == Long.TYPE) && Date.class.isAssignableFrom(this.toClass)) {
                                       return toDate((Long)from, this.toClass);
                                    } else if ((fromClass == Long.class || fromClass == Long.TYPE) && Temporal.class.isAssignableFrom(this.toClass)) {
                                       return toDate((Long)from, this.toClass);
                                    } else if (fromClass == String.class && this.toClass == java.sql.Date.class) {
                                       try {
                                          return java.sql.Date.valueOf((String)from);
                                       } catch (IllegalArgumentException var20) {
                                          return null;
                                       }
                                    } else if (fromClass == String.class && this.toClass == Time.class) {
                                       try {
                                          return Time.valueOf((String)from);
                                       } catch (IllegalArgumentException var21) {
                                          return null;
                                       }
                                    } else if (fromClass == String.class && this.toClass == Timestamp.class) {
                                       try {
                                          return Timestamp.valueOf((String)from);
                                       } catch (IllegalArgumentException var22) {
                                          return null;
                                       }
                                    } else if (fromClass == String.class && this.toClass == LocalDate.class) {
                                       try {
                                          return java.sql.Date.valueOf((String)from).toLocalDate();
                                       } catch (IllegalArgumentException var23) {
                                          try {
                                             return LocalDate.parse((String)from);
                                          } catch (DateTimeParseException var6) {
                                             return null;
                                          }
                                       }
                                    } else if (fromClass == String.class && this.toClass == LocalTime.class) {
                                       try {
                                          return Time.valueOf((String)from).toLocalTime();
                                       } catch (IllegalArgumentException var24) {
                                          try {
                                             return LocalTime.parse((String)from);
                                          } catch (DateTimeParseException var5) {
                                             return null;
                                          }
                                       }
                                    } else if (fromClass == String.class && this.toClass == OffsetTime.class) {
                                       try {
                                          return Time.valueOf((String)from).toLocalTime().atOffset(OffsetTime.now().getOffset());
                                       } catch (IllegalArgumentException var25) {
                                          try {
                                             return OffsetTime.parse((String)from);
                                          } catch (DateTimeParseException var10) {
                                             return null;
                                          }
                                       }
                                    } else if (fromClass == String.class && this.toClass == LocalDateTime.class) {
                                       try {
                                          return Timestamp.valueOf((String)from).toLocalDateTime();
                                       } catch (IllegalArgumentException var26) {
                                          try {
                                             return LocalDateTime.parse((String)from);
                                          } catch (DateTimeParseException var8) {
                                             return null;
                                          }
                                       }
                                    } else if (fromClass == String.class && this.toClass == OffsetDateTime.class) {
                                       try {
                                          return Timestamp.valueOf((String)from).toLocalDateTime().atOffset(OffsetDateTime.now().getOffset());
                                       } catch (IllegalArgumentException var27) {
                                          try {
                                             return OffsetDateTime.parse((String)from);
                                          } catch (DateTimeParseException var7) {
                                             return null;
                                          }
                                       }
                                    } else if (fromClass == String.class && this.toClass == Instant.class) {
                                       try {
                                          return Timestamp.valueOf((String)from).toLocalDateTime().atOffset(OffsetDateTime.now().getOffset()).toInstant();
                                       } catch (IllegalArgumentException var28) {
                                          try {
                                             return Instant.parse((String)from);
                                          } catch (DateTimeParseException var9) {
                                             return null;
                                          }
                                       }
                                    } else if (fromClass == String.class && Enum.class.isAssignableFrom(this.toClass)) {
                                       try {
                                          return Enum.valueOf(this.toClass, (String)from);
                                       } catch (IllegalArgumentException var29) {
                                          return null;
                                       }
                                    } else if (fromClass == String.class && this.toClass == UUID.class) {
                                       try {
                                          return parseUUID((String)from);
                                       } catch (IllegalArgumentException var30) {
                                          return null;
                                       }
                                    } else if (Record.class.isAssignableFrom(fromClass)) {
                                       Record record = (Record)from;
                                       return record.into(this.toClass);
                                    } else {
                                       throw fail(from, this.toClass);
                                    }
                                 } else if (fromClass != Boolean.class && fromClass != Boolean.TYPE) {
                                    return from.toString().length() < 1 ? null : from.toString().charAt(0);
                                 } else {
                                    return (Boolean)from ? '1' : '0';
                                 }
                              } else {
                                 String s = from.toString().toLowerCase().trim();
                                 if (Convert.TRUE_VALUES.contains(s)) {
                                    return Boolean.TRUE;
                                 } else if (Convert.FALSE_VALUES.contains(s)) {
                                    return Boolean.FALSE;
                                 } else {
                                    return this.toClass == Boolean.class ? null : false;
                                 }
                              }
                           } else if (Number.class.isAssignableFrom(fromClass)) {
                              return ((Number)from).doubleValue();
                           } else if (fromClass != Boolean.class && fromClass != Boolean.TYPE) {
                              try {
                                 return Double.valueOf(from.toString().trim());
                              } catch (NumberFormatException var31) {
                                 return null;
                              }
                           } else {
                              return (Boolean)from ? 1.0D : 0.0D;
                           }
                        } else if (Number.class.isAssignableFrom(fromClass)) {
                           return ((Number)from).floatValue();
                        } else if (fromClass != Boolean.class && fromClass != Boolean.TYPE) {
                           try {
                              return Float.valueOf(from.toString().trim());
                           } catch (NumberFormatException var32) {
                              return null;
                           }
                        } else {
                           return (Boolean)from ? 1.0F : 0.0F;
                        }
                     } else if (Number.class.isAssignableFrom(fromClass)) {
                        return ((Number)from).longValue();
                     } else if (fromClass != Boolean.class && fromClass != Boolean.TYPE) {
                        if (Date.class.isAssignableFrom(fromClass)) {
                           return ((Date)from).getTime();
                        } else if (Temporal.class.isAssignableFrom(fromClass)) {
                           return millis((Temporal)from);
                        } else {
                           try {
                              return (new BigDecimal(from.toString().trim())).longValue();
                           } catch (NumberFormatException var33) {
                              return null;
                           }
                        }
                     } else {
                        return (Boolean)from ? 1L : 0L;
                     }
                  } else if (Number.class.isAssignableFrom(fromClass)) {
                     return ((Number)from).intValue();
                  } else if (fromClass != Boolean.class && fromClass != Boolean.TYPE) {
                     try {
                        return (new BigDecimal(from.toString().trim())).intValue();
                     } catch (NumberFormatException var34) {
                        return null;
                     }
                  } else {
                     return (Boolean)from ? 1 : 0;
                  }
               } else if (Number.class.isAssignableFrom(fromClass)) {
                  return ((Number)from).shortValue();
               } else if (fromClass != Boolean.class && fromClass != Boolean.TYPE) {
                  try {
                     return (new BigDecimal(from.toString().trim())).shortValue();
                  } catch (NumberFormatException var35) {
                     return null;
                  }
               } else {
                  return (Boolean)from ? Short.valueOf((short)1) : Short.valueOf((short)0);
               }
            } else if (Number.class.isAssignableFrom(fromClass)) {
               return ((Number)from).byteValue();
            } else if (fromClass != Boolean.class && fromClass != Boolean.TYPE) {
               try {
                  return (new BigDecimal(from.toString().trim())).byteValue();
               } catch (NumberFormatException var36) {
                  return null;
               }
            } else {
               return (Boolean)from ? 1 : 0;
            }
         }
      }

      public Object to(U to) {
         return to;
      }

      public Class<Object> fromType() {
         return Object.class;
      }

      public Class<U> toType() {
         return this.toClass;
      }

      private static <X> X toDate(long time, Class<X> toClass) {
         if (toClass == java.sql.Date.class) {
            return new java.sql.Date(time);
         } else if (toClass == Time.class) {
            return new Time(time);
         } else if (toClass == Timestamp.class) {
            return new Timestamp(time);
         } else if (toClass == Date.class) {
            return new Date(time);
         } else if (toClass == Calendar.class) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            return calendar;
         } else if (toClass == LocalDate.class) {
            return (new java.sql.Date(time)).toLocalDate();
         } else if (toClass == LocalTime.class) {
            return (new Time(time)).toLocalTime();
         } else if (toClass == OffsetTime.class) {
            return (new Time(time)).toLocalTime().atOffset(OffsetTime.now().getOffset());
         } else if (toClass == LocalDateTime.class) {
            return (new Timestamp(time)).toLocalDateTime();
         } else if (toClass == OffsetDateTime.class) {
            return (new Timestamp(time)).toLocalDateTime().atOffset(OffsetDateTime.now().getOffset());
         } else if (toClass == Instant.class) {
            return Instant.ofEpochMilli(time);
         } else {
            throw fail(time, toClass);
         }
      }

      private static final long millis(Temporal temporal) {
         if (temporal instanceof LocalDate) {
            return java.sql.Date.valueOf((LocalDate)temporal).getTime();
         } else if (temporal instanceof LocalTime) {
            return Time.valueOf((LocalTime)temporal).getTime();
         } else if (temporal instanceof LocalDateTime) {
            return Timestamp.valueOf((LocalDateTime)temporal).getTime();
         } else if (temporal.isSupported(ChronoField.INSTANT_SECONDS)) {
            return 1000L * temporal.getLong(ChronoField.INSTANT_SECONDS) + temporal.getLong(ChronoField.MILLI_OF_SECOND);
         } else if (temporal.isSupported(ChronoField.MILLI_OF_DAY)) {
            return temporal.getLong(ChronoField.MILLI_OF_DAY);
         } else {
            throw fail(temporal, Long.class);
         }
      }

      private static final UUID parseUUID(String string) {
         if (string == null) {
            return null;
         } else {
            return string.contains("-") ? UUID.fromString(string) : UUID.fromString(Convert.UUID_PATTERN.matcher(string).replaceAll("$1-$2-$3-$4-$5"));
         }
      }

      private static DataTypeException fail(Object from, Class<?> toClass) {
         return new DataTypeException("Cannot convert from " + from + " (" + from.getClass() + ") to " + toClass);
      }
   }
}
