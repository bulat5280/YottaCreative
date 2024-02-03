package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.ExecuteType;
import org.jooq.Record;
import org.jooq.RecordListener;
import org.jooq.RecordListenerProvider;
import org.jooq.exception.ControlFlowSignal;

final class RecordDelegate<R extends Record> {
   private final Configuration configuration;
   private final R record;
   private final RecordDelegate.RecordLifecycleType type;

   RecordDelegate(Configuration configuration, R record) {
      this(configuration, record, RecordDelegate.RecordLifecycleType.LOAD);
   }

   RecordDelegate(Configuration configuration, R record, RecordDelegate.RecordLifecycleType type) {
      this.configuration = configuration;
      this.record = record;
      this.type = type;
   }

   static final <R extends Record> RecordDelegate<R> delegate(Configuration configuration, R record) {
      return new RecordDelegate(configuration, record);
   }

   static final <R extends Record> RecordDelegate<R> delegate(Configuration configuration, R record, RecordDelegate.RecordLifecycleType type) {
      return new RecordDelegate(configuration, record, type);
   }

   final <E extends Exception> R operate(RecordOperation<? super R, E> operation) throws E {
      RecordListenerProvider[] providers = null;
      RecordListener[] listeners = null;
      DefaultRecordContext ctx = null;
      E exception = null;
      if (this.configuration != null) {
         providers = this.configuration.recordListenerProviders();
         if (providers != null && providers.length > 0) {
            listeners = new RecordListener[providers.length];
            ctx = new DefaultRecordContext(this.configuration, this.executeType(), new Record[]{this.record});

            for(int i = 0; i < providers.length; ++i) {
               listeners[i] = providers[i].provide();
            }
         }
      }

      int var7;
      int var8;
      RecordListener listener;
      RecordListener[] var12;
      if (listeners != null) {
         var12 = listeners;
         var7 = listeners.length;

         for(var8 = 0; var8 < var7; ++var8) {
            listener = var12[var8];
            switch(this.type) {
            case LOAD:
               listener.loadStart(ctx);
               break;
            case REFRESH:
               listener.refreshStart(ctx);
               break;
            case STORE:
               listener.storeStart(ctx);
               break;
            case INSERT:
               listener.insertStart(ctx);
               break;
            case UPDATE:
               listener.updateStart(ctx);
               break;
            case DELETE:
               listener.deleteStart(ctx);
               break;
            default:
               throw new IllegalStateException("Type not supported: " + this.type);
            }
         }
      }

      if (Tools.attachRecords(this.configuration)) {
         this.record.attach(this.configuration);
      }

      if (operation != null) {
         try {
            operation.operate(this.record);
         } catch (Exception var11) {
            exception = var11;
            if (!(var11 instanceof ControlFlowSignal)) {
               if (ctx != null) {
                  ctx.exception = var11;
               }

               if (listeners != null) {
                  RecordListener[] var13 = listeners;
                  var8 = listeners.length;

                  for(int var14 = 0; var14 < var8; ++var14) {
                     RecordListener listener = var13[var14];
                     listener.exception(ctx);
                  }
               }
            }
         }
      }

      if (listeners != null) {
         var12 = listeners;
         var7 = listeners.length;

         for(var8 = 0; var8 < var7; ++var8) {
            listener = var12[var8];
            switch(this.type) {
            case LOAD:
               listener.loadEnd(ctx);
               break;
            case REFRESH:
               listener.refreshEnd(ctx);
               break;
            case STORE:
               listener.storeEnd(ctx);
               break;
            case INSERT:
               listener.insertEnd(ctx);
               break;
            case UPDATE:
               listener.updateEnd(ctx);
               break;
            case DELETE:
               listener.deleteEnd(ctx);
               break;
            default:
               throw new IllegalStateException("Type not supported: " + this.type);
            }
         }
      }

      if (exception != null) {
         throw exception;
      } else {
         return this.record;
      }
   }

   private final ExecuteType executeType() {
      return this.type != RecordDelegate.RecordLifecycleType.LOAD && this.type != RecordDelegate.RecordLifecycleType.REFRESH ? ExecuteType.WRITE : ExecuteType.READ;
   }

   static enum RecordLifecycleType {
      LOAD,
      REFRESH,
      STORE,
      INSERT,
      UPDATE,
      DELETE;
   }
}
