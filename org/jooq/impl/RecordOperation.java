package org.jooq.impl;

import org.jooq.Record;

interface RecordOperation<R extends Record, E extends Exception> {
   R operate(R var1) throws E;
}
