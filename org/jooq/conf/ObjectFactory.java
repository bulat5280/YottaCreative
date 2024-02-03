package org.jooq.conf;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
   private static final QName _Settings_QNAME = new QName("http://www.jooq.org/xsd/jooq-runtime-3.9.0.xsd", "settings");

   public Settings createSettings() {
      return new Settings();
   }

   public MappedSchema createMappedSchema() {
      return new MappedSchema();
   }

   public MappedTable createMappedTable() {
      return new MappedTable();
   }

   public RenderMapping createRenderMapping() {
      return new RenderMapping();
   }

   @XmlElementDecl(
      namespace = "http://www.jooq.org/xsd/jooq-runtime-3.9.0.xsd",
      name = "settings"
   )
   public JAXBElement<Settings> createSettings(Settings value) {
      return new JAXBElement(_Settings_QNAME, Settings.class, (Class)null, value);
   }
}
