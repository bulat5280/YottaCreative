package org.jooq;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.bind.JAXB;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.MappedTable;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.SettingsTools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

/** @deprecated */
@Deprecated
public class SchemaMapping implements Serializable {
   private static final long serialVersionUID = 8269660159338710470L;
   private static final JooqLogger log = JooqLogger.getLogger(SchemaMapping.class);
   private static volatile boolean loggedDeprecation = false;
   private final Configuration configuration;
   private transient volatile Map<String, Schema> schemata;
   private transient volatile Map<String, Table<?>> tables;

   public SchemaMapping(Configuration configuration) {
      this.configuration = configuration;
   }

   private final RenderMapping mapping() {
      return SettingsTools.getRenderMapping(this.configuration.settings());
   }

   private final boolean renderCatalog() {
      return Boolean.TRUE.equals(this.configuration.settings().isRenderCatalog());
   }

   private final boolean renderSchema() {
      return Boolean.TRUE.equals(this.configuration.settings().isRenderSchema());
   }

   private static void logDeprecation() {
      if (!loggedDeprecation) {
         loggedDeprecation = true;
         log.warn("DEPRECATION", (Object)"org.jooq.SchemaMapping is deprecated as of jOOQ 2.0.5. Consider using jOOQ's runtime configuration org.jooq.conf.Settings instead");
      }

   }

   public void use(Schema schema) {
      this.use(schema.getName());
   }

   public void use(String schemaName) {
      logDeprecation();
      this.mapping().setDefaultSchema(schemaName);
   }

   public void add(String inputSchema, String outputSchema) {
      logDeprecation();
      MappedSchema schema = null;
      Iterator var4 = this.mapping().getSchemata().iterator();

      while(var4.hasNext()) {
         MappedSchema s = (MappedSchema)var4.next();
         if (inputSchema.equals(s.getInput())) {
            schema = s;
            break;
         }
      }

      if (schema == null) {
         schema = (new MappedSchema()).withInput(inputSchema);
         this.mapping().getSchemata().add(schema);
      }

      schema.setOutput(outputSchema);
   }

   public void add(String inputSchema, Schema outputSchema) {
      this.add(inputSchema, outputSchema.getName());
   }

   public void add(Schema inputSchema, Schema outputSchema) {
      this.add(inputSchema.getName(), outputSchema.getName());
   }

   public void add(Schema inputSchema, String outputSchema) {
      this.add(inputSchema.getName(), outputSchema);
   }

   public void add(Table<?> inputTable, Table<?> outputTable) {
      this.add(inputTable, outputTable.getName());
   }

   public void add(Table<?> inputTable, String outputTable) {
      logDeprecation();
      MappedSchema schema = null;
      MappedTable table = null;
      Iterator var5 = this.mapping().getSchemata().iterator();

      while(var5.hasNext()) {
         MappedSchema s = (MappedSchema)var5.next();
         if (inputTable.getSchema().getName().equals(s.getInput())) {
            Iterator var7 = s.getTables().iterator();

            while(var7.hasNext()) {
               MappedTable t = (MappedTable)var7.next();
               if (inputTable.getName().equals(t.getInput())) {
                  table = t;
                  break;
               }
            }

            schema = s;
            break;
         }
      }

      if (schema == null) {
         schema = (new MappedSchema()).withInput(inputTable.getSchema().getName());
         this.mapping().getSchemata().add(schema);
      }

      if (table == null) {
         table = (new MappedTable()).withInput(inputTable.getName());
         schema.getTables().add(table);
      }

      table.setOutput(outputTable);
   }

   public Catalog map(Catalog catalog) {
      if (!this.renderCatalog()) {
         return null;
      } else {
         if (catalog != null) {
            String catalogName = catalog.getName();
            if (StringUtils.isEmpty(catalogName)) {
               return null;
            }
         }

         return catalog;
      }
   }

