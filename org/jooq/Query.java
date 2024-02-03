package org.jooq;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import org.jooq.conf.ParamType;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataTypeException;

public interface Query extends QueryPart, Attachable, AutoCloseable {
   int execute() throws DataAccessException;

   CompletionStage<Integer> executeAsync();

   CompletionStage<Integer> executeAsync(Executor var1);

   boolean isExecutable();

   String getSQL();

   /** @deprecated */
   @Deprecated
   String getSQL(boolean var1);

   String getSQL(ParamType var1);

   List<Object> getBindValues();

   Map<String, Param<?>> getParams();

   Param<?> getParam(String var1);

   Query bind(String var1, Object var2) throws IllegalArgumentException, DataTypeException;

   Query bind(int var1, Object var2) throws IllegalArgumentException, DataTypeException;

   Query queryTimeout(int var1);

   Query keepStatement(boolean var1);

   void close() throws DataAccessException;

   void cancel() throws DataAccessException;
}
