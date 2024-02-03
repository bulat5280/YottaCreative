package org.jooq.util;

public class DefaultSequenceDefinition extends AbstractTypedElementDefinition<SchemaDefinition> implements SequenceDefinition {
   public DefaultSequenceDefinition(SchemaDefinition schema, String name, DataTypeDefinition type) {
      super(schema, name, -1, type, (String)null);
   }
}
