package org.jooq.util.postgres.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.information_schema.InformationSchema;

public class ConstraintColumnUsage extends TableImpl<Record> {
   private static final long serialVersionUID = -1833497305L;
   public static final ConstraintColumnUsage CONSTRAINT_COLUMN_USAGE = new ConstraintColumnUsage();
   public final TableField<Record, String> TABLE_CATALOG;
   public final TableField<Record, String> TABLE_SCHEMA;
   public final TableField<Record, String> TABLE_NAME;
   public final TableField<Record, String> COLUMN_NAME;
   public final TableField<Record, String> CONSTRAINT_CATALOG;
   public final TableField<Record, String> CONSTRAINT_SCHEMA;
   public final TableField<Record, String> CONSTRAINT_NAME;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public ConstraintColumnUsage() {
      this("constraint_column_usage", (Table)null);
   }

   public ConstraintColumnUsage(String alias) {
      this(alias, CONSTRAINT_COLUMN_USAGE);
   }

   private ConstraintColumnUsage(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private ConstraintColumnUsage(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.TABLE_CATALOG = createField("table_catalog", SQLDataType.VARCHAR, this, "");
      this.TABLE_SCHEMA = createField("table_schema", SQLDataType.VARCHAR, this, "");
      this.TABLE_NAME = createField("table_name", SQLDataType.VARCHAR, this, "");
      this.COLUMN_NAME = createField("column_name", SQLDataType.VARCHAR, this, "");
      this.CONSTRAINT_CATALOG = createField("constraint_catalog", SQLDataType.VARCHAR, this, "");
      this.CONSTRAINT_SCHEMA = createField("constraint_schema", SQLDataType.VARCHAR, this, "");
      this.CONSTRAINT_NAME = createField("constraint_name", SQLDataType.VARCHAR, this, "");
   }

   public ConstraintColumnUsage as(String alias) {
      return new ConstraintColumnUsage(alias, this);
   }

   public ConstraintColumnUsage rename(String name) {
      return new ConstraintColumnUsage(name, (Table)null);
   }
}
