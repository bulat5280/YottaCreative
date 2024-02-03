package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Param;
import org.jooq.ParamMode;
import org.jooq.UDTRecord;
import org.jooq.conf.ParamType;
import org.jooq.tools.StringUtils;

abstract class AbstractParam<T> extends AbstractField<T> implements Param<T> {
   private static final long serialVersionUID = 1311856649676227970L;
   private static final Clause[] CLAUSES;
   private final String paramName;
   T value;
   private boolean inline;

   AbstractParam(T value, DataType<T> type) {
      this(value, type, (String)null);
   }

   AbstractParam(T value, DataType<T> type, String paramName) {
      super(name(value, paramName), type);
      this.paramName = paramName;
      this.value = value;
   }

   private static String name(Object value, String paramName) {
      return paramName != null ? paramName : (value instanceof UDTRecord ? ((UDTRecord)value).getUDT().getName() : String.valueOf(value));
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   public final boolean generatesCast() {
      return true;
   }

   public final void setValue(T value) {
      this.setConverted(value);
   }

   public final void setConverted(Object value) {
      this.value = this.getDataType().convert(value);
   }

   public final T getValue() {
      return this.value;
   }

   public final String getParamName() {
      return this.paramName;
   }

   public final void setInline(boolean inline) {
      this.inline = inline;
   }

   public final boolean isInline() {
      return this.inline;
   }

   final boolean isInline(Context<?> context) {
      return this.isInline() || context.paramType() == ParamType.INLINED || context.paramType() == ParamType.NAMED_OR_INLINED && StringUtils.isBlank(this.paramName);
   }

   public final ParamType getParamType() {
      return this.inline ? ParamType.INLINED : (StringUtils.isBlank(this.paramName) ? ParamType.INDEXED : ParamType.NAMED);
   }

   public final ParamMode getParamMode() {
      return ParamMode.IN;
   }

   static {
      CLAUSES = new Clause[]{Clause.FIELD, Clause.FIELD_VALUE};
   }
}
