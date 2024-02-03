package net.mineland.core.bukkit.modules.locale;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class MapInjector implements Map {
   private Map<String, String> current;

   MapInjector(Map<String, String> current) {
      this.current = current;
   }

   public int size() {
      return 0;
   }

   public boolean isEmpty() {
      return false;
   }

   public boolean containsKey(Object key) {
      return false;
   }

   public boolean containsValue(Object value) {
      return false;
   }

   public Object get(Object key) {
      String value = (String)this.current.get(key);
      return value == null ? key : value;
   }

   public Object put(Object key, Object value) {
      return null;
   }

   public Object remove(Object key) {
      return null;
   }

   public void putAll(Map m) {
   }

   public void clear() {
   }

   public Set keySet() {
      return null;
   }

   public Collection values() {
      return null;
   }

   public Set<Entry> entrySet() {
      return null;
   }

   public Map<String, String> getCurrent() {
      return this.current;
   }

   public void setCurrent(Map<String, String> current) {
      this.current = current;
   }
}
