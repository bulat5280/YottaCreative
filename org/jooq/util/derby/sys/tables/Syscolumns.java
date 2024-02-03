package org.jooq.util.derby.sys.tables;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.derby.sys.Sys;

public class Syscolumns extends TableImpl<Record> {
   private static final long serialVersionUID = 1515539769L;
   public static final Syscolumns SYSCOLUMNS = new Syscolumns();
   public static final TableField<Record, String> REFERENCEID;
   public static final TableField<Record, String> COLUMNNAME;
   public static final TableField<Record, Integer> COLUMNNUMBER;
   public static final TableField<Record, String> COLUMNDATATYPE;
   public static final TableField<Record, String> COLUMNDEFAULT;
   public static final TableField<Record, String> COLUMNDEFAULTID;
   public static final TableField<Record, Long> AUTOINCREMENTVALUE;
   public static final TableField<Record, Long> AUTOINCREMENTSTART;
   public static final TableField<Record, Long> AUTOINCREMENTINC;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Syscolumns() {
      super("SYSCOLUMNS", Sys.SYS);
   }

   static {
      REFERENCEID = createField("REFERENCEID", SQLDataType.CHAR, SYSCOLUMNS);
      COLUMNNAME = createField("COLUMNNAME", SQLDataType.VARCHAR, SYSCOLUMNS);
      COLUMNNUMBER = createField("COLUMNNUMBER", SQLDataType.INTEGER, SYSCOLUMNS);
      COLUMNDATATYPE = createField("COLUMNDATATYPE", SQLDataType.CLOB, SYSCOLUMNS);
      COLUMNDEFAULT = createField("COLUMNDEFAULT", SQLDataType.CLOB, SYSCOLUMNS);
      COLUMNDEFAULTID = createField("COLUMNDEFAULTID", SQLDataType.CHAR, SYSCOLUMNS);
      AUTOINCREMENTVALUE = createField("AUTOINCREMENTVALUE", SQLDataType.BIGINT, SYSCOLUMNS);
      AUTOINCREMENTSTART = createField("AUTOINCREMENTSTART", SQLDataType.BIGINT, SYSCOLUMNS);
      AUTOINCREMENTINC = createField("AUTOINCREMENTINC", SQLDataType.BIGINT, SYSCOLUMNS);
   }
}
