package org.jooq.util.firebird.rdb.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.firebird.rdb.DefaultSchema;

public class Rdb$procedures extends TableImpl<Record> {
   private static final long serialVersionUID = 1871158502L;
   public static final Rdb$procedures RDB$PROCEDURES = new Rdb$procedures();
   public final TableField<Record, String> RDB$PROCEDURE_NAME;
   public final TableField<Record, Short> RDB$PROCEDURE_ID;
   public final TableField<Record, Short> RDB$PROCEDURE_INPUTS;
   public final TableField<Record, Short> RDB$PROCEDURE_OUTPUTS;
   public final TableField<Record, String> RDB$DESCRIPTION;
   public final TableField<Record, String> RDB$PROCEDURE_SOURCE;
   public final TableField<Record, byte[]> RDB$PROCEDURE_BLR;
   public final TableField<Record, String> RDB$SECURITY_CLASS;
   public final TableField<Record, String> RDB$OWNER_NAME;
   public final TableField<Record, byte[]> RDB$RUNTIME;
   public final TableField<Record, Short> RDB$SYSTEM_FLAG;
   public final TableField<Record, Short> RDB$PROCEDURE_TYPE;
   public final TableField<Record, Short> RDB$VALID_BLR;
   public final TableField<Record, byte[]> RDB$DEBUG_INFO;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Rdb$procedures() {
      this("RDB$PROCEDURES", (Table)null);
   }

   public Rdb$procedures(String alias) {
      this(alias, RDB$PROCEDURES);
   }

   private Rdb$procedures(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Rdb$procedures(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
      this.RDB$PROCEDURE_NAME = createField("RDB$PROCEDURE_NAME", SQLDataType.CHAR, this, "");
      this.RDB$PROCEDURE_ID = createField("RDB$PROCEDURE_ID", SQLDataType.SMALLINT, this, "");
      this.RDB$PROCEDURE_INPUTS = createField("RDB$PROCEDURE_INPUTS", SQLDataType.SMALLINT, this, "");
      this.RDB$PROCEDURE_OUTPUTS = createField("RDB$PROCEDURE_OUTPUTS", SQLDataType.SMALLINT, this, "");
      this.RDB$DESCRIPTION = createField("RDB$DESCRIPTION", SQLDataType.CLOB, this, "");
      this.RDB$PROCEDURE_SOURCE = createField("RDB$PROCEDURE_SOURCE", SQLDataType.CLOB, this, "");
      this.RDB$PROCEDURE_BLR = createField("RDB$PROCEDURE_BLR", SQLDataType.BLOB, this, "");
      this.RDB$SECURITY_CLASS = createField("RDB$SECURITY_CLASS", SQLDataType.CHAR, this, "");
      this.RDB$OWNER_NAME = createField("RDB$OWNER_NAME", SQLDataType.CHAR, this, "");
      this.RDB$RUNTIME = createField("RDB$RUNTIME", SQLDataType.BLOB, this, "");
      this.RDB$SYSTEM_FLAG = createField("RDB$SYSTEM_FLAG", SQLDataType.SMALLINT, this, "");
      this.RDB$PROCEDURE_TYPE = createField("RDB$PROCEDURE_TYPE", SQLDataType.SMALLINT, this, "");
      this.RDB$VALID_BLR = createField("RDB$VALID_BLR", SQLDataType.SMALLINT, this, "");
      this.RDB$DEBUG_INFO = createField("RDB$DEBUG_INFO", SQLDataType.BLOB, this, "");
   }

   public Rdb$procedures as(String alias) {
      return new Rdb$procedures(alias, this);
   }

   public Rdb$procedures rename(String name) {
      return new Rdb$procedures(name, (Table)null);
   }
}
