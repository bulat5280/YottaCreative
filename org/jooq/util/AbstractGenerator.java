package org.jooq.util;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import org.jooq.tools.JooqLogger;

abstract class AbstractGenerator implements Generator {
   private static final JooqLogger log = JooqLogger.getLogger(AbstractGenerator.class);
   boolean generateDeprecated = true;
   boolean generateRelations = true;
   boolean generateInstanceFields = true;
   boolean generateGeneratedAnnotation = true;
   boolean useSchemaVersionProvider = false;
   boolean useCatalogVersionProvider = false;
   boolean generateRoutines = true;
   boolean generateSequences = true;
   boolean generateUDTs = true;
   boolean generateTables = true;
   boolean generateRecords = true;
   boolean generatePojos = false;
   boolean generatePojosEqualsAndHashCode = false;
   boolean generatePojosToString = true;
   boolean generateImmutablePojos = false;
   boolean generateInterfaces = false;
   boolean generateImmutableInterfaces = false;
   boolean generateDaos = false;
   boolean generateJPAAnnotations = false;
   boolean generateValidationAnnotations = false;
   boolean generateSpringAnnotations = false;
   boolean generateQueues = true;
   boolean generateLinks = true;
   boolean generateGlobalObjectReferences = true;
   boolean generateGlobalCatalogReferences = true;
   boolean generateGlobalSchemaReferences = true;
   boolean generateGlobalRoutineReferences = true;
   boolean generateGlobalSequenceReferences = true;
   boolean generateGlobalTableReferences = true;
   boolean generateGlobalUDTReferences = true;
   boolean generateGlobalQueueReferences = true;
   boolean generateGlobalLinkReferences = true;
   boolean fluentSetters = false;
   String generateFullyQualifiedTypes = "";
   boolean generateJavaTimeTypes = false;
   boolean generateTableValuedFunctions = false;
   boolean generateEmptyCatalogs = false;
   boolean generateEmptySchemas = false;
   protected GeneratorStrategyWrapper strategy;
   protected String targetEncoding = "UTF-8";
   final AbstractGenerator.Language language;

   AbstractGenerator(AbstractGenerator.Language language) {
      this.language = language;
   }

   void logDatabaseParameters(Database db) {
      String url = "";

      try {
         Connection connection = db.getConnection();
         if (connection != null) {
            url = connection.getMetaData().getURL();
         }
      } catch (SQLException var4) {
      }

      log.info("License parameters");
      log.info("----------------------------------------------------------");
      log.info("  Thank you for using jOOQ and jOOQ's code generator");
      log.info("");
      log.info("Database parameters");
      log.info("----------------------------------------------------------");
      log.info("  dialect", (Object)db.getDialect());
      log.info("  URL", (Object)url);
      log.info("  target dir", (Object)this.getTargetDirectory());
      log.info("  target package", (Object)this.getTargetPackage());
      log.info("  includes", (Object)Arrays.asList(db.getIncludes()));
      log.info("  excludes", (Object)Arrays.asList(db.getExcludes()));
      log.info("  includeExcludeColumns", (Object)db.getIncludeExcludeColumns());
      log.info("----------------------------------------------------------");
   }

   void logGenerationRemarks(Database db) {
      log.info("Generation remarks");
      log.info("----------------------------------------------------------");
      if (this.contains(db.getIncludes(), ',') && db.getIncluded().isEmpty()) {
         log.info("  includes", (Object)"The <includes/> element takes a Java regular expression, not a comma-separated list. This might be why no objects were included.");
      }

      if (this.contains(db.getExcludes(), ',') && db.getExcluded().isEmpty()) {
         log.info("  excludes", (Object)"The <excludes/> element takes a Java regular expression, not a comma-separated list. This might be why no objects were excluded.");
      }

   }

