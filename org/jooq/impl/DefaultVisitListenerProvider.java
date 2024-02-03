package org.jooq.impl;

import java.io.Serializable;
import org.jooq.VisitListener;
import org.jooq.VisitListenerProvider;

public class DefaultVisitListenerProvider implements VisitListenerProvider, Serializable {
   private static final long serialVersionUID = -2122007794302549679L;
   private final VisitListener listener;

   public static VisitListenerProvider[] providers(VisitListener... listeners) {
      VisitListenerProvider[] result = new VisitListenerProvider[listeners.length];

      for(int i = 0; i < listeners.length; ++i) {
         result[i] = new DefaultVisitListenerProvider(listeners[i]);
      }

      return result;
   }

   public DefaultVisitListenerProvider(VisitListener listener) {
      this.listener = listener;
   }

   public final VisitListener provide() {
      return this.listener;
   }

   public String toString() {
      return this.listener.toString();
   }
}
