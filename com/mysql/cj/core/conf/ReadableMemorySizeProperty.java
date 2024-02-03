package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.conf.ReadableProperty;
import java.io.Serializable;

public class ReadableMemorySizeProperty extends ReadableIntegerProperty implements ReadableProperty<Integer>, Serializable {
   private static final long serialVersionUID = 4200558564320133284L;
   protected String valueAsString;

   protected ReadableMemorySizeProperty(PropertyDefinition<Integer> propertyDefinition) {
      super(propertyDefinition);
   }

   public String getStringValue() {
      return this.valueAsString;
   }
}
