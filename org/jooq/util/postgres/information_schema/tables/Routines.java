package org.jooq.util.postgres.information_schema.tables;

import java.sql.Timestamp;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.information_schema.InformationSchema;

public class Routines extends TableImpl<Record> {
   private static final long serialVersionUID = 218812048L;
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
   public final TableField<Record, Integer> CHARACTER_MAXIMUM_LENGTH;
   public final TableField<Record, Integer> CHARACTER_OCTET_LENGTH;
   public final TableField<Record, String> CHARACTER_SET_CATALOG;
   public final TableField<Record, String> CHARACTER_SET_SCHEMA;
   public final TableField<Record, String> CHARACTER_SET_NAME;
   public final TableField<Record, String> COLLATION_CATALOG;
   public final TableField<Record, String> COLLATION_SCHEMA;
   public final TableField<Record, String> COLLATION_NAME;
   public final TableField<Record, Integer> NUMERIC_PRECISION;
   public final TableField<Record, Integer> NUMERIC_PRECISION_RADIX;
   public final TableField<Record, Integer> NUMERIC_SCALE;
   public final TableField<Record, Integer> DATETIME_PRECISION;
   public final TableField<Record, String> INTERVAL_TYPE;
   public final TableField<Record, Integer> INTERVAL_PRECISION;
   public final TableField<Record, String> TYPE_UDT_CATALOG;
   public final TableField<Record, String> TYPE_UDT_SCHEMA;
   public final TableField<Record, String> TYPE_UDT_NAME;
   public final TableField<Record, String> SCOPE_CATALOG;
   public final TableField<Record, String> SCOPE_SCHEMA;
   public final TableField<Record, String> SCOPE_NAME;
   public final TableField<Record, Integer> MAXIMUM_CARDINALITY;
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
   public final TableField<Record, Integer> MAX_DYNAMIC_RESULT_SETS;
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
   public final TableField<Record, Integer> RESULT_CAST_CHAR_MAX_LENGTH;
   public final TableField<Record, Integer> RESULT_CAST_CHAR_OCTET_LENGTH;
   public final TableField<Record, String> RESULT_CAST_CHAR_SET_CATALOG;
   public final TableField<Record, String> RESULT_CAST_CHAR_SET_SCHEMA;
   public final TableField<Record, String> RESULT_CAST_CHARACTER_SET_NAME;
   public final TableField<Record, String> RESULT_CAST_COLLATION_CATALOG;
   public final TableField<Record, String> RESULT_CAST_COLLATION_SCHEMA;
   public final TableField<Record, String> RESULT_CAST_COLLATION_NAME;
   public final TableField<Record, Integer> RESULT_CAST_NUMERIC_PRECISION;
   public final TableField<Record, Integer> RESULT_CAST_NUMERIC_PRECISION_RADIX;
   public final TableField<Record, Integer> RESULT_CAST_NUMERIC_SCALE;
   public final TableField<Record, Integer> RESULT_CAST_DATETIME_PRECISION;
   public final TableField<Record, String> RESULT_CAST_INTERVAL_TYPE;
   public final TableField<Record, Integer> RESULT_CAST_INTERVAL_PRECISION;
   public final TableField<Record, String> RESULT_CAST_TYPE_UDT_CATALOG;
   public final TableField<Record, String> RESULT_CAST_TYPE_UDT_SCHEMA;
   public final TableField<Record, String> RESULT_CAST_TYPE_UDT_NAME;
   public final TableField<Record, String> RESULT_CAST_SCOPE_CATALOG;
   public final TableField<Record, String> RESULT_CAST_SCOPE_SCHEMA;
   public final TableField<Record, String> RESULT_CAST_SCOPE_NAME;
   public final TableField<Record, Integer> RESULT_CAST_MAXIMUM_CARDINALITY;
   public final TableField<Record, String> RESULT_CAST_DTD_IDENTIFIER;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Routines() {
      this("routines", (Table)null);
   }

   public Routines(String alias) {
      this(alias, ROUTINES);
   }

