package org.jooq.util.firebird.rdb.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.firebird.rdb.DefaultSchema;

public class Rdb$refConstraints extends TableImpl<Record> {
   private static final long serialVersionUID = -1557249328L;
   public static final Rdb$refConstraints RDB$REF_CONSTRAINTS = new Rdb$refConstraints();
   public final TableField<Record, String> RDB$CONSTRAINT_NAME;
   public final TableField<Record, String> RDB$CONST_NAME_UQ;
   public final TableField<Record, String> RDB$MATCH_OPTION;
   public final TableField<Record, String> RDB$UPDATE_RULE;
   public final TableField<Record, String> RDB$DELETE_RULE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Rdb$refConstraints() {
      this("RDB$REF_CONSTRAINTS", (Table)null);
   }

   public Rdb$refConstraints(String alias) {
      this(alias, RDB$REF_CONSTRAINTS);
   }

   private Rdb$refConstraints(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Rdb$refConstraints(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
      this.RDB$CONSTRAINT_NAME = createField("RDB$CONSTRAINT_NAME", SQLDataType.CHAR, this, "");
      this.RDB$CONST_NAME_UQ = createField("RDB$CONST_NAME_UQ", SQLDataType.CHAR, this, "");
      this.RDB$MATCH_OPTION = createField("RDB$MATCH_OPTION", SQLDataType.CHAR, this, "");
      this.RDB$UPDATE_RULE = createField("RDB$UPDATE_RULE", SQLDataType.CHAR, this, "");
      this.RDB$DELETE_RULE = createField("RDB$DELETE_RULE", SQLDataType.CHAR, this, "");
   }

   public Rdb$refConstraints as(String alias) {
      return new Rdb$refConstraints(alias, this);
   }

   public Rdb$refConstraints rename(String name) {
      return new Rdb$refConstraints(name, (Table)null);
   }
}
