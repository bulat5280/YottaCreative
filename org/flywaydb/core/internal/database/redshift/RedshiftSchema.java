package org.flywaydb.core.internal.database.redshift;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.database.Type;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class RedshiftSchema extends Schema<RedshiftDatabase> {
   RedshiftSchema(JdbcTemplate jdbcTemplate, RedshiftDatabase database, String name) {
      super(jdbcTemplate, database, name);
   }

   protected boolean doExists() throws SQLException {
      return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM pg_namespace WHERE nspname=?", this.name) > 0;
   }

   protected boolean doEmpty() throws SQLException {
      return !this.jdbcTemplate.queryForBoolean("SELECT EXISTS (   SELECT 1\n   FROM   pg_catalog.pg_class c\n   JOIN   pg_catalog.pg_namespace n ON n.oid = c.relnamespace\n   WHERE  n.nspname = ?)", this.name);
   }

   protected void doCreate() throws SQLException {
      this.jdbcTemplate.execute("CREATE SCHEMA " + ((RedshiftDatabase)this.database).quote(new String[]{this.name}));
   }

   protected void doDrop() throws SQLException {
      this.jdbcTemplate.execute("DROP SCHEMA " + ((RedshiftDatabase)this.database).quote(new String[]{this.name}) + " CASCADE");
   }

   protected void doClean() throws SQLException {
      Iterator var1 = this.generateDropStatementsForViews().iterator();

      String statement;
      while(var1.hasNext()) {
         statement = (String)var1.next();
         this.jdbcTemplate.execute(statement);
      }

      Table[] var5 = this.allTables();
      int var6 = var5.length;

      for(int var3 = 0; var3 < var6; ++var3) {
         Table table = var5[var3];
         table.drop();
      }

      var1 = this.generateDropStatementsForRoutines().iterator();

      while(var1.hasNext()) {
         statement = (String)var1.next();
         this.jdbcTemplate.execute(statement);
      }

   }

   private List<String> generateDropStatementsForRoutines() throws SQLException {
      List<Map<String, String>> rows = this.jdbcTemplate.queryForList("SELECT proname, oidvectortypes(proargtypes) AS args FROM pg_proc INNER JOIN pg_namespace ns ON (pg_proc.pronamespace = ns.oid) LEFT JOIN pg_depend dep ON dep.objid = pg_proc.oid AND dep.deptype = 'e' WHERE pg_proc.proisagg = false AND ns.nspname = ? AND dep.objid IS NULL", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = rows.iterator();

      while(var3.hasNext()) {
         Map<String, String> row = (Map)var3.next();
         statements.add("DROP FUNCTION " + ((RedshiftDatabase)this.database).quote(new String[]{this.name, (String)row.get("proname")}) + "(" + (String)row.get("args") + ") CASCADE");
      }

      return statements;
   }

   private List<String> generateDropStatementsForViews() throws SQLException {
      List<String> viewNames = this.jdbcTemplate.queryForStringList("SELECT relname FROM pg_catalog.pg_class c JOIN pg_namespace n ON n.oid = c.relnamespace LEFT JOIN pg_depend dep ON dep.objid = c.oid AND dep.deptype = 'e' WHERE c.relkind = 'v' AND  n.nspname = ? AND dep.objid IS NULL", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = viewNames.iterator();

      while(var3.hasNext()) {
         String domainName = (String)var3.next();
         statements.add("DROP VIEW IF EXISTS " + ((RedshiftDatabase)this.database).quote(new String[]{this.name, domainName}) + " CASCADE");
      }

      return statements;
   }

   protected Table[] doAllTables() throws SQLException {
      List<String> tableNames = this.jdbcTemplate.queryForStringList("SELECT t.table_name FROM information_schema.tables t WHERE table_schema=? AND table_type='BASE TABLE'", this.name);
      Table[] tables = new Table[tableNames.size()];

      for(int i = 0; i < tableNames.size(); ++i) {
         tables[i] = new RedshiftTable(this.jdbcTemplate, this.database, this, (String)tableNames.get(i));
      }

      return tables;
   }

   public Table getTable(String tableName) {
      return new RedshiftTable(this.jdbcTemplate, this.database, this, tableName);
   }

   protected Type getType(String typeName) {
      return new RedshiftType(this.jdbcTemplate, this.database, this, typeName);
   }
}
