package org.jooq.util.postgres.pg_catalog.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.pg_catalog.PgCatalog;

public class PgInherits extends TableImpl<Record> {
   private static final long serialVersionUID = -1118829730L;
   public static final PgInherits PG_INHERITS = new PgInherits();
   public final TableField<Record, Long> INHRELID;
   public final TableField<Record, Long> INHPARENT;
   public final TableField<Record, Integer> INHSEQNO;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public PgInherits() {
      this("pg_inherits", (Table)null);
   }

   public PgInherits(String alias) {
      this(alias, PG_INHERITS);
   }

   private PgInherits(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private PgInherits(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, PgCatalog.PG_CATALOG, aliased, parameters, "");
      this.INHRELID = createField("inhrelid", SQLDataType.BIGINT.nullable(false), this, "");
      this.INHPARENT = createField("inhparent", SQLDataType.BIGINT.nullable(false), this, "");
      this.INHSEQNO = createField("inhseqno", SQLDataType.INTEGER.nullable(false), this, "");
   }

   public PgInherits as(String alias) {
      return new PgInherits(alias, this);
   }

   public PgInherits rename(String name) {
      return new PgInherits(name, (Table)null);
   }
}
