package org.jooq.impl;

import java.io.Serializable;
import org.jooq.RecordListener;
import org.jooq.RecordListenerProvider;

public class DefaultRecordListenerProvider implements RecordListenerProvider, Serializable {
   private static final long serialVersionUID = -2122007794302549679L;
   private final RecordListener listener;

   public static RecordListenerProvider[] providers(RecordListener... listeners) {
      RecordListenerProvider[] result = new RecordListenerProvider[listeners.length];

      for(int i = 0; i < listeners.length; ++i) {
         result[i] = new DefaultRecordListenerProvider(listeners[i]);
      }

      return result;
   }

   public DefaultRecordListenerProvider(RecordListener listener) {
      this.listener = listener;
   }

   public final RecordListener provide() {
      return this.listener;
   }

   public String toString() {
      return this.listener.toString();
   }
}
