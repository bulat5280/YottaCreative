package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DAO;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.UpdatableRecord;
import org.jooq.conf.Settings;

public abstract class DAOImpl<R extends UpdatableRecord<R>, P, T> implements DAO<R, P, T> {
   private final Table<R> table;
   private final Class<P> type;
   private RecordMapper<R, P> mapper;
   private Configuration configuration;

   protected DAOImpl(Table<R> table, Class<P> type) {
      this(table, type, (Configuration)null);
   }

   protected DAOImpl(Table<R> table, Class<P> type, Configuration configuration) {
      this.table = table;
      this.type = type;
      this.setConfiguration(configuration);
   }

   public void setConfiguration(Configuration configuration) {
      this.configuration = configuration;
      this.mapper = Tools.configuration(configuration).recordMapperProvider().provide(this.table.recordType(), this.type);
   }

   public Configuration configuration() {
      return this.configuration;
   }

   public Settings settings() {
      return Tools.settings(this.configuration());
   }

   public SQLDialect dialect() {
      return Tools.configuration(this.configuration()).dialect();
   }

   public SQLDialect family() {
      return this.dialect().family();
   }

   public RecordMapper<R, P> mapper() {
      return this.mapper;
   }

   public void insert(P object) {
      this.insert((Collection)Collections.singletonList(object));
   }

   public void insert(P... objects) {
      this.insert((Collection)Arrays.asList(objects));
   }

   public void insert(Collection<P> objects) {
      if (objects.size() > 1) {
         DSL.using(this.configuration).batchInsert((Collection)this.records(objects, false)).execute();
      } else if (objects.size() == 1) {
         ((UpdatableRecord)this.records(objects, false).get(0)).insert();
      }

   }

   public void update(P object) {
      this.update((Collection)Collections.singletonList(object));
   }

   public void update(P... objects) {
      this.update((Collection)Arrays.asList(objects));
   }

   public void update(Collection<P> objects) {
      if (objects.size() > 1) {
         DSL.using(this.configuration).batchUpdate((Collection)this.records(objects, true)).execute();
      } else if (objects.size() == 1) {
         ((UpdatableRecord)this.records(objects, true).get(0)).update();
      }

   }

   public void delete(P object) {
      this.delete((Collection)Collections.singletonList(object));
   }

   public void delete(P... objects) {
      this.delete((Collection)Arrays.asList(objects));
   }

   public void delete(Collection<P> objects) {
      if (objects.size() > 1) {
         DSL.using(this.configuration).batchDelete((Collection)this.records(objects, true)).execute();
      } else if (objects.size() == 1) {
         ((UpdatableRecord)this.records(objects, true).get(0)).delete();
      }

   }

   public void deleteById(T... ids) {
      this.deleteById((Collection)Arrays.asList(ids));
   }

   public void deleteById(Collection<T> ids) {
      Field<?>[] pk = this.pk();
      if (pk != null) {
         DSL.using(this.configuration).delete(this.table).where(this.equal(pk, ids)).execute();
      }

   }

   public boolean exists(P object) {
      return this.existsById(this.getId(object));
   }

   public boolean existsById(T id) {
      Field<?>[] pk = this.pk();
      if (pk != null) {
         return (Integer)DSL.using(this.configuration).selectCount().from(this.table).where(new Condition[]{this.equal(pk, id)}).fetchOne(0, Integer.class) > 0;
      } else {
         return false;
      }
   }

   public long count() {
      return (Long)DSL.using(this.configuration).selectCount().from(this.table).fetchOne(0, Long.class);
   }

   public List<P> findAll() {
      return DSL.using(this.configuration).selectFrom(this.table).fetch().map(this.mapper());
   }

   public P findById(T id) {
      Field<?>[] pk = this.pk();
      R record = null;
      if (pk != null) {
         record = (UpdatableRecord)DSL.using(this.configuration).selectFrom(this.table).where(this.equal(pk, id)).fetchOne();
      }

      return record == null ? null : this.mapper().map(record);
   }

   public <Z> List<P> fetch(Field<Z> field, Z... values) {
      return DSL.using(this.configuration).selectFrom(this.table).where(field.in(values)).fetch().map(this.mapper());
   }

   public <Z> P fetchOne(Field<Z> field, Z value) {
      R record = (UpdatableRecord)DSL.using(this.configuration).selectFrom(this.table).where(field.equal(value)).fetchOne();
      return record == null ? null : this.mapper().map(record);
   }

   public <Z> Optional<P> fetchOptional(Field<Z> field, Z value) {
      return Optional.ofNullable(this.fetchOne(field, value));
   }

   public Table<R> getTable() {
      return this.table;
   }

   public Class<P> getType() {
      return this.type;
   }

   protected abstract T getId(P var1);

   protected T compositeKeyRecord(Object... values) {
      UniqueKey<R> key = this.table.getPrimaryKey();
      if (key == null) {
         return null;
      } else {
         TableField<R, Object>[] fields = (TableField[])key.getFieldsArray();
         Record result = DSL.using(this.configuration).newRecord((Field[])fields);

         for(int i = 0; i < values.length; ++i) {
            result.set(fields[i], fields[i].getDataType().convert(values[i]));
         }

         return result;
      }
   }

   private Condition equal(Field<?>[] pk, T id) {
      return pk.length == 1 ? pk[0].equal(pk[0].getDataType().convert(id)) : DSL.row(pk).equal((Record)id);
   }

   private Condition equal(Field<?>[] pk, Collection<T> ids) {
      if (pk.length == 1) {
         return ids.size() == 1 ? this.equal(pk, ids.iterator().next()) : pk[0].in((Collection)pk[0].getDataType().convert(ids));
      } else {
         return DSL.row(pk).in((Record[])ids.toArray(Tools.EMPTY_RECORD));
      }
   }

   private Field<?>[] pk() {
      UniqueKey<?> key = this.table.getPrimaryKey();
      return key == null ? null : key.getFieldsArray();
   }

   private List<R> records(Collection<P> objects, boolean forUpdate) {
      List<R> result = new ArrayList();
      Field<?>[] pk = this.pk();
      Iterator var5 = objects.iterator();

      while(var5.hasNext()) {
         P object = var5.next();
         R record = (UpdatableRecord)DSL.using(this.configuration).newRecord(this.table, object);
         if (forUpdate && pk != null) {
            Field[] var8 = pk;
            int var9 = pk.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               Field<?> field = var8[var10];
               record.changed(field, false);
            }
         }

         Tools.resetChangedOnNotNull(record);
         result.add(record);
      }

      return result;
   }
}
