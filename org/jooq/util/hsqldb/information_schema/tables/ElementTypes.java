package org.jooq.util.hsqldb.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.hsqldb.information_schema.InformationSchema;

public class ElementTypes extends TableImpl<Record> {
   private static final long serialVersionUID = -1323905185L;
   public static final ElementTypes ELEMENT_TYPES = new ElementTypes();
   public final TableField<Record, String> OBJECT_CATALOG;
   public final TableField<Record, String> OBJECT_SCHEMA;
   public final TableField<Record, String> OBJECT_NAME;
   public final TableField<Record, String> OBJECT_TYPE;
   public final TableField<Record, String> COLLECTION_TYPE_IDENTIFIER;
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

   public ElementTypes() {
      this("ELEMENT_TYPES", (Table)null);
   }

   public ElementTypes(String alias) {
      this(alias, ELEMENT_TYPES);
   }

   private ElementTypes(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private ElementTypes(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.OBJECT_CATALOG = createField("OBJECT_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.OBJECT_SCHEMA = createField("OBJECT_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.OBJECT_NAME = createField("OBJECT_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.OBJECT_TYPE = createField("OBJECT_TYPE", SQLDataType.VARCHAR.length(128), this, "");
      this.COLLECTION_TYPE_IDENTIFIER = createField("COLLECTION_TYPE_IDENTIFIER", SQLDataType.VARCHAR.length(128), this, "");
      this.DATA_TYPE = createField("DATA_TYPE", SQLDataType.VARCHAR.length(128), this, "");
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

   public ElementTypes as(String alias) {
      return new ElementTypes(alias, this);
   }

   public ElementTypes rename(String name) {
      return new ElementTypes(name, (Table)null);
   }
}
