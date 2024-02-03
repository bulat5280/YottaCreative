package org.flywaydb.core.internal.database.derby;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class DerbySchema extends Schema<DerbyDatabase> {
   public DerbySchema(JdbcTemplate jdbcTemplate, DerbyDatabase database, String name) {
      super(jdbcTemplate, database, name);
   }

   protected boolean doExists() throws SQLException {
      return this.jdbcTemplate.queryForInt("SELECT COUNT (*) FROM sys.sysschemas WHERE schemaname=?", this.name) > 0;
   }

   protected boolean doEmpty() throws SQLException {
      return this.allTables().length == 0;
   }

   protected void doCreate() throws SQLException {
      this.jdbcTemplate.execute("CREATE SCHEMA " + ((DerbyDatabase)this.database).quote(new String[]{this.name}));
   }

   protected void doDrop() throws SQLException {
      this.clean();
      this.jdbcTemplate.execute("DROP SCHEMA " + ((DerbyDatabase)this.database).quote(new String[]{this.name}) + " RESTRICT");
   }

   protected void doClean() throws SQLException {
      List<String> triggerNames = this.listObjectNames("TRIGGER", "");
      Iterator var2 = this.generateDropStatements("TRIGGER", triggerNames, "").iterator();

      String statement;
      while(var2.hasNext()) {
         statement = (String)var2.next();
         this.jdbcTemplate.execute(statement);
      }

      var2 = this.generateDropStatementsForConstraints().iterator();

      while(var2.hasNext()) {
         statement = (String)var2.next();
         this.jdbcTemplate.execute(statement);
      }

      List<String> viewNames = this.listObjectNames("TABLE", "TABLETYPE='V'");
      Iterator var8 = this.generateDropStatements("VIEW", viewNames, "").iterator();

      while(var8.hasNext()) {
         String statement = (String)var8.next();
         this.jdbcTemplate.execute(statement);
      }

      Table[] var9 = this.allTables();
      int var10 = var9.length;

      for(int var5 = 0; var5 < var10; ++var5) {
         Table table = var9[var5];
         table.drop();
      }

      List<String> sequenceNames = this.listObjectNames("SEQUENCE", "");
      Iterator var12 = this.generateDropStatements("SEQUENCE", sequenceNames, "RESTRICT").iterator();

      while(var12.hasNext()) {
         String statement = (String)var12.next();
         this.jdbcTemplate.execute(statement);
      }

   }

   private List<String> generateDropStatementsForConstraints() throws SQLException {
      List<Map<String, String>> results = this.jdbcTemplate.queryForList("SELECT c.constraintname, t.tablename FROM sys.sysconstraints c INNER JOIN sys.systables t ON c.tableid = t.tableid INNER JOIN sys.sysschemas s ON c.schemaid = s.schemaid WHERE c.type = 'F' AND s.schemaname = ?", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = results.iterator();

      while(var3.hasNext()) {
         Map<String, String> result = (Map)var3.next();
         String dropStatement = "ALTER TABLE " + ((DerbyDatabase)this.database).quote(new String[]{this.name, (String)result.get("TABLENAME")}) + " DROP CONSTRAINT " + ((DerbyDatabase)this.database).quote(new String[]{(String)result.get("CONSTRAINTNAME")});
         statements.add(dropStatement);
      }

      return statements;
   }

   private List<String> generateDropStatements(String objectType, List<String> objectNames, String dropStatementSuffix) {
      List<String> statements = new ArrayList();
      Iterator var5 = objectNames.iterator();

      while(var5.hasNext()) {
         String objectName = (String)var5.next();
         String dropStatement = "DROP " + objectType + " " + ((DerbyDatabase)this.database).quote(new String[]{this.name, objectName}) + " " + dropStatementSuffix;
         statements.add(dropStatement);
      }

      return statements;
   }

   protected Table[] doAllTables() throws SQLException {
      List<String> tableNames = this.listObjectNames("TABLE", "TABLETYPE='T'");
      Table[] tables = new Table[tableNames.size()];

      for(int i = 0; i < tableNames.size(); ++i) {
         tables[i] = new DerbyTable(this.jdbcTemplate, this.database, this, (String)tableNames.get(i));
      }

      return tables;
   }

   private List<String> listObjectNames(String objectType, String querySuffix) throws SQLException {
      String query = "SELECT " + objectType + "name FROM sys.sys" + objectType + "s WHERE schemaid in (SELECT schemaid FROM sys.sysschemas where schemaname = ?)";
      if (StringUtils.hasLength(querySuffix)) {
         query = query + " AND " + querySuffix;
      }

      return this.jdbcTemplate.queryForStringList(query, this.name);
   }

   public Table getTable(String tableName) {
      return new DerbyTable(this.jdbcTemplate, this.database, this, tableName);
   }
}
