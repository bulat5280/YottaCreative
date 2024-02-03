package org.flywaydb.core.internal.database.h2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class H2Schema extends Schema<H2Database> {
   private static final Log LOG = LogFactory.getLog(H2Schema.class);

   H2Schema(JdbcTemplate jdbcTemplate, H2Database database, String name) {
      super(jdbcTemplate, database, name);
   }

   protected boolean doExists() throws SQLException {
      return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM INFORMATION_SCHEMA.schemata WHERE schema_name=?", this.name) > 0;
   }

   protected boolean doEmpty() throws SQLException {
      return this.allTables().length == 0;
   }

   protected void doCreate() throws SQLException {
      this.jdbcTemplate.execute("CREATE SCHEMA " + ((H2Database)this.database).quote(new String[]{this.name}));
   }

   protected void doDrop() throws SQLException {
      this.jdbcTemplate.execute("DROP SCHEMA " + ((H2Database)this.database).quote(new String[]{this.name}));
   }

   protected void doClean() throws SQLException {
      Table[] var1 = this.allTables();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Table table = var1[var3];
         table.drop();
      }

      List<String> sequenceNames = this.listObjectNames("SEQUENCE", "IS_GENERATED = false");
      Iterator var7 = this.generateDropStatements("SEQUENCE", sequenceNames).iterator();

      while(var7.hasNext()) {
         String statement = (String)var7.next();
         this.jdbcTemplate.execute(statement);
      }

      List<String> constantNames = this.listObjectNames("CONSTANT", "");
      Iterator var10 = this.generateDropStatements("CONSTANT", constantNames).iterator();

      while(var10.hasNext()) {
         String statement = (String)var10.next();
         this.jdbcTemplate.execute(statement);
      }

      List<String> domainNames = this.listObjectNames("DOMAIN", "");
      if (!domainNames.isEmpty()) {
         if (this.name.equals(((H2Database)this.database).getMainConnection().getCurrentSchemaName())) {
            Iterator var13 = this.generateDropStatementsForCurrentSchema("DOMAIN", domainNames).iterator();

            while(var13.hasNext()) {
               String statement = (String)var13.next();
               this.jdbcTemplate.execute(statement);
            }
         } else {
            LOG.error("Unable to drop DOMAIN objects in schema " + ((H2Database)this.database).quote(new String[]{this.name}) + " due to H2 bug! (More info: http://code.google.com/p/h2database/issues/detail?id=306)");
         }
      }

   }

   private List<String> generateDropStatements(String objectType, List<String> objectNames) {
      List<String> statements = new ArrayList();
      Iterator var4 = objectNames.iterator();

      while(var4.hasNext()) {
         String objectName = (String)var4.next();
         String dropStatement = "DROP " + objectType + ((H2Database)this.database).quote(new String[]{this.name, objectName});
         statements.add(dropStatement);
      }

      return statements;
   }

   private List<String> generateDropStatementsForCurrentSchema(String objectType, List<String> objectNames) {
      List<String> statements = new ArrayList();
      Iterator var4 = objectNames.iterator();

      while(var4.hasNext()) {
         String objectName = (String)var4.next();
         String dropStatement = "DROP " + objectType + ((H2Database)this.database).quote(new String[]{objectName});
         statements.add(dropStatement);
      }

      return statements;
   }

   protected Table[] doAllTables() throws SQLException {
      List<String> tableNames = this.listObjectNames("TABLE", "TABLE_TYPE = 'TABLE'");
      Table[] tables = new Table[tableNames.size()];

      for(int i = 0; i < tableNames.size(); ++i) {
         tables[i] = new H2Table(this.jdbcTemplate, this.database, this, (String)tableNames.get(i));
      }

      return tables;
   }

   private List<String> listObjectNames(String objectType, String querySuffix) throws SQLException {
      String query = "SELECT " + objectType + "_NAME FROM INFORMATION_SCHEMA." + objectType + "s WHERE " + objectType + "_schema = ?";
      if (StringUtils.hasLength(querySuffix)) {
         query = query + " AND " + querySuffix;
      }

      return this.jdbcTemplate.queryForStringList(query, this.name);
   }

   public Table getTable(String tableName) {
      return new H2Table(this.jdbcTemplate, this.database, this, tableName);
   }
}
