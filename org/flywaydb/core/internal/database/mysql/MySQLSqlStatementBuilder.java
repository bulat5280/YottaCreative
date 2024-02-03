package org.flywaydb.core.internal.database.mysql;

import java.util.regex.Pattern;
import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.util.StringUtils;

public class MySQLSqlStatementBuilder extends SqlStatementBuilder {
   private static final String DELIMITER_KEYWORD = "DELIMITER";
   private final String[] charSets = new String[]{"ARMSCII8", "ASCII", "BIG5", "BINARY", "CP1250", "CP1251", "CP1256", "CP1257", "CP850", "CP852", "CP866", "CP932", "DEC8", "EUCJPMS", "EUCKR", "GB2312", "GBK", "GEOSTD8", "GREEK", "HEBREW", "HP8", "KEYBCS2", "KOI8R", "KOI8U", "LATIN1", "LATIN2", "LATIN5", "LATIN7", "MACCE", "MACROMAN", "SJIS", "SWE7", "TIS620", "UCS2", "UJIS", "UTF8"};
   boolean isInMultiLineCommentDirective = false;

   public MySQLSqlStatementBuilder(Delimiter defaultDelimiter) {
      super(defaultDelimiter);
   }

   public Delimiter extractNewDelimiterFromLine(String line) {
      return line.toUpperCase().startsWith("DELIMITER") ? new Delimiter(line.substring("DELIMITER".length()).trim(), false) : null;
   }

   protected Delimiter changeDelimiterIfNecessary(String line, Delimiter delimiter) {
      return line.toUpperCase().startsWith("DELIMITER") ? new Delimiter(line.substring("DELIMITER".length()).trim(), false) : delimiter;
   }

   public boolean isCommentDirective(String line) {
      if (line.matches("^" + Pattern.quote("/*!") + "\\d{5} .*" + Pattern.quote("*/") + "\\s*;?")) {
         return true;
      } else if (line.matches("^" + Pattern.quote("/*!") + "\\d{5} .*")) {
         this.isInMultiLineCommentDirective = true;
         return true;
      } else if (this.isInMultiLineCommentDirective && line.matches(".*" + Pattern.quote("*/") + "\\s*;?")) {
         this.isInMultiLineCommentDirective = false;
         return true;
      } else {
         return this.isInMultiLineCommentDirective;
      }
   }

   protected boolean isSingleLineComment(String token) {
      return token.startsWith("--") || token.startsWith("#") && (!"#".equals(this.delimiter.getDelimiter()) || !"#".equals(token));
   }

   protected String removeEscapedQuotes(String token) {
      String noEscapedBackslashes = StringUtils.replaceAll(token, "\\\\", "");
      String noBackslashEscapes = StringUtils.replaceAll(StringUtils.replaceAll(noEscapedBackslashes, "\\'", ""), "\\\"", "");
      return StringUtils.replaceAll(noBackslashEscapes, "''", "").replace("'", " ' ");
   }

   protected String cleanToken(String token) {
      if (!token.startsWith("B'") && !token.startsWith("X'")) {
         if (token.startsWith("_")) {
            String[] var2 = this.charSets;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               String charSet = var2[var4];
               String cast = "_" + charSet;
               if (token.startsWith(cast)) {
                  return token.substring(cast.length());
               }
            }
         }

         return token;
      } else {
         return token.substring(token.indexOf("'"));
      }
   }

   protected String extractAlternateOpenQuote(String token) {
      return token.startsWith("\"") ? "\"" : null;
   }
}
