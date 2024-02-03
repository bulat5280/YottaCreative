package org.jooq.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.jooq.tools.JooqLogger;

public class DefaultRelations implements Relations {
   private static final JooqLogger log = JooqLogger.getLogger(DefaultRelations.class);
   private Map<DefaultRelations.Key, UniqueKeyDefinition> primaryKeys = new LinkedHashMap();
   private Map<DefaultRelations.Key, UniqueKeyDefinition> uniqueKeys = new LinkedHashMap();
   private Map<DefaultRelations.Key, ForeignKeyDefinition> foreignKeys = new LinkedHashMap();
   private Map<DefaultRelations.Key, CheckConstraintDefinition> checkConstraints = new LinkedHashMap();
   private transient Map<ColumnDefinition, UniqueKeyDefinition> primaryKeysByColumn;
   private transient Map<ColumnDefinition, List<UniqueKeyDefinition>> uniqueKeysByColumn;
   private transient Map<ColumnDefinition, List<ForeignKeyDefinition>> foreignKeysByColumn;
   private transient Map<TableDefinition, List<CheckConstraintDefinition>> checkConstraintsByTable;

   public void addPrimaryKey(String keyName, ColumnDefinition column) {
      if (column == null) {
         log.info("Ignoring primary key", (Object)(keyName + "(column unavailable)"));
      } else {
         if (log.isDebugEnabled()) {
            log.debug("Adding primary key", (Object)(keyName + " (" + column + ")"));
         }

         UniqueKeyDefinition key = this.getUniqueKey(keyName, column, true);
         key.getKeyColumns().add(column);
      }
   }

   public void addUniqueKey(String keyName, ColumnDefinition column) {
      if (column == null) {
         log.info("Ignoring unique key", (Object)(keyName + "(column unavailable)"));
      } else {
         if (log.isDebugEnabled()) {
            log.debug("Adding unique key", (Object)(keyName + " (" + column + ")"));
         }

         UniqueKeyDefinition key = this.getUniqueKey(keyName, column, false);
         key.getKeyColumns().add(column);
      }
   }

   public void overridePrimaryKey(UniqueKeyDefinition key) {
      UniqueKeyDefinition old = null;
      this.primaryKeysByColumn = null;
      this.uniqueKeysByColumn = null;
      Iterator it = this.primaryKeys.entrySet().iterator();

      while(it.hasNext()) {
         Entry<DefaultRelations.Key, UniqueKeyDefinition> entry = (Entry)it.next();
         if (((UniqueKeyDefinition)entry.getValue()).getTable().equals(key.getTable())) {
            old = (UniqueKeyDefinition)entry.getValue();
            it.remove();
            break;
         }
      }

      DefaultRelations.Key mapKey = key(key.getTable(), key.getName());
      this.primaryKeys.put(mapKey, key);
      this.uniqueKeys.put(mapKey, key);
      log.info("Overriding primary key", (Object)("Table : " + key.getTable() + ", previous key : " + (old == null ? "none" : old.getName()) + ", new key : " + key.getName()));
   }

   private UniqueKeyDefinition getUniqueKey(String keyName, ColumnDefinition column, boolean isPK) {
      UniqueKeyDefinition key = (UniqueKeyDefinition)this.uniqueKeys.get(key(column, keyName));
      if (key == null) {
         key = new DefaultUniqueKeyDefinition(column.getSchema(), keyName, (TableDefinition)column.getContainer(), isPK);
         this.uniqueKeys.put(key(column, keyName), key);
         if (isPK) {
            this.primaryKeys.put(key(column, keyName), key);
         }
      }

      return (UniqueKeyDefinition)key;
   }

   public void addForeignKey(String foreignKeyName, String uniqueKeyName, ColumnDefinition foreignKeyColumn, SchemaDefinition uniqueKeySchema) {
      if (foreignKeyColumn == null) {
         log.info("Ignoring foreign key", (Object)(foreignKeyColumn + "(column unavailable)"));
      } else if (uniqueKeySchema == null) {
         log.info("Ignoring foreign key", (Object)(foreignKeyName + " (" + foreignKeyColumn + ") referencing " + uniqueKeyName + " references a schema out of scope for jooq-meta: " + uniqueKeySchema));
      } else {
         log.info("Adding foreign key", (Object)(foreignKeyName + " (" + foreignKeyColumn + ") referencing " + uniqueKeyName));
         ForeignKeyDefinition foreignKey = (ForeignKeyDefinition)this.foreignKeys.get(key(foreignKeyColumn, foreignKeyName));
         if (foreignKey == null) {
            UniqueKeyDefinition uniqueKey = (UniqueKeyDefinition)this.uniqueKeys.get(key(uniqueKeySchema, uniqueKeyName));
            if (uniqueKey != null) {
               foreignKey = new DefaultForeignKeyDefinition(foreignKeyColumn.getSchema(), foreignKeyName, (TableDefinition)foreignKeyColumn.getContainer(), uniqueKey);
               this.foreignKeys.put(key(foreignKeyColumn, foreignKeyName), foreignKey);
               uniqueKey.getForeignKeys().add(foreignKey);
            }
         }

         if (foreignKey != null) {
            ((ForeignKeyDefinition)foreignKey).getKeyColumns().add(foreignKeyColumn);
         }

      }
   }

   public void addCheckConstraint(TableDefinition table, CheckConstraintDefinition constraint) {
      this.checkConstraints.put(key(table, constraint.getName()), constraint);
   }

