package org.apache.http.impl.client;

import org.apache.http.annotation.Immutable;

@Immutable
public class LaxRedirectStrategy extends DefaultRedirectStrategy {
   private static final String[] REDIRECT_METHODS = new String[]{"GET", "POST", "HEAD", "DELETE"};

   protected boolean isRedirectable(String method) {
      String[] arr$ = REDIRECT_METHODS;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String m = arr$[i$];
         if (m.equalsIgnoreCase(method)) {
            return true;
         }
      }

      return false;
   }
}
