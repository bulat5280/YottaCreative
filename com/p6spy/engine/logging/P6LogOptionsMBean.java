package com.p6spy.engine.logging;

import java.util.Set;

public interface P6LogOptionsMBean {
   void setExclude(String var1);

   String getExclude();

   void setExcludecategories(String var1);

   String getExcludecategories();

   void setExcludebinary(boolean var1);

   boolean getExcludebinary();

   void setFilter(boolean var1);

   boolean getFilter();

   void setInclude(String var1);

   String getInclude();

   String getSQLExpression();

   void setSQLExpression(String var1);

   void unSetSQLExpression();

   void setExecutionThreshold(long var1);

   long getExecutionThreshold();

   Set<String> getIncludeList();

   Set<String> getExcludeList();

   Set<Category> getExcludeCategoriesSet();
}
