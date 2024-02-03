package org.jooq.util;

import java.util.List;

public class DefaultIdentityDefinition extends AbstractDefinition implements IdentityDefinition {
   private final ColumnDefinition column;

   public DefaultIdentityDefinition(ColumnDefinition column) {
      super(column.getDatabase(), column.getSchema(), column.getName());
      this.column = column;
   }

   public List<Definition> getDefinitionPath() {
      return this.getColumn().getDefinitionPath();
   }

   public final TableDefinition getTable() {
      return (TableDefinition)this.getColumn().getContainer();
   }

   public ColumnDefinition getColumn() {
      return this.column;
   }
}
