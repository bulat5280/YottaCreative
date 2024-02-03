package org.jooq;

import java.util.concurrent.Executor;

@FunctionalInterface
public interface ExecutorProvider {
   Executor provide();
}
