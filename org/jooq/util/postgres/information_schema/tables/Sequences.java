package org.jooq.util.postgres.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.information_schema.InformationSchema;

public class Sequences extends TableImpl<Record> {
   private static final long serialVersionUID = -42417332L;
   public static final Sequences SEQUENCES = new Sequences();
   public final TableField<Record, String> SEQUENCE_CATALOG;
   public final TableField<Record, String> SEQUENCE_SCHEMA;
   public final TableField<Record, String> SEQUENCE_NAME;
   public final TableField<Record, String> DATA_TYPE;
   public final TableField<Record, Integer> NUMERIC_PRECISION;
   public final TableField<Record, Integer> NUMERIC_PRECISION_RADIX;
   public final TableField<Record, Integer> NUMERIC_SCALE;
   public final TableField<Record, String> START_VALUE;
   public final TableField<Record, String> MINIMUM_VALUE;
   public final TableField<Record, String> MAXIMUM_VALUE;
   public final TableField<Record, String> INCREMENT;
   public final TableField<Record, String> CYCLE_OPTION;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Sequences() {
      this("sequences", (Table)null);
   }

   public Sequences(String alias) {
      this(alias, SEQUENCES);
   }

   private Sequences(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Sequences(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.SEQUENCE_CATALOG = createField("sequence_catalog", SQLDataType.VARCHAR, this, "");
      this.SEQUENCE_SCHEMA = createField("sequence_schema", SQLDataType.VARCHAR, this, "");
      this.SEQUENCE_NAME = createField("sequence_name", SQLDataType.VARCHAR, this, "");
      this.DATA_TYPE = createField("data_type", SQLDataType.VARCHAR, this, "");
      this.NUMERIC_PRECISION = createField("numeric_precision", SQLDataType.INTEGER, this, "");
      this.NUMERIC_PRECISION_RADIX = createField("numeric_precision_radix", SQLDataType.INTEGER, this, "");
      this.NUMERIC_SCALE = createField("numeric_scale", SQLDataType.INTEGER, this, "");
      this.START_VALUE = createField("start_value", SQLDataType.VARCHAR, this, "");
      this.MINIMUM_VALUE = createField("minimum_value", SQLDataType.VARCHAR, this, "");
      this.MAXIMUM_VALUE = createField("maximum_value", SQLDataType.VARCHAR, this, "");
      this.INCREMENT = createField("increment", SQLDataType.VARCHAR, this, "");
      this.CYCLE_OPTION = createField("cycle_option", SQLDataType.VARCHAR.length(3), this, "");
   }

   public Sequences as(String alias) {
      return new Sequences(alias, this);
   }

   public Sequences rename(String name) {
      return new Sequences(name, (Table)null);
   }
}
