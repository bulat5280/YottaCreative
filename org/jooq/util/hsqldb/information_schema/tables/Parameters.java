package org.jooq.util.hsqldb.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.hsqldb.information_schema.InformationSchema;

public class Parameters extends TableImpl<Record> {
   private static final long serialVersionUID = -803327563L;
   public static final Parameters PARAMETERS = new Parameters();
   public final TableField<Record, String> SPECIFIC_CATALOG;
   public final TableField<Record, String> SPECIFIC_SCHEMA;
   public final TableField<Record, String> SPECIFIC_NAME;
   public final TableField<Record, Long> ORDINAL_POSITION;
   public final TableField<Record, String> PARAMETER_MODE;
   public final TableField<Record, String> IS_RESULT;
   public final TableField<Record, String> AS_LOCATOR;
   public final TableField<Record, String> PARAMETER_NAME;
   public final TableField<Record, String> FROM_SQL_SPECIFIC_CATALOG;
   public final TableField<Record, String> FROM_SQL_SPECIFIC_SCHEMA;
   public final TableField<Record, String> FROM_SQL_SPECIFIC_NAME;
   public final TableField<Record, String> TO_SQL_SPECIFIC_CATALOG;
   public final TableField<Record, String> TO_SQL_SPECIFIC_SCHEMA;
   public final TableField<Record, String> TO_SQL_SPECIFIC_NAME;
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
   public final TableField<Record, String> UDT_CATALOG;
   public final TableField<Record, String> UDT_SCHEMA;
   public final TableField<Record, String> UDT_NAME;
   public final TableField<Record, String> SCOPE_CATALOG;
   public final TableField<Record, String> SCOPE_SCHEMA;
   public final TableField<Record, String> SCOPE_NAME;
   public final TableField<Record, Long> MAXIMUM_CARDINALITY;
   public final TableField<Record, String> DTD_IDENTIFIER;
   public final TableField<Record, String> DECLARED_DATA_TYPE;
   public final TableField<Record, Long> DECLARED_NUMERIC_PRECISION;
   public final TableField<Record, Long> DECLARED_NUMERIC_SCALE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Parameters() {
      this("PARAMETERS", (Table)null);
   }

   public Parameters(String alias) {
      this(alias, PARAMETERS);
   }

   private Parameters(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Parameters(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.SPECIFIC_CATALOG = createField("SPECIFIC_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.SPECIFIC_SCHEMA = createField("SPECIFIC_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.SPECIFIC_NAME = createField("SPECIFIC_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.ORDINAL_POSITION = createField("ORDINAL_POSITION", SQLDataType.BIGINT, this, "");
      this.PARAMETER_MODE = createField("PARAMETER_MODE", SQLDataType.VARCHAR.length(65536), this, "");
      this.IS_RESULT = createField("IS_RESULT", SQLDataType.VARCHAR.length(3), this, "");
      this.AS_LOCATOR = createField("AS_LOCATOR", SQLDataType.VARCHAR.length(3), this, "");
      this.PARAMETER_NAME = createField("PARAMETER_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.FROM_SQL_SPECIFIC_CATALOG = createField("FROM_SQL_SPECIFIC_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.FROM_SQL_SPECIFIC_SCHEMA = createField("FROM_SQL_SPECIFIC_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.FROM_SQL_SPECIFIC_NAME = createField("FROM_SQL_SPECIFIC_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.TO_SQL_SPECIFIC_CATALOG = createField("TO_SQL_SPECIFIC_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.TO_SQL_SPECIFIC_SCHEMA = createField("TO_SQL_SPECIFIC_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.TO_SQL_SPECIFIC_NAME = createField("TO_SQL_SPECIFIC_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.DATA_TYPE = createField("DATA_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.CHARACTER_MAXIMUM_LENGTH = createField("CHARACTER_MAXIMUM_LENGTH", SQLDataType.BIGINT, this, "");
      this.CHARACTER_OCTET_LENGTH = createField("CHARACTER_OCTET_LENGTH", SQLDataType.BIGINT, this, "");
      this.CHARACTER_SET_CATALOG = createField("CHARACTER_SET_CATALOG", SQLDataType.VARCHAR.length(65536), this, "");
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
      this.UDT_CATALOG = createField("UDT_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.UDT_SCHEMA = createField("UDT_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.UDT_NAME = createField("UDT_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.SCOPE_CATALOG = createField("SCOPE_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.SCOPE_SCHEMA = createField("SCOPE_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.SCOPE_NAME = createField("SCOPE_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.MAXIMUM_CARDINALITY = createField("MAXIMUM_CARDINALITY", SQLDataType.BIGINT, this, "");
      this.DTD_IDENTIFIER = createField("DTD_IDENTIFIER", SQLDataType.VARCHAR.length(128), this, "");
      this.DECLARED_DATA_TYPE = createField("DECLARED_DATA_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.DECLARED_NUMERIC_PRECISION = createField("DECLARED_NUMERIC_PRECISION", SQLDataType.BIGINT, this, "");
      this.DECLARED_NUMERIC_SCALE = createField("DECLARED_NUMERIC_SCALE", SQLDataType.BIGINT, this, "");
   }

   public Parameters as(String alias) {
      return new Parameters(alias, this);
   }

   public Parameters rename(String name) {
      return new Parameters(name, (Table)null);
   }
}
