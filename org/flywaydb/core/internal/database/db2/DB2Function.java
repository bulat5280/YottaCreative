package org.flywaydb.core.internal.database.db2;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Function;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class DB2Function extends Function {
   private static final Collection<String> typesWithLength = Arrays.asList("character", "char", "varchar", "graphic", "vargraphic", "decimal", "float", "varbinary");

   DB2Function(JdbcTemplate jdbcTemplate, Database database, Schema schema, String name, String... args) {
      super(jdbcTemplate, database, schema, name, args);
   }

   protected void doDrop() throws SQLException {
      try {
         this.jdbcTemplate.execute("DROP FUNCTION " + this.database.quote(this.schema.getName(), this.name) + "(" + this.argsToCommaSeparatedString(this.args) + ")");
      } catch (SQLException var2) {
         this.jdbcTemplate.execute("DROP FUNCTION " + this.database.quote(this.schema.getName(), this.name));
      }

   }

   private String argsToCommaSeparatedString(String[] args) {
      StringBuilder buf = new StringBuilder();
      String[] var3 = args;
      int var4 = args.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String arg = var3[var5];
         if (buf.length() > 0) {
            buf.append(",");
         }

         buf.append(arg);
         if (typesWithLength.contains(arg.toLowerCase())) {
            buf.append("()");
         }
      }

      return buf.toString();
   }

   public String toString() {
      return super.toString() + "(" + StringUtils.arrayToCommaDelimitedString(this.args) + ")";
   }
}
