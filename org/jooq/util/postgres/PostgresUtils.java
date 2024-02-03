package org.jooq.util.postgres;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jooq.Record;
import org.jooq.exception.DataTypeException;
import org.jooq.tools.StringUtils;
import org.jooq.tools.reflect.Reflect;
import org.jooq.types.DayToSecond;
import org.jooq.types.YearToMonth;

public class PostgresUtils {
   private static final String POSTGRESQL_HEX_STRING_PREFIX = "\\x";
   private static final int PG_OBJECT_INIT = 0;
   private static final int PG_OBJECT_BEFORE_VALUE = 1;
   private static final int PG_OBJECT_QUOTED_VALUE = 2;
   private static final int PG_OBJECT_UNQUOTED_VALUE = 3;
   private static final int PG_OBJECT_AFTER_VALUE = 4;
   private static final int PG_OBJECT_END = 5;

   public static byte[] toBytes(String string) {
      return string.startsWith("\\x") ? toBytesFromHexEncoding(string) : toBytesFromOctalEncoding(string);
   }

   private static byte[] toBytesFromOctalEncoding(String string) {
      Reader reader = new StringReader(string);
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();

      try {
         convertOctalToBytes(reader, bytes);
         return bytes.toByteArray();
      } catch (IOException var4) {
         throw new DataTypeException("failed to parse octal hex string: " + var4.getMessage(), var4);
      }
   }

   private static void convertOctalToBytes(Reader reader, OutputStream bytes) throws IOException {
      int ch;
      while((ch = reader.read()) != -1) {
         if (ch == 92) {
            ch = reader.read();
            if (ch == -1) {
               throw new DataTypeException("unexpected end of stream after initial backslash");
            }

            if (ch == 92) {
               bytes.write(92);
            } else {
               int val = octalValue(ch);
               ch = reader.read();
               if (ch == -1) {
                  throw new DataTypeException("unexpected end of octal value");
               }

               val <<= 3;
               val += octalValue(ch);
               ch = reader.read();
               if (ch == -1) {
                  throw new DataTypeException("unexpected end of octal value");
               }

               val <<= 3;
               val += octalValue(ch);
               bytes.write(val);
            }
         } else {
            bytes.write(ch);
         }
      }

   }

   private static byte[] toBytesFromHexEncoding(String string) {
      String hex = string.substring("\\x".length());
      StringReader input = new StringReader(hex);
      ByteArrayOutputStream bytes = new ByteArrayOutputStream(hex.length() / 2);

      int hexDigit;
      try {
         while((hexDigit = input.read()) != -1) {
            int byteValue = hexValue(hexDigit) << 4;
            if ((hexDigit = input.read()) == -1) {
               break;
            }

            byteValue += hexValue(hexDigit);
            bytes.write(byteValue);
         }
      } catch (IOException var7) {
         throw new DataTypeException("Error while decoding hex string", var7);
      }

      input.close();
      return bytes.toByteArray();
   }

   private static int hexValue(int hexDigit) {
      if (hexDigit >= 48 && hexDigit <= 57) {
         return hexDigit - 48;
      } else if (hexDigit >= 97 && hexDigit <= 102) {
         return hexDigit - 97 + 10;
      } else if (hexDigit >= 65 && hexDigit <= 70) {
         return hexDigit - 65 + 10;
      } else {
         throw new DataTypeException("unknown postgresql character format for hexValue: " + hexDigit);
      }
   }

   private static int octalValue(int octalDigit) {
      if (octalDigit >= 48 && octalDigit <= 55) {
         return octalDigit - 48;
      } else {
         throw new DataTypeException("unknown postgresql character format for octalValue: " + octalDigit);
      }
   }

   public static Object toPGInterval(DayToSecond interval) {
      return Reflect.on("org.postgresql.util.PGInterval").create(0, 0, interval.getSign() * interval.getDays(), interval.getSign() * interval.getHours(), interval.getSign() * interval.getMinutes(), (double)(interval.getSign() * interval.getSeconds()) + (double)(interval.getSign() * interval.getNano()) / 1.0E9D).get();
   }

   public static Object toPGInterval(YearToMonth interval) {
      return Reflect.on("org.postgresql.util.PGInterval").create(interval.getSign() * interval.getYears(), interval.getSign() * interval.getMonths(), 0, 0, 0, 0.0D).get();
   }

