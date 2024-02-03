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
   name = "Target",
   propOrder = {}
)
public class Target implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlElement(
      defaultValue = "org.jooq.generated"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String packageName = "org.jooq.generated";
   @XmlElement(
      defaultValue = "target/generated-sources/jooq"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String directory = "target/generated-sources/jooq";
   @XmlElement(
      defaultValue = "UTF-8"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String encoding = "UTF-8";

   public String getPackageName() {
      return this.packageName;
   }

   public void setPackageName(String value) {
      this.packageName = value;
   }

   public String getDirectory() {
      return this.directory;
   }

   public void setDirectory(String value) {
      this.directory = value;
   }

   public String getEncoding() {
      return this.encoding;
   }

   public void setEncoding(String value) {
      this.encoding = value;
   }

   public Target withPackageName(String value) {
      this.setPackageName(value);
      return this;
   }

   public Target withDirectory(String value) {
      this.setDirectory(value);
      return this;
   }

   public Target withEncoding(String value) {
      this.setEncoding(value);
      return this;
   }
}
