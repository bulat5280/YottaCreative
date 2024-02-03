package org.jooq.util.hsqldb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record4;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
import org.jooq.TableLike;
import org.jooq.impl.DSL;
import org.jooq.tools.JooqLogger;
import org.jooq.util.AbstractDatabase;
import org.jooq.util.ArrayDefinition;
import org.jooq.util.CatalogDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultCheckConstraintDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.DefaultRelations;
import org.jooq.util.DefaultSequenceDefinition;
import org.jooq.util.DomainDefinition;
import org.jooq.util.EnumDefinition;
import org.jooq.util.PackageDefinition;
import org.jooq.util.RoutineDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.SequenceDefinition;
import org.jooq.util.TableDefinition;
import org.jooq.util.UDTDefinition;
import org.jooq.util.hsqldb.information_schema.Tables;
import org.jooq.util.hsqldb.information_schema.tables.CheckConstraints;
import org.jooq.util.hsqldb.information_schema.tables.TableConstraints;

public class HSQLDBDatabase extends AbstractDatabase {
   protected DSLContext create0() {
      return DSL.using(this.getConnection(), SQLDialect.HSQLDB);
   }

   protected void loadPrimaryKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.fetchKeys("PRIMARY KEY").iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Tables.KEY_COLUMN_USAGE.TABLE_SCHEMA));
         String key = (String)record.get((Field)Tables.KEY_COLUMN_USAGE.CONSTRAINT_NAME);
         String tableName = (String)record.get((Field)Tables.KEY_COLUMN_USAGE.TABLE_NAME);
         String columnName = (String)record.get((Field)Tables.KEY_COLUMN_USAGE.COLUMN_NAME);
         TableDefinition table = this.getTable(schema, tableName);
         if (table != null) {
            relations.addPrimaryKey(key, table.getColumn(columnName));
         }
      }

   }

   protected void loadUniqueKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.fetchKeys("UNIQUE").iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Tables.KEY_COLUMN_USAGE.TABLE_SCHEMA));
         String key = (String)record.get((Field)Tables.KEY_COLUMN_USAGE.CONSTRAINT_NAME);
         String tableName = (String)record.get((Field)Tables.KEY_COLUMN_USAGE.TABLE_NAME);
         String columnName = (String)record.get((Field)Tables.KEY_COLUMN_USAGE.COLUMN_NAME);
         TableDefinition table = this.getTable(schema, tableName);
         if (table != null) {
            relations.addUniqueKey(key, table.getColumn(columnName));
         }
      }

   }

   private Result<Record4<String, String, String, String>> fetchKeys(String constraintType) {
      return this.create().select(Tables.KEY_COLUMN_USAGE.TABLE_SCHEMA, Tables.KEY_COLUMN_USAGE.CONSTRAINT_NAME, Tables.KEY_COLUMN_USAGE.TABLE_NAME, Tables.KEY_COLUMN_USAGE.COLUMN_NAME).from(Tables.TABLE_CONSTRAINTS.join(Tables.KEY_COLUMN_USAGE).on(Tables.TABLE_CONSTRAINTS.CONSTRAINT_SCHEMA.equal(Tables.KEY_COLUMN_USAGE.CONSTRAINT_SCHEMA)).and(Tables.TABLE_CONSTRAINTS.CONSTRAINT_NAME.equal(Tables.KEY_COLUMN_USAGE.CONSTRAINT_NAME))).where(new Condition[]{Tables.TABLE_CONSTRAINTS.CONSTRAINT_TYPE.equal(constraintType)}).and(Tables.TABLE_CONSTRAINTS.TABLE_SCHEMA.in(this.getInputSchemata())).orderBy(Tables.KEY_COLUMN_USAGE.TABLE_SCHEMA.asc(), Tables.KEY_COLUMN_USAGE.TABLE_NAME.asc(), Tables.KEY_COLUMN_USAGE.CONSTRAINT_NAME.asc(), Tables.KEY_COLUMN_USAGE.ORDINAL_POSITION.asc()).fetch();
   }

   protected void loadForeignKeys(DefaultRelations relations) throws SQLException {
      Result<?> result = this.create().select(Tables.REFERENTIAL_CONSTRAINTS.UNIQUE_CONSTRAINT_NAME, Tables.REFERENTIAL_CONSTRAINTS.UNIQUE_CONSTRAINT_SCHEMA, Tables.KEY_COLUMN_USAGE.CONSTRAINT_NAME, Tables.KEY_COLUMN_USAGE.TABLE_SCHEMA, Tables.KEY_COLUMN_USAGE.TABLE_NAME, Tables.KEY_COLUMN_USAGE.COLUMN_NAME).from(Tables.REFERENTIAL_CONSTRAINTS).join((TableLike)Tables.KEY_COLUMN_USAGE).on(Tables.KEY_COLUMN_USAGE.CONSTRAINT_SCHEMA.equal(Tables.REFERENTIAL_CONSTRAINTS.CONSTRAINT_SCHEMA)).and(Tables.KEY_COLUMN_USAGE.CONSTRAINT_NAME.equal(Tables.REFERENTIAL_CONSTRAINTS.CONSTRAINT_NAME)).where(new Condition[]{Tables.KEY_COLUMN_USAGE.TABLE_SCHEMA.in(this.getInputSchemata())}).orderBy(Tables.KEY_COLUMN_USAGE.TABLE_SCHEMA.asc(), Tables.KEY_COLUMN_USAGE.TABLE_NAME.asc(), Tables.KEY_COLUMN_USAGE.CONSTRAINT_NAME.asc(), Tables.KEY_COLUMN_USAGE.ORDINAL_POSITION.asc()).fetch();
      Iterator var3 = result.iterator();

      while(var3.hasNext()) {
         Record record = (Record)var3.next();
         SchemaDefinition foreignKeySchema = this.getSchema((String)record.get((Field)Tables.KEY_COLUMN_USAGE.TABLE_SCHEMA));
         SchemaDefinition uniqueKeySchema = this.getSchema((String)record.get((Field)Tables.REFERENTIAL_CONSTRAINTS.UNIQUE_CONSTRAINT_SCHEMA));
         String foreignKey = (String)record.get((Field)Tables.KEY_COLUMN_USAGE.CONSTRAINT_NAME);
         String foreignKeyTable = (String)record.get((Field)Tables.KEY_COLUMN_USAGE.TABLE_NAME);
         String foreignKeyColumn = (String)record.get((Field)Tables.KEY_COLUMN_USAGE.COLUMN_NAME);
         String uniqueKey = (String)record.get((Field)Tables.REFERENTIAL_CONSTRAINTS.UNIQUE_CONSTRAINT_NAME);
         TableDefinition referencingTable = this.getTable(foreignKeySchema, foreignKeyTable);
         if (referencingTable != null) {
            ColumnDefinition referencingColumn = referencingTable.getColumn(foreignKeyColumn);
            relations.addForeignKey(foreignKey, uniqueKey, referencingColumn, uniqueKeySchema);
         }
      }

   }

   protected void loadCheckConstraints(DefaultRelations relations) throws SQLException {
      TableConstraints tc = Tables.TABLE_CONSTRAINTS.as("tc");
      CheckConstraints cc = Tables.CHECK_CONSTRAINTS.as("cc");
      Field<String> constraintName = DSL.field(DSL.name(cc.CONSTRAINT_NAME.getName()), String.class);
      Iterator var5 = this.create().select(tc.TABLE_SCHEMA, tc.TABLE_NAME, constraintName, cc.CHECK_CLAUSE).from(tc).join((TableLike)cc).using(tc.CONSTRAINT_CATALOG, tc.CONSTRAINT_SCHEMA, tc.CONSTRAINT_NAME).where(new Condition[]{tc.TABLE_SCHEMA.in(this.getInputSchemata())}).fetch().iterator();

      while(var5.hasNext()) {
         Record record = (Record)var5.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)tc.TABLE_SCHEMA));
         TableDefinition table = this.getTable(schema, (String)record.get((Field)tc.TABLE_NAME));
         if (table != null) {
            relations.addCheckConstraint(table, new DefaultCheckConstraintDefinition(schema, table, (String)record.get(constraintName), (String)record.get((Field)cc.CHECK_CLAUSE)));
         }
      }

   }

   protected List<CatalogDefinition> getCatalogs0() throws SQLException {
      List<CatalogDefinition> result = new ArrayList();
      result.add(new CatalogDefinition(this, "", ""));
      return result;
   }

   protected List<SchemaDefinition> getSchemata0() throws SQLException {
      List<SchemaDefinition> result = new ArrayList();
      Iterator var2 = this.create().select((SelectField)Tables.SCHEMATA.SCHEMA_NAME).from(Tables.SCHEMATA).fetch(Tables.SCHEMATA.SCHEMA_NAME).iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();
         result.add(new SchemaDefinition(this, name, ""));
      }

      return result;
   }

   protected List<SequenceDefinition> getSequences0() throws SQLException {
      List<SequenceDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(Tables.SEQUENCES.SEQUENCE_SCHEMA, Tables.SEQUENCES.SEQUENCE_NAME, Tables.SEQUENCES.DATA_TYPE).from(Tables.SEQUENCES).where(new Condition[]{Tables.SEQUENCES.SEQUENCE_SCHEMA.in(this.getInputSchemata())}).orderBy(Tables.SEQUENCES.SEQUENCE_SCHEMA, Tables.SEQUENCES.SEQUENCE_NAME).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Tables.SEQUENCES.SEQUENCE_SCHEMA));
         DataTypeDefinition type = new DefaultDataTypeDefinition(this, schema, (String)record.get((Field)Tables.SEQUENCES.DATA_TYPE));
         result.add(new DefaultSequenceDefinition(schema, (String)record.get((Field)Tables.SEQUENCES.SEQUENCE_NAME), type));
      }

      return result;
   }

   protected List<TableDefinition> getTables0() throws SQLException {
      List<TableDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(Tables.TABLES.TABLE_SCHEMA, Tables.TABLES.TABLE_NAME).from(Tables.TABLES).where(new Condition[]{Tables.TABLES.TABLE_SCHEMA.in(this.getInputSchemata())}).orderBy(Tables.TABLES.TABLE_SCHEMA, Tables.TABLES.TABLE_NAME).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Tables.TABLES.TABLE_SCHEMA));
         String name = (String)record.get((Field)Tables.TABLES.TABLE_NAME);
         String comment = "";
         result.add(new HSQLDBTableDefinition(schema, name, comment));
      }

      return result;
   }

   protected List<EnumDefinition> getEnums0() throws SQLException {
      List<EnumDefinition> result = new ArrayList();
      return result;
   }

   protected List<DomainDefinition> getDomains0() throws SQLException {
      List<DomainDefinition> result = new ArrayList();
      return result;
   }

   protected List<UDTDefinition> getUDTs0() throws SQLException {
      List<UDTDefinition> result = new ArrayList();
      return result;
   }

   protected List<ArrayDefinition> getArrays0() throws SQLException {
      List<ArrayDefinition> result = new ArrayList();
      return result;
   }

   protected List<RoutineDefinition> getRoutines0() throws SQLException {
      List<RoutineDefinition> result = new ArrayList();

      Record record;
      String datatype;
      for(Iterator var2 = this.create().select(Tables.ROUTINES.ROUTINE_SCHEMA, Tables.ROUTINES.ROUTINE_NAME, Tables.ROUTINES.SPECIFIC_NAME, DSL.nvl((Field)Tables.ELEMENT_TYPES.COLLECTION_TYPE_IDENTIFIER, (Field)Tables.ROUTINES.DATA_TYPE).as("datatype"), Tables.ROUTINES.NUMERIC_PRECISION, Tables.ROUTINES.NUMERIC_SCALE, DSL.field(Tables.ROUTINES.ROUTINE_DEFINITION.likeRegex(".*(?i:(\\w+\\s+)+aggregate\\s+function).*")).as("aggregate")).from(Tables.ROUTINES).leftOuterJoin((TableLike)Tables.ELEMENT_TYPES).on(new Condition[]{Tables.ROUTINES.ROUTINE_SCHEMA.equal(Tables.ELEMENT_TYPES.OBJECT_SCHEMA)}).and(Tables.ROUTINES.ROUTINE_NAME.equal(Tables.ELEMENT_TYPES.OBJECT_NAME)).and(Tables.ROUTINES.DTD_IDENTIFIER.equal(Tables.ELEMENT_TYPES.COLLECTION_TYPE_IDENTIFIER)).where(new Condition[]{Tables.ROUTINES.ROUTINE_SCHEMA.in(this.getInputSchemata())}).orderBy(Tables.ROUTINES.ROUTINE_SCHEMA, Tables.ROUTINES.ROUTINE_NAME).fetch().iterator(); var2.hasNext(); result.add(new HSQLDBRoutineDefinition(this.getSchema((String)record.get((Field)Tables.ROUTINES.ROUTINE_SCHEMA)), (String)record.get((Field)Tables.ROUTINES.ROUTINE_NAME), (String)record.get((Field)Tables.ROUTINES.SPECIFIC_NAME), datatype, (Number)record.get((Field)Tables.ROUTINES.NUMERIC_PRECISION), (Number)record.get((Field)Tables.ROUTINES.NUMERIC_SCALE), (Boolean)record.get("aggregate", Boolean.TYPE)))) {
         record = (Record)var2.next();
         datatype = (String)record.get("datatype", String.class);
         if (datatype != null && datatype.toUpperCase().startsWith("ROW")) {
            JooqLogger.getLogger(this.getClass()).info("A row : " + datatype);
            datatype = "ROW";
         }
      }

      return result;
   }

   protected List<PackageDefinition> getPackages0() throws SQLException {
      List<PackageDefinition> result = new ArrayList();
      return result;
   }
}
