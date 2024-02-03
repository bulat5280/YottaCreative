package org.jooq.util;

import java.util.List;

public interface ForeignKeyDefinition extends Definition {
   TableDefinition getKeyTable();

   List<ColumnDefinition> getKeyColumns();

   UniqueKeyDefinition getReferencedKey();

   TableDefinition getReferencedTable();

   List<ColumnDefinition> getReferencedColumns();

   int countSimilarReferences();
}
