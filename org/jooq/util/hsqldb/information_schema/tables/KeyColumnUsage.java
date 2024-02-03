package org.jooq.util.hsqldb.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.hsqldb.information_schema.InformationSchema;

public class KeyColumnUsage extends TableImpl<Record> {
   private static final long serialVersionUID = -1291516054L;
   public static final KeyColumnUsage KEY_COLUMN_USAGE = new KeyColumnUsage();
   public final TableField<Record, String> CONSTRAINT_CATALOG;
   public final TableField<Record, String> CONSTRAINT_SCHEMA;
   public final TableField<Record, String> CONSTRAINT_NAME;
   public final TableField<Record, String> TABLE_CATALOG;
   public final TableField<Record, String> TABLE_SCHEMA;
   public final TableField<Record, String> TABLE_NAME;
   public final TableField<Record, String> COLUMN_NAME;
   public final TableField<Record, Long> ORDINAL_POSITION;
   public final TableField<Record, Long> POSITION_IN_UNIQUE_CONSTRAINT;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public KeyColumnUsage() {
      this("KEY_COLUMN_USAGE", (Table)null);
   }

   public KeyColumnUsage(String alias) {
      this(alias, KEY_COLUMN_USAGE);
   }

   private KeyColumnUsage(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private KeyColumnUsage(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.CONSTRAINT_CATALOG = createField("CONSTRAINT_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.CONSTRAINT_SCHEMA = createField("CONSTRAINT_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.CONSTRAINT_NAME = createField("CONSTRAINT_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.TABLE_CATALOG = createField("TABLE_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.TABLE_SCHEMA = createField("TABLE_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.TABLE_NAME = createField("TABLE_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.COLUMN_NAME = createField("COLUMN_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.ORDINAL_POSITION = createField("ORDINAL_POSITION", SQLDataType.BIGINT, this, "");
      this.POSITION_IN_UNIQUE_CONSTRAINT = createField("POSITION_IN_UNIQUE_CONSTRAINT", SQLDataType.BIGINT, this, "");
   }

   public KeyColumnUsage as(String alias) {
      return new KeyColumnUsage(alias, this);
   }

   public KeyColumnUsage rename(String name) {
      return new KeyColumnUsage(name, (Table)null);
   }
}
