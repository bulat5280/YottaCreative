package com.mysql.cj.api.x;

public interface CreateTableStatement {
   Table execute();

   public interface CreateTableLikeStatement extends CreateTableStatement {
   }

   public interface CreateTableFullStatement extends CreateTableStatement {
      CreateTableStatement.CreateTableFullStatement addColumn(ColumnDefinition<?> var1);

      CreateTableStatement.CreateTableFullStatement addPrimaryKey(String... var1);

      CreateTableStatement.CreateTableFullStatement addIndex(String var1, String... var2);

      CreateTableStatement.CreateTableFullStatement addUniqueIndex(String var1, String... var2);

      CreateTableStatement.CreateTableFullStatement addForeignKey(String var1, ForeignKeyDefinition var2);

      CreateTableStatement.CreateTableFullStatement setInitialAutoIncrement(Number var1);

      CreateTableStatement.CreateTableFullStatement setDefaultCharset(String var1);

      CreateTableStatement.CreateTableFullStatement setDefaultCollation(String var1);

      CreateTableStatement.CreateTableFullStatement setComment(String var1);

      CreateTableStatement.CreateTableFullStatement temporary();

      CreateTableStatement.CreateTableFullStatement as(String var1);

      CreateTableStatement.CreateTableFullStatement as(SelectStatement var1);
   }

   public interface CreateTableSplitStatement extends CreateTableStatement {
      CreateTableStatement.CreateTableFullStatement addColumn(ColumnDefinition<?> var1);

      CreateTableStatement.CreateTableFullStatement addPrimaryKey(String... var1);

      CreateTableStatement.CreateTableFullStatement addIndex(String var1, String... var2);

      CreateTableStatement.CreateTableFullStatement addUniqueIndex(String var1, String... var2);

      CreateTableStatement.CreateTableFullStatement addForeignKey(String var1, ForeignKeyDefinition var2);

      CreateTableStatement.CreateTableFullStatement setInitialAutoIncrement(Number var1);

      CreateTableStatement.CreateTableFullStatement setDefaultCharset(String var1);

      CreateTableStatement.CreateTableFullStatement setDefaultCollation(String var1);

      CreateTableStatement.CreateTableFullStatement setComment(String var1);

      CreateTableStatement.CreateTableFullStatement temporary();

      CreateTableStatement.CreateTableFullStatement as(String var1);

      CreateTableStatement.CreateTableFullStatement as(SelectStatement var1);

      CreateTableStatement.CreateTableLikeStatement like(String var1);
   }
}
