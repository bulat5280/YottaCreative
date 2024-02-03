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
   name = "Jdbc",
   propOrder = {}
)
public class Jdbc implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlElement(
      required = true
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String driver;
   @XmlElement(
      required = true
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String url;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String schema;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String user;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String username;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String password;
   @XmlElementWrapper(
      name = "properties"
   )
   @XmlElement(
      name = "property"
   )
   protected List<Property> properties;

   public String getDriver() {
      return this.driver;
   }

   public void setDriver(String value) {
      this.driver = value;
   }

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String value) {
      this.url = value;
   }

   public String getSchema() {
      return this.schema;
   }

   public void setSchema(String value) {
      this.schema = value;
   }

   public String getUser() {
      return this.user;
   }

   public void setUser(String value) {
      this.user = value;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String value) {
      this.username = value;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String value) {
      this.password = value;
   }

   public List<Property> getProperties() {
      if (this.properties == null) {
         this.properties = new ArrayList();
      }

      return this.properties;
   }

   public void setProperties(List<Property> properties) {
      this.properties = properties;
   }

   public Jdbc withDriver(String value) {
      this.setDriver(value);
      return this;
   }

   public Jdbc withUrl(String value) {
      this.setUrl(value);
      return this;
   }

   public Jdbc withSchema(String value) {
      this.setSchema(value);
      return this;
   }

   public Jdbc withUser(String value) {
      this.setUser(value);
      return this;
   }

   public Jdbc withUsername(String value) {
      this.setUsername(value);
      return this;
   }

   public Jdbc withPassword(String value) {
      this.setPassword(value);
      return this;
   }

   public Jdbc withProperties(Property... values) {
      if (values != null) {
         Property[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Property value = var2[var4];
            this.getProperties().add(value);
         }
      }

      return this;
   }

   public Jdbc withProperties(Collection<Property> values) {
      if (values != null) {
         this.getProperties().addAll(values);
      }

      return this;
   }

   public Jdbc withProperties(List<Property> properties) {
      this.setProperties(properties);
      return this;
   }
}
