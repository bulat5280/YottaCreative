package org.jooq.util.cubrid.dba.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.cubrid.dba.DefaultSchema;

public class DbIndex extends TableImpl<Record> {
   private static final long serialVersionUID = -34142941L;
   public static final DbIndex DB_INDEX = new DbIndex();
   public final TableField<Record, String> INDEX_NAME;
   public final TableField<Record, String> IS_UNIQUE;
   public final TableField<Record, String> IS_REVERSE;
   public final TableField<Record, String> CLASS_NAME;
   public final TableField<Record, Integer> KEY_COUNT;
   public final TableField<Record, String> IS_PRIMARY_KEY;
   public final TableField<Record, String> IS_FOREIGN_KEY;
   public final TableField<Record, String> FILTER_EXPRESSION;
   public final TableField<Record, String> HAVE_FUNCTION;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public DbIndex() {
      this("db_index", (Table)null);
   }

   public DbIndex(String alias) {
      this(alias, DB_INDEX);
   }

   private DbIndex(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private DbIndex(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
      this.INDEX_NAME = createField("index_name", SQLDataType.VARCHAR.length(255), this, "");
      this.IS_UNIQUE = createField("is_unique", SQLDataType.VARCHAR.length(3), this, "");
      this.IS_REVERSE = createField("is_reverse", SQLDataType.VARCHAR.length(3), this, "");
      this.CLASS_NAME = createField("class_name", SQLDataType.VARCHAR.length(255), this, "");
      this.KEY_COUNT = createField("key_count", SQLDataType.INTEGER, this, "");
      this.IS_PRIMARY_KEY = createField("is_primary_key", SQLDataType.VARCHAR.length(3), this, "");
      this.IS_FOREIGN_KEY = createField("is_foreign_key", SQLDataType.VARCHAR.length(3), this, "");
      this.FILTER_EXPRESSION = createField("filter_expression", SQLDataType.VARCHAR.length(255), this, "");
      this.HAVE_FUNCTION = createField("have_function", SQLDataType.VARCHAR.length(3), this, "");
   }

   public DbIndex as(String alias) {
      return new DbIndex(alias, this);
   }

   public DbIndex rename(String name) {
      return new DbIndex(name, (Table)null);
   }
}
