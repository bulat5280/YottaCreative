package org.jooq.util.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.jooq.util.jaxb.tools.StringAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "EnumType",
   propOrder = {}
)
public class EnumType implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlElement(
      required = true
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String name;
   @XmlElement(
      required = true
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String literals;

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public String getLiterals() {
      return this.literals;
   }

   public void setLiterals(String value) {
      this.literals = value;
   }

   public EnumType withName(String value) {
      this.setName(value);
      return this;
   }

   public EnumType withLiterals(String value) {
      this.setLiterals(value);
      return this;
   }
}
