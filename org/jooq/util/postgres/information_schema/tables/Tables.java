package org.jooq.util.postgres.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.information_schema.InformationSchema;

public class Tables extends TableImpl<Record> {
   private static final long serialVersionUID = 1600719780L;
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
      this("tables", (Table)null);
   }

   public Tables(String alias) {
      this(alias, TABLES);
   }

   private Tables(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Tables(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.TABLE_CATALOG = createField("table_catalog", SQLDataType.VARCHAR, this, "");
      this.TABLE_SCHEMA = createField("table_schema", SQLDataType.VARCHAR, this, "");
      this.TABLE_NAME = createField("table_name", SQLDataType.VARCHAR, this, "");
      this.TABLE_TYPE = createField("table_type", SQLDataType.VARCHAR, this, "");
      this.SELF_REFERENCING_COLUMN_NAME = createField("self_referencing_column_name", SQLDataType.VARCHAR, this, "");
      this.REFERENCE_GENERATION = createField("reference_generation", SQLDataType.VARCHAR, this, "");
      this.USER_DEFINED_TYPE_CATALOG = createField("user_defined_type_catalog", SQLDataType.VARCHAR, this, "");
      this.USER_DEFINED_TYPE_SCHEMA = createField("user_defined_type_schema", SQLDataType.VARCHAR, this, "");
      this.USER_DEFINED_TYPE_NAME = createField("user_defined_type_name", SQLDataType.VARCHAR, this, "");
      this.IS_INSERTABLE_INTO = createField("is_insertable_into", SQLDataType.VARCHAR.length(3), this, "");
      this.IS_TYPED = createField("is_typed", SQLDataType.VARCHAR.length(3), this, "");
      this.COMMIT_ACTION = createField("commit_action", SQLDataType.VARCHAR, this, "");
   }

   public Tables as(String alias) {
      return new Tables(alias, this);
   }

   public Tables rename(String name) {
      return new Tables(name, (Table)null);
   }
}
