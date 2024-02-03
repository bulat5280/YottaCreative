package com.p6spy.engine.logging;

import com.p6spy.engine.spy.P6LoadableOptions;
import java.util.regex.Pattern;

public interface P6LogLoadableOptions extends P6LoadableOptions, P6LogOptionsMBean {
   void setExcludebinary(String var1);

   void setFilter(String var1);

   void setExecutionThreshold(String var1);

   Pattern getIncludeExcludePattern();

   Pattern getSQLExpressionPattern();
}
