package org.jooq.util.postgres.information_schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import org.jooq.util.postgres.information_schema.tables.Attributes;
import org.jooq.util.postgres.information_schema.tables.CheckConstraints;
import org.jooq.util.postgres.information_schema.tables.Columns;
import org.jooq.util.postgres.information_schema.tables.ConstraintColumnUsage;
import org.jooq.util.postgres.information_schema.tables.KeyColumnUsage;
import org.jooq.util.postgres.information_schema.tables.Parameters;
import org.jooq.util.postgres.information_schema.tables.ReferentialConstraints;
import org.jooq.util.postgres.information_schema.tables.Routines;
import org.jooq.util.postgres.information_schema.tables.Schemata;
import org.jooq.util.postgres.information_schema.tables.Sequences;
import org.jooq.util.postgres.information_schema.tables.TableConstraints;

public class InformationSchema extends SchemaImpl {
   private static final long serialVersionUID = -279981472L;
   public static final InformationSchema INFORMATION_SCHEMA = new InformationSchema();

   private InformationSchema() {
      super("information_schema");
   }

   public final List<Table<?>> getTables() {
      List result = new ArrayList();
      result.addAll(this.getTables0());
      return result;
   }

   private final List<Table<?>> getTables0() {
      return Arrays.asList(Attributes.ATTRIBUTES, CheckConstraints.CHECK_CONSTRAINTS, Columns.COLUMNS, ConstraintColumnUsage.CONSTRAINT_COLUMN_USAGE, KeyColumnUsage.KEY_COLUMN_USAGE, Parameters.PARAMETERS, ReferentialConstraints.REFERENTIAL_CONSTRAINTS, Routines.ROUTINES, Schemata.SCHEMATA, Sequences.SEQUENCES, TableConstraints.TABLE_CONSTRAINTS, org.jooq.util.postgres.information_schema.tables.Tables.TABLES);
   }
}
