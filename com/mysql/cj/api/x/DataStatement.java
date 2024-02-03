package com.mysql.cj.api.x;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public interface DataStatement<STMT_T, RES_T, RES_ELEMENT_T> extends Statement<STMT_T, RES_T> {
   <R> CompletableFuture<R> executeAsync(R var1, DataStatement.Reducer<RES_ELEMENT_T, R> var2);

   public interface Reducer<RES_ELEMENT_T, R> extends BiFunction<R, RES_ELEMENT_T, R> {
   }
}
