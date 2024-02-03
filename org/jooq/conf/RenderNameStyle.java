package org.jooq.conf;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(
   name = "RenderNameStyle"
)
@XmlEnum
public enum RenderNameStyle {
   QUOTED,
   AS_IS,
   LOWER,
   UPPER;

   public String value() {
      return this.name();
   }

   public static RenderNameStyle fromValue(String v) {
      return valueOf(v);
   }
}
