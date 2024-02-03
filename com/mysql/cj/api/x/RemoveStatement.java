package com.mysql.cj.api.x;

public interface RemoveStatement extends Statement<RemoveStatement, Result> {
   RemoveStatement orderBy(String... var1);

   RemoveStatement limit(long var1);
}
