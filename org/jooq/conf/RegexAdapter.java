package org.jooq.conf;

import java.util.regex.Pattern;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class RegexAdapter extends XmlAdapter<String, Pattern> {
   public Pattern unmarshal(String v) throws Exception {
      return v == null ? null : Pattern.compile(v);
   }

   public String marshal(Pattern v) throws Exception {
      return v == null ? null : v.pattern();
   }
}
