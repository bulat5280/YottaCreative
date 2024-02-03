package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.io.ServerCapabilities;
import com.mysql.cj.api.io.ServerSession;
import com.mysql.cj.core.CharsetMapping;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.ServerVersion;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.util.StringUtils;
import java.util.HashMap;
import java.util.Map;

public class MysqlaServerSession implements ServerSession {
   public static final int SERVER_STATUS_IN_TRANS = 1;
   public static final int SERVER_STATUS_AUTOCOMMIT = 2;
   public static final int SERVER_MORE_RESULTS_EXISTS = 8;
   public static final int SERVER_QUERY_NO_GOOD_INDEX_USED = 16;
   public static final int SERVER_QUERY_NO_INDEX_USED = 32;
   public static final int SERVER_STATUS_CURSOR_EXISTS = 64;
   public static final int SERVER_STATUS_LAST_ROW_SENT = 128;
   public static final int SERVER_QUERY_WAS_SLOW = 2048;
   public static final int CLIENT_LONG_PASSWORD = 1;
   public static final int CLIENT_FOUND_ROWS = 2;
   public static final int CLIENT_LONG_FLAG = 4;
   public static final int CLIENT_CONNECT_WITH_DB = 8;
   public static final int CLIENT_COMPRESS = 32;
   public static final int CLIENT_LOCAL_FILES = 128;
   public static final int CLIENT_PROTOCOL_41 = 512;
   public static final int CLIENT_INTERACTIVE = 1024;
   public static final int CLIENT_SSL = 2048;
   public static final int CLIENT_TRANSACTIONS = 8192;
   public static final int CLIENT_RESERVED = 16384;
   public static final int CLIENT_SECURE_CONNECTION = 32768;
   public static final int CLIENT_MULTI_STATEMENTS = 65536;
   public static final int CLIENT_MULTI_RESULTS = 131072;
   public static final int CLIENT_PS_MULTI_RESULTS = 262144;
   public static final int CLIENT_PLUGIN_AUTH = 524288;
   public static final int CLIENT_CONNECT_ATTRS = 1048576;
   public static final int CLIENT_PLUGIN_AUTH_LENENC_CLIENT_DATA = 2097152;
   public static final int CLIENT_CAN_HANDLE_EXPIRED_PASSWORD = 4194304;
   public static final int CLIENT_SESSION_TRACK = 8388608;
   public static final int CLIENT_DEPRECATE_EOF = 16777216;
   private PropertySet propertySet;
   private MysqlaCapabilities capabilities;
   private int oldStatusFlags = 0;
   private int statusFlags = 0;
   private int serverDefaultCollationIndex;
   private long clientParam = 0L;
   private boolean hasLongColumnInfo = false;
   private Map<String, String> serverVariables = new HashMap();
   public Map<Integer, String> indexToMysqlCharset = new HashMap();
   public Map<Integer, String> indexToCustomMysqlCharset = null;
   public Map<String, Integer> mysqlCharsetToCustomMblen = null;
   private String characterSetMetadata = null;
   private int metadataCollationIndex;
   private String characterSetResultsOnServer = null;
   private String errorMessageEncoding = "Cp1252";

   public MysqlaServerSession(PropertySet propertySet) {
      this.propertySet = propertySet;
   }

   public MysqlaCapabilities getCapabilities() {
      return this.capabilities;
   }

   public void setCapabilities(ServerCapabilities capabilities) {
      this.capabilities = (MysqlaCapabilities)capabilities;
   }

   public int getStatusFlags() {
      return this.statusFlags;
   }

   public void setStatusFlags(int statusFlags) {
      this.setStatusFlags(statusFlags, false);
   }

   public void setStatusFlags(int statusFlags, boolean saveOldStatus) {
      if (saveOldStatus) {
         this.oldStatusFlags = this.statusFlags;
      }

      this.statusFlags = statusFlags;
   }

   public int getOldStatusFlags() {
      return this.oldStatusFlags;
   }

   public void setOldStatusFlags(int oldStatusFlags) {
      this.oldStatusFlags = oldStatusFlags;
   }

