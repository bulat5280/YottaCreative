package org.jooq.util.mysql.mysql;

import org.jooq.util.mysql.mysql.tables.Proc;
import org.jooq.util.mysql.mysql.tables.ProcsPriv;

public class Tables {
   public static final Proc PROC;
   public static final ProcsPriv PROCS_PRIV;

   static {
      PROC = Proc.PROC;
      PROCS_PRIV = ProcsPriv.PROCS_PRIV;
   }
}
