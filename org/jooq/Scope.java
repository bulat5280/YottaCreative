package org.jooq;

import java.util.Map;
import org.jooq.conf.Settings;

public interface Scope {
   Configuration configuration();

   Settings settings();

   SQLDialect dialect();

   SQLDialect family();

   Map<Object, Object> data();

   Object data(Object var1);

   Object data(Object var1, Object var2);
}
