package org.jooq.util.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(
   name = "MatcherTransformType"
)
@XmlEnum
public enum MatcherTransformType {
   AS_IS,
   LOWER,
   UPPER,
   CAMEL,
   PASCAL;

   public String value() {
      return this.name();
   }

   public static MatcherTransformType fromValue(String v) {
      return valueOf(v);
   }
}
