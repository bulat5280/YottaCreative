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
   name = "ReferentialConstraint",
   propOrder = {}
)
public class ReferentialConstraint implements Serializable {
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
      name = "unique_constraint_catalog"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String uniqueConstraintCatalog;
   @XmlElement(
      name = "unique_constraint_schema"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String uniqueConstraintSchema;
   @XmlElement(
      name = "unique_constraint_name",
      required = true
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String uniqueConstraintName;

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

   public String getUniqueConstraintCatalog() {
      return this.uniqueConstraintCatalog;
   }

   public void setUniqueConstraintCatalog(String value) {
      this.uniqueConstraintCatalog = value;
   }

   public String getUniqueConstraintSchema() {
      return this.uniqueConstraintSchema;
   }

   public void setUniqueConstraintSchema(String value) {
      this.uniqueConstraintSchema = value;
   }

   public String getUniqueConstraintName() {
      return this.uniqueConstraintName;
   }

   public void setUniqueConstraintName(String value) {
      this.uniqueConstraintName = value;
   }

   public ReferentialConstraint withConstraintCatalog(String value) {
      this.setConstraintCatalog(value);
      return this;
   }

   public ReferentialConstraint withConstraintSchema(String value) {
      this.setConstraintSchema(value);
      return this;
   }

   public ReferentialConstraint withConstraintName(String value) {
      this.setConstraintName(value);
      return this;
   }

   public ReferentialConstraint withUniqueConstraintCatalog(String value) {
      this.setUniqueConstraintCatalog(value);
      return this;
   }

   public ReferentialConstraint withUniqueConstraintSchema(String value) {
      this.setUniqueConstraintSchema(value);
      return this;
   }

   public ReferentialConstraint withUniqueConstraintName(String value) {
      this.setUniqueConstraintName(value);
      return this;
   }
}
