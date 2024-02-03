package com.mysql.cj.api.x;

public interface DatabaseObject {
   BaseSession getSession();

   Schema getSchema();

   String getName();

   DatabaseObject.DbObjectStatus existsInDatabase();

   public static enum DbObjectStatus {
      EXISTS,
      NOT_EXISTS,
      UNKNOWN;
   }

   public static enum DbObjectType {
      COLLECTION,
      TABLE,
      VIEW;
   }
}
