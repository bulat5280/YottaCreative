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
   name = "CustomType",
   propOrder = {}
)
public class CustomType implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlElement(
      required = true
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String name;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String type;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String converter;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String binding;

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }

   public String getConverter() {
      return this.converter;
   }

   public void setConverter(String value) {
      this.converter = value;
   }

   public String getBinding() {
      return this.binding;
   }

   public void setBinding(String value) {
      this.binding = value;
   }

   public CustomType withName(String value) {
      this.setName(value);
      return this;
   }

   public CustomType withType(String value) {
      this.setType(value);
      return this;
   }

   public CustomType withConverter(String value) {
      this.setConverter(value);
      return this;
   }

   public CustomType withBinding(String value) {
      this.setBinding(value);
      return this;
   }
}
