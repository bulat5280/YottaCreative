package org.jooq.util.firebird.rdb.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.firebird.rdb.DefaultSchema;

public class Rdb$procedureParameters extends TableImpl<Record> {
   private static final long serialVersionUID = 1112918214L;
   public static final Rdb$procedureParameters RDB$PROCEDURE_PARAMETERS = new Rdb$procedureParameters();
   public final TableField<Record, String> RDB$PARAMETER_NAME;
   public final TableField<Record, String> RDB$PROCEDURE_NAME;
   public final TableField<Record, Short> RDB$PARAMETER_NUMBER;
   public final TableField<Record, Short> RDB$PARAMETER_TYPE;
   public final TableField<Record, String> RDB$FIELD_SOURCE;
   public final TableField<Record, String> RDB$DESCRIPTION;
   public final TableField<Record, Short> RDB$SYSTEM_FLAG;
   public final TableField<Record, byte[]> RDB$DEFAULT_VALUE;
   public final TableField<Record, String> RDB$DEFAULT_SOURCE;
   public final TableField<Record, Short> RDB$COLLATION_ID;
   public final TableField<Record, Short> RDB$NULL_FLAG;
   public final TableField<Record, Short> RDB$PARAMETER_MECHANISM;
   public final TableField<Record, String> RDB$FIELD_NAME;
   public final TableField<Record, String> RDB$RELATION_NAME;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public Rdb$procedureParameters() {
      this("RDB$PROCEDURE_PARAMETERS", (Table)null);
   }

   public Rdb$procedureParameters(String alias) {
      this(alias, RDB$PROCEDURE_PARAMETERS);
   }

   private Rdb$procedureParameters(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Rdb$procedureParameters(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
      this.RDB$PARAMETER_NAME = createField("RDB$PARAMETER_NAME", SQLDataType.CHAR, this, "");
      this.RDB$PROCEDURE_NAME = createField("RDB$PROCEDURE_NAME", SQLDataType.CHAR, this, "");
      this.RDB$PARAMETER_NUMBER = createField("RDB$PARAMETER_NUMBER", SQLDataType.SMALLINT, this, "");
      this.RDB$PARAMETER_TYPE = createField("RDB$PARAMETER_TYPE", SQLDataType.SMALLINT, this, "");
      this.RDB$FIELD_SOURCE = createField("RDB$FIELD_SOURCE", SQLDataType.CHAR, this, "");
      this.RDB$DESCRIPTION = createField("RDB$DESCRIPTION", SQLDataType.CLOB, this, "");
      this.RDB$SYSTEM_FLAG = createField("RDB$SYSTEM_FLAG", SQLDataType.SMALLINT, this, "");
      this.RDB$DEFAULT_VALUE = createField("RDB$DEFAULT_VALUE", SQLDataType.BLOB, this, "");
      this.RDB$DEFAULT_SOURCE = createField("RDB$DEFAULT_SOURCE", SQLDataType.CLOB, this, "");
      this.RDB$COLLATION_ID = createField("RDB$COLLATION_ID", SQLDataType.SMALLINT, this, "");
      this.RDB$NULL_FLAG = createField("RDB$NULL_FLAG", SQLDataType.SMALLINT, this, "");
      this.RDB$PARAMETER_MECHANISM = createField("RDB$PARAMETER_MECHANISM", SQLDataType.SMALLINT, this, "");
      this.RDB$FIELD_NAME = createField("RDB$FIELD_NAME", SQLDataType.CHAR, this, "");
      this.RDB$RELATION_NAME = createField("RDB$RELATION_NAME", SQLDataType.CHAR, this, "");
   }

   public Rdb$procedureParameters as(String alias) {
      return new Rdb$procedureParameters(alias, this);
   }

   public Rdb$procedureParameters rename(String name) {
      return new Rdb$procedureParameters(name, (Table)null);
   }
}
