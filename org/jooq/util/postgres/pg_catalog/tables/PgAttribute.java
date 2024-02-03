package org.jooq.util.postgres.pg_catalog.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.pg_catalog.PgCatalog;

public class PgAttribute extends TableImpl<Record> {
   private static final long serialVersionUID = -1595093218L;
   public static final PgAttribute PG_ATTRIBUTE = new PgAttribute();
   public final TableField<Record, Long> ATTRELID;
   public final TableField<Record, String> ATTNAME;
   public final TableField<Record, Long> ATTTYPID;
   public final TableField<Record, Integer> ATTSTATTARGET;
   public final TableField<Record, Short> ATTLEN;
   public final TableField<Record, Short> ATTNUM;
   public final TableField<Record, Integer> ATTNDIMS;
   public final TableField<Record, Integer> ATTCACHEOFF;
   public final TableField<Record, Integer> ATTTYPMOD;
   public final TableField<Record, Boolean> ATTBYVAL;
   public final TableField<Record, String> ATTSTORAGE;
   public final TableField<Record, String> ATTALIGN;
   public final TableField<Record, Boolean> ATTNOTNULL;
   public final TableField<Record, Boolean> ATTHASDEF;
   public final TableField<Record, Boolean> ATTISDROPPED;
   public final TableField<Record, Boolean> ATTISLOCAL;
   public final TableField<Record, Integer> ATTINHCOUNT;
   public final TableField<Record, Long> ATTCOLLATION;
   public final TableField<Record, String[]> ATTACL;
   public final TableField<Record, String[]> ATTOPTIONS;
   public final TableField<Record, String[]> ATTFDWOPTIONS;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public PgAttribute() {
      this("pg_attribute", (Table)null);
   }

   public PgAttribute(String alias) {
      this(alias, PG_ATTRIBUTE);
   }

   private PgAttribute(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private PgAttribute(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, PgCatalog.PG_CATALOG, aliased, parameters, "");
      this.ATTRELID = createField("attrelid", SQLDataType.BIGINT.nullable(false), this, "");
      this.ATTNAME = createField("attname", SQLDataType.VARCHAR.nullable(false), this, "");
      this.ATTTYPID = createField("atttypid", SQLDataType.BIGINT.nullable(false), this, "");
      this.ATTSTATTARGET = createField("attstattarget", SQLDataType.INTEGER.nullable(false), this, "");
      this.ATTLEN = createField("attlen", SQLDataType.SMALLINT.nullable(false), this, "");
      this.ATTNUM = createField("attnum", SQLDataType.SMALLINT.nullable(false), this, "");
      this.ATTNDIMS = createField("attndims", SQLDataType.INTEGER.nullable(false), this, "");
      this.ATTCACHEOFF = createField("attcacheoff", SQLDataType.INTEGER.nullable(false), this, "");
      this.ATTTYPMOD = createField("atttypmod", SQLDataType.INTEGER.nullable(false), this, "");
      this.ATTBYVAL = createField("attbyval", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.ATTSTORAGE = createField("attstorage", SQLDataType.CHAR.nullable(false), this, "");
      this.ATTALIGN = createField("attalign", SQLDataType.CHAR.nullable(false), this, "");
      this.ATTNOTNULL = createField("attnotnull", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.ATTHASDEF = createField("atthasdef", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.ATTISDROPPED = createField("attisdropped", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.ATTISLOCAL = createField("attislocal", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.ATTINHCOUNT = createField("attinhcount", SQLDataType.INTEGER.nullable(false), this, "");
      this.ATTCOLLATION = createField("attcollation", SQLDataType.BIGINT.nullable(false), this, "");
      this.ATTACL = createField("attacl", SQLDataType.VARCHAR.getArrayDataType(), this, "");
      this.ATTOPTIONS = createField("attoptions", SQLDataType.CLOB.getArrayDataType(), this, "");
      this.ATTFDWOPTIONS = createField("attfdwoptions", SQLDataType.CLOB.getArrayDataType(), this, "");
   }

   public PgAttribute as(String alias) {
      return new PgAttribute(alias, this);
   }

   public PgAttribute rename(String name) {
      return new PgAttribute(name, (Table)null);
   }
}