   public UniqueKeyDefinition getPrimaryKey(ColumnDefinition column) {
      if (this.primaryKeysByColumn == null) {
         this.primaryKeysByColumn = new LinkedHashMap();
         Iterator var2 = this.primaryKeys.values().iterator();

         while(var2.hasNext()) {
            UniqueKeyDefinition primaryKey = (UniqueKeyDefinition)var2.next();
            Iterator var4 = primaryKey.getKeyColumns().iterator();

            while(var4.hasNext()) {
               ColumnDefinition keyColumn = (ColumnDefinition)var4.next();
               this.primaryKeysByColumn.put(keyColumn, primaryKey);
            }
         }
      }

      return (UniqueKeyDefinition)this.primaryKeysByColumn.get(column);
   }

   public List<UniqueKeyDefinition> getUniqueKeys(ColumnDefinition column) {
      if (this.uniqueKeysByColumn == null) {
         this.uniqueKeysByColumn = new LinkedHashMap();
         Iterator var2 = this.uniqueKeys.values().iterator();

         while(var2.hasNext()) {
            UniqueKeyDefinition uniqueKey = (UniqueKeyDefinition)var2.next();

            Object list;
            for(Iterator var4 = uniqueKey.getKeyColumns().iterator(); var4.hasNext(); ((List)list).add(uniqueKey)) {
               ColumnDefinition keyColumn = (ColumnDefinition)var4.next();
               list = (List)this.uniqueKeysByColumn.get(keyColumn);
               if (list == null) {
                  list = new ArrayList();
                  this.uniqueKeysByColumn.put(keyColumn, list);
               }
            }
         }
      }

      List<UniqueKeyDefinition> list = (List)this.uniqueKeysByColumn.get(column);
      return list != null ? list : Collections.emptyList();
   }

   public List<UniqueKeyDefinition> getUniqueKeys(TableDefinition table) {
      Set<UniqueKeyDefinition> result = new LinkedHashSet();
      Iterator var3 = table.getColumns().iterator();

      while(var3.hasNext()) {
         ColumnDefinition column = (ColumnDefinition)var3.next();
         result.addAll(this.getUniqueKeys(column));
      }

      return new ArrayList(result);
   }

   public List<UniqueKeyDefinition> getUniqueKeys(SchemaDefinition schema) {
      Set<UniqueKeyDefinition> result = new LinkedHashSet();
      Iterator var3 = schema.getDatabase().getTables(schema).iterator();

      while(var3.hasNext()) {
         TableDefinition table = (TableDefinition)var3.next();
         result.addAll(this.getUniqueKeys(table));
      }

      return new ArrayList(result);
   }

   public List<UniqueKeyDefinition> getUniqueKeys() {
      return new ArrayList(this.uniqueKeys.values());
   }

   public List<ForeignKeyDefinition> getForeignKeys(ColumnDefinition column) {
      if (this.foreignKeysByColumn == null) {
         this.foreignKeysByColumn = new LinkedHashMap();
         Iterator var2 = this.foreignKeys.values().iterator();

         while(var2.hasNext()) {
            ForeignKeyDefinition foreignKey = (ForeignKeyDefinition)var2.next();

            Object list;
            for(Iterator var4 = foreignKey.getKeyColumns().iterator(); var4.hasNext(); ((List)list).add(foreignKey)) {
               ColumnDefinition keyColumn = (ColumnDefinition)var4.next();
               list = (List)this.foreignKeysByColumn.get(keyColumn);
               if (list == null) {
                  list = new ArrayList();
                  this.foreignKeysByColumn.put(keyColumn, list);
               }
            }
         }
      }

      List<ForeignKeyDefinition> list = (List)this.foreignKeysByColumn.get(column);
      return list != null ? list : Collections.emptyList();
   }

   public List<ForeignKeyDefinition> getForeignKeys(TableDefinition table) {
      Set<ForeignKeyDefinition> result = new LinkedHashSet();
      Iterator var3 = table.getColumns().iterator();

      while(var3.hasNext()) {
         ColumnDefinition column = (ColumnDefinition)var3.next();
         result.addAll(this.getForeignKeys(column));
      }

      return new ArrayList(result);
   }

   public List<CheckConstraintDefinition> getCheckConstraints(TableDefinition table) {
      if (this.checkConstraintsByTable == null) {
         this.checkConstraintsByTable = new LinkedHashMap();

         CheckConstraintDefinition constraint;
         Object list;
         for(Iterator var2 = this.checkConstraints.values().iterator(); var2.hasNext(); ((List)list).add(constraint)) {
            constraint = (CheckConstraintDefinition)var2.next();
            list = (List)this.checkConstraintsByTable.get(table);
            if (list == null) {
               list = new ArrayList();
               this.checkConstraintsByTable.put(table, list);
            }
         }
      }

      List<CheckConstraintDefinition> list = (List)this.checkConstraintsByTable.get(table);
      return list != null ? list : Collections.emptyList();
   }

   private static DefaultRelations.Key key(Definition definition, String keyName) {
      return new DefaultRelations.Key(definition.getSchema(), keyName);
   }

   private static class Key {
      final SchemaDefinition schema;
      final String keyName;

      Key(SchemaDefinition schema, String keyName) {
         this.schema = schema;
         this.keyName = keyName;
      }

      public String toString() {
         return "Key [schema=" + this.schema + ", keyName=" + this.keyName + "]";
      }

      public int hashCode() {
         int prime = true;
         int result = 1;
         int result = 31 * result + (this.keyName == null ? 0 : this.keyName.hashCode());
         result = 31 * result + (this.schema == null ? 0 : this.schema.hashCode());
         return result;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            DefaultRelations.Key other = (DefaultRelations.Key)obj;
            if (this.keyName == null) {
               if (other.keyName != null) {
                  return false;
               }
            } else if (!this.keyName.equals(other.keyName)) {
               return false;
            }

            if (this.schema == null) {
               if (other.schema != null) {
                  return false;
               }
            } else if (!this.schema.equals(other.schema)) {
               return false;
            }

            return true;
         }
      }
   }
}
