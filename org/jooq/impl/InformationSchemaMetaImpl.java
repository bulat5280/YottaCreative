package org.jooq.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXB;
import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.ForeignKey;
import org.jooq.Meta;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.util.xml.jaxb.Column;
import org.jooq.util.xml.jaxb.InformationSchema;
import org.jooq.util.xml.jaxb.KeyColumnUsage;
import org.jooq.util.xml.jaxb.ReferentialConstraint;
import org.jooq.util.xml.jaxb.Table;
import org.jooq.util.xml.jaxb.TableConstraint;
import org.jooq.util.xml.jaxb.TableConstraintType;

final class InformationSchemaMetaImpl implements Meta {
   private final Configuration configuration;
   private final InformationSchema source;
   private final List<Catalog> catalogs;
   private final List<Schema> schemas;
   private final Map<Name, Schema> schemasByName;
   private final Map<Catalog, List<Schema>> schemasPerCatalog;
   private final List<InformationSchemaMetaImpl.InformationSchemaTable> tables;
   private final Map<Name, InformationSchemaMetaImpl.InformationSchemaTable> tablesByName;
   private final Map<Schema, List<InformationSchemaMetaImpl.InformationSchemaTable>> tablesPerSchema;
   private final List<Sequence<?>> sequences;
   private final Map<Schema, List<Sequence<?>>> sequencesPerSchema;
   private final List<UniqueKeyImpl<Record>> primaryKeys;
   private final Map<Name, UniqueKeyImpl<Record>> uniqueKeysByName;
   private final Map<Name, Name> referentialKeys;

   InformationSchemaMetaImpl(Configuration configuration, InformationSchema source) {
      this.configuration = configuration;
      this.source = source;
      this.catalogs = new ArrayList();
      this.schemas = new ArrayList();
      this.schemasByName = new HashMap();
      this.schemasPerCatalog = new HashMap();
      this.tables = new ArrayList();
      this.tablesByName = new HashMap();
      this.tablesPerSchema = new HashMap();
      this.sequences = new ArrayList();
      this.sequencesPerSchema = new HashMap();
      this.primaryKeys = new ArrayList();
      this.uniqueKeysByName = new HashMap();
      this.referentialKeys = new HashMap();
      this.init(source);
   }

   public static void main(String[] args) {
      System.out.println(String.format("x y %0$s", "abc"));
   }

