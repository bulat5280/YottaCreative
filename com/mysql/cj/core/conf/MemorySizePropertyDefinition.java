package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.RuntimeProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.core.util.StringUtils;

public class MemorySizePropertyDefinition extends IntegerPropertyDefinition {
   private static final long serialVersionUID = -6878680905514177949L;

   public MemorySizePropertyDefinition(String name, int defaultValue, boolean isRuntimeModifiable, String description, String sinceVersion, String category, int orderInCategory) {
      super(name, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
   }

   public MemorySizePropertyDefinition(String name, int defaultValue, boolean isRuntimeModifiable, String description, String sinceVersion, String category, int orderInCategory, int lowerBound, int upperBound) {
      super(name, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory, lowerBound, upperBound);
   }

   public Integer parseObject(String value, ExceptionInterceptor exceptionInterceptor) {
      this.multiplier = 1;
      int indexOfG;
      if (!value.endsWith("k") && !value.endsWith("K") && !value.endsWith("kb") && !value.endsWith("Kb") && !value.endsWith("kB") && !value.endsWith("KB")) {
         if (!value.endsWith("m") && !value.endsWith("M") && !value.endsWith("mb") && !value.endsWith("Mb") && !value.endsWith("mB") && !value.endsWith("MB")) {
            if (value.endsWith("g") || value.endsWith("G") || value.endsWith("gb") || value.endsWith("Gb") || value.endsWith("gB") || value.endsWith("GB")) {
               this.multiplier = 1073741824;
               indexOfG = StringUtils.indexOfIgnoreCase(value, "g");
               value = value.substring(0, indexOfG);
            }
         } else {
            this.multiplier = 1048576;
            indexOfG = StringUtils.indexOfIgnoreCase(value, "m");
            value = value.substring(0, indexOfG);
         }
      } else {
         this.multiplier = 1024;
         indexOfG = StringUtils.indexOfIgnoreCase(value, "k");
         value = value.substring(0, indexOfG);
      }

      return super.parseObject(value, exceptionInterceptor);
   }

   public RuntimeProperty<Integer> createRuntimeProperty() {
      return (RuntimeProperty)(this.isRuntimeModifiable() ? new ModifiableMemorySizeProperty(this) : new ReadableMemorySizeProperty(this));
   }
}
