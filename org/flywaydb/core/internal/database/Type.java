package org.flywaydb.core.internal.database;

import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public abstract class Type extends SchemaObject {
   public Type(JdbcTemplate jdbcTemplate, Database database, Schema schema, String name) {
      super(jdbcTemplate, database, schema, name);
   }
}
