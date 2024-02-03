package org.jooq.conf;

import java.io.Serializable;
import java.util.regex.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "MappedTable",
   propOrder = {}
)
public class MappedTable extends SettingsBase implements Serializable, Cloneable {
   private static final long serialVersionUID = 390L;
   protected String input;
   @XmlElement(
      type = String.class
   )
   @XmlJavaTypeAdapter(RegexAdapter.class)
   protected Pattern inputExpression;
   @XmlElement(
      required = true
   )
   protected String output;

   public String getInput() {
      return this.input;
   }

   public void setInput(String value) {
      this.input = value;
   }

   public Pattern getInputExpression() {
      return this.inputExpression;
   }

   public void setInputExpression(Pattern value) {
      this.inputExpression = value;
   }

   public String getOutput() {
      return this.output;
   }

   public void setOutput(String value) {
      this.output = value;
   }

   public MappedTable withInput(String value) {
      this.setInput(value);
      return this;
   }

   public MappedTable withInputExpression(Pattern value) {
      this.setInputExpression(value);
      return this;
   }

   public MappedTable withOutput(String value) {
      this.setOutput(value);
      return this;
   }
}
