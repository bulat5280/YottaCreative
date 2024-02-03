package com.mysql.cj.api.jdbc.result;

import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.api.mysqla.result.ResultsetRowsOwner;
import com.mysql.cj.jdbc.PreparedStatement;
import com.mysql.cj.jdbc.StatementImpl;
import com.mysql.cj.jdbc.io.ResultSetFactory;
import com.mysql.cj.jdbc.result.CachedResultSetMetaData;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public interface ResultSetInternalMethods extends ResultSet, ResultsetRowsOwner, Resultset {
   ResultSetInternalMethods copy(ResultSetFactory var1) throws SQLException;

   Object getObjectStoredProc(int var1, int var2) throws SQLException;

   Object getObjectStoredProc(int var1, Map<Object, Object> var2, int var3) throws SQLException;

   Object getObjectStoredProc(String var1, int var2) throws SQLException;

   Object getObjectStoredProc(String var1, Map<Object, Object> var2, int var3) throws SQLException;

   void realClose(boolean var1) throws SQLException;

   void setFirstCharOfQuery(char var1);

   void setOwningStatement(StatementImpl var1);

   char getFirstCharOfQuery();

   void setStatementUsedForFetchingRows(PreparedStatement var1);

   void setWrapperStatement(Statement var1);

   void initializeWithMetadata() throws SQLException;

   void populateCachedMetaData(CachedResultSetMetaData var1) throws SQLException;

   BigInteger getBigInteger(int var1) throws SQLException;
}
