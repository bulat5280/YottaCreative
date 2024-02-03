package org.jooq.util.postgres.pg_catalog.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.pg_catalog.PgCatalog;

public class PgProc extends TableImpl<Record> {
   private static final long serialVersionUID = -575731366L;
   public static final PgProc PG_PROC = new PgProc();
   public final TableField<Record, String> PRONAME;
   public final TableField<Record, Long> PRONAMESPACE;
   public final TableField<Record, Long> PROOWNER;
   public final TableField<Record, Long> PROLANG;
   public final TableField<Record, Float> PROCOST;
   public final TableField<Record, Float> PROROWS;
   public final TableField<Record, Long> PROVARIADIC;
   public final TableField<Record, String> PROTRANSFORM;
   public final TableField<Record, Boolean> PROISAGG;
   public final TableField<Record, Boolean> PROISWINDOW;
   public final TableField<Record, Boolean> PROSECDEF;
   public final TableField<Record, Boolean> PROLEAKPROOF;
   public final TableField<Record, Boolean> PROISSTRICT;
   public final TableField<Record, Boolean> PRORETSET;
   public final TableField<Record, String> PROVOLATILE;
   public final TableField<Record, Short> PRONARGS;
   public final TableField<Record, Short> PRONARGDEFAULTS;
   public final TableField<Record, Long> PRORETTYPE;
   public final TableField<Record, Long[]> PROARGTYPES;
   public final TableField<Record, Long[]> PROALLARGTYPES;
   public final TableField<Record, String[]> PROARGMODES;
   public final TableField<Record, String[]> PROARGNAMES;
   public final TableField<Record, Object> PROARGDEFAULTS;
   public final TableField<Record, Long[]> PROTRFTYPES;
   public final TableField<Record, String> PROSRC;
   public final TableField<Record, String> PROBIN;
   public final TableField<Record, String[]> PROCONFIG;
   public final TableField<Record, String[]> PROACL;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public PgProc() {
      this("pg_proc", (Table)null);
   }

   public PgProc(String alias) {
      this(alias, PG_PROC);
   }

   private PgProc(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private PgProc(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, PgCatalog.PG_CATALOG, aliased, parameters, "");
      this.PRONAME = createField("proname", SQLDataType.VARCHAR.nullable(false), this, "");
      this.PRONAMESPACE = createField("pronamespace", SQLDataType.BIGINT.nullable(false), this, "");
      this.PROOWNER = createField("proowner", SQLDataType.BIGINT.nullable(false), this, "");
      this.PROLANG = createField("prolang", SQLDataType.BIGINT.nullable(false), this, "");
      this.PROCOST = createField("procost", SQLDataType.REAL.nullable(false), this, "");
      this.PROROWS = createField("prorows", SQLDataType.REAL.nullable(false), this, "");
      this.PROVARIADIC = createField("provariadic", SQLDataType.BIGINT.nullable(false), this, "");
      this.PROTRANSFORM = createField("protransform", SQLDataType.VARCHAR.nullable(false), this, "");
      this.PROISAGG = createField("proisagg", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.PROISWINDOW = createField("proiswindow", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.PROSECDEF = createField("prosecdef", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.PROLEAKPROOF = createField("proleakproof", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.PROISSTRICT = createField("proisstrict", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.PRORETSET = createField("proretset", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.PROVOLATILE = createField("provolatile", SQLDataType.CHAR.nullable(false), this, "");
      this.PRONARGS = createField("pronargs", SQLDataType.SMALLINT.nullable(false), this, "");
      this.PRONARGDEFAULTS = createField("pronargdefaults", SQLDataType.SMALLINT.nullable(false), this, "");
      this.PRORETTYPE = createField("prorettype", SQLDataType.BIGINT.nullable(false), this, "");
      this.PROARGTYPES = createField("proargtypes", SQLDataType.BIGINT.getArrayDataType(), this, "");
      this.PROALLARGTYPES = createField("proallargtypes", SQLDataType.BIGINT.getArrayDataType(), this, "");
      this.PROARGMODES = createField("proargmodes", SQLDataType.CHAR.getArrayDataType(), this, "");
      this.PROARGNAMES = createField("proargnames", SQLDataType.CLOB.getArrayDataType(), this, "");
      this.PROARGDEFAULTS = createField("proargdefaults", DefaultDataType.getDefaultDataType("pg_node_tree"), this, "");
      this.PROTRFTYPES = createField("protrftypes", SQLDataType.BIGINT.getArrayDataType(), this, "");
      this.PROSRC = createField("prosrc", SQLDataType.CLOB.nullable(false), this, "");
      this.PROBIN = createField("probin", SQLDataType.CLOB, this, "");
      this.PROCONFIG = createField("proconfig", SQLDataType.CLOB.getArrayDataType(), this, "");
      this.PROACL = createField("proacl", SQLDataType.VARCHAR.getArrayDataType(), this, "");
   }

   public PgProc as(String alias) {
      return new PgProc(alias, this);
   }

   public PgProc rename(String name) {
      return new PgProc(name, (Table)null);
   }
}
