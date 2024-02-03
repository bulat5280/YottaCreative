package com.mysql.cj.api.mysqla.result;

public interface Resultset extends ProtocolEntity {
   void setColumnDefinition(ColumnDefinition var1);

   ColumnDefinition getColumnDefinition();

   boolean hasRows();

   void initRowsWithMetadata();

   int getResultId();

   void setNextResultset(Resultset var1);

   Resultset getNextResultset();

   void clearNextResultset();

   long getUpdateCount();

   long getUpdateID();

   String getServerInfo();

   public static enum Type {
      FORWARD_ONLY(1003),
      SCROLL_INSENSITIVE(1004),
      SCROLL_SENSITIVE(1005);

      private int value;

      private Type(int jdbcRsType) {
         this.value = jdbcRsType;
      }

      public int getIntValue() {
         return this.value;
      }

      public static Resultset.Type fromValue(int rsType, Resultset.Type backupValue) {
         Resultset.Type[] var2 = values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Resultset.Type t = var2[var4];
            if (t.getIntValue() == rsType) {
               return t;
            }
         }

         return backupValue;
      }
   }

   public static enum Concurrency {
      READ_ONLY(1007),
      UPDATABLE(1008);

      private int value;

      private Concurrency(int jdbcRsConcur) {
         this.value = jdbcRsConcur;
      }

      public int getIntValue() {
         return this.value;
      }

      public static Resultset.Concurrency fromValue(int concurMode, Resultset.Concurrency backupValue) {
         Resultset.Concurrency[] var2 = values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Resultset.Concurrency c = var2[var4];
            if (c.getIntValue() == concurMode) {
               return c;
            }
         }

         return backupValue;
      }
   }
}
