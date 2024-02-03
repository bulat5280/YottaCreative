package org.jooq.conf;

import java.io.Serializable;

abstract class SettingsBase implements Serializable, Cloneable {
   private static final long serialVersionUID = 958655542175990197L;

   public Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new RuntimeException(var2);
      }
   }
}
