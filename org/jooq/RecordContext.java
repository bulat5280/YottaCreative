package org.jooq;

public interface RecordContext extends Scope {
   ExecuteType type();

   Record record();

   RecordType<?> recordType();

   Record[] batchRecords();

   Exception exception();
}
