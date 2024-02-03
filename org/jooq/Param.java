package org.jooq;

import org.jooq.conf.ParamType;
import org.jooq.exception.DataTypeException;

public interface Param<T> extends Field<T> {
   String getName();

   String getParamName();

   T getValue();

   /** @deprecated */
   @Deprecated
   void setValue(T var1);

   /** @deprecated */
   @Deprecated
   void setConverted(Object var1) throws DataTypeException;

   /** @deprecated */
   @Deprecated
   void setInline(boolean var1);

   boolean isInline();

   ParamType getParamType();

   ParamMode getParamMode();
}
