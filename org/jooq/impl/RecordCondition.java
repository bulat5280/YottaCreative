package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Record;

final class RecordCondition extends AbstractCondition {
   private static final long serialVersionUID = 56430809227808954L;
   private final Record record;

   RecordCondition(Record record) {
      this.record = record;
   }

   public void accept(Context<?> ctx) {
      ConditionProviderImpl condition = new ConditionProviderImpl();
      int size = this.record.size();

      for(int i = 0; i < size; ++i) {
         Object value = this.record.get(i);
         if (value != null) {
            Field f1 = this.record.field(i);
            Field f2 = DSL.val(value, f1.getDataType());
            condition.addConditions(f1.eq((Field)f2));
         }
      }

      ctx.visit(condition);
   }
}
