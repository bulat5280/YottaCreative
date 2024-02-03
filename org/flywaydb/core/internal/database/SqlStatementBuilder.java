package org.flywaydb.core.internal.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.flywaydb.core.internal.sqlscript.SqlStatement;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.jdbc.ContextImpl;

public class SqlStatementBuilder {
   protected StringBuilder statement = new StringBuilder();
   protected int lineNumber;
   private boolean empty = true;
   private boolean terminated;
   private boolean insideQuoteStringLiteral = false;
   private boolean insideAlternateQuoteStringLiteral = false;
   private String alternateQuote;
   private boolean lineEndsWithSingleLineComment = false;
   private boolean insideMultiLineComment = false;
   private boolean nonCommentStatementPartSeen = false;
   private int nestedBlockDepth = 0;
   protected boolean executeInTransaction = true;
   protected final Delimiter defaultDelimiter;
   protected Delimiter delimiter;

   public SqlStatementBuilder(Delimiter defaultDelimiter) {
      this.defaultDelimiter = defaultDelimiter;
      this.delimiter = defaultDelimiter;
   }

   public void setLineNumber(int lineNumber) {
      this.lineNumber = lineNumber;
   }

   public void setDelimiter(Delimiter delimiter) {
      this.delimiter = delimiter;
   }

   public boolean isEmpty() {
      return this.empty;
   }

   public boolean isTerminated() {
      return this.terminated;
   }

   public <C extends ContextImpl> SqlStatement<C> getSqlStatement() {
      return new StandardSqlStatement(this.lineNumber, this.statement.toString());
   }

   public Delimiter extractNewDelimiterFromLine(String line) {
      return null;
   }

   public boolean isCommentDirective(String line) {
      return false;
   }

   protected boolean isSingleLineComment(String line) {
      return line.startsWith("--");
   }

   public void addLine(String line) {
      if (this.isEmpty()) {
         this.empty = false;
      } else {
         this.statement.append("\n");
      }

      if (this.isCommentDirective(line.trim())) {
         this.nonCommentStatementPartSeen = true;
      }

      String lineSimplified = this.simplifyLine(line);
      this.applyStateChanges(lineSimplified);
      if (!this.endWithOpenMultilineStringLiteral() && !this.insideMultiLineComment && !this.isSingleLineComment(lineSimplified)) {
         this.delimiter = this.changeDelimiterIfNecessary(lineSimplified, this.delimiter);
         this.statement.append(line);
         if (!this.lineEndsWithSingleLineComment && this.lineTerminatesStatement(lineSimplified, this.delimiter)) {
            stripDelimiter(this.statement, this.delimiter);
            this.terminated = true;
         }

      } else {
         this.statement.append(line);
      }
   }

   boolean endWithOpenMultilineStringLiteral() {
      return this.insideQuoteStringLiteral || this.insideAlternateQuoteStringLiteral;
   }

   public boolean canDiscard() {
      return !this.insideAlternateQuoteStringLiteral && !this.insideQuoteStringLiteral && !this.insideMultiLineComment && !this.nonCommentStatementPartSeen;
   }

   protected String simplifyLine(String line) {
      return this.removeEscapedQuotes(line).replace("--", " -- ").replace("/*", " /* ").replace("*/", " */ ").replaceAll("\\s+", " ").trim().toUpperCase();
   }

   protected Delimiter changeDelimiterIfNecessary(String line, Delimiter delimiter) {
      return delimiter;
   }

   private boolean lineTerminatesStatement(String line, Delimiter delimiter) {
      if (delimiter != null && (!this.defaultDelimiter.equals(delimiter) || this.nestedBlockDepth <= 0)) {
         String upperCaseDelimiter = delimiter.getDelimiter().toUpperCase();
         return delimiter.isAloneOnLine() ? line.equals(upperCaseDelimiter) : line.endsWith(upperCaseDelimiter);
      } else {
         return false;
      }
   }

   static void stripDelimiter(StringBuilder sql, Delimiter delimiter) {
      int last;
      for(last = sql.length(); last > 0 && Character.isWhitespace(sql.charAt(last - 1)); --last) {
      }

      sql.delete(last - delimiter.getDelimiter().length(), sql.length());
   }

   protected String extractAlternateOpenQuote(String token) {
      return null;
   }

   protected String computeAlternateCloseQuote(String openQuote) {
      return openQuote;
   }

