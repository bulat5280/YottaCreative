package com.p6spy.engine.spy.appender;

public class MultiLineFormat implements MessageFormattingStrategy {
   public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
      return "#" + now + " | took " + elapsed + "ms | " + category + " | connection " + connectionId + "|" + prepared + "\n" + sql + ";";
   }
}
