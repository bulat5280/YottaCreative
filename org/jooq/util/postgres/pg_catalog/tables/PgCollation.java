package org.jooq.util.postgres.pg_catalog.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.pg_catalog.PgCatalog;

public class PgCollation extends TableImpl<Record> {
   private static final long serialVersionUID = 39183355L;
   public static final PgCollation PG_COLLATION = new PgCollation();
   public final TableField<Record, String> COLLNAME;
   public final TableField<Record, Long> COLLNAMESPACE;
   public final TableField<Record, Long> COLLOWNER;
   public final TableField<Record, Integer> COLLENCODING;
   public final TableField<Record, String> COLLCOLLATE;
   public final TableField<Record, String> COLLCTYPE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public PgCollation() {
      this("pg_collation", (Table)null);
   }

   public PgCollation(String alias) {
      this(alias, PG_COLLATION);
   }

   private PgCollation(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private PgCollation(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, PgCatalog.PG_CATALOG, aliased, parameters, "");
      this.COLLNAME = createField("collname", SQLDataType.VARCHAR.nullable(false), this, "");
      this.COLLNAMESPACE = createField("collnamespace", SQLDataType.BIGINT.nullable(false), this, "");
      this.COLLOWNER = createField("collowner", SQLDataType.BIGINT.nullable(false), this, "");
      this.COLLENCODING = createField("collencoding", SQLDataType.INTEGER.nullable(false), this, "");
      this.COLLCOLLATE = createField("collcollate", SQLDataType.VARCHAR.nullable(false), this, "");
      this.COLLCTYPE = createField("collctype", SQLDataType.VARCHAR.nullable(false), this, "");
   }

   public PgCollation as(String alias) {
      return new PgCollation(alias, this);
   }

   public PgCollation rename(String name) {
      return new PgCollation(name, (Table)null);
   }
}
