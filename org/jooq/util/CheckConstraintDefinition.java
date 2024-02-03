package org.jooq.util;

public interface CheckConstraintDefinition extends Definition {
   String getCheckClause();

   TableDefinition getTable();
}
