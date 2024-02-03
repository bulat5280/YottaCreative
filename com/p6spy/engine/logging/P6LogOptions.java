package com.p6spy.engine.logging;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.spy.P6ModuleManager;
import com.p6spy.engine.spy.option.P6OptionsRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.management.StandardMBean;

public class P6LogOptions extends StandardMBean implements P6LogLoadableOptions {
   public static final String EXCLUDE = "exclude";
   public static final String INCLUDE = "include";
   public static final String FILTER = "filter";
   public static final String EXCLUDECATEGORIES = "excludecategories";
   public static final String EXCLUDEBINARY = "excludebinary";
   public static final String EXECUTION_THRESHOLD = "executionThreshold";
   public static final String SQLEXPRESSION = "sqlexpression";
   public static final String INCLUDE_LIST = "includeList";
   public static final String EXCLUDE_LIST = "excludeList";
   public static final String INCLUDE_EXCLUDE_PATTERN = "includeExcludePattern";
   public static final String EXCLUDECATEGORIES_SET = "excludecategoriesSet";
   public static final String SQLEXPRESSION_PATTERN = "sqlexpressionPattern";
   public static final Map<String, String> defaults = new HashMap();
   private final P6OptionsRepository optionsRepository;

   public P6LogOptions(P6OptionsRepository optionsRepository) {
      super(P6LogOptionsMBean.class, false);
      this.optionsRepository = optionsRepository;
   }

   public void load(Map<String, String> options) {
      this.setSQLExpression((String)options.get("sqlexpression"));
      this.setExecutionThreshold((String)options.get("executionThreshold"));
      this.setExcludecategories((String)options.get("excludecategories"));
      this.setFilter((String)options.get("filter"));
      this.setInclude((String)options.get("include"));
      this.setExclude((String)options.get("exclude"));
      this.setExcludebinary((String)options.get("excludebinary"));
   }

   public static P6LogLoadableOptions getActiveInstance() {
      return (P6LogLoadableOptions)P6ModuleManager.getInstance().getOptions(P6LogOptions.class);
   }

   public Map<String, String> getDefaults() {
      return defaults;
   }

   public void setExclude(String exclude) {
      this.optionsRepository.setSet(String.class, "excludeList", exclude);
      this.optionsRepository.set(String.class, "exclude", P6Util.joinNullSafe(this.optionsRepository.getSet(String.class, "excludeList"), ","));
      this.optionsRepository.setOrUnSet(Pattern.class, "includeExcludePattern", this.computeIncludeExcludePattern(), defaults.get("includeExcludePattern"));
   }

   private String computeIncludeExcludePattern() {
      String excludes = P6Util.joinNullSafe(this.optionsRepository.getSet(String.class, "excludeList"), "|");
      String includes = P6Util.joinNullSafe(this.optionsRepository.getSet(String.class, "includeList"), "|");
      if (excludes.length() == 0 && includes.length() == 0) {
         return null;
      } else {
         StringBuilder sb = new StringBuilder("(?mis)^");
         if (excludes.length() > 0) {
            sb.append("(?!.*(").append(excludes).append(").*)");
         }

         if (includes.length() > 0) {
            sb.append("(.*(").append(includes).append(").*)");
         } else {
            sb.append("(.*)");
         }

         return sb.append("$").toString();
      }
   }

   public String getExclude() {
      return (String)this.optionsRepository.get(String.class, "exclude");
   }

   public void setExcludebinary(boolean excludebinary) {
      this.optionsRepository.set(Boolean.class, "excludebinary", excludebinary);
   }

   public void setExcludebinary(String excludebinary) {
      this.optionsRepository.set(Boolean.class, "excludebinary", excludebinary);
   }

   public boolean getExcludebinary() {
      return (Boolean)this.optionsRepository.get(Boolean.class, "excludebinary");
   }

   public void setExcludecategories(String excludecategories) {
      this.optionsRepository.set(String.class, "excludecategories", excludecategories);
      this.optionsRepository.setSet(Category.class, "excludecategoriesSet", excludecategories);
   }

   public String getExcludecategories() {
      return (String)this.optionsRepository.get(String.class, "excludecategories");
   }

   public void setFilter(String filter) {
      this.optionsRepository.set(Boolean.class, "filter", filter);
   }

   public void setFilter(boolean filter) {
      this.optionsRepository.set(Boolean.class, "filter", filter);
   }

   public boolean getFilter() {
      return (Boolean)this.optionsRepository.get(Boolean.class, "filter");
   }

   public void setInclude(String include) {
      this.optionsRepository.setSet(String.class, "includeList", include);
      this.optionsRepository.set(String.class, "include", P6Util.joinNullSafe(this.optionsRepository.getSet(String.class, "includeList"), ","));
      this.optionsRepository.setOrUnSet(Pattern.class, "includeExcludePattern", this.computeIncludeExcludePattern(), defaults.get("includeExcludePattern"));
   }

   public String getInclude() {
      return (String)this.optionsRepository.get(String.class, "include");
   }

   public String getSQLExpression() {
      return (String)this.optionsRepository.get(String.class, "sqlexpression");
   }

   public Pattern getSQLExpressionPattern() {
      return (Pattern)this.optionsRepository.get(Pattern.class, "sqlexpressionPattern");
   }

   public void setSQLExpression(String sqlexpression) {
      this.optionsRepository.set(String.class, "sqlexpression", sqlexpression);
      this.optionsRepository.set(Pattern.class, "sqlexpressionPattern", sqlexpression);
   }

   public void unSetSQLExpression() {
      this.optionsRepository.setOrUnSet(String.class, "sqlexpression", (Object)null, defaults.get("sqlexpression"));
      this.optionsRepository.setOrUnSet(Pattern.class, "sqlexpressionPattern", (Object)null, defaults.get("sqlexpressionPattern"));
   }

   public void setExecutionThreshold(String executionThreshold) {
      this.optionsRepository.set(Long.class, "executionThreshold", executionThreshold);
   }

   public void setExecutionThreshold(long executionThreshold) {
      this.optionsRepository.set(Long.class, "executionThreshold", executionThreshold);
   }

   public long getExecutionThreshold() {
      return (Long)this.optionsRepository.get(Long.class, "executionThreshold");
   }

   public Set<String> getIncludeList() {
      return this.optionsRepository.getSet(String.class, "includeList");
   }

   public Set<String> getExcludeList() {
      return this.optionsRepository.getSet(String.class, "excludeList");
   }

   public Pattern getIncludeExcludePattern() {
      return (Pattern)this.optionsRepository.get(Pattern.class, "includeExcludePattern");
   }

   public Set<Category> getExcludeCategoriesSet() {
      return this.optionsRepository.getSet(Category.class, "excludecategoriesSet");
   }

   static {
      defaults.put("filter", Boolean.toString(false));
      defaults.put("excludecategories", "info,debug,result,resultset,batch");
      defaults.put("excludebinary", Boolean.toString(false));
      defaults.put("executionThreshold", Long.toString(0L));
   }
}