   public int getTransactionState() {
      if ((this.oldStatusFlags & 1) == 0) {
         return (this.statusFlags & 1) == 0 ? 0 : 2;
      } else {
         return (this.statusFlags & 1) == 0 ? 3 : 1;
      }
   }

   public boolean inTransactionOnServer() {
      return (this.statusFlags & 1) != 0;
   }

   public boolean cursorExists() {
      return (this.statusFlags & 64) != 0;
   }

   public boolean isAutocommit() {
      return (this.statusFlags & 2) != 0;
   }

   public boolean hasMoreResults() {
      return (this.statusFlags & 8) != 0;
   }

   public boolean noGoodIndexUsed() {
      return (this.statusFlags & 16) != 0;
   }

   public boolean noIndexUsed() {
      return (this.statusFlags & 32) != 0;
   }

   public boolean queryWasSlow() {
      return (this.statusFlags & 2048) != 0;
   }

   public boolean isLastRowSent() {
      return (this.statusFlags & 128) != 0;
   }

   public long getClientParam() {
      return this.clientParam;
   }

   public void setClientParam(long clientParam) {
      this.clientParam = clientParam;
   }

   public boolean useMultiResults() {
      return (this.clientParam & 131072L) != 0L || (this.clientParam & 262144L) != 0L;
   }

   public boolean isEOFDeprecated() {
      return (this.clientParam & 16777216L) != 0L;
   }

   public int getServerDefaultCollationIndex() {
      return this.serverDefaultCollationIndex;
   }

   public void setServerDefaultCollationIndex(int serverDefaultCollationIndex) {
      this.serverDefaultCollationIndex = serverDefaultCollationIndex;
   }

   public boolean hasLongColumnInfo() {
      return this.hasLongColumnInfo;
   }

   public void setHasLongColumnInfo(boolean hasLongColumnInfo) {
      this.hasLongColumnInfo = hasLongColumnInfo;
   }

   public Map<String, String> getServerVariables() {
      return this.serverVariables;
   }

   public String getServerVariable(String name) {
      return (String)this.serverVariables.get(name);
   }

   public int getServerVariable(String variableName, int fallbackValue) {
      try {
         return Integer.valueOf(this.getServerVariable(variableName));
      } catch (NumberFormatException var4) {
         return fallbackValue;
      }
   }

   public void setServerVariables(Map<String, String> serverVariables) {
      this.serverVariables = serverVariables;
   }

   public boolean characterSetNamesMatches(String mysqlEncodingName) {
      return mysqlEncodingName != null && mysqlEncodingName.equalsIgnoreCase(this.getServerVariable("character_set_client")) && mysqlEncodingName.equalsIgnoreCase(this.getServerVariable("character_set_connection"));
   }

   public final ServerVersion getServerVersion() {
      return this.capabilities.getServerVersion();
   }

   public boolean isVersion(ServerVersion version) {
      return this.getServerVersion().equals(version);
   }

   public boolean isSetNeededForAutoCommitMode(boolean autoCommitFlag, boolean elideSetAutoCommitsFlag) {
      if (elideSetAutoCommitsFlag) {
         boolean autoCommitModeOnServer = this.isAutocommit();
         if (!autoCommitFlag) {
            return !this.inTransactionOnServer();
         } else {
            return autoCommitModeOnServer != autoCommitFlag;
         }
      } else {
         return true;
      }
   }

   public String getErrorMessageEncoding() {
      return this.errorMessageEncoding;
   }

   public void setErrorMessageEncoding(String errorMessageEncoding) {
      this.errorMessageEncoding = errorMessageEncoding;
   }

   public String getServerDefaultCharset() {
      String charset = null;
      if (this.indexToCustomMysqlCharset != null) {
         charset = (String)this.indexToCustomMysqlCharset.get(this.getServerDefaultCollationIndex());
      }

      if (charset == null) {
         charset = CharsetMapping.getMysqlCharsetNameForCollationIndex(this.getServerDefaultCollationIndex());
      }

      return charset != null ? charset : this.getServerVariable("character_set_server");
   }

   public int getMaxBytesPerChar(String javaCharsetName) {
      return this.getMaxBytesPerChar((Integer)null, javaCharsetName);
   }

