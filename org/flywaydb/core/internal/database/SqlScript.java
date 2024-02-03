package org.flywaydb.core.internal.database;

import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.util.scanner.Resource;

public abstract class SqlScript {
   protected final Resource resource;

   protected SqlScript(Resource resource) {
      this.resource = resource;
   }

   public Resource getResource() {
      return this.resource;
   }

   public abstract boolean executeInTransaction();

   public abstract void execute(JdbcTemplate var1);
}
