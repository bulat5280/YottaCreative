package com.mysql.cj.api.mysqla.result;

import com.mysql.cj.api.MysqlConnection;

public interface ResultsetRowsOwner {
   void closeOwner(boolean var1);

   MysqlConnection getConnection();

   long getConnectionId();

   String getPointOfOrigin();

   int getOwnerFetchSize();

   String getCurrentCatalog();

   int getOwningStatementId();

   int getOwningStatementMaxRows();

   int getOwningStatementFetchSize();

   long getOwningStatementServerId();
}
