package org.jooq.util.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXB;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;
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
import org.jooq.util.xml.jaxb.InformationSchema;
import org.jooq.util.xml.jaxb.KeyColumnUsage;
import org.jooq.util.xml.jaxb.ReferentialConstraint;
import org.jooq.util.xml.jaxb.Schema;
import org.jooq.util.xml.jaxb.Sequence;
import org.jooq.util.xml.jaxb.Table;
import org.jooq.util.xml.jaxb.TableConstraint;
import org.jooq.util.xml.jaxb.TableConstraintType;

public class XMLDatabase extends AbstractDatabase {
   private static final JooqLogger log = JooqLogger.getLogger(XMLDatabase.class);
   public static final String P_XML_FILE = "xml-file";
   public static final String P_XSL_FILE = "xsl-file";
   public static final String P_DIALECT = "dialect";
   InformationSchema info;

   private InformationSchema info() {
      if (this.info == null) {
         String xml = this.getProperties().getProperty("xml-file");
         String xsl = this.getProperties().getProperty("xsl-file");
         InputStream xmlIs = null;
         InputStream xslIs = null;
         log.info("Using XML file", (Object)xml);

         try {
            xmlIs = XMLDatabase.class.getResourceAsStream(xml);
            if (xmlIs == null) {
               xmlIs = new FileInputStream(xml);
            }

            if (StringUtils.isBlank(xsl)) {
               this.info = (InformationSchema)JAXB.unmarshal(new File(xml), InformationSchema.class);
            } else {
               log.info("Using XSL file", (Object)xsl);
               xslIs = XMLDatabase.class.getResourceAsStream(xsl);
               if (xslIs == null) {
                  xslIs = new FileInputStream(xsl);
               }

               try {
                  StringWriter writer = new StringWriter();
                  TransformerFactory factory = TransformerFactory.newInstance();
                  Transformer transformer = factory.newTransformer(new StreamSource((InputStream)xslIs));
                  transformer.transform(new StreamSource((InputStream)xmlIs), new StreamResult(writer));
                  this.info = (InformationSchema)JAXB.unmarshal(new StringReader(writer.getBuffer().toString()), InformationSchema.class);
               } catch (TransformerException var19) {
                  throw new RuntimeException("Error while transforming XML file " + xml + " with XSL file " + xsl, var19);
               }
            }
         } catch (IOException var20) {
            throw new RuntimeException("Error while opening files " + xml + " or " + xsl, var20);
         } finally {
            if (xmlIs != null) {
               try {
                  ((InputStream)xmlIs).close();
               } catch (Exception var18) {
               }
            }

            if (xslIs != null) {
               try {
                  ((InputStream)xslIs).close();
               } catch (Exception var17) {
               }
            }

         }
      }

      return this.info;
   }

   protected DSLContext create0() {
      SQLDialect dialect = SQLDialect.DEFAULT;

      try {
         dialect = SQLDialect.valueOf(this.getProperties().getProperty("dialect"));
      } catch (Exception var3) {
      }

      return DSL.using(dialect);
   }

   protected void loadPrimaryKeys(DefaultRelations relations) {
      Iterator var2 = this.keyColumnUsage(TableConstraintType.PRIMARY_KEY).iterator();

      while(var2.hasNext()) {
         KeyColumnUsage usage = (KeyColumnUsage)var2.next();
         SchemaDefinition schema = this.getSchema(usage.getConstraintSchema());
         String key = usage.getConstraintName();
         String tableName = usage.getTableName();
         String columnName = usage.getColumnName();
         TableDefinition table = this.getTable(schema, tableName);
         if (table != null) {
            relations.addPrimaryKey(key, table.getColumn(columnName));
         }
      }

   }

   protected void loadUniqueKeys(DefaultRelations relations) {
      Iterator var2 = this.keyColumnUsage(TableConstraintType.UNIQUE).iterator();

      while(var2.hasNext()) {
         KeyColumnUsage usage = (KeyColumnUsage)var2.next();
         SchemaDefinition schema = this.getSchema(usage.getConstraintSchema());
         String key = usage.getConstraintName();
         String tableName = usage.getTableName();
         String columnName = usage.getColumnName();
         TableDefinition table = this.getTable(schema, tableName);
         if (table != null) {
            relations.addPrimaryKey(key, table.getColumn(columnName));
         }
      }

   }

   private List<KeyColumnUsage> keyColumnUsage(TableConstraintType constraintType) {
      List<KeyColumnUsage> result = new ArrayList();
      Iterator var3 = this.info().getTableConstraints().iterator();

      while(true) {
         TableConstraint constraint;
         do {
            do {
               if (!var3.hasNext()) {
                  Collections.sort(result, new Comparator<KeyColumnUsage>() {
                     public int compare(KeyColumnUsage o1, KeyColumnUsage o2) {
                        int r = false;
                        int rx = ((String)StringUtils.defaultIfNull(o1.getConstraintCatalog(), "")).compareTo((String)StringUtils.defaultIfNull(o2.getConstraintCatalog(), ""));
                        if (rx != 0) {
                           return rx;
                        } else {
                           rx = ((String)StringUtils.defaultIfNull(o1.getConstraintSchema(), "")).compareTo((String)StringUtils.defaultIfNull(o2.getConstraintSchema(), ""));
                           if (rx != 0) {
                              return rx;
                           } else {
                              rx = ((String)StringUtils.defaultIfNull(o1.getConstraintName(), "")).compareTo((String)StringUtils.defaultIfNull(o2.getConstraintName(), ""));
                              return rx != 0 ? rx : Integer.valueOf(o1.getOrdinalPosition()).compareTo(o2.getOrdinalPosition());
                           }
                        }
                     }
                  });
                  return result;
               }

               constraint = (TableConstraint)var3.next();
            } while(constraintType != constraint.getConstraintType());
         } while(!this.getInputSchemata().contains(constraint.getConstraintSchema()));

         Iterator var5 = this.info().getKeyColumnUsages().iterator();

         while(var5.hasNext()) {
            KeyColumnUsage usage = (KeyColumnUsage)var5.next();
            if (StringUtils.equals(constraint.getConstraintCatalog(), usage.getConstraintCatalog()) && StringUtils.equals(constraint.getConstraintSchema(), usage.getConstraintSchema()) && StringUtils.equals(constraint.getConstraintName(), usage.getConstraintName())) {
               result.add(usage);
            }
         }
      }
   }

