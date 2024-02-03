package org.jooq.util.mysql.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.mysql.information_schema.InformationSchema;

public class Statistics extends TableImpl<Record> {
   private static final long serialVersionUID = 357430055L;
   public static final Statistics STATISTICS = new Statistics();
   public static final TableField<Record, String> TABLE_CATALOG;
   public static final TableField<Record, String> TABLE_SCHEMA;
   public static final TableField<Record, String> TABLE_NAME;
   public static final TableField<Record, Long> NON_UNIQUE;
   public static final TableField<Record, String> INDEX_SCHEMA;
   public static final TableField<Record, String> INDEX_NAME;
   public static final TableField<Record, Long> SEQ_IN_INDEX;
   public static final TableField<Record, String> COLUMN_NAME;
   public static final TableField<Record, String> COLLATION;
   public static final TableField<Record, Long> CARDINALITY;
   public static final TableField<Record, Long> SUB_PART;
   public static final TableField<Record, String> PACKED;
   public static final TableField<Record, String> NULLABLE;
   public static final TableField<Record, String> INDEX_TYPE;
   public static final TableField<Record, String> COMMENT;
   public static final TableField<Record, String> INDEX_COMMENT;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Statistics() {
      this("STATISTICS", (Table)null);
   }

   private Statistics(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Statistics(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
   }

   static {
      TABLE_CATALOG = createField("TABLE_CATALOG", SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), STATISTICS, "");
      TABLE_SCHEMA = createField("TABLE_SCHEMA", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), STATISTICS, "");
      TABLE_NAME = createField("TABLE_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), STATISTICS, "");
      NON_UNIQUE = createField("NON_UNIQUE", SQLDataType.BIGINT.nullable(false).defaulted(true), STATISTICS, "");
      INDEX_SCHEMA = createField("INDEX_SCHEMA", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), STATISTICS, "");
      INDEX_NAME = createField("INDEX_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), STATISTICS, "");
      SEQ_IN_INDEX = createField("SEQ_IN_INDEX", SQLDataType.BIGINT.nullable(false).defaulted(true), STATISTICS, "");
      COLUMN_NAME = createField("COLUMN_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), STATISTICS, "");
      COLLATION = createField("COLLATION", SQLDataType.VARCHAR.length(1), STATISTICS, "");
      CARDINALITY = createField("CARDINALITY", SQLDataType.BIGINT, STATISTICS, "");
      SUB_PART = createField("SUB_PART", SQLDataType.BIGINT, STATISTICS, "");
      PACKED = createField("PACKED", SQLDataType.VARCHAR.length(10), STATISTICS, "");
      NULLABLE = createField("NULLABLE", SQLDataType.VARCHAR.length(3).nullable(false).defaulted(true), STATISTICS, "");
      INDEX_TYPE = createField("INDEX_TYPE", SQLDataType.VARCHAR.length(16).nullable(false).defaulted(true), STATISTICS, "");
      COMMENT = createField("COMMENT", SQLDataType.VARCHAR.length(16), STATISTICS, "");
      INDEX_COMMENT = createField("INDEX_COMMENT", SQLDataType.VARCHAR.length(1024).nullable(false).defaulted(true), STATISTICS, "");
   }
}
