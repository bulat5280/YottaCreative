package com.mysql.cj.api.conf;

public interface ReadableProperty<T> extends RuntimeProperty<T> {
   T getValue();

   T getInitialValue();

   String getStringValue();
}
