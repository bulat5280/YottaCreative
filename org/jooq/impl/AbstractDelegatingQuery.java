package org.jooq.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import org.jooq.AttachableInternal;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Param;
import org.jooq.Query;
import org.jooq.conf.ParamType;

abstract class AbstractDelegatingQuery<Q extends Query> extends AbstractQueryPart implements Query {
   private static final long serialVersionUID = 6710523592699040547L;
   private final Q delegate;

   AbstractDelegatingQuery(Q delegate) {
      this.delegate = delegate;
   }

   public final Configuration configuration() {
      return this.delegate instanceof AttachableInternal ? ((AttachableInternal)this.delegate).configuration() : super.configuration();
   }

   public final List<Object> getBindValues() {
      return this.delegate.getBindValues();
   }

   public final Map<String, Param<?>> getParams() {
      return this.delegate.getParams();
   }

   public final Param<?> getParam(String name) {
      return this.delegate.getParam(name);
   }

   public final void accept(Context<?> context) {
      context.visit(this.delegate);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   public final String getSQL() {
      return this.delegate.getSQL();
   }

   /** @deprecated */
   @Deprecated
   public final String getSQL(boolean inline) {
      return this.delegate.getSQL(inline);
   }

   public final String getSQL(ParamType paramType) {
      return this.delegate.getSQL(paramType);
   }

   public final void attach(Configuration configuration) {
      this.delegate.attach(configuration);
   }

   public final void detach() {
      this.delegate.detach();
   }

   public final int execute() {
      return this.delegate.execute();
   }

   public final CompletionStage<Integer> executeAsync() {
      return this.delegate.executeAsync();
   }

   public final CompletionStage<Integer> executeAsync(Executor executor) {
      return this.delegate.executeAsync(executor);
   }

   public final boolean isExecutable() {
      return this.delegate.isExecutable();
   }

   public final Q bind(String param, Object value) {
      return this.delegate.bind(param, value);
   }

   public final Q bind(int index, Object value) {
      return this.delegate.bind(index, value);
   }

   public final Q queryTimeout(int timeout) {
      return this.delegate.queryTimeout(timeout);
   }

   public final Q keepStatement(boolean keepStatement) {
      return this.delegate.keepStatement(keepStatement);
   }

   public final void close() {
      this.delegate.close();
   }

   public final void cancel() {
      this.delegate.cancel();
   }

   final Q getDelegate() {
      return this.delegate;
   }
}
