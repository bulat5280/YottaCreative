package org.apache.commons.pool2.proxy;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import org.apache.commons.pool2.UsageTracking;

public class JdkProxySource<T> implements ProxySource<T> {
   private final ClassLoader classLoader;
   private final Class<?>[] interfaces;

   public JdkProxySource(ClassLoader classLoader, Class<?>[] interfaces) {
      this.classLoader = classLoader;
      this.interfaces = new Class[interfaces.length];
      System.arraycopy(interfaces, 0, this.interfaces, 0, interfaces.length);
   }

   public T createProxy(T pooledObject, UsageTracking<T> usageTracking) {
      T proxy = Proxy.newProxyInstance(this.classLoader, this.interfaces, new JdkProxyHandler(pooledObject, usageTracking));
      return proxy;
   }

   public T resolveProxy(T proxy) {
      JdkProxyHandler<T> jdkProxyHandler = (JdkProxyHandler)Proxy.getInvocationHandler(proxy);
      T pooledObject = jdkProxyHandler.disableProxy();
      return pooledObject;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("JdkProxySource [classLoader=");
      builder.append(this.classLoader);
      builder.append(", interfaces=");
      builder.append(Arrays.toString(this.interfaces));
      builder.append("]");
      return builder.toString();
   }
}
