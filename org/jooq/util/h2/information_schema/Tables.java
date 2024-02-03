package org.jooq.util.h2.information_schema;

import org.jooq.util.h2.information_schema.tables.Columns;
import org.jooq.util.h2.information_schema.tables.Constraints;
import org.jooq.util.h2.information_schema.tables.CrossReferences;
import org.jooq.util.h2.information_schema.tables.FunctionAliases;
import org.jooq.util.h2.information_schema.tables.FunctionColumns;
import org.jooq.util.h2.information_schema.tables.Indexes;
import org.jooq.util.h2.information_schema.tables.Schemata;
import org.jooq.util.h2.information_schema.tables.Sequences;
import org.jooq.util.h2.information_schema.tables.TypeInfo;

public class Tables {
   public static final FunctionColumns FUNCTION_COLUMNS;
   public static final Constraints CONSTRAINTS;
   public static final CrossReferences CROSS_REFERENCES;
   public static final Schemata SCHEMATA;
   public static final FunctionAliases FUNCTION_ALIASES;
   public static final Sequences SEQUENCES;
   public static final TypeInfo TYPE_INFO;
   public static final Indexes INDEXES;
   public static final Columns COLUMNS;
   public static final org.jooq.util.h2.information_schema.tables.Tables TABLES;

   static {
      FUNCTION_COLUMNS = FunctionColumns.FUNCTION_COLUMNS;
      CONSTRAINTS = Constraints.CONSTRAINTS;
      CROSS_REFERENCES = CrossReferences.CROSS_REFERENCES;
      SCHEMATA = Schemata.SCHEMATA;
      FUNCTION_ALIASES = FunctionAliases.FUNCTION_ALIASES;
      SEQUENCES = Sequences.SEQUENCES;
      TYPE_INFO = TypeInfo.TYPE_INFO;
      INDEXES = Indexes.INDEXES;
      COLUMNS = Columns.COLUMNS;
      TABLES = org.jooq.util.h2.information_schema.tables.Tables.TABLES;
   }
}
