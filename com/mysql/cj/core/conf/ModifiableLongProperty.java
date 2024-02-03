package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.ModifiableProperty;
import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import java.io.Serializable;

public class ModifiableLongProperty extends ReadableLongProperty implements ModifiableProperty<Long>, Serializable {
   private static final long serialVersionUID = 2870949628194348648L;

   protected ModifiableLongProperty(PropertyDefinition<Long> propertyDefinition) {
      super(propertyDefinition);
   }

   protected void initializeFrom(String extractedValue, ExceptionInterceptor exceptionInterceptor) {
      super.initializeFrom(extractedValue, exceptionInterceptor);
      this.initialValueAsObject = this.valueAsObject;
   }

   public void setFromString(String value, ExceptionInterceptor exceptionInterceptor) {
      this.setValue(((LongPropertyDefinition)this.getPropertyDefinition()).parseObject(value, exceptionInterceptor), value, exceptionInterceptor);
   }

   public void setValue(Long longValue) {
      this.setValue(longValue, (String)null, (ExceptionInterceptor)null);
   }

   public void setValue(Long longValue, ExceptionInterceptor exceptionInterceptor) {
      this.setValue(longValue, (String)null, exceptionInterceptor);
   }

   void setValue(long longValue, String valueAsString, ExceptionInterceptor exceptionInterceptor) {
      if (this.getPropertyDefinition().isRangeBased() && (longValue < (long)this.getPropertyDefinition().getLowerBound() || longValue > (long)this.getPropertyDefinition().getUpperBound())) {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, "The connection property '" + this.getPropertyDefinition().getName() + "' only accepts long integer values in the range of " + this.getPropertyDefinition().getLowerBound() + " - " + this.getPropertyDefinition().getUpperBound() + ", the value '" + (valueAsString == null ? longValue : valueAsString) + "' exceeds this range.", exceptionInterceptor);
      } else {
         this.valueAsObject = longValue;
         this.wasExplicitlySet = true;
         this.invokeListeners();
      }
   }

   public void resetValue() {
      this.valueAsObject = this.initialValueAsObject;
      this.invokeListeners();
   }
}
