package org.flywaydb.core.api.migration.spring;

import org.springframework.jdbc.core.JdbcTemplate;

public interface SpringJdbcMigration {
   void migrate(JdbcTemplate var1) throws Exception;
}
