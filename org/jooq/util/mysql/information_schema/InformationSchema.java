package org.jooq.util.mysql.information_schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import org.jooq.util.mysql.information_schema.tables.Columns;
import org.jooq.util.mysql.information_schema.tables.KeyColumnUsage;
import org.jooq.util.mysql.information_schema.tables.Parameters;
import org.jooq.util.mysql.information_schema.tables.ReferentialConstraints;
import org.jooq.util.mysql.information_schema.tables.Schemata;
import org.jooq.util.mysql.information_schema.tables.Statistics;
import org.jooq.util.mysql.information_schema.tables.TableConstraints;

public class InformationSchema extends SchemaImpl {
   private static final long serialVersionUID = -679998315L;
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
      return Arrays.asList(Columns.COLUMNS, KeyColumnUsage.KEY_COLUMN_USAGE, Parameters.PARAMETERS, ReferentialConstraints.REFERENTIAL_CONSTRAINTS, Schemata.SCHEMATA, Statistics.STATISTICS, org.jooq.util.mysql.information_schema.tables.Tables.TABLES, TableConstraints.TABLE_CONSTRAINTS);
   }
}
