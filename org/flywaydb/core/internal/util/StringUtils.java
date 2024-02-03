package org.flywaydb.core.internal.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
   private StringUtils() {
   }

   public static String trimOrPad(String str, int length) {
      return trimOrPad(str, length, ' ');
   }

   public static String trimOrPad(String str, int length, char padChar) {
      String result;
      if (str == null) {
         result = "";
      } else {
         result = str;
      }

      if (result.length() > length) {
         return result.substring(0, length);
      } else {
         while(result.length() < length) {
            result = result + padChar;
         }

         return result;
      }
   }

   public static boolean isNumeric(String str) {
      return str != null && str.matches("\\d*");
   }

   public static String collapseWhitespace(String str) {
      return str.replaceAll("\\s+", " ");
   }

   public static String left(String str, int count) {
      if (str == null) {
         return null;
      } else {
         return str.length() < count ? str : str.substring(0, count);
      }
   }

   public static String replaceAll(String str, String originalToken, String replacementToken) {
      return str.replaceAll(Pattern.quote(originalToken), Matcher.quoteReplacement(replacementToken));
   }

   public static boolean hasLength(String str) {
      return str != null && str.length() > 0;
   }

   public static String arrayToCommaDelimitedString(Object[] strings) {
      return arrayToDelimitedString(",", strings);
   }

   public static String arrayToDelimitedString(String delimiter, Object[] strings) {
      if (strings == null) {
         return null;
      } else {
         StringBuilder builder = new StringBuilder();

         for(int i = 0; i < strings.length; ++i) {
            if (i > 0) {
               builder.append(delimiter);
            }

            builder.append(String.valueOf(strings[i]));
         }

         return builder.toString();
      }
   }

   public static boolean hasText(String s) {
      return s != null && s.trim().length() > 0;
   }

   public static String[] tokenizeToStringArray(String str, String delimiters) {
      if (str == null) {
         return null;
      } else {
         String[] tokens = str.split("[" + delimiters + "]");

         for(int i = 0; i < tokens.length; ++i) {
            tokens[i] = tokens[i].trim();
         }

         return tokens;
      }
   }

   public static int countOccurrencesOf(String str, String token) {
      if (str != null && token != null && str.length() != 0 && token.length() != 0) {
         int count = 0;

         int idx;
         for(int pos = 0; (idx = str.indexOf(token, pos)) != -1; pos = idx + token.length()) {
            ++count;
         }

         return count;
      } else {
         return 0;
      }
   }

   public static String replace(String inString, String oldPattern, String newPattern) {
      if (hasLength(inString) && hasLength(oldPattern) && newPattern != null) {
         StringBuilder sb = new StringBuilder();
         int pos = 0;
         int index = inString.indexOf(oldPattern);

         for(int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
            sb.append(inString.substring(pos, index));
            sb.append(newPattern);
            pos = index + patLen;
         }

         sb.append(inString.substring(pos));
         return sb.toString();
      } else {
         return inString;
      }
   }

   public static String collectionToCommaDelimitedString(Collection<?> collection) {
      return collectionToDelimitedString(collection, ", ");
   }

   public static String collectionToDelimitedString(Collection<?> collection, String delimiter) {
      if (collection == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder();
         Iterator it = collection.iterator();

         while(it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
               sb.append(delimiter);
            }
         }

         return sb.toString();
      }
   }

   public static String trimLeadingWhitespace(String str) {
      if (!hasLength(str)) {
         return str;
      } else {
         StringBuilder buf = new StringBuilder(str);

         while(buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
            buf.deleteCharAt(0);
         }

         return buf.toString();
      }
   }

   public static String trimTrailingWhitespace(String str) {
      if (!hasLength(str)) {
         return str;
      } else {
         StringBuilder buf = new StringBuilder(str);

         while(buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
            buf.deleteCharAt(buf.length() - 1);
         }

         return buf.toString();
      }
   }
}
