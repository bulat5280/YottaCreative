package org.jooq.util.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.UniqueKey;
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

public class JDBCDatabase extends AbstractDatabase {
   private List<Schema> schemas;

   protected DSLContext create0() {
      return DSL.using(this.getConnection());
   }

   protected void loadPrimaryKeys(DefaultRelations relations) throws SQLException {
   }

   protected void loadUniqueKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.getSchemasFromMeta().iterator();

      label41:
      while(true) {
         Schema schema;
         SchemaDefinition s;
         do {
            if (!var2.hasNext()) {
               return;
            }

            schema = (Schema)var2.next();
            s = this.getSchema(schema.getName());
         } while(s == null);

         Iterator var5 = schema.getTables().iterator();

         while(true) {
            TableDefinition t;
            UniqueKey key;
            do {
               Table table;
               do {
                  if (!var5.hasNext()) {
                     continue label41;
                  }

                  table = (Table)var5.next();
                  t = this.getTable(s, table.getName());
               } while(t == null);

               key = table.getPrimaryKey();
            } while(key == null);

            Iterator var9 = key.getFields().iterator();

            while(var9.hasNext()) {
               Field<?> field = (Field)var9.next();
               ColumnDefinition c = t.getColumn(field.getName());
               relations.addPrimaryKey("PK_" + key.getTable().getName(), c);
            }
         }
      }
   }

   protected void loadForeignKeys(DefaultRelations relations) throws SQLException {
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
      Iterator var2 = this.getSchemasFromMeta().iterator();

      while(var2.hasNext()) {
         Schema schema = (Schema)var2.next();
         result.add(new SchemaDefinition(this, schema.getName(), ""));
      }

      return result;
   }

   private List<Schema> getSchemasFromMeta() {
      if (this.schemas == null) {
         this.schemas = new ArrayList();
         Iterator var1 = this.create().meta().getSchemas().iterator();

         while(var1.hasNext()) {
            Schema schema = (Schema)var1.next();
            if (this.getInputSchemata().contains(schema.getName())) {
               this.schemas.add(schema);
            }
         }
      }

      return this.schemas;
   }

   protected List<SequenceDefinition> getSequences0() throws SQLException {
      List<SequenceDefinition> result = new ArrayList();
      Iterator var2 = this.getSchemasFromMeta().iterator();

      while(var2.hasNext()) {
         Schema schema = (Schema)var2.next();
         Iterator var4 = schema.getSequences().iterator();

         while(var4.hasNext()) {
            Sequence<?> sequence = (Sequence)var4.next();
            SchemaDefinition sd = this.getSchema(schema.getName());
            DataTypeDefinition type = new DefaultDataTypeDefinition(this, sd, sequence.getDataType().getTypeName());
            result.add(new DefaultSequenceDefinition(sd, sequence.getName(), type));
         }
      }

      return result;
   }

   protected List<TableDefinition> getTables0() throws SQLException {
      List<TableDefinition> result = new ArrayList();
      Iterator var2 = this.getSchemasFromMeta().iterator();

      while(true) {
         Schema schema;
         SchemaDefinition sd;
         do {
            if (!var2.hasNext()) {
               return result;
            }

            schema = (Schema)var2.next();
            sd = this.getSchema(schema.getName());
         } while(sd == null);

         Iterator var5 = schema.getTables().iterator();

         while(var5.hasNext()) {
            Table<?> table = (Table)var5.next();
            result.add(new JDBCTableDefinition(sd, table));
         }
      }
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
}
