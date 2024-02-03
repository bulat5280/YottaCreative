package org.jooq.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jooq.Attachable;
import org.jooq.ConditionProvider;
import org.jooq.Configuration;
import org.jooq.DeleteQuery;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
import org.jooq.SelectQuery;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableRecord;
import org.jooq.UniqueKey;
import org.jooq.UpdatableRecord;
import org.jooq.UpdateQuery;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataChangedException;
import org.jooq.exception.NoDataFoundException;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

public class UpdatableRecordImpl<R extends UpdatableRecord<R>> extends TableRecordImpl<R> implements UpdatableRecord<R> {
   private static final long serialVersionUID = -1012420583600561579L;
   private static final JooqLogger log = JooqLogger.getLogger(UpdatableRecordImpl.class);

   public UpdatableRecordImpl(Table<R> table) {
      super(table);
   }

   public Record key() {
      RecordImpl result = new RecordImpl(this.getPrimaryKey().getFields());
      result.setValues(result.fields.fields.fields, this);
      return result;
   }

   public final <O extends TableRecord<O>> O fetchChild(ForeignKey<O, R> key) {
      return (TableRecord)Tools.filterOne(this.fetchChildren(key));
   }

   public final <O extends TableRecord<O>> Result<O> fetchChildren(ForeignKey<O, R> key) {
      return key.fetchChildren((Record)this);
   }

   final UniqueKey<R> getPrimaryKey() {
      return this.getTable().getPrimaryKey();
   }

   public final int store() {
      return this.store(this.fields.fields.fields);
   }

   public final int store(final Field<?>... storeFields) {
      final int[] result = new int[1];
      RecordDelegate.delegate(this.configuration(), this, RecordDelegate.RecordLifecycleType.STORE).operate(new RecordOperation<Record, RuntimeException>() {
         public Record operate(Record record) throws RuntimeException {
            result[0] = UpdatableRecordImpl.this.store0(storeFields);
            return record;
         }
      });
      return result[0];
   }

   public final int store(Collection<? extends Field<?>> storeFields) {
      return this.store((Field[])storeFields.toArray(Tools.EMPTY_FIELD));
   }

   public final int update() {
      return this.update(this.fields.fields.fields);
   }

   public int update(Field<?>... storeFields) {
      return this.storeUpdate(storeFields, this.getPrimaryKey().getFieldsArray());
   }

   public final int update(Collection<? extends Field<?>> storeFields) {
      return this.update((Field[])storeFields.toArray(Tools.EMPTY_FIELD));
   }

   private final int store0(Field<?>[] storeFields) {
      TableField<R, ?>[] keys = this.getPrimaryKey().getFieldsArray();
      boolean executeUpdate = false;
      if (SettingsTools.updatablePrimaryKeys(Tools.settings((Attachable)this))) {
         executeUpdate = this.fetched;
      } else {
         TableField[] var4 = keys;
         int var5 = keys.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            TableField<R, ?> field = var4[var6];
            if (this.changed(field) || !field.getDataType().nullable() && this.get(field) == null) {
               executeUpdate = false;
               break;
            }

            executeUpdate = true;
         }
      }

      int result = false;
      int result;
      if (executeUpdate) {
         result = this.storeUpdate(storeFields, keys);
      } else {
         result = this.storeInsert(storeFields);
      }

