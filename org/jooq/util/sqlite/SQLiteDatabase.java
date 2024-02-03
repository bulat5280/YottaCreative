package org.jooq.util.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
import org.jooq.impl.DSL;
import org.jooq.util.AbstractDatabase;
import org.jooq.util.ArrayDefinition;
import org.jooq.util.CatalogDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DefaultRelations;
import org.jooq.util.DomainDefinition;
import org.jooq.util.EnumDefinition;
import org.jooq.util.PackageDefinition;
import org.jooq.util.RoutineDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.SequenceDefinition;
import org.jooq.util.TableDefinition;
import org.jooq.util.UDTDefinition;
import org.jooq.util.jaxb.Schema;
import org.jooq.util.sqlite.sqlite_master.SQLiteMaster;

public class SQLiteDatabase extends AbstractDatabase {
   public SQLiteDatabase() {
      Schema schema = new Schema();
      schema.setInputSchema("");
      schema.setOutputSchema("");
      List<Schema> schemata = new ArrayList();
      schemata.add(schema);
      this.setConfiguredSchemata(schemata);
   }

   protected DSLContext create0() {
      return DSL.using(this.getConnection(), SQLDialect.SQLITE);
   }

   protected void loadPrimaryKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.create().select((SelectField)SQLiteMaster.NAME).from(SQLiteMaster.SQLITE_MASTER).where(new Condition[]{SQLiteMaster.TYPE.in(new String[]{"table"})}).orderBy(SQLiteMaster.NAME).fetch(SQLiteMaster.NAME).iterator();

      while(var2.hasNext()) {
         String tableName = (String)var2.next();
         Iterator var4 = this.create().fetch("pragma table_info('" + tableName + "')").iterator();

         while(var4.hasNext()) {
            Record record = (Record)var4.next();
            if ((Integer)record.get("pk", Integer.TYPE) > 0) {
               String columnName = (String)record.get("name", String.class);
               String key = "pk_" + tableName;
               TableDefinition table = this.getTable((SchemaDefinition)this.getSchemata().get(0), tableName);
               if (table != null) {
                  ColumnDefinition column = table.getColumn(columnName);
                  relations.addPrimaryKey(key, column);
               }
            }
         }
      }

   }

   protected void loadUniqueKeys(DefaultRelations r) throws SQLException {
   }

   protected void loadForeignKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.getTables((SchemaDefinition)this.getSchemata().get(0)).iterator();

      while(var2.hasNext()) {
         TableDefinition table = (TableDefinition)var2.next();
         Map<String, Integer> map = new HashMap();
         Iterator var5 = this.create().fetch("pragma foreign_key_list(" + table.getName() + ")").iterator();

         while(var5.hasNext()) {
            Record record = (Record)var5.next();
            String foreignKeyPrefix = "fk_" + table.getName() + "_" + record.get("table");
            Integer sequence = (Integer)map.get(foreignKeyPrefix);
            if (sequence == null) {
               sequence = 0;
            }

            if (0 == (Integer)record.get("seq", Integer.class)) {
               sequence = sequence + 1;
            }

            map.put(foreignKeyPrefix, sequence);
            String foreignKey = "fk_" + table.getName() + "_" + record.get("table") + "_" + sequence;
            String foreignKeyTable = table.getName();
            String foreignKeyColumn = (String)record.get("from", String.class);
            TableDefinition referencingTable = this.getTable((SchemaDefinition)this.getSchemata().get(0), foreignKeyTable);
            TableDefinition referencedTable = this.getTable((SchemaDefinition)this.getSchemata().get(0), (String)record.get("table", String.class), true);
            if (referencedTable != null) {
               String uniqueKey = "pk_" + referencedTable.getName();
               if (referencingTable != null) {
                  ColumnDefinition referencingColumn = referencingTable.getColumn(foreignKeyColumn);
                  relations.addForeignKey(foreignKey, uniqueKey, referencingColumn, (SchemaDefinition)this.getSchemata().get(0));
               }
            }
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
      return result;
   }

   protected List<TableDefinition> getTables0() throws SQLException {
      List<TableDefinition> result = new ArrayList();
      Iterator var2 = this.create().select((SelectField)SQLiteMaster.NAME).from(SQLiteMaster.SQLITE_MASTER).where(new Condition[]{SQLiteMaster.TYPE.in(new String[]{"table", "view"})}).orderBy(SQLiteMaster.NAME).fetch(SQLiteMaster.NAME).iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();
         SQLiteTableDefinition table = new SQLiteTableDefinition((SchemaDefinition)this.getSchemata().get(0), name, "");
         result.add(table);
      }

      return result;
   }

   protected List<RoutineDefinition> getRoutines0() throws SQLException {
      List<RoutineDefinition> result = new ArrayList();
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
