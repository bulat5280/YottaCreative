package org.apache.http.client.utils;

import org.apache.http.annotation.Immutable;

/** @deprecated */
@Deprecated
@Immutable
public class Punycode {
   private static final Idn impl;

   public static String toUnicode(String punycode) {
      return impl.toUnicode(punycode);
   }

   static {
      Object _impl;
      try {
         _impl = new JdkIdn();
      } catch (Exception var2) {
         _impl = new Rfc3492Idn();
      }

      impl = (Idn)_impl;
   }
}
