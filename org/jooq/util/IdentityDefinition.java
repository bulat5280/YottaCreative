package org.jooq.util;

public interface IdentityDefinition extends Definition {
   TableDefinition getTable();

   ColumnDefinition getColumn();
}
