package org.jooq.util;

import java.sql.Connection;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

class SQLSchemaVersionProvider implements SchemaVersionProvider {
   private Connection connection;
   private String sql;

   SQLSchemaVersionProvider(Connection connection, String sql) {
      this.connection = connection;
      this.sql = sql;
   }

   public String version(SchemaDefinition schema) {
      return "" + DSL.using((new DefaultConfiguration()).set(this.connection).set((new Settings()).withStatementType(StatementType.STATIC_STATEMENT))).fetchValue(this.sql.replace(":schema_name", "?"), DSL.param("schema_name", (Object)schema.getInputName()));
   }
}
