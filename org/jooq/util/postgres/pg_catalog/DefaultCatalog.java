package org.jooq.util.postgres.pg_catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Schema;
import org.jooq.impl.CatalogImpl;

public class DefaultCatalog extends CatalogImpl {
   private static final long serialVersionUID = 2037041374L;
   public static final DefaultCatalog DEFAULT_CATALOG = new DefaultCatalog();

   private DefaultCatalog() {
      super("");
   }

   public final List<Schema> getSchemas() {
      List result = new ArrayList();
      result.addAll(this.getSchemas0());
      return result;
   }

   private final List<Schema> getSchemas0() {
      return Arrays.asList(PgCatalog.PG_CATALOG);
   }
}
