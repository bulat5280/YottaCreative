package org.jooq.util.derby.sys.tables;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.derby.sys.Sys;

public class Sysconstraints extends TableImpl<Record> {
   private static final long serialVersionUID = 896651060L;
   public static final Sysconstraints SYSCONSTRAINTS = new Sysconstraints();
   public static final TableField<Record, String> CONSTRAINTID;
   public static final TableField<Record, String> TABLEID;
   public static final TableField<Record, String> CONSTRAINTNAME;
   public static final TableField<Record, String> TYPE;
   public static final TableField<Record, String> SCHEMAID;
   public static final TableField<Record, String> STATE;
   public static final TableField<Record, Integer> REFERENCECOUNT;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Sysconstraints() {
      super("SYSCONSTRAINTS", Sys.SYS);
   }

   static {
      CONSTRAINTID = createField("CONSTRAINTID", SQLDataType.CHAR, SYSCONSTRAINTS);
      TABLEID = createField("TABLEID", SQLDataType.CHAR, SYSCONSTRAINTS);
      CONSTRAINTNAME = createField("CONSTRAINTNAME", SQLDataType.VARCHAR, SYSCONSTRAINTS);
      TYPE = createField("TYPE", SQLDataType.CHAR, SYSCONSTRAINTS);
      SCHEMAID = createField("SCHEMAID", SQLDataType.CHAR, SYSCONSTRAINTS);
      STATE = createField("STATE", SQLDataType.CHAR, SYSCONSTRAINTS);
      REFERENCECOUNT = createField("REFERENCECOUNT", SQLDataType.INTEGER, SYSCONSTRAINTS);
   }
}
