package org.apache.http.protocol;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.http.annotation.GuardedBy;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.util.Args;

@ThreadSafe
public class UriPatternMatcher<T> {
   @GuardedBy("this")
   private final Map<String, T> map = new HashMap();

   public synchronized void register(String pattern, T obj) {
      Args.notNull(pattern, "URI request pattern");
      this.map.put(pattern, obj);
   }

   public synchronized void unregister(String pattern) {
      if (pattern != null) {
         this.map.remove(pattern);
      }
   }

   /** @deprecated */
   @Deprecated
   public synchronized void setHandlers(Map<String, T> map) {
      Args.notNull(map, "Map of handlers");
      this.map.clear();
      this.map.putAll(map);
   }

   /** @deprecated */
   @Deprecated
   public synchronized void setObjects(Map<String, T> map) {
      Args.notNull(map, "Map of handlers");
      this.map.clear();
      this.map.putAll(map);
   }

   /** @deprecated */
   @Deprecated
   public synchronized Map<String, T> getObjects() {
      return this.map;
   }

   public synchronized T lookup(String path) {
      Args.notNull(path, "Request path");
      T obj = this.map.get(path);
      if (obj == null) {
         String bestMatch = null;
         Iterator i$ = this.map.keySet().iterator();

         while(true) {
            String pattern;
            do {
               do {
                  if (!i$.hasNext()) {
                     return obj;
                  }

                  pattern = (String)i$.next();
               } while(!this.matchUriRequestPattern(pattern, path));
            } while(bestMatch != null && bestMatch.length() >= pattern.length() && (bestMatch.length() != pattern.length() || !pattern.endsWith("*")));

            obj = this.map.get(pattern);
            bestMatch = pattern;
         }
      } else {
         return obj;
      }
   }

   protected boolean matchUriRequestPattern(String pattern, String path) {
      if (pattern.equals("*")) {
         return true;
      } else {
         return pattern.endsWith("*") && path.startsWith(pattern.substring(0, pattern.length() - 1)) || pattern.startsWith("*") && path.endsWith(pattern.substring(1, pattern.length()));
      }
   }

   public String toString() {
      return this.map.toString();
   }
}
