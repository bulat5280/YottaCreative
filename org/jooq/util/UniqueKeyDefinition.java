package org.jooq.util;

import java.util.List;

public interface UniqueKeyDefinition extends Definition {
   boolean isPrimaryKey();

   List<ColumnDefinition> getKeyColumns();

   List<ForeignKeyDefinition> getForeignKeys();

   TableDefinition getTable();
}
