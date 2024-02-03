package org.apache.commons.math3.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.commons.math3.exception.MathIllegalArgumentException;

public class TransformerMap implements NumberTransformer, Serializable {
   private static final long serialVersionUID = 4605318041528645258L;
   private NumberTransformer defaultTransformer = null;
   private Map<Class<?>, NumberTransformer> map = null;

   public TransformerMap() {
      this.map = new HashMap();
      this.defaultTransformer = new DefaultTransformer();
   }

   public boolean containsClass(Class<?> key) {
      return this.map.containsKey(key);
   }

   public boolean containsTransformer(NumberTransformer value) {
      return this.map.containsValue(value);
   }

   public NumberTransformer getTransformer(Class<?> key) {
      return (NumberTransformer)this.map.get(key);
   }

   public NumberTransformer putTransformer(Class<?> key, NumberTransformer transformer) {
      return (NumberTransformer)this.map.put(key, transformer);
   }

   public NumberTransformer removeTransformer(Class<?> key) {
      return (NumberTransformer)this.map.remove(key);
   }

   public void clear() {
      this.map.clear();
   }

   public Set<Class<?>> classes() {
      return this.map.keySet();
   }

   public Collection<NumberTransformer> transformers() {
      return this.map.values();
   }

   public double transform(Object o) throws MathIllegalArgumentException {
      double value = Double.NaN;
      if (!(o instanceof Number) && !(o instanceof String)) {
         NumberTransformer trans = this.getTransformer(o.getClass());
         if (trans != null) {
            value = trans.transform(o);
         }
      } else {
         value = this.defaultTransformer.transform(o);
      }

      return value;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other instanceof TransformerMap) {
         TransformerMap rhs = (TransformerMap)other;
         if (!this.defaultTransformer.equals(rhs.defaultTransformer)) {
            return false;
         } else if (this.map.size() != rhs.map.size()) {
            return false;
         } else {
            Iterator i$ = this.map.entrySet().iterator();

            Entry entry;
            do {
               if (!i$.hasNext()) {
                  return true;
               }

               entry = (Entry)i$.next();
            } while(((NumberTransformer)entry.getValue()).equals(rhs.map.get(entry.getKey())));

            return false;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int hash = this.defaultTransformer.hashCode();

      NumberTransformer t;
      for(Iterator i$ = this.map.values().iterator(); i$.hasNext(); hash = hash * 31 + t.hashCode()) {
         t = (NumberTransformer)i$.next();
      }

      return hash;
   }
}
