package org.jooq.util.hsqldb.information_schema.tables;

import java.sql.Timestamp;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.hsqldb.information_schema.InformationSchema;

public class Routines extends TableImpl<Record> {
   private static final long serialVersionUID = 2031776439L;
   public static final Routines ROUTINES = new Routines();
   public final TableField<Record, String> SPECIFIC_CATALOG;
   public final TableField<Record, String> SPECIFIC_SCHEMA;
   public final TableField<Record, String> SPECIFIC_NAME;
   public final TableField<Record, String> ROUTINE_CATALOG;
   public final TableField<Record, String> ROUTINE_SCHEMA;
   public final TableField<Record, String> ROUTINE_NAME;
   public final TableField<Record, String> ROUTINE_TYPE;
   public final TableField<Record, String> MODULE_CATALOG;
   public final TableField<Record, String> MODULE_SCHEMA;
   public final TableField<Record, String> MODULE_NAME;
   public final TableField<Record, String> UDT_CATALOG;
   public final TableField<Record, String> UDT_SCHEMA;
   public final TableField<Record, String> UDT_NAME;
   public final TableField<Record, String> DATA_TYPE;
   public final TableField<Record, Long> CHARACTER_MAXIMUM_LENGTH;
   public final TableField<Record, Long> CHARACTER_OCTET_LENGTH;
   public final TableField<Record, String> CHARACTER_SET_CATALOG;
   public final TableField<Record, String> CHARACTER_SET_SCHEMA;
   public final TableField<Record, String> CHARACTER_SET_NAME;
   public final TableField<Record, String> COLLATION_CATALOG;
   public final TableField<Record, String> COLLATION_SCHEMA;
   public final TableField<Record, String> COLLATION_NAME;
   public final TableField<Record, Long> NUMERIC_PRECISION;
   public final TableField<Record, Long> NUMERIC_PRECISION_RADIX;
   public final TableField<Record, Long> NUMERIC_SCALE;
   public final TableField<Record, Long> DATETIME_PRECISION;
   public final TableField<Record, String> INTERVAL_TYPE;
   public final TableField<Record, Long> INTERVAL_PRECISION;
   public final TableField<Record, String> TYPE_UDT_CATALOG;
   public final TableField<Record, String> TYPE_UDT_SCHEMA;
   public final TableField<Record, String> TYPE_UDT_NAME;
   public final TableField<Record, String> SCOPE_CATALOG;
   public final TableField<Record, String> SCOPE_SCHEMA;
   public final TableField<Record, String> SCOPE_NAME;
   public final TableField<Record, Long> MAXIMUM_CARDINALITY;
   public final TableField<Record, String> DTD_IDENTIFIER;
   public final TableField<Record, String> ROUTINE_BODY;
   public final TableField<Record, String> ROUTINE_DEFINITION;
   public final TableField<Record, String> EXTERNAL_NAME;
   public final TableField<Record, String> EXTERNAL_LANGUAGE;
   public final TableField<Record, String> PARAMETER_STYLE;
   public final TableField<Record, String> IS_DETERMINISTIC;
   public final TableField<Record, String> SQL_DATA_ACCESS;
   public final TableField<Record, String> IS_NULL_CALL;
   public final TableField<Record, String> SQL_PATH;
   public final TableField<Record, String> SCHEMA_LEVEL_ROUTINE;
   public final TableField<Record, Long> MAX_DYNAMIC_RESULT_SETS;
   public final TableField<Record, String> IS_USER_DEFINED_CAST;
   public final TableField<Record, String> IS_IMPLICITLY_INVOCABLE;
   public final TableField<Record, String> SECURITY_TYPE;
   public final TableField<Record, String> TO_SQL_SPECIFIC_CATALOG;
   public final TableField<Record, String> TO_SQL_SPECIFIC_SCHEMA;
   public final TableField<Record, String> TO_SQL_SPECIFIC_NAME;
   public final TableField<Record, String> AS_LOCATOR;
   public final TableField<Record, Timestamp> CREATED;
   public final TableField<Record, Timestamp> LAST_ALTERED;
   public final TableField<Record, String> NEW_SAVEPOINT_LEVEL;
   public final TableField<Record, String> IS_UDT_DEPENDENT;
   public final TableField<Record, String> RESULT_CAST_FROM_DATA_TYPE;
   public final TableField<Record, String> RESULT_CAST_AS_LOCATOR;
   public final TableField<Record, Long> RESULT_CAST_CHAR_MAX_LENGTH;
   public final TableField<Record, Long> RESULT_CAST_CHAR_OCTET_LENGTH;
   public final TableField<Record, String> RESULT_CAST_CHAR_SET_CATALOG;
   public final TableField<Record, String> RESULT_CAST_CHAR_SET_SCHEMA;
   public final TableField<Record, String> RESULT_CAST_CHARACTER_SET_NAME;
   public final TableField<Record, String> RESULT_CAST_COLLATION_CATALOG;
   public final TableField<Record, String> RESULT_CAST_COLLATION_SCHEMA;
   public final TableField<Record, String> RESULT_CAST_COLLATION_NAME;
   public final TableField<Record, Long> RESULT_CAST_NUMERIC_PRECISION;
   public final TableField<Record, Long> RESULT_CAST_NUMERIC_RADIX;
   public final TableField<Record, Long> RESULT_CAST_NUMERIC_SCALE;
   public final TableField<Record, Long> RESULT_CAST_DATETIME_PRECISION;
   public final TableField<Record, String> RESULT_CAST_INTERVAL_TYPE;
   public final TableField<Record, Long> RESULT_CAST_INTERVAL_PRECISION;
   public final TableField<Record, String> RESULT_CAST_TYPE_UDT_CATALOG;
   public final TableField<Record, String> RESULT_CAST_TYPE_UDT_SCHEMA;
   public final TableField<Record, String> RESULT_CAST_TYPE_UDT_NAME;
   public final TableField<Record, String> RESULT_CAST_SCOPE_CATALOG;
   public final TableField<Record, String> RESULT_CAST_SCOPE_SCHEMA;
   public final TableField<Record, String> RESULT_CAST_SCOPE_NAME;
   public final TableField<Record, Long> RESULT_CAST_MAX_CARDINALITY;
   public final TableField<Record, String> RESULT_CAST_DTD_IDENTIFIER;
   public final TableField<Record, String> DECLARED_DATA_TYPE;
   public final TableField<Record, Long> DECLARED_NUMERIC_PRECISION;
   public final TableField<Record, Long> DECLARED_NUMERIC_SCALE;
   public final TableField<Record, String> RESULT_CAST_FROM_DECLARED_DATA_TYPE;
   public final TableField<Record, Long> RESULT_CAST_DECLARED_NUMERIC_PRECISION;
   public final TableField<Record, Long> RESULT_CAST_DECLARED_NUMERIC_SCALE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Routines() {
      this("ROUTINES", (Table)null);
   }

