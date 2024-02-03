package org.jooq.util.postgres.pg_catalog.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.pg_catalog.PgCatalog;

public class PgNamespace extends TableImpl<Record> {
   private static final long serialVersionUID = -1052606720L;
   public static final PgNamespace PG_NAMESPACE = new PgNamespace();
   public final TableField<Record, String> NSPNAME;
   public final TableField<Record, Long> NSPOWNER;
   public final TableField<Record, String[]> NSPACL;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public PgNamespace() {
      this("pg_namespace", (Table)null);
   }

   public PgNamespace(String alias) {
      this(alias, PG_NAMESPACE);
   }

   private PgNamespace(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private PgNamespace(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, PgCatalog.PG_CATALOG, aliased, parameters, "");
      this.NSPNAME = createField("nspname", SQLDataType.VARCHAR.nullable(false), this, "");
      this.NSPOWNER = createField("nspowner", SQLDataType.BIGINT.nullable(false), this, "");
      this.NSPACL = createField("nspacl", SQLDataType.VARCHAR.getArrayDataType(), this, "");
   }

   public PgNamespace as(String alias) {
      return new PgNamespace(alias, this);
   }

   public PgNamespace rename(String name) {
      return new PgNamespace(name, (Table)null);
   }
}
