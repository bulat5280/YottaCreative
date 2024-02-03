package org.jooq.util;

import java.util.ArrayList;
import java.util.List;

public class DefaultUniqueKeyDefinition extends AbstractDefinition implements UniqueKeyDefinition {
   private final List<ForeignKeyDefinition> foreignKeys = new ArrayList();
   private final List<ColumnDefinition> keyColumns = new ArrayList();
   private final TableDefinition table;
   private final boolean isPrimaryKey;

   public DefaultUniqueKeyDefinition(SchemaDefinition schema, String name, TableDefinition table, boolean isPrimaryKey) {
      super(schema.getDatabase(), schema, name, (String)null);
      this.table = table;
      this.isPrimaryKey = isPrimaryKey;
   }

   public boolean isPrimaryKey() {
      return this.isPrimaryKey;
   }

   public List<Definition> getDefinitionPath() {
      List<Definition> result = new ArrayList();
      result.addAll(this.getSchema().getDefinitionPath());
      result.add(this);
      return result;
   }

   public List<ColumnDefinition> getKeyColumns() {
      return this.keyColumns;
   }

   public List<ForeignKeyDefinition> getForeignKeys() {
      return this.foreignKeys;
   }

   public TableDefinition getTable() {
      return this.table;
   }
}
