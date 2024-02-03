package com.p6spy.engine.spy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.wrapper.ConnectionWrapper;
import java.sql.Connection;

/** @deprecated */
public class P6Core {
   public static Connection wrapConnection(Connection realConnection, ConnectionInformation connectionInformation) {
      return realConnection == null ? null : ConnectionWrapper.wrap(realConnection, JdbcEventListenerFactoryLoader.load().createJdbcEventListener(), connectionInformation);
   }

   public static JdbcEventListener getJdbcEventListener() {
      return JdbcEventListenerFactoryLoader.load().createJdbcEventListener();
   }
}
