package org.jooq.tools.jdbc;

import java.sql.SQLException;

@FunctionalInterface
public interface MockDataProvider {
   MockResult[] execute(MockExecuteContext var1) throws SQLException;
}
