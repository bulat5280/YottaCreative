package com.mysql.cj.api.x;

public interface ColumnDefinition<T extends ColumnDefinition<T>> {
   T notNull();

   T uniqueIndex();

   T primaryKey();

   T comment(String var1);

   T unsigned();

   T decimals(int var1);

   T charset(String var1);

   T collation(String var1);

   T binary();

   T values(String... var1);

   public interface GeneratedColumnDefinition extends ColumnDefinition<ColumnDefinition.GeneratedColumnDefinition> {
      ColumnDefinition.GeneratedColumnDefinition stored();
   }

   public interface StaticColumnDefinition extends ColumnDefinition<ColumnDefinition.StaticColumnDefinition> {
      ColumnDefinition.StaticColumnDefinition setDefault(String var1);

      ColumnDefinition.StaticColumnDefinition autoIncrement();

      ColumnDefinition.StaticColumnDefinition foreignKey(String var1, String... var2);
   }
}
