package org.jooq.util;

import java.util.ArrayList;
import java.util.List;

public class DefaultArrayDefinition extends AbstractDefinition implements ArrayDefinition {
   private final DataTypeDefinition definedType;
   private transient DataTypeDefinition type;

   public DefaultArrayDefinition(SchemaDefinition schema, String name, DataTypeDefinition type) {
      super(schema.getDatabase(), schema, name, "");
      this.definedType = type;
   }

   public List<Definition> getDefinitionPath() {
      List<Definition> result = new ArrayList();
      result.addAll(this.getSchema().getDefinitionPath());
      result.add(this);
      return result;
   }

   public DataTypeDefinition getElementType() {
      if (this.type == null) {
         this.type = AbstractTypedElementDefinition.mapDefinedType(this, this, this.definedType);
      }

      return this.type;
   }
}
