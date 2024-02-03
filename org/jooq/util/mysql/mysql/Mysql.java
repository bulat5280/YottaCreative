package org.jooq.util.mysql.mysql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import org.jooq.util.mysql.mysql.tables.Proc;
import org.jooq.util.mysql.mysql.tables.ProcsPriv;

public class Mysql extends SchemaImpl {
   private static final long serialVersionUID = 1140106601L;
   public static final Mysql MYSQL = new Mysql();
   public final Proc PROC;
   public final ProcsPriv PROCS_PRIV;

   private Mysql() {
      super("mysql", (Catalog)null);
      this.PROC = Proc.PROC;
      this.PROCS_PRIV = ProcsPriv.PROCS_PRIV;
   }

   public Catalog getCatalog() {
      return DefaultCatalog.DEFAULT_CATALOG;
   }

   public final List<Table<?>> getTables() {
      List result = new ArrayList();
      result.addAll(this.getTables0());
      return result;
   }

   private final List<Table<?>> getTables0() {
      return Arrays.asList(Proc.PROC, ProcsPriv.PROCS_PRIV);
   }
}
