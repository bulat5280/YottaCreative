package org.jooq.impl;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;

public abstract class AbstractKeys {
   protected static <R extends Record, T> Identity<R, T> createIdentity(Table<R> table, TableField<R, T> field) {
      return new IdentityImpl(table, field);
   }

   @SafeVarargs
   protected static <R extends Record> UniqueKey<R> createUniqueKey(Table<R> table, TableField<R, ?>... fields) {
      return new UniqueKeyImpl(table, fields);
   }

   @SafeVarargs
   protected static <R extends Record> UniqueKey<R> createUniqueKey(Table<R> table, String name, TableField<R, ?>... fields) {
      return new UniqueKeyImpl(table, name, fields);
   }

   @SafeVarargs
   protected static <R extends Record, U extends Record> ForeignKey<R, U> createForeignKey(UniqueKey<U> key, Table<R> table, TableField<R, ?>... fields) {
      return createForeignKey(key, table, (String)null, fields);
   }

   @SafeVarargs
   protected static <R extends Record, U extends Record> ForeignKey<R, U> createForeignKey(UniqueKey<U> key, Table<R> table, String name, TableField<R, ?>... fields) {
      ForeignKey<R, U> result = new ReferenceImpl(key, table, name, fields);
      if (key instanceof UniqueKeyImpl) {
         ((UniqueKeyImpl)key).references.add(result);
      }

      return result;
   }
}
