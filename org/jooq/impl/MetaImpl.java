package org.jooq.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.ConnectionCallable;
import org.jooq.Constraint;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Meta;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.exception.SQLDialectNotSupportedException;

final class MetaImpl implements Meta, Serializable {
   private static final long serialVersionUID = 3582980783173033809L;
   private final DSLContext ctx;
   private final Configuration configuration;
   private final boolean inverseSchemaCatalog;

   MetaImpl(Configuration configuration) {
      this.ctx = DSL.using(configuration);
      this.configuration = configuration;
      this.inverseSchemaCatalog = Arrays.asList(SQLDialect.MYSQL, SQLDialect.MARIADB).contains(configuration.family());
   }

   private final Result<Record> meta(final MetaImpl.MetaFunction consumer) {
      return (Result)this.ctx.connectionResult(new ConnectionCallable<Result<Record>>() {
         public Result<Record> run(Connection connection) throws SQLException {
            return consumer.run(connection.getMetaData());
         }
      });
   }

   public final List<Catalog> getCatalogs() {
      List<Catalog> result = new ArrayList();
      if (!this.inverseSchemaCatalog) {
         Result<Record> catalogs = this.meta(new MetaImpl.MetaFunction() {
            public Result<Record> run(DatabaseMetaData meta) throws SQLException {
               return MetaImpl.this.ctx.fetch(meta.getCatalogs(), SQLDataType.VARCHAR);
            }
         });
         Iterator var3 = catalogs.getValues(0, (Class)String.class).iterator();

         while(var3.hasNext()) {
            String name = (String)var3.next();
            result.add(new MetaImpl.MetaCatalog(name));
         }
      }

      if (result.isEmpty()) {
         result.add(new MetaImpl.MetaCatalog(""));
      }

      return result;
   }

   public final List<Schema> getSchemas() {
      List<Schema> result = new ArrayList();
      Iterator var2 = this.getCatalogs().iterator();

      while(var2.hasNext()) {
         Catalog catalog = (Catalog)var2.next();
         result.addAll(catalog.getSchemas());
      }

      return result;
   }

   public final List<Table<?>> getTables() {
      List<Table<?>> result = new ArrayList();
      Iterator var2 = this.getSchemas().iterator();

      while(var2.hasNext()) {
         Schema schema = (Schema)var2.next();
         result.addAll(schema.getTables());
      }

      return result;
   }

   public final List<Sequence<?>> getSequences() {
      List<Sequence<?>> result = new ArrayList();
      Iterator var2 = this.getSchemas().iterator();

      while(var2.hasNext()) {
         Schema schema = (Schema)var2.next();
         result.addAll(schema.getSequences());
      }

      return result;
   }

   public final List<UniqueKey<?>> getPrimaryKeys() {
      List<UniqueKey<?>> result = new ArrayList();
      Iterator var2 = this.getTables().iterator();

      while(var2.hasNext()) {
         Table<?> table = (Table)var2.next();
         UniqueKey<?> pk = table.getPrimaryKey();
         if (pk != null) {
            result.add(pk);
         }
      }

      return result;
   }

   private class MetaPrimaryKey implements UniqueKey<Record> {
      private static final long serialVersionUID = 6997258619475953490L;
      private final String pkName;
      private final Table<Record> pkTable;
      private final TableField<Record, ?>[] pkFields;

      MetaPrimaryKey(Table<Record> table, String pkName, TableField<Record, ?>[] fields) {
         this.pkName = pkName;
         this.pkTable = table;
         this.pkFields = fields;
      }

      public final String getName() {
         return this.pkName;
      }

      public final Table<Record> getTable() {
         return this.pkTable;
      }

      public final List<TableField<Record, ?>> getFields() {
         return Collections.unmodifiableList(Arrays.asList(this.pkFields));
      }

      public final TableField<Record, ?>[] getFieldsArray() {
         return (TableField[])this.pkFields.clone();
      }

      public final boolean isPrimary() {
         return true;
      }

