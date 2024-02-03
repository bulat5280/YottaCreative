package org.jooq.util.cubrid.dba.tables;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.cubrid.dba.DefaultSchema;
import org.jooq.util.cubrid.dba.Keys;

public class DbSerial extends TableImpl<Record> {
   private static final long serialVersionUID = -1258358481L;
   public static final DbSerial DB_SERIAL = new DbSerial();
   public final TableField<Record, String> NAME;
   public final TableField<Record, Object> OWNER;
   public final TableField<Record, BigInteger> CURRENT_VAL;
   public final TableField<Record, BigInteger> INCREMENT_VAL;
   public final TableField<Record, BigInteger> MAX_VAL;
   public final TableField<Record, BigInteger> MIN_VAL;
   public final TableField<Record, Integer> CYCLIC;
   public final TableField<Record, Integer> STARTED;
   public final TableField<Record, String> CLASS_NAME;
   public final TableField<Record, String> ATT_NAME;
   public final TableField<Record, Integer> CACHED_NUM;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public DbSerial() {
      this("db_serial", (Table)null);
   }

   public DbSerial(String alias) {
      this(alias, DB_SERIAL);
   }

   private DbSerial(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private DbSerial(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
      this.NAME = createField("name", SQLDataType.VARCHAR.length(1073741823).nullable(false), this, "");
      this.OWNER = createField("owner", SQLDataType.OTHER, this, "");
      this.CURRENT_VAL = createField("current_val", SQLDataType.DECIMAL_INTEGER.precision(38).nullable(false).defaulted(true), this, "");
      this.INCREMENT_VAL = createField("increment_val", SQLDataType.DECIMAL_INTEGER.precision(38).nullable(false).defaulted(true), this, "");
      this.MAX_VAL = createField("max_val", SQLDataType.DECIMAL_INTEGER.precision(38).nullable(false), this, "");
      this.MIN_VAL = createField("min_val", SQLDataType.DECIMAL_INTEGER.precision(38).nullable(false), this, "");
      this.CYCLIC = createField("cyclic", SQLDataType.INTEGER.defaulted(true), this, "");
      this.STARTED = createField("started", SQLDataType.INTEGER.defaulted(true), this, "");
      this.CLASS_NAME = createField("class_name", SQLDataType.VARCHAR.length(1073741823), this, "");
      this.ATT_NAME = createField("att_name", SQLDataType.VARCHAR.length(1073741823), this, "");
      this.CACHED_NUM = createField("cached_num", SQLDataType.INTEGER.defaulted(true), this, "");
   }

   public UniqueKey<Record> getPrimaryKey() {
      return Keys.DB_SERIAL__PK_DB_SERIAL_NAME;
   }

   public List<UniqueKey<Record>> getKeys() {
      return Arrays.asList(Keys.DB_SERIAL__PK_DB_SERIAL_NAME);
   }

   public DbSerial as(String alias) {
      return new DbSerial(alias, this);
   }

   public DbSerial rename(String name) {
      return new DbSerial(name, (Table)null);
   }
}
