package org.jooq.util;

import java.util.List;
import org.jooq.Record;
import org.jooq.Table;

public interface TableDefinition extends Definition {
   List<ColumnDefinition> getColumns();

   ColumnDefinition getColumn(String var1);

   ColumnDefinition getColumn(String var1, boolean var2);

   ColumnDefinition getColumn(int var1);

   UniqueKeyDefinition getPrimaryKey();

   List<UniqueKeyDefinition> getUniqueKeys();

   List<ForeignKeyDefinition> getForeignKeys();

   List<CheckConstraintDefinition> getCheckConstraints();

   IdentityDefinition getIdentity();

   TableDefinition getParentTable();

   List<TableDefinition> getChildTables();

   Table<Record> getTable();

   List<ParameterDefinition> getParameters();

   boolean isTableValuedFunction();
}
