package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.ModifiableProperty;
import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import java.io.Serializable;

public class ModifiableMemorySizeProperty extends ReadableMemorySizeProperty implements ModifiableProperty<Integer>, Serializable {
   private static final long serialVersionUID = -8018059699460539279L;
   private String initialValueAsString;

   protected ModifiableMemorySizeProperty(PropertyDefinition<Integer> propertyDefinition) {
      super(propertyDefinition);
   }

   public void setFromString(String value, ExceptionInterceptor exceptionInterceptor) {
      this.setValue(((MemorySizePropertyDefinition)this.getPropertyDefinition()).parseObject(value, exceptionInterceptor), value, exceptionInterceptor);
      this.valueAsString = value;
   }

   protected void initializeFrom(String extractedValue, ExceptionInterceptor exceptionInterceptor) {
      super.initializeFrom(extractedValue, exceptionInterceptor);
      this.initialValueAsObject = this.valueAsObject;
      this.initialValueAsString = this.valueAsString;
   }

   public void setValue(Integer value) {
      this.setValue(value, (String)null, (ExceptionInterceptor)null);
   }

   public void setValue(Integer value, ExceptionInterceptor exceptionInterceptor) {
      this.setValue(value, (String)null, exceptionInterceptor);
   }

   void setValue(int intValue, String valueAsString, ExceptionInterceptor exceptionInterceptor) {
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
      this.valueAsString = this.initialValueAsString;
      this.invokeListeners();
   }
}
