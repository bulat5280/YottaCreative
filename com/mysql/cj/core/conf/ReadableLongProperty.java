package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.conf.ReadableProperty;
import java.io.Serializable;

public class ReadableLongProperty extends AbstractReadableProperty<Long> implements ReadableProperty<Long>, Serializable {
   private static final long serialVersionUID = 1814429804634837665L;

   protected ReadableLongProperty(PropertyDefinition<Long> propertyDefinition) {
      super(propertyDefinition);
   }

   public Long getValue() {
      return (Long)this.valueAsObject;
   }
}
