package org.jooq.util.mysql.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.mysql.information_schema.InformationSchema;

public class KeyColumnUsage extends TableImpl<Record> {
   private static final long serialVersionUID = -669574892L;
   public static final KeyColumnUsage KEY_COLUMN_USAGE = new KeyColumnUsage();
   public static final TableField<Record, String> CONSTRAINT_CATALOG;
   public static final TableField<Record, String> CONSTRAINT_SCHEMA;
   public static final TableField<Record, String> CONSTRAINT_NAME;
   public static final TableField<Record, String> TABLE_CATALOG;
   public static final TableField<Record, String> TABLE_SCHEMA;
   public static final TableField<Record, String> TABLE_NAME;
   public static final TableField<Record, String> COLUMN_NAME;
   public static final TableField<Record, Long> ORDINAL_POSITION;
   public static final TableField<Record, Long> POSITION_IN_UNIQUE_CONSTRAINT;
   public static final TableField<Record, String> REFERENCED_TABLE_SCHEMA;
   public static final TableField<Record, String> REFERENCED_TABLE_NAME;
   public static final TableField<Record, String> REFERENCED_COLUMN_NAME;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private KeyColumnUsage() {
      this("KEY_COLUMN_USAGE", (Table)null);
   }

   private KeyColumnUsage(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private KeyColumnUsage(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
   }

   static {
      CONSTRAINT_CATALOG = createField("CONSTRAINT_CATALOG", SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), KEY_COLUMN_USAGE, "");
      CONSTRAINT_SCHEMA = createField("CONSTRAINT_SCHEMA", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), KEY_COLUMN_USAGE, "");
      CONSTRAINT_NAME = createField("CONSTRAINT_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), KEY_COLUMN_USAGE, "");
      TABLE_CATALOG = createField("TABLE_CATALOG", SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), KEY_COLUMN_USAGE, "");
      TABLE_SCHEMA = createField("TABLE_SCHEMA", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), KEY_COLUMN_USAGE, "");
      TABLE_NAME = createField("TABLE_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), KEY_COLUMN_USAGE, "");
      COLUMN_NAME = createField("COLUMN_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), KEY_COLUMN_USAGE, "");
      ORDINAL_POSITION = createField("ORDINAL_POSITION", SQLDataType.BIGINT.nullable(false).defaulted(true), KEY_COLUMN_USAGE, "");
      POSITION_IN_UNIQUE_CONSTRAINT = createField("POSITION_IN_UNIQUE_CONSTRAINT", SQLDataType.BIGINT, KEY_COLUMN_USAGE, "");
      REFERENCED_TABLE_SCHEMA = createField("REFERENCED_TABLE_SCHEMA", SQLDataType.VARCHAR.length(64), KEY_COLUMN_USAGE, "");
      REFERENCED_TABLE_NAME = createField("REFERENCED_TABLE_NAME", SQLDataType.VARCHAR.length(64), KEY_COLUMN_USAGE, "");
      REFERENCED_COLUMN_NAME = createField("REFERENCED_COLUMN_NAME", SQLDataType.VARCHAR.length(64), KEY_COLUMN_USAGE, "");
   }
}
