package org.jooq.util.postgres.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.information_schema.InformationSchema;

public class KeyColumnUsage extends TableImpl<Record> {
   private static final long serialVersionUID = -319256529L;
   public static final KeyColumnUsage KEY_COLUMN_USAGE = new KeyColumnUsage();
   public final TableField<Record, String> CONSTRAINT_CATALOG;
   public final TableField<Record, String> CONSTRAINT_SCHEMA;
   public final TableField<Record, String> CONSTRAINT_NAME;
   public final TableField<Record, String> TABLE_CATALOG;
   public final TableField<Record, String> TABLE_SCHEMA;
   public final TableField<Record, String> TABLE_NAME;
   public final TableField<Record, String> COLUMN_NAME;
   public final TableField<Record, Integer> ORDINAL_POSITION;
   public final TableField<Record, Integer> POSITION_IN_UNIQUE_CONSTRAINT;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public KeyColumnUsage() {
      this("key_column_usage", (Table)null);
   }

   public KeyColumnUsage(String alias) {
      this(alias, KEY_COLUMN_USAGE);
   }

   private KeyColumnUsage(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private KeyColumnUsage(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.CONSTRAINT_CATALOG = createField("constraint_catalog", SQLDataType.VARCHAR, this, "");
      this.CONSTRAINT_SCHEMA = createField("constraint_schema", SQLDataType.VARCHAR, this, "");
      this.CONSTRAINT_NAME = createField("constraint_name", SQLDataType.VARCHAR, this, "");
      this.TABLE_CATALOG = createField("table_catalog", SQLDataType.VARCHAR, this, "");
      this.TABLE_SCHEMA = createField("table_schema", SQLDataType.VARCHAR, this, "");
      this.TABLE_NAME = createField("table_name", SQLDataType.VARCHAR, this, "");
      this.COLUMN_NAME = createField("column_name", SQLDataType.VARCHAR, this, "");
      this.ORDINAL_POSITION = createField("ordinal_position", SQLDataType.INTEGER, this, "");
      this.POSITION_IN_UNIQUE_CONSTRAINT = createField("position_in_unique_constraint", SQLDataType.INTEGER, this, "");
   }

   public KeyColumnUsage as(String alias) {
      return new KeyColumnUsage(alias, this);
   }

   public KeyColumnUsage rename(String name) {
      return new KeyColumnUsage(name, (Table)null);
   }
}
