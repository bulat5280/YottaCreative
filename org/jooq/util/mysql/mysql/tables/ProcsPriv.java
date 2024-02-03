package org.jooq.util.mysql.mysql.tables;

import java.sql.Timestamp;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.mysql.MySQLDataType;
import org.jooq.util.mysql.mysql.Mysql;
import org.jooq.util.mysql.mysql.enums.ProcsPrivRoutineType;

public class ProcsPriv extends TableImpl<Record> {
   private static final long serialVersionUID = -1425770453L;
   public static final ProcsPriv PROCS_PRIV = new ProcsPriv();
   public static final TableField<Record, String> HOST;
   public static final TableField<Record, String> DB;
   public static final TableField<Record, String> USER;
   public static final TableField<Record, String> ROUTINE_NAME;
   public static final TableField<Record, ProcsPrivRoutineType> ROUTINE_TYPE;
   public static final TableField<Record, String> GRANTOR;
   public static final TableField<Record, String> PROC_PRIV;
   public static final TableField<Record, Timestamp> TIMESTAMP;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private ProcsPriv() {
      this("procs_priv", (Table)null);
   }

   private ProcsPriv(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private ProcsPriv(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, (Schema)null, aliased, parameters, "Procedure privileges");
   }

   public Schema getSchema() {
      return Mysql.MYSQL;
   }

   static {
      HOST = createField("Host", SQLDataType.CHAR.length(60).nullable(false).defaultValue((Field)DSL.inline("", (DataType)SQLDataType.CHAR)), PROCS_PRIV, "");
      DB = createField("Db", SQLDataType.CHAR.length(64).nullable(false).defaultValue((Field)DSL.inline("", (DataType)SQLDataType.CHAR)), PROCS_PRIV, "");
      USER = createField("User", SQLDataType.CHAR.length(32).nullable(false).defaultValue((Field)DSL.inline("", (DataType)SQLDataType.CHAR)), PROCS_PRIV, "");
      ROUTINE_NAME = createField("Routine_name", SQLDataType.CHAR.length(64).nullable(false).defaultValue((Field)DSL.inline("", (DataType)SQLDataType.CHAR)), PROCS_PRIV, "");
      ROUTINE_TYPE = createField("Routine_type", MySQLDataType.VARCHAR.asEnumDataType(ProcsPrivRoutineType.class), PROCS_PRIV, "");
      GRANTOR = createField("Grantor", SQLDataType.CHAR.length(77).nullable(false).defaultValue((Field)DSL.inline("", (DataType)SQLDataType.CHAR)), PROCS_PRIV, "");
      PROC_PRIV = createField("Proc_priv", SQLDataType.VARCHAR.length(27).nullable(false).defaultValue((Field)DSL.inline("", (DataType)SQLDataType.VARCHAR)), PROCS_PRIV, "");
      TIMESTAMP = createField("Timestamp", SQLDataType.TIMESTAMP.nullable(false).defaultValue((Field)DSL.inline("CURRENT_TIMESTAMP", (DataType)SQLDataType.TIMESTAMP)), PROCS_PRIV, "");
   }
}
