package org.jooq.util.hsqldb.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.hsqldb.information_schema.InformationSchema;

public class Sequences extends TableImpl<Record> {
   private static final long serialVersionUID = 1818124946L;
   public static final Sequences SEQUENCES = new Sequences();
   public final TableField<Record, String> SEQUENCE_CATALOG;
   public final TableField<Record, String> SEQUENCE_SCHEMA;
   public final TableField<Record, String> SEQUENCE_NAME;
   public final TableField<Record, String> DATA_TYPE;
   public final TableField<Record, Long> NUMERIC_PRECISION;
   public final TableField<Record, Long> NUMERIC_PRECISION_RADIX;
   public final TableField<Record, Long> NUMERIC_SCALE;
   public final TableField<Record, String> MAXIMUM_VALUE;
   public final TableField<Record, String> MINIMUM_VALUE;
   public final TableField<Record, String> INCREMENT;
   public final TableField<Record, String> CYCLE_OPTION;
   public final TableField<Record, String> DECLARED_DATA_TYPE;
   public final TableField<Record, Long> DECLARED_NUMERIC_PRECISION;
   public final TableField<Record, Long> DECLARED_NUMERIC_SCALE;
   public final TableField<Record, String> START_WITH;
   public final TableField<Record, String> NEXT_VALUE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Sequences() {
      this("SEQUENCES", (Table)null);
   }

   public Sequences(String alias) {
      this(alias, SEQUENCES);
   }

   private Sequences(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Sequences(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.SEQUENCE_CATALOG = createField("SEQUENCE_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.SEQUENCE_SCHEMA = createField("SEQUENCE_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.SEQUENCE_NAME = createField("SEQUENCE_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.DATA_TYPE = createField("DATA_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.NUMERIC_PRECISION = createField("NUMERIC_PRECISION", SQLDataType.BIGINT, this, "");
      this.NUMERIC_PRECISION_RADIX = createField("NUMERIC_PRECISION_RADIX", SQLDataType.BIGINT, this, "");
      this.NUMERIC_SCALE = createField("NUMERIC_SCALE", SQLDataType.BIGINT, this, "");
      this.MAXIMUM_VALUE = createField("MAXIMUM_VALUE", SQLDataType.VARCHAR.length(65536), this, "");
      this.MINIMUM_VALUE = createField("MINIMUM_VALUE", SQLDataType.VARCHAR.length(65536), this, "");
      this.INCREMENT = createField("INCREMENT", SQLDataType.VARCHAR.length(65536), this, "");
      this.CYCLE_OPTION = createField("CYCLE_OPTION", SQLDataType.VARCHAR.length(3), this, "");
      this.DECLARED_DATA_TYPE = createField("DECLARED_DATA_TYPE", SQLDataType.VARCHAR.length(65536), this, "");
      this.DECLARED_NUMERIC_PRECISION = createField("DECLARED_NUMERIC_PRECISION", SQLDataType.BIGINT, this, "");
      this.DECLARED_NUMERIC_SCALE = createField("DECLARED_NUMERIC_SCALE", SQLDataType.BIGINT, this, "");
      this.START_WITH = createField("START_WITH", SQLDataType.VARCHAR.length(65536), this, "");
      this.NEXT_VALUE = createField("NEXT_VALUE", SQLDataType.VARCHAR.length(65536), this, "");
   }

   public Sequences as(String alias) {
      return new Sequences(alias, this);
   }

   public Sequences rename(String name) {
      return new Sequences(name, (Table)null);
   }
}
