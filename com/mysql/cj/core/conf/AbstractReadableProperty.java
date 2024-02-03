package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.conf.ReadableProperty;
import java.io.Serializable;

public abstract class AbstractReadableProperty<T> extends AbstractRuntimeProperty<T> implements ReadableProperty<T>, Serializable {
   private static final long serialVersionUID = -3424722534876438236L;

   public AbstractReadableProperty() {
   }

   protected AbstractReadableProperty(PropertyDefinition<T> propertyDefinition) {
      super(propertyDefinition);
   }

   public T getValue() {
      return this.valueAsObject;
   }

   public T getInitialValue() {
      return this.initialValueAsObject;
   }

   public String getStringValue() {
      return this.valueAsObject != null ? this.valueAsObject.toString() : null;
   }
}
