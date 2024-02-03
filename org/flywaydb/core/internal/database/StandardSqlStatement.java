package org.flywaydb.core.internal.database;

import java.sql.SQLException;
import java.util.List;
import org.flywaydb.core.internal.util.jdbc.ContextImpl;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.util.jdbc.Result;

public class StandardSqlStatement extends AbstractSqlStatement<ContextImpl> {
   public StandardSqlStatement(int lineNumber, String sql) {
      super(lineNumber, sql);
   }

   public List<Result> execute(ContextImpl context, JdbcTemplate jdbcTemplate) throws SQLException {
      return jdbcTemplate.executeStatement(context, this.sql);
   }
}
