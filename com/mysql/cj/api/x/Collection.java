package com.mysql.cj.api.x;

import com.mysql.cj.x.json.DbDoc;
import java.util.Map;

public interface Collection extends DatabaseObject {
   AddStatement add(Map<String, ?> var1);

   AddStatement add(String... var1);

   AddStatement add(DbDoc var1);

   AddStatement add(DbDoc... var1);

   FindStatement find();

   FindStatement find(String var1);

   ModifyStatement modify();

   ModifyStatement modify(String var1);

   RemoveStatement remove();

   RemoveStatement remove(String var1);

   CreateCollectionIndexStatement createIndex(String var1, boolean var2);

   DropCollectionIndexStatement dropIndex(String var1);

   long count();

   DbDoc newDoc();
}
