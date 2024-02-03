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
   name = "Table",
   propOrder = {}
)
public class Table implements Serializable {
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

   public Table withTableCatalog(String value) {
      this.setTableCatalog(value);
      return this;
   }

   public Table withTableSchema(String value) {
      this.setTableSchema(value);
      return this;
   }

   public Table withTableName(String value) {
      this.setTableName(value);
      return this;
   }
}
