package org.jooq.util.mysql.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.types.ULong;
import org.jooq.util.mysql.information_schema.InformationSchema;

public class Columns extends TableImpl<Record> {
   private static final long serialVersionUID = -1192516157L;
   public static final Columns COLUMNS = new Columns();
   public static final TableField<Record, String> TABLE_CATALOG;
   public static final TableField<Record, String> TABLE_SCHEMA;
   public static final TableField<Record, String> TABLE_NAME;
   public static final TableField<Record, String> COLUMN_NAME;
   public static final TableField<Record, ULong> ORDINAL_POSITION;
   public static final TableField<Record, String> COLUMN_DEFAULT;
   public static final TableField<Record, String> IS_NULLABLE;
   public static final TableField<Record, String> DATA_TYPE;
   public static final TableField<Record, ULong> CHARACTER_MAXIMUM_LENGTH;
   public static final TableField<Record, ULong> CHARACTER_OCTET_LENGTH;
   public static final TableField<Record, ULong> NUMERIC_PRECISION;
   public static final TableField<Record, ULong> NUMERIC_SCALE;
   public static final TableField<Record, String> CHARACTER_SET_NAME;
   public static final TableField<Record, String> COLLATION_NAME;
   public static final TableField<Record, String> COLUMN_TYPE;
   public static final TableField<Record, String> COLUMN_KEY;
   public static final TableField<Record, String> EXTRA;
   public static final TableField<Record, String> PRIVILEGES;
   public static final TableField<Record, String> COLUMN_COMMENT;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Columns() {
      this("COLUMNS", (Table)null);
   }

   private Columns(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Columns(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
   }

   static {
      TABLE_CATALOG = createField("TABLE_CATALOG", SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), COLUMNS, "");
      TABLE_SCHEMA = createField("TABLE_SCHEMA", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), COLUMNS, "");
      TABLE_NAME = createField("TABLE_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), COLUMNS, "");
      COLUMN_NAME = createField("COLUMN_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), COLUMNS, "");
      ORDINAL_POSITION = createField("ORDINAL_POSITION", SQLDataType.BIGINTUNSIGNED.nullable(false).defaulted(true), COLUMNS, "");
      COLUMN_DEFAULT = createField("COLUMN_DEFAULT", SQLDataType.CLOB, COLUMNS, "");
      IS_NULLABLE = createField("IS_NULLABLE", SQLDataType.VARCHAR.length(3).nullable(false).defaulted(true), COLUMNS, "");
      DATA_TYPE = createField("DATA_TYPE", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), COLUMNS, "");
      CHARACTER_MAXIMUM_LENGTH = createField("CHARACTER_MAXIMUM_LENGTH", SQLDataType.BIGINTUNSIGNED, COLUMNS, "");
      CHARACTER_OCTET_LENGTH = createField("CHARACTER_OCTET_LENGTH", SQLDataType.BIGINTUNSIGNED, COLUMNS, "");
      NUMERIC_PRECISION = createField("NUMERIC_PRECISION", SQLDataType.BIGINTUNSIGNED, COLUMNS, "");
      NUMERIC_SCALE = createField("NUMERIC_SCALE", SQLDataType.BIGINTUNSIGNED, COLUMNS, "");
      CHARACTER_SET_NAME = createField("CHARACTER_SET_NAME", SQLDataType.VARCHAR.length(32), COLUMNS, "");
      COLLATION_NAME = createField("COLLATION_NAME", SQLDataType.VARCHAR.length(32), COLUMNS, "");
      COLUMN_TYPE = createField("COLUMN_TYPE", SQLDataType.CLOB.nullable(false), COLUMNS, "");
      COLUMN_KEY = createField("COLUMN_KEY", SQLDataType.VARCHAR.length(3).nullable(false).defaulted(true), COLUMNS, "");
      EXTRA = createField("EXTRA", SQLDataType.VARCHAR.length(27).nullable(false).defaulted(true), COLUMNS, "");
      PRIVILEGES = createField("PRIVILEGES", SQLDataType.VARCHAR.length(80).nullable(false).defaulted(true), COLUMNS, "");
      COLUMN_COMMENT = createField("COLUMN_COMMENT", SQLDataType.VARCHAR.length(1024).nullable(false).defaulted(true), COLUMNS, "");
   }
}
