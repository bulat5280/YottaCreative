package org.flywaydb.core.internal.database.postgresql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.database.Type;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class PostgreSQLSchema extends Schema<PostgreSQLDatabase> {
   PostgreSQLSchema(JdbcTemplate jdbcTemplate, PostgreSQLDatabase database, String name) {
      super(jdbcTemplate, database, name);
   }

   protected boolean doExists() throws SQLException {
      return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM pg_namespace WHERE nspname=?", this.name) > 0;
   }

   protected boolean doEmpty() throws SQLException {
      return !this.jdbcTemplate.queryForBoolean("SELECT EXISTS (   SELECT 1\n   FROM   pg_catalog.pg_class c\n   JOIN   pg_catalog.pg_namespace n ON n.oid = c.relnamespace\n   WHERE  n.nspname = ?)", this.name);
   }

   protected void doCreate() throws SQLException {
      this.jdbcTemplate.execute("CREATE SCHEMA " + ((PostgreSQLDatabase)this.database).quote(new String[]{this.name}));
   }

   protected void doDrop() throws SQLException {
      this.jdbcTemplate.execute("DROP SCHEMA " + ((PostgreSQLDatabase)this.database).quote(new String[]{this.name}) + " CASCADE");
   }

   protected void doClean() throws SQLException {
      Iterator var1 = this.generateDropStatementsForMaterializedViews().iterator();

      String statement;
      while(var1.hasNext()) {
         statement = (String)var1.next();
         this.jdbcTemplate.execute(statement);
      }

      var1 = this.generateDropStatementsForViews().iterator();

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

      var1 = this.generateDropStatementsForBaseTypes(true).iterator();

      while(var1.hasNext()) {
         statement = (String)var1.next();
         this.jdbcTemplate.execute(statement);
      }

      var1 = this.generateDropStatementsForAggregates().iterator();

      while(var1.hasNext()) {
         statement = (String)var1.next();
         this.jdbcTemplate.execute(statement);
      }

      var1 = this.generateDropStatementsForRoutines().iterator();

      while(var1.hasNext()) {
         statement = (String)var1.next();
         this.jdbcTemplate.execute(statement);
      }

      var1 = this.generateDropStatementsForEnums().iterator();

      while(var1.hasNext()) {
         statement = (String)var1.next();
         this.jdbcTemplate.execute(statement);
      }

      var1 = this.generateDropStatementsForDomains().iterator();

      while(var1.hasNext()) {
         statement = (String)var1.next();
         this.jdbcTemplate.execute(statement);
      }

      var1 = this.generateDropStatementsForSequences().iterator();

      while(var1.hasNext()) {
         statement = (String)var1.next();
         this.jdbcTemplate.execute(statement);
      }

      var1 = this.generateDropStatementsForBaseTypes(false).iterator();

      while(var1.hasNext()) {
         statement = (String)var1.next();
         this.jdbcTemplate.execute(statement);
      }

   }

   private List<String> generateDropStatementsForSequences() throws SQLException {
      List<String> sequenceNames = this.jdbcTemplate.queryForStringList("SELECT sequence_name FROM information_schema.sequences WHERE sequence_schema=?", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = sequenceNames.iterator();

      while(var3.hasNext()) {
         String sequenceName = (String)var3.next();
         statements.add("DROP SEQUENCE IF EXISTS " + ((PostgreSQLDatabase)this.database).quote(new String[]{this.name, sequenceName}));
      }

      return statements;
   }

   private List<String> generateDropStatementsForBaseTypes(boolean recreate) throws SQLException {
      List<Map<String, String>> rows = this.jdbcTemplate.queryForList("select typname, typcategory from pg_catalog.pg_type t where (t.typrelid = 0 OR (SELECT c.relkind = 'c' FROM pg_catalog.pg_class c WHERE c.oid = t.typrelid)) and NOT EXISTS(SELECT 1 FROM pg_catalog.pg_type el WHERE el.oid = t.typelem AND el.typarray = t.oid) and t.typnamespace in (select oid from pg_catalog.pg_namespace where nspname = ?)", this.name);
      List<String> statements = new ArrayList();
      Iterator var4 = rows.iterator();

      Map row;
      while(var4.hasNext()) {
         row = (Map)var4.next();
         statements.add("DROP TYPE IF EXISTS " + ((PostgreSQLDatabase)this.database).quote(new String[]{this.name, (String)row.get("typname")}) + " CASCADE");
      }

      if (recreate) {
         var4 = rows.iterator();

         while(var4.hasNext()) {
            row = (Map)var4.next();
            if (Arrays.asList("P", "U").contains(row.get("typcategory"))) {
               statements.add("CREATE TYPE " + ((PostgreSQLDatabase)this.database).quote(new String[]{this.name, (String)row.get("typname")}));
            }
         }
      }

      return statements;
   }

   private List<String> generateDropStatementsForAggregates() throws SQLException {
      List<Map<String, String>> rows = this.jdbcTemplate.queryForList("SELECT proname, oidvectortypes(proargtypes) AS args FROM pg_proc INNER JOIN pg_namespace ns ON (pg_proc.pronamespace = ns.oid) WHERE pg_proc.proisagg = true AND ns.nspname = ?", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = rows.iterator();

      while(var3.hasNext()) {
         Map<String, String> row = (Map)var3.next();
         statements.add("DROP AGGREGATE IF EXISTS " + ((PostgreSQLDatabase)this.database).quote(new String[]{this.name, (String)row.get("proname")}) + "(" + (String)row.get("args") + ") CASCADE");
      }

      return statements;
   }

   private List<String> generateDropStatementsForRoutines() throws SQLException {
      List<Map<String, String>> rows = this.jdbcTemplate.queryForList("SELECT proname, oidvectortypes(proargtypes) AS args FROM pg_proc INNER JOIN pg_namespace ns ON (pg_proc.pronamespace = ns.oid) LEFT JOIN pg_depend dep ON dep.objid = pg_proc.oid AND dep.deptype = 'e' WHERE pg_proc.proisagg = false AND ns.nspname = ? AND dep.objid IS NULL", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = rows.iterator();

      while(var3.hasNext()) {
         Map<String, String> row = (Map)var3.next();
         statements.add("DROP FUNCTION IF EXISTS " + ((PostgreSQLDatabase)this.database).quote(new String[]{this.name, (String)row.get("proname")}) + "(" + (String)row.get("args") + ") CASCADE");
      }

      return statements;
   }

   private List<String> generateDropStatementsForEnums() throws SQLException {
      List<String> enumNames = this.jdbcTemplate.queryForStringList("SELECT t.typname FROM pg_catalog.pg_type t INNER JOIN pg_catalog.pg_namespace n ON n.oid = t.typnamespace WHERE n.nspname = ? and t.typtype = 'e'", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = enumNames.iterator();

      while(var3.hasNext()) {
         String enumName = (String)var3.next();
         statements.add("DROP TYPE " + ((PostgreSQLDatabase)this.database).quote(new String[]{this.name, enumName}));
      }

      return statements;
   }

   private List<String> generateDropStatementsForDomains() throws SQLException {
      List<String> domainNames = this.jdbcTemplate.queryForStringList("SELECT domain_name FROM information_schema.domains WHERE domain_schema=?", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = domainNames.iterator();

      while(var3.hasNext()) {
         String domainName = (String)var3.next();
         statements.add("DROP DOMAIN " + ((PostgreSQLDatabase)this.database).quote(new String[]{this.name, domainName}));
      }

      return statements;
   }

   private List<String> generateDropStatementsForMaterializedViews() throws SQLException {
      List<String> viewNames = this.jdbcTemplate.queryForStringList("SELECT relname FROM pg_catalog.pg_class c JOIN pg_namespace n ON n.oid = c.relnamespace WHERE c.relkind = 'm' AND n.nspname = ?", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = viewNames.iterator();

      while(var3.hasNext()) {
         String domainName = (String)var3.next();
         statements.add("DROP MATERIALIZED VIEW IF EXISTS " + ((PostgreSQLDatabase)this.database).quote(new String[]{this.name, domainName}) + " CASCADE");
      }

      return statements;
   }

   private List<String> generateDropStatementsForViews() throws SQLException {
      List<String> viewNames = this.jdbcTemplate.queryForStringList("SELECT relname FROM pg_catalog.pg_class c JOIN pg_namespace n ON n.oid = c.relnamespace LEFT JOIN pg_depend dep ON dep.objid = c.oid AND dep.deptype = 'e' WHERE c.relkind = 'v' AND  n.nspname = ? AND dep.objid IS NULL", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = viewNames.iterator();

      while(var3.hasNext()) {
         String domainName = (String)var3.next();
         statements.add("DROP VIEW IF EXISTS " + ((PostgreSQLDatabase)this.database).quote(new String[]{this.name, domainName}) + " CASCADE");
      }

      return statements;
   }

   protected Table[] doAllTables() throws SQLException {
      List<String> tableNames = this.jdbcTemplate.queryForStringList("SELECT t.table_name FROM information_schema.tables t WHERE table_schema=? AND table_type='BASE TABLE' AND NOT (SELECT EXISTS (SELECT inhrelid FROM pg_catalog.pg_inherits WHERE inhrelid = (quote_ident(t.table_schema)||'.'||quote_ident(t.table_name))::regclass::oid))", this.name);
      Table[] tables = new Table[tableNames.size()];

      for(int i = 0; i < tableNames.size(); ++i) {
         tables[i] = new PostgreSQLTable(this.jdbcTemplate, this.database, this, (String)tableNames.get(i));
      }

      return tables;
   }

   public Table getTable(String tableName) {
      return new PostgreSQLTable(this.jdbcTemplate, this.database, this, tableName);
   }

   protected Type getType(String typeName) {
      return new PostgreSQLType(this.jdbcTemplate, this.database, this, typeName);
   }
}
