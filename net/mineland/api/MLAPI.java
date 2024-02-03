package net.mineland.api;

import net.mineland.api.user.UserManager;

public final class MLAPI {
   private static UserManager userManager;

   private MLAPI() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static UserManager getUserManager() {
      return userManager;
   }

   public static void setUserManager(UserManager userManager) {
      MLAPI.userManager = userManager;
   }
}
