package org.flywaydb.core.internal.database.saphana;

import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.util.StringUtils;

public class SAPHANASqlStatementBuilder extends SqlStatementBuilder {
   private static final Log LOG = LogFactory.getLog(SAPHANASqlStatementBuilder.class);
   private int beginEndNestedDepth = 0;
   private String statementStartNormalized = "";

   SAPHANASqlStatementBuilder(Delimiter defaultDelimiter) {
      super(defaultDelimiter);
   }

   protected String cleanToken(String token) {
      return !token.startsWith("N'") && !token.startsWith("X'") && !token.startsWith("DATE'") && !token.startsWith("TIME'") && !token.startsWith("TIMESTAMP'") ? super.cleanToken(token) : token.substring(token.indexOf("'"));
   }

   protected Delimiter changeDelimiterIfNecessary(String line, Delimiter delimiter) {
      if (this.statementStartNormalized.length() < 16) {
         String effectiveLine = cutCommentsFromEnd(line);
         this.statementStartNormalized = this.statementStartNormalized + effectiveLine + " ";
         this.statementStartNormalized = StringUtils.trimLeadingWhitespace(StringUtils.collapseWhitespace(this.statementStartNormalized));
      }

      boolean insideStatementAllowingNestedBeginEndBlocks = this.statementStartNormalized.startsWith("CREATE PROCEDURE") || this.statementStartNormalized.startsWith("CREATE FUNCTION") || this.statementStartNormalized.startsWith("CREATE TRIGGER") || this.statementStartNormalized.startsWith("DO");
      if (insideStatementAllowingNestedBeginEndBlocks) {
         if (line.startsWith("BEGIN")) {
            ++this.beginEndNestedDepth;
         }

         if (line.endsWith("END;")) {
            --this.beginEndNestedDepth;
            if (this.beginEndNestedDepth < 0) {
               LOG.warn("SQL statement parsed unsuccessfully: found unpaired 'END;' in statement");
            }

            if (this.beginEndNestedDepth <= 0) {
               insideStatementAllowingNestedBeginEndBlocks = false;
            }
         }
      }

      return insideStatementAllowingNestedBeginEndBlocks ? null : this.defaultDelimiter;
   }

   private static String cutCommentsFromEnd(String line) {
      if (null == line) {
         return line;
      } else {
         int beginOfLineComment = line.indexOf("--");
         int beginOfBlockComment = line.indexOf("/*");
         if (-1 != beginOfLineComment) {
            return -1 != beginOfBlockComment ? line.substring(0, Math.min(beginOfBlockComment, beginOfLineComment)) : line.substring(0, beginOfLineComment);
         } else {
            return -1 != beginOfBlockComment ? line.substring(0, beginOfBlockComment) : line;
         }
      }
   }
}
