package org.flywaydb.core.internal.database;

import java.io.Closeable;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.Pair;
import org.flywaydb.core.internal.util.PlaceholderReplacer;
import org.flywaydb.core.internal.util.jdbc.JdbcUtils;
import org.flywaydb.core.internal.util.scanner.Resource;
import org.flywaydb.core.internal.util.scanner.classpath.ClassPathResource;

public abstract class Database<C extends Connection> implements Closeable {
   private static final Log LOG = LogFactory.getLog(Database.class);
   protected final FlywayConfiguration configuration;
   protected final DatabaseMetaData jdbcMetaData;
   protected final C mainConnection;
   private C migrationConnection;
   private final int nullType;
   protected final int majorVersion;
   protected final int minorVersion;

   public Database(FlywayConfiguration configuration, java.sql.Connection connection, int nullType) {
      this.configuration = configuration;

      try {
         this.jdbcMetaData = connection.getMetaData();
      } catch (SQLException var5) {
         throw new FlywaySqlException("Unable to get metadata for connection", var5);
      }

      this.mainConnection = this.getConnection(connection, nullType);
      this.nullType = nullType;
      Pair<Integer, Integer> majorMinor = this.determineMajorAndMinorVersion();
      this.majorVersion = (Integer)majorMinor.getLeft();
      this.minorVersion = (Integer)majorMinor.getRight();
      this.ensureSupported();
   }

   protected abstract C getConnection(java.sql.Connection var1, int var2);

   protected abstract void ensureSupported();

   protected final void recommendFlywayUpgrade(String database, String version) {
      LOG.warn("Flyway upgrade recommended: " + database + " " + version + " is newer than this version of Flyway and support has not been tested.");
   }

   public final SqlScript createSqlScript(Resource resource, String sqlScriptSource, boolean mixed) {
      return this.doCreateSqlScript(resource, sqlScriptSource, mixed);
   }

   protected abstract SqlScript doCreateSqlScript(Resource var1, String var2, boolean var3);

   public Delimiter getDefaultDelimiter() {
      return Delimiter.SEMICOLON;
   }

   public abstract String getDbName();

   public final String getCurrentUser() {
      try {
         return this.doGetCurrentUser();
      } catch (SQLException var2) {
         throw new FlywaySqlException("Error retrieving the database user", var2);
      }
   }

   protected String doGetCurrentUser() throws SQLException {
      return this.jdbcMetaData.getUserName();
   }

   public abstract boolean supportsDdlTransactions();

   public abstract String getBooleanTrue();

   public abstract String getBooleanFalse();

   public final String quote(String... identifiers) {
      StringBuilder result = new StringBuilder();
      boolean first = true;
      String[] var4 = identifiers;
      int var5 = identifiers.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String identifier = var4[var6];
         if (!first) {
            result.append(".");
         }

         first = false;
         result.append(this.doQuote(identifier));
      }

      return result.toString();
   }

   protected abstract String doQuote(String var1);

   public abstract boolean catalogIsSchema();

   public boolean useSingleConnection() {
      return false;
   }

   public DatabaseMetaData getJdbcMetaData() {
      return this.jdbcMetaData;
   }

   public final C getMainConnection() {
      return this.mainConnection;
   }

   public final C getMigrationConnection() {
      if (this.migrationConnection == null) {
         this.migrationConnection = this.useSingleConnection() ? this.mainConnection : this.getConnection(JdbcUtils.openConnection(this.configuration.getDataSource()), this.nullType);
      }

      return this.migrationConnection;
   }

   public int getMajorVersion() {
      return this.majorVersion;
   }

   public int getMinorVersion() {
      return this.minorVersion;
   }

   protected Pair<Integer, Integer> determineMajorAndMinorVersion() {
      try {
         return Pair.of(this.jdbcMetaData.getDatabaseMajorVersion(), this.jdbcMetaData.getDatabaseMinorVersion());
      } catch (SQLException var2) {
         throw new FlywaySqlException("Unable to determine the major version of the database", var2);
      }
   }

   public final String getCreateScript(Table table) {
      String source = this.getRawCreateScript();
      Map<String, String> placeholders = new HashMap();
      placeholders.put("schema", table.getSchema().getName());
      placeholders.put("table", table.getName());
      placeholders.put("table_quoted", table.toString());
      return (new PlaceholderReplacer(placeholders, "${", "}")).replacePlaceholders(source);
   }

   protected String getRawCreateScript() {
      String resourceName = "org/flywaydb/core/internal/database/" + this.getDbName() + "/createMetaDataTable.sql";
      return (new ClassPathResource(resourceName, this.getClass().getClassLoader())).loadAsString("UTF-8");
   }

   public String getInsertStatement(Table table) {
      return "INSERT INTO " + table + " (" + this.quote("installed_rank") + "," + this.quote("version") + "," + this.quote("description") + "," + this.quote("type") + "," + this.quote("script") + "," + this.quote("checksum") + "," + this.quote("installed_by") + "," + this.quote("execution_time") + "," + this.quote("success") + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
   }

   public void close() {
      if (!this.useSingleConnection() && this.migrationConnection != null) {
         this.migrationConnection.close();
      }

      this.mainConnection.close();
   }
}
