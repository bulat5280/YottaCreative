package org.jooq.util.h2.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.h2.information_schema.InformationSchema;

public class Tables extends TableImpl<Record> {
   private static final long serialVersionUID = -1494601101L;
   public static final Tables TABLES = new Tables();
   public static final TableField<Record, String> TABLE_CATALOG;
   public static final TableField<Record, String> TABLE_SCHEMA;
   public static final TableField<Record, String> TABLE_NAME;
   public static final TableField<Record, String> TABLE_TYPE;
   public static final TableField<Record, String> STORAGE_TYPE;
   public static final TableField<Record, String> SQL;
   public static final TableField<Record, String> REMARKS;
   public static final TableField<Record, Long> LAST_MODIFICATION;
   public static final TableField<Record, Integer> ID;
   public static final TableField<Record, String> TYPE_NAME;
   public static final TableField<Record, String> TABLE_CLASS;
   public static final TableField<Record, Long> ROW_COUNT_ESTIMATE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Tables() {
      this("TABLES", (Table)null);
   }

   private Tables(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Tables(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
   }

   static {
      TABLE_CATALOG = createField("TABLE_CATALOG", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), TABLES, "");
      TABLE_SCHEMA = createField("TABLE_SCHEMA", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), TABLES, "");
      TABLE_NAME = createField("TABLE_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), TABLES, "");
      TABLE_TYPE = createField("TABLE_TYPE", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), TABLES, "");
      STORAGE_TYPE = createField("STORAGE_TYPE", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), TABLES, "");
      SQL = createField("SQL", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), TABLES, "");
      REMARKS = createField("REMARKS", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), TABLES, "");
      LAST_MODIFICATION = createField("LAST_MODIFICATION", SQLDataType.BIGINT, TABLES, "");
      ID = createField("ID", SQLDataType.INTEGER, TABLES, "");
      TYPE_NAME = createField("TYPE_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), TABLES, "");
      TABLE_CLASS = createField("TABLE_CLASS", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), TABLES, "");
      ROW_COUNT_ESTIMATE = createField("ROW_COUNT_ESTIMATE", SQLDataType.BIGINT, TABLES, "");
   }
}
