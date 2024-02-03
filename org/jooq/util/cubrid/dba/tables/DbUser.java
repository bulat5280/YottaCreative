package org.jooq.util.cubrid.dba.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.cubrid.dba.DefaultSchema;

public class DbUser extends TableImpl<Record> {
   private static final long serialVersionUID = -394454388L;
   public static final DbUser DB_USER = new DbUser();
   public final TableField<Record, String> NAME;
   public final TableField<Record, Integer> ID;
   public final TableField<Record, Object> PASSWORD;
   public final TableField<Record, Object> DIRECT_GROUPS;
   public final TableField<Record, Object> GROUPS;
   public final TableField<Record, Object> AUTHORIZATION;
   public final TableField<Record, Object> TRIGGERS;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public DbUser() {
      this("db_user", (Table)null);
   }

   public DbUser(String alias) {
      this(alias, DB_USER);
   }

   private DbUser(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private DbUser(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
      this.NAME = createField("name", SQLDataType.VARCHAR.length(1073741823), this, "");
      this.ID = createField("id", SQLDataType.INTEGER, this, "");
      this.PASSWORD = createField("password", SQLDataType.OTHER, this, "");
      this.DIRECT_GROUPS = createField("direct_groups", SQLDataType.OTHER, this, "");
      this.GROUPS = createField("groups", SQLDataType.OTHER, this, "");
      this.AUTHORIZATION = createField("authorization", SQLDataType.OTHER, this, "");
      this.TRIGGERS = createField("triggers", SQLDataType.OTHER, this, "");
   }

   public DbUser as(String alias) {
      return new DbUser(alias, this);
   }

   public DbUser rename(String name) {
      return new DbUser(name, (Table)null);
   }
}
