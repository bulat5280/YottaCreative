package org.jooq.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {
   public static final String EMPTY = "";
   public static final int INDEX_NOT_FOUND = -1;
   private static final int PAD_LIMIT = 8192;

   public static String defaultString(String str) {
      return str == null ? "" : str;
   }

   public static String defaultString(String str, String defaultStr) {
      return str == null ? defaultStr : str;
   }

   public static String defaultIfEmpty(String str, String defaultStr) {
      return isEmpty(str) ? defaultStr : str;
   }

   public static String defaultIfBlank(String str, String defaultStr) {
      return isBlank(str) ? defaultStr : str;
   }

   public static boolean isEmpty(String str) {
      return str == null || str.length() == 0;
   }

   public static boolean isBlank(String str) {
      int strLen;
      if (str != null && (strLen = str.length()) != 0) {
         for(int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }

   public static int countMatches(String str, String sub) {
      if (!isEmpty(str) && !isEmpty(sub)) {
         int count = 0;

         for(int idx = 0; (idx = str.indexOf(sub, idx)) != -1; idx += sub.length()) {
            ++count;
         }

         return count;
      } else {
         return 0;
      }
   }

   public static String rightPad(String str, int size) {
      return rightPad(str, size, ' ');
   }

   public static String rightPad(String str, int size, char padChar) {
      if (str == null) {
         return null;
      } else {
         int pads = size - str.length();
         if (pads <= 0) {
            return str;
         } else {
            return pads > 8192 ? rightPad(str, size, String.valueOf(padChar)) : str.concat(padding(pads, padChar));
         }
      }
   }

   public static String rightPad(String str, int size, String padStr) {
      if (str == null) {
         return null;
      } else {
         if (isEmpty(padStr)) {
            padStr = " ";
         }

         int padLen = padStr.length();
         int strLen = str.length();
         int pads = size - strLen;
         if (pads <= 0) {
            return str;
         } else if (padLen == 1 && pads <= 8192) {
            return rightPad(str, size, padStr.charAt(0));
         } else if (pads == padLen) {
            return str.concat(padStr);
         } else if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
         } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();

            for(int i = 0; i < pads; ++i) {
               padding[i] = padChars[i % padLen];
            }

            return str.concat(new String(padding));
         }
      }
   }

   public static String leftPad(String str, int size) {
      return leftPad(str, size, ' ');
   }

   public static String leftPad(String str, int size, char padChar) {
      if (str == null) {
         return null;
      } else {
         int pads = size - str.length();
         if (pads <= 0) {
            return str;
         } else {
            return pads > 8192 ? leftPad(str, size, String.valueOf(padChar)) : padding(pads, padChar).concat(str);
         }
      }
   }

   public static String leftPad(String str, int size, String padStr) {
      if (str == null) {
         return null;
      } else {
         if (isEmpty(padStr)) {
            padStr = " ";
         }

         int padLen = padStr.length();
         int strLen = str.length();
         int pads = size - strLen;
         if (pads <= 0) {
            return str;
         } else if (padLen == 1 && pads <= 8192) {
            return leftPad(str, size, padStr.charAt(0));
         } else if (pads == padLen) {
            return padStr.concat(str);
         } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
         } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();

            for(int i = 0; i < pads; ++i) {
               padding[i] = padChars[i % padLen];
            }

            return (new String(padding)).concat(str);
         }
      }
   }

   private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
      if (repeat < 0) {
         throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
      } else {
         char[] buf = new char[repeat];

         for(int i = 0; i < buf.length; ++i) {
            buf[i] = padChar;
         }

         return new String(buf);
      }
   }

   public static String abbreviate(String str, int maxWidth) {
      return abbreviate(str, 0, maxWidth);
   }

   public static String abbreviate(String str, int offset, int maxWidth) {
      if (str == null) {
         return null;
      } else if (maxWidth < 4) {
         throw new IllegalArgumentException("Minimum abbreviation width is 4");
      } else if (str.length() <= maxWidth) {
         return str;
      } else {
         if (offset > str.length()) {
            offset = str.length();
         }

         if (str.length() - offset < maxWidth - 3) {
            offset = str.length() - (maxWidth - 3);
         }

         if (offset <= 4) {
            return str.substring(0, maxWidth - 3) + "...";
         } else if (maxWidth < 7) {
            throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
         } else {
            return offset + (maxWidth - 3) < str.length() ? "..." + abbreviate(str.substring(offset), maxWidth - 3) : "..." + str.substring(str.length() - (maxWidth - 3));
         }
      }
   }

   public static boolean containsAny(String str, char... searchChars) {
      if (str != null && str.length() != 0 && searchChars != null && searchChars.length != 0) {
         for(int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);

            for(int j = 0; j < searchChars.length; ++j) {
               if (searchChars[j] == ch) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static String replace(String text, String searchString, String replacement) {
      return replace(text, searchString, replacement, -1);
   }

   public static String replace(String text, String searchString, String replacement, int max) {
      if (!isEmpty(text) && !isEmpty(searchString) && replacement != null && max != 0) {
         int start = 0;
         int end = text.indexOf(searchString, start);
         if (end == -1) {
            return text;
         } else {
            int replLength = searchString.length();
            int increase = replacement.length() - replLength;
            increase = increase < 0 ? 0 : increase;
            increase *= max < 0 ? 16 : (max > 64 ? 64 : max);

            StringBuilder buf;
            for(buf = new StringBuilder(text.length() + increase); end != -1; end = text.indexOf(searchString, start)) {
               buf.append(text.substring(start, end)).append(replacement);
               start = end + replLength;
               --max;
               if (max == 0) {
                  break;
               }
            }

            buf.append(text.substring(start));
            return buf.toString();
         }
      } else {
         return text;
      }
   }

   public static String replaceEach(String text, String[] searchList, String[] replacementList) {
      return replaceEach(text, searchList, replacementList, false, 0);
   }

   private static String replaceEach(String text, String[] searchList, String[] replacementList, boolean repeat, int timeToLive) {
      if (text != null && text.length() != 0 && searchList != null && searchList.length != 0 && replacementList != null && replacementList.length != 0) {
         if (timeToLive < 0) {
            throw new IllegalStateException("TimeToLive of " + timeToLive + " is less than 0: " + text);
         } else {
            int searchLength = searchList.length;
            int replacementLength = replacementList.length;
            if (searchLength != replacementLength) {
               throw new IllegalArgumentException("Search and Replace array lengths don't match: " + searchLength + " vs " + replacementLength);
            } else {
               boolean[] noMoreMatchesForReplIndex = new boolean[searchLength];
               int textIndex = -1;
               int replaceIndex = -1;
               int tempIndex = true;

               int start;
               int tempIndex;
               for(start = 0; start < searchLength; ++start) {
                  if (!noMoreMatchesForReplIndex[start] && searchList[start] != null && searchList[start].length() != 0 && replacementList[start] != null) {
                     tempIndex = text.indexOf(searchList[start]);
                     if (tempIndex == -1) {
                        noMoreMatchesForReplIndex[start] = true;
                     } else if (textIndex == -1 || tempIndex < textIndex) {
                        textIndex = tempIndex;
                        replaceIndex = start;
                     }
                  }
               }

               if (textIndex == -1) {
                  return text;
               } else {
                  start = 0;
                  int increase = 0;

                  int i;
                  for(int i = 0; i < searchList.length; ++i) {
                     i = replacementList[i].length() - searchList[i].length();
                     if (i > 0) {
                        increase += 3 * i;
                     }
                  }

                  increase = Math.min(increase, text.length() / 5);
                  StringBuffer buf = new StringBuffer(text.length() + increase);

                  while(textIndex != -1) {
                     for(i = start; i < textIndex; ++i) {
                        buf.append(text.charAt(i));
                     }

                     buf.append(replacementList[replaceIndex]);
                     start = textIndex + searchList[replaceIndex].length();
                     textIndex = -1;
                     replaceIndex = -1;
                     tempIndex = true;

                     for(i = 0; i < searchLength; ++i) {
                        if (!noMoreMatchesForReplIndex[i] && searchList[i] != null && searchList[i].length() != 0 && replacementList[i] != null) {
                           tempIndex = text.indexOf(searchList[i], start);
                           if (tempIndex == -1) {
                              noMoreMatchesForReplIndex[i] = true;
                           } else if (textIndex == -1 || tempIndex < textIndex) {
                              textIndex = tempIndex;
                              replaceIndex = i;
                           }
                        }
                     }
                  }

                  i = text.length();

                  for(int i = start; i < i; ++i) {
                     buf.append(text.charAt(i));
                  }

                  String result = buf.toString();
                  if (!repeat) {
                     return result;
                  } else {
                     return replaceEach(result, searchList, replacementList, repeat, timeToLive - 1);
                  }
               }
            }
         }
      } else {
         return text;
      }
   }

   @SafeVarargs
   public static <T> String join(T... elements) {
      return join(elements, (String)null);
   }

   public static String join(Object[] array, char separator) {
      return array == null ? null : join(array, separator, 0, array.length);
   }

   public static String join(Object[] array, char separator, int startIndex, int endIndex) {
      if (array == null) {
         return null;
      } else {
         int noOfItems = endIndex - startIndex;
         if (noOfItems <= 0) {
            return "";
         } else {
            StringBuilder buf = new StringBuilder(noOfItems * 16);

            for(int i = startIndex; i < endIndex; ++i) {
               if (i > startIndex) {
                  buf.append(separator);
               }

               if (array[i] != null) {
                  buf.append(array[i]);
               }
            }

            return buf.toString();
         }
      }
   }

   public static String join(Object[] array, String separator) {
      return array == null ? null : join(array, separator, 0, array.length);
   }

   public static String join(Object[] array, String separator, int startIndex, int endIndex) {
      if (array == null) {
         return null;
      } else {
         if (separator == null) {
            separator = "";
         }

         int noOfItems = endIndex - startIndex;
         if (noOfItems <= 0) {
            return "";
         } else {
            StringBuilder buf = new StringBuilder(noOfItems * 16);

            for(int i = startIndex; i < endIndex; ++i) {
               if (i > startIndex) {
                  buf.append(separator);
               }

               if (array[i] != null) {
                  buf.append(array[i]);
               }
            }

            return buf.toString();
         }
      }
   }

   private StringUtils() {
   }

   public static boolean equals(Object object1, Object object2) {
      if (object1 == object2) {
         return true;
      } else {
         return object1 != null && object2 != null ? object1.equals(object2) : false;
      }
   }

   public static <T> T defaultIfNull(T object, T defaultValue) {
      return object != null ? object : defaultValue;
   }

   public static String toCamelCase(String string) {
      StringBuilder result = new StringBuilder();
      String[] var2 = string.split("_", -1);
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String word = var2[var4];
         if (word.length() > 0) {
            if (Character.isDigit(word.charAt(0))) {
               result.append("_");
            }

            result.append(word.substring(0, 1).toUpperCase());
            result.append(word.substring(1).toLowerCase());
         } else {
            result.append("_");
         }
      }

      return result.toString();
   }

   public static String toCamelCaseLC(String string) {
      return toLC(toCamelCase(string));
   }

   public static String toLC(String string) {
      return string != null && !string.isEmpty() ? Character.toLowerCase(string.charAt(0)) + string.substring(1) : string;
   }

   public static String[] split(String regex, CharSequence input) {
      int index = 0;
      ArrayList<String> matchList = new ArrayList();

      for(Matcher m = Pattern.compile(regex).matcher(input); m.find(); index = m.end()) {
         matchList.add(input.subSequence(index, m.start()).toString());
         matchList.add(input.subSequence(m.start(), m.end()).toString());
      }

      if (index == 0) {
         return new String[]{input.toString()};
      } else {
         matchList.add(input.subSequence(index, input.length()).toString());
         Iterator it = matchList.iterator();

         while(it.hasNext()) {
            if ("".equals(it.next())) {
               it.remove();
            }
         }

         String[] result = new String[matchList.size()];
         return (String[])matchList.toArray(result);
      }
   }
}
