package org.jooq.util.postgres.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.information_schema.InformationSchema;

public class Schemata extends TableImpl<Record> {
   private static final long serialVersionUID = 444195803L;
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
      this("schemata", (Table)null);
   }

   public Schemata(String alias) {
      this(alias, SCHEMATA);
   }

   private Schemata(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Schemata(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.CATALOG_NAME = createField("catalog_name", SQLDataType.VARCHAR, this, "");
      this.SCHEMA_NAME = createField("schema_name", SQLDataType.VARCHAR, this, "");
      this.SCHEMA_OWNER = createField("schema_owner", SQLDataType.VARCHAR, this, "");
      this.DEFAULT_CHARACTER_SET_CATALOG = createField("default_character_set_catalog", SQLDataType.VARCHAR, this, "");
      this.DEFAULT_CHARACTER_SET_SCHEMA = createField("default_character_set_schema", SQLDataType.VARCHAR, this, "");
      this.DEFAULT_CHARACTER_SET_NAME = createField("default_character_set_name", SQLDataType.VARCHAR, this, "");
      this.SQL_PATH = createField("sql_path", SQLDataType.VARCHAR, this, "");
   }

   public Schemata as(String alias) {
      return new Schemata(alias, this);
   }

   public Schemata rename(String name) {
      return new Schemata(name, (Table)null);
   }
}
