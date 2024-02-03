package org.jooq.util.mysql.mysql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Schema;
import org.jooq.impl.CatalogImpl;

public class DefaultCatalog extends CatalogImpl {
   private static final long serialVersionUID = -178598874L;
   public static final DefaultCatalog DEFAULT_CATALOG = new DefaultCatalog();
   public final Mysql MYSQL;

   private DefaultCatalog() {
      super("");
      this.MYSQL = Mysql.MYSQL;
   }

   public final List<Schema> getSchemas() {
      List result = new ArrayList();
      result.addAll(this.getSchemas0());
      return result;
   }

   private final List<Schema> getSchemas0() {
      return Arrays.asList(Mysql.MYSQL);
   }
}
