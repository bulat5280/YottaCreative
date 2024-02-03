package com.mysql.cj.api.x;

public interface ForeignKeyDefinition {
   ForeignKeyDefinition setName(String var1);

   ForeignKeyDefinition fields(String... var1);

   ForeignKeyDefinition refersTo(String var1, String... var2);

   ForeignKeyDefinition onDelete(ForeignKeyDefinition.ChangeMode var1);

   ForeignKeyDefinition onUpdate(ForeignKeyDefinition.ChangeMode var1);

   public static enum ChangeMode {
      RESTRICT,
      CASCADE,
      SET_NULL,
      NO_ACTION;

      public String getExpr() {
         switch(this) {
         case SET_NULL:
            return "SET NULL";
         case NO_ACTION:
            return "NO ACTION";
         default:
            return this.name();
         }
      }
   }
}
