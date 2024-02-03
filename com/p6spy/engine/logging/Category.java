package com.p6spy.engine.logging;

public class Category {
   public static final Category ERROR = new Category("error");
   public static final Category WARN = new Category("warn");
   public static final Category INFO = new Category("info");
   public static final Category DEBUG = new Category("debug");
   public static final Category BATCH = new Category("batch");
   public static final Category STATEMENT = new Category("statement");
   public static final Category RESULTSET = new Category("resultset");
   public static final Category COMMIT = new Category("commit");
   public static final Category ROLLBACK = new Category("rollback");
   public static final Category RESULT = new Category("result");
   public static final Category OUTAGE = new Category("outage");
   private final String name;

   public Category(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      return this.name;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      int result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         Category other = (Category)obj;
         if (this.name == null) {
            if (other.name != null) {
               return false;
            }
         } else if (!this.name.equals(other.name)) {
            return false;
         }

         return true;
      }
   }
}
