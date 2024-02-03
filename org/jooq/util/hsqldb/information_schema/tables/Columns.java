package org.jooq.util.hsqldb.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.hsqldb.information_schema.InformationSchema;

public class Columns extends TableImpl<Record> {
   private static final long serialVersionUID = 2067909884L;
   public static final Columns COLUMNS = new Columns();
   public final TableField<Record, String> TABLE_CATALOG;
   public final TableField<Record, String> TABLE_SCHEMA;
   public final TableField<Record, String> TABLE_NAME;
   public final TableField<Record, String> COLUMN_NAME;
   public final TableField<Record, Long> ORDINAL_POSITION;
   public final TableField<Record, String> COLUMN_DEFAULT;
   public final TableField<Record, String> IS_NULLABLE;
   public final TableField<Record, String> DATA_TYPE;
   public final TableField<Record, Long> CHARACTER_MAXIMUM_LENGTH;
   public final TableField<Record, Long> CHARACTER_OCTET_LENGTH;
   public final TableField<Record, Long> NUMERIC_PRECISION;
   public final TableField<Record, Long> NUMERIC_PRECISION_RADIX;
   public final TableField<Record, Long> NUMERIC_SCALE;
   public final TableField<Record, Long> DATETIME_PRECISION;
   public final TableField<Record, String> INTERVAL_TYPE;
   public final TableField<Record, Long> INTERVAL_PRECISION;
   public final TableField<Record, String> CHARACTER_SET_CATALOG;
   public final TableField<Record, String> CHARACTER_SET_SCHEMA;
   public final TableField<Record, String> CHARACTER_SET_NAME;
   public final TableField<Record, String> COLLATION_CATALOG;
   public final TableField<Record, String> COLLATION_SCHEMA;
   public final TableField<Record, String> COLLATION_NAME;
   public final TableField<Record, String> DOMAIN_CATALOG;
   public final TableField<Record, String> DOMAIN_SCHEMA;
   public final TableField<Record, String> DOMAIN_NAME;
   public final TableField<Record, String> UDT_CATALOG;
   public final TableField<Record, String> UDT_SCHEMA;
   public final TableField<Record, String> UDT_NAME;
   public final TableField<Record, String> SCOPE_CATALOG;
   public final TableField<Record, String> SCOPE_SCHEMA;
   public final TableField<Record, String> SCOPE_NAME;
   public final TableField<Record, Long> MAXIMUM_CARDINALITY;
   public final TableField<Record, String> DTD_IDENTIFIER;
   public final TableField<Record, String> IS_SELF_REFERENCING;
   public final TableField<Record, String> IS_IDENTITY;
   public final TableField<Record, String> IDENTITY_GENERATION;
   public final TableField<Record, String> IDENTITY_START;
   public final TableField<Record, String> IDENTITY_INCREMENT;
   public final TableField<Record, String> IDENTITY_MAXIMUM;
   public final TableField<Record, String> IDENTITY_MINIMUM;
   public final TableField<Record, String> IDENTITY_CYCLE;
   public final TableField<Record, String> IS_GENERATED;
   public final TableField<Record, String> GENERATION_EXPRESSION;
   public final TableField<Record, String> IS_UPDATABLE;
   public final TableField<Record, String> DECLARED_DATA_TYPE;
   public final TableField<Record, Long> DECLARED_NUMERIC_PRECISION;
   public final TableField<Record, Long> DECLARED_NUMERIC_SCALE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Columns() {
      this("COLUMNS", (Table)null);
   }

   public Columns(String alias) {
      this(alias, COLUMNS);
   }

