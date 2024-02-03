package org.apache.http.impl.cookie;

import java.util.Iterator;
import java.util.List;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie2;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@ThreadSafe
public class DefaultCookieSpec implements CookieSpec {
   private final RFC2965Spec strict;
   private final RFC2109Spec obsoleteStrict;
   private final NetscapeDraftSpec netscapeDraft;

   DefaultCookieSpec(RFC2965Spec strict, RFC2109Spec obsoleteStrict, NetscapeDraftSpec netscapeDraft) {
      this.strict = strict;
      this.obsoleteStrict = obsoleteStrict;
      this.netscapeDraft = netscapeDraft;
   }

   public DefaultCookieSpec(String[] datepatterns, boolean oneHeader) {
      this.strict = new RFC2965Spec(oneHeader, new CommonCookieAttributeHandler[]{new RFC2965VersionAttributeHandler(), new BasicPathHandler(), new RFC2965DomainAttributeHandler(), new RFC2965PortAttributeHandler(), new BasicMaxAgeHandler(), new BasicSecureHandler(), new BasicCommentHandler(), new RFC2965CommentUrlAttributeHandler(), new RFC2965DiscardAttributeHandler()});
      this.obsoleteStrict = new RFC2109Spec(oneHeader, new CommonCookieAttributeHandler[]{new RFC2109VersionHandler(), new BasicPathHandler(), new RFC2109DomainHandler(), new BasicMaxAgeHandler(), new BasicSecureHandler(), new BasicCommentHandler()});
      this.netscapeDraft = new NetscapeDraftSpec(new CommonCookieAttributeHandler[]{new BasicDomainHandler(), new BasicPathHandler(), new BasicSecureHandler(), new BasicCommentHandler(), new BasicExpiresHandler(datepatterns != null ? (String[])datepatterns.clone() : new String[]{"EEE, dd-MMM-yy HH:mm:ss z"})});
   }

   public DefaultCookieSpec() {
      this((String[])null, false);
   }

   public List<Cookie> parse(Header header, CookieOrigin origin) throws MalformedCookieException {
      Args.notNull(header, "Header");
      Args.notNull(origin, "Cookie origin");
      HeaderElement[] helems = header.getElements();
      boolean versioned = false;
      boolean netscape = false;
      HeaderElement[] arr$ = helems;
      int len$ = helems.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         HeaderElement helem = arr$[i$];
         if (helem.getParameterByName("version") != null) {
            versioned = true;
         }

         if (helem.getParameterByName("expires") != null) {
            netscape = true;
         }
      }

      if (!netscape && versioned) {
         if ("Set-Cookie2".equals(header.getName())) {
            return this.strict.parse(helems, origin);
         } else {
            return this.obsoleteStrict.parse(helems, origin);
         }
      } else {
         NetscapeDraftHeaderParser parser = NetscapeDraftHeaderParser.DEFAULT;
         CharArrayBuffer buffer;
         ParserCursor cursor;
         if (header instanceof FormattedHeader) {
            buffer = ((FormattedHeader)header).getBuffer();
            cursor = new ParserCursor(((FormattedHeader)header).getValuePos(), buffer.length());
         } else {
            String s = header.getValue();
            if (s == null) {
               throw new MalformedCookieException("Header value is null");
            }

            buffer = new CharArrayBuffer(s.length());
            buffer.append(s);
            cursor = new ParserCursor(0, buffer.length());
         }

         helems = new HeaderElement[]{parser.parseHeader(buffer, cursor)};
         return this.netscapeDraft.parse(helems, origin);
      }
   }

   public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
      Args.notNull(cookie, "Cookie");
      Args.notNull(origin, "Cookie origin");
      if (cookie.getVersion() > 0) {
         if (cookie instanceof SetCookie2) {
            this.strict.validate(cookie, origin);
         } else {
            this.obsoleteStrict.validate(cookie, origin);
         }
      } else {
         this.netscapeDraft.validate(cookie, origin);
      }

   }

   public boolean match(Cookie cookie, CookieOrigin origin) {
      Args.notNull(cookie, "Cookie");
      Args.notNull(origin, "Cookie origin");
      if (cookie.getVersion() > 0) {
         return cookie instanceof SetCookie2 ? this.strict.match(cookie, origin) : this.obsoleteStrict.match(cookie, origin);
      } else {
         return this.netscapeDraft.match(cookie, origin);
      }
   }

   public List<Header> formatCookies(List<Cookie> cookies) {
      Args.notNull(cookies, "List of cookies");
      int version = Integer.MAX_VALUE;
      boolean isSetCookie2 = true;
      Iterator i$ = cookies.iterator();

      while(i$.hasNext()) {
         Cookie cookie = (Cookie)i$.next();
         if (!(cookie instanceof SetCookie2)) {
            isSetCookie2 = false;
         }

         if (cookie.getVersion() < version) {
            version = cookie.getVersion();
         }
      }

      if (version > 0) {
         if (isSetCookie2) {
            return this.strict.formatCookies(cookies);
         } else {
            return this.obsoleteStrict.formatCookies(cookies);
         }
      } else {
         return this.netscapeDraft.formatCookies(cookies);
      }
   }

   public int getVersion() {
      return this.strict.getVersion();
   }

   public Header getVersionHeader() {
      return null;
   }

   public String toString() {
      return "default";
   }
}
