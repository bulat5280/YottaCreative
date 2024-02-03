package org.jooq.util.firebird.rdb.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.firebird.rdb.DefaultSchema;

public class Rdb$generators extends TableImpl<Record> {
   private static final long serialVersionUID = 1461095975L;
   public static final Rdb$generators RDB$GENERATORS = new Rdb$generators();
   public final TableField<Record, String> RDB$GENERATOR_NAME;
   public final TableField<Record, Short> RDB$GENERATOR_ID;
   public final TableField<Record, Short> RDB$SYSTEM_FLAG;
   public final TableField<Record, String> RDB$DESCRIPTION;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Rdb$generators() {
      this("RDB$GENERATORS", (Table)null);
   }

   public Rdb$generators(String alias) {
      this(alias, RDB$GENERATORS);
   }

   private Rdb$generators(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Rdb$generators(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
      this.RDB$GENERATOR_NAME = createField("RDB$GENERATOR_NAME", SQLDataType.CHAR, this, "");
      this.RDB$GENERATOR_ID = createField("RDB$GENERATOR_ID", SQLDataType.SMALLINT, this, "");
      this.RDB$SYSTEM_FLAG = createField("RDB$SYSTEM_FLAG", SQLDataType.SMALLINT, this, "");
      this.RDB$DESCRIPTION = createField("RDB$DESCRIPTION", SQLDataType.CLOB, this, "");
   }

   public Rdb$generators as(String alias) {
      return new Rdb$generators(alias, this);
   }

   public Rdb$generators rename(String name) {
      return new Rdb$generators(name, (Table)null);
   }
}
