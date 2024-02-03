package org.jooq.util.derby;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record5;
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
import org.jooq.util.derby.sys.tables.Sysconglomerates;
import org.jooq.util.derby.sys.tables.Sysconstraints;
import org.jooq.util.derby.sys.tables.Syskeys;
import org.jooq.util.derby.sys.tables.Sysschemas;
import org.jooq.util.derby.sys.tables.Syssequences;
import org.jooq.util.derby.sys.tables.Systables;

public class DerbyDatabase extends AbstractDatabase {
   protected void loadPrimaryKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.fetchKeys("P").iterator();

      while(true) {
         String key;
         String descriptor;
         TableDefinition table;
         do {
            if (!var2.hasNext()) {
               return;
            }

            Record record = (Record)var2.next();
            SchemaDefinition schema = this.getSchema((String)record.get((Field)Sysschemas.SCHEMANAME));
            key = (String)record.get((Field)Sysconstraints.CONSTRAINTNAME);
            String tableName = (String)record.get((Field)Systables.TABLENAME);
            descriptor = (String)record.get((Field)Sysconglomerates.DESCRIPTOR, (Class)String.class);
            table = this.getTable(schema, tableName);
         } while(table == null);

         Iterator var9 = this.decode(descriptor).iterator();

         while(var9.hasNext()) {
            int index = (Integer)var9.next();
            relations.addPrimaryKey(key, table.getColumn(index));
         }
      }
   }

   protected void loadUniqueKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.fetchKeys("U").iterator();

      while(true) {
         String key;
         String descriptor;
         TableDefinition table;
         do {
            if (!var2.hasNext()) {
               return;
            }

            Record record = (Record)var2.next();
            SchemaDefinition schema = this.getSchema((String)record.get((Field)Sysschemas.SCHEMANAME));
            key = (String)record.get((Field)Sysconstraints.CONSTRAINTNAME);
            String tableName = (String)record.get((Field)Systables.TABLENAME);
            descriptor = (String)record.get((Field)Sysconglomerates.DESCRIPTOR, (Class)String.class);
            table = this.getTable(schema, tableName);
         } while(table == null);

         Iterator var9 = this.decode(descriptor).iterator();

         while(var9.hasNext()) {
            int index = (Integer)var9.next();
            relations.addUniqueKey(key, table.getColumn(index));
         }
      }
   }

   private Result<Record5<String, String, String, String, String>> fetchKeys(String constraintType) {
      return this.create().select(Sysschemas.SCHEMANAME, Systables.TABLENAME, Systables.TABLEID, Sysconstraints.CONSTRAINTNAME, Sysconglomerates.DESCRIPTOR).from(Sysconglomerates.SYSCONGLOMERATES).join((TableLike)Syskeys.SYSKEYS).on(Syskeys.CONGLOMERATEID.equal(Sysconglomerates.CONGLOMERATEID)).join(Sysconstraints.SYSCONSTRAINTS).on(Sysconstraints.CONSTRAINTID.equal(Syskeys.CONSTRAINTID)).join(Systables.SYSTABLES).on(Systables.TABLEID.equal(Sysconglomerates.TABLEID)).join(Sysschemas.SYSSCHEMAS).on(Sysschemas.SCHEMAID.equal(Systables.SCHEMAID)).and(Sysschemas.SCHEMANAME.in(this.getInputSchemata())).where(new Condition[]{Sysconstraints.TYPE.equal(constraintType)}).orderBy(Sysschemas.SCHEMANAME, Systables.TABLENAME, Sysconstraints.CONSTRAINTNAME).fetch();
   }

   protected void loadForeignKeys(DefaultRelations relations) throws SQLException {
      Field<String> fkName = DSL.field("fc.constraintname", String.class);
      Field<String> fkTable = DSL.field("ft.tablename", String.class);
      Field<String> fkSchema = DSL.field("fs.schemaname", String.class);
      Field<?> fkDescriptor = DSL.field("fg.descriptor");
      Field<String> ukName = DSL.field("pc.constraintname", String.class);
      Field<String> ukSchema = DSL.field("ps.schemaname", String.class);
      Iterator var8 = this.create().select(fkName, fkTable, fkSchema, fkDescriptor, ukName, ukSchema).from("sys.sysconstraints   fc").join("sys.sysforeignkeys   f ").on("f.constraintid = fc.constraintid").join("sys.sysconglomerates fg").on("fg.conglomerateid = f.conglomerateid").join("sys.systables        ft").on("ft.tableid = fg.tableid").join("sys.sysschemas       fs").on("ft.schemaid = fs.schemaid").join("sys.sysconstraints   pc").on("pc.constraintid = f.keyconstraintid").join("sys.sysschemas       ps").on("pc.schemaid = ps.schemaid").where("fc.type = 'F'").fetch().iterator();

      while(true) {
         SchemaDefinition uniqueKeySchema;
         String foreignKeyName;
         List foreignKeyIndexes;
         String uniqueKeyName;
         TableDefinition referencingTable;
         do {
            if (!var8.hasNext()) {
               return;
            }

            Record record = (Record)var8.next();
            SchemaDefinition foreignKeySchema = this.getSchema((String)record.get(fkSchema));
            uniqueKeySchema = this.getSchema((String)record.get(ukSchema));
            foreignKeyName = (String)record.get(fkName);
            String foreignKeyTableName = (String)record.get(fkTable);
            foreignKeyIndexes = this.decode((String)record.get(fkDescriptor, String.class));
            uniqueKeyName = (String)record.get(ukName);
            referencingTable = this.getTable(foreignKeySchema, foreignKeyTableName);
         } while(referencingTable == null);

         for(int i = 0; i < foreignKeyIndexes.size(); ++i) {
            ColumnDefinition column = referencingTable.getColumn((Integer)foreignKeyIndexes.get(i));
            relations.addForeignKey(foreignKeyName, uniqueKeyName, column, uniqueKeySchema);
         }
      }
   }

   private List<Integer> decode(String descriptor) {
      List<Integer> result = new ArrayList();
      Pattern p = Pattern.compile(".*?\\((.*?)\\)");
      Matcher m = p.matcher(descriptor);

      while(true) {
         String[] split;
         do {
            if (!m.find()) {
               return result;
            }

            split = m.group(1).split(",");
         } while(split == null);

         String[] var6 = split;
         int var7 = split.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String index = var6[var8];
            result.add(Integer.valueOf(index.trim()) - 1);
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
      Iterator var2 = this.create().select((SelectField)Sysschemas.SCHEMANAME).from(Sysschemas.SYSSCHEMAS).fetch(Sysschemas.SCHEMANAME).iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();
         result.add(new SchemaDefinition(this, name, ""));
      }

      return result;
   }

   protected List<SequenceDefinition> getSequences0() throws SQLException {
      List<SequenceDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(Sysschemas.SCHEMANAME, Syssequences.SEQUENCENAME, Syssequences.SEQUENCEDATATYPE).from(Syssequences.SYSSEQUENCES).join((TableLike)Sysschemas.SYSSCHEMAS).on(Sysschemas.SCHEMAID.equal(Syssequences.SCHEMAID)).where(new Condition[]{Sysschemas.SCHEMANAME.in(this.getInputSchemata())}).orderBy(Sysschemas.SCHEMANAME, Syssequences.SEQUENCENAME).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Sysschemas.SCHEMANAME));
         DataTypeDefinition type = new DefaultDataTypeDefinition(this, schema, (String)record.get((Field)Syssequences.SEQUENCEDATATYPE));
         result.add(new DefaultSequenceDefinition(schema, (String)record.get((Field)Syssequences.SEQUENCENAME, (Class)String.class), type));
      }

      return result;
   }

   protected List<TableDefinition> getTables0() throws SQLException {
      List<TableDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(Sysschemas.SCHEMANAME, Systables.TABLENAME, Systables.TABLEID).from(Systables.SYSTABLES).join((TableLike)Sysschemas.SYSSCHEMAS).on(Systables.SCHEMAID.equal(Sysschemas.SCHEMAID)).where(new Condition[]{Sysschemas.SCHEMANAME.in(this.getInputSchemata())}).orderBy(Sysschemas.SCHEMANAME, Systables.TABLENAME).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Sysschemas.SCHEMANAME));
         String name = (String)record.get((Field)Systables.TABLENAME);
         String id = (String)record.get((Field)Systables.TABLEID);
         DerbyTableDefinition table = new DerbyTableDefinition(schema, name, id);
         result.add(table);
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
      return result;
   }

   protected List<PackageDefinition> getPackages0() throws SQLException {
      List<PackageDefinition> result = new ArrayList();
      return result;
   }

   protected DSLContext create0() {
      return DSL.using(this.getConnection(), SQLDialect.DERBY);
   }
}
