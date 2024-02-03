package org.jooq.util.derby.sys.tables;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.derby.sys.Sys;

public class Syskeys extends TableImpl<Record> {
   private static final long serialVersionUID = 761298961L;
   public static final Syskeys SYSKEYS = new Syskeys();
   public static final TableField<Record, String> CONSTRAINTID;
   public static final TableField<Record, String> CONGLOMERATEID;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Syskeys() {
      super("SYSKEYS", Sys.SYS);
   }

   static {
      CONSTRAINTID = createField("CONSTRAINTID", SQLDataType.CHAR, SYSKEYS);
      CONGLOMERATEID = createField("CONGLOMERATEID", SQLDataType.CHAR, SYSKEYS);
   }
}
