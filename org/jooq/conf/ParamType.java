package org.jooq.conf;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(
   name = "ParamType"
)
@XmlEnum
public enum ParamType {
   INDEXED,
   NAMED,
   NAMED_OR_INLINED,
   INLINED;

   public String value() {
      return this.name();
   }

   public static ParamType fromValue(String v) {
      return valueOf(v);
   }
}
