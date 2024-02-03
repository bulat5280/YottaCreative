package com.mysql.cj.api.x;

import com.mysql.cj.x.json.DbDoc;

public interface AddStatement extends Statement<AddStatement, Result> {
   AddStatement add(String var1);

   AddStatement add(DbDoc... var1);
}
