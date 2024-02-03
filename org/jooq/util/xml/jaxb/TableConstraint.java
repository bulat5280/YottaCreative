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
   name = "TableConstraint",
   propOrder = {}
)
public class TableConstraint implements Serializable {
   private static final long serialVersionUID = 354L;
   @XmlElement(
      name = "constraint_catalog"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String constraintCatalog;
   @XmlElement(
      name = "constraint_schema"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String constraintSchema;
   @XmlElement(
      name = "constraint_name",
      required = true
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String constraintName;
   @XmlElement(
      name = "constraint_type",
      required = true
   )
   protected TableConstraintType constraintType;
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

   public String getConstraintCatalog() {
      return this.constraintCatalog;
   }

   public void setConstraintCatalog(String value) {
      this.constraintCatalog = value;
   }

   public String getConstraintSchema() {
      return this.constraintSchema;
   }

   public void setConstraintSchema(String value) {
      this.constraintSchema = value;
   }

   public String getConstraintName() {
      return this.constraintName;
   }

   public void setConstraintName(String value) {
      this.constraintName = value;
   }

   public TableConstraintType getConstraintType() {
      return this.constraintType;
   }

   public void setConstraintType(TableConstraintType value) {
      this.constraintType = value;
   }

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

   public TableConstraint withConstraintCatalog(String value) {
      this.setConstraintCatalog(value);
      return this;
   }

   public TableConstraint withConstraintSchema(String value) {
      this.setConstraintSchema(value);
      return this;
   }

   public TableConstraint withConstraintName(String value) {
      this.setConstraintName(value);
      return this;
   }

   public TableConstraint withConstraintType(TableConstraintType value) {
      this.setConstraintType(value);
      return this;
   }

   public TableConstraint withTableCatalog(String value) {
      this.setTableCatalog(value);
      return this;
   }

   public TableConstraint withTableSchema(String value) {
      this.setTableSchema(value);
      return this;
   }

   public TableConstraint withTableName(String value) {
      this.setTableName(value);
      return this;
   }
}
