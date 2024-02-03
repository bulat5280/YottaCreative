package org.jooq.util;

public interface TypedElementDefinition<T extends Definition> extends Definition {
   DataTypeDefinition getType();

   T getContainer();
}
