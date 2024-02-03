package org.jooq.util.postgres.pg_catalog.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.pg_catalog.PgCatalog;

public class PgDescription extends TableImpl<Record> {
   private static final long serialVersionUID = 1010818535L;
   public static final PgDescription PG_DESCRIPTION = new PgDescription();
   public final TableField<Record, Long> OBJOID;
   public final TableField<Record, Long> CLASSOID;
   public final TableField<Record, Integer> OBJSUBID;
   public final TableField<Record, String> DESCRIPTION;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public PgDescription() {
      this("pg_description", (Table)null);
   }

   public PgDescription(String alias) {
      this(alias, PG_DESCRIPTION);
   }

   private PgDescription(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private PgDescription(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, PgCatalog.PG_CATALOG, aliased, parameters, "");
      this.OBJOID = createField("objoid", SQLDataType.BIGINT.nullable(false), this, "");
      this.CLASSOID = createField("classoid", SQLDataType.BIGINT.nullable(false), this, "");
      this.OBJSUBID = createField("objsubid", SQLDataType.INTEGER.nullable(false), this, "");
      this.DESCRIPTION = createField("description", SQLDataType.CLOB.nullable(false), this, "");
   }

   public PgDescription as(String alias) {
      return new PgDescription(alias, this);
   }

   public PgDescription rename(String name) {
      return new PgDescription(name, (Table)null);
   }
}
