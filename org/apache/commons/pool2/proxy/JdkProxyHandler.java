package org.apache.commons.pool2.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.apache.commons.pool2.UsageTracking;

class JdkProxyHandler<T> extends BaseProxyHandler<T> implements InvocationHandler {
   JdkProxyHandler(T pooledObject, UsageTracking<T> usageTracking) {
      super(pooledObject, usageTracking);
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      return this.doInvoke(method, args);
   }
}
