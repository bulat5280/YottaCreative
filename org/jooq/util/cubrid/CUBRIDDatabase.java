package org.jooq.util.cubrid;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.ConnectionRunnable;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
import org.jooq.TableLike;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.jooq.util.AbstractDatabase;
import org.jooq.util.ArrayDefinition;
import org.jooq.util.CatalogDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultEnumDefinition;
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
import org.jooq.util.cubrid.dba.Tables;

public class CUBRIDDatabase extends AbstractDatabase {
   protected void loadPrimaryKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.fetchKeys(Tables.DB_INDEX.IS_UNIQUE.isTrue().and(Tables.DB_INDEX.IS_PRIMARY_KEY.isFalse())).iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         String key = (String)record.get("constraint_name", String.class);
         String tableName = (String)record.get((Field)Tables.DB_CLASS.CLASS_NAME);
         String columnName = (String)record.get((Field)Tables.DB_INDEX_KEY.KEY_ATTR_NAME);
         TableDefinition table = this.getTable((SchemaDefinition)this.getSchemata().get(0), tableName);
         if (table != null) {
            relations.addUniqueKey(key, table.getColumn(columnName));
         }
      }

   }

   protected void loadUniqueKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.fetchKeys(Tables.DB_INDEX.IS_PRIMARY_KEY.isTrue()).iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         String key = (String)record.get("constraint_name", String.class);
         String tableName = (String)record.get((Field)Tables.DB_CLASS.CLASS_NAME);
         String columnName = (String)record.get((Field)Tables.DB_INDEX_KEY.KEY_ATTR_NAME);
         TableDefinition table = this.getTable((SchemaDefinition)this.getSchemata().get(0), tableName);
         if (table != null) {
            relations.addPrimaryKey(key, table.getColumn(columnName));
         }
      }

   }

   private Result<Record3<String, String, String>> fetchKeys(Condition condition) {
      return this.create().select(DSL.concat(Tables.DB_CLASS.CLASS_NAME, DSL.val("__"), Tables.DB_INDEX.INDEX_NAME).as("constraint_name"), Tables.DB_INDEX_KEY.KEY_ATTR_NAME, Tables.DB_CLASS.CLASS_NAME).from(Tables.DB_INDEX).join((TableLike)Tables.DB_CLASS).on(Tables.DB_INDEX.CLASS_NAME.equal(Tables.DB_CLASS.CLASS_NAME)).join(Tables.DB_INDEX_KEY).on(Tables.DB_INDEX_KEY.INDEX_NAME.equal(Tables.DB_INDEX.INDEX_NAME).and(Tables.DB_INDEX_KEY.CLASS_NAME.equal(Tables.DB_INDEX.CLASS_NAME))).where(new Condition[]{condition}).orderBy(Tables.DB_INDEX.INDEX_NAME.asc()).fetch();
   }

   protected void loadForeignKeys(final DefaultRelations relations) throws SQLException {
      this.create().connection(new ConnectionRunnable() {
         public void run(Connection connection) throws SQLException {
            DatabaseMetaData meta = connection.getMetaData();
            Iterator var3 = CUBRIDDatabase.this.create().selectDistinct((SelectField)Tables.DB_INDEX.CLASS_NAME).from(Tables.DB_INDEX).where(new Condition[]{Tables.DB_INDEX.IS_FOREIGN_KEY.isTrue()}).fetch(Tables.DB_INDEX.CLASS_NAME).iterator();

            while(var3.hasNext()) {
               String table = (String)var3.next();
               Iterator var5 = CUBRIDDatabase.this.create().fetch(meta.getImportedKeys((String)null, (String)null, table)).iterator();

               while(var5.hasNext()) {
                  Record record = (Record)var5.next();
                  String foreignKeyName = (String)record.get("FKTABLE_NAME", String.class) + "__" + (String)record.get("FK_NAME", String.class);
                  String foreignKeyTableName = (String)record.get("FKTABLE_NAME", String.class);
                  String foreignKeyColumnName = (String)record.get("FKCOLUMN_NAME", String.class);
                  String uniqueKeyName = (String)record.get("PKTABLE_NAME", String.class) + "__" + (String)record.get("PK_NAME", String.class);
                  TableDefinition referencingTable = CUBRIDDatabase.this.getTable((SchemaDefinition)CUBRIDDatabase.this.getSchemata().get(0), foreignKeyTableName);
                  if (referencingTable != null) {
                     ColumnDefinition column = referencingTable.getColumn(foreignKeyColumnName);
                     relations.addForeignKey(foreignKeyName, uniqueKeyName, column, (SchemaDefinition)CUBRIDDatabase.this.getSchemata().get(0));
                  }
               }
            }

         }
      });
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
      Iterator var2 = this.create().select(Tables.DB_SERIAL.NAME, Tables.DB_SERIAL.MAX_VAL).from(Tables.DB_SERIAL).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         BigInteger value = (BigInteger)StringUtils.defaultIfNull(record.get((Field)Tables.DB_SERIAL.MAX_VAL, (Class)BigInteger.class), BigInteger.valueOf(Long.MAX_VALUE));
         DataTypeDefinition type = this.getDataTypeForMAX_VAL((SchemaDefinition)this.getSchemata().get(0), value);
         result.add(new DefaultSequenceDefinition((SchemaDefinition)this.getSchemata().get(0), (String)record.get((Field)Tables.DB_SERIAL.NAME), type));
      }

      return result;
   }

   protected List<TableDefinition> getTables0() throws SQLException {
      List<TableDefinition> result = new ArrayList();
      Iterator var2 = this.create().select((SelectField)Tables.DB_CLASS.CLASS_NAME).from(Tables.DB_CLASS).orderBy(Tables.DB_CLASS.CLASS_NAME.asc()).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         String name = (String)record.get((Field)Tables.DB_CLASS.CLASS_NAME);
         CUBRIDTableDefinition table = new CUBRIDTableDefinition((SchemaDefinition)this.getSchemata().get(0), name, (String)null);
         result.add(table);
      }

      return result;
   }

   protected List<EnumDefinition> getEnums0() throws SQLException {
      List<EnumDefinition> result = new ArrayList();
      Iterator var2 = this.getTables((SchemaDefinition)this.getSchemata().get(0)).iterator();

      label33:
      while(var2.hasNext()) {
         TableDefinition tableDefinition = (TableDefinition)var2.next();
         Iterator var4 = this.create().fetch("SHOW COLUMNS FROM {0} WHERE TYPE LIKE 'ENUM(%)'", DSL.field(DSL.name(tableDefinition.getInputName()))).iterator();

         while(true) {
            String columnType;
            String name;
            ColumnDefinition columnDefinition;
            do {
               if (!var4.hasNext()) {
                  continue label33;
               }

               Record record = (Record)var4.next();
               String table = tableDefinition.getInputName();
               String column = (String)record.get("Field", String.class);
               columnType = (String)record.get("Type", String.class);
               name = table + "_" + column;
               columnDefinition = tableDefinition.getColumn(column);
            } while(this.getConfiguredForcedType(columnDefinition) != null);

            DefaultEnumDefinition definition = new DefaultEnumDefinition((SchemaDefinition)this.getSchemata().get(0), name, "");
            String[] var12 = columnType.replaceAll("ENUM\\(|\\)", "").split(",");
            int var13 = var12.length;

            for(int var14 = 0; var14 < var13; ++var14) {
               String string = var12[var14];
               definition.addLiteral(string.trim().replaceAll("'", ""));
            }

            result.add(definition);
         }
      }

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
      return DSL.using(this.getConnection(), SQLDialect.CUBRID);
   }
}
