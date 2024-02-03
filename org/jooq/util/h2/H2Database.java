package org.jooq.util.h2;

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
import org.jooq.TableLike;
import org.jooq.impl.DSL;
import org.jooq.util.AbstractDatabase;
import org.jooq.util.ArrayDefinition;
import org.jooq.util.CatalogDefinition;
import org.jooq.util.ColumnDefinition;
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
import org.jooq.util.h2.information_schema.tables.Columns;
import org.jooq.util.h2.information_schema.tables.Constraints;
import org.jooq.util.h2.information_schema.tables.CrossReferences;
import org.jooq.util.h2.information_schema.tables.FunctionAliases;
import org.jooq.util.h2.information_schema.tables.Indexes;
import org.jooq.util.h2.information_schema.tables.Schemata;
import org.jooq.util.h2.information_schema.tables.Sequences;
import org.jooq.util.h2.information_schema.tables.Tables;
import org.jooq.util.h2.information_schema.tables.TypeInfo;

public class H2Database extends AbstractDatabase {
   protected DSLContext create0() {
      return DSL.using(this.getConnection(), SQLDialect.H2);
   }

   protected void loadPrimaryKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.fetchKeys("PRIMARY KEY").iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Constraints.TABLE_SCHEMA));
         if (schema != null) {
            String tableName = (String)record.get((Field)Constraints.TABLE_NAME);
            String primaryKey = (String)record.get((Field)Constraints.CONSTRAINT_NAME);
            String columnName = (String)record.get((Field)Indexes.COLUMN_NAME);
            TableDefinition table = this.getTable(schema, tableName);
            if (table != null) {
               relations.addPrimaryKey(primaryKey, table.getColumn(columnName));
            }
         }
      }

   }

   protected void loadUniqueKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.fetchKeys("UNIQUE").iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Constraints.TABLE_SCHEMA));
         if (schema != null) {
            String tableName = (String)record.get((Field)Constraints.TABLE_NAME);
            String primaryKey = (String)record.get((Field)Constraints.CONSTRAINT_NAME);
            String columnName = (String)record.get((Field)Indexes.COLUMN_NAME);
            TableDefinition table = this.getTable(schema, tableName);
            if (table != null) {
               relations.addUniqueKey(primaryKey, table.getColumn(columnName));
            }
         }
      }

   }

   private Result<Record4<String, String, String, String>> fetchKeys(String constraintType) {
      return this.create().select(Constraints.TABLE_SCHEMA, Constraints.TABLE_NAME, Constraints.CONSTRAINT_NAME, Indexes.COLUMN_NAME).from(Constraints.CONSTRAINTS).join((TableLike)Indexes.INDEXES).on(Constraints.TABLE_SCHEMA.eq(Indexes.TABLE_SCHEMA)).and(Constraints.TABLE_NAME.eq(Indexes.TABLE_NAME)).and(Constraints.UNIQUE_INDEX_NAME.eq(Indexes.INDEX_NAME)).where(new Condition[]{Constraints.TABLE_SCHEMA.in(this.getInputSchemata())}).and(Constraints.CONSTRAINT_TYPE.equal(constraintType)).orderBy(Constraints.TABLE_SCHEMA, Constraints.CONSTRAINT_NAME, Indexes.ORDINAL_POSITION).fetch();
   }

   protected void loadForeignKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.create().select(CrossReferences.FK_NAME, CrossReferences.FKTABLE_NAME, CrossReferences.FKTABLE_SCHEMA, CrossReferences.FKCOLUMN_NAME, Constraints.CONSTRAINT_NAME, Constraints.CONSTRAINT_SCHEMA).from(CrossReferences.CROSS_REFERENCES).join((TableLike)Constraints.CONSTRAINTS).on(CrossReferences.PK_NAME.equal(Constraints.UNIQUE_INDEX_NAME)).and(CrossReferences.PKTABLE_NAME.equal(Constraints.TABLE_NAME)).and(CrossReferences.PKTABLE_SCHEMA.equal(Constraints.TABLE_SCHEMA)).where(new Condition[]{CrossReferences.FKTABLE_SCHEMA.in(this.getInputSchemata())}).and(Constraints.CONSTRAINT_TYPE.in(new String[]{"PRIMARY KEY", "UNIQUE"})).orderBy(CrossReferences.FKTABLE_SCHEMA.asc(), CrossReferences.FK_NAME.asc(), CrossReferences.ORDINAL_POSITION.asc()).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition foreignKeySchema = this.getSchema((String)record.get((Field)CrossReferences.FKTABLE_SCHEMA));
         SchemaDefinition uniqueKeySchema = this.getSchema((String)record.get((Field)Constraints.CONSTRAINT_SCHEMA));
         if (foreignKeySchema != null && uniqueKeySchema != null) {
            String foreignKeyTableName = (String)record.get((Field)CrossReferences.FKTABLE_NAME);
            String foreignKeyColumn = (String)record.get((Field)CrossReferences.FKCOLUMN_NAME);
            String foreignKey = (String)record.get((Field)CrossReferences.FK_NAME);
            String uniqueKey = (String)record.get((Field)Constraints.CONSTRAINT_NAME);
            TableDefinition foreignKeyTable = this.getTable(foreignKeySchema, foreignKeyTableName);
            if (foreignKeyTable != null) {
               ColumnDefinition referencingColumn = foreignKeyTable.getColumn(foreignKeyColumn);
               relations.addForeignKey(foreignKey, uniqueKey, referencingColumn, uniqueKeySchema);
            }
         }
      }

   }

   protected void loadCheckConstraints(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.create().select(Constraints.TABLE_SCHEMA, Constraints.TABLE_NAME, Constraints.CONSTRAINT_NAME, Constraints.CHECK_EXPRESSION).from(Constraints.CONSTRAINTS).where(new Condition[]{Constraints.CONSTRAINT_TYPE.eq("CHECK")}).and(Constraints.TABLE_SCHEMA.in(this.getInputSchemata())).union(DSL.select(Columns.TABLE_SCHEMA, Columns.TABLE_NAME, Columns.CHECK_CONSTRAINT, Columns.CHECK_CONSTRAINT).from(Columns.COLUMNS).where(new Condition[]{Columns.CHECK_CONSTRAINT.nvl("").ne((Object)"")}).and(Columns.TABLE_SCHEMA.in(this.getInputSchemata()))).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Constraints.TABLE_SCHEMA));
         if (schema != null) {
            TableDefinition table = this.getTable(schema, (String)record.get((Field)Constraints.TABLE_NAME));
            if (table != null) {
               relations.addCheckConstraint(table, new DefaultCheckConstraintDefinition(schema, table, (String)record.get((Field)Constraints.CONSTRAINT_NAME), (String)record.get((Field)Constraints.CHECK_EXPRESSION)));
            }
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
      Iterator var2 = this.create().select(Schemata.SCHEMA_NAME, Schemata.REMARKS).from(Schemata.SCHEMATA).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         result.add(new SchemaDefinition(this, (String)record.get((Field)Schemata.SCHEMA_NAME), (String)record.get((Field)Schemata.REMARKS)));
      }

      return result;
   }

   protected List<SequenceDefinition> getSequences0() throws SQLException {
      List<SequenceDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(Sequences.SEQUENCE_SCHEMA, Sequences.SEQUENCE_NAME).from(Sequences.SEQUENCES).where(new Condition[]{Sequences.SEQUENCE_SCHEMA.in(this.getInputSchemata())}).orderBy(Sequences.SEQUENCE_SCHEMA, Sequences.SEQUENCE_NAME).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Sequences.SEQUENCE_SCHEMA));
         if (schema != null) {
            String name = (String)record.get((Field)Sequences.SEQUENCE_NAME);
            DefaultDataTypeDefinition type = new DefaultDataTypeDefinition(this, schema, H2DataType.BIGINT.getTypeName());
            result.add(new DefaultSequenceDefinition(schema, name, type));
         }
      }

      return result;
   }

   protected List<TableDefinition> getTables0() throws SQLException {
      List<TableDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(Tables.TABLE_SCHEMA, Tables.TABLE_NAME, Tables.REMARKS).from(Tables.TABLES).where(new Condition[]{Tables.TABLE_SCHEMA.in(this.getInputSchemata())}).orderBy(Tables.TABLE_SCHEMA, Tables.ID).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Tables.TABLE_SCHEMA));
         if (schema != null) {
            String name = (String)record.get((Field)Tables.TABLE_NAME);
            String comment = (String)record.get((Field)Tables.REMARKS);
            H2TableDefinition table = new H2TableDefinition(schema, name, comment);
            result.add(table);
         }
      }

      return result;
   }

   protected List<RoutineDefinition> getRoutines0() throws SQLException {
      List<RoutineDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(FunctionAliases.ALIAS_SCHEMA, FunctionAliases.ALIAS_NAME, FunctionAliases.REMARKS, FunctionAliases.DATA_TYPE, FunctionAliases.RETURNS_RESULT, TypeInfo.TYPE_NAME, TypeInfo.PRECISION, TypeInfo.MAXIMUM_SCALE).from(FunctionAliases.FUNCTION_ALIASES).leftOuterJoin((TableLike)TypeInfo.TYPE_INFO).on(new Condition[]{FunctionAliases.DATA_TYPE.equal(TypeInfo.DATA_TYPE)}).and(TypeInfo.POS.equal(0)).where(new Condition[]{FunctionAliases.ALIAS_SCHEMA.in(this.getInputSchemata())}).and(FunctionAliases.RETURNS_RESULT.in(new Short[]{Short.valueOf((short)1), Short.valueOf((short)2)})).orderBy(FunctionAliases.ALIAS_NAME).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)FunctionAliases.ALIAS_SCHEMA));
         if (schema != null) {
            String name = (String)record.get((Field)FunctionAliases.ALIAS_NAME);
            String comment = (String)record.get((Field)FunctionAliases.REMARKS);
            String typeName = (String)record.get((Field)TypeInfo.TYPE_NAME);
            Integer precision = (Integer)record.get((Field)TypeInfo.PRECISION);
            Short scale = (Short)record.get((Field)TypeInfo.MAXIMUM_SCALE);
            result.add(new H2RoutineDefinition(schema, name, comment, typeName, precision, scale));
         }
      }

      return result;
   }

   protected List<PackageDefinition> getPackages0() throws SQLException {
      List<PackageDefinition> result = new ArrayList();
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
}
