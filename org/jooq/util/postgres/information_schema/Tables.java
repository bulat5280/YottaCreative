package org.jooq.util.postgres.information_schema;

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

public class Tables {
   public static final Attributes ATTRIBUTES;
   public static final CheckConstraints CHECK_CONSTRAINTS;
   public static final Columns COLUMNS;
   public static final ConstraintColumnUsage CONSTRAINT_COLUMN_USAGE;
   public static final KeyColumnUsage KEY_COLUMN_USAGE;
   public static final Parameters PARAMETERS;
   public static final ReferentialConstraints REFERENTIAL_CONSTRAINTS;
   public static final Routines ROUTINES;
   public static final Schemata SCHEMATA;
   public static final Sequences SEQUENCES;
   public static final TableConstraints TABLE_CONSTRAINTS;
   public static final org.jooq.util.postgres.information_schema.tables.Tables TABLES;

   static {
      ATTRIBUTES = Attributes.ATTRIBUTES;
      CHECK_CONSTRAINTS = CheckConstraints.CHECK_CONSTRAINTS;
      COLUMNS = Columns.COLUMNS;
      CONSTRAINT_COLUMN_USAGE = ConstraintColumnUsage.CONSTRAINT_COLUMN_USAGE;
      KEY_COLUMN_USAGE = KeyColumnUsage.KEY_COLUMN_USAGE;
      PARAMETERS = Parameters.PARAMETERS;
      REFERENTIAL_CONSTRAINTS = ReferentialConstraints.REFERENTIAL_CONSTRAINTS;
      ROUTINES = Routines.ROUTINES;
      SCHEMATA = Schemata.SCHEMATA;
      SEQUENCES = Sequences.SEQUENCES;
      TABLE_CONSTRAINTS = TableConstraints.TABLE_CONSTRAINTS;
      TABLES = org.jooq.util.postgres.information_schema.tables.Tables.TABLES;
   }
}
