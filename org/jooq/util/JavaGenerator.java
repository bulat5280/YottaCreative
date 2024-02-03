package org.jooq.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.DatatypeConverter;
import org.jooq.AggregateFunction;
import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Package;
import org.jooq.Parameter;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UDT;
import org.jooq.UDTField;
import org.jooq.UniqueKey;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.impl.AbstractKeys;
import org.jooq.impl.AbstractRoutine;
import org.jooq.impl.CatalogImpl;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SchemaImpl;
import org.jooq.impl.SequenceImpl;
import org.jooq.impl.TableImpl;
import org.jooq.impl.TableRecordImpl;
import org.jooq.impl.UDTImpl;
import org.jooq.impl.UDTRecordImpl;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StopWatch;
import org.jooq.tools.StringUtils;
import org.jooq.tools.reflect.Reflect;
import org.jooq.tools.reflect.ReflectException;
import org.jooq.util.postgres.PostgresDatabase;

public class JavaGenerator extends AbstractGenerator {
   private static final JooqLogger log = JooqLogger.getLogger(JavaGenerator.class);
   private static final String NO_FURTHER_INSTANCES_ALLOWED = "No further instances allowed";
   private static final int INITIALISER_SIZE = 500;
   private static final Map<DataType<?>, String> SQLDATATYPE_LITERAL_LOOKUP = new IdentityHashMap();
   private final StopWatch watch;
   private Database database;
   private String isoDate;
   private Map<SchemaDefinition, String> schemaVersions;
   private Map<CatalogDefinition, String> catalogVersions;
   private Set<File> files;
   private Set<File> directoriesNotForRemoval;
   private final boolean scala;
   private final String tokenVoid;
   private static final Pattern SQUARE_BRACKETS;

   public JavaGenerator() {
      this(AbstractGenerator.Language.JAVA);
   }

   JavaGenerator(AbstractGenerator.Language language) {
      super(language);
      this.watch = new StopWatch();
      this.files = new LinkedHashSet();
      this.directoriesNotForRemoval = new LinkedHashSet();
      this.scala = language == AbstractGenerator.Language.SCALA;
      this.tokenVoid = this.scala ? "Unit" : "void";
   }

   public final void generate(Database db) {
      this.isoDate = DatatypeConverter.printDateTime(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
      this.schemaVersions = new LinkedHashMap();
      this.catalogVersions = new LinkedHashMap();
      this.database = db;
      this.database.addFilter(new JavaGenerator.AvoidAmbiguousClassesFilter());
      this.database.setIncludeRelations(this.generateRelations());
      this.database.setTableValuedFunctions(this.generateTableValuedFunctions());
      this.logDatabaseParameters(db);
      log.info("");
      log.info("JavaGenerator parameters");
      log.info("----------------------------------------------------------");
      log.info("  strategy", (Object)this.strategy.delegate.getClass());
      log.info("  deprecated", (Object)this.generateDeprecated());
      log.info("  generated annotation", (Object)(this.generateGeneratedAnnotation() + (this.generateGeneratedAnnotation || !this.useSchemaVersionProvider && !this.useCatalogVersionProvider ? "" : " (forced to true because of <schemaVersionProvider/> or <catalogVersionProvider/>)")));
      log.info("  JPA annotations", (Object)this.generateJPAAnnotations());
      log.info("  validation annotations", (Object)this.generateValidationAnnotations());
      log.info("  instance fields", (Object)this.generateInstanceFields());
      log.info("  sequences", (Object)this.generateSequences());
      log.info("  udts", (Object)this.generateUDTs());
      log.info("  routines", (Object)this.generateRoutines());
      log.info("  tables", (Object)(this.generateTables() + (!this.generateTables && this.generateRecords ? " (forced to true because of <records/>)" : (!this.generateTables && this.generateDaos ? " (forced to true because of <daos/>)" : ""))));
      log.info("  records", (Object)(this.generateRecords() + (!this.generateRecords && this.generateDaos ? " (forced to true because of <daos/>)" : "")));
      log.info("  pojos", (Object)(this.generatePojos() + (!this.generatePojos && this.generateDaos ? " (forced to true because of <daos/>)" : (!this.generatePojos && this.generateImmutablePojos ? " (forced to true because of <immutablePojos/>)" : ""))));
      log.info("  immutable pojos", (Object)this.generateImmutablePojos());
      log.info("  interfaces", (Object)(this.generateInterfaces() + (!this.generateInterfaces && this.generateImmutableInterfaces ? " (forced to true because of <immutableInterfaces/>)" : "")));
      log.info("  immutable interfaces", (Object)this.generateInterfaces());
      log.info("  daos", (Object)this.generateDaos());
      log.info("  relations", (Object)(this.generateRelations() + (!this.generateRelations && this.generateTables ? " (forced to true because of <tables/>)" : (!this.generateRelations && this.generateDaos ? " (forced to true because of <daos/>)" : ""))));
      log.info("  table-valued functions", (Object)this.generateTableValuedFunctions());
      log.info("  global references", (Object)this.generateGlobalObjectReferences());
      log.info("----------------------------------------------------------");
      if (!this.generateInstanceFields()) {
         log.warn("");
         log.warn("Deprecation warnings");
         log.warn("----------------------------------------------------------");
         log.warn("  <generateInstanceFields/> = false is deprecated! Please adapt your configuration.");
      }

      log.info("");
      this.logGenerationRemarks(db);
      log.info("");
      log.info("----------------------------------------------------------");
      log.info("Generating catalogs", (Object)("Total: " + this.database.getCatalogs().size()));
      Iterator var2 = this.database.getCatalogs().iterator();

      while(var2.hasNext()) {
         CatalogDefinition catalog = (CatalogDefinition)var2.next();

         try {
            if (this.generateCatalogIfEmpty(catalog)) {
               this.generate(catalog);
            } else {
               log.info("Excluding empty catalog", (Object)catalog);
            }
         } catch (Exception var5) {
            throw new GeneratorException("Error generating code for catalog " + catalog, var5);
         }
      }

      log.info("Removing excess files");
      this.empty(this.getStrategy().getFileRoot(), this.scala ? ".scala" : ".java", this.files, this.directoriesNotForRemoval);
      this.directoriesNotForRemoval.clear();
      this.files.clear();
   }

   private boolean generateCatalogIfEmpty(CatalogDefinition catalog) {
      if (this.generateEmptyCatalogs()) {
         return true;
      } else {
         List<SchemaDefinition> schemas = catalog.getSchemata();
         if (schemas.isEmpty()) {
            return false;
         } else {
            Iterator var3 = schemas.iterator();

            SchemaDefinition schema;
            do {
               if (!var3.hasNext()) {
                  return false;
               }

               schema = (SchemaDefinition)var3.next();
            } while(!this.generateSchemaIfEmpty(schema));

            return true;
         }
      }
   }

   private final boolean generateSchemaIfEmpty(SchemaDefinition schema) {
      if (this.generateEmptySchemas()) {
         return true;
      } else {
         return !this.database.getArrays(schema).isEmpty() || !this.database.getEnums(schema).isEmpty() || !this.database.getPackages(schema).isEmpty() || !this.database.getRoutines(schema).isEmpty() || !this.database.getTables(schema).isEmpty() || !this.database.getUDTs(schema).isEmpty();
      }
   }

   private void generate(CatalogDefinition catalog) {
      String newVersion = catalog.getDatabase().getCatalogVersionProvider().version(catalog);
      if (StringUtils.isBlank(newVersion)) {
         log.info("No schema version is applied for catalog " + catalog.getInputName() + ". Regenerating.");
      } else {
         this.catalogVersions.put(catalog, newVersion);
         String oldVersion = this.readVersion(this.getStrategy().getFile((Definition)catalog), "catalog");
         if (StringUtils.isBlank(oldVersion)) {
            log.info("No previous version available for catalog " + catalog.getInputName() + ". Regenerating.");
         } else {
            if (oldVersion.equals(newVersion)) {
               log.info("Existing version " + oldVersion + " is up to date with " + newVersion + " for catalog " + catalog.getInputName() + ". Ignoring catalog.");
               this.directoriesNotForRemoval.add(this.getStrategy().getFile((Definition)catalog).getParentFile());
               return;
            }

            log.info("Existing version " + oldVersion + " is not up to date with " + newVersion + " for catalog " + catalog.getInputName() + ". Regenerating.");
         }
      }

      this.generateCatalog(catalog);
      log.info("Generating schemata", (Object)("Total: " + catalog.getSchemata().size()));
      Iterator var7 = catalog.getSchemata().iterator();

      while(var7.hasNext()) {
         SchemaDefinition schema = (SchemaDefinition)var7.next();

         try {
            if (this.generateSchemaIfEmpty(schema)) {
               this.generate(schema);
            } else {
               log.info("Excluding empty schema", (Object)schema);
            }
         } catch (Exception var6) {
            throw new GeneratorException("Error generating code for schema " + schema, var6);
         }
      }

   }

   private void generate(SchemaDefinition schema) {
      String newVersion = schema.getDatabase().getSchemaVersionProvider().version(schema);
      if (StringUtils.isBlank(newVersion)) {
         log.info("No schema version is applied for schema " + schema.getInputName() + ". Regenerating.");
      } else {
         this.schemaVersions.put(schema, newVersion);
         String oldVersion = this.readVersion(this.getStrategy().getFile((Definition)schema), "schema");
         if (StringUtils.isBlank(oldVersion)) {
            log.info("No previous version available for schema " + schema.getInputName() + ". Regenerating.");
         } else {
            if (oldVersion.equals(newVersion)) {
               log.info("Existing version " + oldVersion + " is up to date with " + newVersion + " for schema " + schema.getInputName() + ". Ignoring schema.");
               this.directoriesNotForRemoval.add(this.getStrategy().getFile((Definition)schema).getParentFile());
               return;
            }

            log.info("Existing version " + oldVersion + " is not up to date with " + newVersion + " for schema " + schema.getInputName() + ". Regenerating.");
         }
      }

      this.generateSchema(schema);
      if (this.generateGlobalSequenceReferences() && this.database.getSequences(schema).size() > 0) {
         this.generateSequences(schema);
      }

      if (this.generateTables() && this.database.getTables(schema).size() > 0) {
         this.generateTables(schema);
      }

      if (this.generatePojos() && this.database.getTables(schema).size() > 0) {
         this.generatePojos(schema);
      }

      if (this.generateDaos() && this.database.getTables(schema).size() > 0) {
         this.generateDaos(schema);
      }

      if (this.generateGlobalTableReferences() && this.database.getTables(schema).size() > 0) {
         this.generateTableReferences(schema);
      }

      if (this.generateRelations() && this.database.getTables(schema).size() > 0) {
         this.generateRelations(schema);
      }

      if (this.generateRecords() && this.database.getTables(schema).size() > 0) {
         this.generateRecords(schema);
      }

      if (this.generateInterfaces() && this.database.getTables(schema).size() > 0) {
         this.generateInterfaces(schema);
      }

      if (this.generateUDTs() && this.database.getUDTs(schema).size() > 0) {
         this.generateUDTs(schema);
      }

      if (this.generatePojos() && this.database.getUDTs(schema).size() > 0) {
         this.generateUDTPojos(schema);
      }

      if (this.generateUDTs() && this.generateRecords() && this.database.getUDTs(schema).size() > 0) {
         this.generateUDTRecords(schema);
      }

      if (this.generateInterfaces() && this.database.getUDTs(schema).size() > 0) {
         this.generateUDTInterfaces(schema);
      }

      if (this.generateUDTs() && this.generateRoutines() && this.database.getUDTs(schema).size() > 0) {
         this.generateUDTRoutines(schema);
      }

      if (this.generateGlobalUDTReferences() && this.database.getUDTs(schema).size() > 0) {
         this.generateUDTReferences(schema);
      }

      if (this.generateUDTs() && this.database.getArrays(schema).size() > 0) {
         this.generateArrays(schema);
      }

      if (this.generateUDTs() && this.database.getEnums(schema).size() > 0) {
         this.generateEnums(schema);
      }

      if (this.generateUDTs() && this.database.getDomains(schema).size() > 0) {
         this.generateDomains(schema);
      }

      if (this.generateRoutines() && (this.database.getRoutines(schema).size() > 0 || this.hasTableValuedFunctions(schema))) {
         this.generateRoutines(schema);
      }

      this.watch.splitInfo("Generation finished: " + schema.getQualifiedName());
      log.info("");
   }

   private boolean hasTableValuedFunctions(SchemaDefinition schema) {
      Iterator var2 = this.database.getTables(schema).iterator();

      TableDefinition table;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         table = (TableDefinition)var2.next();
      } while(!table.isTableValuedFunction());

      return true;
   }

   protected void generateRelations(SchemaDefinition schema) {
      log.info("Generating Keys");
      JavaWriter out = this.newJavaWriter(new File(this.getStrategy().getFile((Definition)schema).getParentFile(), "Keys.java"));
      this.printPackage(out, schema);
      this.printClassJavadoc(out, "A class modelling foreign key relationships between tables of the <code>" + schema.getOutputName() + "</code> schema");
      this.printClassAnnotations(out, schema);
      if (this.scala) {
         out.println("object Keys {");
      } else {
         out.println("public class Keys {");
      }

      ((JavaWriter)out.tab(1)).header("IDENTITY definitions");
      out.println();
      List<IdentityDefinition> allIdentities = new ArrayList();
      List<UniqueKeyDefinition> allUniqueKeys = new ArrayList();
      List<ForeignKeyDefinition> allForeignKeys = new ArrayList();
      Iterator var6 = this.database.getTables(schema).iterator();

      TableDefinition table;
      String keyType;
      while(var6.hasNext()) {
         table = (TableDefinition)var6.next();

         try {
            IdentityDefinition identity = table.getIdentity();
            if (identity != null) {
               String identityType = out.ref(this.getStrategy().getFullJavaClassName(identity.getColumn().getContainer(), GeneratorStrategy.Mode.RECORD));
               String columnType = out.ref(this.getJavaType(identity.getColumn().getType()));
               keyType = this.getStrategy().getJavaIdentifier(identity.getColumn().getContainer());
               int block = allIdentities.size() / 500;
               if (this.scala) {
                  ((JavaWriter)out.tab(1)).println("val IDENTITY_%s = Identities%s.IDENTITY_%s", new Object[]{keyType, block, keyType});
               } else {
                  ((JavaWriter)out.tab(1)).println("public static final %s<%s, %s> IDENTITY_%s = Identities%s.IDENTITY_%s;", new Object[]{Identity.class, identityType, columnType, keyType, block, keyType});
               }

               allIdentities.add(identity);
            }
         } catch (Exception var15) {
            log.error("Error while generating table " + table, (Throwable)var15);
         }
      }

      ((JavaWriter)out.tab(1)).header("UNIQUE and PRIMARY KEY definitions");
      out.println();
      var6 = this.database.getTables(schema).iterator();

      List foreignKeys;
      Iterator var22;
      UniqueKeyDefinition uniqueKey;
      String referencedType;
      while(var6.hasNext()) {
         table = (TableDefinition)var6.next();

         try {
            foreignKeys = table.getUniqueKeys();

            for(var22 = foreignKeys.iterator(); var22.hasNext(); allUniqueKeys.add(uniqueKey)) {
               uniqueKey = (UniqueKeyDefinition)var22.next();
               keyType = out.ref(this.getStrategy().getFullJavaClassName(uniqueKey.getTable(), GeneratorStrategy.Mode.RECORD));
               referencedType = this.getStrategy().getJavaIdentifier(uniqueKey);
               int block = allUniqueKeys.size() / 500;
               if (this.scala) {
                  ((JavaWriter)out.tab(1)).println("val %s = UniqueKeys%s.%s", new Object[]{referencedType, block, referencedType});
               } else {
                  ((JavaWriter)out.tab(1)).println("public static final %s<%s> %s = UniqueKeys%s.%s;", new Object[]{UniqueKey.class, keyType, referencedType, block, referencedType});
               }
            }
         } catch (Exception var17) {
            log.error("Error while generating table " + table, (Throwable)var17);
         }
      }

      ((JavaWriter)out.tab(1)).header("FOREIGN KEY definitions");
      out.println();
      var6 = this.database.getTables(schema).iterator();

      ForeignKeyDefinition foreignKey;
      while(var6.hasNext()) {
         table = (TableDefinition)var6.next();

         try {
            foreignKeys = table.getForeignKeys();

            for(var22 = foreignKeys.iterator(); var22.hasNext(); allForeignKeys.add(foreignKey)) {
               foreignKey = (ForeignKeyDefinition)var22.next();
               keyType = out.ref(this.getStrategy().getFullJavaClassName(foreignKey.getKeyTable(), GeneratorStrategy.Mode.RECORD));
               referencedType = out.ref(this.getStrategy().getFullJavaClassName(foreignKey.getReferencedTable(), GeneratorStrategy.Mode.RECORD));
               String keyId = this.getStrategy().getJavaIdentifier(foreignKey);
               int block = allForeignKeys.size() / 500;
               if (this.scala) {
                  ((JavaWriter)out.tab(1)).println("val %s = ForeignKeys%s.%s", new Object[]{keyId, block, keyId});
               } else {
                  ((JavaWriter)out.tab(1)).println("public static final %s<%s, %s> %s = ForeignKeys%s.%s;", new Object[]{ForeignKey.class, keyType, referencedType, keyId, block, keyId});
               }
            }
         } catch (Exception var16) {
            log.error("Error while generating reference " + table, (Throwable)var16);
         }
      }

      int identityCounter = 0;
      int uniqueKeyCounter = 0;
      int foreignKeyCounter = 0;
      ((JavaWriter)out.tab(1)).header("[#1459] distribute members to avoid static initialisers > 64kb");

      for(var22 = allIdentities.iterator(); var22.hasNext(); ++identityCounter) {
         IdentityDefinition identity = (IdentityDefinition)var22.next();
         this.printIdentity(out, identityCounter, identity);
      }

      if (identityCounter > 0) {
         ((JavaWriter)out.tab(1)).println("}");
      }

      for(var22 = allUniqueKeys.iterator(); var22.hasNext(); ++uniqueKeyCounter) {
         uniqueKey = (UniqueKeyDefinition)var22.next();
         this.printUniqueKey(out, uniqueKeyCounter, uniqueKey);
      }

      if (uniqueKeyCounter > 0) {
         ((JavaWriter)out.tab(1)).println("}");
      }

      for(var22 = allForeignKeys.iterator(); var22.hasNext(); ++foreignKeyCounter) {
         foreignKey = (ForeignKeyDefinition)var22.next();
         this.printForeignKey(out, foreignKeyCounter, foreignKey);
      }

      if (foreignKeyCounter > 0) {
         ((JavaWriter)out.tab(1)).println("}");
      }

      out.println("}");
      this.closeJavaWriter(out);
      this.watch.splitInfo("Keys generated");
   }

   protected void printIdentity(JavaWriter out, int identityCounter, IdentityDefinition identity) {
      int block = identityCounter / 500;
      if (identityCounter % 500 == 0) {
         if (identityCounter > 0) {
            ((JavaWriter)out.tab(1)).println("}");
         }

         out.println();
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("private object Identities%s extends %s {", new Object[]{block, AbstractKeys.class});
         } else {
            ((JavaWriter)out.tab(1)).println("private static class Identities%s extends %s {", new Object[]{block, AbstractKeys.class});
         }
      }

