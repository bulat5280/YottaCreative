package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.RuntimeProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;

public class IntegerPropertyDefinition extends AbstractPropertyDefinition<Integer> {
   private static final long serialVersionUID = 4151893695173946081L;
   protected int multiplier = 1;

   public IntegerPropertyDefinition(String name, int defaultValue, boolean isRuntimeModifiable, String description, String sinceVersion, String category, int orderInCategory) {
      super(name, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
   }

   public IntegerPropertyDefinition(String name, int defaultValue, boolean isRuntimeModifiable, String description, String sinceVersion, String category, int orderInCategory, int lowerBound, int upperBound) {
      super(name, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory, lowerBound, upperBound);
   }

   public boolean isRangeBased() {
      return this.getUpperBound() != this.getLowerBound();
   }

   public Integer parseObject(String value, ExceptionInterceptor exceptionInterceptor) {
      try {
         int intValue = (int)(Double.valueOf(value) * (double)this.multiplier);
         return intValue;
      } catch (NumberFormatException var4) {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, "The connection property '" + this.getName() + "' only accepts integer values. The value '" + value + "' can not be converted to an integer.", exceptionInterceptor);
      }
   }

   public RuntimeProperty<Integer> createRuntimeProperty() {
      return (RuntimeProperty)(this.isRuntimeModifiable() ? new ModifiableIntegerProperty(this) : new ReadableIntegerProperty(this));
   }
}