      public final List<ForeignKey<?, Record>> getReferences() {
         List<ForeignKey<?, Record>> references = new ArrayList();
         Result<Record> result = MetaImpl.this.meta(new MetaImpl.MetaFunction() {
            public Result<Record> run(DatabaseMetaData meta) throws SQLException {
               ResultSet rs = meta.getExportedKeys((String)null, MetaPrimaryKey.this.pkTable.getSchema().getName(), MetaPrimaryKey.this.pkTable.getName());
               return MetaImpl.this.ctx.fetch(rs, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, Short.class, Short.class, Short.class, String.class, String.class);
            }
         });
         Map<Record, Result<Record>> groups = result.intoGroups(new Field[]{result.field(MetaImpl.this.inverseSchemaCatalog ? 5 : 4), result.field(MetaImpl.this.inverseSchemaCatalog ? 4 : 5), result.field(6), result.field(11), result.field(12)});
         Map<String, Schema> schemas = new HashMap();
         Iterator var5 = MetaImpl.this.getSchemas().iterator();

         while(var5.hasNext()) {
            Schema schema = (Schema)var5.next();
            schemas.put(schema.getName(), schema);
         }

         var5 = groups.entrySet().iterator();

         while(var5.hasNext()) {
            Entry<Record, Result<Record>> entry = (Entry)var5.next();
            Schema schemax = (Schema)schemas.get(((Record)entry.getKey()).get(1));
            Table<Record> fkTable = schemax.getTable((String)((Record)entry.getKey()).get(2, (Class)String.class));
            TableField<Record, ?>[] fkFields = new TableField[((Result)entry.getValue()).size()];

            for(int i = 0; i < ((Result)entry.getValue()).size(); ++i) {
               Record record = (Record)((Result)entry.getValue()).get(i);
               fkFields[i] = (TableField)fkTable.field((String)record.get(7, (Class)String.class));
            }

            references.add(new ReferenceImpl(this, fkTable, fkFields));
         }

         return references;
      }

      public final Constraint constraint() {
         return this.isPrimary() ? DSL.constraint(this.getName()).primaryKey((Field[])this.getFieldsArray()) : DSL.constraint(this.getName()).unique((Field[])this.getFieldsArray());
      }
   }

   private class MetaTable extends TableImpl<Record> {
      private static final long serialVersionUID = 4843841667753000233L;

      MetaTable(String name, Schema schema, Result<Record> columns) {
         super(name, schema);
         if (columns != null) {
            this.init(columns);
         }

      }

      public final List<UniqueKey<Record>> getKeys() {
         UniqueKey<Record> pk = this.getPrimaryKey();
         return pk == null ? Collections.emptyList() : Collections.singletonList(pk);
      }

      public final UniqueKey<Record> getPrimaryKey() {
         SQLDialect family = MetaImpl.this.configuration.family();
         final String schema = this.getSchema() == null ? null : this.getSchema().getName();
         Result<Record> result = MetaImpl.this.meta(new MetaImpl.MetaFunction() {
            public Result<Record> run(DatabaseMetaData meta) throws SQLException {
               ResultSet rs;
               if (!MetaImpl.this.inverseSchemaCatalog) {
                  rs = meta.getPrimaryKeys((String)null, schema, MetaTable.this.getName());
               } else {
                  rs = meta.getPrimaryKeys(schema, (String)null, MetaTable.this.getName());
               }

               return MetaImpl.this.ctx.fetch(rs, String.class, String.class, String.class, String.class, Integer.TYPE, String.class);
            }
         });
         result.sortAsc(4);
         return this.createPrimaryKey(result, 3);
      }