   public int getMaxBytesPerChar(Integer charsetIndex, String javaCharsetName) {
      String charset = null;
      int res = 1;
      if (this.indexToCustomMysqlCharset != null) {
         charset = (String)this.indexToCustomMysqlCharset.get(charsetIndex);
      }

      if (charset == null) {
         charset = CharsetMapping.getMysqlCharsetNameForCollationIndex(charsetIndex);
      }

      if (charset == null) {
         charset = CharsetMapping.getMysqlCharsetForJavaEncoding(javaCharsetName, this.getServerVersion());
      }

      Integer mblen = null;
      if (this.mysqlCharsetToCustomMblen != null) {
         mblen = (Integer)this.mysqlCharsetToCustomMblen.get(charset);
      }

      if (mblen == null) {
         mblen = CharsetMapping.getMblen(charset);
      }

      if (mblen != null) {
         res = mblen;
      }

      return res;
   }

   public String getEncodingForIndex(int charsetIndex) {
      String javaEncoding = null;
      String characterEncoding = (String)this.propertySet.getReadableProperty("characterEncoding").getValue();
      if ((Boolean)this.propertySet.getBooleanReadableProperty("useOldUTF8Behavior").getValue()) {
         return characterEncoding;
      } else {
         if (charsetIndex != -1) {
            try {
               if (this.indexToMysqlCharset.size() > 0) {
                  javaEncoding = CharsetMapping.getJavaEncodingForMysqlCharset((String)this.indexToMysqlCharset.get(charsetIndex), characterEncoding);
               }

               if (javaEncoding == null) {
                  javaEncoding = CharsetMapping.getJavaEncodingForCollationIndex(charsetIndex, characterEncoding);
               }
            } catch (ArrayIndexOutOfBoundsException var5) {
               throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Connection.11", new Object[]{charsetIndex}));
            }

            if (javaEncoding == null) {
               javaEncoding = characterEncoding;
            }
         } else {
            javaEncoding = characterEncoding;
         }

         return javaEncoding;
      }
   }

   public void configureCharacterSets() {
      String characterSetResultsOnServerMysql = this.getServerVariable("jdbc.local.character_set_results");
      if (characterSetResultsOnServerMysql != null && !StringUtils.startsWithIgnoreCaseAndWs(characterSetResultsOnServerMysql, "NULL") && characterSetResultsOnServerMysql.length() != 0) {
         this.characterSetResultsOnServer = CharsetMapping.getJavaEncodingForMysqlCharset(characterSetResultsOnServerMysql);
         this.characterSetMetadata = this.characterSetResultsOnServer;
         this.setErrorMessageEncoding(this.characterSetResultsOnServer);
      } else {
         String defaultMetadataCharsetMysql = this.getServerVariable("character_set_system");
         String defaultMetadataCharset = null;
         if (defaultMetadataCharsetMysql != null) {
            defaultMetadataCharset = CharsetMapping.getJavaEncodingForMysqlCharset(defaultMetadataCharsetMysql);
         } else {
            defaultMetadataCharset = "UTF-8";
         }

         this.characterSetMetadata = defaultMetadataCharset;
         this.setErrorMessageEncoding("UTF-8");
      }

      this.metadataCollationIndex = CharsetMapping.getCollationIndexForJavaEncoding(this.characterSetMetadata, this.getServerVersion());
   }

   public String getCharacterSetMetadata() {
      return this.characterSetMetadata;
   }

   public void setCharacterSetMetadata(String characterSetMetadata) {
      this.characterSetMetadata = characterSetMetadata;
   }

   public int getMetadataCollationIndex() {
      return this.metadataCollationIndex;
   }

   public void setMetadataCollationIndex(int metadataCollationIndex) {
      this.metadataCollationIndex = metadataCollationIndex;
   }

   public String getCharacterSetResultsOnServer() {
      return this.characterSetResultsOnServer;
   }

   public void setCharacterSetResultsOnServer(String characterSetResultsOnServer) {
      this.characterSetResultsOnServer = characterSetResultsOnServer;
   }

   public void preserveOldTransactionState() {
      this.statusFlags |= this.oldStatusFlags & 1;
   }
}
