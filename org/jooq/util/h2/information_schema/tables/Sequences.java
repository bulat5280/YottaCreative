package org.jooq.util.h2.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.h2.information_schema.InformationSchema;

public class Sequences extends TableImpl<Record> {
   private static final long serialVersionUID = -1241185884L;
   public static final Sequences SEQUENCES = new Sequences();
   public static final TableField<Record, String> SEQUENCE_CATALOG;
   public static final TableField<Record, String> SEQUENCE_SCHEMA;
   public static final TableField<Record, String> SEQUENCE_NAME;
   public static final TableField<Record, Long> CURRENT_VALUE;
   public static final TableField<Record, Long> INCREMENT;
   public static final TableField<Record, Boolean> IS_GENERATED;
   public static final TableField<Record, String> REMARKS;
   public static final TableField<Record, Long> CACHE;
   public static final TableField<Record, Long> MIN_VALUE;
   public static final TableField<Record, Long> MAX_VALUE;
   public static final TableField<Record, Boolean> IS_CYCLE;
   public static final TableField<Record, Integer> ID;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Sequences() {
      this("SEQUENCES", (Table)null);
   }

   private Sequences(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Sequences(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
   }

   static {
      SEQUENCE_CATALOG = createField("SEQUENCE_CATALOG", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), SEQUENCES, "");
      SEQUENCE_SCHEMA = createField("SEQUENCE_SCHEMA", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), SEQUENCES, "");
      SEQUENCE_NAME = createField("SEQUENCE_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), SEQUENCES, "");
      CURRENT_VALUE = createField("CURRENT_VALUE", SQLDataType.BIGINT, SEQUENCES, "");
      INCREMENT = createField("INCREMENT", SQLDataType.BIGINT, SEQUENCES, "");
      IS_GENERATED = createField("IS_GENERATED", SQLDataType.BOOLEAN, SEQUENCES, "");
      REMARKS = createField("REMARKS", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), SEQUENCES, "");
      CACHE = createField("CACHE", SQLDataType.BIGINT, SEQUENCES, "");
      MIN_VALUE = createField("MIN_VALUE", SQLDataType.BIGINT, SEQUENCES, "");
      MAX_VALUE = createField("MAX_VALUE", SQLDataType.BIGINT, SEQUENCES, "");
      IS_CYCLE = createField("IS_CYCLE", SQLDataType.BOOLEAN, SEQUENCES, "");
      ID = createField("ID", SQLDataType.INTEGER, SEQUENCES, "");
   }
}
