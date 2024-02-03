package org.jooq.util.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "",
   propOrder = {}
)
@XmlRootElement(
   name = "configuration"
)
public class Configuration implements Serializable {
   private static final long serialVersionUID = 390L;
   protected Logging logging;
   protected Jdbc jdbc;
   @XmlElement(
      required = true
   )
   protected Generator generator;

   public Logging getLogging() {
      return this.logging;
   }

   public void setLogging(Logging value) {
      this.logging = value;
   }

   public Jdbc getJdbc() {
      return this.jdbc;
   }

   public void setJdbc(Jdbc value) {
      this.jdbc = value;
   }

   public Generator getGenerator() {
      return this.generator;
   }

   public void setGenerator(Generator value) {
      this.generator = value;
   }

   public Configuration withLogging(Logging value) {
      this.setLogging(value);
      return this;
   }

   public Configuration withJdbc(Jdbc value) {
      this.setJdbc(value);
      return this;
   }

   public Configuration withGenerator(Generator value) {
      this.setGenerator(value);
      return this;
   }
}
