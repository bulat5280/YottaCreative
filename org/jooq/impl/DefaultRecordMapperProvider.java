package org.jooq.impl;

import java.io.Serializable;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.RecordType;

public class DefaultRecordMapperProvider implements RecordMapperProvider, Serializable {
   private static final long serialVersionUID = -5333521849740568028L;
   private final Configuration configuration;

   public DefaultRecordMapperProvider() {
      this((Configuration)null);
   }

   protected DefaultRecordMapperProvider(Configuration configuration) {
      this.configuration = configuration;
   }

   public final <R extends Record, E> RecordMapper<R, E> provide(RecordType<R> rowType, Class<? extends E> type) {
      return new DefaultRecordMapper(rowType, type, this.configuration);
   }
}
