package org.jooq.util.xml.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(
   name = "TableConstraintType"
)
@XmlEnum
public enum TableConstraintType {
   @XmlEnumValue("PRIMARY KEY")
   PRIMARY_KEY("PRIMARY KEY"),
   UNIQUE("UNIQUE"),
   CHECK("CHECK"),
   @XmlEnumValue("FOREIGN KEY")
   FOREIGN_KEY("FOREIGN KEY");

   private final String value;

   private TableConstraintType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static TableConstraintType fromValue(String v) {
      TableConstraintType[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         TableConstraintType c = var1[var3];
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