   public static DayToSecond toDayToSecond(Object pgInterval) {
      boolean negative = pgInterval.toString().contains("-");
      Reflect i = Reflect.on(pgInterval);
      if (negative) {
         i.call("scale", -1);
      }

      Double seconds = (Double)i.call("getSeconds").get();
      DayToSecond result = new DayToSecond((Integer)i.call("getDays").get(), (Integer)i.call("getHours").get(), (Integer)i.call("getMinutes").get(), seconds.intValue(), (int)(1.0E9D * (seconds - (double)seconds.intValue())));
      if (negative) {
         result = result.neg();
      }

      return result;
   }

   public static YearToMonth toYearToMonth(Object pgInterval) {
      boolean negative = pgInterval.toString().contains("-");
      Reflect i = Reflect.on(pgInterval);
      if (negative) {
         i.call("scale", -1);
      }

      YearToMonth result = new YearToMonth((Integer)i.call("getYears").get(), (Integer)i.call("getMonths").get());
      if (negative) {
         result = result.neg();
      }

      return result;
   }

   public static List<String> toPGArray(String input) {
      return "{}".equals(input) ? Collections.emptyList() : toPGObjectOrArray(input, '{', '}');
   }

   public static List<String> toPGObject(String input) {
      return toPGObjectOrArray(input, '(', ')');
   }

   private static List<String> toPGObjectOrArray(String input, char open, char close) {
      List<String> values = new ArrayList();
      int i = 0;
      int state = 0;

      for(StringBuilder sb = null; i < input.length(); ++i) {
         char c = input.charAt(i);
         switch(state) {
         case 0:
            if (c == open) {
               state = 1;
            }
            break;
         case 1:
            sb = new StringBuilder();
            if (c == ',') {
               values.add((Object)null);
               state = 1;
            } else if (c == close) {
               values.add((Object)null);
               state = 5;
            } else if (c == '"') {
               state = 2;
            } else {
               if ((c == 'n' || c == 'N') && i + 4 < input.length() && input.substring(i, i + 4).equalsIgnoreCase("null")) {
                  values.add((Object)null);
                  i += 3;
                  state = 4;
                  continue;
               }

               sb.append(c);
               state = 3;
            }
            break;
         case 2:
            if (c == '"') {
               if (input.charAt(i + 1) == '"') {
                  sb.append(c);
                  ++i;
               } else {
                  values.add(sb.toString());
                  state = 4;
               }
            } else if (c == '\\') {
               if (input.charAt(i + 1) == '\\') {
                  sb.append(c);
                  ++i;
               } else {
                  sb.append(c);
               }
            } else {
               sb.append(c);
            }
            break;
         case 3:
            if (c == close) {
               values.add(sb.toString());
               state = 5;
            } else if (c == ',') {
               values.add(sb.toString());
               state = 1;
            } else {
               sb.append(c);
            }
            break;
         case 4:
            if (c == close) {
               state = 5;
            } else if (c == ',') {
               state = 1;
            }
         }
      }

      return values;
   }

   public static String toPGArrayString(Object[] value) {
      StringBuilder sb = new StringBuilder();
      sb.append("{");
      String separator = "";
      Object[] var3 = value;
      int var4 = value.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object o = var3[var5];
         sb.append(separator);
         if (o == null) {
            sb.append(o);
         } else if (o instanceof byte[]) {
            sb.append(toPGString((byte[])((byte[])o)));
         } else {
            sb.append("\"").append(toPGString(o).replace("\\", "\\\\").replace("\"", "\\\"")).append("\"");
         }

         separator = ",";
      }

      sb.append("}");
      return sb.toString();
   }

   public static String toPGString(Object o) {
      if (o instanceof byte[]) {
         return toPGString((byte[])((byte[])o));
      } else if (o instanceof Object[]) {
         return toPGArrayString((Object[])((Object[])o));
      } else {
         return o instanceof Record ? toPGString((Record)o) : "" + o;
      }
   }

   public static String toPGString(Record r) {
      StringBuilder sb = new StringBuilder();
      sb.append("(");
      String separator = "";

      for(int i = 0; i < r.size(); ++i) {
         Object a = r.field(i).getConverter().to(r.get(i));
         sb.append(separator);
         if (a != null) {
            if (a instanceof byte[]) {
               sb.append(toPGString((byte[])((byte[])a)));
            } else {
               sb.append("\"").append(toPGString(a).replace("\\", "\\\\").replace("\"", "\\\"")).append("\"");
            }
         }

         separator = ",";
      }

      sb.append(")");
      return sb.toString();
   }

   public static String toPGString(byte[] binary) {
      StringBuilder sb = new StringBuilder();
      byte[] var2 = binary;
      int var3 = binary.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         byte b = var2[var4];
         sb.append("\\\\");
         sb.append(StringUtils.leftPad(Integer.toOctalString(b & 255), 3, '0'));
      }

      return sb.toString();
   }
}
