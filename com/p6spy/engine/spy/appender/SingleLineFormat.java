package com.p6spy.engine.spy.appender;

import com.p6spy.engine.common.P6Util;

public class SingleLineFormat implements MessageFormattingStrategy {
   public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
      return now + "|" + elapsed + "|" + category + "|connection " + connectionId + "|" + P6Util.singleLine(prepared) + "|" + P6Util.singleLine(sql);
   }
}
