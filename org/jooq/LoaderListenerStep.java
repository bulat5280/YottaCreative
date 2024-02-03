package org.jooq;

public interface LoaderListenerStep<R extends Record> extends LoaderLoadStep<R> {
   @Support
   LoaderLoadStep<R> onRow(LoaderRowListener var1);
}
