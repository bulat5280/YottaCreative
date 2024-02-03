package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.RuntimeProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;

public class StringPropertyDefinition extends AbstractPropertyDefinition<String> {
   private static final long serialVersionUID = 8228934389127796555L;

   public StringPropertyDefinition(String name, String defaultValue, boolean isRuntimeModifiable, String description, String sinceVersion, String category, int orderInCategory) {
      super(name, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
   }

   public StringPropertyDefinition(String name, String defaultValue, boolean isRuntimeModifiable, String description, String sinceVersion, String category, int orderInCategory, String[] allowableValues) {
      super(name, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory, allowableValues);
   }

   public String parseObject(String value, ExceptionInterceptor exceptionInterceptor) {
      this.validateAllowableValues(value, exceptionInterceptor);
      return value;
   }

   public RuntimeProperty<String> createRuntimeProperty() {
      return (RuntimeProperty)(this.isRuntimeModifiable() ? new ModifiableStringProperty(this) : new ReadableStringProperty(this));
   }
}
