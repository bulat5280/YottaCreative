package org.jooq.impl;

import java.io.Serializable;
import java.util.concurrent.Executor;
import org.jooq.ExecutorProvider;

public class DefaultExecutorProvider implements ExecutorProvider, Serializable {
   private static final long serialVersionUID = -5333521849740568028L;

   public final Executor provide() {
      return new DefaultExecutor();
   }
}