   private Columns(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Columns(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.TABLE_CATALOG = createField("TABLE_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.TABLE_SCHEMA = createField("TABLE_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.TABLE_NAME = createField("TABLE_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.COLUMN_NAME = createField("COLUMN_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.ORDINAL_POSITION = createField("ORDINAL_POSITION", SQLDataType.BIGINT, this, "");
      this.COLUMN_DEFAULT = createField("COLUMN_DEFAULT", SQLDataType.VARCHAR.length(65536), this, "");
      this.IS_NULLABLE = createField("IS_NULLABLE", SQLDataType.VARCHAR.length(3), this, "");
      this.DATA_TYPE = createField("DATA_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.CHARACTER_MAXIMUM_LENGTH = createField("CHARACTER_MAXIMUM_LENGTH", SQLDataType.BIGINT, this, "");
      this.CHARACTER_OCTET_LENGTH = createField("CHARACTER_OCTET_LENGTH", SQLDataType.BIGINT, this, "");
      this.NUMERIC_PRECISION = createField("NUMERIC_PRECISION", SQLDataType.BIGINT, this, "");
      this.NUMERIC_PRECISION_RADIX = createField("NUMERIC_PRECISION_RADIX", SQLDataType.BIGINT, this, "");
      this.NUMERIC_SCALE = createField("NUMERIC_SCALE", SQLDataType.BIGINT, this, "");
      this.DATETIME_PRECISION = createField("DATETIME_PRECISION", SQLDataType.BIGINT, this, "");
      this.INTERVAL_TYPE = createField("INTERVAL_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.INTERVAL_PRECISION = createField("INTERVAL_PRECISION", SQLDataType.BIGINT, this, "");
      this.CHARACTER_SET_CATALOG = createField("CHARACTER_SET_CATALOG", SQLDataType.VARCHAR.length(65536), this, "");
      this.CHARACTER_SET_SCHEMA = createField("CHARACTER_SET_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.CHARACTER_SET_NAME = createField("CHARACTER_SET_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.COLLATION_CATALOG = createField("COLLATION_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.COLLATION_SCHEMA = createField("COLLATION_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.COLLATION_NAME = createField("COLLATION_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.DOMAIN_CATALOG = createField("DOMAIN_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.DOMAIN_SCHEMA = createField("DOMAIN_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.DOMAIN_NAME = createField("DOMAIN_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.UDT_CATALOG = createField("UDT_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.UDT_SCHEMA = createField("UDT_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.UDT_NAME = createField("UDT_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.SCOPE_CATALOG = createField("SCOPE_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.SCOPE_SCHEMA = createField("SCOPE_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.SCOPE_NAME = createField("SCOPE_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.MAXIMUM_CARDINALITY = createField("MAXIMUM_CARDINALITY", SQLDataType.BIGINT, this, "");
      this.DTD_IDENTIFIER = createField("DTD_IDENTIFIER", SQLDataType.VARCHAR.length(128), this, "");
      this.IS_SELF_REFERENCING = createField("IS_SELF_REFERENCING", SQLDataType.VARCHAR.length(3), this, "");
      this.IS_IDENTITY = createField("IS_IDENTITY", SQLDataType.VARCHAR.length(3), this, "");
      this.IDENTITY_GENERATION = createField("IDENTITY_GENERATION", SQLDataType.VARCHAR.length(65536), this, "");
      this.IDENTITY_START = createField("IDENTITY_START", SQLDataType.VARCHAR.length(65536), this, "");
      this.IDENTITY_INCREMENT = createField("IDENTITY_INCREMENT", SQLDataType.VARCHAR.length(65536), this, "");
      this.IDENTITY_MAXIMUM = createField("IDENTITY_MAXIMUM", SQLDataType.VARCHAR.length(65536), this, "");
      this.IDENTITY_MINIMUM = createField("IDENTITY_MINIMUM", SQLDataType.VARCHAR.length(65536), this, "");
      this.IDENTITY_CYCLE = createField("IDENTITY_CYCLE", SQLDataType.VARCHAR.length(3), this, "");
      this.IS_GENERATED = createField("IS_GENERATED", SQLDataType.VARCHAR.length(65536), this, "");
      this.GENERATION_EXPRESSION = createField("GENERATION_EXPRESSION", SQLDataType.VARCHAR.length(65536), this, "");
      this.IS_UPDATABLE = createField("IS_UPDATABLE", SQLDataType.VARCHAR.length(3), this, "");
      this.DECLARED_DATA_TYPE = createField("DECLARED_DATA_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.DECLARED_NUMERIC_PRECISION = createField("DECLARED_NUMERIC_PRECISION", SQLDataType.BIGINT, this, "");
      this.DECLARED_NUMERIC_SCALE = createField("DECLARED_NUMERIC_SCALE", SQLDataType.BIGINT, this, "");
   }

   public Columns as(String alias) {
      return new Columns(alias, this);
   }

   public Columns rename(String name) {
      return new Columns(name, (Table)null);
   }
}