   private final void init(InformationSchema meta) {
      List<String> errors = new ArrayList();
      Iterator var3 = meta.getSchemata().iterator();

      while(var3.hasNext()) {
         org.jooq.util.xml.jaxb.Schema xs = (org.jooq.util.xml.jaxb.Schema)var3.next();
         InformationSchemaMetaImpl.InformationSchemaCatalog catalog = new InformationSchemaMetaImpl.InformationSchemaCatalog(xs.getCatalogName());
         if (!this.catalogs.contains(catalog)) {
            this.catalogs.add(catalog);
         }

         InformationSchemaMetaImpl.InformationSchemaSchema is = new InformationSchemaMetaImpl.InformationSchemaSchema(xs.getSchemaName(), catalog);
         this.schemas.add(is);
         this.schemasByName.put(DSL.name(xs.getCatalogName(), xs.getSchemaName()), is);
      }

      var3 = meta.getTables().iterator();

      InformationSchemaMetaImpl.InformationSchemaTable t;
      while(var3.hasNext()) {
         Table xt = (Table)var3.next();
         Name schemaName = DSL.name(xt.getTableCatalog(), xt.getTableSchema());
         Schema schema = (Schema)this.schemasByName.get(schemaName);
         if (schema == null) {
            errors.add(String.format("Schema " + schemaName + " not defined for table " + xt.getTableName()));
         } else {
            t = new InformationSchemaMetaImpl.InformationSchemaTable(xt.getTableName(), schema);
            this.tables.add(t);
            this.tablesByName.put(DSL.name(xt.getTableCatalog(), xt.getTableSchema(), xt.getTableName()), t);
         }
      }

      List<Column> columns = new ArrayList(meta.getColumns());
      Collections.sort(columns, new Comparator<Column>() {
         public int compare(Column o1, Column o2) {
            Integer p1 = o1.getOrdinalPosition();
            Integer p2 = o2.getOrdinalPosition();
            if (p1 == p2) {
               return 0;
            } else if (p1 == null) {
               return -1;
            } else {
               return p2 == null ? 1 : p1.compareTo(p2);
            }
         }
      });
      Iterator var18 = columns.iterator();

      while(var18.hasNext()) {
         Column xc = (Column)var18.next();
         String typeName = xc.getDataType();
         int length = xc.getCharacterMaximumLength() == null ? 0 : xc.getCharacterMaximumLength();
         int precision = xc.getNumericPrecision() == null ? 0 : xc.getNumericPrecision();
         int scale = xc.getNumericScale() == null ? 0 : xc.getNumericScale();
         boolean nullable = xc.isIsNullable() == null ? true : xc.isIsNullable();
         Name tableName = DSL.name(xc.getTableCatalog(), xc.getTableSchema(), xc.getTableName());
         InformationSchemaMetaImpl.InformationSchemaTable table = (InformationSchemaMetaImpl.InformationSchemaTable)this.tablesByName.get(tableName);
         if (table == null) {
            errors.add(String.format("Table " + tableName + " not defined for column " + xc.getColumnName()));
         } else {
            AbstractTable.createField(xc.getColumnName(), this.type(typeName, length, precision, scale, nullable), (org.jooq.Table)table);
         }
      }

      Map<Name, List<TableField<Record, ?>>> columnsByConstraint = new HashMap();
      List<KeyColumnUsage> keyColumnUsages = new ArrayList(meta.getKeyColumnUsages());
      Collections.sort(keyColumnUsages, new Comparator<KeyColumnUsage>() {
         public int compare(KeyColumnUsage o1, KeyColumnUsage o2) {
            int p1 = o1.getOrdinalPosition();
            int p2 = o2.getOrdinalPosition();
            return p1 < p2 ? -1 : (p1 == p2 ? 0 : 1);
         }
      });
      Iterator var25 = keyColumnUsages.iterator();

      Name schemaName;
      Object list;
      while(var25.hasNext()) {
         KeyColumnUsage xc = (KeyColumnUsage)var25.next();
         schemaName = DSL.name(xc.getConstraintCatalog(), xc.getConstraintSchema(), xc.getConstraintName());
         list = (List)columnsByConstraint.get(schemaName);
         if (list == null) {
            list = new ArrayList();
            columnsByConstraint.put(schemaName, list);
         }

         Name tableName = DSL.name(xc.getTableCatalog(), xc.getTableSchema(), xc.getTableName());
         InformationSchemaMetaImpl.InformationSchemaTable table = (InformationSchemaMetaImpl.InformationSchemaTable)this.tablesByName.get(tableName);
         if (table == null) {
            errors.add(String.format("Table " + tableName + " not defined for constraint " + schemaName));
         } else {
            TableField<Record, ?> field = (TableField)table.field(xc.getColumnName());
            if (field == null) {
               errors.add(String.format("Column " + xc.getColumnName() + " not defined for table " + tableName));
            } else {
               ((List)list).add(field);
            }
         }
      }

      var25 = meta.getTableConstraints().iterator();

      while(true) {
         while(true) {
            TableConstraint xc;
            Name constraintName;
            InformationSchemaMetaImpl.InformationSchemaTable table;
            List c;
            UniqueKeyImpl uniqueKey;
            while(var25.hasNext()) {
               xc = (TableConstraint)var25.next();
               switch(xc.getConstraintType()) {
               case PRIMARY_KEY:
               case UNIQUE:
                  schemaName = DSL.name(xc.getTableCatalog(), xc.getTableSchema(), xc.getTableName());
                  constraintName = DSL.name(xc.getConstraintCatalog(), xc.getConstraintSchema(), xc.getConstraintName());
                  table = (InformationSchemaMetaImpl.InformationSchemaTable)this.tablesByName.get(schemaName);
                  if (table == null) {
                     errors.add(String.format("Table " + schemaName + " not defined for constraint " + constraintName));
                  } else {
                     c = (List)columnsByConstraint.get(constraintName);
                     if (c != null && !c.isEmpty()) {
                        uniqueKey = (UniqueKeyImpl)AbstractKeys.createUniqueKey(table, xc.getConstraintName(), (TableField[])c.toArray(new TableField[0]));
                        if (xc.getConstraintType() == TableConstraintType.PRIMARY_KEY) {
                           table.primaryKey = uniqueKey;
                           this.primaryKeys.add(uniqueKey);
                        }

                        table.uniqueKeys.add(uniqueKey);
                        this.uniqueKeysByName.put(constraintName, uniqueKey);
                     } else {
                        errors.add(String.format("No columns defined for constraint " + constraintName));
                     }
                  }
               }
            }

            var25 = meta.getReferentialConstraints().iterator();

            while(var25.hasNext()) {
               ReferentialConstraint xr = (ReferentialConstraint)var25.next();
               this.referentialKeys.put(DSL.name(xr.getConstraintCatalog(), xr.getConstraintSchema(), xr.getConstraintName()), DSL.name(xr.getUniqueConstraintCatalog(), xr.getUniqueConstraintSchema(), xr.getUniqueConstraintName()));
            }

            var25 = meta.getTableConstraints().iterator();

            while(true) {
               while(true) {
                  while(var25.hasNext()) {
                     xc = (TableConstraint)var25.next();
                     switch(xc.getConstraintType()) {
                     case FOREIGN_KEY:
                        schemaName = DSL.name(xc.getTableCatalog(), xc.getTableSchema(), xc.getTableName());
                        constraintName = DSL.name(xc.getConstraintCatalog(), xc.getConstraintSchema(), xc.getConstraintName());
                        table = (InformationSchemaMetaImpl.InformationSchemaTable)this.tablesByName.get(schemaName);
                        if (table == null) {
                           errors.add(String.format("Table " + schemaName + " not defined for constraint " + constraintName));
                        } else {
                           c = (List)columnsByConstraint.get(constraintName);
                           if (c != null && !c.isEmpty()) {
                              uniqueKey = (UniqueKeyImpl)this.uniqueKeysByName.get(this.referentialKeys.get(constraintName));
                              if (uniqueKey == null) {
                                 errors.add(String.format("No unique key defined for foreign key " + constraintName));
                              } else {
                                 ForeignKey<Record, Record> key = AbstractKeys.createForeignKey(uniqueKey, table, xc.getConstraintName(), (TableField[])c.toArray(new TableField[0]));
                                 table.foreignKeys.add(key);
                              }
                           } else {
                              errors.add(String.format("No columns defined for constraint " + constraintName));
                           }
                        }
                     }
                  }

                  var25 = meta.getSequences().iterator();

                  while(var25.hasNext()) {
                     org.jooq.util.xml.jaxb.Sequence xs = (org.jooq.util.xml.jaxb.Sequence)var25.next();
                     schemaName = DSL.name(xs.getSequenceCatalog(), xs.getSequenceSchema());
                     Schema schema = (Schema)this.schemasByName.get(schemaName);
                     if (schema == null) {
                        errors.add(String.format("Schema " + schemaName + " not defined for sequence " + xs.getSequenceName()));
                     } else {
                        String typeName = xs.getDataType();
                        int length = xs.getCharacterMaximumLength() == null ? 0 : xs.getCharacterMaximumLength();
                        int precision = xs.getNumericPrecision() == null ? 0 : xs.getNumericPrecision();
                        int scale = xs.getNumericScale() == null ? 0 : xs.getNumericScale();
                        boolean nullable = true;
                        InformationSchemaMetaImpl.InformationSchemaSequence is = new InformationSchemaMetaImpl.InformationSchemaSequence(xs.getSequenceName(), schema, this.type(typeName, length, precision, scale, nullable));
                        this.sequences.add(is);
                     }
                  }

                  Schema s;
                  for(var25 = this.schemas.iterator(); var25.hasNext(); ((List)list).add(s)) {
                     s = (Schema)var25.next();
                     Catalog c = s.getCatalog();
                     list = (List)this.schemasPerCatalog.get(c);
                     if (list == null) {
                        list = new ArrayList();
                        this.schemasPerCatalog.put(c, list);
                     }
                  }

                  Schema s;
                  for(var25 = this.tables.iterator(); var25.hasNext(); ((List)list).add(t)) {
                     t = (InformationSchemaMetaImpl.InformationSchemaTable)var25.next();
                     s = t.getSchema();
                     list = (List)this.tablesPerSchema.get(s);
                     if (list == null) {
                        list = new ArrayList();
                        this.tablesPerSchema.put(s, list);
                     }
                  }

                  Sequence q;
                  for(var25 = this.sequences.iterator(); var25.hasNext(); ((List)list).add(q)) {
                     q = (Sequence)var25.next();
                     s = q.getSchema();
                     list = (List)this.sequencesPerSchema.get(s);
                     if (list == null) {
                        list = new ArrayList();
                        this.sequencesPerSchema.put(s, list);
                     }
                  }

                  if (!errors.isEmpty()) {
                     throw new IllegalArgumentException(errors.toString());
                  }

                  return;
               }
            }
         }
      }
   }

