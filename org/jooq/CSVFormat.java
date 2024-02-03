package org.jooq;

public final class CSVFormat {
   final String delimiter;
   final String nullString;
   final String emptyString;
   final String newline;
   final String quoteString;
   final CSVFormat.Quote quote;
   final boolean header;

   public CSVFormat() {
      this(",", "\"\"", "\"\"", "\n", "\"", CSVFormat.Quote.SPECIAL_CHARACTERS, true);
   }

   private CSVFormat(String delimiter, String nullString, String emptyString, String newline, String quoteString, CSVFormat.Quote quote, boolean header) {
      this.delimiter = delimiter;
      this.nullString = nullString;
      this.emptyString = emptyString;
      this.newline = newline;
      this.quoteString = quoteString;
      this.quote = quote;
      this.header = header;
   }

   public CSVFormat delimiter(String newDelimiter) {
      return new CSVFormat(newDelimiter, this.nullString, this.emptyString, this.newline, this.quoteString, this.quote, this.header);
   }

   public CSVFormat delimiter(char newDelimiter) {
      return this.delimiter("" + newDelimiter);
   }

   public String delimiter() {
      return this.delimiter;
   }

   public CSVFormat nullString(String newNullString) {
      return new CSVFormat(this.delimiter, newNullString, this.emptyString, this.newline, this.quoteString, this.quote, this.header);
   }

   public String nullString() {
      return this.nullString;
   }

   public CSVFormat emptyString(String newEmptyString) {
      return new CSVFormat(this.delimiter, this.nullString, newEmptyString, this.newline, this.quoteString, this.quote, this.header);
   }

   public String emptyString() {
      return this.emptyString;
   }

   public CSVFormat newline(String newNewline) {
      return new CSVFormat(this.delimiter, this.nullString, this.emptyString, newNewline, this.quoteString, this.quote, this.header);
   }

   public String newline() {
      return this.newline;
   }

   public CSVFormat quoteString(String newQuoteString) {
      return new CSVFormat(this.delimiter, this.nullString, this.emptyString, this.newline, newQuoteString, this.quote, this.header);
   }

   public String quoteString() {
      return this.quoteString;
   }

   public CSVFormat quote(CSVFormat.Quote newQuote) {
      return new CSVFormat(this.delimiter, this.nullString, this.emptyString, this.newline, this.quoteString, newQuote, this.header);
   }

   public CSVFormat.Quote quote() {
      return this.quote;
   }

   public CSVFormat header(boolean newHeader) {
      return new CSVFormat(this.delimiter, this.nullString, this.emptyString, this.newline, this.quoteString, this.quote, newHeader);
   }

   public boolean header() {
      return this.header;
   }

   public static enum Quote {
      ALWAYS,
      SPECIAL_CHARACTERS,
      NEVER;
   }
}
