package org.jooq.impl;

import java.util.List;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.TableRecord;
import org.jooq.UniqueKey;

public abstract class CustomTable<R extends TableRecord<R>> extends TableImpl<R> {
   private static final long serialVersionUID = 4300737872863697213L;

   protected CustomTable(String name) {
      super(name);
   }

   protected CustomTable(String name, Schema schema) {
      super(name, schema);
   }

   public abstract Class<? extends R> getRecordType();

   public Identity<R, ?> getIdentity() {
      return super.getIdentity();
   }

   public UniqueKey<R> getPrimaryKey() {
      return super.getPrimaryKey();
   }

   public List<UniqueKey<R>> getKeys() {
      return super.getKeys();
   }

   public List<ForeignKey<R, ?>> getReferences() {
      return super.getReferences();
   }

   public final boolean declaresFields() {
      return super.declaresFields();
   }

   public final boolean declaresTables() {
      return super.declaresTables();
   }
}
