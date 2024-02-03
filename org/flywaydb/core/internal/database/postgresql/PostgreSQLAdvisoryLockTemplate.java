package org.flywaydb.core.internal.database.postgresql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.util.jdbc.RowMapper;

public class PostgreSQLAdvisoryLockTemplate {
   private static final Log LOG = LogFactory.getLog(PostgreSQLAdvisoryLockTemplate.class);
   private static final long LOCK_MAGIC_NUM = 77431708279161L;
   private final JdbcTemplate jdbcTemplate;
   private final long lockNum;

   PostgreSQLAdvisoryLockTemplate(JdbcTemplate jdbcTemplate, int discriminator) {
      this.jdbcTemplate = jdbcTemplate;
      this.lockNum = 77431708279161L + (long)discriminator;
   }

   public <T> T execute(Callable<T> callable) {
      Object var2;
      try {
         this.lock();
         var2 = callable.call();
      } catch (SQLException var12) {
         throw new FlywaySqlException("Unable to acquire PostgreSQL advisory lock", var12);
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
            this.jdbcTemplate.execute("SELECT pg_advisory_unlock(" + this.lockNum + ")");
         } catch (SQLException var11) {
            LOG.error("Unable to release PostgreSQL advisory lock", var11);
         }

      }

      return var2;
   }

   private void lock() throws SQLException {
      while(!this.tryLock()) {
         try {
            Thread.sleep(100L);
         } catch (InterruptedException var2) {
            throw new FlywayException("Interrupted while attempting to acquire PostgreSQL advisory lock", var2);
         }
      }

   }

   private boolean tryLock() throws SQLException {
      List<Boolean> results = this.jdbcTemplate.query("SELECT pg_try_advisory_lock(" + this.lockNum + ")", new RowMapper<Boolean>() {
         public Boolean mapRow(ResultSet rs) throws SQLException {
            return "t".equals(rs.getString("pg_try_advisory_lock"));
         }
      });
      return results.size() == 1 && (Boolean)results.get(0);
   }
}
