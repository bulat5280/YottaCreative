package org.jooq.util.mysql.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.mysql.information_schema.InformationSchema;

public class Parameters extends TableImpl<Record> {
   private static final long serialVersionUID = 1665633877L;
   public static final Parameters PARAMETERS = new Parameters();
   public static final TableField<Record, String> SPECIFIC_CATALOG;
   public static final TableField<Record, String> SPECIFIC_SCHEMA;
   public static final TableField<Record, String> SPECIFIC_NAME;
   public static final TableField<Record, Integer> ORDINAL_POSITION;
   public static final TableField<Record, String> PARAMETER_MODE;
   public static final TableField<Record, String> PARAMETER_NAME;
   public static final TableField<Record, String> DATA_TYPE;
   public static final TableField<Record, Integer> CHARACTER_MAXIMUM_LENGTH;
   public static final TableField<Record, Integer> CHARACTER_OCTET_LENGTH;
   public static final TableField<Record, Integer> NUMERIC_PRECISION;
   public static final TableField<Record, Integer> NUMERIC_SCALE;
   public static final TableField<Record, String> CHARACTER_SET_NAME;
   public static final TableField<Record, String> COLLATION_NAME;
   public static final TableField<Record, String> DTD_IDENTIFIER;
   public static final TableField<Record, String> ROUTINE_TYPE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Parameters() {
      this("PARAMETERS", (Table)null);
   }

   private Parameters(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Parameters(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
   }

   static {
      SPECIFIC_CATALOG = createField("SPECIFIC_CATALOG", SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), PARAMETERS, "");
      SPECIFIC_SCHEMA = createField("SPECIFIC_SCHEMA", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), PARAMETERS, "");
      SPECIFIC_NAME = createField("SPECIFIC_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), PARAMETERS, "");
      ORDINAL_POSITION = createField("ORDINAL_POSITION", SQLDataType.INTEGER.nullable(false).defaulted(true), PARAMETERS, "");
      PARAMETER_MODE = createField("PARAMETER_MODE", SQLDataType.VARCHAR.length(5), PARAMETERS, "");
      PARAMETER_NAME = createField("PARAMETER_NAME", SQLDataType.VARCHAR.length(64), PARAMETERS, "");
      DATA_TYPE = createField("DATA_TYPE", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), PARAMETERS, "");
      CHARACTER_MAXIMUM_LENGTH = createField("CHARACTER_MAXIMUM_LENGTH", SQLDataType.INTEGER, PARAMETERS, "");
      CHARACTER_OCTET_LENGTH = createField("CHARACTER_OCTET_LENGTH", SQLDataType.INTEGER, PARAMETERS, "");
      NUMERIC_PRECISION = createField("NUMERIC_PRECISION", SQLDataType.INTEGER, PARAMETERS, "");
      NUMERIC_SCALE = createField("NUMERIC_SCALE", SQLDataType.INTEGER, PARAMETERS, "");
      CHARACTER_SET_NAME = createField("CHARACTER_SET_NAME", SQLDataType.VARCHAR.length(64), PARAMETERS, "");
      COLLATION_NAME = createField("COLLATION_NAME", SQLDataType.VARCHAR.length(64), PARAMETERS, "");
      DTD_IDENTIFIER = createField("DTD_IDENTIFIER", SQLDataType.CLOB.nullable(false), PARAMETERS, "");
      ROUTINE_TYPE = createField("ROUTINE_TYPE", SQLDataType.VARCHAR.length(9).nullable(false).defaulted(true), PARAMETERS, "");
   }
}
