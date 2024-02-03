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
   name = "Generator",
   propOrder = {}
)
public class Generator implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlElement(
      defaultValue = "org.jooq.util.DefaultGenerator"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String name = "org.jooq.util.DefaultGenerator";
   protected Strategy strategy;
   @XmlElement(
      required = true
   )
   protected Database database;
   protected Generate generate;
   protected Target target;

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public Strategy getStrategy() {
      return this.strategy;
   }

   public void setStrategy(Strategy value) {
      this.strategy = value;
   }

   public Database getDatabase() {
      return this.database;
   }

   public void setDatabase(Database value) {
      this.database = value;
   }

   public Generate getGenerate() {
      return this.generate;
   }

   public void setGenerate(Generate value) {
      this.generate = value;
   }

   public Target getTarget() {
      return this.target;
   }

   public void setTarget(Target value) {
      this.target = value;
   }

   public Generator withName(String value) {
      this.setName(value);
      return this;
   }

   public Generator withStrategy(Strategy value) {
      this.setStrategy(value);
      return this;
   }

   public Generator withDatabase(Database value) {
      this.setDatabase(value);
      return this;
   }

   public Generator withGenerate(Generate value) {
      this.setGenerate(value);
      return this;
   }

   public Generator withTarget(Target value) {
      this.setTarget(value);
      return this;
   }
}
