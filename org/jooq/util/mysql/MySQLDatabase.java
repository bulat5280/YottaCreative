package org.jooq.util.mysql;

import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.csv.CSVReader;
import org.jooq.util.AbstractDatabase;
import org.jooq.util.ArrayDefinition;
import org.jooq.util.CatalogDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DefaultEnumDefinition;
import org.jooq.util.DefaultRelations;
import org.jooq.util.DomainDefinition;
import org.jooq.util.EnumDefinition;
import org.jooq.util.PackageDefinition;
import org.jooq.util.RoutineDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.SequenceDefinition;
import org.jooq.util.TableDefinition;
import org.jooq.util.UDTDefinition;
import org.jooq.util.mysql.information_schema.Tables;
import org.jooq.util.mysql.information_schema.tables.Columns;
import org.jooq.util.mysql.information_schema.tables.KeyColumnUsage;
import org.jooq.util.mysql.information_schema.tables.ReferentialConstraints;
import org.jooq.util.mysql.information_schema.tables.Schemata;
import org.jooq.util.mysql.information_schema.tables.Statistics;
import org.jooq.util.mysql.mysql.enums.ProcType;
import org.jooq.util.mysql.mysql.tables.Proc;

public class MySQLDatabase extends AbstractDatabase {
   private static final JooqLogger log = JooqLogger.getLogger(MySQLDatabase.class);

