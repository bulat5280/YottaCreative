package org.flywaydb.core.internal.database.postgresql;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.flywaydb.core.internal.database.AbstractSqlStatement;
import org.flywaydb.core.internal.util.jdbc.ContextImpl;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.util.jdbc.Result;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

public class PostgreSQLCopyStatement extends AbstractSqlStatement {
   PostgreSQLCopyStatement(int lineNumber, String sql) {
      super(lineNumber, sql);
   }

   public List<Result> execute(ContextImpl context, JdbcTemplate jdbcTemplate) throws SQLException {
      int split = this.sql.indexOf(";");
      String statement = this.sql.substring(0, split);
      String data = this.sql.substring(split + 1).trim();
      List<Result> results = new ArrayList();
      CopyManager copyManager = new CopyManager((BaseConnection)jdbcTemplate.getConnection().unwrap(BaseConnection.class));

      try {
         long updateCount = copyManager.copyIn(statement, new StringReader(data));
         results.add(new Result(updateCount));
         return results;
      } catch (IOException var10) {
         throw new SQLException("Unable to execute COPY operation", var10);
      }
   }
}
