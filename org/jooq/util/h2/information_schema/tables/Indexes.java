package org.jooq.util.h2.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.h2.information_schema.InformationSchema;

public class Indexes extends TableImpl<Record> {
   private static final long serialVersionUID = -277122397L;
   public static final Indexes INDEXES = new Indexes();
   public static final TableField<Record, String> TABLE_CATALOG;
   public static final TableField<Record, String> TABLE_SCHEMA;
   public static final TableField<Record, String> TABLE_NAME;
   public static final TableField<Record, Boolean> NON_UNIQUE;
   public static final TableField<Record, String> INDEX_NAME;
   public static final TableField<Record, Short> ORDINAL_POSITION;
   public static final TableField<Record, String> COLUMN_NAME;
   public static final TableField<Record, Integer> CARDINALITY;
   public static final TableField<Record, Boolean> PRIMARY_KEY;
   public static final TableField<Record, String> INDEX_TYPE_NAME;
   public static final TableField<Record, Boolean> IS_GENERATED;
   public static final TableField<Record, Short> INDEX_TYPE;
   public static final TableField<Record, String> ASC_OR_DESC;
   public static final TableField<Record, Integer> PAGES;
   public static final TableField<Record, String> FILTER_CONDITION;
   public static final TableField<Record, String> REMARKS;
   public static final TableField<Record, String> SQL;
   public static final TableField<Record, Integer> ID;
   public static final TableField<Record, Integer> SORT_TYPE;
   public static final TableField<Record, String> CONSTRAINT_NAME;
   public static final TableField<Record, String> INDEX_CLASS;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Indexes() {
      this("INDEXES", (Table)null);
   }

   private Indexes(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Indexes(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
   }

   static {
      TABLE_CATALOG = createField("TABLE_CATALOG", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), INDEXES, "");
      TABLE_SCHEMA = createField("TABLE_SCHEMA", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), INDEXES, "");
      TABLE_NAME = createField("TABLE_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), INDEXES, "");
      NON_UNIQUE = createField("NON_UNIQUE", SQLDataType.BOOLEAN, INDEXES, "");
      INDEX_NAME = createField("INDEX_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), INDEXES, "");
      ORDINAL_POSITION = createField("ORDINAL_POSITION", SQLDataType.SMALLINT, INDEXES, "");
      COLUMN_NAME = createField("COLUMN_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), INDEXES, "");
      CARDINALITY = createField("CARDINALITY", SQLDataType.INTEGER, INDEXES, "");
      PRIMARY_KEY = createField("PRIMARY_KEY", SQLDataType.BOOLEAN, INDEXES, "");
      INDEX_TYPE_NAME = createField("INDEX_TYPE_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), INDEXES, "");
      IS_GENERATED = createField("IS_GENERATED", SQLDataType.BOOLEAN, INDEXES, "");
      INDEX_TYPE = createField("INDEX_TYPE", SQLDataType.SMALLINT, INDEXES, "");
      ASC_OR_DESC = createField("ASC_OR_DESC", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), INDEXES, "");
      PAGES = createField("PAGES", SQLDataType.INTEGER, INDEXES, "");
      FILTER_CONDITION = createField("FILTER_CONDITION", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), INDEXES, "");
      REMARKS = createField("REMARKS", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), INDEXES, "");
      SQL = createField("SQL", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), INDEXES, "");
      ID = createField("ID", SQLDataType.INTEGER, INDEXES, "");
      SORT_TYPE = createField("SORT_TYPE", SQLDataType.INTEGER, INDEXES, "");
      CONSTRAINT_NAME = createField("CONSTRAINT_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), INDEXES, "");
      INDEX_CLASS = createField("INDEX_CLASS", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), INDEXES, "");
   }
}
