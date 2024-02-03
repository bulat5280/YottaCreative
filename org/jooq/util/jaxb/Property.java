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
   name = "Property",
   propOrder = {}
)
public class Property implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlElement(
      required = true
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String key;
   @XmlElement(
      required = true
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String value;

   public String getKey() {
      return this.key;
   }

   public void setKey(String value) {
      this.key = value;
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public Property withKey(String value) {
      this.setKey(value);
      return this;
   }

   public Property withValue(String value) {
      this.setValue(value);
      return this;
   }
}
