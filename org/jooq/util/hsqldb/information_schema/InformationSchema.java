package org.jooq.util.hsqldb.information_schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import org.jooq.util.hsqldb.information_schema.tables.CheckConstraints;
import org.jooq.util.hsqldb.information_schema.tables.Columns;
import org.jooq.util.hsqldb.information_schema.tables.ElementTypes;
import org.jooq.util.hsqldb.information_schema.tables.KeyColumnUsage;
import org.jooq.util.hsqldb.information_schema.tables.Parameters;
import org.jooq.util.hsqldb.information_schema.tables.ReferentialConstraints;
import org.jooq.util.hsqldb.information_schema.tables.Routines;
import org.jooq.util.hsqldb.information_schema.tables.Schemata;
import org.jooq.util.hsqldb.information_schema.tables.Sequences;
import org.jooq.util.hsqldb.information_schema.tables.TableConstraints;

public class InformationSchema extends SchemaImpl {
   private static final long serialVersionUID = 392228711L;
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
      return Arrays.asList(CheckConstraints.CHECK_CONSTRAINTS, Columns.COLUMNS, ElementTypes.ELEMENT_TYPES, KeyColumnUsage.KEY_COLUMN_USAGE, Parameters.PARAMETERS, ReferentialConstraints.REFERENTIAL_CONSTRAINTS, Routines.ROUTINES, Schemata.SCHEMATA, Sequences.SEQUENCES, org.jooq.util.hsqldb.information_schema.tables.Tables.TABLES, TableConstraints.TABLE_CONSTRAINTS);
   }
}
