package org.jooq.util.derby.sys.tables;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.derby.sys.Sys;

public class Sysconglomerates extends TableImpl<Record> {
   private static final long serialVersionUID = 1621303140L;
   public static final Sysconglomerates SYSCONGLOMERATES = new Sysconglomerates();
   public static final TableField<Record, String> SCHEMAID;
   public static final TableField<Record, String> TABLEID;
   public static final TableField<Record, Long> CONGLOMERATENUMBER;
   public static final TableField<Record, String> CONGLOMERATENAME;
   public static final TableField<Record, Boolean> ISINDEX;
   public static final TableField<Record, String> DESCRIPTOR;
   public static final TableField<Record, Boolean> ISCONSTRAINT;
   public static final TableField<Record, String> CONGLOMERATEID;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Sysconglomerates() {
      super("SYSCONGLOMERATES", Sys.SYS);
   }

   static {
      SCHEMAID = createField("SCHEMAID", SQLDataType.CHAR, SYSCONGLOMERATES);
      TABLEID = createField("TABLEID", SQLDataType.CHAR, SYSCONGLOMERATES);
      CONGLOMERATENUMBER = createField("CONGLOMERATENUMBER", SQLDataType.BIGINT, SYSCONGLOMERATES);
      CONGLOMERATENAME = createField("CONGLOMERATENAME", SQLDataType.VARCHAR, SYSCONGLOMERATES);
      ISINDEX = createField("ISINDEX", SQLDataType.BOOLEAN, SYSCONGLOMERATES);
      DESCRIPTOR = createField("DESCRIPTOR", SQLDataType.CLOB, SYSCONGLOMERATES);
      ISCONSTRAINT = createField("ISCONSTRAINT", SQLDataType.BOOLEAN, SYSCONGLOMERATES);
      CONGLOMERATEID = createField("CONGLOMERATEID", SQLDataType.CHAR, SYSCONGLOMERATES);
   }
}
