package org.jooq.util;

public class DefaultAttributeDefinition extends AbstractTypedElementDefinition<UDTDefinition> implements AttributeDefinition {
   public DefaultAttributeDefinition(UDTDefinition udt, String name, int position, DataTypeDefinition type) {
      super(udt, name, position, type, (String)null);
   }
}