      public List<ForeignKey<Record, ?>> getReferences() {
         List<ForeignKey<Record, ?>> references = new ArrayList();
         Result<Record> result = MetaImpl.this.meta(new MetaImpl.MetaFunction() {
            public Result<Record> run(DatabaseMetaData meta) throws SQLException {
               ResultSet rs = meta.getImportedKeys((String)null, MetaTable.this.getSchema().getName(), MetaTable.this.getName());
               return MetaImpl.this.ctx.fetch(rs, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, Short.class, Short.class, Short.class, String.class, String.class);
            }
         });
         Map<Record, Result<Record>> groups = result.intoGroups(new Field[]{result.field(MetaImpl.this.inverseSchemaCatalog ? 1 : 0), result.field(MetaImpl.this.inverseSchemaCatalog ? 0 : 1), result.field(2), result.field(11), result.field(12)});
         Map<String, Schema> schemas = new HashMap();
         Iterator var5 = MetaImpl.this.getSchemas().iterator();

         while(var5.hasNext()) {
            Schema schema = (Schema)var5.next();
            schemas.put(schema.getName(), schema);
         }

         var5 = groups.entrySet().iterator();

         while(var5.hasNext()) {
            Entry<Record, Result<Record>> entry = (Entry)var5.next();
            Schema schemax = (Schema)schemas.get(((Record)entry.getKey()).get(1));
            String pkName = (String)((Record)entry.getKey()).get(4, (Class)String.class);
            Table<Record> pkTable = schemax.getTable((String)((Record)entry.getKey()).get(2, (Class)String.class));
            TableField<Record, ?>[] pkFields = new TableField[((Result)entry.getValue()).size()];
            TableField<Record, ?>[] fkFields = new TableField[((Result)entry.getValue()).size()];

            for(int i = 0; i < ((Result)entry.getValue()).size(); ++i) {
               Record record = (Record)((Result)entry.getValue()).get(i);
               pkFields[i] = (TableField)pkTable.field((String)record.get(3, (Class)String.class));
               fkFields[i] = (TableField)this.field((String)record.get(7, (Class)String.class));
            }

            references.add(new ReferenceImpl(MetaImpl.this.new MetaPrimaryKey(pkTable, pkName, pkFields), this, fkFields));
         }

         return references;
      }

      private final UniqueKey<Record> createPrimaryKey(Result<Record> result, int columnName) {
         if (result.size() <= 0) {
            return null;
         } else {
            TableField<Record, ?>[] f = new TableField[result.size()];

            for(int i = 0; i < f.length; ++i) {
               String name = (String)((Record)result.get(i)).get(columnName, String.class);
               f[i] = (TableField)this.field(name);
               if (f[i] == null && MetaImpl.this.configuration.family() == SQLDialect.SQLITE) {
                  Field[] var6 = this.fields();
                  int var7 = var6.length;

                  for(int var8 = 0; var8 < var7; ++var8) {
                     Field<?> field = var6[var8];
                     if (field.getName().equalsIgnoreCase(name)) {
                        f[i] = (TableField)field;
                     }
                  }
               }
            }

            String indexName = (String)((Record)result.get(0)).get(5, (Class)String.class);
            return MetaImpl.this.new MetaPrimaryKey(this, indexName, f);
         }
      }

      private final void init(Result<Record> columns) {
         String columnName;
         DataType type;
         for(Iterator var2 = columns.iterator(); var2.hasNext(); createField(columnName, type, this)) {
            Record column = (Record)var2.next();
            columnName = (String)column.getValue(3, (Class)String.class);
            String typeName = (String)column.getValue(5, (Class)String.class);
            int precision = (Integer)column.getValue(6, (Class)Integer.TYPE);
            int scale = (Integer)column.getValue(8, (Class)Integer.TYPE);
            int nullable = (Integer)column.getValue(10, (Class)Integer.TYPE);
            type = null;

            try {
               type = DefaultDataType.getDataType(MetaImpl.this.configuration.family(), typeName, precision, scale);
               type = type.precision(precision, scale);
               type = type.length(precision);
               if (nullable == 0) {
                  type = type.nullable(false);
               }
            } catch (SQLDialectNotSupportedException var11) {
               type = SQLDataType.OTHER;
            }
         }

      }
   }

   private class MetaSchema extends SchemaImpl {
      private static final long serialVersionUID = -2621899850912554198L;
      private transient volatile Map<Name, Result<Record>> columnCache;

      MetaSchema(String name, Catalog catalog) {
         super(name, catalog);
      }

      public final synchronized List<Table<?>> getTables() {
         Result<Record> tables = MetaImpl.this.meta(new MetaImpl.MetaFunction() {
            public Result<Record> run(DatabaseMetaData meta) throws SQLException {
               String[] types = null;
               switch(MetaImpl.this.configuration.family()) {
               case POSTGRES:
                  types = new String[]{"TABLE", "VIEW", "SYSTEM_TABLE", "SYSTEM_VIEW", "MATERIALIZED VIEW"};
                  break;
               case SQLITE:
                  types = new String[]{"TABLE", "VIEW"};
               }

               ResultSet rs;
               if (!MetaImpl.this.inverseSchemaCatalog) {
                  rs = meta.getTables((String)null, MetaSchema.this.getName(), "%", types);
               } else {
                  rs = meta.getTables(MetaSchema.this.getName(), (String)null, "%", types);
               }

               return MetaImpl.this.ctx.fetch(rs, SQLDataType.VARCHAR, SQLDataType.VARCHAR, SQLDataType.VARCHAR, SQLDataType.VARCHAR);
            }
         });
         List<Table<?>> result = new ArrayList();
         Iterator var3 = tables.iterator();

         while(var3.hasNext()) {
            Record table = (Record)var3.next();
            String catalog = (String)table.get(0, (Class)String.class);
            String schema = (String)table.get(1, (Class)String.class);
            String name = (String)table.get(2, (Class)String.class);
            result.add(MetaImpl.this.new MetaTable(name, this, this.getColumns(MetaImpl.this.inverseSchemaCatalog ? catalog : schema, name)));
         }

         return result;
      }

