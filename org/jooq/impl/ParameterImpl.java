package org.jooq.impl;

import org.jooq.Binding;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.Parameter;
import org.jooq.tools.StringUtils;

final class ParameterImpl<T> extends AbstractQueryPart implements Parameter<T> {
   private static final long serialVersionUID = -5277225593751085577L;
   private final String name;
   private final DataType<T> type;
   private final boolean isDefaulted;
   private final boolean isUnnamed;

   ParameterImpl(String name, DataType<T> type, Binding<?, T> binding, boolean isDefaulted, boolean isUnnamed) {
      this.name = name;
      this.type = type.asConvertedDataType(binding);
      this.isDefaulted = isDefaulted;
      this.isUnnamed = isUnnamed;
   }

   public final String getName() {
      return this.name;
   }

   public final Converter<?, T> getConverter() {
      return this.type.getBinding().converter();
   }

   public final Binding<?, T> getBinding() {
      return this.type.getBinding();
   }

   public final DataType<T> getDataType() {
      return this.type;
   }

   public final DataType<T> getDataType(Configuration configuration) {
      return this.type.getDataType(configuration);
   }

   public final Class<T> getType() {
      return this.type.getType();
   }

   public final void accept(Context<?> ctx) {
      ctx.literal(this.getName());
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   public final boolean isDefaulted() {
      return this.isDefaulted;
   }

   public final boolean isUnnamed() {
      return this.isUnnamed;
   }

   public int hashCode() {
      return this.name != null ? this.name.hashCode() : 0;
   }

   public boolean equals(Object that) {
      if (this == that) {
         return true;
      } else {
         return that instanceof ParameterImpl ? StringUtils.equals(this.name, ((ParameterImpl)that).name) : super.equals(that);
      }
   }
}
