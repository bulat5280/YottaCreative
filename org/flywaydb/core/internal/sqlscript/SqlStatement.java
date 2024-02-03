package org.flywaydb.core.internal.sqlscript;

import java.sql.SQLException;
import java.util.List;
import org.flywaydb.core.internal.util.jdbc.ContextImpl;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.util.jdbc.Result;

public interface SqlStatement<C extends ContextImpl> {
   int getLineNumber();

   String getSql();

   List<Result> execute(C var1, JdbcTemplate var2) throws SQLException;
}
