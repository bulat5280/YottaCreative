package org.jooq.util.postgres.pg_catalog.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.pg_catalog.PgCatalog;

public class PgClass extends TableImpl<Record> {
   private static final long serialVersionUID = -1454094151L;
   public static final PgClass PG_CLASS = new PgClass();
   public final TableField<Record, String> RELNAME;
   public final TableField<Record, Long> RELNAMESPACE;
   public final TableField<Record, Long> RELTYPE;
   public final TableField<Record, Long> RELOFTYPE;
   public final TableField<Record, Long> RELOWNER;
   public final TableField<Record, Long> RELAM;
   public final TableField<Record, Long> RELFILENODE;
   public final TableField<Record, Long> RELTABLESPACE;
   public final TableField<Record, Integer> RELPAGES;
   public final TableField<Record, Float> RELTUPLES;
   public final TableField<Record, Integer> RELALLVISIBLE;
   public final TableField<Record, Long> RELTOASTRELID;
   public final TableField<Record, Boolean> RELHASINDEX;
   public final TableField<Record, Boolean> RELISSHARED;
   public final TableField<Record, String> RELPERSISTENCE;
   public final TableField<Record, String> RELKIND;
   public final TableField<Record, Short> RELNATTS;
   public final TableField<Record, Short> RELCHECKS;
   public final TableField<Record, Boolean> RELHASOIDS;
   public final TableField<Record, Boolean> RELHASPKEY;
   public final TableField<Record, Boolean> RELHASRULES;
   public final TableField<Record, Boolean> RELHASTRIGGERS;
   public final TableField<Record, Boolean> RELHASSUBCLASS;
   public final TableField<Record, Boolean> RELROWSECURITY;
   public final TableField<Record, Boolean> RELFORCEROWSECURITY;
   public final TableField<Record, Boolean> RELISPOPULATED;
   public final TableField<Record, String> RELREPLIDENT;
   public final TableField<Record, Long> RELFROZENXID;
   public final TableField<Record, Long> RELMINMXID;
   public final TableField<Record, String[]> RELACL;
   public final TableField<Record, String[]> RELOPTIONS;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public PgClass() {
      this("pg_class", (Table)null);
   }

   public PgClass(String alias) {
      this(alias, PG_CLASS);
   }

   private PgClass(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private PgClass(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, PgCatalog.PG_CATALOG, aliased, parameters, "");
      this.RELNAME = createField("relname", SQLDataType.VARCHAR.nullable(false), this, "");
      this.RELNAMESPACE = createField("relnamespace", SQLDataType.BIGINT.nullable(false), this, "");
      this.RELTYPE = createField("reltype", SQLDataType.BIGINT.nullable(false), this, "");
      this.RELOFTYPE = createField("reloftype", SQLDataType.BIGINT.nullable(false), this, "");
      this.RELOWNER = createField("relowner", SQLDataType.BIGINT.nullable(false), this, "");
      this.RELAM = createField("relam", SQLDataType.BIGINT.nullable(false), this, "");
      this.RELFILENODE = createField("relfilenode", SQLDataType.BIGINT.nullable(false), this, "");
      this.RELTABLESPACE = createField("reltablespace", SQLDataType.BIGINT.nullable(false), this, "");
      this.RELPAGES = createField("relpages", SQLDataType.INTEGER.nullable(false), this, "");
      this.RELTUPLES = createField("reltuples", SQLDataType.REAL.nullable(false), this, "");
      this.RELALLVISIBLE = createField("relallvisible", SQLDataType.INTEGER.nullable(false), this, "");
      this.RELTOASTRELID = createField("reltoastrelid", SQLDataType.BIGINT.nullable(false), this, "");
      this.RELHASINDEX = createField("relhasindex", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.RELISSHARED = createField("relisshared", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.RELPERSISTENCE = createField("relpersistence", SQLDataType.CHAR.nullable(false), this, "");
      this.RELKIND = createField("relkind", SQLDataType.CHAR.nullable(false), this, "");
      this.RELNATTS = createField("relnatts", SQLDataType.SMALLINT.nullable(false), this, "");
      this.RELCHECKS = createField("relchecks", SQLDataType.SMALLINT.nullable(false), this, "");
      this.RELHASOIDS = createField("relhasoids", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.RELHASPKEY = createField("relhaspkey", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.RELHASRULES = createField("relhasrules", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.RELHASTRIGGERS = createField("relhastriggers", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.RELHASSUBCLASS = createField("relhassubclass", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.RELROWSECURITY = createField("relrowsecurity", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.RELFORCEROWSECURITY = createField("relforcerowsecurity", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.RELISPOPULATED = createField("relispopulated", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.RELREPLIDENT = createField("relreplident", SQLDataType.CHAR.nullable(false), this, "");
      this.RELFROZENXID = createField("relfrozenxid", SQLDataType.BIGINT.nullable(false), this, "");
      this.RELMINMXID = createField("relminmxid", SQLDataType.BIGINT.nullable(false), this, "");
      this.RELACL = createField("relacl", SQLDataType.VARCHAR.getArrayDataType(), this, "");
      this.RELOPTIONS = createField("reloptions", SQLDataType.CLOB.getArrayDataType(), this, "");
   }

   public PgClass as(String alias) {
      return new PgClass(alias, this);
   }

   public PgClass rename(String name) {
      return new PgClass(name, (Table)null);
   }
}
