package org.jooq.util.jaxb;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
   public Configuration createConfiguration() {
      return new Configuration();
   }

   public Jdbc createJdbc() {
      return new Jdbc();
   }

   public Generator createGenerator() {
      return new Generator();
   }

   public ForcedType createForcedType() {
      return new ForcedType();
   }

   public Schema createSchema() {
      return new Schema();
   }

   public MatchersRoutineType createMatchersRoutineType() {
      return new MatchersRoutineType();
   }

   public Target createTarget() {
      return new Target();
   }

   public MatchersSchemaType createMatchersSchemaType() {
      return new MatchersSchemaType();
   }

   public EnumType createEnumType() {
      return new EnumType();
   }

   public Generate createGenerate() {
      return new Generate();
   }

   public Database createDatabase() {
      return new Database();
   }

   public Property createProperty() {
      return new Property();
   }

   public CustomType createCustomType() {
      return new CustomType();
   }

   public MatchersTableType createMatchersTableType() {
      return new MatchersTableType();
   }

   public Matchers createMatchers() {
      return new Matchers();
   }

   public MatcherRule createMatcherRule() {
      return new MatcherRule();
   }

   public Catalog createCatalog() {
      return new Catalog();
   }

   public MatchersSequenceType createMatchersSequenceType() {
      return new MatchersSequenceType();
   }

   public Strategy createStrategy() {
      return new Strategy();
   }

   public MatchersFieldType createMatchersFieldType() {
      return new MatchersFieldType();
   }
}
