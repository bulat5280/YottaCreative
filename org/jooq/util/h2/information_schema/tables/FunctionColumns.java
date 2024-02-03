package org.jooq.util.h2.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.h2.information_schema.InformationSchema;

public class FunctionColumns extends TableImpl<Record> {
   private static final long serialVersionUID = 953355853L;
   public static final FunctionColumns FUNCTION_COLUMNS = new FunctionColumns();
   public static final TableField<Record, String> ALIAS_CATALOG;
   public static final TableField<Record, String> ALIAS_SCHEMA;
   public static final TableField<Record, String> ALIAS_NAME;
   public static final TableField<Record, String> JAVA_CLASS;
   public static final TableField<Record, String> JAVA_METHOD;
   public static final TableField<Record, Integer> COLUMN_COUNT;
   public static final TableField<Record, Integer> POS;
   public static final TableField<Record, String> COLUMN_NAME;
   public static final TableField<Record, Integer> DATA_TYPE;
   public static final TableField<Record, String> TYPE_NAME;
   public static final TableField<Record, Integer> PRECISION;
   public static final TableField<Record, Short> SCALE;
   public static final TableField<Record, Short> RADIX;
   public static final TableField<Record, Short> NULLABLE;
   public static final TableField<Record, Short> COLUMN_TYPE;
   public static final TableField<Record, String> REMARKS;
   public static final TableField<Record, String> COLUMN_DEFAULT;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private FunctionColumns() {
      this("FUNCTION_COLUMNS", (Table)null);
   }

   private FunctionColumns(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private FunctionColumns(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
   }

   static {
      ALIAS_CATALOG = createField("ALIAS_CATALOG", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_COLUMNS, "");
      ALIAS_SCHEMA = createField("ALIAS_SCHEMA", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_COLUMNS, "");
      ALIAS_NAME = createField("ALIAS_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_COLUMNS, "");
      JAVA_CLASS = createField("JAVA_CLASS", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_COLUMNS, "");
      JAVA_METHOD = createField("JAVA_METHOD", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_COLUMNS, "");
      COLUMN_COUNT = createField("COLUMN_COUNT", SQLDataType.INTEGER, FUNCTION_COLUMNS, "");
      POS = createField("POS", SQLDataType.INTEGER, FUNCTION_COLUMNS, "");
      COLUMN_NAME = createField("COLUMN_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_COLUMNS, "");
      DATA_TYPE = createField("DATA_TYPE", SQLDataType.INTEGER, FUNCTION_COLUMNS, "");
      TYPE_NAME = createField("TYPE_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_COLUMNS, "");
      PRECISION = createField("PRECISION", SQLDataType.INTEGER, FUNCTION_COLUMNS, "");
      SCALE = createField("SCALE", SQLDataType.SMALLINT, FUNCTION_COLUMNS, "");
      RADIX = createField("RADIX", SQLDataType.SMALLINT, FUNCTION_COLUMNS, "");
      NULLABLE = createField("NULLABLE", SQLDataType.SMALLINT, FUNCTION_COLUMNS, "");
      COLUMN_TYPE = createField("COLUMN_TYPE", SQLDataType.SMALLINT, FUNCTION_COLUMNS, "");
      REMARKS = createField("REMARKS", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_COLUMNS, "");
      COLUMN_DEFAULT = createField("COLUMN_DEFAULT", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_COLUMNS, "");
   }
}
