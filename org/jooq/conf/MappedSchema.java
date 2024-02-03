package org.jooq.conf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "MappedSchema",
   propOrder = {}
)
public class MappedSchema extends SettingsBase implements Serializable, Cloneable {
   private static final long serialVersionUID = 390L;
   protected String input;
   @XmlElement(
      type = String.class
   )
   @XmlJavaTypeAdapter(RegexAdapter.class)
   protected Pattern inputExpression;
   protected String output;
   @XmlElementWrapper(
      name = "tables"
   )
   @XmlElement(
      name = "table"
   )
   protected List<MappedTable> tables;

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

   public List<MappedTable> getTables() {
      if (this.tables == null) {
         this.tables = new ArrayList();
      }

      return this.tables;
   }

   public void setTables(List<MappedTable> tables) {
      this.tables = tables;
   }

   public MappedSchema withInput(String value) {
      this.setInput(value);
      return this;
   }

   public MappedSchema withInputExpression(Pattern value) {
      this.setInputExpression(value);
      return this;
   }

   public MappedSchema withOutput(String value) {
      this.setOutput(value);
      return this;
   }

   public MappedSchema withTables(MappedTable... values) {
      if (values != null) {
         MappedTable[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MappedTable value = var2[var4];
            this.getTables().add(value);
         }
      }

      return this;
   }

   public MappedSchema withTables(Collection<MappedTable> values) {
      if (values != null) {
         this.getTables().addAll(values);
      }

      return this;
   }

   public MappedSchema withTables(List<MappedTable> tables) {
      this.setTables(tables);
      return this;
   }
}
