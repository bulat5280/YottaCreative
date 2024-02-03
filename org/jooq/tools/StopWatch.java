package org.jooq.tools;

public final class StopWatch {
   private static final JooqLogger log = JooqLogger.getLogger(StopWatch.class);
   private long start = System.nanoTime();
   private long split;

   public StopWatch() {
      this.split = this.start;
   }

   public void splitTrace(String message) {
      if (log.isTraceEnabled()) {
         log.trace(message, (Object)this.splitMessage(0L));
      }

   }

   public void splitTrace(String message, long thresholdNano) {
      if (log.isTraceEnabled()) {
         String splitMessage = this.splitMessage(thresholdNano);
         if (splitMessage != null) {
            log.trace(message, (Object)splitMessage);
         }
      }

   }

   public void splitDebug(String message) {
      if (log.isDebugEnabled()) {
         log.debug(message, (Object)this.splitMessage(0L));
      }

   }

   public void splitDebug(String message, long thresholdNano) {
      if (log.isDebugEnabled()) {
         String splitMessage = this.splitMessage(thresholdNano);
         if (splitMessage != null) {
            log.debug(message, (Object)splitMessage);
         }
      }

   }

   public void splitInfo(String message) {
      if (log.isInfoEnabled()) {
         log.info(message, (Object)this.splitMessage(0L));
      }

   }

   public void splitInfo(String message, long thresholdNano) {
      if (log.isInfoEnabled()) {
         String splitMessage = this.splitMessage(thresholdNano);
         if (splitMessage != null) {
            log.info(message, (Object)splitMessage);
         }
      }

   }

   public void splitWarn(String message) {
      log.warn(message, (Object)this.splitMessage(0L));
   }

   public void splitWarn(String message, long thresholdNano) {
      String splitMessage = this.splitMessage(thresholdNano);
      if (splitMessage != null) {
         log.warn(message, (Object)splitMessage);
      }

   }

   public long split() {
      return System.nanoTime() - this.start;
   }

   private String splitMessage(long thresholdNano) {
      long temp = this.split;
      this.split = System.nanoTime();
      long inc = this.split - temp;
      if (thresholdNano > 0L && inc < thresholdNano) {
         return null;
      } else {
         return temp == this.start ? "Total: " + format(this.split - this.start) : "Total: " + format(this.split - this.start) + ", +" + format(inc);
      }
   }

   public static String format(long nanoTime) {
      if (nanoTime > 60000000000L) {
         return formatHours(nanoTime / 1000000000L);
      } else {
         return nanoTime > 1000000000L ? (double)(nanoTime / 1000000L) / 1000.0D + "s" : (double)(nanoTime / 1000L) / 1000.0D + "ms";
      }
   }

   public static String formatHours(long seconds) {
      long s = seconds % 60L;
      long m = seconds / 60L % 60L;
      long h = seconds / 3600L;
      StringBuilder sb = new StringBuilder();
      if (h != 0L) {
         if (h < 10L) {
            sb.append("0");
            sb.append(h);
            sb.append(":");
         } else {
            sb.append(h);
            sb.append(":");
         }
      }

      if (m < 10L) {
         sb.append("0");
         sb.append(m);
         sb.append(":");
      } else {
         sb.append(m);
         sb.append(":");
      }

      if (s < 10L) {
         sb.append("0");
         sb.append(s);
      } else {
         sb.append(s);
      }

      return sb.toString();
   }
}
