package org.flywaydb.core.internal.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.flywaydb.core.api.FlywayException;

public class PlaceholderReplacer {
   public static final PlaceholderReplacer NO_PLACEHOLDERS = new PlaceholderReplacer(new HashMap(), "", "") {
      public String replacePlaceholders(String input) {
         return input;
      }
   };
   private final Map<String, String> placeholders;
   private final String placeholderPrefix;
   private final String placeholderSuffix;

   public PlaceholderReplacer(Map<String, String> placeholders, String placeholderPrefix, String placeholderSuffix) {
      this.placeholders = placeholders;
      this.placeholderPrefix = placeholderPrefix;
      this.placeholderSuffix = placeholderSuffix;
   }

   public String replacePlaceholders(String input) {
      String noPlaceholders = input;

      String searchTerm;
      String value;
      for(Iterator var3 = this.placeholders.keySet().iterator(); var3.hasNext(); noPlaceholders = StringUtils.replaceAll(noPlaceholders, searchTerm, value == null ? "" : value)) {
         String placeholder = (String)var3.next();
         searchTerm = this.placeholderPrefix + placeholder + this.placeholderSuffix;
         value = (String)this.placeholders.get(placeholder);
      }

      this.checkForUnmatchedPlaceholderExpression(noPlaceholders);
      return noPlaceholders;
   }

   private void checkForUnmatchedPlaceholderExpression(String input) {
      String regex = Pattern.quote(this.placeholderPrefix) + "(.+?)" + Pattern.quote(this.placeholderSuffix);
      Matcher matcher = Pattern.compile(regex).matcher(input);
      TreeSet unmatchedPlaceHolderExpressions = new TreeSet();

      while(matcher.find()) {
         unmatchedPlaceHolderExpressions.add(matcher.group());
      }

      if (!unmatchedPlaceHolderExpressions.isEmpty()) {
         throw new FlywayException("No value provided for placeholder expressions: " + StringUtils.collectionToCommaDelimitedString(unmatchedPlaceHolderExpressions) + ".  Check your configuration!");
      }
   }
}
