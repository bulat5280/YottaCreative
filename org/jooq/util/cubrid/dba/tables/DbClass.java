package org.jooq.util.cubrid.dba.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.cubrid.dba.DefaultSchema;

public class DbClass extends TableImpl<Record> {
   private static final long serialVersionUID = 842801437L;
   public static final DbClass DB_CLASS = new DbClass();
   public final TableField<Record, String> CLASS_NAME;
   public final TableField<Record, String> OWNER_NAME;
   public final TableField<Record, String> CLASS_TYPE;
   public final TableField<Record, String> IS_SYSTEM_CLASS;
   public final TableField<Record, String> PARTITIONED;
   public final TableField<Record, String> IS_REUSE_OID_CLASS;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public DbClass() {
      this("db_class", (Table)null);
   }

   public DbClass(String alias) {
      this(alias, DB_CLASS);
   }

   private DbClass(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private DbClass(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
      this.CLASS_NAME = createField("class_name", SQLDataType.VARCHAR.length(255), this, "");
      this.OWNER_NAME = createField("owner_name", SQLDataType.VARCHAR.length(255), this, "");
      this.CLASS_TYPE = createField("class_type", SQLDataType.VARCHAR.length(6), this, "");
      this.IS_SYSTEM_CLASS = createField("is_system_class", SQLDataType.VARCHAR.length(3), this, "");
      this.PARTITIONED = createField("partitioned", SQLDataType.VARCHAR.length(3), this, "");
      this.IS_REUSE_OID_CLASS = createField("is_reuse_oid_class", SQLDataType.VARCHAR.length(3), this, "");
   }

   public DbClass as(String alias) {
      return new DbClass(alias, this);
   }

   public DbClass rename(String name) {
      return new DbClass(name, (Table)null);
   }
}
