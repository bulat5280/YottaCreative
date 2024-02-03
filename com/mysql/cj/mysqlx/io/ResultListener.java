package com.mysql.cj.mysqlx.io;

import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.mysqlx.MysqlxError;
import com.mysql.cj.mysqlx.result.MysqlxRow;
import java.util.ArrayList;

public interface ResultListener {
   void onMetadata(ArrayList<Field> var1);

   void onRow(MysqlxRow var1);

   void onComplete(StatementExecuteOk var1);

   void onError(MysqlxError var1);

   void onException(Throwable var1);
}
