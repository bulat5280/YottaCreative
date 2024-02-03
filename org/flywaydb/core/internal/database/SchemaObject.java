package org.flywaydb.core.internal.database;

import java.sql.SQLException;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public abstract class SchemaObject {
   protected final JdbcTemplate jdbcTemplate;
   protected final Database database;
   protected final Schema schema;
   protected final String name;

   SchemaObject(JdbcTemplate jdbcTemplate, Database database, Schema schema, String name) {
      this.name = name;
      this.jdbcTemplate = jdbcTemplate;
      this.database = database;
      this.schema = schema;
   }

   public final Schema getSchema() {
      return this.schema;
   }

   public final String getName() {
      return this.name;
   }

   public final void drop() {
      try {
         this.doDrop();
      } catch (SQLException var2) {
         throw new FlywaySqlException("Unable to drop " + this, var2);
      }
   }

   protected abstract void doDrop() throws SQLException;

   public String toString() {
      return this.database.quote(this.schema.getName(), this.name);
   }
}
