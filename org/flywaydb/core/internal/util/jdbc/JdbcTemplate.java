package org.flywaydb.core.internal.util.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;

public class JdbcTemplate {
   private static final Log LOG = LogFactory.getLog(JdbcTemplate.class);
   private final Connection connection;
   private final int nullType;

   public JdbcTemplate(Connection connection) throws SQLException {
      this(connection, 0);
   }

   public JdbcTemplate(Connection connection, int nullType) {
      this.connection = connection;
      this.nullType = nullType;
   }

   public Connection getConnection() {
      return this.connection;
   }

   public List<Map<String, String>> queryForList(String query, String... params) throws SQLException {
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      ArrayList result;
      try {
         statement = this.connection.prepareStatement(query);

         for(int i = 0; i < params.length; ++i) {
            statement.setString(i + 1, params[i]);
         }

         resultSet = statement.executeQuery();
         result = new ArrayList();

         while(resultSet.next()) {
            Map<String, String> rowMap = new LinkedHashMap();

            for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); ++i) {
               rowMap.put(resultSet.getMetaData().getColumnLabel(i), resultSet.getString(i));
            }

            result.add(rowMap);
         }
      } finally {
         JdbcUtils.closeResultSet(resultSet);
         JdbcUtils.closeStatement(statement);
      }

      return result;
   }

   public List<String> queryForStringList(String query, String... params) throws SQLException {
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      ArrayList result;
      try {
         statement = this.connection.prepareStatement(query);

         for(int i = 0; i < params.length; ++i) {
            statement.setString(i + 1, params[i]);
         }

         resultSet = statement.executeQuery();
         result = new ArrayList();

         while(resultSet.next()) {
            result.add(resultSet.getString(1));
         }
      } finally {
         JdbcUtils.closeResultSet(resultSet);
         JdbcUtils.closeStatement(statement);
      }

      return result;
   }

   public int queryForInt(String query, String... params) throws SQLException {
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      try {
         statement = this.connection.prepareStatement(query);

         for(int i = 0; i < params.length; ++i) {
            statement.setString(i + 1, params[i]);
         }

         resultSet = statement.executeQuery();
         resultSet.next();
         int result = resultSet.getInt(1);
         return result;
      } finally {
         JdbcUtils.closeResultSet(resultSet);
         JdbcUtils.closeStatement(statement);
      }
   }

   public boolean queryForBoolean(String query, String... params) throws SQLException {
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      try {
         statement = this.connection.prepareStatement(query);

         for(int i = 0; i < params.length; ++i) {
            statement.setString(i + 1, params[i]);
         }

         resultSet = statement.executeQuery();
         resultSet.next();
         boolean result = resultSet.getBoolean(1);
         return result;
      } finally {
         JdbcUtils.closeResultSet(resultSet);
         JdbcUtils.closeStatement(statement);
      }
   }

   public String queryForString(String query, String... params) throws SQLException {
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      String result;
      try {
         statement = this.connection.prepareStatement(query);
         int i = 0;

         while(true) {
            if (i >= params.length) {
               resultSet = statement.executeQuery();
               result = null;
               if (resultSet.next()) {
                  result = resultSet.getString(1);
               }
               break;
            }

            statement.setString(i + 1, params[i]);
            ++i;
         }
      } finally {
         JdbcUtils.closeResultSet(resultSet);
         JdbcUtils.closeStatement(statement);
      }

      return result;
   }

   public void execute(String sql, Object... params) throws SQLException {
      PreparedStatement statement = null;

      try {
         statement = this.prepareStatement(sql, params);
         statement.execute();
      } finally {
         JdbcUtils.closeStatement(statement);
      }

   }

   public List<Result> executeStatement(ContextImpl errorContext, String sql) throws SQLException {
      Statement statement = null;

      try {
         statement = this.connection.createStatement();
         statement.setEscapeProcessing(false);
         boolean var13 = false;

         boolean hasResults;
         try {
            var13 = true;
            hasResults = statement.execute(sql);
            var13 = false;
         } finally {
            if (var13) {
               for(SQLWarning warning = statement.getWarnings(); warning != null; warning = warning.getNextWarning()) {
                  errorContext.addWarning(new WarningImpl(warning.getErrorCode(), warning.getSQLState(), warning.getMessage()));
               }

            }
         }

         for(SQLWarning warning = statement.getWarnings(); warning != null; warning = warning.getNextWarning()) {
            errorContext.addWarning(new WarningImpl(warning.getErrorCode(), warning.getSQLState(), warning.getMessage()));
         }

         List var16 = this.extractResults(statement, hasResults);
         return var16;
      } finally {
         JdbcUtils.closeStatement(statement);
      }
   }

   private List<Result> extractResults(Statement statement, boolean hasResults) throws SQLException {
      List<Result> results = new ArrayList();

      for(int updateCount = -1; hasResults || (updateCount = statement.getUpdateCount()) != -1; hasResults = statement.getMoreResults()) {
         results.add(new Result((long)updateCount));
      }

      return results;
   }

   public void update(String sql, Object... params) throws SQLException {
      PreparedStatement statement = null;

      try {
         statement = this.prepareStatement(sql, params);
         statement.executeUpdate();
      } finally {
         JdbcUtils.closeStatement(statement);
      }

   }

   private PreparedStatement prepareStatement(String sql, Object[] params) throws SQLException {
      PreparedStatement statement = this.connection.prepareStatement(sql);

      for(int i = 0; i < params.length; ++i) {
         if (params[i] == null) {
            statement.setNull(i + 1, this.nullType);
         } else if (params[i] instanceof Integer) {
            statement.setInt(i + 1, (Integer)params[i]);
         } else if (params[i] instanceof Boolean) {
            statement.setBoolean(i + 1, (Boolean)params[i]);
         } else {
            statement.setString(i + 1, params[i].toString());
         }
      }

      return statement;
   }

   public <T> List<T> query(String query, RowMapper<T> rowMapper) throws SQLException {
      Statement statement = null;
      ResultSet resultSet = null;

      ArrayList results;
      try {
         statement = this.connection.createStatement();
         resultSet = statement.executeQuery(query);
         results = new ArrayList();

         while(resultSet.next()) {
            results.add(rowMapper.mapRow(resultSet));
         }
      } finally {
         JdbcUtils.closeResultSet(resultSet);
         JdbcUtils.closeStatement(statement);
      }

      return results;
   }
}
