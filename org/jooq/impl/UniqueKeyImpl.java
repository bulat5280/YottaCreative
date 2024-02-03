package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jooq.Constraint;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;

final class UniqueKeyImpl<R extends Record> extends AbstractKey<R> implements UniqueKey<R> {
   private static final long serialVersionUID = 162853300137140844L;
   final List<ForeignKey<?, R>> references;

   @SafeVarargs
   UniqueKeyImpl(Table<R> table, TableField<R, ?>... fields) {
      this(table, (String)null, fields);
   }

   @SafeVarargs
   UniqueKeyImpl(Table<R> table, String name, TableField<R, ?>... fields) {
      super(table, name, fields);
      this.references = new ArrayList();
   }

   public final boolean isPrimary() {
      return this.equals(this.getTable().getPrimaryKey());
   }

   public final List<ForeignKey<?, R>> getReferences() {
      return Collections.unmodifiableList(this.references);
   }

   public Constraint constraint() {
      return this.isPrimary() ? DSL.constraint(this.getName()).primaryKey((Field[])this.getFieldsArray()) : DSL.constraint(this.getName()).unique((Field[])this.getFieldsArray());
   }
}
