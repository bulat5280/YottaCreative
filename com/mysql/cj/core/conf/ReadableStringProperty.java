package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.conf.ReadableProperty;
import java.io.Serializable;

public class ReadableStringProperty extends AbstractReadableProperty<String> implements ReadableProperty<String>, Serializable {
   private static final long serialVersionUID = -4141084145739428803L;

   protected ReadableStringProperty(PropertyDefinition<String> propertyDefinition) {
      super(propertyDefinition);
   }

   public String getStringValue() {
      return (String)this.valueAsObject;
   }
}
