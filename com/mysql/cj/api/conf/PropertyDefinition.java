package com.mysql.cj.api.conf;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;

public interface PropertyDefinition<T> {
   boolean hasValueConstraints();

   boolean isRangeBased();

   String getName();

   T getDefaultValue();

   boolean isRuntimeModifiable();

   String getDescription();

   String getSinceVersion();

   String getCategory();

   int getOrder();

   String[] getAllowableValues();

   int getLowerBound();

   int getUpperBound();

   T parseObject(String var1, ExceptionInterceptor var2);

   void validateAllowableValues(String var1, ExceptionInterceptor var2);

   RuntimeProperty<T> createRuntimeProperty();
}
