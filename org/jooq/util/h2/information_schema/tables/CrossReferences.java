package org.jooq.util.h2.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.h2.information_schema.InformationSchema;

public class CrossReferences extends TableImpl<Record> {
   private static final long serialVersionUID = -1946209481L;
   public static final CrossReferences CROSS_REFERENCES = new CrossReferences();
   public static final TableField<Record, String> PKTABLE_CATALOG;
   public static final TableField<Record, String> PKTABLE_SCHEMA;
   public static final TableField<Record, String> PKTABLE_NAME;
   public static final TableField<Record, String> PKCOLUMN_NAME;
   public static final TableField<Record, String> FKTABLE_CATALOG;
   public static final TableField<Record, String> FKTABLE_SCHEMA;
   public static final TableField<Record, String> FKTABLE_NAME;
   public static final TableField<Record, String> FKCOLUMN_NAME;
   public static final TableField<Record, Short> ORDINAL_POSITION;
   public static final TableField<Record, Short> UPDATE_RULE;
   public static final TableField<Record, Short> DELETE_RULE;
   public static final TableField<Record, String> FK_NAME;
   public static final TableField<Record, String> PK_NAME;
   public static final TableField<Record, Short> DEFERRABILITY;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private CrossReferences() {
      this("CROSS_REFERENCES", (Table)null);
   }

   private CrossReferences(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private CrossReferences(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
   }

   static {
      PKTABLE_CATALOG = createField("PKTABLE_CATALOG", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CROSS_REFERENCES, "");
      PKTABLE_SCHEMA = createField("PKTABLE_SCHEMA", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CROSS_REFERENCES, "");
      PKTABLE_NAME = createField("PKTABLE_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CROSS_REFERENCES, "");
      PKCOLUMN_NAME = createField("PKCOLUMN_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CROSS_REFERENCES, "");
      FKTABLE_CATALOG = createField("FKTABLE_CATALOG", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CROSS_REFERENCES, "");
      FKTABLE_SCHEMA = createField("FKTABLE_SCHEMA", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CROSS_REFERENCES, "");
      FKTABLE_NAME = createField("FKTABLE_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CROSS_REFERENCES, "");
      FKCOLUMN_NAME = createField("FKCOLUMN_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CROSS_REFERENCES, "");
      ORDINAL_POSITION = createField("ORDINAL_POSITION", SQLDataType.SMALLINT, CROSS_REFERENCES, "");
      UPDATE_RULE = createField("UPDATE_RULE", SQLDataType.SMALLINT, CROSS_REFERENCES, "");
      DELETE_RULE = createField("DELETE_RULE", SQLDataType.SMALLINT, CROSS_REFERENCES, "");
      FK_NAME = createField("FK_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CROSS_REFERENCES, "");
      PK_NAME = createField("PK_NAME", SQLDataType.VARCHAR.length(Integer.MAX_VALUE), CROSS_REFERENCES, "");
      DEFERRABILITY = createField("DEFERRABILITY", SQLDataType.SMALLINT, CROSS_REFERENCES, "");
   }
}
