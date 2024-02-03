package org.flywaydb.core.internal.database;

import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public abstract class Function extends SchemaObject {
   protected String[] args;

   public Function(JdbcTemplate jdbcTemplate, Database database, Schema schema, String name, String... args) {
      super(jdbcTemplate, database, schema, name);
      this.args = args == null ? new String[0] : args;
   }
}
