package com.p6spy.engine.wrapper;

import java.sql.SQLException;
import java.sql.Wrapper;

public abstract class AbstractWrapper implements Wrapper, P6Proxy {
   private final Object delegate;

   protected AbstractWrapper(Object delegate) {
      this.delegate = delegate;
   }

   public static boolean isProxy(Object obj) {
      return obj != null && isProxy(obj.getClass());
   }

   public static boolean isProxy(Class<?> clazz) {
      return clazz != null && P6Proxy.class.isAssignableFrom(clazz);
   }

   public <T> T unwrap(Class<T> iface) throws SQLException {
      Object result;
      if (iface.isAssignableFrom(this.getClass())) {
         result = this;
      } else if (iface.isAssignableFrom(this.delegate.getClass())) {
         result = this.unwrapP6SpyProxy();
      } else {
         if (!Wrapper.class.isAssignableFrom(this.delegate.getClass())) {
            throw new SQLException("Can not unwrap to " + iface.getName());
         }

         result = ((Wrapper)this.unwrapP6SpyProxy()).unwrap(iface);
      }

      return iface.cast(result);
   }

   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      if (iface.isAssignableFrom(this.getClass())) {
         return true;
      } else if (iface.isAssignableFrom(this.delegate.getClass())) {
         return true;
      } else {
         return Wrapper.class.isAssignableFrom(this.delegate.getClass()) ? ((Wrapper)this.unwrapP6SpyProxy()).isWrapperFor(iface) : false;
      }
   }

   public boolean equals(Object obj) {
      if (obj instanceof P6Proxy) {
         obj = ((P6Proxy)obj).unwrapP6SpyProxy();
      }

      return this.delegate.equals(obj);
   }

   public Object unwrapP6SpyProxy() {
      return this.delegate;
   }
}
