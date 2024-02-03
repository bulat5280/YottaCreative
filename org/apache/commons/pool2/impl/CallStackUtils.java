package org.apache.commons.pool2.impl;

import java.security.AccessControlException;

public final class CallStackUtils {
   private static final boolean CAN_CREATE_SECURITY_MANAGER = canCreateSecurityManager();

   private static boolean canCreateSecurityManager() {
      SecurityManager manager = System.getSecurityManager();
      if (manager == null) {
         return true;
      } else {
         try {
            manager.checkPermission(new RuntimePermission("createSecurityManager"));
            return true;
         } catch (AccessControlException var2) {
            return false;
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public static CallStack newCallStack(String messageFormat, boolean useTimestamp) {
      return newCallStack(messageFormat, useTimestamp, false);
   }

   public static CallStack newCallStack(String messageFormat, boolean useTimestamp, boolean requireFullStackTrace) {
      return (CallStack)(CAN_CREATE_SECURITY_MANAGER && !requireFullStackTrace ? new SecurityManagerCallStack(messageFormat, useTimestamp) : new ThrowableCallStack(messageFormat, useTimestamp));
   }

   private CallStackUtils() {
   }
}
