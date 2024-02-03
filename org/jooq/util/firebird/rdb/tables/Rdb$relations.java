package org.jooq.util.firebird.rdb.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.firebird.rdb.DefaultSchema;

public class Rdb$relations extends TableImpl<Record> {
   private static final long serialVersionUID = 1522914669L;
   public static final Rdb$relations RDB$RELATIONS = new Rdb$relations();
   public final TableField<Record, byte[]> RDB$VIEW_BLR;
   public final TableField<Record, String> RDB$VIEW_SOURCE;
   public final TableField<Record, String> RDB$DESCRIPTION;
   public final TableField<Record, Short> RDB$RELATION_ID;
   public final TableField<Record, Short> RDB$SYSTEM_FLAG;
   public final TableField<Record, Short> RDB$DBKEY_LENGTH;
   public final TableField<Record, Short> RDB$FORMAT;
   public final TableField<Record, Short> RDB$FIELD_ID;
   public final TableField<Record, String> RDB$RELATION_NAME;
   public final TableField<Record, String> RDB$SECURITY_CLASS;
   public final TableField<Record, String> RDB$EXTERNAL_FILE;
   public final TableField<Record, byte[]> RDB$RUNTIME;
   public final TableField<Record, byte[]> RDB$EXTERNAL_DESCRIPTION;
   public final TableField<Record, String> RDB$OWNER_NAME;
   public final TableField<Record, String> RDB$DEFAULT_CLASS;
   public final TableField<Record, Short> RDB$FLAGS;
   public final TableField<Record, Short> RDB$RELATION_TYPE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Rdb$relations() {
      this("RDB$RELATIONS", (Table)null);
   }

   public Rdb$relations(String alias) {
      this(alias, RDB$RELATIONS);
   }

   private Rdb$relations(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Rdb$relations(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
      this.RDB$VIEW_BLR = createField("RDB$VIEW_BLR", SQLDataType.BLOB, this, "");
      this.RDB$VIEW_SOURCE = createField("RDB$VIEW_SOURCE", SQLDataType.CLOB, this, "");
      this.RDB$DESCRIPTION = createField("RDB$DESCRIPTION", SQLDataType.CLOB, this, "");
      this.RDB$RELATION_ID = createField("RDB$RELATION_ID", SQLDataType.SMALLINT, this, "");
      this.RDB$SYSTEM_FLAG = createField("RDB$SYSTEM_FLAG", SQLDataType.SMALLINT, this, "");
      this.RDB$DBKEY_LENGTH = createField("RDB$DBKEY_LENGTH", SQLDataType.SMALLINT, this, "");
      this.RDB$FORMAT = createField("RDB$FORMAT", SQLDataType.SMALLINT, this, "");
      this.RDB$FIELD_ID = createField("RDB$FIELD_ID", SQLDataType.SMALLINT, this, "");
      this.RDB$RELATION_NAME = createField("RDB$RELATION_NAME", SQLDataType.CHAR, this, "");
      this.RDB$SECURITY_CLASS = createField("RDB$SECURITY_CLASS", SQLDataType.CHAR, this, "");
      this.RDB$EXTERNAL_FILE = createField("RDB$EXTERNAL_FILE", SQLDataType.VARCHAR, this, "");
      this.RDB$RUNTIME = createField("RDB$RUNTIME", SQLDataType.BLOB, this, "");
      this.RDB$EXTERNAL_DESCRIPTION = createField("RDB$EXTERNAL_DESCRIPTION", SQLDataType.BLOB, this, "");
      this.RDB$OWNER_NAME = createField("RDB$OWNER_NAME", SQLDataType.CHAR, this, "");
      this.RDB$DEFAULT_CLASS = createField("RDB$DEFAULT_CLASS", SQLDataType.CHAR, this, "");
      this.RDB$FLAGS = createField("RDB$FLAGS", SQLDataType.SMALLINT, this, "");
      this.RDB$RELATION_TYPE = createField("RDB$RELATION_TYPE", SQLDataType.SMALLINT, this, "");
   }

   public Rdb$relations as(String alias) {
      return new Rdb$relations(alias, this);
   }

   public Rdb$relations rename(String name) {
      return new Rdb$relations(name, (Table)null);
   }
}
