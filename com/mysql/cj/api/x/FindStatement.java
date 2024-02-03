package com.mysql.cj.api.x;

import com.mysql.cj.x.json.DbDoc;

public interface FindStatement extends DataStatement<FindStatement, DocResult, DbDoc> {
   FindStatement fields(String... var1);

   FindStatement fields(Expression var1);

   FindStatement groupBy(String... var1);

   FindStatement having(String var1);

   FindStatement orderBy(String... var1);

   FindStatement sort(String... var1);

   FindStatement skip(long var1);

   FindStatement limit(long var1);
}