      private final Result<Record> getColumns(String schema, String table) {
         if (this.columnCache == null && MetaImpl.this.configuration.dialect() != SQLDialect.SQLITE) {
            Result<Record> columns = this.getColumns0(schema, "%");
            Field<String> tableCat = columns.field(0);
            Field<String> tableSchem = columns.field(1);
            Field<String> tableName = columns.field(2);
            Map<Record, Result<Record>> groups = columns.intoGroups(new Field[]{MetaImpl.this.inverseSchemaCatalog ? tableCat : tableSchem, tableName});
            this.columnCache = new LinkedHashMap();
            Iterator var8 = groups.entrySet().iterator();

            while(var8.hasNext()) {
               Entry<Record, Result<Record>> entry = (Entry)var8.next();
               Record key = (Record)entry.getKey();
               Result<Record> value = (Result)entry.getValue();
               this.columnCache.put(DSL.name((String)key.get(MetaImpl.this.inverseSchemaCatalog ? tableCat : tableSchem), (String)key.get(tableName)), value);
            }
         }

         return this.columnCache != null ? (Result)this.columnCache.get(DSL.name(schema, table)) : this.getColumns0(schema, table);
      }

      private final Result<Record> getColumns0(final String schema, final String table) {
         return MetaImpl.this.meta(new MetaImpl.MetaFunction() {
            public Result<Record> run(DatabaseMetaData meta) throws SQLException {
               ResultSet rs;
               if (!MetaImpl.this.inverseSchemaCatalog) {
                  rs = meta.getColumns((String)null, schema, table, "%");
               } else {
                  rs = meta.getColumns(schema, (String)null, table, "%");
               }

               return MetaImpl.this.ctx.fetch(rs, String.class, String.class, String.class, String.class, Integer.TYPE, String.class, Integer.TYPE, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE);
            }
         });
      }
   }

   private class MetaCatalog extends CatalogImpl {
      private static final long serialVersionUID = -2821093577201327275L;

      MetaCatalog(String name) {
         super(name);
      }

      public final List<Schema> getSchemas() {
         List<Schema> result = new ArrayList();
         Result schemas;
         Iterator var3;
         String name;
         if (!MetaImpl.this.inverseSchemaCatalog) {
            schemas = MetaImpl.this.meta(new MetaImpl.MetaFunction() {
               public Result<Record> run(DatabaseMetaData meta) throws SQLException {
                  return MetaImpl.this.ctx.fetch(meta.getSchemas(), SQLDataType.VARCHAR);
               }
            });
            var3 = schemas.getValues(0, (Class)String.class).iterator();

            while(var3.hasNext()) {
               name = (String)var3.next();
               result.add(MetaImpl.this.new MetaSchema(name, this));
            }
         } else {
            schemas = MetaImpl.this.meta(new MetaImpl.MetaFunction() {
               public Result<Record> run(DatabaseMetaData meta) throws SQLException {
                  return MetaImpl.this.ctx.fetch(meta.getCatalogs(), SQLDataType.VARCHAR);
               }
            });
            var3 = schemas.getValues(0, (Class)String.class).iterator();

            while(var3.hasNext()) {
               name = (String)var3.next();
               result.add(MetaImpl.this.new MetaSchema(name, this));
            }
         }

         if (result.isEmpty()) {
            result.add(MetaImpl.this.new MetaSchema("", this));
         }

         return result;
      }
   }

   private interface MetaFunction {
      Result<Record> run(DatabaseMetaData var1) throws SQLException;
   }
}
