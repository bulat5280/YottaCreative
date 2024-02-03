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
   name = "Schema",
   propOrder = {}
)
public class Schema implements Serializable {
   private static final long serialVersionUID = 354L;
   @XmlElement(
      name = "catalog_name"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String catalogName;
   @XmlElement(
      name = "schema_name",
      required = true
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String schemaName;

   public String getCatalogName() {
      return this.catalogName;
   }

   public void setCatalogName(String value) {
      this.catalogName = value;
   }

   public String getSchemaName() {
      return this.schemaName;
   }

   public void setSchemaName(String value) {
      this.schemaName = value;
   }

   public Schema withCatalogName(String value) {
      this.setCatalogName(value);
      return this;
   }

   public Schema withSchemaName(String value) {
      this.setSchemaName(value);
      return this;
   }
}