   private Routines(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Routines(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.SPECIFIC_CATALOG = createField("specific_catalog", SQLDataType.VARCHAR, this, "");
      this.SPECIFIC_SCHEMA = createField("specific_schema", SQLDataType.VARCHAR, this, "");
      this.SPECIFIC_NAME = createField("specific_name", SQLDataType.VARCHAR, this, "");
      this.ROUTINE_CATALOG = createField("routine_catalog", SQLDataType.VARCHAR, this, "");
      this.ROUTINE_SCHEMA = createField("routine_schema", SQLDataType.VARCHAR, this, "");
      this.ROUTINE_NAME = createField("routine_name", SQLDataType.VARCHAR, this, "");
      this.ROUTINE_TYPE = createField("routine_type", SQLDataType.VARCHAR, this, "");
      this.MODULE_CATALOG = createField("module_catalog", SQLDataType.VARCHAR, this, "");
      this.MODULE_SCHEMA = createField("module_schema", SQLDataType.VARCHAR, this, "");
      this.MODULE_NAME = createField("module_name", SQLDataType.VARCHAR, this, "");
      this.UDT_CATALOG = createField("udt_catalog", SQLDataType.VARCHAR, this, "");
      this.UDT_SCHEMA = createField("udt_schema", SQLDataType.VARCHAR, this, "");
      this.UDT_NAME = createField("udt_name", SQLDataType.VARCHAR, this, "");
      this.DATA_TYPE = createField("data_type", SQLDataType.VARCHAR, this, "");
      this.CHARACTER_MAXIMUM_LENGTH = createField("character_maximum_length", SQLDataType.INTEGER, this, "");
      this.CHARACTER_OCTET_LENGTH = createField("character_octet_length", SQLDataType.INTEGER, this, "");
      this.CHARACTER_SET_CATALOG = createField("character_set_catalog", SQLDataType.VARCHAR, this, "");
      this.CHARACTER_SET_SCHEMA = createField("character_set_schema", SQLDataType.VARCHAR, this, "");
      this.CHARACTER_SET_NAME = createField("character_set_name", SQLDataType.VARCHAR, this, "");
      this.COLLATION_CATALOG = createField("collation_catalog", SQLDataType.VARCHAR, this, "");
      this.COLLATION_SCHEMA = createField("collation_schema", SQLDataType.VARCHAR, this, "");
      this.COLLATION_NAME = createField("collation_name", SQLDataType.VARCHAR, this, "");
      this.NUMERIC_PRECISION = createField("numeric_precision", SQLDataType.INTEGER, this, "");
      this.NUMERIC_PRECISION_RADIX = createField("numeric_precision_radix", SQLDataType.INTEGER, this, "");
      this.NUMERIC_SCALE = createField("numeric_scale", SQLDataType.INTEGER, this, "");
      this.DATETIME_PRECISION = createField("datetime_precision", SQLDataType.INTEGER, this, "");
      this.INTERVAL_TYPE = createField("interval_type", SQLDataType.VARCHAR, this, "");
      this.INTERVAL_PRECISION = createField("interval_precision", SQLDataType.INTEGER, this, "");
      this.TYPE_UDT_CATALOG = createField("type_udt_catalog", SQLDataType.VARCHAR, this, "");
      this.TYPE_UDT_SCHEMA = createField("type_udt_schema", SQLDataType.VARCHAR, this, "");
      this.TYPE_UDT_NAME = createField("type_udt_name", SQLDataType.VARCHAR, this, "");
      this.SCOPE_CATALOG = createField("scope_catalog", SQLDataType.VARCHAR, this, "");
      this.SCOPE_SCHEMA = createField("scope_schema", SQLDataType.VARCHAR, this, "");
      this.SCOPE_NAME = createField("scope_name", SQLDataType.VARCHAR, this, "");
      this.MAXIMUM_CARDINALITY = createField("maximum_cardinality", SQLDataType.INTEGER, this, "");
      this.DTD_IDENTIFIER = createField("dtd_identifier", SQLDataType.VARCHAR, this, "");
      this.ROUTINE_BODY = createField("routine_body", SQLDataType.VARCHAR, this, "");
      this.ROUTINE_DEFINITION = createField("routine_definition", SQLDataType.VARCHAR, this, "");
      this.EXTERNAL_NAME = createField("external_name", SQLDataType.VARCHAR, this, "");
      this.EXTERNAL_LANGUAGE = createField("external_language", SQLDataType.VARCHAR, this, "");
      this.PARAMETER_STYLE = createField("parameter_style", SQLDataType.VARCHAR, this, "");
      this.IS_DETERMINISTIC = createField("is_deterministic", SQLDataType.VARCHAR.length(3), this, "");
      this.SQL_DATA_ACCESS = createField("sql_data_access", SQLDataType.VARCHAR, this, "");
      this.IS_NULL_CALL = createField("is_null_call", SQLDataType.VARCHAR.length(3), this, "");
      this.SQL_PATH = createField("sql_path", SQLDataType.VARCHAR, this, "");
      this.SCHEMA_LEVEL_ROUTINE = createField("schema_level_routine", SQLDataType.VARCHAR.length(3), this, "");
      this.MAX_DYNAMIC_RESULT_SETS = createField("max_dynamic_result_sets", SQLDataType.INTEGER, this, "");
      this.IS_USER_DEFINED_CAST = createField("is_user_defined_cast", SQLDataType.VARCHAR.length(3), this, "");
      this.IS_IMPLICITLY_INVOCABLE = createField("is_implicitly_invocable", SQLDataType.VARCHAR.length(3), this, "");
      this.SECURITY_TYPE = createField("security_type", SQLDataType.VARCHAR, this, "");
      this.TO_SQL_SPECIFIC_CATALOG = createField("to_sql_specific_catalog", SQLDataType.VARCHAR, this, "");
      this.TO_SQL_SPECIFIC_SCHEMA = createField("to_sql_specific_schema", SQLDataType.VARCHAR, this, "");
      this.TO_SQL_SPECIFIC_NAME = createField("to_sql_specific_name", SQLDataType.VARCHAR, this, "");
      this.AS_LOCATOR = createField("as_locator", SQLDataType.VARCHAR.length(3), this, "");
      this.CREATED = createField("created", SQLDataType.TIMESTAMP, this, "");
      this.LAST_ALTERED = createField("last_altered", SQLDataType.TIMESTAMP, this, "");
      this.NEW_SAVEPOINT_LEVEL = createField("new_savepoint_level", SQLDataType.VARCHAR.length(3), this, "");
      this.IS_UDT_DEPENDENT = createField("is_udt_dependent", SQLDataType.VARCHAR.length(3), this, "");
      this.RESULT_CAST_FROM_DATA_TYPE = createField("result_cast_from_data_type", SQLDataType.VARCHAR, this, "");
      this.RESULT_CAST_AS_LOCATOR = createField("result_cast_as_locator", SQLDataType.VARCHAR.length(3), this, "");
      this.RESULT_CAST_CHAR_MAX_LENGTH = createField("result_cast_char_max_length", SQLDataType.INTEGER, this, "");
      this.RESULT_CAST_CHAR_OCTET_LENGTH = createField("result_cast_char_octet_length", SQLDataType.INTEGER, this, "");
      this.RESULT_CAST_CHAR_SET_CATALOG = createField("result_cast_char_set_catalog", SQLDataType.VARCHAR, this, "");
      this.RESULT_CAST_CHAR_SET_SCHEMA = createField("result_cast_char_set_schema", SQLDataType.VARCHAR, this, "");
      this.RESULT_CAST_CHARACTER_SET_NAME = createField("result_cast_character_set_name", SQLDataType.VARCHAR, this, "");
      this.RESULT_CAST_COLLATION_CATALOG = createField("result_cast_collation_catalog", SQLDataType.VARCHAR, this, "");
      this.RESULT_CAST_COLLATION_SCHEMA = createField("result_cast_collation_schema", SQLDataType.VARCHAR, this, "");
      this.RESULT_CAST_COLLATION_NAME = createField("result_cast_collation_name", SQLDataType.VARCHAR, this, "");
      this.RESULT_CAST_NUMERIC_PRECISION = createField("result_cast_numeric_precision", SQLDataType.INTEGER, this, "");
      this.RESULT_CAST_NUMERIC_PRECISION_RADIX = createField("result_cast_numeric_precision_radix", SQLDataType.INTEGER, this, "");
      this.RESULT_CAST_NUMERIC_SCALE = createField("result_cast_numeric_scale", SQLDataType.INTEGER, this, "");
      this.RESULT_CAST_DATETIME_PRECISION = createField("result_cast_datetime_precision", SQLDataType.INTEGER, this, "");
      this.RESULT_CAST_INTERVAL_TYPE = createField("result_cast_interval_type", SQLDataType.VARCHAR, this, "");
      this.RESULT_CAST_INTERVAL_PRECISION = createField("result_cast_interval_precision", SQLDataType.INTEGER, this, "");
      this.RESULT_CAST_TYPE_UDT_CATALOG = createField("result_cast_type_udt_catalog", SQLDataType.VARCHAR, this, "");
      this.RESULT_CAST_TYPE_UDT_SCHEMA = createField("result_cast_type_udt_schema", SQLDataType.VARCHAR, this, "");
      this.RESULT_CAST_TYPE_UDT_NAME = createField("result_cast_type_udt_name", SQLDataType.VARCHAR, this, "");
      this.RESULT_CAST_SCOPE_CATALOG = createField("result_cast_scope_catalog", SQLDataType.VARCHAR, this, "");
      this.RESULT_CAST_SCOPE_SCHEMA = createField("result_cast_scope_schema", SQLDataType.VARCHAR, this, "");
      this.RESULT_CAST_SCOPE_NAME = createField("result_cast_scope_name", SQLDataType.VARCHAR, this, "");
      this.RESULT_CAST_MAXIMUM_CARDINALITY = createField("result_cast_maximum_cardinality", SQLDataType.INTEGER, this, "");
      this.RESULT_CAST_DTD_IDENTIFIER = createField("result_cast_dtd_identifier", SQLDataType.VARCHAR, this, "");
   }

   public Routines as(String alias) {
      return new Routines(alias, this);
   }

   public Routines rename(String name) {
      return new Routines(name, (Table)null);
   }
}
