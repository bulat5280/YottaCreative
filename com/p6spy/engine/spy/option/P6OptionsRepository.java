package com.p6spy.engine.spy.option;

import com.p6spy.engine.common.ClassHasher;
import com.p6spy.engine.common.CustomHashedHashSet;
import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6Factory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class P6OptionsRepository {
   private final Map<String, Object> map = new HashMap();
   private Set<P6OptionsRepository.DelayedOptionChange> delayedOptionChanges = new HashSet();
   private List<P6OptionChangedListener> listeners = new ArrayList();
   private boolean initCompleted = false;

   public void initCompleted() {
      this.initCompleted = true;
      this.fireDelayedOptionChanges();
   }

   public <T> boolean set(Class<T> type, String key, Object value) {
      return value == null ? false : this.setOrUnSet(type, key, value, (Object)null);
   }

   public <T> boolean setOrUnSet(Class<T> type, String key, Object value, Object defaultValue) {
      if (key != null && !key.isEmpty()) {
         if (value == null) {
            value = defaultValue;
         }

         if (value == null) {
            this.setInternal(key, value);
         } else {
            this.setInternal(key, this.parse(type, value));
         }

         return true;
      } else {
         throw new IllegalArgumentException("key can be neither null nor empty!");
      }
   }

   <T> Object parse(Class<T> type, Object value) {
      if (type.isAssignableFrom(Boolean.class)) {
         return P6Util.isTrue(value.toString(), true);
      } else if (type.isAssignableFrom(String.class)) {
         return value.toString();
      } else if (type.isAssignableFrom(Long.class)) {
         return Long.parseLong(value.toString());
      } else if (type.isAssignableFrom(Integer.class)) {
         return Integer.parseInt(value.toString());
      } else if (type.isAssignableFrom(Set.class)) {
         throw new IllegalArgumentException("please call the setSet() method instead!");
      } else if (!type.isAssignableFrom(Collection.class) && !type.isAssignableFrom(List.class)) {
         if (type.isAssignableFrom(Pattern.class)) {
            return Pattern.compile(value.toString());
         } else if (type.isAssignableFrom(Category.class)) {
            return new Category(value.toString());
         } else {
            Object instance;
            try {
               instance = P6Util.forName(value.toString()).newInstance();
            } catch (Exception var8) {
               try {
                  ClassLoader loader = ClassLoader.getSystemClassLoader();
                  instance = loader.loadClass(value.toString()).newInstance();
               } catch (Exception var7) {
                  System.err.println("Cannot instantiate " + value + ", even on second attempt. ");
                  var7.printStackTrace(System.err);
                  return null;
               }
            }

            try {
               return instance;
            } catch (ClassCastException var6) {
               System.err.println("Value " + value + ", is not of expected type. Error: " + var6);
               return null;
            }
         }
      } else {
         throw new IllegalArgumentException("type not supported:" + type.getName());
      }
   }

   void setInternal(String key, Object value) {
      Object oldValue = this.map.put(key, value);
      this.fireOptionChanged(key, oldValue, value);
   }

   public <T> boolean setSet(Class<T> type, String key, String csv) {
      if (csv == null) {
         return false;
      } else {
         List<String> collection = P6Util.parseCSVList(csv);
         if (collection == null) {
            return false;
         } else {
            Set<T> oldValue = this.getSet(type, key);
            Set<T> newValue = null;
            if (collection.isEmpty()) {
               this.map.remove(key);
            } else {
               if (type.equals(P6Factory.class)) {
                  newValue = new CustomHashedHashSet(new ClassHasher());
               } else {
                  newValue = new HashSet();
               }

               Iterator var7 = collection.iterator();

               while(var7.hasNext()) {
                  String item = (String)var7.next();
                  if (item.toString().startsWith("-")) {
                     throw new IllegalArgumentException("- prefix has been deprecated for list-like properties! Full overriding happens (see: http://p6spy.github.io/p6spy/2.0/configandusage.html)");
                  }

                  ((Set)newValue).add(this.parse(type, item));
               }

               this.map.put(key, newValue);
            }

            this.fireOptionChanged(key, oldValue, newValue);
            return true;
         }
      }
   }

   void fireOptionChanged(String key, Object oldValue, Object newValue) {
      if (this.initCompleted) {
         this.fireDelayedOptionChanges();
         Iterator var4 = this.listeners.iterator();

         while(var4.hasNext()) {
            P6OptionChangedListener listener = (P6OptionChangedListener)var4.next();
            listener.optionChanged(key, oldValue, newValue);
         }
      } else {
         this.delayedOptionChanges.add(new P6OptionsRepository.DelayedOptionChange(key, oldValue, newValue));
      }

   }

   private synchronized void fireDelayedOptionChanges() {
      if (null != this.delayedOptionChanges) {
         Iterator var1 = this.delayedOptionChanges.iterator();

         while(var1.hasNext()) {
            P6OptionsRepository.DelayedOptionChange delayedOption = (P6OptionsRepository.DelayedOptionChange)var1.next();
            Iterator var3 = this.listeners.iterator();

            while(var3.hasNext()) {
               P6OptionChangedListener listener = (P6OptionChangedListener)var3.next();
               listener.optionChanged(delayedOption.getKey(), delayedOption.getOldValue(), delayedOption.getNewValue());
            }
         }

         this.delayedOptionChanges = null;
      }
   }

   public <T> T get(Class<T> type, String key) {
      if (!this.initCompleted) {
         throw new IllegalStateException("Options didn't load completely, yet!");
      } else {
         return this.map.get(key);
      }
   }

   public <T> Set<T> getSet(Class<T> type, String key) {
      return (Set)this.map.get(key);
   }

   public void registerOptionChangedListener(P6OptionChangedListener listener) {
      if (listener == null) {
         throw new IllegalArgumentException("P6OptionChangedListener can't be null!");
      } else {
         this.listeners.add(listener);
      }
   }

   public void unregisterOptionChangedListener(P6OptionChangedListener listener) {
      if (listener == null) {
         throw new IllegalArgumentException("P6OptionChangedListener can't be null!");
      } else {
         this.listeners.remove(listener);
      }
   }

   class DelayedOptionChange {
      private final String key;
      private final Object oldValue;
      private final Object newValue;

      public DelayedOptionChange(String key, Object oldValue, Object newValue) {
         if (null != key && !key.isEmpty()) {
            this.key = key;
            this.oldValue = oldValue;
            this.newValue = newValue;
         } else {
            throw new IllegalArgumentException("key can be neither null nor empty!");
         }
      }

      public int hashCode() {
         return this.key.hashCode();
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            P6OptionsRepository.DelayedOptionChange other = (P6OptionsRepository.DelayedOptionChange)obj;
            if (!this.getOuterType().equals(other.getOuterType())) {
               return false;
            } else {
               if (this.key == null) {
                  if (other.key != null) {
                     return false;
                  }
               } else if (!this.key.equals(other.key)) {
                  return false;
               }

               return true;
            }
         }
      }

      public String getKey() {
         return this.key;
      }

      public Object getOldValue() {
         return this.oldValue;
      }

      public Object getNewValue() {
         return this.newValue;
      }

      private P6OptionsRepository getOuterType() {
         return P6OptionsRepository.this;
      }
   }
}
