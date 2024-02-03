package org.jooq;

@FunctionalInterface
public interface VisitListenerProvider {
   VisitListener provide();
}
