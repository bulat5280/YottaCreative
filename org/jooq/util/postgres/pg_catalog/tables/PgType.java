package org.jooq.util.postgres.pg_catalog.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.pg_catalog.PgCatalog;

public class PgType extends TableImpl<Record> {
   private static final long serialVersionUID = -444239825L;
   public static final PgType PG_TYPE = new PgType();
   public final TableField<Record, String> TYPNAME;
   public final TableField<Record, Long> TYPNAMESPACE;
   public final TableField<Record, Long> TYPOWNER;
   public final TableField<Record, Short> TYPLEN;
   public final TableField<Record, Boolean> TYPBYVAL;
   public final TableField<Record, String> TYPTYPE;
   public final TableField<Record, String> TYPCATEGORY;
   public final TableField<Record, Boolean> TYPISPREFERRED;
   public final TableField<Record, Boolean> TYPISDEFINED;
   public final TableField<Record, String> TYPDELIM;
   public final TableField<Record, Long> TYPRELID;
   public final TableField<Record, Long> TYPELEM;
   public final TableField<Record, Long> TYPARRAY;
   public final TableField<Record, String> TYPINPUT;
   public final TableField<Record, String> TYPOUTPUT;
   public final TableField<Record, String> TYPRECEIVE;
   public final TableField<Record, String> TYPSEND;
   public final TableField<Record, String> TYPMODIN;
   public final TableField<Record, String> TYPMODOUT;
   public final TableField<Record, String> TYPANALYZE;
   public final TableField<Record, String> TYPALIGN;
   public final TableField<Record, String> TYPSTORAGE;
   public final TableField<Record, Boolean> TYPNOTNULL;
   public final TableField<Record, Long> TYPBASETYPE;
   public final TableField<Record, Integer> TYPTYPMOD;
   public final TableField<Record, Integer> TYPNDIMS;
   public final TableField<Record, Long> TYPCOLLATION;
   public final TableField<Record, Object> TYPDEFAULTBIN;
   public final TableField<Record, String> TYPDEFAULT;
   public final TableField<Record, String[]> TYPACL;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public PgType() {
      this("pg_type", (Table)null);
   }

   public PgType(String alias) {
      this(alias, PG_TYPE);
   }

   private PgType(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private PgType(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, PgCatalog.PG_CATALOG, aliased, parameters, "");
      this.TYPNAME = createField("typname", SQLDataType.VARCHAR.nullable(false), this, "");
      this.TYPNAMESPACE = createField("typnamespace", SQLDataType.BIGINT.nullable(false), this, "");
      this.TYPOWNER = createField("typowner", SQLDataType.BIGINT.nullable(false), this, "");
      this.TYPLEN = createField("typlen", SQLDataType.SMALLINT.nullable(false), this, "");
      this.TYPBYVAL = createField("typbyval", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.TYPTYPE = createField("typtype", SQLDataType.CHAR.nullable(false), this, "");
      this.TYPCATEGORY = createField("typcategory", SQLDataType.CHAR.nullable(false), this, "");
      this.TYPISPREFERRED = createField("typispreferred", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.TYPISDEFINED = createField("typisdefined", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.TYPDELIM = createField("typdelim", SQLDataType.CHAR.nullable(false), this, "");
      this.TYPRELID = createField("typrelid", SQLDataType.BIGINT.nullable(false), this, "");
      this.TYPELEM = createField("typelem", SQLDataType.BIGINT.nullable(false), this, "");
      this.TYPARRAY = createField("typarray", SQLDataType.BIGINT.nullable(false), this, "");
      this.TYPINPUT = createField("typinput", SQLDataType.VARCHAR.nullable(false), this, "");
      this.TYPOUTPUT = createField("typoutput", SQLDataType.VARCHAR.nullable(false), this, "");
      this.TYPRECEIVE = createField("typreceive", SQLDataType.VARCHAR.nullable(false), this, "");
      this.TYPSEND = createField("typsend", SQLDataType.VARCHAR.nullable(false), this, "");
      this.TYPMODIN = createField("typmodin", SQLDataType.VARCHAR.nullable(false), this, "");
      this.TYPMODOUT = createField("typmodout", SQLDataType.VARCHAR.nullable(false), this, "");
      this.TYPANALYZE = createField("typanalyze", SQLDataType.VARCHAR.nullable(false), this, "");
      this.TYPALIGN = createField("typalign", SQLDataType.CHAR.nullable(false), this, "");
      this.TYPSTORAGE = createField("typstorage", SQLDataType.CHAR.nullable(false), this, "");
      this.TYPNOTNULL = createField("typnotnull", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.TYPBASETYPE = createField("typbasetype", SQLDataType.BIGINT.nullable(false), this, "");
      this.TYPTYPMOD = createField("typtypmod", SQLDataType.INTEGER.nullable(false), this, "");
      this.TYPNDIMS = createField("typndims", SQLDataType.INTEGER.nullable(false), this, "");
      this.TYPCOLLATION = createField("typcollation", SQLDataType.BIGINT.nullable(false), this, "");
      this.TYPDEFAULTBIN = createField("typdefaultbin", DefaultDataType.getDefaultDataType("pg_node_tree"), this, "");
      this.TYPDEFAULT = createField("typdefault", SQLDataType.CLOB, this, "");
      this.TYPACL = createField("typacl", SQLDataType.VARCHAR.getArrayDataType(), this, "");
   }

   public PgType as(String alias) {
      return new PgType(alias, this);
   }

   public PgType rename(String name) {
      return new PgType(name, (Table)null);
   }
}
