package org.jooq.util;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;
import org.jooq.DSLContext;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.util.jaxb.Catalog;
import org.jooq.util.jaxb.CustomType;
import org.jooq.util.jaxb.EnumType;
import org.jooq.util.jaxb.ForcedType;
import org.jooq.util.jaxb.RegexFlag;
import org.jooq.util.jaxb.Schema;

public interface Database {
   List<CatalogDefinition> getCatalogs();

   CatalogDefinition getCatalog(String var1);

   List<SchemaDefinition> getSchemata();

   List<SchemaDefinition> getSchemata(CatalogDefinition var1);

   SchemaDefinition getSchema(String var1);

   Relations getRelations();

   List<SequenceDefinition> getSequences(SchemaDefinition var1);

   List<IdentityDefinition> getIdentities(SchemaDefinition var1);

   List<UniqueKeyDefinition> getUniqueKeys(SchemaDefinition var1);

   List<ForeignKeyDefinition> getForeignKeys(SchemaDefinition var1);

   List<CheckConstraintDefinition> getCheckConstraints(SchemaDefinition var1);

   List<TableDefinition> getTables(SchemaDefinition var1);

   TableDefinition getTable(SchemaDefinition var1, String var2);

   TableDefinition getTable(SchemaDefinition var1, String var2, boolean var3);

   TableDefinition getTable(SchemaDefinition var1, Name var2);

   TableDefinition getTable(SchemaDefinition var1, Name var2, boolean var3);

   List<EnumDefinition> getEnums(SchemaDefinition var1);

   EnumDefinition getEnum(SchemaDefinition var1, String var2);

   EnumDefinition getEnum(SchemaDefinition var1, String var2, boolean var3);

   EnumDefinition getEnum(SchemaDefinition var1, Name var2);

   EnumDefinition getEnum(SchemaDefinition var1, Name var2, boolean var3);

   List<DomainDefinition> getDomains(SchemaDefinition var1);

   DomainDefinition getDomain(SchemaDefinition var1, String var2);

   DomainDefinition getDomain(SchemaDefinition var1, String var2, boolean var3);

   DomainDefinition getDomain(SchemaDefinition var1, Name var2);

   DomainDefinition getDomain(SchemaDefinition var1, Name var2, boolean var3);

   List<UDTDefinition> getUDTs(SchemaDefinition var1);

   UDTDefinition getUDT(SchemaDefinition var1, String var2);

   UDTDefinition getUDT(SchemaDefinition var1, String var2, boolean var3);

   UDTDefinition getUDT(SchemaDefinition var1, Name var2);

   UDTDefinition getUDT(SchemaDefinition var1, Name var2, boolean var3);

   List<UDTDefinition> getUDTs(PackageDefinition var1);

   List<ArrayDefinition> getArrays(SchemaDefinition var1);

   ArrayDefinition getArray(SchemaDefinition var1, String var2);

   ArrayDefinition getArray(SchemaDefinition var1, String var2, boolean var3);

   ArrayDefinition getArray(SchemaDefinition var1, Name var2);

   ArrayDefinition getArray(SchemaDefinition var1, Name var2, boolean var3);

   List<RoutineDefinition> getRoutines(SchemaDefinition var1);

   List<PackageDefinition> getPackages(SchemaDefinition var1);

   PackageDefinition getPackage(SchemaDefinition var1, String var2);

   void setConnection(Connection var1);

   Connection getConnection();

   List<String> getInputCatalogs();

   List<String> getInputSchemata();

   List<String> getInputSchemata(CatalogDefinition var1);

   List<String> getInputSchemata(String var1);

   /** @deprecated */
   @Deprecated
   String getOutputCatalog(String var1);

   /** @deprecated */
   @Deprecated
   String getOutputSchema(String var1);

   /** @deprecated */
   @Deprecated
   String getOutputSchema(String var1, String var2);

   void setConfiguredCatalogs(List<Catalog> var1);

   void setConfiguredSchemata(List<Schema> var1);

   void setExcludes(String[] var1);

   String[] getExcludes();

   void setIncludes(String[] var1);

   String[] getIncludes();

   void setIncludeExcludeColumns(boolean var1);

   boolean getIncludeExcludeColumns();

   void setIncludeForeignKeys(boolean var1);

   boolean getIncludeForeignKeys();

   void setIncludeUniqueKeys(boolean var1);

   boolean getIncludeUniqueKeys();

   void setIncludePrimaryKeys(boolean var1);

   boolean getIncludePrimaryKeys();

   void setIncludeSequences(boolean var1);

   boolean getIncludeSequences();

   void setIncludeUDTs(boolean var1);

   boolean getIncludeUDTs();

   void setIncludePackages(boolean var1);

   boolean getIncludePackages();

   void setIncludeRoutines(boolean var1);

   boolean getIncludeRoutines();

   void setIncludeTables(boolean var1);

   boolean getIncludeTables();

   void addFilter(Database.Filter var1);

   List<Database.Filter> getFilters();

   <D extends Definition> List<D> filterExcludeInclude(List<D> var1);

   List<Definition> getIncluded();

   List<Definition> getExcluded();

   List<Definition> getAll();

   void setRegexFlags(List<RegexFlag> var1);

   List<RegexFlag> getRegexFlags();

   void setRecordVersionFields(String[] var1);

   String[] getRecordVersionFields();

   void setRecordTimestampFields(String[] var1);

   String[] getRecordTimestampFields();

   void setSyntheticPrimaryKeys(String[] var1);

   String[] getSyntheticPrimaryKeys();

   void setOverridePrimaryKeys(String[] var1);

   String[] getOverridePrimaryKeys();

   void setSyntheticIdentities(String[] var1);

   String[] getSyntheticIdentities();

   void setConfiguredCustomTypes(List<CustomType> var1);

   List<CustomType> getConfiguredCustomTypes();

   CustomType getConfiguredCustomType(String var1);

   void setConfiguredEnumTypes(List<EnumType> var1);

   List<EnumType> getConfiguredEnumTypes();

   void setConfiguredForcedTypes(List<ForcedType> var1);

   SchemaVersionProvider getSchemaVersionProvider();

   void setSchemaVersionProvider(SchemaVersionProvider var1);

   CatalogVersionProvider getCatalogVersionProvider();

   void setCatalogVersionProvider(CatalogVersionProvider var1);

   List<ForcedType> getConfiguredForcedTypes();

   ForcedType getConfiguredForcedType(Definition var1);

   ForcedType getConfiguredForcedType(Definition var1, DataTypeDefinition var2);

   SQLDialect getDialect();

   DSLContext create();

   boolean isArrayType(String var1);

   void setSupportsUnsignedTypes(boolean var1);

   boolean supportsUnsignedTypes();

   void setIgnoreProcedureReturnValues(boolean var1);

   boolean ignoreProcedureReturnValues();

   void setDateAsTimestamp(boolean var1);

   boolean dateAsTimestamp();

   void setIncludeRelations(boolean var1);

   boolean includeRelations();

   void setTableValuedFunctions(boolean var1);

   boolean tableValuedFunctions();

   boolean exists(Table<?> var1);

   boolean existAll(Table<?>... var1);

   void setProperties(Properties var1);

   public interface Filter {
      boolean exclude(Definition var1);
   }
}
