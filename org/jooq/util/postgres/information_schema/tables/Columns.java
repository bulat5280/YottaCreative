package org.jooq.util.postgres.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.information_schema.InformationSchema;

public class Columns extends TableImpl<Record> {
   private static final long serialVersionUID = -1925489124L;
   public static final Columns COLUMNS = new Columns();
   public final TableField<Record, String> TABLE_CATALOG;
   public final TableField<Record, String> TABLE_SCHEMA;
   public final TableField<Record, String> TABLE_NAME;
   public final TableField<Record, String> COLUMN_NAME;
   public final TableField<Record, Integer> ORDINAL_POSITION;
   public final TableField<Record, String> COLUMN_DEFAULT;
   public final TableField<Record, String> IS_NULLABLE;
   public final TableField<Record, String> DATA_TYPE;
   public final TableField<Record, Integer> CHARACTER_MAXIMUM_LENGTH;
   public final TableField<Record, Integer> CHARACTER_OCTET_LENGTH;
   public final TableField<Record, Integer> NUMERIC_PRECISION;
   public final TableField<Record, Integer> NUMERIC_PRECISION_RADIX;
   public final TableField<Record, Integer> NUMERIC_SCALE;
   public final TableField<Record, Integer> DATETIME_PRECISION;
   public final TableField<Record, String> INTERVAL_TYPE;
   public final TableField<Record, Integer> INTERVAL_PRECISION;
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
   public final TableField<Record, Integer> MAXIMUM_CARDINALITY;
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

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Columns() {
      this("columns", (Table)null);
   }

   public Columns(String alias) {
      this(alias, COLUMNS);
   }

   private Columns(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Columns(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.TABLE_CATALOG = createField("table_catalog", SQLDataType.VARCHAR, this, "");
      this.TABLE_SCHEMA = createField("table_schema", SQLDataType.VARCHAR, this, "");
      this.TABLE_NAME = createField("table_name", SQLDataType.VARCHAR, this, "");
      this.COLUMN_NAME = createField("column_name", SQLDataType.VARCHAR, this, "");
      this.ORDINAL_POSITION = createField("ordinal_position", SQLDataType.INTEGER, this, "");
      this.COLUMN_DEFAULT = createField("column_default", SQLDataType.VARCHAR, this, "");
      this.IS_NULLABLE = createField("is_nullable", SQLDataType.VARCHAR.length(3), this, "");
      this.DATA_TYPE = createField("data_type", SQLDataType.VARCHAR, this, "");
      this.CHARACTER_MAXIMUM_LENGTH = createField("character_maximum_length", SQLDataType.INTEGER, this, "");
      this.CHARACTER_OCTET_LENGTH = createField("character_octet_length", SQLDataType.INTEGER, this, "");
      this.NUMERIC_PRECISION = createField("numeric_precision", SQLDataType.INTEGER, this, "");
      this.NUMERIC_PRECISION_RADIX = createField("numeric_precision_radix", SQLDataType.INTEGER, this, "");
      this.NUMERIC_SCALE = createField("numeric_scale", SQLDataType.INTEGER, this, "");
      this.DATETIME_PRECISION = createField("datetime_precision", SQLDataType.INTEGER, this, "");
      this.INTERVAL_TYPE = createField("interval_type", SQLDataType.VARCHAR, this, "");
      this.INTERVAL_PRECISION = createField("interval_precision", SQLDataType.INTEGER, this, "");
      this.CHARACTER_SET_CATALOG = createField("character_set_catalog", SQLDataType.VARCHAR, this, "");
      this.CHARACTER_SET_SCHEMA = createField("character_set_schema", SQLDataType.VARCHAR, this, "");
      this.CHARACTER_SET_NAME = createField("character_set_name", SQLDataType.VARCHAR, this, "");
      this.COLLATION_CATALOG = createField("collation_catalog", SQLDataType.VARCHAR, this, "");
      this.COLLATION_SCHEMA = createField("collation_schema", SQLDataType.VARCHAR, this, "");
      this.COLLATION_NAME = createField("collation_name", SQLDataType.VARCHAR, this, "");
      this.DOMAIN_CATALOG = createField("domain_catalog", SQLDataType.VARCHAR, this, "");
      this.DOMAIN_SCHEMA = createField("domain_schema", SQLDataType.VARCHAR, this, "");
      this.DOMAIN_NAME = createField("domain_name", SQLDataType.VARCHAR, this, "");
      this.UDT_CATALOG = createField("udt_catalog", SQLDataType.VARCHAR, this, "");
      this.UDT_SCHEMA = createField("udt_schema", SQLDataType.VARCHAR, this, "");
      this.UDT_NAME = createField("udt_name", SQLDataType.VARCHAR, this, "");
      this.SCOPE_CATALOG = createField("scope_catalog", SQLDataType.VARCHAR, this, "");
      this.SCOPE_SCHEMA = createField("scope_schema", SQLDataType.VARCHAR, this, "");
      this.SCOPE_NAME = createField("scope_name", SQLDataType.VARCHAR, this, "");
      this.MAXIMUM_CARDINALITY = createField("maximum_cardinality", SQLDataType.INTEGER, this, "");
      this.DTD_IDENTIFIER = createField("dtd_identifier", SQLDataType.VARCHAR, this, "");
      this.IS_SELF_REFERENCING = createField("is_self_referencing", SQLDataType.VARCHAR.length(3), this, "");
      this.IS_IDENTITY = createField("is_identity", SQLDataType.VARCHAR.length(3), this, "");
      this.IDENTITY_GENERATION = createField("identity_generation", SQLDataType.VARCHAR, this, "");
      this.IDENTITY_START = createField("identity_start", SQLDataType.VARCHAR, this, "");
      this.IDENTITY_INCREMENT = createField("identity_increment", SQLDataType.VARCHAR, this, "");
      this.IDENTITY_MAXIMUM = createField("identity_maximum", SQLDataType.VARCHAR, this, "");
      this.IDENTITY_MINIMUM = createField("identity_minimum", SQLDataType.VARCHAR, this, "");
      this.IDENTITY_CYCLE = createField("identity_cycle", SQLDataType.VARCHAR.length(3), this, "");
      this.IS_GENERATED = createField("is_generated", SQLDataType.VARCHAR, this, "");
      this.GENERATION_EXPRESSION = createField("generation_expression", SQLDataType.VARCHAR, this, "");
      this.IS_UPDATABLE = createField("is_updatable", SQLDataType.VARCHAR.length(3), this, "");
   }

   public Columns as(String alias) {
      return new Columns(alias, this);
   }

   public Columns rename(String name) {
      return new Columns(name, (Table)null);
   }
}
