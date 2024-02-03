package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.RuntimeProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;

public class LongPropertyDefinition extends AbstractPropertyDefinition<Long> {
   private static final long serialVersionUID = -5264490959206230852L;

   public LongPropertyDefinition(String name, long defaultValue, boolean isRuntimeModifiable, String description, String sinceVersion, String category, int orderInCategory) {
      super(name, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
   }

   public LongPropertyDefinition(String name, long defaultValue, boolean isRuntimeModifiable, String description, String sinceVersion, String category, int orderInCategory, long lowerBound, long upperBound) {
      super(name, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory, (int)lowerBound, (int)upperBound);
   }

   public Long parseObject(String value, ExceptionInterceptor exceptionInterceptor) {
      try {
         return Double.valueOf(value).longValue();
      } catch (NumberFormatException var4) {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, "The connection property '" + this.getName() + "' only accepts long integer values. The value '" + value + "' can not be converted to a long integer.", exceptionInterceptor);
      }
   }

   public boolean isRangeBased() {
      return this.getUpperBound() != this.getLowerBound();
   }

   public RuntimeProperty<Long> createRuntimeProperty() {
      return (RuntimeProperty)(this.isRuntimeModifiable() ? new ModifiableLongProperty(this) : new ReadableLongProperty(this));
   }
}
