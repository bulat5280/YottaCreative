package org.jooq.impl;

import java.util.Collection;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.SelectField;

final class SelectFieldList extends QueryPartList<Field<?>> {
   private static final long serialVersionUID = 8850104968428500798L;

   SelectFieldList() {
   }

   SelectFieldList(Collection<? extends SelectField<?>> wrappedList) {
      super((Collection)Tools.fields(wrappedList));
   }

   SelectFieldList(SelectField<?>... wrappedList) {
      super((Collection)Tools.fields(wrappedList));
   }

   protected void toSQLEmptyList(Context<?> ctx) {
      ctx.sql('*');
   }

   public final boolean declaresFields() {
      return true;
   }
}
