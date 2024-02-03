package org.jooq.util.firebird.rdb.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.firebird.rdb.DefaultSchema;

public class Rdb$indexSegments extends TableImpl<Record> {
   private static final long serialVersionUID = 1575389338L;
   public static final Rdb$indexSegments RDB$INDEX_SEGMENTS = new Rdb$indexSegments();
   public final TableField<Record, String> RDB$INDEX_NAME;
   public final TableField<Record, String> RDB$FIELD_NAME;
   public final TableField<Record, Short> RDB$FIELD_POSITION;
   public final TableField<Record, Double> RDB$STATISTICS;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Rdb$indexSegments() {
      this("RDB$INDEX_SEGMENTS", (Table)null);
   }

   public Rdb$indexSegments(String alias) {
      this(alias, RDB$INDEX_SEGMENTS);
   }

   private Rdb$indexSegments(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Rdb$indexSegments(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
      this.RDB$INDEX_NAME = createField("RDB$INDEX_NAME", SQLDataType.CHAR, this, "");
      this.RDB$FIELD_NAME = createField("RDB$FIELD_NAME", SQLDataType.CHAR, this, "");
      this.RDB$FIELD_POSITION = createField("RDB$FIELD_POSITION", SQLDataType.SMALLINT, this, "");
      this.RDB$STATISTICS = createField("RDB$STATISTICS", SQLDataType.DOUBLE, this, "");
   }

   public Rdb$indexSegments as(String alias) {
      return new Rdb$indexSegments(alias, this);
   }

   public Rdb$indexSegments rename(String name) {
      return new Rdb$indexSegments(name, (Table)null);
   }
}
