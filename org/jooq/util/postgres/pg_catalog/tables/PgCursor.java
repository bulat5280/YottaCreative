package org.jooq.util.postgres.pg_catalog.tables;

import java.sql.Timestamp;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.pg_catalog.PgCatalog;

public class PgCursor extends TableImpl<Record> {
   private static final long serialVersionUID = -1860388401L;
   public static final PgCursor PG_CURSOR = new PgCursor();
   public final TableField<Record, String> NAME;
   public final TableField<Record, String> STATEMENT;
   public final TableField<Record, Boolean> IS_HOLDABLE;
   public final TableField<Record, Boolean> IS_BINARY;
   public final TableField<Record, Boolean> IS_SCROLLABLE;
   public final TableField<Record, Timestamp> CREATION_TIME;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public PgCursor() {
      this("pg_cursor", (Table)null);
   }

   public PgCursor(String alias) {
      this(alias, PG_CURSOR);
   }

   private PgCursor(String alias, Table<Record> aliased) {
      this(alias, aliased, new Field[0]);
   }

   private PgCursor(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, PgCatalog.PG_CATALOG, aliased, parameters, "");
      this.NAME = createField("name", SQLDataType.CLOB, this, "");
      this.STATEMENT = createField("statement", SQLDataType.CLOB, this, "");
      this.IS_HOLDABLE = createField("is_holdable", SQLDataType.BOOLEAN, this, "");
      this.IS_BINARY = createField("is_binary", SQLDataType.BOOLEAN, this, "");
      this.IS_SCROLLABLE = createField("is_scrollable", SQLDataType.BOOLEAN, this, "");
      this.CREATION_TIME = createField("creation_time", SQLDataType.TIMESTAMP, this, "");
   }

   public PgCursor as(String alias) {
      return new PgCursor(alias, this, this.parameters);
   }

   public PgCursor rename(String name) {
      return new PgCursor(name, (Table)null, this.parameters);
   }

   public PgCursor call() {
      return new PgCursor(this.getName(), (Table)null, new Field[0]);
   }
}
