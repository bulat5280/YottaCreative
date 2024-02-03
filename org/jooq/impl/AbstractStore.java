package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import org.jooq.Attachable;
import org.jooq.AttachableInternal;
import org.jooq.Configuration;
import org.jooq.DSLContext;

abstract class AbstractStore implements AttachableInternal {
   private static final long serialVersionUID = -2989496800221194411L;
   private Configuration configuration;

   AbstractStore() {
      this((Configuration)null);
   }

   AbstractStore(Configuration configuration) {
      this.configuration = configuration;
   }

   abstract List<Attachable> getAttachables();

   public final void attach(Configuration c) {
      this.configuration = c;
      List<Attachable> attachables = this.getAttachables();
      int size = attachables.size();

      for(int i = 0; i < size; ++i) {
         Attachable attachable = (Attachable)attachables.get(i);
         if (attachable != null) {
            attachable.attach(c);
         }
      }

   }

   public final void detach() {
      this.attach((Configuration)null);
   }

   public final Configuration configuration() {
      return this.configuration;
   }

   protected final DSLContext create() {
      return DSL.using(this.configuration());
   }

   abstract int size();

   abstract Object get(int var1);

   public int hashCode() {
      int hashCode = 1;

      for(int i = 0; i < this.size(); ++i) {
         Object obj = this.get(i);
         if (obj == null) {
            hashCode = 31 * hashCode;
         } else if (obj.getClass().isArray()) {
            hashCode = 31 * hashCode;
         } else {
            hashCode = 31 * hashCode + obj.hashCode();
         }
      }

      return hashCode;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         if (obj instanceof AbstractStore) {
            AbstractStore that = (AbstractStore)obj;
            if (this.size() == that.size()) {
               for(int i = 0; i < this.size(); ++i) {
                  Object thisValue = this.get(i);
                  Object thatValue = that.get(i);
                  if (thisValue != null || thatValue != null) {
                     if (thisValue == null || thatValue == null) {
                        return false;
                     }

                     if (thisValue.getClass().isArray() && thatValue.getClass().isArray()) {
                        if (thisValue.getClass() == byte[].class && thatValue.getClass() == byte[].class) {
                           if (!Arrays.equals((byte[])((byte[])thisValue), (byte[])((byte[])thatValue))) {
                              return false;
                           }
                        } else {
                           if (thisValue.getClass().getComponentType().isPrimitive() || thatValue.getClass().getComponentType().isPrimitive()) {
                              return false;
                           }

                           if (!Arrays.equals((Object[])((Object[])thisValue), (Object[])((Object[])thatValue))) {
                              return false;
                           }
                        }
                     } else if (!thisValue.equals(thatValue)) {
                        return false;
                     }
                  }
               }

               return true;
            }
         }

         return false;
      }
   }
}
