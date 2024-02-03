package org.jooq.impl;

import org.jooq.Binding;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.UDT;
import org.jooq.UDTField;
import org.jooq.UDTRecord;

final class UDTFieldImpl<R extends UDTRecord<R>, T> extends AbstractField<T> implements UDTField<R, T> {
   private static final long serialVersionUID = -2211214195583539735L;
   private final UDT<R> udt;

   UDTFieldImpl(String name, DataType<T> type, UDT<R> udt, String comment, Binding<?, T> binding) {
      super(name, type, comment, binding);
      this.udt = udt;
      if (udt instanceof UDTImpl) {
         ((UDTImpl)udt).fields0().add(this);
      }

   }

   public final UDT<R> getUDT() {
      return this.udt;
   }

   public final void accept(Context<?> ctx) {
      ctx.literal(this.getName());
   }
}
