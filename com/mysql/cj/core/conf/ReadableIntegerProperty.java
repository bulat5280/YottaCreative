package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.conf.ReadableProperty;
import java.io.Serializable;

public class ReadableIntegerProperty extends AbstractReadableProperty<Integer> implements ReadableProperty<Integer>, Serializable {
   private static final long serialVersionUID = 9208223182595760858L;

   public ReadableIntegerProperty(PropertyDefinition<Integer> propertyDefinition) {
      super(propertyDefinition);
   }

   public Integer getValue() {
      return (Integer)this.valueAsObject;
   }
}
