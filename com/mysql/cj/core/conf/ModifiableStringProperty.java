package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.ModifiableProperty;
import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import java.io.Serializable;

public class ModifiableStringProperty extends ReadableStringProperty implements ModifiableProperty<String>, Serializable {
   private static final long serialVersionUID = -3956001600419271415L;

   protected ModifiableStringProperty(PropertyDefinition<String> propertyDefinition) {
      super(propertyDefinition);
   }

   protected void initializeFrom(String extractedValue, ExceptionInterceptor exceptionInterceptor) {
      super.initializeFrom(extractedValue, exceptionInterceptor);
      this.initialValueAsObject = this.valueAsObject;
   }

   public void setValue(String value) {
      this.setValue((String)value, (ExceptionInterceptor)null);
   }

   public void setValue(String value, ExceptionInterceptor exceptionInterceptor) {
      this.setFromString(value, exceptionInterceptor);
      this.invokeListeners();
   }

   public void resetValue() {
      this.valueAsObject = this.initialValueAsObject;
      this.invokeListeners();
   }
}
