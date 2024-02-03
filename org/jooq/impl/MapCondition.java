package org.jooq.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.jooq.Context;
import org.jooq.Field;

final class MapCondition extends AbstractCondition {
   private static final long serialVersionUID = 6320436041406801993L;
   private final Map<Field<?>, ?> map;

   MapCondition(Map<Field<?>, ?> map) {
      this.map = map;
   }

   public void accept(Context<?> ctx) {
      ConditionProviderImpl condition = new ConditionProviderImpl();
      Iterator var3 = this.map.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<Field<?>, ?> entry = (Entry)var3.next();
         Field f1 = (Field)entry.getKey();
         Field f2 = Tools.field(entry.getValue(), f1);
         condition.addConditions(f1.eq(f2));
      }

      ctx.visit(condition);
   }
}
