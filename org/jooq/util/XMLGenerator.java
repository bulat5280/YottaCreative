package org.jooq.util;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXB;
import org.jooq.tools.JooqLogger;
import org.jooq.util.xml.jaxb.Column;
import org.jooq.util.xml.jaxb.InformationSchema;
import org.jooq.util.xml.jaxb.KeyColumnUsage;
import org.jooq.util.xml.jaxb.ReferentialConstraint;
import org.jooq.util.xml.jaxb.Schema;
import org.jooq.util.xml.jaxb.Sequence;
import org.jooq.util.xml.jaxb.Table;
import org.jooq.util.xml.jaxb.TableConstraint;
import org.jooq.util.xml.jaxb.TableConstraintType;

public class XMLGenerator extends AbstractGenerator {
   private static final JooqLogger log = JooqLogger.getLogger(XMLGenerator.class);

   public XMLGenerator() {
      super(AbstractGenerator.Language.XML);
   }

   public void generate(Database db) {
      this.logDatabaseParameters(db);
      log.info("");
      this.logGenerationRemarks(db);
      log.info("");
      log.info("----------------------------------------------------------");
      TextWriter out = new TextWriter(this.getStrategy().getFile("information_schema.xml"), this.targetEncoding);
      log.info("");
      log.info("Generating XML", (Object)out.file().getName());
      log.info("==========================================================");
      InformationSchema is = new InformationSchema();
      Iterator var4 = db.getCatalogs().iterator();

      while(var4.hasNext()) {
         CatalogDefinition c = (CatalogDefinition)var4.next();
         String catalogName = c.getOutputName();
         Iterator var7 = c.getSchemata().iterator();

         while(var7.hasNext()) {
            SchemaDefinition s = (SchemaDefinition)var7.next();
            String schemaName = s.getOutputName();
            Schema schema = new Schema();
            schema.setCatalogName(catalogName);
            schema.setSchemaName(schemaName);
            is.getSchemata().add(schema);
            Iterator var11 = s.getTables().iterator();

            String sequenceName;
            while(var11.hasNext()) {
               TableDefinition t = (TableDefinition)var11.next();
               sequenceName = t.getOutputName();
               Table table = new Table();
               table.setTableCatalog(catalogName);
               table.setTableSchema(schemaName);
               table.setTableName(sequenceName);
               is.getTables().add(table);
               Iterator var15 = t.getColumns().iterator();

               while(var15.hasNext()) {
                  ColumnDefinition co = (ColumnDefinition)var15.next();
                  String columnName = co.getOutputName();
                  DataTypeDefinition type = co.getType();
                  Column column = new Column();
                  column.setTableCatalog(catalogName);
                  column.setTableSchema(schemaName);
                  column.setTableName(sequenceName);
                  column.setColumnName(columnName);
                  column.setCharacterMaximumLength(type.getLength());
                  column.setColumnDefault(type.getDefaultValue());
                  column.setDataType(type.getType());
                  column.setIsNullable(column.isIsNullable());
                  column.setNumericPrecision(type.getPrecision());
                  column.setNumericScale(type.getScale());
                  column.setOrdinalPosition(co.getPosition());
                  is.getColumns().add(column);
               }
            }

            var11 = db.getUniqueKeys(s).iterator();

            TableDefinition table;
            while(var11.hasNext()) {
               UniqueKeyDefinition u = (UniqueKeyDefinition)var11.next();
               sequenceName = u.getOutputName();
               table = u.getTable();
               List<ColumnDefinition> columns = u.getKeyColumns();
               TableConstraint constraint = new TableConstraint();
               constraint.setConstraintCatalog(catalogName);
               constraint.setConstraintSchema(schemaName);
               constraint.setConstraintName(sequenceName);
               constraint.setConstraintType(u.isPrimaryKey() ? TableConstraintType.PRIMARY_KEY : TableConstraintType.UNIQUE);
               constraint.setTableCatalog(table.getCatalog().getOutputName());
               constraint.setTableSchema(table.getSchema().getOutputName());
               constraint.setTableName(table.getOutputName());
               is.getTableConstraints().add(constraint);

               for(int i = 0; i < columns.size(); ++i) {
                  ColumnDefinition column = (ColumnDefinition)columns.get(i);
                  KeyColumnUsage kc = new KeyColumnUsage();
                  kc.setConstraintCatalog(catalogName);
                  kc.setConstraintSchema(schemaName);
                  kc.setConstraintName(sequenceName);
                  kc.setColumnName(column.getOutputName());
                  kc.setOrdinalPosition(i);
                  kc.setTableCatalog(table.getCatalog().getOutputName());
                  kc.setTableSchema(table.getSchema().getOutputName());
                  kc.setTableName(table.getOutputName());
                  is.getKeyColumnUsages().add(kc);
               }
            }

            var11 = db.getForeignKeys(s).iterator();

            while(var11.hasNext()) {
               ForeignKeyDefinition f = (ForeignKeyDefinition)var11.next();
               sequenceName = f.getOutputName();
               UniqueKeyDefinition referenced = f.getReferencedKey();
               TableDefinition table = f.getKeyTable();
               List<ColumnDefinition> columns = f.getKeyColumns();
               TableConstraint tc = new TableConstraint();
               tc.setConstraintCatalog(catalogName);
               tc.setConstraintSchema(schemaName);
               tc.setConstraintName(sequenceName);
               tc.setConstraintType(TableConstraintType.FOREIGN_KEY);
               tc.setTableCatalog(table.getCatalog().getOutputName());
               tc.setTableSchema(table.getSchema().getOutputName());
               tc.setTableName(table.getOutputName());
               ReferentialConstraint rc = new ReferentialConstraint();
               rc.setConstraintCatalog(catalogName);
               rc.setConstraintSchema(schemaName);
               rc.setConstraintName(sequenceName);
               rc.setUniqueConstraintCatalog(referenced.getOutputName());
               rc.setUniqueConstraintSchema(referenced.getSchema().getOutputName());
               rc.setUniqueConstraintName(referenced.getOutputName());
               is.getTableConstraints().add(tc);
               is.getReferentialConstraints().add(rc);

               for(int i = 0; i < columns.size(); ++i) {
                  ColumnDefinition column = (ColumnDefinition)columns.get(i);
                  KeyColumnUsage kc = new KeyColumnUsage();
                  kc.setConstraintCatalog(catalogName);
                  kc.setConstraintSchema(schemaName);
                  kc.setConstraintName(sequenceName);
                  kc.setColumnName(column.getOutputName());
                  kc.setOrdinalPosition(i);
                  kc.setTableCatalog(table.getCatalog().getOutputName());
                  kc.setTableSchema(table.getSchema().getOutputName());
                  kc.setTableName(table.getOutputName());
                  is.getKeyColumnUsages().add(kc);
               }
            }

            var11 = db.getCheckConstraints(s).iterator();

            while(var11.hasNext()) {
               CheckConstraintDefinition ch = (CheckConstraintDefinition)var11.next();
               sequenceName = ch.getOutputName();
               table = ch.getTable();
               TableConstraint constraint = new TableConstraint();
               constraint.setConstraintCatalog(catalogName);
               constraint.setConstraintSchema(schemaName);
               constraint.setConstraintName(sequenceName);
               constraint.setConstraintType(TableConstraintType.CHECK);
               constraint.setTableCatalog(table.getCatalog().getOutputName());
               constraint.setTableSchema(table.getSchema().getOutputName());
               constraint.setTableName(table.getOutputName());
               is.getTableConstraints().add(constraint);
            }

            var11 = db.getSequences(s).iterator();

            while(var11.hasNext()) {
               SequenceDefinition se = (SequenceDefinition)var11.next();
               sequenceName = se.getOutputName();
               DataTypeDefinition type = se.getType();
               Sequence sequence = new Sequence();
               sequence.setSequenceCatalog(catalogName);
               sequence.setSequenceSchema(schemaName);
               sequence.setSequenceName(sequenceName);
               sequence.setCharacterMaximumLength(type.getLength());
               sequence.setDataType(type.getType());
               sequence.setNumericPrecision(type.getPrecision());
               sequence.setNumericScale(type.getScale());
               is.getSequences().add(sequence);
            }
         }
      }

      StringWriter writer = new StringWriter();
      JAXB.marshal(is, writer);
      out.print(writer.toString());
      out.close();
   }
}
