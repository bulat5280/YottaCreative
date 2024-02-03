package org.jooq.util.firebird.rdb.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.firebird.rdb.DefaultSchema;

public class Rdb$relationConstraints extends TableImpl<Record> {
   private static final long serialVersionUID = 107405623L;
   public static final Rdb$relationConstraints RDB$RELATION_CONSTRAINTS = new Rdb$relationConstraints();
   public final TableField<Record, String> RDB$CONSTRAINT_NAME;
   public final TableField<Record, String> RDB$CONSTRAINT_TYPE;
   public final TableField<Record, String> RDB$RELATION_NAME;
   public final TableField<Record, String> RDB$DEFERRABLE;
   public final TableField<Record, String> RDB$INITIALLY_DEFERRED;
   public final TableField<Record, String> RDB$INDEX_NAME;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Rdb$relationConstraints() {
      this("RDB$RELATION_CONSTRAINTS", (Table)null);
   }

   public Rdb$relationConstraints(String alias) {
      this(alias, RDB$RELATION_CONSTRAINTS);
   }

   private Rdb$relationConstraints(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Rdb$relationConstraints(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
      this.RDB$CONSTRAINT_NAME = createField("RDB$CONSTRAINT_NAME", SQLDataType.CHAR, this, "");
      this.RDB$CONSTRAINT_TYPE = createField("RDB$CONSTRAINT_TYPE", SQLDataType.CHAR, this, "");
      this.RDB$RELATION_NAME = createField("RDB$RELATION_NAME", SQLDataType.CHAR, this, "");
      this.RDB$DEFERRABLE = createField("RDB$DEFERRABLE", SQLDataType.CHAR, this, "");
      this.RDB$INITIALLY_DEFERRED = createField("RDB$INITIALLY_DEFERRED", SQLDataType.CHAR, this, "");
      this.RDB$INDEX_NAME = createField("RDB$INDEX_NAME", SQLDataType.CHAR, this, "");
   }

   public Rdb$relationConstraints as(String alias) {
      return new Rdb$relationConstraints(alias, this);
   }

   public Rdb$relationConstraints rename(String name) {
      return new Rdb$relationConstraints(name, (Table)null);
   }
}
