package org.jooq.util.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(
   name = "Logging"
)
@XmlEnum
public enum Logging {
   TRACE,
   DEBUG,
   INFO,
   WARN,
   ERROR,
   FATAL;

   public String value() {
      return this.name();
   }

   public static Logging fromValue(String v) {
      return valueOf(v);
   }
}
