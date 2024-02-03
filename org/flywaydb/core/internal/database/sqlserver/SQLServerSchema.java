package org.flywaydb.core.internal.database.sqlserver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.util.jdbc.RowMapper;

public class SQLServerSchema extends Schema<SQLServerDatabase> {
   private final String databaseName;

   SQLServerSchema(JdbcTemplate jdbcTemplate, SQLServerDatabase database, String databaseName, String name) {
      super(jdbcTemplate, database, name);
      this.databaseName = databaseName;
   }

   protected boolean doExists() throws SQLException {
      return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME=?", this.name) > 0;
   }

   protected boolean doEmpty() throws SQLException {
      boolean empty = this.queryDBObjects(SQLServerSchema.ObjectType.SCALAR_FUNCTION, SQLServerSchema.ObjectType.AGGREGATE, SQLServerSchema.ObjectType.CLR_SCALAR_FUNCTION, SQLServerSchema.ObjectType.CLR_TABLE_VALUED_FUNCTION, SQLServerSchema.ObjectType.TABLE_VALUED_FUNCTION, SQLServerSchema.ObjectType.STORED_PROCEDURE, SQLServerSchema.ObjectType.CLR_STORED_PROCEDURE, SQLServerSchema.ObjectType.USER_TABLE, SQLServerSchema.ObjectType.SYNONYM, SQLServerSchema.ObjectType.SEQUENCE_OBJECT, SQLServerSchema.ObjectType.FOREIGN_KEY, SQLServerSchema.ObjectType.VIEW).isEmpty();
      if (empty) {
         int objectCount = this.jdbcTemplate.queryForInt("SELECT count(*) FROM ( SELECT t.name FROM sys.types t INNER JOIN sys.schemas s ON t.schema_id = s.schema_id WHERE t.is_user_defined = 1 AND s.name = ? Union SELECT name FROM sys.assemblies WHERE is_user_defined=1) R", this.name);
         empty = objectCount == 0;
      }

      return empty;
   }

   protected void doCreate() throws SQLException {
      this.jdbcTemplate.execute("CREATE SCHEMA " + ((SQLServerDatabase)this.database).quote(new String[]{this.name}));
   }

   protected void doDrop() throws SQLException {
      this.clean();
      this.jdbcTemplate.execute("DROP SCHEMA " + ((SQLServerDatabase)this.database).quote(new String[]{this.name}));
   }

   protected void doClean() throws SQLException {
      List<SQLServerSchema.DBObject> tables = this.queryDBObjects(SQLServerSchema.ObjectType.USER_TABLE);
      Iterator var2 = this.cleanTriggers().iterator();

      String statement;
      while(var2.hasNext()) {
         statement = (String)var2.next();
         this.jdbcTemplate.execute(statement);
      }

      var2 = this.cleanForeignKeys(tables).iterator();

      while(var2.hasNext()) {
         statement = (String)var2.next();
         this.jdbcTemplate.execute(statement);
      }

      var2 = this.cleanDefaultConstraints(tables).iterator();

      while(var2.hasNext()) {
         statement = (String)var2.next();
         this.jdbcTemplate.execute(statement);
      }

      var2 = this.cleanObjects("PROCEDURE", SQLServerSchema.ObjectType.STORED_PROCEDURE, SQLServerSchema.ObjectType.CLR_STORED_PROCEDURE).iterator();

      while(var2.hasNext()) {
         statement = (String)var2.next();
         this.jdbcTemplate.execute(statement);
      }

      var2 = this.cleanObjects("VIEW", SQLServerSchema.ObjectType.VIEW).iterator();

      while(var2.hasNext()) {
         statement = (String)var2.next();
         this.jdbcTemplate.execute(statement);
      }

      Table[] var6 = this.allTables();
      int var7 = var6.length;

      for(int var4 = 0; var4 < var7; ++var4) {
         Table table = var6[var4];
         table.drop();
      }

      var2 = this.cleanObjects("FUNCTION", SQLServerSchema.ObjectType.SCALAR_FUNCTION, SQLServerSchema.ObjectType.CLR_SCALAR_FUNCTION, SQLServerSchema.ObjectType.CLR_TABLE_VALUED_FUNCTION, SQLServerSchema.ObjectType.TABLE_VALUED_FUNCTION, SQLServerSchema.ObjectType.INLINED_TABLE_FUNCTION).iterator();

      while(var2.hasNext()) {
         statement = (String)var2.next();
         this.jdbcTemplate.execute(statement);
      }

      var2 = this.cleanObjects("AGGREGATE", SQLServerSchema.ObjectType.AGGREGATE).iterator();

      while(var2.hasNext()) {
         statement = (String)var2.next();
         this.jdbcTemplate.execute(statement);
      }

      var2 = this.cleanTypes().iterator();

      while(var2.hasNext()) {
         statement = (String)var2.next();
         this.jdbcTemplate.execute(statement);
      }

      var2 = this.cleanAssemblies().iterator();

      while(var2.hasNext()) {
         statement = (String)var2.next();
         this.jdbcTemplate.execute(statement);
      }

      var2 = this.cleanObjects("SYNONYM", SQLServerSchema.ObjectType.SYNONYM).iterator();

      while(var2.hasNext()) {
         statement = (String)var2.next();
         this.jdbcTemplate.execute(statement);
      }

      var2 = this.cleanObjects("RULE", SQLServerSchema.ObjectType.RULE).iterator();

      while(var2.hasNext()) {
         statement = (String)var2.next();
         this.jdbcTemplate.execute(statement);
      }

      var2 = this.cleanObjects("DEFAULT", SQLServerSchema.ObjectType.DEFAULT_CONSTRAINT).iterator();

      while(var2.hasNext()) {
         statement = (String)var2.next();
         this.jdbcTemplate.execute(statement);
      }

      var2 = this.cleanObjects("SEQUENCE", SQLServerSchema.ObjectType.SEQUENCE_OBJECT).iterator();

      while(var2.hasNext()) {
         statement = (String)var2.next();
         this.jdbcTemplate.execute(statement);
      }

   }

