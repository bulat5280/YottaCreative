package org.jooq.util;

import java.util.List;

public interface ColumnDefinition extends TypedElementDefinition<TableDefinition> {
   int getPosition();

   UniqueKeyDefinition getPrimaryKey();

   List<UniqueKeyDefinition> getUniqueKeys();

   List<ForeignKeyDefinition> getForeignKeys();

   boolean isIdentity();

   /** @deprecated */
   @Deprecated
   boolean isNullable();
}
