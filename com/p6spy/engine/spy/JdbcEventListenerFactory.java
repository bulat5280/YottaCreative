package com.p6spy.engine.spy;

import com.p6spy.engine.event.JdbcEventListener;

public interface JdbcEventListenerFactory {
   JdbcEventListener createJdbcEventListener();
}
