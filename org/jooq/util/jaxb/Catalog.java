package org.jooq.util.jaxb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.jooq.util.jaxb.tools.StringAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "Catalog",
   propOrder = {}
)
public class Catalog implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlElement(
      required = true,
      defaultValue = ""
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String inputCatalog = "";
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String outputCatalog;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean outputCatalogToDefault = false;
   @XmlElementWrapper(
      name = "schemata"
   )
   @XmlElement(
      name = "schema"
   )
   protected List<Schema> schemata;

   public String getInputCatalog() {
      return this.inputCatalog;
   }

   public void setInputCatalog(String value) {
      this.inputCatalog = value;
   }

   public String getOutputCatalog() {
      return this.outputCatalog;
   }

   public void setOutputCatalog(String value) {
      this.outputCatalog = value;
   }

   public Boolean isOutputCatalogToDefault() {
      return this.outputCatalogToDefault;
   }

   public void setOutputCatalogToDefault(Boolean value) {
      this.outputCatalogToDefault = value;
   }

   public List<Schema> getSchemata() {
      if (this.schemata == null) {
         this.schemata = new ArrayList();
      }

      return this.schemata;
   }

   public void setSchemata(List<Schema> schemata) {
      this.schemata = schemata;
   }

   public Catalog withInputCatalog(String value) {
      this.setInputCatalog(value);
      return this;
   }

   public Catalog withOutputCatalog(String value) {
      this.setOutputCatalog(value);
      return this;
   }

   public Catalog withOutputCatalogToDefault(Boolean value) {
      this.setOutputCatalogToDefault(value);
      return this;
   }

   public Catalog withSchemata(Schema... values) {
      if (values != null) {
         Schema[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Schema value = var2[var4];
            this.getSchemata().add(value);
         }
      }

      return this;
   }

   public Catalog withSchemata(Collection<Schema> values) {
      if (values != null) {
         this.getSchemata().addAll(values);
      }

      return this;
   }

   public Catalog withSchemata(List<Schema> schemata) {
      this.setSchemata(schemata);
      return this;
   }
}
