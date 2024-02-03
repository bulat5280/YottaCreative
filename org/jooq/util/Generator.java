package org.jooq.util;

public interface Generator {
   void generate(Database var1);

   void setStrategy(GeneratorStrategy var1);

   GeneratorStrategy getStrategy();

   boolean generateDeprecated();

   void setGenerateDeprecated(boolean var1);

   boolean generateRelations();

   void setGenerateRelations(boolean var1);

   boolean generateTableValuedFunctions();

   void setGenerateTableValuedFunctions(boolean var1);

   boolean generateInstanceFields();

   void setGenerateInstanceFields(boolean var1);

   boolean generateGeneratedAnnotation();

   void setGenerateGeneratedAnnotation(boolean var1);

   boolean useSchemaVersionProvider();

   void setUseSchemaVersionProvider(boolean var1);

   boolean useCatalogVersionProvider();

   void setUseCatalogVersionProvider(boolean var1);

   boolean generateRoutines();

   void setGenerateRoutines(boolean var1);

   boolean generateSequences();

   void setGenerateSequences(boolean var1);

   boolean generateUDTs();

   void setGenerateUDTs(boolean var1);

   boolean generateTables();

   void setGenerateTables(boolean var1);

   boolean generateRecords();

   void setGenerateRecords(boolean var1);

   boolean generatePojos();

   void setGeneratePojos(boolean var1);

   boolean generateImmutablePojos();

   void setGenerateImmutablePojos(boolean var1);

   boolean generateInterfaces();

   void setGenerateInterfaces(boolean var1);

   boolean generateImmutableInterfaces();

   void setGenerateImmutableInterfaces(boolean var1);

   boolean generateDaos();

   void setGenerateDaos(boolean var1);

   boolean generateJPAAnnotations();

   void setGenerateJPAAnnotations(boolean var1);

   boolean generateValidationAnnotations();

   void setGenerateValidationAnnotations(boolean var1);

   boolean generateSpringAnnotations();

   void setGenerateSpringAnnotations(boolean var1);

   boolean generateGlobalObjectReferences();

   void setGenerateGlobalObjectReferences(boolean var1);

   boolean generateGlobalCatalogReferences();

   void setGenerateGlobalCatalogReferences(boolean var1);

   boolean generateGlobalSchemaReferences();

   void setGenerateGlobalSchemaReferences(boolean var1);

   boolean generateGlobalRoutineReferences();

   void setGenerateGlobalRoutineReferences(boolean var1);

   boolean generateGlobalSequenceReferences();

   void setGenerateGlobalSequenceReferences(boolean var1);

   boolean generateGlobalTableReferences();

   void setGenerateGlobalTableReferences(boolean var1);

   boolean generateGlobalUDTReferences();

   void setGenerateGlobalUDTReferences(boolean var1);

   boolean generateGlobalQueueReferences();

   void setGenerateGlobalQueueReferences(boolean var1);

   boolean generateGlobalLinkReferences();

   void setGenerateGlobalLinkReferences(boolean var1);

   boolean generateQueues();

   void setGenerateQueues(boolean var1);

   boolean generateLinks();

   void setGenerateLinks(boolean var1);

   boolean fluentSetters();

   void setFluentSetters(boolean var1);

   boolean generatePojosEqualsAndHashCode();

   void setGeneratePojosEqualsAndHashCode(boolean var1);

   boolean generatePojosToString();

   void setGeneratePojosToString(boolean var1);

   /** @deprecated */
   @Deprecated
   String fullyQualifiedTypes();

   /** @deprecated */
   @Deprecated
   void setFullyQualifiedTypes(String var1);

   String generateFullyQualifiedTypes();

   void setGenerateFullyQualifiedTypes(String var1);

   boolean generateJavaTimeTypes();

   void setGenerateJavaTimeTypes(boolean var1);

   boolean generateEmptyCatalogs();

   void setGenerateEmptyCatalogs(boolean var1);

   boolean generateEmptySchemas();

   void setGenerateEmptySchemas(boolean var1);

   String getTargetDirectory();

   void setTargetDirectory(String var1);

   String getTargetEncoding();

   void setTargetEncoding(String var1);

   String getTargetPackage();

   void setTargetPackage(String var1);
}
