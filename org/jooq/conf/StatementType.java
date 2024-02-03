package org.jooq.conf;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(
   name = "StatementType"
)
@XmlEnum
public enum StatementType {
   STATIC_STATEMENT,
   PREPARED_STATEMENT;

   public String value() {
      return this.name();
   }

   public static StatementType fromValue(String v) {
      return valueOf(v);
   }
}
