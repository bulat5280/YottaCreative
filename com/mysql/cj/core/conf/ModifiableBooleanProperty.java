package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.ModifiableProperty;
import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import java.io.Serializable;

public class ModifiableBooleanProperty extends ReadableBooleanProperty implements ModifiableProperty<Boolean>, Serializable {
   private static final long serialVersionUID = 7810312684423192133L;

   protected ModifiableBooleanProperty(PropertyDefinition<Boolean> propertyDefinition) {
      super(propertyDefinition);
   }

   protected void initializeFrom(String extractedValue, ExceptionInterceptor exceptionInterceptor) {
      super.initializeFrom(extractedValue, exceptionInterceptor);
      this.initialValueAsObject = this.valueAsObject;
   }

   public void setValue(Boolean value) {
      this.setValue((Boolean)value, (ExceptionInterceptor)null);
   }

   public void setValue(Boolean value, ExceptionInterceptor exceptionInterceptor) {
      this.valueAsObject = value;
      this.wasExplicitlySet = true;
      this.invokeListeners();
   }

   public void resetValue() {
      this.valueAsObject = this.initialValueAsObject;
      this.invokeListeners();
   }
}
