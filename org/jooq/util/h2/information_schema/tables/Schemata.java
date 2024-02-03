package org.jooq.util.h2.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.h2.information_schema.InformationSchema;

public class Schemata extends TableImpl<Record> {
   private static final long serialVersionUID = -863032455L;
   public static final Schemata SCHEMATA = new Schemata();
   public static final TableField<Record, String> CATALOG_NAME;
   public static final TableField<Record, String> SCHEMA_NAME;
   public static final TableField<Record, String> SCHEMA_OWNER;
   public static final TableField<Record, String> DEFAULT_CHARACTER_SET_NAME;
   public static final TableField<Record, String> DEFAULT_COLLATION_NAME;
   public static final TableField<Record, Boolean> IS_DEFAULT;
   public static final TableField<Record, String> REMARKS;
   public static final TableField<Record, Integer> ID;

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
      CATALOG_NAME = createField("CATALOG_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), SCHEMATA, "");
      SCHEMA_NAME = createField("SCHEMA_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), SCHEMATA, "");
      SCHEMA_OWNER = createField("SCHEMA_OWNER", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), SCHEMATA, "");
      DEFAULT_CHARACTER_SET_NAME = createField("DEFAULT_CHARACTER_SET_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), SCHEMATA, "");
      DEFAULT_COLLATION_NAME = createField("DEFAULT_COLLATION_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), SCHEMATA, "");
      IS_DEFAULT = createField("IS_DEFAULT", SQLDataType.BOOLEAN, SCHEMATA, "");
      REMARKS = createField("REMARKS", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), SCHEMATA, "");
      ID = createField("ID", SQLDataType.INTEGER, SCHEMATA, "");
   }
}
