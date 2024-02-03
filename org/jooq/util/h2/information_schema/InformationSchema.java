package org.jooq.util.h2.information_schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import org.jooq.util.h2.information_schema.tables.Columns;
import org.jooq.util.h2.information_schema.tables.Constraints;
import org.jooq.util.h2.information_schema.tables.CrossReferences;
import org.jooq.util.h2.information_schema.tables.FunctionAliases;
import org.jooq.util.h2.information_schema.tables.FunctionColumns;
import org.jooq.util.h2.information_schema.tables.Indexes;
import org.jooq.util.h2.information_schema.tables.Schemata;
import org.jooq.util.h2.information_schema.tables.Sequences;
import org.jooq.util.h2.information_schema.tables.TypeInfo;

public class InformationSchema extends SchemaImpl {
   private static final long serialVersionUID = 1479405880L;
   public static final InformationSchema INFORMATION_SCHEMA = new InformationSchema();

   private InformationSchema() {
      super("INFORMATION_SCHEMA");
   }

   public final List<Table<?>> getTables() {
      List result = new ArrayList();
      result.addAll(this.getTables0());
      return result;
   }

   private final List<Table<?>> getTables0() {
      return Arrays.asList(FunctionColumns.FUNCTION_COLUMNS, Constraints.CONSTRAINTS, CrossReferences.CROSS_REFERENCES, Schemata.SCHEMATA, FunctionAliases.FUNCTION_ALIASES, Sequences.SEQUENCES, TypeInfo.TYPE_INFO, Indexes.INDEXES, Columns.COLUMNS, org.jooq.util.h2.information_schema.tables.Tables.TABLES);
   }
}
