package org.apache.http.impl.cookie;

import org.apache.http.annotation.Immutable;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;

/** @deprecated */
@Deprecated
@Immutable
public class BrowserCompatVersionAttributeHandler extends AbstractCookieAttributeHandler implements CommonCookieAttributeHandler {
   public void parse(SetCookie cookie, String value) throws MalformedCookieException {
      Args.notNull(cookie, "Cookie");
      if (value == null) {
         throw new MalformedCookieException("Missing value for version attribute");
      } else {
         int version = 0;

         try {
            version = Integer.parseInt(value);
         } catch (NumberFormatException var5) {
         }

         cookie.setVersion(version);
      }
   }

   public String getAttributeName() {
      return "version";
   }
}
