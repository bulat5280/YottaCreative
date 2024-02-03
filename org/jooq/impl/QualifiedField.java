package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.tools.StringUtils;

final class QualifiedField<T> extends AbstractField<T> implements TableField<Record, T> {
   private static final long serialVersionUID = 6937002867156868761L;
   private final Name name;
   private final Table<Record> table;

   QualifiedField(Name name, DataType<T> type) {
      super((String)StringUtils.defaultIfNull(name.last(), ""), type);
      this.name = name;
      this.table = name.getName().length > 1 ? DSL.table(DSL.name((String[])Arrays.copyOf(name.getName(), name.getName().length - 1))) : null;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.name);
   }

   public final Table<Record> getTable() {
      return this.table;
   }
}
