package org.apache.http.conn.ssl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.annotation.Immutable;
import org.apache.http.conn.util.DomainType;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.conn.util.PublicSuffixMatcher;

@Immutable
public final class DefaultHostnameVerifier implements HostnameVerifier {
   static final int DNS_NAME_TYPE = 2;
   static final int IP_ADDRESS_TYPE = 7;
   private final Log log;
   private final PublicSuffixMatcher publicSuffixMatcher;

   public DefaultHostnameVerifier(PublicSuffixMatcher publicSuffixMatcher) {
      this.log = LogFactory.getLog(this.getClass());
      this.publicSuffixMatcher = publicSuffixMatcher;
   }

   public DefaultHostnameVerifier() {
      this((PublicSuffixMatcher)null);
   }

   public boolean verify(String host, SSLSession session) {
      try {
         Certificate[] certs = session.getPeerCertificates();
         X509Certificate x509 = (X509Certificate)certs[0];
         this.verify(host, x509);
         return true;
      } catch (SSLException var5) {
         if (this.log.isDebugEnabled()) {
            this.log.debug(var5.getMessage(), var5);
         }

         return false;
      }
   }

   public void verify(String host, X509Certificate cert) throws SSLException {
      DefaultHostnameVerifier.TYPE hostFormat = DefaultHostnameVerifier.TYPE.DNS;
      if (InetAddressUtils.isIPv4Address(host)) {
         hostFormat = DefaultHostnameVerifier.TYPE.IPv4;
      } else {
         String s = host;
         if (host.startsWith("[") && host.endsWith("]")) {
            s = host.substring(1, host.length() - 1);
         }

         if (InetAddressUtils.isIPv6Address(s)) {
            hostFormat = DefaultHostnameVerifier.TYPE.IPv6;
         }
      }

      int subjectType = hostFormat != DefaultHostnameVerifier.TYPE.IPv4 && hostFormat != DefaultHostnameVerifier.TYPE.IPv6 ? 2 : 7;
      List<String> subjectAlts = extractSubjectAlts(cert, subjectType);
      if (subjectAlts != null && !subjectAlts.isEmpty()) {
         switch(hostFormat) {
         case IPv4:
            matchIPAddress(host, subjectAlts);
            break;
         case IPv6:
            matchIPv6Address(host, subjectAlts);
            break;
         default:
            matchDNSName(host, subjectAlts, this.publicSuffixMatcher);
         }
      } else {
         X500Principal subjectPrincipal = cert.getSubjectX500Principal();
         String cn = extractCN(subjectPrincipal.getName("RFC2253"));
         if (cn == null) {
            throw new SSLException("Certificate subject for <" + host + "> doesn't contain " + "a common name and does not have alternative names");
         }

         matchCN(host, cn, this.publicSuffixMatcher);
      }

   }

   static void matchIPAddress(String host, List<String> subjectAlts) throws SSLException {
      for(int i = 0; i < subjectAlts.size(); ++i) {
         String subjectAlt = (String)subjectAlts.get(i);
         if (host.equals(subjectAlt)) {
            return;
         }
      }

      throw new SSLException("Certificate for <" + host + "> doesn't match any " + "of the subject alternative names: " + subjectAlts);
   }

   static void matchIPv6Address(String host, List<String> subjectAlts) throws SSLException {
      String normalisedHost = normaliseAddress(host);

      for(int i = 0; i < subjectAlts.size(); ++i) {
         String subjectAlt = (String)subjectAlts.get(i);
         String normalizedSubjectAlt = normaliseAddress(subjectAlt);
         if (normalisedHost.equals(normalizedSubjectAlt)) {
            return;
         }
      }

      throw new SSLException("Certificate for <" + host + "> doesn't match any " + "of the subject alternative names: " + subjectAlts);
   }

   static void matchDNSName(String host, List<String> subjectAlts, PublicSuffixMatcher publicSuffixMatcher) throws SSLException {
      String normalizedHost = host.toLowerCase(Locale.ROOT);

      for(int i = 0; i < subjectAlts.size(); ++i) {
         String subjectAlt = (String)subjectAlts.get(i);
         String normalizedSubjectAlt = subjectAlt.toLowerCase(Locale.ROOT);
         if (matchIdentityStrict(normalizedHost, normalizedSubjectAlt, publicSuffixMatcher)) {
            return;
         }
      }

      throw new SSLException("Certificate for <" + host + "> doesn't match any " + "of the subject alternative names: " + subjectAlts);
   }

