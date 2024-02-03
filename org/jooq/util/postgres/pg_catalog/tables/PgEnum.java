package org.jooq.util.postgres.pg_catalog.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.pg_catalog.PgCatalog;

public class PgEnum extends TableImpl<Record> {
   private static final long serialVersionUID = 1505652681L;
   public static final PgEnum PG_ENUM = new PgEnum();
   public final TableField<Record, Long> ENUMTYPID;
   public final TableField<Record, Float> ENUMSORTORDER;
   public final TableField<Record, String> ENUMLABEL;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public PgEnum() {
      this("pg_enum", (Table)null);
   }

   public PgEnum(String alias) {
      this(alias, PG_ENUM);
   }

   private PgEnum(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private PgEnum(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, PgCatalog.PG_CATALOG, aliased, parameters, "");
      this.ENUMTYPID = createField("enumtypid", SQLDataType.BIGINT.nullable(false), this, "");
      this.ENUMSORTORDER = createField("enumsortorder", SQLDataType.REAL.nullable(false), this, "");
      this.ENUMLABEL = createField("enumlabel", SQLDataType.VARCHAR.nullable(false), this, "");
   }

   public PgEnum as(String alias) {
      return new PgEnum(alias, this);
   }

   public PgEnum rename(String name) {
      return new PgEnum(name, (Table)null);
   }
}
