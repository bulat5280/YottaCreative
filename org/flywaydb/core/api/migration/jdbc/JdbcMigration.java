package org.flywaydb.core.api.migration.jdbc;

import java.sql.Connection;

public interface JdbcMigration {
   void migrate(Connection var1) throws Exception;
}