   public Schema map(Schema schema) {
      if (!this.renderSchema()) {
         return null;
      } else {
         Schema result = schema;
         if (schema != null) {
            String schemaName = schema.getName();
            if (StringUtils.isEmpty(schemaName)) {
               return null;
            }

            if (!this.mapping().getSchemata().isEmpty()) {
               if (!this.getSchemata().containsKey(schemaName)) {
                  synchronized(this) {
                     if (!this.getSchemata().containsKey(schemaName)) {
                        Iterator var5 = this.mapping().getSchemata().iterator();

                        while(var5.hasNext()) {
                           MappedSchema s = (MappedSchema)var5.next();
                           if (this.matches(s, schemaName)) {
                              if (!StringUtils.isBlank(s.getOutput())) {
                                 if (s.getInput() != null && !s.getOutput().equals(schemaName)) {
                                    result = new RenamedSchema((Schema)result, s.getOutput());
                                 } else if (s.getInputExpression() != null) {
                                    result = new RenamedSchema((Schema)result, s.getInputExpression().matcher(schemaName).replaceAll(s.getOutput()));
                                 }
                              }
                              break;
                           }
                        }

                        this.getSchemata().put(schemaName, result);
                     }
                  }
               }

               result = (Schema)this.getSchemata().get(schemaName);
            }

            if (((Schema)result).getName().equals(this.mapping().getDefaultSchema())) {
               result = null;
            }
         }

         return (Schema)result;
      }
   }

   public <R extends Record> Table<R> map(Table<R> table) {
      Table<R> result = table;
      if (table != null && !this.mapping().getSchemata().isEmpty()) {
         Schema schema = table.getSchema();
         String schemaName = schema == null ? "" : schema.getName();
         String tableName = table.getName();
         String key = schema != null && !StringUtils.isEmpty(schemaName) ? schemaName + "." + tableName : tableName;
         if (!this.getTables().containsKey(key)) {
            synchronized(this) {
               if (!this.getTables().containsKey(key)) {
                  Iterator var8 = this.mapping().getSchemata().iterator();

                  label61:
                  while(true) {
                     MappedSchema s;
                     do {
                        if (!var8.hasNext()) {
                           break label61;
                        }

                        s = (MappedSchema)var8.next();
                     } while(!this.matches(s, schemaName));

                     Iterator var10 = s.getTables().iterator();

                     while(var10.hasNext()) {
                        MappedTable t = (MappedTable)var10.next();
                        if (this.matches(t, tableName)) {
                           if (StringUtils.isBlank(t.getOutput())) {
                              break label61;
                           }

                           if (t.getInput() != null && !t.getOutput().equals(tableName)) {
                              result = new RenamedTable((Table)result, t.getOutput());
                              break label61;
                           }

                           if (t.getInputExpression() != null) {
                              result = new RenamedTable((Table)result, t.getInputExpression().matcher(tableName).replaceAll(t.getOutput()));
                           }
                           break label61;
                        }
                     }
                  }

                  this.getTables().put(key, result);
               }
            }
         }

         result = (Table)this.getTables().get(key);
      }

      return (Table)result;
   }

   private final boolean matches(MappedSchema s, String schemaName) {
      return s.getInput() != null && schemaName.equals(s.getInput()) || s.getInputExpression() != null && s.getInputExpression().matcher(schemaName).matches();
   }

   private final boolean matches(MappedTable t, String tableName) {
      return t.getInput() != null && tableName.equals(t.getInput()) || t.getInputExpression() != null && t.getInputExpression().matcher(tableName).matches();
   }

   public void setDefaultSchema(String schema) {
      this.use(schema);
   }

   public void setSchemaMapping(Map<String, String> schemaMap) {
      Iterator var2 = schemaMap.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<String, String> entry = (Entry)var2.next();
         this.add((String)entry.getKey(), (String)entry.getValue());
      }

   }

   private final Map<String, Schema> getSchemata() {
      if (this.schemata == null) {
         synchronized(this) {
            if (this.schemata == null) {
               this.schemata = new HashMap();
            }
         }
      }

      return this.schemata;
   }

   private final Map<String, Table<?>> getTables() {
      if (this.tables == null) {
         synchronized(this) {
            if (this.tables == null) {
               this.tables = new HashMap();
            }
         }
      }

      return this.tables;
   }

   public String toString() {
      StringWriter writer = new StringWriter();
      JAXB.marshal(this.mapping(), writer);
      return writer.toString();
   }
}
