package org.jooq.util.derby.sys;

import java.util.Arrays;
import java.util.List;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import org.jooq.util.derby.sys.tables.Syscolumns;
import org.jooq.util.derby.sys.tables.Sysconglomerates;
import org.jooq.util.derby.sys.tables.Sysconstraints;
import org.jooq.util.derby.sys.tables.Syskeys;
import org.jooq.util.derby.sys.tables.Sysschemas;
import org.jooq.util.derby.sys.tables.Syssequences;
import org.jooq.util.derby.sys.tables.Systables;

public class Sys extends SchemaImpl {
   private static final long serialVersionUID = -1659535602L;
   public static final Sys SYS = new Sys();

   private Sys() {
      super("SYS");
   }

   public final List<Table<?>> getTables() {
      return Arrays.asList(Syscolumns.SYSCOLUMNS, Sysconglomerates.SYSCONGLOMERATES, Sysconstraints.SYSCONSTRAINTS, Syskeys.SYSKEYS, Sysschemas.SYSSCHEMAS, Syssequences.SYSSEQUENCES, Systables.SYSTABLES);
   }
}
