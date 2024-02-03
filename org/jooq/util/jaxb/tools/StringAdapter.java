package org.jooq.util.jaxb.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StringAdapter extends XmlAdapter<String, String> {
   private static final Pattern PROPERTY_PATTERN = Pattern.compile("\\$\\{(.*?)\\}");

   public final String unmarshal(String v) throws Exception {
      if (v == null) {
         return null;
      } else {
         String result = v.trim();

         for(Matcher matcher = PROPERTY_PATTERN.matcher(result); matcher.find(); result = result.replace(matcher.group(0), System.getProperty(matcher.group(1), matcher.group(0)))) {
         }

         return result;
      }
   }

   public final String marshal(String v) throws Exception {
      return v == null ? null : v.trim();
   }
}
