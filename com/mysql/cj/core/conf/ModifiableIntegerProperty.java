package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.ModifiableProperty;
import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import java.io.Serializable;

public class ModifiableIntegerProperty extends ReadableIntegerProperty implements ModifiableProperty<Integer>, Serializable {
   private static final long serialVersionUID = 1954410331604145901L;

   protected ModifiableIntegerProperty(PropertyDefinition<Integer> propertyDefinition) {
      super(propertyDefinition);
   }

   protected void initializeFrom(String extractedValue, ExceptionInterceptor exceptionInterceptor) {
      super.initializeFrom(extractedValue, exceptionInterceptor);
      this.initialValueAsObject = this.valueAsObject;
   }

   public void setFromString(String value, ExceptionInterceptor exceptionInterceptor) {
      this.setValue(((IntegerPropertyDefinition)this.getPropertyDefinition()).parseObject(value, exceptionInterceptor), value, exceptionInterceptor);
   }

   public void setValue(Integer value) {
      this.setValue(value, (String)null, (ExceptionInterceptor)null);
   }

   public void setValue(Integer value, ExceptionInterceptor exceptionInterceptor) {
      this.setValue(value, (String)null, exceptionInterceptor);
   }

   private void setValue(int intValue, String valueAsString, ExceptionInterceptor exceptionInterceptor) {
      if (this.getPropertyDefinition().isRangeBased() && (intValue < this.getPropertyDefinition().getLowerBound() || intValue > this.getPropertyDefinition().getUpperBound())) {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, "The connection property '" + this.getPropertyDefinition().getName() + "' only accepts integer values in the range of " + this.getPropertyDefinition().getLowerBound() + " - " + this.getPropertyDefinition().getUpperBound() + ", the value '" + (valueAsString == null ? intValue : valueAsString) + "' exceeds this range.", exceptionInterceptor);
      } else {
         this.valueAsObject = intValue;
         this.wasExplicitlySet = true;
         this.invokeListeners();
      }
   }

   public void resetValue() {
      this.valueAsObject = this.initialValueAsObject;
      this.invokeListeners();
   }
}
