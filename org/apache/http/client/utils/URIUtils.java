package org.apache.http.client.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Stack;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Immutable;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

@Immutable
public class URIUtils {
   /** @deprecated */
   @Deprecated
   public static URI createURI(String scheme, String host, int port, String path, String query, String fragment) throws URISyntaxException {
      StringBuilder buffer = new StringBuilder();
      if (host != null) {
         if (scheme != null) {
            buffer.append(scheme);
            buffer.append("://");
         }

         buffer.append(host);
         if (port > 0) {
            buffer.append(':');
            buffer.append(port);
         }
      }

      if (path == null || !path.startsWith("/")) {
         buffer.append('/');
      }

      if (path != null) {
         buffer.append(path);
      }

      if (query != null) {
         buffer.append('?');
         buffer.append(query);
      }

      if (fragment != null) {
         buffer.append('#');
         buffer.append(fragment);
      }

      return new URI(buffer.toString());
   }

   public static URI rewriteURI(URI uri, HttpHost target, boolean dropFragment) throws URISyntaxException {
      Args.notNull(uri, "URI");
      if (uri.isOpaque()) {
         return uri;
      } else {
         URIBuilder uribuilder = new URIBuilder(uri);
         if (target != null) {
            uribuilder.setScheme(target.getSchemeName());
            uribuilder.setHost(target.getHostName());
            uribuilder.setPort(target.getPort());
         } else {
            uribuilder.setScheme((String)null);
            uribuilder.setHost((String)null);
            uribuilder.setPort(-1);
         }

         if (dropFragment) {
            uribuilder.setFragment((String)null);
         }

         if (TextUtils.isEmpty(uribuilder.getPath())) {
            uribuilder.setPath("/");
         }

         return uribuilder.build();
      }
   }

   public static URI rewriteURI(URI uri, HttpHost target) throws URISyntaxException {
      return rewriteURI(uri, target, false);
   }

   public static URI rewriteURI(URI uri) throws URISyntaxException {
      Args.notNull(uri, "URI");
      if (uri.isOpaque()) {
         return uri;
      } else {
         URIBuilder uribuilder = new URIBuilder(uri);
         if (uribuilder.getUserInfo() != null) {
            uribuilder.setUserInfo((String)null);
         }

         if (TextUtils.isEmpty(uribuilder.getPath())) {
            uribuilder.setPath("/");
         }

         if (uribuilder.getHost() != null) {
            uribuilder.setHost(uribuilder.getHost().toLowerCase(Locale.ROOT));
         }

         uribuilder.setFragment((String)null);
         return uribuilder.build();
      }
   }

   public static URI rewriteURIForRoute(URI uri, RouteInfo route) throws URISyntaxException {
      if (uri == null) {
         return null;
      } else if (route.getProxyHost() != null && !route.isTunnelled()) {
         if (!uri.isAbsolute()) {
            HttpHost target = route.getTargetHost();
            return rewriteURI(uri, target, true);
         } else {
            return rewriteURI(uri);
         }
      } else {
         return uri.isAbsolute() ? rewriteURI(uri, (HttpHost)null, true) : rewriteURI(uri);
      }
   }

   public static URI resolve(URI baseURI, String reference) {
      return resolve(baseURI, URI.create(reference));
   }

   public static URI resolve(URI baseURI, URI reference) {
      Args.notNull(baseURI, "Base URI");
      Args.notNull(reference, "Reference URI");
      String s = reference.toASCIIString();
      if (s.startsWith("?")) {
         String baseUri = baseURI.toASCIIString();
         int i = baseUri.indexOf(63);
         baseUri = i > -1 ? baseUri.substring(0, i) : baseUri;
         return URI.create(baseUri + s);
      } else {
         boolean emptyReference = s.isEmpty();
         URI resolved;
         if (emptyReference) {
            resolved = baseURI.resolve(URI.create("#"));
            String resolvedString = resolved.toASCIIString();
            resolved = URI.create(resolvedString.substring(0, resolvedString.indexOf(35)));
         } else {
            resolved = baseURI.resolve(reference);
         }

         try {
            return normalizeSyntax(resolved);
         } catch (URISyntaxException var6) {
            throw new IllegalArgumentException(var6);
         }
      }
   }

