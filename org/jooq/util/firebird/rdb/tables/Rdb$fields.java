package org.jooq.util.firebird.rdb.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.firebird.rdb.DefaultSchema;

public class Rdb$fields extends TableImpl<Record> {
   private static final long serialVersionUID = 281517159L;
   public static final Rdb$fields RDB$FIELDS = new Rdb$fields();
   public final TableField<Record, String> RDB$FIELD_NAME;
   public final TableField<Record, String> RDB$QUERY_NAME;
   public final TableField<Record, byte[]> RDB$VALIDATION_BLR;
   public final TableField<Record, String> RDB$VALIDATION_SOURCE;
   public final TableField<Record, byte[]> RDB$COMPUTED_BLR;
   public final TableField<Record, String> RDB$COMPUTED_SOURCE;
   public final TableField<Record, byte[]> RDB$DEFAULT_VALUE;
   public final TableField<Record, String> RDB$DEFAULT_SOURCE;
   public final TableField<Record, Short> RDB$FIELD_LENGTH;
   public final TableField<Record, Short> RDB$FIELD_SCALE;
   public final TableField<Record, Short> RDB$FIELD_TYPE;
   public final TableField<Record, Short> RDB$FIELD_SUB_TYPE;
   public final TableField<Record, byte[]> RDB$MISSING_VALUE;
   public final TableField<Record, String> RDB$MISSING_SOURCE;
   public final TableField<Record, String> RDB$DESCRIPTION;
   public final TableField<Record, Short> RDB$SYSTEM_FLAG;
   public final TableField<Record, String> RDB$QUERY_HEADER;
   public final TableField<Record, Short> RDB$SEGMENT_LENGTH;
   public final TableField<Record, String> RDB$EDIT_STRING;
   public final TableField<Record, Short> RDB$EXTERNAL_LENGTH;
   public final TableField<Record, Short> RDB$EXTERNAL_SCALE;
   public final TableField<Record, Short> RDB$EXTERNAL_TYPE;
   public final TableField<Record, Short> RDB$DIMENSIONS;
   public final TableField<Record, Short> RDB$NULL_FLAG;
   public final TableField<Record, Short> RDB$CHARACTER_LENGTH;
   public final TableField<Record, Short> RDB$COLLATION_ID;
   public final TableField<Record, Short> RDB$CHARACTER_SET_ID;
   public final TableField<Record, Short> RDB$FIELD_PRECISION;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Rdb$fields() {
      this("RDB$FIELDS", (Table)null);
   }

   public Rdb$fields(String alias) {
      this(alias, RDB$FIELDS);
   }

   private Rdb$fields(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Rdb$fields(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
      this.RDB$FIELD_NAME = createField("RDB$FIELD_NAME", SQLDataType.CHAR, this, "");
      this.RDB$QUERY_NAME = createField("RDB$QUERY_NAME", SQLDataType.CHAR, this, "");
      this.RDB$VALIDATION_BLR = createField("RDB$VALIDATION_BLR", SQLDataType.BLOB, this, "");
      this.RDB$VALIDATION_SOURCE = createField("RDB$VALIDATION_SOURCE", SQLDataType.CLOB, this, "");
      this.RDB$COMPUTED_BLR = createField("RDB$COMPUTED_BLR", SQLDataType.BLOB, this, "");
      this.RDB$COMPUTED_SOURCE = createField("RDB$COMPUTED_SOURCE", SQLDataType.CLOB, this, "");
      this.RDB$DEFAULT_VALUE = createField("RDB$DEFAULT_VALUE", SQLDataType.BLOB, this, "");
      this.RDB$DEFAULT_SOURCE = createField("RDB$DEFAULT_SOURCE", SQLDataType.CLOB, this, "");
      this.RDB$FIELD_LENGTH = createField("RDB$FIELD_LENGTH", SQLDataType.SMALLINT, this, "");
      this.RDB$FIELD_SCALE = createField("RDB$FIELD_SCALE", SQLDataType.SMALLINT, this, "");
      this.RDB$FIELD_TYPE = createField("RDB$FIELD_TYPE", SQLDataType.SMALLINT, this, "");
      this.RDB$FIELD_SUB_TYPE = createField("RDB$FIELD_SUB_TYPE", SQLDataType.SMALLINT, this, "");
      this.RDB$MISSING_VALUE = createField("RDB$MISSING_VALUE", SQLDataType.BLOB, this, "");
      this.RDB$MISSING_SOURCE = createField("RDB$MISSING_SOURCE", SQLDataType.CLOB, this, "");
      this.RDB$DESCRIPTION = createField("RDB$DESCRIPTION", SQLDataType.CLOB, this, "");
      this.RDB$SYSTEM_FLAG = createField("RDB$SYSTEM_FLAG", SQLDataType.SMALLINT, this, "");
      this.RDB$QUERY_HEADER = createField("RDB$QUERY_HEADER", SQLDataType.CLOB, this, "");
      this.RDB$SEGMENT_LENGTH = createField("RDB$SEGMENT_LENGTH", SQLDataType.SMALLINT, this, "");
      this.RDB$EDIT_STRING = createField("RDB$EDIT_STRING", SQLDataType.VARCHAR, this, "");
      this.RDB$EXTERNAL_LENGTH = createField("RDB$EXTERNAL_LENGTH", SQLDataType.SMALLINT, this, "");
      this.RDB$EXTERNAL_SCALE = createField("RDB$EXTERNAL_SCALE", SQLDataType.SMALLINT, this, "");
      this.RDB$EXTERNAL_TYPE = createField("RDB$EXTERNAL_TYPE", SQLDataType.SMALLINT, this, "");
      this.RDB$DIMENSIONS = createField("RDB$DIMENSIONS", SQLDataType.SMALLINT, this, "");
      this.RDB$NULL_FLAG = createField("RDB$NULL_FLAG", SQLDataType.SMALLINT, this, "");
      this.RDB$CHARACTER_LENGTH = createField("RDB$CHARACTER_LENGTH", SQLDataType.SMALLINT, this, "");
      this.RDB$COLLATION_ID = createField("RDB$COLLATION_ID", SQLDataType.SMALLINT, this, "");
      this.RDB$CHARACTER_SET_ID = createField("RDB$CHARACTER_SET_ID", SQLDataType.SMALLINT, this, "");
      this.RDB$FIELD_PRECISION = createField("RDB$FIELD_PRECISION", SQLDataType.SMALLINT, this, "");
   }

   public Rdb$fields as(String alias) {
      return new Rdb$fields(alias, this);
   }

   public Rdb$fields rename(String name) {
      return new Rdb$fields(name, (Table)null);
   }
}
