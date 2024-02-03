package org.jooq.util.hsqldb.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.hsqldb.information_schema.InformationSchema;

public class TableConstraints extends TableImpl<Record> {
   private static final long serialVersionUID = 1846850405L;
   public static final TableConstraints TABLE_CONSTRAINTS = new TableConstraints();
   public final TableField<Record, String> CONSTRAINT_CATALOG;
   public final TableField<Record, String> CONSTRAINT_SCHEMA;
   public final TableField<Record, String> CONSTRAINT_NAME;
   public final TableField<Record, String> CONSTRAINT_TYPE;
   public final TableField<Record, String> TABLE_CATALOG;
   public final TableField<Record, String> TABLE_SCHEMA;
   public final TableField<Record, String> TABLE_NAME;
   public final TableField<Record, String> IS_DEFERRABLE;
   public final TableField<Record, String> INITIALLY_DEFERRED;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public TableConstraints() {
      this("TABLE_CONSTRAINTS", (Table)null);
   }

   public TableConstraints(String alias) {
      this(alias, TABLE_CONSTRAINTS);
   }

   private TableConstraints(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private TableConstraints(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.CONSTRAINT_CATALOG = createField("CONSTRAINT_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.CONSTRAINT_SCHEMA = createField("CONSTRAINT_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.CONSTRAINT_NAME = createField("CONSTRAINT_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.CONSTRAINT_TYPE = createField("CONSTRAINT_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.TABLE_CATALOG = createField("TABLE_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.TABLE_SCHEMA = createField("TABLE_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.TABLE_NAME = createField("TABLE_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.IS_DEFERRABLE = createField("IS_DEFERRABLE", SQLDataType.VARCHAR.length(3), this, "");
      this.INITIALLY_DEFERRED = createField("INITIALLY_DEFERRED", SQLDataType.VARCHAR.length(3), this, "");
   }

   public TableConstraints as(String alias) {
      return new TableConstraints(alias, this);
   }

   public TableConstraints rename(String name) {
      return new TableConstraints(name, (Table)null);
   }
}