   public Routines(String alias) {
      this(alias, ROUTINES);
   }

   private Routines(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Routines(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.SPECIFIC_CATALOG = createField("SPECIFIC_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.SPECIFIC_SCHEMA = createField("SPECIFIC_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.SPECIFIC_NAME = createField("SPECIFIC_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.ROUTINE_CATALOG = createField("ROUTINE_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.ROUTINE_SCHEMA = createField("ROUTINE_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.ROUTINE_NAME = createField("ROUTINE_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.ROUTINE_TYPE = createField("ROUTINE_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.MODULE_CATALOG = createField("MODULE_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.MODULE_SCHEMA = createField("MODULE_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.MODULE_NAME = createField("MODULE_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.UDT_CATALOG = createField("UDT_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.UDT_SCHEMA = createField("UDT_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.UDT_NAME = createField("UDT_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.DATA_TYPE = createField("DATA_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.CHARACTER_MAXIMUM_LENGTH = createField("CHARACTER_MAXIMUM_LENGTH", SQLDataType.BIGINT, this, "");
      this.CHARACTER_OCTET_LENGTH = createField("CHARACTER_OCTET_LENGTH", SQLDataType.BIGINT, this, "");
      this.CHARACTER_SET_CATALOG = createField("CHARACTER_SET_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.CHARACTER_SET_SCHEMA = createField("CHARACTER_SET_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.CHARACTER_SET_NAME = createField("CHARACTER_SET_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.COLLATION_CATALOG = createField("COLLATION_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.COLLATION_SCHEMA = createField("COLLATION_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.COLLATION_NAME = createField("COLLATION_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.NUMERIC_PRECISION = createField("NUMERIC_PRECISION", SQLDataType.BIGINT, this, "");
      this.NUMERIC_PRECISION_RADIX = createField("NUMERIC_PRECISION_RADIX", SQLDataType.BIGINT, this, "");
      this.NUMERIC_SCALE = createField("NUMERIC_SCALE", SQLDataType.BIGINT, this, "");
      this.DATETIME_PRECISION = createField("DATETIME_PRECISION", SQLDataType.BIGINT, this, "");
      this.INTERVAL_TYPE = createField("INTERVAL_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.INTERVAL_PRECISION = createField("INTERVAL_PRECISION", SQLDataType.BIGINT, this, "");
      this.TYPE_UDT_CATALOG = createField("TYPE_UDT_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.TYPE_UDT_SCHEMA = createField("TYPE_UDT_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.TYPE_UDT_NAME = createField("TYPE_UDT_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.SCOPE_CATALOG = createField("SCOPE_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.SCOPE_SCHEMA = createField("SCOPE_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.SCOPE_NAME = createField("SCOPE_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.MAXIMUM_CARDINALITY = createField("MAXIMUM_CARDINALITY", SQLDataType.BIGINT, this, "");
      this.DTD_IDENTIFIER = createField("DTD_IDENTIFIER", SQLDataType.VARCHAR.length(128), this, "");
      this.ROUTINE_BODY = createField("ROUTINE_BODY", SQLDataType.VARCHAR.length(65536), this, "");
      this.ROUTINE_DEFINITION = createField("ROUTINE_DEFINITION", SQLDataType.VARCHAR.length(65536), this, "");
      this.EXTERNAL_NAME = createField("EXTERNAL_NAME", SQLDataType.VARCHAR.length(65536), this, "");
      this.EXTERNAL_LANGUAGE = createField("EXTERNAL_LANGUAGE", SQLDataType.VARCHAR.length(65536), this, "");
      this.PARAMETER_STYLE = createField("PARAMETER_STYLE", SQLDataType.VARCHAR.length(65536), this, "");
      this.IS_DETERMINISTIC = createField("IS_DETERMINISTIC", SQLDataType.VARCHAR.length(3), this, "");
      this.SQL_DATA_ACCESS = createField("SQL_DATA_ACCESS", SQLDataType.VARCHAR.length(65536), this, "");
      this.IS_NULL_CALL = createField("IS_NULL_CALL", SQLDataType.VARCHAR.length(3), this, "");
      this.SQL_PATH = createField("SQL_PATH", SQLDataType.VARCHAR.length(65536), this, "");
      this.SCHEMA_LEVEL_ROUTINE = createField("SCHEMA_LEVEL_ROUTINE", SQLDataType.VARCHAR.length(3), this, "");
      this.MAX_DYNAMIC_RESULT_SETS = createField("MAX_DYNAMIC_RESULT_SETS", SQLDataType.BIGINT, this, "");
      this.IS_USER_DEFINED_CAST = createField("IS_USER_DEFINED_CAST", SQLDataType.VARCHAR.length(3), this, "");
      this.IS_IMPLICITLY_INVOCABLE = createField("IS_IMPLICITLY_INVOCABLE", SQLDataType.VARCHAR.length(3), this, "");
      this.SECURITY_TYPE = createField("SECURITY_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.TO_SQL_SPECIFIC_CATALOG = createField("TO_SQL_SPECIFIC_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.TO_SQL_SPECIFIC_SCHEMA = createField("TO_SQL_SPECIFIC_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.TO_SQL_SPECIFIC_NAME = createField("TO_SQL_SPECIFIC_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.AS_LOCATOR = createField("AS_LOCATOR", SQLDataType.VARCHAR.length(3), this, "");
      this.CREATED = createField("CREATED", SQLDataType.TIMESTAMP, this, "");
      this.LAST_ALTERED = createField("LAST_ALTERED", SQLDataType.TIMESTAMP, this, "");
      this.NEW_SAVEPOINT_LEVEL = createField("NEW_SAVEPOINT_LEVEL", SQLDataType.VARCHAR.length(3), this, "");
      this.IS_UDT_DEPENDENT = createField("IS_UDT_DEPENDENT", SQLDataType.VARCHAR.length(3), this, "");
      this.RESULT_CAST_FROM_DATA_TYPE = createField("RESULT_CAST_FROM_DATA_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.RESULT_CAST_AS_LOCATOR = createField("RESULT_CAST_AS_LOCATOR", SQLDataType.VARCHAR.length(3), this, "");
      this.RESULT_CAST_CHAR_MAX_LENGTH = createField("RESULT_CAST_CHAR_MAX_LENGTH", SQLDataType.BIGINT, this, "");
      this.RESULT_CAST_CHAR_OCTET_LENGTH = createField("RESULT_CAST_CHAR_OCTET_LENGTH", SQLDataType.BIGINT, this, "");
      this.RESULT_CAST_CHAR_SET_CATALOG = createField("RESULT_CAST_CHAR_SET_CATALOG", SQLDataType.VARCHAR.length(65536), this, "");
      this.RESULT_CAST_CHAR_SET_SCHEMA = createField("RESULT_CAST_CHAR_SET_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.RESULT_CAST_CHARACTER_SET_NAME = createField("RESULT_CAST_CHARACTER_SET_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.RESULT_CAST_COLLATION_CATALOG = createField("RESULT_CAST_COLLATION_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.RESULT_CAST_COLLATION_SCHEMA = createField("RESULT_CAST_COLLATION_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.RESULT_CAST_COLLATION_NAME = createField("RESULT_CAST_COLLATION_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.RESULT_CAST_NUMERIC_PRECISION = createField("RESULT_CAST_NUMERIC_PRECISION", SQLDataType.BIGINT, this, "");
      this.RESULT_CAST_NUMERIC_RADIX = createField("RESULT_CAST_NUMERIC_RADIX", SQLDataType.BIGINT, this, "");
      this.RESULT_CAST_NUMERIC_SCALE = createField("RESULT_CAST_NUMERIC_SCALE", SQLDataType.BIGINT, this, "");
      this.RESULT_CAST_DATETIME_PRECISION = createField("RESULT_CAST_DATETIME_PRECISION", SQLDataType.BIGINT, this, "");
      this.RESULT_CAST_INTERVAL_TYPE = createField("RESULT_CAST_INTERVAL_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.RESULT_CAST_INTERVAL_PRECISION = createField("RESULT_CAST_INTERVAL_PRECISION", SQLDataType.BIGINT, this, "");
      this.RESULT_CAST_TYPE_UDT_CATALOG = createField("RESULT_CAST_TYPE_UDT_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.RESULT_CAST_TYPE_UDT_SCHEMA = createField("RESULT_CAST_TYPE_UDT_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.RESULT_CAST_TYPE_UDT_NAME = createField("RESULT_CAST_TYPE_UDT_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.RESULT_CAST_SCOPE_CATALOG = createField("RESULT_CAST_SCOPE_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.RESULT_CAST_SCOPE_SCHEMA = createField("RESULT_CAST_SCOPE_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.RESULT_CAST_SCOPE_NAME = createField("RESULT_CAST_SCOPE_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.RESULT_CAST_MAX_CARDINALITY = createField("RESULT_CAST_MAX_CARDINALITY", SQLDataType.BIGINT, this, "");
      this.RESULT_CAST_DTD_IDENTIFIER = createField("RESULT_CAST_DTD_IDENTIFIER", SQLDataType.VARCHAR.length(65536), this, "");
      this.DECLARED_DATA_TYPE = createField("DECLARED_DATA_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.DECLARED_NUMERIC_PRECISION = createField("DECLARED_NUMERIC_PRECISION", SQLDataType.BIGINT, this, "");
      this.DECLARED_NUMERIC_SCALE = createField("DECLARED_NUMERIC_SCALE", SQLDataType.BIGINT, this, "");
      this.RESULT_CAST_FROM_DECLARED_DATA_TYPE = createField("RESULT_CAST_FROM_DECLARED_DATA_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.RESULT_CAST_DECLARED_NUMERIC_PRECISION = createField("RESULT_CAST_DECLARED_NUMERIC_PRECISION", SQLDataType.BIGINT, this, "");
      this.RESULT_CAST_DECLARED_NUMERIC_SCALE = createField("RESULT_CAST_DECLARED_NUMERIC_SCALE", SQLDataType.BIGINT, this, "");
   }

   public Routines as(String alias) {
      return new Routines(alias, this);
   }

   public Routines rename(String name) {
      return new Routines(name, (Table)null);
   }
}
