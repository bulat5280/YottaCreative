package org.jooq.util.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(
   name = "RegexFlag"
)
@XmlEnum
public enum RegexFlag {
   UNIX_LINES,
   CASE_INSENSITIVE,
   COMMENTS,
   MULTILINE,
   LITERAL,
   DOTALL,
   UNICODE_CASE,
   CANON_EQ,
   UNICODE_CHARACTER_CLASS;

   public String value() {
      return this.name();
   }

   public static RegexFlag fromValue(String v) {
      return valueOf(v);
   }
}
