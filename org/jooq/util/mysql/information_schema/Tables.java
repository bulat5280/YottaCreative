package org.jooq.util.mysql.information_schema;

import org.jooq.util.mysql.information_schema.tables.Columns;
import org.jooq.util.mysql.information_schema.tables.KeyColumnUsage;
import org.jooq.util.mysql.information_schema.tables.Parameters;
import org.jooq.util.mysql.information_schema.tables.ReferentialConstraints;
import org.jooq.util.mysql.information_schema.tables.Schemata;
import org.jooq.util.mysql.information_schema.tables.Statistics;
import org.jooq.util.mysql.information_schema.tables.TableConstraints;

public class Tables {
   public static final Columns COLUMNS;
   public static final KeyColumnUsage KEY_COLUMN_USAGE;
   public static final Parameters PARAMETERS;
   public static final ReferentialConstraints REFERENTIAL_CONSTRAINTS;
   public static final Schemata SCHEMATA;
   public static final Statistics STATISTICS;
   public static final org.jooq.util.mysql.information_schema.tables.Tables TABLES;
   public static final TableConstraints TABLE_CONSTRAINTS;

   static {
      COLUMNS = Columns.COLUMNS;
      KEY_COLUMN_USAGE = KeyColumnUsage.KEY_COLUMN_USAGE;
      PARAMETERS = Parameters.PARAMETERS;
      REFERENTIAL_CONSTRAINTS = ReferentialConstraints.REFERENTIAL_CONSTRAINTS;
      SCHEMATA = Schemata.SCHEMATA;
      STATISTICS = Statistics.STATISTICS;
      TABLES = org.jooq.util.mysql.information_schema.tables.Tables.TABLES;
      TABLE_CONSTRAINTS = TableConstraints.TABLE_CONSTRAINTS;
   }
}
