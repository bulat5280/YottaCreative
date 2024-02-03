package org.jooq.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DefaultForeignKeyDefinition extends AbstractDefinition implements ForeignKeyDefinition {
   private final List<ColumnDefinition> keyColumns = new ArrayList();
   private final TableDefinition table;
   private final UniqueKeyDefinition uniqueKey;

   public DefaultForeignKeyDefinition(SchemaDefinition schema, String name, TableDefinition table, UniqueKeyDefinition uniqueKey) {
      super(schema.getDatabase(), schema, name, (String)null);
      this.table = table;
      this.uniqueKey = uniqueKey;
   }

   public List<Definition> getDefinitionPath() {
      List<Definition> result = new ArrayList();
      result.addAll(this.getSchema().getDefinitionPath());
      result.add(this);
      return result;
   }

   public TableDefinition getKeyTable() {
      return this.table;
   }

   public List<ColumnDefinition> getKeyColumns() {
      return this.keyColumns;
   }

   public UniqueKeyDefinition getReferencedKey() {
      return this.uniqueKey;
   }

   public TableDefinition getReferencedTable() {
      return this.uniqueKey.getTable();
   }

   public List<ColumnDefinition> getReferencedColumns() {
      return this.uniqueKey.getKeyColumns();
   }

   public int countSimilarReferences() {
      Set<String> keys = new HashSet();
      Iterator var2 = this.getDatabase().getRelations().getForeignKeys(this.table).iterator();

      while(var2.hasNext()) {
         ForeignKeyDefinition key = (ForeignKeyDefinition)var2.next();
         if (key.getReferencedTable().equals(this.getReferencedTable())) {
            keys.add(key.getName());
         }
      }

      return keys.size();
   }
}
