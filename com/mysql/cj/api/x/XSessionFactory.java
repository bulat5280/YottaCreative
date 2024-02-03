package com.mysql.cj.api.x;

import java.util.Properties;

public interface XSessionFactory {
   XSession getSession(String var1);

   XSession getSession(Properties var1);

   NodeSession getNodeSession(String var1);

   NodeSession getNodeSession(Properties var1);
}