   protected void loadPrimaryKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.fetchKeys(true).iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Statistics.TABLE_SCHEMA));
         String constraintName = (String)record.get((Field)Statistics.INDEX_NAME);
         String tableName = (String)record.get((Field)Statistics.TABLE_NAME);
         String columnName = (String)record.get((Field)Statistics.COLUMN_NAME);
         String key = this.getKeyName(tableName, constraintName);
         TableDefinition table = this.getTable(schema, tableName);
         if (table != null) {
            relations.addPrimaryKey(key, table.getColumn(columnName));
         }
      }

   }

   protected void loadUniqueKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.fetchKeys(false).iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Statistics.TABLE_SCHEMA));
         String constraintName = (String)record.get((Field)Statistics.INDEX_NAME);
         String tableName = (String)record.get((Field)Statistics.TABLE_NAME);
         String columnName = (String)record.get((Field)Statistics.COLUMN_NAME);
         String key = this.getKeyName(tableName, constraintName);
         TableDefinition table = this.getTable(schema, tableName);
         if (table != null) {
            relations.addUniqueKey(key, table.getColumn(columnName));
         }
      }

   }

   private String getKeyName(String tableName, String keyName) {
      return "KEY_" + tableName + "_" + keyName;
   }

   private Result<Record4<String, String, String, String>> fetchKeys(boolean primary) {
      return this.create().select(Statistics.TABLE_SCHEMA, Statistics.TABLE_NAME, Statistics.COLUMN_NAME, Statistics.INDEX_NAME).from(Tables.STATISTICS).where(new Condition[]{Statistics.TABLE_SCHEMA.in(this.getInputSchemata())}).and(primary ? Statistics.INDEX_NAME.eq(DSL.inline("PRIMARY")) : Statistics.INDEX_NAME.ne(DSL.inline("PRIMARY")).and(Statistics.NON_UNIQUE.eq(DSL.inline(0L)))).orderBy(Statistics.TABLE_SCHEMA, Statistics.TABLE_NAME, Statistics.INDEX_NAME, Statistics.SEQ_IN_INDEX).fetch();
   }

   protected void loadForeignKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.create().select(ReferentialConstraints.CONSTRAINT_SCHEMA, ReferentialConstraints.CONSTRAINT_NAME, ReferentialConstraints.TABLE_NAME, ReferentialConstraints.REFERENCED_TABLE_NAME, ReferentialConstraints.UNIQUE_CONSTRAINT_NAME, ReferentialConstraints.UNIQUE_CONSTRAINT_SCHEMA, KeyColumnUsage.COLUMN_NAME).from(Tables.REFERENTIAL_CONSTRAINTS).join((TableLike)Tables.KEY_COLUMN_USAGE).on(ReferentialConstraints.CONSTRAINT_SCHEMA.equal(KeyColumnUsage.CONSTRAINT_SCHEMA)).and(ReferentialConstraints.CONSTRAINT_NAME.equal(KeyColumnUsage.CONSTRAINT_NAME)).where(new Condition[]{ReferentialConstraints.CONSTRAINT_SCHEMA.in(this.getInputSchemata())}).orderBy(KeyColumnUsage.CONSTRAINT_SCHEMA.asc(), KeyColumnUsage.CONSTRAINT_NAME.asc(), KeyColumnUsage.ORDINAL_POSITION.asc()).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition foreignKeySchema = this.getSchema((String)record.get((Field)ReferentialConstraints.CONSTRAINT_SCHEMA));
         SchemaDefinition uniqueKeySchema = this.getSchema((String)record.get((Field)ReferentialConstraints.UNIQUE_CONSTRAINT_SCHEMA));
         String foreignKey = (String)record.get((Field)ReferentialConstraints.CONSTRAINT_NAME);
         String foreignKeyColumn = (String)record.get((Field)KeyColumnUsage.COLUMN_NAME);
         String foreignKeyTableName = (String)record.get((Field)ReferentialConstraints.TABLE_NAME);
         String referencedKey = (String)record.get((Field)ReferentialConstraints.UNIQUE_CONSTRAINT_NAME);
         String referencedTableName = (String)record.get((Field)ReferentialConstraints.REFERENCED_TABLE_NAME);
         TableDefinition foreignKeyTable = this.getTable(foreignKeySchema, foreignKeyTableName);
         if (foreignKeyTable != null) {
            ColumnDefinition column = foreignKeyTable.getColumn(foreignKeyColumn);
            String key = this.getKeyName(referencedTableName, referencedKey);
            relations.addForeignKey(foreignKey, key, column, uniqueKeySchema);
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
      Iterator var2 = this.create().select((SelectField)Schemata.SCHEMA_NAME).from(Tables.SCHEMATA).fetch(Schemata.SCHEMA_NAME).iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();
         result.add(new SchemaDefinition(this, name, ""));
      }

      return result;
   }

   protected List<SequenceDefinition> getSequences0() throws SQLException {
      List<SequenceDefinition> result = new ArrayList();
      return result;
   }

   protected List<TableDefinition> getTables0() throws SQLException {
      List<TableDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(org.jooq.util.mysql.information_schema.tables.Tables.TABLE_SCHEMA, org.jooq.util.mysql.information_schema.tables.Tables.TABLE_NAME, org.jooq.util.mysql.information_schema.tables.Tables.TABLE_COMMENT).from(Tables.TABLES).where(new Condition[]{org.jooq.util.mysql.information_schema.tables.Tables.TABLE_SCHEMA.in(this.getInputSchemata())}).orderBy(org.jooq.util.mysql.information_schema.tables.Tables.TABLE_SCHEMA, org.jooq.util.mysql.information_schema.tables.Tables.TABLE_NAME).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)org.jooq.util.mysql.information_schema.tables.Tables.TABLE_SCHEMA));
         String name = (String)record.get((Field)org.jooq.util.mysql.information_schema.tables.Tables.TABLE_NAME);
         String comment = (String)record.get((Field)org.jooq.util.mysql.information_schema.tables.Tables.TABLE_COMMENT);
         MySQLTableDefinition table = new MySQLTableDefinition(schema, name, comment);
         result.add(table);
      }

      return result;
   }

   protected List<EnumDefinition> getEnums0() throws SQLException {
      List<EnumDefinition> result = new ArrayList();
      Result<Record5<String, String, String, String, String>> records = this.create().select(Columns.TABLE_SCHEMA, Columns.COLUMN_COMMENT, Columns.TABLE_NAME, Columns.COLUMN_NAME, Columns.COLUMN_TYPE).from(Tables.COLUMNS).where(new Condition[]{Columns.COLUMN_TYPE.like("enum(%)").and(Columns.TABLE_SCHEMA.in(this.getInputSchemata()))}).orderBy(Columns.TABLE_SCHEMA.asc(), Columns.TABLE_NAME.asc(), Columns.COLUMN_NAME.asc()).fetch();
      Iterator var3 = records.iterator();

      while(true) {
         SchemaDefinition schema;
         String comment;
         String name;
         String columnType;
         ColumnDefinition columnDefinition;
         do {
            do {
               String column;
               TableDefinition tableDefinition;
               do {
                  if (!var3.hasNext()) {
                     return result;
                  }

                  Record record = (Record)var3.next();
                  schema = this.getSchema((String)record.get((Field)Columns.TABLE_SCHEMA));
                  comment = (String)record.get((Field)Columns.COLUMN_COMMENT);
                  String table = (String)record.get((Field)Columns.TABLE_NAME);
                  column = (String)record.get((Field)Columns.COLUMN_NAME);
                  name = table + "_" + column;
                  columnType = (String)record.get((Field)Columns.COLUMN_TYPE);
                  tableDefinition = this.getTable(schema, table);
               } while(tableDefinition == null);

               columnDefinition = tableDefinition.getColumn(column);
            } while(columnDefinition == null);
         } while(this.getConfiguredForcedType(columnDefinition, columnDefinition.getType()) != null);

         DefaultEnumDefinition definition = new DefaultEnumDefinition(schema, name, comment);
         CSVReader reader = new CSVReader(new StringReader(columnType.replaceAll("(^enum\\()|(\\)$)", "")), ',', '\'', true);
         String[] var15 = reader.next();
         int var16 = var15.length;

         for(int var17 = 0; var17 < var16; ++var17) {
            String string = var15[var17];
            definition.addLiteral(string);
         }

         result.add(definition);
      }
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
      ArrayList result = new ArrayList();

      try {
         this.create(true).fetchCount((Table)Proc.PROC);
      } catch (DataAccessException var15) {
         log.warn("Table unavailable", (Object)"The `mysql`.`proc` table is unavailable. Stored procedures cannot be loaded. Check if you have sufficient grants");
         return result;
      }

      Result<Record6<String, String, String, byte[], byte[], ProcType>> records = this.create().select(Proc.DB, Proc.NAME, Proc.COMMENT, Proc.PARAM_LIST, Proc.RETURNS, Proc.TYPE).from(Proc.PROC).where(new Condition[]{Proc.DB.in(this.getInputSchemata())}).orderBy(Proc.DB, Proc.NAME).fetch();
      Map<Record, Result<Record6<String, String, String, byte[], byte[], ProcType>>> groups = records.intoGroups(new Field[]{Proc.DB, Proc.NAME});
      Iterator var4 = groups.entrySet().iterator();

      while(var4.hasNext()) {
         Entry<Record, Result<Record6<String, String, String, byte[], byte[], ProcType>>> entry = (Entry)var4.next();
         Result<?> overloads = (Result)entry.getValue();

         for(int i = 0; i < overloads.size(); ++i) {
            Record record = (Record)overloads.get(i);
            SchemaDefinition schema = this.getSchema((String)record.get((Field)Proc.DB));
            String name = (String)record.get((Field)Proc.NAME);
            String comment = (String)record.get((Field)Proc.COMMENT);
            String params = new String((byte[])record.get((Field)Proc.PARAM_LIST));
            String returns = new String((byte[])record.get((Field)Proc.RETURNS));
            ProcType type = (ProcType)record.get((Field)Proc.TYPE);
            if (overloads.size() > 1) {
               result.add(new MySQLRoutineDefinition(schema, name, comment, params, returns, type, "_" + type.name()));
            } else {
               result.add(new MySQLRoutineDefinition(schema, name, comment, params, returns, type, (String)null));
            }
         }
      }

      return result;
   }

   protected List<PackageDefinition> getPackages0() throws SQLException {
      List<PackageDefinition> result = new ArrayList();
      return result;
   }

   protected DSLContext create0() {
      return DSL.using(this.getConnection(), SQLDialect.MYSQL);
   }
}
