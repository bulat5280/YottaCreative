package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import java.io.Serializable;

public abstract class AbstractPropertyDefinition<T> implements PropertyDefinition<T>, Serializable {
   private static final long serialVersionUID = 2696624840927848766L;
   private String name;
   private T defaultValue;
   private boolean isRuntimeModifiable;
   private String description;
   private String sinceVersion;
   private String category;
   private int order;
   private String[] allowableValues;
   private int lowerBound;
   private int upperBound;

   public AbstractPropertyDefinition(String name, T defaultValue, boolean isRuntimeModifiable, String description, String sinceVersion, String category, int orderInCategory) {
      this.allowableValues = null;
      this.setName(name);
      this.setDefaultValue(defaultValue);
      this.setRuntimeModifiable(isRuntimeModifiable);
      this.setDescription(description);
      this.setSinceVersion(sinceVersion);
      this.setCategory(category);
      this.setOrder(orderInCategory);
   }

   public AbstractPropertyDefinition(String name, T defaultValue, boolean isRuntimeModifiable, String description, String sinceVersion, String category, int orderInCategory, String[] allowableValues) {
      this(name, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
      this.setAllowableValues(allowableValues);
   }

   public AbstractPropertyDefinition(String name, T defaultValue, boolean isRuntimeModifiable, String description, String sinceVersion, String category, int orderInCategory, int lowerBound, int upperBound) {
      this(name, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
      this.setLowerBound(lowerBound);
      this.setUpperBound(upperBound);
   }

   public boolean hasValueConstraints() {
      return this.getAllowableValues() != null && this.getAllowableValues().length > 0;
   }

   public boolean isRangeBased() {
      return false;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public T getDefaultValue() {
      return this.defaultValue;
   }

   public void setDefaultValue(T defaultValue) {
      this.defaultValue = defaultValue;
   }

   public boolean isRuntimeModifiable() {
      return this.isRuntimeModifiable;
   }

   public void setRuntimeModifiable(boolean isRuntimeModifiable) {
      this.isRuntimeModifiable = isRuntimeModifiable;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getSinceVersion() {
      return this.sinceVersion;
   }

   public void setSinceVersion(String sinceVersion) {
      this.sinceVersion = sinceVersion;
   }

   public String getCategory() {
      return this.category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public int getOrder() {
      return this.order;
   }

   public void setOrder(int order) {
      this.order = order;
   }

   public String[] getAllowableValues() {
      return this.allowableValues;
   }

   public void setAllowableValues(String[] allowableValues) {
      this.allowableValues = allowableValues;
   }

   public int getLowerBound() {
      return this.lowerBound;
   }

   public void setLowerBound(int lowerBound) {
      this.lowerBound = lowerBound;
   }

   public int getUpperBound() {
      return this.upperBound;
   }

   public void setUpperBound(int upperBound) {
      this.upperBound = upperBound;
   }

   public abstract T parseObject(String var1, ExceptionInterceptor var2);

   public void validateAllowableValues(String valueToValidate, ExceptionInterceptor exceptionInterceptor) {
      String[] validateAgainst = this.getAllowableValues();
      if (valueToValidate != null && validateAgainst != null && validateAgainst.length != 0) {
         for(int i = 0; i < validateAgainst.length; ++i) {
            if (validateAgainst[i] != null && validateAgainst[i].equalsIgnoreCase(valueToValidate)) {
               return;
            }
         }

         StringBuilder errorMessageBuf = new StringBuilder();
         errorMessageBuf.append(Messages.getString("PropertyDefinition.1", new Object[]{this.getName(), validateAgainst[0]}));

         for(int i = 1; i < validateAgainst.length - 1; ++i) {
            errorMessageBuf.append(Messages.getString("PropertyDefinition.2", new Object[]{validateAgainst[i]}));
         }

         errorMessageBuf.append(Messages.getString("PropertyDefinition.3", new Object[]{validateAgainst[validateAgainst.length - 1], valueToValidate}));
         throw ExceptionFactory.createException(errorMessageBuf.toString(), exceptionInterceptor);
      }
   }
}
