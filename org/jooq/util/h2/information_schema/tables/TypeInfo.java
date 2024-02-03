package org.jooq.util.h2.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.h2.information_schema.InformationSchema;

public class TypeInfo extends TableImpl<Record> {
   private static final long serialVersionUID = -931469077L;
   public static final TypeInfo TYPE_INFO = new TypeInfo();
   public static final TableField<Record, String> TYPE_NAME;
   public static final TableField<Record, Integer> DATA_TYPE;
   public static final TableField<Record, Integer> PRECISION;
   public static final TableField<Record, String> PREFIX;
   public static final TableField<Record, String> SUFFIX;
   public static final TableField<Record, String> PARAMS;
   public static final TableField<Record, Boolean> AUTO_INCREMENT;
   public static final TableField<Record, Short> MINIMUM_SCALE;
   public static final TableField<Record, Short> MAXIMUM_SCALE;
   public static final TableField<Record, Integer> RADIX;
   public static final TableField<Record, Integer> POS;
   public static final TableField<Record, Boolean> CASE_SENSITIVE;
   public static final TableField<Record, Short> NULLABLE;
   public static final TableField<Record, Short> SEARCHABLE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private TypeInfo() {
      this("TYPE_INFO", (Table)null);
   }

   private TypeInfo(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private TypeInfo(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
   }

   static {
      TYPE_NAME = createField("TYPE_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), TYPE_INFO, "");
      DATA_TYPE = createField("DATA_TYPE", SQLDataType.INTEGER, TYPE_INFO, "");
      PRECISION = createField("PRECISION", SQLDataType.INTEGER, TYPE_INFO, "");
      PREFIX = createField("PREFIX", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), TYPE_INFO, "");
      SUFFIX = createField("SUFFIX", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), TYPE_INFO, "");
      PARAMS = createField("PARAMS", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), TYPE_INFO, "");
      AUTO_INCREMENT = createField("AUTO_INCREMENT", SQLDataType.BOOLEAN, TYPE_INFO, "");
      MINIMUM_SCALE = createField("MINIMUM_SCALE", SQLDataType.SMALLINT, TYPE_INFO, "");
      MAXIMUM_SCALE = createField("MAXIMUM_SCALE", SQLDataType.SMALLINT, TYPE_INFO, "");
      RADIX = createField("RADIX", SQLDataType.INTEGER, TYPE_INFO, "");
      POS = createField("POS", SQLDataType.INTEGER, TYPE_INFO, "");
      CASE_SENSITIVE = createField("CASE_SENSITIVE", SQLDataType.BOOLEAN, TYPE_INFO, "");
      NULLABLE = createField("NULLABLE", SQLDataType.SMALLINT, TYPE_INFO, "");
      SEARCHABLE = createField("SEARCHABLE", SQLDataType.SMALLINT, TYPE_INFO, "");
   }
}
