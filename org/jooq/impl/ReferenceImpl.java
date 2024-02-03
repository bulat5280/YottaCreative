package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.AttachableInternal;
import org.jooq.Constraint;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.RowN;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.exception.DetachedException;

final class ReferenceImpl<R extends Record, O extends Record> extends AbstractKey<R> implements ForeignKey<R, O> {
   private static final long serialVersionUID = 3636724364192618701L;
   private final UniqueKey<O> key;

   @SafeVarargs
   ReferenceImpl(UniqueKey<O> key, Table<R> table, TableField<R, ?>... fields) {
      this(key, table, (String)null, fields);
   }

   @SafeVarargs
   ReferenceImpl(UniqueKey<O> key, Table<R> table, String name, TableField<R, ?>... fields) {
      super(table, name, fields);
      this.key = key;
   }

   public final UniqueKey<O> getKey() {
      return this.key;
   }

   public final O fetchParent(R record) {
      return Tools.filterOne(this.fetchParents(record));
   }

   @SafeVarargs
   public final Result<O> fetchParents(R... records) {
      return this.fetchParents((Collection)Tools.list(records));
   }

   public final Result<R> fetchChildren(O record) {
      return this.fetchChildren((Collection)Tools.list(record));
   }

   @SafeVarargs
   public final Result<R> fetchChildren(O... records) {
      return this.fetchChildren((Collection)Tools.list(records));
   }

   public final Result<O> fetchParents(Collection<? extends R> records) {
      return (Result)(records != null && records.size() != 0 ? fetch(records, this.key.getTable(), this.key.getFieldsArray(), this.getFieldsArray()) : new ResultImpl(new DefaultConfiguration(), this.key.getFields()));
   }

   public final Result<R> fetchChildren(Collection<? extends O> records) {
      return (Result)(records != null && records.size() != 0 ? fetch(records, this.getTable(), this.getFieldsArray(), this.key.getFieldsArray()) : new ResultImpl(new DefaultConfiguration(), this.getFields()));
   }

   private static <R1 extends Record, R2 extends Record> Result<R1> fetch(Collection<? extends R2> records, Table<R1> table, TableField<R1, ?>[] fields1, TableField<R2, ?>[] fields2) {
      return fields1.length == 1 ? extractDSLContext(records).selectFrom(table).where(fields1[0].in((Collection)extractValues(records, fields2[0]))).fetch() : extractDSLContext(records).selectFrom(table).where(DSL.row((Field[])fields1).in((Collection)extractRows(records, fields2))).fetch();
   }

   private static <R extends Record> List<Object> extractValues(Collection<? extends R> records, TableField<R, ?> field2) {
      List<Object> result = new ArrayList();
      Iterator var3 = records.iterator();

      while(var3.hasNext()) {
         R record = (Record)var3.next();
         result.add(record.get((Field)field2));
      }

      return result;
   }

   private static <R extends Record> List<RowN> extractRows(Collection<? extends R> records, TableField<R, ?>[] fields) {
      List<RowN> rows = new ArrayList();
      Iterator var3 = records.iterator();

      while(var3.hasNext()) {
         R record = (Record)var3.next();
         Object[] values = new Object[fields.length];

         for(int i = 0; i < fields.length; ++i) {
            values[i] = record.get((Field)fields[i]);
         }

         rows.add(DSL.row(values));
      }

      return rows;
   }

   private static <R extends Record> DSLContext extractDSLContext(Collection<? extends R> records) throws DetachedException {
      R first = (Record)Tools.first(records);
      if (first instanceof AttachableInternal) {
         return DSL.using(((AttachableInternal)first).configuration());
      } else {
         throw new DetachedException("Supply at least one attachable record");
      }
   }

   public Constraint constraint() {
      return DSL.constraint(this.getName()).foreignKey((Field[])this.getFieldsArray()).references((Table)this.key.getTable(), (Field[])this.key.getFieldsArray());
   }
}
