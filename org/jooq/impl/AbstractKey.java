package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import org.jooq.Key;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;

abstract class AbstractKey<R extends Record> implements Key<R> {
   private static final long serialVersionUID = 8176874459141379340L;
   private final String name;
   private final Table<R> table;
   private final TableField<R, ?>[] fields;

   @SafeVarargs
   AbstractKey(Table<R> table, TableField<R, ?>... fields) {
      this(table, (String)null, fields);
   }

   @SafeVarargs
   AbstractKey(Table<R> table, String name, TableField<R, ?>... fields) {
      this.table = table;
      this.name = name;
      this.fields = fields;
   }

   public final String getName() {
      return this.name;
   }

   public final Table<R> getTable() {
      return this.table;
   }

   public final List<TableField<R, ?>> getFields() {
      return Arrays.asList(this.fields);
   }

   public final TableField<R, ?>[] getFieldsArray() {
      return this.fields;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      int result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
      result = 31 * result + (this.table == null ? 0 : this.table.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         AbstractKey<?> other = (AbstractKey)obj;
         if (this.name == null) {
            if (other.name != null) {
               return false;
            }
         } else if (!this.name.equals(other.name)) {
            return false;
         }

         if (this.table == null) {
            if (other.table != null) {
               return false;
            }
         } else if (!this.table.equals(other.table)) {
            return false;
         }

         return true;
      }
   }

   public String toString() {
      return this.constraint().toString();
   }
}
