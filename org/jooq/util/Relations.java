package org.jooq.util;

import java.util.List;

public interface Relations {
   UniqueKeyDefinition getPrimaryKey(ColumnDefinition var1);

   List<UniqueKeyDefinition> getUniqueKeys(ColumnDefinition var1);

   List<UniqueKeyDefinition> getUniqueKeys(TableDefinition var1);

   List<UniqueKeyDefinition> getUniqueKeys(SchemaDefinition var1);

   List<UniqueKeyDefinition> getUniqueKeys();

   List<ForeignKeyDefinition> getForeignKeys(ColumnDefinition var1);

   List<ForeignKeyDefinition> getForeignKeys(TableDefinition var1);

   List<CheckConstraintDefinition> getCheckConstraints(TableDefinition var1);
}
