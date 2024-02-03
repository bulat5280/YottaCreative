package org.jooq.util.h2.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.h2.information_schema.InformationSchema;

public class FunctionAliases extends TableImpl<Record> {
   private static final long serialVersionUID = -288287044L;
   public static final FunctionAliases FUNCTION_ALIASES = new FunctionAliases();
   public static final TableField<Record, String> ALIAS_CATALOG;
   public static final TableField<Record, String> ALIAS_SCHEMA;
   public static final TableField<Record, String> ALIAS_NAME;
   public static final TableField<Record, String> JAVA_CLASS;
   public static final TableField<Record, String> JAVA_METHOD;
   public static final TableField<Record, Integer> DATA_TYPE;
   public static final TableField<Record, String> TYPE_NAME;
   public static final TableField<Record, Integer> COLUMN_COUNT;
   public static final TableField<Record, Short> RETURNS_RESULT;
   public static final TableField<Record, String> REMARKS;
   public static final TableField<Record, Integer> ID;
   public static final TableField<Record, String> SOURCE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private FunctionAliases() {
      this("FUNCTION_ALIASES", (Table)null);
   }

   private FunctionAliases(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private FunctionAliases(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
   }

   static {
      ALIAS_CATALOG = createField("ALIAS_CATALOG", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_ALIASES, "");
      ALIAS_SCHEMA = createField("ALIAS_SCHEMA", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_ALIASES, "");
      ALIAS_NAME = createField("ALIAS_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_ALIASES, "");
      JAVA_CLASS = createField("JAVA_CLASS", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_ALIASES, "");
      JAVA_METHOD = createField("JAVA_METHOD", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_ALIASES, "");
      DATA_TYPE = createField("DATA_TYPE", SQLDataType.INTEGER, FUNCTION_ALIASES, "");
      TYPE_NAME = createField("TYPE_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_ALIASES, "");
      COLUMN_COUNT = createField("COLUMN_COUNT", SQLDataType.INTEGER, FUNCTION_ALIASES, "");
      RETURNS_RESULT = createField("RETURNS_RESULT", SQLDataType.SMALLINT, FUNCTION_ALIASES, "");
      REMARKS = createField("REMARKS", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_ALIASES, "");
      ID = createField("ID", SQLDataType.INTEGER, FUNCTION_ALIASES, "");
      SOURCE = createField("SOURCE", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), FUNCTION_ALIASES, "");
   }
}
