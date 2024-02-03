package org.jooq.conf;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(
   name = "BackslashEscaping"
)
@XmlEnum
public enum BackslashEscaping {
   DEFAULT,
   ON,
   OFF;

   public String value() {
      return this.name();
   }

   public static BackslashEscaping fromValue(String v) {
      return valueOf(v);
   }
}
