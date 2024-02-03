package org.jooq;

public interface LoaderCSVOptionsStep<R extends Record> extends LoaderListenerStep<R> {
   @Support
   LoaderCSVOptionsStep<R> ignoreRows(int var1);

   @Support
   LoaderCSVOptionsStep<R> quote(char var1);

   @Support
   LoaderCSVOptionsStep<R> separator(char var1);

   @Support
   LoaderCSVOptionsStep<R> nullString(String var1);
}
