package org.jooq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

public interface ExecuteContext extends Scope {
   Connection connection();

   ExecuteType type();

   Query query();

   Query[] batchQueries();

   Routine<?> routine();

   String sql();

   void sql(String var1);

   String[] batchSQL();

   void connectionProvider(ConnectionProvider var1);

   PreparedStatement statement();

   void statement(PreparedStatement var1);

   ResultSet resultSet();

   void resultSet(ResultSet var1);

   Record record();

   void record(Record var1);

   int rows();

   void rows(int var1);

   int[] batchRows();

   Result<?> result();

   void result(Result<?> var1);

   RuntimeException exception();

   void exception(RuntimeException var1);

   SQLException sqlException();

   void sqlException(SQLException var1);

   SQLWarning sqlWarning();

   void sqlWarning(SQLWarning var1);
}
