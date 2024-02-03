package org.flywaydb.core.internal.database.saphana;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class SAPHANASchema extends Schema<SAPHANADatabase> {
   SAPHANASchema(JdbcTemplate jdbcTemplate, SAPHANADatabase database, String name) {
      super(jdbcTemplate, database, name);
   }

   protected boolean doExists() throws SQLException {
      return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM SYS.SCHEMAS WHERE SCHEMA_NAME=?", this.name) > 0;
   }

   protected boolean doEmpty() throws SQLException {
      int objectCount = this.jdbcTemplate.queryForInt("select count(*) from sys.tables where schema_name = ?", this.name);
      objectCount += this.jdbcTemplate.queryForInt("select count(*) from sys.views where schema_name = ?", this.name);
      objectCount += this.jdbcTemplate.queryForInt("select count(*) from sys.sequences where schema_name = ?", this.name);
      objectCount += this.jdbcTemplate.queryForInt("select count(*) from sys.synonyms where schema_name = ?", this.name);
      return objectCount == 0;
   }

   protected void doCreate() throws SQLException {
      this.jdbcTemplate.execute("CREATE SCHEMA " + ((SAPHANADatabase)this.database).quote(new String[]{this.name}));
   }

   protected void doDrop() throws SQLException {
      this.clean();
      this.jdbcTemplate.execute("DROP SCHEMA " + ((SAPHANADatabase)this.database).quote(new String[]{this.name}) + " RESTRICT");
   }

   protected void doClean() throws SQLException {
      Iterator var1 = this.generateDropStatements("SYNONYM").iterator();

      String dropStatement;
      while(var1.hasNext()) {
         dropStatement = (String)var1.next();
         this.jdbcTemplate.execute(dropStatement);
      }

      var1 = this.generateDropStatements("VIEW").iterator();

      while(var1.hasNext()) {
         dropStatement = (String)var1.next();
         this.jdbcTemplate.execute(dropStatement);
      }

      var1 = this.generateDropStatements("TABLE").iterator();

      while(var1.hasNext()) {
         dropStatement = (String)var1.next();
         this.jdbcTemplate.execute(dropStatement);
      }

      var1 = this.generateDropStatements("SEQUENCE").iterator();

      while(var1.hasNext()) {
         dropStatement = (String)var1.next();
         this.jdbcTemplate.execute(dropStatement);
      }

   }

   private List<String> generateDropStatements(String objectType) throws SQLException {
      List<String> dropStatements = new ArrayList();
      List<String> dbObjects = this.getDbObjects(objectType);
      Iterator var4 = dbObjects.iterator();

      while(var4.hasNext()) {
         String dbObject = (String)var4.next();
         dropStatements.add("DROP " + objectType + " " + ((SAPHANADatabase)this.database).quote(new String[]{this.name, dbObject}) + " CASCADE");
      }

      return dropStatements;
   }

   private List<String> getDbObjects(String objectType) throws SQLException {
      return this.jdbcTemplate.queryForStringList("select " + objectType + "_NAME from SYS." + objectType + "S where SCHEMA_NAME = ?", this.name);
   }

   protected Table[] doAllTables() throws SQLException {
      List<String> tableNames = this.getDbObjects("TABLE");
      Table[] tables = new Table[tableNames.size()];

      for(int i = 0; i < tableNames.size(); ++i) {
         tables[i] = new SAPHANATable(this.jdbcTemplate, this.database, this, (String)tableNames.get(i));
      }

      return tables;
   }

   public Table getTable(String tableName) {
      return new SAPHANATable(this.jdbcTemplate, this.database, this, tableName);
   }
}
