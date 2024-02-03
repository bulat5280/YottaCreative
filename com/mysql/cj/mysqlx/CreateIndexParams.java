package com.mysql.cj.mysqlx;

import java.util.ArrayList;
import java.util.List;

public class CreateIndexParams {
   private String indexName;
   private boolean unique;
   private List<String> docPaths = new ArrayList();
   private List<String> types = new ArrayList();
   private List<Boolean> notNulls = new ArrayList();

   public CreateIndexParams(String indexName, boolean unique) {
      this.indexName = indexName;
      this.unique = unique;
   }

   public void addField(String docPath, String type, boolean notNull) {
      this.docPaths.add(docPath);
      this.types.add(type);
      this.notNulls.add(notNull);
   }

   public String getIndexName() {
      return this.indexName;
   }

   public boolean isUnique() {
      return this.unique;
   }

   public List<String> getDocPaths() {
      return this.docPaths;
   }

   public List<String> getTypes() {
      return this.types;
   }

   public List<Boolean> getNotNulls() {
      return this.notNulls;
   }
}
