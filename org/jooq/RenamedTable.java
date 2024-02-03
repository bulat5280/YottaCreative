package org.jooq;

import org.jooq.impl.TableImpl;

class RenamedTable<R extends Record> extends TableImpl<R> {
   private static final long serialVersionUID = -309012919785933903L;

   RenamedTable(Table<R> delegate, String rename) {
      super(rename, delegate.getSchema());
      Field[] var3 = delegate.fields();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field<?> field = var3[var5];
         createField(field.getName(), field.getDataType(), this);
      }

   }
}
