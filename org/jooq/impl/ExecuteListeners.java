package org.jooq.impl;

import java.util.ArrayList;
import java.util.List;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteListenerProvider;
import org.jooq.tools.LoggerListener;
import org.jooq.tools.StopWatchListener;

final class ExecuteListeners implements ExecuteListener {
   private static final long serialVersionUID = 7399239846062763212L;
   private final ExecuteListener[] listeners;
   private boolean resultStart;
   private boolean fetchEnd;

   ExecuteListeners(ExecuteContext ctx) {
      this.listeners = listeners(ctx);
      this.start(ctx);
   }

   private static ExecuteListener[] listeners(ExecuteContext ctx) {
      List<ExecuteListener> result = new ArrayList();
      ExecuteListenerProvider[] var2 = ctx.configuration().executeListenerProviders();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListenerProvider provider = var2[var4];
         if (provider != null) {
            result.add(provider.provide());
         }
      }

      if (!Boolean.FALSE.equals(ctx.configuration().settings().isExecuteLogging())) {
         result.add(new LoggerListener());
         result.add(new StopWatchListener());
      }

      return (ExecuteListener[])result.toArray(Tools.EMPTY_EXECUTE_LISTENER);
   }

   public final void start(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.start(ctx);
      }

   }

   public final void renderStart(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.renderStart(ctx);
      }

   }

   public final void renderEnd(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.renderEnd(ctx);
      }

   }

   public final void prepareStart(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.prepareStart(ctx);
      }

   }

   public final void prepareEnd(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.prepareEnd(ctx);
      }

   }

   public final void bindStart(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.bindStart(ctx);
      }

   }

   public final void bindEnd(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.bindEnd(ctx);
      }

   }

   public final void executeStart(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.executeStart(ctx);
      }

   }

   public final void executeEnd(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.executeEnd(ctx);
      }

   }

   public final void fetchStart(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.fetchStart(ctx);
      }

   }

   public final void outStart(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.outStart(ctx);
      }

   }

   public final void outEnd(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.outEnd(ctx);
      }

   }

   public final void resultStart(ExecuteContext ctx) {
      this.resultStart = true;
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.resultStart(ctx);
      }

   }

   public final void recordStart(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.recordStart(ctx);
      }

   }

   public final void recordEnd(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.recordEnd(ctx);
      }

   }

   public final void resultEnd(ExecuteContext ctx) {
      this.resultStart = false;
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.resultEnd(ctx);
      }

      if (this.fetchEnd) {
         this.fetchEnd(ctx);
      }

   }

   public final void fetchEnd(ExecuteContext ctx) {
      if (this.resultStart) {
         this.fetchEnd = true;
      } else {
         ExecuteListener[] var2 = this.listeners;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ExecuteListener listener = var2[var4];
            listener.fetchEnd(ctx);
         }
      }

   }

   public final void end(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.end(ctx);
      }

   }

   public final void exception(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.exception(ctx);
      }

   }

   public final void warning(ExecuteContext ctx) {
      ExecuteListener[] var2 = this.listeners;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ExecuteListener listener = var2[var4];
         listener.warning(ctx);
      }

   }
}
