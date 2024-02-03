package org.apache.commons.pool2.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import org.apache.commons.pool2.UsageTracking;

public class CglibProxySource<T> implements ProxySource<T> {
   private final Class<? extends T> superclass;

   public CglibProxySource(Class<? extends T> superclass) {
      this.superclass = superclass;
   }

   public T createProxy(T pooledObject, UsageTracking<T> usageTracking) {
      Enhancer enhancer = new Enhancer();
      enhancer.setSuperclass(this.superclass);
      CglibProxyHandler<T> proxyInterceptor = new CglibProxyHandler(pooledObject, usageTracking);
      enhancer.setCallback(proxyInterceptor);
      T proxy = enhancer.create();
      return proxy;
   }

   public T resolveProxy(T proxy) {
      CglibProxyHandler<T> cglibProxyHandler = (CglibProxyHandler)((Factory)proxy).getCallback(0);
      T pooledObject = cglibProxyHandler.disableProxy();
      return pooledObject;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("CglibProxySource [superclass=");
      builder.append(this.superclass);
      builder.append("]");
      return builder.toString();
   }
}
