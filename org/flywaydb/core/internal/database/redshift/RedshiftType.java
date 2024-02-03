package org.flywaydb.core.internal.database.redshift;

import java.sql.SQLException;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Type;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class RedshiftType extends Type {
   public RedshiftType(JdbcTemplate jdbcTemplate, Database database, Schema schema, String name) {
      super(jdbcTemplate, database, schema, name);
   }

   protected void doDrop() throws SQLException {
      this.jdbcTemplate.execute("DROP TYPE " + this.database.quote(this.schema.getName(), this.name));
   }
}
