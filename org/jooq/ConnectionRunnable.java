package org.jooq;

import java.sql.Connection;

@FunctionalInterface
public interface ConnectionRunnable {
   void run(Connection var1) throws Exception;
}
