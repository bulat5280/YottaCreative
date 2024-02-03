package org.jooq.tools.jdbc;

import org.jooq.Configuration;

@FunctionalInterface
public interface MockRunnable {
   void run(Configuration var1) throws Exception;
}
