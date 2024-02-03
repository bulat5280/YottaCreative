package org.jooq;

@FunctionalInterface
public interface RecordListenerProvider {
   RecordListener provide();
}
