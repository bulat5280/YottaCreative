package org.jooq;

import java.sql.Connection;

@FunctionalInterface
public interface ConnectionCallable<T> {
   T run(Connection var1) throws Exception;
}
