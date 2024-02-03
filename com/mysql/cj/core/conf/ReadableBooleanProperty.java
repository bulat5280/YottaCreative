package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.conf.ReadableProperty;
import java.io.Serializable;

public class ReadableBooleanProperty extends AbstractReadableProperty<Boolean> implements ReadableProperty<Boolean>, Serializable {
   private static final long serialVersionUID = 1102859411443650569L;

   protected ReadableBooleanProperty(PropertyDefinition<Boolean> propertyDefinition) {
      super(propertyDefinition);
   }
}
