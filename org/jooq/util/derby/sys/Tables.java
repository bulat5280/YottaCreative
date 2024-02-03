package org.jooq.util.derby.sys;

import org.jooq.util.derby.sys.tables.Syscolumns;
import org.jooq.util.derby.sys.tables.Sysconglomerates;
import org.jooq.util.derby.sys.tables.Sysconstraints;
import org.jooq.util.derby.sys.tables.Syskeys;
import org.jooq.util.derby.sys.tables.Sysschemas;
import org.jooq.util.derby.sys.tables.Syssequences;
import org.jooq.util.derby.sys.tables.Systables;

public final class Tables {
   public static final Syscolumns SYSCOLUMNS;
   public static final Sysconglomerates SYSCONGLOMERATES;
   public static final Sysconstraints SYSCONSTRAINTS;
   public static final Syskeys SYSKEYS;
   public static final Sysschemas SYSSCHEMAS;
   public static final Syssequences SYSSEQUENCES;
   public static final Systables SYSTABLES;

   private Tables() {
   }

   static {
      SYSCOLUMNS = Syscolumns.SYSCOLUMNS;
      SYSCONGLOMERATES = Sysconglomerates.SYSCONGLOMERATES;
      SYSCONSTRAINTS = Sysconstraints.SYSCONSTRAINTS;
      SYSKEYS = Syskeys.SYSKEYS;
      SYSSCHEMAS = Sysschemas.SYSSCHEMAS;
      SYSSEQUENCES = Syssequences.SYSSEQUENCES;
      SYSTABLES = Systables.SYSTABLES;
   }
}
