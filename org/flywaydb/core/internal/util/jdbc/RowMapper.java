package org.flywaydb.core.internal.util.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
   T mapRow(ResultSet var1) throws SQLException;
}
