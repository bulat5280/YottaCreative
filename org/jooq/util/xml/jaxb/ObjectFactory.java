package org.jooq.util.xml.jaxb;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
   public InformationSchema createInformationSchema() {
      return new InformationSchema();
   }

   public Table createTable() {
      return new Table();
   }

   public ReferentialConstraint createReferentialConstraint() {
      return new ReferentialConstraint();
   }

   public Schema createSchema() {
      return new Schema();
   }

   public Sequence createSequence() {
      return new Sequence();
   }

   public KeyColumnUsage createKeyColumnUsage() {
      return new KeyColumnUsage();
   }

   public Column createColumn() {
      return new Column();
   }

   public TableConstraint createTableConstraint() {
      return new TableConstraint();
   }
}
