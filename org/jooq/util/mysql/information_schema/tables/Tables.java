package org.jooq.util.mysql.information_schema.tables;

import java.sql.Timestamp;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.types.ULong;
import org.jooq.util.mysql.information_schema.InformationSchema;

public class Tables extends TableImpl<Record> {
   private static final long serialVersionUID = 1728740650L;
   public static final Tables TABLES = new Tables();
   public static final TableField<Record, String> TABLE_CATALOG;
   public static final TableField<Record, String> TABLE_SCHEMA;
   public static final TableField<Record, String> TABLE_NAME;
   public static final TableField<Record, String> TABLE_TYPE;
   public static final TableField<Record, String> ENGINE;
   public static final TableField<Record, ULong> VERSION;
   public static final TableField<Record, String> ROW_FORMAT;
   public static final TableField<Record, ULong> TABLE_ROWS;
   public static final TableField<Record, ULong> AVG_ROW_LENGTH;
   public static final TableField<Record, ULong> DATA_LENGTH;
   public static final TableField<Record, ULong> MAX_DATA_LENGTH;
   public static final TableField<Record, ULong> INDEX_LENGTH;
   public static final TableField<Record, ULong> DATA_FREE;
   public static final TableField<Record, ULong> AUTO_INCREMENT;
   public static final TableField<Record, Timestamp> CREATE_TIME;
   public static final TableField<Record, Timestamp> UPDATE_TIME;
   public static final TableField<Record, Timestamp> CHECK_TIME;
   public static final TableField<Record, String> TABLE_COLLATION;
   public static final TableField<Record, ULong> CHECKSUM;
   public static final TableField<Record, String> CREATE_OPTIONS;
   public static final TableField<Record, String> TABLE_COMMENT;

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
      TABLE_CATALOG = createField("TABLE_CATALOG", SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), TABLES, "");
      TABLE_SCHEMA = createField("TABLE_SCHEMA", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), TABLES, "");
      TABLE_NAME = createField("TABLE_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), TABLES, "");
      TABLE_TYPE = createField("TABLE_TYPE", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), TABLES, "");
      ENGINE = createField("ENGINE", SQLDataType.VARCHAR.length(64), TABLES, "");
      VERSION = createField("VERSION", SQLDataType.BIGINTUNSIGNED, TABLES, "");
      ROW_FORMAT = createField("ROW_FORMAT", SQLDataType.VARCHAR.length(10), TABLES, "");
      TABLE_ROWS = createField("TABLE_ROWS", SQLDataType.BIGINTUNSIGNED, TABLES, "");
      AVG_ROW_LENGTH = createField("AVG_ROW_LENGTH", SQLDataType.BIGINTUNSIGNED, TABLES, "");
      DATA_LENGTH = createField("DATA_LENGTH", SQLDataType.BIGINTUNSIGNED, TABLES, "");
      MAX_DATA_LENGTH = createField("MAX_DATA_LENGTH", SQLDataType.BIGINTUNSIGNED, TABLES, "");
      INDEX_LENGTH = createField("INDEX_LENGTH", SQLDataType.BIGINTUNSIGNED, TABLES, "");
      DATA_FREE = createField("DATA_FREE", SQLDataType.BIGINTUNSIGNED, TABLES, "");
      AUTO_INCREMENT = createField("AUTO_INCREMENT", SQLDataType.BIGINTUNSIGNED, TABLES, "");
      CREATE_TIME = createField("CREATE_TIME", SQLDataType.TIMESTAMP, TABLES, "");
      UPDATE_TIME = createField("UPDATE_TIME", SQLDataType.TIMESTAMP, TABLES, "");
      CHECK_TIME = createField("CHECK_TIME", SQLDataType.TIMESTAMP, TABLES, "");
      TABLE_COLLATION = createField("TABLE_COLLATION", SQLDataType.VARCHAR.length(32), TABLES, "");
      CHECKSUM = createField("CHECKSUM", SQLDataType.BIGINTUNSIGNED, TABLES, "");
      CREATE_OPTIONS = createField("CREATE_OPTIONS", SQLDataType.VARCHAR.length(255), TABLES, "");
      TABLE_COMMENT = createField("TABLE_COMMENT", SQLDataType.VARCHAR.length(2048).nullable(false).defaulted(true), TABLES, "");
   }
}
