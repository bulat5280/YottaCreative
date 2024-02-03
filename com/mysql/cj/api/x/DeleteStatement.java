package com.mysql.cj.api.x;

public interface DeleteStatement extends Statement<DeleteStatement, Result> {
   DeleteStatement where(String var1);

   DeleteStatement orderBy(String... var1);

   DeleteStatement limit(long var1);
}