   private List<SQLServerSchema.DBObject> queryDBObjects(SQLServerSchema.ObjectType... types) throws SQLException {
      return this.queryDBObjectsWithParent((SQLServerSchema.DBObject)null, types);
   }

   private List<SQLServerSchema.DBObject> queryDBObjectsWithParent(SQLServerSchema.DBObject parent, SQLServerSchema.ObjectType... types) throws SQLException {
      assert types != null && types.length > 0;

      StringBuilder query = new StringBuilder("SELECT obj.object_id, obj.name FROM sys.objects AS obj LEFT JOIN sys.extended_properties AS eps ON obj.object_id = eps.major_id AND eps.class = 1 AND eps.minor_id = 0 AND eps.name='microsoft_database_tools_support' WHERE SCHEMA_NAME(obj.schema_id) = '" + this.name + "'  AND eps.major_id IS NULL AND obj.is_ms_shipped = 0 AND obj.type IN (");
      boolean first = true;
      SQLServerSchema.ObjectType[] var5 = types;
      int var6 = types.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         SQLServerSchema.ObjectType type = var5[var7];
         if (!first) {
            query.append(", ");
         }

         query.append("'").append(type.code).append("'");
         first = false;
      }

      query.append(")");
      if (parent != null) {
         query.append(" AND obj.parent_object_id = ").append(parent.objectId);
      }

