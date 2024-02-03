package org.jooq.util.h2.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.h2.information_schema.InformationSchema;

public class Constraints extends TableImpl<Record> {
   private static final long serialVersionUID = 1624133436L;
   public static final Constraints CONSTRAINTS = new Constraints();
   public static final TableField<Record, String> CONSTRAINT_CATALOG;
   public static final TableField<Record, String> CONSTRAINT_SCHEMA;
   public static final TableField<Record, String> CONSTRAINT_NAME;
   public static final TableField<Record, String> CONSTRAINT_TYPE;
   public static final TableField<Record, String> TABLE_CATALOG;
   public static final TableField<Record, String> TABLE_SCHEMA;
   public static final TableField<Record, String> TABLE_NAME;
   public static final TableField<Record, String> UNIQUE_INDEX_NAME;
   public static final TableField<Record, String> CHECK_EXPRESSION;
   public static final TableField<Record, String> COLUMN_LIST;
   public static final TableField<Record, String> REMARKS;
   public static final TableField<Record, String> SQL;
   public static final TableField<Record, Integer> ID;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Constraints() {
      this("CONSTRAINTS", (Table)null);
   }

   private Constraints(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Constraints(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
   }

   static {
      CONSTRAINT_CATALOG = createField("CONSTRAINT_CATALOG", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CONSTRAINTS, "");
      CONSTRAINT_SCHEMA = createField("CONSTRAINT_SCHEMA", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CONSTRAINTS, "");
      CONSTRAINT_NAME = createField("CONSTRAINT_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CONSTRAINTS, "");
      CONSTRAINT_TYPE = createField("CONSTRAINT_TYPE", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CONSTRAINTS, "");
      TABLE_CATALOG = createField("TABLE_CATALOG", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CONSTRAINTS, "");
      TABLE_SCHEMA = createField("TABLE_SCHEMA", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CONSTRAINTS, "");
      TABLE_NAME = createField("TABLE_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CONSTRAINTS, "");
      UNIQUE_INDEX_NAME = createField("UNIQUE_INDEX_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CONSTRAINTS, "");
      CHECK_EXPRESSION = createField("CHECK_EXPRESSION", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CONSTRAINTS, "");
      COLUMN_LIST = createField("COLUMN_LIST", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CONSTRAINTS, "");
      REMARKS = createField("REMARKS", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CONSTRAINTS, "");
      SQL = createField("SQL", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CONSTRAINTS, "");
      ID = createField("ID", SQLDataType.INTEGER, CONSTRAINTS, "");
   }
}
