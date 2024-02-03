package org.jooq.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.jooq.CaseValueStep;
import org.jooq.CaseWhenStep;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Select;

final class CaseValueStepImpl<V> implements CaseValueStep<V> {
   private final Field<V> value;

   CaseValueStepImpl(Field<V> value) {
      this.value = value;
   }

   public final <T> CaseWhenStep<V, T> when(V compareValue, T result) {
      return this.when(Tools.field(compareValue), Tools.field(result));
   }

   public final <T> CaseWhenStep<V, T> when(V compareValue, Field<T> result) {
      return this.when(Tools.field(compareValue), result);
   }

   public final <T> CaseWhenStep<V, T> when(V compareValue, Select<? extends Record1<T>> result) {
      return this.when(Tools.field(compareValue), DSL.field(result));
   }

   public final <T> CaseWhenStep<V, T> when(Field<V> compareValue, T result) {
      return this.when(compareValue, Tools.field(result));
   }

   public final <T> CaseWhenStep<V, T> when(Field<V> compareValue, Field<T> result) {
      return new CaseWhenStepImpl(this.value, compareValue, result);
   }

   public final <T> CaseWhenStep<V, T> when(Field<V> compareValue, Select<? extends Record1<T>> result) {
      return this.when(compareValue, DSL.field(result));
   }

   public final <T> CaseWhenStep<V, T> mapValues(Map<V, T> values) {
      Map<Field<V>, Field<T>> fields = new LinkedHashMap();
      Iterator var3 = values.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<V, T> entry = (Entry)var3.next();
         fields.put(Tools.field(entry.getKey()), Tools.field(entry.getValue()));
      }

      return this.mapFields(fields);
   }

   public final <T> CaseWhenStep<V, T> mapFields(Map<? extends Field<V>, ? extends Field<T>> fields) {
      return new CaseWhenStepImpl(this.value, fields);
   }
}
