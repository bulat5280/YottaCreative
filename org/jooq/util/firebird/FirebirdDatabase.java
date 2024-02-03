package org.jooq.util.firebird;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
import org.jooq.TableLike;
import org.jooq.impl.DSL;
import org.jooq.util.AbstractDatabase;
import org.jooq.util.ArrayDefinition;
import org.jooq.util.CatalogDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DataTypeDefinition;
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
import org.jooq.util.firebird.rdb.Tables;
import org.jooq.util.firebird.rdb.tables.Rdb$fields;
import org.jooq.util.firebird.rdb.tables.Rdb$indexSegments;
import org.jooq.util.firebird.rdb.tables.Rdb$refConstraints;
import org.jooq.util.firebird.rdb.tables.Rdb$relationConstraints;
import org.jooq.util.jaxb.Schema;

public class FirebirdDatabase extends AbstractDatabase {
   public FirebirdDatabase() {
      Schema schema = new Schema();
      schema.setInputSchema("");
      schema.setOutputSchema("");
      List<Schema> schemata = new ArrayList();
      schemata.add(schema);
      this.setConfiguredSchemata(schemata);
   }

   protected void loadPrimaryKeys(DefaultRelations r) throws SQLException {
      Iterator var2 = this.fetchKeys("PRIMARY KEY").iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         String tableName = (String)record.get(Tables.RDB$RELATION_CONSTRAINTS.RDB$RELATION_NAME.trim());
         String fieldName = (String)record.get(Tables.RDB$INDEX_SEGMENTS.RDB$FIELD_NAME.trim());
         String key = (String)record.get(Tables.RDB$RELATION_CONSTRAINTS.RDB$CONSTRAINT_NAME.trim());
         TableDefinition td = this.getTable((SchemaDefinition)this.getSchemata().get(0), tableName);
         if (td != null) {
            ColumnDefinition cd = td.getColumn(fieldName);
            r.addPrimaryKey(key, cd);
         }
      }

   }

   protected void loadUniqueKeys(DefaultRelations r) throws SQLException {
      Iterator var2 = this.fetchKeys("UNIQUE").iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         String tableName = (String)record.get(Tables.RDB$RELATION_CONSTRAINTS.RDB$RELATION_NAME.trim());
         String fieldName = (String)record.get(Tables.RDB$INDEX_SEGMENTS.RDB$FIELD_NAME.trim());
         String key = (String)record.get(Tables.RDB$RELATION_CONSTRAINTS.RDB$CONSTRAINT_NAME.trim());
         TableDefinition td = this.getTable((SchemaDefinition)this.getSchemata().get(0), tableName);
         if (td != null) {
            ColumnDefinition cd = td.getColumn(fieldName);
            r.addUniqueKey(key, cd);
         }
      }

   }

   private Result<Record3<String, String, String>> fetchKeys(String constraintType) {
      return this.create().select(Tables.RDB$RELATION_CONSTRAINTS.RDB$CONSTRAINT_NAME.trim(), Tables.RDB$RELATION_CONSTRAINTS.RDB$RELATION_NAME.trim(), Tables.RDB$INDEX_SEGMENTS.RDB$FIELD_NAME.trim()).from(Tables.RDB$RELATION_CONSTRAINTS).join((TableLike)Tables.RDB$INDEX_SEGMENTS).on(Tables.RDB$INDEX_SEGMENTS.RDB$INDEX_NAME.eq(Tables.RDB$RELATION_CONSTRAINTS.RDB$INDEX_NAME)).where(new Condition[]{Tables.RDB$RELATION_CONSTRAINTS.RDB$CONSTRAINT_TYPE.eq(constraintType)}).orderBy(Tables.RDB$RELATION_CONSTRAINTS.RDB$CONSTRAINT_NAME.asc(), Tables.RDB$INDEX_SEGMENTS.RDB$FIELD_POSITION.asc()).fetch();
   }

   protected void loadForeignKeys(DefaultRelations relations) throws SQLException {
      Rdb$relationConstraints pk = Tables.RDB$RELATION_CONSTRAINTS.as("pk");
      Rdb$relationConstraints fk = Tables.RDB$RELATION_CONSTRAINTS.as("fk");
      Rdb$refConstraints rc = Tables.RDB$REF_CONSTRAINTS.as("rc");
      Rdb$indexSegments isp = Tables.RDB$INDEX_SEGMENTS.as("isp");
      Rdb$indexSegments isf = Tables.RDB$INDEX_SEGMENTS.as("isf");
      Iterator var7 = this.create().selectDistinct(fk.RDB$CONSTRAINT_NAME.trim().as("fk"), fk.RDB$RELATION_NAME.trim().as("fkTable"), isf.RDB$FIELD_NAME.trim().as("fkField"), pk.RDB$CONSTRAINT_NAME.trim().as("pk"), pk.RDB$RELATION_NAME.trim().as("pkTable")).from(fk).join((TableLike)rc).on(fk.RDB$CONSTRAINT_NAME.eq(rc.RDB$CONSTRAINT_NAME)).join(pk).on(pk.RDB$CONSTRAINT_NAME.eq(rc.RDB$CONST_NAME_UQ)).join(isp).on(isp.RDB$INDEX_NAME.eq(pk.RDB$INDEX_NAME)).join(isf).on(isf.RDB$INDEX_NAME.eq(fk.RDB$INDEX_NAME)).where(new Condition[]{isp.RDB$FIELD_POSITION.eq(isf.RDB$FIELD_POSITION)}).orderBy(fk.RDB$CONSTRAINT_NAME.asc(), isf.RDB$FIELD_POSITION.asc()).fetch().iterator();

      while(var7.hasNext()) {
         Record record = (Record)var7.next();
         String pkName = (String)record.get("pk", String.class);
         String pkTable = (String)record.get("pkTable", String.class);
         String fkName = (String)record.get("fk", String.class);
         String fkTable = (String)record.get("fkTable", String.class);
         String fkField = (String)record.get("fkField", String.class);
         TableDefinition tdReferencing = this.getTable((SchemaDefinition)this.getSchemata().get(0), fkTable, true);
         TableDefinition tdReferenced = this.getTable((SchemaDefinition)this.getSchemata().get(0), pkTable, true);
         if (tdReferenced != null && tdReferencing != null) {
            ColumnDefinition referencingColumn = tdReferencing.getColumn(fkField);
            relations.addForeignKey(fkName, pkName, referencingColumn, (SchemaDefinition)this.getSchemata().get(0));
         }
      }

   }

   protected void loadCheckConstraints(DefaultRelations r) throws SQLException {
   }

   protected List<CatalogDefinition> getCatalogs0() throws SQLException {
      List<CatalogDefinition> result = new ArrayList();
      result.add(new CatalogDefinition(this, "", ""));
      return result;
   }

   protected List<SchemaDefinition> getSchemata0() throws SQLException {
      List<SchemaDefinition> result = new ArrayList();
      result.add(new SchemaDefinition(this, "", ""));
      return result;
   }

   protected List<SequenceDefinition> getSequences0() throws SQLException {
      List<SequenceDefinition> result = new ArrayList();
      Iterator var2 = this.create().select((SelectField)Tables.RDB$GENERATORS.RDB$GENERATOR_NAME.trim()).from(Tables.RDB$GENERATORS).orderBy(new int[]{1}).fetch(Tables.RDB$GENERATORS.RDB$GENERATOR_NAME.trim()).iterator();

      while(var2.hasNext()) {
         String sequenceName = (String)var2.next();
         SchemaDefinition schema = (SchemaDefinition)this.getSchemata().get(0);
         DataTypeDefinition type = new DefaultDataTypeDefinition(this, schema, FirebirdDataType.BIGINT.getTypeName());
         result.add(new DefaultSequenceDefinition(schema, sequenceName, type));
      }

      return result;
   }

   protected List<TableDefinition> getTables0() throws SQLException {
      List<TableDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(Tables.RDB$RELATIONS.RDB$RELATION_NAME.trim(), DSL.inline(false).as("table_valued_function")).from(Tables.RDB$RELATIONS).unionAll(DSL.select(Tables.RDB$PROCEDURES.RDB$PROCEDURE_NAME.trim(), DSL.inline(true).as("table_valued_function")).from(Tables.RDB$PROCEDURES).where(new Condition[]{Tables.RDB$PROCEDURES.RDB$PROCEDURE_TYPE.eq(Short.valueOf((short)1))}).and(this.tableValuedFunctions() ? DSL.trueCondition() : DSL.falseCondition())).orderBy(1).iterator();

      while(var2.hasNext()) {
         Record2<String, Boolean> record = (Record2)var2.next();
         if ((Boolean)record.value2()) {
            result.add(new FirebirdTableValuedFunction((SchemaDefinition)this.getSchemata().get(0), (String)record.value1(), ""));
         } else {
            result.add(new FirebirdTableDefinition((SchemaDefinition)this.getSchemata().get(0), (String)record.value1(), ""));
         }
      }

      return result;
   }

   protected List<RoutineDefinition> getRoutines0() throws SQLException {
      List<RoutineDefinition> result = new ArrayList();
      Iterator var2 = this.create().select((SelectField)Tables.RDB$PROCEDURES.RDB$PROCEDURE_NAME.trim()).from(Tables.RDB$PROCEDURES).where(new Condition[]{Tables.RDB$PROCEDURES.RDB$PROCEDURE_TYPE.eq(Short.valueOf((short)2))}).orderBy(new int[]{1}).fetch(0, String.class).iterator();

      while(var2.hasNext()) {
         String procedureName = (String)var2.next();
         result.add(new FirebirdRoutineDefinition((SchemaDefinition)this.getSchemata().get(0), procedureName));
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

   protected DSLContext create0() {
      return DSL.using(this.getConnection(), SQLDialect.FIREBIRD);
   }

   static Field<String> FIELD_TYPE(Rdb$fields f) {
      return DSL.decode().value((Field)f.RDB$FIELD_TYPE).when((Object)Short.valueOf((short)7), (Field)DSL.decode().when(f.RDB$FIELD_SUB_TYPE.eq(Short.valueOf((short)1)), (Object)"NUMERIC").when(f.RDB$FIELD_SUB_TYPE.eq(Short.valueOf((short)0)).and(f.RDB$FIELD_SCALE.lt(Short.valueOf((short)0))), (Object)"NUMERIC").when(f.RDB$FIELD_SUB_TYPE.eq(Short.valueOf((short)2)), (Object)"DECIMAL").otherwise((Object)"SMALLINT")).when((Object)Short.valueOf((short)8), (Field)DSL.decode().when(f.RDB$FIELD_SUB_TYPE.eq(Short.valueOf((short)1)), (Object)"NUMERIC").when(f.RDB$FIELD_SUB_TYPE.eq(Short.valueOf((short)0)).and(f.RDB$FIELD_SCALE.lt(Short.valueOf((short)0))), (Object)"NUMERIC").when(f.RDB$FIELD_SUB_TYPE.eq(Short.valueOf((short)2)), (Object)"DECIMAL").otherwise((Object)"INTEGER")).when((Object)Short.valueOf((short)9), (Object)"QUAD").when((Object)Short.valueOf((short)10), (Object)"FLOAT").when((Object)Short.valueOf((short)11), (Object)"D_FLOAT").when((Object)Short.valueOf((short)12), (Object)"DATE").when((Object)Short.valueOf((short)13), (Object)"TIME").when((Object)Short.valueOf((short)14), (Object)"CHAR").when((Object)Short.valueOf((short)16), (Field)DSL.decode().when(f.RDB$FIELD_SUB_TYPE.eq(Short.valueOf((short)1)), (Object)"NUMERIC").when(f.RDB$FIELD_SUB_TYPE.eq(Short.valueOf((short)0)).and(f.RDB$FIELD_SCALE.lt(Short.valueOf((short)0))), (Object)"NUMERIC").when(f.RDB$FIELD_SUB_TYPE.eq(Short.valueOf((short)2)), (Object)"DECIMAL").otherwise((Object)"BIGINT")).when((Object)Short.valueOf((short)27), (Object)"DOUBLE").when((Object)Short.valueOf((short)35), (Object)"TIMESTAMP").when((Object)Short.valueOf((short)37), (Object)"VARCHAR").when((Object)Short.valueOf((short)40), (Object)"CSTRING").when((short)261, (Field)DSL.decode().value((Field)f.RDB$FIELD_SUB_TYPE).when((Object)Short.valueOf((short)0), (Object)"BLOB").when((Object)Short.valueOf((short)1), (Object)"BLOB SUB_TYPE TEXT").otherwise((Object)"BLOB")).otherwise((Object)"UNKNOWN");
   }

   static Field<Short> FIELD_SCALE(Rdb$fields f) {
      return f.RDB$FIELD_SCALE.neg();
   }

   static Field<Short> CHARACTER_LENGTH(Rdb$fields f) {
      return DSL.choose((Field)f.RDB$FIELD_TYPE).when((short)261, (Object)Short.valueOf((short)0)).otherwise((Field)f.RDB$CHARACTER_LENGTH);
   }
}
