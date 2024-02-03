package org.jooq.util.derby.sys.tables;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.derby.sys.Sys;

public class Systables extends TableImpl<Record> {
   private static final long serialVersionUID = -675426733L;
   public static final Systables SYSTABLES = new Systables();
   public static final TableField<Record, String> TABLEID;
   public static final TableField<Record, String> TABLENAME;
   public static final TableField<Record, String> TABLETYPE;
   public static final TableField<Record, String> SCHEMAID;
   public static final TableField<Record, String> LOCKGRANULARITY;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Systables() {
      super("SYSTABLES", Sys.SYS);
   }

   static {
      TABLEID = createField("TABLEID", SQLDataType.CHAR, SYSTABLES);
      TABLENAME = createField("TABLENAME", SQLDataType.VARCHAR, SYSTABLES);
      TABLETYPE = createField("TABLETYPE", SQLDataType.CHAR, SYSTABLES);
      SCHEMAID = createField("SCHEMAID", SQLDataType.CHAR, SYSTABLES);
      LOCKGRANULARITY = createField("LOCKGRANULARITY", SQLDataType.CHAR, SYSTABLES);
   }
}