      return result;
   }

   private final int storeUpdate(final Field<?>[] storeFields, final TableField<R, ?>[] keys) {
      final int[] result = new int[1];
      RecordDelegate.delegate(this.configuration(), this, RecordDelegate.RecordLifecycleType.UPDATE).operate(new RecordOperation<Record, RuntimeException>() {
         public Record operate(Record record) throws RuntimeException {
            result[0] = UpdatableRecordImpl.this.storeUpdate0(storeFields, keys);
            return record;
         }
      });
      return result[0];
   }

   private final int storeUpdate0(Field<?>[] storeFields, TableField<R, ?>[] keys) {
      UpdateQuery<R> update = this.create().updateQuery(this.getTable());
      this.addChangedValues(storeFields, update);
      Tools.addConditions(update, this, keys);
      if (!update.isExecutable()) {
         if (log.isDebugEnabled()) {
            log.debug("Query is not executable", (Object)update);
         }

         return 0;
      } else {
         BigInteger version = this.addRecordVersion(update);
         Timestamp timestamp = this.addRecordTimestamp(update);
         if (this.isExecuteWithOptimisticLocking()) {
            if (this.isTimestampOrVersionAvailable()) {
               this.addConditionForVersionAndTimestamp(update);
            } else if (this.isExecuteWithOptimisticLockingIncludeUnversioned()) {
               this.checkIfChanged(keys);
            }
         }

         Collection<Field<?>> key = this.setReturningIfNeeded(update);
         int result = update.execute();
         this.checkIfChanged(result, version, timestamp);
         if (result > 0) {
            Field[] var8 = storeFields;
            int var9 = storeFields.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               Field<?> storeField = var8[var10];
               this.changed(storeField, false);
            }

            this.getReturningIfNeeded(update, key);
         }

         return result;
      }
   }

   public final int delete() {
      final int[] result = new int[1];
      RecordDelegate.delegate(this.configuration(), this, RecordDelegate.RecordLifecycleType.DELETE).operate(new RecordOperation<Record, RuntimeException>() {
         public Record operate(Record record) throws RuntimeException {
            result[0] = UpdatableRecordImpl.this.delete0();
            return record;
         }
      });
      return result[0];
   }

   private final int delete0() {
      TableField[] keys = this.getPrimaryKey().getFieldsArray();

      int var4;
      try {
         DeleteQuery<R> delete1 = this.create().deleteQuery(this.getTable());
         Tools.addConditions(delete1, this, keys);
         if (this.isExecuteWithOptimisticLocking()) {
            if (this.isTimestampOrVersionAvailable()) {
               this.addConditionForVersionAndTimestamp(delete1);
            } else if (this.isExecuteWithOptimisticLockingIncludeUnversioned()) {
               this.checkIfChanged(keys);
            }
         }

         int result = delete1.execute();
         this.checkIfChanged(result, (BigInteger)null, (Timestamp)null);
         var4 = result;
      } finally {
         this.changed(true);
         this.fetched = false;
      }

      return var4;
   }

   public final void refresh() {
      this.refresh(this.fields.fields.fields);
   }

   public final void refresh(final Field<?>... refreshFields) {
      SelectQuery<Record> select = this.create().selectQuery();
      select.addSelect((SelectField[])refreshFields);
      select.addFrom((TableLike)this.getTable());
      Tools.addConditions(select, this, this.getPrimaryKey().getFieldsArray());
      if (select.execute() == 1) {
         final AbstractRecord source = (AbstractRecord)select.getResult().get(0);
         RecordDelegate.delegate(this.configuration(), this, RecordDelegate.RecordLifecycleType.REFRESH).operate(new RecordOperation<Record, RuntimeException>() {
            public Record operate(Record record) throws RuntimeException {
               UpdatableRecordImpl.this.setValues(refreshFields, source);
               return record;
            }
         });
      } else {
         throw new NoDataFoundException("Exactly one row expected for refresh. Record does not exist in database.");
      }
   }

   public final void refresh(Collection<? extends Field<?>> refreshFields) {
      this.refresh((Field[])refreshFields.toArray(Tools.EMPTY_FIELD));
   }

   public final R copy() {
      return (UpdatableRecord)Tools.newRecord(false, this.getTable(), this.configuration()).operate(new RecordOperation<R, RuntimeException>() {
         public R operate(R copy) throws RuntimeException {
            List<TableField<R, ?>> key = UpdatableRecordImpl.this.getPrimaryKey().getFields();
            Field[] var3 = UpdatableRecordImpl.this.fields.fields.fields;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Field<?> field = var3[var5];
               if (!key.contains(field)) {
                  this.setValue(copy, field);
               }
            }

            return copy;
         }

         private final <T> void setValue(Record record, Field<T> field) {
            record.set(field, UpdatableRecordImpl.this.get(field));
         }
      });
   }

   private final boolean isExecuteWithOptimisticLocking() {
      Configuration configuration = this.configuration();
      return configuration != null ? Boolean.TRUE.equals(configuration.settings().isExecuteWithOptimisticLocking()) : false;
   }

   private final boolean isExecuteWithOptimisticLockingIncludeUnversioned() {
      Configuration configuration = this.configuration();
      return configuration != null ? !Boolean.TRUE.equals(configuration.settings().isExecuteWithOptimisticLockingExcludeUnversioned()) : true;
   }

   private final void addConditionForVersionAndTimestamp(ConditionProvider query) {
      TableField<R, ?> v = this.getTable().getRecordVersion();
      TableField<R, ?> t = this.getTable().getRecordTimestamp();
      if (v != null) {
         Tools.addCondition(query, this, v);
      }

      if (t != null) {
         Tools.addCondition(query, this, t);
      }

   }

   private final void checkIfChanged(TableField<R, ?>[] keys) {
      SelectQuery<R> select = this.create().selectQuery(this.getTable());
      Tools.addConditions(select, this, keys);
      if (!Arrays.asList(SQLDialect.SQLITE).contains(this.create().configuration().dialect().family())) {
         select.setForUpdate(true);
      }

      R record = (UpdatableRecord)select.fetchOne();
      if (record == null) {
         throw new DataChangedException("Database record no longer exists");
      } else {
         Field[] var4 = this.fields.fields.fields;
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Field<?> field = var4[var6];
            Object thisObject = this.original(field);
            Object thatObject = record.original(field);
            if (!StringUtils.equals(thisObject, thatObject)) {
               throw new DataChangedException("Database record has been changed");
            }
         }

      }
   }

   private final void checkIfChanged(int result, BigInteger version, Timestamp timestamp) {
      if (result > 0) {
         this.setRecordVersionAndTimestamp(version, timestamp);
      } else if (this.isExecuteWithOptimisticLocking()) {
         throw new DataChangedException("Database record has been changed or doesn't exist any longer");
      }

   }
}