   private final DataType<?> type(String typeName, int length, int precision, int scale, boolean nullable) {
      DataType type = null;

      try {
         type = DefaultDataType.getDataType(this.configuration.family(), typeName);
         type = type.nullable(nullable);
         if (length != 0) {
            type = type.length(length);
         } else if (precision != 0 || scale != 0) {
            type = type.precision(precision, scale);
         }
      } catch (SQLDialectNotSupportedException var8) {
         type = SQLDataType.OTHER;
      }

      return type;
   }

   public final List<Catalog> getCatalogs() {
      return Collections.unmodifiableList(this.catalogs);
   }

   public final List<Schema> getSchemas() {
      return Collections.unmodifiableList(this.schemas);
   }

   public final List<org.jooq.Table<?>> getTables() {
      return Collections.unmodifiableList(this.tables);
   }

   public final List<Sequence<?>> getSequences() {
      return Collections.unmodifiableList(this.sequences);
   }

   public final List<UniqueKey<?>> getPrimaryKeys() {
      return Collections.unmodifiableList(this.primaryKeys);
   }

   public String toString() {
      StringWriter writer = new StringWriter();
      JAXB.marshal(this.source, writer);
      return writer.toString();
   }

   private final class InformationSchemaSequence<N extends Number> extends SequenceImpl<N> {
      private static final long serialVersionUID = -1246697252597049756L;