   protected void applyStateChanges(String line) {
      String[] tokens = this.tokenizeLine(line);
      List<SqlStatementBuilder.TokenType> delimitingTokens = this.extractStringLiteralDelimitingTokens(tokens);
      this.lineEndsWithSingleLineComment = false;
      Iterator var4 = delimitingTokens.iterator();

      while(true) {
         SqlStatementBuilder.TokenType delimitingToken;
         do {
            do {
               do {
                  do {
                     if (!var4.hasNext()) {
                        return;
                     }

                     delimitingToken = (SqlStatementBuilder.TokenType)var4.next();
                     if (!this.insideQuoteStringLiteral && !this.insideAlternateQuoteStringLiteral && SqlStatementBuilder.TokenType.MULTI_LINE_COMMENT_OPEN.equals(delimitingToken)) {
                        this.insideMultiLineComment = true;
                     }

                     if (!this.insideQuoteStringLiteral && !this.insideAlternateQuoteStringLiteral && SqlStatementBuilder.TokenType.MULTI_LINE_COMMENT_CLOSE.equals(delimitingToken)) {
                        this.insideMultiLineComment = false;
                     }

                     if (!this.insideQuoteStringLiteral && !this.insideAlternateQuoteStringLiteral && !this.insideMultiLineComment && SqlStatementBuilder.TokenType.SINGLE_LINE_COMMENT.equals(delimitingToken)) {
                        this.lineEndsWithSingleLineComment = true;
                        return;
                     }

                     if (!this.insideMultiLineComment && !this.insideQuoteStringLiteral && SqlStatementBuilder.TokenType.ALTERNATE_QUOTE.equals(delimitingToken)) {
                        this.insideAlternateQuoteStringLiteral = !this.insideAlternateQuoteStringLiteral;
                     }

                     if (!this.insideMultiLineComment && !this.insideAlternateQuoteStringLiteral && SqlStatementBuilder.TokenType.QUOTE.equals(delimitingToken)) {
                        this.insideQuoteStringLiteral = !this.insideQuoteStringLiteral;
                     }
                  } while(this.insideMultiLineComment);
               } while(this.insideQuoteStringLiteral);
            } while(this.insideAlternateQuoteStringLiteral);
         } while(!SqlStatementBuilder.TokenType.OTHER.equals(delimitingToken) && !SqlStatementBuilder.TokenType.BLOCK_BEGIN.equals(delimitingToken) && !SqlStatementBuilder.TokenType.BLOCK_END.equals(delimitingToken));

         this.nonCommentStatementPartSeen = true;
         if (this.isBlockStatement()) {
            if (SqlStatementBuilder.TokenType.BLOCK_BEGIN.equals(delimitingToken)) {
               ++this.nestedBlockDepth;
            } else if (SqlStatementBuilder.TokenType.BLOCK_END.equals(delimitingToken)) {
               --this.nestedBlockDepth;
            }
         }
      }
   }

   protected boolean isBlockStatement() {
      return false;
   }

   protected String[] tokenizeLine(String line) {
      return StringUtils.tokenizeToStringArray(line, " @<>;:=|(),+{}");
   }

   private List<SqlStatementBuilder.TokenType> extractStringLiteralDelimitingTokens(String[] tokens) {
      List<SqlStatementBuilder.TokenType> delimitingTokens = new ArrayList();
      String[] var3 = tokens;
      int var4 = tokens.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String token = var3[var5];
         String cleanToken = this.cleanToken(token);
         boolean handled = false;
         if (this.alternateQuote == null) {
            String alternateQuoteFromToken = this.extractAlternateOpenQuote(cleanToken);
            if (alternateQuoteFromToken != null) {
               String closeQuote = this.computeAlternateCloseQuote(alternateQuoteFromToken);
               if (cleanToken.length() < alternateQuoteFromToken.length() + closeQuote.length() || !cleanToken.startsWith(alternateQuoteFromToken) || !cleanToken.endsWith(closeQuote)) {
                  this.alternateQuote = closeQuote;
                  delimitingTokens.add(SqlStatementBuilder.TokenType.ALTERNATE_QUOTE);
               }
               continue;
            }
         }

         if (this.alternateQuote != null && cleanToken.endsWith(this.alternateQuote)) {
            this.alternateQuote = null;
            delimitingTokens.add(SqlStatementBuilder.TokenType.ALTERNATE_QUOTE);
         } else if (cleanToken.length() < 2 || !cleanToken.startsWith("'") || !cleanToken.endsWith("'")) {
            if (cleanToken.length() >= 4) {
               int numberOfOpeningMultiLineComments = StringUtils.countOccurrencesOf(cleanToken, "/*");
               int numberOfClosingMultiLineComments = StringUtils.countOccurrencesOf(cleanToken, "*/");
               if (numberOfOpeningMultiLineComments > 0 && numberOfOpeningMultiLineComments == numberOfClosingMultiLineComments) {
                  continue;
               }
            }

            if (this.isSingleLineComment(cleanToken)) {
               delimitingTokens.add(SqlStatementBuilder.TokenType.SINGLE_LINE_COMMENT);
               handled = true;
            }

            if (cleanToken.contains("/*")) {
               delimitingTokens.add(SqlStatementBuilder.TokenType.MULTI_LINE_COMMENT_OPEN);
               handled = true;
            } else if (cleanToken.startsWith("'")) {
               delimitingTokens.add(SqlStatementBuilder.TokenType.QUOTE);
               handled = true;
            }

            if (!cleanToken.contains("/*") && cleanToken.contains("*/")) {
               delimitingTokens.add(SqlStatementBuilder.TokenType.MULTI_LINE_COMMENT_CLOSE);
               handled = true;
            } else if (!cleanToken.startsWith("'") && cleanToken.endsWith("'")) {
               delimitingTokens.add(SqlStatementBuilder.TokenType.QUOTE);
               handled = true;
            }

            if (!handled) {
               if (this.isBlockBeginToken(cleanToken)) {
                  delimitingTokens.add(SqlStatementBuilder.TokenType.BLOCK_BEGIN);
               } else if (this.isBlockEndToken(cleanToken)) {
                  delimitingTokens.add(SqlStatementBuilder.TokenType.BLOCK_END);
               } else {
                  delimitingTokens.add(SqlStatementBuilder.TokenType.OTHER);
               }
            }
         }
      }

      return delimitingTokens;
   }

   protected boolean isBlockBeginToken(String token) {
      return false;
   }

   protected boolean isBlockEndToken(String token) {
      return false;
   }

   protected String removeEscapedQuotes(String token) {
      return StringUtils.replaceAll(token, "''", "");
   }

   protected String cleanToken(String token) {
      return token;
   }

   public boolean executeInTransaction() {
      return this.executeInTransaction;
   }

   private static enum TokenType {
      OTHER,
      QUOTE,
      ALTERNATE_QUOTE,
      SINGLE_LINE_COMMENT,
      MULTI_LINE_COMMENT_OPEN,
      MULTI_LINE_COMMENT_CLOSE,
      BLOCK_BEGIN,
      BLOCK_END;
   }
}
