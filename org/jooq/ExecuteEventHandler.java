package org.jooq;

@FunctionalInterface
public interface ExecuteEventHandler {
   void fire(ExecuteContext var1);
}
