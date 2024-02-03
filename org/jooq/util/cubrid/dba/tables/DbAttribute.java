package org.jooq.util.cubrid.dba.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.cubrid.dba.DefaultSchema;

public class DbAttribute extends TableImpl<Record> {
   private static final long serialVersionUID = -737420802L;
   public static final DbAttribute DB_ATTRIBUTE = new DbAttribute();
   public final TableField<Record, String> ATTR_NAME;
   public final TableField<Record, String> CLASS_NAME;
   public final TableField<Record, String> ATTR_TYPE;
   public final TableField<Record, Integer> DEF_ORDER;
   public final TableField<Record, String> FROM_CLASS_NAME;
   public final TableField<Record, String> FROM_ATTR_NAME;
   public final TableField<Record, String> DATA_TYPE;
   public final TableField<Record, Integer> PREC;
   public final TableField<Record, Integer> SCALE;
   public final TableField<Record, Integer> CODE_SET;
   public final TableField<Record, String> DOMAIN_CLASS_NAME;
   public final TableField<Record, String> DEFAULT_VALUE;
   public final TableField<Record, String> IS_NULLABLE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public DbAttribute() {
      this("db_attribute", (Table)null);
   }

   public DbAttribute(String alias) {
      this(alias, DB_ATTRIBUTE);
   }

   private DbAttribute(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private DbAttribute(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
      this.ATTR_NAME = createField("attr_name", SQLDataType.VARCHAR.length(255), this, "");
      this.CLASS_NAME = createField("class_name", SQLDataType.VARCHAR.length(255), this, "");
      this.ATTR_TYPE = createField("attr_type", SQLDataType.VARCHAR.length(8), this, "");
      this.DEF_ORDER = createField("def_order", SQLDataType.INTEGER, this, "");
      this.FROM_CLASS_NAME = createField("from_class_name", SQLDataType.VARCHAR.length(255), this, "");
      this.FROM_ATTR_NAME = createField("from_attr_name", SQLDataType.VARCHAR.length(255), this, "");
      this.DATA_TYPE = createField("data_type", SQLDataType.VARCHAR.length(9), this, "");
      this.PREC = createField("prec", SQLDataType.INTEGER, this, "");
      this.SCALE = createField("scale", SQLDataType.INTEGER, this, "");
      this.CODE_SET = createField("code_set", SQLDataType.INTEGER, this, "");
      this.DOMAIN_CLASS_NAME = createField("domain_class_name", SQLDataType.VARCHAR.length(255), this, "");
      this.DEFAULT_VALUE = createField("default_value", SQLDataType.VARCHAR.length(255), this, "");
      this.IS_NULLABLE = createField("is_nullable", SQLDataType.VARCHAR.length(3), this, "");
   }

   public DbAttribute as(String alias) {
      return new DbAttribute(alias, this);
   }

   public DbAttribute rename(String name) {
      return new DbAttribute(name, (Table)null);
   }
}
