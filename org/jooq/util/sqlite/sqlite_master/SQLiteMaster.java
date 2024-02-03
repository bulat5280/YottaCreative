package org.jooq.util.sqlite.sqlite_master;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;
import org.jooq.util.sqlite.SQLiteDataType;

public class SQLiteMaster extends TableImpl<Record> {
   private static final long serialVersionUID = -823335201L;
   public static final SQLiteMaster SQLITE_MASTER = new SQLiteMaster();
   public static final TableField<Record, String> TYPE;
   public static final TableField<Record, String> NAME;
   public static final TableField<Record, String> TBL_NAME;
   public static final TableField<Record, Integer> ROOTPAGE;
   public static final TableField<Record, String> SQL;

   private SQLiteMaster() {
      super("sqlite_master");
   }

   static {
      TYPE = createField("type", SQLiteDataType.VARCHAR, SQLITE_MASTER);
      NAME = createField("name", SQLiteDataType.VARCHAR, SQLITE_MASTER);
      TBL_NAME = createField("tbl_name", SQLiteDataType.VARCHAR, SQLITE_MASTER);
      ROOTPAGE = createField("rootpage", SQLiteDataType.INTEGER, SQLITE_MASTER);
      SQL = createField("sql", SQLiteDataType.VARCHAR, SQLITE_MASTER);
   }
}
