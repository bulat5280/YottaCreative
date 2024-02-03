package com.mysql.cj.api.x;

public interface SelectStatement extends DataStatement<SelectStatement, RowResult, Row> {
   SelectStatement where(String var1);

   SelectStatement groupBy(String... var1);

   SelectStatement having(String var1);

   SelectStatement orderBy(String... var1);

   SelectStatement limit(long var1);

   SelectStatement offset(long var1);
}
