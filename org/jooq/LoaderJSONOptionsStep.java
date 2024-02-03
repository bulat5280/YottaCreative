package org.jooq;

public interface LoaderJSONOptionsStep<R extends Record> extends LoaderListenerStep<R> {
   /** @deprecated */
   @Deprecated
   @Support
   LoaderJSONOptionsStep<R> ignoreRows(int var1);
}
