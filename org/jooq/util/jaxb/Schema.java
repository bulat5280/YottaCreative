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
   name = "Schema",
   propOrder = {}
)
public class Schema implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlElement(
      required = true,
      defaultValue = ""
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String inputSchema = "";
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String outputSchema;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean outputSchemaToDefault = false;

   public String getInputSchema() {
      return this.inputSchema;
   }

   public void setInputSchema(String value) {
      this.inputSchema = value;
   }

   public String getOutputSchema() {
      return this.outputSchema;
   }

   public void setOutputSchema(String value) {
      this.outputSchema = value;
   }

   public Boolean isOutputSchemaToDefault() {
      return this.outputSchemaToDefault;
   }

   public void setOutputSchemaToDefault(Boolean value) {
      this.outputSchemaToDefault = value;
   }

   public Schema withInputSchema(String value) {
      this.setInputSchema(value);
      return this;
   }

   public Schema withOutputSchema(String value) {
      this.setOutputSchema(value);
      return this;
   }

   public Schema withOutputSchemaToDefault(Boolean value) {
      this.setOutputSchemaToDefault(value);
      return this;
   }
}
