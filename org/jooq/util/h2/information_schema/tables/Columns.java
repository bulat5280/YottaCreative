package org.jooq.util.h2.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.h2.information_schema.InformationSchema;

public class Columns extends TableImpl<Record> {
   private static final long serialVersionUID = -1092445246L;
   public static final Columns COLUMNS = new Columns();
   public static final TableField<Record, String> TABLE_CATALOG;
   public static final TableField<Record, String> TABLE_SCHEMA;
   public static final TableField<Record, String> TABLE_NAME;
   public static final TableField<Record, String> COLUMN_NAME;
   public static final TableField<Record, Integer> ORDINAL_POSITION;
   public static final TableField<Record, String> COLUMN_DEFAULT;
   public static final TableField<Record, String> IS_NULLABLE;
   public static final TableField<Record, Integer> DATA_TYPE;
   public static final TableField<Record, Integer> CHARACTER_MAXIMUM_LENGTH;
   public static final TableField<Record, Integer> CHARACTER_OCTET_LENGTH;
   public static final TableField<Record, Integer> NUMERIC_PRECISION;
   public static final TableField<Record, Integer> NUMERIC_PRECISION_RADIX;
   public static final TableField<Record, Integer> NUMERIC_SCALE;
   public static final TableField<Record, String> CHARACTER_SET_NAME;
   public static final TableField<Record, String> COLLATION_NAME;
   public static final TableField<Record, String> TYPE_NAME;
   public static final TableField<Record, Integer> NULLABLE;
   public static final TableField<Record, Boolean> IS_COMPUTED;
   public static final TableField<Record, Integer> SELECTIVITY;
   public static final TableField<Record, String> CHECK_CONSTRAINT;
   public static final TableField<Record, String> SEQUENCE_NAME;
   public static final TableField<Record, String> REMARKS;
   public static final TableField<Record, Short> SOURCE_DATA_TYPE;

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
      TABLE_CATALOG = createField("TABLE_CATALOG", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), COLUMNS, "");
      TABLE_SCHEMA = createField("TABLE_SCHEMA", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), COLUMNS, "");
      TABLE_NAME = createField("TABLE_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), COLUMNS, "");
      COLUMN_NAME = createField("COLUMN_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), COLUMNS, "");
      ORDINAL_POSITION = createField("ORDINAL_POSITION", SQLDataType.INTEGER, COLUMNS, "");
      COLUMN_DEFAULT = createField("COLUMN_DEFAULT", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), COLUMNS, "");
      IS_NULLABLE = createField("IS_NULLABLE", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), COLUMNS, "");
      DATA_TYPE = createField("DATA_TYPE", SQLDataType.INTEGER, COLUMNS, "");
      CHARACTER_MAXIMUM_LENGTH = createField("CHARACTER_MAXIMUM_LENGTH", SQLDataType.INTEGER, COLUMNS, "");
      CHARACTER_OCTET_LENGTH = createField("CHARACTER_OCTET_LENGTH", SQLDataType.INTEGER, COLUMNS, "");
      NUMERIC_PRECISION = createField("NUMERIC_PRECISION", SQLDataType.INTEGER, COLUMNS, "");
      NUMERIC_PRECISION_RADIX = createField("NUMERIC_PRECISION_RADIX", SQLDataType.INTEGER, COLUMNS, "");
      NUMERIC_SCALE = createField("NUMERIC_SCALE", SQLDataType.INTEGER, COLUMNS, "");
      CHARACTER_SET_NAME = createField("CHARACTER_SET_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), COLUMNS, "");
      COLLATION_NAME = createField("COLLATION_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), COLUMNS, "");
      TYPE_NAME = createField("TYPE_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), COLUMNS, "");
      NULLABLE = createField("NULLABLE", SQLDataType.INTEGER, COLUMNS, "");
      IS_COMPUTED = createField("IS_COMPUTED", SQLDataType.BOOLEAN, COLUMNS, "");
      SELECTIVITY = createField("SELECTIVITY", SQLDataType.INTEGER, COLUMNS, "");
      CHECK_CONSTRAINT = createField("CHECK_CONSTRAINT", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), COLUMNS, "");
      SEQUENCE_NAME = createField("SEQUENCE_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), COLUMNS, "");
      REMARKS = createField("REMARKS", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), COLUMNS, "");
      SOURCE_DATA_TYPE = createField("SOURCE_DATA_TYPE", SQLDataType.SMALLINT, COLUMNS, "");
   }
}
