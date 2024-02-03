package org.jooq.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.xml.bind.JAXB;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListenerProvider;
import org.jooq.Name;
import org.jooq.Query;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteListener;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.jooq.impl.SQLDataType;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StopWatch;
import org.jooq.tools.StringUtils;
import org.jooq.tools.csv.CSVReader;
import org.jooq.util.jaxb.Catalog;
import org.jooq.util.jaxb.CustomType;
import org.jooq.util.jaxb.EnumType;
import org.jooq.util.jaxb.ForcedType;
import org.jooq.util.jaxb.RegexFlag;
import org.jooq.util.jaxb.Schema;

public abstract class AbstractDatabase implements Database {
   private static final JooqLogger log = JooqLogger.getLogger(AbstractDatabase.class);
   private Properties properties;
   private SQLDialect dialect;
   private Connection connection;
   private List<RegexFlag> regexFlags;
   private List<Database.Filter> filters = new ArrayList();
   private String[] excludes;
   private String[] includes;
   private boolean includeExcludeColumns;
   private boolean includeTables = true;
   private boolean includeRoutines = true;
   private boolean includePackages = true;
   private boolean includeUDTs = true;
   private boolean includeSequences = true;
   private boolean includePrimaryKeys = true;
   private boolean includeUniqueKeys = true;
   private boolean includeForeignKeys = true;
   private String[] recordVersionFields;
   private String[] recordTimestampFields;
   private String[] syntheticPrimaryKeys;
   private String[] overridePrimaryKeys;
   private String[] syntheticIdentities;
   private boolean supportsUnsignedTypes;
   private boolean ignoreProcedureReturnValues;
   private boolean dateAsTimestamp;
   private List<Catalog> configuredCatalogs;
   private List<Schema> configuredSchemata;
   private List<CustomType> configuredCustomTypes;
   private List<EnumType> configuredEnumTypes;
   private List<ForcedType> configuredForcedTypes;
   private SchemaVersionProvider schemaVersionProvider;
   private CatalogVersionProvider catalogVersionProvider;
   private List<String> inputCatalogs;
   private List<String> inputSchemata;
   private Map<String, List<String>> inputSchemataPerCatalog;
   private List<CatalogDefinition> catalogs;
   private List<SchemaDefinition> schemata;
   private List<SequenceDefinition> sequences;
   private List<IdentityDefinition> identities;
   private List<UniqueKeyDefinition> uniqueKeys;
   private List<ForeignKeyDefinition> foreignKeys;
   private List<CheckConstraintDefinition> checkConstraints;
   private List<TableDefinition> tables;
   private List<EnumDefinition> enums;
   private List<DomainDefinition> domains;
   private List<UDTDefinition> udts;
   private List<ArrayDefinition> arrays;
   private List<RoutineDefinition> routines;
   private List<PackageDefinition> packages;
   private Relations relations;
   private boolean includeRelations = true;
   private boolean tableValuedFunctions = true;
   private transient Map<SchemaDefinition, List<SequenceDefinition>> sequencesBySchema;
   private transient Map<SchemaDefinition, List<IdentityDefinition>> identitiesBySchema;
   private transient Map<SchemaDefinition, List<UniqueKeyDefinition>> uniqueKeysBySchema;
   private transient Map<SchemaDefinition, List<ForeignKeyDefinition>> foreignKeysBySchema;
   private transient Map<SchemaDefinition, List<CheckConstraintDefinition>> checkConstraintsBySchema;
   private transient Map<SchemaDefinition, List<TableDefinition>> tablesBySchema;
   private transient Map<SchemaDefinition, List<EnumDefinition>> enumsBySchema;
   private transient Map<SchemaDefinition, List<UDTDefinition>> udtsBySchema;
   private transient Map<SchemaDefinition, List<ArrayDefinition>> arraysBySchema;
   private transient Map<SchemaDefinition, List<RoutineDefinition>> routinesBySchema;
   private transient Map<SchemaDefinition, List<PackageDefinition>> packagesBySchema;
   private transient boolean initialised;
   private final List<Definition> all = new ArrayList();
   private final List<Definition> included = new ArrayList();
   private final List<Definition> excluded = new ArrayList();
   private final Map<Table<?>, Boolean> exists = new HashMap();
   private final Map<String, Pattern> patterns = new HashMap();

   protected AbstractDatabase() {
   }

   public final SQLDialect getDialect() {
      if (this.dialect == null) {
         this.dialect = this.create().configuration().dialect();
      }

      return this.dialect;
   }

   public final void setConnection(Connection connection) {
      this.connection = connection;
   }

   public final Connection getConnection() {
      return this.connection;
   }

   public final DSLContext create() {
      return this.create(false);
   }

   protected final DSLContext create(boolean muteExceptions) {
      final Configuration configuration = this.create0().configuration();
      if (muteExceptions) {
         return DSL.using(configuration);
      } else {
         final Settings newSettings = SettingsTools.clone(configuration.settings()).withRenderFormatted(true);
         ExecuteListenerProvider[] oldProviders = configuration.executeListenerProviders();
         ExecuteListenerProvider[] newProviders = new ExecuteListenerProvider[oldProviders.length + 1];
         System.arraycopy(oldProviders, 0, newProviders, 0, oldProviders.length);
         newProviders[oldProviders.length] = new DefaultExecuteListenerProvider(new DefaultExecuteListener() {
            public void start(ExecuteContext ctx) {
               if (!AbstractDatabase.this.initialised) {
                  DSL.using(configuration).selectOne().fetch();
                  AbstractDatabase.this.initialised = true;
               }

            }

            public void executeStart(ExecuteContext ctx) {
               ctx.data("org.jooq.util.AbstractDatabase.watch", new StopWatch());
            }

            public void executeEnd(ExecuteContext ctx) {
               StopWatch watch = (StopWatch)ctx.data("org.jooq.util.AbstractDatabase.watch");
               if (watch.split() > TimeUnit.SECONDS.toNanos(5L)) {
                  watch.splitWarn("Slow SQL");
                  AbstractDatabase.log.warn("Slow SQL", "jOOQ Meta executed a slow query (slower than 5 seconds)\n\nPlease report this bug here: https://github.com/jOOQ/jOOQ/issues/new\n\n" + this.formatted(ctx.query()), new null.SQLPerformanceWarning());
               }

            }

            public void exception(ExecuteContext ctx) {
               AbstractDatabase.log.warn("SQL exception", (Object)("Exception while executing meta query: " + (ctx.sqlException() != null ? ctx.sqlException().getMessage() : (ctx.exception() != null ? ctx.exception().getMessage() : "No exception available")) + "\n\nPlease report this bug here: https://github.com/jOOQ/jOOQ/issues/new\n\n" + this.formatted(ctx.query())));
            }

            private String formatted(Query query) {
               return DSL.using(configuration.derive(newSettings)).renderInlined(query);
            }

            class SQLPerformanceWarning extends Exception {
            }
         });
         return DSL.using(configuration.derive(newProviders));
      }
   }

