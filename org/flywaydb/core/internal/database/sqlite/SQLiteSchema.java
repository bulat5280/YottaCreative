package org.flywaydb.core.internal.database.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class SQLiteSchema extends Schema<SQLiteDatabase> {
   private static final Log LOG = LogFactory.getLog(SQLiteSchema.class);
   private static final List<String> IGNORED_SYSTEM_TABLE_NAMES = Arrays.asList("android_metadata", "sqlite_sequence");

   SQLiteSchema(JdbcTemplate jdbcTemplate, SQLiteDatabase database, String name) {
      super(jdbcTemplate, database, name);
   }

   protected boolean doExists() throws SQLException {
      try {
         this.doAllTables();
         return true;
      } catch (SQLException var2) {
         return false;
      }
   }

   protected boolean doEmpty() throws SQLException {
      Table[] tables = this.allTables();
      List<String> tableNames = new ArrayList();
      Table[] var3 = tables;
      int var4 = tables.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Table table = var3[var5];
         String tableName = table.getName();
         if (!IGNORED_SYSTEM_TABLE_NAMES.contains(tableName)) {
            tableNames.add(tableName);
         }
      }

      return tableNames.isEmpty();
   }

   protected void doCreate() throws SQLException {
      LOG.info("SQLite does not support creating schemas. Schema not created: " + this.name);
   }

   protected void doDrop() throws SQLException {
      LOG.info("SQLite does not support dropping schemas. Schema not dropped: " + this.name);
   }

   protected void doClean() throws SQLException {
      List<String> viewNames = this.jdbcTemplate.queryForStringList("SELECT tbl_name FROM " + ((SQLiteDatabase)this.database).quote(new String[]{this.name}) + ".sqlite_master WHERE type='view'");
      Iterator var2 = viewNames.iterator();

      while(var2.hasNext()) {
         String viewName = (String)var2.next();
         this.jdbcTemplate.execute("DROP VIEW " + ((SQLiteDatabase)this.database).quote(new String[]{this.name, viewName}));
      }

      Table[] var6 = this.allTables();
      int var7 = var6.length;

      for(int var4 = 0; var4 < var7; ++var4) {
         Table table = var6[var4];
         table.drop();
      }

      if (this.getTable("sqlite_sequence").exists()) {
         this.jdbcTemplate.execute("DELETE FROM sqlite_sequence");
      }

   }

   protected Table[] doAllTables() throws SQLException {
      List<String> tableNames = this.jdbcTemplate.queryForStringList("SELECT tbl_name FROM " + ((SQLiteDatabase)this.database).quote(new String[]{this.name}) + ".sqlite_master WHERE type='table'");
      Table[] tables = new Table[tableNames.size()];

      for(int i = 0; i < tableNames.size(); ++i) {
         tables[i] = new SQLiteTable(this.jdbcTemplate, this.database, this, (String)tableNames.get(i));
      }

      return tables;
   }

   public Table getTable(String tableName) {
      return new SQLiteTable(this.jdbcTemplate, this.database, this, tableName);
   }
}
