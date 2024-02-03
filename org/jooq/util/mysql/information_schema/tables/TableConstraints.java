package org.jooq.util.mysql.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.mysql.information_schema.InformationSchema;

public class TableConstraints extends TableImpl<Record> {
   private static final long serialVersionUID = -1909788577L;
   public static final TableConstraints TABLE_CONSTRAINTS = new TableConstraints();
   public static final TableField<Record, String> CONSTRAINT_CATALOG;
   public static final TableField<Record, String> CONSTRAINT_SCHEMA;
   public static final TableField<Record, String> CONSTRAINT_NAME;
   public static final TableField<Record, String> TABLE_SCHEMA;
   public static final TableField<Record, String> TABLE_NAME;
   public static final TableField<Record, String> CONSTRAINT_TYPE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private TableConstraints() {
      this("TABLE_CONSTRAINTS", (Table)null);
   }

   private TableConstraints(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private TableConstraints(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
   }

   static {
      CONSTRAINT_CATALOG = createField("CONSTRAINT_CATALOG", SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), TABLE_CONSTRAINTS, "");
      CONSTRAINT_SCHEMA = createField("CONSTRAINT_SCHEMA", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), TABLE_CONSTRAINTS, "");
      CONSTRAINT_NAME = createField("CONSTRAINT_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), TABLE_CONSTRAINTS, "");
      TABLE_SCHEMA = createField("TABLE_SCHEMA", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), TABLE_CONSTRAINTS, "");
      TABLE_NAME = createField("TABLE_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), TABLE_CONSTRAINTS, "");
      CONSTRAINT_TYPE = createField("CONSTRAINT_TYPE", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), TABLE_CONSTRAINTS, "");
   }
}
