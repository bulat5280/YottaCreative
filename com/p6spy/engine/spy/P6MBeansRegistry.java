package com.p6spy.engine.spy;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.StandardMBean;

public class P6MBeansRegistry {
   private final Collection<ObjectName> mBeans = new ArrayList();
   public static final String PACKAGE_NAME = "com.p6spy";

   public void registerMBeans(Collection<P6LoadableOptions> allOptions) throws MBeanRegistrationException, InstanceNotFoundException, MalformedObjectNameException, NotCompliantMBeanException {
      boolean jmx = true;
      String jmxPrefix = "";
      Iterator var4 = allOptions.iterator();

      P6LoadableOptions options;
      while(var4.hasNext()) {
         options = (P6LoadableOptions)var4.next();
         if (options instanceof P6SpyOptions) {
            jmx = ((P6SpyOptions)options).getJmx();
            jmxPrefix = ((P6SpyOptions)options).getJmxPrefix();
            break;
         }
      }

      if (jmx) {
         this.unregisterAllMBeans(jmxPrefix);
         var4 = allOptions.iterator();

         while(var4.hasNext()) {
            options = (P6LoadableOptions)var4.next();

            try {
               this.registerMBean(options, jmxPrefix);
            } catch (InstanceAlreadyExistsException var7) {
               this.registerMBeans(allOptions);
            }
         }

      }
   }

   protected void registerMBean(P6LoadableOptions mBean, String jmxPrefix) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException {
      this.checkMBean(mBean);
      ObjectName mBeanObjectName = this.getObjectName(mBean, jmxPrefix);
      ManagementFactory.getPlatformMBeanServer().registerMBean(mBean, mBeanObjectName);
      this.mBeans.add(mBeanObjectName);
   }

   public void unregisterAllMBeans(String jmxPrefix) throws MBeanRegistrationException, MalformedObjectNameException {
      MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
      Iterator var3 = this.mBeans.iterator();

      ObjectName objectName;
      while(var3.hasNext()) {
         objectName = (ObjectName)var3.next();

         try {
            mbs.unregisterMBean(objectName);
         } catch (InstanceNotFoundException var7) {
         }
      }

      this.mBeans.clear();
      var3 = mbs.queryNames(new ObjectName(getPackageName(jmxPrefix) + ":name=com.p6spy.*"), (QueryExp)null).iterator();

      while(var3.hasNext()) {
         objectName = (ObjectName)var3.next();

         try {
            mbs.unregisterMBean(objectName);
         } catch (InstanceNotFoundException var6) {
         }
      }

   }

   private void checkMBean(P6LoadableOptions mBean) {
      if (null == mBean) {
         throw new IllegalArgumentException("mBean is null!");
      } else if (!(mBean instanceof StandardMBean)) {
         throw new IllegalArgumentException("mBean has to be instance of the StandardMBean class! But is not: " + mBean);
      }
   }

   protected ObjectName getObjectName(P6LoadableOptions mBean, String jmxPrefix) throws MalformedObjectNameException {
      return new ObjectName(getPackageName(jmxPrefix) + ":name=" + mBean.getClass().getName());
   }

   protected static String getPackageName(String jmxPrefix) {
      return "com.p6spy" + (null != jmxPrefix && !jmxPrefix.isEmpty() ? "." + jmxPrefix : "");
   }
}
