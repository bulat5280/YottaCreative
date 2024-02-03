package org.jooq.impl;

import java.io.Serializable;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteListenerProvider;

public class DefaultExecuteListenerProvider implements ExecuteListenerProvider, Serializable {
   private static final long serialVersionUID = -2122007794302549679L;
   private final ExecuteListener listener;

   public static ExecuteListenerProvider[] providers(ExecuteListener... listeners) {
      ExecuteListenerProvider[] result = new ExecuteListenerProvider[listeners.length];

      for(int i = 0; i < listeners.length; ++i) {
         result[i] = new DefaultExecuteListenerProvider(listeners[i]);
      }

      return result;
   }

   public DefaultExecuteListenerProvider(ExecuteListener listener) {
      this.listener = listener;
   }

   public final ExecuteListener provide() {
      return this.listener;
   }

   public String toString() {
      return this.listener.toString();
   }
}
