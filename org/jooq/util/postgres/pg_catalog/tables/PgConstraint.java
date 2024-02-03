package org.jooq.util.postgres.pg_catalog.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.pg_catalog.PgCatalog;

public class PgConstraint extends TableImpl<Record> {
   private static final long serialVersionUID = 1090390727L;
   public static final PgConstraint PG_CONSTRAINT = new PgConstraint();
   public final TableField<Record, String> CONNAME;
   public final TableField<Record, Long> CONNAMESPACE;
   public final TableField<Record, String> CONTYPE;
   public final TableField<Record, Boolean> CONDEFERRABLE;
   public final TableField<Record, Boolean> CONDEFERRED;
   public final TableField<Record, Boolean> CONVALIDATED;
   public final TableField<Record, Long> CONRELID;
   public final TableField<Record, Long> CONTYPID;
   public final TableField<Record, Long> CONINDID;
   public final TableField<Record, Long> CONFRELID;
   public final TableField<Record, String> CONFUPDTYPE;
   public final TableField<Record, String> CONFDELTYPE;
   public final TableField<Record, String> CONFMATCHTYPE;
   public final TableField<Record, Boolean> CONISLOCAL;
   public final TableField<Record, Integer> CONINHCOUNT;
   public final TableField<Record, Boolean> CONNOINHERIT;
   public final TableField<Record, Short[]> CONKEY;
   public final TableField<Record, Short[]> CONFKEY;
   public final TableField<Record, Long[]> CONPFEQOP;
   public final TableField<Record, Long[]> CONPPEQOP;
   public final TableField<Record, Long[]> CONFFEQOP;
   public final TableField<Record, Long[]> CONEXCLOP;
   public final TableField<Record, Object> CONBIN;
   public final TableField<Record, String> CONSRC;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public PgConstraint() {
      this("pg_constraint", (Table)null);
   }

   public PgConstraint(String alias) {
      this(alias, PG_CONSTRAINT);
   }

   private PgConstraint(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private PgConstraint(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, PgCatalog.PG_CATALOG, aliased, parameters, "");
      this.CONNAME = createField("conname", SQLDataType.VARCHAR.nullable(false), this, "");
      this.CONNAMESPACE = createField("connamespace", SQLDataType.BIGINT.nullable(false), this, "");
      this.CONTYPE = createField("contype", SQLDataType.CHAR.nullable(false), this, "");
      this.CONDEFERRABLE = createField("condeferrable", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.CONDEFERRED = createField("condeferred", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.CONVALIDATED = createField("convalidated", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.CONRELID = createField("conrelid", SQLDataType.BIGINT.nullable(false), this, "");
      this.CONTYPID = createField("contypid", SQLDataType.BIGINT.nullable(false), this, "");
      this.CONINDID = createField("conindid", SQLDataType.BIGINT.nullable(false), this, "");
      this.CONFRELID = createField("confrelid", SQLDataType.BIGINT.nullable(false), this, "");
      this.CONFUPDTYPE = createField("confupdtype", SQLDataType.CHAR.nullable(false), this, "");
      this.CONFDELTYPE = createField("confdeltype", SQLDataType.CHAR.nullable(false), this, "");
      this.CONFMATCHTYPE = createField("confmatchtype", SQLDataType.CHAR.nullable(false), this, "");
      this.CONISLOCAL = createField("conislocal", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.CONINHCOUNT = createField("coninhcount", SQLDataType.INTEGER.nullable(false), this, "");
      this.CONNOINHERIT = createField("connoinherit", SQLDataType.BOOLEAN.nullable(false), this, "");
      this.CONKEY = createField("conkey", SQLDataType.SMALLINT.getArrayDataType(), this, "");
      this.CONFKEY = createField("confkey", SQLDataType.SMALLINT.getArrayDataType(), this, "");
      this.CONPFEQOP = createField("conpfeqop", SQLDataType.BIGINT.getArrayDataType(), this, "");
      this.CONPPEQOP = createField("conppeqop", SQLDataType.BIGINT.getArrayDataType(), this, "");
      this.CONFFEQOP = createField("conffeqop", SQLDataType.BIGINT.getArrayDataType(), this, "");
      this.CONEXCLOP = createField("conexclop", SQLDataType.BIGINT.getArrayDataType(), this, "");
      this.CONBIN = createField("conbin", DefaultDataType.getDefaultDataType("pg_node_tree"), this, "");
      this.CONSRC = createField("consrc", SQLDataType.CLOB, this, "");
   }

   public PgConstraint as(String alias) {
      return new PgConstraint(alias, this);
   }

   public PgConstraint rename(String name) {
      return new PgConstraint(name, (Table)null);
   }
}
