package org.flywaydb.core.internal.database.sqlserver;

import java.sql.SQLException;
import java.util.concurrent.Callable;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class SQLServerApplicationLockTemplate {
   private static final Log LOG = LogFactory.getLog(SQLServerApplicationLockTemplate.class);
   private final SQLServerConnection connection;
   private final JdbcTemplate jdbcTemplate;
   private final String databaseName;
   private final String lockName;

   SQLServerApplicationLockTemplate(SQLServerConnection connection, JdbcTemplate jdbcTemplate, String databaseName, int discriminator) {
      this.connection = connection;
      this.jdbcTemplate = jdbcTemplate;
      this.databaseName = databaseName;
      this.lockName = "Flyway-" + discriminator;
   }

   public <T> T execute(Callable<T> callable) {
      Object var2;
      try {
         this.connection.setCurrentDatabase(this.databaseName);
         this.jdbcTemplate.execute("EXEC sp_getapplock @Resource = ?, @LockTimeout='3600000', @LockMode = 'Exclusive', @LockOwner = 'Session'", this.lockName);
         var2 = callable.call();
      } catch (SQLException var12) {
         throw new FlywaySqlException("Unable to acquire SQL Server application lock", var12);
      } catch (Exception var13) {
         Object rethrow;
         if (var13 instanceof RuntimeException) {
            rethrow = (RuntimeException)var13;
         } else {
            rethrow = new FlywayException(var13);
         }

         throw rethrow;
      } finally {
         try {
            this.connection.setCurrentDatabase(this.databaseName);
            this.jdbcTemplate.execute("EXEC sp_releaseapplock @Resource = ?, @LockOwner = 'Session'", this.lockName);
         } catch (SQLException var11) {
            LOG.error("Unable to release SQL Server application lock", var11);
         }

      }

      return var2;
   }
}
