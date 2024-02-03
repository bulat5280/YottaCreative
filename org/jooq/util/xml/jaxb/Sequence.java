package org.jooq.util.xml.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.jooq.util.jaxb.tools.StringAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "Sequence",
   propOrder = {}
)
public class Sequence implements Serializable {
   private static final long serialVersionUID = 354L;
   @XmlElement(
      name = "sequence_catalog"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String sequenceCatalog;
   @XmlElement(
      name = "sequence_schema"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String sequenceSchema;
   @XmlElement(
      name = "sequence_name",
      required = true
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String sequenceName;
   @XmlElement(
      name = "data_type",
      required = true
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String dataType;
   @XmlElement(
      name = "character_maximum_length"
   )
   protected Integer characterMaximumLength;
   @XmlElement(
      name = "numeric_precision"
   )
   protected Integer numericPrecision;
   @XmlElement(
      name = "numeric_scale"
   )
   protected Integer numericScale;

   public String getSequenceCatalog() {
      return this.sequenceCatalog;
   }

   public void setSequenceCatalog(String value) {
      this.sequenceCatalog = value;
   }

   public String getSequenceSchema() {
      return this.sequenceSchema;
   }

   public void setSequenceSchema(String value) {
      this.sequenceSchema = value;
   }

   public String getSequenceName() {
      return this.sequenceName;
   }

   public void setSequenceName(String value) {
      this.sequenceName = value;
   }

   public String getDataType() {
      return this.dataType;
   }

   public void setDataType(String value) {
      this.dataType = value;
   }

   public Integer getCharacterMaximumLength() {
      return this.characterMaximumLength;
   }

   public void setCharacterMaximumLength(Integer value) {
      this.characterMaximumLength = value;
   }

   public Integer getNumericPrecision() {
      return this.numericPrecision;
   }

   public void setNumericPrecision(Integer value) {
      this.numericPrecision = value;
   }

   public Integer getNumericScale() {
      return this.numericScale;
   }

   public void setNumericScale(Integer value) {
      this.numericScale = value;
   }

   public Sequence withSequenceCatalog(String value) {
      this.setSequenceCatalog(value);
      return this;
   }

   public Sequence withSequenceSchema(String value) {
      this.setSequenceSchema(value);
      return this;
   }

   public Sequence withSequenceName(String value) {
      this.setSequenceName(value);
      return this;
   }

   public Sequence withDataType(String value) {
      this.setDataType(value);
      return this;
   }

   public Sequence withCharacterMaximumLength(Integer value) {
      this.setCharacterMaximumLength(value);
      return this;
   }

   public Sequence withNumericPrecision(Integer value) {
      this.setNumericPrecision(value);
      return this;
   }

   public Sequence withNumericScale(Integer value) {
      this.setNumericScale(value);
      return this;
   }
}
