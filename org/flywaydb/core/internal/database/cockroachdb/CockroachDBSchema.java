package org.flywaydb.core.internal.database.cockroachdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class CockroachDBSchema extends Schema<CockroachDBDatabase> {
   CockroachDBSchema(JdbcTemplate jdbcTemplate, CockroachDBDatabase database, String name) {
      super(jdbcTemplate, database, name);
   }

   protected boolean doExists() throws SQLException {
      return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM pg_namespace WHERE nspname=?", this.name) > 0;
   }

   protected boolean doEmpty() throws SQLException {
      int objectCount = this.jdbcTemplate.queryForInt("SELECT count(*) FROM information_schema.tables WHERE table_schema=? AND table_type='BASE TABLE'", this.name);
      return objectCount == 0;
   }

   protected void doCreate() throws SQLException {
      this.jdbcTemplate.execute("CREATE DATABASE " + ((CockroachDBDatabase)this.database).quote(new String[]{this.name}));
   }

   protected void doDrop() throws SQLException {
      this.jdbcTemplate.execute("DROP DATABASE " + ((CockroachDBDatabase)this.database).quote(new String[]{this.name}));
   }

   protected void doClean() throws SQLException {
      Iterator var1 = this.generateDropStatementsForViews().iterator();

      while(var1.hasNext()) {
         String statement = (String)var1.next();
         this.jdbcTemplate.execute(statement);
      }

      Table[] var5 = this.allTables();
      int var6 = var5.length;

      for(int var3 = 0; var3 < var6; ++var3) {
         Table table = var5[var3];
         table.drop();
      }

   }

   private List<String> generateDropStatementsForViews() throws SQLException {
      List<String> viewNames = this.jdbcTemplate.queryForStringList("SELECT relname FROM pg_catalog.pg_class c JOIN pg_namespace n ON n.oid = c.relnamespace LEFT JOIN pg_depend dep ON dep.objid = c.oid AND dep.deptype = 'e' WHERE c.relkind = 'v' AND  n.nspname = ? AND dep.objid IS NULL", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = viewNames.iterator();

      while(var3.hasNext()) {
         String domainName = (String)var3.next();
         statements.add("DROP VIEW IF EXISTS " + ((CockroachDBDatabase)this.database).quote(new String[]{this.name, domainName}) + " CASCADE");
      }

      return statements;
   }

   protected Table[] doAllTables() throws SQLException {
      List<String> tableNames = this.jdbcTemplate.queryForStringList("SELECT table_name FROM information_schema.tables WHERE table_schema=? AND table_type='BASE TABLE'", this.name);
      Table[] tables = new Table[tableNames.size()];

      for(int i = 0; i < tableNames.size(); ++i) {
         tables[i] = new CockroachDBTable(this.jdbcTemplate, this.database, this, (String)tableNames.get(i));
      }

      return tables;
   }

   public Table getTable(String tableName) {
      return new CockroachDBTable(this.jdbcTemplate, this.database, this, tableName);
   }
}