      if (this.scala) {
         ((JavaWriter)out.tab(2)).println("val %s : %s[%s, %s] = %s.createIdentity(%s, %s)", new Object[]{this.getStrategy().getJavaIdentifier(identity), Identity.class, out.ref(this.getStrategy().getFullJavaClassName(identity.getTable(), GeneratorStrategy.Mode.RECORD)), out.ref(this.getJavaType(identity.getColumn().getType())), AbstractKeys.class, out.ref(this.getStrategy().getFullJavaIdentifier(identity.getColumn().getContainer()), 2), out.ref(this.getStrategy().getFullJavaIdentifier(identity.getColumn()), this.colRefSegments(identity.getColumn()))});
      } else {
         ((JavaWriter)out.tab(2)).println("public static %s<%s, %s> %s = createIdentity(%s, %s);", new Object[]{Identity.class, out.ref(this.getStrategy().getFullJavaClassName(identity.getTable(), GeneratorStrategy.Mode.RECORD)), out.ref(this.getJavaType(identity.getColumn().getType())), this.getStrategy().getJavaIdentifier(identity), out.ref(this.getStrategy().getFullJavaIdentifier(identity.getColumn().getContainer()), 2), out.ref(this.getStrategy().getFullJavaIdentifier(identity.getColumn()), this.colRefSegments(identity.getColumn()))});
      }

   }

   protected void printUniqueKey(JavaWriter out, int uniqueKeyCounter, UniqueKeyDefinition uniqueKey) {
      int block = uniqueKeyCounter / 500;
      if (uniqueKeyCounter % 500 == 0) {
         if (uniqueKeyCounter > 0) {
            ((JavaWriter)out.tab(1)).println("}");
         }

         out.println();
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("private object UniqueKeys%s extends %s {", new Object[]{block, AbstractKeys.class});
         } else {
            ((JavaWriter)out.tab(1)).println("private static class UniqueKeys%s extends %s {", new Object[]{block, AbstractKeys.class});
         }
      }

      if (this.scala) {
         ((JavaWriter)out.tab(2)).println("val %s : %s[%s] = %s.createUniqueKey(%s, \"%s\", [[%s]])", new Object[]{this.getStrategy().getJavaIdentifier(uniqueKey), UniqueKey.class, out.ref(this.getStrategy().getFullJavaClassName(uniqueKey.getTable(), GeneratorStrategy.Mode.RECORD)), AbstractKeys.class, out.ref(this.getStrategy().getFullJavaIdentifier(uniqueKey.getTable()), 2), this.escapeString(uniqueKey.getOutputName()), out.ref(this.getStrategy().getFullJavaIdentifiers((Collection)uniqueKey.getKeyColumns()), this.colRefSegments((TypedElementDefinition)null))});
      } else {
         ((JavaWriter)out.tab(2)).println("public static final %s<%s> %s = createUniqueKey(%s, \"%s\", [[%s]]);", new Object[]{UniqueKey.class, out.ref(this.getStrategy().getFullJavaClassName(uniqueKey.getTable(), GeneratorStrategy.Mode.RECORD)), this.getStrategy().getJavaIdentifier(uniqueKey), out.ref(this.getStrategy().getFullJavaIdentifier(uniqueKey.getTable()), 2), this.escapeString(uniqueKey.getOutputName()), out.ref(this.getStrategy().getFullJavaIdentifiers((Collection)uniqueKey.getKeyColumns()), this.colRefSegments((TypedElementDefinition)null))});
      }

   }

   protected void printForeignKey(JavaWriter out, int foreignKeyCounter, ForeignKeyDefinition foreignKey) {
      int block = foreignKeyCounter / 500;
      if (foreignKeyCounter % 500 == 0) {
         if (foreignKeyCounter > 0) {
            ((JavaWriter)out.tab(1)).println("}");
         }

         out.println();
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("private object ForeignKeys%s extends %s {", new Object[]{block, AbstractKeys.class});
         } else {
            ((JavaWriter)out.tab(1)).println("private static class ForeignKeys%s extends %s {", new Object[]{block, AbstractKeys.class});
         }
      }

      if (this.scala) {
         ((JavaWriter)out.tab(2)).println("val %s : %s[%s, %s] = %s.createForeignKey(%s, %s, \"%s\", [[%s]])", new Object[]{this.getStrategy().getJavaIdentifier(foreignKey), ForeignKey.class, out.ref(this.getStrategy().getFullJavaClassName(foreignKey.getKeyTable(), GeneratorStrategy.Mode.RECORD)), out.ref(this.getStrategy().getFullJavaClassName(foreignKey.getReferencedTable(), GeneratorStrategy.Mode.RECORD)), AbstractKeys.class, out.ref(this.getStrategy().getFullJavaIdentifier(foreignKey.getReferencedKey()), 2), out.ref(this.getStrategy().getFullJavaIdentifier(foreignKey.getKeyTable()), 2), this.escapeString(foreignKey.getOutputName()), out.ref(this.getStrategy().getFullJavaIdentifiers((Collection)foreignKey.getKeyColumns()), this.colRefSegments((TypedElementDefinition)null))});
      } else {
         ((JavaWriter)out.tab(2)).println("public static final %s<%s, %s> %s = createForeignKey(%s, %s, \"%s\", [[%s]]);", new Object[]{ForeignKey.class, out.ref(this.getStrategy().getFullJavaClassName(foreignKey.getKeyTable(), GeneratorStrategy.Mode.RECORD)), out.ref(this.getStrategy().getFullJavaClassName(foreignKey.getReferencedTable(), GeneratorStrategy.Mode.RECORD)), this.getStrategy().getJavaIdentifier(foreignKey), out.ref(this.getStrategy().getFullJavaIdentifier(foreignKey.getReferencedKey()), 2), out.ref(this.getStrategy().getFullJavaIdentifier(foreignKey.getKeyTable()), 2), this.escapeString(foreignKey.getOutputName()), out.ref(this.getStrategy().getFullJavaIdentifiers((Collection)foreignKey.getKeyColumns()), this.colRefSegments((TypedElementDefinition)null))});
      }

   }

   protected void generateRecords(SchemaDefinition schema) {
      log.info("Generating table records");
      Iterator var2 = this.database.getTables(schema).iterator();

      while(var2.hasNext()) {
         TableDefinition table = (TableDefinition)var2.next();

         try {
            this.generateRecord(table);
         } catch (Exception var5) {
            log.error("Error while generating table record " + table, (Throwable)var5);
         }
      }

      this.watch.splitInfo("Table records generated");
   }

   protected void generateRecord(TableDefinition table) {
      JavaWriter out = this.newJavaWriter(this.getStrategy().getFile(table, GeneratorStrategy.Mode.RECORD));
      log.info("Generating record", (Object)out.file().getName());
      this.generateRecord(table, out);
      this.closeJavaWriter(out);
   }

   protected void generateUDTRecord(UDTDefinition udt) {
      JavaWriter out = this.newJavaWriter(this.getStrategy().getFile(udt, GeneratorStrategy.Mode.RECORD));
      log.info("Generating record", (Object)out.file().getName());
      this.generateRecord0(udt, out);
      this.closeJavaWriter(out);
   }

   protected void generateRecord(TableDefinition table, JavaWriter out) {
      this.generateRecord0(table, out);
   }

   protected void generateUDTRecord(UDTDefinition udt, JavaWriter out) {
      this.generateRecord0(udt, out);
   }

   private final void generateRecord0(Definition tableOrUdt, JavaWriter out) {
      UniqueKeyDefinition key = tableOrUdt instanceof TableDefinition ? ((TableDefinition)tableOrUdt).getPrimaryKey() : null;
      String className = this.getStrategy().getJavaClassName(tableOrUdt, GeneratorStrategy.Mode.RECORD);
      String tableIdentifier = out.ref(this.getStrategy().getFullJavaIdentifier(tableOrUdt), 2);
      List<String> interfaces = out.ref(this.getStrategy().getJavaClassImplements(tableOrUdt, GeneratorStrategy.Mode.RECORD));
      List<? extends TypedElementDefinition<?>> columns = this.getTypedElements(tableOrUdt);
      this.printPackage(out, tableOrUdt, GeneratorStrategy.Mode.RECORD);
      if (tableOrUdt instanceof TableDefinition) {
         this.generateRecordClassJavadoc((TableDefinition)tableOrUdt, out);
      } else {
         this.generateUDTRecordClassJavadoc((UDTDefinition)tableOrUdt, out);
      }

      this.printClassAnnotations(out, tableOrUdt.getSchema());
      if (tableOrUdt instanceof TableDefinition) {
         this.printTableJPAAnnotation(out, (TableDefinition)tableOrUdt);
      }

      Class baseClass;
      if (tableOrUdt instanceof UDTDefinition) {
         baseClass = UDTRecordImpl.class;
      } else if (this.generateRelations() && key != null) {
         baseClass = UpdatableRecordImpl.class;
      } else {
         baseClass = TableRecordImpl.class;
      }

      int degree = columns.size();
      String rowType = null;
      String rowTypeRecord = null;
      if (degree > 0 && degree <= 22) {
         rowType = this.refRowType(out, columns);
         if (this.scala) {
            rowTypeRecord = out.ref(Record.class.getName() + degree) + "[" + rowType + "]";
         } else {
            rowTypeRecord = out.ref(Record.class.getName() + degree) + "<" + rowType + ">";
         }

         interfaces.add(rowTypeRecord);
      }

      if (this.generateInterfaces()) {
         interfaces.add(out.ref(this.getStrategy().getFullJavaClassName(tableOrUdt, GeneratorStrategy.Mode.INTERFACE)));
      }

      if (this.scala) {
         out.println("class %s extends %s[%s](%s)[[before= with ][separator= with ][%s]] {", new Object[]{className, baseClass, className, tableIdentifier, interfaces});
      } else {
         out.println("public class %s extends %s<%s>[[before= implements ][%s]] {", new Object[]{className, baseClass, className, interfaces});
      }

      out.printSerial();

      int i;
      TypedElementDefinition column;
      for(i = 0; i < degree; ++i) {
         column = (TypedElementDefinition)columns.get(i);
         if (tableOrUdt instanceof TableDefinition) {
            this.generateRecordSetter(column, i, out);
            this.generateRecordGetter(column, i, out);
         } else {
            this.generateUDTRecordSetter(column, i, out);
            this.generateUDTRecordGetter(column, i, out);
         }
      }

      if (this.generateRelations() && key != null) {
         i = key.getKeyColumns().size();
         if (i <= 22) {
            String keyType = this.refRowType(out, key.getKeyColumns());
            ((JavaWriter)out.tab(1)).header("Primary key information");
            if (this.scala) {
               ((JavaWriter)out.tab(1)).println("override def key : %s[%s] = {", new Object[]{out.ref(Record.class.getName() + i), keyType});
               ((JavaWriter)out.tab(2)).println("return super.key.asInstanceOf[ %s[%s] ]", new Object[]{out.ref(Record.class.getName() + i), keyType});
               ((JavaWriter)out.tab(1)).println("}");
            } else {
               ((JavaWriter)out.tab(1)).overrideInherit();
               ((JavaWriter)out.tab(1)).println("public %s<%s> key() {", new Object[]{out.ref(Record.class.getName() + i), keyType});
               ((JavaWriter)out.tab(2)).println("return (%s) super.key();", new Object[]{out.ref(Record.class.getName() + i)});
               ((JavaWriter)out.tab(1)).println("}");
            }
         }
      }

      if (tableOrUdt instanceof UDTDefinition) {
         Iterator var18 = ((UDTDefinition)tableOrUdt).getRoutines().iterator();

         while(var18.hasNext()) {
            RoutineDefinition routine = (RoutineDefinition)var18.next();
            boolean instance = routine.getInParameters().size() > 0 && ((ParameterDefinition)routine.getInParameters().get(0)).getInputName().toUpperCase().equals("SELF");

            try {
               if (!routine.isSQLUsable()) {
                  this.printConvenienceMethodProcedure(out, routine, instance);
               } else if (!routine.isAggregate()) {
                  this.printConvenienceMethodFunction(out, routine, instance);
               }
            } catch (Exception var17) {
               log.error("Error while generating routine " + routine, (Throwable)var17);
            }
         }
      }

      String call;
      String colType;
      ArrayList arguments;
      if (degree > 0 && degree <= 22) {
         ((JavaWriter)out.tab(1)).header("Record%s type implementation", degree);
         if (this.scala) {
            out.println();
            ((JavaWriter)out.tab(1)).println("override def fieldsRow : %s[%s] = {", new Object[]{out.ref(Row.class.getName() + degree), rowType});
            ((JavaWriter)out.tab(2)).println("super.fieldsRow.asInstanceOf[ %s[%s] ]", new Object[]{out.ref(Row.class.getName() + degree), rowType});
            ((JavaWriter)out.tab(1)).println("}");
         } else {
            ((JavaWriter)out.tab(1)).overrideInherit();
            ((JavaWriter)out.tab(1)).println("public %s<%s> fieldsRow() {", new Object[]{out.ref(Row.class.getName() + degree), rowType});
            ((JavaWriter)out.tab(2)).println("return (%s) super.fieldsRow();", new Object[]{out.ref(Row.class.getName() + degree)});
            ((JavaWriter)out.tab(1)).println("}");
         }

         if (this.scala) {
            out.println();
            ((JavaWriter)out.tab(1)).println("override def valuesRow : %s[%s] = {", new Object[]{out.ref(Row.class.getName() + degree), rowType});
            ((JavaWriter)out.tab(2)).println("super.valuesRow.asInstanceOf[ %s[%s] ]", new Object[]{out.ref(Row.class.getName() + degree), rowType});
            ((JavaWriter)out.tab(1)).println("}");
         } else {
            ((JavaWriter)out.tab(1)).overrideInherit();
            ((JavaWriter)out.tab(1)).println("public %s<%s> valuesRow() {", new Object[]{out.ref(Row.class.getName() + degree), rowType});
            ((JavaWriter)out.tab(2)).println("return (%s) super.valuesRow();", new Object[]{out.ref(Row.class.getName() + degree)});
            ((JavaWriter)out.tab(1)).println("}");
         }

         String colType;
         for(i = 1; i <= degree; ++i) {
            column = (TypedElementDefinition)columns.get(i - 1);
            colType = out.ref(this.getJavaType(column.getType()));
            call = out.ref(this.getStrategy().getFullJavaIdentifier(column), this.colRefSegments(column));
            if (this.scala) {
               ((JavaWriter)out.tab(1)).println("override def field%s : %s[%s] = %s", new Object[]{i, Field.class, colType, call});
            } else {
               ((JavaWriter)out.tab(1)).overrideInherit();
               ((JavaWriter)out.tab(1)).println("public %s<%s> field%s() {", new Object[]{Field.class, colType, i});
               ((JavaWriter)out.tab(2)).println("return %s;", new Object[]{call});
               ((JavaWriter)out.tab(1)).println("}");
            }
         }

         for(i = 1; i <= degree; ++i) {
            column = (TypedElementDefinition)columns.get(i - 1);
            colType = out.ref(this.getJavaType(column.getType()));
            call = this.getStrategy().getJavaGetterName(column, GeneratorStrategy.Mode.RECORD);
            if (this.scala) {
               ((JavaWriter)out.tab(1)).println("override def value%s : %s = %s", new Object[]{i, colType, call});
            } else {
               ((JavaWriter)out.tab(1)).overrideInherit();
               ((JavaWriter)out.tab(1)).println("public %s value%s() {", new Object[]{colType, i});
               ((JavaWriter)out.tab(2)).println("return %s();", new Object[]{call});
               ((JavaWriter)out.tab(1)).println("}");
            }
         }

         for(i = 1; i <= degree; ++i) {
            column = (TypedElementDefinition)columns.get(i - 1);
            colType = out.ref(this.getJavaType(column.getType()));
            call = this.getStrategy().getJavaSetterName(column, GeneratorStrategy.Mode.RECORD);
            if (this.scala) {
               out.println();
               ((JavaWriter)out.tab(1)).println("override def value%s(value : %s) : %s = {", new Object[]{i, colType, className});
               ((JavaWriter)out.tab(2)).println("%s(value)", new Object[]{call});
               ((JavaWriter)out.tab(2)).println("this");
               ((JavaWriter)out.tab(1)).println("}");
            } else {
               ((JavaWriter)out.tab(1)).overrideInherit();
               ((JavaWriter)out.tab(1)).println("public %s value%s(%s value) {", new Object[]{className, i, this.varargsIfArray(colType)});
               ((JavaWriter)out.tab(2)).println("%s(value);", new Object[]{call});
               ((JavaWriter)out.tab(2)).println("return this;");
               ((JavaWriter)out.tab(1)).println("}");
            }
         }

         arguments = new ArrayList();
         List<String> calls = new ArrayList();

         for(int i = 1; i <= degree; ++i) {
            TypedElementDefinition<?> column = (TypedElementDefinition)columns.get(i - 1);
            colType = out.ref(this.getJavaType(column.getType()));
            if (this.scala) {
               arguments.add("value" + i + " : " + colType);
               calls.add("this.value" + i + "(value" + i + ")");
            } else {
               arguments.add(colType + " value" + i);
               calls.add("value" + i + "(value" + i + ");");
            }
         }

         Iterator var27;
         if (this.scala) {
            out.println();
            ((JavaWriter)out.tab(1)).println("override def values([[%s]]) : %s = {", new Object[]{arguments, className});
            var27 = calls.iterator();

            while(var27.hasNext()) {
               call = (String)var27.next();
               ((JavaWriter)out.tab(2)).println(call);
            }

            ((JavaWriter)out.tab(2)).println("this");
            ((JavaWriter)out.tab(1)).println("}");
         } else {
            ((JavaWriter)out.tab(1)).overrideInherit();
            ((JavaWriter)out.tab(1)).println("public %s values([[%s]]) {", new Object[]{className, arguments});
            var27 = calls.iterator();

            while(var27.hasNext()) {
               call = (String)var27.next();
               ((JavaWriter)out.tab(2)).println(call);
            }

            ((JavaWriter)out.tab(2)).println("return this;");
            ((JavaWriter)out.tab(1)).println("}");
         }
      }

      if (this.generateInterfaces()) {
         this.printFromAndInto(out, tableOrUdt);
      }

      if (!this.scala) {
         ((JavaWriter)out.tab(1)).header("Constructors");
         ((JavaWriter)out.tab(1)).javadoc("Create a detached %s", className);
         ((JavaWriter)out.tab(1)).println("public %s() {", new Object[]{className});
         ((JavaWriter)out.tab(2)).println("super(%s);", new Object[]{tableIdentifier});
         ((JavaWriter)out.tab(1)).println("}");
      }

      if (degree > 0 && degree < 256) {
         arguments = new ArrayList();

         int i;
         TypedElementDefinition column;
         for(i = 0; i < degree; ++i) {
            column = (TypedElementDefinition)columns.get(i);
            call = this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.DEFAULT);
            colType = out.ref(this.getJavaType(column.getType()));
            if (this.scala) {
               arguments.add(call + " : " + colType);
            } else {
               arguments.add(colType + " " + call);
            }
         }

         ((JavaWriter)out.tab(1)).javadoc("Create a detached, initialised %s", className);
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("def this([[%s]]) = {", new Object[]{arguments});
            ((JavaWriter)out.tab(2)).println("this()", new Object[]{tableIdentifier});
         } else {
            ((JavaWriter)out.tab(1)).println("public %s([[%s]]) {", new Object[]{className, arguments});
            ((JavaWriter)out.tab(2)).println("super(%s);", new Object[]{tableIdentifier});
         }

         out.println();

         for(i = 0; i < degree; ++i) {
            column = (TypedElementDefinition)columns.get(i);
            call = this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.DEFAULT);
            if (this.scala) {
               ((JavaWriter)out.tab(2)).println("set(%s, %s)", new Object[]{i, call});
            } else {
               ((JavaWriter)out.tab(2)).println("set(%s, %s);", new Object[]{i, call});
            }
         }

         ((JavaWriter)out.tab(1)).println("}");
      }

      if (tableOrUdt instanceof TableDefinition) {
         this.generateRecordClassFooter((TableDefinition)tableOrUdt, out);
      } else {
         this.generateUDTRecordClassFooter((UDTDefinition)tableOrUdt, out);
      }

      out.println("}");
   }

   protected void generateRecordSetter(TypedElementDefinition<?> column, int index, JavaWriter out) {
      this.generateRecordSetter0(column, index, out);
   }

   protected void generateUDTRecordSetter(TypedElementDefinition<?> column, int index, JavaWriter out) {
      this.generateRecordSetter0(column, index, out);
   }

   private final void generateRecordSetter0(TypedElementDefinition<?> column, int index, JavaWriter out) {
      String comment = StringUtils.defaultString(column.getComment());
      String className = this.getStrategy().getJavaClassName(column.getContainer(), GeneratorStrategy.Mode.RECORD);
      String setterReturnType = this.fluentSetters() ? className : this.tokenVoid;
      String setter = this.getStrategy().getJavaSetterName(column, GeneratorStrategy.Mode.RECORD);
      String type = out.ref(this.getJavaType(column.getType()));
      String name = column.getQualifiedOutputName();
      boolean isUDT = column.getType().isUDT();
      boolean isArray = column.getType().isArray();
      boolean isUDTArray = column.getType().isArray() && this.database.getArray(column.getType().getSchema(), column.getType().getQualifiedUserType()).getElementType().isUDT();
      if (!this.generateInterfaces() || !isArray) {
         ((JavaWriter)out.tab(1)).javadoc("Setter for <code>%s</code>.%s", name, StringUtils.defaultIfBlank(" " + this.escapeEntities(comment), ""));
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("def %s(value : %s) : %s = {", new Object[]{setter, type, setterReturnType});
            ((JavaWriter)out.tab(2)).println("set(%s, value)", new Object[]{index});
            if (this.fluentSetters()) {
               ((JavaWriter)out.tab(2)).println("this");
            }

            ((JavaWriter)out.tab(1)).println("}");
         } else {
            ((JavaWriter)out.tab(1)).overrideIf(this.generateInterfaces() && !this.generateImmutableInterfaces() && !isUDT);
            ((JavaWriter)out.tab(1)).println("public %s %s(%s value) {", new Object[]{setterReturnType, setter, this.varargsIfArray(type)});
            ((JavaWriter)out.tab(2)).println("set(%s, value);", new Object[]{index});
            if (this.fluentSetters()) {
               ((JavaWriter)out.tab(2)).println("return this;");
            }

            ((JavaWriter)out.tab(1)).println("}");
         }
      }

      if (this.generateInterfaces() && !this.generateImmutableInterfaces() && (isUDT || isArray)) {
         String columnType = out.ref(this.getJavaType(column.getType(), GeneratorStrategy.Mode.RECORD));
         String columnTypeInterface = out.ref(this.getJavaType(column.getType(), GeneratorStrategy.Mode.INTERFACE));
         ((JavaWriter)out.tab(1)).javadoc("Setter for <code>%s</code>.%s", name, StringUtils.defaultIfBlank(" " + comment, ""));
         ((JavaWriter)out.tab(1)).override();
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("def %s(value : %s) : %s = {", new Object[]{setter, columnTypeInterface, setterReturnType});
            ((JavaWriter)out.tab(2)).println("if (value == null)");
            ((JavaWriter)out.tab(3)).println("set(%s, null)", new Object[]{index});
            ((JavaWriter)out.tab(2)).println("else");
            ((JavaWriter)out.tab(3)).println("set(%s, value.into(new %s()))", new Object[]{index, type});
            if (this.fluentSetters()) {
               ((JavaWriter)out.tab(2)).println("this");
            }

            ((JavaWriter)out.tab(1)).println("}");
         } else {
            ((JavaWriter)out.tab(1)).println("public %s %s(%s value) {", new Object[]{setterReturnType, setter, this.varargsIfArray(columnTypeInterface)});
            ((JavaWriter)out.tab(2)).println("if (value == null)");
            ((JavaWriter)out.tab(3)).println("set(%s, null);", new Object[]{index});
            if (isUDT) {
               ((JavaWriter)out.tab(2)).println("else");
               ((JavaWriter)out.tab(3)).println("set(%s, value.into(new %s()));", new Object[]{index, type});
            } else if (isArray) {
               ArrayDefinition array = this.database.getArray(column.getType().getSchema(), column.getType().getQualifiedUserType());
               String componentType = out.ref(this.getJavaType(array.getElementType(), GeneratorStrategy.Mode.RECORD));
               String componentTypeInterface = out.ref(this.getJavaType(array.getElementType(), GeneratorStrategy.Mode.INTERFACE));
               ((JavaWriter)out.tab(2)).println("else {");
               ((JavaWriter)out.tab(3)).println("%s a = new %s();", new Object[]{columnType, columnType});
               out.println();
               ((JavaWriter)out.tab(3)).println("for (%s i : value)", new Object[]{componentTypeInterface});
               if (isUDTArray) {
                  ((JavaWriter)out.tab(4)).println("a.add(i.into(new %s()));", new Object[]{componentType});
               } else {
                  ((JavaWriter)out.tab(4)).println("a.add(i);", new Object[]{componentType});
               }

               out.println();
               ((JavaWriter)out.tab(3)).println("set(1, a);");
               ((JavaWriter)out.tab(2)).println("}");
            }

            if (this.fluentSetters()) {
               ((JavaWriter)out.tab(2)).println("return this;");
            }

            ((JavaWriter)out.tab(1)).println("}");
         }
      }

   }

   protected void generateRecordGetter(TypedElementDefinition<?> column, int index, JavaWriter out) {
      this.generateRecordGetter0(column, index, out);
   }

   protected void generateUDTRecordGetter(TypedElementDefinition<?> column, int index, JavaWriter out) {
      this.generateRecordGetter0(column, index, out);
   }

   private final void generateRecordGetter0(TypedElementDefinition<?> column, int index, JavaWriter out) {
      String comment = StringUtils.defaultString(column.getComment());
      String getter = this.getStrategy().getJavaGetterName(column, GeneratorStrategy.Mode.RECORD);
      String type = out.ref(this.getJavaType(column.getType()));
      String name = column.getQualifiedOutputName();
      ((JavaWriter)out.tab(1)).javadoc("Getter for <code>%s</code>.%s", name, StringUtils.defaultIfBlank(" " + this.escapeEntities(comment), ""));
      if (column.getContainer() instanceof TableDefinition) {
         this.printColumnJPAAnnotation(out, (ColumnDefinition)column);
      }

      this.printValidationAnnotation(out, column);
      if (this.scala) {
         ((JavaWriter)out.tab(1)).println("def %s : %s = {", new Object[]{getter, type});
         ((JavaWriter)out.tab(2)).println("val r = get(%s)", new Object[]{index});
         ((JavaWriter)out.tab(2)).println("if (r == null) null else r.asInstanceOf[%s]", new Object[]{type});
         ((JavaWriter)out.tab(1)).println("}");
      } else {
         ((JavaWriter)out.tab(1)).overrideIf(this.generateInterfaces());
         ((JavaWriter)out.tab(1)).println("public %s %s() {", new Object[]{type, getter});
         ((JavaWriter)out.tab(2)).println("return (%s) get(%s);", new Object[]{type, index});
         ((JavaWriter)out.tab(1)).println("}");
      }

   }

   private int colRefSegments(TypedElementDefinition<?> column) {
      if (column != null && column.getContainer() instanceof UDTDefinition) {
         return 2;
      } else {
         return !this.getStrategy().getInstanceFields() ? 2 : 3;
      }
   }

   protected void generateRecordClassFooter(TableDefinition table, JavaWriter out) {
   }

   protected void generateRecordClassJavadoc(TableDefinition table, JavaWriter out) {
      this.printClassJavadoc(out, (Definition)table);
   }

   private String refRowType(JavaWriter out, Collection<? extends TypedElementDefinition<?>> columns) {
      StringBuilder result = new StringBuilder();
      String separator = "";

      for(Iterator var5 = columns.iterator(); var5.hasNext(); separator = ", ") {
         TypedElementDefinition<?> column = (TypedElementDefinition)var5.next();
         result.append(separator);
         result.append(out.ref(this.getJavaType(column.getType())));
      }

      return result.toString();
   }

   protected void generateInterfaces(SchemaDefinition schema) {
      log.info("Generating table interfaces");
      Iterator var2 = this.database.getTables(schema).iterator();

      while(var2.hasNext()) {
         TableDefinition table = (TableDefinition)var2.next();

         try {
            this.generateInterface(table);
         } catch (Exception var5) {
            log.error("Error while generating table interface " + table, (Throwable)var5);
         }
      }

      this.watch.splitInfo("Table interfaces generated");
   }

   protected void generateInterface(TableDefinition table) {
      JavaWriter out = this.newJavaWriter(this.getStrategy().getFile(table, GeneratorStrategy.Mode.INTERFACE));
      log.info("Generating interface", (Object)out.file().getName());
      this.generateInterface(table, out);
      this.closeJavaWriter(out);
   }

   protected void generateUDTInterface(UDTDefinition udt) {
      JavaWriter out = this.newJavaWriter(this.getStrategy().getFile(udt, GeneratorStrategy.Mode.INTERFACE));
      log.info("Generating interface", (Object)out.file().getName());
      this.generateInterface0(udt, out);
      this.closeJavaWriter(out);
   }

   protected void generateInterface(TableDefinition table, JavaWriter out) {
      this.generateInterface0(table, out);
   }

   protected void generateUDTInterface(UDTDefinition udt, JavaWriter out) {
      this.generateInterface0(udt, out);
   }

   private final void generateInterface0(Definition tableOrUDT, JavaWriter out) {
      String className = this.getStrategy().getJavaClassName(tableOrUDT, GeneratorStrategy.Mode.INTERFACE);
      List<String> interfaces = out.ref(this.getStrategy().getJavaClassImplements(tableOrUDT, GeneratorStrategy.Mode.INTERFACE));
      this.printPackage(out, tableOrUDT, GeneratorStrategy.Mode.INTERFACE);
      if (tableOrUDT instanceof TableDefinition) {
         this.generateInterfaceClassJavadoc((TableDefinition)tableOrUDT, out);
      } else {
         this.generateUDTInterfaceClassJavadoc((UDTDefinition)tableOrUDT, out);
      }

      this.printClassAnnotations(out, tableOrUDT.getSchema());
      if (tableOrUDT instanceof TableDefinition) {
         this.printTableJPAAnnotation(out, (TableDefinition)tableOrUDT);
      }

      if (this.scala) {
         out.println("trait %s [[before=extends ][%s]] {", new Object[]{className, interfaces});
      } else {
         out.println("public interface %s [[before=extends ][%s]] {", new Object[]{className, interfaces});
      }

      List<? extends TypedElementDefinition<?>> typedElements = this.getTypedElements(tableOrUDT);

      for(int i = 0; i < typedElements.size(); ++i) {
         TypedElementDefinition<?> column = (TypedElementDefinition)typedElements.get(i);
         if (!this.generateImmutableInterfaces()) {
            if (tableOrUDT instanceof TableDefinition) {
               this.generateInterfaceSetter(column, i, out);
            } else {
               this.generateUDTInterfaceSetter(column, i, out);
            }
         }

         if (tableOrUDT instanceof TableDefinition) {
            this.generateInterfaceGetter(column, i, out);
         } else {
            this.generateUDTInterfaceGetter(column, i, out);
         }
      }

      if (!this.generateImmutableInterfaces()) {
         String local = this.getStrategy().getJavaClassName(tableOrUDT, GeneratorStrategy.Mode.INTERFACE);
         String qualified = out.ref(this.getStrategy().getFullJavaClassName(tableOrUDT, GeneratorStrategy.Mode.INTERFACE));
         ((JavaWriter)out.tab(1)).header("FROM and INTO");
         ((JavaWriter)out.tab(1)).javadoc("Load data from another generated Record/POJO implementing the common interface %s", local);
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("def from(from : %s)", new Object[]{qualified});
         } else {
            ((JavaWriter)out.tab(1)).println("public void from(%s from);", new Object[]{qualified});
         }

         ((JavaWriter)out.tab(1)).javadoc("Copy data into another generated Record/POJO implementing the common interface %s", local);
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("def into [E <: %s](into : E) : E", new Object[]{qualified});
         } else {
            ((JavaWriter)out.tab(1)).println("public <E extends %s> E into(E into);", new Object[]{qualified});
         }
      }

      if (tableOrUDT instanceof TableDefinition) {
         this.generateInterfaceClassFooter((TableDefinition)tableOrUDT, out);
      } else {
         this.generateUDTInterfaceClassFooter((UDTDefinition)tableOrUDT, out);
      }

      out.println("}");
   }

   protected void generateInterfaceSetter(TypedElementDefinition<?> column, int index, JavaWriter out) {
      this.generateInterfaceSetter0(column, index, out);
   }

   protected void generateUDTInterfaceSetter(TypedElementDefinition<?> column, int index, JavaWriter out) {
      this.generateInterfaceSetter0(column, index, out);
   }

   private final void generateInterfaceSetter0(TypedElementDefinition<?> column, int index, JavaWriter out) {
      String className = this.getStrategy().getJavaClassName(column.getContainer(), GeneratorStrategy.Mode.INTERFACE);
      String comment = StringUtils.defaultString(column.getComment());
      String setterReturnType = this.fluentSetters() ? className : "void";
      String setter = this.getStrategy().getJavaSetterName(column, GeneratorStrategy.Mode.INTERFACE);
      String type = out.ref(this.getJavaType(column.getType(), GeneratorStrategy.Mode.INTERFACE));
      String name = column.getQualifiedOutputName();
      ((JavaWriter)out.tab(1)).javadoc("Setter for <code>%s</code>.%s", name, StringUtils.defaultIfBlank(" " + this.escapeEntities(comment), ""));
      if (this.scala) {
         ((JavaWriter)out.tab(1)).println("def %s(value : %s) : %s", new Object[]{setter, type, setterReturnType});
      } else {
         ((JavaWriter)out.tab(1)).println("public %s %s(%s value);", new Object[]{setterReturnType, setter, this.varargsIfArray(type)});
      }

   }

   protected void generateInterfaceGetter(TypedElementDefinition<?> column, int index, JavaWriter out) {
      this.generateInterfaceGetter0(column, index, out);
   }

   protected void generateUDTInterfaceGetter(TypedElementDefinition<?> column, int index, JavaWriter out) {
      this.generateInterfaceGetter0(column, index, out);
   }

   private final void generateInterfaceGetter0(TypedElementDefinition<?> column, int index, JavaWriter out) {
      String comment = StringUtils.defaultString(column.getComment());
      String getter = this.getStrategy().getJavaGetterName(column, GeneratorStrategy.Mode.INTERFACE);
      String type = out.ref(this.getJavaType(column.getType(), GeneratorStrategy.Mode.INTERFACE));
      String name = column.getQualifiedOutputName();
      ((JavaWriter)out.tab(1)).javadoc("Getter for <code>%s</code>.%s", name, StringUtils.defaultIfBlank(" " + this.escapeEntities(comment), ""));
      if (column instanceof ColumnDefinition) {
         this.printColumnJPAAnnotation(out, (ColumnDefinition)column);
      }

      this.printValidationAnnotation(out, column);
      if (this.scala) {
         ((JavaWriter)out.tab(1)).println("def %s : %s", new Object[]{getter, type});
      } else {
         ((JavaWriter)out.tab(1)).println("public %s %s();", new Object[]{type, getter});
      }

   }

   protected void generateInterfaceClassFooter(TableDefinition table, JavaWriter out) {
   }

   protected void generateInterfaceClassJavadoc(TableDefinition table, JavaWriter out) {
      this.printClassJavadoc(out, (Definition)table);
   }

   protected void generateUDTs(SchemaDefinition schema) {
      log.info("Generating UDTs");
      Iterator var2 = this.database.getUDTs(schema).iterator();

      while(var2.hasNext()) {
         UDTDefinition udt = (UDTDefinition)var2.next();

         try {
            this.generateUDT(schema, udt);
         } catch (Exception var5) {
            log.error("Error while generating udt " + udt, (Throwable)var5);
         }
      }

      this.watch.splitInfo("UDTs generated");
   }

   protected void generateUDT(SchemaDefinition schema, UDTDefinition udt) {
      JavaWriter out = this.newJavaWriter(this.getStrategy().getFile((Definition)udt));
      log.info("Generating UDT ", (Object)out.file().getName());
      this.generateUDT(udt, out);
      this.closeJavaWriter(out);
   }

   protected void generateUDT(UDTDefinition udt, JavaWriter out) {
      SchemaDefinition schema = udt.getSchema();
      PackageDefinition pkg = udt.getPackage();
      String className = this.getStrategy().getJavaClassName(udt);
      String recordType = out.ref(this.getStrategy().getFullJavaClassName(udt, GeneratorStrategy.Mode.RECORD));
      List<String> interfaces = out.ref(this.getStrategy().getJavaClassImplements(udt, GeneratorStrategy.Mode.DEFAULT));
      String schemaId = out.ref(this.getStrategy().getFullJavaIdentifier(schema), 2);
      String packageId = pkg == null ? null : out.ref(this.getStrategy().getFullJavaIdentifier(pkg), 2);
      String udtId = out.ref(this.getStrategy().getJavaIdentifier(udt), 2);
      this.printPackage(out, udt);
      Iterator var11;
      AttributeDefinition attribute;
      String attrType;
      String attrTypeRef;
      if (this.scala) {
         out.println("object %s {", new Object[]{className});
         this.printSingletonInstance(out, udt);
         var11 = udt.getAttributes().iterator();

         while(var11.hasNext()) {
            attribute = (AttributeDefinition)var11.next();
            attrType = out.ref(this.getStrategy().getJavaIdentifier(attribute), 2);
            attrTypeRef = StringUtils.defaultString(attribute.getComment());
            ((JavaWriter)out.tab(1)).javadoc("The attribute <code>%s</code>.%s", attribute.getQualifiedOutputName(), StringUtils.defaultIfBlank(" " + this.escapeEntities(attrTypeRef), ""));
            ((JavaWriter)out.tab(1)).println("val %s = %s.%s", new Object[]{attrType, udtId, attrType});
         }

         out.println("}");
         out.println();
      }

      this.generateUDTClassJavadoc(udt, out);
      this.printClassAnnotations(out, schema);
      if (udt.getRoutines().size() > 0) {
         interfaces.add(out.ref(Package.class));
      }

      if (this.scala) {
         out.println("class %s extends %s[%s](\"%s\", null[[before=, ][%s]])[[before= with ][separator= with ][%s]] {", new Object[]{className, UDTImpl.class, recordType, udt.getOutputName(), list(packageId), interfaces});
      } else {
         out.println("public class %s extends %s<%s>[[before= implements ][%s]] {", new Object[]{className, UDTImpl.class, recordType, interfaces});
         out.printSerial();
         this.printSingletonInstance(out, udt);
      }

      this.printRecordTypeMethod(out, udt);
      var11 = udt.getAttributes().iterator();

      while(var11.hasNext()) {
         attribute = (AttributeDefinition)var11.next();
         attrType = out.ref(this.getJavaType(attribute.getType()));
         attrTypeRef = this.getJavaTypeReference(attribute.getDatabase(), attribute.getType());
         String attrId = out.ref(this.getStrategy().getJavaIdentifier(attribute), 2);
         String attrName = attribute.getName();
         String attrComment = StringUtils.defaultString(attribute.getComment());
         List<String> converters = out.ref(list(attribute.getType().getConverter(), attribute.getType().getBinding()));
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("private val %s : %s[%s, %s] = %s.createField(\"%s\", %s, this, \"%s\"[[before=, ][new %s]])", new Object[]{attrId, UDTField.class, recordType, attrType, UDTImpl.class, attrName, attrTypeRef, this.escapeString(""), converters});
         } else {
            ((JavaWriter)out.tab(1)).javadoc("The attribute <code>%s</code>.%s", attribute.getQualifiedOutputName(), StringUtils.defaultIfBlank(" " + attrComment, ""));
            ((JavaWriter)out.tab(1)).println("public static final %s<%s, %s> %s = createField(\"%s\", %s, %s, \"%s\"[[before=, ][new %s()]]);", new Object[]{UDTField.class, recordType, attrType, attrId, attrName, attrTypeRef, udtId, this.escapeString(""), converters});
         }
      }

      var11 = udt.getRoutines().iterator();

      while(var11.hasNext()) {
         RoutineDefinition routine = (RoutineDefinition)var11.next();

         try {
            if (!routine.isSQLUsable()) {
               this.printConvenienceMethodProcedure(out, routine, false);
            } else {
               if (!routine.isAggregate()) {
                  this.printConvenienceMethodFunction(out, routine, false);
               }

               this.printConvenienceMethodFunctionAsField(out, routine, false);
               this.printConvenienceMethodFunctionAsField(out, routine, true);
            }
         } catch (Exception var19) {
            log.error("Error while generating routine " + routine, (Throwable)var19);
         }
      }

      if (!this.scala) {
         ((JavaWriter)out.tab(1)).javadoc("No further instances allowed");
         ((JavaWriter)out.tab(1)).println("private %s() {", new Object[]{className});
         ((JavaWriter)out.tab(2)).println("super(\"%s\", null[[before=, ][%s]]);", new Object[]{udt.getOutputName(), list(packageId)});
         ((JavaWriter)out.tab(1)).println("}");
      }

      if (this.scala) {
         out.println();
         ((JavaWriter)out.tab(1)).println("override def getSchema : %s = %s", new Object[]{Schema.class, schemaId});
      } else {
         ((JavaWriter)out.tab(1)).overrideInherit();
         ((JavaWriter)out.tab(1)).println("public %s getSchema() {", new Object[]{Schema.class});
         ((JavaWriter)out.tab(2)).println("return %s;", new Object[]{schemaId});
         ((JavaWriter)out.tab(1)).println("}");
      }

      this.generateUDTClassFooter(udt, out);
      out.println("}");
      this.closeJavaWriter(out);
   }

   protected void generateUDTClassFooter(UDTDefinition udt, JavaWriter out) {
   }

   protected void generateUDTClassJavadoc(UDTDefinition udt, JavaWriter out) {
      this.printClassJavadoc(out, (Definition)udt);
   }

   protected void generateUDTPojos(SchemaDefinition schema) {
      log.info("Generating UDT POJOs");
      Iterator var2 = this.database.getUDTs(schema).iterator();

      while(var2.hasNext()) {
         UDTDefinition udt = (UDTDefinition)var2.next();

         try {
            this.generateUDTPojo(udt);
         } catch (Exception var5) {
            log.error("Error while generating UDT POJO " + udt, (Throwable)var5);
         }
      }

      this.watch.splitInfo("UDT POJOs generated");
   }

   protected void generateUDTPojoClassFooter(UDTDefinition udt, JavaWriter out) {
   }

   protected void generateUDTPojoClassJavadoc(UDTDefinition udt, JavaWriter out) {
      this.printClassJavadoc(out, (Definition)udt);
   }

   protected void generateUDTInterfaces(SchemaDefinition schema) {
      log.info("Generating UDT interfaces");
      Iterator var2 = this.database.getUDTs(schema).iterator();

      while(var2.hasNext()) {
         UDTDefinition udt = (UDTDefinition)var2.next();

         try {
            this.generateUDTInterface(udt);
         } catch (Exception var5) {
            log.error("Error while generating UDT interface " + udt, (Throwable)var5);
         }
      }

      this.watch.splitInfo("UDT interfaces generated");
   }

   protected void generateUDTInterfaceClassFooter(UDTDefinition udt, JavaWriter out) {
   }

   protected void generateUDTInterfaceClassJavadoc(UDTDefinition udt, JavaWriter out) {
      this.printClassJavadoc(out, (Definition)udt);
   }

   protected void generateUDTRecords(SchemaDefinition schema) {
      log.info("Generating UDT records");
      Iterator var2 = this.database.getUDTs(schema).iterator();

      while(var2.hasNext()) {
         UDTDefinition udt = (UDTDefinition)var2.next();

         try {
            this.generateUDTRecord(udt);
         } catch (Exception var5) {
            log.error("Error while generating UDT record " + udt, (Throwable)var5);
         }
      }

      this.watch.splitInfo("UDT records generated");
   }

   protected void generateUDTRecordClassFooter(UDTDefinition udt, JavaWriter out) {
   }

   protected void generateUDTRecordClassJavadoc(UDTDefinition udt, JavaWriter out) {
      this.printClassJavadoc(out, (Definition)udt);
   }

   protected void generateUDTRoutines(SchemaDefinition schema) {
      Iterator var2 = this.database.getUDTs(schema).iterator();

      while(true) {
         UDTDefinition udt;
         do {
            if (!var2.hasNext()) {
               return;
            }

            udt = (UDTDefinition)var2.next();
         } while(udt.getRoutines().size() <= 0);

         try {
            log.info("Generating member routines");
            Iterator var4 = udt.getRoutines().iterator();

            while(var4.hasNext()) {
               RoutineDefinition routine = (RoutineDefinition)var4.next();

               try {
                  this.generateRoutine(schema, routine);
               } catch (Exception var7) {
                  log.error("Error while generating member routines " + routine, (Throwable)var7);
               }
            }
         } catch (Exception var8) {
            log.error("Error while generating UDT " + udt, (Throwable)var8);
         }

         this.watch.splitInfo("Member procedures routines");
      }
   }

   protected void generateUDTReferences(SchemaDefinition schema) {
      log.info("Generating UDT references");
      JavaWriter out = this.newJavaWriter(new File(this.getStrategy().getFile((Definition)schema).getParentFile(), "UDTs.java"));
      this.printPackage(out, schema);
      this.printClassJavadoc(out, "Convenience access to all UDTs in " + schema.getOutputName());
      this.printClassAnnotations(out, schema);
      if (this.scala) {
         out.println("object UDTs {");
      } else {
         out.println("public class UDTs {");
      }

      Iterator var3 = this.database.getUDTs(schema).iterator();

      while(var3.hasNext()) {
         UDTDefinition udt = (UDTDefinition)var3.next();
         String className = out.ref(this.getStrategy().getFullJavaClassName(udt));
         String id = this.getStrategy().getJavaIdentifier(udt);
         String fullId = this.getStrategy().getFullJavaIdentifier(udt);
         ((JavaWriter)out.tab(1)).javadoc("The type <code>%s</code>", udt.getQualifiedOutputName());
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("val %s = %s", new Object[]{id, fullId});
         } else {
            ((JavaWriter)out.tab(1)).println("public static %s %s = %s;", new Object[]{className, id, fullId});
         }
      }

      out.println("}");
      this.closeJavaWriter(out);
      this.watch.splitInfo("UDT references generated");
   }

   protected void generateArrays(SchemaDefinition schema) {
      log.info("Generating ARRAYs");
      Iterator var2 = this.database.getArrays(schema).iterator();

      while(var2.hasNext()) {
         ArrayDefinition array = (ArrayDefinition)var2.next();

         try {
            this.generateArray(schema, array);
         } catch (Exception var5) {
            log.error("Error while generating ARRAY record " + array, (Throwable)var5);
         }
      }

      this.watch.splitInfo("ARRAYs generated");
   }

   protected void generateArray(SchemaDefinition schema, ArrayDefinition array) {
      JavaWriter out = this.newJavaWriter(this.getStrategy().getFile(array, GeneratorStrategy.Mode.RECORD));
      log.info("Generating ARRAY", (Object)out.file().getName());
      this.generateArray(array, out);
      this.closeJavaWriter(out);
   }

   protected void generateArray(ArrayDefinition array, JavaWriter out) {
   }

   protected void generateArrayClassFooter(ArrayDefinition array, JavaWriter out) {
   }

   protected void generateArrayClassJavadoc(ArrayDefinition array, JavaWriter out) {
      this.printClassJavadoc(out, (Definition)array);
   }

   protected void generateEnums(SchemaDefinition schema) {
      log.info("Generating ENUMs");
      Iterator var2 = this.database.getEnums(schema).iterator();

      while(var2.hasNext()) {
         EnumDefinition e = (EnumDefinition)var2.next();

         try {
            this.generateEnum(e);
         } catch (Exception var5) {
            log.error("Error while generating enum " + e, (Throwable)var5);
         }
      }

      this.watch.splitInfo("Enums generated");
   }

   protected void generateDomains(SchemaDefinition schema) {
      log.info("Generating DOMAINs");
      Iterator var2 = this.database.getDomains(schema).iterator();

      while(var2.hasNext()) {
         DomainDefinition d = (DomainDefinition)var2.next();

         try {
            this.generateDomain(d);
         } catch (Exception var5) {
            log.error("Error while generating domain " + d, (Throwable)var5);
         }
      }

      this.watch.splitInfo("Domains generated");
   }

   protected void generateEnum(EnumDefinition e) {
      JavaWriter out = this.newJavaWriter(this.getStrategy().getFile(e, GeneratorStrategy.Mode.ENUM));
      log.info("Generating ENUM", (Object)out.file().getName());
      this.generateEnum(e, out);
      this.closeJavaWriter(out);
   }

   protected void generateEnum(EnumDefinition e, JavaWriter out) {
      String className = this.getStrategy().getJavaClassName(e, GeneratorStrategy.Mode.ENUM);
      List<String> interfaces = out.ref(this.getStrategy().getJavaClassImplements(e, GeneratorStrategy.Mode.ENUM));
      List<String> literals = e.getLiterals();
      List<String> identifiers = new ArrayList();

      for(int i = 0; i < literals.size(); ++i) {
         String identifier = GenerationUtil.convertToIdentifier((String)literals.get(i), this.language);
         if (identifier.equals(this.getStrategy().getJavaPackageName(e).replaceAll("\\..*", ""))) {
            identifier = identifier + "_";
         }

         identifiers.add(identifier);
      }

      this.printPackage(out, e);
      this.generateEnumClassJavadoc(e, out);
      this.printClassAnnotations(out, e.getSchema());
      boolean enumHasNoSchema = e.isSynthetic() || !(e.getDatabase() instanceof PostgresDatabase);
      int i;
      if (this.scala) {
         out.println("object %s {", new Object[]{className});
         out.println();

         for(i = 0; i < identifiers.size(); ++i) {
            ((JavaWriter)out.tab(1)).println("val %s : %s = %s.%s", new Object[]{identifiers.get(i), className, this.getStrategy().getJavaPackageName(e), identifiers.get(i)});
         }

         out.println();
         ((JavaWriter)out.tab(1)).println("def values : %s[%s] = %s(", new Object[]{out.ref("scala.Array"), className, out.ref("scala.Array")});

         for(i = 0; i < identifiers.size(); ++i) {
            ((JavaWriter)out.tab(2)).print(i > 0 ? ", " : "  ");
            out.println((String)identifiers.get(i));
         }

         ((JavaWriter)out.tab(1)).println(")");
         out.println();
         ((JavaWriter)out.tab(1)).println("def valueOf(s : %s) : %s = s match {", new Object[]{String.class, className});

         for(i = 0; i < identifiers.size(); ++i) {
            ((JavaWriter)out.tab(2)).println("case \"%s\" => %s", new Object[]{literals.get(i), identifiers.get(i)});
         }

         ((JavaWriter)out.tab(2)).println("case _ => throw new %s()", new Object[]{IllegalArgumentException.class});
         ((JavaWriter)out.tab(1)).println("}");
         out.println("}");
         out.println();
         out.println("sealed trait %s extends %s[[before= with ][%s]] {", new Object[]{className, EnumType.class, interfaces});
         if (enumHasNoSchema) {
            ((JavaWriter)out.tab(1)).println("override def getCatalog : %s = null", new Object[]{Catalog.class});
         } else {
            ((JavaWriter)out.tab(1)).println("override def getCatalog : %s = if (getSchema == null) null else getSchema().getCatalog()", new Object[]{Catalog.class});
         }

         ((JavaWriter)out.tab(1)).println("override def getSchema : %s = %s", new Object[]{Schema.class, enumHasNoSchema ? "null" : out.ref(this.getStrategy().getFullJavaIdentifier(e.getSchema()), 2)});
         ((JavaWriter)out.tab(1)).println("override def getName : %s = %s", new Object[]{String.class, e.isSynthetic() ? "null" : "\"" + e.getName().replace("\"", "\\\"") + "\""});
         this.generateEnumClassFooter(e, out);
         out.println("}");

         for(i = 0; i < literals.size(); ++i) {
            out.println();
            out.println("case object %s extends %s {", new Object[]{identifiers.get(i), className});
            ((JavaWriter)out.tab(1)).println("override def getLiteral : %s = \"%s\"", new Object[]{String.class, literals.get(i)});
            out.println("}");
         }
      } else {
         interfaces.add(out.ref(EnumType.class));
         out.println("public enum %s[[before= implements ][%s]] {", new Object[]{className, interfaces});

         for(i = 0; i < literals.size(); ++i) {
            out.println();
            ((JavaWriter)out.tab(1)).println("%s(\"%s\")%s", new Object[]{identifiers.get(i), literals.get(i), i == literals.size() - 1 ? ";" : ","});
         }

         out.println();
         ((JavaWriter)out.tab(1)).println("private final %s literal;", new Object[]{String.class});
         out.println();
         ((JavaWriter)out.tab(1)).println("private %s(%s literal) {", new Object[]{className, String.class});
         ((JavaWriter)out.tab(2)).println("this.literal = literal;");
         ((JavaWriter)out.tab(1)).println("}");
         ((JavaWriter)out.tab(1)).overrideInherit();
         ((JavaWriter)out.tab(1)).println("public %s getCatalog() {", new Object[]{Catalog.class});
         if (enumHasNoSchema) {
            ((JavaWriter)out.tab(2)).println("return null;");
         } else {
            ((JavaWriter)out.tab(2)).println("return getSchema() == null ? null : getSchema().getCatalog();");
         }

         ((JavaWriter)out.tab(1)).println("}");
         ((JavaWriter)out.tab(1)).overrideInherit();
         ((JavaWriter)out.tab(1)).println("public %s getSchema() {", new Object[]{Schema.class});
         ((JavaWriter)out.tab(2)).println("return %s;", new Object[]{enumHasNoSchema ? "null" : out.ref(this.getStrategy().getFullJavaIdentifier(e.getSchema()), 2)});
         ((JavaWriter)out.tab(1)).println("}");
         ((JavaWriter)out.tab(1)).overrideInherit();
         ((JavaWriter)out.tab(1)).println("public %s getName() {", new Object[]{String.class});
         ((JavaWriter)out.tab(2)).println("return %s;", new Object[]{e.isSynthetic() ? "null" : "\"" + e.getName().replace("\"", "\\\"") + "\""});
         ((JavaWriter)out.tab(1)).println("}");
         ((JavaWriter)out.tab(1)).overrideInherit();
         ((JavaWriter)out.tab(1)).println("public %s getLiteral() {", new Object[]{String.class});
         ((JavaWriter)out.tab(2)).println("return literal;");
         ((JavaWriter)out.tab(1)).println("}");
         this.generateEnumClassFooter(e, out);
         out.println("}");
      }

   }

   protected void generateEnumClassFooter(EnumDefinition e, JavaWriter out) {
   }

   protected void generateEnumClassJavadoc(EnumDefinition e, JavaWriter out) {
      this.printClassJavadoc(out, (Definition)e);
   }

   protected void generateDomain(DomainDefinition d) {
      JavaWriter out = this.newJavaWriter(this.getStrategy().getFile(d, GeneratorStrategy.Mode.DOMAIN));
      log.info("Generating DOMAIN", (Object)out.file().getName());
      this.generateDomain(d, out);
      this.closeJavaWriter(out);
   }

   protected void generateDomain(DomainDefinition d, JavaWriter out) {
      String className = this.getStrategy().getJavaClassName(d, GeneratorStrategy.Mode.DOMAIN);
      String superName = out.ref(this.getStrategy().getJavaClassExtends(d, GeneratorStrategy.Mode.DOMAIN));
      List<String> interfaces = out.ref(this.getStrategy().getJavaClassImplements(d, GeneratorStrategy.Mode.DOMAIN));
      List<String> superTypes = list(superName, interfaces);
      this.printPackage(out, d);
      this.generateDomainClassJavadoc(d, out);
      this.printClassAnnotations(out, d.getSchema());
      Iterator var7 = d.getCheckClauses().iterator();

      while(var7.hasNext()) {
         String clause = (String)var7.next();
         out.println("// " + clause);
      }

      if (this.scala) {
         out.println("class %s[[before= extends ][%s]][[before= with ][separator= with ][%s]] {", new Object[]{className, first(superTypes), remaining(superTypes)});
      } else {
         out.println("public class %s[[before= extends ][%s]][[before= implements ][%s]] {", new Object[]{className, list(superName), interfaces});
      }

      this.generateDomainClassFooter(d, out);
      out.println("}");
   }

   protected void generateDomainClassFooter(DomainDefinition d, JavaWriter out) {
   }

   protected void generateDomainClassJavadoc(DomainDefinition e, JavaWriter out) {
      this.printClassJavadoc(out, (Definition)e);
   }

   protected void generateRoutines(SchemaDefinition schema) {
      log.info("Generating routines and table-valued functions");
      if (this.generateGlobalRoutineReferences()) {
         JavaWriter out = this.newJavaWriter(new File(this.getStrategy().getFile((Definition)schema).getParentFile(), "Routines.java"));
         this.printPackage(out, schema);
         this.printClassJavadoc(out, "Convenience access to all stored procedures and functions in " + schema.getOutputName());
         this.printClassAnnotations(out, schema);
         if (this.scala) {
            out.println("object Routines {");
         } else {
            out.println("public class Routines {");
         }

         Iterator var3 = this.database.getRoutines(schema).iterator();

         while(var3.hasNext()) {
            RoutineDefinition routine = (RoutineDefinition)var3.next();
            this.printRoutine(out, routine);
         }

         var3 = this.database.getTables(schema).iterator();

         while(var3.hasNext()) {
            TableDefinition table = (TableDefinition)var3.next();
            if (table.isTableValuedFunction()) {
               this.printTableValuedFunction(out, table, this.getStrategy().getJavaMethodName(table, GeneratorStrategy.Mode.DEFAULT));
            }
         }

         out.println("}");
         this.closeJavaWriter(out);
      }

      Iterator var6 = this.database.getRoutines(schema).iterator();

      while(var6.hasNext()) {
         RoutineDefinition routine = (RoutineDefinition)var6.next();

         try {
            this.generateRoutine(schema, routine);
         } catch (Exception var5) {
            log.error("Error while generating routine " + routine, (Throwable)var5);
         }
      }

      this.watch.splitInfo("Routines generated");
   }

   protected void printConstant(JavaWriter out, AttributeDefinition constant) {
   }

   protected void printRoutine(JavaWriter out, RoutineDefinition routine) {
      if (!routine.isSQLUsable()) {
         this.printConvenienceMethodProcedure(out, routine, false);
      } else {
         if (!routine.isAggregate()) {
            this.printConvenienceMethodFunction(out, routine, false);
         }

         this.printConvenienceMethodFunctionAsField(out, routine, false);
         this.printConvenienceMethodFunctionAsField(out, routine, true);
      }

   }

   protected void printTableValuedFunction(JavaWriter out, TableDefinition table, String javaMethodName) {
      this.printConvenienceMethodTableValuedFunction(out, table, javaMethodName);
      this.printConvenienceMethodTableValuedFunctionAsField(out, table, false, javaMethodName);
      this.printConvenienceMethodTableValuedFunctionAsField(out, table, true, javaMethodName);
   }

   protected void generatePackages(SchemaDefinition schema) {
   }

   protected void generatePackage(SchemaDefinition schema, PackageDefinition pkg) {
   }

   protected void generatePackageClassFooter(PackageDefinition pkg, JavaWriter out) {
   }

   protected void generatePackageClassJavadoc(PackageDefinition pkg, JavaWriter out) {
      this.printClassJavadoc(out, "Convenience access to all stored procedures and functions in " + pkg.getName());
   }

   protected void generateTableReferences(SchemaDefinition schema) {
      log.info("Generating table references");
      JavaWriter out = this.newJavaWriter(new File(this.getStrategy().getFile((Definition)schema).getParentFile(), "Tables.java"));
      this.printPackage(out, schema);
      this.printClassJavadoc(out, "Convenience access to all tables in " + schema.getOutputName());
      this.printClassAnnotations(out, schema);
      if (this.scala) {
         out.println("object Tables {");
      } else {
         out.println("public class Tables {");
      }

      Iterator var3 = this.database.getTables(schema).iterator();

      while(var3.hasNext()) {
         TableDefinition table = (TableDefinition)var3.next();
         String className = out.ref(this.getStrategy().getFullJavaClassName(table));
         String id = this.getStrategy().getJavaIdentifier(table);
         String fullId = this.getStrategy().getFullJavaIdentifier(table);
         String comment = !StringUtils.isBlank(table.getComment()) ? this.escapeEntities(table.getComment()) : "The table <code>" + table.getQualifiedOutputName() + "</code>.";
         if (!this.scala || !table.isTableValuedFunction() || !table.getParameters().isEmpty()) {
            ((JavaWriter)out.tab(1)).javadoc(comment);
            if (this.scala) {
               ((JavaWriter)out.tab(1)).println("val %s = %s", new Object[]{id, fullId});
            } else {
               ((JavaWriter)out.tab(1)).println("public static final %s %s = %s;", new Object[]{className, id, fullId});
            }
         }

         if (table.isTableValuedFunction()) {
            this.printTableValuedFunction(out, table, this.getStrategy().getJavaIdentifier(table));
         }
      }

      out.println("}");
      this.closeJavaWriter(out);
      this.watch.splitInfo("Table refs generated");
   }

   protected void generateDaos(SchemaDefinition schema) {
      log.info("Generating DAOs");
      Iterator var2 = this.database.getTables(schema).iterator();

      while(var2.hasNext()) {
         TableDefinition table = (TableDefinition)var2.next();

         try {
            this.generateDao(table);
         } catch (Exception var5) {
            log.error("Error while generating table DAO " + table, (Throwable)var5);
         }
      }

      this.watch.splitInfo("Table DAOs generated");
   }

   protected void generateDao(TableDefinition table) {
      JavaWriter out = this.newJavaWriter(this.getStrategy().getFile(table, GeneratorStrategy.Mode.DAO));
      log.info("Generating DAO", (Object)out.file().getName());
      this.generateDao(table, out);
      this.closeJavaWriter(out);
   }

   protected void generateDao(TableDefinition table, JavaWriter out) {
      UniqueKeyDefinition key = table.getPrimaryKey();
      if (key == null) {
         log.info("Skipping DAO generation", (Object)out.file().getName());
      } else {
         String className = this.getStrategy().getJavaClassName(table, GeneratorStrategy.Mode.DAO);
         List<String> interfaces = out.ref(this.getStrategy().getJavaClassImplements(table, GeneratorStrategy.Mode.DAO));
         String tableRecord = out.ref(this.getStrategy().getFullJavaClassName(table, GeneratorStrategy.Mode.RECORD));
         String daoImpl = out.ref(DAOImpl.class);
         String tableIdentifier = out.ref(this.getStrategy().getFullJavaIdentifier(table), 2);
         String tType = this.scala ? "Unit" : "Void";
         String pType = out.ref(this.getStrategy().getFullJavaClassName(table, GeneratorStrategy.Mode.POJO));
         List<ColumnDefinition> keyColumns = key.getKeyColumns();
         String params;
         String separator;
         Iterator var14;
         ColumnDefinition column;
         if (keyColumns.size() == 1) {
            tType = this.getJavaType(((ColumnDefinition)keyColumns.get(0)).getType());
         } else if (keyColumns.size() <= 22) {
            params = "";
            separator = "";

            for(var14 = keyColumns.iterator(); var14.hasNext(); separator = ", ") {
               column = (ColumnDefinition)var14.next();
               params = params + separator + out.ref(this.getJavaType(column.getType()));
            }

            if (this.scala) {
               tType = Record.class.getName() + keyColumns.size() + "[" + params + "]";
            } else {
               tType = Record.class.getName() + keyColumns.size() + "<" + params + ">";
            }
         } else {
            tType = Record.class.getName();
         }

         tType = out.ref(tType);
         this.printPackage(out, table, GeneratorStrategy.Mode.DAO);
         this.generateDaoClassJavadoc(table, out);
         this.printClassAnnotations(out, table.getSchema());
         if (this.generateSpringAnnotations()) {
            out.println("@%s", new Object[]{out.ref("org.springframework.stereotype.Repository")});
         }

         if (this.scala) {
            out.println("class %s(configuration : %s) extends %s[%s, %s, %s](%s, classOf[%s], configuration)[[before= with ][separator= with ][%s]] {", new Object[]{className, Configuration.class, daoImpl, tableRecord, pType, tType, tableIdentifier, pType, interfaces});
         } else {
            out.println("public class %s extends %s<%s, %s, %s>[[before= implements ][%s]] {", new Object[]{className, daoImpl, tableRecord, pType, tType, interfaces});
         }

         ((JavaWriter)out.tab(1)).javadoc("Create a new %s without any configuration", className);
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("def this() = {");
            ((JavaWriter)out.tab(2)).println("this(null)");
            ((JavaWriter)out.tab(1)).println("}");
         } else {
            ((JavaWriter)out.tab(1)).println("public %s() {", new Object[]{className});
            ((JavaWriter)out.tab(2)).println("super(%s, %s.class);", new Object[]{tableIdentifier, pType});
            ((JavaWriter)out.tab(1)).println("}");
         }

         if (!this.scala) {
            ((JavaWriter)out.tab(1)).javadoc("Create a new %s with an attached configuration", className);
            if (this.generateSpringAnnotations()) {
               ((JavaWriter)out.tab(1)).println("@%s", new Object[]{out.ref("org.springframework.beans.factory.annotation.Autowired")});
            }

            ((JavaWriter)out.tab(1)).println("public %s(%s configuration) {", new Object[]{className, Configuration.class});
            ((JavaWriter)out.tab(2)).println("super(%s, %s.class, configuration);", new Object[]{tableIdentifier, pType});
            ((JavaWriter)out.tab(1)).println("}");
         }

         if (this.scala) {
            out.println();
            ((JavaWriter)out.tab(1)).println("override protected def getId(o : %s) : %s = {", new Object[]{pType, tType});
         } else {
            ((JavaWriter)out.tab(1)).overrideInherit();
            ((JavaWriter)out.tab(1)).println("protected %s getId(%s object) {", new Object[]{tType, pType});
         }

         if (keyColumns.size() == 1) {
            if (this.scala) {
               ((JavaWriter)out.tab(2)).println("o.%s", new Object[]{this.getStrategy().getJavaGetterName((Definition)keyColumns.get(0), GeneratorStrategy.Mode.POJO)});
            } else {
               ((JavaWriter)out.tab(2)).println("return object.%s();", new Object[]{this.getStrategy().getJavaGetterName((Definition)keyColumns.get(0), GeneratorStrategy.Mode.POJO)});
            }
         } else {
            params = "";
            separator = "";

            for(var14 = keyColumns.iterator(); var14.hasNext(); separator = ", ") {
               column = (ColumnDefinition)var14.next();
               if (this.scala) {
                  params = params + separator + "o." + this.getStrategy().getJavaGetterName(column, GeneratorStrategy.Mode.POJO);
               } else {
                  params = params + separator + "object." + this.getStrategy().getJavaGetterName(column, GeneratorStrategy.Mode.POJO) + "()";
               }
            }

            if (this.scala) {
               ((JavaWriter)out.tab(2)).println("compositeKeyRecord(%s)", new Object[]{params});
            } else {
               ((JavaWriter)out.tab(2)).println("return compositeKeyRecord(%s);", new Object[]{params});
            }
         }

         ((JavaWriter)out.tab(1)).println("}");
         Iterator var20 = table.getColumns().iterator();

         while(true) {
            while(var20.hasNext()) {
               ColumnDefinition column = (ColumnDefinition)var20.next();
               String colName = column.getOutputName();
               String colClass = this.getStrategy().getJavaClassName(column);
               String colType = out.ref(this.getJavaType(column.getType()));
               String colIdentifier = out.ref(this.getStrategy().getFullJavaIdentifier(column), this.colRefSegments(column));
               ((JavaWriter)out.tab(1)).javadoc("Fetch records that have <code>%s IN (values)</code>", colName);
               if (this.scala) {
                  ((JavaWriter)out.tab(1)).println("def fetchBy%s(values : %s*) : %s[%s] = {", new Object[]{colClass, colType, List.class, pType});
                  ((JavaWriter)out.tab(2)).println("fetch(%s, values:_*)", new Object[]{colIdentifier});
                  ((JavaWriter)out.tab(1)).println("}");
               } else {
                  ((JavaWriter)out.tab(1)).println("public %s<%s> fetchBy%s(%s... values) {", new Object[]{List.class, pType, colClass, colType});
                  ((JavaWriter)out.tab(2)).println("return fetch(%s, values);", new Object[]{colIdentifier});
                  ((JavaWriter)out.tab(1)).println("}");
               }

               Iterator var18 = column.getUniqueKeys().iterator();

               while(var18.hasNext()) {
                  UniqueKeyDefinition uk = (UniqueKeyDefinition)var18.next();
                  if (uk.getKeyColumns().size() == 1 && ((ColumnDefinition)uk.getKeyColumns().get(0)).equals(column)) {
                     ((JavaWriter)out.tab(1)).javadoc("Fetch a unique record that has <code>%s = value</code>", colName);
                     if (this.scala) {
                        ((JavaWriter)out.tab(1)).println("def fetchOneBy%s(value : %s) : %s = {", new Object[]{colClass, colType, pType});
                        ((JavaWriter)out.tab(2)).println("fetchOne(%s, value)", new Object[]{colIdentifier});
                        ((JavaWriter)out.tab(1)).println("}");
                     } else {
                        ((JavaWriter)out.tab(1)).println("public %s fetchOneBy%s(%s value) {", new Object[]{pType, colClass, colType});
                        ((JavaWriter)out.tab(2)).println("return fetchOne(%s, value);", new Object[]{colIdentifier});
                        ((JavaWriter)out.tab(1)).println("}");
                     }
                     break;
                  }
               }
            }

            this.generateDaoClassFooter(table, out);
            out.println("}");
            return;
         }
      }
   }

   protected void generateDaoClassFooter(TableDefinition table, JavaWriter out) {
   }

   protected void generateDaoClassJavadoc(TableDefinition table, JavaWriter out) {
      this.printClassJavadoc(out, (Definition)table);
   }

   protected void generatePojos(SchemaDefinition schema) {
      log.info("Generating table POJOs");
      Iterator var2 = this.database.getTables(schema).iterator();

      while(var2.hasNext()) {
         TableDefinition table = (TableDefinition)var2.next();

         try {
            this.generatePojo(table);
         } catch (Exception var5) {
            log.error("Error while generating table POJO " + table, (Throwable)var5);
         }
      }

      this.watch.splitInfo("Table POJOs generated");
   }

   protected void generatePojo(TableDefinition table) {
      JavaWriter out = this.newJavaWriter(this.getStrategy().getFile(table, GeneratorStrategy.Mode.POJO));
      log.info("Generating POJO", (Object)out.file().getName());
      this.generatePojo(table, out);
      this.closeJavaWriter(out);
   }

   protected void generateUDTPojo(UDTDefinition udt) {
      JavaWriter out = this.newJavaWriter(this.getStrategy().getFile(udt, GeneratorStrategy.Mode.POJO));
      log.info("Generating POJO", (Object)out.file().getName());
      this.generatePojo0(udt, out);
      this.closeJavaWriter(out);
   }

   protected void generatePojo(TableDefinition table, JavaWriter out) {
      this.generatePojo0(table, out);
   }

   protected void generateUDTPojo(UDTDefinition udt, JavaWriter out) {
      this.generatePojo0(udt, out);
   }

   private final void generatePojo0(Definition tableOrUDT, JavaWriter out) {
      String className = this.getStrategy().getJavaClassName(tableOrUDT, GeneratorStrategy.Mode.POJO);
      String superName = out.ref(this.getStrategy().getJavaClassExtends(tableOrUDT, GeneratorStrategy.Mode.POJO));
      List<String> interfaces = out.ref(this.getStrategy().getJavaClassImplements(tableOrUDT, GeneratorStrategy.Mode.POJO));
      List<String> superTypes = list(superName, interfaces);
      if (this.generateInterfaces()) {
         interfaces.add(out.ref(this.getStrategy().getFullJavaClassName(tableOrUDT, GeneratorStrategy.Mode.INTERFACE)));
      }

      this.printPackage(out, tableOrUDT, GeneratorStrategy.Mode.POJO);
      if (tableOrUDT instanceof TableDefinition) {
         this.generatePojoClassJavadoc((TableDefinition)tableOrUDT, out);
      } else {
         this.generateUDTPojoClassJavadoc((UDTDefinition)tableOrUDT, out);
      }

      this.printClassAnnotations(out, tableOrUDT.getSchema());
      if (tableOrUDT instanceof TableDefinition) {
         this.printTableJPAAnnotation(out, (TableDefinition)tableOrUDT);
      }

      int maxLength = 0;

      Iterator var8;
      TypedElementDefinition column;
      for(var8 = this.getTypedElements(tableOrUDT).iterator(); var8.hasNext(); maxLength = Math.max(maxLength, out.ref(this.getJavaType(column.getType(), GeneratorStrategy.Mode.POJO)).length())) {
         column = (TypedElementDefinition)var8.next();
      }

      TypedElementDefinition column;
      String separator1;
      Iterator var13;
      if (this.scala) {
         out.println("%sclass %s(", new Object[]{this.generateImmutablePojos() ? "case " : "", className});
         separator1 = "  ";

         for(var13 = this.getTypedElements(tableOrUDT).iterator(); var13.hasNext(); separator1 = ", ") {
            column = (TypedElementDefinition)var13.next();
            ((JavaWriter)out.tab(1)).println("%s%s%s : %s", new Object[]{separator1, this.generateImmutablePojos() ? "" : "private var ", this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO), StringUtils.rightPad(out.ref(this.getJavaType(column.getType(), GeneratorStrategy.Mode.POJO)), maxLength)});
         }

         out.println(")[[before= extends ][%s]][[before= with ][separator= with ][%s]] {", new Object[]{first(superTypes), remaining(superTypes)});
      } else {
         out.println("public class %s[[before= extends ][%s]][[before= implements ][%s]] {", new Object[]{className, list(superName), interfaces});
         out.printSerial();
         out.println();
         var8 = this.getTypedElements(tableOrUDT).iterator();

         while(var8.hasNext()) {
            column = (TypedElementDefinition)var8.next();
            ((JavaWriter)out.tab(1)).println("private %s%s %s;", new Object[]{this.generateImmutablePojos() ? "final " : "", StringUtils.rightPad(out.ref(this.getJavaType(column.getType(), GeneratorStrategy.Mode.POJO)), maxLength), this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO)});
         }
      }

      if (!this.generateImmutablePojos()) {
         out.println();
         if (this.scala) {
            int size = this.getTypedElements(tableOrUDT).size();
            if (size > 0) {
               List<String> nulls = new ArrayList();
               Iterator var16 = this.getTypedElements(tableOrUDT).iterator();

               while(var16.hasNext()) {
                  TypedElementDefinition<?> column = (TypedElementDefinition)var16.next();
                  if (size == 1) {
                     nulls.add("null : " + out.ref(this.getJavaType(column.getType(), GeneratorStrategy.Mode.POJO)));
                  } else {
                     nulls.add("null");
                  }
               }

               ((JavaWriter)out.tab(1)).println("def this() = {", new Object[]{className});
               ((JavaWriter)out.tab(2)).println("this([[%s]])", new Object[]{nulls});
               ((JavaWriter)out.tab(1)).println("}");
            }
         } else {
            ((JavaWriter)out.tab(1)).println("public %s() {}", new Object[]{className});
         }
      }

      out.println();
      if (this.scala) {
         ((JavaWriter)out.tab(1)).println("def this (value : %s) = {", new Object[]{className, className});
         ((JavaWriter)out.tab(2)).println("this(");
         separator1 = "  ";

         for(var13 = this.getTypedElements(tableOrUDT).iterator(); var13.hasNext(); separator1 = ", ") {
            column = (TypedElementDefinition)var13.next();
            ((JavaWriter)out.tab(3)).println("%svalue.%s", new Object[]{separator1, this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO), this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO)});
         }

         ((JavaWriter)out.tab(2)).println(")");
         ((JavaWriter)out.tab(1)).println("}");
      } else {
         ((JavaWriter)out.tab(1)).println("public %s(%s value) {", new Object[]{className, className});
         var8 = this.getTypedElements(tableOrUDT).iterator();

         while(var8.hasNext()) {
            column = (TypedElementDefinition)var8.next();
            ((JavaWriter)out.tab(2)).println("this.%s = value.%s;", new Object[]{this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO), this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO)});
         }

         ((JavaWriter)out.tab(1)).println("}");
      }

      if (!this.scala && this.getTypedElements(tableOrUDT).size() > 0 && this.getTypedElements(tableOrUDT).size() < 256) {
         out.println();
         ((JavaWriter)out.tab(1)).print("public %s(", new Object[]{className});
         separator1 = "";

         for(var13 = this.getTypedElements(tableOrUDT).iterator(); var13.hasNext(); separator1 = ",") {
            column = (TypedElementDefinition)var13.next();
            out.println(separator1);
            ((JavaWriter)out.tab(2)).print("%s %s", new Object[]{StringUtils.rightPad(out.ref(this.getJavaType(column.getType(), GeneratorStrategy.Mode.POJO)), maxLength), this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO)});
         }

         out.println();
         ((JavaWriter)out.tab(1)).println(") {");
         var13 = this.getTypedElements(tableOrUDT).iterator();

         while(var13.hasNext()) {
            column = (TypedElementDefinition)var13.next();
            String columnMember = this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO);
            ((JavaWriter)out.tab(2)).println("this.%s = %s;", new Object[]{columnMember, columnMember});
         }

         ((JavaWriter)out.tab(1)).println("}");
      }

      List<? extends TypedElementDefinition<?>> elements = this.getTypedElements(tableOrUDT);

      for(int i = 0; i < elements.size(); ++i) {
         column = (TypedElementDefinition)elements.get(i);
         if (tableOrUDT instanceof TableDefinition) {
            this.generatePojoGetter(column, i, out);
         } else {
            this.generateUDTPojoGetter(column, i, out);
         }

         if (!this.generateImmutablePojos()) {
            if (tableOrUDT instanceof TableDefinition) {
               this.generatePojoSetter(column, i, out);
            } else {
               this.generateUDTPojoSetter(column, i, out);
            }
         }
      }

      if (this.generatePojosEqualsAndHashCode()) {
         this.generatePojoEqualsAndHashCode(tableOrUDT, out);
      }

      if (this.generatePojosToString()) {
         this.generatePojoToString(tableOrUDT, out);
      }

      if (this.generateInterfaces() && !this.generateImmutablePojos()) {
         this.printFromAndInto(out, tableOrUDT);
      }

      if (tableOrUDT instanceof TableDefinition) {
         this.generatePojoClassFooter((TableDefinition)tableOrUDT, out);
      } else {
         this.generateUDTPojoClassFooter((UDTDefinition)tableOrUDT, out);
      }

      out.println("}");
      this.closeJavaWriter(out);
   }

   protected void generatePojoGetter(TypedElementDefinition<?> column, int index, JavaWriter out) {
      this.generatePojoGetter0(column, index, out);
   }

   protected void generateUDTPojoGetter(TypedElementDefinition<?> column, int index, JavaWriter out) {
      this.generatePojoGetter0(column, index, out);
   }

   private final void generatePojoGetter0(TypedElementDefinition<?> column, int index, JavaWriter out) {
      String columnType = out.ref(this.getJavaType(column.getType(), GeneratorStrategy.Mode.POJO));
      String columnGetter = this.getStrategy().getJavaGetterName(column, GeneratorStrategy.Mode.POJO);
      String columnMember = this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO);
      out.println();
      if (column instanceof ColumnDefinition) {
         this.printColumnJPAAnnotation(out, (ColumnDefinition)column);
      }

      this.printValidationAnnotation(out, column);
      if (this.scala) {
         ((JavaWriter)out.tab(1)).println("def %s : %s = {", new Object[]{columnGetter, columnType});
         ((JavaWriter)out.tab(2)).println("this.%s", new Object[]{columnMember});
         ((JavaWriter)out.tab(1)).println("}");
      } else {
         ((JavaWriter)out.tab(1)).overrideIf(this.generateInterfaces());
         ((JavaWriter)out.tab(1)).println("public %s %s() {", new Object[]{columnType, columnGetter});
         ((JavaWriter)out.tab(2)).println("return this.%s;", new Object[]{columnMember});
         ((JavaWriter)out.tab(1)).println("}");
      }

   }

   protected void generatePojoSetter(TypedElementDefinition<?> column, int index, JavaWriter out) {
      this.generatePojoSetter0(column, index, out);
   }

   protected void generateUDTPojoSetter(TypedElementDefinition<?> column, int index, JavaWriter out) {
      this.generatePojoSetter0(column, index, out);
   }

   private final void generatePojoSetter0(TypedElementDefinition<?> column, int index, JavaWriter out) {
      String className = this.getStrategy().getJavaClassName(column.getContainer(), GeneratorStrategy.Mode.POJO);
      String columnType = out.ref(this.getJavaType(column.getType(), GeneratorStrategy.Mode.POJO));
      String columnSetterReturnType = this.fluentSetters() ? className : (this.scala ? "Unit" : "void");
      String columnSetter = this.getStrategy().getJavaSetterName(column, GeneratorStrategy.Mode.POJO);
      String columnMember = this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO);
      boolean isUDT = column.getType().isUDT();
      boolean isUDTArray = column.getType().isArray() && this.database.getArray(column.getType().getSchema(), column.getType().getQualifiedUserType()).getElementType().isUDT();
      if (!this.generateInterfaces() || !isUDTArray) {
         out.println();
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("def %s(%s : %s) : %s = {", new Object[]{columnSetter, columnMember, columnType, columnSetterReturnType});
            ((JavaWriter)out.tab(2)).println("this.%s = %s", new Object[]{columnMember, columnMember});
            if (this.fluentSetters()) {
               ((JavaWriter)out.tab(2)).println("this");
            }

            ((JavaWriter)out.tab(1)).println("}");
         } else {
            ((JavaWriter)out.tab(1)).overrideIf(this.generateInterfaces() && !this.generateImmutableInterfaces() && !isUDT);
            ((JavaWriter)out.tab(1)).println("public %s %s(%s %s) {", new Object[]{columnSetterReturnType, columnSetter, this.varargsIfArray(columnType), columnMember});
            ((JavaWriter)out.tab(2)).println("this.%s = %s;", new Object[]{columnMember, columnMember});
            if (this.fluentSetters()) {
               ((JavaWriter)out.tab(2)).println("return this;");
            }

            ((JavaWriter)out.tab(1)).println("}");
         }
      }

      if (this.generateInterfaces() && (isUDT || isUDTArray)) {
         String columnTypeInterface = out.ref(this.getJavaType(column.getType(), GeneratorStrategy.Mode.INTERFACE));
         out.println();
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("def %s(%s : %s) : %s = {", new Object[]{columnSetter, columnMember, columnTypeInterface, columnSetterReturnType});
            ((JavaWriter)out.tab(2)).println("if (%s == null)", new Object[]{columnMember});
            ((JavaWriter)out.tab(3)).println("this.%s = null", new Object[]{columnMember});
            ((JavaWriter)out.tab(2)).println("else");
            ((JavaWriter)out.tab(3)).println("this.%s = %s.into(new %s)", new Object[]{columnMember, columnMember, columnType});
            if (this.fluentSetters()) {
               ((JavaWriter)out.tab(2)).println("this");
            }

            ((JavaWriter)out.tab(1)).println("}");
         } else {
            ((JavaWriter)out.tab(1)).override();
            ((JavaWriter)out.tab(1)).println("public %s %s(%s %s) {", new Object[]{columnSetterReturnType, columnSetter, this.varargsIfArray(columnTypeInterface), columnMember});
            ((JavaWriter)out.tab(2)).println("if (%s == null)", new Object[]{columnMember});
            ((JavaWriter)out.tab(3)).println("this.%s = null;", new Object[]{columnMember});
            if (isUDT) {
               ((JavaWriter)out.tab(2)).println("else");
               ((JavaWriter)out.tab(3)).println("this.%s = %s.into(new %s());", new Object[]{columnMember, columnMember, columnType});
            } else if (isUDTArray) {
               ArrayDefinition array = this.database.getArray(column.getType().getSchema(), column.getType().getQualifiedUserType());
               String componentType = out.ref(this.getJavaType(array.getElementType(), GeneratorStrategy.Mode.POJO));
               String componentTypeInterface = out.ref(this.getJavaType(array.getElementType(), GeneratorStrategy.Mode.INTERFACE));
               ((JavaWriter)out.tab(2)).println("else {");
               ((JavaWriter)out.tab(3)).println("this.%s = new %s();", new Object[]{columnMember, ArrayList.class});
               out.println();
               ((JavaWriter)out.tab(3)).println("for (%s i : %s)", new Object[]{componentTypeInterface, columnMember});
               ((JavaWriter)out.tab(4)).println("this.%s.add(i.into(new %s()));", new Object[]{columnMember, componentType});
               ((JavaWriter)out.tab(2)).println("}");
            }

            if (this.fluentSetters()) {
               ((JavaWriter)out.tab(2)).println("return this;");
            }

            ((JavaWriter)out.tab(1)).println("}");
         }
      }

   }

   protected void generatePojoEqualsAndHashCode(Definition tableOrUDT, JavaWriter out) {
      String className = this.getStrategy().getJavaClassName(tableOrUDT, GeneratorStrategy.Mode.POJO);
      out.println();
      Iterator var4;
      TypedElementDefinition column;
      String columnMember;
      if (this.scala) {
         ((JavaWriter)out.tab(1)).println("override def equals(obj : Any) : scala.Boolean = {");
         ((JavaWriter)out.tab(2)).println("if (this == obj)");
         ((JavaWriter)out.tab(3)).println("return true");
         ((JavaWriter)out.tab(2)).println("if (obj == null)");
         ((JavaWriter)out.tab(3)).println("return false");
         ((JavaWriter)out.tab(2)).println("if (getClass() != obj.getClass())");
         ((JavaWriter)out.tab(3)).println("return false");
         ((JavaWriter)out.tab(2)).println("val other = obj.asInstanceOf[%s]", new Object[]{className});

         for(var4 = this.getTypedElements(tableOrUDT).iterator(); var4.hasNext(); ((JavaWriter)out.tab(3)).println("return false")) {
            column = (TypedElementDefinition)var4.next();
            columnMember = this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO);
            ((JavaWriter)out.tab(2)).println("if (%s == null) {", new Object[]{columnMember});
            ((JavaWriter)out.tab(3)).println("if (other.%s != null)", new Object[]{columnMember});
            ((JavaWriter)out.tab(4)).println("return false");
            ((JavaWriter)out.tab(2)).println("}");
            if (this.getJavaType(column.getType()).endsWith("[]")) {
               ((JavaWriter)out.tab(2)).println("else if (!%s.equals(%s, other.%s))", new Object[]{Arrays.class, columnMember, columnMember});
            } else {
               ((JavaWriter)out.tab(2)).println("else if (!%s.equals(other.%s))", new Object[]{columnMember, columnMember});
            }
         }

         ((JavaWriter)out.tab(2)).println("return true");
         ((JavaWriter)out.tab(1)).println("}");
      } else {
         ((JavaWriter)out.tab(1)).println("@Override");
         ((JavaWriter)out.tab(1)).println("public boolean equals(%s obj) {", new Object[]{Object.class});
         ((JavaWriter)out.tab(2)).println("if (this == obj)");
         ((JavaWriter)out.tab(3)).println("return true;");
         ((JavaWriter)out.tab(2)).println("if (obj == null)");
         ((JavaWriter)out.tab(3)).println("return false;");
         ((JavaWriter)out.tab(2)).println("if (getClass() != obj.getClass())");
         ((JavaWriter)out.tab(3)).println("return false;");
         ((JavaWriter)out.tab(2)).println("final %s other = (%s) obj;", new Object[]{className, className});

         for(var4 = this.getTypedElements(tableOrUDT).iterator(); var4.hasNext(); ((JavaWriter)out.tab(3)).println("return false;")) {
            column = (TypedElementDefinition)var4.next();
            columnMember = this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO);
            ((JavaWriter)out.tab(2)).println("if (%s == null) {", new Object[]{columnMember});
            ((JavaWriter)out.tab(3)).println("if (other.%s != null)", new Object[]{columnMember});
            ((JavaWriter)out.tab(4)).println("return false;");
            ((JavaWriter)out.tab(2)).println("}");
            if (this.getJavaType(column.getType()).endsWith("[]")) {
               ((JavaWriter)out.tab(2)).println("else if (!%s.equals(%s, other.%s))", new Object[]{Arrays.class, columnMember, columnMember});
            } else {
               ((JavaWriter)out.tab(2)).println("else if (!%s.equals(other.%s))", new Object[]{columnMember, columnMember});
            }
         }

         ((JavaWriter)out.tab(2)).println("return true;");
         ((JavaWriter)out.tab(1)).println("}");
      }

      out.println();
      if (this.scala) {
         ((JavaWriter)out.tab(1)).println("override def hashCode : Int = {");
         ((JavaWriter)out.tab(2)).println("val prime = 31");
         ((JavaWriter)out.tab(2)).println("var result = 1");
         var4 = this.getTypedElements(tableOrUDT).iterator();

         while(var4.hasNext()) {
            column = (TypedElementDefinition)var4.next();
            columnMember = this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO);
            if (this.getJavaType(column.getType()).endsWith("[]")) {
               ((JavaWriter)out.tab(2)).println("result = prime * result + (if (%s == null) 0 else %s.hashCode(%s))", new Object[]{columnMember, Arrays.class, columnMember});
            } else {
               ((JavaWriter)out.tab(2)).println("result = prime * result + (if (%s == null) 0 else %s.hashCode())", new Object[]{columnMember, columnMember});
            }
         }

         ((JavaWriter)out.tab(2)).println("return result");
         ((JavaWriter)out.tab(1)).println("}");
      } else {
         ((JavaWriter)out.tab(1)).println("@Override");
         ((JavaWriter)out.tab(1)).println("public int hashCode() {");
         ((JavaWriter)out.tab(2)).println("final int prime = 31;");
         ((JavaWriter)out.tab(2)).println("int result = 1;");
         var4 = this.getTypedElements(tableOrUDT).iterator();

         while(var4.hasNext()) {
            column = (TypedElementDefinition)var4.next();
            columnMember = this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO);
            if (this.getJavaType(column.getType()).endsWith("[]")) {
               ((JavaWriter)out.tab(2)).println("result = prime * result + ((%s == null) ? 0 : %s.hashCode(%s));", new Object[]{columnMember, Arrays.class, columnMember});
            } else {
               ((JavaWriter)out.tab(2)).println("result = prime * result + ((%s == null) ? 0 : %s.hashCode());", new Object[]{columnMember, columnMember});
            }
         }

         ((JavaWriter)out.tab(2)).println("return result;");
         ((JavaWriter)out.tab(1)).println("}");
      }

   }

   protected void generatePojoToString(Definition tableOrUDT, JavaWriter out) {
      String className = this.getStrategy().getJavaClassName(tableOrUDT, GeneratorStrategy.Mode.POJO);
      out.println();
      String separator;
      Iterator var5;
      TypedElementDefinition column;
      String columnMember;
      String columnType;
      if (this.scala) {
         ((JavaWriter)out.tab(1)).println("override def toString : String = {");
         ((JavaWriter)out.tab(2)).println("val sb = new %s(\"%s (\")", new Object[]{StringBuilder.class, className});
         ((JavaWriter)out.tab(2)).println();
         separator = "";

         for(var5 = this.getTypedElements(tableOrUDT).iterator(); var5.hasNext(); separator = ".append(\", \")") {
            column = (TypedElementDefinition)var5.next();
            columnMember = this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO);
            columnType = this.getJavaType(column.getType());
            if (columnType.equals("scala.Array[scala.Byte]")) {
               ((JavaWriter)out.tab(2)).println("sb%s.append(\"[binary...]\")", new Object[]{separator});
            } else {
               ((JavaWriter)out.tab(2)).println("sb%s.append(%s)", new Object[]{separator, columnMember});
            }
         }

         ((JavaWriter)out.tab(2)).println();
         ((JavaWriter)out.tab(2)).println("sb.append(\")\");");
         ((JavaWriter)out.tab(2)).println("return sb.toString");
         ((JavaWriter)out.tab(1)).println("}");
      } else {
         ((JavaWriter)out.tab(1)).println("@Override");
         ((JavaWriter)out.tab(1)).println("public String toString() {");
         ((JavaWriter)out.tab(2)).println("%s sb = new %s(\"%s (\");", new Object[]{StringBuilder.class, StringBuilder.class, className});
         ((JavaWriter)out.tab(2)).println();
         separator = "";

         for(var5 = this.getTypedElements(tableOrUDT).iterator(); var5.hasNext(); separator = ".append(\", \")") {
            column = (TypedElementDefinition)var5.next();
            columnMember = this.getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO);
            columnType = this.getJavaType(column.getType());
            boolean array = columnType.endsWith("[]");
            if (array && columnType.equals("byte[]")) {
               ((JavaWriter)out.tab(2)).println("sb%s.append(\"[binary...]\");", new Object[]{separator});
            } else if (array) {
               ((JavaWriter)out.tab(2)).println("sb%s.append(%s.toString(%s));", new Object[]{separator, Arrays.class, columnMember});
            } else {
               ((JavaWriter)out.tab(2)).println("sb%s.append(%s);", new Object[]{separator, columnMember});
            }
         }

         ((JavaWriter)out.tab(2)).println();
         ((JavaWriter)out.tab(2)).println("sb.append(\")\");");
         ((JavaWriter)out.tab(2)).println("return sb.toString();");
         ((JavaWriter)out.tab(1)).println("}");
      }

   }

   private List<? extends TypedElementDefinition<? extends Definition>> getTypedElements(Definition definition) {
      if (definition instanceof TableDefinition) {
         return ((TableDefinition)definition).getColumns();
      } else if (definition instanceof UDTDefinition) {
         return ((UDTDefinition)definition).getAttributes();
      } else if (definition instanceof RoutineDefinition) {
         return ((RoutineDefinition)definition).getAllParameters();
      } else {
         throw new IllegalArgumentException("Unsupported type : " + definition);
      }
   }

   protected void generatePojoClassFooter(TableDefinition table, JavaWriter out) {
   }

   protected void generatePojoClassJavadoc(TableDefinition table, JavaWriter out) {
      this.printClassJavadoc(out, (Definition)table);
   }

   protected void generateTables(SchemaDefinition schema) {
      log.info("Generating tables");
      Iterator var2 = this.database.getTables(schema).iterator();

      while(var2.hasNext()) {
         TableDefinition table = (TableDefinition)var2.next();

         try {
            this.generateTable(schema, table);
         } catch (Exception var5) {
            log.error("Error while generating table " + table, (Throwable)var5);
         }
      }

      this.watch.splitInfo("Tables generated");
   }

   protected void generateTable(SchemaDefinition schema, TableDefinition table) {
      JavaWriter out = this.newJavaWriter(this.getStrategy().getFile((Definition)table));
      this.generateTable(table, out);
      this.closeJavaWriter(out);
   }

   protected void generateTable(TableDefinition table, JavaWriter out) {
      SchemaDefinition schema = table.getSchema();
      UniqueKeyDefinition primaryKey = table.getPrimaryKey();
      boolean updatable = this.generateRelations() && primaryKey != null;
      String className = this.getStrategy().getJavaClassName(table);
      String tableId = this.scala ? out.ref(this.getStrategy().getFullJavaIdentifier(table), 2) : this.getStrategy().getJavaIdentifier(table);
      String recordType = out.ref(this.getStrategy().getFullJavaClassName(table, GeneratorStrategy.Mode.RECORD));
      List<String> interfaces = out.ref(this.getStrategy().getJavaClassImplements(table, GeneratorStrategy.Mode.DEFAULT));
      String schemaId = out.ref(this.getStrategy().getFullJavaIdentifier(schema), 2);
      String comment = StringUtils.defaultString(table.getComment());
      log.info("Generating table", (Object)(out.file().getName() + " [input=" + table.getInputName() + ", output=" + table.getOutputName() + ", pk=" + (primaryKey != null ? primaryKey.getName() : "N/A") + "]"));
      this.printPackage(out, table);
      if (this.scala) {
         out.println("object %s {", new Object[]{className});
         this.printSingletonInstance(out, table);
         out.println("}");
         out.println();
      }

      this.generateTableClassJavadoc(table, out);
      this.printClassAnnotations(out, schema);
      if (this.scala) {
         out.println("class %s(alias : String, aliased : %s[%s], parameters : %s[ %s[_] ]) extends %s[%s](alias, %s, aliased, parameters, \"%s\")[[before= with ][separator= with ][%s]] {", new Object[]{className, Table.class, recordType, out.ref("scala.Array"), Field.class, TableImpl.class, recordType, schemaId, this.escapeString(comment), interfaces});
      } else {
         out.println("public class %s extends %s<%s>[[before= implements ][%s]] {", new Object[]{className, TableImpl.class, recordType, interfaces});
         out.printSerial();
         this.printSingletonInstance(out, table);
      }

      this.printRecordTypeMethod(out, table);
      Iterator var12 = table.getColumns().iterator();

      String identityFullId;
      String pattern;
      String separator;
      String columnId;
      while(var12.hasNext()) {
         ColumnDefinition column = (ColumnDefinition)var12.next();
         identityFullId = out.ref(this.getJavaType(column.getType()));
         pattern = this.getJavaTypeReference(column.getDatabase(), column.getType());
         separator = out.ref(this.getStrategy().getJavaIdentifier(column), this.colRefSegments(column));
         String columnName = column.getName();
         String columnComment = StringUtils.defaultString(column.getComment());
         List<String> converters = out.ref(list(column.getType().getConverter(), column.getType().getBinding()));
         ((JavaWriter)out.tab(1)).javadoc("The column <code>%s</code>.%s", column.getQualifiedOutputName(), StringUtils.defaultIfBlank(" " + this.escapeEntities(columnComment), ""));
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("val %s : %s[%s, %s] = createField(\"%s\", %s, \"%s\"[[before=, ][new %s()]])", new Object[]{separator, TableField.class, recordType, identityFullId, columnName, pattern, this.escapeString(columnComment), converters});
         } else {
            columnId = this.generateInstanceFields() ? "" : "static ";
            String tableRef = this.generateInstanceFields() ? "this" : out.ref(this.getStrategy().getJavaIdentifier(table), 2);
            ((JavaWriter)out.tab(1)).println("public %sfinal %s<%s, %s> %s = createField(\"%s\", %s, %s, \"%s\"[[before=, ][new %s()]]);", new Object[]{columnId, TableField.class, recordType, identityFullId, separator, columnName, pattern, tableRef, this.escapeString(columnComment), converters});
         }
      }

      if (this.scala) {
         ((JavaWriter)out.tab(1)).javadoc("Create a <code>%s</code> table reference", table.getQualifiedOutputName());
         ((JavaWriter)out.tab(1)).println("def this() = {");
         ((JavaWriter)out.tab(2)).println("this(\"%s\", null, null)", new Object[]{table.getOutputName()});
         ((JavaWriter)out.tab(1)).println("}");
      } else {
         if (this.generateInstanceFields()) {
            ((JavaWriter)out.tab(1)).javadoc("Create a <code>%s</code> table reference", table.getQualifiedOutputName());
            ((JavaWriter)out.tab(1)).println("public %s() {", new Object[]{className});
         } else {
            ((JavaWriter)out.tab(1)).javadoc("No further instances allowed");
            ((JavaWriter)out.tab(1)).println("private %s() {", new Object[]{className});
         }

         ((JavaWriter)out.tab(2)).println("this(\"%s\", null);", new Object[]{table.getOutputName()});
         ((JavaWriter)out.tab(1)).println("}");
      }

      if (this.scala) {
         ((JavaWriter)out.tab(1)).javadoc("Create an aliased <code>%s</code> table reference", table.getQualifiedOutputName());
         ((JavaWriter)out.tab(1)).println("def this(alias : %s) = {", new Object[]{String.class});
         ((JavaWriter)out.tab(2)).println("this(alias, %s, null)", new Object[]{tableId});
         ((JavaWriter)out.tab(1)).println("}");
      } else if (this.generateInstanceFields()) {
         ((JavaWriter)out.tab(1)).javadoc("Create an aliased <code>%s</code> table reference", table.getQualifiedOutputName());
         ((JavaWriter)out.tab(1)).println("public %s(%s alias) {", new Object[]{className, String.class});
         ((JavaWriter)out.tab(2)).println("this(alias, %s);", new Object[]{tableId});
         ((JavaWriter)out.tab(1)).println("}");
      }

      out.println();
      if (this.scala) {
         ((JavaWriter)out.tab(1)).println("private def this(alias : %s, aliased : %s[%s]) = {", new Object[]{String.class, Table.class, recordType});
         if (table.isTableValuedFunction()) {
            ((JavaWriter)out.tab(2)).println("this(alias, aliased, new %s[ %s[_] ](%s))", new Object[]{out.ref("scala.Array"), Field.class, table.getParameters().size()});
         } else {
            ((JavaWriter)out.tab(2)).println("this(alias, aliased, null)");
         }

         ((JavaWriter)out.tab(1)).println("}");
      } else {
         ((JavaWriter)out.tab(1)).println("private %s(%s alias, %s<%s> aliased) {", new Object[]{className, String.class, Table.class, recordType});
         if (table.isTableValuedFunction()) {
            ((JavaWriter)out.tab(2)).println("this(alias, aliased, new %s[%s]);", new Object[]{Field.class, table.getParameters().size()});
         } else {
            ((JavaWriter)out.tab(2)).println("this(alias, aliased, null);");
         }

         ((JavaWriter)out.tab(1)).println("}");
         out.println();
         ((JavaWriter)out.tab(1)).println("private %s(%s alias, %s<%s> aliased, %s<?>[] parameters) {", new Object[]{className, String.class, Table.class, recordType, Field.class});
         ((JavaWriter)out.tab(2)).println("super(alias, null, aliased, parameters, \"%s\");", new Object[]{this.escapeString(comment)});
         ((JavaWriter)out.tab(1)).println("}");
      }

      if (this.scala) {
         out.println();
         ((JavaWriter)out.tab(1)).println("override def getSchema : %s = %s", new Object[]{Schema.class, schemaId});
      } else {
         ((JavaWriter)out.tab(1)).overrideInherit();
         ((JavaWriter)out.tab(1)).println("public %s getSchema() {", new Object[]{Schema.class});
         ((JavaWriter)out.tab(2)).println("return %s;", new Object[]{schemaId});
         ((JavaWriter)out.tab(1)).println("}");
      }

      if (this.generateRelations()) {
         IdentityDefinition identity = table.getIdentity();
         String keyFullId;
         if (identity != null) {
            keyFullId = out.ref(this.getJavaType(identity.getColumn().getType()));
            identityFullId = out.ref(this.getStrategy().getFullJavaIdentifier(identity), 2);
            if (this.scala) {
               out.println();
               ((JavaWriter)out.tab(1)).println("override def getIdentity : %s[%s, %s] = {", new Object[]{Identity.class, recordType, keyFullId});
               ((JavaWriter)out.tab(2)).println("%s", new Object[]{identityFullId});
               ((JavaWriter)out.tab(1)).println("}");
            } else {
               ((JavaWriter)out.tab(1)).overrideInherit();
               ((JavaWriter)out.tab(1)).println("public %s<%s, %s> getIdentity() {", new Object[]{Identity.class, recordType, keyFullId});
               ((JavaWriter)out.tab(2)).println("return %s;", new Object[]{identityFullId});
               ((JavaWriter)out.tab(1)).println("}");
            }
         }

         if (primaryKey != null) {
            keyFullId = out.ref(this.getStrategy().getFullJavaIdentifier(primaryKey), 2);
            if (this.scala) {
               out.println();
               ((JavaWriter)out.tab(1)).println("override def getPrimaryKey : %s[%s] = {", new Object[]{UniqueKey.class, recordType});
               ((JavaWriter)out.tab(2)).println("%s", new Object[]{keyFullId});
               ((JavaWriter)out.tab(1)).println("}");
            } else {
               ((JavaWriter)out.tab(1)).overrideInherit();
               ((JavaWriter)out.tab(1)).println("public %s<%s> getPrimaryKey() {", new Object[]{UniqueKey.class, recordType});
               ((JavaWriter)out.tab(2)).println("return %s;", new Object[]{keyFullId});
               ((JavaWriter)out.tab(1)).println("}");
            }
         }

         List<UniqueKeyDefinition> uniqueKeys = table.getUniqueKeys();
         List foreignKeys;
         if (uniqueKeys.size() > 0) {
            foreignKeys = out.ref(this.getStrategy().getFullJavaIdentifiers((Collection)uniqueKeys), 2);
            if (this.scala) {
               out.println();
               ((JavaWriter)out.tab(1)).println("override def getKeys : %s[ %s[%s] ] = {", new Object[]{List.class, UniqueKey.class, recordType});
               ((JavaWriter)out.tab(2)).println("return %s.asList[ %s[%s] ]([[%s]])", new Object[]{Arrays.class, UniqueKey.class, recordType, foreignKeys});
               ((JavaWriter)out.tab(1)).println("}");
            } else {
               ((JavaWriter)out.tab(1)).overrideInherit();
               ((JavaWriter)out.tab(1)).println("public %s<%s<%s>> getKeys() {", new Object[]{List.class, UniqueKey.class, recordType});
               ((JavaWriter)out.tab(2)).println("return %s.<%s<%s>>asList([[%s]]);", new Object[]{Arrays.class, UniqueKey.class, recordType, foreignKeys});
               ((JavaWriter)out.tab(1)).println("}");
            }
         }

         foreignKeys = table.getForeignKeys();
         if (foreignKeys.size() > 0) {
            List<String> keyFullIds = out.ref(this.getStrategy().getFullJavaIdentifiers((Collection)foreignKeys), 2);
            if (this.scala) {
               out.println();
               ((JavaWriter)out.tab(1)).println("override def getReferences : %s[ %s[%s, _] ] = {", new Object[]{List.class, ForeignKey.class, recordType});
               ((JavaWriter)out.tab(2)).println("return %s.asList[ %s[%s, _] ]([[%s]])", new Object[]{Arrays.class, ForeignKey.class, recordType, keyFullIds});
               ((JavaWriter)out.tab(1)).println("}");
            } else {
               ((JavaWriter)out.tab(1)).overrideInherit();
               ((JavaWriter)out.tab(1)).println("public %s<%s<%s, ?>> getReferences() {", new Object[]{List.class, ForeignKey.class, recordType});
               ((JavaWriter)out.tab(2)).println("return %s.<%s<%s, ?>>asList([[%s]]);", new Object[]{Arrays.class, ForeignKey.class, recordType, keyFullIds});
               ((JavaWriter)out.tab(1)).println("}");
            }
         }
      }

      int var27;
      int var29;
      Iterator var33;
      if (updatable) {
         String[] var23 = this.database.getRecordVersionFields();
         var27 = var23.length;

         Pattern p;
         ColumnDefinition column;
         String columnType;
         label247:
         for(var29 = 0; var29 < var27; ++var29) {
            pattern = var23[var29];
            p = Pattern.compile(pattern, 4);
            var33 = table.getColumns().iterator();

            while(var33.hasNext()) {
               column = (ColumnDefinition)var33.next();
               if (p.matcher(column.getName()).matches() || p.matcher(column.getQualifiedName()).matches()) {
                  columnType = out.ref(this.getJavaType(column.getType()));
                  columnId = this.getStrategy().getJavaIdentifier(column);
                  if (this.scala) {
                     out.println();
                     ((JavaWriter)out.tab(1)).println("override def getRecordVersion : %s[%s, %s] = {", new Object[]{TableField.class, recordType, columnType});
                     ((JavaWriter)out.tab(2)).println("%s", new Object[]{columnId});
                     ((JavaWriter)out.tab(1)).println("}");
                  } else {
                     ((JavaWriter)out.tab(1)).overrideInherit();
                     ((JavaWriter)out.tab(1)).println("public %s<%s, %s> getRecordVersion() {", new Object[]{TableField.class, recordType, columnType});
                     ((JavaWriter)out.tab(2)).println("return %s;", new Object[]{columnId});
                     ((JavaWriter)out.tab(1)).println("}");
                  }
                  break label247;
               }
            }
         }

         var23 = this.database.getRecordTimestampFields();
         var27 = var23.length;

         label233:
         for(var29 = 0; var29 < var27; ++var29) {
            pattern = var23[var29];
            p = Pattern.compile(pattern, 4);
            var33 = table.getColumns().iterator();

            while(var33.hasNext()) {
               column = (ColumnDefinition)var33.next();
               if (p.matcher(column.getName()).matches() || p.matcher(column.getQualifiedName()).matches()) {
                  columnType = out.ref(this.getJavaType(column.getType()));
                  columnId = this.getStrategy().getJavaIdentifier(column);
                  if (this.scala) {
                     out.println();
                     ((JavaWriter)out.tab(1)).println("override def getRecordTimestamp : %s[%s, %s] = {", new Object[]{TableField.class, recordType, columnType});
                     ((JavaWriter)out.tab(2)).println("%s", new Object[]{columnId});
                     ((JavaWriter)out.tab(1)).println("}");
                  } else {
                     ((JavaWriter)out.tab(1)).overrideInherit();
                     ((JavaWriter)out.tab(1)).println("public %s<%s, %s> getRecordTimestamp() {", new Object[]{TableField.class, recordType, columnType});
                     ((JavaWriter)out.tab(2)).println("return %s;", new Object[]{columnId});
                     ((JavaWriter)out.tab(1)).println("}");
                  }
                  break label233;
               }
            }
         }
      }

      if (this.scala) {
         out.println();
         ((JavaWriter)out.tab(1)).println("override def as(alias : %s) : %s = {", new Object[]{String.class, className});
         if (table.isTableValuedFunction()) {
            ((JavaWriter)out.tab(2)).println("new %s(alias, this, parameters)", new Object[]{className});
         } else {
            ((JavaWriter)out.tab(2)).println("new %s(alias, this)", new Object[]{className});
         }

         ((JavaWriter)out.tab(1)).println("}");
      } else if (this.generateInstanceFields()) {
         ((JavaWriter)out.tab(1)).overrideInherit();
         ((JavaWriter)out.tab(1)).println("public %s as(%s alias) {", new Object[]{className, String.class});
         if (table.isTableValuedFunction()) {
            ((JavaWriter)out.tab(2)).println("return new %s(alias, this, parameters);", new Object[]{className});
         } else {
            ((JavaWriter)out.tab(2)).println("return new %s(alias, this);", new Object[]{className});
         }

         ((JavaWriter)out.tab(1)).println("}");
      }

      if (this.scala) {
         ((JavaWriter)out.tab(1)).javadoc("Rename this table");
         ((JavaWriter)out.tab(1)).println("override def rename(name : %s) : %s = {", new Object[]{String.class, className});
         if (table.isTableValuedFunction()) {
            ((JavaWriter)out.tab(2)).println("new %s(name, null, parameters)", new Object[]{className});
         } else {
            ((JavaWriter)out.tab(2)).println("new %s(name, null)", new Object[]{className});
         }

         ((JavaWriter)out.tab(1)).println("}");
      } else if (this.generateInstanceFields()) {
         ((JavaWriter)out.tab(1)).javadoc("Rename this table");
         ((JavaWriter)out.tab(1)).override();
         ((JavaWriter)out.tab(1)).println("public %s rename(%s name) {", new Object[]{className, String.class});
         if (table.isTableValuedFunction()) {
            ((JavaWriter)out.tab(2)).println("return new %s(name, null, parameters);", new Object[]{className});
         } else {
            ((JavaWriter)out.tab(2)).println("return new %s(name, null);", new Object[]{className});
         }

         ((JavaWriter)out.tab(1)).println("}");
      }

      if (table.isTableValuedFunction()) {
         boolean[] var25 = new boolean[]{false, true};
         var27 = var25.length;

         for(var29 = 0; var29 < var27; ++var29) {
            boolean parametersAsField = var25[var29];
            if (parametersAsField && table.getParameters().size() == 0) {
               break;
            }

            ((JavaWriter)out.tab(1)).javadoc("Call this table-valued function");
            ParameterDefinition parameter;
            if (this.scala) {
               ((JavaWriter)out.tab(1)).print("def call(");
               this.printParameterDeclarations(out, table, parametersAsField);
               out.println(") : %s = {", new Object[]{className});
               ((JavaWriter)out.tab(2)).print("return new %s(getName(), null, %s(", new Object[]{className, out.ref("scala.Array")});
               separator = "";

               for(var33 = table.getParameters().iterator(); var33.hasNext(); separator = ", ") {
                  parameter = (ParameterDefinition)var33.next();
                  out.print(separator);
                  if (parametersAsField) {
                     out.print("%s", new Object[]{this.getStrategy().getJavaMemberName(parameter)});
                  } else {
                     out.print("%s.value(%s)", new Object[]{DSL.class, this.getStrategy().getJavaMemberName(parameter)});
                  }
               }

               out.println("));");
               ((JavaWriter)out.tab(1)).println("}");
            } else {
               ((JavaWriter)out.tab(1)).print("public %s call(", new Object[]{className});
               this.printParameterDeclarations(out, table, parametersAsField);
               out.println(") {");
               ((JavaWriter)out.tab(2)).print("return new %s(getName(), null, new %s[] { ", new Object[]{className, Field.class});
               separator = "";

               for(var33 = table.getParameters().iterator(); var33.hasNext(); separator = ", ") {
                  parameter = (ParameterDefinition)var33.next();
                  out.print(separator);
                  if (parametersAsField) {
                     out.print("%s", new Object[]{this.getStrategy().getJavaMemberName(parameter)});
                  } else {
                     out.print("%s.val(%s)", new Object[]{DSL.class, this.getStrategy().getJavaMemberName(parameter)});
                  }
               }

               out.println(" });");
               ((JavaWriter)out.tab(1)).println("}");
            }
         }
      }

      this.generateTableClassFooter(table, out);
      out.println("}");
      this.closeJavaWriter(out);
   }

   private String escapeString(String comment) {
      return comment == null ? null : comment.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
   }

   protected void generateTableClassFooter(TableDefinition table, JavaWriter out) {
   }

   protected void generateTableClassJavadoc(TableDefinition table, JavaWriter out) {
      this.printClassJavadoc(out, (Definition)table);
   }

   protected void generateSequences(SchemaDefinition schema) {
      log.info("Generating sequences");
      JavaWriter out = this.newJavaWriter(new File(this.getStrategy().getFile((Definition)schema).getParentFile(), "Sequences.java"));
      this.printPackage(out, schema);
      this.printClassJavadoc(out, "Convenience access to all sequences in " + schema.getOutputName());
      this.printClassAnnotations(out, schema);
      if (this.scala) {
         out.println("object Sequences {");
      } else {
         out.println("public class Sequences {");
      }

      Iterator var3 = this.database.getSequences(schema).iterator();

      while(var3.hasNext()) {
         SequenceDefinition sequence = (SequenceDefinition)var3.next();
         String seqType = out.ref(this.getJavaType(sequence.getType()));
         String seqId = this.getStrategy().getJavaIdentifier(sequence);
         String seqName = sequence.getOutputName();
         String schemaId = out.ref(this.getStrategy().getFullJavaIdentifier(schema), 2);
         String typeRef = this.getJavaTypeReference(sequence.getDatabase(), sequence.getType());
         ((JavaWriter)out.tab(1)).javadoc("The sequence <code>%s</code>", sequence.getQualifiedOutputName());
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("val %s : %s[%s] = new %s[%s](\"%s\", %s, %s)", new Object[]{seqId, Sequence.class, seqType, SequenceImpl.class, seqType, seqName, schemaId, typeRef});
         } else {
            ((JavaWriter)out.tab(1)).println("public static final %s<%s> %s = new %s<%s>(\"%s\", %s, %s);", new Object[]{Sequence.class, seqType, seqId, SequenceImpl.class, seqType, seqName, schemaId, typeRef});
         }
      }

      out.println("}");
      this.closeJavaWriter(out);
      this.watch.splitInfo("Sequences generated");
   }

   protected void generateCatalog(CatalogDefinition catalog) {
      JavaWriter out = this.newJavaWriter(this.getStrategy().getFile((Definition)catalog));
      log.info("");
      log.info("Generating catalog", (Object)out.file().getName());
      log.info("==========================================================");
      this.generateCatalog(catalog, out);
      this.closeJavaWriter(out);
   }

   protected void generateCatalog(CatalogDefinition catalog, JavaWriter out) {
      String catalogName = catalog.getQualifiedOutputName();
      String catalogId = this.getStrategy().getJavaIdentifier(catalog);
      String className = this.getStrategy().getJavaClassName(catalog);
      List<String> interfaces = out.ref(this.getStrategy().getJavaClassImplements(catalog, GeneratorStrategy.Mode.DEFAULT));
      this.printPackage(out, catalog);
      if (this.scala) {
         out.println("object %s {", new Object[]{className});
         ((JavaWriter)out.tab(1)).javadoc("The reference instance of <code>%s</code>", catalogName);
         ((JavaWriter)out.tab(1)).println("val %s = new %s", new Object[]{catalogId, className});
         out.println("}");
         out.println();
      }

      this.generateCatalogClassJavadoc(catalog, out);
      this.printClassAnnotations(out, (SchemaDefinition)null, catalog);
      if (this.scala) {
         out.println("class %s extends %s(\"%s\")[[before= with ][separator= with ][%s]] {", new Object[]{className, CatalogImpl.class, catalog.getOutputName(), interfaces});
      } else {
         out.println("public class %s extends %s[[before= implements ][%s]] {", new Object[]{className, CatalogImpl.class, interfaces});
         out.printSerial();
         ((JavaWriter)out.tab(1)).javadoc("The reference instance of <code>%s</code>", catalogName);
         ((JavaWriter)out.tab(1)).println("public static final %s %s = new %s();", new Object[]{className, catalogId, className});
      }

      List<SchemaDefinition> schemas = new ArrayList();
      if (this.generateGlobalSchemaReferences()) {
         Iterator var8 = catalog.getSchemata().iterator();

         while(var8.hasNext()) {
            SchemaDefinition schema = (SchemaDefinition)var8.next();
            if (this.generateSchemaIfEmpty(schema)) {
               schemas.add(schema);
               String schemaClassName = out.ref(this.getStrategy().getFullJavaClassName(schema));
               String schemaId = this.getStrategy().getJavaIdentifier(schema);
               String schemaFullId = this.getStrategy().getFullJavaIdentifier(schema);
               String schemaComment = !StringUtils.isBlank(schema.getComment()) ? this.escapeEntities(schema.getComment()) : "The schema <code>" + schema.getQualifiedOutputName() + "</code>.";
               ((JavaWriter)out.tab(1)).javadoc(schemaComment);
               if (this.scala) {
                  ((JavaWriter)out.tab(1)).println("val %s = %s", new Object[]{schemaId, schemaFullId});
               } else {
                  ((JavaWriter)out.tab(1)).println("public final %s %s = %s;", new Object[]{schemaClassName, schemaId, schemaFullId});
               }
            }
         }
      }

      if (!this.scala) {
         ((JavaWriter)out.tab(1)).javadoc("No further instances allowed");
         ((JavaWriter)out.tab(1)).println("private %s() {", new Object[]{className});
         ((JavaWriter)out.tab(2)).println("super(\"%s\");", new Object[]{catalog.getOutputName()});
         ((JavaWriter)out.tab(1)).println("}");
      }

      this.printReferences(out, schemas, Schema.class, false);
      this.generateCatalogClassFooter(catalog, out);
      out.println("}");
   }

   protected void generateCatalogClassFooter(CatalogDefinition schema, JavaWriter out) {
   }

   protected void generateCatalogClassJavadoc(CatalogDefinition schema, JavaWriter out) {
      this.printClassJavadoc(out, (Definition)schema);
   }

   protected void generateSchema(SchemaDefinition schema) {
      JavaWriter out = this.newJavaWriter(this.getStrategy().getFile((Definition)schema));
      log.info("Generating schema", (Object)out.file().getName());
      log.info("----------------------------------------------------------");
      this.generateSchema(schema, out);
      this.closeJavaWriter(out);
   }

   protected void generateSchema(SchemaDefinition schema, JavaWriter out) {
      String catalogId = out.ref(this.getStrategy().getFullJavaIdentifier(schema.getCatalog()), 2);
      String schemaName = schema.getQualifiedOutputName();
      String schemaId = this.getStrategy().getJavaIdentifier(schema);
      String className = this.getStrategy().getJavaClassName(schema);
      List<String> interfaces = out.ref(this.getStrategy().getJavaClassImplements(schema, GeneratorStrategy.Mode.DEFAULT));
      this.printPackage(out, schema);
      if (this.scala) {
         out.println("object %s {", new Object[]{className});
         ((JavaWriter)out.tab(1)).javadoc("The reference instance of <code>%s</code>", schemaName);
         ((JavaWriter)out.tab(1)).println("val %s = new %s", new Object[]{schemaId, className});
         out.println("}");
         out.println();
      }

      this.generateSchemaClassJavadoc(schema, out);
      this.printClassAnnotations(out, schema);
      if (this.scala) {
         out.println("class %s extends %s(\"%s\", %s)[[before= with ][separator= with ][%s]] {", new Object[]{className, SchemaImpl.class, schema.getOutputName(), catalogId, interfaces});
      } else {
         out.println("public class %s extends %s[[before= implements ][%s]] {", new Object[]{className, SchemaImpl.class, interfaces});
         out.printSerial();
         ((JavaWriter)out.tab(1)).javadoc("The reference instance of <code>%s</code>", schemaName);
         ((JavaWriter)out.tab(1)).println("public static final %s %s = new %s();", new Object[]{className, schemaId, className});
         if (this.generateGlobalTableReferences()) {
            Iterator var8 = schema.getTables().iterator();

            while(var8.hasNext()) {
               TableDefinition table = (TableDefinition)var8.next();
               String tableClassName = out.ref(this.getStrategy().getFullJavaClassName(table));
               String tableId = this.getStrategy().getJavaIdentifier(table);
               String tableFullId = this.getStrategy().getFullJavaIdentifier(table);
               String tableComment = !StringUtils.isBlank(table.getComment()) ? this.escapeEntities(table.getComment()) : "The table <code>" + table.getQualifiedOutputName() + "</code>.";
               ((JavaWriter)out.tab(1)).javadoc(tableComment);
               if (this.scala) {
                  ((JavaWriter)out.tab(1)).println("val %s = %s", new Object[]{tableId, tableFullId});
               } else {
                  ((JavaWriter)out.tab(1)).println("public final %s %s = %s;", new Object[]{tableClassName, tableId, tableFullId});
               }

               if (table.isTableValuedFunction()) {
                  this.printTableValuedFunction(out, table, this.getStrategy().getJavaIdentifier(table));
               }
            }
         }

         ((JavaWriter)out.tab(1)).javadoc("No further instances allowed");
         ((JavaWriter)out.tab(1)).println("private %s() {", new Object[]{className});
         ((JavaWriter)out.tab(2)).println("super(\"%s\", null);", new Object[]{schema.getOutputName()});
         ((JavaWriter)out.tab(1)).println("}");
      }

      out.println();
      if (this.scala) {
         ((JavaWriter)out.tab(1)).println("override def getCatalog : %s = %s", new Object[]{Catalog.class, catalogId});
      } else {
         ((JavaWriter)out.tab(1)).overrideInherit();
         ((JavaWriter)out.tab(1)).println("public %s getCatalog() {", new Object[]{Catalog.class});
         ((JavaWriter)out.tab(2)).println("return %s;", new Object[]{catalogId});
         ((JavaWriter)out.tab(1)).println("}");
      }

      if (this.generateGlobalSequenceReferences()) {
         this.printReferences(out, this.database.getSequences(schema), Sequence.class, true);
      }

      if (this.generateGlobalTableReferences()) {
         this.printReferences(out, this.database.getTables(schema), Table.class, true);
      }

      if (this.generateGlobalUDTReferences()) {
         this.printReferences(out, this.database.getUDTs(schema), UDT.class, true);
      }

      this.generateSchemaClassFooter(schema, out);
      out.println("}");
   }

   protected void generateSchemaClassFooter(SchemaDefinition schema, JavaWriter out) {
   }

   protected void generateSchemaClassJavadoc(SchemaDefinition schema, JavaWriter out) {
      this.printClassJavadoc(out, (Definition)schema);
   }

   protected void printFromAndInto(JavaWriter out, TableDefinition table) {
      this.printFromAndInto(out, (Definition)table);
   }

   private void printFromAndInto(JavaWriter out, Definition tableOrUDT) {
      String qualified = out.ref(this.getStrategy().getFullJavaClassName(tableOrUDT, GeneratorStrategy.Mode.INTERFACE));
      ((JavaWriter)out.tab(1)).header("FROM and INTO");
      ((JavaWriter)out.tab(1)).overrideInheritIf(this.generateInterfaces() && !this.generateImmutableInterfaces());
      ((JavaWriter)out.tab(1)).println("public void from(%s from) {", new Object[]{qualified});
      Iterator var4 = this.getTypedElements(tableOrUDT).iterator();

      while(var4.hasNext()) {
         TypedElementDefinition<?> column = (TypedElementDefinition)var4.next();
         String setter = this.getStrategy().getJavaSetterName(column, GeneratorStrategy.Mode.INTERFACE);
         String getter = this.getStrategy().getJavaGetterName(column, GeneratorStrategy.Mode.INTERFACE);
         if (this.scala) {
            ((JavaWriter)out.tab(2)).println("%s(from.%s)", new Object[]{setter, getter});
         } else {
            ((JavaWriter)out.tab(2)).println("%s(from.%s());", new Object[]{setter, getter});
         }
      }

      ((JavaWriter)out.tab(1)).println("}");
      if (this.generateInterfaces() && !this.generateImmutableInterfaces()) {
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("public <E extends %s> E into(E into) {", new Object[]{qualified});
            ((JavaWriter)out.tab(2)).println("into.from(this)");
            ((JavaWriter)out.tab(2)).println("return into");
            ((JavaWriter)out.tab(1)).println("}");
         } else {
            ((JavaWriter)out.tab(1)).overrideInherit();
            ((JavaWriter)out.tab(1)).println("public <E extends %s> E into(E into) {", new Object[]{qualified});
            ((JavaWriter)out.tab(2)).println("into.from(this);");
            ((JavaWriter)out.tab(2)).println("return into;");
            ((JavaWriter)out.tab(1)).println("}");
         }
      }

   }

   protected void printReferences(JavaWriter out, List<? extends Definition> definitions, Class<?> type, boolean isGeneric) {
      if (out != null && !definitions.isEmpty()) {
         String generic = isGeneric ? (this.scala ? "[_]" : "<?>") : "";
         List<String> references = out.ref(this.getStrategy().getFullJavaIdentifiers((Collection)definitions), 2);
         out.println();
         int i;
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("override def get%ss : %s[%s%s] = {", new Object[]{type.getSimpleName(), List.class, type, generic});
            ((JavaWriter)out.tab(2)).println("val result = new %s[%s%s]", new Object[]{ArrayList.class, type, generic});

            for(i = 0; i < definitions.size(); i += 500) {
               ((JavaWriter)out.tab(2)).println("result.addAll(get%ss%s)", new Object[]{type.getSimpleName(), i / 500});
            }

            ((JavaWriter)out.tab(2)).println("result");
            ((JavaWriter)out.tab(1)).println("}");
         } else {
            ((JavaWriter)out.tab(1)).override();
            ((JavaWriter)out.tab(1)).println("public final %s<%s%s> get%ss() {", new Object[]{List.class, type, generic, type.getSimpleName()});
            ((JavaWriter)out.tab(2)).println("%s result = new %s();", new Object[]{List.class, ArrayList.class});

            for(i = 0; i < definitions.size(); i += 500) {
               ((JavaWriter)out.tab(2)).println("result.addAll(get%ss%s());", new Object[]{type.getSimpleName(), i / 500});
            }

            ((JavaWriter)out.tab(2)).println("return result;");
            ((JavaWriter)out.tab(1)).println("}");
         }

         for(i = 0; i < definitions.size(); i += 500) {
            out.println();
            if (this.scala) {
               ((JavaWriter)out.tab(1)).println("private def get%ss%s(): %s[%s%s] = {", new Object[]{type.getSimpleName(), i / 500, List.class, type, generic});
               ((JavaWriter)out.tab(2)).println("return %s.asList[%s%s]([[before=\n\t\t\t][separator=,\n\t\t\t][%s]])", new Object[]{Arrays.class, type, generic, references.subList(i, Math.min(i + 500, references.size()))});
               ((JavaWriter)out.tab(1)).println("}");
            } else {
               ((JavaWriter)out.tab(1)).println("private final %s<%s%s> get%ss%s() {", new Object[]{List.class, type, generic, type.getSimpleName(), i / 500});
               ((JavaWriter)out.tab(2)).println("return %s.<%s%s>asList([[before=\n\t\t\t][separator=,\n\t\t\t][%s]]);", new Object[]{Arrays.class, type, generic, references.subList(i, Math.min(i + 500, references.size()))});
               ((JavaWriter)out.tab(1)).println("}");
            }
         }
      }

   }

   protected void printTableJPAAnnotation(JavaWriter out, TableDefinition table) {
      SchemaDefinition schema = table.getSchema();
      if (this.generateJPAAnnotations()) {
         out.println("@%s", new Object[]{out.ref("javax.persistence.Entity")});
         out.print("@%s(name = \"", new Object[]{out.ref("javax.persistence.Table")});
         out.print(table.getName().replace("\"", "\\\""));
         out.print("\"");
         if (!schema.isDefaultSchema()) {
            out.print(", schema = \"");
            out.print(schema.getOutputName().replace("\"", "\\\""));
            out.print("\"");
         }

         StringBuilder sb = new StringBuilder();
         String glue1 = "\n";
         Iterator var6 = table.getUniqueKeys().iterator();

         while(true) {
            UniqueKeyDefinition uk;
            do {
               if (!var6.hasNext()) {
                  if (sb.length() > 0) {
                     ((JavaWriter)out.print(", uniqueConstraints = ")).print(this.scala ? "Array(" : "{");
                     out.println(sb.toString());
                     out.print(this.scala ? ")" : "}");
                  }

                  out.println(")");
                  return;
               }

               uk = (UniqueKeyDefinition)var6.next();
            } while(uk.getKeyColumns().size() <= 1);

            sb.append(glue1);
            sb.append("\t").append(this.scala ? "new " : "@").append(out.ref("javax.persistence.UniqueConstraint")).append("(columnNames = ").append(this.scala ? "Array(" : "{");
            String glue2 = "";

            for(Iterator var9 = uk.getKeyColumns().iterator(); var9.hasNext(); glue2 = ", ") {
               ColumnDefinition column = (ColumnDefinition)var9.next();
               sb.append(glue2);
               sb.append("\"");
               sb.append(column.getName().replace("\"", "\\\""));
               sb.append("\"");
            }

            sb.append(this.scala ? ")" : "}").append(")");
            glue1 = ",\n";
         }
      }
   }

   protected void printColumnJPAAnnotation(JavaWriter out, ColumnDefinition column) {
      if (this.generateJPAAnnotations()) {
         UniqueKeyDefinition pk = column.getPrimaryKey();
         List<UniqueKeyDefinition> uks = column.getUniqueKeys();
         if (pk != null && pk.getKeyColumns().size() == 1) {
            ((JavaWriter)out.tab(1)).println("@%s", new Object[]{out.ref("javax.persistence.Id")});
            if (((ColumnDefinition)pk.getKeyColumns().get(0)).isIdentity()) {
               ((JavaWriter)out.tab(1)).println("@%s(strategy = %s.IDENTITY)", new Object[]{out.ref("javax.persistence.GeneratedValue"), out.ref("javax.persistence.GenerationType")});
            }
         }

         String unique = "";
         Iterator var6 = uks.iterator();

         while(var6.hasNext()) {
            UniqueKeyDefinition uk = (UniqueKeyDefinition)var6.next();
            if (uk.getKeyColumns().size() == 1) {
               unique = ", unique = true";
               break;
            }
         }

         String nullable = "";
         if (!column.getType().isNullable()) {
            nullable = ", nullable = false";
         }

         String length = "";
         String precision = "";
         String scale = "";
         if (column.getType().getLength() > 0) {
            length = ", length = " + column.getType().getLength();
         } else if (column.getType().getPrecision() > 0) {
            precision = ", precision = " + column.getType().getPrecision();
            if (column.getType().getScale() > 0) {
               scale = ", scale = " + column.getType().getScale();
            }
         }

         out.print("\t@%s(name = \"", new Object[]{out.ref("javax.persistence.Column")});
         out.print(column.getName().replace("\"", "\\\""));
         out.print("\"");
         out.print(unique);
         out.print(nullable);
         out.print(length);
         out.print(precision);
         out.print(scale);
         out.println(")");
      }

   }

   /** @deprecated */
   @Deprecated
   protected void printColumnValidationAnnotation(JavaWriter out, ColumnDefinition column) {
      this.printValidationAnnotation(out, column);
   }

   private void printValidationAnnotation(JavaWriter out, TypedElementDefinition<?> column) {
      if (this.generateValidationAnnotations()) {
         DataTypeDefinition type = column.getType();
         if (!column.getType().isNullable() && !column.getType().isDefaulted()) {
            ((JavaWriter)out.tab(1)).println("@%s", new Object[]{out.ref("javax.validation.constraints.NotNull")});
         }

         if ("java.lang.String".equals(this.getJavaType(type))) {
            int length = type.getLength();
            if (length > 0) {
               ((JavaWriter)out.tab(1)).println("@%s(max = %s)", new Object[]{out.ref("javax.validation.constraints.Size"), length});
            }
         }
      }

   }

   protected void generateRoutine(SchemaDefinition schema, RoutineDefinition routine) {
      JavaWriter out = this.newJavaWriter(this.getStrategy().getFile((Definition)routine));
      log.info("Generating routine", (Object)out.file().getName());
      this.generateRoutine(routine, out);
      this.closeJavaWriter(out);
   }

   protected void generateRoutine(RoutineDefinition routine, JavaWriter out) {
      SchemaDefinition schema = routine.getSchema();
      String className = this.getStrategy().getJavaClassName(routine);
      String returnType = routine.getReturnValue() == null ? Void.class.getName() : out.ref(this.getJavaType(routine.getReturnType()));
      List<?> returnTypeRef = list(routine.getReturnValue() != null ? this.getJavaTypeReference(this.database, routine.getReturnType()) : null);
      List<?> returnConverterType = out.ref(list(routine.getReturnValue() != null ? routine.getReturnType().getConverter() : null, routine.getReturnValue() != null ? routine.getReturnType().getBinding() : null));
      List<String> interfaces = out.ref(this.getStrategy().getJavaClassImplements(routine, GeneratorStrategy.Mode.DEFAULT));
      String schemaId = out.ref(this.getStrategy().getFullJavaIdentifier(schema), 2);
      List<String> packageId = out.ref(this.getStrategy().getFullJavaIdentifiers(routine.getPackage()), 2);
      this.printPackage(out, routine);
      Iterator var11;
      ParameterDefinition parameter;
      String setterReturnType;
      String setter;
      String paramName;
      String numberField;
      String paramId;
      String paramId;
      String isUnnamed;
      List converters;
      if (this.scala) {
         out.println("object %s {", new Object[]{className});
         var11 = routine.getAllParameters().iterator();

         while(var11.hasNext()) {
            parameter = (ParameterDefinition)var11.next();
            setterReturnType = out.ref(this.getJavaType(parameter.getType()));
            setter = this.getJavaTypeReference(parameter.getDatabase(), parameter.getType());
            paramName = out.ref(this.getStrategy().getJavaIdentifier(parameter), 2);
            numberField = parameter.getName();
            paramId = StringUtils.defaultString(parameter.getComment());
            paramId = parameter.isDefaulted() ? "true" : "false";
            isUnnamed = parameter.isUnnamed() ? "true" : "false";
            converters = out.ref(list(parameter.getType().getConverter(), parameter.getType().getBinding()));
            ((JavaWriter)out.tab(1)).javadoc("The parameter <code>%s</code>.%s", parameter.getQualifiedOutputName(), StringUtils.defaultIfBlank(" " + this.escapeEntities(paramId), ""));
            ((JavaWriter)out.tab(1)).println("val %s : %s[%s] = %s.createParameter(\"%s\", %s, %s, %s[[before=, ][new %s]])", new Object[]{paramName, Parameter.class, setterReturnType, AbstractRoutine.class, numberField, setter, paramId, isUnnamed, converters});
         }

         out.println("}");
         out.println();
      }

      this.generateRoutineClassJavadoc(routine, out);
      this.printClassAnnotations(out, schema);
      if (this.scala) {
         out.println("class %s extends %s[%s](\"%s\", %s[[before=, ][%s]][[before=, ][%s]][[before=, ][new %s()]])[[before= with ][separator= with ][%s]] {", new Object[]{className, AbstractRoutine.class, returnType, routine.getName(), schemaId, packageId, returnTypeRef, returnConverterType, interfaces});
      } else {
         out.println("public class %s extends %s<%s>[[before= implements ][%s]] {", new Object[]{className, AbstractRoutine.class, returnType, interfaces});
         out.printSerial();
         var11 = routine.getAllParameters().iterator();

         while(var11.hasNext()) {
            parameter = (ParameterDefinition)var11.next();
            setterReturnType = out.ref(this.getJavaType(parameter.getType()));
            setter = this.getJavaTypeReference(parameter.getDatabase(), parameter.getType());
            paramName = out.ref(this.getStrategy().getJavaIdentifier(parameter), 2);
            numberField = parameter.getName();
            paramId = StringUtils.defaultString(parameter.getComment());
            paramId = parameter.isDefaulted() ? "true" : "false";
            isUnnamed = parameter.isUnnamed() ? "true" : "false";
            converters = out.ref(list(parameter.getType().getConverter(), parameter.getType().getBinding()));
            ((JavaWriter)out.tab(1)).javadoc("The parameter <code>%s</code>.%s", parameter.getQualifiedOutputName(), StringUtils.defaultIfBlank(" " + paramId, ""));
            ((JavaWriter)out.tab(1)).println("public static final %s<%s> %s = createParameter(\"%s\", %s, %s, %s[[before=, ][new %s()]]);", new Object[]{Parameter.class, setterReturnType, paramName, numberField, setter, paramId, isUnnamed, converters});
         }
      }

      if (this.scala) {
         ((JavaWriter)out.tab(1)).println("{");
      } else {
         ((JavaWriter)out.tab(1)).javadoc("Create a new routine call instance");
         ((JavaWriter)out.tab(1)).println("public %s() {", new Object[]{className});
         ((JavaWriter)out.tab(2)).println("super(\"%s\", %s[[before=, ][%s]][[before=, ][%s]][[before=, ][new %s()]]);", new Object[]{routine.getName(), schemaId, packageId, returnTypeRef, returnConverterType});
         if (routine.getAllParameters().size() > 0) {
            out.println();
         }
      }

      var11 = routine.getAllParameters().iterator();

      while(var11.hasNext()) {
         parameter = (ParameterDefinition)var11.next();
         setterReturnType = this.getStrategy().getJavaIdentifier(parameter);
         if (parameter.equals(routine.getReturnValue())) {
            if (this.scala) {
               ((JavaWriter)out.tab(2)).println("setReturnParameter(%s.%s)", new Object[]{className, setterReturnType});
            } else {
               ((JavaWriter)out.tab(2)).println("setReturnParameter(%s);", new Object[]{setterReturnType});
            }
         } else if (routine.getInParameters().contains(parameter)) {
            if (routine.getOutParameters().contains(parameter)) {
               if (this.scala) {
                  ((JavaWriter)out.tab(2)).println("addInOutParameter(%s.%s)", new Object[]{className, setterReturnType});
               } else {
                  ((JavaWriter)out.tab(2)).println("addInOutParameter(%s);", new Object[]{setterReturnType});
               }
            } else if (this.scala) {
               ((JavaWriter)out.tab(2)).println("addInParameter(%s.%s)", new Object[]{className, setterReturnType});
            } else {
               ((JavaWriter)out.tab(2)).println("addInParameter(%s);", new Object[]{setterReturnType});
            }
         } else if (this.scala) {
            ((JavaWriter)out.tab(2)).println("addOutParameter(%s.%s)", new Object[]{className, setterReturnType});
         } else {
            ((JavaWriter)out.tab(2)).println("addOutParameter(%s);", new Object[]{setterReturnType});
         }
      }

      if (routine.getOverload() != null) {
         if (this.scala) {
            ((JavaWriter)out.tab(2)).println("setOverloaded(true)");
         } else {
            ((JavaWriter)out.tab(2)).println("setOverloaded(true);");
         }
      }

      ((JavaWriter)out.tab(1)).println("}");
      var11 = routine.getInParameters().iterator();

      while(var11.hasNext()) {
         parameter = (ParameterDefinition)var11.next();
         setterReturnType = this.fluentSetters() ? className : (this.scala ? "Unit" : "void");
         setter = this.getStrategy().getJavaSetterName(parameter, GeneratorStrategy.Mode.DEFAULT);
         paramName = parameter.getType().isGenericNumberType() ? "Number" : "Value";
         numberField = parameter.getType().isGenericNumberType() ? "Number" : "Field";
         paramId = this.getStrategy().getJavaIdentifier(parameter);
         paramId = "value".equals(paramId) ? "value_" : "value";
         ((JavaWriter)out.tab(1)).javadoc("Set the <code>%s</code> parameter IN value to the routine", parameter.getOutputName());
         if (this.scala) {
            ((JavaWriter)out.tab(1)).println("def %s(%s : %s) : Unit = {", new Object[]{setter, paramId, this.refNumberType(out, parameter.getType())});
            ((JavaWriter)out.tab(2)).println("set%s(%s.%s, %s)", new Object[]{paramName, className, paramId, paramId});
            ((JavaWriter)out.tab(1)).println("}");
         } else {
            ((JavaWriter)out.tab(1)).println("public void %s(%s %s) {", new Object[]{setter, this.varargsIfArray(this.refNumberType(out, parameter.getType())), paramId});
            ((JavaWriter)out.tab(2)).println("set%s(%s, %s);", new Object[]{paramName, paramId, paramId});
            ((JavaWriter)out.tab(1)).println("}");
         }

         if (routine.isSQLUsable()) {
            ((JavaWriter)out.tab(1)).javadoc("Set the <code>%s</code> parameter to the function to be used with a {@link org.jooq.Select} statement", parameter.getOutputName());
            if (this.scala) {
               ((JavaWriter)out.tab(1)).println("def %s(field : %s[%s]) : %s = {", new Object[]{setter, Field.class, this.refExtendsNumberType(out, parameter.getType()), setterReturnType});
               ((JavaWriter)out.tab(2)).println("set%s(%s.%s, field)", new Object[]{numberField, className, paramId});
               if (this.fluentSetters()) {
                  ((JavaWriter)out.tab(2)).println("this");
               }

               ((JavaWriter)out.tab(1)).println("}");
            } else {
               ((JavaWriter)out.tab(1)).println("public %s %s(%s<%s> field) {", new Object[]{setterReturnType, setter, Field.class, this.refExtendsNumberType(out, parameter.getType())});
               ((JavaWriter)out.tab(2)).println("set%s(%s, field);", new Object[]{numberField, paramId});
               if (this.fluentSetters()) {
                  ((JavaWriter)out.tab(2)).println("return this;");
               }

               ((JavaWriter)out.tab(1)).println("}");
            }
         }
      }

      var11 = routine.getAllParameters().iterator();

      while(var11.hasNext()) {
         parameter = (ParameterDefinition)var11.next();
         boolean isReturnValue = parameter.equals(routine.getReturnValue());
         boolean isOutParameter = routine.getOutParameters().contains(parameter);
         if (isOutParameter && !isReturnValue) {
            paramName = parameter.getOutputName();
            numberField = out.ref(this.getJavaType(parameter.getType()));
            paramId = this.getStrategy().getJavaGetterName(parameter, GeneratorStrategy.Mode.DEFAULT);
            paramId = this.getStrategy().getJavaIdentifier(parameter);
            ((JavaWriter)out.tab(1)).javadoc("Get the <code>%s</code> parameter OUT value from the routine", paramName);
            if (this.scala) {
               ((JavaWriter)out.tab(1)).println("def %s : %s = {", new Object[]{paramId, numberField});
               ((JavaWriter)out.tab(2)).println("get(%s.%s)", new Object[]{className, paramId});
               ((JavaWriter)out.tab(1)).println("}");
            } else {
               ((JavaWriter)out.tab(1)).println("public %s %s() {", new Object[]{numberField, paramId});
               ((JavaWriter)out.tab(2)).println("return get(%s);", new Object[]{paramId});
               ((JavaWriter)out.tab(1)).println("}");
            }
         }
      }

      this.generateRoutineClassFooter(routine, out);
      out.println("}");
   }

   protected void generateRoutineClassFooter(RoutineDefinition routine, JavaWriter out) {
   }

   protected void generateRoutineClassJavadoc(RoutineDefinition routine, JavaWriter out) {
      this.printClassJavadoc(out, (Definition)routine);
   }

   protected void printConvenienceMethodFunctionAsField(JavaWriter out, RoutineDefinition function, boolean parametersAsField) {
      if (function.getInParameters().size() > 254) {
         log.warn("Too many parameters", (Object)("Function " + function + " has more than 254 in parameters. Skipping generation of convenience method."));
      } else if (!parametersAsField || !function.getInParameters().isEmpty()) {
         String className = out.ref(this.getStrategy().getFullJavaClassName(function));
         String localVar = this.disambiguateJavaMemberName(function.getInParameters(), "f");
         ((JavaWriter)out.tab(1)).javadoc("Get <code>%s</code> as a field.", function.getQualifiedOutputName());
         if (this.scala) {
            ((JavaWriter)out.tab(1)).print("def %s(", new Object[]{this.getStrategy().getJavaMethodName(function, GeneratorStrategy.Mode.DEFAULT)});
         } else {
            ((JavaWriter)out.tab(1)).print("public static %s<%s> %s(", new Object[]{function.isAggregate() ? AggregateFunction.class : Field.class, out.ref(this.getJavaType(function.getReturnType())), this.getStrategy().getJavaMethodName(function, GeneratorStrategy.Mode.DEFAULT)});
         }

         String separator = "";

         Iterator var7;
         ParameterDefinition parameter;
         for(var7 = function.getInParameters().iterator(); var7.hasNext(); separator = ", ") {
            parameter = (ParameterDefinition)var7.next();
            out.print(separator);
            if (this.scala) {
               out.print("%s : ", new Object[]{this.getStrategy().getJavaMemberName(parameter)});
               if (parametersAsField) {
                  out.print("%s[%s]", new Object[]{Field.class, this.refExtendsNumberType(out, parameter.getType())});
               } else {
                  out.print(this.refNumberType(out, parameter.getType()));
               }
            } else {
               if (parametersAsField) {
                  out.print("%s<%s>", new Object[]{Field.class, this.refExtendsNumberType(out, parameter.getType())});
               } else {
                  out.print(this.refNumberType(out, parameter.getType()));
               }

               out.print(" %s", new Object[]{this.getStrategy().getJavaMemberName(parameter)});
            }
         }

         if (this.scala) {
            out.println(") : %s[%s] = {", new Object[]{function.isAggregate() ? AggregateFunction.class : Field.class, out.ref(this.getJavaType(function.getReturnType()))});
            ((JavaWriter)out.tab(2)).println("val %s = new %s", new Object[]{localVar, className});
         } else {
            out.println(") {");
            ((JavaWriter)out.tab(2)).println("%s %s = new %s();", new Object[]{className, localVar, className});
         }

         var7 = function.getInParameters().iterator();

         while(var7.hasNext()) {
            parameter = (ParameterDefinition)var7.next();
            String paramSetter = this.getStrategy().getJavaSetterName(parameter, GeneratorStrategy.Mode.DEFAULT);
            String paramMember = this.getStrategy().getJavaMemberName(parameter);
            if (this.scala) {
               ((JavaWriter)out.tab(2)).println("%s.%s(%s)", new Object[]{localVar, paramSetter, paramMember});
            } else {
               ((JavaWriter)out.tab(2)).println("%s.%s(%s);", new Object[]{localVar, paramSetter, paramMember});
            }
         }

         out.println();
         if (this.scala) {
            ((JavaWriter)out.tab(2)).println("return %s.as%s", new Object[]{localVar, function.isAggregate() ? "AggregateFunction" : "Field"});
         } else {
            ((JavaWriter)out.tab(2)).println("return %s.as%s();", new Object[]{localVar, function.isAggregate() ? "AggregateFunction" : "Field"});
         }

         ((JavaWriter)out.tab(1)).println("}");
      }
   }

   protected void printConvenienceMethodTableValuedFunctionAsField(JavaWriter out, TableDefinition function, boolean parametersAsField, String javaMethodName) {
      if (function.getParameters().size() > 254) {
         log.warn("Too many parameters", (Object)("Function " + function + " has more than 254 in parameters. Skipping generation of convenience method."));
      } else if (!parametersAsField || !function.getParameters().isEmpty()) {
         String className = out.ref(this.getStrategy().getFullJavaClassName(function));
         ((JavaWriter)out.tab(1)).javadoc("Get <code>%s</code> as a table.", function.getQualifiedOutputName());
         if (this.scala) {
            ((JavaWriter)out.tab(1)).print("def %s(", new Object[]{javaMethodName});
         } else {
            ((JavaWriter)out.tab(1)).print("public static %s %s(", new Object[]{className, javaMethodName});
         }

         this.printParameterDeclarations(out, function, parametersAsField);
         if (this.scala) {
            out.println(") : %s = {", new Object[]{className});
            ((JavaWriter)out.tab(2)).print("%s.call(", new Object[]{out.ref(this.getStrategy().getFullJavaIdentifier(function), 2)});
         } else {
            out.println(") {");
            ((JavaWriter)out.tab(2)).print("return %s.call(", new Object[]{out.ref(this.getStrategy().getFullJavaIdentifier(function), 2)});
         }

         String separator = "";

         for(Iterator var7 = function.getParameters().iterator(); var7.hasNext(); separator = ", ") {
            ParameterDefinition parameter = (ParameterDefinition)var7.next();
            out.print(separator);
            out.print("%s", new Object[]{this.getStrategy().getJavaMemberName(parameter)});
         }

         if (this.scala) {
            out.println(")");
         } else {
            out.println(");");
         }

         ((JavaWriter)out.tab(1)).println("}");
      }
   }

   private void printParameterDeclarations(JavaWriter out, TableDefinition function, boolean parametersAsField) {
      String sep1 = "";

      for(Iterator var5 = function.getParameters().iterator(); var5.hasNext(); sep1 = ", ") {
         ParameterDefinition parameter = (ParameterDefinition)var5.next();
         out.print(sep1);
         if (this.scala) {
            out.print("%s : ", new Object[]{this.getStrategy().getJavaMemberName(parameter)});
            if (parametersAsField) {
               out.print("%s[%s]", new Object[]{Field.class, this.refExtendsNumberType(out, parameter.getType())});
            } else {
               out.print(this.refNumberType(out, parameter.getType()));
            }
         } else {
            if (parametersAsField) {
               out.print("%s<%s>", new Object[]{Field.class, this.refExtendsNumberType(out, parameter.getType())});
            } else {
               out.print(this.refNumberType(out, parameter.getType()));
            }

            out.print(" %s", new Object[]{this.getStrategy().getJavaMemberName(parameter)});
         }
      }

   }

   private String disambiguateJavaMemberName(Collection<? extends Definition> definitions, String defaultName) {
      Set<String> names = new HashSet();
      Iterator var4 = definitions.iterator();

      while(var4.hasNext()) {
         Definition definition = (Definition)var4.next();
         names.add(this.getStrategy().getJavaMemberName(definition));
      }

      String name;
      for(name = defaultName; names.contains(name); name = name + "_") {
      }

      return name;
   }

   protected void printConvenienceMethodFunction(JavaWriter out, RoutineDefinition function, boolean instance) {
      if (function.getInParameters().size() > 254) {
         log.warn("Too many parameters", (Object)("Function " + function + " has more than 254 in parameters. Skipping generation of convenience method."));
      } else {
         String className = out.ref(this.getStrategy().getFullJavaClassName(function));
         String functionName = function.getQualifiedOutputName();
         String functionType = out.ref(this.getJavaType(function.getReturnType()));
         String methodName = this.getStrategy().getJavaMethodName(function, GeneratorStrategy.Mode.DEFAULT);
         String configurationArgument = this.disambiguateJavaMemberName(function.getInParameters(), "configuration");
         String localVar = this.disambiguateJavaMemberName(function.getInParameters(), "f");
         ((JavaWriter)out.tab(1)).javadoc("Call <code>%s</code>", functionName);
         if (this.scala) {
            ((JavaWriter)out.tab(1)).print("def %s(", new Object[]{methodName});
         } else {
            ((JavaWriter)out.tab(1)).print("public %s%s %s(", new Object[]{!instance ? "static " : "", functionType, methodName});
         }

         String glue = "";
         if (!instance) {
            if (this.scala) {
               out.print("%s : %s", new Object[]{configurationArgument, Configuration.class});
            } else {
               out.print("%s %s", new Object[]{Configuration.class, configurationArgument});
            }

            glue = ", ";
         }

         Iterator var11 = function.getInParameters().iterator();

         while(true) {
            ParameterDefinition parameter;
            String paramSetter;
            String paramMember;
            do {
               if (!var11.hasNext()) {
                  if (this.scala) {
                     out.println(") : %s = {", new Object[]{functionType});
                     ((JavaWriter)out.tab(2)).println("val %s = new %s()", new Object[]{localVar, className});
                  } else {
                     out.println(") {");
                     ((JavaWriter)out.tab(2)).println("%s %s = new %s();", new Object[]{className, localVar, className});
                  }

                  var11 = function.getInParameters().iterator();

                  while(var11.hasNext()) {
                     parameter = (ParameterDefinition)var11.next();
                     paramSetter = this.getStrategy().getJavaSetterName(parameter, GeneratorStrategy.Mode.DEFAULT);
                     paramMember = instance && parameter.equals(function.getInParameters().get(0)) ? "this" : this.getStrategy().getJavaMemberName(parameter);
                     if (this.scala) {
                        ((JavaWriter)out.tab(2)).println("%s.%s(%s)", new Object[]{localVar, paramSetter, paramMember});
                     } else {
                        ((JavaWriter)out.tab(2)).println("%s.%s(%s);", new Object[]{localVar, paramSetter, paramMember});
                     }
                  }

                  out.println();
                  if (this.scala) {
                     ((JavaWriter)out.tab(2)).println("%s.execute(%s)", new Object[]{localVar, instance ? "configuration()" : configurationArgument});
                  } else {
                     ((JavaWriter)out.tab(2)).println("%s.execute(%s);", new Object[]{localVar, instance ? "configuration()" : configurationArgument});
                  }

                  if (this.scala) {
                     ((JavaWriter)out.tab(2)).println("%s.getReturnValue", new Object[]{localVar});
                  } else {
                     ((JavaWriter)out.tab(2)).println("return %s.getReturnValue();", new Object[]{localVar});
                  }

                  ((JavaWriter)out.tab(1)).println("}");
                  return;
               }

               parameter = (ParameterDefinition)var11.next();
            } while(instance && parameter.equals(function.getInParameters().get(0)));

            paramSetter = this.refNumberType(out, parameter.getType());
            paramMember = this.getStrategy().getJavaMemberName(parameter);
            if (this.scala) {
               out.print("%s%s : %s", new Object[]{glue, paramMember, paramSetter});
            } else {
               out.print("%s%s %s", new Object[]{glue, paramSetter, paramMember});
            }

            glue = ", ";
         }
      }
   }

   protected void printConvenienceMethodProcedure(JavaWriter out, RoutineDefinition procedure, boolean instance) {
      if (procedure.getInParameters().size() > 254) {
         log.warn("Too many parameters", (Object)("Procedure " + procedure + " has more than 254 in parameters. Skipping generation of convenience method."));
      } else {
         String className = out.ref(this.getStrategy().getFullJavaClassName(procedure));
         String configurationArgument = this.disambiguateJavaMemberName(procedure.getInParameters(), "configuration");
         String localVar = this.disambiguateJavaMemberName(procedure.getInParameters(), "p");
         List<ParameterDefinition> outParams = list(procedure.getReturnValue(), procedure.getOutParameters());
         ((JavaWriter)out.tab(1)).javadoc("Call <code>%s</code>", procedure.getQualifiedOutputName());
         if (this.scala) {
            ((JavaWriter)out.tab(1)).print("def ");
         } else {
            ((JavaWriter)out.tab(1)).print("public ");
            if (!instance) {
               out.print("static ");
            }

            if (outParams.size() == 0) {
               out.print("void ");
            } else if (outParams.size() == 1) {
               out.print(out.ref(this.getJavaType(((ParameterDefinition)outParams.get(0)).getType())));
               out.print(" ");
            } else {
               out.print(className + " ");
            }
         }

         out.print(this.getStrategy().getJavaMethodName(procedure, GeneratorStrategy.Mode.DEFAULT));
         out.print("(");
         String glue = "";
         if (!instance) {
            if (this.scala) {
               out.print("%s : %s", new Object[]{configurationArgument, Configuration.class});
            } else {
               out.print("%s %s", new Object[]{Configuration.class, configurationArgument});
            }

            glue = ", ";
         }

         Iterator var9 = procedure.getInParameters().iterator();

         while(true) {
            ParameterDefinition parameter;
            do {
               if (!var9.hasNext()) {
                  if (this.scala) {
                     out.print(") : ");
                     if (outParams.size() == 0) {
                        out.print("Unit");
                     } else if (outParams.size() == 1) {
                        out.print(out.ref(this.getJavaType(((ParameterDefinition)outParams.get(0)).getType())));
                     } else {
                        out.print(className);
                     }

                     out.println(" = {");
                     ((JavaWriter)out.tab(2)).println("val %s = new %s", new Object[]{localVar, className});
                  } else {
                     out.println(") {");
                     ((JavaWriter)out.tab(2)).println("%s %s = new %s();", new Object[]{className, localVar, className});
                  }

                  var9 = procedure.getInParameters().iterator();

                  String columnTypeInterface;
                  while(var9.hasNext()) {
                     parameter = (ParameterDefinition)var9.next();
                     String setter = this.getStrategy().getJavaSetterName(parameter, GeneratorStrategy.Mode.DEFAULT);
                     columnTypeInterface = instance && parameter.equals(procedure.getInParameters().get(0)) ? "this" : this.getStrategy().getJavaMemberName(parameter);
                     if (this.scala) {
                        ((JavaWriter)out.tab(2)).println("%s.%s(%s)", new Object[]{localVar, setter, columnTypeInterface});
                     } else {
                        ((JavaWriter)out.tab(2)).println("%s.%s(%s);", new Object[]{localVar, setter, columnTypeInterface});
                     }
                  }

                  out.println();
                  if (this.scala) {
                     ((JavaWriter)out.tab(2)).println("%s.execute(%s)", new Object[]{localVar, instance ? "configuration()" : configurationArgument});
                  } else {
                     ((JavaWriter)out.tab(2)).println("%s.execute(%s);", new Object[]{localVar, instance ? "configuration()" : configurationArgument});
                  }

                  if (outParams.size() > 0) {
                     ParameterDefinition parameter = (ParameterDefinition)outParams.get(0);
                     String getter = parameter == procedure.getReturnValue() ? "getReturnValue" : this.getStrategy().getJavaGetterName(parameter, GeneratorStrategy.Mode.DEFAULT);
                     boolean isUDT = parameter.getType().isUDT();
                     if (instance) {
                        if (this.generateInterfaces() && isUDT) {
                           columnTypeInterface = out.ref(this.getJavaType(parameter.getType(), GeneratorStrategy.Mode.INTERFACE));
                           if (this.scala) {
                              ((JavaWriter)out.tab(2)).println("from(%s.%s.asInstanceOf[%s])", new Object[]{localVar, getter, columnTypeInterface});
                           } else {
                              ((JavaWriter)out.tab(2)).println("from((%s) %s.%s());", new Object[]{columnTypeInterface, localVar, getter});
                           }
                        } else if (this.scala) {
                           ((JavaWriter)out.tab(2)).println("from(%s.%s)", new Object[]{localVar, getter});
                        } else {
                           ((JavaWriter)out.tab(2)).println("from(%s.%s());", new Object[]{localVar, getter});
                        }
                     }

                     if (outParams.size() == 1) {
                        if (this.scala) {
                           ((JavaWriter)out.tab(2)).println("return %s.%s", new Object[]{localVar, getter});
                        } else {
                           ((JavaWriter)out.tab(2)).println("return %s.%s();", new Object[]{localVar, getter});
                        }
                     } else if (outParams.size() > 1) {
                        if (this.scala) {
                           ((JavaWriter)out.tab(2)).println("return %s", new Object[]{localVar});
                        } else {
                           ((JavaWriter)out.tab(2)).println("return %s;", new Object[]{localVar});
                        }
                     }
                  }

                  ((JavaWriter)out.tab(1)).println("}");
                  return;
               }

               parameter = (ParameterDefinition)var9.next();
            } while(instance && parameter.equals(procedure.getInParameters().get(0)));

            out.print(glue);
            if (this.scala) {
               out.print("%s : %s", new Object[]{this.getStrategy().getJavaMemberName(parameter), this.refNumberType(out, parameter.getType())});
            } else {
               out.print("%s %s", new Object[]{this.refNumberType(out, parameter.getType()), this.getStrategy().getJavaMemberName(parameter)});
            }

            glue = ", ";
         }
      }
   }

   protected void printConvenienceMethodTableValuedFunction(JavaWriter out, TableDefinition function, String javaMethodName) {
      if (function.getParameters().size() > 254) {
         log.warn("Too many parameters", (Object)("Function " + function + " has more than 254 in parameters. Skipping generation of convenience method."));
      } else {
         String recordClassName = out.ref(this.getStrategy().getFullJavaClassName(function, GeneratorStrategy.Mode.RECORD));
         String configurationArgument = this.disambiguateJavaMemberName(function.getParameters(), "configuration");
         ((JavaWriter)out.tab(1)).javadoc("Call <code>%s</code>.", function.getQualifiedOutputName());
         if (this.scala) {
            ((JavaWriter)out.tab(1)).print("def %s(%s : %s", new Object[]{javaMethodName, configurationArgument, Configuration.class});
         } else {
            ((JavaWriter)out.tab(1)).print("public static %s<%s> %s(%s %s", new Object[]{Result.class, recordClassName, javaMethodName, Configuration.class, configurationArgument});
         }

         if (!function.getParameters().isEmpty()) {
            out.print(", ");
         }

         this.printParameterDeclarations(out, function, false);
         if (this.scala) {
            out.println(") : %s[%s] = {", new Object[]{Result.class, recordClassName});
            ((JavaWriter)out.tab(2)).print("%s.using(%s).selectFrom(%s.call(", new Object[]{DSL.class, configurationArgument, out.ref(this.getStrategy().getFullJavaIdentifier(function), 2)});
         } else {
            out.println(") {");
            ((JavaWriter)out.tab(2)).print("return %s.using(%s).selectFrom(%s.call(", new Object[]{DSL.class, configurationArgument, out.ref(this.getStrategy().getFullJavaIdentifier(function), 2)});
         }

         String separator = "";

         for(Iterator var7 = function.getParameters().iterator(); var7.hasNext(); separator = ", ") {
            ParameterDefinition parameter = (ParameterDefinition)var7.next();
            out.print(separator);
            out.print("%s", new Object[]{this.getStrategy().getJavaMemberName(parameter)});
         }

         out.print(")).fetch()");
         if (this.scala) {
            out.println();
         } else {
            out.println(";");
         }

         ((JavaWriter)out.tab(1)).println("}");
      }
   }

   protected void printRecordTypeMethod(JavaWriter out, Definition definition) {
      String className = out.ref(this.getStrategy().getFullJavaClassName(definition, GeneratorStrategy.Mode.RECORD));
      ((JavaWriter)out.tab(1)).javadoc("The class holding records for this type");
      if (this.scala) {
         ((JavaWriter)out.tab(1)).println("override def getRecordType : %s[%s] = {", new Object[]{Class.class, className});
         ((JavaWriter)out.tab(2)).println("classOf[%s]", new Object[]{className});
         ((JavaWriter)out.tab(1)).println("}");
      } else {
         ((JavaWriter)out.tab(1)).override();
         ((JavaWriter)out.tab(1)).println("public %s<%s> getRecordType() {", new Object[]{Class.class, className});
         ((JavaWriter)out.tab(2)).println("return %s.class;", new Object[]{className});
         ((JavaWriter)out.tab(1)).println("}");
      }

   }

   protected void printSingletonInstance(JavaWriter out, Definition definition) {
      String className = this.getStrategy().getJavaClassName(definition);
      String identifier = this.getStrategy().getJavaIdentifier(definition);
      ((JavaWriter)out.tab(1)).javadoc("The reference instance of <code>%s</code>", definition.getQualifiedOutputName());
      if (this.scala) {
         ((JavaWriter)out.tab(1)).println("val %s = new %s", new Object[]{identifier, className});
      } else {
         ((JavaWriter)out.tab(1)).println("public static final %s %s = new %s();", new Object[]{className, identifier, className});
      }

   }

   protected final String escapeEntities(String comment) {
      return comment == null ? null : comment.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
   }

   protected void printClassJavadoc(JavaWriter out, Definition definition) {
      this.printClassJavadoc(out, this.escapeEntities(definition.getComment()));
   }

   protected void printClassJavadoc(JavaWriter out, String comment) {
      out.println("/**");
      if (comment != null && comment.length() > 0) {
         this.printJavadocParagraph(out, comment, "");
      } else {
         out.println(" * This class is generated by jOOQ.");
      }

      out.println(" */");
   }

   protected void printClassAnnotations(JavaWriter out, SchemaDefinition schema) {
      this.printClassAnnotations(out, schema, schema.getCatalog());
   }

   protected void printClassAnnotations(JavaWriter out, SchemaDefinition schema, CatalogDefinition catalog) {
      if (this.generateGeneratedAnnotation()) {
         out.println("@%s(", new Object[]{out.ref("javax.annotation.Generated")});
         if (!this.useSchemaVersionProvider() && !this.useCatalogVersionProvider()) {
            if (this.scala) {
               ((JavaWriter)out.tab(1)).println("value = %s(", new Object[]{out.ref("scala.Array")});
            } else {
               ((JavaWriter)out.tab(1)).println("value = {");
            }

            ((JavaWriter)out.tab(2)).println("\"http://www.jooq.org\",");
            ((JavaWriter)out.tab(2)).println("\"jOOQ version:%s\"", new Object[]{"3.9.1"});
            if (this.scala) {
               ((JavaWriter)out.tab(1)).println("),");
            } else {
               ((JavaWriter)out.tab(1)).println("},");
            }

            ((JavaWriter)out.tab(1)).println("comments = \"This class is generated by jOOQ\"");
         } else {
            boolean hasCatalogVersion = !StringUtils.isBlank((String)this.catalogVersions.get(catalog));
            boolean hasSchemaVersion = !StringUtils.isBlank((String)this.schemaVersions.get(schema));
            if (this.scala) {
               ((JavaWriter)out.tab(1)).println("value = %s(", new Object[]{out.ref("scala.Array")});
            } else {
               ((JavaWriter)out.tab(1)).println("value = {");
            }

            ((JavaWriter)out.tab(2)).println("\"http://www.jooq.org\",");
            if (!hasCatalogVersion && !hasSchemaVersion) {
               ((JavaWriter)out.tab(2)).println("\"jOOQ version:%s\"", new Object[]{"3.9.1"});
            } else {
               ((JavaWriter)out.tab(2)).println("\"jOOQ version:%s\",", new Object[]{"3.9.1"});
               if (hasCatalogVersion) {
                  ((JavaWriter)out.tab(2)).println("\"catalog version:%s\"", new Object[]{((String)this.catalogVersions.get(catalog)).replace("\"", "\\\"")});
               }

               if (hasSchemaVersion) {
                  ((JavaWriter)out.tab(2)).println("\"schema version:%s\"", new Object[]{((String)this.schemaVersions.get(schema)).replace("\"", "\\\"")});
               }
            }

            if (this.scala) {
               ((JavaWriter)out.tab(1)).println("),");
            } else {
               ((JavaWriter)out.tab(1)).println("},");
            }

            ((JavaWriter)out.tab(1)).println("date = \"" + this.isoDate + "\",");
            ((JavaWriter)out.tab(1)).println("comments = \"This class is generated by jOOQ\"");
         }

         out.println(")");
      }

      if (!this.scala) {
         out.println("@%s({ \"all\", \"unchecked\", \"rawtypes\" })", new Object[]{out.ref("java.lang.SuppressWarnings")});
      }

   }

   private String readVersion(File file, String type) {
      String result = null;

      try {
         RandomAccessFile f = new RandomAccessFile(file, "r");

         try {
            byte[] bytes = new byte[(int)f.length()];
            f.readFully(bytes);
            String string = new String(bytes);
            Matcher matcher = Pattern.compile("@(?:javax\\.annotation\\.)?Generated\\(\\s*?value\\s*?=\\s*?\\{[^}]*?\"" + type + " version:([^\"]*?)\"").matcher(string);
            if (matcher.find()) {
               result = matcher.group(1);
            }
         } finally {
            f.close();
         }
      } catch (IOException var12) {
      }

      return result;
   }

   protected void printJavadocParagraph(JavaWriter out, String comment, String indent) {
      String escaped = comment.replace("/*", "/ *").replace("*/", "* /");
      this.printParagraph(out, escaped, indent + " * ");
   }

   protected void printParagraph(GeneratorWriter<?> out, String comment, String indent) {
      boolean newLine = true;
      int lineLength = 0;

      for(int i = 0; i < comment.length(); ++i) {
         if (newLine) {
            out.print(indent);
            newLine = false;
         }

         out.print(comment.charAt(i));
         ++lineLength;
         if (comment.charAt(i) == '\n') {
            lineLength = 0;
            newLine = true;
         } else if (lineLength > 70 && Character.isWhitespace(comment.charAt(i))) {
            out.println();
            lineLength = 0;
            newLine = true;
         }
      }

      if (!newLine) {
         out.println();
      }

   }

   protected void printPackage(JavaWriter out, Definition definition) {
      this.printPackage(out, definition, GeneratorStrategy.Mode.DEFAULT);
   }

   protected void printPackage(JavaWriter out, Definition definition, GeneratorStrategy.Mode mode) {
      String header = this.getStrategy().getFileHeader(definition, mode);
      if (!StringUtils.isBlank(header)) {
         out.println("/*");
         this.printJavadocParagraph(out, header, "");
         out.println("*/");
      }

      if (this.scala) {
         out.println("package %s", new Object[]{this.getStrategy().getJavaPackageName(definition, mode)});
      } else {
         out.println("package %s;", new Object[]{this.getStrategy().getJavaPackageName(definition, mode)});
      }

      out.println();
      out.printImports();
      out.println();
   }

   /** @deprecated */
   @Deprecated
   protected String getExtendsNumberType(DataTypeDefinition type) {
      return this.getNumberType(type, this.scala ? "_ <: " : "? extends ");
   }

   protected String refExtendsNumberType(JavaWriter out, DataTypeDefinition type) {
      return type.isGenericNumberType() ? (this.scala ? "_ <: " : "? extends ") + out.ref(Number.class) : out.ref(this.getJavaType(type));
   }

   /** @deprecated */
   @Deprecated
   protected String getNumberType(DataTypeDefinition type) {
      return type.isGenericNumberType() ? Number.class.getName() : this.getJavaType(type);
   }

   protected String refNumberType(JavaWriter out, DataTypeDefinition type) {
      return type.isGenericNumberType() ? out.ref(Number.class) : out.ref(this.getJavaType(type));
   }

   /** @deprecated */
   @Deprecated
   protected String getNumberType(DataTypeDefinition type, String prefix) {
      return type.isGenericNumberType() ? prefix + Number.class.getName() : this.getJavaType(type);
   }

   protected String getSimpleJavaType(DataTypeDefinition type) {
      return GenerationUtil.getSimpleJavaType(this.getJavaType(type));
   }

   protected String getJavaTypeReference(Database db, DataTypeDefinition type) {
      if (this.database.isArrayType(type.getType())) {
         Name baseType = GenerationUtil.getArrayBaseType(db.getDialect(), type.getType(), type.getQualifiedUserType());
         return this.getTypeReference(db, type.getSchema(), baseType.last(), 0, 0, 0, true, (String)null, (Name)baseType) + ".getArrayDataType()";
      } else {
         return this.getTypeReference(db, type.getSchema(), type.getType(), type.getPrecision(), type.getScale(), type.getLength(), type.isNullable(), type.getDefaultValue(), type.getQualifiedUserType());
      }
   }

   protected String getJavaType(DataTypeDefinition type) {
      return this.getJavaType(type, GeneratorStrategy.Mode.RECORD);
   }

   protected String getJavaType(DataTypeDefinition type, GeneratorStrategy.Mode udtMode) {
      return this.getType(type.getDatabase(), type.getSchema(), type.getType(), type.getPrecision(), type.getScale(), type.getQualifiedUserType(), type.getJavaType(), Object.class.getName(), udtMode);
   }

   /** @deprecated */
   @Deprecated
   protected String getType(Database db, SchemaDefinition schema, String t, int p, int s, String u, String javaType, String defaultType) {
      return this.getType(db, schema, t, p, s, DSL.name(u), javaType, defaultType);
   }

   protected String getType(Database db, SchemaDefinition schema, String t, int p, int s, Name u, String javaType, String defaultType) {
      return this.getType(db, schema, t, p, s, u, javaType, defaultType, GeneratorStrategy.Mode.RECORD);
   }

   /** @deprecated */
   @Deprecated
   protected String getType(Database db, SchemaDefinition schema, String t, int p, int s, String u, String javaType, String defaultType, GeneratorStrategy.Mode udtMode) {
      return this.getType(db, schema, t, p, s, DSL.name(u), javaType, defaultType, udtMode);
   }

   protected String getType(Database db, SchemaDefinition schema, String t, int p, int s, Name u, String javaType, String defaultType, GeneratorStrategy.Mode udtMode) {
      String type = defaultType;
      if (javaType != null) {
         type = javaType;
      } else if (db.isArrayType(t)) {
         Name baseType = GenerationUtil.getArrayBaseType(db.getDialect(), t, u);
         if (this.scala) {
            type = "scala.Array[" + this.getType(db, schema, baseType.last(), p, s, baseType, javaType, defaultType, udtMode) + "]";
         } else {
            type = this.getType(db, schema, baseType.last(), p, s, baseType, javaType, defaultType, udtMode) + "[]";
         }
      } else if (db.getArray(schema, u) != null) {
         boolean udtArray = db.getArray(schema, u).getElementType().isUDT();
         if (udtMode == GeneratorStrategy.Mode.POJO || udtMode == GeneratorStrategy.Mode.INTERFACE && !udtArray) {
            if (this.scala) {
               type = "java.util.List[" + this.getJavaType(db.getArray(schema, u).getElementType(), udtMode) + "]";
            } else {
               type = "java.util.List<" + this.getJavaType(db.getArray(schema, u).getElementType(), udtMode) + ">";
            }
         } else if (udtMode == GeneratorStrategy.Mode.INTERFACE) {
            if (this.scala) {
               type = "java.util.List[_ <:" + this.getJavaType(db.getArray(schema, u).getElementType(), udtMode) + "]";
            } else {
               type = "java.util.List<? extends " + this.getJavaType(db.getArray(schema, u).getElementType(), udtMode) + ">";
            }
         } else {
            type = this.getStrategy().getFullJavaClassName(db.getArray(schema, u), GeneratorStrategy.Mode.RECORD);
         }
      } else if (db.getEnum(schema, u) != null) {
         type = this.getStrategy().getFullJavaClassName(db.getEnum(schema, u));
      } else if (db.getUDT(schema, u) != null) {
         type = this.getStrategy().getFullJavaClassName(db.getUDT(schema, u), udtMode);
      } else if (db.getDialect().family() == SQLDialect.POSTGRES && db.getTable(schema, u) != null) {
         type = this.getStrategy().getFullJavaClassName(db.getTable(schema, u), udtMode);
      } else if (u != null && db.getConfiguredCustomType(u.last()) != null) {
         type = u.last();
      } else {
         try {
            Class<?> clazz = this.mapJavaTimeTypes(DefaultDataType.getDataType(db.getDialect(), t, p, s)).getType();
            if (this.scala && clazz == byte[].class) {
               type = "scala.Array[scala.Byte]";
            } else {
               type = clazz.getCanonicalName();
            }

            if (clazz.getTypeParameters().length > 0) {
               type = type + (this.scala ? "[" : "<");
               String separator = "";
               TypeVariable[] var13 = clazz.getTypeParameters();
               int var14 = var13.length;
               int var15 = 0;

               while(true) {
                  if (var15 >= var14) {
                     type = type + (this.scala ? "]" : ">");
                     break;
                  }

                  TypeVariable<?> var = var13[var15];
                  type = type + separator;
                  type = type + ((Class)var.getBounds()[0]).getCanonicalName();
                  separator = ", ";
                  ++var15;
               }
            }
         } catch (SQLDialectNotSupportedException var17) {
            if (defaultType == null) {
               throw var17;
            }
         }
      }

      return type;
   }

   /** @deprecated */
   @Deprecated
   protected String getTypeReference(Database db, SchemaDefinition schema, String t, int p, int s, int l, boolean n, String d, String u) {
      return this.getTypeReference(db, schema, t, p, s, l, n, d, DSL.name(u));
   }

   protected String getTypeReference(Database db, SchemaDefinition schema, String t, int p, int s, int l, boolean n, String d, Name u) {
      StringBuilder sb = new StringBuilder();
      if (db.getArray(schema, u) != null) {
         ArrayDefinition array = this.database.getArray(schema, u);
         sb.append(this.getJavaTypeReference(db, array.getElementType()));
         sb.append(".asArrayDataType(");
         sb.append(this.classOf(this.getStrategy().getFullJavaClassName(array, GeneratorStrategy.Mode.RECORD)));
         sb.append(")");
      } else if (db.getUDT(schema, u) != null) {
         sb.append(this.getStrategy().getFullJavaIdentifier(db.getUDT(schema, u)));
         sb.append(".getDataType()");
      } else if (db.getDialect().family() == SQLDialect.POSTGRES && db.getTable(schema, u) != null) {
         sb.append(this.getStrategy().getFullJavaIdentifier(db.getTable(schema, u)));
         sb.append(".getDataType()");
      } else if (db.getEnum(schema, u) != null) {
         sb.append("org.jooq.util.");
         sb.append(db.getDialect().getName().toLowerCase());
         sb.append(".");
         sb.append(db.getDialect().getName());
         sb.append("DataType.");
         sb.append(DefaultDataType.normalise(DefaultDataType.getDataType(db.getDialect(), String.class).getTypeName()));
         sb.append(".asEnumDataType(");
         sb.append(this.classOf(this.getStrategy().getFullJavaClassName(db.getEnum(schema, u))));
         sb.append(")");
      } else {
         DataType dataType = null;

         try {
            dataType = this.mapJavaTimeTypes(DefaultDataType.getDataType(db.getDialect(), t, p, s)).nullable(n);
            if (d != null) {
               dataType = dataType.defaultValue(DSL.field(d, dataType));
            }
         } catch (SQLDialectNotSupportedException var19) {
         }

         String type1;
         String sqlDataTypeRef;
         if (dataType != null && dataType.getSQLDataType() != null) {
            DataType<?> sqlDataType = dataType.getSQLDataType();
            type1 = (String)SQLDATATYPE_LITERAL_LOOKUP.get(sqlDataType);
            sqlDataTypeRef = SQLDataType.class.getCanonicalName() + '.' + type1;
            sb.append(sqlDataTypeRef);
            if (dataType.hasPrecision() && p > 0) {
               sb.append(".precision(").append(p);
               if (dataType.hasScale() && s > 0) {
                  sb.append(", ").append(s);
               }

               sb.append(")");
            }

            if (dataType.hasLength() && l > 0) {
               sb.append(".length(").append(l).append(")");
            }

            if (!dataType.nullable()) {
               sb.append(".nullable(false)");
            }

            if (dataType.defaulted()) {
               sb.append(".defaultValue(");
               if (Arrays.asList(SQLDialect.MYSQL).contains(db.getDialect().family())) {
                  sb.append("org.jooq.impl.DSL.inline(\"").append(this.escapeString(d)).append("\"");
               } else {
                  sb.append("org.jooq.impl.DSL.field(\"").append(this.escapeString(d)).append("\"");
               }

               sb.append(", ").append(sqlDataTypeRef).append("))");
            }
         } else {
            try {
               String typeClass = db.getDialect().getName() == null ? SQLDataType.class.getName() : "org.jooq.util." + db.getDialect().getName().toLowerCase() + "." + db.getDialect().getName() + "DataType";
               sb.append(typeClass);
               sb.append(".");
               type1 = this.getType(db, schema, t, p, s, (Name)u, (String)null, (String)null);
               sqlDataTypeRef = this.getType(db, schema, t, 0, 0, (Name)u, (String)null, (String)null);
               String typeName = DefaultDataType.normalise(t);
               Reflect.on(typeClass).field(typeName);
               sb.append(typeName);
               if (!type1.equals(sqlDataTypeRef)) {
                  Class<?> clazz = this.mapJavaTimeTypes(DefaultDataType.getDataType(db.getDialect(), t, p, s)).getType();
                  sb.append(".asNumberDataType(");
                  sb.append(this.classOf(clazz.getCanonicalName()));
                  sb.append(")");
               }
            } catch (SQLDialectNotSupportedException var17) {
               sb = new StringBuilder();
               sb.append(DefaultDataType.class.getName());
               sb.append(".getDefaultDataType(\"");
               sb.append(t.replace("\"", "\\\""));
               sb.append("\")");
            } catch (ReflectException var18) {
               sb = new StringBuilder();
               sb.append(DefaultDataType.class.getName());
               sb.append(".getDefaultDataType(\"");
               sb.append(t.replace("\"", "\\\""));
               sb.append("\")");
            }
         }
      }

      return sb.toString();
   }

   private DataType<?> mapJavaTimeTypes(DataType<?> dataType) {
      DataType<?> result = dataType;
      if (dataType.isDateTime() && this.generateJavaTimeTypes) {
         if (dataType.getType() == Date.class) {
            result = SQLDataType.LOCALDATE;
         } else if (dataType.getType() == Time.class) {
            result = SQLDataType.LOCALTIME;
         } else if (dataType.getType() == Timestamp.class) {
            result = SQLDataType.LOCALDATETIME;
         }
      }

      return result;
   }

   protected boolean match(DataTypeDefinition type1, DataTypeDefinition type2) {
      return this.getJavaType(type1).equals(this.getJavaType(type2));
   }

   @SafeVarargs
   private static final <T> List<T> list(T... objects) {
      List<T> result = new ArrayList();
      if (objects != null) {
         Object[] var2 = objects;
         int var3 = objects.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            T object = var2[var4];
            if (object != null && !"".equals(object)) {
               result.add(object);
            }
         }
      }

      return result;
   }

   private static final <T> List<T> list(T first, List<T> remaining) {
      List<T> result = new ArrayList();
      result.addAll(list(first));
      result.addAll(remaining);
      return result;
   }

   private static final <T> List<T> first(Collection<T> objects) {
      List<T> result = new ArrayList();
      if (objects != null) {
         Iterator var2 = objects.iterator();
         if (var2.hasNext()) {
            T object = var2.next();
            result.add(object);
         }
      }

      return result;
   }

   private static final <T> List<T> remaining(Collection<T> objects) {
      List<T> result = new ArrayList();
      if (objects != null) {
         result.addAll(objects);
         if (result.size() > 0) {
            result.remove(0);
         }
      }

      return result;
   }

   private String classOf(String string) {
      return this.scala ? "classOf[" + string + "]" : string + ".class";
   }

   private String varargsIfArray(String type) {
      return this.scala ? type : SQUARE_BRACKETS.matcher(type).replaceFirst("...");
   }

   protected JavaWriter newJavaWriter(File file) {
      if (this.scala) {
         file = new File(file.getParentFile(), file.getName().replace(".java", ".scala"));
      }

      return new JavaWriter(file, this.generateFullyQualifiedTypes(), this.targetEncoding);
   }

   protected void closeJavaWriter(JavaWriter out) {
      if (out.close()) {
         this.files.add(out.file());
      }

   }

   static {
      try {
         java.lang.reflect.Field[] var0 = SQLDataType.class.getFields();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            java.lang.reflect.Field f = var0[var2];
            if (Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers())) {
               SQLDATATYPE_LITERAL_LOOKUP.put((DataType)f.get(SQLDataType.class), f.getName());
            }
         }
      } catch (Exception var4) {
         log.warn(var4);
      }

      SQUARE_BRACKETS = Pattern.compile("\\[\\]$");
   }

   private class AvoidAmbiguousClassesFilter implements Database.Filter {
      private Map<String, String> included;

      private AvoidAmbiguousClassesFilter() {
         this.included = new HashMap();
      }

      public boolean exclude(Definition definition) {
         if (!(definition instanceof ColumnDefinition) && !(definition instanceof AttributeDefinition) && !(definition instanceof ParameterDefinition)) {
            String name = JavaGenerator.this.getStrategy().getFullJavaClassName(definition);
            String nameLC = name.toLowerCase();
            String existing = (String)this.included.put(nameLC, name);
            if (existing == null) {
               return false;
            } else {
               JavaGenerator.log.warn("Ambiguous type name", (Object)("The object " + definition.getQualifiedOutputName() + " generates a type " + name + " which conflicts with the existing type " + existing + " on some operating systems. Use a custom generator strategy to disambiguate the types."));
               return true;
            }
         } else {
            return false;
         }
      }

      // $FF: synthetic method
      AvoidAmbiguousClassesFilter(Object x1) {
         this();
      }
   }
}
