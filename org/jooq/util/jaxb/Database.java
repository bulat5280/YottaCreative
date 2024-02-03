package org.jooq.util.jaxb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.jooq.util.jaxb.tools.StringAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "Database",
   propOrder = {}
)
public class Database implements Serializable {
   private static final long serialVersionUID = 390L;
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String name;
   @XmlList
   @XmlElement(
      defaultValue = "COMMENTS CASE_INSENSITIVE"
   )
   protected List<RegexFlag> regexFlags;
   @XmlElement(
      defaultValue = ".*"
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String includes = ".*";
   @XmlElement(
      defaultValue = ""
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String excludes = "";
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean includeExcludeColumns = false;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean includeTables = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean includeRoutines = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean includePackages = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean includeUDTs = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean includeSequences = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean includePrimaryKeys = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean includeUniqueKeys = true;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean includeForeignKeys = true;
   @XmlElement(
      defaultValue = ""
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String recordVersionFields = "";
   @XmlElement(
      defaultValue = ""
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String recordTimestampFields = "";
   @XmlElement(
      defaultValue = ""
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String syntheticPrimaryKeys = "";
   @XmlElement(
      defaultValue = ""
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String overridePrimaryKeys = "";
   @XmlElement(
      defaultValue = ""
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String syntheticIdentities = "";
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean dateAsTimestamp = false;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean ignoreProcedureReturnValues = false;
   @XmlElement(
      defaultValue = "true"
   )
   protected Boolean unsignedTypes = true;
   @XmlElement(
      defaultValue = ""
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String inputCatalog = "";
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String outputCatalog;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean outputCatalogToDefault = false;
   @XmlElement(
      defaultValue = ""
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String inputSchema = "";
   @XmlElement(
      defaultValue = ""
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String schemaVersionProvider = "";
   @XmlElement(
      defaultValue = ""
   )
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String catalogVersionProvider = "";
   @XmlJavaTypeAdapter(StringAdapter.class)
   protected String outputSchema;
   @XmlElement(
      defaultValue = "false"
   )
   protected Boolean outputSchemaToDefault = false;
   protected Boolean tableValuedFunctions;
   @XmlElementWrapper(
      name = "properties"
   )
   @XmlElement(
      name = "property"
   )
   protected List<Property> properties;
   @XmlElementWrapper(
      name = "catalogs"
   )
   @XmlElement(
      name = "catalog"
   )
   protected List<Catalog> catalogs;
   @XmlElementWrapper(
      name = "schemata"
   )
   @XmlElement(
      name = "schema"
   )
   protected List<Schema> schemata;
   @XmlElementWrapper(
      name = "customTypes"
   )
   @XmlElement(
      name = "customType"
   )
   protected List<CustomType> customTypes;
   @XmlElementWrapper(
      name = "enumTypes"
   )
   @XmlElement(
      name = "enumType"
   )
   protected List<EnumType> enumTypes;
   @XmlElementWrapper(
      name = "forcedTypes"
   )
   @XmlElement(
      name = "forcedType"
   )
   protected List<ForcedType> forcedTypes;

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public List<RegexFlag> getRegexFlags() {
      if (this.regexFlags == null) {
         this.regexFlags = new ArrayList();
      }

      return this.regexFlags;
   }

   public String getIncludes() {
      return this.includes;
   }

   public void setIncludes(String value) {
      this.includes = value;
   }

   public String getExcludes() {
      return this.excludes;
   }

   public void setExcludes(String value) {
      this.excludes = value;
   }

   public Boolean isIncludeExcludeColumns() {
      return this.includeExcludeColumns;
   }

   public void setIncludeExcludeColumns(Boolean value) {
      this.includeExcludeColumns = value;
   }

   public Boolean isIncludeTables() {
      return this.includeTables;
   }

   public void setIncludeTables(Boolean value) {
      this.includeTables = value;
   }

   public Boolean isIncludeRoutines() {
      return this.includeRoutines;
   }

   public void setIncludeRoutines(Boolean value) {
      this.includeRoutines = value;
   }

   public Boolean isIncludePackages() {
      return this.includePackages;
   }

   public void setIncludePackages(Boolean value) {
      this.includePackages = value;
   }

   public Boolean isIncludeUDTs() {
      return this.includeUDTs;
   }

   public void setIncludeUDTs(Boolean value) {
      this.includeUDTs = value;
   }

   public Boolean isIncludeSequences() {
      return this.includeSequences;
   }

   public void setIncludeSequences(Boolean value) {
      this.includeSequences = value;
   }

   public Boolean isIncludePrimaryKeys() {
      return this.includePrimaryKeys;
   }

   public void setIncludePrimaryKeys(Boolean value) {
      this.includePrimaryKeys = value;
   }

   public Boolean isIncludeUniqueKeys() {
      return this.includeUniqueKeys;
   }

   public void setIncludeUniqueKeys(Boolean value) {
      this.includeUniqueKeys = value;
   }

   public Boolean isIncludeForeignKeys() {
      return this.includeForeignKeys;
   }

   public void setIncludeForeignKeys(Boolean value) {
      this.includeForeignKeys = value;
   }

   public String getRecordVersionFields() {
      return this.recordVersionFields;
   }

   public void setRecordVersionFields(String value) {
      this.recordVersionFields = value;
   }

   public String getRecordTimestampFields() {
      return this.recordTimestampFields;
   }

   public void setRecordTimestampFields(String value) {
      this.recordTimestampFields = value;
   }

   public String getSyntheticPrimaryKeys() {
      return this.syntheticPrimaryKeys;
   }

   public void setSyntheticPrimaryKeys(String value) {
      this.syntheticPrimaryKeys = value;
   }

   public String getOverridePrimaryKeys() {
      return this.overridePrimaryKeys;
   }

   public void setOverridePrimaryKeys(String value) {
      this.overridePrimaryKeys = value;
   }

   public String getSyntheticIdentities() {
      return this.syntheticIdentities;
   }

   public void setSyntheticIdentities(String value) {
      this.syntheticIdentities = value;
   }

   public Boolean isDateAsTimestamp() {
      return this.dateAsTimestamp;
   }

   public void setDateAsTimestamp(Boolean value) {
      this.dateAsTimestamp = value;
   }

   public Boolean isIgnoreProcedureReturnValues() {
      return this.ignoreProcedureReturnValues;
   }

   public void setIgnoreProcedureReturnValues(Boolean value) {
      this.ignoreProcedureReturnValues = value;
   }

   public Boolean isUnsignedTypes() {
      return this.unsignedTypes;
   }

   public void setUnsignedTypes(Boolean value) {
      this.unsignedTypes = value;
   }

   public String getInputCatalog() {
      return this.inputCatalog;
   }

   public void setInputCatalog(String value) {
      this.inputCatalog = value;
   }

   public String getOutputCatalog() {
      return this.outputCatalog;
   }

   public void setOutputCatalog(String value) {
      this.outputCatalog = value;
   }

   public Boolean isOutputCatalogToDefault() {
      return this.outputCatalogToDefault;
   }

   public void setOutputCatalogToDefault(Boolean value) {
      this.outputCatalogToDefault = value;
   }

   public String getInputSchema() {
      return this.inputSchema;
   }

   public void setInputSchema(String value) {
      this.inputSchema = value;
   }

   public String getSchemaVersionProvider() {
      return this.schemaVersionProvider;
   }

   public void setSchemaVersionProvider(String value) {
      this.schemaVersionProvider = value;
   }

   public String getCatalogVersionProvider() {
      return this.catalogVersionProvider;
   }

   public void setCatalogVersionProvider(String value) {
      this.catalogVersionProvider = value;
   }

   public String getOutputSchema() {
      return this.outputSchema;
   }

   public void setOutputSchema(String value) {
      this.outputSchema = value;
   }

   public Boolean isOutputSchemaToDefault() {
      return this.outputSchemaToDefault;
   }

   public void setOutputSchemaToDefault(Boolean value) {
      this.outputSchemaToDefault = value;
   }

   public Boolean isTableValuedFunctions() {
      return this.tableValuedFunctions;
   }

   public void setTableValuedFunctions(Boolean value) {
      this.tableValuedFunctions = value;
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

   public List<Catalog> getCatalogs() {
      if (this.catalogs == null) {
         this.catalogs = new ArrayList();
      }

      return this.catalogs;
   }

   public void setCatalogs(List<Catalog> catalogs) {
      this.catalogs = catalogs;
   }

   public List<Schema> getSchemata() {
      if (this.schemata == null) {
         this.schemata = new ArrayList();
      }

      return this.schemata;
   }

   public void setSchemata(List<Schema> schemata) {
      this.schemata = schemata;
   }

   public List<CustomType> getCustomTypes() {
      if (this.customTypes == null) {
         this.customTypes = new ArrayList();
      }

      return this.customTypes;
   }

   public void setCustomTypes(List<CustomType> customTypes) {
      this.customTypes = customTypes;
   }

   public List<EnumType> getEnumTypes() {
      if (this.enumTypes == null) {
         this.enumTypes = new ArrayList();
      }

      return this.enumTypes;
   }

   public void setEnumTypes(List<EnumType> enumTypes) {
      this.enumTypes = enumTypes;
   }

   public List<ForcedType> getForcedTypes() {
      if (this.forcedTypes == null) {
         this.forcedTypes = new ArrayList();
      }

      return this.forcedTypes;
   }

   public void setForcedTypes(List<ForcedType> forcedTypes) {
      this.forcedTypes = forcedTypes;
   }

   public Database withName(String value) {
      this.setName(value);
      return this;
   }

   public Database withRegexFlags(RegexFlag... values) {
      if (values != null) {
         RegexFlag[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            RegexFlag value = var2[var4];
            this.getRegexFlags().add(value);
         }
      }

      return this;
   }

   public Database withRegexFlags(Collection<RegexFlag> values) {
      if (values != null) {
         this.getRegexFlags().addAll(values);
      }

      return this;
   }

   public Database withIncludes(String value) {
      this.setIncludes(value);
      return this;
   }

   public Database withExcludes(String value) {
      this.setExcludes(value);
      return this;
   }

   public Database withIncludeExcludeColumns(Boolean value) {
      this.setIncludeExcludeColumns(value);
      return this;
   }

   public Database withIncludeTables(Boolean value) {
      this.setIncludeTables(value);
      return this;
   }

   public Database withIncludeRoutines(Boolean value) {
      this.setIncludeRoutines(value);
      return this;
   }

   public Database withIncludePackages(Boolean value) {
      this.setIncludePackages(value);
      return this;
   }

   public Database withIncludeUDTs(Boolean value) {
      this.setIncludeUDTs(value);
      return this;
   }

   public Database withIncludeSequences(Boolean value) {
      this.setIncludeSequences(value);
      return this;
   }

   public Database withIncludePrimaryKeys(Boolean value) {
      this.setIncludePrimaryKeys(value);
      return this;
   }

   public Database withIncludeUniqueKeys(Boolean value) {
      this.setIncludeUniqueKeys(value);
      return this;
   }

   public Database withIncludeForeignKeys(Boolean value) {
      this.setIncludeForeignKeys(value);
      return this;
   }

   public Database withRecordVersionFields(String value) {
      this.setRecordVersionFields(value);
      return this;
   }

   public Database withRecordTimestampFields(String value) {
      this.setRecordTimestampFields(value);
      return this;
   }

   public Database withSyntheticPrimaryKeys(String value) {
      this.setSyntheticPrimaryKeys(value);
      return this;
   }

   public Database withOverridePrimaryKeys(String value) {
      this.setOverridePrimaryKeys(value);
      return this;
   }

   public Database withSyntheticIdentities(String value) {
      this.setSyntheticIdentities(value);
      return this;
   }

   public Database withDateAsTimestamp(Boolean value) {
      this.setDateAsTimestamp(value);
      return this;
   }

   public Database withIgnoreProcedureReturnValues(Boolean value) {
      this.setIgnoreProcedureReturnValues(value);
      return this;
   }

   public Database withUnsignedTypes(Boolean value) {
      this.setUnsignedTypes(value);
      return this;
   }

   public Database withInputCatalog(String value) {
      this.setInputCatalog(value);
      return this;
   }

   public Database withOutputCatalog(String value) {
      this.setOutputCatalog(value);
      return this;
   }

   public Database withOutputCatalogToDefault(Boolean value) {
      this.setOutputCatalogToDefault(value);
      return this;
   }

   public Database withInputSchema(String value) {
      this.setInputSchema(value);
      return this;
   }

   public Database withSchemaVersionProvider(String value) {
      this.setSchemaVersionProvider(value);
      return this;
   }

   public Database withCatalogVersionProvider(String value) {
      this.setCatalogVersionProvider(value);
      return this;
   }

   public Database withOutputSchema(String value) {
      this.setOutputSchema(value);
      return this;
   }

   public Database withOutputSchemaToDefault(Boolean value) {
      this.setOutputSchemaToDefault(value);
      return this;
   }

   public Database withTableValuedFunctions(Boolean value) {
      this.setTableValuedFunctions(value);
      return this;
   }

   public Database withProperties(Property... values) {
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

   public Database withProperties(Collection<Property> values) {
      if (values != null) {
         this.getProperties().addAll(values);
      }

      return this;
   }

   public Database withProperties(List<Property> properties) {
      this.setProperties(properties);
      return this;
   }

   public Database withCatalogs(Catalog... values) {
      if (values != null) {
         Catalog[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Catalog value = var2[var4];
            this.getCatalogs().add(value);
         }
      }

      return this;
   }

   public Database withCatalogs(Collection<Catalog> values) {
      if (values != null) {
         this.getCatalogs().addAll(values);
      }

      return this;
   }

   public Database withCatalogs(List<Catalog> catalogs) {
      this.setCatalogs(catalogs);
      return this;
   }

   public Database withSchemata(Schema... values) {
      if (values != null) {
         Schema[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Schema value = var2[var4];
            this.getSchemata().add(value);
         }
      }

      return this;
   }

   public Database withSchemata(Collection<Schema> values) {
      if (values != null) {
         this.getSchemata().addAll(values);
      }

      return this;
   }

   public Database withSchemata(List<Schema> schemata) {
      this.setSchemata(schemata);
      return this;
   }

   public Database withCustomTypes(CustomType... values) {
      if (values != null) {
         CustomType[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            CustomType value = var2[var4];
            this.getCustomTypes().add(value);
         }
      }

      return this;
   }

   public Database withCustomTypes(Collection<CustomType> values) {
      if (values != null) {
         this.getCustomTypes().addAll(values);
      }

      return this;
   }

   public Database withCustomTypes(List<CustomType> customTypes) {
      this.setCustomTypes(customTypes);
      return this;
   }

   public Database withEnumTypes(EnumType... values) {
      if (values != null) {
         EnumType[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            EnumType value = var2[var4];
            this.getEnumTypes().add(value);
         }
      }

      return this;
   }

   public Database withEnumTypes(Collection<EnumType> values) {
      if (values != null) {
         this.getEnumTypes().addAll(values);
      }

      return this;
   }

   public Database withEnumTypes(List<EnumType> enumTypes) {
      this.setEnumTypes(enumTypes);
      return this;
   }

   public Database withForcedTypes(ForcedType... values) {
      if (values != null) {
         ForcedType[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ForcedType value = var2[var4];
            this.getForcedTypes().add(value);
         }
      }

      return this;
   }

   public Database withForcedTypes(Collection<ForcedType> values) {
      if (values != null) {
         this.getForcedTypes().addAll(values);
      }

      return this;
   }

   public Database withForcedTypes(List<ForcedType> forcedTypes) {
      this.setForcedTypes(forcedTypes);
      return this;
   }
}
