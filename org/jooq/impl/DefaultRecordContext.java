package org.jooq.impl;

import java.util.Map;
import org.jooq.Configuration;
import org.jooq.ExecuteType;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.RecordType;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;

class DefaultRecordContext implements RecordContext {
   private final Configuration configuration;
   private final Map<Object, Object> data;
   private final ExecuteType type;
   private final Record[] records;
   Exception exception;

   DefaultRecordContext(Configuration configuration, ExecuteType type, Record... records) {
      this.configuration = configuration;
      this.type = type;
      this.data = new DataMap();
      this.records = records;
   }

   public final Map<Object, Object> data() {
      return this.data;
   }

   public final Object data(Object key) {
      return this.data.get(key);
   }

   public final Object data(Object key, Object value) {
      return this.data.put(key, value);
   }

   public final Configuration configuration() {
      return this.configuration;
   }

   public final Settings settings() {
      return Tools.settings(this.configuration());
   }

   public final SQLDialect dialect() {
      return Tools.configuration(this.configuration()).dialect();
   }

   public final SQLDialect family() {
      return this.dialect().family();
   }

   public final ExecuteType type() {
      return this.type;
   }

   public final Record record() {
      return this.records != null && this.records.length > 0 ? this.records[0] : null;
   }

   public final Record[] batchRecords() {
      return this.records;
   }

   public final RecordType<?> recordType() {
      Record record = this.record();
      return record != null ? new Fields(record.fields()) : null;
   }

   public final Exception exception() {
      return this.exception;
   }
}
