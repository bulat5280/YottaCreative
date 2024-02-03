package com.p6spy.engine.logging;

import com.p6spy.engine.common.Loggable;
import java.sql.SQLException;

public class ErrorLoggingEventListener extends LoggingEventListener {
   protected void logElapsed(Loggable loggable, long timeElapsedNanos, Category category, SQLException e) {
      if (e != null) {
         super.logElapsed(loggable, timeElapsedNanos, category, e);
      }

   }
}
