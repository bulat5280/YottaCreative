package org.jooq.impl;

import org.jooq.Record;

@FunctionalInterface
interface RecordFactory<R extends Record> {
   R newInstance();
}
