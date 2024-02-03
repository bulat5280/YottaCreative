package org.jooq.util.derby.sys.tables;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.derby.sys.Sys;

public class Sysschemas extends TableImpl<Record> {
   private static final long serialVersionUID = 1872180098L;
   public static final Sysschemas SYSSCHEMAS = new Sysschemas();
   public static final TableField<Record, String> SCHEMAID;
   public static final TableField<Record, String> SCHEMANAME;
   public static final TableField<Record, String> AUTHORIZATIONID;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private Sysschemas() {
      super("SYSSCHEMAS", Sys.SYS);
   }

   static {
      SCHEMAID = createField("SCHEMAID", SQLDataType.CHAR, SYSSCHEMAS);
      SCHEMANAME = createField("SCHEMANAME", SQLDataType.VARCHAR, SYSSCHEMAS);
      AUTHORIZATIONID = createField("AUTHORIZATIONID", SQLDataType.VARCHAR, SYSSCHEMAS);
   }
}
