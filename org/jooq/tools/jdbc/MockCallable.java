package org.jooq.tools.jdbc;

import org.jooq.Configuration;

@FunctionalInterface
public interface MockCallable<T> {
   T run(Configuration var1) throws Exception;
}
