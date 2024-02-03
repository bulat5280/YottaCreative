package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.TransactionContext;
import org.jooq.TransactionListener;
import org.jooq.TransactionListenerProvider;

class TransactionListeners implements TransactionListener {
   private final TransactionListener[] listeners;

   TransactionListeners(Configuration configuration) {
      TransactionListenerProvider[] providers = configuration.transactionListenerProviders();
      this.listeners = new TransactionListener[providers.length];

      for(int i = 0; i < providers.length; ++i) {
         this.listeners[i] = providers[i].provide();
      }

   }

   public final void beginStart(TransactionContext ctx) {
      TransactionListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TransactionListener listener = var2[var4];
         listener.beginStart(ctx);
      }

   }

   public final void beginEnd(TransactionContext ctx) {
      TransactionListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TransactionListener listener = var2[var4];
         listener.beginEnd(ctx);
      }

   }

   public final void commitStart(TransactionContext ctx) {
      TransactionListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TransactionListener listener = var2[var4];
         listener.commitStart(ctx);
      }

   }

   public final void commitEnd(TransactionContext ctx) {
      TransactionListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TransactionListener listener = var2[var4];
         listener.commitEnd(ctx);
      }

   }

   public final void rollbackStart(TransactionContext ctx) {
      TransactionListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TransactionListener listener = var2[var4];
         listener.rollbackStart(ctx);
      }

   }

   public final void rollbackEnd(TransactionContext ctx) {
      TransactionListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TransactionListener listener = var2[var4];
         listener.rollbackEnd(ctx);
      }

   }
}
