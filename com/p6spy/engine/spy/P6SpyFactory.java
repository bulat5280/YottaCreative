package com.p6spy.engine.spy;

import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.spy.option.P6OptionsRepository;

public class P6SpyFactory implements P6Factory {
   public P6LoadableOptions getOptions(P6OptionsRepository optionsRepository) {
      return new P6SpyOptions(optionsRepository);
   }

   public JdbcEventListener getJdbcEventListener() {
      return null;
   }
}