      query.append(" order by create_date desc");
      return this.jdbcTemplate.query(query.toString(), new RowMapper<SQLServerSchema.DBObject>() {
         public SQLServerSchema.DBObject mapRow(ResultSet rs) throws SQLException {
            return SQLServerSchema.this.new DBObject(rs.getLong("object_id"), rs.getString("name"));
         }
      });
   }

   private List<String> cleanForeignKeys(List<SQLServerSchema.DBObject> tables) throws SQLException {
      List<String> statements = new ArrayList();
      Iterator var3 = tables.iterator();

      while(var3.hasNext()) {
         SQLServerSchema.DBObject table = (SQLServerSchema.DBObject)var3.next();
         List<SQLServerSchema.DBObject> fks = this.queryDBObjectsWithParent(table, SQLServerSchema.ObjectType.FOREIGN_KEY, SQLServerSchema.ObjectType.CHECK_CONSTRAINT);
         Iterator var6 = fks.iterator();

         while(var6.hasNext()) {
            SQLServerSchema.DBObject fk = (SQLServerSchema.DBObject)var6.next();
            statements.add("ALTER TABLE " + ((SQLServerDatabase)this.database).quote(new String[]{this.name, table.name}) + " DROP CONSTRAINT " + ((SQLServerDatabase)this.database).quote(new String[]{fk.name}));
         }
      }

      return statements;
   }

   private List<String> cleanDefaultConstraints(List<SQLServerSchema.DBObject> tables) throws SQLException {
      List<String> statements = new ArrayList();
      Iterator var3 = tables.iterator();

      while(var3.hasNext()) {
         SQLServerSchema.DBObject table = (SQLServerSchema.DBObject)var3.next();
         List<SQLServerSchema.DBObject> dfs = this.queryDBObjectsWithParent(table, SQLServerSchema.ObjectType.DEFAULT_CONSTRAINT);
         Iterator var6 = dfs.iterator();

         while(var6.hasNext()) {
            SQLServerSchema.DBObject df = (SQLServerSchema.DBObject)var6.next();
            statements.add("ALTER TABLE " + ((SQLServerDatabase)this.database).quote(new String[]{this.name, table.name}) + " DROP CONSTRAINT " + ((SQLServerDatabase)this.database).quote(new String[]{df.name}));
         }
      }

      return statements;
   }

   private List<String> cleanTypes() throws SQLException {
      List<String> typeNames = this.jdbcTemplate.queryForStringList("SELECT t.name FROM sys.types t INNER JOIN sys.schemas s ON t.schema_id = s.schema_id WHERE t.is_user_defined = 1 AND s.name = ?", this.name);
      List<String> statements = new ArrayList();
      Iterator var3 = typeNames.iterator();

      while(var3.hasNext()) {
         String typeName = (String)var3.next();
         statements.add("DROP TYPE " + ((SQLServerDatabase)this.database).quote(new String[]{this.name, typeName}));
      }

      return statements;
   }

   private List<String> cleanAssemblies() throws SQLException {
      List<String> assemblyNames = this.jdbcTemplate.queryForStringList("SELECT * FROM sys.assemblies WHERE is_user_defined=1");
      List<String> statements = new ArrayList();
      Iterator var3 = assemblyNames.iterator();

      while(var3.hasNext()) {
         String assemblyName = (String)var3.next();
         statements.add("DROP ASSEMBLY " + ((SQLServerDatabase)this.database).quote(new String[]{assemblyName}));
      }

      return statements;
   }

   private List<String> cleanTriggers() throws SQLException {
      List<String> triggerNames = this.jdbcTemplate.queryForStringList("SELECT * FROM sys.triggers WHERE is_ms_shipped=0 AND parent_id=0 AND parent_class_desc='DATABASE'");
      List<String> statements = new ArrayList();
      Iterator var3 = triggerNames.iterator();

      while(var3.hasNext()) {
         String triggerName = (String)var3.next();
         statements.add("DROP TRIGGER " + ((SQLServerDatabase)this.database).quote(new String[]{triggerName}) + " ON DATABASE");
      }

      return statements;
   }

   private List<String> cleanObjects(String dropQualifier, SQLServerSchema.ObjectType... objectTypes) throws SQLException {
      List<String> statements = new ArrayList();
      List<SQLServerSchema.DBObject> dbObjects = this.queryDBObjects(objectTypes);
      Iterator var5 = dbObjects.iterator();

      while(var5.hasNext()) {
         SQLServerSchema.DBObject dbObject = (SQLServerSchema.DBObject)var5.next();
         statements.add("DROP " + dropQualifier + " " + ((SQLServerDatabase)this.database).quote(new String[]{this.name, dbObject.name}));
      }

      return statements;
   }

   protected Table[] doAllTables() throws SQLException {
      List<String> tableNames = new ArrayList();
      Iterator var2 = this.queryDBObjects(SQLServerSchema.ObjectType.USER_TABLE).iterator();

      while(var2.hasNext()) {
         SQLServerSchema.DBObject table = (SQLServerSchema.DBObject)var2.next();
         tableNames.add(table.name);
      }

      Table[] tables = new Table[tableNames.size()];

      for(int i = 0; i < tableNames.size(); ++i) {
         tables[i] = new SQLServerTable(this.jdbcTemplate, this.database, this.databaseName, this, (String)tableNames.get(i));
      }

      return tables;
   }

   public Table getTable(String tableName) {
      return new SQLServerTable(this.jdbcTemplate, this.database, this.databaseName, this, tableName);
   }

   private class DBObject {
      final String name;
      final long objectId;

      DBObject(long objectId, String name) {
         assert name != null;

         this.objectId = objectId;
         this.name = name;
      }
   }

   private static enum ObjectType {
      AGGREGATE("AF"),
      CHECK_CONSTRAINT("C"),
      DEFAULT_CONSTRAINT("D"),
      FOREIGN_KEY("F"),
      INLINED_TABLE_FUNCTION("IF"),
      SCALAR_FUNCTION("FN"),
      CLR_SCALAR_FUNCTION("FS"),
      CLR_TABLE_VALUED_FUNCTION("FT"),
      STORED_PROCEDURE("P"),
      CLR_STORED_PROCEDURE("PC"),
      RULE("R"),
      SYNONYM("SN"),
      TABLE_VALUED_FUNCTION("TF"),
      ASSEMBLY_DML_TRIGGER("TA"),
      SQL_DML_TRIGGER("TR"),
      USER_TABLE("U"),
      VIEW("V"),
      SEQUENCE_OBJECT("SO");

      final String code;

      private ObjectType(String code) {
         assert code != null;

         this.code = code;
      }
   }
}
