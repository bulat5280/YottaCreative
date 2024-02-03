package org.jooq.util.derby.sys.tables;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.derby.sys.Sys;

public class Syssequences extends TableImpl<Record> {
   private static final long serialVersionUID = 1195022971L;
   public static final Syssequences SYSSEQUENCES = new Syssequences();
   public static final TableField<Record, String> SEQUENCEID;
   public static final TableField<Record, String> SEQUENCENAME;
   public static final TableField<Record, String> SCHEMAID;
   public static final TableField<Record, String> SEQUENCEDATATYPE;
   public static final TableField<Record, Long> CURRENTVALUE;
   public static final TableField<Record, Long> STARTVALUE;
   public static final TableField<Record, Long> MINIMUMVALUE;
   public static final TableField<Record, Long> MAXIMUMVALUE;
   public static final TableField<Record, Long> INCREMENT;
   public static final TableField<Record, String> CYCLEOPTION;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Syssequences() {
      super("SYSSEQUENCES", Sys.SYS);
   }

   static {
      SEQUENCEID = createField("SEQUENCEID", SQLDataType.CHAR, SYSSEQUENCES);
      SEQUENCENAME = createField("SEQUENCENAME", SQLDataType.VARCHAR, SYSSEQUENCES);
      SCHEMAID = createField("SCHEMAID", SQLDataType.CHAR, SYSSEQUENCES);
      SEQUENCEDATATYPE = createField("SEQUENCEDATATYPE", SQLDataType.CLOB, SYSSEQUENCES);
      CURRENTVALUE = createField("CURRENTVALUE", SQLDataType.BIGINT, SYSSEQUENCES);
      STARTVALUE = createField("STARTVALUE", SQLDataType.BIGINT, SYSSEQUENCES);
      MINIMUMVALUE = createField("MINIMUMVALUE", SQLDataType.BIGINT, SYSSEQUENCES);
      MAXIMUMVALUE = createField("MAXIMUMVALUE", SQLDataType.BIGINT, SYSSEQUENCES);
      INCREMENT = createField("INCREMENT", SQLDataType.BIGINT, SYSSEQUENCES);
      CYCLEOPTION = createField("CYCLEOPTION", SQLDataType.CHAR, SYSSEQUENCES);
   }
}
