package org.jooq.util;

import java.sql.Connection;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

class SQLCatalogVersionProvider implements CatalogVersionProvider {
   private Connection connection;
   private String sql;

   SQLCatalogVersionProvider(Connection connection, String sql) {
      this.connection = connection;
      this.sql = sql;
   }

   public String version(CatalogDefinition catalog) {
      return "" + DSL.using((new DefaultConfiguration()).set(this.connection).set((new Settings()).withStatementType(StatementType.STATIC_STATEMENT))).fetchValue(this.sql.replace(":catalog_name", "?"), DSL.param("catalog_name", (Object)catalog.getInputName()));
   }
}
