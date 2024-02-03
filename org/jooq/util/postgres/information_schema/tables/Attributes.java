package org.jooq.util.postgres.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.information_schema.InformationSchema;

public class Attributes extends TableImpl<Record> {
   private static final long serialVersionUID = 2017178996L;
   public static final Attributes ATTRIBUTES = new Attributes();
   public final TableField<Record, String> UDT_CATALOG;
   public final TableField<Record, String> UDT_SCHEMA;
   public final TableField<Record, String> UDT_NAME;
   public final TableField<Record, String> ATTRIBUTE_NAME;
   public final TableField<Record, Integer> ORDINAL_POSITION;
   public final TableField<Record, String> ATTRIBUTE_DEFAULT;
   public final TableField<Record, String> IS_NULLABLE;
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
   public final TableField<Record, String> ATTRIBUTE_UDT_CATALOG;
   public final TableField<Record, String> ATTRIBUTE_UDT_SCHEMA;
   public final TableField<Record, String> ATTRIBUTE_UDT_NAME;
   public final TableField<Record, String> SCOPE_CATALOG;
   public final TableField<Record, String> SCOPE_SCHEMA;
   public final TableField<Record, String> SCOPE_NAME;
   public final TableField<Record, Integer> MAXIMUM_CARDINALITY;
   public final TableField<Record, String> DTD_IDENTIFIER;
   public final TableField<Record, String> IS_DERIVED_REFERENCE_ATTRIBUTE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Attributes() {
      this("attributes", (Table)null);
   }

   public Attributes(String alias) {
      this(alias, ATTRIBUTES);
   }

   private Attributes(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Attributes(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.UDT_CATALOG = createField("udt_catalog", SQLDataType.VARCHAR, this, "");
      this.UDT_SCHEMA = createField("udt_schema", SQLDataType.VARCHAR, this, "");
      this.UDT_NAME = createField("udt_name", SQLDataType.VARCHAR, this, "");
      this.ATTRIBUTE_NAME = createField("attribute_name", SQLDataType.VARCHAR, this, "");
      this.ORDINAL_POSITION = createField("ordinal_position", SQLDataType.INTEGER, this, "");
      this.ATTRIBUTE_DEFAULT = createField("attribute_default", SQLDataType.VARCHAR, this, "");
      this.IS_NULLABLE = createField("is_nullable", SQLDataType.VARCHAR.length(3), this, "");
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
      this.ATTRIBUTE_UDT_CATALOG = createField("attribute_udt_catalog", SQLDataType.VARCHAR, this, "");
      this.ATTRIBUTE_UDT_SCHEMA = createField("attribute_udt_schema", SQLDataType.VARCHAR, this, "");
      this.ATTRIBUTE_UDT_NAME = createField("attribute_udt_name", SQLDataType.VARCHAR, this, "");
      this.SCOPE_CATALOG = createField("scope_catalog", SQLDataType.VARCHAR, this, "");
      this.SCOPE_SCHEMA = createField("scope_schema", SQLDataType.VARCHAR, this, "");
      this.SCOPE_NAME = createField("scope_name", SQLDataType.VARCHAR, this, "");
      this.MAXIMUM_CARDINALITY = createField("maximum_cardinality", SQLDataType.INTEGER, this, "");
      this.DTD_IDENTIFIER = createField("dtd_identifier", SQLDataType.VARCHAR, this, "");
      this.IS_DERIVED_REFERENCE_ATTRIBUTE = createField("is_derived_reference_attribute", SQLDataType.VARCHAR.length(3), this, "");
   }

   public Attributes as(String alias) {
      return new Attributes(alias, this);
   }

   public Attributes rename(String name) {
      return new Attributes(name, (Table)null);
   }
}
