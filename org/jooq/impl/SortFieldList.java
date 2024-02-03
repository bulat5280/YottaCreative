package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jooq.Field;
import org.jooq.SortField;

final class SortFieldList extends QueryPartList<SortField<?>> {
   private static final long serialVersionUID = -1825164005148183725L;

   SortFieldList() {
      this(Collections.emptyList());
   }

   SortFieldList(List<SortField<?>> wrappedList) {
      super((Collection)wrappedList);
   }

   final void addAll(Field<?>... fields) {
      SortField<?>[] result = new SortField[fields.length];

      for(int i = 0; i < fields.length; ++i) {
         result[i] = fields[i].asc();
      }

      this.addAll(Arrays.asList(result));
   }

   final boolean uniform() {
      Iterator var1 = this.iterator();

      SortField field;
      do {
         if (!var1.hasNext()) {
            return true;
         }

         field = (SortField)var1.next();
      } while(field.getOrder() == ((SortField)this.get(0)).getOrder());

      return false;
   }

   final boolean nulls() {
      Iterator var1 = this.iterator();

      SortField field;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         field = (SortField)var1.next();
      } while(!((SortFieldImpl)field).getNullsFirst() && !((SortFieldImpl)field).getNullsLast());

      return true;
   }

   final List<Field<?>> fields() {
      List<Field<?>> result = new ArrayList();
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         SortField<?> field = (SortField)var2.next();
         result.add(((SortFieldImpl)field).getField());
      }

      return result;
   }
}
