package org.jooq;

import java.util.Map;

public interface BatchBindStep extends Batch {
   BatchBindStep bind(Object... var1);

   BatchBindStep bind(Object[]... var1);

   BatchBindStep bind(Map<String, Object> var1);

   BatchBindStep bind(Map<String, Object>... var1);
}
