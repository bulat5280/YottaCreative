package org.jooq.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jooq.CaseWhenStep;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.QueryPart;

final class CaseWhenStepImpl<V, T> extends AbstractFunction<T> implements CaseWhenStep<V, T> {
   private static final long serialVersionUID = -3817194006479624228L;
   private final Field<V> value;
   private final List<Field<V>> compareValues;
   private final List<Field<T>> results;
   private Field<T> otherwise;

   CaseWhenStepImpl(Field<V> value, Field<V> compareValue, Field<T> result) {
      this(value, result.getDataType());
      this.when(compareValue, result);
   }

   CaseWhenStepImpl(Field<V> value, Map<? extends Field<V>, ? extends Field<T>> map) {
      this(value, dataType(map));
      Iterator var3 = map.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<? extends Field<V>, ? extends Field<T>> entry = (Entry)var3.next();
         this.when((Field)entry.getKey(), (Field)entry.getValue());
      }

   }

   private CaseWhenStepImpl(Field<V> value, DataType<T> type) {
      super("case", type);
      this.value = value;
      this.compareValues = new ArrayList();
      this.results = new ArrayList();
   }

   private static final <T> DataType<T> dataType(Map<? extends Field<?>, ? extends Field<T>> map) {
      return map.isEmpty() ? SQLDataType.OTHER : ((Field)((Entry)map.entrySet().iterator().next()).getValue()).getDataType();
   }

   public final Field<T> otherwise(T result) {
      return this.otherwise(Tools.field(result));
   }

   public final Field<T> otherwise(Field<T> result) {
      this.otherwise = result;
      return this;
   }

   public final CaseWhenStep<V, T> when(V compareValue, T result) {
      return this.when(Tools.field(compareValue), Tools.field(result));
   }

   public final CaseWhenStep<V, T> when(V compareValue, Field<T> result) {
      return this.when(Tools.field(compareValue), result);
   }

   public final CaseWhenStep<V, T> when(Field<V> compareValue, T result) {
      return this.when(compareValue, Tools.field(result));
   }

   public final CaseWhenStep<V, T> when(Field<V> compareValue, Field<T> result) {
      this.compareValues.add(compareValue);
      this.results.add(result);
      return this;
   }

   public final CaseWhenStep<V, T> mapValues(Map<V, T> values) {
      Iterator var2 = values.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<V, T> entry = (Entry)var2.next();
         this.when(entry.getKey(), entry.getValue());
      }

      return this;
   }

   public final CaseWhenStep<V, T> mapFields(Map<? extends Field<V>, ? extends Field<T>> fields) {
      Iterator var2 = fields.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<? extends Field<V>, ? extends Field<T>> entry = (Entry)var2.next();
         this.when((Field)entry.getKey(), (Field)entry.getValue());
      }

      return this;
   }

   final QueryPart getFunction0(Configuration configuration) {
      switch(configuration.dialect().family()) {
      default:
         return new CaseWhenStepImpl.Native();
      }
   }

   private class Native extends CaseWhenStepImpl<V, T>.Base {
      private static final long serialVersionUID = 7564667836130498156L;

      private Native() {
         super(null);
      }

      public final void accept(Context<?> ctx) {
         int size;
         ctx.formatIndentLockStart().keyword("case");
         size = CaseWhenStepImpl.this.compareValues.size();
         int i;
         label40:
         switch(ctx.configuration().dialect()) {
         case DERBY:
            ctx.formatIndentLockStart();
            i = 0;

            while(true) {
               if (i >= size) {
                  break label40;
               }

               if (i > 0) {
                  ctx.formatNewLine();
               }

               ctx.sql(' ').keyword("when").sql(' ');
               ctx.visit(CaseWhenStepImpl.this.value.equal((Field)CaseWhenStepImpl.this.compareValues.get(i)));
               ctx.sql(' ').keyword("then").sql(' ');
               ctx.visit((QueryPart)CaseWhenStepImpl.this.results.get(i));
               ++i;
            }
         default:
            ctx.sql(' ').visit(CaseWhenStepImpl.this.value).formatIndentStart();

            for(i = 0; i < size; ++i) {
               ctx.formatSeparator().keyword("when").sql(' ').visit((QueryPart)CaseWhenStepImpl.this.compareValues.get(i)).sql(' ').keyword("then").sql(' ').visit((QueryPart)CaseWhenStepImpl.this.results.get(i));
            }
         }

         if (CaseWhenStepImpl.this.otherwise != null) {
            ctx.formatSeparator().keyword("else").sql(' ').visit(CaseWhenStepImpl.this.otherwise);
         }

         ctx.formatIndentEnd();
         if (size <= 1 && CaseWhenStepImpl.this.otherwise == null) {
            ctx.sql(' ');
         } else {
            ctx.formatSeparator();
         }

         ctx.keyword("end").formatIndentLockEnd();
      }

      // $FF: synthetic method
      Native(Object x1) {
         this();
      }
   }

   private abstract class Base extends AbstractQueryPart {
      private static final long serialVersionUID = 6146002888421945901L;

      private Base() {
      }

      public final Clause[] clauses(Context<?> ctx) {
         return null;
      }

      // $FF: synthetic method
      Base(Object x1) {
         this();
      }
   }
}