   public final boolean exists(Table<?> table) {
      Boolean result = (Boolean)this.exists.get(table);
      if (result == null) {
         try {
            this.create(true).selectOne().from(table).where(new Condition[]{DSL.falseCondition()}).fetch();
            result = true;
         } catch (DataAccessException var4) {
            result = false;
         }

         this.exists.put(table, result);
      }

      return result;
   }

   public final boolean existAll(Table<?>... t) {
      Table[] var2 = t;
      int var3 = t.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Table<?> table = var2[var4];
         if (!this.exists(table)) {
            return false;
         }
      }

      return true;
   }

   final Pattern pattern(String regex) {
      Pattern pattern = (Pattern)this.patterns.get(regex);
      if (pattern == null) {
         int flags = 0;
         List<RegexFlag> list = new ArrayList(this.getRegexFlags());
         if (list.isEmpty()) {
            list.add(RegexFlag.COMMENTS);
            list.add(RegexFlag.CASE_INSENSITIVE);
         }

         Iterator var5 = list.iterator();

         while(var5.hasNext()) {
            RegexFlag flag = (RegexFlag)var5.next();
            switch(flag) {
            case CANON_EQ:
               flags |= 128;
               break;
            case CASE_INSENSITIVE:
               flags |= 2;
               break;
            case COMMENTS:
               flags |= 4;
               break;
            case DOTALL:
               flags |= 32;
               break;
            case LITERAL:
               flags |= 16;
               break;
            case MULTILINE:
               flags |= 8;
               break;
            case UNICODE_CASE:
               flags |= 64;
               break;
            case UNICODE_CHARACTER_CLASS:
               flags |= 256;
               break;
            case UNIX_LINES:
               flags |= 1;
            }
         }

         pattern = Pattern.compile(regex, flags);
         this.patterns.put(regex, pattern);
      }

      return pattern;
   }

   public final List<CatalogDefinition> getCatalogs() {
      if (this.catalogs == null) {
         this.catalogs = new ArrayList();

         try {
            this.catalogs = this.getCatalogs0();
         } catch (Exception var2) {
            log.error("Could not load catalogs", (Throwable)var2);
         }
      }

      return this.catalogs;
   }

   public final CatalogDefinition getCatalog(String inputName) {
      Iterator var2 = this.getCatalogs().iterator();

      CatalogDefinition catalog;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         catalog = (CatalogDefinition)var2.next();
      } while(!catalog.getName().equals(inputName));

      return catalog;
   }

   public final List<SchemaDefinition> getSchemata() {
      if (this.schemata == null) {
         this.schemata = new ArrayList();

         try {
            this.schemata = this.getSchemata0();
         } catch (Exception var3) {
            log.error("Could not load schemata", (Throwable)var3);
         }

         Iterator it = this.schemata.iterator();

         while(it.hasNext()) {
            SchemaDefinition schema = (SchemaDefinition)it.next();
            if (!this.getInputSchemata().contains(schema.getName())) {
               it.remove();
            }
         }

         if (this.schemata.isEmpty()) {
            log.warn("No schemata were loaded", (Object)("Please check your connection settings, and whether your database (and your database version!) is really supported by jOOQ. Also, check the case-sensitivity in your configured <inputSchema/> elements : " + this.inputSchemataPerCatalog));
         }
      }

      return this.schemata;
   }

   public final List<SchemaDefinition> getSchemata(CatalogDefinition catalog) {
      List<SchemaDefinition> result = new ArrayList();
      Iterator var3 = this.getSchemata().iterator();

      while(var3.hasNext()) {
         SchemaDefinition schema = (SchemaDefinition)var3.next();
         if (catalog.equals(schema.getCatalog())) {
            result.add(schema);
         }
      }

      return result;
   }

   public final SchemaDefinition getSchema(String inputName) {
      Iterator var2 = this.getSchemata().iterator();

      SchemaDefinition schema;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         schema = (SchemaDefinition)var2.next();
      } while(!schema.getName().equals(inputName));

      return schema;
   }

   public final List<String> getInputCatalogs() {
      if (this.inputCatalogs == null) {
         this.inputCatalogs = new ArrayList();
         Iterator var1;
         if (this.configuredCatalogs.size() == 1 && StringUtils.isBlank(((Catalog)this.configuredCatalogs.get(0)).getInputCatalog())) {
            try {
               var1 = this.getCatalogs0().iterator();

               while(var1.hasNext()) {
                  CatalogDefinition catalog = (CatalogDefinition)var1.next();
                  this.inputCatalogs.add(catalog.getName());
               }
            } catch (Exception var3) {
               log.error("Could not load catalogs", (Throwable)var3);
            }
         } else {
            var1 = this.configuredCatalogs.iterator();

            while(var1.hasNext()) {
               Catalog catalog = (Catalog)var1.next();
               this.inputCatalogs.add(catalog.getInputCatalog());
            }
         }
      }

      return this.inputCatalogs;
   }

   public final List<String> getInputSchemata() {
      if (this.inputSchemataPerCatalog == null) {
         this.inputSchemata = new ArrayList();
         this.inputSchemataPerCatalog = new LinkedHashMap();
         if (this.configuredSchemata.size() == 1 && StringUtils.isBlank(((Schema)this.configuredSchemata.get(0)).getInputSchema())) {
            this.initAllSchemata();
         } else if (this.configuredCatalogs.size() == 1 && StringUtils.isBlank(((Catalog)this.configuredCatalogs.get(0)).getInputCatalog()) && ((Catalog)this.configuredCatalogs.get(0)).getSchemata().size() == 1 && StringUtils.isBlank(((Schema)((Catalog)this.configuredCatalogs.get(0)).getSchemata().get(0)).getInputSchema())) {
            this.initAllSchemata();
         } else {
            Iterator var1;
            if (this.configuredCatalogs.isEmpty()) {
               this.inputSchemataPerCatalog.put("", this.inputSchemata);
               var1 = this.configuredSchemata.iterator();

               while(var1.hasNext()) {
                  Schema schema = (Schema)var1.next();
                  this.inputSchemata.add(schema.getInputSchema());
               }
            } else {
               var1 = this.configuredCatalogs.iterator();

               while(var1.hasNext()) {
                  Catalog catalog = (Catalog)var1.next();

                  String inputSchema;
                  Object list;
                  for(Iterator var3 = catalog.getSchemata().iterator(); var3.hasNext(); ((List)list).add(inputSchema)) {
                     Schema schema = (Schema)var3.next();
                     inputSchema = schema.getInputSchema();
                     this.inputSchemata.add(inputSchema);
                     list = (List)this.inputSchemataPerCatalog.get(catalog.getInputCatalog());
                     if (list == null) {
                        list = new ArrayList();
                        this.inputSchemataPerCatalog.put(catalog.getInputCatalog(), list);
                     }
                  }
               }
            }
         }
      }

      return this.inputSchemata;
   }

   private void initAllSchemata() {
      SchemaDefinition schema;
      Object list;
      try {
         for(Iterator var1 = this.getSchemata0().iterator(); var1.hasNext(); ((List)list).add(schema.getName())) {
            schema = (SchemaDefinition)var1.next();
            this.inputSchemata.add(schema.getName());
            list = (List)this.inputSchemataPerCatalog.get(schema.getCatalog().getName());
            if (list == null) {
               list = new ArrayList();
               this.inputSchemataPerCatalog.put(schema.getCatalog().getName(), list);
            }
         }
      } catch (Exception var4) {
         log.error("Could not load schemata", (Throwable)var4);
      }

   }

   public final List<String> getInputSchemata(CatalogDefinition catalog) {
      return this.getInputSchemata(catalog.getInputName());
   }

   public final List<String> getInputSchemata(String catalog) {
      this.getInputSchemata();
      return this.inputSchemataPerCatalog.containsKey(catalog) ? (List)this.inputSchemataPerCatalog.get(catalog) : Collections.emptyList();
   }

   /** @deprecated */
   @Deprecated
   public String getOutputCatalog(String inputCatalog) {
      Iterator var2 = this.configuredCatalogs.iterator();

      Catalog catalog;
      do {
         if (!var2.hasNext()) {
            return inputCatalog;
         }

         catalog = (Catalog)var2.next();
      } while(!inputCatalog.equals(catalog.getInputCatalog()));

      return catalog.getOutputCatalog();
   }

   /** @deprecated */
   @Deprecated
   public String getOutputSchema(String inputSchema) {
      Iterator var2 = this.configuredSchemata.iterator();

      Schema schema;
      do {
         if (!var2.hasNext()) {
            return inputSchema;
         }

         schema = (Schema)var2.next();
      } while(!inputSchema.equals(schema.getInputSchema()));

      return schema.getOutputSchema();
   }

   public String getOutputSchema(String inputCatalog, String inputSchema) {
      Iterator var3 = this.configuredCatalogs.iterator();

      while(true) {
         Catalog catalog;
         do {
            if (!var3.hasNext()) {
               return inputSchema;
            }

            catalog = (Catalog)var3.next();
         } while(!inputCatalog.equals(catalog.getInputCatalog()));

         Iterator var5 = catalog.getSchemata().iterator();

         while(var5.hasNext()) {
            Schema schema = (Schema)var5.next();
            if (inputSchema.equals(schema.getInputSchema())) {
               return schema.getOutputSchema();
            }
         }
      }
   }

   public final void setConfiguredCatalogs(List<Catalog> catalogs) {
      this.configuredCatalogs = catalogs;
   }

   public final void setConfiguredSchemata(List<Schema> schemata) {
      this.configuredSchemata = schemata;
   }

   public final void setProperties(Properties properties) {
      this.properties = properties;
   }

   public final Properties getProperties() {
      return this.properties;
   }

   public final List<Database.Filter> getFilters() {
      if (this.filters == null) {
         this.filters = new ArrayList();
      }

      return Collections.unmodifiableList(this.filters);
   }

   public final void addFilter(Database.Filter filter) {
      this.filters.add(filter);
   }

   public final void setExcludes(String[] excludes) {
      this.excludes = excludes;
   }

   public final String[] getExcludes() {
      if (this.excludes == null) {
         this.excludes = new String[0];
      }

      return this.excludes;
   }

   public final void setIncludes(String[] includes) {
      this.includes = includes;
   }

   public final String[] getIncludes() {
      if (this.includes == null) {
         this.includes = new String[0];
      }

      return this.includes;
   }

   public final void setIncludeExcludeColumns(boolean includeExcludeColumns) {
      this.includeExcludeColumns = includeExcludeColumns;
   }

   public final boolean getIncludeExcludeColumns() {
      return this.includeExcludeColumns;
   }

   public final boolean getIncludeTables() {
      return this.includeTables;
   }

   public final void setIncludeTables(boolean includeTables) {
      this.includeTables = includeTables;
   }

   public final boolean getIncludeRoutines() {
      return this.includeRoutines;
   }

   public final void setIncludeRoutines(boolean includeRoutines) {
      this.includeRoutines = includeRoutines;
   }

   public final boolean getIncludePackages() {
      return this.includePackages;
   }

   public final void setIncludePackages(boolean includePackages) {
      this.includePackages = includePackages;
   }

   public final boolean getIncludeUDTs() {
      return this.includeUDTs;
   }

   public final void setIncludeUDTs(boolean includeUDTs) {
      this.includeUDTs = includeUDTs;
   }

   public final boolean getIncludeSequences() {
      return this.includeSequences;
   }

   public final void setIncludeSequences(boolean includeSequences) {
      this.includeSequences = includeSequences;
   }

   public final boolean getIncludePrimaryKeys() {
      return this.includePrimaryKeys;
   }

   public final void setIncludePrimaryKeys(boolean includePrimaryKeys) {
      this.includePrimaryKeys = includePrimaryKeys;
   }

   public final boolean getIncludeUniqueKeys() {
      return this.includeUniqueKeys;
   }

   public final void setIncludeUniqueKeys(boolean includeUniqueKeys) {
      this.includeUniqueKeys = includeUniqueKeys;
   }

   public final boolean getIncludeForeignKeys() {
      return this.includeForeignKeys;
   }

   public final void setIncludeForeignKeys(boolean includeForeignKeys) {
      this.includeForeignKeys = includeForeignKeys;
   }

   public final void setRegexFlags(List<RegexFlag> regexFlags) {
      this.regexFlags = regexFlags;
   }

   public final List<RegexFlag> getRegexFlags() {
      if (this.regexFlags == null) {
         this.regexFlags = new ArrayList();
      }

      return this.regexFlags;
   }

   public void setRecordVersionFields(String[] recordVersionFields) {
      this.recordVersionFields = recordVersionFields;
   }

   public String[] getRecordVersionFields() {
      if (this.recordVersionFields == null) {
         this.recordVersionFields = new String[0];
      }

      return this.recordVersionFields;
   }

   public void setRecordTimestampFields(String[] recordTimestampFields) {
      this.recordTimestampFields = recordTimestampFields;
   }

   public String[] getRecordTimestampFields() {
      if (this.recordTimestampFields == null) {
         this.recordTimestampFields = new String[0];
      }

      return this.recordTimestampFields;
   }

   public void setSyntheticPrimaryKeys(String[] syntheticPrimaryKeys) {
      this.syntheticPrimaryKeys = syntheticPrimaryKeys;
   }

   public String[] getSyntheticPrimaryKeys() {
      if (this.syntheticPrimaryKeys == null) {
         this.syntheticPrimaryKeys = new String[0];
      }

      return this.syntheticPrimaryKeys;
   }

   public void setOverridePrimaryKeys(String[] overridePrimaryKeys) {
      this.overridePrimaryKeys = overridePrimaryKeys;
   }

   public String[] getOverridePrimaryKeys() {
      if (this.overridePrimaryKeys == null) {
         this.overridePrimaryKeys = new String[0];
      }

      return this.overridePrimaryKeys;
   }

   public void setSyntheticIdentities(String[] syntheticIdentities) {
      this.syntheticIdentities = syntheticIdentities;
   }

   public final String[] getSyntheticIdentities() {
      if (this.syntheticIdentities == null) {
         this.syntheticIdentities = new String[0];
      }

      return this.syntheticIdentities;
   }

   public final void setConfiguredEnumTypes(List<EnumType> configuredEnumTypes) {
      this.configuredEnumTypes = configuredEnumTypes;
   }

   public final List<EnumType> getConfiguredEnumTypes() {
      return this.configuredEnumTypes;
   }

   public final void setConfiguredCustomTypes(List<CustomType> configuredCustomTypes) {
      this.configuredCustomTypes = configuredCustomTypes;
   }

   public final List<CustomType> getConfiguredCustomTypes() {
      if (this.configuredCustomTypes == null) {
         this.configuredCustomTypes = new ArrayList();
      }

      return this.configuredCustomTypes;
   }

   public final CustomType getConfiguredCustomType(String typeName) {
      if (typeName == null) {
         return null;
      } else {
         Iterator it1 = this.configuredCustomTypes.iterator();

         CustomType type;
         label79:
         do {
            for(; it1.hasNext(); it1.remove()) {
               type = (CustomType)it1.next();
               if (type != null && (type.getName() != null || type.getType() != null)) {
                  continue label79;
               }

               try {
                  StringWriter writer = new StringWriter();
                  JAXB.marshal(type, writer);
                  log.warn("Invalid custom type encountered: " + writer.toString());
               } catch (Exception var5) {
                  log.warn("Invalid custom type encountered: " + type);
               }
            }

            Iterator it2 = this.configuredForcedTypes.iterator();

            while(true) {
               while(it2.hasNext()) {
                  ForcedType type = (ForcedType)it2.next();
                  if (StringUtils.isBlank(type.getName())) {
                     if (StringUtils.isBlank(type.getUserType())) {
                        log.warn("Bad configuration for <forcedType/>. Either <name/> or <userType/> is required: " + this.toString(type));
                        it2.remove();
                        continue;
                     }

                     if (StringUtils.isBlank(type.getBinding()) && StringUtils.isBlank(type.getConverter())) {
                        log.warn("Bad configuration for <forcedType/>. Either <binding/> or <converter/> is required: " + this.toString(type));
                        it2.remove();
                        continue;
                     }
                  } else {
                     if (!StringUtils.isBlank(type.getUserType())) {
                        log.warn("Bad configuration for <forcedType/>. <userType/> is not allowed when <name/> is provided: " + this.toString(type));
                        type.setUserType((String)null);
                     }

                     if (!StringUtils.isBlank(type.getBinding())) {
                        log.warn("Bad configuration for <forcedType/>. <binding/> is not allowed when <name/> is provided: " + this.toString(type));
                        type.setBinding((String)null);
                     }

                     if (!StringUtils.isBlank(type.getConverter())) {
                        log.warn("Bad configuration for <forcedType/>. <converter/> is not allowed when <name/> is provided: " + this.toString(type));
                        type.setConverter((String)null);
                     }
                  }

                  if (type.getUserType() != null && StringUtils.equals(type.getUserType(), typeName)) {
                     return AbstractTypedElementDefinition.customType(this, type);
                  }
               }

               return null;
            }
         } while(!StringUtils.equals(type.getType() != null ? type.getType() : type.getName(), typeName));

         return type;
      }
   }

   private final String toString(ForcedType type) {
      StringWriter writer = new StringWriter();
      JAXB.marshal(type, writer);
      return writer.toString();
   }

   public final void setConfiguredForcedTypes(List<ForcedType> configuredForcedTypes) {
      this.configuredForcedTypes = configuredForcedTypes;
   }

   public final List<ForcedType> getConfiguredForcedTypes() {
      if (this.configuredForcedTypes == null) {
         this.configuredForcedTypes = new ArrayList();
      }

      return this.configuredForcedTypes;
   }

   public final SchemaVersionProvider getSchemaVersionProvider() {
      return this.schemaVersionProvider;
   }

   public final void setSchemaVersionProvider(SchemaVersionProvider schemaVersionProvider) {
      this.schemaVersionProvider = schemaVersionProvider;
   }

   public final CatalogVersionProvider getCatalogVersionProvider() {
      return this.catalogVersionProvider;
   }

   public final void setCatalogVersionProvider(CatalogVersionProvider catalogVersionProvider) {
      this.catalogVersionProvider = catalogVersionProvider;
   }

   public final void setSupportsUnsignedTypes(boolean supportsUnsignedTypes) {
      this.supportsUnsignedTypes = supportsUnsignedTypes;
   }

   public final boolean supportsUnsignedTypes() {
      return this.supportsUnsignedTypes;
   }

   public final void setIgnoreProcedureReturnValues(boolean ignoreProcedureReturnValues) {
      this.ignoreProcedureReturnValues = ignoreProcedureReturnValues;
   }

   public final boolean ignoreProcedureReturnValues() {
      return this.ignoreProcedureReturnValues;
   }

   public final void setDateAsTimestamp(boolean dateAsTimestamp) {
      this.dateAsTimestamp = dateAsTimestamp;
   }

   public final boolean dateAsTimestamp() {
      return this.dateAsTimestamp;
   }

   public final void setIncludeRelations(boolean includeRelations) {
      this.includeRelations = includeRelations;
   }

   public final boolean includeRelations() {
      return this.includeRelations;
   }

   public final void setTableValuedFunctions(boolean tableValuedFunctions) {
      this.tableValuedFunctions = tableValuedFunctions;
   }

   public final boolean tableValuedFunctions() {
      return this.tableValuedFunctions;
   }

   public final List<SequenceDefinition> getSequences(SchemaDefinition schema) {
      if (this.sequences == null) {
         this.sequences = new ArrayList();
         if (this.getIncludeSequences()) {
            try {
               List<SequenceDefinition> s = this.getSequences0();
               this.sequences = this.filterExcludeInclude(s);
               log.info("Sequences fetched", (Object)fetchedSize(s, this.sequences));
            } catch (Exception var3) {
               log.error("Error while fetching sequences", (Throwable)var3);
            }
         } else {
            log.info("Sequences excluded");
         }
      }

      if (this.sequencesBySchema == null) {
         this.sequencesBySchema = new LinkedHashMap();
      }

      return this.filterSchema(this.sequences, schema, this.sequencesBySchema);
   }

   public final List<IdentityDefinition> getIdentities(SchemaDefinition schema) {
      if (this.identities == null) {
         this.identities = new ArrayList();
         Iterator var2 = this.getSchemata().iterator();

         while(var2.hasNext()) {
            SchemaDefinition s = (SchemaDefinition)var2.next();
            Iterator var4 = this.getTables(s).iterator();

            while(var4.hasNext()) {
               TableDefinition table = (TableDefinition)var4.next();
               IdentityDefinition identity = table.getIdentity();
               if (identity != null) {
                  this.identities.add(identity);
               }
            }
         }
      }

      if (this.identitiesBySchema == null) {
         this.identitiesBySchema = new LinkedHashMap();
      }

      return this.filterSchema(this.identities, schema, this.identitiesBySchema);
   }

   public final List<UniqueKeyDefinition> getUniqueKeys(SchemaDefinition schema) {
      if (this.uniqueKeys == null) {
         this.uniqueKeys = new ArrayList();
         if (this.getIncludeUniqueKeys()) {
            Iterator var2 = this.getSchemata().iterator();

            while(var2.hasNext()) {
               SchemaDefinition s = (SchemaDefinition)var2.next();
               Iterator var4 = this.getTables(s).iterator();

               while(var4.hasNext()) {
                  TableDefinition table = (TableDefinition)var4.next();
                  Iterator var6 = table.getUniqueKeys().iterator();

                  while(var6.hasNext()) {
                     UniqueKeyDefinition uniqueKey = (UniqueKeyDefinition)var6.next();
                     this.uniqueKeys.add(uniqueKey);
                  }
               }
            }
         }
      }

      if (this.uniqueKeysBySchema == null) {
         this.uniqueKeysBySchema = new LinkedHashMap();
      }

      return this.filterSchema(this.uniqueKeys, schema, this.uniqueKeysBySchema);
   }

   public final List<ForeignKeyDefinition> getForeignKeys(SchemaDefinition schema) {
      if (this.foreignKeys == null) {
         this.foreignKeys = new ArrayList();
         if (this.getIncludeForeignKeys()) {
            Iterator var2 = this.getSchemata().iterator();

            while(var2.hasNext()) {
               SchemaDefinition s = (SchemaDefinition)var2.next();
               Iterator var4 = this.getTables(s).iterator();

               while(var4.hasNext()) {
                  TableDefinition table = (TableDefinition)var4.next();
                  Iterator var6 = table.getForeignKeys().iterator();

                  while(var6.hasNext()) {
                     ForeignKeyDefinition foreignKey = (ForeignKeyDefinition)var6.next();
                     this.foreignKeys.add(foreignKey);
                  }
               }
            }
         }
      }

      if (this.foreignKeysBySchema == null) {
         this.foreignKeysBySchema = new LinkedHashMap();
      }

      return this.filterSchema(this.foreignKeys, schema, this.foreignKeysBySchema);
   }

   public final List<CheckConstraintDefinition> getCheckConstraints(SchemaDefinition schema) {
      if (this.checkConstraints == null) {
         this.checkConstraints = new ArrayList();
         Iterator var2 = this.getSchemata().iterator();

         while(var2.hasNext()) {
            SchemaDefinition s = (SchemaDefinition)var2.next();
            Iterator var4 = this.getTables(s).iterator();

            while(var4.hasNext()) {
               TableDefinition table = (TableDefinition)var4.next();
               Iterator var6 = table.getCheckConstraints().iterator();

               while(var6.hasNext()) {
                  CheckConstraintDefinition checkConstraint = (CheckConstraintDefinition)var6.next();
                  this.checkConstraints.add(checkConstraint);
               }
            }
         }
      }

      if (this.checkConstraintsBySchema == null) {
         this.checkConstraintsBySchema = new LinkedHashMap();
      }

      return this.filterSchema(this.checkConstraints, schema, this.checkConstraintsBySchema);
   }

   public final List<TableDefinition> getTables(SchemaDefinition schema) {
      if (this.tables == null) {
         this.tables = new ArrayList();
         if (this.getIncludeTables()) {
            try {
               List<TableDefinition> t = this.getTables0();
               this.tables = this.filterExcludeInclude(t);
               log.info("Tables fetched", (Object)fetchedSize(t, this.tables));
            } catch (Exception var3) {
               log.error("Error while fetching tables", (Throwable)var3);
            }
         } else {
            log.info("Tables excluded");
         }
      }

      if (this.tablesBySchema == null) {
         this.tablesBySchema = new LinkedHashMap();
      }

      return this.filterSchema(this.tables, schema, this.tablesBySchema);
   }

   public final TableDefinition getTable(SchemaDefinition schema, String name) {
      return this.getTable(schema, name, false);
   }

   public final TableDefinition getTable(SchemaDefinition schema, String name, boolean ignoreCase) {
      return (TableDefinition)getDefinition(this.getTables(schema), name, ignoreCase);
   }

   public final TableDefinition getTable(SchemaDefinition schema, Name name) {
      return this.getTable(schema, name, false);
   }

   public final TableDefinition getTable(SchemaDefinition schema, Name name, boolean ignoreCase) {
      return (TableDefinition)getDefinition(this.getTables(schema), name, ignoreCase);
   }

   public final List<EnumDefinition> getEnums(SchemaDefinition schema) {
      if (this.enums == null) {
         this.enums = new ArrayList();

         try {
            List<EnumDefinition> e = this.getEnums0();
            this.enums = this.filterExcludeInclude(e);
            this.enums.addAll(this.getConfiguredEnums());
            log.info("Enums fetched", (Object)fetchedSize(e, this.enums));
         } catch (Exception var3) {
            log.error("Error while fetching enums", (Throwable)var3);
         }
      }

      if (this.enumsBySchema == null) {
         this.enumsBySchema = new LinkedHashMap();
      }

      return this.filterSchema(this.enums, schema, this.enumsBySchema);
   }

   private final List<EnumDefinition> getConfiguredEnums() {
      List<EnumDefinition> result = new ArrayList();

      DefaultEnumDefinition e;
      for(Iterator var2 = this.configuredEnumTypes.iterator(); var2.hasNext(); result.add(e)) {
         EnumType enumType = (EnumType)var2.next();
         String name = enumType.getName();
         e = new DefaultEnumDefinition((SchemaDefinition)this.getSchemata().get(0), name, (String)null, true);
         String literals = enumType.getLiterals();

         try {
            CSVReader reader = new CSVReader(new StringReader(literals));
            e.addLiterals(reader.readNext());
         } catch (IOException var8) {
         }
      }

      return result;
   }

   public final ForcedType getConfiguredForcedType(Definition definition) {
      return this.getConfiguredForcedType(definition, (DataTypeDefinition)null);
   }

   public final ForcedType getConfiguredForcedType(Definition definition, DataTypeDefinition definedType) {
      Iterator var3 = this.getConfiguredForcedTypes().iterator();

      ForcedType forcedType;
      Pattern p;
      do {
         String types;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            forcedType = (ForcedType)var3.next();
            String expression = forcedType.getExpression();
            if (forcedType.getExpressions() != null) {
               expression = forcedType.getExpressions();
               log.warn("DEPRECATED", (Object)"The <expressions/> element in <forcedType/> is deprecated. Use <expression/> instead");
            }

            types = forcedType.getTypes();
            if (expression == null) {
               break;
            }

            p = this.pattern(expression);
         } while(!p.matcher(definition.getName()).matches() && !p.matcher(definition.getQualifiedName()).matches());

         if (types == null || definedType == null) {
            break;
         }

         p = this.pattern(types);
      } while(!p.matcher(definedType.getType()).matches() && (definedType.getLength() == 0 || !p.matcher(definedType.getType() + "(" + definedType.getLength() + ")").matches()) && (definedType.getScale() != 0 || !p.matcher(definedType.getType() + "(" + definedType.getPrecision() + ")").matches()) && !p.matcher(definedType.getType() + "(" + definedType.getPrecision() + "," + definedType.getScale() + ")").matches() && !p.matcher(definedType.getType() + "(" + definedType.getPrecision() + ", " + definedType.getScale() + ")").matches());

      return forcedType;
   }

   public final EnumDefinition getEnum(SchemaDefinition schema, String name) {
      return this.getEnum(schema, name, false);
   }

   public final EnumDefinition getEnum(SchemaDefinition schema, String name, boolean ignoreCase) {
      return (EnumDefinition)getDefinition(this.getEnums(schema), name, ignoreCase);
   }

   public final EnumDefinition getEnum(SchemaDefinition schema, Name name) {
      return this.getEnum(schema, name, false);
   }

   public final EnumDefinition getEnum(SchemaDefinition schema, Name name, boolean ignoreCase) {
      return (EnumDefinition)getDefinition(this.getEnums(schema), name, ignoreCase);
   }

   public final List<DomainDefinition> getDomains(SchemaDefinition schema) {
      if (this.domains == null) {
         this.domains = new ArrayList();

         try {
            List<DomainDefinition> e = this.getDomains0();
            this.domains = this.filterExcludeInclude(e);
            log.info("Domains fetched", (Object)fetchedSize(e, this.domains));
         } catch (Exception var3) {
            log.error("Error while fetching domains", (Throwable)var3);
         }
      }

      return this.domains;
   }

   public final DomainDefinition getDomain(SchemaDefinition schema, String name) {
      return this.getDomain(schema, name, false);
   }

   public final DomainDefinition getDomain(SchemaDefinition schema, String name, boolean ignoreCase) {
      return (DomainDefinition)getDefinition(this.getDomains(schema), name, ignoreCase);
   }

   public final DomainDefinition getDomain(SchemaDefinition schema, Name name) {
      return this.getDomain(schema, name, false);
   }

   public final DomainDefinition getDomain(SchemaDefinition schema, Name name, boolean ignoreCase) {
      return (DomainDefinition)getDefinition(this.getDomains(schema), name, ignoreCase);
   }

   public final List<ArrayDefinition> getArrays(SchemaDefinition schema) {
      if (this.arrays == null) {
         this.arrays = new ArrayList();
         if (this.getIncludeUDTs()) {
            try {
               List<ArrayDefinition> a = this.getArrays0();
               this.arrays = this.filterExcludeInclude(a);
               log.info("ARRAYs fetched", (Object)fetchedSize(a, this.arrays));
            } catch (Exception var3) {
               log.error("Error while fetching ARRAYS", (Throwable)var3);
            }
         } else {
            log.info("ARRAYs excluded");
         }
      }

      if (this.arraysBySchema == null) {
         this.arraysBySchema = new LinkedHashMap();
      }

      return this.filterSchema(this.arrays, schema, this.arraysBySchema);
   }

   public final ArrayDefinition getArray(SchemaDefinition schema, String name) {
      return this.getArray(schema, name, false);
   }

   public final ArrayDefinition getArray(SchemaDefinition schema, String name, boolean ignoreCase) {
      return (ArrayDefinition)getDefinition(this.getArrays(schema), name, ignoreCase);
   }

   public final ArrayDefinition getArray(SchemaDefinition schema, Name name) {
      return this.getArray(schema, name, false);
   }

   public final ArrayDefinition getArray(SchemaDefinition schema, Name name, boolean ignoreCase) {
      return (ArrayDefinition)getDefinition(this.getArrays(schema), name, ignoreCase);
   }

   private final List<UDTDefinition> getAllUDTs(SchemaDefinition schema) {
      if (this.udts == null) {
         this.udts = new ArrayList();
         if (this.getIncludeUDTs()) {
            try {
               List<UDTDefinition> u = this.getUDTs0();
               this.udts = this.filterExcludeInclude(u);
               log.info("UDTs fetched", (Object)fetchedSize(u, this.udts));
            } catch (Exception var3) {
               log.error("Error while fetching udts", (Throwable)var3);
            }
         } else {
            log.info("UDTs excluded");
         }
      }

      if (this.udtsBySchema == null) {
         this.udtsBySchema = new LinkedHashMap();
      }

      return this.filterSchema(this.udts, schema, this.udtsBySchema);
   }

   private final List<UDTDefinition> ifInPackage(List<UDTDefinition> allUDTs, boolean expected) {
      List<UDTDefinition> result = new ArrayList();
      Iterator var4 = allUDTs.iterator();

      while(var4.hasNext()) {
         UDTDefinition u = (UDTDefinition)var4.next();
         if (u.getPackage() != null == expected) {
            result.add(u);
         }
      }

      return result;
   }

   public final List<UDTDefinition> getUDTs(SchemaDefinition schema) {
      return this.getAllUDTs(schema);
   }

   public final UDTDefinition getUDT(SchemaDefinition schema, String name) {
      return this.getUDT(schema, name, false);
   }

   public final UDTDefinition getUDT(SchemaDefinition schema, String name, boolean ignoreCase) {
      return (UDTDefinition)getDefinition(this.getUDTs(schema), name, ignoreCase);
   }

   public final UDTDefinition getUDT(SchemaDefinition schema, Name name) {
      return this.getUDT(schema, name, false);
   }

   public final UDTDefinition getUDT(SchemaDefinition schema, Name name, boolean ignoreCase) {
      return (UDTDefinition)getDefinition(this.getUDTs(schema), name, ignoreCase);
   }

   public final List<UDTDefinition> getUDTs(PackageDefinition pkg) {
      return this.ifInPackage(this.getAllUDTs(pkg.getSchema()), true);
   }

   public final Relations getRelations() {
      if (this.relations == null) {
         this.relations = new DefaultRelations();
         if (this.includeRelations) {
            try {
               this.relations = this.getRelations0();
            } catch (Exception var2) {
               log.error("Error while fetching relations", (Throwable)var2);
            }
         }
      }

      return this.relations;
   }

   public final List<RoutineDefinition> getRoutines(SchemaDefinition schema) {
      if (this.routines == null) {
         this.routines = new ArrayList();
         if (this.getIncludeRoutines()) {
            try {
               List<RoutineDefinition> r = this.getRoutines0();
               this.routines = this.filterExcludeInclude(r);
               log.info("Routines fetched", (Object)fetchedSize(r, this.routines));
            } catch (Exception var3) {
               log.error("Error while fetching functions", (Throwable)var3);
            }
         } else {
            log.info("Routines excluded");
         }
      }

      if (this.routinesBySchema == null) {
         this.routinesBySchema = new LinkedHashMap();
      }

      return this.filterSchema(this.routines, schema, this.routinesBySchema);
   }

   public final List<PackageDefinition> getPackages(SchemaDefinition schema) {
      if (this.packages == null) {
         this.packages = new ArrayList();
         if (this.getIncludePackages()) {
            try {
               List<PackageDefinition> p = this.getPackages0();
               this.packages = this.filterExcludeInclude(p);
               log.info("Packages fetched", (Object)fetchedSize(p, this.packages));
            } catch (Exception var3) {
               log.error("Error while fetching packages", (Throwable)var3);
            }
         } else {
            log.info("Packages excluded");
         }
      }

      if (this.packagesBySchema == null) {
         this.packagesBySchema = new LinkedHashMap();
      }

      return this.filterSchema(this.packages, schema, this.packagesBySchema);
   }

   public PackageDefinition getPackage(SchemaDefinition schema, String inputName) {
      Iterator var3 = this.getPackages(schema).iterator();

      PackageDefinition pkg;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         pkg = (PackageDefinition)var3.next();
      } while(!pkg.getName().equals(inputName));

      return pkg;
   }

   protected static final <D extends Definition> D getDefinition(List<D> definitions, String name, boolean ignoreCase) {
      Iterator var3 = definitions.iterator();

      Definition definition;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         definition = (Definition)var3.next();
      } while((!ignoreCase || !definition.getName().equalsIgnoreCase(name)) && (ignoreCase || !definition.getName().equals(name)));

      return definition;
   }

   protected static final <D extends Definition> D getDefinition(List<D> definitions, Name name, boolean ignoreCase) {
      Iterator var3 = definitions.iterator();

      Definition definition;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         definition = (Definition)var3.next();
      } while((!ignoreCase || !definition.getQualifiedNamePart().equalsIgnoreCase(name)) && (ignoreCase || !definition.getQualifiedNamePart().equals(name)));

      return definition;
   }

   protected final <T extends Definition> List<T> filterSchema(List<T> definitions, SchemaDefinition schema, Map<SchemaDefinition, List<T>> cache) {
      List<T> result = (List)cache.get(schema);
      if (result == null) {
         result = this.filterSchema(definitions, schema);
         cache.put(schema, result);
      }

      return result;
   }

   protected final <T extends Definition> List<T> filterSchema(List<T> definitions, SchemaDefinition schema) {
      if (schema == null) {
         return definitions;
      } else {
         List<T> result = new ArrayList();
         Iterator var4 = definitions.iterator();

         while(var4.hasNext()) {
            T definition = (Definition)var4.next();
            if (definition.getSchema().equals(schema)) {
               result.add(definition);
            }
         }

         return result;
      }
   }

   public final <T extends Definition> List<T> filterExcludeInclude(List<T> definitions) {
      List<T> result = this.filterExcludeInclude(definitions, this.excludes, this.includes, this.filters);
      this.all.addAll(definitions);
      this.included.addAll(result);
      this.excluded.addAll(definitions);
      this.excluded.removeAll(result);
      return result;
   }

   public final List<Definition> getIncluded() {
      return Collections.unmodifiableList(this.included);
   }

   public final List<Definition> getExcluded() {
      return Collections.unmodifiableList(this.excluded);
   }

   public final List<Definition> getAll() {
      return Collections.unmodifiableList(this.all);
   }

   protected final <T extends Definition> List<T> filterExcludeInclude(List<T> definitions, String[] e, String[] i, List<Database.Filter> f) {
      List<T> result = new ArrayList();
      Iterator var6 = definitions.iterator();

      while(true) {
         label68:
         while(var6.hasNext()) {
            T definition = (Definition)var6.next();
            String[] var8;
            int var9;
            int var10;
            String include;
            Pattern p;
            if (e != null) {
               var8 = e;
               var9 = e.length;

               for(var10 = 0; var10 < var9; ++var10) {
                  include = var8[var10];
                  p = this.pattern(include);
                  if (include != null && (p.matcher(definition.getName()).matches() || p.matcher(definition.getQualifiedName()).matches())) {
                     if (log.isDebugEnabled()) {
                        log.debug("Exclude", (Object)("Excluding " + definition.getQualifiedName() + " because of pattern " + include));
                     }
                     continue label68;
                  }
               }
            }

            if (i != null) {
               var8 = i;
               var9 = i.length;

               for(var10 = 0; var10 < var9; ++var10) {
                  include = var8[var10];
                  p = this.pattern(include);
                  if (include != null && (p.matcher(definition.getName()).matches() || p.matcher(definition.getQualifiedName()).matches())) {
                     Iterator var13 = f.iterator();

                     Database.Filter filter;
                     do {
                        if (!var13.hasNext()) {
                           result.add(definition);
                           if (log.isDebugEnabled()) {
                              log.debug("Include", (Object)("Including " + definition.getQualifiedName() + " because of pattern " + include));
                           }
                           continue label68;
                        }

                        filter = (Database.Filter)var13.next();
                     } while(!filter.exclude(definition));

                     if (log.isDebugEnabled()) {
                        log.debug("Exclude", (Object)("Excluding " + definition.getQualifiedName() + " because of filter " + filter));
                     }
                     break;
                  }
               }
            }
         }

         return result;
      }
   }

   protected final Relations getRelations0() {
      DefaultRelations result = new DefaultRelations();

      try {
         this.loadPrimaryKeys(result);
      } catch (Exception var8) {
         log.error("Error while fetching primary keys", (Throwable)var8);
      }

      try {
         this.loadUniqueKeys(result);
      } catch (Exception var7) {
         log.error("Error while fetching unique keys", (Throwable)var7);
      }

      try {
         this.loadForeignKeys(result);
      } catch (Exception var6) {
         log.error("Error while fetching foreign keys", (Throwable)var6);
      }

      try {
         this.loadCheckConstraints(result);
      } catch (Exception var5) {
         log.error("Error while fetching check constraints", (Throwable)var5);
      }

      try {
         this.syntheticPrimaryKeys(result);
      } catch (Exception var4) {
         log.error("Error while generating synthetic primary keys", (Throwable)var4);
      }

      try {
         this.overridePrimaryKeys(result);
      } catch (Exception var3) {
         log.error("Error while overriding primary keys", (Throwable)var3);
      }

      return result;
   }

   public final boolean isArrayType(String dataType) {
      switch(this.getDialect().family()) {
      case POSTGRES:
      case H2:
         return "ARRAY".equals(dataType);
      case HSQLDB:
         return dataType.endsWith(" ARRAY");
      default:
         return false;
      }
   }

   protected static final String fetchedSize(List<?> fetched, List<?> included) {
      return fetched.size() + " (" + included.size() + " included, " + (fetched.size() - included.size()) + " excluded)";
   }

   private final void syntheticPrimaryKeys(DefaultRelations r) {
      List<UniqueKeyDefinition> syntheticKeys = new ArrayList();
      Iterator var3 = this.getSchemata().iterator();

      while(var3.hasNext()) {
         SchemaDefinition schema = (SchemaDefinition)var3.next();
         Iterator var5 = schema.getTables().iterator();

         while(var5.hasNext()) {
            TableDefinition table = (TableDefinition)var5.next();
            List<ColumnDefinition> columns = this.filterExcludeInclude(table.getColumns(), (String[])null, this.getSyntheticPrimaryKeys(), this.filters);
            if (!columns.isEmpty()) {
               DefaultUniqueKeyDefinition syntheticKey = new DefaultUniqueKeyDefinition(schema, "SYNTHETIC_PK_" + table.getName(), table, true);
               syntheticKey.getKeyColumns().addAll(columns);
               syntheticKeys.add(syntheticKey);
            }
         }
      }

      log.info("Synthetic primary keys", (Object)fetchedSize(syntheticKeys, syntheticKeys));
      var3 = syntheticKeys.iterator();

      while(var3.hasNext()) {
         UniqueKeyDefinition key = (UniqueKeyDefinition)var3.next();
         r.overridePrimaryKey(key);
      }

   }

   private final void overridePrimaryKeys(DefaultRelations r) {
      List<UniqueKeyDefinition> allKeys = r.getUniqueKeys();
      List<UniqueKeyDefinition> filteredKeys = this.filterExcludeInclude(allKeys, (String[])null, this.overridePrimaryKeys, this.filters);
      log.info("Overriding primary keys", (Object)fetchedSize(allKeys, filteredKeys));
      Iterator var4 = filteredKeys.iterator();

      while(var4.hasNext()) {
         UniqueKeyDefinition key = (UniqueKeyDefinition)var4.next();
         r.overridePrimaryKey(key);
      }

   }

   protected abstract DSLContext create0();

   protected abstract void loadPrimaryKeys(DefaultRelations var1) throws SQLException;

   protected abstract void loadUniqueKeys(DefaultRelations var1) throws SQLException;

   protected abstract void loadForeignKeys(DefaultRelations var1) throws SQLException;

   protected abstract void loadCheckConstraints(DefaultRelations var1) throws SQLException;

   protected abstract List<CatalogDefinition> getCatalogs0() throws SQLException;

   protected abstract List<SchemaDefinition> getSchemata0() throws SQLException;

   protected abstract List<SequenceDefinition> getSequences0() throws SQLException;

   protected abstract List<TableDefinition> getTables0() throws SQLException;

   protected abstract List<RoutineDefinition> getRoutines0() throws SQLException;

   protected abstract List<PackageDefinition> getPackages0() throws SQLException;

   protected abstract List<EnumDefinition> getEnums0() throws SQLException;

   protected abstract List<DomainDefinition> getDomains0() throws SQLException;

   protected abstract List<UDTDefinition> getUDTs0() throws SQLException;

   protected abstract List<ArrayDefinition> getArrays0() throws SQLException;

   protected final DataTypeDefinition getDataTypeForMAX_VAL(SchemaDefinition schema, BigInteger value) {
      DefaultDataTypeDefinition type;
      if (BigInteger.valueOf(127L).compareTo(value) >= 0) {
         type = new DefaultDataTypeDefinition(this, schema, SQLDataType.NUMERIC.getTypeName(), 0, 2, 0, false, (String)null);
      } else if (BigInteger.valueOf(32767L).compareTo(value) >= 0) {
         type = new DefaultDataTypeDefinition(this, schema, SQLDataType.NUMERIC.getTypeName(), 0, 4, 0, false, (String)null);
      } else if (BigInteger.valueOf(2147483647L).compareTo(value) >= 0) {
         type = new DefaultDataTypeDefinition(this, schema, SQLDataType.NUMERIC.getTypeName(), 0, 9, 0, false, (String)null);
      } else if (BigInteger.valueOf(Long.MAX_VALUE).compareTo(value) >= 0) {
         type = new DefaultDataTypeDefinition(this, schema, SQLDataType.NUMERIC.getTypeName(), 0, 18, 0, false, (String)null);
      } else {
         type = new DefaultDataTypeDefinition(this, schema, SQLDataType.NUMERIC.getTypeName(), 0, 38, 0, false, (String)null);
      }

      return type;
   }
}
