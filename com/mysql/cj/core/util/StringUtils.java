package com.mysql.cj.core.util;

import com.mysql.cj.core.Messages;
import com.mysql.cj.core.ServerVersion;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.NumberOutOfRange;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtils {
   public static final Set<StringUtils.SearchMode> SEARCH_MODE__ALL = Collections.unmodifiableSet(EnumSet.allOf(StringUtils.SearchMode.class));
   public static final Set<StringUtils.SearchMode> SEARCH_MODE__MRK_COM_WS;
   public static final Set<StringUtils.SearchMode> SEARCH_MODE__BSESC_COM_WS;
   public static final Set<StringUtils.SearchMode> SEARCH_MODE__BSESC_MRK_WS;
   public static final Set<StringUtils.SearchMode> SEARCH_MODE__COM_WS;
   public static final Set<StringUtils.SearchMode> SEARCH_MODE__MRK_WS;
   public static final Set<StringUtils.SearchMode> SEARCH_MODE__NONE;
   private static final int NON_COMMENTS_MYSQL_VERSION_REF_LENGTH = 5;
   static final int WILD_COMPARE_MATCH_NO_WILD = 0;
   static final int WILD_COMPARE_MATCH_WITH_WILD = 1;
   public static final int WILD_COMPARE_NO_MATCH = -1;
   private static final String VALID_ID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789$_#@";
   private static final char[] HEX_DIGITS;
   static final char[] EMPTY_SPACE;

   public static String dumpAsHex(byte[] byteBuffer, int length) {
      StringBuilder outputBuilder = new StringBuilder(length * 4);
      int p = 0;
      int rows = length / 8;

      int n;
      int i;
      int b;
      for(n = 0; n < rows && p < length; ++n) {
         i = p;

         for(b = 0; b < 8; ++b) {
            String hexVal = Integer.toHexString(byteBuffer[i] & 255);
            if (hexVal.length() == 1) {
               hexVal = "0" + hexVal;
            }

            outputBuilder.append(hexVal + " ");
            ++i;
         }

         outputBuilder.append("    ");

         for(b = 0; b < 8; ++b) {
            int b = 255 & byteBuffer[p];
            if (b > 32 && b < 127) {
               outputBuilder.append((char)b + " ");
            } else {
               outputBuilder.append(". ");
            }

            ++p;
         }

         outputBuilder.append("\n");
      }

      n = 0;

      for(i = p; i < length; ++i) {
         String hexVal = Integer.toHexString(byteBuffer[i] & 255);
         if (hexVal.length() == 1) {
            hexVal = "0" + hexVal;
         }

         outputBuilder.append(hexVal + " ");
         ++n;
      }

      for(i = n; i < 8; ++i) {
         outputBuilder.append("   ");
      }

      outputBuilder.append("    ");

      for(i = p; i < length; ++i) {
         b = 255 & byteBuffer[i];
         if (b > 32 && b < 127) {
            outputBuilder.append((char)b + " ");
         } else {
            outputBuilder.append(". ");
         }
      }

      outputBuilder.append("\n");
      return outputBuilder.toString();
   }

   private static boolean endsWith(byte[] dataFrom, String suffix) {
      for(int i = 1; i <= suffix.length(); ++i) {
         int dfOffset = dataFrom.length - i;
         int suffixOffset = suffix.length() - i;
         if (dataFrom[dfOffset] != suffix.charAt(suffixOffset)) {
            return false;
         }
      }

      return true;
   }

   public static char firstNonWsCharUc(String searchIn) {
      return firstNonWsCharUc(searchIn, 0);
   }

   public static char firstNonWsCharUc(String searchIn, int startAt) {
      if (searchIn == null) {
         return '\u0000';
      } else {
         int length = searchIn.length();

         for(int i = startAt; i < length; ++i) {
            char c = searchIn.charAt(i);
            if (!Character.isWhitespace(c)) {
               return Character.toUpperCase(c);
            }
         }

         return '\u0000';
      }
   }

   public static char firstAlphaCharUc(String searchIn, int startAt) {
      if (searchIn == null) {
         return '\u0000';
      } else {
         int length = searchIn.length();

         for(int i = startAt; i < length; ++i) {
            char c = searchIn.charAt(i);
            if (Character.isLetter(c)) {
               return Character.toUpperCase(c);
            }
         }

         return '\u0000';
      }
   }

   public static String fixDecimalExponent(String dString) {
      int ePos = dString.indexOf(69);
      if (ePos == -1) {
         ePos = dString.indexOf(101);
      }

      if (ePos != -1 && dString.length() > ePos + 1) {
         char maybeMinusChar = dString.charAt(ePos + 1);
         if (maybeMinusChar != '-' && maybeMinusChar != '+') {
            StringBuilder strBuilder = new StringBuilder(dString.length() + 1);
            strBuilder.append(dString.substring(0, ePos + 1));
            strBuilder.append('+');
            strBuilder.append(dString.substring(ePos + 1, dString.length()));
            dString = strBuilder.toString();
         }
      }

      return dString;
   }

   public static byte[] getBytes(String s, String encoding) {
      if (encoding == null) {
         return getBytes(s);
      } else {
         try {
            return s.getBytes(encoding);
         } catch (UnsupportedEncodingException var3) {
            throw (WrongArgumentException)ExceptionFactory.createException((Class)WrongArgumentException.class, (String)Messages.getString("StringUtils.0", new Object[]{encoding}), (Throwable)var3);
         }
      }
   }

   public static byte[] getBytesWrapped(String s, char beginWrap, char endWrap, String encoding) {
      byte[] b;
      StringBuilder strBuilder;
      if (encoding == null) {
         strBuilder = new StringBuilder(s.length() + 2);
         strBuilder.append(beginWrap);
         strBuilder.append(s);
         strBuilder.append(endWrap);
         b = getBytes(strBuilder.toString());
      } else {
         strBuilder = new StringBuilder(s.length() + 2);
         strBuilder.append(beginWrap);
         strBuilder.append(s);
         strBuilder.append(endWrap);
         s = strBuilder.toString();
         b = getBytes(s, encoding);
      }

      return b;
   }

   public static int getInt(byte[] buf) throws NumberFormatException {
      return getInt(buf, 0, buf.length);
   }

   public static int getInt(byte[] buf, int offset, int endpos) throws NumberFormatException {
      long l = getLong(buf, offset, endpos);
      if (l >= -2147483648L && l <= 2147483647L) {
         return (int)l;
      } else {
         throw new NumberOutOfRange(Messages.getString("StringUtils.badIntFormat", new Object[]{toString(buf, offset, endpos - offset)}));
      }
   }

   public static long getLong(byte[] buf) throws NumberFormatException {
      return getLong(buf, 0, buf.length);
   }

   public static long getLong(byte[] buf, int offset, int endpos) throws NumberFormatException {
      int base = 10;

      int s;
      for(s = offset; s < endpos && Character.isWhitespace((char)buf[s]); ++s) {
      }

      if (s == endpos) {
         throw new NumberFormatException(toString(buf));
      } else {
         boolean negative = false;
         if ((char)buf[s] == '-') {
            negative = true;
            ++s;
         } else if ((char)buf[s] == '+') {
            ++s;
         }

         int save = s;
         long cutoff = Long.MAX_VALUE / (long)base;
         long cutlim = (long)((int)(Long.MAX_VALUE % (long)base));
         if (negative) {
            ++cutlim;
         }

         boolean overflow = false;

         long i;
         for(i = 0L; s < endpos; ++s) {
            char c = (char)buf[s];
            if (c >= '0' && c <= '9') {
               c = (char)(c - 48);
            } else {
               if (!Character.isLetter(c)) {
                  break;
               }

               c = (char)(Character.toUpperCase(c) - 65 + 10);
            }

            if (c >= base) {
               break;
            }

            if (i <= cutoff && (i != cutoff || (long)c <= cutlim)) {
               i *= (long)base;
               i += (long)c;
            } else {
               overflow = true;
            }
         }

         if (s == save) {
            throw new NumberFormatException(Messages.getString("StringUtils.badIntFormat", new Object[]{toString(buf, offset, endpos - offset)}));
         } else if (overflow) {
            throw new NumberOutOfRange(Messages.getString("StringUtils.badIntFormat", new Object[]{toString(buf, offset, endpos - offset)}));
         } else {
            return negative ? -i : i;
         }
      }
   }

   public static int indexOfIgnoreCase(String searchIn, String searchFor) {
      return indexOfIgnoreCase(0, searchIn, searchFor);
   }

   public static int indexOfIgnoreCase(int startingPosition, String searchIn, String searchFor) {
      if (searchIn != null && searchFor != null) {
         int searchInLength = searchIn.length();
         int searchForLength = searchFor.length();
         int stopSearchingAt = searchInLength - searchForLength;
         if (startingPosition <= stopSearchingAt && searchForLength != 0) {
            char firstCharOfSearchForUc = Character.toUpperCase(searchFor.charAt(0));
            char firstCharOfSearchForLc = Character.toLowerCase(searchFor.charAt(0));

            for(int i = startingPosition; i <= stopSearchingAt; ++i) {
               if (isCharAtPosNotEqualIgnoreCase(searchIn, i, firstCharOfSearchForUc, firstCharOfSearchForLc)) {
                  do {
                     ++i;
                  } while(i <= stopSearchingAt && isCharAtPosNotEqualIgnoreCase(searchIn, i, firstCharOfSearchForUc, firstCharOfSearchForLc));
               }

               if (i <= stopSearchingAt && startsWithIgnoreCase(searchIn, i, searchFor)) {
                  return i;
               }
            }

            return -1;
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   public static int indexOfIgnoreCase(int startingPosition, String searchIn, String[] searchForSequence, String openingMarkers, String closingMarkers, Set<StringUtils.SearchMode> searchMode) {
      if (searchIn != null && searchForSequence != null) {
         int searchInLength = searchIn.length();
         int searchForLength = 0;
         String[] var8 = searchForSequence;
         int stopSearchingAt = searchForSequence.length;

         for(int var10 = 0; var10 < stopSearchingAt; ++var10) {
            String searchForPart = var8[var10];
            searchForLength += searchForPart.length();
         }

         if (searchForLength == 0) {
            return -1;
         } else {
            int searchForWordsCount = searchForSequence.length;
            searchForLength += searchForWordsCount > 0 ? searchForWordsCount - 1 : 0;
            stopSearchingAt = searchInLength - searchForLength;
            if (startingPosition > stopSearchingAt) {
               return -1;
            } else if (!((Set)searchMode).contains(StringUtils.SearchMode.SKIP_BETWEEN_MARKERS) || openingMarkers != null && closingMarkers != null && openingMarkers.length() == closingMarkers.length()) {
               if (Character.isWhitespace(searchForSequence[0].charAt(0)) && ((Set)searchMode).contains(StringUtils.SearchMode.SKIP_WHITE_SPACE)) {
                  searchMode = EnumSet.copyOf((Collection)searchMode);
                  ((Set)searchMode).remove(StringUtils.SearchMode.SKIP_WHITE_SPACE);
               }

               Set<StringUtils.SearchMode> searchMode2 = EnumSet.of(StringUtils.SearchMode.SKIP_WHITE_SPACE);
               searchMode2.addAll((Collection)searchMode);
               searchMode2.remove(StringUtils.SearchMode.SKIP_BETWEEN_MARKERS);
               int positionOfFirstWord = startingPosition;

               while(positionOfFirstWord <= stopSearchingAt) {
                  positionOfFirstWord = indexOfIgnoreCase(positionOfFirstWord, searchIn, (String)searchForSequence[0], openingMarkers, closingMarkers, (Set)searchMode);
                  if (positionOfFirstWord == -1 || positionOfFirstWord > stopSearchingAt) {
                     return -1;
                  }

                  int startingPositionForNextWord = positionOfFirstWord + searchForSequence[0].length();
                  int wc = 0;
                  boolean match = true;

                  while(true) {
                     ++wc;
                     if (wc >= searchForWordsCount || !match) {
                        if (match) {
                           return positionOfFirstWord;
                        }

                        ++positionOfFirstWord;
                        break;
                     }

                     int positionOfNextWord = indexOfNextChar(startingPositionForNextWord, searchInLength - 1, searchIn, (String)null, (String)null, searchMode2);
                     if (startingPositionForNextWord != positionOfNextWord && startsWithIgnoreCase(searchIn, positionOfNextWord, searchForSequence[wc])) {
                        startingPositionForNextWord = positionOfNextWord + searchForSequence[wc].length();
                     } else {
                        match = false;
                     }
                  }
               }

               return -1;
            } else {
               throw new IllegalArgumentException(Messages.getString("StringUtils.15", new String[]{openingMarkers, closingMarkers}));
            }
         }
      } else {
         return -1;
      }
   }

   public static int indexOfIgnoreCase(int startingPosition, String searchIn, String searchFor, String openingMarkers, String closingMarkers, Set<StringUtils.SearchMode> searchMode) {
      if (searchIn != null && searchFor != null) {
         int searchInLength = searchIn.length();
         int searchForLength = searchFor.length();
         int stopSearchingAt = searchInLength - searchForLength;
         if (startingPosition <= stopSearchingAt && searchForLength != 0) {
            if (((Set)searchMode).contains(StringUtils.SearchMode.SKIP_BETWEEN_MARKERS) && (openingMarkers == null || closingMarkers == null || openingMarkers.length() != closingMarkers.length())) {
               throw new IllegalArgumentException(Messages.getString("StringUtils.15", new String[]{openingMarkers, closingMarkers}));
            } else {
               char firstCharOfSearchForUc = Character.toUpperCase(searchFor.charAt(0));
               char firstCharOfSearchForLc = Character.toLowerCase(searchFor.charAt(0));
               if (Character.isWhitespace(firstCharOfSearchForLc) && ((Set)searchMode).contains(StringUtils.SearchMode.SKIP_WHITE_SPACE)) {
                  searchMode = EnumSet.copyOf((Collection)searchMode);
                  ((Set)searchMode).remove(StringUtils.SearchMode.SKIP_WHITE_SPACE);
               }

               for(int i = startingPosition; i <= stopSearchingAt; ++i) {
                  i = indexOfNextChar(i, stopSearchingAt, searchIn, openingMarkers, closingMarkers, (Set)searchMode);
                  if (i == -1) {
                     return -1;
                  }

                  char c = searchIn.charAt(i);
                  if (isCharEqualIgnoreCase(c, firstCharOfSearchForUc, firstCharOfSearchForLc) && startsWithIgnoreCase(searchIn, i, searchFor)) {
                     return i;
                  }
               }

               return -1;
            }
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   private static int indexOfNextChar(int startingPosition, int stopPosition, String searchIn, String openingMarkers, String closingMarkers, Set<StringUtils.SearchMode> searchMode) {
      if (searchIn == null) {
         return -1;
      } else {
         int searchInLength = searchIn.length();
         if (startingPosition >= searchInLength) {
            return -1;
         } else {
            char c0 = false;
            char c1 = searchIn.charAt(startingPosition);
            char c2 = startingPosition + 1 < searchInLength ? searchIn.charAt(startingPosition + 1) : 0;

            for(int i = startingPosition; i <= stopPosition; ++i) {
               char c0 = c1;
               c1 = c2;
               c2 = i + 2 < searchInLength ? searchIn.charAt(i + 2) : 0;
               boolean dashDashCommentImmediateEnd = false;
               int markerIndex = true;
               if (searchMode.contains(StringUtils.SearchMode.ALLOW_BACKSLASH_ESCAPE) && c0 == '\\') {
                  ++i;
                  c1 = c2;
                  c2 = i + 2 < searchInLength ? searchIn.charAt(i + 2) : 0;
               } else {
                  int j;
                  int markerIndex;
                  if (searchMode.contains(StringUtils.SearchMode.SKIP_BETWEEN_MARKERS) && (markerIndex = openingMarkers.indexOf(c0)) != -1) {
                     j = 0;
                     char openingMarker = c0;
                     char closingMarker = closingMarkers.charAt(markerIndex);

                     while(true) {
                        ++i;
                        if (i > stopPosition || (c0 = searchIn.charAt(i)) == closingMarker && j == 0) {
                           c1 = i + 1 < searchInLength ? searchIn.charAt(i + 1) : 0;
                           c2 = i + 2 < searchInLength ? searchIn.charAt(i + 2) : 0;
                           break;
                        }

                        if (c0 == openingMarker) {
                           ++j;
                        } else if (c0 == closingMarker) {
                           --j;
                        } else if (searchMode.contains(StringUtils.SearchMode.ALLOW_BACKSLASH_ESCAPE) && c0 == '\\') {
                           ++i;
                        }
                     }
                  } else if (searchMode.contains(StringUtils.SearchMode.SKIP_BLOCK_COMMENTS) && c0 == '/' && c1 == '*') {
                     if (c2 != '!') {
                        ++i;

                        do {
                           ++i;
                        } while(i <= stopPosition && (searchIn.charAt(i) != '*' || (i + 1 < searchInLength ? searchIn.charAt(i + 1) : 0) != '/'));

                        ++i;
                     } else {
                        ++i;
                        ++i;

                        for(j = 1; j <= 5 && i + j < searchInLength && Character.isDigit(searchIn.charAt(i + j)); ++j) {
                        }

                        if (j == 5) {
                           i += 5;
                        }
                     }

                     c1 = i + 1 < searchInLength ? searchIn.charAt(i + 1) : 0;
                     c2 = i + 2 < searchInLength ? searchIn.charAt(i + 2) : 0;
                  } else if (searchMode.contains(StringUtils.SearchMode.SKIP_BLOCK_COMMENTS) && c0 == '*' && c1 == '/') {
                     ++i;
                     c1 = c2;
                     c2 = i + 2 < searchInLength ? searchIn.charAt(i + 2) : 0;
                  } else if (searchMode.contains(StringUtils.SearchMode.SKIP_LINE_COMMENTS) && (c0 == '-' && c1 == '-' && (Character.isWhitespace(c2) || (dashDashCommentImmediateEnd = c2 == ';') || c2 == 0) || c0 == '#')) {
                     if (dashDashCommentImmediateEnd) {
                        ++i;
                        ++i;
                        c1 = i + 1 < searchInLength ? searchIn.charAt(i + 1) : 0;
                        c2 = i + 2 < searchInLength ? searchIn.charAt(i + 2) : 0;
                     } else {
                        do {
                           ++i;
                        } while(i <= stopPosition && (c0 = searchIn.charAt(i)) != '\n' && c0 != '\r');

                        c1 = i + 1 < searchInLength ? searchIn.charAt(i + 1) : 0;
                        if (c0 == '\r' && c1 == '\n') {
                           ++i;
                           c1 = i + 1 < searchInLength ? searchIn.charAt(i + 1) : 0;
                        }

                        c2 = i + 2 < searchInLength ? searchIn.charAt(i + 2) : 0;
                     }
                  } else if (!searchMode.contains(StringUtils.SearchMode.SKIP_WHITE_SPACE) || !Character.isWhitespace(c0)) {
                     return i;
                  }
               }
            }

            return -1;
         }
      }
   }

   private static boolean isCharAtPosNotEqualIgnoreCase(String searchIn, int pos, char firstCharOfSearchForUc, char firstCharOfSearchForLc) {
      return Character.toLowerCase(searchIn.charAt(pos)) != firstCharOfSearchForLc && Character.toUpperCase(searchIn.charAt(pos)) != firstCharOfSearchForUc;
   }

   private static boolean isCharEqualIgnoreCase(char charToCompare, char compareToCharUC, char compareToCharLC) {
      return Character.toLowerCase(charToCompare) == compareToCharLC || Character.toUpperCase(charToCompare) == compareToCharUC;
   }

   public static List<String> split(String stringToSplit, String delimiter, boolean trim) {
      if (stringToSplit == null) {
         return new ArrayList();
      } else if (delimiter == null) {
         throw new IllegalArgumentException();
      } else {
         String[] tokens = stringToSplit.split(delimiter, -1);
         Stream<String> tokensStream = Arrays.asList(tokens).stream();
         if (trim) {
            tokensStream = tokensStream.map(String::trim);
         }

         return (List)tokensStream.collect(Collectors.toList());
      }
   }

   public static List<String> split(String stringToSplit, String delimiter, String markers, String markerCloses, boolean trim) {
      return split(stringToSplit, delimiter, markers, markerCloses, trim, SEARCH_MODE__MRK_COM_WS);
   }

   public static List<String> split(String stringToSplit, String delimiter, String markers, String markerCloses, boolean trim, Set<StringUtils.SearchMode> searchMode) {
      if (stringToSplit == null) {
         return new ArrayList();
      } else if (delimiter == null) {
         throw new IllegalArgumentException();
      } else {
         int delimPos = false;
         int currentPos = 0;

         ArrayList splitTokens;
         String token;
         int delimPos;
         for(splitTokens = new ArrayList(); (delimPos = indexOfIgnoreCase(currentPos, stringToSplit, delimiter, markers, markerCloses, searchMode)) != -1; currentPos = delimPos + delimiter.length()) {
            token = stringToSplit.substring(currentPos, delimPos);
            if (trim) {
               token = token.trim();
            }

            splitTokens.add(token);
         }

         token = stringToSplit.substring(currentPos);
         if (trim) {
            token = token.trim();
         }

         splitTokens.add(token);
         return splitTokens;
      }
   }

   private static boolean startsWith(byte[] dataFrom, String chars) {
      int charsLength = chars.length();
      if (dataFrom.length < charsLength) {
         return false;
      } else {
         for(int i = 0; i < charsLength; ++i) {
            if (dataFrom[i] != chars.charAt(i)) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean startsWithIgnoreCase(String searchIn, int startAt, String searchFor) {
      return searchIn.regionMatches(true, startAt, searchFor, 0, searchFor.length());
   }

   public static boolean startsWithIgnoreCase(String searchIn, String searchFor) {
      return startsWithIgnoreCase(searchIn, 0, searchFor);
   }

   public static boolean startsWithIgnoreCaseAndNonAlphaNumeric(String searchIn, String searchFor) {
      if (searchIn == null) {
         return searchFor == null;
      } else {
         int beginPos = 0;

         for(int inLength = searchIn.length(); beginPos < inLength; ++beginPos) {
            char c = searchIn.charAt(beginPos);
            if (Character.isLetterOrDigit(c)) {
               break;
            }
         }

         return startsWithIgnoreCase(searchIn, beginPos, searchFor);
      }
   }

   public static boolean startsWithIgnoreCaseAndWs(String searchIn, String searchFor) {
      return startsWithIgnoreCaseAndWs(searchIn, searchFor, 0);
   }

   public static boolean startsWithIgnoreCaseAndWs(String searchIn, String searchFor, int beginPos) {
      if (searchIn == null) {
         return searchFor == null;
      } else {
         for(int inLength = searchIn.length(); beginPos < inLength && Character.isWhitespace(searchIn.charAt(beginPos)); ++beginPos) {
         }

         return startsWithIgnoreCase(searchIn, beginPos, searchFor);
      }
   }

   public static int startsWithIgnoreCaseAndWs(String searchIn, String[] searchFor) {
      for(int i = 0; i < searchFor.length; ++i) {
         if (startsWithIgnoreCaseAndWs(searchIn, searchFor[i], 0)) {
            return i;
         }
      }

      return -1;
   }

   public static byte[] stripEnclosure(byte[] source, String prefix, String suffix) {
      if (source.length >= prefix.length() + suffix.length() && startsWith(source, prefix) && endsWith(source, suffix)) {
         int totalToStrip = prefix.length() + suffix.length();
         int enclosedLength = source.length - totalToStrip;
         byte[] enclosed = new byte[enclosedLength];
         int startPos = prefix.length();
         int numToCopy = enclosed.length;
         System.arraycopy(source, startPos, enclosed, 0, numToCopy);
         return enclosed;
      } else {
         return source;
      }
   }

   public static String toAsciiString(byte[] buffer) {
      return toAsciiString(buffer, 0, buffer.length);
   }

   public static String toAsciiString(byte[] buffer, int startPos, int length) {
      char[] charArray = new char[length];
      int readpoint = startPos;

      for(int i = 0; i < length; ++i) {
         charArray[i] = (char)buffer[readpoint];
         ++readpoint;
      }

      return new String(charArray);
   }

   public static int wildCompare(String searchIn, String searchForWildcard) {
      if (searchIn != null && searchForWildcard != null) {
         if (searchForWildcard.equals("%")) {
            return 1;
         } else {
            int result = -1;
            char wildcardMany = 37;
            char wildcardOne = 95;
            char wildcardEscape = 92;
            int searchForPos = 0;
            int searchForEnd = searchForWildcard.length();
            int searchInPos = 0;
            int searchInEnd = searchIn.length();

            label144:
            do {
               if (searchForPos != searchForEnd) {
                  for(char wildstrChar = searchForWildcard.charAt(searchForPos); searchForWildcard.charAt(searchForPos) != wildcardMany && wildstrChar != wildcardOne; result = 1) {
                     if (searchForWildcard.charAt(searchForPos) == wildcardEscape && searchForPos + 1 != searchForEnd) {
                        ++searchForPos;
                     }

                     if (searchInPos == searchInEnd || Character.toUpperCase(searchForWildcard.charAt(searchForPos++)) != Character.toUpperCase(searchIn.charAt(searchInPos++))) {
                        return 1;
                     }

                     if (searchForPos == searchForEnd) {
                        return searchInPos != searchInEnd ? 1 : 0;
                     }
                  }

                  if (searchForWildcard.charAt(searchForPos) != wildcardOne) {
                     continue;
                  }

                  while(true) {
                     if (searchInPos == searchInEnd) {
                        return result;
                     }

                     ++searchInPos;
                     ++searchForPos;
                     if (searchForPos >= searchForEnd || searchForWildcard.charAt(searchForPos) != wildcardOne) {
                        if (searchForPos != searchForEnd) {
                           continue label144;
                        }
                        break;
                     }
                  }
               }

               return searchInPos != searchInEnd ? 1 : 0;
            } while(searchForWildcard.charAt(searchForPos) != wildcardMany);

            ++searchForPos;

            for(; searchForPos != searchForEnd; ++searchForPos) {
               if (searchForWildcard.charAt(searchForPos) != wildcardMany) {
                  if (searchForWildcard.charAt(searchForPos) != wildcardOne) {
                     break;
                  }

                  if (searchInPos == searchInEnd) {
                     return -1;
                  }

                  ++searchInPos;
               }
            }

            if (searchForPos == searchForEnd) {
               return 0;
            } else if (searchInPos == searchInEnd) {
               return -1;
            } else {
               char cmp;
               if ((cmp = searchForWildcard.charAt(searchForPos)) == wildcardEscape && searchForPos + 1 != searchForEnd) {
                  ++searchForPos;
                  cmp = searchForWildcard.charAt(searchForPos);
               }

               ++searchForPos;

               do {
                  while(searchInPos != searchInEnd && Character.toUpperCase(searchIn.charAt(searchInPos)) != Character.toUpperCase(cmp)) {
                     ++searchInPos;
                  }

                  if (searchInPos++ == searchInEnd) {
                     return -1;
                  }

                  int tmp = wildCompare(searchIn, searchForWildcard);
                  if (tmp <= 0) {
                     return tmp;
                  }
               } while(searchInPos != searchInEnd && searchForWildcard.charAt(0) != wildcardMany);

               return -1;
            }
         }
      } else {
         return -1;
      }
   }

   public static int lastIndexOf(byte[] s, char c) {
      if (s == null) {
         return -1;
      } else {
         for(int i = s.length - 1; i >= 0; --i) {
            if (s[i] == c) {
               return i;
            }
         }

         return -1;
      }
   }

   public static int indexOf(byte[] s, char c) {
      if (s == null) {
         return -1;
      } else {
         int length = s.length;

         for(int i = 0; i < length; ++i) {
            if (s[i] == c) {
               return i;
            }
         }

         return -1;
      }
   }

   public static boolean isNullOrEmpty(String toTest) {
      return toTest == null || toTest.isEmpty();
   }

   public static String stripComments(String src, String stringOpens, String stringCloses, boolean slashStarComments, boolean slashSlashComments, boolean hashComments, boolean dashDashComments) {
      if (src == null) {
         return null;
      } else {
         StringBuilder strBuilder = new StringBuilder(src.length());
         StringReader sourceReader = new StringReader(src);
         int contextMarker = 0;
         boolean escaped = false;
         int markerTypeFound = -1;
         int ind = false;
         boolean var13 = false;

         int currentChar;
         try {
            label141:
            while((currentChar = sourceReader.read()) != -1) {
               if (markerTypeFound != -1 && currentChar == stringCloses.charAt(markerTypeFound) && !escaped) {
                  contextMarker = 0;
                  markerTypeFound = -1;
               } else {
                  int ind;
                  if ((ind = stringOpens.indexOf(currentChar)) != -1 && !escaped && contextMarker == 0) {
                     markerTypeFound = ind;
                     contextMarker = currentChar;
                  }
               }

               if (contextMarker == 0 && currentChar == 47 && (slashSlashComments || slashStarComments)) {
                  currentChar = sourceReader.read();
                  if (currentChar == 42 && slashStarComments) {
                     int prevChar = 0;

                     while(true) {
                        if ((currentChar = sourceReader.read()) == 47 && prevChar == 42) {
                           continue label141;
                        }

                        if (currentChar == 13) {
                           currentChar = sourceReader.read();
                           if (currentChar == 10) {
                              currentChar = sourceReader.read();
                           }
                        } else if (currentChar == 10) {
                           currentChar = sourceReader.read();
                        }

                        if (currentChar < 0) {
                           continue label141;
                        }

                        prevChar = currentChar;
                     }
                  }

                  if (currentChar == 47 && slashSlashComments) {
                     while((currentChar = sourceReader.read()) != 10 && currentChar != 13 && currentChar >= 0) {
                     }
                  }
               } else if (contextMarker == 0 && currentChar == 35 && hashComments) {
                  while((currentChar = sourceReader.read()) != 10 && currentChar != 13 && currentChar >= 0) {
                  }
               } else if (contextMarker == 0 && currentChar == 45 && dashDashComments) {
                  label156: {
                     currentChar = sourceReader.read();
                     if (currentChar != -1 && currentChar == 45) {
                        while(true) {
                           if ((currentChar = sourceReader.read()) == 10 || currentChar == 13 || currentChar < 0) {
                              break label156;
                           }
                        }
                     }

                     strBuilder.append('-');
                     if (currentChar != -1) {
                        strBuilder.append(currentChar);
                     }
                     continue;
                  }
               }

               if (currentChar != -1) {
                  strBuilder.append((char)currentChar);
               }
            }
         } catch (IOException var15) {
         }

         return strBuilder.toString();
      }
   }

   public static String sanitizeProcOrFuncName(String src) {
      return src != null && !src.equals("%") ? src : null;
   }

   public static List<String> splitDBdotName(String src, String cat, String quotId, boolean isNoBslashEscSet) {
      if (src != null && !src.equals("%")) {
         boolean isQuoted = indexOfIgnoreCase(0, src, quotId) > -1;
         String tmpCat = cat;
         int trueDotIndex = true;
         int trueDotIndex;
         if (!" ".equals(quotId)) {
            if (isQuoted) {
               trueDotIndex = indexOfIgnoreCase(0, src, quotId + "." + quotId);
            } else {
               trueDotIndex = indexOfIgnoreCase(0, src, ".");
            }
         } else {
            trueDotIndex = src.indexOf(".");
         }

         List<String> retTokens = new ArrayList(2);
         String retval;
         if (trueDotIndex != -1) {
            if (isQuoted) {
               tmpCat = toString(stripEnclosure(src.substring(0, trueDotIndex + 1).getBytes(), quotId, quotId));
               if (startsWithIgnoreCaseAndWs(tmpCat, quotId)) {
                  tmpCat = tmpCat.substring(1, tmpCat.length() - 1);
               }

               retval = src.substring(trueDotIndex + 2);
               retval = toString(stripEnclosure(retval.getBytes(), quotId, quotId));
            } else {
               tmpCat = src.substring(0, trueDotIndex);
               retval = src.substring(trueDotIndex + 1);
            }
         } else {
            retval = toString(stripEnclosure(src.getBytes(), quotId, quotId));
         }

         retTokens.add(tmpCat);
         retTokens.add(retval);
         return retTokens;
      } else {
         return new ArrayList();
      }
   }

   public static boolean isEmptyOrWhitespaceOnly(String str) {
      if (str != null && str.length() != 0) {
         int length = str.length();

         for(int i = 0; i < length; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }

   public static String escapeQuote(String src, String quotChar) {
      if (src == null) {
         return null;
      } else {
         src = toString(stripEnclosure(src.getBytes(), quotChar, quotChar));
         int lastNdx = src.indexOf(quotChar);
         String tmpSrc = src.substring(0, lastNdx);
         tmpSrc = tmpSrc + quotChar + quotChar;
         String tmpRest = src.substring(lastNdx + 1, src.length());

         for(lastNdx = tmpRest.indexOf(quotChar); lastNdx > -1; lastNdx = tmpRest.indexOf(quotChar)) {
            tmpSrc = tmpSrc + tmpRest.substring(0, lastNdx);
            tmpSrc = tmpSrc + quotChar + quotChar;
            tmpRest = tmpRest.substring(lastNdx + 1, tmpRest.length());
         }

         tmpSrc = tmpSrc + tmpRest;
         return tmpSrc;
      }
   }

   public static String quoteIdentifier(String identifier, String quoteChar, boolean isPedantic) {
      if (identifier == null) {
         return null;
      } else {
         identifier = identifier.trim();
         int quoteCharLength = quoteChar.length();
         if (quoteCharLength == 0) {
            return identifier;
         } else {
            if (!isPedantic && identifier.startsWith(quoteChar) && identifier.endsWith(quoteChar)) {
               String identifierQuoteTrimmed = identifier.substring(quoteCharLength, identifier.length() - quoteCharLength);

               int quoteCharPos;
               int quoteCharNextPosition;
               for(quoteCharPos = identifierQuoteTrimmed.indexOf(quoteChar); quoteCharPos >= 0; quoteCharPos = identifierQuoteTrimmed.indexOf(quoteChar, quoteCharNextPosition + quoteCharLength)) {
                  int quoteCharNextExpectedPos = quoteCharPos + quoteCharLength;
                  quoteCharNextPosition = identifierQuoteTrimmed.indexOf(quoteChar, quoteCharNextExpectedPos);
                  if (quoteCharNextPosition != quoteCharNextExpectedPos) {
                     break;
                  }
               }

               if (quoteCharPos < 0) {
                  return identifier;
               }
            }

            return quoteChar + identifier.replaceAll(quoteChar, quoteChar + quoteChar) + quoteChar;
         }
      }
   }

   public static String quoteIdentifier(String identifier, boolean isPedantic) {
      return quoteIdentifier(identifier, "`", isPedantic);
   }

   public static String unQuoteIdentifier(String identifier, String quoteChar) {
      if (identifier == null) {
         return null;
      } else {
         identifier = identifier.trim();
         int quoteCharLength = quoteChar.length();
         if (quoteCharLength == 0) {
            return identifier;
         } else if (identifier.startsWith(quoteChar) && identifier.endsWith(quoteChar)) {
            String identifierQuoteTrimmed = identifier.substring(quoteCharLength, identifier.length() - quoteCharLength);

            int quoteCharNextPosition;
            for(int quoteCharPos = identifierQuoteTrimmed.indexOf(quoteChar); quoteCharPos >= 0; quoteCharPos = identifierQuoteTrimmed.indexOf(quoteChar, quoteCharNextPosition + quoteCharLength)) {
               int quoteCharNextExpectedPos = quoteCharPos + quoteCharLength;
               quoteCharNextPosition = identifierQuoteTrimmed.indexOf(quoteChar, quoteCharNextExpectedPos);
               if (quoteCharNextPosition != quoteCharNextExpectedPos) {
                  return identifier;
               }
            }

            return identifier.substring(quoteCharLength, identifier.length() - quoteCharLength).replaceAll(quoteChar + quoteChar, quoteChar);
         } else {
            return identifier;
         }
      }
   }

   public static int indexOfQuoteDoubleAware(String searchIn, String quoteChar, int startFrom) {
      if (searchIn != null && quoteChar != null && quoteChar.length() != 0 && startFrom <= searchIn.length()) {
         int lastIndex = searchIn.length() - 1;
         int beginPos = startFrom;
         int pos = -1;
         boolean next = true;

         while(true) {
            while(next) {
               pos = searchIn.indexOf(quoteChar, beginPos);
               if (pos != -1 && pos != lastIndex && searchIn.startsWith(quoteChar, pos + 1)) {
                  beginPos = pos + 2;
               } else {
                  next = false;
               }
            }

            return pos;
         }
      } else {
         return -1;
      }
   }

   public static String toString(byte[] value, int offset, int length, String encoding) {
      if (encoding == null) {
         return new String(value, offset, length);
      } else {
         try {
            return new String(value, offset, length, encoding);
         } catch (UnsupportedEncodingException var5) {
            throw (WrongArgumentException)ExceptionFactory.createException((Class)WrongArgumentException.class, (String)Messages.getString("StringUtils.0", new Object[]{encoding}), (Throwable)var5);
         }
      }
   }

   public static String toString(byte[] value, String encoding) {
      if (encoding == null) {
         return new String(value);
      } else {
         try {
            return new String(value, encoding);
         } catch (UnsupportedEncodingException var3) {
            throw (WrongArgumentException)ExceptionFactory.createException((Class)WrongArgumentException.class, (String)Messages.getString("StringUtils.0", new Object[]{encoding}), (Throwable)var3);
         }
      }
   }

   public static String toString(byte[] value, int offset, int length) {
      return new String(value, offset, length);
   }

   public static String toString(byte[] value) {
      return new String(value);
   }

   public static byte[] getBytes(char[] value) {
      return getBytes((char[])value, 0, value.length);
   }

   public static byte[] getBytes(char[] c, String encoding) {
      return getBytes((char[])c, 0, c.length, encoding);
   }

   public static byte[] getBytes(char[] value, int offset, int length) {
      return getBytes((char[])value, offset, length, (String)null);
   }

   public static byte[] getBytes(char[] value, int offset, int length, String encoding) {
      Charset cs;
      try {
         if (encoding == null) {
            cs = Charset.defaultCharset();
         } else {
            cs = Charset.forName(encoding);
         }
      } catch (UnsupportedCharsetException var8) {
         throw (WrongArgumentException)ExceptionFactory.createException((Class)WrongArgumentException.class, (String)Messages.getString("StringUtils.0", new Object[]{encoding}), (Throwable)var8);
      }

      ByteBuffer buf = cs.encode(CharBuffer.wrap(value, offset, length));
      int encodedLen = buf.limit();
      byte[] asBytes = new byte[encodedLen];
      buf.get(asBytes, 0, encodedLen);
      return asBytes;
   }

   public static byte[] getBytes(String value) {
      return value.getBytes();
   }

   public static byte[] getBytes(String value, int offset, int length) {
      return value.substring(offset, offset + length).getBytes();
   }

   public static byte[] getBytes(String value, int offset, int length, String encoding) {
      if (encoding == null) {
         return getBytes(value, offset, length);
      } else {
         try {
            return value.substring(offset, offset + length).getBytes(encoding);
         } catch (UnsupportedEncodingException var5) {
            throw (WrongArgumentException)ExceptionFactory.createException((Class)WrongArgumentException.class, (String)Messages.getString("StringUtils.0", new Object[]{encoding}), (Throwable)var5);
         }
      }
   }

   public static final boolean isValidIdChar(char c) {
      return "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789$_#@".indexOf(c) != -1;
   }

   public static void appendAsHex(StringBuilder builder, byte[] bytes) {
      builder.append("0x");
      byte[] var2 = bytes;
      int var3 = bytes.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         byte b = var2[var4];
         builder.append(HEX_DIGITS[b >>> 4 & 15]).append(HEX_DIGITS[b & 15]);
      }

   }

   public static void appendAsHex(StringBuilder builder, int value) {
      if (value == 0) {
         builder.append("0x0");
      } else {
         int shift = 32;
         boolean nonZeroFound = false;
         builder.append("0x");

         do {
            shift -= 4;
            byte nibble = (byte)(value >>> shift & 15);
            if (nonZeroFound) {
               builder.append(HEX_DIGITS[nibble]);
            } else if (nibble != 0) {
               builder.append(HEX_DIGITS[nibble]);
               nonZeroFound = true;
            }
         } while(shift != 0);

      }
   }

   public static byte[] getBytesNullTerminated(String value, String encoding) {
      Charset cs = Charset.forName(encoding);
      ByteBuffer buf = cs.encode(value);
      int encodedLen = buf.limit();
      byte[] asBytes = new byte[encodedLen + 1];
      buf.get(asBytes, 0, encodedLen);
      asBytes[encodedLen] = 0;
      return asBytes;
   }

   public static boolean canHandleAsServerPreparedStatementNoCache(String sql, ServerVersion serverVersion) {
      if (startsWithIgnoreCaseAndNonAlphaNumeric(sql, "CALL")) {
         return false;
      } else {
         boolean canHandleAsStatement = true;
         if (startsWithIgnoreCaseAndWs(sql, "XA ")) {
            canHandleAsStatement = false;
         } else if (startsWithIgnoreCaseAndWs(sql, "CREATE TABLE")) {
            canHandleAsStatement = false;
         } else if (startsWithIgnoreCaseAndWs(sql, "DO")) {
            canHandleAsStatement = false;
         } else if (startsWithIgnoreCaseAndWs(sql, "SET")) {
            canHandleAsStatement = false;
         } else if (startsWithIgnoreCaseAndWs(sql, "SHOW WARNINGS") && serverVersion.meetsMinimum(ServerVersion.parseVersion("5.7.2"))) {
            canHandleAsStatement = false;
         }

         return canHandleAsStatement;
      }
   }

   public static String padString(String stringVal, int requiredLength) {
      int currentLength = stringVal.length();
      int difference = requiredLength - currentLength;
      if (difference > 0) {
         StringBuilder paddedBuf = new StringBuilder(requiredLength);
         paddedBuf.append(stringVal);
         paddedBuf.append(EMPTY_SPACE, 0, difference);
         return paddedBuf.toString();
      } else {
         return stringVal;
      }
   }

   public static int safeIntParse(String intAsString) {
      try {
         return Integer.parseInt(intAsString);
      } catch (NumberFormatException var2) {
         return 0;
      }
   }

   public static boolean isStrictlyNumeric(CharSequence cs) {
      if (cs != null && cs.length() != 0) {
         for(int i = 0; i < cs.length(); ++i) {
            if (!Character.isDigit(cs.charAt(i))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static String safeTrim(String toTrim) {
      return isNullOrEmpty(toTrim) ? toTrim : toTrim.trim();
   }

   static {
      SEARCH_MODE__MRK_COM_WS = Collections.unmodifiableSet(EnumSet.of(StringUtils.SearchMode.SKIP_BETWEEN_MARKERS, StringUtils.SearchMode.SKIP_BLOCK_COMMENTS, StringUtils.SearchMode.SKIP_LINE_COMMENTS, StringUtils.SearchMode.SKIP_WHITE_SPACE));
      SEARCH_MODE__BSESC_COM_WS = Collections.unmodifiableSet(EnumSet.of(StringUtils.SearchMode.ALLOW_BACKSLASH_ESCAPE, StringUtils.SearchMode.SKIP_BLOCK_COMMENTS, StringUtils.SearchMode.SKIP_LINE_COMMENTS, StringUtils.SearchMode.SKIP_WHITE_SPACE));
      SEARCH_MODE__BSESC_MRK_WS = Collections.unmodifiableSet(EnumSet.of(StringUtils.SearchMode.ALLOW_BACKSLASH_ESCAPE, StringUtils.SearchMode.SKIP_BETWEEN_MARKERS, StringUtils.SearchMode.SKIP_WHITE_SPACE));
      SEARCH_MODE__COM_WS = Collections.unmodifiableSet(EnumSet.of(StringUtils.SearchMode.SKIP_BLOCK_COMMENTS, StringUtils.SearchMode.SKIP_LINE_COMMENTS, StringUtils.SearchMode.SKIP_WHITE_SPACE));
      SEARCH_MODE__MRK_WS = Collections.unmodifiableSet(EnumSet.of(StringUtils.SearchMode.SKIP_BETWEEN_MARKERS, StringUtils.SearchMode.SKIP_WHITE_SPACE));
      SEARCH_MODE__NONE = Collections.unmodifiableSet(EnumSet.noneOf(StringUtils.SearchMode.class));
      HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
      EMPTY_SPACE = new char[255];

      for(int i = 0; i < EMPTY_SPACE.length; ++i) {
         EMPTY_SPACE[i] = ' ';
      }

   }

   public static enum SearchMode {
      ALLOW_BACKSLASH_ESCAPE,
      SKIP_BETWEEN_MARKERS,
      SKIP_BLOCK_COMMENTS,
      SKIP_LINE_COMMENTS,
      SKIP_WHITE_SPACE;
   }
}