   static URI normalizeSyntax(URI uri) throws URISyntaxException {
      if (!uri.isOpaque() && uri.getAuthority() != null) {
         Args.check(uri.isAbsolute(), "Base URI must be absolute");
         URIBuilder builder = new URIBuilder(uri);
         String path = builder.getPath();
         if (path != null && !path.equals("/")) {
            String[] inputSegments = path.split("/");
            Stack<String> outputSegments = new Stack();
            String[] arr$ = inputSegments;
            int len$ = inputSegments.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               String inputSegment = arr$[i$];
               if (!inputSegment.isEmpty() && !".".equals(inputSegment)) {
                  if ("..".equals(inputSegment)) {
                     if (!outputSegments.isEmpty()) {
                        outputSegments.pop();
                     }
                  } else {
                     outputSegments.push(inputSegment);
                  }
               }
            }

            StringBuilder outputBuffer = new StringBuilder();
            Iterator i$ = outputSegments.iterator();

            while(i$.hasNext()) {
               String outputSegment = (String)i$.next();
               outputBuffer.append('/').append(outputSegment);
            }

            if (path.lastIndexOf(47) == path.length() - 1) {
               outputBuffer.append('/');
            }

            builder.setPath(outputBuffer.toString());
         }

         if (builder.getScheme() != null) {
            builder.setScheme(builder.getScheme().toLowerCase(Locale.ROOT));
         }

         if (builder.getHost() != null) {
            builder.setHost(builder.getHost().toLowerCase(Locale.ROOT));
         }

         return builder.build();
      } else {
         return uri;
      }
   }

   public static HttpHost extractHost(URI uri) {
      if (uri == null) {
         return null;
      } else {
         HttpHost target = null;
         if (uri.isAbsolute()) {
            int port = uri.getPort();
            String host = uri.getHost();
            if (host == null) {
               host = uri.getAuthority();
               if (host != null) {
                  int at = host.indexOf(64);
                  if (at >= 0) {
                     if (host.length() > at + 1) {
                        host = host.substring(at + 1);
                     } else {
                        host = null;
                     }
                  }

                  if (host != null) {
                     int colon = host.indexOf(58);
                     if (colon >= 0) {
                        int pos = colon + 1;
                        int len = 0;

                        for(int i = pos; i < host.length() && Character.isDigit(host.charAt(i)); ++i) {
                           ++len;
                        }

                        if (len > 0) {
                           try {
                              port = Integer.parseInt(host.substring(pos, pos + len));
                           } catch (NumberFormatException var10) {
                           }
                        }

                        host = host.substring(0, colon);
                     }
                  }
               }
            }

            String scheme = uri.getScheme();
            if (!TextUtils.isBlank(host)) {
               try {
                  target = new HttpHost(host, port, scheme);
               } catch (IllegalArgumentException var9) {
               }
            }
         }

         return target;
      }
   }

   public static URI resolve(URI originalURI, HttpHost target, List<URI> redirects) throws URISyntaxException {
      Args.notNull(originalURI, "Request URI");
      URIBuilder uribuilder;
      if (redirects != null && !redirects.isEmpty()) {
         uribuilder = new URIBuilder((URI)redirects.get(redirects.size() - 1));
         String frag = uribuilder.getFragment();

         for(int i = redirects.size() - 1; frag == null && i >= 0; --i) {
            frag = ((URI)redirects.get(i)).getFragment();
         }

         uribuilder.setFragment(frag);
      } else {
         uribuilder = new URIBuilder(originalURI);
      }

      if (uribuilder.getFragment() == null) {
         uribuilder.setFragment(originalURI.getFragment());
      }

      if (target != null && !uribuilder.isAbsolute()) {
         uribuilder.setScheme(target.getSchemeName());
         uribuilder.setHost(target.getHostName());
         uribuilder.setPort(target.getPort());
      }

      return uribuilder.build();
   }

   private URIUtils() {
   }
}
