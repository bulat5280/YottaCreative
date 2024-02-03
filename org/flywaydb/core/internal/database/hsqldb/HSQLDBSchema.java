package org.flywaydb.core.internal.database.hsqldb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class HSQLDBSchema extends Schema<HSQLDBDatabase> {
   HSQLDBSchema(JdbcTemplate jdbcTemplate, HSQLDBDatabase database, String name) {
      super(jdbcTemplate, database, name);
   }

   protected boolean doExists() throws SQLException {
      return this.jdbcTemplate.queryForInt("SELECT COUNT (*) FROM information_schema.system_schemas WHERE table_schem=?", this.name) > 0;
   }

   protected boolean doEmpty() throws SQLException {
      return this.allTables().length == 0;
   }

   protected void doCreate() throws SQLException {
      String user = this.jdbcTemplate.queryForString("SELECT USER() FROM (VALUES(0))");
      this.jdbcTemplate.execute("CREATE SCHEMA " + ((HSQLDBDatabase)this.database).quote(new String[]{this.name}) + " AUTHORIZATION " + user);
   }

   protected void doDrop() throws SQLException {
      this.jdbcTemplate.execute("DROP SCHEMA " + ((HSQLDBDatabase)this.database).quote(new String[]{this.name}) + " CASCADE");
   }

   protected void doClean() throws SQLException {
      Table[] var1 = this.allTables();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Table table = var1[var3];
         table.drop();
      }

      Iterator var5 = this.generateDropStatementsForSequences().iterator();

      while(var5.hasNext()) {
         String statement = (String)var5.next();
         this.jdbcTemplate.execute(statement);
      }

   }

   private List<String> generateDropStatementsForSequences() throws SQLException {
      List<String> sequenceNames = this.jdbcTemplate.queryForStringList("SELECT SEQUENCE_NAME FROM INFORMATION_SCHEMA.SYSTEM_SEQUENCES where SEQUENCE_SCHEMA = ?", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = sequenceNames.iterator();

      while(var3.hasNext()) {
         String seqName = (String)var3.next();
         statements.add("DROP SEQUENCE " + ((HSQLDBDatabase)this.database).quote(new String[]{this.name, seqName}));
      }

      return statements;
   }

   protected Table[] doAllTables() throws SQLException {
      List<String> tableNames = this.jdbcTemplate.queryForStringList("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.SYSTEM_TABLES where TABLE_SCHEM = ? AND TABLE_TYPE = 'TABLE'", this.name);
      Table[] tables = new Table[tableNames.size()];

      for(int i = 0; i < tableNames.size(); ++i) {
         tables[i] = new HSQLDBTable(this.jdbcTemplate, this.database, this, (String)tableNames.get(i));
      }

      return tables;
   }

   public Table getTable(String tableName) {
      return new HSQLDBTable(this.jdbcTemplate, this.database, this, tableName);
   }
}
