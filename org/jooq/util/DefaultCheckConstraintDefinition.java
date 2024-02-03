package org.jooq.util;

import java.util.ArrayList;
import java.util.List;

public class DefaultCheckConstraintDefinition extends AbstractDefinition implements CheckConstraintDefinition {
   private final TableDefinition table;
   private final String checkClause;

   public DefaultCheckConstraintDefinition(SchemaDefinition schema, TableDefinition table, String name, String checkClause) {
      super(schema.getDatabase(), schema, name, (String)null);
      this.table = table;
      this.checkClause = checkClause;
   }

   public List<Definition> getDefinitionPath() {
      List<Definition> result = new ArrayList();
      result.addAll(this.getSchema().getDefinitionPath());
      result.add(this);
      return result;
   }

   public TableDefinition getTable() {
      return this.table;
   }

   public String getCheckClause() {
      return this.checkClause;
   }
}
