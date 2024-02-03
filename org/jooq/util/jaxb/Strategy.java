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
   name = "Strategy",
   propOrder = {"name", "matchers"}
)
public class Strategy implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlElement(
      defaultValue = "org.jooq.util.DefaultGeneratorStrategy"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String name = "org.jooq.util.DefaultGeneratorStrategy";
   protected Matchers matchers;

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public Matchers getMatchers() {
      return this.matchers;
   }

   public void setMatchers(Matchers value) {
      this.matchers = value;
   }

   public Strategy withName(String value) {
      this.setName(value);
      return this;
   }

   public Strategy withMatchers(Matchers value) {
      this.setMatchers(value);
      return this;
   }
}