   protected void loadForeignKeys(DefaultRelations relations) {
      Iterator var2 = this.info().getReferentialConstraints().iterator();

      while(true) {
         ReferentialConstraint constraint;
         do {
            if (!var2.hasNext()) {
               return;
            }

            constraint = (ReferentialConstraint)var2.next();
         } while(!this.getInputSchemata().contains(constraint.getConstraintSchema()));

         Iterator var4 = this.info().getKeyColumnUsages().iterator();

         while(var4.hasNext()) {
            KeyColumnUsage usage = (KeyColumnUsage)var4.next();
            if (StringUtils.equals(constraint.getConstraintCatalog(), usage.getConstraintCatalog()) && StringUtils.equals(constraint.getConstraintSchema(), usage.getConstraintSchema()) && StringUtils.equals(constraint.getConstraintName(), usage.getConstraintName())) {
               SchemaDefinition foreignKeySchema = this.getSchema(constraint.getConstraintSchema());
               SchemaDefinition uniqueKeySchema = this.getSchema(constraint.getUniqueConstraintSchema());
               String foreignKey = usage.getConstraintName();
               String foreignKeyTable = usage.getTableName();
               String foreignKeyColumn = usage.getColumnName();
               String uniqueKey = constraint.getUniqueConstraintName();
               TableDefinition referencingTable = this.getTable(foreignKeySchema, foreignKeyTable);
               if (referencingTable != null) {
                  ColumnDefinition referencingColumn = referencingTable.getColumn(foreignKeyColumn);
                  relations.addForeignKey(foreignKey, uniqueKey, referencingColumn, uniqueKeySchema);
               }
            }
         }
      }
   }

   protected void loadCheckConstraints(DefaultRelations r) {
   }

   protected List<CatalogDefinition> getCatalogs0() throws SQLException {
      List<CatalogDefinition> result = new ArrayList();
      result.add(new CatalogDefinition(this, "", ""));
      return result;
   }

   protected List<SchemaDefinition> getSchemata0() {
      List<SchemaDefinition> result = new ArrayList();
      Iterator var2 = this.info().getSchemata().iterator();

      while(var2.hasNext()) {
         Schema schema = (Schema)var2.next();
         result.add(new SchemaDefinition(this, schema.getSchemaName(), (String)null));
      }

      return result;
   }

   protected List<SequenceDefinition> getSequences0() {
      List<SequenceDefinition> result = new ArrayList();
      Iterator var2 = this.info().getSequences().iterator();

      while(var2.hasNext()) {
         Sequence sequence = (Sequence)var2.next();
         if (this.getInputSchemata().contains(sequence.getSequenceSchema())) {
            SchemaDefinition schema = this.getSchema(sequence.getSequenceSchema());
            DataTypeDefinition type = new DefaultDataTypeDefinition(this, schema, sequence.getDataType(), sequence.getCharacterMaximumLength(), sequence.getNumericPrecision(), sequence.getNumericScale(), false, (String)null);
            result.add(new DefaultSequenceDefinition(schema, sequence.getSequenceName(), type));
         }
      }

      return result;
   }

   protected List<TableDefinition> getTables0() {
      List<TableDefinition> result = new ArrayList();
      Iterator var2 = this.info().getTables().iterator();

      while(var2.hasNext()) {
         Table table = (Table)var2.next();
         if (this.getInputSchemata().contains(table.getTableSchema())) {
            SchemaDefinition schema = this.getSchema(table.getTableSchema());
            result.add(new XMLTableDefinition(schema, this.info(), table));
         }
      }

      return result;
   }

   protected List<EnumDefinition> getEnums0() {
      List<EnumDefinition> result = new ArrayList();
      return result;
   }

   protected List<DomainDefinition> getDomains0() throws SQLException {
      List<DomainDefinition> result = new ArrayList();
      return result;
   }

   protected List<UDTDefinition> getUDTs0() {
      List<UDTDefinition> result = new ArrayList();
      return result;
   }

   protected List<ArrayDefinition> getArrays0() {
      List<ArrayDefinition> result = new ArrayList();
      return result;
   }

   protected List<RoutineDefinition> getRoutines0() {
      List<RoutineDefinition> result = new ArrayList();
      return result;
   }

   protected List<PackageDefinition> getPackages0() {
      List<PackageDefinition> result = new ArrayList();
      return result;
   }

   static int unbox(Integer i) {
      return i == null ? 0 : i;
   }

   static long unbox(Long l) {
      return l == null ? 0L : l;
   }
}
