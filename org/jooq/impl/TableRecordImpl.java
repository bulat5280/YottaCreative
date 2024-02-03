package org.jooq.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import org.jooq.Converter;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.InsertQuery;
import org.jooq.Record;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.StoreQuery;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableRecord;
import org.jooq.UniqueKey;
import org.jooq.UpdatableRecord;
import org.jooq.tools.JooqLogger;

public class TableRecordImpl<R extends TableRecord<R>> extends AbstractRecord implements TableRecord<R> {
   private static final long serialVersionUID = 3216746611562261641L;
   private static final JooqLogger log = JooqLogger.getLogger(TableRecordImpl.class);
   private final Table<R> table;

   public TableRecordImpl(Table<R> table) {
      super(table.fields());
      this.table = table;
   }

   public final <T> R with(Field<T> field, T value) {
      return (TableRecord)super.with(field, value);
   }

   public final <T, U> R with(Field<T> field, U value, Converter<? extends T, ? super U> converter) {
      return (TableRecord)super.with(field, value, converter);
   }

   public final Table<R> getTable() {
      return this.table;
   }

   public Row fieldsRow() {
      return this.fields;
   }

   public Row valuesRow() {
      return new RowImpl(Tools.fields(this.intoArray(), this.fields.fields.fields()));
   }

   public final R original() {
      return (TableRecord)super.original();
   }

   public final <O extends UpdatableRecord<O>> O fetchParent(ForeignKey<R, O> key) {
      return (UpdatableRecord)key.fetchParent(this);
   }

   public final int insert() {
      return this.insert(this.fields.fields.fields);
   }

   public final int insert(Field<?>... storeFields) {
      return this.storeInsert(storeFields);
   }

   public final int insert(Collection<? extends Field<?>> storeFields) {
      return this.insert((Field[])storeFields.toArray(Tools.EMPTY_FIELD));
   }

   final int storeInsert(final Field<?>[] storeFields) {
      final int[] result = new int[1];
      RecordDelegate.delegate(this.configuration(), this, RecordDelegate.RecordLifecycleType.INSERT).operate(new RecordOperation<Record, RuntimeException>() {
         public Record operate(Record record) throws RuntimeException {
            result[0] = TableRecordImpl.this.storeInsert0(storeFields);
            return record;
         }
      });
      return result[0];
   }

   final int storeInsert0(Field<?>[] storeFields) {
      DSLContext create = this.create();
      InsertQuery<R> insert = create.insertQuery(this.getTable());
      this.addChangedValues(storeFields, insert);
      if (!insert.isExecutable()) {
         if (log.isDebugEnabled()) {
            log.debug("Query is not executable", (Object)insert);
         }

         return 0;
      } else {
         BigInteger version = this.addRecordVersion(insert);
         Timestamp timestamp = this.addRecordTimestamp(insert);
         Collection<Field<?>> key = this.setReturningIfNeeded(insert);
         int result = insert.execute();
         if (result > 0) {
            Field[] var8 = storeFields;
            int var9 = storeFields.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               Field<?> storeField = var8[var10];
               this.changed(storeField, false);
            }

            this.setRecordVersionAndTimestamp(version, timestamp);
            this.getReturningIfNeeded(insert, key);
            this.fetched = true;
         }

         return result;
      }
   }

   final void getReturningIfNeeded(StoreQuery<R> query, Collection<Field<?>> key) {
      if (key != null && !key.isEmpty()) {
         R record = (TableRecord)query.getReturnedRecord();
         int index;
         Object value;
         if (record != null) {
            for(Iterator var4 = key.iterator(); var4.hasNext(); this.originals[index] = value) {
               Field<?> field = (Field)var4.next();
               index = Tools.indexOrFail(this.fieldsRow(), field);
               value = record.get(field);
               this.values[index] = value;
            }
         }

         if (Arrays.asList(SQLDialect.DERBY, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MYSQL).contains(this.configuration().family()) && this instanceof UpdatableRecord) {
            ((UpdatableRecord)this).refresh((Field[])key.toArray(Tools.EMPTY_FIELD));
         }
      }

   }

   final Collection<Field<?>> setReturningIfNeeded(StoreQuery<R> query) {
      Collection<Field<?>> key = null;
      if (this.configuration() != null && !Boolean.TRUE.equals(this.configuration().data(Tools.DataKey.DATA_OMIT_RETURNING_CLAUSE))) {
         if (Boolean.TRUE.equals(this.configuration().settings().isReturnAllOnUpdatableRecord())) {
            key = Arrays.asList(this.fields());
         } else {
            key = this.getReturning();
         }

         query.setReturning((Collection)key);
      }

      return (Collection)key;
   }

   final void setRecordVersionAndTimestamp(BigInteger version, Timestamp timestamp) {
      TableField field;
      int fieldIndex;
      Object value;
      if (version != null) {
         field = this.getTable().getRecordVersion();
         fieldIndex = Tools.indexOrFail((Row)this.fieldsRow(), (Field)field);
         value = field.getDataType().convert((Object)version);
         this.values[fieldIndex] = value;
         this.originals[fieldIndex] = value;
         this.changed.clear(fieldIndex);
      }

      if (timestamp != null) {
         field = this.getTable().getRecordTimestamp();
         fieldIndex = Tools.indexOrFail((Row)this.fieldsRow(), (Field)field);
         value = field.getDataType().convert((Object)timestamp);
         this.values[fieldIndex] = value;
         this.originals[fieldIndex] = value;
         this.changed.clear(fieldIndex);
      }

   }

   final void addChangedValues(Field<?>[] storeFields, StoreQuery<R> query) {
      Fields<Record> f = new Fields(storeFields);
      Field[] var4 = this.fields.fields.fields;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Field<?> field = var4[var6];
         if (this.changed(field) && f.field(field) != null) {
            this.addValue(query, field);
         }
      }

   }

   final <T> void addValue(StoreQuery<?> store, Field<T> field, Object value) {
      store.addValue(field, Tools.field(value, field));
   }

   final <T> void addValue(StoreQuery<?> store, Field<T> field) {
      this.addValue(store, field, this.get(field));
   }

   final Timestamp addRecordTimestamp(StoreQuery<?> store) {
      Timestamp result = null;
      if (this.isTimestampOrVersionAvailable()) {
         TableField<R, ?> timestamp = this.getTable().getRecordTimestamp();
         if (timestamp != null) {
            result = new Timestamp(System.currentTimeMillis());
            this.addValue(store, timestamp, result);
         }
      }

      return result;
   }

   final BigInteger addRecordVersion(StoreQuery<?> store) {
      BigInteger result = null;
      if (this.isTimestampOrVersionAvailable()) {
         TableField<R, ?> version = this.getTable().getRecordVersion();
         if (version != null) {
            Object value = this.get(version);
            if (value == null) {
               result = BigInteger.ONE;
            } else {
               result = (new BigInteger(value.toString())).add(BigInteger.ONE);
            }

            this.addValue(store, version, result);
         }
      }

      return result;
   }

   final boolean isTimestampOrVersionAvailable() {
      return this.getTable().getRecordTimestamp() != null || this.getTable().getRecordVersion() != null;
   }

   final Collection<Field<?>> getReturning() {
      Collection<Field<?>> result = new LinkedHashSet();
      Identity<R, ?> identity = this.getTable().getIdentity();
      if (identity != null) {
         result.add(identity.getField());
      }

      UniqueKey<?> key = this.getPrimaryKey();
      if (key != null) {
         result.addAll(key.getFields());
      }

      return result;
   }
}
