package org.jooq.util.firebird.rdb;

import org.jooq.Sequence;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SequenceImpl;

public class Sequences {
   public static final Sequence<Long> RDB$PROCEDURES;

   static {
      RDB$PROCEDURES = new SequenceImpl("RDB$PROCEDURES", DefaultSchema.DEFAULT_SCHEMA, SQLDataType.BIGINT);
   }
}
