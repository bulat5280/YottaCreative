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
   name = "Column",
   propOrder = {}
)
public class Column implements Serializable {
   private static final long serialVersionUID = 354L;
   @XmlElement(
      name = "table_catalog"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String tableCatalog;
   @XmlElement(
      name = "table_schema"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String tableSchema;
   @XmlElement(
      name = "table_name",
      required = true
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String tableName;
   @XmlElement(
      name = "column_name",
      required = true
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String columnName;
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
   @XmlElement(
      name = "ordinal_position"
   )
   protected Integer ordinalPosition;
   @XmlElement(
      name = "identity_generation"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String identityGeneration;
   @XmlElement(
      name = "is_nullable"
   )
   protected Boolean isNullable;
   @XmlElement(
      name = "column_default"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String columnDefault;

   public String getTableCatalog() {
      return this.tableCatalog;
   }

   public void setTableCatalog(String value) {
      this.tableCatalog = value;
   }

   public String getTableSchema() {
      return this.tableSchema;
   }

   public void setTableSchema(String value) {
      this.tableSchema = value;
   }

   public String getTableName() {
      return this.tableName;
   }

   public void setTableName(String value) {
      this.tableName = value;
   }

   public String getColumnName() {
      return this.columnName;
   }

   public void setColumnName(String value) {
      this.columnName = value;
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

   public Integer getOrdinalPosition() {
      return this.ordinalPosition;
   }

   public void setOrdinalPosition(Integer value) {
      this.ordinalPosition = value;
   }

   public String getIdentityGeneration() {
      return this.identityGeneration;
   }

   public void setIdentityGeneration(String value) {
      this.identityGeneration = value;
   }

   public Boolean isIsNullable() {
      return this.isNullable;
   }

   public void setIsNullable(Boolean value) {
      this.isNullable = value;
   }

   public String getColumnDefault() {
      return this.columnDefault;
   }

   public void setColumnDefault(String value) {
      this.columnDefault = value;
   }

   public Column withTableCatalog(String value) {
      this.setTableCatalog(value);
      return this;
   }

   public Column withTableSchema(String value) {
      this.setTableSchema(value);
      return this;
   }

   public Column withTableName(String value) {
      this.setTableName(value);
      return this;
   }

   public Column withColumnName(String value) {
      this.setColumnName(value);
      return this;
   }

   public Column withDataType(String value) {
      this.setDataType(value);
      return this;
   }

   public Column withCharacterMaximumLength(Integer value) {
      this.setCharacterMaximumLength(value);
      return this;
   }

   public Column withNumericPrecision(Integer value) {
      this.setNumericPrecision(value);
      return this;
   }

   public Column withNumericScale(Integer value) {
      this.setNumericScale(value);
      return this;
   }

   public Column withOrdinalPosition(Integer value) {
      this.setOrdinalPosition(value);
      return this;
   }

   public Column withIdentityGeneration(String value) {
      this.setIdentityGeneration(value);
      return this;
   }

   public Column withIsNullable(Boolean value) {
      this.setIsNullable(value);
      return this;
   }

   public Column withColumnDefault(String value) {
      this.setColumnDefault(value);
      return this;
   }
}
