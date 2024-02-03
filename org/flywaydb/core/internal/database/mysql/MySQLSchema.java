package org.flywaydb.core.internal.database.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class MySQLSchema extends Schema<MySQLDatabase> {
   MySQLSchema(JdbcTemplate jdbcTemplate, MySQLDatabase database, String name) {
      super(jdbcTemplate, database, name);
   }

   protected boolean doExists() throws SQLException {
      return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM information_schema.schemata WHERE schema_name=?", this.name) > 0;
   }

   protected boolean doEmpty() throws SQLException {
      int objectCount = this.jdbcTemplate.queryForInt("Select (Select count(*) from information_schema.TABLES Where TABLE_SCHEMA=?) + (Select count(*) from information_schema.VIEWS Where TABLE_SCHEMA=?) + (Select count(*) from information_schema.TABLE_CONSTRAINTS Where TABLE_SCHEMA=?) + (Select count(*) from information_schema.EVENTS Where EVENT_SCHEMA=?) + (Select count(*) from information_schema.TRIGGERS Where TRIGGER_SCHEMA=?) + (Select count(*) from information_schema.ROUTINES Where ROUTINE_SCHEMA=?)", this.name, this.name, this.name, this.name, this.name, this.name);
      return objectCount == 0;
   }

   protected void doCreate() throws SQLException {
      this.jdbcTemplate.execute("CREATE SCHEMA " + ((MySQLDatabase)this.database).quote(new String[]{this.name}));
   }

   protected void doDrop() throws SQLException {
      this.jdbcTemplate.execute("DROP SCHEMA " + ((MySQLDatabase)this.database).quote(new String[]{this.name}));
   }

   protected void doClean() throws SQLException {
      Iterator var1 = this.cleanEvents().iterator();

      String statement;
      while(var1.hasNext()) {
         statement = (String)var1.next();
         this.jdbcTemplate.execute(statement);
      }

      var1 = this.cleanRoutines().iterator();

      while(var1.hasNext()) {
         statement = (String)var1.next();
         this.jdbcTemplate.execute(statement);
      }

      var1 = this.cleanViews().iterator();

      while(var1.hasNext()) {
         statement = (String)var1.next();
         this.jdbcTemplate.execute(statement);
      }

      this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
      Table[] var5 = this.allTables();
      int var6 = var5.length;

      for(int var3 = 0; var3 < var6; ++var3) {
         Table table = var5[var3];
         table.drop();
      }

      this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
   }

   private List<String> cleanEvents() throws SQLException {
      List<String> eventNames = this.jdbcTemplate.queryForStringList("SELECT event_name FROM information_schema.events WHERE event_schema=?", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = eventNames.iterator();

      while(var3.hasNext()) {
         String eventName = (String)var3.next();
         statements.add("DROP EVENT " + ((MySQLDatabase)this.database).quote(new String[]{this.name, eventName}));
      }

      return statements;
   }

   private List<String> cleanRoutines() throws SQLException {
      List<Map<String, String>> routineNames = this.jdbcTemplate.queryForList("SELECT routine_name as 'N', routine_type as 'T' FROM information_schema.routines WHERE routine_schema=?", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = routineNames.iterator();

      while(var3.hasNext()) {
         Map<String, String> row = (Map)var3.next();
         String routineName = (String)row.get("N");
         String routineType = (String)row.get("T");
         statements.add("DROP " + routineType + " " + ((MySQLDatabase)this.database).quote(new String[]{this.name, routineName}));
      }

      return statements;
   }

   private List<String> cleanViews() throws SQLException {
      List<String> viewNames = this.jdbcTemplate.queryForStringList("SELECT table_name FROM information_schema.views WHERE table_schema=?", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = viewNames.iterator();

      while(var3.hasNext()) {
         String viewName = (String)var3.next();
         statements.add("DROP VIEW " + ((MySQLDatabase)this.database).quote(new String[]{this.name, viewName}));
      }

      return statements;
   }

   protected Table[] doAllTables() throws SQLException {
      List<String> tableNames = this.jdbcTemplate.queryForStringList("SELECT table_name FROM information_schema.tables WHERE table_schema=? AND table_type='BASE TABLE'", this.name);
      Table[] tables = new Table[tableNames.size()];

      for(int i = 0; i < tableNames.size(); ++i) {
         tables[i] = new MySQLTable(this.jdbcTemplate, this.database, this, (String)tableNames.get(i));
      }

      return tables;
   }

   public Table getTable(String tableName) {
      return new MySQLTable(this.jdbcTemplate, this.database, this, tableName);
   }
}
