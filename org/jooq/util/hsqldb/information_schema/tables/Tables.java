package org.jooq.util.hsqldb.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.hsqldb.information_schema.InformationSchema;

public class Tables extends TableImpl<Record> {
   private static final long serialVersionUID = -1661673658L;
   public static final Tables TABLES = new Tables();
   public final TableField<Record, String> TABLE_CATALOG;
   public final TableField<Record, String> TABLE_SCHEMA;
   public final TableField<Record, String> TABLE_NAME;
   public final TableField<Record, String> TABLE_TYPE;
   public final TableField<Record, String> SELF_REFERENCING_COLUMN_NAME;
   public final TableField<Record, String> REFERENCE_GENERATION;
   public final TableField<Record, String> USER_DEFINED_TYPE_CATALOG;
   public final TableField<Record, String> USER_DEFINED_TYPE_SCHEMA;
   public final TableField<Record, String> USER_DEFINED_TYPE_NAME;
   public final TableField<Record, String> IS_INSERTABLE_INTO;
   public final TableField<Record, String> IS_TYPED;
   public final TableField<Record, String> COMMIT_ACTION;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Tables() {
      this("TABLES", (Table)null);
   }

   public Tables(String alias) {
      this(alias, TABLES);
   }

   private Tables(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Tables(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.TABLE_CATALOG = createField("TABLE_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.TABLE_SCHEMA = createField("TABLE_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.TABLE_NAME = createField("TABLE_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.TABLE_TYPE = createField("TABLE_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.SELF_REFERENCING_COLUMN_NAME = createField("SELF_REFERENCING_COLUMN_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.REFERENCE_GENERATION = createField("REFERENCE_GENERATION", SQLDataType.VARCHAR.length(65536), this, "");
      this.USER_DEFINED_TYPE_CATALOG = createField("USER_DEFINED_TYPE_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.USER_DEFINED_TYPE_SCHEMA = createField("USER_DEFINED_TYPE_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.USER_DEFINED_TYPE_NAME = createField("USER_DEFINED_TYPE_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.IS_INSERTABLE_INTO = createField("IS_INSERTABLE_INTO", SQLDataType.VARCHAR.length(3), this, "");
      this.IS_TYPED = createField("IS_TYPED", SQLDataType.VARCHAR.length(3), this, "");
      this.COMMIT_ACTION = createField("COMMIT_ACTION", SQLDataType.VARCHAR.length(65536), this, "");
   }

   public Tables as(String alias) {
      return new Tables(alias, this);
   }

   public Tables rename(String name) {
      return new Tables(name, (Table)null);
   }
}