   static void matchCN(String host, String cn, PublicSuffixMatcher publicSuffixMatcher) throws SSLException {
      if (!matchIdentityStrict(host, cn, publicSuffixMatcher)) {
         throw new SSLException("Certificate for <" + host + "> doesn't match " + "common name of the certificate subject: " + cn);
      }
   }

   static boolean matchDomainRoot(String host, String domainRoot) {
      if (domainRoot == null) {
         return false;
      } else {
         return host.endsWith(domainRoot) && (host.length() == domainRoot.length() || host.charAt(host.length() - domainRoot.length() - 1) == '.');
      }
   }

   private static boolean matchIdentity(String host, String identity, PublicSuffixMatcher publicSuffixMatcher, boolean strict) {
      if (publicSuffixMatcher != null && host.contains(".") && !matchDomainRoot(host, publicSuffixMatcher.getDomainRoot(identity, DomainType.ICANN))) {
         return false;
      } else {
         int asteriskIdx = identity.indexOf(42);
         if (asteriskIdx != -1) {
            String prefix = identity.substring(0, asteriskIdx);
            String suffix = identity.substring(asteriskIdx + 1);
            if (!prefix.isEmpty() && !host.startsWith(prefix)) {
               return false;
            } else if (!suffix.isEmpty() && !host.endsWith(suffix)) {
               return false;
            } else {
               if (strict) {
                  String remainder = host.substring(prefix.length(), host.length() - suffix.length());
                  if (remainder.contains(".")) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return host.equalsIgnoreCase(identity);
         }
      }
   }

   static boolean matchIdentity(String host, String identity, PublicSuffixMatcher publicSuffixMatcher) {
      return matchIdentity(host, identity, publicSuffixMatcher, false);
   }

   static boolean matchIdentity(String host, String identity) {
      return matchIdentity(host, identity, (PublicSuffixMatcher)null, false);
   }

   static boolean matchIdentityStrict(String host, String identity, PublicSuffixMatcher publicSuffixMatcher) {
      return matchIdentity(host, identity, publicSuffixMatcher, true);
   }

   static boolean matchIdentityStrict(String host, String identity) {
      return matchIdentity(host, identity, (PublicSuffixMatcher)null, true);
   }

   static String extractCN(String subjectPrincipal) throws SSLException {
      if (subjectPrincipal == null) {
         return null;
      } else {
         try {
            LdapName subjectDN = new LdapName(subjectPrincipal);
            List<Rdn> rdns = subjectDN.getRdns();

            for(int i = rdns.size() - 1; i >= 0; --i) {
               Rdn rds = (Rdn)rdns.get(i);
               Attributes attributes = rds.toAttributes();
               Attribute cn = attributes.get("cn");
               if (cn != null) {
                  try {
                     Object value = cn.get();
                     if (value != null) {
                        return value.toString();
                     }
                  } catch (NoSuchElementException var8) {
                  } catch (NamingException var9) {
                  }
               }
            }

            return null;
         } catch (InvalidNameException var10) {
            throw new SSLException(subjectPrincipal + " is not a valid X500 distinguished name");
         }
      }
   }

   static List<String> extractSubjectAlts(X509Certificate cert, int subjectType) {
      Collection c = null;

      try {
         c = cert.getSubjectAlternativeNames();
      } catch (CertificateParsingException var9) {
      }

      List<String> subjectAltList = null;
      if (c != null) {
         Iterator i$ = c.iterator();

         while(i$.hasNext()) {
            List<?> aC = (List)i$.next();
            int type = (Integer)aC.get(0);
            if (type == subjectType) {
               String s = (String)aC.get(1);
               if (subjectAltList == null) {
                  subjectAltList = new ArrayList();
               }

               subjectAltList.add(s);
            }
         }
      }

      return subjectAltList;
   }

   static String normaliseAddress(String hostname) {
      if (hostname == null) {
         return hostname;
      } else {
         try {
            InetAddress inetAddress = InetAddress.getByName(hostname);
            return inetAddress.getHostAddress();
         } catch (UnknownHostException var2) {
            return hostname;
         }
      }
   }

   static enum TYPE {
      IPv4,
      IPv6,
      DNS;
   }
}
