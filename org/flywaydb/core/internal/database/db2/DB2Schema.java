package org.flywaydb.core.internal.database.db2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.flywaydb.core.internal.database.Function;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.database.Type;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class DB2Schema extends Schema<DB2Database> {
   DB2Schema(JdbcTemplate jdbcTemplate, DB2Database database, String name) {
      super(jdbcTemplate, database, name);
   }

   protected boolean doExists() throws SQLException {
      return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM syscat.schemata WHERE schemaname=?", this.name) > 0;
   }

   protected boolean doEmpty() throws SQLException {
      int objectCount = this.jdbcTemplate.queryForInt("select count(*) from syscat.tables where tabschema = ?", this.name);
      objectCount += this.jdbcTemplate.queryForInt("select count(*) from syscat.views where viewschema = ?", this.name);
      objectCount += this.jdbcTemplate.queryForInt("select count(*) from syscat.sequences where seqschema = ?", this.name);
      objectCount += this.jdbcTemplate.queryForInt("select count(*) from syscat.indexes where indschema = ?", this.name);
      objectCount += this.jdbcTemplate.queryForInt("select count(*) from syscat.procedures where procschema = ?", this.name);
      objectCount += this.jdbcTemplate.queryForInt("select count(*) from syscat.functions where funcschema = ?", this.name);
      objectCount += this.jdbcTemplate.queryForInt("select count(*) from syscat.triggers where trigschema = ?", this.name);
      return objectCount == 0;
   }

   protected void doCreate() throws SQLException {
      this.jdbcTemplate.execute("CREATE SCHEMA " + ((DB2Database)this.database).quote(new String[]{this.name}));
   }

   protected void doDrop() throws SQLException {
      this.clean();
      this.jdbcTemplate.execute("DROP SCHEMA " + ((DB2Database)this.database).quote(new String[]{this.name}) + " RESTRICT");
   }

   protected void doClean() throws SQLException {
      if (((DB2Database)this.database).getMajorVersion() >= 10) {
         List<String> dropVersioningStatements = this.generateDropVersioningStatement();
         Iterator var2;
         String dropVersioningStatement;
         if (!dropVersioningStatements.isEmpty()) {
            var2 = this.generateDropStatements("S", "TABLE").iterator();

            while(var2.hasNext()) {
               dropVersioningStatement = (String)var2.next();
               this.jdbcTemplate.execute(dropVersioningStatement);
            }
         }

         var2 = dropVersioningStatements.iterator();

         while(var2.hasNext()) {
            dropVersioningStatement = (String)var2.next();
            this.jdbcTemplate.execute(dropVersioningStatement);
         }
      }

      Iterator var5 = this.generateDropStatementsForViews().iterator();

      String dropStatement;
      while(var5.hasNext()) {
         dropStatement = (String)var5.next();
         this.jdbcTemplate.execute(dropStatement);
      }

      var5 = this.generateDropStatements("A", "ALIAS").iterator();

      while(var5.hasNext()) {
         dropStatement = (String)var5.next();
         this.jdbcTemplate.execute(dropStatement);
      }

      var5 = this.generateDropStatements("G", "TABLE").iterator();

      while(var5.hasNext()) {
         dropStatement = (String)var5.next();
         this.jdbcTemplate.execute(dropStatement);
      }

      Table[] var6 = this.allTables();
      int var8 = var6.length;

      int var9;
      for(var9 = 0; var9 < var8; ++var9) {
         Table table = var6[var9];
         table.drop();
      }

      var5 = this.generateDropStatementsForSequences().iterator();

      while(var5.hasNext()) {
         dropStatement = (String)var5.next();
         this.jdbcTemplate.execute(dropStatement);
      }

      var5 = this.generateDropStatementsForProcedures().iterator();

      while(var5.hasNext()) {
         dropStatement = (String)var5.next();
         this.jdbcTemplate.execute(dropStatement);
      }

      var5 = this.generateDropStatementsForTriggers().iterator();

      while(var5.hasNext()) {
         dropStatement = (String)var5.next();
         this.jdbcTemplate.execute(dropStatement);
      }

      Function[] var10 = this.allFunctions();
      var8 = var10.length;

      for(var9 = 0; var9 < var8; ++var9) {
         Function function = var10[var9];
         function.drop();
      }

      Type[] var11 = this.allTypes();
      var8 = var11.length;

      for(var9 = 0; var9 < var8; ++var9) {
         Type type = var11[var9];
         type.drop();
      }

   }

   private List<String> generateDropStatementsForProcedures() throws SQLException {
      String dropProcGenQuery = "select SPECIFICNAME from SYSCAT.PROCEDURES where PROCSCHEMA = '" + this.name + "'";
      return this.buildDropStatements("DROP SPECIFIC PROCEDURE", dropProcGenQuery);
   }

   private List<String> generateDropStatementsForTriggers() throws SQLException {
      String dropTrigGenQuery = "select TRIGNAME from SYSCAT.TRIGGERS where TRIGSCHEMA = '" + this.name + "'";
      return this.buildDropStatements("DROP TRIGGER", dropTrigGenQuery);
   }

   private List<String> generateDropStatementsForSequences() throws SQLException {
      String dropSeqGenQuery = "select SEQNAME from SYSCAT.SEQUENCES where SEQSCHEMA = '" + this.name + "' and SEQTYPE='S'";
      return this.buildDropStatements("DROP SEQUENCE", dropSeqGenQuery);
   }

   private List<String> generateDropStatementsForViews() throws SQLException {
      String dropSeqGenQuery = "select TABNAME from SYSCAT.TABLES where TABSCHEMA = '" + this.name + "' and TABNAME NOT LIKE '%_V' and TYPE='V'";
      return this.buildDropStatements("DROP VIEW", dropSeqGenQuery);
   }

   private List<String> generateDropStatements(String tableType, String objectType) throws SQLException {
      String dropTablesGenQuery = "select TABNAME from SYSCAT.TABLES where TYPE='" + tableType + "' and TABSCHEMA = '" + this.name + "'";
      return this.buildDropStatements("DROP " + objectType, dropTablesGenQuery);
   }

   private List<String> buildDropStatements(String dropPrefix, String query) throws SQLException {
      List<String> dropStatements = new ArrayList();
      List<String> dbObjects = this.jdbcTemplate.queryForStringList(query);
      Iterator var5 = dbObjects.iterator();

      while(var5.hasNext()) {
         String dbObject = (String)var5.next();
         dropStatements.add(dropPrefix + " " + ((DB2Database)this.database).quote(new String[]{this.name, dbObject}));
      }

      return dropStatements;
   }

   private List<String> generateDropVersioningStatement() throws SQLException {
      List<String> dropVersioningStatements = new ArrayList();
      Table[] versioningTables = this.findTables("select TABNAME from SYSCAT.TABLES where TEMPORALTYPE <> 'N' and TABSCHEMA = ?", this.name);
      Table[] var3 = versioningTables;
      int var4 = versioningTables.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Table table = var3[var5];
         dropVersioningStatements.add("ALTER TABLE " + table.toString() + " DROP VERSIONING");
      }

      return dropVersioningStatements;
   }

   private Table[] findTables(String sqlQuery, String... params) throws SQLException {
      List<String> tableNames = this.jdbcTemplate.queryForStringList(sqlQuery, params);
      Table[] tables = new Table[tableNames.size()];

      for(int i = 0; i < tableNames.size(); ++i) {
         tables[i] = new DB2Table(this.jdbcTemplate, this.database, this, (String)tableNames.get(i));
      }

      return tables;
   }

   protected Table[] doAllTables() throws SQLException {
      return this.findTables("select TABNAME from SYSCAT.TABLES where TYPE='T' and TABSCHEMA = ?", this.name);
   }

   protected Function[] doAllFunctions() throws SQLException {
      List<Map<String, String>> rows = this.jdbcTemplate.queryForList("select p.SPECIFICNAME, p.FUNCNAME, substr( xmlserialize( xmlagg( xmltext( concat( ', ', TYPENAME ) ) ) as varchar( 1024 ) ), 3 ) as PARAMS from SYSCAT.FUNCTIONS f inner join SYSCAT.FUNCPARMS p on f.SPECIFICNAME = p.SPECIFICNAME where f.ORIGIN = 'Q' and p.FUNCSCHEMA = ? and p.ROWTYPE = 'P' group by p.SPECIFICNAME, p.FUNCNAME order by p.SPECIFICNAME", this.name);
      List<Function> functions = new ArrayList();
      Iterator var3 = rows.iterator();

      while(var3.hasNext()) {
         Map<String, String> row = (Map)var3.next();
         functions.add(this.getFunction((String)row.get("FUNCNAME"), StringUtils.tokenizeToStringArray((String)row.get("PARAMS"), ",")));
      }

      return (Function[])functions.toArray(new Function[functions.size()]);
   }

   public Table getTable(String tableName) {
      return new DB2Table(this.jdbcTemplate, this.database, this, tableName);
   }

   protected Type getType(String typeName) {
      return new DB2Type(this.jdbcTemplate, this.database, this, typeName);
   }

   public Function getFunction(String functionName, String... args) {
      return new DB2Function(this.jdbcTemplate, this.database, this, functionName, args);
   }
}
