package org.jooq.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.impl.DSL;
import org.jooq.util.h2.H2DataType;

class GenerationUtil {
   private static Set<String> JAVA_KEYWORDS = Collections.unmodifiableSet(new HashSet(Arrays.asList("abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "double", "do", "else", "enum", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "interface", "int", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while")));
   private static Set<String> SCALA_KEYWORDS = Collections.unmodifiableSet(new HashSet(Arrays.asList("abstract", "case", "catch", "class", "def", "do", "else", "extends", "false", "final", "finally", "for", "forSome", "if", "implicit", "import", "lazy", "match", "new", "null", "object", "override", "package", "private", "protected", "return", "sealed", "super", "this", "throw", "trait", "try", "true", "type", "val", "var", "while", "with", "yield")));
   private static Set<Character> SCALA_WHITESPACE = Collections.unmodifiableSet(new HashSet(Arrays.asList(' ', '\t', '\r', '\n')));
   private static Set<Character> SCALA_PARENTHESES = Collections.unmodifiableSet(new HashSet(Arrays.asList('(', ')', '[', ']', '{', '}')));
   private static Set<Character> SCALA_DELIMITER = Collections.unmodifiableSet(new HashSet(Arrays.asList('`', '\'', '"', '.', ';', ',')));
   private static Set<String> WINDOWS_FORBIDDEN = Collections.unmodifiableSet(new HashSet(Arrays.asList("CON", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9")));

   private static Boolean isScalaOperator(char c) {
      return c >= ' ' && c <= 127 && !Character.isLetter(c) && !Character.isDigit(c) && !SCALA_DELIMITER.contains(c) && !SCALA_PARENTHESES.contains(c) && !SCALA_WHITESPACE.contains(c) || Character.getType(c) == 25 || Character.getType(c) == 28;
   }

   private static Boolean isScalaLetter(char c) {
      return Character.isLetter(c) || c == '_' || c == '$';
   }

   private static Boolean isScalaIdentifierStart(char c) {
      return isScalaLetter(c);
   }

   private static Boolean isScalaIdentifierPart(char c) {
      return isScalaIdentifierStart(c) || Character.isDigit(c);
   }

   public static String escapeWindowsForbiddenNames(String name) {
      return name == null ? null : (WINDOWS_FORBIDDEN.contains(name.toUpperCase()) ? name + "_" : name);
   }

   public static String convertToIdentifier(String literal, AbstractGenerator.Language language) {
      if (language == AbstractGenerator.Language.JAVA && JAVA_KEYWORDS.contains(literal)) {
         return literal + "_";
      } else if (language == AbstractGenerator.Language.SCALA && SCALA_KEYWORDS.contains(literal)) {
         return "`" + literal + "`";
      } else {
         StringBuilder sb = new StringBuilder();
         if ("".equals(literal)) {
            return language == AbstractGenerator.Language.SCALA ? "`_`" : "_";
         } else {
            for(int i = 0; i < literal.length(); ++i) {
               char c = literal.charAt(i);
               if (language == AbstractGenerator.Language.SCALA && i == literal.length() - 1 && literal.length() >= 2 && literal.charAt(i - 1) == '_' && isScalaOperator(c)) {
                  sb.append(c);
               } else if (language == AbstractGenerator.Language.SCALA && !isScalaIdentifierPart(c)) {
                  sb.append(escape(c));
               } else if (language == AbstractGenerator.Language.JAVA && !Character.isJavaIdentifierPart(c)) {
                  sb.append(escape(c));
               } else if (language == AbstractGenerator.Language.SCALA && i == 0 && !isScalaIdentifierStart(c)) {
                  sb.append("_").append(c);
               } else if (language == AbstractGenerator.Language.JAVA && i == 0 && !Character.isJavaIdentifierStart(c)) {
                  sb.append("_").append(c);
               } else {
                  sb.append(c);
               }
            }

            return sb.toString();
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public static String convertToJavaIdentifier(String literal) {
      return convertToIdentifier(literal, AbstractGenerator.Language.JAVA);
   }

   private static String escape(char c) {
      return c != ' ' && c != '-' && c != '.' ? "_" + Integer.toHexString(c) : "_";
   }

   static String getSimpleJavaType(String qualifiedJavaType) {
      return qualifiedJavaType == null ? null : qualifiedJavaType.replaceAll(".*\\.", "");
   }

   static Name getArrayBaseType(SQLDialect dialect, String t, Name u) {
      switch(dialect.family()) {
      case POSTGRES:
         if (u != null && u.last().startsWith("_")) {
            String[] name = u.getName();
            name[name.length - 1] = name[name.length - 1].substring(1);
            return DSL.name(name);
         }

         return u;
      case H2:
         return DSL.name(H2DataType.OTHER.getTypeName());
      case HSQLDB:
         if ("ARRAY".equalsIgnoreCase(t)) {
            return DSL.name("OTHER");
         }

         return DSL.name(t.replace(" ARRAY", ""));
      default:
         throw new SQLDialectNotSupportedException("getArrayBaseType() is not supported for dialect " + dialect);
      }
   }

   public static Integer[] range(Integer from, Integer to) {
      Integer[] result = new Integer[to - from + 1];

      for(int i = from; i <= to; ++i) {
         result[i - from] = i;
      }

      return result;
   }
}
