package org.jooq.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.validation.SchemaFactory;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.util.jaxb.Catalog;
import org.jooq.util.jaxb.Configuration;
import org.jooq.util.jaxb.Generate;
import org.jooq.util.jaxb.Jdbc;
import org.jooq.util.jaxb.Matchers;
import org.jooq.util.jaxb.Property;
import org.jooq.util.jaxb.Schema;
import org.jooq.util.jaxb.Strategy;
import org.jooq.util.jaxb.Target;

public class GenerationTool {
   private static final JooqLogger log = JooqLogger.getLogger(GenerationTool.class);
   private ClassLoader loader;
   private DataSource dataSource;
   private Connection connection;
   private boolean close;

   public void setClassLoader(ClassLoader loader) {
      this.loader = loader;
   }

   public void setConnection(Connection connection) {
      this.connection = connection;
   }

   public void setDataSource(DataSource dataSource) {
      this.dataSource = dataSource;
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 1) {
         log.error("Usage : GenerationTool <configuration-file>");
         System.exit(-1);
      } else {
         String[] var1 = args;
         int var2 = args.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            String arg = var1[var3];
            Object in = GenerationTool.class.getResourceAsStream(arg);

            try {
               if (in == null && !arg.startsWith("/")) {
                  in = GenerationTool.class.getResourceAsStream("/" + arg);
               }

               if (in == null && (new File(arg)).exists()) {
                  in = new FileInputStream(new File(arg));
               }

               if (in != null) {
                  log.info("Initialising properties", (Object)arg);
                  generate(load((InputStream)in));
                  continue;
               }

               log.error("Cannot find " + arg + " on classpath, or in directory " + (new File(".")).getCanonicalPath());
               log.error("-----------");
               log.error("Please be sure it is located");
               log.error("  - on the classpath and qualified as a classpath location.");
               log.error("  - in the local directory or at a global path in the file system.");
               System.exit(-1);
            } catch (Exception var10) {
               log.error("Cannot read " + arg + ". Error : " + var10.getMessage(), (Throwable)var10);
               System.exit(-1);
               return;
            } finally {
               if (in != null) {
                  ((InputStream)in).close();
               }

            }

            return;
         }

      }
   }

   /** @deprecated */
   @Deprecated
   public static void main(Configuration configuration) throws Exception {
      (new GenerationTool()).run(configuration);
   }

   public static void generate(String xml) throws Exception {
      (new GenerationTool()).run(load(new ByteArrayInputStream(xml.getBytes("UTF-8"))));
   }

   public static void generate(Configuration configuration) throws Exception {
      (new GenerationTool()).run(configuration);
   }

   public void run(Configuration configuration) throws Exception {
      if (configuration.getLogging() != null) {
         switch(configuration.getLogging()) {
         case TRACE:
            JooqLogger.globalThreshold(JooqLogger.Level.TRACE);
            break;
         case DEBUG:
            JooqLogger.globalThreshold(JooqLogger.Level.DEBUG);
            break;
         case INFO:
            JooqLogger.globalThreshold(JooqLogger.Level.INFO);
            break;
         case WARN:
            JooqLogger.globalThreshold(JooqLogger.Level.WARN);
            break;
         case ERROR:
            JooqLogger.globalThreshold(JooqLogger.Level.ERROR);
            break;
         case FATAL:
            JooqLogger.globalThreshold(JooqLogger.Level.FATAL);
         }
      }

      Jdbc j = configuration.getJdbc();
      org.jooq.util.jaxb.Generator g = configuration.getGenerator();
      if (g == null) {
         throw new GeneratorException("The <generator/> tag is mandatory. For details, see http://www.jooq.org/xsd/jooq-codegen-3.9.0.xsd");
      } else {
         if (g.getStrategy() == null) {
            g.setStrategy(new Strategy());
         }

         if (g.getTarget() == null) {
            g.setTarget(new Target());
         }

         try {
            Class driver;
            if (this.connection == null) {
               this.close = true;
               if (this.dataSource != null) {
                  this.connection = this.dataSource.getConnection();
               } else if (j != null) {
                  driver = this.loadClass(this.driverClass(j));
                  Properties properties = this.properties(j.getProperties());
                  if (!properties.containsKey("user")) {
                     properties.put("user", StringUtils.defaultString(StringUtils.defaultString(j.getUser(), j.getUsername())));
                  }

                  if (!properties.containsKey("password")) {
                     properties.put("password", StringUtils.defaultString(j.getPassword()));
                  }

                  this.connection = ((Driver)driver.newInstance()).connect(StringUtils.defaultString(j.getUrl()), properties);
               }
            }

            j = (Jdbc)StringUtils.defaultIfNull(j, new Jdbc());
            driver = !StringUtils.isBlank(g.getName()) ? this.loadClass(trim(g.getName())) : JavaGenerator.class;
            Generator generator = (Generator)driver.newInstance();
            Matchers matchers = g.getStrategy().getMatchers();
            Object strategy;
            if (matchers != null) {
               strategy = new MatcherStrategy(matchers);
               if (g.getStrategy().getName() != null) {
                  log.warn("WARNING: Matchers take precedence over custom strategy. Strategy ignored: " + g.getStrategy().getName());
               }
            } else {
               Class<GeneratorStrategy> strategyClass = !StringUtils.isBlank(g.getStrategy().getName()) ? this.loadClass(trim(g.getStrategy().getName())) : DefaultGeneratorStrategy.class;
               strategy = (GeneratorStrategy)strategyClass.newInstance();
            }

            generator.setStrategy((GeneratorStrategy)strategy);
            org.jooq.util.jaxb.Database d = (org.jooq.util.jaxb.Database)StringUtils.defaultIfNull(g.getDatabase(), new org.jooq.util.jaxb.Database());
            String databaseName = trim(d.getName());
            Class<? extends Database> databaseClass = !StringUtils.isBlank(databaseName) ? this.loadClass(databaseName) : (this.connection != null ? this.databaseClass(this.connection) : this.databaseClass(j));
            Database database = (Database)databaseClass.newInstance();
            database.setProperties(this.properties(d.getProperties()));
            List<Catalog> catalogs = d.getCatalogs();
            List<Schema> schemata = d.getSchemata();
            boolean catalogsEmpty = catalogs.isEmpty();
            boolean schemataEmpty = schemata.isEmpty();
            if (catalogsEmpty) {
               Catalog catalog = new Catalog();
               catalog.setInputCatalog(trim(d.getInputCatalog()));
               catalog.setOutputCatalog(trim(d.getOutputCatalog()));
               catalog.setOutputCatalogToDefault(d.isOutputCatalogToDefault());
               catalogs.add(catalog);
               if (!StringUtils.isBlank(catalog.getInputCatalog())) {
                  catalogsEmpty = false;
               }

               if (schemataEmpty) {
                  Schema schema = new Schema();
                  schema.setInputSchema(trim(d.getInputSchema()));
                  schema.setOutputSchema(trim(d.getOutputSchema()));
                  schema.setOutputSchemaToDefault(d.isOutputSchemaToDefault());
                  catalog.getSchemata().add(schema);
                  if (!StringUtils.isBlank(schema.getInputSchema())) {
                     schemataEmpty = false;
                  }
               } else {
                  catalog.getSchemata().addAll(schemata);
                  if (!StringUtils.isBlank(d.getInputSchema())) {
                     log.warn("WARNING: Cannot combine configuration properties /configuration/generator/database/inputSchema and /configuration/generator/database/schemata");
                  }

                  if (!StringUtils.isBlank(d.getOutputSchema())) {
                     log.warn("WARNING: Cannot combine configuration properties /configuration/generator/database/outputSchema and /configuration/generator/database/schemata");
                  }
               }
            } else {
               if (!StringUtils.isBlank(d.getInputCatalog())) {
                  log.warn("WARNING: Cannot combine configuration properties /configuration/generator/database/inputCatalog and /configuration/generator/database/catalogs");
               }

               if (!StringUtils.isBlank(d.getOutputCatalog())) {
                  log.warn("WARNING: Cannot combine configuration properties /configuration/generator/database/outputCatalog and /configuration/generator/database/catalogs");
               }

               if (!StringUtils.isBlank(d.getInputSchema())) {
                  log.warn("WARNING: Cannot combine configuration properties /configuration/generator/database/inputSchema and /configuration/generator/database/catalogs");
               }

               if (!StringUtils.isBlank(d.getOutputSchema())) {
                  log.warn("WARNING: Cannot combine configuration properties /configuration/generator/database/outputSchema and /configuration/generator/database/catalogs");
               }

               if (!schemataEmpty) {
                  log.warn("WARNING: Cannot combine configuration properties /configuration/generator/database/catalogs and /configuration/generator/database/schemata");
               }
            }

            Iterator var29 = catalogs.iterator();

            while(var29.hasNext()) {
               Catalog catalog = (Catalog)var29.next();
               if ("".equals(catalog.getOutputCatalog())) {
                  log.warn("WARNING: Empty <outputCatalog/> should not be used to model default outputCatalogs. Use <outputCatalogToDefault>true</outputCatalogToDefault>, instead. See also: https://github.com/jOOQ/jOOQ/issues/3018");
               }

               if (Boolean.TRUE.equals(catalog.isOutputCatalogToDefault())) {
                  catalog.setOutputCatalog("");
               } else if (catalog.getOutputCatalog() == null) {
                  catalog.setOutputCatalog(trim(catalog.getInputCatalog()));
               }

               Iterator var18 = catalog.getSchemata().iterator();

               while(var18.hasNext()) {
                  Schema schema = (Schema)var18.next();
                  if (catalogsEmpty && schemataEmpty && StringUtils.isBlank(schema.getInputSchema())) {
                     if (!StringUtils.isBlank(j.getSchema())) {
                        log.warn("WARNING: The configuration property jdbc.Schema is deprecated and will be removed in the future. Use /configuration/generator/database/inputSchema instead");
                     }

                     schema.setInputSchema(trim(j.getSchema()));
                  }

                  if ("".equals(schema.getOutputSchema())) {
                     log.warn("WARNING: Empty <outputSchema/> should not be used to model default outputSchemas. Use <outputSchemaToDefault>true</outputSchemaToDefault>, instead. See also: https://github.com/jOOQ/jOOQ/issues/3018");
                  }

                  if (Boolean.TRUE.equals(schema.isOutputSchemaToDefault())) {
                     schema.setOutputSchema("");
                  } else if (schema.getOutputSchema() == null) {
                     schema.setOutputSchema(trim(schema.getInputSchema()));
                  }
               }
            }

            if (catalogsEmpty) {
               log.info("No <inputCatalog/> was provided. Generating ALL available catalogs instead.");
            }

            if (catalogsEmpty && schemataEmpty) {
               log.info("No <inputSchema/> was provided. Generating ALL available schemata instead.");
            }

            database.setConnection(this.connection);
            database.setConfiguredCatalogs(catalogs);
            database.setConfiguredSchemata(schemata);
            database.setIncludes(new String[]{StringUtils.defaultString(d.getIncludes())});
            database.setExcludes(new String[]{StringUtils.defaultString(d.getExcludes())});
            database.setIncludeExcludeColumns(Boolean.TRUE.equals(d.isIncludeExcludeColumns()));
            database.setIncludeForeignKeys(!Boolean.FALSE.equals(d.isIncludeForeignKeys()));
            database.setIncludePackages(!Boolean.FALSE.equals(d.isIncludePackages()));
            database.setIncludePrimaryKeys(!Boolean.FALSE.equals(d.isIncludePrimaryKeys()));
            database.setIncludeRoutines(!Boolean.FALSE.equals(d.isIncludeRoutines()));
            database.setIncludeSequences(!Boolean.FALSE.equals(d.isIncludeSequences()));
            database.setIncludeTables(!Boolean.FALSE.equals(d.isIncludeTables()));
            database.setIncludeUDTs(!Boolean.FALSE.equals(d.isIncludeUDTs()));
            database.setIncludeUniqueKeys(!Boolean.FALSE.equals(d.isIncludeUniqueKeys()));
            database.setRecordVersionFields(new String[]{StringUtils.defaultString(d.getRecordVersionFields())});
            database.setRecordTimestampFields(new String[]{StringUtils.defaultString(d.getRecordTimestampFields())});
            database.setSyntheticPrimaryKeys(new String[]{StringUtils.defaultString(d.getSyntheticPrimaryKeys())});
            database.setOverridePrimaryKeys(new String[]{StringUtils.defaultString(d.getOverridePrimaryKeys())});
            database.setSyntheticIdentities(new String[]{StringUtils.defaultString(d.getSyntheticIdentities())});
            database.setConfiguredCustomTypes(d.getCustomTypes());
            database.setConfiguredEnumTypes(d.getEnumTypes());
            database.setConfiguredForcedTypes(d.getForcedTypes());
            if (d.getRegexFlags() != null) {
               database.setRegexFlags(d.getRegexFlags());
            }

            SchemaVersionProvider svp = null;
            CatalogVersionProvider cvp = null;
            if (!StringUtils.isBlank(d.getSchemaVersionProvider())) {
               try {
                  svp = (SchemaVersionProvider)Class.forName(d.getSchemaVersionProvider()).newInstance();
                  log.info("Using custom schema version provider : " + svp);
               } catch (Exception var24) {
                  if (d.getSchemaVersionProvider().toLowerCase().startsWith("select")) {
                     svp = new SQLSchemaVersionProvider(this.connection, d.getSchemaVersionProvider());
                     log.info("Using SQL schema version provider : " + d.getSchemaVersionProvider());
                  } else {
                     svp = new ConstantSchemaVersionProvider(d.getSchemaVersionProvider());
                  }
               }
            }

            if (!StringUtils.isBlank(d.getCatalogVersionProvider())) {
               try {
                  cvp = (CatalogVersionProvider)Class.forName(d.getCatalogVersionProvider()).newInstance();
                  log.info("Using custom catalog version provider : " + cvp);
               } catch (Exception var25) {
                  if (d.getCatalogVersionProvider().toLowerCase().startsWith("select")) {
                     cvp = new SQLCatalogVersionProvider(this.connection, d.getCatalogVersionProvider());
                     log.info("Using SQL catalog version provider : " + d.getCatalogVersionProvider());
                  } else {
                     cvp = new ConstantCatalogVersionProvider(d.getCatalogVersionProvider());
                  }
               }
            }

            if (svp == null) {
               svp = new ConstantSchemaVersionProvider((String)null);
            }

            if (cvp == null) {
               cvp = new ConstantCatalogVersionProvider((String)null);
            }

            database.setSchemaVersionProvider((SchemaVersionProvider)svp);
            database.setCatalogVersionProvider((CatalogVersionProvider)cvp);
            if (d.getEnumTypes().size() > 0) {
               log.warn("DEPRECATED", (Object)"The configuration property /configuration/generator/database/enumTypes is experimental and deprecated and will be removed in the future.");
            }

            if (Boolean.TRUE.equals(d.isDateAsTimestamp())) {
               log.warn("DEPRECATED", (Object)"The configuration property /configuration/generator/database/dateAsTimestamp is deprecated as it is superseded by custom bindings and converters. It will thus be removed in the future.");
            }

            if (d.isDateAsTimestamp() != null) {
               database.setDateAsTimestamp(d.isDateAsTimestamp());
            }

            if (d.isUnsignedTypes() != null) {
               database.setSupportsUnsignedTypes(d.isUnsignedTypes());
            }

            if (d.isIgnoreProcedureReturnValues() != null) {
               database.setIgnoreProcedureReturnValues(d.isIgnoreProcedureReturnValues());
            }

            if (Boolean.TRUE.equals(d.isIgnoreProcedureReturnValues())) {
               log.warn("DEPRECATED", (Object)"The <ignoreProcedureReturnValues/> flag is deprecated and used for backwards-compatibility only. It will be removed in the future.");
            }

            if (StringUtils.isBlank(g.getTarget().getPackageName())) {
               g.getTarget().setPackageName("org.jooq.generated");
            }

            if (StringUtils.isBlank(g.getTarget().getDirectory())) {
               g.getTarget().setDirectory("target/generated-sources/jooq");
            }

            if (StringUtils.isBlank(g.getTarget().getEncoding())) {
               g.getTarget().setEncoding("UTF-8");
            }

            generator.setTargetPackage(g.getTarget().getPackageName());
            generator.setTargetDirectory(g.getTarget().getDirectory());
            generator.setTargetEncoding(g.getTarget().getEncoding());
            if (g.getGenerate() == null) {
               g.setGenerate(new Generate());
            }

            if (g.getGenerate().isRelations() != null) {
               generator.setGenerateRelations(g.getGenerate().isRelations());
            }

            if (g.getGenerate().isDeprecated() != null) {
               generator.setGenerateDeprecated(g.getGenerate().isDeprecated());
            }

            if (g.getGenerate().isInstanceFields() != null) {
               generator.setGenerateInstanceFields(g.getGenerate().isInstanceFields());
            }

            if (g.getGenerate().isGeneratedAnnotation() != null) {
               generator.setGenerateGeneratedAnnotation(g.getGenerate().isGeneratedAnnotation());
            }

            if (g.getGenerate().isRoutines() != null) {
               generator.setGenerateRoutines(g.getGenerate().isRoutines());
            }

            if (g.getGenerate().isSequences() != null) {
               generator.setGenerateSequences(g.getGenerate().isSequences());
            }

            if (g.getGenerate().isUdts() != null) {
               generator.setGenerateUDTs(g.getGenerate().isUdts());
            }

            if (g.getGenerate().isTables() != null) {
               generator.setGenerateTables(g.getGenerate().isTables());
            }

            if (g.getGenerate().isRecords() != null) {
               generator.setGenerateRecords(g.getGenerate().isRecords());
            }

            if (g.getGenerate().isPojos() != null) {
               generator.setGeneratePojos(g.getGenerate().isPojos());
            }

            if (g.getGenerate().isImmutablePojos() != null) {
               generator.setGenerateImmutablePojos(g.getGenerate().isImmutablePojos());
            }

            if (g.getGenerate().isInterfaces() != null) {
               generator.setGenerateInterfaces(g.getGenerate().isInterfaces());
            }

            if (g.getGenerate().isImmutableInterfaces() != null) {
               generator.setGenerateImmutableInterfaces(g.getGenerate().isImmutableInterfaces());
            }

            if (g.getGenerate().isDaos() != null) {
               generator.setGenerateDaos(g.getGenerate().isDaos());
            }

            if (g.getGenerate().isJpaAnnotations() != null) {
               generator.setGenerateJPAAnnotations(g.getGenerate().isJpaAnnotations());
            }

            if (g.getGenerate().isValidationAnnotations() != null) {
               generator.setGenerateValidationAnnotations(g.getGenerate().isValidationAnnotations());
            }

            if (g.getGenerate().isSpringAnnotations() != null) {
               generator.setGenerateSpringAnnotations(g.getGenerate().isSpringAnnotations());
            }

            if (g.getGenerate().isQueues() != null) {
               generator.setGenerateQueues(g.getGenerate().isQueues());
            }

            if (g.getGenerate().isLinks() != null) {
               generator.setGenerateLinks(g.getGenerate().isLinks());
            }

            if (g.getGenerate().isGlobalLinkReferences() != null) {
               generator.setGenerateGlobalLinkReferences(g.getGenerate().isGlobalLinkReferences());
            }

            if (g.getGenerate().isGlobalObjectReferences() != null) {
               generator.setGenerateGlobalObjectReferences(g.getGenerate().isGlobalObjectReferences());
            }

            if (g.getGenerate().isGlobalCatalogReferences() != null) {
               generator.setGenerateGlobalCatalogReferences(g.getGenerate().isGlobalCatalogReferences());
            }

            if (g.getGenerate().isGlobalSchemaReferences() != null) {
               generator.setGenerateGlobalSchemaReferences(g.getGenerate().isGlobalSchemaReferences());
            }

            if (g.getGenerate().isGlobalRoutineReferences() != null) {
               generator.setGenerateGlobalRoutineReferences(g.getGenerate().isGlobalRoutineReferences());
            }

            if (g.getGenerate().isGlobalSequenceReferences() != null) {
               generator.setGenerateGlobalSequenceReferences(g.getGenerate().isGlobalSequenceReferences());
            }

            if (g.getGenerate().isGlobalTableReferences() != null) {
               generator.setGenerateGlobalTableReferences(g.getGenerate().isGlobalTableReferences());
            }

            if (g.getGenerate().isGlobalUDTReferences() != null) {
               generator.setGenerateGlobalUDTReferences(g.getGenerate().isGlobalUDTReferences());
            }

            if (g.getGenerate().isGlobalQueueReferences() != null) {
               generator.setGenerateGlobalQueueReferences(g.getGenerate().isGlobalQueueReferences());
            }

            if (g.getGenerate().isGlobalLinkReferences() != null) {
               generator.setGenerateGlobalLinkReferences(g.getGenerate().isGlobalLinkReferences());
            }

            if (g.getGenerate().isFluentSetters() != null) {
               generator.setFluentSetters(g.getGenerate().isFluentSetters());
            }

            if (g.getGenerate().isPojosEqualsAndHashCode() != null) {
               generator.setGeneratePojosEqualsAndHashCode(g.getGenerate().isPojosEqualsAndHashCode());
            }

            if (g.getGenerate().isPojosToString() != null) {
               generator.setGeneratePojosToString(g.getGenerate().isPojosToString());
            }

            if (g.getGenerate().getFullyQualifiedTypes() != null) {
               generator.setGenerateFullyQualifiedTypes(g.getGenerate().getFullyQualifiedTypes());
            }

            if (g.getGenerate().isJavaTimeTypes() != null) {
               generator.setGenerateJavaTimeTypes(g.getGenerate().isJavaTimeTypes());
            }

            if (g.getGenerate().isEmptyCatalogs() != null) {
               generator.setGenerateEmptyCatalogs(g.getGenerate().isEmptyCatalogs());
            }

            if (g.getGenerate().isEmptySchemas() != null) {
               generator.setGenerateEmptySchemas(g.getGenerate().isEmptySchemas());
            }

            if (g.getDatabase() == null) {
               g.setDatabase(new org.jooq.util.jaxb.Database());
            }

            if (!StringUtils.isBlank(g.getDatabase().getSchemaVersionProvider())) {
               generator.setUseSchemaVersionProvider(true);
            }

            if (!StringUtils.isBlank(g.getDatabase().getCatalogVersionProvider())) {
               generator.setUseCatalogVersionProvider(true);
            }

            if (g.getDatabase().isTableValuedFunctions() != null) {
               generator.setGenerateTableValuedFunctions(g.getDatabase().isTableValuedFunctions());
            } else {
               generator.setGenerateTableValuedFunctions(true);
            }

            ((GeneratorStrategy)strategy).setInstanceFields(generator.generateInstanceFields());
            generator.generate(database);
         } finally {
            if (this.close && this.connection != null) {
               this.connection.close();
            }

         }

      }
   }

   private Properties properties(List<Property> properties) {
      Properties result = new Properties();
      Iterator var3 = properties.iterator();

      while(var3.hasNext()) {
         Property p = (Property)var3.next();
         result.put(p.getKey(), p.getValue());
      }

      return result;
   }

   private String driverClass(Jdbc j) {
      String result = j.getDriver();
      if (result == null) {
         result = JDBCUtils.driver(j.getUrl());
         log.info("Database", (Object)("Inferring driver " + result + " from URL " + j.getUrl()));
      }

      return result;
   }

   private Class<? extends Database> databaseClass(Jdbc j) {
      return this.databaseClass(j.getUrl());
   }

   private Class<? extends Database> databaseClass(Connection c) {
      try {
         return this.databaseClass(c.getMetaData().getURL());
      } catch (SQLException var3) {
         throw new GeneratorException("Error when reading URL from JDBC connection", var3);
      }
   }

   private Class<? extends Database> databaseClass(String url) {
      if (StringUtils.isBlank(url)) {
         throw new GeneratorException("No JDBC URL configured.");
      } else {
         Class<? extends Database> result = Databases.databaseClass(JDBCUtils.dialect(url));
         log.info("Database", (Object)("Inferring database " + result.getName() + " from URL " + url));
         return result;
      }
   }

   private Class<?> loadClass(String className) throws ClassNotFoundException {
      try {
         if (this.loader == null) {
            try {
               return Class.forName(className);
            } catch (ClassNotFoundException var3) {
               return Thread.currentThread().getContextClassLoader().loadClass(className);
            }
         } else {
            return this.loader.loadClass(className);
         }
      } catch (ClassNotFoundException var4) {
         if (className.startsWith("org.jooq.util.") && className.endsWith("Database")) {
            log.warn("Type not found", (Object)"Your configured database type was not found. This can have several reasons:\n- You want to use a commercial jOOQ Edition, but you pulled the Open Source Edition from Maven Central.\n- You have mis-typed your class name.");
         }

         throw var4;
      }
   }

   private static String trim(String string) {
      return string == null ? null : string.trim();
   }

   public static long copyLarge(InputStream input, OutputStream output) throws IOException {
      byte[] buffer = new byte[4096];
      long count = 0L;

      int n;
      for(boolean var5 = false; -1 != (n = input.read(buffer)); count += (long)n) {
         output.write(buffer, 0, n);
      }

      return count;
   }

   public static Configuration load(InputStream in) throws IOException {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      copyLarge(in, out);
      String xml = out.toString();
      xml = xml.replaceAll("<(\\w+:)?configuration xmlns(:\\w+)?=\"http://www.jooq.org/xsd/jooq-codegen-\\d+\\.\\d+\\.\\d+.xsd\">", "<$1configuration xmlns$2=\"http://www.jooq.org/xsd/jooq-codegen-3.9.0.xsd\">");
      xml = xml.replace("<configuration>", "<configuration xmlns=\"http://www.jooq.org/xsd/jooq-codegen-3.9.0.xsd\">");

      try {
         SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
         javax.xml.validation.Schema schema = sf.newSchema(GenerationTool.class.getResource("/xsd/jooq-codegen-3.9.0.xsd"));
         JAXBContext ctx = JAXBContext.newInstance(Configuration.class);
         Unmarshaller unmarshaller = ctx.createUnmarshaller();
         unmarshaller.setSchema(schema);
         unmarshaller.setEventHandler(new ValidationEventHandler() {
            public boolean handleEvent(ValidationEvent event) {
               GenerationTool.log.warn("Unmarshal warning", (Object)event.getMessage());
               return true;
            }
         });
         return (Configuration)unmarshaller.unmarshal(new StringReader(xml));
      } catch (Exception var7) {
         throw new GeneratorException("Error while reading XML configuration", var7);
      }
   }
}
