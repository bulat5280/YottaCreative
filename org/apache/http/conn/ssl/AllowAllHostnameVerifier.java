package org.apache.http.conn.ssl;

import org.apache.http.annotation.Immutable;

/** @deprecated */
@Deprecated
@Immutable
public class AllowAllHostnameVerifier extends AbstractVerifier {
   public static final AllowAllHostnameVerifier INSTANCE = new AllowAllHostnameVerifier();

   public final void verify(String host, String[] cns, String[] subjectAlts) {
   }

   public final String toString() {
      return "ALLOW_ALL";
   }
}