      InformationSchemaSequence(String name, Schema schema, DataType<N> type) {
         super(name, schema, type);
      }
   }

   private final class InformationSchemaTable extends TableImpl<Record> {
      private static final long serialVersionUID = 4314110578549768267L;
      UniqueKey<Record> primaryKey;
      final List<UniqueKey<Record>> uniqueKeys = new ArrayList();
      final List<ForeignKey<Record, Record>> foreignKeys = new ArrayList();

      public InformationSchemaTable(String name, Schema schema) {
         super(name, schema);
      }

      public UniqueKey<Record> getPrimaryKey() {
         return this.primaryKey;
      }

      public List<UniqueKey<Record>> getKeys() {
         return Collections.unmodifiableList(this.uniqueKeys);
      }

      public List<ForeignKey<Record, ?>> getReferences() {
         return Collections.unmodifiableList(this.foreignKeys);
      }
   }

   private final class InformationSchemaSchema extends SchemaImpl {
      private static final long serialVersionUID = 7290709749127378187L;

      public InformationSchemaSchema(String name, Catalog catalog) {
         super(name, catalog);
      }

      public final List<org.jooq.Table<?>> getTables() {
         return Collections.unmodifiableList((List)InformationSchemaMetaImpl.this.tablesPerSchema.get(this));
      }

      public final List<Sequence<?>> getSequences() {
         return Collections.unmodifiableList((List)InformationSchemaMetaImpl.this.sequencesPerSchema.get(this));
      }
   }

   private final class InformationSchemaCatalog extends CatalogImpl {
      private static final long serialVersionUID = 87038321849045492L;

      InformationSchemaCatalog(String name) {
         super(name);
      }

      public final List<Schema> getSchemas() {
         return Collections.unmodifiableList((List)InformationSchemaMetaImpl.this.schemasPerCatalog.get(this));
      }
   }
}
