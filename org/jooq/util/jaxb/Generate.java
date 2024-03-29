package org.jooq.util.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.jooq.util.jaxb.tools.StringAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "Generate",
   propOrder = {}
)
public class Generate implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean relations = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean deprecated = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean instanceFields = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean generatedAnnotation = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean routines = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean sequences = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean udts = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean queues = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean links = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean tables = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean records = true;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean pojos = false;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean pojosEqualsAndHashCode = false;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean pojosToString = true;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean immutablePojos = false;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean interfaces = false;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean immutableInterfaces = false;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean daos = false;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean jpaAnnotations = false;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean validationAnnotations = false;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean springAnnotations = false;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean globalObjectReferences = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean globalCatalogReferences = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean globalSchemaReferences = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean globalTableReferences = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean globalSequenceReferences = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean globalUDTReferences = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean globalRoutineReferences = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean globalQueueReferences = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean globalLinkReferences = true;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean fluentSetters = false;
   @XmlElement(
      defaultValue = ""
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String fullyQualifiedTypes = "";
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean emptyCatalogs = false;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean emptySchemas = false;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean javaTimeTypes = false;

   public Boolean isRelations() {
      return this.relations;
   }

   public void setRelations(Boolean value) {
      this.relations = value;
   }

   public Boolean isDeprecated() {
      return this.deprecated;
   }

   public void setDeprecated(Boolean value) {
      this.deprecated = value;
   }

   public Boolean isInstanceFields() {
      return this.instanceFields;
   }

   public void setInstanceFields(Boolean value) {
      this.instanceFields = value;
   }

   public Boolean isGeneratedAnnotation() {
      return this.generatedAnnotation;
   }

   public void setGeneratedAnnotation(Boolean value) {
      this.generatedAnnotation = value;
   }

   public Boolean isRoutines() {
      return this.routines;
   }

   public void setRoutines(Boolean value) {
      this.routines = value;
   }

   public Boolean isSequences() {
      return this.sequences;
   }

   public void setSequences(Boolean value) {
      this.sequences = value;
   }

   public Boolean isUdts() {
      return this.udts;
   }

   public void setUdts(Boolean value) {
      this.udts = value;
   }

   public Boolean isQueues() {
      return this.queues;
   }

   public void setQueues(Boolean value) {
      this.queues = value;
   }

   public Boolean isLinks() {
      return this.links;
   }

   public void setLinks(Boolean value) {
      this.links = value;
   }

   public Boolean isTables() {
      return this.tables;
   }

   public void setTables(Boolean value) {
      this.tables = value;
   }

   public Boolean isRecords() {
      return this.records;
   }

   public void setRecords(Boolean value) {
      this.records = value;
   }

   public Boolean isPojos() {
      return this.pojos;
   }

   public void setPojos(Boolean value) {
      this.pojos = value;
   }

   public Boolean isPojosEqualsAndHashCode() {
      return this.pojosEqualsAndHashCode;
   }

   public void setPojosEqualsAndHashCode(Boolean value) {
      this.pojosEqualsAndHashCode = value;
   }

   public Boolean isPojosToString() {
      return this.pojosToString;
   }

   public void setPojosToString(Boolean value) {
      this.pojosToString = value;
   }

   public Boolean isImmutablePojos() {
      return this.immutablePojos;
   }

   public void setImmutablePojos(Boolean value) {
      this.immutablePojos = value;
   }

   public Boolean isInterfaces() {
      return this.interfaces;
   }

   public void setInterfaces(Boolean value) {
      this.interfaces = value;
   }

   public Boolean isImmutableInterfaces() {
      return this.immutableInterfaces;
   }

   public void setImmutableInterfaces(Boolean value) {
      this.immutableInterfaces = value;
   }

   public Boolean isDaos() {
      return this.daos;
   }

   public void setDaos(Boolean value) {
      this.daos = value;
   }

   public Boolean isJpaAnnotations() {
      return this.jpaAnnotations;
   }

   public void setJpaAnnotations(Boolean value) {
      this.jpaAnnotations = value;
   }

   public Boolean isValidationAnnotations() {
      return this.validationAnnotations;
   }

   public void setValidationAnnotations(Boolean value) {
      this.validationAnnotations = value;
   }

   public Boolean isSpringAnnotations() {
      return this.springAnnotations;
   }

   public void setSpringAnnotations(Boolean value) {
      this.springAnnotations = value;
   }

   public Boolean isGlobalObjectReferences() {
      return this.globalObjectReferences;
   }

   public void setGlobalObjectReferences(Boolean value) {
      this.globalObjectReferences = value;
   }

   public Boolean isGlobalCatalogReferences() {
      return this.globalCatalogReferences;
   }

   public void setGlobalCatalogReferences(Boolean value) {
      this.globalCatalogReferences = value;
   }

   public Boolean isGlobalSchemaReferences() {
      return this.globalSchemaReferences;
   }

   public void setGlobalSchemaReferences(Boolean value) {
      this.globalSchemaReferences = value;
   }

   public Boolean isGlobalTableReferences() {
      return this.globalTableReferences;
   }

   public void setGlobalTableReferences(Boolean value) {
      this.globalTableReferences = value;
   }

   public Boolean isGlobalSequenceReferences() {
      return this.globalSequenceReferences;
   }

   public void setGlobalSequenceReferences(Boolean value) {
      this.globalSequenceReferences = value;
   }

   public Boolean isGlobalUDTReferences() {
      return this.globalUDTReferences;
   }

   public void setGlobalUDTReferences(Boolean value) {
      this.globalUDTReferences = value;
   }

   public Boolean isGlobalRoutineReferences() {
      return this.globalRoutineReferences;
   }

   public void setGlobalRoutineReferences(Boolean value) {
      this.globalRoutineReferences = value;
   }

   public Boolean isGlobalQueueReferences() {
      return this.globalQueueReferences;
   }

   public void setGlobalQueueReferences(Boolean value) {
      this.globalQueueReferences = value;
   }

   public Boolean isGlobalLinkReferences() {
      return this.globalLinkReferences;
   }

   public void setGlobalLinkReferences(Boolean value) {
      this.globalLinkReferences = value;
   }

   public Boolean isFluentSetters() {
      return this.fluentSetters;
   }

   public void setFluentSetters(Boolean value) {
      this.fluentSetters = value;
   }

   public String getFullyQualifiedTypes() {
      return this.fullyQualifiedTypes;
   }

   public void setFullyQualifiedTypes(String value) {
      this.fullyQualifiedTypes = value;
   }

   public Boolean isEmptyCatalogs() {
      return this.emptyCatalogs;
   }

   public void setEmptyCatalogs(Boolean value) {
      this.emptyCatalogs = value;
   }

   public Boolean isEmptySchemas() {
      return this.emptySchemas;
   }

   public void setEmptySchemas(Boolean value) {
      this.emptySchemas = value;
   }

   public Boolean isJavaTimeTypes() {
      return this.javaTimeTypes;
   }

   public void setJavaTimeTypes(Boolean value) {
      this.javaTimeTypes = value;
   }

   public Generate withRelations(Boolean value) {
      this.setRelations(value);
      return this;
   }

   public Generate withDeprecated(Boolean value) {
      this.setDeprecated(value);
      return this;
   }

   public Generate withInstanceFields(Boolean value) {
      this.setInstanceFields(value);
      return this;
   }

   public Generate withGeneratedAnnotation(Boolean value) {
      this.setGeneratedAnnotation(value);
      return this;
   }

   public Generate withRoutines(Boolean value) {
      this.setRoutines(value);
      return this;
   }

   public Generate withSequences(Boolean value) {
      this.setSequences(value);
      return this;
   }

   public Generate withUdts(Boolean value) {
      this.setUdts(value);
      return this;
   }

   public Generate withQueues(Boolean value) {
      this.setQueues(value);
      return this;
   }

   public Generate withLinks(Boolean value) {
      this.setLinks(value);
      return this;
   }

   public Generate withTables(Boolean value) {
      this.setTables(value);
      return this;
   }

   public Generate withRecords(Boolean value) {
      this.setRecords(value);
      return this;
   }

   public Generate withPojos(Boolean value) {
      this.setPojos(value);
      return this;
   }

   public Generate withPojosEqualsAndHashCode(Boolean value) {
      this.setPojosEqualsAndHashCode(value);
      return this;
   }

   public Generate withPojosToString(Boolean value) {
      this.setPojosToString(value);
      return this;
   }

   public Generate withImmutablePojos(Boolean value) {
      this.setImmutablePojos(value);
      return this;
   }

   public Generate withInterfaces(Boolean value) {
      this.setInterfaces(value);
      return this;
   }

   public Generate withImmutableInterfaces(Boolean value) {
      this.setImmutableInterfaces(value);
      return this;
   }

   public Generate withDaos(Boolean value) {
      this.setDaos(value);
      return this;
   }

   public Generate withJpaAnnotations(Boolean value) {
      this.setJpaAnnotations(value);
      return this;
   }

   public Generate withValidationAnnotations(Boolean value) {
      this.setValidationAnnotations(value);
      return this;
   }

   public Generate withSpringAnnotations(Boolean value) {
      this.setSpringAnnotations(value);
      return this;
   }

   public Generate withGlobalObjectReferences(Boolean value) {
      this.setGlobalObjectReferences(value);
      return this;
   }

   public Generate withGlobalCatalogReferences(Boolean value) {
      this.setGlobalCatalogReferences(value);
      return this;
   }

   public Generate withGlobalSchemaReferences(Boolean value) {
      this.setGlobalSchemaReferences(value);
      return this;
   }

   public Generate withGlobalTableReferences(Boolean value) {
      this.setGlobalTableReferences(value);
      return this;
   }

   public Generate withGlobalSequenceReferences(Boolean value) {
      this.setGlobalSequenceReferences(value);
      return this;
   }

   public Generate withGlobalUDTReferences(Boolean value) {
      this.setGlobalUDTReferences(value);
      return this;
   }

   public Generate withGlobalRoutineReferences(Boolean value) {
      this.setGlobalRoutineReferences(value);
      return this;
   }

   public Generate withGlobalQueueReferences(Boolean value) {
      this.setGlobalQueueReferences(value);
      return this;
   }

   public Generate withGlobalLinkReferences(Boolean value) {
      this.setGlobalLinkReferences(value);
      return this;
   }

   public Generate withFluentSetters(Boolean value) {
      this.setFluentSetters(value);
      return this;
   }

   public Generate withFullyQualifiedTypes(String value) {
      this.setFullyQualifiedTypes(value);
      return this;
   }

   public Generate withEmptyCatalogs(Boolean value) {
      this.setEmptyCatalogs(value);
      return this;
   }

   public Generate withEmptySchemas(Boolean value) {
      this.setEmptySchemas(value);
      return this;
   }

   public Generate withJavaTimeTypes(Boolean value) {
      this.setJavaTimeTypes(value);
      return this;
   }
}
