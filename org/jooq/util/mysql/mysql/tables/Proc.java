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
import org.jooq.util.mysql.mysql.enums.ProcIsDeterministic;
import org.jooq.util.mysql.mysql.enums.ProcLanguage;
import org.jooq.util.mysql.mysql.enums.ProcSecurityType;
import org.jooq.util.mysql.mysql.enums.ProcSqlDataAccess;
import org.jooq.util.mysql.mysql.enums.ProcType;

public class Proc extends TableImpl<Record> {
   private static final long serialVersionUID = -765641597L;
   public static final Proc PROC = new Proc();
   public static final TableField<Record, String> DB;
   public static final TableField<Record, String> NAME;
   public static final TableField<Record, ProcType> TYPE;
   public static final TableField<Record, String> SPECIFIC_NAME;
   public static final TableField<Record, ProcLanguage> LANGUAGE;
   public static final TableField<Record, ProcSqlDataAccess> SQL_DATA_ACCESS;
   public static final TableField<Record, ProcIsDeterministic> IS_DETERMINISTIC;
   public static final TableField<Record, ProcSecurityType> SECURITY_TYPE;
   public static final TableField<Record, byte[]> PARAM_LIST;
   public static final TableField<Record, byte[]> RETURNS;
   public static final TableField<Record, byte[]> BODY;
   public static final TableField<Record, String> DEFINER;
   public static final TableField<Record, Timestamp> CREATED;
   public static final TableField<Record, Timestamp> MODIFIED;
   public static final TableField<Record, String> SQL_MODE;
   public static final TableField<Record, String> COMMENT;
   public static final TableField<Record, String> CHARACTER_SET_CLIENT;
   public static final TableField<Record, String> COLLATION_CONNECTION;
   public static final TableField<Record, String> DB_COLLATION;
   public static final TableField<Record, byte[]> BODY_UTF8;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Proc() {
      this("proc", (Table)null);
   }

   private Proc(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private Proc(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, (Schema)null, aliased, parameters, "Stored Procedures");
   }

   public Schema getSchema() {
      return Mysql.MYSQL;
   }

   static {
      DB = createField("db", SQLDataType.CHAR.length(64).nullable(false).defaultValue((Field)DSL.inline("", (DataType)SQLDataType.CHAR)), PROC, "");
      NAME = createField("name", SQLDataType.CHAR.length(64).nullable(false).defaultValue((Field)DSL.inline("", (DataType)SQLDataType.CHAR)), PROC, "");
      TYPE = createField("type", MySQLDataType.VARCHAR.asEnumDataType(ProcType.class), PROC, "");
      SPECIFIC_NAME = createField("specific_name", SQLDataType.CHAR.length(64).nullable(false).defaultValue((Field)DSL.inline("", (DataType)SQLDataType.CHAR)), PROC, "");
      LANGUAGE = createField("language", MySQLDataType.VARCHAR.asEnumDataType(ProcLanguage.class), PROC, "");
      SQL_DATA_ACCESS = createField("sql_data_access", MySQLDataType.VARCHAR.asEnumDataType(ProcSqlDataAccess.class), PROC, "");
      IS_DETERMINISTIC = createField("is_deterministic", MySQLDataType.VARCHAR.asEnumDataType(ProcIsDeterministic.class), PROC, "");
      SECURITY_TYPE = createField("security_type", MySQLDataType.VARCHAR.asEnumDataType(ProcSecurityType.class), PROC, "");
      PARAM_LIST = createField("param_list", SQLDataType.BLOB.nullable(false), PROC, "");
      RETURNS = createField("returns", SQLDataType.BLOB.nullable(false), PROC, "");
      BODY = createField("body", SQLDataType.BLOB.nullable(false), PROC, "");
      DEFINER = createField("definer", SQLDataType.CHAR.length(77).nullable(false).defaultValue((Field)DSL.inline("", (DataType)SQLDataType.CHAR)), PROC, "");
      CREATED = createField("created", SQLDataType.TIMESTAMP.nullable(false).defaultValue((Field)DSL.inline("CURRENT_TIMESTAMP", (DataType)SQLDataType.TIMESTAMP)), PROC, "");
      MODIFIED = createField("modified", SQLDataType.TIMESTAMP.nullable(false).defaultValue((Field)DSL.inline("0000-00-00 00:00:00", (DataType)SQLDataType.TIMESTAMP)), PROC, "");
      SQL_MODE = createField("sql_mode", SQLDataType.VARCHAR.length(478).nullable(false).defaultValue((Field)DSL.inline("", (DataType)SQLDataType.VARCHAR)), PROC, "");
      COMMENT = createField("comment", SQLDataType.CLOB.nullable(false), PROC, "");
      CHARACTER_SET_CLIENT = createField("character_set_client", SQLDataType.CHAR.length(32), PROC, "");
      COLLATION_CONNECTION = createField("collation_connection", SQLDataType.CHAR.length(32), PROC, "");
      DB_COLLATION = createField("db_collation", SQLDataType.CHAR.length(32), PROC, "");
      BODY_UTF8 = createField("body_utf8", SQLDataType.BLOB, PROC, "");
   }
}
