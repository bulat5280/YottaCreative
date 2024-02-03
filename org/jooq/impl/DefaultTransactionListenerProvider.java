package org.jooq.impl;

import java.io.Serializable;
import org.jooq.TransactionListener;
import org.jooq.TransactionListenerProvider;

public class DefaultTransactionListenerProvider implements TransactionListenerProvider, Serializable {
   private static final long serialVersionUID = -2122007794302549679L;
   private final TransactionListener listener;

   public static TransactionListenerProvider[] providers(TransactionListener... listeners) {
      TransactionListenerProvider[] result = new TransactionListenerProvider[listeners.length];

      for(int i = 0; i < listeners.length; ++i) {
         result[i] = new DefaultTransactionListenerProvider(listeners[i]);
      }

      return result;
   }

   public DefaultTransactionListenerProvider(TransactionListener listener) {
      this.listener = listener;
   }

   public final TransactionListener provide() {
      return this.listener;
   }

   public String toString() {
      return this.listener.toString();
   }
}
