package com.mysql.cj.core.conf.url;

import com.mysql.cj.api.conf.DatabaseUrlContainer;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.util.StringUtils;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectionUrlParser implements DatabaseUrlContainer {
   private static final String DUMMY_SCHEMA = "cj://";
   private static final String USER_PASS_SEPARATOR = ":";
   private static final String USER_HOST_SEPARATOR = "@";
   private static final String HOSTS_SEPARATOR = ",";
   private static final String KEY_VALUE_HOST_INFO_OPENING_MARKER = "(";
   private static final String KEY_VALUE_HOST_INFO_CLOSING_MARKER = ")";
   private static final String HOSTS_LIST_OPENING_MARKERS = "[(";
   private static final String HOSTS_LIST_CLOSING_MARKERS = "])";
   private static final String ADDRESS_EQUALS_HOST_INFO_PREFIX = "ADDRESS=";
   private static final Pattern CONNECTION_STRING_PTRN = Pattern.compile("(?<scheme>[\\w:%]+)\\s*(?://(?<authority>[^/?#]*))?\\s*(?:/(?!\\s*/)(?<path>[^?#]*))?(?:\\?(?!\\s*\\?)(?<query>[^#]*))?(?:\\s*#(?<fragment>.*))?");
   private static final Pattern HOST_LIST_PTRN = Pattern.compile("^\\[(?<hosts>.*)\\]$");
   private static final Pattern GENERIC_HOST_PTRN = Pattern.compile("^(?<host>.*?)(?::(?<port>[^:]*))?$");
   private static final Pattern KEY_VALUE_HOST_PTRN = Pattern.compile("[,\\s]*(?<key>[\\w\\s%]*)(?:=(?<value>[^,=]*))?");
   private static final Pattern ADDRESS_EQUALS_HOST_PTRN = Pattern.compile("\\s*\\(\\s*(?<key>[\\w%]+)?\\s*(?:=(?<value>[^)]*))?\\)\\s*");
   private static final Pattern PROPERTIES_PTRN = Pattern.compile("[&\\s]*(?<key>[\\w\\s%]*)(?:=(?<value>[^&=]*))?");
   private final String baseConnectionString;
   private String scheme;
   private String authority;
   private String path;
   private String query;
   private List<HostInfo> parsedHosts = null;
   private Map<String, String> parsedProperties = null;

   public static ConnectionUrlParser parseConnectionString(String connString) {
      if (connString == null) {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.0"));
      } else {
         return new ConnectionUrlParser(connString);
      }
   }

   private ConnectionUrlParser(String connString) {
      this.baseConnectionString = connString;
      this.parseConnectionString();
   }

   private void parseConnectionString() {
      String connString = this.baseConnectionString;
      Matcher matcher = CONNECTION_STRING_PTRN.matcher(connString);
      if (!matcher.matches()) {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.1"));
      } else {
         this.scheme = decode(matcher.group("scheme"));
         this.authority = matcher.group("authority");
         this.path = matcher.group("path") == null ? null : decode(matcher.group("path")).trim();
         this.query = matcher.group("query");
      }
   }

   private void parseAuthoritySection() {
      if (StringUtils.isNullOrEmpty(this.authority)) {
         this.parsedHosts.add(new HostInfo());
      } else {
         List<String> authoritySegments = StringUtils.split(this.authority, ",", "[(", "])", true, StringUtils.SEARCH_MODE__MRK_WS);
         Iterator var2 = authoritySegments.iterator();

         while(var2.hasNext()) {
            String hi = (String)var2.next();
            this.parseAuthoritySegment(hi);
         }

      }
   }

   private void parseAuthoritySegment(String authSegment) {
      ConnectionUrlParser.Pair<String, String> userHostInfoSplit = this.splitByUserInfoAndHostInfo(authSegment);
      String userInfo = StringUtils.safeTrim((String)userHostInfoSplit.left);
      String user = null;
      String password = null;
      if (!StringUtils.isNullOrEmpty(userInfo)) {
         ConnectionUrlParser.Pair<String, String> userInfoPair = parseUserInfo(userInfo);
         user = decode(StringUtils.safeTrim((String)userInfoPair.left));
         password = decode(StringUtils.safeTrim((String)userInfoPair.right));
      }

      String hostInfo = StringUtils.safeTrim((String)userHostInfoSplit.right);
      HostInfo hi = this.buildHostInfoForEmptyHost(user, password, hostInfo);
      if (hi != null) {
         this.parsedHosts.add(hi);
      } else {
         hi = this.buildHostInfoResortingToUriParser(user, password, authSegment);
         if (hi != null) {
            this.parsedHosts.add(hi);
         } else {
            List<HostInfo> hiList = this.buildHostInfoResortingToSubHostsListParser(user, password, hostInfo);
            if (hiList != null) {
               this.parsedHosts.addAll(hiList);
            } else {
               hi = this.buildHostInfoResortingToKeyValueSyntaxParser(user, password, hostInfo);
               if (hi != null) {
                  this.parsedHosts.add(hi);
               } else {
                  hi = this.buildHostInfoResortingToAddressEqualsSyntaxParser(user, password, hostInfo);
                  if (hi != null) {
                     this.parsedHosts.add(hi);
                  } else {
                     hi = this.buildHostInfoResortingToGenericSyntaxParser(user, password, hostInfo);
                     if (hi != null) {
                        this.parsedHosts.add(hi);
                     } else {
                        throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.2", new Object[]{authSegment}));
                     }
                  }
               }
            }
         }
      }
   }

   private HostInfo buildHostInfoForEmptyHost(String user, String password, String hostInfo) {
      if (StringUtils.isNullOrEmpty(hostInfo)) {
         return StringUtils.isNullOrEmpty(user) && StringUtils.isNullOrEmpty(password) ? new HostInfo() : new HostInfo(this, (String)null, -1, user, password);
      } else {
         return null;
      }
   }

   private HostInfo buildHostInfoResortingToUriParser(String user, String password, String hostInfo) {
      String host = null;
      int port = -1;

      try {
         URI uri = URI.create("cj://" + hostInfo);
         if (uri.getHost() != null) {
            host = uri.getHost();
         }

         if (uri.getPort() != -1) {
            port = uri.getPort();
         }

         if (uri.getUserInfo() != null) {
            return null;
         }
      } catch (IllegalArgumentException var7) {
         return null;
      }

      return host == null && port == -1 ? null : new HostInfo(this, host, port, user, password);
   }

   private List<HostInfo> buildHostInfoResortingToSubHostsListParser(String user, String password, String hostInfo) {
      Matcher matcher = HOST_LIST_PTRN.matcher(hostInfo);
      if (!matcher.matches()) {
         return null;
      } else {
         String hosts = matcher.group("hosts");
         List<String> hostsList = StringUtils.split(hosts, ",", "[(", "])", true, StringUtils.SEARCH_MODE__MRK_WS);
         boolean maybeIPv6 = hostsList.size() == 1 && ((String)hostsList.get(0)).matches("(?i)^[\\dabcdef:]+$");
         List<HostInfo> hostInfoList = new ArrayList();
         Iterator var9 = hostsList.iterator();

         while(true) {
            while(var9.hasNext()) {
               String h = (String)var9.next();
               HostInfo hi;
               if ((hi = this.buildHostInfoForEmptyHost(user, password, h)) != null) {
                  hostInfoList.add(hi);
               } else if ((hi = this.buildHostInfoResortingToUriParser(user, password, h)) == null && (!maybeIPv6 || (hi = this.buildHostInfoResortingToUriParser(user, password, "[" + h + "]")) == null)) {
                  if ((hi = this.buildHostInfoResortingToKeyValueSyntaxParser(user, password, h)) != null) {
                     hostInfoList.add(hi);
                  } else if ((hi = this.buildHostInfoResortingToAddressEqualsSyntaxParser(user, password, h)) != null) {
                     hostInfoList.add(hi);
                  } else {
                     if ((hi = this.buildHostInfoResortingToGenericSyntaxParser(user, password, h)) == null) {
                        return null;
                     }

                     hostInfoList.add(hi);
                  }
               } else {
                  hostInfoList.add(hi);
               }
            }

            return hostInfoList;
         }
      }
   }

   private HostInfo buildHostInfoResortingToKeyValueSyntaxParser(String user, String password, String hostInfo) {
      if (hostInfo.startsWith("(") && hostInfo.endsWith(")")) {
         hostInfo = hostInfo.substring("(".length(), hostInfo.length() - ")".length());
         return new HostInfo(this, (String)null, -1, user, password, this.processKeyValuePattern(KEY_VALUE_HOST_PTRN, hostInfo));
      } else {
         return null;
      }
   }

   private HostInfo buildHostInfoResortingToAddressEqualsSyntaxParser(String user, String password, String hostInfo) {
      int p = StringUtils.indexOfIgnoreCase(hostInfo, "ADDRESS=");
      if (p != 0) {
         return null;
      } else {
         hostInfo = hostInfo.substring(p + "ADDRESS=".length()).trim();
         return new HostInfo(this, (String)null, -1, user, password, this.processKeyValuePattern(ADDRESS_EQUALS_HOST_PTRN, hostInfo));
      }
   }

   private HostInfo buildHostInfoResortingToGenericSyntaxParser(String user, String password, String hostInfo) {
      if (this.splitByUserInfoAndHostInfo(hostInfo).left != null) {
         return null;
      } else {
         ConnectionUrlParser.Pair<String, Integer> hostPortPair = parseHostPortPair(hostInfo);
         String host = decode(StringUtils.safeTrim((String)hostPortPair.left));
         Integer port = (Integer)hostPortPair.right;
         return new HostInfo(this, StringUtils.isNullOrEmpty(host) ? null : host, port, user, password);
      }
   }

   private ConnectionUrlParser.Pair<String, String> splitByUserInfoAndHostInfo(String authSegment) {
      String userInfoPart = null;
      String hostInfoPart = authSegment;
      int p = authSegment.indexOf("@");
      if (p >= 0) {
         userInfoPart = authSegment.substring(0, p);
         hostInfoPart = authSegment.substring(p + "@".length());
      }

      return new ConnectionUrlParser.Pair(userInfoPart, hostInfoPart);
   }

   public static ConnectionUrlParser.Pair<String, String> parseUserInfo(String userInfo) {
      if (StringUtils.isNullOrEmpty(userInfo)) {
         return null;
      } else {
         String[] userInfoParts = userInfo.split(":", 2);
         String userName = userInfoParts[0];
         String password = userInfoParts.length > 1 ? userInfoParts[1] : null;
         return new ConnectionUrlParser.Pair(userName, password);
      }
   }

   public static ConnectionUrlParser.Pair<String, Integer> parseHostPortPair(String hostInfo) {
      if (StringUtils.isNullOrEmpty(hostInfo)) {
         return null;
      } else {
         Matcher matcher = GENERIC_HOST_PTRN.matcher(hostInfo);
         if (matcher.matches()) {
            String host = matcher.group("host");
            String portAsString = decode(StringUtils.safeTrim(matcher.group("port")));
            Integer portAsInteger = -1;
            if (!StringUtils.isNullOrEmpty(portAsString)) {
               try {
                  portAsInteger = Integer.parseInt(portAsString);
               } catch (NumberFormatException var6) {
                  throw (WrongArgumentException)ExceptionFactory.createException((Class)WrongArgumentException.class, (String)Messages.getString("ConnectionString.3", new Object[]{hostInfo}), (Throwable)var6);
               }
            }

            return new ConnectionUrlParser.Pair(host, portAsInteger);
         } else {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.3", new Object[]{hostInfo}));
         }
      }
   }

   private void parseQuerySection() {
      if (StringUtils.isNullOrEmpty(this.query)) {
         this.parsedProperties = new HashMap();
      } else {
         this.parsedProperties = this.processKeyValuePattern(PROPERTIES_PTRN, this.query);
      }
   }

   private Map<String, String> processKeyValuePattern(Pattern pattern, String input) {
      Matcher matcher = pattern.matcher(input);
      int p = 0;

      HashMap kvMap;
      for(kvMap = new HashMap(); matcher.find(); p = matcher.end()) {
         if (matcher.start() != p) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.4", new Object[]{input.substring(p)}));
         }

         String key = decode(StringUtils.safeTrim(matcher.group("key")));
         String value = decode(StringUtils.safeTrim(matcher.group("value")));
         if (!StringUtils.isNullOrEmpty(key)) {
            kvMap.put(key, value);
         } else if (!StringUtils.isNullOrEmpty(value)) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.4", new Object[]{input.substring(p)}));
         }
      }

      if (p != input.length()) {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.4", new Object[]{input.substring(p)}));
      } else {
         return kvMap;
      }
   }

   private static String decode(String text) {
      if (StringUtils.isNullOrEmpty(text)) {
         return text;
      } else {
         try {
            return URLDecoder.decode(text, StandardCharsets.UTF_8.name());
         } catch (UnsupportedEncodingException var2) {
            return "";
         }
      }
   }

   public String getDatabaseUrl() {
      return this.baseConnectionString;
   }

   public String getScheme() {
      return this.scheme;
   }

   public String getAuthority() {
      return this.authority;
   }

   public String getPath() {
      return this.path;
   }

   public String getQuery() {
      return this.query;
   }

   public List<HostInfo> getHosts() {
      if (this.parsedHosts == null) {
         this.parsedHosts = new ArrayList();
         this.parseAuthoritySection();
      }

      return this.parsedHosts;
   }

   public Map<String, String> getProperties() {
      if (this.parsedProperties == null) {
         this.parseQuerySection();
      }

      return Collections.unmodifiableMap(this.parsedProperties);
   }

   public String toString() {
      StringBuilder asStr = new StringBuilder(super.toString());
      asStr.append(String.format(" :: {scheme: \"%s\", authority: \"%s\", path: \"%s\", query: \"%s\", parsedHosts: %s, parsedProperties: %s}", this.scheme, this.authority, this.path, this.query, this.parsedHosts, this.parsedProperties));
      return asStr.toString();
   }

   public static class Pair<T, U> {
      public final T left;
      public final U right;

      public Pair(T left, U right) {
         this.left = left;
         this.right = right;
      }

      public String toString() {
         StringBuilder asStr = new StringBuilder(super.toString());
         asStr.append(String.format(" :: { left: %s, right: %s }", this.left, this.right));
         return asStr.toString();
      }
   }
}
