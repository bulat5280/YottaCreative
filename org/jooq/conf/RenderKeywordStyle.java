package org.jooq.conf;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(
   name = "RenderKeywordStyle"
)
@XmlEnum
public enum RenderKeywordStyle {
   AS_IS,
   LOWER,
   UPPER;

   public String value() {
      return this.name();
   }

   public static RenderKeywordStyle fromValue(String v) {
      return valueOf(v);
   }
}
