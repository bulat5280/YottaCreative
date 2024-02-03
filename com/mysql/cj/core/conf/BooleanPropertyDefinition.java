package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.RuntimeProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;

public class BooleanPropertyDefinition extends AbstractPropertyDefinition<Boolean> {
   private static final long serialVersionUID = -7288366734350231540L;

   public BooleanPropertyDefinition(String name, Boolean defaultValue, boolean isRuntimeModifiable, String description, String sinceVersion, String category, int orderInCategory) {
      super(name, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
   }

   public String[] getAllowableValues() {
      return new String[]{"true", "false", "yes", "no"};
   }

   public Boolean parseObject(String value, ExceptionInterceptor exceptionInterceptor) {
      this.validateAllowableValues(value, exceptionInterceptor);
      return value.equalsIgnoreCase("TRUE") || value.equalsIgnoreCase("YES");
   }

   public RuntimeProperty<Boolean> createRuntimeProperty() {
      return (RuntimeProperty)(this.isRuntimeModifiable() ? new ModifiableBooleanProperty(this) : new ReadableBooleanProperty(this));
   }
}
