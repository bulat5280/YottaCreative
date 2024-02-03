package com.mysql.cj.api.x;

import com.mysql.cj.mysqlx.MysqlxSession;
import java.util.List;

public interface BaseSession {
   List<Schema> getSchemas();

   Schema getSchema(String var1);

   String getDefaultSchemaName();

   Schema getDefaultSchema();

   Schema createSchema(String var1);

   Schema createSchema(String var1, boolean var2);

   void dropSchema(String var1);

   void dropCollection(String var1, String var2);

   void dropTable(String var1, String var2);

   String getUri();

   boolean isOpen();

   void close();

   void startTransaction();

   void commit();

   void rollback();

   MysqlxSession getMysqlxSession();
}