   private boolean contains(String[] array, char c) {
      if (array == null) {
         return false;
      } else {
         String[] var3 = array;
         int var4 = array.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String string = var3[var5];
            if (string != null && string.indexOf(c) > -1) {
               return true;
            }
         }

         return false;
      }
   }

   public void setStrategy(GeneratorStrategy strategy) {
      this.strategy = new GeneratorStrategyWrapper(this, strategy, this.language);
   }

   public GeneratorStrategy getStrategy() {
      return this.strategy;
   }

   public boolean generateDeprecated() {
      return this.generateDeprecated;
   }

   public void setGenerateDeprecated(boolean generateDeprecated) {
      this.generateDeprecated = generateDeprecated;
   }

   public boolean generateRelations() {
      return this.generateRelations || this.generateTables || this.generateDaos;
   }

   public void setGenerateRelations(boolean generateRelations) {
      this.generateRelations = generateRelations;
   }

   public boolean generateTableValuedFunctions() {
      return this.generateTableValuedFunctions;
   }

   public void setGenerateTableValuedFunctions(boolean generateTableValuedFunctions) {
      this.generateTableValuedFunctions = generateTableValuedFunctions;
   }

   public boolean generateInstanceFields() {
      return this.generateInstanceFields;
   }

   public void setGenerateInstanceFields(boolean generateInstanceFields) {
      this.generateInstanceFields = generateInstanceFields;
   }

   public boolean generateGeneratedAnnotation() {
      return this.generateGeneratedAnnotation || this.useSchemaVersionProvider || this.useCatalogVersionProvider;
   }

   public void setGenerateGeneratedAnnotation(boolean generateGeneratedAnnotation) {
      this.generateGeneratedAnnotation = generateGeneratedAnnotation;
   }

   public boolean useSchemaVersionProvider() {
      return this.useSchemaVersionProvider;
   }

   public void setUseSchemaVersionProvider(boolean useSchemaVersionProvider) {
      this.useSchemaVersionProvider = useSchemaVersionProvider;
   }

   public boolean useCatalogVersionProvider() {
      return this.useCatalogVersionProvider;
   }

   public void setUseCatalogVersionProvider(boolean useCatalogVersionProvider) {
      this.useCatalogVersionProvider = useCatalogVersionProvider;
   }

   public boolean generateRoutines() {
      return this.generateRoutines;
   }

   public void setGenerateRoutines(boolean generateRoutines) {
      this.generateRoutines = generateRoutines;
   }

   public boolean generateSequences() {
      return this.generateSequences;
   }

   public void setGenerateSequences(boolean generateSequences) {
      this.generateSequences = generateSequences;
   }

   public boolean generateUDTs() {
      return this.generateUDTs;
   }

   public void setGenerateUDTs(boolean generateUDTs) {
      this.generateUDTs = generateUDTs;
   }

   public boolean generateTables() {
      return this.generateTables || this.generateRecords || this.generateDaos;
   }

   public void setGenerateTables(boolean generateTables) {
      this.generateTables = generateTables;
   }

   public boolean generateRecords() {
      return this.generateRecords || this.generateDaos;
   }

   public void setGenerateRecords(boolean generateRecords) {
      this.generateRecords = generateRecords;
   }

   public boolean generatePojos() {
      return this.generatePojos || this.generateImmutablePojos || this.generateDaos;
   }

   public void setGeneratePojos(boolean generatePojos) {
      this.generatePojos = generatePojos;
   }

   public boolean generateImmutablePojos() {
      return this.generateImmutablePojos;
   }

   public void setGenerateImmutablePojos(boolean generateImmutablePojos) {
      this.generateImmutablePojos = generateImmutablePojos;
   }

   public boolean generateInterfaces() {
      return this.generateInterfaces || this.generateImmutableInterfaces;
   }

   public void setGenerateInterfaces(boolean generateInterfaces) {
      this.generateInterfaces = generateInterfaces;
   }

   public boolean generateImmutableInterfaces() {
      return this.generateImmutableInterfaces || this.generateInterfaces && this.generateImmutablePojos;
   }

   public void setGenerateImmutableInterfaces(boolean generateImmutableInterfaces) {
      this.generateImmutableInterfaces = generateImmutableInterfaces;
   }

   public boolean generateDaos() {
      return this.generateDaos;
   }

   public void setGenerateDaos(boolean generateDaos) {
      this.generateDaos = generateDaos;
   }

   public boolean generateJPAAnnotations() {
      return this.generateJPAAnnotations;
   }

   public void setGenerateJPAAnnotations(boolean generateJPAAnnotations) {
      this.generateJPAAnnotations = generateJPAAnnotations;
   }

   public boolean generateValidationAnnotations() {
      return this.generateValidationAnnotations;
   }

   public void setGenerateValidationAnnotations(boolean generateValidationAnnotations) {
      this.generateValidationAnnotations = generateValidationAnnotations;
   }

   public boolean generateSpringAnnotations() {
      return this.generateSpringAnnotations;
   }

   public void setGenerateSpringAnnotations(boolean generateSpringAnnotations) {
      this.generateSpringAnnotations = generateSpringAnnotations;
   }

   public boolean generateGlobalObjectReferences() {
      return this.generateGlobalObjectReferences;
   }

   public void setGenerateGlobalObjectReferences(boolean generateGlobalObjectReferences) {
      this.generateGlobalObjectReferences = generateGlobalObjectReferences;
   }

   public boolean generateGlobalCatalogReferences() {
      return this.generateGlobalObjectReferences() && this.generateGlobalCatalogReferences;
   }

   public void setGenerateGlobalCatalogReferences(boolean globalCatalogReferences) {
      this.generateGlobalCatalogReferences = globalCatalogReferences;
   }

   public boolean generateGlobalSchemaReferences() {
      return this.generateGlobalObjectReferences() && this.generateGlobalSchemaReferences;
   }

   public void setGenerateGlobalSchemaReferences(boolean globalSchemaReferences) {
      this.generateGlobalSchemaReferences = globalSchemaReferences;
   }

   public boolean generateGlobalRoutineReferences() {
      return this.generateRoutines() && this.generateGlobalObjectReferences() && this.generateGlobalRoutineReferences;
   }

   public void setGenerateGlobalRoutineReferences(boolean generateGlobalRoutineReferences) {
      this.generateGlobalRoutineReferences = generateGlobalRoutineReferences;
   }

   public boolean generateGlobalSequenceReferences() {
      return this.generateSequences() && this.generateGlobalObjectReferences() && this.generateGlobalSequenceReferences;
   }

   public void setGenerateGlobalSequenceReferences(boolean generateGlobalSequenceReferences) {
      this.generateGlobalSequenceReferences = generateGlobalSequenceReferences;
   }

   public boolean generateGlobalTableReferences() {
      return this.generateTables() && this.generateGlobalObjectReferences() && this.generateGlobalTableReferences;
   }

   public void setGenerateGlobalTableReferences(boolean generateGlobalTableReferences) {
      this.generateGlobalTableReferences = generateGlobalTableReferences;
   }

   public boolean generateGlobalUDTReferences() {
      return this.generateUDTs() && this.generateGlobalObjectReferences() && this.generateGlobalUDTReferences;
   }

   public void setGenerateGlobalUDTReferences(boolean generateGlobalUDTReferences) {
      this.generateGlobalUDTReferences = generateGlobalUDTReferences;
   }

   public boolean generateGlobalQueueReferences() {
      return this.generateQueues() && this.generateGlobalObjectReferences() && this.generateGlobalQueueReferences;
   }

   public void setGenerateGlobalQueueReferences(boolean globalQueueReferences) {
      this.generateGlobalQueueReferences = globalQueueReferences;
   }

   public boolean generateGlobalLinkReferences() {
      return this.generateLinks() && this.generateGlobalObjectReferences() && this.generateGlobalLinkReferences;
   }

   public void setGenerateGlobalLinkReferences(boolean globalLinkReferences) {
      this.generateGlobalLinkReferences = globalLinkReferences;
   }

   public boolean generateQueues() {
      return this.generateQueues;
   }

   public void setGenerateQueues(boolean queues) {
      this.generateQueues = queues;
   }

   public boolean generateLinks() {
      return this.generateLinks;
   }

   public void setGenerateLinks(boolean links) {
      this.generateLinks = links;
   }

   public boolean fluentSetters() {
      return this.fluentSetters;
   }

   public void setFluentSetters(boolean fluentSetters) {
      this.fluentSetters = fluentSetters;
   }

   public boolean generatePojosEqualsAndHashCode() {
      return this.generatePojosEqualsAndHashCode;
   }

   public void setGeneratePojosEqualsAndHashCode(boolean generatePojosEqualsAndHashCode) {
      this.generatePojosEqualsAndHashCode = generatePojosEqualsAndHashCode;
   }

   public boolean generatePojosToString() {
      return this.generatePojosToString;
   }

   public void setGeneratePojosToString(boolean generatePojosToString) {
      this.generatePojosToString = generatePojosToString;
   }

   /** @deprecated */
   @Deprecated
   public String fullyQualifiedTypes() {
      return this.generateFullyQualifiedTypes();
   }

   /** @deprecated */
   @Deprecated
   public void setFullyQualifiedTypes(String fullyQualifiedTypes) {
      this.setGenerateFullyQualifiedTypes(fullyQualifiedTypes);
   }

   public String generateFullyQualifiedTypes() {
      return this.generateFullyQualifiedTypes;
   }

   public void setGenerateFullyQualifiedTypes(String generateFullyQualifiedTypes) {
      this.generateFullyQualifiedTypes = generateFullyQualifiedTypes;
   }

   public boolean generateJavaTimeTypes() {
      return this.generateJavaTimeTypes;
   }

   public void setGenerateJavaTimeTypes(boolean generateJavaTimeTypes) {
      this.generateJavaTimeTypes = generateJavaTimeTypes;
   }

   public boolean generateEmptyCatalogs() {
      return this.generateEmptyCatalogs;
   }

   public void setGenerateEmptyCatalogs(boolean generateEmptyCatalogs) {
      this.generateEmptyCatalogs = generateEmptyCatalogs;
   }

   public boolean generateEmptySchemas() {
      return this.generateEmptySchemas;
   }

   public void setGenerateEmptySchemas(boolean generateEmptySchemas) {
      this.generateEmptySchemas = generateEmptySchemas;
   }

   public void setTargetDirectory(String directory) {
      this.strategy.setTargetDirectory(directory);
   }

   public String getTargetDirectory() {
      return this.strategy.getTargetDirectory();
   }

   public void setTargetPackage(String packageName) {
      this.strategy.setTargetPackage(packageName);
   }

   public String getTargetPackage() {
      return this.strategy.getTargetPackage();
   }

   public String getTargetEncoding() {
      return this.targetEncoding;
   }

   public void setTargetEncoding(String encoding) {
      this.targetEncoding = encoding;
   }

   protected void empty(File file, String suffix) {
      this.empty(file, suffix, Collections.emptySet(), Collections.emptySet());
   }

   protected void empty(File file, String suffix, Set<File> keep, Set<File> ignore) {
      if (file != null) {
         if (file.getParentFile() == null) {
            log.warn("WARNING: Root directory configured for code generation. Not deleting anything from previous generations!");
            return;
         }

         Iterator var5 = ignore.iterator();

         while(var5.hasNext()) {
            File i = (File)var5.next();
            if (file.getAbsolutePath().startsWith(i.getAbsolutePath())) {
               return;
            }
         }

         if (file.isDirectory()) {
            File[] children = file.listFiles();
            File[] childrenAfterDeletion;
            if (children != null) {
               childrenAfterDeletion = children;
               int var7 = children.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  File child = childrenAfterDeletion[var8];
                  this.empty(child, suffix, keep, ignore);
               }
            }

            childrenAfterDeletion = file.listFiles();
            if (childrenAfterDeletion != null && childrenAfterDeletion.length == 0) {
               file.delete();
            }
         } else if (file.getName().endsWith(suffix) && !keep.contains(file)) {
            file.delete();
         }
      }

   }

   static enum Language {
      JAVA,
      SCALA,
      XML;
   }
}
