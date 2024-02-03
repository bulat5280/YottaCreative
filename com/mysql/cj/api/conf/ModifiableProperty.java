package com.mysql.cj.api.conf;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;

public interface ModifiableProperty<T> extends ReadableProperty<T> {
   void setFromString(String var1, ExceptionInterceptor var2);

   void setValue(T var1);

   void setValue(T var1, ExceptionInterceptor var2);
}
