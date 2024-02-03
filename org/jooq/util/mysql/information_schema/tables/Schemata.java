package org.jooq.util.mysql.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.mysql.information_schema.InformationSchema;

public class Schemata extends TableImpl<Record> {
   private static final long serialVersionUID = -2145867409L;
   public static final Schemata SCHEMATA = new Schemata();
   public static final TableField<Record, String> CATALOG_NAME;
   public static final TableField<Record, String> SCHEMA_NAME;
   public static final TableField<Record, String> DEFAULT_CHARACTER_SET_NAME;
   public static final TableField<Record, String> DEFAULT_COLLATION_NAME;
   public static final TableField<Record, String> SQL_PATH;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Schemata() {
      this("SCHEMATA", (Table)null);
   }

   private Schemata(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Schemata(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
   }

   static {
      CATALOG_NAME = createField("CATALOG_NAME", SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), SCHEMATA, "");
      SCHEMA_NAME = createField("SCHEMA_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), SCHEMATA, "");
      DEFAULT_CHARACTER_SET_NAME = createField("DEFAULT_CHARACTER_SET_NAME", SQLDataType.VARCHAR.length(32).nullable(false).defaulted(true), SCHEMATA, "");
      DEFAULT_COLLATION_NAME = createField("DEFAULT_COLLATION_NAME", SQLDataType.VARCHAR.length(32).nullable(false).defaulted(true), SCHEMATA, "");
      SQL_PATH = createField("SQL_PATH", SQLDataType.VARCHAR.length(512), SCHEMATA, "");
   }
}
