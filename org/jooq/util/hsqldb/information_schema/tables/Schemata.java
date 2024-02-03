package org.jooq.util.hsqldb.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.hsqldb.information_schema.InformationSchema;

public class Schemata extends TableImpl<Record> {
   private static final long serialVersionUID = 1483886468L;
   public static final Schemata SCHEMATA = new Schemata();
   public final TableField<Record, String> CATALOG_NAME;
   public final TableField<Record, String> SCHEMA_NAME;
   public final TableField<Record, String> SCHEMA_OWNER;
   public final TableField<Record, String> DEFAULT_CHARACTER_SET_CATALOG;
   public final TableField<Record, String> DEFAULT_CHARACTER_SET_SCHEMA;
   public final TableField<Record, String> DEFAULT_CHARACTER_SET_NAME;
   public final TableField<Record, String> SQL_PATH;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Schemata() {
      this("SCHEMATA", (Table)null);
   }

   public Schemata(String alias) {
      this(alias, SCHEMATA);
   }

   private Schemata(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Schemata(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.CATALOG_NAME = createField("CATALOG_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.SCHEMA_NAME = createField("SCHEMA_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.SCHEMA_OWNER = createField("SCHEMA_OWNER", SQLDataType.VARCHAR.length(128), this, "");
      this.DEFAULT_CHARACTER_SET_CATALOG = createField("DEFAULT_CHARACTER_SET_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.DEFAULT_CHARACTER_SET_SCHEMA = createField("DEFAULT_CHARACTER_SET_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.DEFAULT_CHARACTER_SET_NAME = createField("DEFAULT_CHARACTER_SET_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.SQL_PATH = createField("SQL_PATH", SQLDataType.VARCHAR.length(65536), this, "");
   }

   public Schemata as(String alias) {
      return new Schemata(alias, this);
   }

   public Schemata rename(String name) {
      return new Schemata(name, (Table)null);
   }
}
