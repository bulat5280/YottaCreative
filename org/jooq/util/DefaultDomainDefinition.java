package org.jooq.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultDomainDefinition extends AbstractDefinition implements DomainDefinition {
   private final List<String> checkClauses;
   private final DataTypeDefinition baseType;

   public DefaultDomainDefinition(SchemaDefinition schema, String name, DataTypeDefinition baseType) {
      super(schema.getDatabase(), schema, name, "");
      this.baseType = baseType;
      this.checkClauses = new ArrayList();
   }

   public List<Definition> getDefinitionPath() {
      List<Definition> result = new ArrayList();
      result.addAll(this.getSchema().getDefinitionPath());
      result.add(this);
      return result;
   }

   public void addCheckClause(String checkClause) {
      this.checkClauses.add(checkClause);
   }

   public void addCheckClause(String... checkClause) {
      this.checkClauses.addAll(Arrays.asList(checkClause));
   }

   public List<String> getCheckClauses() {
      return this.checkClauses;
   }

   public DataTypeDefinition getBaseType() {
      return this.baseType;
   }
}
