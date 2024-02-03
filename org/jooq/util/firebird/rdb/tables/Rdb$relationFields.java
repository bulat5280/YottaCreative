package org.jooq.util.firebird.rdb.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.firebird.rdb.DefaultSchema;

public class Rdb$relationFields extends TableImpl<Record> {
   private static final long serialVersionUID = -2139580421L;
   public static final Rdb$relationFields RDB$RELATION_FIELDS = new Rdb$relationFields();
   public final TableField<Record, String> RDB$FIELD_NAME;
   public final TableField<Record, String> RDB$RELATION_NAME;
   public final TableField<Record, String> RDB$FIELD_SOURCE;
   public final TableField<Record, String> RDB$QUERY_NAME;
   public final TableField<Record, String> RDB$BASE_FIELD;
   public final TableField<Record, String> RDB$EDIT_STRING;
   public final TableField<Record, Short> RDB$FIELD_POSITION;
   public final TableField<Record, String> RDB$QUERY_HEADER;
   public final TableField<Record, Short> RDB$UPDATE_FLAG;
   public final TableField<Record, Short> RDB$FIELD_ID;
   public final TableField<Record, Short> RDB$VIEW_CONTEXT;
   public final TableField<Record, String> RDB$DESCRIPTION;
   public final TableField<Record, byte[]> RDB$DEFAULT_VALUE;
   public final TableField<Record, Short> RDB$SYSTEM_FLAG;
   public final TableField<Record, String> RDB$SECURITY_CLASS;
   public final TableField<Record, String> RDB$COMPLEX_NAME;
   public final TableField<Record, Short> RDB$NULL_FLAG;
   public final TableField<Record, String> RDB$DEFAULT_SOURCE;
   public final TableField<Record, Short> RDB$COLLATION_ID;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Rdb$relationFields() {
      this("RDB$RELATION_FIELDS", (Table)null);
   }

   public Rdb$relationFields(String alias) {
      this(alias, RDB$RELATION_FIELDS);
   }

   private Rdb$relationFields(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Rdb$relationFields(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
      this.RDB$FIELD_NAME = createField("RDB$FIELD_NAME", SQLDataType.CHAR, this, "");
      this.RDB$RELATION_NAME = createField("RDB$RELATION_NAME", SQLDataType.CHAR, this, "");
      this.RDB$FIELD_SOURCE = createField("RDB$FIELD_SOURCE", SQLDataType.CHAR, this, "");
      this.RDB$QUERY_NAME = createField("RDB$QUERY_NAME", SQLDataType.CHAR, this, "");
      this.RDB$BASE_FIELD = createField("RDB$BASE_FIELD", SQLDataType.CHAR, this, "");
      this.RDB$EDIT_STRING = createField("RDB$EDIT_STRING", SQLDataType.VARCHAR, this, "");
      this.RDB$FIELD_POSITION = createField("RDB$FIELD_POSITION", SQLDataType.SMALLINT, this, "");
      this.RDB$QUERY_HEADER = createField("RDB$QUERY_HEADER", SQLDataType.CLOB, this, "");
      this.RDB$UPDATE_FLAG = createField("RDB$UPDATE_FLAG", SQLDataType.SMALLINT, this, "");
      this.RDB$FIELD_ID = createField("RDB$FIELD_ID", SQLDataType.SMALLINT, this, "");
      this.RDB$VIEW_CONTEXT = createField("RDB$VIEW_CONTEXT", SQLDataType.SMALLINT, this, "");
      this.RDB$DESCRIPTION = createField("RDB$DESCRIPTION", SQLDataType.CLOB, this, "");
      this.RDB$DEFAULT_VALUE = createField("RDB$DEFAULT_VALUE", SQLDataType.BLOB, this, "");
      this.RDB$SYSTEM_FLAG = createField("RDB$SYSTEM_FLAG", SQLDataType.SMALLINT, this, "");
      this.RDB$SECURITY_CLASS = createField("RDB$SECURITY_CLASS", SQLDataType.CHAR, this, "");
      this.RDB$COMPLEX_NAME = createField("RDB$COMPLEX_NAME", SQLDataType.CHAR, this, "");
      this.RDB$NULL_FLAG = createField("RDB$NULL_FLAG", SQLDataType.SMALLINT, this, "");
      this.RDB$DEFAULT_SOURCE = createField("RDB$DEFAULT_SOURCE", SQLDataType.CLOB, this, "");
      this.RDB$COLLATION_ID = createField("RDB$COLLATION_ID", SQLDataType.SMALLINT, this, "");
   }

   public Rdb$relationFields as(String alias) {
      return new Rdb$relationFields(alias, this);
   }

   public Rdb$relationFields rename(String name) {
      return new Rdb$relationFields(name, (Table)null);
   }
}
