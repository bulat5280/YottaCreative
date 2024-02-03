package org.jooq.util.cubrid.dba.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.cubrid.dba.DefaultSchema;

public class DbIndexKey extends TableImpl<Record> {
   private static final long serialVersionUID = 1317315060L;
   public static final DbIndexKey DB_INDEX_KEY = new DbIndexKey();
   public final TableField<Record, String> INDEX_NAME;
   public final TableField<Record, String> CLASS_NAME;
   public final TableField<Record, String> KEY_ATTR_NAME;
   public final TableField<Record, Integer> KEY_ORDER;
   public final TableField<Record, String> ASC_DESC;
   public final TableField<Record, Integer> KEY_PREFIX_LENGTH;
   public final TableField<Record, String> FUNC;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public DbIndexKey() {
      this("db_index_key", (Table)null);
   }

   public DbIndexKey(String alias) {
      this(alias, DB_INDEX_KEY);
   }

   private DbIndexKey(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private DbIndexKey(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
      this.INDEX_NAME = createField("index_name", SQLDataType.VARCHAR.length(255), this, "");
      this.CLASS_NAME = createField("class_name", SQLDataType.VARCHAR.length(255), this, "");
      this.KEY_ATTR_NAME = createField("key_attr_name", SQLDataType.VARCHAR.length(255), this, "");
      this.KEY_ORDER = createField("key_order", SQLDataType.INTEGER, this, "");
      this.ASC_DESC = createField("asc_desc", SQLDataType.VARCHAR.length(4), this, "");
      this.KEY_PREFIX_LENGTH = createField("key_prefix_length", SQLDataType.INTEGER, this, "");
      this.FUNC = createField("func", SQLDataType.VARCHAR.length(255), this, "");
   }

   public DbIndexKey as(String alias) {
      return new DbIndexKey(alias, this);
   }

   public DbIndexKey rename(String name) {
      return new DbIndexKey(name, (Table)null);
   }
}
