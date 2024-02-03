package com.mysql.cj.api.conf;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import java.util.Properties;
import javax.naming.Reference;

public interface RuntimeProperty<T> {
   PropertyDefinition<T> getPropertyDefinition();

   void initializeFrom(Properties var1, ExceptionInterceptor var2);

   void initializeFrom(Reference var1, ExceptionInterceptor var2);

   void resetValue();

   boolean isExplicitlySet();

   void addListener(RuntimeProperty.RuntimePropertyListener var1);

   void removeListener(RuntimeProperty.RuntimePropertyListener var1);

   @FunctionalInterface
   public interface RuntimePropertyListener {
      void handlePropertyChange(RuntimeProperty<?> var1);
   }
}
