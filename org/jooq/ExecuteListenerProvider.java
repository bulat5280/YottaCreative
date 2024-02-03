package org.jooq;

@FunctionalInterface
public interface ExecuteListenerProvider {
   ExecuteListener provide();
}
