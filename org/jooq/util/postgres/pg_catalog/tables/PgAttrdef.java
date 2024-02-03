package org.jooq.util.postgres.pg_catalog.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.pg_catalog.PgCatalog;

public class PgAttrdef extends TableImpl<Record> {
   private static final long serialVersionUID = -772871199L;
   public static final PgAttrdef PG_ATTRDEF = new PgAttrdef();
   public final TableField<Record, Long> ADRELID;
   public final TableField<Record, Short> ADNUM;
   public final TableField<Record, Object> ADBIN;
   public final TableField<Record, String> ADSRC;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public PgAttrdef() {
      this("pg_attrdef", (Table)null);
   }

   public PgAttrdef(String alias) {
      this(alias, PG_ATTRDEF);
   }

   private PgAttrdef(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private PgAttrdef(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, PgCatalog.PG_CATALOG, aliased, parameters, "");
      this.ADRELID = createField("adrelid", SQLDataType.BIGINT.nullable(false), this, "");
      this.ADNUM = createField("adnum", SQLDataType.SMALLINT.nullable(false), this, "");
      this.ADBIN = createField("adbin", DefaultDataType.getDefaultDataType("pg_node_tree"), this, "");
      this.ADSRC = createField("adsrc", SQLDataType.CLOB, this, "");
   }

   public PgAttrdef as(String alias) {
      return new PgAttrdef(alias, this);
   }

   public PgAttrdef rename(String name) {
      return new PgAttrdef(name, (Table)null);
   }
}
