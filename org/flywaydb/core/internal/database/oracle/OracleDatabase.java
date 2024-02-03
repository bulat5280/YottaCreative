package org.flywaydb.core.internal.database.oracle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.SqlScript;
import org.flywaydb.core.internal.exception.FlywayDbUpgradeRequiredException;
import org.flywaydb.core.internal.exception.FlywayEnterpriseUpgradeRequiredException;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.scanner.Resource;

public class OracleDatabase extends Database {
   private static final String ORACLE_NET_TNS_ADMIN = "oracle.net.tns_admin";

   public OracleDatabase(FlywayConfiguration configuration, Connection connection) {
      super(configuration, connection, 12);
      String tnsAdminEnvVar = System.getenv("TNS_ADMIN");
      String tnsAdminSysProp = System.getProperty("oracle.net.tns_admin");
      if (StringUtils.hasLength(tnsAdminEnvVar) && tnsAdminSysProp == null) {
         System.setProperty("oracle.net.tns_admin", tnsAdminEnvVar);
      }

   }

   protected org.flywaydb.core.internal.database.Connection getConnection(Connection connection, int nullType) {
      return new OracleConnection(this.configuration, this, connection, nullType);
   }

   protected final void ensureSupported() {
      int majorVersion = this.getMajorVersion();
      if (majorVersion < 10) {
         throw new FlywayDbUpgradeRequiredException("Oracle", "" + majorVersion, "10");
      } else if (majorVersion != 10 && majorVersion != 11) {
         if (majorVersion > 12) {
            this.recommendFlywayUpgrade("Oracle", "" + majorVersion);
         }

      } else {
         throw new FlywayEnterpriseUpgradeRequiredException("Oracle", "Oracle", "" + majorVersion);
      }
   }

   protected SqlScript doCreateSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      return new OracleSqlScript(sqlScriptResource, sqlScriptSource, mixed);
   }

   public String getDbName() {
      return "oracle";
   }

   protected String doGetCurrentUser() throws SQLException {
      return this.mainConnection.getJdbcTemplate().queryForString("SELECT USER FROM DUAL");
   }

   public boolean supportsDdlTransactions() {
      return false;
   }

   public String getBooleanTrue() {
      return "1";
   }

   public String getBooleanFalse() {
      return "0";
   }

   public String doQuote(String identifier) {
      return "\"" + identifier + "\"";
   }

   public boolean catalogIsSchema() {
      return false;
   }

   boolean queryReturnsRows(String query, String... params) throws SQLException {
      return this.mainConnection.getJdbcTemplate().queryForBoolean("SELECT CASE WHEN EXISTS(" + query + ") THEN 1 ELSE 0 END FROM DUAL", params);
   }

   boolean isPrivOrRoleGranted(String name) throws SQLException {
      return this.queryReturnsRows("SELECT 1 FROM SESSION_PRIVS WHERE PRIVILEGE = ? UNION ALL SELECT 1 FROM SESSION_ROLES WHERE ROLE = ?", name, name);
   }

   private boolean isDataDictViewAccessible(String owner, String name) throws SQLException {
      return this.queryReturnsRows("SELECT * FROM ALL_TAB_PRIVS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? AND PRIVILEGE = 'SELECT'", owner, name);
   }

   boolean isDataDictViewAccessible(String name) throws SQLException {
      return this.isDataDictViewAccessible("SYS", name);
   }

   String dbaOrAll(String baseName) throws SQLException {
      return !this.isPrivOrRoleGranted("SELECT ANY DICTIONARY") && !this.isDataDictViewAccessible("DBA_" + baseName) ? "ALL_" + baseName : "DBA_" + baseName;
   }

   private Set<String> getAvailableOptions() throws SQLException {
      return new HashSet(this.mainConnection.getJdbcTemplate().queryForStringList("SELECT PARAMETER FROM V$OPTION WHERE VALUE = 'TRUE'"));
   }

   boolean isFlashbackDataArchiveAvailable() throws SQLException {
      return this.getAvailableOptions().contains("Flashback Data Archive");
   }

   boolean isXmlDbAvailable() throws SQLException {
      return this.isDataDictViewAccessible("ALL_XML_TABLES");
   }

   boolean isDataMiningAvailable() throws SQLException {
      return this.getAvailableOptions().contains("Data Mining");
   }

   boolean isLocatorAvailable() throws SQLException {
      return this.isDataDictViewAccessible("MDSYS", "ALL_SDO_GEOM_METADATA");
   }

   Set<String> getSystemSchemas() throws SQLException {
      Set<String> result = new HashSet(Arrays.asList("SYS", "SYSTEM", "SYSBACKUP", "SYSDG", "SYSKM", "SYSRAC", "SYS$UMF", "DBSNMP", "MGMT_VIEW", "SYSMAN", "OUTLN", "AUDSYS", "ORACLE_OCM", "APPQOSSYS", "OJVMSYS", "DVF", "DVSYS", "DBSFWUSER", "REMOTE_SCHEDULER_AGENT", "DIP", "APEX_PUBLIC_USER", "FLOWS_FILES", "ANONYMOUS", "XDB", "XS$NULL", "CTXSYS", "LBACSYS", "EXFSYS", "MDDATA", "MDSYS", "SPATIAL_CSW_ADMIN_USR", "SPATIAL_WFS_ADMIN_USR", "ORDDATA", "ORDPLUGINS", "ORDSYS", "SI_INFORMTN_SCHEMA", "WMSYS", "OLAPSYS", "OWBSYS", "OWBSYS_AUDIT", "GSMADMIN_INTERNAL", "GSMCATUSER", "GSMUSER", "GGSYS", "WK_TEST", "WKSYS", "WKPROXY", "ODM", "ODM_MTR", "DMSYS", "TSMSYS"));
      result.addAll(this.mainConnection.getJdbcTemplate().queryForStringList("SELECT USERNAME FROM ALL_USERS WHERE REGEXP_LIKE(USERNAME, '^(APEX|FLOWS)_\\d+$') OR ORACLE_MAINTAINED = 'Y'"));
      return result;
   }
}
